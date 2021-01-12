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
package functionalj.stream.doublestream;

import static functionalj.function.Func.itself;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
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
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.DoubleDoubleTuple;
import lombok.val;


// TODO - Use this for byte, short and char
// TODO - Intersect


@FunctionalInterface
public interface DoubleStreamPlus
        extends
            DoubleStream,
            AsDoubleStreamPlus,
            DoubleStreamPlusWithCalculate,
            DoubleStreamPlusWithCombine,
            DoubleStreamPlusWithFilter,
            DoubleStreamPlusWithFlatMap,
            DoubleStreamPlusWithLimit,
            DoubleStreamPlusWithMap,
            DoubleStreamPlusWithMapFirst,
            DoubleStreamPlusWithMapGroup,
            DoubleStreamPlusWithMapThen,
            DoubleStreamPlusWithMapToMap,
            DoubleStreamPlusWithMapToTuple,
            DoubleStreamPlusWithMapWithIndex,
            DoubleStreamPlusWithModify,
            DoubleStreamPlusWithReshape,
            DoubleStreamPlusWithPeek,
            DoubleStreamPlusWithPipe,
            DoubleStreamPlusWithSort,
            DoubleStreamPlusWithSplit {
    
    // //== Constructor ==
    
    /** Returns an empty IntStreamPlus. */
    public static DoubleStreamPlus empty() {
        return DoubleStreamPlus
                .from(DoubleStream.empty());
    }
    
    /** Returns an empty StreamPlus. */
    public static DoubleStreamPlus emptyDoubleStream() {
        return DoubleStreamPlus
                .from(DoubleStream.empty());
    }
    
    /** Returns an empty StreamPlus. */
    public static DoubleStreamPlus of(double ... values) {
        if ((values == null) || (values.length == 0))
            return DoubleStreamPlus.empty();
        
        return DoubleStreamPlus.from(DoubleStream.of(Arrays.copyOf(values, values.length)));
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
        return DoubleStreamPlus.generate(()->1.0);
    }
    
    public static DoubleStreamPlus ones(int count) {
        return DoubleStreamPlus.generate(()->1.0).limit(count);
    }

    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static DoubleStreamPlus repeat(double ... data) {
        return cycle(data);
    }

    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static DoubleStreamPlus cycle(double ... data) {
        val doubles = Arrays.copyOf(data, data.length);
        val size    = doubles.length;
        return DoubleStreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToDouble(i -> data[i % size]));
    }
    
    public static DoubleStreamPlus naturalNumbers() {
        return DoubleStreamPlus
                .iterate(1.0, d -> d + 1.0);
    }
    
    public static DoubleStreamPlus naturalNumbers(int count) {
        return naturalNumbers()
                .limit(count);
    }
    
    public static DoubleStreamPlus wholeNumbers() {
        return DoubleStreamPlus
                .iterate(0.0, d -> d + 1.0);
    }
    
    public static DoubleStreamPlus wholeNumbers(int count) {
        return wholeNumbers()
                .limit(count);
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively bu the given step. */
    public static DoubleStreamPlus rangeStep(double startInclusive, double step) {
        return DoubleStreamPlus
                .iterate(startInclusive, d -> d + step);
    }
    
    /** Concatenate all the given streams. */
    public static DoubleStreamPlus concat(DoubleStream ... streams) {
        return StreamPlus
                .of             (streams)
                .map            (s -> DoubleStreamPlus.from(s))
                .flatMapToDouble(s -> s.doubleStream());
    }
    
    /** Concatenate all the given streams. */
    public static DoubleStreamPlus combine(DoubleStreamPlus ... streams) {
        return concat(streams);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleStreamPlus generate(DoubleSupplier supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleStreamPlus generateWith(DoubleSupplier supplier) {
        val iterable = (DoubleIterable)() -> new DoubleSupplierBackedIterator(supplier);
        return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
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
    public static DoubleStreamPlus iterate(double seed, DoubleUnaryOperator f) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
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
    public static DoubleStreamPlus compound(double seed, DoubleUnaryOperator f) {
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
    public static DoubleStreamPlus iterate(double seed1, double seed2, DoubleBinaryOperator f) {
        val counter = new AtomicInteger(0);
        val value1  = new double[] { seed1 };
        val value2  = new double[] { seed2 };
        return DoubleStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            double i2 = value1[0];
            double i1 = value2[0];
            value2[0] = i2;
            double i  = f.applyAsDouble(i1, i2);
            value2[0] = i;
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
    public static DoubleStreamPlus compound(Double seed1, Double seed2, DoubleBinaryOperator f) {
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
    public static StreamPlus<DoubleDoubleTuple> zipOf(
            DoubleStream stream1,
            DoubleStream stream2) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2);
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
    public static StreamPlus<DoubleDoubleTuple> zipOf(
            DoubleStream stream1,
            DoubleStream stream2,
            double       defaultValue) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue);
    }
    
    public static StreamPlus<DoubleDoubleTuple> zipOf(
            DoubleStream stream1, double defaultValue1,
            DoubleStream stream2, double defaultValue2) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2);
    }
    
    public static DoubleStreamPlus zipOf(
            DoubleStream              stream1,
            DoubleStream              stream2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, merger);
    }
    public static DoubleStreamPlus zipOf(
            DoubleStream              stream1,
            DoubleStream              stream2,
            double                    defaultValue,
            DoubleBiFunctionPrimitive merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue, merger);
    }
    public static DoubleStreamPlus zipOf(  
            DoubleStream stream1, double defaultValue1,
            DoubleStream stream2, double defaultValue2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    //== Core ==
    
    public DoubleStream doubleStream();
    
    
    public default DoubleStreamPlus doubleStreamPlus() {
        return this;
    }
    
    
    public default DoubleStreamPlus derive(Func1<DoubleStreamPlus, DoubleStream> action) {
        return DoubleStreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus deriveToInt(Func1<DoubleStreamPlus, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
    public default LongStream deriveToLong(Func1<DoubleStreamPlus, LongStream> action) {
//        return LongStreamPlus.from(action.apply(this));
        return null;
    }
    
    public default DoubleStreamPlus deriveToDouble(Func1<DoubleStreamPlus, DoubleStream> action) {
        return DoubleStreamPlus.from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Func1<DoubleStreamPlus, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
    }
    
    @Override
    public default StreamPlus<Double> boxed() {
        return StreamPlus.from(doubleStream().boxed());
    }
    
    // //== Helper functions ==
    
    public default <TARGET> TARGET terminate(
            Function<DoubleStream, TARGET> action) {
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
    
    public default DoubleStreamPlus sequential(Func1<DoubleStreamPlus, DoubleStreamPlus> action) {
        val isParallel = isParallel();
        val orgDoubleStreamPlus = sequential();
        val newDoubleStreamPlus = action.apply(orgDoubleStreamPlus);
        if (newDoubleStreamPlus.isParallel() == isParallel)
            return newDoubleStreamPlus;
        
        if (isParallel)
            return newDoubleStreamPlus.parallel();
        
        return newDoubleStreamPlus.sequential();
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
    public default DoubleStreamPlus sequential() {
        return DoubleStreamPlus.from(doubleStream().sequential());
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
    public default DoubleStreamPlus parallel() {
        return DoubleStreamPlus.from(doubleStream().parallel());
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
    public default DoubleStreamPlus unordered() {
        return DoubleStreamPlus.from(doubleStream().unordered());
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
        return doubleStream().isParallel();
    }
    
    //-- Close --
    
    @Terminal
    @Override
    public default void close() {
        doubleStream().close();
    }
    
    @Override
    public default DoubleStreamPlus onClose(Runnable closeHandler) {
        return DoubleStreamPlus.from(doubleStream().onClose(closeHandler));
    }
    
    //-- Iterator --
    
    /** @return a iterator of this streamable. */
    @Override
    public default DoubleIteratorPlus iterator() {
        return DoubleIteratorPlus.from(doubleStream().iterator());
    }
    
    /** @return a spliterator of this streamable. */
    @Override
    public default Spliterator.OfDouble spliterator() {
        return terminate(s -> {
            val iterator = iterator();
            return Spliterators.spliteratorUnknownSize(iterator, 0);
        });
    }
    
    //== Functionalities ==
    
    //-- Map --
    
    @Override
    public default DoubleStreamPlus map(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    public default IntStreamPlus mapToInt() {
        return mapToInt(d -> (int)Math.round(d));
    }
    
    public default IntStreamPlus mapToInt(DoubleToIntFunction mapper) {
        return IntStreamPlus.from(doubleStream().mapToInt(mapper));
    }
    
    public default DoubleStreamPlus mapToDouble(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(DoubleFunction<? extends T> mapper) {
        return StreamPlus.from(doubleStream().mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    @Override
    public default DoubleStreamPlus flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return flatMap(mapper);
    }
    
    public default IntStreamPlus flatMapToInt(DoubleFunction<? extends IntStream> mapper) {
        return mapToObj(mapper).flatMapToInt(itself());
    }
    
    public default DoubleStreamPlus flatMapToDouble(DoubleFunction<? extends DoubleStream> mapper) {
        return flatMap(mapper);
    }
    
    public default <T> StreamPlus<T> flatMapToObj(DoubleFunction<? extends Stream<T>> mapper) {
        return mapToObj(mapper).flatMap(itself());
    }
    
    //-- Filter --
    
    @Override
    public default DoubleStreamPlus filter(DoublePredicate predicate) {
        return from(doubleStream().filter(predicate));
    }
    
    //-- Peek --
    
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
    
    @Override
    public default DoubleStreamPlus distinct() {
        return DoubleStreamPlus.from(doubleStream().distinct());
    }
    
    //-- Sorted --
    
    @Eager
    @Override
    public default DoubleStreamPlus sorted() {
        return DoubleStreamPlus.from(doubleStream().sorted());
    }
    
    @Eager
    public default DoubleStreamPlus sorted(DoubleDoubleToIntFunctionPrimitive comparator) {
        return DoubleStreamPlus.from(
                doubleStream()
                .boxed      ()
                .sorted     ((a,b) -> comparator.applyAsDoubleAndDouble(a, b))
                .mapToDouble(d -> d));
    }
    
    //-- Terminate --
    
    @Eager
    @Terminal
    @Override
    public default void forEach(DoubleConsumer action) {
        terminate(stream -> {
            stream
            .forEach(action);
        });
    }
    
    @Eager
    @Terminal
    @Sequential
    @Override
    public default void forEachOrdered(DoubleConsumer action) {
        terminate(stream -> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default double reduce(double identity, DoubleBinaryOperator reducer) {
        return terminate(stream -> {
            return stream
                    .reduce(identity, reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble reduce(DoubleBinaryOperator reducer) {
        return terminate(stream -> {
            return stream
                    .reduce(reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <R> R collect(
            Supplier<R>          supplier,
            ObjDoubleConsumer<R> accumulator,
            BiConsumer<R, R>     combiner) {
        return terminate(stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    //-- statistics --
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble min() {
        return terminate(stream -> {
            return stream.min();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble max() {
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
    public default double sum() {
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
    public default DoubleSummaryStatistics summaryStatistics() {
        return terminate(stream -> {
            return stream
                    .summaryStatistics();
        });
    }
    
    //-- Match --
    
    @Terminal
    @Override
    public default boolean anyMatch(DoublePredicate predicate) {
        return terminate(stream -> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean allMatch(DoublePredicate predicate) {
        return terminate(stream -> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean noneMatch(DoublePredicate predicate) {
        return terminate(stream -> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Terminal
    @Override
    public default OptionalDouble findFirst() {
        return terminate(stream -> {
            return stream
                    .findFirst();
        });
    }
    
    @Terminal
    @Override
    public default OptionalDouble findAny() {
        return terminate(stream -> {
            return stream
                    .findAny();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble firstResult() {
        return findFirst();
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble lastResult() {
        return terminate(stream -> {
            boolean[] isAdded = new boolean[] { false };
            double[]  dataRef = new double[1];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalDouble.of(dataRef[0]) : OptionalDouble.empty();
        });
    }
    //== Conversion ==
    
    @Eager
    @Terminal
    @Override
    public default double[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
}
