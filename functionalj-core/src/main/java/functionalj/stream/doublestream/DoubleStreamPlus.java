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

package functionalj.stream.doublestream;

import static functionalj.function.Func.f;
import static functionalj.function.Func.itself;
import static functionalj.lens.Access.theDouble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.FuncUnit1;
import functionalj.function.ToIntBiDoubleFunctionPrimitive;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.DoubleIteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.DoubleTuple2;
import lombok.val;

@FunctionalInterface
public interface DoubleStreamPlus
        extends 
            DoubleStream/*,
            DoubleStreamPlusWithMapFirst,
            DoubleStreamPlusWithMapThen,
            DoubleStreamPlusWithMapTuple,
            DoubleStreamPlusWithMapToMap,
            DoubleStreamPlusWithSplit,
            DoubleStreamPlusWithSegment,
            DoubleStreamPlusWithCombine,
            DoubleStreamPlusWithCalculate,
            DoubleStreamPlusAddtionalOperators,
            DoubleStreamPlusAdditionalTerminalOperators */{
    
    //== Constructor ==
    
    public static DoubleStreamPlus empty() {
        return DoubleStreamPlus.from(DoubleStream.empty());
    }
    
    public static DoubleStreamPlus emptyDoubleStream() {
        return DoubleStreamPlus.from(DoubleStream.empty());
    }
    
    public static DoubleStreamPlus of(double ... doubles) {
        if ((doubles == null) || (doubles.length == 0))
            return DoubleStreamPlus.empty();
        
        return DoubleStreamPlus.from(DoubleStream.of(Arrays.copyOf(doubles, doubles.length)));
    }
    
    public static DoubleStreamPlus doubles(double ... doubles) {
        return DoubleStreamPlus.of(doubles);
    }
    
    // TODO - from-to, from almostTo, stepping.
    
    public static DoubleStreamPlus from(DoubleStream doubleStream) {
        if (doubleStream instanceof DoubleStreamPlus)
            return (DoubleStreamPlus)doubleStream;
            
        return ()->doubleStream;
    }
    
    public static DoubleStreamPlus zeroes() {
        return DoubleStreamPlus.generate(()->0.0);
    }
    
    public static DoubleStreamPlus zeroes(int count) {
        return DoubleStreamPlus.generate(()->0.0).limit(count);
    }
    
    public static DoubleStreamPlus ones() {
        return DoubleStreamPlus.generate(()->1);
    }
    
    public static DoubleStreamPlus ones(int count) {
        return DoubleStreamPlus.generate(()->1).limit(count);
    }
    
    public static DoubleStreamPlus repeat(double ... data) {
        return cycle(data);
    }
    
    public static DoubleStreamPlus cycle(double ... data) {
        val ints = Arrays.copyOf(data, data.length);
        val size = ints.length;
        return DoubleStreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToDouble(i -> data[i % size]));
    }
    
    public static DoubleStreamPlus stepBy(double step) {
        return DoubleStreamPlus
                .stepBy(1.0)
                .mapToDouble(i -> i*step);
    }
    
    public static DoubleStreamPlus stepBy(double step, int time) {
        return DoubleStreamPlus
                .stepBy(step)
                .limit(time);
    }
    
    public static DoubleStreamPlus concat(DoubleStreamPlus ... streams) {
        return StreamPlus.of(streams).flatMapToDouble(s -> s.doubleStream()).mapToDouble(i -> i);
    }
    
    public static DoubleStreamPlus generate(DoubleSupplier s) {
        return DoubleStreamPlus.from(DoubleStream.generate(s));
    }
    
    public static DoubleStreamPlus compound(double seed, DoubleUnaryOperator f) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
    }
    
    public static DoubleStreamPlus iterate(double seed, DoubleUnaryOperator f) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
    }
    
    public static DoubleStreamPlus compound(double seed1, double seed2, DoubleBinaryOperator f) {
        return DoubleStreamPlus.from(DoubleStreamPlus.iterate(seed1, seed2, f));
    }
    
    public static DoubleStreamPlus iterate(double seed1, double seed2, DoubleBinaryOperator f) {
        val counter = new AtomicInteger(0);
        val double1 = new AtomicReference<Double>(seed1);
        val double2 = new AtomicReference<Double>(seed2);
        return DoubleStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            double d2 = double2.get();
            double d1 = double1.getAndSet(d2);
            double d  = f.applyAsDouble(d1, d2);
            double2.set(d);
            return d;
        });
    }
