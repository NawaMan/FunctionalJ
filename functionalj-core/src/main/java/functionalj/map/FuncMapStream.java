// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.stream.Collectors.toSet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.list.FuncListStream;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.IntTuple2;
import lombok.val;

@SuppressWarnings("javadoc")
public class FuncMapStream<KEY, VALUE> extends FuncMap<KEY, VALUE> {
    
    private static final Supplier<Stream<IntTuple2<ImmutableTuple2<?, ?>>>> EmptyStreamSupplier = ()->Stream.empty();
    
    private final FuncList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> entries;
    private final boolean isKeyComparable;
    
    FuncMapStream(Boolean isKeyComparable, FuncList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> entries) {
        // TODO - Thinking about sorting by hash to take advantage of binary search
        this.entries         = entries;
        this.isKeyComparable = (isKeyComparable != null)
                ? isKeyComparable.booleanValue()
                : entries.allMatch(entry -> 
                    (entry._2._1 == null) || (entry._2._1 instanceof Comparable));
    }
    
    private Integer calculateHash(Object key) {
        return Optional.ofNullable(key)
                .map   (Object::hashCode)
                .orElse(0);
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
    @SuppressWarnings({ "unchecked", "rawtypes"})
    public FuncMap<KEY, VALUE> eager() {
        Stream entries = this.entries.stream();
        return new ImmutableMap<KEY, VALUE>(entries, false);
    }
    
    @Override
    public int size() {
        return entries.size();
    }
    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
    
    @Override
    public int hashCode() {
        return FuncMap.class.hashCode() + entries.hashCode();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map))
            return false;
        
        val thisMap = toImmutableMap();
        
        val keyExist = ((Predicate<Object>)((Map)o)::containsKey).negate();
        val hasMissingKey = thisMap.keys().anyMatch(keyExist);
        if (hasMissingKey)
            return false;
        
        val thatMap = ImmutableMap.from((Map)o);
        if (thatMap.size() != thisMap.size())
            return false;
        
