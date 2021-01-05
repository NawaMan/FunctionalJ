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
package functionalj.streamable.doublestreamable;

import static functionalj.lens.Access.theDouble;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func0;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleIterable;
import functionalj.stream.doublestream.DoubleIteratorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.DoubleDoubleTuple;
import lombok.val;


//TODO - Use this for byte, short and char
//TODO - Add intersect (retain) - but might want to do it after sort.

@FunctionalInterface
public interface DoubleStreamable
        extends
            AsDoubleStreamable,
            DoubleIterable,
            DoubleStreamableWithCalculate,
            DoubleStreamableWithCombine,
            DoubleStreamableWithFilter,
            DoubleStreamableWithFlatMap,
            DoubleStreamableWithGroupingBy,
            DoubleStreamableWithLimit,
            DoubleStreamableWithMap,
            DoubleStreamableWithMapFirst,
            DoubleStreamableWithMapGroup,
            DoubleStreamableWithMapThen,
            DoubleStreamableWithMapToMap,
            DoubleStreamableWithMapToTuple,
            DoubleStreamableWithMapWithIndex,
            DoubleStreamableWithModify,
            DoubleStreamableWithPeek,
            DoubleStreamableWithPipe,
            DoubleStreamableWithReshape,
            DoubleStreamableWithSort,
            DoubleStreamableWithSplit,
            DoubleStreamableWithStatistic {
    
    /** Returns an empty Streamable. */
    public static DoubleStreamable empty() {
        return ()->DoubleStreamPlus.empty();
    }
    
    /** Returns an empty Streamable. */
    public static DoubleStreamable emptyDoubleStreamable() {
        return ()->DoubleStreamPlus.empty();
    }
    
    /** Create a Streamable from the given data. */
    public static DoubleStreamable of(double ... data) {
        if (data == null) {
            return empty();
        }
        
        val length = data.length;
        val ints   = new double[length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->DoubleStreamPlus.of(ints);
    }
    
    /** Create a Streamable from the given data. */
    public static DoubleStreamable steamableOf(double ... data) {
        return of(data);
    }
    
    /** Create a Streamable from the given data. */
    public static DoubleStreamable doubleSteamableOf(double ... data) {
        return of(data);
    }
    
    /** Create a Streamable from the given data. */
    public static DoubleStreamable ints(double ... data) {
        return of(data);
    }
    
    public static DoubleStreamable from(Supplier<DoubleStreamPlus> supplier) {
        return supplier::get;
    }
    
    /** Concatenate all the given streams. */
    public static DoubleStreamable concat(DoubleStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToDouble(s -> s.doubleStream());
    }
    
    /** Concatenate all the given streamables. */
    public static DoubleStreamable combine(DoubleStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToDouble(s -> s.doubleStream());
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleStreamable generate(DoubleSupplier doubleSupplier) {
        return ()->DoubleStreamPlus.generate(doubleSupplier);
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleStreamable generateWith(Func0<DoubleStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToDouble(s -> s.get());
    }
    
    /**
     * Create a Streamable by apply the compounder to the seed over and over.
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
    // TODO - Make it a throwable version of UnaryOperator
    public static DoubleStreamable iterate(
            double              seed,
            DoubleUnaryOperator compounder) {
        return ()->DoubleStreamPlus.iterate(seed, compounder);
    }
    
    /**
     * Create a Streamable by apply the compounder to the seed over and over.
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
    // TODO - Make it a throwable version of UnaryOperator
    public static DoubleStreamable compound(
            double              seed,
            DoubleUnaryOperator compounder) {
        return ()->DoubleStreamPlus.compound(seed, compounder);
    }
    
    /**
     * Create a Streamable by apply the compounder to the seeds over and over.
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
    // TODO - Make it a throwable version of BinaryOperator
    public static DoubleStreamable iterate(
            double               seed1,
            double               seed2,
            DoubleBinaryOperator compounder) {
        return ()->DoubleStreamPlus.iterate(seed1, seed2, compounder);
    }
    
    /**
     * Create a Streamable by apply the compounder to the seeds over and over.
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
    // TODO - Make it a throwable version of BinaryOperator
    public static DoubleStreamable compound(
            double               seed1,
            double               seed2,
            DoubleBinaryOperator compounder) {
        return ()->DoubleStreamPlus.iterate(seed1, seed2, compounder);
    }
    
    public static DoubleStreamable repeat(double ... data) {
        val length = data.length;
        val ints   = new double[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->{
            val flatten
                    = Stream
                    .generate       (() -> DoubleStreamPlus.of(ints))
                    .flatMapToDouble(s -> s);
            return DoubleStreamPlus.from(flatten);
        };
    }
    
    public static DoubleStreamable repeat(DoubleFuncList data) {
        return ()->{
            val flatten
                    = Stream
                    .generate       (() -> data.doubleStream())
                    .flatMapToDouble(s  -> s);
            return DoubleStreamPlus.from(flatten);
        };
    }
    
    public static DoubleStreamable cycle(double ... data) {
        return DoubleStreamable.repeat(data);
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static DoubleStreamable cycle(DoubleFuncList data) {
        return DoubleStreamable.repeat(data);
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively bu the given step. */
    public static DoubleStreamable rangeStep(double startInclusive, double step) {
        return DoubleStreamable
                .iterate(startInclusive, d -> d + step);
    }
    
    /** Returns the infinite streams of zeroes. */
    public static DoubleStreamable zeroes() {
        return DoubleStreamable.generate(()->0);
    }
    
    /** Returns the streams of zeroes. */
    public static DoubleStreamable zeroes(int count) {
        return DoubleStreamable.generate(()->0).limit(count);
    }
    
    /** Returns the infinite streams of ones. */
    public static DoubleStreamable ones() {
        return DoubleStreamable.generate(()->1);
    }
    
    /** Returns the streams of ones. */
    public static DoubleStreamable ones(int count) {
        return DoubleStreamable.generate(()->1).limit(count);
    }
    
    //-- Zip --
    
    /**
     * Create a Streamable by combining elements together into a Streamable of tuples.
     * Only elements with pair will be combined. If this is not desirable, use streamable1.zip(streamable2).
     *
     * For example:
     *     streamable1 = [A, B, C, D, E]
     *     streamable2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static Streamable<DoubleDoubleTuple> zipOf(
            AsDoubleStreamable streamable1,
            AsDoubleStreamable streamable2) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.doubleStream());
        };
    }
    
    public static Streamable<DoubleDoubleTuple> zipOf(
            AsDoubleStreamable streamable1,
            AsDoubleStreamable streamable2,
            double             defaultValue) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.doubleStream(),
                    defaultValue);
        };
    }
    
    public static Streamable<DoubleDoubleTuple> zipOf(
            AsDoubleStreamable streamable1,
            double             defaultValue1,
            AsDoubleStreamable streamable2,
            double             defaultValue2) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(), defaultValue1,
                    streamable2.doubleStream(), defaultValue2);
        };
    }
    
    /** Zip integers from two DoubleStreamables and combine it into another object. */
    public static DoubleStreamable zipOf(
            AsDoubleStreamable        streamable1,
            AsDoubleStreamable        streamable2,
            DoubleBiFunctionPrimitive merger) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.doubleStream(),
                    merger);
        };
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static DoubleStreamable zipOf(
            AsDoubleStreamable        streamable1,
            AsDoubleStreamable        streamable2,
            double                    defaultValue,
            DoubleBiFunctionPrimitive merger) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.doubleStream(),
                    defaultValue,
                    merger);
        };
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static DoubleStreamable zipOf(
            AsDoubleStreamable        streamable1,
            double                    defaultValue1,
            AsDoubleStreamable        streamable2,
            double                    defaultValue2,
            DoubleBiFunctionPrimitive merger) {
        return ()->{
            return DoubleStreamPlus.zipOf(
                    streamable1.doubleStream(), defaultValue1,
                    streamable2.doubleStream(), defaultValue2,
                    merger);
        };
    }
    
    //== Core ==
    
    /** Return the stream of data behind this IntStreamable. */
    public default DoubleStreamPlus doubleStream() {
        return doubleStreamPlus();
    }
    
    /** Return this StreamPlus. */
    public  DoubleStreamPlus doubleStreamPlus();
    
    /** Return the this as a streamable. */
    public default DoubleStreamable doubleStreamable() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE> DoubleStreamable deriveFrom(
            AsStreamable<SOURCE>                       asStreamable,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.streamPlus();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> DoubleStreamable deriveFrom(
            AsIntStreamable                       asStreamable,
            Function<IntStreamPlus, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.intStreamPlus();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }
    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> IntStreamable deriveFrom(
//            AsLongStreamable                    asStreamable,
//            Function<LongStreamPlus, IntStream> action) {
//        return () -> {
//            val sourceStream = asStreamable.longStream();
//            val targetStream = action.apply(sourceStream);
//            return IntStreamPlus.from(targetStream);
//        };
//    }
    
    /** Create a Streamable from the given LongStreamable. */
    public static <TARGET> DoubleStreamable deriveFrom(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.doubleStream();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> IntStreamable deriveToInt(
            AsDoubleStreamable                    asStreamable,
            Function<DoubleStreamPlus, IntStream> action) {
        return IntStreamable.deriveFrom(asStreamable, action);
    }
    
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> LongStreamable deriveToLong(
//            AsIntStreamable                     asStreamable,
//            Function<IntStreamPlus, LongStream> action) {
//        return LongStreamable.deriveFrom(asStreamable, action);
//    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> DoubleStreamable deriveToDouble(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleStreamable.deriveFrom(asStreamable, action);
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE, TARGET> Streamable<TARGET> deriveToObj(
            AsDoubleStreamable                         asStreamable,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return Streamable.deriveFrom(asStreamable, action);
    }
    
    /** Returns stream of Integer from the value of this stream. */
    public default Streamable<Double> boxed() {
        return ()->StreamPlus.from(doubleStream().mapToObj(theDouble.boxed()::apply));
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
    public default DoubleStreamable sequential() {
        return deriveFrom(this, streamble -> streamble.doubleStream().sequential());
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
    public default DoubleStreamable parallel() {
        return deriveFrom(this, streamble -> streamble.doubleStream().parallel());
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
    public default DoubleStreamable unordered() {
        return deriveFrom(this, streamble -> streamble.doubleStream().unordered());
    }
    
    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    public default boolean isParallel() {
        return doubleStreamPlus()
                .isParallel();
    }
    
    //-- Iterator --
    
    /** @return the iterable of this streamable. */
    public default DoubleIterable iterable() {
        return () -> iterator();
    }
    
    /** @return a iterator of this streamable. */
    @Override
    public default DoubleIteratorPlus iterator() {
        return () -> doubleStream().doubleStream().iterator();
    }
    
    /** @return a spliterator of this streamable. */
    public default Spliterator.OfDouble spliterator() {
        return doubleStream().spliterator();
    }
    
    //-- Map --
    
    /** Map each value into other value using the function. */
    public default DoubleStreamable map(DoubleUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.doubleStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntStreamable mapToInt() {
        return mapToInt(d -> (int)Math.round(d));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntStreamable mapToInt(DoubleToIntFunction mapper) {
        return deriveToInt(this, stream -> stream.mapToInt(mapper));
    }
    
//    /** Map each value into a long value using the function. */
//    public default LongStreamable mapToLong(IntToLongFunction mapper) {
//        return deriveToLong(this, stream -> stream.mapToLong(mapper));
//    }
    
    /** Map each value into a double value using the function. */
    public default DoubleStreamable mapToDouble(DoubleUnaryOperator mapper) {
        return deriveFrom(this, stream -> stream.map(mapper));
    }
    
    public default <TARGET> Streamable<TARGET> mapToObj(DoubleFunction<? extends TARGET> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a streamable and then flatten that streamable */
    public default DoubleStreamable flatMap(DoubleFunction<? extends AsDoubleStreamable> mapper) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).doubleStream()));
    }
    
    /** Map a value into an integer streamable and then flatten that streamable */
    public default IntStreamable flatMapToInt(DoubleFunction<? extends AsIntStreamable> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
    }
    
//    /** Map a value into a long streamable and then flatten that streamable */
//    public default LongStreamable flatMapToLong(IntFunction<? extends AsLongStreamable> mapper) {
//        return LongStreamable.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
//    }
    
    /** Map a value into a double streamable and then flatten that streamable */
    public default DoubleStreamable flatMapToDouble(DoubleFunction<? extends AsDoubleStreamable> mapper) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default DoubleStreamable filter(DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default DoubleStreamable filter(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default DoubleStreamable peek(DoubleConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default DoubleStreamable limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    
    public default DoubleStreamable skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default DoubleStreamable distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default DoubleStreamable sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default DoubleStreamable sorted(
            // TODO - DoubleComparator
            DoubleDoubleToIntFunctionPrimitive comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    //-- Terminate --
    
    /** Process each value using the given action */
    public default void forEach(DoubleConsumer action) {
        doubleStream()
        .forEach(action);
    }
    
    /**
     * Performs an action for each element of this stream, in the encounter
     * order of the stream if the stream has a defined encounter order.
     */
    public default void forEachOrdered(DoubleConsumer action) {
        if (action == null)
            return;
        
        doubleStream()
        .forEachOrdered(action);
    }
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     **/
    public default double reduce(double identity, DoubleBinaryOperator accumulator) {
        return doubleStream()
                .reduce(identity, accumulator);
    }
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     **/
    public default OptionalDouble reduce(DoubleBinaryOperator accumulator) {
        return doubleStream()
                .reduce(accumulator);
    }
    
    /**
     * Performs a mutable reduction operation on the elements of this stream. A mutable reduction is one in which the reduced value is
     * a mutable result container, such as an {@code ArrayList}, and elements are incorporated by updating the state of the result rather
     * than by replacing the result.
     **/
    public default <R> R collect(
            Supplier<R>          supplier,
            ObjDoubleConsumer<R> accumulator,
            BiConsumer<R, R>     combiner) {
        return doubleStream()
                .collect(supplier, accumulator, combiner);
    }
    
    //-- statistics --
    
    /** Using the comparator, find the minimal value */
    public default OptionalDouble min() {
        return doubleStream()
                .min();
    }
    
    /** Using the comparator, find the maximum value */
    public default OptionalDouble max() {
        return doubleStream()
                .max();
    }
    
    public default long count() {
        return doubleStream().count();
    }
    
    public default double sum() {
        return doubleStream().sum();
    }
    
    public default OptionalDouble average() {
        return doubleStream().average();
    }
    
    public default DoubleSummaryStatistics summaryStatistics() {
        return doubleStream().summaryStatistics();
    }
    
    //-- Match --
    
    /** Check if any element match the predicate */
    public default boolean anyMatch(DoublePredicate predicate) {
        return doubleStream().anyMatch(predicate);
    }
    
    /** Check if all elements match the predicate */
    public default boolean allMatch(DoublePredicate predicate) {
        return doubleStream().allMatch(predicate);
    }
    
    /** Check if none of the elements match the predicate */
    public default boolean noneMatch(DoublePredicate predicate) {
        return doubleStream().noneMatch(predicate);
    }
    
    /** Returns the sequentially first element */
    public default OptionalDouble findFirst() {
        return doubleStream().findFirst();
    }
    
    /** Returns the any element */
    public default OptionalDouble findAny() {
        return doubleStream().findAny();
    }
    
    //== Conversion ==
    
    /** Convert this streamable to an array. */
    public default double[] toArray() {
        return doubleStream()
                .toArray();
    }
    
}
