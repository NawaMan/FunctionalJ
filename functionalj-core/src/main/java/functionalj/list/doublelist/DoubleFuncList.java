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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func;
import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.doublestream.DoubleIterable;
import functionalj.stream.doublestream.DoubleIteratorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.IntDoubleTuple;
import lombok.val;
import nullablej.nullable.Nullable;


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
            DoubleFuncListWithSegment,
            DoubleFuncListWithSort,
            DoubleFuncListWithSplit,
            DoubleFuncListWithStatistic {
    
    
    /** Throw a no more element exception. This is used for generator. */
    public static double noMoreElement() throws NoMoreResultException {
        SupplierBackedIterator.noMoreElement();
        return Double.NaN;
    }
    
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
    
    /** Create a FuncList from the given doubles. */
    public static ImmutableDoubleFuncList from(double[] datas) {
        return ImmutableDoubleFuncList.of(datas);
    }
    
    /** Create a FuncList from the given collection. */
    public static ImmutableDoubleFuncList from(Collection<Double> data, double valueForNull) {
        DoubleStream doubleStream = StreamPlus.from(data.stream())
                .fillNull   ((Double)valueForNull)
                .mapToDouble(theDouble);
        return ImmutableDoubleFuncList.from(doubleStream);
    }
    
    /** Create a FuncList from the given FuncList. */
    public static DoubleFuncList from(boolean isLazy, AsDoubleFuncList asFuncList) {
        val funcList = asFuncList.asDoubleFuncList();
        return isLazy ? funcList.lazy() :funcList.eager();
    }
    
    /** Create a FuncList from the given stream. */
    public static DoubleFuncList from(DoubleStream stream) {
        return ImmutableDoubleFuncList.from(stream);
    }
    
    /**
     * Create a FuncList from the given supplier of stream.
     *
     * The provided stream should produce the same sequence of values.
     **/
    public static DoubleFuncList from(Supplier<DoubleStream> supplier) {
        return new DoubleFuncListDerived(()->DoubleStreamPlus.from(supplier.get()));
    }
    
    //== Create ==
    
    /** Returns the infinite streams of zeroes. */
    public static DoubleFuncList zeroes() {
        return zeroes(Integer.MAX_VALUE);
    }
    
    /** Returns a list that contains zeroes. */
    public static DoubleFuncList zeroes(int count) {
        return DoubleFuncList.from(()->DoubleStreamPlus.zeroes(count));
    }
    
    /** Returns the list of ones. */
    public static DoubleFuncList ones() {
        return ones(Integer.MAX_VALUE);
    }
    
    /** Returns a list that contains ones. */
    public static DoubleFuncList ones(int count) {
        return DoubleFuncList.from(()->DoubleStreamPlus.ones(count));
    }
    
    /** Create a list that is the repeat of the given array of data. */
    public static DoubleFuncList repeat(double ... data) {
        return DoubleFuncList.from(()->DoubleStreamPlus.repeat(data));
    }
    
    /** Create a list that is the repeat of the given array of data. */
    public static DoubleFuncList cycle(double ... data) {
        return DoubleFuncList.from(()->DoubleStreamPlus.cycle(data));
    }
    
    /** Create a list that for a loop with the number of time given - the value is the index of the loop. */
    public static DoubleFuncList loop() {
        return DoubleFuncList.from(()->DoubleStreamPlus.loop());
    }
    
    /** Create a list that for a loop with the number of time given - the value is the index of the loop. */
    public static DoubleFuncList loop(int times) {
        return DoubleFuncList.from(()->DoubleStreamPlus.loop(times));
    }
    
    public static DoubleFuncList loopBy(int step) {
        return DoubleFuncList.from(()->DoubleStreamPlus.loopBy(step));
    }
    
    public static DoubleFuncList loopBy(int step, int times) {
        return DoubleFuncList.from(()->DoubleStreamPlus.loopBy(step, times));
    }
    
    public static DoubleFuncList infinite() {
        return DoubleFuncList.from(()->DoubleStreamPlus.infinite());
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
    
    /** Returns the infinite streams of wholes numbers -- 0, 1, 2, 3, .... */
    public static DoubleFuncList wholeNumbers() {
        return DoubleFuncList.from(
                IntStream
                .range(0, Integer.MAX_VALUE)
                .mapToDouble(i -> 1.0*i));
    }
    
    /** Create a FuncList that for a loop with the number of time given - the value is the index of the loop. */
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
    
    //-- Concat + Combine --
    
    /** Concatenate all the given streams. */
    public static DoubleFuncList concat(DoubleFuncList ... lists) {
        return combine(lists);
    }
    
    /**
     * Concatenate all the given lists.
     *
     * This method is the alias of {@link FuncList#concat(FuncList...)}
     *   but allowing static import without colliding with {@link String#concat(String)}.
     **/
    public static DoubleFuncList combine(DoubleFuncList ... lists) {
        ImmutableList<DoubleFuncList> listOfList = FuncList.listOf(lists);
        return listOfList.flatMapToDouble(Func.itself());
    }
    
    // TODO - Rethink ... as this will generate un-repeatable stream.
    //          we may want to do cache here.
    
    /**
     * Create a FuncList from the supplier of suppliers.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleFuncList generate(Supplier<DoubleSupplier> suppliers) {
        return DoubleFuncList.from(() -> {
            val generator = suppliers.get();
            return DoubleStreamPlus.generate(generator);
        });
    }
    
    /**
     * Create a list from the supplier of suppliers.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static DoubleFuncList generateWith(Supplier<DoubleSupplier> suppliers) {
        return generate(suppliers);
    }
    
    /**
     * Create a list by apply the compounder to the seed over and over.
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
    public static DoubleFuncList iterate(
            double                        seed, 
            DoubleToDoubleAccessPrimitive compounder) {
        return DoubleFuncList.from(()->DoubleStreamPlus.iterate(seed, compounder));
    }
    
    /**
     * Create a list by apply the compounder to the seed over and over.
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
    public static DoubleFuncList compound(
            double                        seed, 
            DoubleToDoubleAccessPrimitive compounder) {
        return DoubleFuncList.from(()->DoubleStreamPlus.compound(seed, compounder));
    }
    
    /**
     * Create a list by apply the compounder to the seeds over and over.
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
    public static DoubleFuncList iterate(
            double                    seed1,
            double                    seed2,
            DoubleBiFunctionPrimitive compounder) {
        return DoubleFuncList.from(()->DoubleStreamPlus.iterate(seed1, seed2, compounder));
    }
    
    /**
     * Create a list by apply the compounder to the seeds over and over.
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
    public static DoubleFuncList compound(
            double                    seed1,
            double                    seed2,
            DoubleBiFunctionPrimitive compounder) {
        return iterate(seed1, seed2, compounder);
    }
    
    //== Zip ==
    
    /**
     * Create a FuncList by combining elements together into a FuncList of tuples.
     * Only elements with pair will be combined. If this is not desirable, use FuncList1.zip(FuncList2).
     *
     * For example:
     *     list1 = [A, B, C, D, E]
     *     list2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1,
            DoubleFuncList list2) {
        return FuncList.from(() -> {
            return DoubleStreamPlus.zipOf(
                    list1.doubleStream(),
                    list2.doubleStream());
        });
    }
    
    public static FuncList<DoubleDoubleTuple> zipOf(
            DoubleFuncList list1, double defaultValue1,
            DoubleFuncList list2, double defaultValue2) {
        return FuncList.from(() -> {
            return DoubleStreamPlus.zipOf(
                    list1.doubleStream(), defaultValue1,
                    list2.doubleStream(), defaultValue2);
        });
    }
    
    /** Zip integers from two IntFuncLists and combine it into another object. */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1,
            DoubleFuncList            list2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(() -> {
            return DoubleStreamPlus.zipOf(
                    list1.doubleStream(),
                    list2.doubleStream(),
                    merger);
        });
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static DoubleFuncList zipOf(
            DoubleFuncList            list1, double defaultValue1,
            DoubleFuncList            list2, double defaultValue2,
            DoubleBiFunctionPrimitive merger) {
        return DoubleFuncList.from(() -> {
            return DoubleStreamPlus.zipOf(
                    list1.doubleStream(), defaultValue1,
                    list2.doubleStream(), defaultValue2,
                    merger);
        });
    }
    
    //-- Builder --
    
    /** Create a new FuncList. */
    public static DoubleFuncListBuilder newListBuilder() {
        return new DoubleFuncListBuilder();
    }
    
    /** Create a new list builder. */
    public static DoubleFuncListBuilder newBuilder() {
        return new DoubleFuncListBuilder();
    }
    
    /** Create a new FuncList. */
    public static DoubleFuncListBuilder newDoubleListBuilder() {
        return new DoubleFuncListBuilder();
    }
    
    //== Core ==
    
    /** Return the stream of data behind this IntFuncList. */
    public DoubleStreamPlus doubleStream();
    
    
    /** Return the stream of data behind this IntFuncList. */
    public default DoubleStreamPlus doubleStreamPlus() {
        return doubleStream();
    }
    
    /** Return the this as a FuncList. */
    @Override
    public default DoubleFuncList asDoubleFuncList() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a FuncList from the given FuncList. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <SOURCE> DoubleFuncList deriveFrom(
            List<SOURCE>                               list,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        boolean isLazy 
                = (list instanceof FuncList)
                ? ((FuncList)list).isLazy()
                : true;
        
        if (!isLazy) {
            val orgStreamPlus = (list instanceof FuncList)
                    ? ((FuncList)list).streamPlus()
                    : StreamPlus.from(list.stream());
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = DoubleStreamPlus.from(newStream);
            return ImmutableDoubleFuncList.from(isLazy, newStreamPlus);
        }
        
        return DoubleFuncList.from(() -> {
            val orgStreamPlus = (list instanceof FuncList)
                    ? ((FuncList)list).streamPlus()
                    : StreamPlus.from(list.stream());
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = DoubleStreamPlus.from(newStream);
            return newStreamPlus;
        });
    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsIntFuncList                         asFuncList,
            Function<IntStreamPlus, DoubleStream> action) {
        boolean isLazy = asFuncList.asIntFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = DoubleStreamPlus.from(newStream);
            return ImmutableDoubleFuncList.from(isLazy, newStreamPlus);
        }
        
        return DoubleFuncList.from(() -> {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream = action.apply(orgStreamPlus);
            return DoubleStreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> DoubleFuncList deriveFrom(
            AsDoubleFuncList                       asFuncList,
            Function<DoubleStreamPlus, DoubleStream> action) {
        boolean isLazy = asFuncList.asDoubleFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.doubleStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = DoubleStreamPlus.from(newStream);
            return ImmutableDoubleFuncList.from(isLazy, newStreamPlus);
        }
        
        return DoubleFuncList.from(() -> {
            val orgStreamPlus = asFuncList.doubleStreamPlus();
            val newStream = action.apply(orgStreamPlus);
            return DoubleStreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from another FuncList. */
    public static IntFuncList deriveToInt(
            AsDoubleFuncList                      funcList,
            Function<DoubleStreamPlus, IntStream> action) {
        return IntFuncList.deriveFrom(funcList, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static DoubleFuncList deriveToDouble(
            AsDoubleFuncList                         funcList,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleFuncList.deriveFrom(funcList, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsDoubleFuncList                           funcList,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return FuncList.deriveFrom(funcList, action);
    }
    
    //-- Predicate --
    
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
    
    //-- Lazy + Eager --
    
    /** Return a lazy list with the data of this list. */
    public DoubleFuncList lazy();
    
    /** Return a eager list with the data of this list. */
    public DoubleFuncList eager();
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableDoubleFuncList freeze() {
        return toImmutableList();
    }
    
    //-- Iterable --
    
    /** @return the iterable of this FuncList. */
    public default DoubleIterable iterable() {
        return () -> iterator();
    }
    
    //-- Iterator --
    
    /** @return a iterator of this FuncList. */
    @Override
    public default DoubleIteratorPlus iterator() {
        return () -> iterator();
    }
    
    /** @return a spliterator of this FuncList. */
    public default Spliterator.OfDouble spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    /** Map each value into a string value. */
    public default FuncList<String> mapToString() {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(i -> String.valueOf(i)));
    }
    
    /** Map each value into other value using the function. */
    public default DoubleFuncList map(DoubleUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.doubleStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(DoubleToIntFunction mapper) {
        return deriveToInt(this, stream -> stream.mapToInt(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt() {
        return deriveToInt(this, stream -> stream.mapToInt());
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
    
    /** Map a value into a FuncList and then flatten that FuncList */
    public default IntFuncList flatMapToInt(DoubleFunction<? extends AsIntFuncList> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
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
    
    //== Conversion ==
    
    public default DoubleFuncList toFuncList() {
        return this;
    }
    
    /** Convert this FuncList to an array. */
    public default double[] toArray() {
        return doubleStream()
                .toArray();
    }
    
    /** Returns stream of Double from the value of this list. */
    public default FuncList<Double> boxed() {
        return FuncList.from(doubleStream().mapToObj(d -> d));
    }
    
    /** Returns a functional list builder with the initial data of this func list. */
    public default DoubleFuncListBuilder toBuilder() {
        return new DoubleFuncListBuilder(toArray());
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
        return mapToIntWithIndex((index, data) -> check.test(data) ? index : -1)
                .filter(i -> i != -1);
    }
    
    public default IntFuncList indexesOf(double value) {
        return mapToIntWithIndex((index, data) -> (data == value) ? index : -1)
                .filter(i -> i != -1);
    }
    
    public default int indexOf(double o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(double o){
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
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
                .findFirst();
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
    public default DoubleFuncList tail() {
        return skip(1);
    }
    
    /** Add the given value to the end of the list. */
    public default DoubleFuncList append(double value) {
        return DoubleFuncList.concat(this, DoubleFuncList.of(value));
    }
    
    /** Add the given values to the end of the list. */
    public default DoubleFuncList appendAll(double ... values) {
        return DoubleFuncList.concat(this, DoubleFuncList.of(values));
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default DoubleFuncList appendAll(DoubleFuncList values) {
        return DoubleFuncList.concat(this, values);
    }
    
    /** Add the given value to the beginning of the list */
    public default DoubleFuncList prepend(int value) {
        return DoubleFuncList.concat(DoubleFuncList.of(value), this);
    }
    
    /** Add the given values to the beginning of the list */
    public default DoubleFuncList prependAll(double ... values) {
        return DoubleFuncList.concat(DoubleFuncList.of(values), this);
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default DoubleFuncList prependAll(DoubleFuncList prefixFuncList) {
        if (prefixFuncList == null)
            return this;
        
        return DoubleFuncList.concat(prefixFuncList, this);
    }
    
    /** Returns a new functional list with the value replacing at the index. */
    public default DoubleFuncList with(int index, double value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, orgValue) -> ((i == index) ? value : orgValue));
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default DoubleFuncList with(int index, DoubleUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, value) -> {
            return (i == index) ? mapper.applyAsDouble(value) : value;
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
        val middle = theFuncList.asDoubleFuncList();
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
        val tail   = skip(toIndexExclusive);
        return DoubleFuncList.concat(first, tail);
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    public default DoubleFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive)
                .limit(length);
    }
    
    /** Returns the new list with reverse order of this list. */
    @Eager
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
    @Eager
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
    
    //-- Query --
    
    /** Returns the list of tuple of the index and the value for which the value match the predicate. */
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
    
}
