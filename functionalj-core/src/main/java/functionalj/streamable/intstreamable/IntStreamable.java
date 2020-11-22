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
package functionalj.streamable.intstreamable;

import static functionalj.lens.Access.theInteger;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntIterable;
import functionalj.stream.intstream.IntIteratorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.tuple.IntIntTuple;
import lombok.val;


//TODO - Use this for byte, short and char
//TODO - Add intersect (retain) - but might want to do it after sort.

public interface IntStreamable
        extends
            AsIntStreamable,
            IntIterable,
            IntStreamableWithCalculate,
            IntStreamableWithCombine,
            IntStreamableWithFilter,
            IntStreamableWithFlatMap,
            IntStreamableWithGroupingBy,
            IntStreamableWithLimit,
            IntStreamableWithMap,
            IntStreamableWithMapFirst,
            IntStreamableWithMapThen,
            IntStreamableWithMapToMap,
            IntStreamableWithMapToTuple,
            IntStreamableWithMapWithIndex,
            IntStreamableWithMapWithPrev,
            IntStreamableWithModify,
            IntStreamableWithPeek,
            IntStreamableWithPipe,
            IntStreamableWithReshape,
            IntStreamableWithSort,
            IntStreamableWithSplit,
            IntStreamableWithStatistic {
    
    /** Returns an empty Streamable. */
    public static IntStreamable empty() {
        return ()->IntStreamPlus.empty();
    }
    
    /** Returns an empty Streamable. */
    public static IntStreamable emptyIntStreamable() {
        return ()->IntStreamPlus.empty();
    }
    
    /** Create a Streamable from the given data. */
    public static IntStreamable of(int ... data) {
        if (data == null) {
            return empty();
        }
        
        var length = data.length;
        var ints   = new int[length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->IntStreamPlus.of(ints);
    }
    
    /** Create a Streamable from the given data. */
    public static IntStreamable steamableOf(int ... data) {
        return of(data);
    }
    
    /** Create a Streamable from the given data. */
    public static IntStreamable intSteamableOf(int ... data) {
        return of(data);
    }
    
    /** Create a Streamable from the given data. */
    public static IntStreamable ints(int ... data) {
        return of(data);
    }
    
    public static IntStreamable from(Supplier<IntStreamPlus> supplier) {
        return supplier::get;
    }
    
    /** Concatenate all the given streams. */
    public static IntStreamable concat(IntStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToInt(s -> s.intStream());
    }
    
    /** Concatenate all the given streamables. */
    public static IntStreamable combine(IntStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToInt(s -> s.intStream());
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static IntStreamable generate(IntSupplier intSupplier) {
        return ()->IntStreamPlus.generate(intSupplier);
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static IntStreamable generateWith(Func0<IntStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToInt(s -> s.get());
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
    public static IntStreamable iterate(
            int              seed,
            IntUnaryOperator compounder) {
        return ()->IntStreamPlus.iterate(seed, compounder);
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
    public static IntStreamable compound(
            int              seed,
            IntUnaryOperator compounder) {
        return ()->IntStreamPlus.compound(seed, compounder);
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
    public static IntStreamable iterate(
            int               seed1,
            int               seed2,
            IntBinaryOperator compounder) {
        return ()->IntStreamPlus.iterate(seed1, seed2, compounder);
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
    public static IntStreamable compound(
            int               seed1,
            int               seed2,
            IntBinaryOperator compounder) {
        return ()->IntStreamPlus.iterate(seed1, seed2, compounder);
    }
    
    public static IntStreamable repeat(int ... data) {
        var length = data.length;
        var ints   = new int[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->{
            var flatten
                    = Stream
                    .generate    (() -> IntStreamPlus.of(ints))
                    .flatMapToInt(s -> s);
            return IntStreamPlus.from(flatten);
        };
    }
    
    public static IntStreamable repeat(IntFuncList data) {
        return ()->{
            var flatten
                    = Stream
                    .generate    (() -> data.intStream())
                    .flatMapToInt(s -> s);
            return IntStreamPlus.from(flatten);
        };
    }
    
    public static IntStreamable cycle(int ... data) {
        return IntStreamable.repeat(data);
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static IntStreamable cycle(IntFuncList data) {
        return IntStreamable.repeat(data);
    }
    
    /** Create a Streamable that for an infinite loop - the value is boolean true */
    public static IntStreamable loop() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    /** Create a Streamable that for a loop with the number of time given - the value is the index of the loop. */
    public static IntStreamable loop(int time) {
        return ()->IntStreamPlus.range(0, time);
    }
    
    /** Create a Streamable that for an infinite loop - the value is the index of the loop. */
    public static IntStreamable infinite() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    /** Create a Streamable that for an infinite loop - the value is the index of the loop. */
    public static IntStreamable infiniteInt() {
        return infinite();
    }
    
    /** Create a Streamable that for a loop from the start value inclusively to the end value exclusively. */
    public static IntStreamable range(int startInclusive, int endExclusive) {
        return ()->IntStreamPlus.range(startInclusive, endExclusive);
    }
    
    /** Create a Streamable that for a loop from the start value inclusively to the end value inclusively. */
    public static IntStreamable rangeAll(int startInclusive, int endInclusive) {
        return ()->IntStreamPlus.range(startInclusive, endInclusive + 1);
    }
    
    /** Returns the infinite streams of zeroes. */
    public static IntStreamable zeroes() {
        return IntStreamable.generate(()->0);
    }
    
    /** Returns the streams of zeroes. */
    public static IntStreamable zeroes(int count) {
        return IntStreamable.generate(()->0).limit(count);
    }
    
    /** Returns the infinite streams of ones. */
    public static IntStreamable ones() {
        return IntStreamable.generate(()->1);
    }
    
    /** Returns the streams of ones. */
    public static IntStreamable ones(int count) {
        return IntStreamable.generate(()->1).limit(count);
    }
    
    /** Returns the infinite streams of natural numbers -- 1, 2, 3, .... */
    public static IntStreamable naturalNumbers() {
        return ()->IntStreamPlus.naturalNumbers();
    }
    
    /** Returns the infinite streams of wholes numbers -- 0, 1, 2, 3, .... */
    public static IntStreamable wholeNumbers() {
        return ()->IntStreamPlus.wholeNumbers();
    }
    
    /** Returns the streams of natural numbers -- 1, 2, 3, .... */
    public static IntStreamable naturalNumbers(int count) {
        return naturalNumbers().limit(count);
    }
    
    /** Returns the streams of wholes numbers -- 0, 1, 2, 3, .... */
    public static IntStreamable wholeNumbers(int count) {
        return wholeNumbers().limit(count);
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
    public static Streamable<IntIntTuple> zipOf(
            AsIntStreamable streamable1,
            AsIntStreamable streamable2) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream());
        };
    }
    
    public static Streamable<IntIntTuple> zipOf(
            AsIntStreamable streamable1,
            AsIntStreamable streamable2,
            int             defaultValue) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    defaultValue);
        };
    }
    
    public static Streamable<IntIntTuple> zipOf(
            AsIntStreamable streamable1,
            int             defaultValue1,
            AsIntStreamable streamable2,
            int             defaultValue2) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2);
        };
    }
    
    /** Zip integers from two IntStreamables and combine it into another object. */
    public static IntStreamable zipOf(
            AsIntStreamable        streamable1,
            AsIntStreamable        streamable2,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    merger);
        };
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static IntStreamable zipOf(
            AsIntStreamable        streamable1,
            AsIntStreamable        streamable2,
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    defaultValue,
                    merger);
        };
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static IntStreamable zipOf(
            AsIntStreamable        streamable1,
            int                    defaultValue1,
            AsIntStreamable        streamable2,
            int                    defaultValue2,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2,
                    merger);
        };
    }
    
    //== Core ==
    
    /** Return the stream of data behind this IntStreamable. */
    public default IntStreamPlus intStream() {
        return intStreamPlus();
    }
    
    /** Return this StreamPlus. */
    public  IntStreamPlus intStreamPlus();
    
    /** Return the this as a streamable. */
    public default IntStreamable intStreamable() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE> IntStreamable deriveFrom(
            AsStreamable<SOURCE>                    asStreamable,
            Function<StreamPlus<SOURCE>, IntStream> action) {
        return () -> {
            var sourceStream = asStreamable.streamPlus();
            var targetStream = action.apply(sourceStream);
            return IntStreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> IntStreamable deriveFrom(
            AsIntStreamable                    asStreamable,
            Function<IntStreamPlus, IntStream> action) {
        return () -> {
            var sourceStream = asStreamable.intStreamPlus();
            var targetStream = action.apply(sourceStream);
            return IntStreamPlus.from(targetStream);
        };
    }
//    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> IntStreamable deriveFrom(
//            AsLongStreamable                    asStreamable,
//            Function<LongStreamPlus, IntStream> action) {
//        return () -> {
//            var sourceStream = asStreamable.longStream();
//            var targetStream = action.apply(sourceStream);
//            return IntStreamPlus.from(targetStream);
//        };
//    }
//    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> IntStreamable deriveFrom(
//            AsDoubleStreamable                    asStreamable,
//            Function<DoubleStreamPlus, IntStream> action) {
//        return () -> {
//            var sourceStream = asStreamable.doubleStream();
//            var targetStream = action.apply(sourceStream);
//            return IntStreamPlus.from(targetStream);
//        };
//    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> IntStreamable deriveToInt(
            AsIntStreamable                    asStreamable,
            Function<IntStreamPlus, IntStream> action) {
        return IntStreamable.deriveFrom(asStreamable, action);
    }
//    
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> LongStreamable deriveToLong(
//            AsIntStreamable                     asStreamable,
//            Function<IntStreamPlus, LongStream> action) {
//        return LongStreamable.deriveFrom(asStreamable, action);
//    }
//    
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> DoubleStreamable deriveToDouble(
//            AsIntStreamable                       asStreamable,
//            Function<IntStreamPlus, DoubleStream> action) {
////        return DoubleStreamable.deriveFrom(asStreamable, action);
//        return null;
//    }
//    
    /** Create a Streamable from another streamable. */
    public static <SOURCE, TARGET> Streamable<TARGET> deriveToObj(
            AsIntStreamable                         asStreamable,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return Streamable.deriveFrom(asStreamable, action);
    }
    
    /** Returns stream of Integer from the value of this stream. */
    public default Streamable<Integer> boxed() {
        return ()->StreamPlus.from(intStream().mapToObj(theInteger.boxed()));
    }
    
    /** Returns the streamable value in this stream as int aka itself */
    public default IntStreamable asIntStreamable() {
        return this;
    }
//    
//    /** Returns the streamable value in this stream as long */
//    public default LongStreamable asLongStreamable() {
//        return deriveToLong(this, stream -> stream.mapToLong(i -> (long)i));
//    }
//    
//    /** Returns the streamable value in this stream as double */
//    public default DoubleStreamable asDoubleStream() {
//        return deriveToDouble(this, stream -> stream.mapToDouble(i -> (long)i));
//    }
    
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
    public default IntStreamable sequential() {
        return deriveFrom(this, streamble -> streamble.intStream().sequential());
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
    public default IntStreamable parallel() {
        return deriveFrom(this, streamble -> streamble.intStream().parallel());
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
    public default IntStreamable unordered() {
        return deriveFrom(this, streamble -> streamble.intStream().unordered());
    }
    
    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    public default boolean isParallel() {
        return intStreamPlus()
                .isParallel();
    }
    
    //-- Iterator --
    
    /** @return the iterable of this streamable. */
    public default IntIterable iterable() {
        return () -> iterator();
    }
    
    /** @return a iterator of this streamable. */
    @Override
    public default IntIteratorPlus iterator() {
        return () -> iterator();
    }
    
    /** @return a spliterator of this streamable. */
    public default Spliterator.OfInt spliterator() {
        return intStream().spliterator();
    }
    
    //-- Map --
    
    /** Map each value into other value using the function. */
    public default IntStreamable map(IntUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.intStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntStreamable mapToInt(IntUnaryOperator mapper) {
        return deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
//    
//    /** Map each value into a long value using the function. */
//    public default LongStreamable mapToLong(IntToLongFunction mapper) {
//        return deriveToLong(this, stream -> stream.mapToLong(mapper));
//    }
//    
//    /** Map each value into a double value using the function. */
//    public default DoubleStreamable mapToDouble(IntToDoubleFunction mapper) {
//        return deriveToDouble(this, stream -> stream.mapToDouble(mapper));
//    }
    
    public default <TARGET> Streamable<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a streamable and then flatten that streamable */
    public default IntStreamable flatMap(IntFunction<? extends AsIntStreamable> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
    
    /** Map a value into an integer streamable and then flatten that streamable */
    public default IntStreamable flatMapToInt(IntFunction<? extends AsIntStreamable> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
//    
//    /** Map a value into a long streamable and then flatten that streamable */
//    public default LongStreamable flatMapToLong(IntFunction<? extends AsLongStreamable> mapper) {
//        return LongStreamable.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
//    }
//    
//    /** Map a value into a double streamable and then flatten that streamable */
//    public default DoubleStreamable flatMapToDouble(IntFunction<? extends AsDoubleStreamable> mapper) {
//        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
//    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default IntStreamable filter(IntPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default IntStreamable filter(
            IntUnaryOperator mapper,
            IntPredicate     predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default IntStreamable peek(IntConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default IntStreamable limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    
    public default IntStreamable skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default IntStreamable distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default IntStreamable sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default IntStreamable sorted(
            IntBiFunctionPrimitive comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    //-- Terminate --
    
    /** Process each value using the given action */
    public default void forEach(IntConsumer action) {
        intStream()
        .forEach(action);
    }
    
    /**
     * Performs an action for each element of this stream, in the encounter
     * order of the stream if the stream has a defined encounter order.
     */
    public default void forEachOrdered(IntConsumer action) {
        if (action == null)
            return;
        
        intStream()
        .forEachOrdered(action);
    }
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     **/
    public default int reduce(int identity, IntBinaryOperator accumulator) {
        return intStream()
                .reduce(identity, accumulator);
    }
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     **/
    public default OptionalInt reduce(IntBinaryOperator accumulator) {
        return intStream()
                .reduce(accumulator);
    }
    
    /**
     * Performs a mutable reduction operation on the elements of this stream. A mutable reduction is one in which the reduced value is
     * a mutable result container, such as an {@code ArrayList}, and elements are incorporated by updating the state of the result rather
     * than by replacing the result.
     **/
    public default <R> R collect(
            Supplier<R>       supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R>  combiner) {
        return intStream()
                .collect(supplier, accumulator, combiner);
    }
    
    //-- statistics --
    
    /** Using the comparator, find the minimal value */
    public default OptionalInt min() {
        return intStream()
                .min();
    }
    
    /** Using the comparator, find the maximum value */
    public default OptionalInt max() {
        return intStream()
                .max();
    }
    
    public default long count() {
        return intStream().count();
    }
    
    public default int sum() {
        return intStream().sum();
    }
    
    public default OptionalDouble average() {
        return intStream().average();
    }
    
    public default IntSummaryStatistics summaryStatistics() {
        return intStream().summaryStatistics();
    }
    
    //-- Match --
    
    /** Check if any element match the predicate */
    public default boolean anyMatch(IntPredicate predicate) {
        return intStream().anyMatch(predicate);
    }
    
    /** Check if all elements match the predicate */
    public default boolean allMatch(IntPredicate predicate) {
        return intStream().allMatch(predicate);
    }
    
    /** Check if none of the elements match the predicate */
    public default boolean noneMatch(IntPredicate predicate) {
        return intStream().noneMatch(predicate);
    }
    
    /** Returns the sequentially first element */
    public default OptionalInt findFirst() {
        return intStream().findFirst();
    }
    
    /** Returns the any element */
    public default OptionalInt findAny() {
        return intStream().findAny();
    }
    
    //== Conversion ==
    
    /** Convert this streamable to an array. */
    public default int[] toArray() {
        return intStream()
                .toArray();
    }
    
}
