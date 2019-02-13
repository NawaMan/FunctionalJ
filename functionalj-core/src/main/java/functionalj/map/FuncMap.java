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

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func2;
import functionalj.list.FuncList;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.IntTuple2;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class FuncMap<KEY, VALUE>
                    implements
                        ReadOnlyMap<KEY, VALUE>, 
                        IFuncMap<KEY, VALUE, FuncMap<KEY, VALUE>> {
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> empty() {
        return new ImmutableMap<KEY, VALUE>(Stream.empty());
    }
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Map<? extends KEY, ? extends VALUE> map) {
        return new ImmutableMap<KEY, VALUE>(map.entrySet().stream());
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        return new ImmutableMap<KEY, VALUE>(stream);
    }
    
    @SafeVarargs
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> ofEntries(ImmutableTuple2<KEY, VALUE> ... entries) {
        return new ImmutableMap<KEY, VALUE>(Stream.of(entries));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0) {
        return ofEntries(new ImmutableTuple2<KEY, VALUE>(key0, value0));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9,
            KEY key10, VALUE value10) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9),
                new ImmutableTuple2<KEY, VALUE>(key10, value10));
    }
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0) {
        return ofEntries(new ImmutableTuple2<KEY, VALUE>(key0, value0));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> mapOf(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9,
            KEY key10, VALUE value10) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9),
                new ImmutableTuple2<KEY, VALUE>(key10, value10));
    }
    
    <K, V> FuncMapStream<K, V> derivedWith(Boolean isKeyComparable, FuncList<IntTuple2<ImmutableTuple2<K, V>>> entries) {
        val lazyMap = new FuncMapStream<K, V>(isKeyComparable, entries);
        return isLazy()
                ? lazyMap
                : new ImmutableMap<K, V>(lazyMap.entries(), true);
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
    public abstract FuncList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultTo(KEY key, VALUE value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FuncMap<KEY, VALUE> exclude(KEY key);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck);

    @Override
    public abstract FuncList<KEY> keys();
    
    @Override
    public abstract FuncList<VALUE> values();
    
    @Override
    public abstract Set<Entry<KEY, VALUE>> entrySet();
    
    @Override
    public abstract FuncList<ImmutableTuple2<KEY, VALUE>> entries();
    
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
                .map(each -> each._1 + ":" + each._2)
                .collect(Collectors.joining(", ")) +
                "}";
    }
    
    
}
