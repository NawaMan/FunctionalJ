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

import static functionalj.function.Func.it;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static java.util.Arrays.stream;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.ref.Ref;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.Tuple2;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class FuncMap<KEY, VALUE>
                    implements
                        ReadOnlyMap<KEY, VALUE>, 
                        IFuncMap<KEY, VALUE, FuncMap<KEY, VALUE>> {
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static enum UnderlineMap {
        HashMap      (java.util.HashMap::new),
        LinkedHashMap(java.util.LinkedHashMap::new),
        TreeMap      (java.util.TreeMap::new);
        
        private final Func0<Map> newMap;
        private UnderlineMap(Func0<Map> newMap) {
            this.newMap = newMap;
        }
        <K, V> Map<K, V> newMap() {
            return (Map<K, V>)newMap.apply();
        }
    }
    
    public static Ref<UnderlineMap> underlineMap = Ref.ofValue(UnderlineMap.HashMap);
    
    public static class Entry<KEY, VALUE> implements Map.Entry<KEY, VALUE>, Tuple2<KEY, VALUE> {
        
        private final KEY   key;
        private final VALUE value;
        
        public static <K, V> Entry<K, V> of(K key, V value) {
            return new Entry<K, V>(key, value);
        }
        public static <K, V> Entry<K, V> of(Map.Entry<K, V> entry) {
            if (entry == null)
                return null;
            
            if (entry instanceof Entry)
                return (Entry<K, V>)entry;
            
            K key   = entry.getKey();
            V value = entry.getValue();
            return new Entry<K, V>(key, value);
        }
        
        public Entry(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public final KEY _1() {
            return key;
        }
        @Override
        public final VALUE _2() {
            return value;
        }
        @Override
        public final KEY getKey() {
            return key;
        }
        @Override
        public final VALUE getValue() {
            return value;
        }
        @Override
        public final VALUE setValue(VALUE value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public final String toString() {
            return key + "=" + value;
        }
        @Override
        public final int hashCode() {
            return Entry.class.hashCode() + Objects.hash(key, value);
        }
        @SuppressWarnings("unchecked")
        @Override
        public final boolean equals(Object obj) {
            if (obj == this)
                return true;
            
            if (!(obj instanceof Entry))
                return false;
            
            val entry = (Entry<KEY, VALUE>)obj;
            return Objects.equals(entry.getKey(),   key)
                && Objects.equals(entry.getValue(), value);
        }
        
    }
    
    public static <K, V> ImmutableMap<K, V> empty() {
        return ImmutableMap.empty();
    }
    
    public static <K, V> ImmutableMap<K, V> of(Map<? extends K, ? extends V> map) {
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(Stream<? extends Map.Entry<? extends K, ? extends V>> stream) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream
        .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return new ImmutableMap<K, V>(map);
    }
    
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> ofEntries(Map.Entry<K, V> ... entries) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream(entries)
            .forEach(entry -> map.putIfAbsent(entry.getKey(), entry.getValue()));
        return new ImmutableMap<K, V>(map);
    }
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> ofTuples(Tuple2<K, V> ... entries) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream(entries)
            .forEach(entry -> map.putIfAbsent(entry._1(), entry._2()));
        return new ImmutableMap<K, V>(map);
    }
    
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8,
            K key9, V value9) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        map.putIfAbsent(key9, value9);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8,
            K key9, V value9,
            K key10, V value10) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        map.putIfAbsent(key9, value9);
        map.putIfAbsent(key10, value10);
        return new ImmutableMap<K, V>(map);
    }

    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8,
            K key9, V value9) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        map.putIfAbsent(key9, value9);
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8,
            K key9, V value9,
            K key10, V value10) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        map.putIfAbsent(key0, value0);
        map.putIfAbsent(key1, value1);
        map.putIfAbsent(key2, value2);
        map.putIfAbsent(key3, value3);
        map.putIfAbsent(key4, value4);
        map.putIfAbsent(key5, value5);
        map.putIfAbsent(key6, value6);
        map.putIfAbsent(key7, value7);
        map.putIfAbsent(key8, value8);
        map.putIfAbsent(key9, value9);
        map.putIfAbsent(key10, value10);
        return new ImmutableMap<K, V>(map);
    }
    
    // TODO Map builder.
    
    public boolean isLazy() {
        return true;
    }
    
    public boolean isEager() {
        return false;
    }
    
    public abstract FuncMap<KEY, VALUE> lazy();
    public abstract FuncMap<KEY, VALUE> eager();
    
    @Override
    public abstract int size();
    
    @Override
    public abstract boolean isEmpty();
    
    @Override
    public abstract boolean hasKey(KEY key);
    
    @Override
    public abstract boolean hasValue(VALUE value);
    
    @Override
    public abstract boolean hasKey(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    @Override
    public abstract Optional<VALUE> findBy(KEY key);

    @Override
    public abstract FuncList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FuncList<Map.Entry<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FuncMap<KEY, VALUE> exclude(KEY key);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filterByEntry(Predicate<? super Map.Entry<? super KEY, ? super VALUE>> entryCheck);
    
    @Override
    public abstract FuncList<KEY> keys();
    
    @Override
    public abstract FuncList<VALUE> values();
    
    @Override
    public abstract Set<Map.Entry<KEY, VALUE>> entrySet();
    
    @Override
    public abstract FuncList<Map.Entry<KEY, VALUE>> entries();
    
    @Override
    public abstract Map<KEY, VALUE> toMap();
    
    @Override
    public abstract ImmutableMap<KEY, VALUE> toImmutableMap();
    
    public ImmutableMap<KEY, VALUE> freeze() {
        return toImmutableMap();
    }
    
    @Override
    public abstract FuncMap<KEY, VALUE> sorted();
    
    @Override
    public abstract FuncMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    @Override
    public abstract <TARGET> FuncMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper);
    
    @Override
    public abstract void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    @Override
    public abstract void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action);
    
    public <IN, OUT> FuncMap<KEY, OUT> zipWith(Map<KEY, IN> anotherMap, Func2<VALUE, IN, OUT> merger) {
        return zipWith(anotherMap, RequireBoth, merger);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <IN, OUT> FuncMap<KEY, OUT> zipWith(Map<KEY, IN> anotherMap, ZipWithOption option, Func2<VALUE, IN, OUT> merger) {
        val keys1 = this.keys();
        val keys2 = FuncList.from(anotherMap.keySet());
        val map   = keys1.appendAll(keys2.excludeIn(keys1))
        .filter(key -> !(option == RequireBoth) || (this.containsKey(key) && anotherMap.containsKey(key)))
        .toMap(it(), key -> {
            val v1 = this.get(key);
            val v2 = anotherMap.get(key);
            return merger.apply(v1, v2);
        });
        return (FuncMap)map;
    }
    
    public String toString() {
        return "{" +
                entries()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")) +
                "}";
    }
    
    @Override
    public int hashCode() {
        return FuncMap.class.hashCode() + entries().hashCode();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map))
            return false;
        
        val thatMap = (Map<KEY, VALUE>)o;
        if (thatMap.size() != size())
            return false;
        
        val keyExist = ((Predicate<KEY>)((Map)o)::containsKey).negate();
        val hasMissingKey = keys().anyMatch(keyExist);
        if (hasMissingKey)
            return false;
        
        val matchEntry = (Predicate<? super Map.Entry<KEY, VALUE>>)(t -> {
            val key       = t.getKey();
            val thatValue = this.get(key);
            val thisValue = t.getValue();
            return Objects.equals(thatValue, thisValue);
        });
        val allMatchValue 
                = thatMap
                .entrySet().stream()
                .allMatch(matchEntry);
        return allMatchValue;
    }
    
}
