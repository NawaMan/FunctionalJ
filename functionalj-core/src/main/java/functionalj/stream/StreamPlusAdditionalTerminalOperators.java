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
package functionalj.stream;

import static functionalj.tuple.Tuple.tuple2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import functionalj.function.Func1;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusAdditionalTerminalOperators<DATA> extends AsStreamPlus<DATA> {
    
    //-- Functionalities --
    
    public default void forEachWithIndex(BiConsumer<? super Integer, ? super DATA> action) {
        val index = new AtomicInteger();
        streamPlus()
        .forEach(each -> {
            val currentIndex = index.getAndIncrement();
            action.accept(currentIndex, each);
        });
    }
    
    //-- groupingBy --
    
    // Eager
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper) {
        Supplier  <Map<KEY, ArrayList<DATA>>>                            supplier;
        BiConsumer<Map<KEY, ArrayList<DATA>>, ? super DATA>              accumulator;
        BiConsumer<Map<KEY, ArrayList<DATA>>, Map<KEY, ArrayList<DATA>>> combiner;
        
        Supplier<ArrayList<DATA>>                   collectorSupplier = ArrayList::new;
        Function<ArrayList<DATA>, StreamPlus<DATA>> toStreamPlus      = array -> StreamPlus.from(array.stream());
        
        supplier = LinkedHashMap::new;
        accumulator = (map, each) -> {
            val key = keyMapper.apply(each);
            map.compute(key, (k, a)->{
                if (a == null) {
                    a = collectorSupplier.get();
                }
                a.add(each);
                return a;
            });
        };
        combiner = (map1, map2) -> map1.putAll(map2);
        
        val theMap = streamPlus().collect(supplier, accumulator, combiner);
        return ImmutableMap
                .from    (theMap)
                .mapValue(toStreamPlus);
    }
    
    // Eager
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper,
            Function<StreamPlus<DATA>, VALUE>     aggregate) {
        val groupingBy = groupingBy(keyMapper);
        val mapValue = groupingBy.mapValue(aggregate);
        return (FuncMap<KEY, VALUE>)mapValue;
    }
    
    // Eager
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper,
            StreamProcessor<? super DATA, VALUE>  processor) {
        Function<StreamPlus<DATA>, VALUE> aggregate = (StreamPlus<DATA> stream) -> {
            return (VALUE)processor.process((StreamPlus)stream);
        };
        return groupingBy(keyMapper, aggregate);
    }
    
    //-- min-max --
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(
            Func1<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .min((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return mappedA.compareTo(mappedB);
                });
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(
            Func1<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .max((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return mappedA.compareTo(mappedB);
                });
    }
    
    public default <D> Optional<DATA> minBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .min((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return comparator.compare(mappedA, mappedB);
                });
    }
    
    public default <D> Optional<DATA> maxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .max((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return comparator.compare(mappedA, mappedB);
                });
    }
    
    @SuppressWarnings("unchecked")
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(
            Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
            .sorted(comparator)
            .forEach(each -> {
                minRef.compareAndSet(StreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    @SuppressWarnings("unchecked")
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D> mapper) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    @SuppressWarnings("unchecked")
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
        .forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    //-- Find --
    
    public default <T> Optional<DATA> findFirst(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return streamPlus()
                .filter(mapper, theCondition)
                .findFirst();
    }
    
    public default <T>  Optional<DATA> findAny(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return streamPlus()
                .filter(mapper, theCondition)
                .findAny();
    }
    
    public default Optional<DATA> findFirst(
            Predicate<? super DATA> predicate) {
        return streamPlus()
                .filter(predicate)
                .findFirst();
    }
    
    public default Optional<DATA> findAny(
            Predicate<? super DATA> predicate) {
        return streamPlus()
                .filter(predicate)
                .findAny();
    }
    
    //-- toMap --
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper) {
        val theMap 
                = streamPlus()
                .collect(Collectors.toMap(keyMapper, data -> data));
        return (FuncMap<KEY, DATA>)ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>  keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper) {
        val theMap 
                = streamPlus()
                .collect(Collectors.toMap(keyMapper, valueMapper));
        return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>   keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper,
            BinaryOperator<VALUE>                   mergeFunction) {
        val theMap 
                = streamPlus()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper,
            BinaryOperator<DATA>                  mergeFunction) {
        val theMap 
                = streamPlus()
                .collect(Collectors.toMap(keyMapper, value -> value, mergeFunction));
        return (FuncMap<KEY, DATA>) ImmutableMap.from(theMap);
    }
}
