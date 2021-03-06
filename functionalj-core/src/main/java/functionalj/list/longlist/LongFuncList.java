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
package functionalj.list.longlist;

import static functionalj.lens.Access.theLong;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func;
import functionalj.function.LongComparator;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongIterable;
import functionalj.stream.longstream.LongIteratorPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamPlusHelper;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.IntLongTuple;
import functionalj.tuple.LongLongTuple;
import lombok.val;
import nullablej.nullable.Nullable;

// TODO - Use this for byte, short and char

public interface LongFuncList 
        extends 
            AsLongFuncList,
            LongIterable, 
            LongPredicate, 
            LongFuncListWithCalculate, 
            LongFuncListWithCombine,
            LongFuncListWithFilter, 
            LongFuncListWithFlatMap, 
            LongFuncListWithLimit, 
            LongFuncListWithMap, 
            LongFuncListWithMapFirst,
            LongFuncListWithMapGroup, 
            LongFuncListWithMapThen, 
            LongFuncListWithMapToMap, 
            LongFuncListWithMapToTuple, 
            LongFuncListWithMapWithIndex,
            LongFuncListWithModify, 
            LongFuncListWithPeek, 
            LongFuncListWithPipe, 
            LongFuncListWithSegment, 
            LongFuncListWithSort, 
            LongFuncListWithSplit,
            LongFuncListWithStatistic {
    
    
    /** Throw a no more element exception. This is used for generator. */
    public static long noMoreElement() throws NoMoreResultException {
        SupplierBackedIterator.noMoreElement();
        return Long.MIN_VALUE;
    }
    
    /** Returns an empty LongFuncList. */
    public static ImmutableLongFuncList empty() {
        return ImmutableLongFuncList.empty();
    }
    
    /** Returns an empty functional list. */
    public static ImmutableLongFuncList emptyList() {
        return ImmutableLongFuncList.empty();
    }
    
    /** Returns an empty functional list. */
    public static ImmutableLongFuncList emptyLongList() {
        return ImmutableLongFuncList.empty();
    }
    
    /** Create a FuncList from the given longs. */
    public static ImmutableLongFuncList of(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    /** Create a FuncList from the given longs. */
    public static ImmutableLongFuncList AllOf(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    /** Create a FuncList from the given longs. */
    public static ImmutableLongFuncList longs(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    /** Create a FuncList from the given longs. */
    public static ImmutableLongFuncList longList(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    /** Create a FuncList from the given longs. */
    @SafeVarargs
    public static ImmutableLongFuncList ListOf(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    /** Create a FuncList from the given longs. */
    @SafeVarargs
    public static ImmutableLongFuncList listOf(long... data) {
        return ImmutableLongFuncList.of(data);
    }
    
    //-- From --
    
    /** Create a FuncList from the given longs. */
    public static ImmutableLongFuncList from(long[] datas) {
        return new ImmutableLongFuncList(datas, datas.length);
    }
    
    /** Create a FuncList from the given collection. */
    public static ImmutableLongFuncList from(Collection<Long> data, long valueForNull) {
        LongStream LongStream 
                = StreamPlus.from(data.stream())
                .fillNull((Long)valueForNull)
                .mapToLong(theLong);
        ImmutableLongFuncList list = ImmutableLongFuncList.from(LongStream);
        if (!(data instanceof FuncList))
            return list;
        val funcList = (FuncList<Long>)data;
        return funcList.isLazy() ? list : (ImmutableLongFuncList)list.eager();
    }
    
    /** Create a FuncList from the given FuncList. */
    public static LongFuncList from(boolean isLazy, AsLongFuncList asFuncList) {
        val funcList = asFuncList.asLongFuncList();
        return isLazy ? funcList.lazy() : funcList.eager();
    }
    
    /** Create a FuncList from the given stream. */
    public static LongFuncList from(LongStream stream) {
        return ImmutableLongFuncList.from(stream);
    }
    
    /**
     * Create a FuncList from the given supplier of stream.
     *
     * The provided stream should produce the same sequence of values.
     **/
    public static LongFuncList from(Supplier<LongStream> supplier) {
        return new LongFuncListDerived(() -> LongStreamPlus.from(supplier.get()));
    }
    
    // == Create ==
    
    /** Returns the infinite streams of zeroes. */
    public static LongFuncList zeroes() {
        return zeroes(Integer.MAX_VALUE);
    }
    
    /** Returns a list that contains zeroes. */
    public static LongFuncList zeroes(int count) {
        return LongFuncList.from(() -> LongStreamPlus.zeroes(count));
    }
    
    /** Returns the list of ones. */
    public static LongFuncList ones() {
        return ones(Integer.MAX_VALUE);
    }
    
    /** Returns a list that contains ones. */
    public static LongFuncList ones(int count) {
        return LongFuncList.from(() -> LongStreamPlus.ones(count));
    }
    
    /** Create a list that is the repeat of the given array of data. */
    public static LongFuncList repeat(long... data) {
        return LongFuncList.from(() -> LongStreamPlus.repeat(data));
    }
    
    /** Create a list that is the repeat of the given array of data. */
    public static LongFuncList cycle(long... data) {
        return LongFuncList.from(() -> LongStreamPlus.cycle(data));
    }
    
    /** Create a list that is the repeat of the given list of data. */
    public static LongFuncList repeat(LongFuncList data) {
        return LongFuncList.from(() -> LongStreamPlus.repeat(data));
    }
    
    /** Create a list that is the repeat of the given list of data. */
    public static LongFuncList cycle(LongFuncList data) {
        return LongFuncList.from(() -> LongStreamPlus.cycle(data));
    }
    
    /** Create a list that for a loop with the number of time given - the value is the index of the loop. */
    public static LongFuncList loop() {
        return LongFuncList.from(() -> LongStreamPlus.loop());
    }
    
    /** Create a list that for a loop with the number of time given - the value is the index of the loop. */
    public static LongFuncList loop(long times) {
        return LongFuncList.from(() -> LongStreamPlus.loop(times));
    }
    
    public static LongFuncList loopBy(long step) {
        return LongFuncList.from(() -> LongStreamPlus.loopBy(step));
    }
    
    public static LongFuncList loopBy(long step, int times) {
        return LongFuncList.from(() -> LongStreamPlus.loopBy(step, times));
    }
    
    public static LongFuncList infinite() {
        return LongFuncList.from(() -> LongStreamPlus.infinite());
    }
    
    public static LongFuncList infiniteInt() {
        return LongFuncList.from(() -> LongStreamPlus.infiniteInt());
    }
    
    public static LongFuncList naturalNumbers() {
        return LongFuncList.from(() -> LongStreamPlus.naturalNumbers());
    }
    
    public static LongFuncList naturalNumbers(int count) {
        return LongFuncList.from(() -> LongStreamPlus.naturalNumbers(count));
    }
    
    public static LongFuncList wholeNumbers() {
        return LongFuncList.from(() -> LongStreamPlus.wholeNumbers());
    }
    
    /** Returns the infinite streams of wholes numbers -- 0, 1, 2, 3, .... */
    public static LongFuncList wholeNumbers(int count) {
        return LongFuncList.from(() -> LongStreamPlus.wholeNumbers(count));
    }
    
    /** Create a FuncList that for a loop with the number of time given - the value is the index of the loop. */
    public static LongFuncList range(int startInclusive, int endExclusive) {
        return LongFuncList.from(() -> LongStreamPlus.range(startInclusive, endExclusive));
    }
    
    //-- Concat + Combine --
    
    /** Concatenate all the given streams. */
    public static LongFuncList concat(LongFuncList... lists) {
        return combine(lists);
    }
    
    /**
     * Concatenate all the given lists.
     *
     * This method is the alias of {@link FuncList#concat(FuncList...)} but allowing static import without colliding with
     * {@link String#concat(String)}.
     **/
    public static LongFuncList combine(LongFuncList... lists) {
        ImmutableFuncList<LongFuncList> listOfList = FuncList.listOf(lists);
        return listOfList.flatMapToLong(Func.itself());
    }
    
    // TODO - Rethink ... as this will generate un-repeatable stream.
    //          we may want to do cache here.
    
    /**
     * Create a FuncList from the supplier of suppliers. The supplier will be repeatedly asked for value until NoMoreResultException is
     * thrown.
     **/
    public static LongFuncList generate(Supplier<LongSupplier> suppliers) {
        return LongFuncList.from(() -> {
            val generator = suppliers.get();
            return LongStreamPlus.generate(generator);
        });
    }
    
    /**
     * Create a list from the supplier of suppliers. The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static LongFuncList generateWith(Supplier<LongSupplier> suppliers) {
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
    public static LongFuncList iterate(
            long              seed, 
            LongUnaryOperator compounder) {
        return LongFuncList.from(() -> LongStreamPlus.iterate(seed, compounder));
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
    public static LongFuncList compound(long seed, LongUnaryOperator compounder) {
        return LongFuncList.from(() -> LongStreamPlus.compound(seed, compounder));
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
    public static LongFuncList iterate(long seed1, long seed2, LongBinaryOperator compounder) {
        return LongFuncList.from(() -> LongStreamPlus.iterate(seed1, seed2, compounder));
    }
    
    /**
     * Create a list by apply the compounder to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b. The result stream will be: 1 <- seed1, 1 <- seed2, 2 <- (1+1), 3 <-
     * (1+2), 5 <- (2+3), 8 <- (5+8) ...
     *
     * Note: this is an alias of iterate()
     **/
    public static LongFuncList compound(long seed1, long seed2, LongBinaryOperator compounder) {
        return LongFuncList.from(() -> LongStreamPlus.compound(seed1, seed2, compounder));
    }
    
    // == Zip ==
    
    /**
     * Create a FuncList by combining elements together into a FuncList of tuples. Only elements with pair will be combined. If this is not
     * desirable, use FuncList1.zip(FuncList2).
     *
     * For example: list1 = [A, B, C, D, E] list2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static FuncList<LongLongTuple> zipOf(AsLongFuncList list1, AsLongFuncList list2) {
        return FuncList.from(() -> {
            return LongStreamPlus.zipOf(list1.longStream(), list2.longStream());
        });
    }
    
    public static FuncList<LongLongTuple> zipOf(AsLongFuncList list1, long defaultValue1, LongFuncList list2, long defaultValue2) {
        return FuncList.from(() -> {
            return LongStreamPlus.zipOf(list1.longStream(), defaultValue1, list2.longStream(), defaultValue2);
        });
    }
    
    /** Zip integers from two LongFuncLists and combine it into another object. */
    public static LongFuncList zipOf(
            AsLongFuncList list1, 
            AsLongFuncList list2, 
            LongBinaryOperator merger) {
        return LongFuncList.from(() -> {
            return LongStreamPlus.zipOf(list1.longStream(), list2.longStream(), merger);
        });
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object. The result stream has the size of the
     * shortest stream.
     */
    public static LongFuncList zipOf(
            AsLongFuncList list1, long defaultValue1, 
            AsLongFuncList list2, long defaultValue2,
            LongBinaryOperator merger) {
        return LongFuncList.from(() -> {
            return LongStreamPlus.zipOf(list1.longStream(), defaultValue1, list2.longStream(), defaultValue2, merger);
        });
    }
    
    // -- Builder --
    
    /** Create a new FuncList. */
    public static LongFuncListBuilder newListBuilder() {
        return new LongFuncListBuilder();
    }
    
    /** Create a new list builder. */
    public static LongFuncListBuilder newBuilder() {
        return new LongFuncListBuilder();
    }
    
    /** Create a new FuncList. */
    public static LongFuncListBuilder newIntListBuilder() {
        return new LongFuncListBuilder();
    }
    
    // == Core ==
    
    /** Return the stream of data behind this LongFuncList. */
    public LongStreamPlus longStream();
    
    /** Return the stream of data behind this LongFuncList. */
    public default LongStreamPlus longStreamPlus() {
        return longStream();
    }
    
    /** Return the this as a FuncList. */
    @Override
    public default LongFuncList asLongFuncList() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a FuncList from the given FuncList. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <SOURCE> LongFuncList deriveFrom(
            List<SOURCE>                             list,
            Function<StreamPlus<SOURCE>, LongStream> action) {
        boolean isLazy = (list instanceof FuncList) ? ((FuncList) list).isLazy() : true;
        
        if (!isLazy) {
            val orgStreamPlus = (list instanceof FuncList) ? ((FuncList) list).streamPlus() : StreamPlus.from(list.stream());
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = LongStreamPlus.from(newStream);
            return ImmutableLongFuncList.from(isLazy, newStreamPlus);
        }
        
        return LongFuncList.from(() -> {
            val orgStreamPlus = (list instanceof FuncList) ? ((FuncList) list).streamPlus() : StreamPlus.from(list.stream());
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = LongStreamPlus.from(newStream);
            return newStreamPlus;
        });
    }
    
    /** Create a FuncList from the given LongFuncList. */
    public static <TARGET> LongFuncList deriveFrom(
            AsIntFuncList                        asFuncList, 
            Function<IntStreamPlus, LongStream> action) {
        boolean isLazy = asFuncList.asIntFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = LongStreamPlus.from(newStream);
            return ImmutableLongFuncList.from(isLazy, newStreamPlus);
        }
        
        return LongFuncList.from(() -> {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given LongFuncList. */
    public static <TARGET> LongFuncList deriveFrom(
            AsLongFuncList                       asFuncList, 
            Function<LongStreamPlus, LongStream> action) {
        boolean isLazy = asFuncList.asLongFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.longStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = LongStreamPlus.from(newStream);
            return ImmutableLongFuncList.from(isLazy, newStreamPlus);
        }
        
        return LongFuncList.from(() -> {
            val orgStreamPlus = asFuncList.longStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> LongFuncList deriveFrom(
            AsDoubleFuncList                       asFuncList, 
            Function<DoubleStreamPlus, LongStream> action) {
        boolean isLazy = asFuncList.asDoubleFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.doubleStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = LongStreamPlus.from(newStream);
            return ImmutableLongFuncList.from(isLazy, newStreamPlus);
        }
        
        return LongFuncList.from(() -> {
            val orgStreamPlus = asFuncList.doubleStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from another FuncList. */
    public static IntFuncList deriveToInt(
            AsLongFuncList                      funcList, 
            Function<LongStreamPlus, IntStream> action) {
        return IntFuncList.deriveFrom(funcList, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static LongFuncList deriveToLong(
            AsLongFuncList                      funcList, 
            Function<LongStreamPlus, LongStream> action) {
        return LongFuncList.deriveFrom(funcList, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static DoubleFuncList deriveToDouble(
            AsLongFuncList                         funcList, 
            Function<LongStreamPlus, DoubleStream> action) {
        return DoubleFuncList.deriveFrom(funcList, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static <TARGET> FuncList<TARGET> deriveToObj(
            AsLongFuncList                           funcList, 
            Function<LongStreamPlus, Stream<TARGET>> action) {
        return FuncList.deriveFrom(funcList, action);
    }
    
    //-- Predicate --
    
    /** Test if the data is in the list */
    @Override
    public default boolean test(long value) {
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
    public LongFuncList lazy();
    
    /** Return a eager list with the data of this list. */
    public LongFuncList eager();
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableLongFuncList freeze() {
        return toImmutableList();
    }
    
    //-- Iterable --
    
    /** @return the iterable of this FuncList. */
    public default LongIterable iterable() {
        return () -> iterator();
    }
    
    //-- Iterator --
    
    /** @return a iterator of this list. */
    @Override
    public default LongIteratorPlus iterator() {
        return LongIteratorPlus.from(longStream());
    }
    
    /** @return a spliterator of this list. */
    public default Spliterator.OfLong spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    /** Map each value into a string value. */
    public default FuncList<String> mapToString() {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(i -> String.valueOf(i)));
    }
    
    /** Map each value into other value using the function. */
    public default LongFuncList map(LongUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.longStream().map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(LongToIntFunction mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default LongFuncList mapToLong(LongUnaryOperator mapper) {
        return deriveFrom(this, stream -> stream.mapToLong(mapper));
    }
    
    /** Map each value into a double value. */
    public default DoubleFuncList mapToDouble() {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(i -> i));
    }
    
    /** Map each value into a double value using the function. */
    public default DoubleFuncList mapToDouble(LongToDoubleFunction mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(LongFunction<? extends TARGET> mapper) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a FuncList and then flatten that list */
    public default LongFuncList flatMap(LongFunction<? extends AsLongFuncList> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).longStream()));
    }
    
    /** Map a value into an integer FuncList and then flatten that list */
    public default LongFuncList flatMapToInt(LongFunction<? extends AsLongFuncList> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).longStream()));
    }
    
    /** Map a value into an integer FuncList and then flatten that list */
    public default LongFuncList flatMapToLong(LongFunction<? extends AsLongFuncList> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).longStream()));
    }
    
    /** Map a value into a double FuncList and then flatten that list */
    public default DoubleFuncList flatMapToDouble(LongFunction<? extends AsDoubleFuncList> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default LongFuncList filter(LongPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default LongFuncList filter(LongUnaryOperator mapper, LongPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default LongFuncList peek(LongConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default LongFuncList limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    
    public default LongFuncList skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default LongFuncList distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default LongFuncList sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default LongFuncList sorted(LongComparator comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    //-- Terminate --
    
    /** Process each value using the given action */
    public default void forEach(LongConsumer action) {
        longStream().forEach(action);
    }
    
    /**
     * Performs an action for each element of this stream, in the encounter order of the stream if the stream has a defined encounter order.
     */
    public default void forEachOrdered(LongConsumer action) {
        longStream().forEachOrdered(action);
    }
    
    //== Conversion ==
    
    public default LongFuncList toFuncList() {
        return this;
    }
    
    /** Convert this FuncList to an array. */
    public default long[] toArray() {
        return longStream().toArray();
    }
    
    /** Returns stream of Integer from the value of this list. */
    public default FuncList<Long> boxed() {
        return mapToObj(theLong.boxed());
    }
    
    /** Returns a functional list builder with the initial data of this func list. */
    public default LongFuncListBuilder toBuilder() {
        return new LongFuncListBuilder(toArray());
    }
    
    //== Nullable, Optional and Result
    
    public default Nullable<LongFuncList> __nullable() {
        return Nullable.of(this);
    }
    
    public default Optional<LongFuncList> __optional() {
        return Optional.of(this);
    }
    
    public default Result<LongFuncList> __result() {
        return Result.valueOf(this);
    }
    
    // -- List specific --
    
    public default IntFuncList indexesOf(LongPredicate check) {
        return mapWithIndex((index, data) -> check.test(data) ? index : -1).filter(i -> i != -1).mapToInt(l -> (int)l);
    }
    
    public default IntFuncList indexesOf(long value) {
        return mapWithIndex((index, data) -> (data == value) ? index : -1).filter(i -> i != -1).mapToInt(l -> (int)l);
    }
    
    public default int indexOf(long o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(long o) {
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
    }
    
    /** Returns the first element. */
    public default OptionalLong first() {
        return longStream()
                .limit(1)
                .findFirst();
    }
    
    /** Returns the first elements */
    public default LongFuncList first(int count) {
        return limit(count);
    }
    
    /** Returns the last element. */
    public default OptionalLong last() {
        return last(1).findFirst();
    }
    
    /** Returns the last elements */
    public default LongFuncList last(int count) {
        val size   = this.size();
        val offset = Math.max(0, size - count);
        return skip(offset);
    }
    
    /** Returns the element at the index. */
    public default OptionalLong at(int index) {
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the second to the last elements. */
    public default LongFuncList tail() {
        return skip(1);
    }
    
    /** Add the given value to the end of the list. */
    public default LongFuncList append(long value) {
        return LongFuncList.concat(this, LongFuncList.of(value));
    }
    
    /** Add the given values to the end of the list. */
    public default LongFuncList appendAll(long ... values) {
        return LongFuncList.concat(this, LongFuncList.of(values));
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default LongFuncList appendAll(LongFuncList values) {
        return LongFuncList.concat(this, values);
    }
    
    /** Add the given value to the beginning of the list */
    public default LongFuncList prepend(long value) {
        return LongFuncList.concat(LongFuncList.of(value), this);
    }
    
    /** Add the given values to the beginning of the list */
    public default LongFuncList prependAll(long ... values) {
        return LongFuncList.concat(LongFuncList.of(values), this);
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default LongFuncList prependAll(LongFuncList prefixFuncList) {
        if (prefixFuncList == null)
            return this;
        
        return LongFuncList.concat(prefixFuncList, this);
    }
    
    /** Returns a new functional list with the value replacing at the index. */
    public default LongFuncList with(int index, long value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, orgValue) -> ((i == index) ? value : orgValue));
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default LongFuncList with(int index, LongUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, value) -> {
            return (i == index) ? mapper.applyAsLong(value) : value;
        });
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default LongFuncList insertAt(int index, long... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        val first = limit(index);
        val tail  = skip(index);
        return LongFuncList.concat(first, LongFuncList.of(elements), tail);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default LongFuncList insertAllAt(int index, AsLongFuncList theFuncList) {
        if (theFuncList == null)
            return this;
        
        val first  = limit(index);
        val middle = theFuncList.asLongFuncList();
        val tail   = skip(index);
        return LongFuncList.concat(first, middle, tail);
    }
    
    /** Returns the new list from this list without the element. */
    public default LongFuncList exclude(long element) {
        return filter(each -> each != element);
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default LongFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        val first = limit(index);
        val tail  = skip(index + 1);
        return LongFuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default LongFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        val first = limit(fromIndexInclusive);
        val tail  = skip(fromIndexInclusive + count);
        return LongFuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`. */
    public default LongFuncList excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        val first = limit(fromIndexInclusive);
        val tail  = skip(toIndexExclusive);
        return LongFuncList.concat(first, tail);
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    public default LongFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive).limit(length);
    }
    
    /** Returns the new list with reverse order of this list. */
    @Eager
    public default LongFuncList reverse() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val mid   = length / 2;
        for (int i = 0; i < mid; i++) {
            val j    = length - i - 1;
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableLongFuncList(array, array.length, isLazy());
    }
    
    /** Returns the new list with random order of this list. */
    @Eager
    public default LongFuncList shuffle() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val rand  = new Random();
        for (int i = 0; i < length; i++) {
            val j    = rand.nextInt(length);
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableLongFuncList(array,array.length, isLazy());
    }
    
    //-- Query --
    
    /** Returns the list of tuple of the index and the value for which the value match the predicate. */
    public default FuncList<IntLongTuple> query(LongPredicate check) {
        return mapToObjWithIndex((index, data) -> check.test(data) ? IntLongTuple.of(index, data) : null).filterNonNull();
    }
    
    public default boolean isEmpty() {
        return !iterator().hasNext();
    }
    
    public default boolean contains(long value) {
        return longStream().anyMatch(i -> i == value);
    }
    
    public default boolean containsAllOf(long... array) {
        return LongStreamPlus
                .of(array)
                .allMatch(each -> longStream()
                                    .anyMatch(value -> Objects.equals(each, value)));
    }
    
    public default boolean containsSomeOf(long... c) {
        return LongStreamPlus
                .of(c)
                .anyMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(long... c) {
        return LongStreamPlus.of(c)
                .noneMatch(each -> longStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Long> c) {
        return c.stream()
                .allMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Long> c) {
        return c.stream()
                .anyMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(Collection<Long> c) {
        return c.stream()
                .noneMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(LongFuncList c) {
        return c.longStream()
                .allMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(LongFuncList c) {
        return c.longStream()
                .anyMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(LongFuncList c) {
        return c.longStream()
                .noneMatch(each -> longStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default long get(int index) {
        val ref   = new long[1][];
        val found = LongStreamPlusHelper.hasAt(this.longStream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref[0][0];
    }
    
    //-- Match --
    
    /** Check if any element match the predicate */
    public default boolean anyMatch(LongPredicate predicate) {
        return longStream().anyMatch(predicate);
    }
    
    /** Check if all elements match the predicate */
    public default boolean allMatch(LongPredicate predicate) {
        return longStream().allMatch(predicate);
    }
    
    /** Check if none of the elements match the predicate */
    public default boolean noneMatch(LongPredicate predicate) {
        return longStream().noneMatch(predicate);
    }
    
    /** Returns the sequentially first element */
    public default OptionalLong findFirst() {
        return longStream().findFirst();
    }
    
    /** Returns the any element */
    public default OptionalLong findAny() {
        return longStream().findAny();
    }
    
    @Sequential
    @Terminal
    public default OptionalLong findLast() {
        return longStream().findLast();
    }
    
}
