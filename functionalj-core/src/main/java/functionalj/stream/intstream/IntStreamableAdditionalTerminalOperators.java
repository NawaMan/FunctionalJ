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

import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

import functionalj.function.IntBiConsumer;
import functionalj.tuple.IntIntTuple;

public interface IntStreamableAdditionalTerminalOperators {
    
    public IntStreamPlus intStream();
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(
            IntBiConsumer action) {
        intStream()
            .forEachWithIndex(action);
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
    
    public default <D extends Comparable<D>> OptionalInt minBy(
            IntFunction<D> mapper) {
        return intStream()
                .minBy(mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxBy(
            IntFunction<D> mapper) {
        return intStream()
                .maxBy(mapper);
    }
    
    public default <D> OptionalInt minBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return intStream()
                .minBy(mapper, comparator);
    }
    
    public default <D> OptionalInt maxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return intStream()
                .maxBy(mapper, comparator);
    }
    
    public default <D> OptionalInt minOf(
            IntUnaryOperator mapper) {
        return intStream()
                .minOf(mapper);
    }
    
    public default <D> OptionalInt maxOf(
            IntUnaryOperator mapper) {
        return intStream()
                .maxOf(mapper);
    }
    
    public default Optional<IntIntTuple> minMax() {
        return intStream()
                .minMax();
    }
    
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxOf(
            IntUnaryOperator mapper) {
        return intStream()
                .minMaxOf(mapper);
    }
    
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxBy(
            IntFunction<D> mapper) {
        return intStream()
                .minMaxBy(mapper);
    }
    
    public default <D> Optional<IntIntTuple> minMaxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        return intStream()
                .minMaxBy(mapper, comparator);
    }
    
    //-- Find --
    
    public default OptionalInt findFirst(
            IntPredicate predicate) {
        return intStream()
                .findFirst(predicate);
    }
    
    public default OptionalInt findAny(
            IntPredicate predicate) {
        return intStream()
                .findAny(predicate);
    }
    
    public default OptionalInt findFirst(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        return intStream()
                .findFirst(mapper, theCondition);
    }
    
    public default <T> OptionalInt findAny(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        return intStream()
                .findAny(mapper, theCondition);
    }
    
    public default <T> OptionalInt findFirstBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>     theCondition) {
        return intStream()
                .findFirstBy(mapper, theCondition);
    }
    
    public default <T> OptionalInt findAnyBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>     theCondition) {
        return intStream()
                .findAnyBy(mapper, theCondition);
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
