// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.ref.Ref;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.Tuple2;
import lombok.val;


public abstract class FuncMap<KEY, VALUE>
                    implements
                        ReadOnlyMap<KEY, VALUE> {
    
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
    
    public static <K, V> ImmutableMap<K, V> emptyMap() {
        return ImmutableMap.empty();
    }
    
    public static <K, V> ImmutableMap<K, V> empty(Class<K> keyClass, Class<V> valueClass) {
        return ImmutableMap.empty();
    }
    
    public static <K, V> ImmutableMap<K, V> emptyMap(Class<K> keyClass, Class<V> valueClass) {
        return ImmutableMap.empty();
    }
    
    public static <K, V> ImmutableMap<K, V> from(Map<? extends K, ? extends V> map) {
        return new ImmutableMap<K, V>(map);
    }
    public static <K, V> ImmutableMap<K, V> from(Stream<? extends Map.Entry<? extends K, ? extends V>> stream) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream
        .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return new ImmutableMap<K, V>(map);
    }
    
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> ofEntries(Map.Entry<K, V> ... entries) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream(entries)
            .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return new ImmutableMap<K, V>(map);
    }
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> ofTuples(Tuple2<K, V> ... entries) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        stream(entries)
            .forEach(entry -> map.put(entry._1(), entry._2()));
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> of(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        if (key9 != null) map.put(key9, value9);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        if (key9 != null) map.put(key9, value9);
        if (key10 != null) map.put(key10, value10);
        return new ImmutableMap<K, V>(map);
    }
    
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
    public static <K, V> ImmutableMap<K, V> mapOf(
            K key0, V value0,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6) {
        val map = underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        if (key9 != null) map.put(key9, value9);
        return new ImmutableMap<K, V>(map);
    }
    
    /**
     * Create an immutable map with using the key-value pair.
     * Note: the pair will be ignore if the key is null.
     * */
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
        if (key0 != null) map.put(key0, value0);
        if (key1 != null) map.put(key1, value1);
        if (key2 != null) map.put(key2, value2);
        if (key3 != null) map.put(key3, value3);
        if (key4 != null) map.put(key4, value4);
        if (key5 != null) map.put(key5, value5);
        if (key6 != null) map.put(key6, value6);
        if (key7 != null) map.put(key7, value7);
        if (key8 != null) map.put(key8, value8);
        if (key9 != null) map.put(key9, value9);
        if (key10 != null) map.put(key10, value10);
        return new ImmutableMap<K, V>(map);
    }
    
    // Map builder.
    
    public static <K, V> FuncMapBuilder<K, V> newFuncMap() {
        return new FuncMapBuilder<K, V>();
    }
    
    public static <K, V> FuncMapBuilder<K, V> newMap() {
        return new FuncMapBuilder<K, V>();
    }
    
    public static <K, V> FuncMapBuilder<K, V> newBuilder() {
        return new FuncMapBuilder<K, V>();
    }
    
    public static <K, V> FuncMapBuilder<K, V> newFuncMap(Class<K> keyClass, Class<V> valueClass) {
        return new FuncMapBuilder<K, V>();
    }
    
    public static <K, V> FuncMapBuilder<K, V> newMap(Class<K> keyClass, Class<V> valueClass) {
        return new FuncMapBuilder<K, V>();
    }
    
    public static <K, V> FuncMapBuilder<K, V> newBuilder(Class<K> keyClass, Class<V> valueClass) {
        return new FuncMapBuilder<K, V>();
    }
    
    // Functionalities
    
    public <TARGET> FuncMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper) {
        return mapValue(v->mapper.apply(v));
    }
    
    public <TARGET> FuncMap<KEY, TARGET> map(BiFunction<? super KEY, ? super VALUE, ? extends TARGET> mapper) {
        return mapEntry((k, v)->mapper.apply(k, v));
    }
    
    public <TARGET> FuncMap<KEY, TARGET> mapValue(Function<? super VALUE, TARGET> mapper) {
        return map((k, v)->mapper.apply(v));
    }
    
    public abstract <TARGET> FuncMap<KEY, TARGET> mapEntry(BiFunction<? super KEY, ? super VALUE, ? extends TARGET> mapper);
    
    
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
    
    public abstract boolean hasKey(KEY key);
    
    public abstract boolean hasValue(VALUE value);
    
    public abstract boolean hasKey(Predicate<? super KEY> keyCheck);
    
    public abstract boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    public abstract Optional<VALUE> findBy(KEY key);
    
    public abstract FuncList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    public abstract FuncList<Map.Entry<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    public abstract FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    public abstract FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    public abstract FuncMap<KEY, VALUE> exclude(KEY key);
    
    public abstract FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    public abstract FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    @SuppressWarnings("unchecked")
    public FuncMap<KEY, VALUE> filterByValue(Predicate<? super VALUE> valueCheck) {
        return filterByEntry(entry -> valueCheck.test((VALUE)entry.getValue()));
    }
    
    public abstract FuncMap<KEY, VALUE> filterByEntry(Predicate<? super Map.Entry<? super KEY, ? super VALUE>> entryCheck);
    
    public abstract FuncList<KEY> keys();
    
    public abstract FuncList<VALUE> values();
    
    public abstract Set<Map.Entry<KEY, VALUE>> entrySet();
    
    public abstract FuncList<Map.Entry<KEY, VALUE>> entries();
    
    public abstract Map<KEY, VALUE> toMap();
    
    public abstract ImmutableMap<KEY, VALUE> toImmutableMap();
    
    public Func1<KEY, VALUE> toFunction() {
        return this::get;
    }
    
    public Func1<KEY, VALUE> toFunction(VALUE elseValue) {
        return key -> {
            try {
                val value = this.get(key);
                return (value == null) ? elseValue : value;
            } catch (Exception e) {
                return elseValue;
            }
        };
    }
    
    public Func1<KEY, VALUE> toFunction(Func0<VALUE> elseSupplier) {
        return key -> {
            try {
                val value = this.get(key);
                return (value == null) ? elseSupplier.get() : value;
            } catch (Exception e) {
                return elseSupplier.get();
            }
        };
    }
    
    public Func1<KEY, VALUE> toFunction(Func1<KEY, VALUE> elseProvider) {
        return key -> {
            try {
                val value = this.get(key);
                return (value == null) ? elseProvider.apply(key) : value;
            } catch (Exception e) {
                return elseProvider.apply(key);
            }
        };
    }
    
    public Func1<KEY, VALUE> toFunction(FuncUnit1<KEY> action, VALUE elseValue) {
        return key -> {
            try {
                val value = this.get(key);
                if (value == null)
                    action.accept(key);
                
                return value;
            } catch (Exception e) {
                return elseValue;
            }
        };
    }
    
    public Func1<KEY, VALUE> toFunction(FuncUnit1<KEY> action, Func0<VALUE> elseSupplier) {
        return key -> {
            try {
                val value = this.get(key);
                if (value == null)
                    action.accept(key);
                
                return (value == null) ? elseSupplier.get() : value;
            } catch (Exception e) {
                return elseSupplier.get();
            }
        };
    }
    
    public Func1<KEY, VALUE> toFunction(FuncUnit1<KEY> action, Func1<KEY, VALUE> elseProvider) {
        return key -> {
            try {
                val value = this.get(key);
                if (value == null)
                    action.accept(key);
                
                if (value == null)
                    action.accept(key);
                
                return (value == null) ? elseProvider.apply(key) : value;
            } catch (Exception e) {
                return elseProvider.apply(key);
            }
        };
    }
    
    public ImmutableMap<KEY, VALUE> freeze() {
        return toImmutableMap();
    }
    
    public abstract FuncMap<KEY, VALUE> sorted();
    
    public abstract FuncMap<KEY, VALUE> sorted(Comparator<? super Map.Entry<KEY, VALUE>> comparator);
    
    public abstract FuncMap<KEY, VALUE> sortedByKey(Comparator<? super KEY> comparator);
    
    public abstract FuncMap<KEY, VALUE> sortedByValue(Comparator<? super VALUE> comparator);
    
    public abstract void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
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
