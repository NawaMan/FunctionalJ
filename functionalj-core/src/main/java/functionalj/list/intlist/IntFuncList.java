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
package functionalj.list.intlist;

import static functionalj.lens.Access.theInteger;

import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntIterable;
import functionalj.stream.intstream.IntIteratorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamPlusHelper;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.doublestreamable.AsDoubleStreamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.IntIntTuple;
import lombok.val;
import nullablej.nullable.Nullable;

// TODO - Use this for byte, short and char

public interface IntFuncList
        extends
            AsIntStreamable,
            IntIterable,
            IntPredicate,
            IntFuncListWithCalculate,
            IntFuncListWithCombine,
            IntFuncListWithFilter,
            IntFuncListWithFlatMap,
            IntFuncListWithGroupingBy,
            IntFuncListWithLimit,
            IntFuncListWithMap,
            IntFuncListWithMapFirst,
            IntFuncListWithMapGroup,
            IntFuncListWithMapThen,
            IntFuncListWithMapToMap,
            IntFuncListWithMapToTuple,
            IntFuncListWithMapWithIndex,
            IntFuncListWithModify,
            IntFuncListWithPeek,
            IntFuncListWithPipe,
            IntFuncListWithReshape,
            IntFuncListWithSort,
            IntFuncListWithSplit,
            IntFuncListWithStatistic {
    
    /** Returns an empty IntFuncList. */
     public static ImmutableIntFuncList empty() {
         return ImmutableIntFuncList.empty();
     }
     
     /** Returns an empty IntFuncList. */
    public static ImmutableIntFuncList emptyList() {
        return ImmutableIntFuncList.empty();
    }
    
    /** Returns an empty IntFuncList. */
    public static ImmutableIntFuncList emptyIntList() {
        return ImmutableIntFuncList.empty();
    }
    
    /** Create a FuncList from the given ints. */
    public static ImmutableIntFuncList of(int ... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /** Create a FuncList from the given ints. */
    public static ImmutableIntFuncList AllOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /** Create a FuncList from the given ints. */
    public static ImmutableIntFuncList ints(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /** Create a FuncList from the given ints. */
    public static ImmutableIntFuncList intList(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /** Create a FuncList from the given ints. */
    @SafeVarargs
    public static ImmutableIntFuncList ListOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /** Create a FuncList from the given ints. */
    @SafeVarargs
    public static ImmutableIntFuncList listOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    //TODO - Function to create FuncList from function of Array
    
    /** Create a FuncList from the given ints. */
    public static ImmutableIntFuncList from(int[] datas) {
        return ImmutableIntFuncList.from(datas);
    }
    
    /** Create a FuncList from the given collection. */
    public static ImmutableIntFuncList from(Collection<Integer> data, int valueForNull) {
        IntStream intStream = StreamPlus.from(data.stream())
                .fillNull((Integer)valueForNull)
                .mapToInt(theInteger);
        return ImmutableIntFuncList.from(intStream);
    }
    
    /** Create a FuncList from the given streamable. */
    public static IntFuncList from(AsIntStreamable streamable) {
        if (streamable instanceof IntFuncList)
            return (IntFuncList)streamable;
        
        return new IntFuncListDerivedFromIntStreamable(streamable);
    }
    
    /** Create a FuncList from the given stream. */
    public static IntFuncList from(IntStream stream) {
        return ImmutableIntFuncList.from(stream);
    }
    
    /** Returns the infinite streams of zeroes. */
    public static IntFuncList zeroes(int count) {
        return IntFuncList.from(IntStreamable.zeroes(count));
    }
    
    /** Returns the streams of ones. */
    public static IntFuncList ones(int count) {
        return IntFuncList.from(IntStreamable.ones(count));
    }
    
    public static IntFuncList naturalNumbers(int count) {
        return IntFuncList.from(IntStreamable.naturalNumbers().limit(count));
    }
    
    /** Returns the infinite streams of wholes numbers -- 0, 1, 2, 3, .... */
    public static IntFuncList wholeNumbers(int count) {
        return IntFuncList.from(IntStreamable.wholeNumbers().limit(count));
    }
    
    /** Create a Streamable that for a loop with the number of time given - the value is the index of the loop. */
    public static IntFuncList range(int startInclusive, int endExclusive) {
        return IntFuncList.from(IntStreamable.range(startInclusive, endExclusive));
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
    public static FuncList<IntIntTuple> zipOf(
            IntFuncList list1,
            IntFuncList list2) {
        return FuncList.from(IntStreamable.zipOf(list1, list2));
    }
    
    public static FuncList<IntIntTuple> zipOf(
            IntFuncList list1,
            IntFuncList list2,
            int         defaultValue) {
        return FuncList.from(IntStreamable.zipOf(list1, list2, defaultValue));
    }
    
    public static FuncList<IntIntTuple> zipOf(
            IntFuncList list1,
            int         defaultValue1,
            IntFuncList list2,
            int         defaultValue2) {
        return FuncList.from(IntStreamable.zipOf(list1, defaultValue1, list2, defaultValue2));
    }
    
    /** Zip integers from two IntStreamables and combine it into another object. */
    public static IntFuncList zipOf(
            IntFuncList            list1,
            IntFuncList            list2,
            IntBiFunctionPrimitive merger) {
        return IntFuncList.from(IntStreamable.zipOf(list1, list2, merger));
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static IntFuncList zipOf(
            IntFuncList            list1,
            IntFuncList            list2,
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return IntFuncList.from(IntStreamable.zipOf(list1, list2, defaultValue, merger));
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static IntFuncList zipOf(
            IntFuncList            list1,
            int                    defaultValue1,
            IntFuncList            list2,
            int                    defaultValue2,
            IntBiFunctionPrimitive merger) {
        return IntFuncList.from(IntStreamable.zipOf(list1, defaultValue1, list2, defaultValue2, merger));
    }
    
    //== Core ==
    
    /** Return the this as a streamable. */
    public IntStreamable intStreamable();
    
    /** Return the stream of data behind this IntStreamable. */
    public default IntStreamPlus intStream() {
        return intStreamable().intStream();
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE> IntFuncList deriveFrom(
            AsStreamable<SOURCE>                    asStreamable,
            Function<StreamPlus<SOURCE>, IntStream> action) {
        return IntFuncList.from(IntStreamable.deriveFrom(asStreamable, action));
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> IntFuncList deriveFrom(
            AsIntStreamable                    asStreamable,
            Function<IntStreamPlus, IntStream> action) {
        return IntFuncList.from(IntStreamable.deriveFrom(asStreamable, action));
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
    public static <TARGET> IntFuncList deriveFrom(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, IntStream> action) {
        return IntFuncList.from(IntStreamable.deriveFrom(asStreamable, action));
    }
    
    /** Create a Streamable from another streamable. */
    public static IntFuncList deriveToInt(
            AsIntStreamable                    asStreamable,
            Function<IntStreamPlus, IntStream> action) {
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
            AsIntStreamable                       asStreamable,
            Function<IntStreamPlus, DoubleStream> action) {
        return DoubleFuncList.deriveFrom(asStreamable, action);
    }
    
    /** Create a Streamable from another streamable. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsIntStreamable                         asStreamable,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return FuncList.from(Streamable.deriveFrom(asStreamable, action));
    }
    
    /** Test if the data is in the list */
    @Override
    public default boolean test(int value) {
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
    public IntFuncList lazy();
    
    /** Return a eager list with the data of this list. */
    public IntFuncList eager();
    
    public default IntFuncList toFuncList() {
        return this;
    }
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableIntFuncList freeze() {
        return toImmutableList();
    }
    
    /** Create a Streamable from another streamable. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsIntFuncList                           list,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return FuncList.from(IntStreamable.deriveToObj(list, action));
    }
    
    /** Returns stream of Integer from the value of this list. */
    public default Streamable<Integer> boxed() {
        return ()->StreamPlus.from(intStream().mapToObj(theInteger.boxed()));
    }
    
    /** Returns the list value in this stream as int aka itself */
    public default IntFuncList asIntFuncList() {
        return this;
    }
    
//    /** Returns the streamable value in this stream as long */
//    public default LongFuncList asLongFuncList() {
//        return deriveToLong(this, stream -> stream.mapToLong(i -> (long)i));
//    }
    
    /** Returns the streamable value in this stream as double */
    public default DoubleFuncList asDoubleFuncList() {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(i -> (double)i));
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
    public default IntFuncList map(IntUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.intStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(IntUnaryOperator mapper) {
        return deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
//    /** Map each value into a long value using the function. */
//    public default LongFuncList mapToLong(IntToLongFunction mapper) {
//        return deriveToLong(this, stream -> stream.mapToLong(mapper));
//    }
    
    /** Map each value into a double value using the function. */
    public default DoubleFuncList mapToDouble() {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(i -> i));
    }
    
    /** Map each value into a double value using the function. */
    public default DoubleFuncList mapToDouble(IntToDoubleFunction mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a streamable and then flatten that streamable */
    public default IntFuncList flatMap(IntFunction<? extends AsIntStreamable> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
    
    /** Map a value into an integer streamable and then flatten that streamable */
    public default IntFuncList flatMapToInt(IntFunction<? extends AsIntStreamable> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
    
//    /** Map a value into a long streamable and then flatten that streamable */
//    public default LongFuncList flatMapToLong(IntFunction<? extends AsLongStreamable> mapper) {
//        return LongFuncList.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
//    }
    
    /** Map a value into a double streamable and then flatten that streamable */
    public default DoubleFuncList flatMapToDouble(IntFunction<? extends AsDoubleStreamable> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default IntFuncList filter(IntPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default IntFuncList filter(
            IntUnaryOperator mapper,
            IntPredicate     predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default IntFuncList peek(IntConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default IntFuncList limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    
    public default IntFuncList skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default IntFuncList distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default IntFuncList sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default IntFuncList sorted(
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
    
    //== Nullable, Optional and Result
    
    public default Nullable<IntFuncList> __nullable() {
        return Nullable.of(this);
    }
    
    public default Optional<IntFuncList> __optional() {
        return Optional.of(this);
    }
    
    public default Result<IntFuncList> __result() {
        return Result.valueOf(this);
    }
    
    // -- List specific --
    
    public default IntFuncList indexesOf(IntPredicate check) {
        val streamable = intStreamable()
                    .mapWithIndex((index, data) -> check.test(data) ? index : -1)
                    .filter(i -> i != -1);
        return from(streamable);
    }
    
    public default IntFuncList indexesOf(int value) {
        val streamable = intStreamable()
                .mapWithIndex((index, data) -> (data == value) ? index : -1)
                .filter(i -> i != -1);
        return from(streamable);
    }
    
    /** Returns the first element. */
    public default OptionalInt first() {
        return intStream()
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the first elements */
    public default IntFuncList first(int count) {
        val streamable = limit(count).intStreamable();
        return from(streamable);
    }
    
    /** Returns the last element. */
    public default OptionalInt last() {
        return last(1)
                .findFirst()
                ;
    }
    
    /** Returns the last elements */
    public default IntFuncList last(int count) {
        val size   = this.size();
        val offset = Math.max(0, size - count);
        return skip(offset);
    }
    
    /** Returns the element at the index. */
    public default OptionalInt at(int index){
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the second to the last elements. */
    public default IntFuncList rest() {
        return skip(1);
    }
    
    /** Add the given value to the end of the list. */
    public default IntFuncList append(int value) {
        val streamable = IntStreamable.concat(intStreamable(), IntStreamable.of(value));
        return from(streamable);
    }
    
    /** Add the given values to the end of the list. */
    public default IntFuncList appendAll(int ... values) {
        val streamable = IntStreamable.concat(intStreamable(), IntStreamable.of(values));
        return from(streamable);
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default IntFuncList appendAll(IntStreamable values) {
        val streamable = IntStreamable.concat(intStreamable(), values);
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
    public default IntFuncList prepend(int value) {
        val streamable = IntStreamable.concat(IntStreamable.of(value), intStreamable());
        return from(streamable);
    }
    
    /** Add the given values to the beginning of the list */
    public default IntFuncList prependAll(int ... values) {
        val streamable = IntStreamable.concat(IntStreamable.of(values), intStreamable());
        return from(streamable);
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default IntFuncList prependAll(IntStreamable prefixStreamable) {
        if (prefixStreamable == null)
            return this;
        
        val streamable = IntStreamable.concat(prefixStreamable, intStreamable());
        return from(streamable);
    }
    
//    /** Add the given values from the supplier to the beginning of the list. */
//    public default IntFuncList prependAll(Supplier<IntStream> supplier) {
//        return (supplier == null)
//                ? this
//                : derive(() -> IntStreamPlus.concat(IntStreamPlus.from(supplier.get()), intStream()));
//    }
    
    /** Returns a new functional list with the value replacing at the index. */
    public default IntFuncList with(int index, int value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return deriveToInt(this, stream -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? value : each)
                    .intStream();
        });
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default IntFuncList with(int index, IntUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return deriveToInt(this, stream -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? mapper.applyAsInt(each) : each)
                    .intStream();
        });
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default IntFuncList insertAt(int index, int ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        val first      = intStreamable().limit(index);
        val tail       = intStreamable().skip(index);
        val streamable = IntStreamable.concat(first, IntStreamable.of(elements), tail);
        return from(streamable);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default IntFuncList insertAllAt(int index, AsIntStreamable theStreamable) {
        if (theStreamable == null)
            return this;
        
        val first  = intStreamable().limit(index);
        val middle = theStreamable.intStreamable();
        val tail   = intStreamable().skip(index);
        val streamable = IntStreamable.concat(first, middle, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the element. */
    public default IntFuncList exclude(int element) {
        return filter(each -> each != element);
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default IntFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        val first  = intStreamable().limit(index);
        val tail   = intStreamable().skip(index + 1);
        val streamable = IntStreamable.concat(first, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default IntFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        val first  = intStreamable().limit(fromIndexInclusive);
        val tail   = intStreamable().skip(fromIndexInclusive + count);
        val streamable = IntStreamable.concat(first, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`. */
    public default IntFuncList excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException(
                    "fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        val first  = intStreamable().limit(fromIndexInclusive);
        val tail   = intStreamable().skip(toIndexExclusive + 1);
        return from(IntStreamable.concat(first, tail));
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    public default IntFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive)
                .limit(length);
    }
    
    /** Returns the new list with reverse order of this list. */
    //Note - Eager
    public default IntFuncList reverse() {
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
        return new ImmutableIntFuncList(array, isLazy());
    }
    
    /** Returns the new list with random order of this list. */
    //Note - Eager
    public default IntFuncList shuffle() {
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
        return new ImmutableIntFuncList(array, isLazy());
    }
    
    public default FuncList<IntIntTuple> query(IntPredicate check) {
        val streamable
                = intStreamable()
                .mapToObjWithIndex((index, data) -> check.test(data) ? IntIntTuple.of(index, data) : null)
                .filterNonNull();
        return FuncList.from(streamable);
    }
    
    public default boolean isEmpty() {
        return ! iterator().hasNext();
    }
    
    public default boolean contains(int value) {
        return intStream().anyMatch(i -> i == value);
    }
    
    public default boolean containsAllOf(int ... array) {
        return IntStreamPlus
                .of(array)
                .allMatch(each -> intStream()
                                    .anyMatch(value -> Objects.equals(each, value)));
    }
    
    public default boolean containsSomeOf(int ... c) {
        return IntStreamPlus
                .of(c)
                .anyMatch(each -> intStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Integer> c) {
        return c.stream()
                .allMatch(each -> intStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Integer> c) {
        return c.stream()
                .anyMatch(each -> intStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default int get(int index) {
        val ref   = new int[1][];
        val found = IntStreamPlusHelper.hasAt(this.intStream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref[0][0];
    }
    
    public default int indexOf(int o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(int o){
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
    }
    
    public default int size() {
        return (int) intStream().count();
    }
    
}
