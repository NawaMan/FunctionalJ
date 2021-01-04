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

import static functionalj.function.Func.itself;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.Spliterators;
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
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.IntIntTuple;
import lombok.val;


// TODO - Use this for byte, short and char
// TODO - Intersect

@FunctionalInterface
public interface IntStreamPlus
        extends
            IntStream,
            AsIntStreamPlus,
            IntStreamPlusWithCalculate,
            IntStreamPlusWithCombine,
            IntStreamPlusWithFilter,
            IntStreamPlusWithFlatMap,
            IntStreamPlusWithGroupingBy,
            IntStreamPlusWithLimit,
            IntStreamPlusWithMap,
            IntStreamPlusWithMapFirst,
            IntStreamPlusWithMapThen,
            IntStreamPlusWithMapToMap,
            IntStreamPlusWithMapToTuple,
            IntStreamPlusWithMapWithIndex,
            IntStreamPlusWithMapWithPrev,
            IntStreamPlusWithModify,
            IntStreamPlusWithReshape,
            IntStreamPlusWithPeek,
            IntStreamPlusWithPipe,
            IntStreamPlusWithSort,
            IntStreamPlusWithSplit {
    
    // //== Constructor ==
    
    /** Returns an empty IntStreamPlus. */
    public static IntStreamPlus empty() {
        return IntStreamPlus
                .from(IntStream.empty());
    }
    
    /** Returns an empty StreamPlus. */
    public static IntStreamPlus emptyIntStream() {
        return IntStreamPlus
                .from(IntStream.empty());
    }
    
    /** Returns an empty StreamPlus. */
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
    
    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static IntStreamPlus repeat(int ... data) {
        return cycle(data);
    }
    
    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static IntStreamPlus cycle(int ... data) {
        val ints = Arrays.copyOf(data, data.length);
        val size = ints.length;
        return IntStreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .map(i -> data[i % size]));
    }
    
    /** Create a StreamPlus that for a loop with the number of time given - the value is the index of the loop. */
    public static IntStreamPlus loop() {
        return IntStreamPlus
                .infinite();
    }
    
    /** Create a StreamPlus that for a loop with the number of time given - the value is the index of the loop. */
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
    
    /** Create a StreamPlus that for an infinite loop - the value is the index of the loop. */
    public static IntStreamPlus infinite() {
        return IntStreamPlus
                .from(IntStream.range(0, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus naturalNumbers() {
        return IntStreamPlus
                .from(IntStream.range(1, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus naturalNumbers(int count) {
        return naturalNumbers()
                .limit(count);
    }
    
    public static IntStreamPlus wholeNumbers() {
        return IntStreamPlus
                .from(IntStream.range(0, Integer.MAX_VALUE));
    }
    
    public static IntStreamPlus wholeNumbers(int count) {
        return wholeNumbers()
                .limit(count);
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively to the end value exclusively. */
    public static IntStreamPlus range(int startInclusive, int endExclusive) {
        return IntStreamPlus
                .from(IntStream.range(startInclusive, endExclusive));
    }
    
    /** Concatenate all the given streams. */
    public static IntStreamPlus concat(IntStream ... streams) {
        return StreamPlus
                .of          (streams)
                .map         (s -> IntStreamPlus.from(s))
                .flatMapToInt(s -> s.intStream())
                .mapToInt    (i -> i);
    }
    
    /** Concatenate all the given streams. */
    public static IntStreamPlus combine(IntStreamPlus ... streams) {
        return concat(streams);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static IntStreamPlus generate(IntSupplier supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static IntStreamPlus generateWith(IntSupplier supplier) {
        val iterable = (IntIterable)() -> new IntSupplierBackedIterator(supplier);
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 <- seed,
     *      2 <- (1*2),
     *      4 <- ((1*2)*2),
     *      8 <- (((1*2)*2)*2),
     *      16 <- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of compound()
     **/
    public static IntStreamPlus iterate(int seed, IntUnaryOperator f) {
        return IntStreamPlus.from(IntStream.iterate(seed, f));
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 <- seed,
     *      2 <- (1*2),
     *      4 <- ((1*2)*2),
     *      8 <- (((1*2)*2)*2),
     *      16 <- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of iterate()
     **/
    public static IntStreamPlus compound(int seed, IntUnaryOperator f) {
        return iterate(seed, f);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 <- seed1,
     *      1 <- seed2,
     *      2 <- (1+1),
     *      3 <- (1+2),
     *      5 <- (2+3),
     *      8 <- (5+8)
     *      ...
     *
     * Note: this is an alias of compound()
     **/
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
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 <- seed1,
     *      1 <- seed2,
     *      2 <- (1+1),
     *      3 <- (1+2),
     *      5 <- (2+3),
     *      8 <- (5+8)
     *      ...
     *
     * Note: this is an alias of iterate()
     **/
    public static IntStreamPlus compound(int seed1, int seed2, IntBinaryOperator f) {
        return iterate(seed1, seed2, f);
    }
    
    /**
     * Create a StreamPlus by combining elements together into a StreamPlus of tuples.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     *
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1,
            IntStream stream2) {
        return IntStreamPlus.from(stream1).zipWith(stream2);
    }
    
    /**
     * Create a StreamPlus by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     *
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *     merger  = a + "+" + b
     *
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     **/
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1,
            IntStream stream2,
            int       defaultValue) {
        return IntStreamPlus.from(stream1).zipWith(stream2, defaultValue);
    }
    
    public static StreamPlus<IntIntTuple> zipOf(
            IntStream stream1, int defaultValue1,
            IntStream stream2, int defaultValue2) {
        return IntStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2);
    }
    
    public static IntStreamPlus zipOf(
            IntStream              stream1,
            IntStream              stream2,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1).zipWith(stream2, merger);
    }
    public static IntStreamPlus zipOf(
            IntStream              stream1,
            IntStream              stream2,
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1).zipWith(stream2, defaultValue, merger);
    }
    public static IntStreamPlus zipOf(
            IntStream stream1, int defaultValue1,
            IntStream stream2, int defaultValue2,
            IntBiFunctionPrimitive merger) {
        return IntStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    //== Core ==
    
    public IntStream intStream();
    
    public default IntStreamPlus derive(Func1<IntStreamPlus, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus deriveToInt(Func1<IntStreamPlus, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Func1<IntStreamPlus, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus intStreamPlus() {
        return this;
    }
    
    @Override
    public default StreamPlus<Integer> boxed() {
        return StreamPlus.from(intStream().boxed());
    }
    
    @Override
    public default LongStream asLongStream() {
        return mapToLong(i -> i);
    }
    
    @Override
    public default DoubleStream asDoubleStream() {
        return mapToDouble(i -> i);
    }
    
    // //== Helper functions ==
    
    public default <TARGET> TARGET terminate(
            Function<IntStream, TARGET> action) {
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
    
    public default IntStreamPlus sequential(Func1<IntStreamPlus, IntStreamPlus> action) {
        val isParallel = isParallel();
        val orgIntStreamPlus = sequential();
        val newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel() == isParallel)
            return newIntStreamPlus;
        
        if (isParallel)
            return newIntStreamPlus.parallel();
        
        return newIntStreamPlus.sequential();
    }
    
    //-- Characteristics --
    
    /**
     * Returns an equivalent stream that is sequential.  May return
     * itself, either because the stream was already sequential, or because
     * the underlying stream state was modified to be sequential.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return a sequential stream
     */
    public default IntStreamPlus sequential() {
        return IntStreamPlus.from(intStream().sequential());
    }
    
    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return a parallel stream
     */
    @Override
    public default IntStreamPlus parallel() {
        return IntStreamPlus.from(intStream().parallel());
    }
    
    /**
     * Returns an equivalent stream that is
     * <a href="package-summary.html#Ordering">unordered</a>.  May return
     * itself, either because the stream was already unordered, or because
     * the underlying stream state was modified to be unordered.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * @return an unordered stream
     */
    @Override
    public default IntStreamPlus unordered() {
        return IntStreamPlus.from(intStream().unordered());
    }
    
    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    @Override
    public default boolean isParallel() {
        return intStream().isParallel();
    }
    
    //-- Close --
    
    @Terminal
    @Override
    public default void close() {
        intStream().close();
    }
    
    @Override
    public default IntStreamPlus onClose(Runnable closeHandler) {
        return IntStreamPlus.from(intStream().onClose(closeHandler));
    }
    
    //-- Iterator --
    
    /** @return a iterator of this streamable. */
    @Override
    public default IntIteratorPlus iterator() {
        return IntIteratorPlus.from(intStream().iterator());
    }
    
    /** @return a spliterator of this streamable. */
    @Override
    public default Spliterator.OfInt spliterator() {
        return terminate(s -> {
            val iterator = iterator();
            return Spliterators.spliteratorUnknownSize(iterator, 0);
        });
    }
    
    //== Functionalities ==
    
    //-- Map --
    
    @Override
    public default IntStreamPlus map(IntUnaryOperator mapper) {
        return IntStreamPlus.from(intStream().map(mapper));
    }
    
    public default IntStreamPlus mapToInt(IntUnaryOperator mapper) {
        return IntStreamPlus.from(intStream().map(mapper));
    }
    
    @Override
    public default LongStream mapToLong(IntToLongFunction mapper) {
        return null;
    }
    
    public default DoubleStreamPlus mapToDouble() {
        return mapToDouble(i -> (double)i);
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
        return DoubleStreamPlus.from(intStream().mapToDouble(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(IntFunction<? extends T> mapper) {
        return StreamPlus.from(intStream().mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    @Override
    public default IntStreamPlus flatMap(IntFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(intStream().flatMap(mapper));
    }
    
    public default IntStreamPlus flatMapToInt(IntFunction<? extends IntStream> mapper) {
        return flatMap(mapper);
    }
    
    public default LongStream flatMapToLong(IntFunction<? extends LongStream> mapper) {
        return null;
    }
    
    public default DoubleStreamPlus flatMapToDouble(IntFunction<? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(mapToObj(mapper).flatMapToDouble(itself()));
    }
    
    public default <T> StreamPlus<T> flatMapToObj(IntFunction<? extends Stream<T>> mapper) {
        return StreamPlus.from(mapToObj(mapper).flatMap(itself()));
    }
    
    //-- Filter --
    
    @Override
    public default IntStreamPlus filter(IntPredicate predicate) {
        return from(intStream().filter(predicate));
    }
    
    //-- Peek --
    
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
    
    @Override
    public default IntStreamPlus distinct() {
        return IntStreamPlus.from(intStream().distinct());
    }
    
    //-- Sorted --
    
    @Eager
    @Override
    public default IntStreamPlus sorted() {
        return IntStreamPlus.from(intStream().sorted());
    }
    
    @Eager
    public default IntStreamPlus sorted(IntBiFunctionPrimitive comparator) {
        return IntStreamPlus.from(
                intStream()
                .boxed   ()
                .sorted  ((a,b) -> comparator.applyAsIntAndInt(a, b))
                .mapToInt(i -> i));
    }
    
    //-- Terminate --
    
    @Eager
    @Terminal
    @Override
    public default void forEach(IntConsumer action) {
        terminate(stream -> {
            stream
            .forEach(action);
        });
    }
    
    @Eager
    @Terminal
    @Sequential
    @Override
    public default void forEachOrdered(IntConsumer action) {
        terminate(stream -> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default int reduce(int identity, IntBinaryOperator reducer) {
        return terminate(stream -> {
            return stream
                    .reduce(identity, reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalInt reduce(IntBinaryOperator reducer) {
        return terminate(stream -> {
            return stream
                    .reduce(reducer);
        });
    }
    
    @Eager
    @Terminal
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
    
    //-- statistics --
    
    @Eager
    @Terminal
    @Override
    public default OptionalInt min() {
        return terminate(stream -> {
            return stream.min();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalInt max() {
        return terminate(stream -> {
            return stream.max();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default long count() {
        return terminate(stream -> {
            return stream
                    .count();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default int sum() {
        return terminate(stream -> {
            return stream.sum();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble average() {
        return terminate(stream -> {
            return stream.average();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default IntSummaryStatistics summaryStatistics() {
        return terminate(stream -> {
            return stream
                    .summaryStatistics();
        });
    }
    
    //-- Match --
    
    @Terminal
    @Override
    public default boolean anyMatch(IntPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean allMatch(IntPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean noneMatch(IntPredicate predicate) {
        return terminate(stream -> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Terminal
    @Override
    public default OptionalInt findFirst() {
        return terminate(stream -> {
            return stream
                    .findFirst();
        });
    }
    
    @Terminal
    @Override
    public default OptionalInt findAny() {
        return terminate(stream -> {
            return stream
                    .findAny();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalInt firstResult() {
        return findFirst();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt lastResult() {
        return terminate(stream -> {
            boolean[] isAdded = new boolean[] { false };
            int[]     dataRef = new int[0];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalInt.of(dataRef[0]) : OptionalInt.empty();
        });
    }
    
    //== Conversion ==
    
    @Eager
    @Terminal
    @Override
    public default int[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
}
