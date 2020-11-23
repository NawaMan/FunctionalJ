// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.map;

import static java.util.stream.Collectors.joining;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.list.FuncListDerived;
import functionalj.map.MapAction.FilterBoth;
import functionalj.map.MapAction.FilterKey;
import functionalj.map.MapAction.Mapping;
import functionalj.map.MapAction.With;
import functionalj.stream.StreamPlus;
import functionalj.streamable.Streamable;
import lombok.val;


public class FuncMapDerived<KEY, SOURCE, VALUE> extends FuncMap<KEY, VALUE> {
    
    final Map<KEY, SOURCE> map;
    
    private final MapAction<KEY, SOURCE, VALUE> action;
    
    FuncMapDerived(Map<KEY, SOURCE> map, MapAction<KEY, SOURCE, VALUE> action) {
        this.map    = map;
        this.action = action;
    }
    
    public boolean isLazy() {
        return true;
    }
    
    public boolean isEager() {
        return false;
    }
    
    public FuncMap<KEY, VALUE> lazy() {
        return this;
    }
    public FuncMap<KEY, VALUE> eager() {
        return new ImmutableMap<KEY, VALUE>(this, false);
    }
    
    private Stream<Map.Entry<KEY, SOURCE>> originalEntryStream() {
        Stream<Map.Entry<KEY, SOURCE>> stream
            = (map instanceof FuncMap)
            ? ((FuncMap<KEY, SOURCE>)map).entries().stream()
            : map.entrySet().stream();
        return stream;
    }
    
    @SuppressWarnings("unchecked")
    StreamPlus<Map.Entry<KEY, VALUE>> entryStream() {
        if (action instanceof With) {
            val with = (With<KEY, VALUE>)action;
            Stream<Map.Entry<KEY, VALUE>> entries 
                    = originalEntryStream()
                        .filter  (e -> !Objects.equals(e.getKey(), with.key))
                        .map     (e -> (Map.Entry<KEY, VALUE>)e);
            Stream<Map.Entry<KEY, VALUE>> combined = StreamPlus.concat(
                entries,
                Stream.of(FuncMap.Entry.of(with.key, with.value))
            );
            if (map instanceof TreeMap) {
                val comparator = ((TreeMap<KEY, SOURCE>)map).comparator();
                combined = combined.sorted((a, b) -> {
                    val aKey = a.getKey();
                    val bKey = b.getKey();
                    return comparator.compare(aKey, bKey);
                });
            }
            return StreamPlus.from(combined);
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            Stream<Map.Entry<KEY, VALUE>> entries
                    =  originalEntryStream()
                        .filter  (e -> filter.keyCheck.test(e.getKey()))
                        .map     (e -> (Map.Entry<KEY, VALUE>)e);
            return StreamPlus.from(entries);
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            Stream<Map.Entry<KEY, VALUE>> entries
                    = originalEntryStream()
                       .map     (e -> (Map.Entry<KEY, VALUE>)e)
                       .filter  (e -> check.test(e));
            return StreamPlus.from(entries);
        }
        if (action instanceof Mapping) {
            val mapper = (Mapping<KEY, SOURCE, VALUE>)action;
            Stream<Map.Entry<KEY, VALUE>> entries
                    = originalEntryStream()
                       .map(e -> {
                           val key = e.getKey();
                           val orgValue = e.getValue();
                           val newValue = mapper.mapper.apply(key, orgValue);
                           val newEntry = FuncMap.Entry.of(key, newValue);
                           return newEntry;
                       });
            return StreamPlus.from(entries);
        }
        
        val source 
                = originalEntryStream()
                .map(e -> (Map.Entry<KEY, VALUE>)e);
        return StreamPlus.from(source);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public int size() {
        if (action instanceof With) {
            val with = (With<KEY,SOURCE>)action;
            if (map.containsKey(with.key))
                return map.size();
            
            return map.size() + 1;
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, SOURCE>)action;
            return (int)map.keySet().stream().filter(filter.keyCheck).count();
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, SOURCE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, SOURCE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            return (int)originalEntryStream()
                    .filter(check)
                    .count();
        }
        return map.size();
    }
    @Override
    public boolean isEmpty() {
        if (action instanceof With)
            return false;
        
        // TODO - Find a faster way.
        return size() == 0;
    }
    