//    
//    public static StreamPlus<DoubleDoubleTuple> zipOf(
//            DoubleStream stream1, 
//            DoubleStream stream2) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2);
//    }
//    public static StreamPlus<DoubleDoubleTuple> zipOf(
//            DoubleStream stream1, 
//            DoubleStream stream2,
//            int       defaultValue) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2, defaultValue);
//    }
//    public static StreamPlus<DoubleDoubleTuple> zipOf(
//            DoubleStream stream1, double defaultValue1,
//            DoubleStream stream2, double defaultValue2) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2, defaultValue1, defaultValue2);
//    }
//    
//    public static DoubleStreamPlus zipOf(
//            DoubleStream              stream1, 
//            DoubleStream              stream2,
//            DoubleBiFunctionPrimitive merger) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2, merger);
//    }
//    public static DoubleStreamPlus zipOf(
//            DoubleStream              stream1, 
//            DoubleStream              stream2,
//            double                    defaultValue,
//            DoubleBiFunctionPrimitive merger) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2, defaultValue, merger);
//    }
//    public static DoubleStreamPlus zipOf(
//            DoubleStreamPlus stream1, int defaultValue1,
//            DoubleStreamPlus stream2, int defaultValue2,
//            DoubleBiFunctionPrimitive merger) {
//        return DoubleStreamPlus.from(stream1)
//                .zipWith(stream2, defaultValue1, defaultValue2, merger);
//    }
    
    //== Core ==
    
    public DoubleStream doubleStream();
    
    public default DoubleStreamPlus doubleStreamPlus() {
        return this;
    }
    
    @Override
    public default StreamPlus<Double> boxed() {
        return StreamPlus.from(doubleStream().boxed());
    }
    
    //== Helper functions ==
    
    public default <TARGET> TARGET terminate(Function<DoubleStream, TARGET> action) {
        val stream = doubleStream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<DoubleStream> action) {
        val stream = doubleStream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    public default DoubleStreamPlus sequential(Function<DoubleStreamPlus, DoubleStreamPlus> action) {
        val stream = doubleStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            val orgDoubleStreamPlus = DoubleStreamPlus.from(stream);
            val newDoubleStreamPlus = action.apply(orgDoubleStreamPlus);
            return newDoubleStreamPlus;
        }
        
        val orgDoubleStreamPlus = DoubleStreamPlus.from(stream.sequential());
        val newDoubleStreamPlus = action.apply(orgDoubleStreamPlus);
        if (newDoubleStreamPlus.isParallel())
            return newDoubleStreamPlus;
        
        return (DoubleStreamPlus)()->newDoubleStreamPlus.parallel();
    }
    
    public default <TARGET> StreamPlus<TARGET> sequentialToObj(Function<DoubleStreamPlus, StreamPlus<TARGET>> action) {
        val stream = doubleStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            return action.apply(DoubleStreamPlus.from(stream));
        }
        
        val resultStream = action.apply(DoubleStreamPlus.from(stream.sequential()));
        if (resultStream.isParallel())
            return resultStream;
        
        return resultStream.parallel();
    }
    
    public default DoubleStreamPlus derive(
            Function<DoubleStream, DoubleStream> action) {
        return DoubleStreamPlus
                .from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToStream(
            Function<DoubleStream, Stream<TARGET>> action) {
        return StreamPlus
                .from(action.apply(this));
    }
    
    //== Stream specific ==
    
    @Override
    public default DoubleStream sequential() {
        return DoubleStreamPlus.from(doubleStream().sequential());
    }
    
    @Override
    public default DoubleStream parallel() {
        return DoubleStreamPlus.from(doubleStream().sequential());
    }
    
    @Override
    public default DoubleStream unordered() {
        return DoubleStreamPlus.from(doubleStream().unordered());
    }
    
    public default boolean isParallel() {
        return doubleStream()
                .isParallel();
    }
    
    // TODO - Think about terminate
    @Override
    public default PrimitiveIterator.OfDouble iterator() {
        return doubleStream()
                .iterator();
    }
    
    @Override
    public default Spliterator.OfDouble spliterator() {
        return doubleStream()
                .spliterator();
    }
    
    @Override
    public default DoubleStream onClose(Runnable closeHandler) {
        return DoubleStreamPlus
                .from(doubleStream()
                .onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        doubleStream()
        .close();
    }
    
    //== Functionalities ==
    
    public default IntStreamPlus mapToInt(DoubleToIntFunction mapper) {
        return IntStreamPlus.from(doubleStream().mapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus mapToLong(DoubleToLongFunction mapper) {
        return LongStreamPlus.from(doubleStream().mapToLong(mapper));
    }
    
    public default DoubleStreamPlus mapToDouble(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(DoubleFunction<? extends T> mapper) {
        StreamPlus<T> stream = StreamPlus.from(doubleStream().mapToObj(mapper));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default DoubleStreamPlus map(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    @Override
    public default DoubleStreamPlus flatMap(
            DoubleFunction<? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(doubleStream().flatMap(mapper));
    }
    
    public default <T> StreamPlus<T> flatMapToObj(
            DoubleFunction<? extends Stream<T>> mapper) {
        return StreamPlus.from(
                doubleStream()
                .mapToObj(mapper)
                .flatMap(stream -> stream));
    }
    
    public default IntStreamPlus flatMapToInt(
            DoubleFunction<? extends IntStream> mapper) {
        return mapToObj(mapper).flatMapToInt(itself());
    }
    
    public default LongStreamPlus flatMapToLong(
            DoubleFunction<? extends LongStream> mapper) {
        return mapToObj(mapper).flatMapToLong(itself());
    }
    
    public default DoubleStreamPlus flatMapToDouble(
            DoubleFunction<? extends DoubleStream> mapper) {
        return mapToObj(mapper).flatMapToDouble(itself());
    }
    
    @Override
    public default DoubleStreamPlus filter(
            DoublePredicate predicate) {
        return DoubleStreamPlus.from(doubleStream().filter(predicate));
    }
    
    // TODO - Rename all filter with map to filter
    public default DoubleStreamPlus filter(
            DoubleUnaryOperator mapper, 
            DoublePredicate     predicate) {
        return DoubleStreamPlus.from(doubleStream().filter(i -> {
            val v = mapper.applyAsDouble(i);
            val b = predicate.test(v);
            return b;
        }));
    }
    
    public default <T> DoubleStreamPlus filterAsObject(
            DoubleFunction<? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> DoubleStreamPlus filterAsObject(
            Function<Double, ? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    @Override
    public default DoubleStreamPlus peek(DoubleConsumer action) {
        return DoubleStreamPlus.from(doubleStream().peek(action));
    }
    
    //-- Limit/Skip --
    
    @Override
    public default DoubleStreamPlus limit(long maxSize) {
        return DoubleStreamPlus.from(doubleStream().limit(maxSize));
    }
    
    @Override
    public default DoubleStreamPlus skip(long offset) {
        return DoubleStreamPlus.from(doubleStream().skip(offset));
    }
    
    public default DoubleStreamPlus skipWhile(DoublePredicate condition) {
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream
                    .filter(e -> {
                        if (!isStillTrue.get())
                            return true;
                        
                        if (!condition.test(e))
                            isStillTrue.set(false);
                        
                        return !isStillTrue.get();
                    });
        });
    }
    
    public default DoubleStreamPlus skipUntil(DoublePredicate condition) {
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream
                    .filter(e -> {
                        if (!isStillTrue.get())
                            return true;
                        
                        if (condition.test(e))
                            isStillTrue.set(false);
                        
                        return !isStillTrue.get();
                    });
        });
    }
    
    public default DoubleStreamPlus takeWhile(DoublePredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        return sequential(stream -> {
            val splitr = stream.spliterator();
            return DoubleStreamPlus.from(
                    StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(DoubleConsumer consumer) {
                            if (stillGoing) {
                                final boolean hadNext = splitr.tryAdvance((double elem) -> {
                                    if (condition.test(elem)) {
                                        consumer.accept(elem);
                                    } else {
                                        stillGoing = false;
                                    }
                                });
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    public default DoubleStreamPlus takeUntil(DoublePredicate condition) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(DoubleConsumer consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance((double elem) -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        });
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
            return DoubleStreamPlus.from(resultStream);
        });
    }
    
    @Override
    public default DoubleStreamPlus distinct() {
        return DoubleStreamPlus.from(doubleStream().distinct());
    }
    
    //-- Sorted --
    
    @Override
    public default DoubleStreamPlus sorted() {
        return DoubleStreamPlus.from(doubleStream().sorted());
    }
    
    public default DoubleStreamPlus sortedBy(
            DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(
                doubleStream()
                .mapToObj   (d     -> new DoubleDoubleTuple(d, mapper.applyAsDouble(d)))
                .sorted     ((a,b) -> Double.compare(a._2, b._2))
                .mapToDouble(t     -> t._1));
    }
    
    public default DoubleStreamPlus sortedBy(
            DoubleUnaryOperator       mapper,
            ToIntBiDoubleFunctionPrimitive comparator) {
        return DoubleStreamPlus.from(
                doubleStream()
                .mapToObj   (d     -> new DoubleDoubleTuple(d, mapper.applyAsDouble(d)))
                .sorted     ((a,b) -> comparator.applyAsDoubleAndDouble(a._2, b._2))
                .mapToDouble(t     -> t._1));
    }
    
    // TODO - This should be changed to sortedAs
    public default <T extends Comparable<? super T>> DoubleStreamPlus sortedByObj(
            DoubleFunction<T> mapper) {
        return DoubleStreamPlus.from(
                doubleStream()
                .mapToObj   (d     -> new DoubleTuple2<>(d, mapper.apply(d)))
                .sorted     ((a,b) -> a._2.compareTo(b._2))
                .mapToDouble(t     -> t._1));
    }
    
    public default <T> DoubleStreamPlus sortedByObj(
            DoubleFunction<T> mapper, 
            Comparator<T>  comparator) {
        return DoubleStreamPlus.from(
                doubleStream()
                .mapToObj   (i     -> new DoubleTuple2<>(i, mapper.apply(i)))
                .sorted     ((a,b) -> comparator.compare(a._2, b._2))
                .mapToDouble(t     -> t._1));
    }
    
    //-- Terminate --
    
    @Override
    public default void forEach(DoubleConsumer action) {
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(DoubleConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default double reduce(double identity, DoubleBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalDouble reduce(DoubleBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(
            Supplier<R> supplier,
            ObjDoubleConsumer<R> accumulator,
            BiConsumer<R, R> combiner) {
        return terminate(stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default OptionalDouble min() {
        return terminate(stream -> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalDouble max() {
        return terminate(stream -> {
            return stream
                    .max();
        });
    }
    
    @Override
    public default long count() {
        return terminate(stream-> {
            return stream
                    .count();
        });
    }
    
    public default int size() {
        return terminate(stream-> {
            return (int)stream
                    .count();
        });
    }
    
    @Override
    public default boolean anyMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalDouble findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalDouble findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    //== toXXX ==
    
    public default StreamPlus<Double> asStream() {
        val stream 
                = StreamPlus.from(doubleStream()
                .mapToObj(i -> Double.valueOf(i)));
        stream.onClose(() -> { close(); });
        return stream;
    }
    
    @Override
    public default double[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default double sum() {
        return terminate(stream -> {
            return stream
                    .sum();
        });
    }
    
    @Override
    public default OptionalDouble average() {
        return terminate(stream -> {
            return stream
                    .average();
        });
    }
    
    @Override
    public default DoubleSummaryStatistics summaryStatistics() {
        return terminate(stream -> {
            return stream
                    .summaryStatistics();
        });
    }
    
    public default IntStreamPlus asIntegerStream() {
        return mapToInt(theDouble.toInteger());
    }
    
    public default LongStreamPlus asLongStream() {
        return mapToLong(theDouble.toLong());
    }
    
    public default DoubleStreamPlus asDoubleStream() {
        return this;
    }
    
    public default String toListString() {
        // TODO - There must be a faster way
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
//  public default ImmutableDoubleFuncList toImmutableList() {
//      return terminate(stream -> {
//          return ImmutableDoubleFuncList.from(this);
//      });
//  }

    
    //-- Iterator --
    
    /** DO NOT USE THIS METHOD OR YOUR STREAM WILL NOT BE CLOSED. */
    // TODO - We should move this to an external function
    public default PrimitiveIterator.OfDouble __iterator() {
        return DoubleIteratorPlus.from(doubleStream());
    }
    
    /**
     * Use iterator of this stream without terminating the stream.
     */
    public default DoubleStreamPlus useIterator(Function<DoubleIteratorPlus, DoubleStreamPlus> action) {
        return sequential(stream -> {
            DoubleStreamPlus result = null;
            try {
                val iterator = DoubleIteratorPlus.from(stream).iterator();
                result = action.apply(iterator);
                return result;
            } finally {
                if (result == null) {
                    f(()->close())
                    .runCarelessly();
                } else {
                    result
                    .onClose(()->{
                        f(()->close())
                        .runCarelessly();
                    });
                }
            }
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> useIteratorToObj(
            Function<DoubleIteratorPlus, StreamPlus<TARGET>> action) {
        return sequentialToObj(stream -> {
            StreamPlus<TARGET> result = null;
            try {
                val iterator = DoubleIteratorPlus.from(stream).iterator();
                result = action.apply(iterator);
                return result;
            } finally {
                if (result == null) {
                    f(()->close())
                    .runCarelessly();
                } else {
                    result
                    .onClose(()->{
                        f(()->close())
                        .runCarelessly();
                    });
                }
            }
        });
    }
    
    //== Plus ==
    
    public default String joinToString() {
        val strValue 
            = mapToObj(String::valueOf)
            .collect  (Collectors.joining());
        return strValue;
    }
    public default String joinToString(String delimiter) {
        val strValue 
            = mapToObj(String::valueOf)
            .collect  (Collectors.joining(delimiter));
        return strValue;
    }
    
    //== Pipe ==
    
    public default Pipeable<DoubleStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super DoubleStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    public default <T> StreamPlus<Result<T>> spawn(DoubleFunction<? extends UncompletedAction<T>> mapToAction) {
        return sequentialToObj(stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            List<? extends UncompletedAction<T>> actions 
                = doubleStream()
                .mapToObj(mapToAction)
                .peek    (action -> results.add(DeferAction.<T>createNew()))
                .peek    (action -> action
                    .getPromise()
                    .onComplete(result -> {
                        val thisIndex  = index.getAndIncrement();
                        val thisAction = results.get(thisIndex);
                        if (result.isValue())
                             thisAction.complete(result.value());
                        else thisAction.fail    (result.exception());
                    })
                )
                .peek   (action -> action.start())
                .collect(Collectors.toList())
                ;
            
            val resultStream 
                = StreamPlus
                .from(results.stream().map(action -> action.getResult()))
                ;
            resultStream
                .onClose(()->actions.forEach(action -> action.abort("Stream closed!")));
            
            return resultStream;
        });
    }
    
    public default DoubleStreamPlus accumulate(DoubleBiFunctionPrimitive accumulator) {
        return useIterator(iterator -> {
            if (!iterator.hasNext())
                return DoubleStreamPlus.empty();
            
            val prev = new double[] { iterator.nextDouble() };
            return DoubleStreamPlus
                    .concat(
                        DoubleStreamPlus.of(prev[0]),
                        iterator.stream().map(n -> {
                            val next = accumulator.applyAsDoubleAndDouble(n, prev[0]);
                            prev[0] = next;
                            return next;
                        })
                    );
        });
    }
    
    public default DoubleStreamPlus restate(DoubleObjBiFunction<DoubleStreamPlus, DoubleStreamPlus> restater) {
        val func = (UnaryOperator<DoubleTuple2<DoubleStreamPlus>>)((DoubleTuple2<DoubleStreamPlus> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            val iterator = stream.__iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = new double[] { iterator.nextDouble() };
            val tail = DoubleObjBiFunction.apply(restater, head[0], DoubleIteratorPlus.from(iterator).stream());
            if (tail == null)
                return null;
            
            return DoubleTuple2.<DoubleStreamPlus>of(head[0], tail);
        });
        val seed = DoubleTuple2.<DoubleStreamPlus>of(0, this);
        DoubleStreamPlus endStream 
            = StreamPlus
            .iterate    (seed, func)
            .takeUntil  (t -> t == null)
            .skip       (1)
            .mapToDouble(t -> t._1());
        return endStream;
    }
    
}
