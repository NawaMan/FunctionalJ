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

import static functionalj.function.Func.f;
import static functionalj.function.Func.itself;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
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
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntObjBiFunction;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.IntIteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;
import lombok.val;


@FunctionalInterface
public interface IntStreamPlus 
        extends 
            IntStream,
            IntStreamPlusWithMapFirst,
            IntStreamPlusWithMapThen,
            IntStreamPlusWithMapTuple,
            IntStreamPlusWithMapToMap,
            IntStreamPlusWithSplit,
            IntStreamPlusWithSegment,
            IntStreamPlusWithCombine,
            IntStreamPlusWithCalculate,
            IntStreamPlusAddtionalOperators,
            IntStreamPlusAdditionalTerminalOperators {
    
    //== Constructor ==
    
    public static IntStreamPlus empty() {
        return IntStreamPlus.from(IntStream.empty());
    }
    
    public static IntStreamPlus emptyIntStream() {
        return IntStreamPlus.from(IntStream.empty());
    }
    
    public static IntStreamPlus of(int ... ints) {
        if ((ints == null) || (ints.length == 0))
            return IntStreamPlus.empty();
        
        return IntStreamPlus.from(IntStream.of(Arrays.copyOf(ints, ints.length)));
    }
    
    public static IntStreamPlus ints(int ... ints) {
        return IntStreamPlus.of(ints);
    }
    
    // TODO - from-to, from almostTo, stepping.
    
    public static IntStreamPlus from(IntStream intStream) {
        if (intStream instanceof IntStreamPlus)
            return (IntStreamPlus)intStream;
            
        return ()->intStream;
    }
    
    public static IntStreamPlus zeroes() {
        return IntStreamPlus.generate(()->0);
    }
    
    public static IntStreamPlus zeroes(int count) {
        return IntStreamPlus.generate(()->0).limit(count);
    }
    
    public static IntStreamPlus ones() {
        return IntStreamPlus.generate(()->1);
    }
    
    public static IntStreamPlus ones(int count) {
        return IntStreamPlus.generate(()->1).limit(count);
    }
    
    public static IntStreamPlus repeat(int ... data) {
        return cycle(data);
    }
    
    public static IntStreamPlus cycle(int ... data) {
        val ints = Arrays.copyOf(data, data.length);
        val size = ints.length;
        return IntStreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .map(i -> data[i % size]));
    }
    
    public static IntStreamPlus loop() {
        return IntStreamPlus
                .infinite();
    }
    
    public static IntStreamPlus loop(int time) {
        return IntStreamPlus
                .infinite()
                .limit(time);
    }
    
    public static IntStreamPlus loopBy(int step) {
        return IntStreamPlus
                .infinite()
                .map(i -> i * step);
    }
    
    public static IntStreamPlus loopBy(int step, int time) {
        return IntStreamPlus
                .loopBy(step)
                .limit(time);
    }
    
    public static IntStreamPlus infinite() {
        return IntStreamPlus.from(IntStream.range(0, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus naturalNumbers() {
        return IntStreamPlus.from(IntStream.range(1, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus naturalNumbers(int count) {
        return naturalNumbers().limit(count);
    }
    
    public static IntStreamPlus wholeNumbers() {
        return IntStreamPlus.from(IntStream.range(0, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus wholeNumbers(int count) {
        return wholeNumbers().limit(count);
    }
    
    public static IntStreamPlus range(int startInclusive, int endExclusive) {
        return IntStreamPlus.from(IntStream.range(startInclusive, endExclusive));
    }
    
    public static IntStreamPlus concat(IntStreamPlus ... streams) {
        return StreamPlus.of(streams).flatMapToInt(s -> s.intStream()).mapToInt(i -> i);
    }
    
    public static IntStreamPlus generate(IntSupplier s) {
        return IntStreamPlus.from(IntStream.generate(s));
    }
    
    public static IntStreamPlus compound(int seed, IntUnaryOperator f) {
        return IntStreamPlus.from(IntStream.iterate(seed, f));
    }
    
    public static IntStreamPlus iterate(int seed, IntUnaryOperator f) {
        return IntStreamPlus.from(IntStream.iterate(seed, f));
    }
    
    public static IntStreamPlus compound(int seed1, int seed2, IntBinaryOperator f) {
        return IntStreamPlus.from(IntStreamPlus.iterate(seed1, seed2, f));
    }
    
    public static IntStreamPlus iterate(int seed1, int seed2, IntBinaryOperator f) {
        val counter = new AtomicInteger(0);
        val int1    = new AtomicInteger(seed1);
        val int2    = new AtomicInteger(seed2);
        return IntStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            int i2 = int2.get();
            int i1 = int1.getAndSet(i2);
            int i  = f.applyAsInt(i1, i2);
            int2.set(i);
            return i;
        });
    }
    
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1, 
            IntStream stream2) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2);
    }
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1, 
            IntStream stream2,
            int       defaultValue) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue);
    }
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1, int defaultValue1,
            IntStream stream2, int defaultValue2) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue1, defaultValue2);
    }
    
    public static IntStreamPlus zipOf(
            IntStream              stream1, 
            IntStream              stream2,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    public static IntStreamPlus zipOf(
            IntStream              stream1, 
            IntStream              stream2,
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue, merger);
    }
    public static IntStreamPlus zipOf(
            IntStream stream1, int defaultValue1,
            IntStream stream2, int defaultValue2,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    //== Core ==
    
    public IntStream intStream();
    
    public default IntStreamPlus intStreamPlus() {
        return this;
    }
    
    @Override
    public default StreamPlus<Integer> boxed() {
        return StreamPlus.from(intStream().boxed());
    }
    
    //== Helper functions ==
    
    public default <TARGET> TARGET terminate(Function<IntStream, TARGET> action) {
        val stream = intStream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<IntStream> action) {
        val stream = intStream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    public default IntStreamPlus sequential(Function<IntStreamPlus, IntStreamPlus> action) {
        val stream = intStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            val orgIntStreamPlus = IntStreamPlus.from(stream);
            val newIntStreamPlus = action.apply(orgIntStreamPlus);
            return newIntStreamPlus;
        }
        
        val orgIntStreamPlus = IntStreamPlus.from(stream.sequential());
        val newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel())
            return newIntStreamPlus;
        
        return newIntStreamPlus.parallel();
    }
    
    public default <TARGET> StreamPlus<TARGET> sequentialToObj(Function<IntStreamPlus, StreamPlus<TARGET>> action) {
        val stream = intStream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            return action.apply(IntStreamPlus.from(stream));
        }
        
        val resultStream = action.apply(IntStreamPlus.from(stream.sequential()));
        if (resultStream.isParallel())
            return resultStream;
        
        return resultStream.parallel();
    }
    
    public default IntStreamPlus derive(
            Function<IntStream, IntStream> action) {
        return IntStreamPlus
                .from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToStream(
            Function<IntStream, Stream<TARGET>> action) {
        return StreamPlus
                .from(action.apply(this));
    }
    
    //== Stream specific ==
    
    @Override
    public default IntStreamPlus sequential() {
        return IntStreamPlus.from(intStream().sequential());
    }
    
    @Override
    public default IntStreamPlus parallel() {
        return IntStreamPlus.from(intStream().sequential());
    }
    
    @Override
    public default IntStreamPlus unordered() {
        return IntStreamPlus.from(intStream().unordered());
    }
    
    public default boolean isParallel() {
        return intStream()
                .isParallel();
    }
    
    // TODO - Think about terminate
    @Override
    public default PrimitiveIterator.OfInt iterator() {
        return intStream()
                .iterator();
    }
    
    @Override
    public default Spliterator.OfInt spliterator() {
        return intStream()
                .spliterator();
    }
    
    @Override
    public default IntStreamPlus onClose(Runnable closeHandler) {
        return IntStreamPlus
                .from(intStream()
                .onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        intStream()
        .close();
    }
    
    //== Functionalities ==
    
    public default IntStreamPlus mapToInt(IntUnaryOperator mapper) {
        return IntStreamPlus.from(intStream().map(mapper));
    }
    
    @Override
    public default LongStreamPlus mapToLong(IntToLongFunction mapper) {
        return LongStreamPlus.from(intStream().mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
        return DoubleStreamPlus.from(intStream().mapToDouble (mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(IntFunction<? extends T> mapper) {
        StreamPlus<T> stream = StreamPlus.from(intStream().mapToObj(mapper));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default IntStreamPlus map(IntUnaryOperator mapper) {
        return IntStreamPlus.from(intStream().map(mapper));
    }
    
    @Override
    public default IntStreamPlus flatMap(
            IntFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(intStream().flatMap(mapper));
    }
    
    public default <T> StreamPlus<T> flatMapToObj(
            IntFunction<? extends Stream<T>> mapper) {
        return StreamPlus.from(
                intStream()
                .mapToObj(mapper)
                .flatMap(stream -> stream));
    }
    
    public default IntStreamPlus flatMapToInt(
            IntFunction<? extends IntStream> mapper) {
        return mapToObj(mapper).flatMapToInt(itself());
    }
    
    public default LongStreamPlus flatMapToLong(
            IntFunction<? extends LongStream> mapper) {
        return mapToObj(mapper).flatMapToLong(itself());
    }
    
    public default DoubleStreamPlus flatMapToDouble(
            IntFunction<? extends DoubleStream> mapper) {
        return mapToObj(mapper).flatMapToDouble(itself());
    }
    
    @Override
    public default IntStreamPlus filter(
            IntPredicate predicate) {
        return IntStreamPlus.from(intStream().filter(predicate));
    }
    
    // TODO - Rename all filter with map to filter
    public default IntStreamPlus filter(
            IntUnaryOperator mapper, 
            IntPredicate     predicate) {
        return IntStreamPlus.from(intStream().filter(i -> {
            val v = mapper.applyAsInt(i);
            val b = predicate.test(v);
            return b;
        }));
    }
    
    public default <T> IntStreamPlus filterAsObject(
            IntFunction<? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> IntStreamPlus filterAsObject(
            Function<Integer, ? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    @Override
    public default IntStreamPlus peek(IntConsumer action) {
        return IntStreamPlus.from(intStream().peek(action));
    }
    
    //-- Limit/Skip --
    
    @Override
    public default IntStreamPlus limit(long maxSize) {
        return IntStreamPlus.from(intStream().limit(maxSize));
    }
    
    @Override
    public default IntStreamPlus skip(long offset) {
        return IntStreamPlus.from(intStream().skip(offset));
    }
    
    public default IntStreamPlus skipWhile(IntPredicate condition) {
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
    
    public default IntStreamPlus skipUntil(IntPredicate condition) {
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
    
    public default IntStreamPlus takeWhile(IntPredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        return sequential(stream -> {
            val splitr = stream.spliterator();
            return IntStreamPlus.from(
                    StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(IntConsumer consumer) {
                            if (stillGoing) {
                                final boolean hadNext = splitr.tryAdvance((int elem) -> {
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
    
    public default IntStreamPlus takeUntil(IntPredicate condition) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance((int elem) -> {
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
            return IntStreamPlus.from(resultStream);
        });
    }
    
    @Override
    public default IntStreamPlus distinct() {
        return IntStreamPlus.from(intStream().distinct());
    }
    
    //-- Sorted --
    
    
    @Override
    public default IntStreamPlus sorted() {
        return IntStreamPlus.from(intStream().sorted());
    }
    
    public default IntStreamPlus sortedBy(
            IntUnaryOperator mapper) {
        return IntStreamPlus.from(
                intStream()
                .mapToObj(i     -> new IntIntTuple(i, mapper.applyAsInt(i)))
                .sorted  ((a,b) -> Integer.compare(a._2, b._2))
                .mapToInt(t     -> t._1));
    }
    
    public default IntStreamPlus sortedBy(
            IntUnaryOperator       mapper,
            IntBiFunctionPrimitive comparator) {
        return IntStreamPlus.from(
                intStream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.applyAsInt(i)))
                .sorted  ((a,b) -> comparator.applyAsIntAndInt(a._2, b._2))
                .mapToInt(t     -> t._1));
    }
    
    // TODO - This should be changed to sortedAs
    public default <T extends Comparable<? super T>> IntStreamPlus sortedByObj(
            IntFunction<T> mapper) {
        return IntStreamPlus.from(
                intStream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .sorted  ((a,b) -> a._2.compareTo(b._2))
                .mapToInt(t     -> t._1));
    }
    
    public default <T> IntStreamPlus sortedByObj(
            IntFunction<T> mapper, 
            Comparator<T>  comparator) {
        return IntStreamPlus.from(
                intStream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .sorted  ((a,b) -> comparator.compare(a._2, b._2))
                .mapToInt(t     -> t._1));
    }
    
    //-- Terminate --
    
    @Override
    public default void forEach(IntConsumer action) {
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(IntConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default int reduce(int identity, IntBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalInt reduce(IntBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(
            Supplier<R>       supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R>  combiner) {
        return terminate(stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default OptionalInt min() {
        return terminate(stream -> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalInt max() {
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
    public default boolean anyMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalInt findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalInt findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    //== toXXX ==
    
    public default StreamPlus<Integer> asStream() {
        val stream 
                = StreamPlus.from(intStream()
                .mapToObj(i -> Integer.valueOf(i)));
        stream.onClose(() -> { close(); });
        return stream;
    }
    
    @Override
    public default int[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default int sum() {
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
    public default IntSummaryStatistics summaryStatistics() {
        return terminate(stream -> {
            return stream
                    .summaryStatistics();
        });
    }
    
    @Override
    public default LongStreamPlus asLongStream() {
        return LongStreamPlus.from(intStream().asLongStream());
    }
    
    @Override
    public default DoubleStreamPlus asDoubleStream() {
        return DoubleStreamPlus.from(intStream().asDoubleStream());
    }
    
    public default String toListString() {
        // TODO - There must be a faster way
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
    public default ImmutableIntFuncList toImmutableList() {
        return terminate(stream -> {
            return ImmutableIntFuncList.from(this);
        });
    }
    
    //-- Iterator --
    
    /** DO NOT USE THIS METHOD OR YOUR STREAM WILL NOT BE CLOSED. */
    // TODO - We should move this to an external function
    public default PrimitiveIterator.OfInt __iterator() {
        return IntIteratorPlus.from(intStream());
    }
    
    /**
     * Use iterator of this stream without terminating the stream.
     */
    public default IntStreamPlus useIterator(Function<IntIteratorPlus, IntStreamPlus> action) {
        return sequential(stream -> {
            IntStreamPlus result = null;
            try {
                val iterator = IntIteratorPlus.from(stream).iterator();
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
    
    @Override
    public default <TARGET> StreamPlus<TARGET> useIteratorToObj(
            Function<IntIteratorPlus, StreamPlus<TARGET>> action) {
        return sequentialToObj(stream -> {
            StreamPlus<TARGET> result = null;
            try {
                val iterator = IntIteratorPlus.from(stream).iterator();
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
    
    public default Pipeable<IntStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super IntStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    public default <T> StreamPlus<Result<T>> spawn(IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return sequentialToObj(stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            List<? extends UncompletedAction<T>> actions 
                = intStream()
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
    
    public default IntStreamPlus accumulate(IntBiFunctionPrimitive accumulator) {
        return useIterator(iterator -> {
            if (!iterator.hasNext())
                return IntStreamPlus.empty();
            
            val prev = new int[] { iterator.nextInt() };
            return IntStreamPlus
                    .concat(
                        IntStreamPlus.of(prev[0]),
                        iterator.stream().map(n -> {
                            val next = accumulator.applyAsIntAndInt(n, prev[0]);
                            prev[0] = next;
                            return next;
                        })
                    );
        });
    }
    
    public default IntStreamPlus restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        val func = (UnaryOperator<IntTuple2<IntStreamPlus>>)((IntTuple2<IntStreamPlus> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            val iterator = stream.__iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = new int[] { iterator.nextInt() };
            val tail = IntObjBiFunction.apply(restater, head[0], IntIteratorPlus.from(iterator).stream());
            if (tail == null)
                return null;
            
            return IntTuple2.<IntStreamPlus>of(head[0], tail);
        });
        val seed = IntTuple2.<IntStreamPlus>of(0, this);
        IntStreamPlus endStream 
            = StreamPlus
            .iterate  (seed, func)
            .takeUntil(t -> t == null)
            .skip     (1)
            .mapToInt (t -> t._1());
        return endStream;
    }
    
}
