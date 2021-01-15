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
package functionalj.list.doublelist;

import static functionalj.lens.Access.theDouble;

import java.util.Arrays;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
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
import functionalj.list.AsFuncList;
import functionalj.list.FuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.intlist.DoubleFuncListWithMapFirst;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleIterable;
import functionalj.stream.doublestream.DoubleIteratorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlusHelper;
import functionalj.stream.doublestream.DoubleSupplierBackedIterator;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.IntDoubleTuple;
import lombok.val;
import nullablej.nullable.Nullable;


@FunctionalInterface
public interface DoubleFuncList
        extends
            AsDoubleFuncList,
            DoubleIterable,
            DoublePredicate,
            DoubleFuncListWithCalculate,
            DoubleFuncListWithCombine,
            DoubleFuncListWithFilter,
            DoubleFuncListWithFlatMap,
            DoubleFuncListWithLimit,
            DoubleFuncListWithMap,
            DoubleFuncListWithMapFirst,
            DoubleFuncListWithMapThen,
            DoubleFuncListWithMapToMap,
            DoubleFuncListWithMapToTuple,
            DoubleFuncListWithMapWithIndex,
            DoubleFuncListWithMapGroup,
            DoubleFuncListWithModify,
            DoubleFuncListWithPeek,
            DoubleFuncListWithPipe,
            DoubleFuncListWithReshape,
            DoubleFuncListWithSort,
            DoubleFuncListWithSplit,
            DoubleFuncListWithStatistic {
    
    /** Returns an empty IntFuncList. */
     public static ImmutableDoubleFuncList empty() {
         return ImmutableDoubleFuncList.empty();
     }
     
     /** Returns an empty DoubleFuncList. */
    public static ImmutableDoubleFuncList emptyList() {
        return ImmutableDoubleFuncList.empty();
    }
    
    /** Returns an empty DoubleFuncList. */
    public static ImmutableDoubleFuncList emptyDoubleList() {
        return ImmutableDoubleFuncList.empty();
    }
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList of(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList AllOf(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList doubles(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList doubleList(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    /** Create a FuncList from the given doubles. */
    @SafeVarargs
    public static ImmutableDoubleFuncList ListOf(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    /** Create a FuncList from the given doubles. */
    @SafeVarargs
    public static ImmutableDoubleFuncList listOf(double ... data) {
        return ImmutableDoubleFuncList.of(data);
    }
    
    //TODO - Function to create FuncList from function of Array
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList from(double[] datas) {
        return ImmutableDoubleFuncList.from(datas);
    }
    
    /** Create a FuncList from the given collection. */
    public static ImmutableDoubleFuncList from(Collection<Double> data, double valueForNull) {
        DoubleStream doubleStream = StreamPlus.from(data.stream())
                .fillNull   ((Double)valueForNull)
                .mapToDouble(theDouble);
        return ImmutableDoubleFuncList.from(doubleStream);
    }
    
    /** Create a FuncList from the given FuncList. */
    public static DoubleFuncList from(AsDoubleFuncList FuncList) {
        if (FuncList instanceof DoubleFuncList)
            return (DoubleFuncList)FuncList;
        
        return new DoubleFuncListDerivedFromDoubleFuncList(FuncList);
    }
    
    /** Create a FuncList from the given stream. */
    public static DoubleFuncList from(DoubleStream stream) {
        return ImmutableDoubleFuncList.from(stream);
    }
    
    /** Returns the infinite streams of zeroes. */
    public static DoubleFuncList zeroes() {
        return zeroes(Integer.MAX_VALUE);
    }
    
    /** Returns the infinite streams of zeroes. */
    public static DoubleFuncList zeroes(int count) {
//        return DoubleFuncList.from(() -> DoubleStreamPlus.zeroes(count));
        return null;
    }
    
    /** Returns the streams of ones. */
    public static DoubleFuncList ones() {
        return ones(Integer.MAX_VALUE);
    }
    
    /** Returns the streams of ones. */
    public static DoubleFuncList ones(int count) {
//        return DoubleFuncList.from(DoubleFuncList.ones(count));
        return null;
    }
    
//    public static <T> FuncListBuilder<T> newFuncList() {
//        return new FuncListBuilder<T>();
//    }
//
//    public static <T> FuncListBuilder<T> newList() {
//        return new FuncListBuilder<T>();
//    }
//
//    public static <T> FuncListBuilder<T> newBuilder() {
//        return new FuncListBuilder<T>();
//    }


    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static DoubleFuncList repeat(double ... data) {
        return cycle(data);
    }

    /** Create a StreamPlus that is the repeat of the given array of data. */
    public static DoubleFuncList cycle(double ... data) {
        val doubles = Arrays.copyOf(data, data.length);
        val size    = doubles.length;
        return DoubleFuncList.from(
                IntStream
                .range(0, Integer.MAX_VALUE)
                .mapToDouble(i -> data[i % size]));
    }
    
    public static DoubleFuncList naturalNumbers() {
        return DoubleFuncList.from(
                IntStream
                .range(1, Integer.MAX_VALUE)
                .mapToDouble(i -> 1.0*i));
    }
    
    public static DoubleFuncList naturalNumbers(int count) {
        return DoubleFuncList.from(
                IntStream
                .range(1, count + 1)
                .mapToDouble(i -> 1.0*i));
    }
    
    public static DoubleFuncList wholeNumbers() {
        return DoubleFuncList.from(
                IntStream
                .range(0, Integer.MAX_VALUE)
                .mapToDouble(i -> 1.0*i));
    }
    
    public static DoubleFuncList wholeNumbers(int count) {
        return DoubleFuncList.from(
                IntStream
                .range(0, count + 1)
                .mapToDouble(i -> 1.0*i));
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively bu the given step. */
    public static DoubleFuncList rangeStep(double startInclusive, double step) {
        return wholeNumbers().map(d -> d * step + startInclusive);
    }
    
    /** Concatenate all the given streams. */
    public static DoubleFuncList concat(AsDoubleFuncList ... doubleFuncList) {
//        return StreamPlus
//                .of             (streams)
//                .map            (s -> DoubleStreamPlus.from(s))
//                .flatMapToDouble(s -> s.doubleStream());
        return null;
    }
    
    /** Concatenate all the given streams. */
    public static DoubleFuncList combine(AsDoubleFuncList ... streams) {
        return concat(streams);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleFuncList generate(DoubleSupplier supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleFuncList generateWith(DoubleSupplier supplier) {
        val iterable = (DoubleIterable)() -> new DoubleSupplierBackedIterator(supplier);
//        return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
        return null;
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
    public static DoubleFuncList iterate(double seed, DoubleUnaryOperator f) {
//        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
        return null;
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
    public static DoubleFuncList compound(double seed, DoubleUnaryOperator f) {
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
    public static DoubleFuncList iterate(double seed1, double seed2, DoubleBinaryOperator f) {
//        val counter = new AtomicInteger(0);
//        val value1  = new double[] { seed1 };
//        val value2  = new double[] { seed2 };
//        return () -> DoubleFuncList.iterate(seed1, seed2, f);
        return null;
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
    public static DoubleFuncList compound(Double seed1, Double seed2, DoubleBinaryOperator f) {
        return iterate(seed1, seed2, f);
    }
    
    //-- Zip --
    
    /**
     * Create a FuncList by combining elements together into a FuncList of tuples.
     * Only elements with pair will be combined. If this is not desirable, use FuncList1.zip(FuncList2).
     *
     * For example:
     *     FuncList1 = [A, B, C, D, E]
     *     FuncList2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1,
            DoubleFuncList list2) {
//        return FuncList.from(DoubleFuncList.zipOf(list1, list2));
        return null;
    }
    
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1,
            DoubleFuncList list2,
            double         defaultValue) {
//        return FuncList.from(DoubleFuncList.zipOf(list1, list2, defaultValue));
        return null;
    }
    
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1, double defaultValue1,
            DoubleFuncList list2, double defaultValue2) {
//        return FuncList.from(DoubleFuncList.zipOf(list1, defaultValue1, list2, defaultValue2));
        return null;
    }
    
    /** Zip integers from two IntFuncLists and combine it into another object. */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1,
            DoubleFuncList            list2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleFuncList.zipOf(list1, list2, merger));
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1,
            DoubleFuncList            list2,
            double                    defaultValue,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleFuncList.zipOf(list1, list2, defaultValue, merger));
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1, double defaultValue1,
            DoubleFuncList            list2, double defaultValue2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleFuncList.zipOf(list1, defaultValue1, list2, defaultValue2, merger));
    }
    
    //== Core ==
    
    /** Return the stream of data behind this IntFuncList. */
    public DoubleStreamPlus doubleStream();
    
    /** Return the this as a FuncList. */
    public default DoubleFuncList asDoubleFuncList() {
        return this;
    }
    
    
    //-- Derive --
    
    /** Create a FuncList from the given FuncList. */
    public static <SOURCE> DoubleFuncList deriveFrom(
            AsFuncList<SOURCE>                         funcList,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
//        return DoubleFuncList.from(DoubleFuncList.deriveFrom(FuncList, action));
        return null;
    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsIntFuncList                         funcList,
            Function<IntStreamPlus, DoubleStream> action) {
//        return DoubleFuncList.from(DoubleFuncList.deriveFrom(FuncList, action));
        return null;
    }
    
//    /** Create a FuncList from the given LongFuncList. */
//    public static <TARGET> IntFuncList deriveFrom(
//            LongFuncList                        list,
//            Function<LongStreamPlus, IntStream> action) {
//        return () -> {
//            val sourceStream = FuncList.longStream();
//            val targetStream = action.apply(sourceStream);
//            return IntStreamPlus.from(targetStream);
//        };
//    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsDoubleFuncList                       FuncList,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleFuncList.from(DoubleFuncList.deriveFrom(FuncList, action));
    }
    
    /** Create a FuncList from another FuncList. */
    public static IntFuncList deriveToInt(
            AsDoubleFuncList                       FuncList,
            Function<DoubleStreamPlus, IntStream> action) {
        return IntFuncList.from(IntFuncList.deriveFrom(FuncList, action));
    }
    
//    /** Create a FuncList from another FuncList. */
//    public static <SOURCE> LongFuncList deriveToLong(
//            AsIntFuncList                     FuncList,
//            Function<IntStreamPlus, LongStream> action) {
//        return LongFuncList.deriveFrom(FuncList, action);
//    }
    
    /** Create a FuncList from another FuncList. */
    public static DoubleFuncList deriveToDouble(
            AsDoubleFuncList                         funcList,
            Function<DoubleStreamPlus, DoubleStream> action) {
//        return DoubleFuncList.from(DoubleFuncList.deriveFrom(FuncList, action));
        return null;
    }
    
    /** Create a FuncList from another FuncList. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsDoubleFuncList                           funcList,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
//        return FuncList.from(FuncList.deriveFrom(FuncList, action));
        return null;
    }
    
    /** Test if the data is in the list */
    @Override
    public default boolean test(double value) {
        return contains(value);
    }
    
    /** Check if this list is a lazy list. */
    public default boolean isLazy() {
        return true;
    }
    
    /** Check if this list is an eager list. */
    public default boolean isEager() {
        return false;
    }
    
    /** Return a lazy list with the data of this list. */
    public default DoubleFuncList lazy() {
        if (isLazy())
            return this;
        
        return (DoubleFuncList)() -> this.doubleStream();
    }
    
    /** Return a eager list with the data of this list. */
    public default DoubleFuncList eager() {
        if (isEager())
            return this;
        
        return ImmutableDoubleFuncList.from(this);
    }
    
    public default DoubleFuncList toFuncList() {
        return this;
    }
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableDoubleFuncList freeze() {
        return toImmutableList();
    }
    
    /** Returns stream of Double from the value of this list. */
    public default FuncList<Double> boxed() {
        return FuncList.from(doubleStream().mapToObj(d -> d));
    }
    
    //-- Iterator --
    
    /** @return the iterable of this FuncList. */
    public default DoubleIterable iterable() {
        return () -> iterator();
    }
    
    /** @return a iterator of this FuncList. */
    @Override
    public default DoubleIteratorPlus iterator() {
        return () -> iterator();
    }
    
    /** @return a spliterator of this FuncList. */
    public default Spliterator.OfDouble spliterator() {
        return doubleStream().spliterator();
    }
    
    //-- Map --
    
    /** Map each value into other value using the function. */
    public default DoubleFuncList map(DoubleUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.doubleStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(DoubleToIntFunction mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt() {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToInt());
    }
    
    /** Map each value into a double value using the function. */
    public default DoubleFuncList mapToDouble(DoubleUnaryOperator mapper) {
        return deriveToDouble(this, stream -> stream.mapToDouble(mapper));
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(DoubleFunction<? extends TARGET> mapper) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a FuncList and then flatten that FuncList */
    public default DoubleFuncList flatMap(DoubleFunction<? extends AsDoubleFuncList> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).doubleStream()));
    }
    
    /** Map a value into an integer FuncList and then flatten that FuncList */
    public default DoubleFuncList flatMapToDouble(DoubleFunction<? extends AsDoubleFuncList> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).doubleStream()));
    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default DoubleFuncList filter(DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default DoubleFuncList filter(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default DoubleFuncList peek(DoubleConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default DoubleFuncList limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    
    public default DoubleFuncList skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default DoubleFuncList distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default DoubleFuncList sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default DoubleFuncList sorted(
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
    
    /** Convert this FuncList to an array. */
    public default double[] toArray() {
        return doubleStream()
                .toArray();
    }
    
    //== Nullable, Optional and Result
    
    public default Nullable<DoubleFuncList> __nullable() {
        return Nullable.of(this);
    }
    
    public default Optional<DoubleFuncList> __optional() {
        return Optional.of(this);
    }
    
    public default Result<DoubleFuncList> __result() {
        return Result.valueOf(this);
    }
    
    // -- List specific --
    
    public default IntFuncList indexesOf(DoublePredicate check) {
        val FuncList = mapWithIndex((index, data) -> check.test(data) ? index : -1)
                    .mapToInt    ()
                    .filter      (i -> i != -1);
        return IntFuncList.from(FuncList);
    }
    
    public default IntFuncList indexesOf(double value) {
        val FuncList = mapWithIndex((index, data) -> (data == value) ? index : -1)
                .mapToInt    ()
                .filter      (i -> i != -1);
        return IntFuncList.from(FuncList);
    }
    
    /** Returns the first element. */
    public default OptionalDouble first() {
        return doubleStream()
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the first elements */
    public default DoubleFuncList first(int count) {
        return limit(count);
    }
    
    /** Returns the last element. */
    public default OptionalDouble last() {
        return last(1)
                .findFirst()
                ;
    }
    
    /** Returns the last elements */
    public default DoubleFuncList last(int count) {
        val size   = this.size();
        val offset = Math.max(0, size - count);
        return skip(offset);
    }
    
    /** Returns the element at the index. */
    public default OptionalDouble at(int index){
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the second to the last elements. */
    public default DoubleFuncList rest() {
        return skip(1);
    }
    
    /** Add the given value to the end of the list. */
    public default DoubleFuncList append(double value) {
        return DoubleFuncList.concat(this, DoubleFuncList.of(value));
    }
    
    /** Add the given values to the end of the list. */
    public default DoubleFuncList appendAll(double ... values) {
        val FuncList = DoubleFuncList.concat(this, DoubleFuncList.of(values));
        return from(FuncList);
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default DoubleFuncList appendAll(DoubleFuncList values) {
        val FuncList = DoubleFuncList.concat(this, values);
        return from(FuncList);
    }
    
//    /** Add the given values from the supplier to the end of the list. */
//    public default IntFuncList appendAll(Supplier<IntStream> supplier) {
//        if (supplier == null) {
//            return this;
//        }
//
//        val FuncList = IntFuncList.concat(FuncList(), (IntFuncList)() -> supplier.get());
//        return from(FuncList);
//    }
    
    /** Add the given value to the beginning of the list */
    public default DoubleFuncList prepend(int value) {
        val FuncList = DoubleFuncList.concat(DoubleFuncList.of(value), this);
        return from(FuncList);
    }
    
    /** Add the given values to the beginning of the list */
    public default DoubleFuncList prependAll(double ... values) {
        val FuncList = DoubleFuncList.concat(DoubleFuncList.of(values), this);
        return from(FuncList);
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default DoubleFuncList prependAll(DoubleFuncList prefixFuncList) {
        if (prefixFuncList == null)
            return this;
        
        val FuncList = DoubleFuncList.concat(prefixFuncList, this);
        return from(FuncList);
    }
    
//    /** Add the given values from the supplier to the beginning of the list. */
//    public default IntFuncList prependAll(Supplier<IntStream> supplier) {
//        return (supplier == null)
//                ? this
//                : derive(() -> IntStreamPlus.concat(IntStreamPlus.from(supplier.get()), intStream()));
//    }
    
    /** Returns a new functional list with the value replacing at the index. */
    public default DoubleFuncList with(int index, double value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return deriveToDouble(this, stream -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? value : each)
                    .doubleStream();
        });
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default DoubleFuncList with(int index, DoubleUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return deriveToDouble(this, stream -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? mapper.applyAsDouble(each) : each)
                    .doubleStream();
        });
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default DoubleFuncList insertAt(int index, double ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        val first = limit(index);
        val tail  = skip(index);
        return DoubleFuncList.concat(first, DoubleFuncList.of(elements), tail);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default DoubleFuncList insertAllAt(int index, AsDoubleFuncList theFuncList) {
        if (theFuncList == null)
            return this;
        
        val first  = limit(index);
        val middle = theFuncList;
        val tail   = skip(index);
        return DoubleFuncList.concat(first, middle, tail);
    }
    
    /** Returns the new list from this list without the element. */
    public default DoubleFuncList exclude(int element) {
        return filter(each -> each != element);
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default DoubleFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        val first = limit(index);
        val tail  = skip(index + 1);
        return DoubleFuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default DoubleFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        val first = limit(fromIndexInclusive);
        val tail  = skip(fromIndexInclusive + count);
        return DoubleFuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`. */
    public default DoubleFuncList excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException(
                    "fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        val first  = limit(fromIndexInclusive);
        val tail   = skip(toIndexExclusive + 1);
        return DoubleFuncList.concat(first, tail);
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    public default DoubleFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive)
                .limit(length);
    }
    
    /** Returns the new list with reverse order of this list. */
    //Note - Eager
    public default DoubleFuncList reverse() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val mid = length / 2;
        for (int i = 0; i < mid; i++) {
            val j = length - i - 1;
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableDoubleFuncList(array, isLazy());
    }
    
    /** Returns the new list with random order of this list. */
    //Note - Eager
    public default DoubleFuncList shuffle() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val rand  = new Random();
        for (int i = 0; i < length; i++) {
            val j = rand.nextInt(length);
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableDoubleFuncList(array, isLazy());
    }
    
    public default FuncList<IntDoubleTuple> query(DoublePredicate check) {
        return mapToObjWithIndex((index, data) -> check.test(data) ? IntDoubleTuple.of(index, data) : null)
                .filterNonNull();
    }
    
    public default boolean isEmpty() {
        return ! iterator().hasNext();
    }
    
    public default boolean contains(double value) {
        return doubleStream().anyMatch(i -> i == value);
    }
    
    public default boolean containsAllOf(double ... array) {
        return DoubleStreamPlus
                .of(array)
                .allMatch(each -> doubleStream()
                                    .anyMatch(value -> Objects.equals(each, value)));
    }
    
    public default boolean containsSomeOf(double ... c) {
        return DoubleStreamPlus
                .of(c)
                .anyMatch(each -> doubleStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Double> c) {
        return c.stream()
                .allMatch(each -> doubleStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Double> c) {
        return c.stream()
                .anyMatch(each -> doubleStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default double get(int index) {
        val ref   = new double[1][];
        val found = DoubleStreamPlusHelper.hasAt(this.doubleStream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref[0][0];
    }
    
    public default int indexOf(double o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(double o){
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
    }
    
    public default int size() {
        return (int) doubleStream().count();
    }
    
}