        val matchEntry = (Predicate<? super ImmutableTuple2<KEY, VALUE>>)(t -> Objects.equals(thatMap.get(t._1), t._2));
        val allMatchValue 
                = thisMap
                .entries()
                .allMatch(matchEntry);
        return allMatchValue;
    }
    
    @Override
    public boolean hasKey(Predicate<? super KEY> keyCheck) {
        return entries.anyMatch(entry -> keyCheck.test(entry._2._1));
    }

    @Override
    public boolean hasValue(Predicate<? super VALUE> valueCheck) {
        return entries.anyMatch(entry -> valueCheck.test(entry._2._2));
    }
    @Override
    public boolean hasKey(KEY key) {
        return containsKey(key);
    }
    
    @Override
    public boolean hasValue(VALUE value) {
        return containsValue(value);
    }
    
    @Override
    public boolean containsKey(Object key) {
        val keyHash = calculateHash(key);
        return StreamPlus.Helper.hasAt(
                entries
                    .filter(entry -> entry._int() == keyHash)
                    .filter(entry -> Objects.equals(key, entry._2._1))
                    .stream(),
                0L);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return StreamPlus.Helper.hasAt(
                entries
                    .filter(entry -> Objects.equals(value, entry._2._2))
                    .stream(),
                0L);
    }
    
    @Override
    public Optional<VALUE> findBy(KEY key) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(entry._2._1, key))
                .map    (entry -> entry._2()._2())
                .findAny();
    }
    
    @Override
    public VALUE get(Object key) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(entry._2._1, key))
                .map    (entry -> entry._2._2)
                .findAny()
                .orElse (null);
    }
    
    @Override
    public VALUE getOrDefault(Object key, VALUE orElse) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(key, entry._2._1))
                .map    (entry -> entry._2._2)
                .findAny()
                .orElse (orElse);
    }
    
    @Override
    public FuncList<VALUE> select(Predicate<? super KEY> keyPredicate) {
        return entries
                .filter(entry -> keyPredicate.test(entry._2._1))
                .map   (entry -> entry._2._2);
    }
    
    @Override
    public FuncList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate) {
        return entries
                .filter(entry -> keyPredicate.test(entry._2._1))
                .map   (entry -> entry._2);
    }
    
    @Override
    public FuncMap<KEY, VALUE> with(KEY key, VALUE value) {
        // Find the way to put in it in the same location.
        int keyHash    = calculateHash(key);
        val valueEntry = new ImmutableTuple2<KEY, VALUE>(key, value);
        val mapEntry   = new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, valueEntry);
        val newEntries = entries
                            .filter(entry -> !Objects.equals(key, entry._2._1))
                            .append(mapEntry);
        val newIsKeyComparable = isKeyComparable && ((key == null) || (key instanceof Comparable));
        return derivedWith(newIsKeyComparable, newEntries);
    }
    
    @Override
    public FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries) {
        val newMap = new HashMap<>(this.toMap());
        newMap.putAll(entries);
        return ImmutableMap.of(newMap);
    }
    
    @Override
    public FuncMap<KEY, VALUE> defaultTo(KEY key, VALUE value) {
        return defaultBy(key, oldValue -> value);
    }
    
    @Override
    public FuncMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> valueSupplier) {
        return defaultBy(key, oldValue -> valueSupplier.get());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public FuncMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> valueFunction) {
        int keyHash = calculateHash(key);
        val newIsKeyComparable = isKeyComparable && ((key == null) || (key instanceof Comparable));
        val streamable = (Streamable)(() -> {
                AtomicReference<Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>> ref = new AtomicReference<>(()->{
                        val valueEntry = new ImmutableTuple2<KEY, VALUE>(key, valueFunction.apply(key));
                        val mapEntry   = new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, valueEntry);
                        return Stream.of(mapEntry);
                    });
                    val main = new AtomicReference<Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>>(()->{
                        Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>> stream = entries
                        .filter(entry -> {
                            boolean found = (entry._1 == keyHash) && Objects.equals(key, entry._2._1);
                            if (found) {
                                ref.set((Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>)(Supplier)EmptyStreamSupplier);
                            }
                            return true;
                        }).stream();
                        return stream;
                    });
                    return (StreamPlus<IntTuple2<ImmutableTuple2<KEY, VALUE>>>)StreamPlus.of(main, ref).
                            flatMap(each -> each.get().get());
                });
        return derivedWith(newIsKeyComparable, FuncListStream.from(streamable));
    }
    
    @Override
    public FuncMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries) {
        return null;
    }
    
    @Override
    public FuncMap<KEY, VALUE> exclude(KEY key) {
        return derivedWith(isKeyComparable,
                entries
                    .filter(entry -> !Objects.equals(key, entry._2._1)));
    }
    
    @Override
    public FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck) {
        return derivedWith(isKeyComparable, 
                entries
                    .filter(entry -> keyCheck.test(entry._2._1)));
    }
    
    @Override
    public FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        return derivedWith(isKeyComparable, 
                entries
                    .filter(entry -> entryCheck.test(entry._2._1, entry._2._2)));
    }
    
    @Override
    public FuncMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck) {
        return derivedWith(isKeyComparable, 
                entries
                    .filter(entry -> entryCheck.test(entry._2)));
    }
    
    @Override
    public FuncList<KEY> keys() {
        return new FuncListStream<>(
                entries.map(each -> each._2._1));
    }
    
    @Override
    public FuncList<VALUE> values() {
        return new FuncListStream<>(
                entries.map(each -> each._2._2));
    }
    
    @Override
    public Set<KEY> keySet() {
        return entries
                .map(each -> each._2._1)
                .collect(toSet());
    }
    
    @Override
    public Set<Entry<KEY, VALUE>> entrySet() {
        return entries
                .map    (each -> each._2)
                .collect(toSet());
    }
    @Override
    public FuncList<ImmutableTuple2<KEY, VALUE>> entries() {
        return new FuncListStream<>(
                entries.map(each -> each._2));
    }
    
    @Override
    public Map<KEY, VALUE> toMap() {
        return this;
    }
    
    @Override
    public ImmutableMap<KEY, VALUE> toImmutableMap() {
        return ImmutableMap.of(this);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FuncMap<KEY, VALUE> sorted() {
        if (isKeyComparable) {
            return derivedWith(isKeyComparable, 
                    entries.sorted((t1,t2)-> {
                        val k1 = t1._2._1;
                        val k2 = t2._2._1;
                        if (k1 == k2)
                            return 0;
                        if (k1 == null)
                            return -1;
                        if (k2 == null)
                            return 1;
                        return ((Comparable)k1).compareTo(k2);
                    }));
        }
        
        return new FuncMapStream<>(isKeyComparable, 
                entries
                .sorted((t1,t2)->t1._int() - t2._int()));
    }
    
    @Override
    public FuncMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator) {
        return derivedWith(isKeyComparable, 
                entries
                .sorted((t1,t2)->comparator.compare(t1._2._1, t2._2._1)));
    }
    
    @Override
    public <TARGET> FuncMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper) {
        return derivedWith(isKeyComparable, 
                entries
                .map(intTuple -> 
                    new IntTuple2<>(intTuple._1, 
                            new ImmutableTuple2<>(intTuple._2._1, 
                                    mapper.apply(intTuple._2._2)))));
    }
    
    @Override
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action) {
        entries
            .map(entry -> entry._2)
            .forEach(entry -> action.accept(entry._1, entry._2));
    }
    
    @Override
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action) {
        entries
        .map(entry -> entry._2)
        .forEach(entry -> action.accept(entry));
    }

}
