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
import functionalj.list.FuncList;
import functionalj.list.intlist.DoubleFuncListWithMapFirst;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleIterable;
import functionalj.stream.doublestream.DoubleIteratorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.doublestreamable.AsDoubleStreamable;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.IntDoubleTuple;
import lombok.val;
import nullablej.nullable.Nullable;


// TODO - Use this for byte, short and char

public interface DoubleFuncList
        extends
            AsDoubleStreamable,
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
    
    /** Create a FuncList from the given streamable. */
    public static DoubleFuncList from(AsDoubleStreamable streamable) {
        if (streamable instanceof DoubleFuncList)
            return (DoubleFuncList)streamable;
        
        return new DoubleFuncListDerivedFromDoubleStreamable(streamable);
    }
    
    /** Create a FuncList from the given stream. */
    public static DoubleFuncList from(DoubleStream stream) {
        return ImmutableDoubleFuncList.from(stream);
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively bu the given step. */
    public static DoubleFuncList rangeStep(double startInclusive, double step) {
        return DoubleStreamable
                .iterate(startInclusive, d -> d + step)
                .toFuncList();
    }
    
    /** Returns the infinite streams of zeroes. */
    public static DoubleFuncList zeroes(int count) {
        return DoubleFuncList.from(DoubleStreamable.zeroes(count));
    }
    
    /** Returns the streams of ones. */
    public static DoubleFuncList ones(int count) {
        return DoubleFuncList.from(DoubleStreamable.ones(count));
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
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1,
            DoubleFuncList list2) {
        return FuncList.from(DoubleStreamable.zipOf(list1, list2));
    }
    
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1,
            DoubleFuncList list2,
            double         defaultValue) {
        return FuncList.from(DoubleStreamable.zipOf(list1, list2, defaultValue));
    }
    
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1, double defaultValue1,
            DoubleFuncList list2, double defaultValue2) {
        return FuncList.from(DoubleStreamable.zipOf(list1, defaultValue1, list2, defaultValue2));
    }
    
    /** Zip integers from two IntStreamables and combine it into another object. */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1,
            DoubleFuncList            list2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleStreamable.zipOf(list1, list2, merger));
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1,
            DoubleFuncList            list2,
            double                    defaultValue,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleStreamable.zipOf(list1, list2, defaultValue, merger));
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1, double defaultValue1,
            DoubleFuncList            list2, double defaultValue2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(DoubleStreamable.zipOf(list1, defaultValue1, list2, defaultValue2, merger));
    }
    
    //== Core ==
    
    /** Return the this as a streamable. */
    public DoubleStreamable doubleStreamable();
    
    /** Return the stream of data behind this IntStreamable. */
    public default DoubleStreamPlus doubleStream() {
        return doubleStreamable().doubleStream();
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE> DoubleFuncList deriveFrom(
            AsStreamable<SOURCE>                    asStreamable,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        return DoubleFuncList.from(DoubleStreamable.deriveFrom(asStreamable, action));
    }
    
    /** Create a Streamable from the given DoubleStreamable. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsIntStreamable                       asStreamable,
            Function<IntStreamPlus, DoubleStream> action) {
        return DoubleFuncList.from(DoubleStreamable.deriveFrom(asStreamable, action));
    }
    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> IntFuncList deriveFrom(
//            LongFuncList                        list,
//            Function<LongStreamPlus, IntStream> action) {
//        return () -> {
//            val sourceStream = asStreamable.longStream();
//            val targetStream = action.apply(sourceStream);
//            return IntStreamPlus.from(targetStream);
//        };
//    }
    
    /** Create a Streamable from the given DoubleStreamable. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleFuncList.from(DoubleStreamable.deriveFrom(asStreamable, action));
    }
    
    /** Create a Streamable from another streamable. */
    public static IntFuncList deriveToInt(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, IntStream> action) {
        return IntFuncList.from(IntStreamable.deriveFrom(asStreamable, action));
    }
    
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> LongStreamable deriveToLong(
//            AsIntStreamable                     asStreamable,
//            Function<IntStreamPlus, LongStream> action) {
//        return LongStreamable.deriveFrom(asStreamable, action);
//    }
    
    /** Create a Streamable from another streamable. */
    public static DoubleFuncList deriveToDouble(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleFuncList.from(DoubleStreamable.deriveFrom(asStreamable, action));
    }
    
    /** Create a Streamable from another streamable. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsDoubleStreamable                         asStreamable,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return FuncList.from(Streamable.deriveFrom(asStreamable, action));
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
    public DoubleFuncList lazy();
    
    /** Return a eager list with the data of this list. */
    public DoubleFuncList eager();
    
    public default DoubleFuncList toFuncList() {
        return this;
    }
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableDoubleFuncList freeze() {
        return toImmutableList();
    }
    
    /** Create a Streamable from another streamable. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsDoubleFuncList                           list,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return FuncList.from(DoubleStreamable.deriveToObj(list, action));
    }
    
    /** Returns stream of Double from the value of this list. */
    public default FuncList<Double> boxed() {
        return FuncList.from(doubleStream().mapToObj(d -> d));
    }
    
    //-- Iterator --
    
    /** @return the iterable of this streamable. */
    public default DoubleIterable iterable() {
        return () -> iterator();
    }
    
    /** @return a iterator of this streamable. */
    @Override
    public default DoubleIteratorPlus iterator() {
        return () -> iterator();
    }
    
    /** @return a spliterator of this streamable. */
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
    
    /** Map a value into a streamable and then flatten that streamable */
    public default DoubleFuncList flatMap(DoubleFunction<? extends AsDoubleStreamable> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).doubleStream()));
    }
    
    /** Map a value into an integer streamable and then flatten that streamable */
    public default DoubleFuncList flatMapToDouble(DoubleFunction<? extends AsDoubleStreamable> mapper) {
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
    
    /** Convert this streamable to an array. */
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
        val streamable = doubleStreamable()
                    .mapWithIndex((index, data) -> check.test(data) ? index : -1)
                    .mapToInt    ()
                    .filter      (i -> i != -1);
        return IntFuncList.from(streamable);
    }
    
    public default IntFuncList indexesOf(double value) {
        val streamable = doubleStreamable()
                .mapWithIndex((index, data) -> (data == value) ? index : -1)
                .mapToInt    ()
                .filter      (i -> i != -1);
        return IntFuncList.from(streamable);
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
        val streamable = limit(count).doubleStreamable();
        return from(streamable);
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
        val streamable = DoubleStreamable.concat(doubleStreamable(), DoubleStreamable.of(value));
        return from(streamable);
    }
    
    /** Add the given values to the end of the list. */
    public default DoubleFuncList appendAll(double ... values) {
        val streamable = DoubleStreamable.concat(doubleStreamable(), DoubleStreamable.of(values));
        return from(streamable);
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default DoubleFuncList appendAll(DoubleStreamable values) {
        val streamable = DoubleStreamable.concat(doubleStreamable(), values);
        return from(streamable);
    }
    
//    /** Add the given values from the supplier to the end of the list. */
//    public default IntFuncList appendAll(Supplier<IntStream> supplier) {
//        if (supplier == null) {
//            return this;
//        }
//
//        val streamable = IntStreamable.concat(streamable(), (IntStreamable)() -> supplier.get());
//        return from(streamable);
//    }
    
    /** Add the given value to the beginning of the list */
    public default DoubleFuncList prepend(int value) {
        val streamable = DoubleStreamable.concat(DoubleStreamable.of(value), doubleStreamable());
        return from(streamable);
    }
    
    /** Add the given values to the beginning of the list */
    public default DoubleFuncList prependAll(double ... values) {
        val streamable = DoubleStreamable.concat(DoubleStreamable.of(values), doubleStreamable());
        return from(streamable);
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default DoubleFuncList prependAll(DoubleStreamable prefixStreamable) {
        if (prefixStreamable == null)
            return this;
        
        val streamable = DoubleStreamable.concat(prefixStreamable, doubleStreamable());
        return from(streamable);
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
        
        val first      = doubleStreamable().limit(index);
        val tail       = doubleStreamable().skip(index);
        val streamable = DoubleStreamable.concat(first, DoubleStreamable.of(elements), tail);
        return from(streamable);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default DoubleFuncList insertAllAt(int index, AsDoubleStreamable theStreamable) {
        if (theStreamable == null)
            return this;
        
        val first  = doubleStreamable().limit(index);
        val middle = theStreamable.doubleStreamable();
        val tail   = doubleStreamable().skip(index);
        val streamable = DoubleStreamable.concat(first, middle, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the element. */
    public default DoubleFuncList exclude(int element) {
        return filter(each -> each != element);
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default DoubleFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        val first  = doubleStreamable().limit(index);
        val tail   = doubleStreamable().skip(index + 1);
        val streamable = DoubleStreamable.concat(first, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default DoubleFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        val first  = doubleStreamable().limit(fromIndexInclusive);
        val tail   = doubleStreamable().skip(fromIndexInclusive + count);
        val streamable = DoubleStreamable.concat(first, tail);
        return from(streamable);
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
        
        val first  = doubleStreamable().limit(fromIndexInclusive);
        val tail   = doubleStreamable().skip(toIndexExclusive + 1);
        return from(DoubleStreamable.concat(first, tail));
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
        val streamable
                = doubleStreamable()
                .mapToObjWithIndex((index, data) -> check.test(data) ? IntDoubleTuple.of(index, data) : null)
                .filterNonNull();
        return FuncList.from(streamable);
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
