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
package functionalj.stream.longstream;

import static functionalj.function.Func.f;
import static functionalj.function.Func.itself;
import static functionalj.lens.Access.theLong;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.FuncUnit1;
import functionalj.function.LongBiFunctionPrimitive;
import functionalj.function.LongObjBiFunction;
import functionalj.function.ToIntBiLongFunctionPrimitive;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.LongTuple2;
import lombok.val;


public interface LongStreamPlus
        extends 
            LongStream,
            LongStreamPlusWithMapFirst,
            LongStreamPlusWithMapThen,
            LongStreamPlusWithMapTuple,
            LongStreamPlusWithMapToMap,
//            IntStreamPlusWithSplit,
            LongStreamPlusWithSegment,
            LongStreamPlusWithCombine,
            LongStreamPlusWithCalculate,
            LongStreamPlusAddtionalOperators,
            LongStreamPlusAdditionalTerminalOperators {
    
    //== Constructor ==
    
    public static LongStreamPlus empty() {
        return LongStreamPlus.from(LongStream.empty());
    }
    
    public static LongStreamPlus emptyLongStream() {
        return LongStreamPlus.from(LongStream.empty());
    }
    
    public static LongStreamPlus of(long ... longs) {
        if ((longs == null) || (longs.length == 0))
            return LongStreamPlus.empty();
        
        return LongStreamPlus.from(LongStream.of(Arrays.copyOf(longs, longs.length)));
    }
    
    public static LongStreamPlus longs(long ... longs) {
        return LongStreamPlus.of(longs);
    }
    
    // TODO - from-to, from almostTo, stepping.
    
    public static LongStreamPlus from(LongStream longStream) {
        if (longStream instanceof LongStreamPlus)
            return (LongStreamPlus)longStream;
            
        return ()->longStream;
    }
    
    public static LongStreamPlus zeroes() {
        return LongStreamPlus.generate(()->0);
    }
    
    public static LongStreamPlus zeroes(long count) {
        return LongStreamPlus.generate(()->0).limit(count);
    }
    
    public static LongStreamPlus ones() {
        return LongStreamPlus.generate(()->1);
    }
    
    public static LongStreamPlus ones(long count) {
        return LongStreamPlus.generate(()->1).limit(count);
    }
    
    public static LongStreamPlus repeat(long ... data) {
        return cycle(data);
    }
    
    public static LongStreamPlus cycle(long ... data) {
        val longs = Arrays.copyOf(data, data.length);
        val size = longs.length;
        return LongStreamPlus.from(
                LongStream
                .iterate(0, i -> i + 1)
                .map(i -> data[(int)(i % size)]));
    }
    
    public static LongStreamPlus loop() {
        return LongStreamPlus
                .infinite();
    }
    
    public static LongStreamPlus loop(long time) {
        return LongStreamPlus
                .infinite()
                .limit(time);
    }
    
    public static LongStreamPlus infinite() {
        return LongStreamPlus
                .from(LongStream.range(0, Long.MAX_VALUE));
    }
    
    public static LongStreamPlus naturalNumbers() {
        return LongStreamPlus.from(LongStream.range(1, Long.MAX_VALUE));
    }
    
    public static LongStreamPlus naturalNumbers(long count) {
        return naturalNumbers().limit(count);
    }
    
    public static LongStreamPlus wholeNumbers() {
        return LongStreamPlus.from(LongStream.range(0, Long.MAX_VALUE));
    }
    
    public static LongStreamPlus wholeNumbers(long count) {
        return wholeNumbers().limit(count);
    }
    
    public static LongStreamPlus range(long startInclusive, long endExclusive) {
        return LongStreamPlus.from(LongStream.range(startInclusive, endExclusive));
    }
    
    public static LongStreamPlus concat(LongStreamPlus ... streams) {
        return StreamPlus.of(streams).flatMapToLong(s -> s.longStream()).mapToLong(i -> i);
    }
    
    public static LongStreamPlus generate(LongSupplier s) {
        return LongStreamPlus.from(LongStream.generate(s));
    }
    
    public static LongStreamPlus compound(long seed, LongUnaryOperator f) {
        return LongStreamPlus.from(LongStream.iterate(seed, f));
    }
    
    public static LongStreamPlus iterate(long seed, LongUnaryOperator f) {
        return LongStreamPlus.from(LongStream.iterate(seed, f));
    }
    
    public static LongStreamPlus compound(long seed1, long seed2, LongBinaryOperator f) {
        return LongStreamPlus.from(LongStreamPlus.iterate(seed1, seed2, f));
    }
    
    public static LongStreamPlus iterate(long seed1, long seed2, LongBinaryOperator f) {
        val counter = new AtomicLong(0);
        val long1   = new AtomicLong(seed1);
        val long2   = new AtomicLong(seed2);
        return LongStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            long l2 = long2.get();
            long l1 = long1.getAndSet(l2);
            long l  = f.applyAsLong(l1, l2);
            long2.set(l);
            return l;
        });
    }
    
    public static StreamPlus<LongLongTuple> zipOf(
            LongStream stream1, 
            LongStream stream2) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2);
    }
    public static StreamPlus<LongLongTuple> zipOf(
            LongStream stream1, 
            LongStream stream2,
            long       defaultValue) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue);
    }
    public static StreamPlus<LongLongTuple> zipOf(
            LongStream stream1, long defaultValue1,
            LongStream stream2, long defaultValue2) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue1, defaultValue2);
    }
    
    public static LongStreamPlus zipOf(
            LongStream              stream1, 
            LongStream              stream2,
            LongBiFunctionPrimitive merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    public static LongStreamPlus zipOf(
            LongStream              stream1, 
            LongStream              stream2,
            long                    defaultValue,
            LongBiFunctionPrimitive merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue, merger);
    }
    public static LongStreamPlus zipOf(
            LongStream stream1, long defaultValue1,
            LongStream stream2, long defaultValue2,
            LongBiFunctionPrimitive merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    //== Core ==
    
    public LongStream longStream();
    
    public default LongStreamPlus longStreamPlus() {
        return this;
    }
    
    public default LongStream stream() {
        return this;
    }
    
    @Override
    public default StreamPlus<Long> boxed() {
        return StreamPlus.from(longStream().boxed());
    }
    
    //== Helper functions ==
    
    public default <TARGET> TARGET terminate(Function<LongStream, TARGET> action) {
        val stream = longStream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<LongStream> action) {
        val stream = longStream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    public default LongStreamPlus sequential(Function<LongStreamPlus, LongStreamPlus> action) {
        val stream = longStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            val orgLongStreamPlus = LongStreamPlus.from(stream);
            val newLongStreamPlus = action.apply(orgLongStreamPlus);
            return newLongStreamPlus;
        }
        
        val orgLongStreamPlus = LongStreamPlus.from(stream.sequential());
        val newLongStreamPlus = action.apply(orgLongStreamPlus);
        if (newLongStreamPlus.isParallel())
            return newLongStreamPlus;
        
        return newLongStreamPlus.parallel();
    }
    
    public default <TARGET> StreamPlus<TARGET> sequentialToObj(Function<LongStreamPlus, StreamPlus<TARGET>> action) {
        val stream = longStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            return action.apply(LongStreamPlus.from(stream));
        }
        
        val resultStream = action.apply(LongStreamPlus.from(stream.sequential()));
        if (resultStream.isParallel())
            return resultStream;
        
        return resultStream.parallel();
    }
    
    public default LongStreamPlus derive(
            Function<LongStream, LongStream> action) {
        return LongStreamPlus
                .from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToStream(
            Function<LongStream, Stream<TARGET>> action) {
        return StreamPlus
                .from(action.apply(this));
    }
    
    //== Stream specific ==
    
    @Override
    public default LongStreamPlus sequential() {
        return LongStreamPlus.from(longStream().sequential());
    }
    
    @Override
    public default LongStreamPlus parallel() {
        return LongStreamPlus.from(longStream().sequential());
    }
    
    @Override
    public default LongStreamPlus unordered() {
        return LongStreamPlus.from(longStream().unordered());
    }
    
    public default boolean isParallel() {
        return longStream()
                .isParallel();
    }
    
    // TODO - Think about terminate
    @Override
    public default PrimitiveIterator.OfLong iterator() {
        return longStream()
                .iterator();
    }
    
    @Override
    public default Spliterator.OfLong spliterator() {
        return longStream()
                .spliterator();
    }
    
    @Override
    public default LongStreamPlus onClose(Runnable closeHandler) {
        return LongStreamPlus
                .from(longStream()
                .onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        longStream()
        .close();
    }
    
    //== Functionalities ==
    
    @Override
    public default IntStreamPlus mapToInt(LongToIntFunction mapper) {
        return IntStreamPlus.from(longStream().mapToInt(mapper));
    }
    
    public default LongStreamPlus mapToLong(LongUnaryOperator mapper) {
        return LongStreamPlus.from(longStream().map(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(LongToDoubleFunction mapper) {
        return DoubleStreamPlus.from(longStream().mapToDouble(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(LongFunction<? extends T> mapper) {
        StreamPlus<T> stream = StreamPlus.from(longStream().mapToObj(mapper));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default LongStreamPlus map(LongUnaryOperator mapper) {
        return LongStreamPlus.from(longStream().map(mapper));
    }
    
    @Override
    public default LongStreamPlus flatMap(
            LongFunction<? extends LongStream> mapper) {
        return LongStreamPlus.from(longStream().flatMap(mapper));
    }
    
    public default <T> StreamPlus<T> flatMapToObj(
            LongFunction<? extends Stream<T>> mapper) {
        return StreamPlus.from(
                longStream()
                .mapToObj(mapper)
                .flatMap(stream -> stream));
    }
    
    public default IntStreamPlus flatMapToInt(
            LongFunction<? extends IntStream> mapper) {
        return mapToObj(mapper).flatMapToInt(itself());
    }
    
    public default LongStreamPlus flatMapToLong(
            LongFunction<? extends LongStream> mapper) {
        return mapToObj(mapper).flatMapToLong(itself());
    }
    
    public default DoubleStreamPlus flatMapToDouble(
            LongFunction<? extends DoubleStream> mapper) {
        return mapToObj(mapper).flatMapToDouble(itself());
    }
    
    @Override
    public default LongStreamPlus filter(
            LongPredicate predicate) {
        return LongStreamPlus.from(longStream().filter(predicate));
    }
    
    // TODO - Rename all filter with map to filter
    public default LongStreamPlus filter(
            LongUnaryOperator mapper, 
            LongPredicate     predicate) {
        return LongStreamPlus.from(longStream().filter(i -> {
            val v = mapper.applyAsLong(i);
            val b = predicate.test(v);
            return b;
        }));
    }
    
    public default <T> LongStreamPlus filterAsObject(
            LongFunction<? extends T> mapper,
            Predicate<? super T>      theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> LongStreamPlus filterAsObject(
            Function<Long, ? extends T> mapper,
            Predicate<? super T>        theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    @Override
    public default LongStreamPlus peek(LongConsumer action) {
        return LongStreamPlus.from(longStream().peek(action));
    }
    
    //-- Limit/Skip --
    
    @Override
    public default LongStreamPlus limit(long maxSize) {
        return LongStreamPlus.from(longStream().limit(maxSize));
    }
    
    @Override
    public default LongStreamPlus skip(long offset) {
        return LongStreamPlus.from(longStream().skip(offset));
    }
    
    public default LongStreamPlus skipWhile(LongPredicate condition) {
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
    
    public default LongStreamPlus skipUntil(LongPredicate condition) {
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
    
    public default LongStreamPlus takeWhile(LongPredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        return sequential(stream -> {
            val splitr = stream.spliterator();
            return LongStreamPlus.from(
                    StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(LongConsumer consumer) {
                            if (stillGoing) {
                                final boolean hadNext = splitr.tryAdvance((long elem) -> {
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
    
    public default LongStreamPlus takeUntil(LongPredicate condition) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance((long elem) -> {
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
            return LongStreamPlus.from(resultStream);
        });
    }
    
    @Override
    public default LongStreamPlus distinct() {
        return LongStreamPlus.from(longStream().distinct());
    }
    
    //-- Sorted --
    
    @Override
    public default LongStreamPlus sorted() {
        return LongStreamPlus.from(longStream().sorted());
    }
    
    public default LongStreamPlus sortedBy(
            LongUnaryOperator mapper) {
        return LongStreamPlus.from(
                longStream()
                .mapToObj (i     -> new LongLongTuple(i, mapper.applyAsLong(i)))
                .sorted   ((a,b) -> Long.compare(a._2, b._2))
                .mapToLong(t     -> t._1));
    }
    
    public default LongStreamPlus sortedBy(
            LongUnaryOperator            mapper,
            ToIntBiLongFunctionPrimitive comparator) {
        return LongStreamPlus.from(
                longStream()
                .mapToObj (i     -> new LongLongTuple(i, mapper.applyAsLong(i)))
                .sorted   ((a,b) -> comparator.applyAsLongAndLong(a._2, b._2))
                .mapToLong(t     -> t._1));
    }
    
    // TODO - This should be changed to sortedAs
    public default <T extends Comparable<? super T>> LongStreamPlus sortedByObj(
            LongFunction<T> mapper) {
        return LongStreamPlus.from(
                longStream()
                .mapToObj (i     -> new LongTuple2<T>(i, mapper.apply(i)))
                .sorted   ((a,b) -> a._2.compareTo(b._2))
                .mapToLong(t     -> t._1));
    }
    
    public default <T> LongStreamPlus sortedByObj(
            LongFunction<T> mapper, 
            Comparator<T>   comparator) {
        return LongStreamPlus.from(
                longStream()
                .mapToObj (i     -> new LongTuple2<T>(i, mapper.apply(i)))
                .sorted   ((a,b) -> comparator.compare(a._2, b._2))
                .mapToLong(t     -> t._1));
    }
    
    //-- Terminate --
    
    @Override
    public default void forEach(LongConsumer action) {
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(LongConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default long reduce(long identity, LongBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalLong reduce(LongBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(
            Supplier<R>        supplier,
            ObjLongConsumer<R> accumulator,
            BiConsumer<R, R>   combiner) {
        return terminate(stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default OptionalLong min() {
        return terminate(stream -> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalLong max() {
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
    public default boolean anyMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalLong findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalLong findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    //== toXXX ==
    
    public default StreamPlus<Long> asStream() {
        val stream 
                = StreamPlus.from(longStream()
                .mapToObj(i -> Long.valueOf(i)));
        stream.onClose(() -> { close(); });
        return stream;
    }
    
    @Override
    public default long[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default long sum() {
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
    public default LongSummaryStatistics summaryStatistics() {
        return terminate(stream -> {
            return stream
                    .summaryStatistics();
        });
    }
    
    public default IntStreamPlus asIntegerStream() {
        return mapToInt((LongToIntFunction)theLong.toInteger()::apply);
    }
    
    @Override
    public default DoubleStreamPlus asDoubleStream() {
        return DoubleStreamPlus.from(longStream().asDoubleStream());
    }
    
    public default String toListString() {
        // TODO - There must be a faster way
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
//    public default ImmutableLongFuncList toImmutableList() {
//        return terminate(stream -> {
//            return ImmutableIntFuncList.from(this);
//        });
//    }
    
    //-- Iterator --
    
    /** DO NOT USE THIS METHOD OR YOUR STREAM WILL NOT BE CLOSED. */
    public default PrimitiveIterator.OfLong __iterator() {
        return LongIteratorPlus.from(longStream());
    }
    
    /**
     * Use iterator of this stream without terminating the stream.
     */
    public default LongStreamPlus useIterator(Function<LongIteratorPlus, LongStreamPlus> action) {
        return sequential(stream -> {
            LongStreamPlus result = null;
            try {
                val iterator = LongIteratorPlus.from(stream).iterator();
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
            Function<LongIteratorPlus, StreamPlus<TARGET>> action) {
        return sequentialToObj(stream -> {
            StreamPlus<TARGET> result = null;
            try {
                val iterator = LongIteratorPlus.from(stream).iterator();
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
    
    public default Pipeable<LongStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super LongStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    public default <T> StreamPlus<Result<T>> spawn(LongFunction<? extends UncompletedAction<T>> mapToAction) {
        return sequentialToObj(stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            List<? extends UncompletedAction<T>> actions 
                = longStream()
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
                .collect(toList())
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
    
    public default LongStreamPlus accumulate(LongBiFunctionPrimitive accumulator) {
        return useIterator(iterator -> {
            if (!iterator.hasNext())
                return LongStreamPlus.empty();
            
            val prev = new long[] { iterator.nextLong() };
            return LongStreamPlus
                    .concat(
                        LongStreamPlus.of(prev[0]),
                        iterator.stream().map(n -> {
                            val next = accumulator.applyAsLongAndLong(n, prev[0]);
                            prev[0] = next;
                            return next;
                        })
                    );
        });
    }
    
    public default LongStreamPlus restate(LongObjBiFunction<LongStreamPlus, LongStreamPlus> restater) {
        val func = (UnaryOperator<LongTuple2<LongStreamPlus>>)((LongTuple2<LongStreamPlus> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            val iterator = stream.__iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = new long[] { iterator.nextLong() };
            val tail = LongObjBiFunction.apply(restater, head[0], LongIteratorPlus.from(iterator).stream());
            if (tail == null)
                return null;
            
            return LongTuple2.<LongStreamPlus>of(head[0], tail);
        });
        val seed = LongTuple2.<LongStreamPlus>of(0, this);
        LongStreamPlus endStream 
            = StreamPlus.iterate(seed, func)
            .takeUntil(t -> t == null)
            .skip     (1)
            .mapToLong(t -> t._1());
        return endStream;
    }
    
}