    public String toString() {
        return "{" +
                entryStream()
                    .map(each -> each.getKey() + ":" + each.getValue())
                    .collect(joining(", ")) +
                "}";
    }
    
    @Override
    public int hashCode() {
        int hashCode = entryStream()
                .mapToInt(each -> Objects.hashCode(each.getKey())*37 + Objects.hashCode(each.getValue()))
                .sum();
        return 43 + FuncMap.class.hashCode() + hashCode;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map))
            return false;
        
        // TODO - not efficient
        // Try to use: entryStream()
        val thisMap = toImmutableMap();
        
        val keyExist = ((Predicate<KEY>)((Map)o)::containsKey).negate();
        val hasMissingKey = thisMap.keys().anyMatch(keyExist);
        if (hasMissingKey)
            return false;
        
        val thatMap = ImmutableMap.from((Map)o);
        if (thatMap.size() != thisMap.size())
            return false;
        
        val matchEntry = (Predicate<? super Map.Entry<KEY, VALUE>>)(t -> Objects.equals(thatMap.get(t.getKey()), t.getValue()));
        val allMatchValue 
                = thisMap
                .entries()
                .allMatch(matchEntry);
        return allMatchValue;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean hasKey(Predicate<? super KEY> keyCheck) {
        if (action instanceof With) {
            val with = (With<KEY,VALUE>)action;
            if (keyCheck.test(with.key))
                return true;
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            return map
                    .keySet  ().stream()
                    .filter  (filter.keyCheck)
                    .anyMatch(keyCheck);
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            return originalEntryStream()
                    .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                    .anyMatch(e -> keyCheck.test(e.getKey()));
        }
        
        return map
                .keySet().stream()
                .anyMatch(keyCheck);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean hasKey(KEY key) {
        if (action instanceof With) {
            val with = (With<KEY,VALUE>)action;
            if (Objects.equals(key, with.key))
                return true;
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            if (!filter.keyCheck.test(key))
                return false;
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            return originalEntryStream()
                    .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                    .anyMatch(e -> Objects.equals(e.getKey(), key));
        }
        
        return map.containsKey(key);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        return hasKey((KEY)key);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean hasValue(Predicate<? super VALUE> valueCheck) {
        if (action instanceof With) {
            val with = (With<KEY,VALUE>)action;
            if (valueCheck.test(with.value))
                return true;
        }
        
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, SOURCE>)action;
            return originalEntryStream()
                    .filter  (e -> filter.keyCheck.test(e.getKey()))
                    .anyMatch(e -> valueCheck.test((VALUE)e.getValue()));
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            return originalEntryStream()
                    .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                    .anyMatch(e -> valueCheck.test((VALUE)e.getValue()));
        }
        if (action instanceof Mapping) {
            val mapping = (Mapping<KEY, SOURCE, VALUE>)action;
            val mpapper = mapping.mapper;
            return originalEntryStream()
                    .map     (e -> mpapper.apply(e.getKey(), e.getValue()))
                    .anyMatch(v -> valueCheck.test(v));
        }
        
        return map
                .values().stream()
                .map     (v -> ((VALUE)v))
                .anyMatch(v -> valueCheck.test(v));
    }
    
    @Override
    public boolean hasValue(VALUE value) {
        return hasValue(v -> Objects.equals(v, value));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean containsValue(Object value) {
        return hasValue((VALUE)value);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Optional<VALUE> findBy(KEY key) {
        if (action instanceof With) {
            val with = (With<KEY,VALUE>)action;
            if (Objects.equals(key, with.key))
                return Optional.ofNullable(with.value);
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            if (!filter.keyCheck.test(key))
                return Optional.empty();
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            
            val hasKey = originalEntryStream()
                    .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                    .anyMatch(e -> Objects.equals(e.getKey(), key));
            if (!hasKey)
                return Optional.empty();
        }
        if (action instanceof Mapping) {
            val mapping = (Mapping<KEY, SOURCE, VALUE>)action;
            val mapper  = mapping.mapper;
            
            val orgValue = map.get(key);
            if (orgValue == null) {
                return Optional.empty();
            }
            
            val newValue = mapper.apply(key, orgValue);
            if (newValue == null) {
                return Optional.empty();
            }
            return Optional.of(newValue);
        }
        
        val value = map.get(key);
        return Optional
                .ofNullable((VALUE)value);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public VALUE get(Object keyObj) {
        KEY key = (KEY)keyObj;
        if (action instanceof With) {
            val with = (With<KEY,VALUE>)action;
            if (Objects.equals(key, with.key))
                return with.value;
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            if (!filter.keyCheck.test((KEY)key))
                return null;
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            
            val hasKey = originalEntryStream()
                    .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                    .anyMatch(e -> Objects.equals(e.getKey(), key));
            if (!hasKey)
                return null;
        }
        if (action instanceof Mapping) {
            val mapping = (Mapping<KEY, SOURCE, VALUE>)action;
            val mapper  = mapping.mapper;
            
            val orgValue = map.get(key);
            if (orgValue == null) {
                return null;
            }
            
            val newValue = mapper.apply(key, orgValue);
            if (newValue == null) {
                return null;
            }
            return newValue;
        }
        
        val value = (VALUE)map.get(key);
        return value;
    }
    
    @Override
    public VALUE getOrDefault(Object key, VALUE orElse) {
        VALUE v;
        return (((v = get(key)) != null) || containsKey(key))
            ? v
            : orElse;
    }
    
    @Override
    public FuncList<VALUE> select(Predicate<? super KEY> keyPredicate) {
        return entryStream()
                .filter(Map.Entry::getKey, keyPredicate)
                .map   (Map.Entry::getValue)
                .toList();
    }
    
    @Override
    public FuncList<Map.Entry<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate) {
        return entryStream()
                .filter(Map.Entry::getKey, keyPredicate)
                .map(e -> (Map.Entry<KEY, VALUE>)e)
                .toList();
    }
    
    @Override
    public FuncMap<KEY, VALUE> with(KEY key, VALUE value) {
        val action = new With<KEY, VALUE>(key, value);
        return new FuncMapDerived<KEY, VALUE, VALUE>(this, action);
    }
    
    @Override
    public FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries) {
        // TODO: Find the way to do it
        val newMap = new HashMap<>(this.toMap());
        newMap.putAll(entries);
        return ImmutableMap.from(newMap);
    }
    
    @Override
    public FuncMap<KEY, VALUE> exclude(KEY key) {
        val action = new FilterKey<KEY, VALUE>(k -> !Objects.equals(k, key));
        return new FuncMapDerived<KEY, VALUE, VALUE>(this, action);
    }
    
    @Override
    public FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck) {
        val action = new FilterKey<KEY, VALUE>(keyCheck);
        return new FuncMapDerived<KEY, VALUE, VALUE>(this, action);
    }
    
    @Override
    public FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        val action = new FilterBoth<KEY, VALUE>(entryCheck);
        return new FuncMapDerived<KEY, VALUE, VALUE>(this, action);
    }
    
    @Override
    public FuncMap<KEY, VALUE> filterByEntry(Predicate<? super Map.Entry<? super KEY, ? super VALUE>> entryCheck) {
        val action = new FilterBoth<KEY, VALUE>((k, v) -> entryCheck.test(FuncMap.Entry.of(k,  v)));
        return new FuncMapDerived<KEY, VALUE, VALUE>(this, action);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FuncList<KEY> keys() {
        if (action instanceof With) {
            val with   = (With<KEY,VALUE>)action;
            val source = Streamable.from(()->{
                StreamPlus<KEY> stream = StreamPlus.concat(
                    map.keySet().stream()
                       .filter(k -> !Objects.equals(k, with.key)),
                   Stream.of(with.key)
                );
                if (map instanceof TreeMap) {
                    val treeMap    = (TreeMap<KEY, SOURCE>)map;
                    val comparator = treeMap.comparator();
                    stream = stream.sorted(comparator);
                }
                return stream;
            });
            return FuncListDerived.from(source);
        }
        if (action instanceof FilterKey) {
            val filter = (FilterKey<KEY, VALUE>)action;
            val source = (Streamable<KEY>)(()->StreamPlus.from(map.keySet().stream().filter(filter.keyCheck)));
            return FuncListDerived.from(source);
        }
        if (action instanceof FilterBoth) {
            val filter = (FilterBoth<KEY, VALUE>)action;
            val check  = (Predicate<? super Map.Entry<KEY, VALUE>>)(e -> filter.check.test(e.getKey(), e.getValue()));
            val source = (Streamable<KEY>)(()->{
                
                return StreamPlus.from(
                        originalEntryStream()
                                .filter  (e -> check.test((Map.Entry<KEY, VALUE>)e))
                                .map     (Map.Entry::getKey));
            });
            return FuncListDerived.from(source);
        }
        
        return FuncListDerived.from(()->map.keySet().stream());
    }
    
    @Override
    public FuncList<VALUE> values() {
        return FuncListDerived.from(()->entryStream().map(Map.Entry::getValue));
    }
    
    @Override
    public Set<KEY> keySet() {
        if (action != null)
            return keys().toSet();
        
        return map.keySet();
    }
    
    @Override
    public Set<Map.Entry<KEY, VALUE>> entrySet() {
        return entryStream()
                .toSet();
    }
    @Override
    public FuncList<Map.Entry<KEY, VALUE>> entries() {
        return FuncList.from((Streamable<Map.Entry<KEY, VALUE>>)(()->entryStream()));
    }
    
    @Override
    public Map<KEY, VALUE> toMap() {
        return this;
    }
    
    @Override
    public ImmutableMap<KEY, VALUE> toImmutableMap() {
        return ImmutableMap.from(this);
    }
    
    @Override
    public FuncMap<KEY, VALUE> sorted() {
        val map = new TreeMap<KEY, VALUE>();
        entryStream()
            .forEach(e -> map.put(e.getKey(), e.getValue()));
        return new ImmutableMap<KEY, VALUE>(map, isLazy());
    }
    
    @Override
    public FuncMap<KEY, VALUE> sorted(Comparator<? super Map.Entry<KEY, VALUE>> comparator) {
        val map = new LinkedHashMap<KEY, VALUE>();
        this
        .entryStream()
        .sorted (comparator)
        .forEach(e -> map.put(e.getKey(), e.getValue()));
        return FuncMap.from(map);
    }
    
    @Override
    public FuncMap<KEY, VALUE> sortedByKey(Comparator<? super KEY> comparator) {
        val map = new TreeMap<KEY, VALUE>(comparator);
        entryStream()
        .forEach(e -> map.put(e.getKey(), e.getValue()));
        return new ImmutableMap<KEY, VALUE>(map, isLazy());
    }
    
    @Override
    public FuncMap<KEY, VALUE> sortedByValue(Comparator<? super VALUE> comparator) {
        return sorted((e1, e2)->comparator.compare(e1.getValue(), e2.getValue()));
    }
    
    @Override
    public <TARGET> FuncMap<KEY, TARGET> mapEntry(BiFunction<? super KEY, ? super VALUE, ? extends TARGET> mapper) {
        val mapFunc = Func2.from(mapper);
        val mapping = new MapAction.Mapping<KEY, VALUE, TARGET>(mapFunc);
        val mapped  = new FuncMapDerived<KEY, VALUE, TARGET>(this, mapping);
        if (isEager()) {
            return mapped.toImmutableMap();
        }
        return mapped;
    }
    
    @Override
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action) {
        entryStream()
        .forEach(entry -> {
            val key   = entry.getKey();
            val value = entry.getValue();
            action.accept(key, value);
        });
    }
    
    @Override
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action) {
        entryStream()
        .forEach(action);
    }
    
}
