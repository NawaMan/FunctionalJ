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
package functionalj.stream.intstream;

import static functionalj.tuple.IntIntTuple.intTuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import functionalj.function.FuncUnit1;
import functionalj.function.IntBiConsumer;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.GrowOnlyIntArray;
import functionalj.stream.IntStreamProcessor;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;
import lombok.val;

public interface IntStreamPlusAdditionalTerminalOperators {
    
    public IntStreamPlus filter(IntPredicate predicate);
    
    public IntStreamPlus filter(IntUnaryOperator mapper, IntPredicate theCondition);
    
    public <T> IntStreamPlus filterAsObject(IntFunction<? extends T> mapper, Predicate<? super T> theCondition);
    
    public <T> T terminate(Function<IntStream, T> action);
    
    public void terminate(FuncUnit1<IntStream> action);
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(IntBiConsumer action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            val index = new AtomicInteger();
            stream.forEach(each -> action.acceptAsIntInt(index.getAndIncrement(), each));
        });
    }
    
    //-- groupingBy --
    
    // Eager
    public default <KEY> FuncMap<KEY, IntStreamPlus> groupingBy(
            IntFunction<? extends KEY> keyMapper) {
        Supplier      <Map<KEY, GrowOnlyIntArray>>                             supplier;
        ObjIntConsumer<Map<KEY, GrowOnlyIntArray>>                             accumulator;
        BiConsumer    <Map<KEY, GrowOnlyIntArray>, Map<KEY, GrowOnlyIntArray>> combiner;
        
        Supplier<GrowOnlyIntArray>                collectorSupplier = GrowOnlyIntArray::new;
        Function<GrowOnlyIntArray, IntStreamPlus> toStreamPlus      = GrowOnlyIntArray::stream;
        
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
        return terminate(stream -> {
            val theMap = stream.collect(supplier, accumulator, combiner);
            return ImmutableMap
                    .from    (theMap)
                    .mapValue(toStreamPlus);
        });
    }
    
    // Eager
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<? extends KEY>     keyMapper,
            Function<IntStreamPlus, VALUE> aggregate) {
        val groupingBy = groupingBy(keyMapper);
        val mapValue = groupingBy.mapValue(aggregate);
        return (FuncMap<KEY, VALUE>)mapValue;
    }
    
    // Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<? extends KEY> keyMapper,
            IntStreamProcessor<VALUE>  processor) {
        Function<IntStreamPlus, VALUE> aggregate = (IntStreamPlus stream) -> {
            return (VALUE)processor.process((IntStreamPlus)stream);
        };
        return groupingBy(keyMapper, aggregate);
    }
    
    //-- min-max --
    
    public default <D extends Comparable<D>> OptionalInt minBy(
            IntFunction<D> mapper) {
        return terminate((IntStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> IntTuple2.of(i, mapper.apply(i)))
                    .min     ((a, b) -> a._2.compareTo(b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default <D extends Comparable<D>> OptionalInt maxBy(
            IntFunction<D> mapper) {
        return terminate((IntStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> IntTuple2.of(i, mapper.apply(i)))
                    .max     ((a, b) -> a._2.compareTo(b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default <D> OptionalInt minBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate((IntStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> IntTuple2.of(i, mapper.apply(i)))
                    .min     ((a, b) -> comparator.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default <D> OptionalInt maxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate((IntStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> IntTuple2.of(i, mapper.apply(i)))
                    .max     ((a, b) -> comparator.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default <D> OptionalInt minOf(
            IntUnaryOperator mapper) {
        return terminate((IntStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> IntIntTuple.of(i, mapper.applyAsInt(i)))
                    .min     ((a, b) -> Integer.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default <D> OptionalInt maxOf(
            IntUnaryOperator mapper) {
        return terminate((IntStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> IntIntTuple.of(i, mapper.applyAsInt(i)))
                    .max     ((a, b) -> Integer.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalInt.of((int)result.get()) 
                    : OptionalInt.empty();
        });
    }
    
    public default Optional<IntIntTuple> minMax() {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            stream
                .sorted  ()
                .forEach (each -> {
                    minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (IntStreamPlusHelper.dummy.equals(min)
             || IntStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            val intTuple = intTuple((int)min, (int)max);
            return Optional.of(intTuple);
        });
    }
    
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxOf(
            IntUnaryOperator mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            IntStreamPlus.from(stream)
                .mapToObj(i    -> intTuple(i, mapper.applyAsInt(i)))
                .sortedBy(t    -> t._2)
                .forEach (each -> {
                    minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (IntStreamPlusHelper.dummy.equals(min)
             || IntStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            val intTuple = intTuple(((IntIntTuple)min)._1, ((IntIntTuple)max)._1);
            return Optional.of(intTuple);
        });
    }
    
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxBy(
            IntFunction<D> mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            IntStreamPlus.from(stream)
                .mapToObj(i    -> IntTuple2.of(i, mapper.apply(i)))
                .sortedBy(t    -> t._2)
                .forEach (each -> {
                    minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (IntStreamPlusHelper.dummy.equals(min)
             || IntStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            @SuppressWarnings("unchecked")
            val intTuple = intTuple(((IntTuple2<D>)min)._1, ((IntTuple2<D>)max)._1);
            return Optional.of(intTuple);
        });
    }
    
    public default <D> Optional<IntIntTuple> minMaxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
            IntStreamPlus.from(stream)
                .mapToObj(i    -> IntTuple2.of(i, mapper.apply(i)))
                .sortedBy(t    -> t._2, comparator)
                .forEach (each -> {
                    minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (IntStreamPlusHelper.dummy.equals(min)
             || IntStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            @SuppressWarnings("unchecked")
            val intTuple = intTuple(((IntTuple2<D>)min)._1, ((IntTuple2<D>)max)._1);
            return Optional.of(intTuple);
        });
    }
    
    //-- Find --
    
    public default OptionalInt findFirst(
            IntPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findFirst();
        });
    }
    
    public default OptionalInt findAny(
            IntPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findAny();
        });
    }
    
    public default OptionalInt findFirst(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        return filter(mapper, theCondition)
                .findFirst();
    }
    
    public default <T> OptionalInt findAny(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        return filter(mapper, theCondition)
                .findAny();
    }
    
    public default <T> OptionalInt findFirstBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>     theCondition) {
        return filterAsObject(mapper, theCondition)
                .findFirst();
    }
    
    public default <T> OptionalInt findAnyBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        return filterAsObject(mapper, theCondition)
                .findAny();
    }
    
    //-- toMap --
    
    public default <KEY> FuncMap<KEY, Integer> toMap(
            IntFunction<? extends KEY> keyMapper) {
        return toMap(keyMapper, i -> i);
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            IntFunction<? extends KEY>   keyMapper,
            IntFunction<? extends VALUE> valueMapper) {
        Supplier<Map<KEY, VALUE>> supplier = ()-> new LinkedHashMap<KEY, VALUE>();
        ObjIntConsumer<Map<KEY, VALUE>> accumulator = (map, i) -> {
            val key = keyMapper.apply(i);
            map.compute(key, (k, a)->{
                if (a != null)
                    throw new IllegalStateException("Duplicate key for value=" + i + ": " + key);
                return valueMapper.apply(i);
            });
        };
        BiConsumer<Map<KEY, VALUE>, Map<KEY, VALUE>>  combiner = (map1, map2) -> {
            val mapKeys = new ArrayList<>(map1.keySet());
            mapKeys.retainAll(map2.keySet());
            if (mapKeys.isEmpty()) {
                map1.putAll(map2);
            } else {
                val firstDuplicateKey = mapKeys.get(0);
                val i = map1.get(firstDuplicateKey);
                throw new IllegalStateException("Duplicate key for value=" + i + ": " + firstDuplicateKey);
            }
        };
        return (FuncMap<KEY, VALUE>) terminate(stream -> {
            val theMap = stream.collect(supplier, accumulator, combiner);
            return ImmutableMap
                    .from(theMap);
        });
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY> FuncMap<KEY, Integer> toMap(
            IntFunction<? extends KEY> keyMapper,
            IntBinaryOperator          mergeFunction) {
        return (FuncMap)
                groupingBy(keyMapper)
                .mapValue(stream -> stream.reduce(mergeFunction));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            IntFunction<KEY>      keyMapper,
            IntFunction<VALUE>    valueMapper,
            BinaryOperator<VALUE> mergeFunction) {
        return (FuncMap)groupingBy(keyMapper)
                .mapValue(stream -> {
                    return stream
                            .mapToObj(valueMapper)
                            .reduce((a, b) -> mergeFunction.apply(a, b));
                });
    }
}
