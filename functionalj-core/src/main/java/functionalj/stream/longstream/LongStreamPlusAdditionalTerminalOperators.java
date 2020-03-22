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
package functionalj.stream.longstream;

import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;

import functionalj.function.FuncUnit1;
import functionalj.function.LongBiConsumer;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.LongTuple2;
import lombok.val;

public interface LongStreamPlusAdditionalTerminalOperators {
    
    public LongStreamPlus filter(LongPredicate predicate);
    
    public LongStreamPlus filter(LongUnaryOperator mapper, LongPredicate theCondition);
    
    public <T> LongStreamPlus filterAsObject(LongFunction<? extends T> mapper, Predicate<? super T> theCondition);
    
    public <T> T terminate(Function<LongStream, T> action);
    
    public void terminate(FuncUnit1<LongStream> action);
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(
            LongBiConsumer action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            val index = new AtomicInteger();
            stream
            .forEach(each -> action.acceptAsLongLong(index.getAndIncrement(), each));
        });
    }
    
    //-- groupingBy --
    
//    // Eager
//    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(
//            Function<? super DATA, ? extends KEY> classifier) {
//        return terminate(stream -> {
//            val theMap = new HashMap<KEY, FuncList<DATA>>();
//            stream
//                .collect(Collectors.groupingBy(classifier))
//                .forEach((key,list)->theMap.put(key, ImmutableList.from(list)));
//            return ImmutableMap.from(theMap);
//        });
//    }
//    
//    // Eager
//    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
//            Function<? super DATA, ? extends KEY>   classifier,
//            Function<? super FuncList<DATA>, VALUE> aggregate) {
//        return terminate(stream -> {
//            val theMap = new HashMap<KEY, VALUE>();
//            stream
//                .collect(Collectors.groupingBy(classifier))
//                .forEach((key,list) -> {
//                    val valueList      = ImmutableList.from(list);
//                    val aggregateValue = aggregate.apply(valueList);
//                    theMap.put(key, aggregateValue);
//                });
//            return ImmutableMap.from(theMap);
//        });
//    }
//    
//    // Eager
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
//            Function<? super DATA, ? extends KEY> classifier,
//            StreamProcessor<? super DATA, VALUE>  processor) {
//        Function<? super FuncList<DATA>, VALUE> aggregate = (FuncList<DATA> list) -> {
//            return (VALUE)processor.process((StreamPlus)list.stream());
//        };
//        return groupingBy(classifier, aggregate);
//    }
//    
//    // Eager
//    public default <KEY, ACCUMULATED, TARGET> FuncMap<KEY, TARGET> groupingBy(
//            Function<? super DATA, ? extends KEY>                  classifier,
//            Supplier<Collector<? super DATA, ACCUMULATED, TARGET>> collectorSupplier) {
//        Objects.requireNonNull(collectorSupplier);
//        
//        val theMap = new ConcurrentHashMap<KEY, Collected<? super DATA, ACCUMULATED, TARGET>>();
//        stream()
//        .forEach(each -> {
//            val key       = classifier.apply(each);
//            val collected = theMap.computeIfAbsent(key, __ -> {
//                val collector = collectorSupplier.get();
//                return new Collected.ByCollector<>(collector);
//            });
//            
//            collected.accumulate(each);
//        });
//        
//        val mapBuilder = FuncMap.<KEY, TARGET>newBuilder();
//        theMap
//        .forEach((key, collected) -> {
//            val target = collected.finish();
//            mapBuilder.with(key, target);
//        });
//        
//        val map = mapBuilder.build();
//        return map;
//    }
    
    //-- min-max --
    
    public default <D extends Comparable<D>> OptionalLong minBy(
            LongFunction<D> mapper) {
        return terminate((LongStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> LongTuple2.of(i, mapper.apply(i)))
                    .min     ((a, b) -> a._2.compareTo(b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default <D extends Comparable<D>> OptionalLong maxBy(
            LongFunction<D> mapper) {
        return terminate((LongStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> LongTuple2.of(i, mapper.apply(i)))
                    .max     ((a, b) -> a._2.compareTo(b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default <D> OptionalLong minBy(
            LongFunction<D>       mapper, 
            Comparator<? super D> comparator) {
        return terminate((LongStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> LongTuple2.of(i, mapper.apply(i)))
                    .min     ((a, b) -> comparator.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default <D> OptionalLong maxBy(
            LongFunction<D>       mapper, 
            Comparator<? super D> comparator) {
        return terminate((LongStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> LongTuple2.of(i, mapper.apply(i)))
                    .max     ((a, b) -> comparator.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default <D> OptionalLong minOf(
            LongUnaryOperator mapper) {
        return terminate((LongStream stream) -> {
            val result 
                    = stream
                    .mapToObj(i      -> LongLongTuple.of(i, mapper.applyAsLong(i)))
                    .min     ((a, b) -> Long.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default <D> OptionalLong maxOf(
            LongUnaryOperator mapper) {
        return terminate((LongStream stream) -> {
            Optional<Object> result 
                    = stream
                    .mapToObj(i      -> LongLongTuple.of(i, mapper.applyAsLong(i)))
                    .max     ((a, b) -> Long.compare(a._2, b._2))
                    .map     (t      -> t._1);
            return result.isPresent() 
                    ? OptionalLong.of((long)result.get()) 
                    : OptionalLong.empty();
        });
    }
    
    public default Optional<LongLongTuple> minMax() {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            stream
                .sorted  ()
                .forEach (each -> {
                    minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (LongStreamPlusHelper.dummy.equals(min)
             || LongStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            val longTuple = LongLongTuple.of((long)min, (long)max);
            return Optional.of(longTuple);
        });
    }
    
    public default <D extends Comparable<D>> Optional<LongLongTuple> minMaxOf(
            LongUnaryOperator mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            LongStreamPlus.from(stream)
                .mapToObj(i    -> LongLongTuple.of(i, mapper.applyAsLong(i)))
                .sortedBy(t    -> t._2)
                .forEach (each -> {
                    minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (LongStreamPlusHelper.dummy.equals(min)
             || LongStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            val longTuple = LongLongTuple.of(((LongLongTuple)min)._1, ((LongLongTuple)max)._1);
            return Optional.of(longTuple);
        });
    }
    
    public default <D extends Comparable<D>> Optional<LongLongTuple> minMaxBy(
            LongFunction<D> mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            LongStreamPlus.from(stream)
                .mapToObj(i    -> LongTuple2.of(i, mapper.apply(i)))
                .sortedBy(t    -> t._2)
                .forEach (each -> {
                    minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (LongStreamPlusHelper.dummy.equals(min)
             || LongStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            @SuppressWarnings("unchecked")
            val tuple = LongLongTuple.of(((LongTuple2<D>)min)._1, ((LongTuple2<D>)max)._1);
            return Optional.of(tuple);
        });
    }
    
    public default <D> Optional<LongLongTuple> minMaxBy(
            LongFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
            LongStreamPlus.from(stream)
                .mapToObj(i    -> LongTuple2.of(i, mapper.apply(i)))
                .sortedBy(t    -> t._2, comparator)
                .forEach (each -> {
                    minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            
            if (LongStreamPlusHelper.dummy.equals(min)
             || LongStreamPlusHelper.dummy.equals(max))
                return Optional.empty();
            
            @SuppressWarnings("unchecked")
            val tuple = LongLongTuple.of(((LongTuple2<D>)min)._1, ((LongTuple2<D>)max)._1);
            return Optional.of(tuple);
        });
    }
    
    //-- Find --
    
    public default OptionalLong findFirst(
            LongPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findFirst();
        });
    }
    
    public default OptionalLong findAny(
            LongPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findAny();
        });
    }
    
    public default OptionalLong findFirst(
            LongUnaryOperator mapper, 
            LongPredicate     theCondition) {
        return filter(mapper, theCondition)
                .findFirst();
    }
    
    public default <T> OptionalLong findAny(
            LongUnaryOperator mapper, 
            LongPredicate     theCondition) {
        return filter(mapper, theCondition)
                .findAny();
    }
    
    public default <T> OptionalLong findFirstBy(
            LongFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        return filterAsObject(mapper, theCondition)
                .findFirst();
    }
    
    public default <T> OptionalLong findAnyBy(
            LongFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        return filterAsObject(mapper, theCondition)
                .findAny();
    }
    
//    //-- toMap --
//    
//    @SuppressWarnings("unchecked")
//    public default <KEY> FuncMap<KEY, DATA> toMap(
//            Function<? super DATA, ? extends KEY> keyMapper) {
//        return terminate(stream -> {
//            val theMap = stream.collect(Collectors.toMap(keyMapper, data -> data));
//            return (FuncMap<KEY, DATA>)ImmutableMap.from(theMap);
//        });
//    }
//    
//    @SuppressWarnings("unchecked")
//    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
//            Function<? super DATA, ? extends KEY>  keyMapper,
//            Function<? super DATA, ? extends VALUE> valueMapper) {
//        return terminate(stream -> {
//            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper));
//            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
//        });
//    }
//    
//    @SuppressWarnings("unchecked")
//    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
//            Function<? super DATA, ? extends KEY>   keyMapper,
//            Function<? super DATA, ? extends VALUE> valueMapper,
//            BinaryOperator<VALUE>                   mergeFunction) {
//        return terminate(stream -> {
//            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
//            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
//        });
//    }
//    
//    @SuppressWarnings("unchecked")
//    public default <KEY> FuncMap<KEY, DATA> toMap(
//            Function<? super DATA, ? extends KEY> keyMapper,
//            BinaryOperator<DATA>                  mergeFunction) {
//        return terminate(stream -> {
//            val theMap = stream.collect(Collectors.toMap(keyMapper, value -> value, mergeFunction));
//            return (FuncMap<KEY, DATA>) ImmutableMap.from(theMap);
//        });
//    }
}
