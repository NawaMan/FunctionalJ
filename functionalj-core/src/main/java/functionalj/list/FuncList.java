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
package functionalj.list;

import static functionalj.lens.Access.$I;
import static java.util.function.Function.identity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.DoubleDoubleFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.Func;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.LongLongBiFunction;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.doublelist.ImmutableDoubleFuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.longlist.AsLongFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.IterablePlus;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.IntTuple2;
import functionalj.tuple.Tuple2;
import lombok.val;
import nullablej.nullable.Nullable;


public interface FuncList<DATA>
        extends
            ReadOnlyList<DATA>,
            Predicate<DATA>,
            AsFuncList<DATA>,
            FuncListWithCalculate<DATA>,
            FuncListWithCombine<DATA>,
            FuncListWithFillNull<DATA>,
            FuncListWithFilter<DATA>,
            FuncListWithFlatMap<DATA>,
            FuncListWithLimit<DATA>,
            FuncListWithMap<DATA>,
            FuncListWithMapFirst<DATA>,
            FuncListWithMapGroup<DATA>,
            FuncListWithMapThen<DATA>,
            FuncListWithMapToMap<DATA>,
            FuncListWithMapToTuple<DATA>,
            FuncListWithMapWithIndex<DATA>,
            FuncListWithModify<DATA>,
            FuncListWithPeek<DATA>,
            FuncListWithPipe<DATA>,
            FuncListWithSegment<DATA>,
            FuncListWithSort<DATA>,
            FuncListWithSplit<DATA> {
    
    
    /** Throw a no more element exception. This is used for generator. */
    public static <TARGET> TARGET noMoreElement() throws NoMoreResultException {
        return SupplierBackedIterator.noMoreElement();
    }
    
    /** Returns an empty functional list. */
    public static <TARGET> ImmutableFuncList<TARGET> empty() {
        return ImmutableFuncList.empty();
    }
    
    /** Returns an empty functional list. */
    public static <TARGET> ImmutableFuncList<TARGET> emptyList() {
        return ImmutableFuncList.empty();
    }
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableFuncList<TARGET> empty(Class<TARGET> elementClass) {
        return ImmutableFuncList.empty();
    }
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableFuncList<TARGET> emptyList(Class<TARGET> elementClass) {
        return ImmutableFuncList.empty();
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableFuncList<TARGET> of(TARGET... data) {
        return ImmutableFuncList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableFuncList<TARGET> AllOf(TARGET... data) {
        return ImmutableFuncList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableFuncList<TARGET> ListOf(TARGET... data) {
        return ImmutableFuncList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableFuncList<TARGET> listOf(TARGET... data) {
        return ImmutableFuncList.of(data);
    }
    
    /** Create a FuncList from the given array. */
    public static <TARGET> ImmutableFuncList<TARGET> from(TARGET[] datas) {
        return ImmutableFuncList.of(datas);
    }
    
    /** Create a FuncList from the given array. */
    public static ImmutableIntFuncList from(int[] datas) {
        return IntFuncList.of(datas);
    }
    
    /** Create a FuncList from the given array. */
    public static ImmutableDoubleFuncList from(double[] datas) {
        return ImmutableDoubleFuncList.of(datas);
    }
    
    /** Create a FuncList from the given collection. */
    public static <TARGET> FuncList<TARGET> from(Collection<TARGET> collection) {
        if (collection instanceof FuncList) {
            val funcList = (FuncList<TARGET>)collection;
            if (funcList.isEager()) {
                return funcList.toImmutableList();
            }
            
            return funcList;
        }
        
        return new FuncListDerived<>(collection, identity());
    }
    
    /** Create a FuncList from the given FuncList. */
    public static <TARGET> FuncList<TARGET> from(boolean isLazy, AsFuncList<TARGET> asFuncList) {
        val funcList = asFuncList.asFuncList();
        return isLazy ? funcList.lazy() :funcList.eager();
    }
    
    /** Create a FuncList from the given stream. */
    public static <TARGET> FuncList<TARGET> from(Stream<TARGET> stream) {
        return ImmutableFuncList.from(stream);
    }
    
    /**
     * Create a FuncList from the given supplier of stream.
     *
     * The provided stream should produce the same sequence of values.
     **/
    public static <TARGET> FuncList<TARGET> from(Supplier<Stream<TARGET>> supplier) {
        return new FuncListDerived<TARGET, TARGET>(()->StreamPlus.from(supplier.get()));
    }
    
    //== Create ==
    
    /** Returns a list that contains nulls. */
    public static <TARGET> FuncList<TARGET> nulls() {
        return FuncList.from(()->StreamPlus.nulls());
    }
    
    /** Returns a list that contains nulls. */
    public static <TARGET> FuncList<TARGET> nulls(Class<TARGET> dataClass) {
        return FuncList.from(()->StreamPlus.nulls(dataClass));
    }
    
    /** Create a list that is the repeat of the given array of data. */
    @SuppressWarnings("unchecked")
    public static <TARGET> FuncList<TARGET> repeat(TARGET ... data) {
        return FuncList.from(()->StreamPlus.repeat(data));
    }
    
    /** Create a list that is the repeat of the given list of data. */
    public static <TARGET> FuncList<TARGET> repeat(FuncList<TARGET> data) {
        return FuncList.from(()->StreamPlus.repeat(data));
    }
    
    /** Create a FuncList that is the repeat of the given array of data. */
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> cycle(TARGET ... data) {
        return FuncList.from(()->StreamPlus.cycle(data));
    }
    
    /** Create a FuncList that is the repeat of the given list of data. */
    public static <TARGET> FuncList<TARGET> cycle(Collection<TARGET> data) {
        return FuncList.from(()->StreamPlus.cycle(data));
    }
    
    /** Create a FuncList that for an infinite loop - the value is null */
    public static <TARGET> FuncList<TARGET> loop() {
        return FuncList.from(()->StreamPlus.loop());
    }
    
    /** Create a FuncList that for a loop with the number of time given - the value is the index of the loop. */
    public static <TARGET> FuncList<TARGET> loop(int times) {
        return FuncList.from(()->StreamPlus.loop(times));
    }
    
    /** Create a FuncList that for an infinite loop - the value is the index of the loop. */
    public static FuncList<Integer> infiniteInt() {
        return FuncList.from(()->StreamPlus.infiniteInt());
    }
    
    //-- Concat + Combine --
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> concat(FuncList<TARGET> ... list) {
        return combine(list);
    }
    
    /**
     * Concatenate all the given lists.
     *
     * This method is the alias of {@link FuncList#concat(FuncList...)}
     *   but allowing static import without colliding with {@link String#concat(String)}.
     **/
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> combine(FuncList<TARGET> ... lists) {
        ImmutableFuncList<FuncList<TARGET>> listOfList = FuncList.listOf(lists);
        return listOfList.flatMap(Func.itself());
    }
    
    // TODO - Rethink ... as this will generate un-repeatable stream.
    //          we may want to do cache here.
    
    /**
     * Create a FuncList from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> FuncList<TARGET> generate(Supplier<Supplier<TARGET>> supplier) {
        return FuncList.from(() -> {
            val generator = supplier.get();
            return StreamPlus.generate(generator);
        });
    }
    
    /**
     * Create a FuncList from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> FuncList<TARGET> generateWith(Supplier<Supplier<TARGET>> supplier) {
        return generate(supplier);
    }
    
    /**
     * Create a FuncList by apply the compounder to the seed over and over.
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
    public static <TARGET> FuncList<TARGET> iterate(
            TARGET                   seed,
            Function<TARGET, TARGET> compounder) {
        return FuncList.from(()->StreamPlus.iterate(seed, compounder));
    }
    
    /**
     * Create a FuncList by apply the compounder to the seed over and over.
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
    public static <TARGET> FuncList<TARGET> compound(
            TARGET                   seed,
            Function<TARGET, TARGET> compounder) {
        return FuncList.from(()->StreamPlus.compound(seed, compounder));
    }
    
    /**
     * Create a FuncList by apply the compounder to the seeds over and over.
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
    public static <TARGET> FuncList<TARGET> iterate(
            TARGET                             seed1,
            TARGET                             seed2,
            BiFunction<TARGET, TARGET, TARGET> compounder) {
        return FuncList.from(()->StreamPlus.iterate(seed1, seed2, compounder));
    }
    
    /**
     * Create a FuncList by apply the compounder to the seeds over and over.
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
    public static <TARGET> FuncList<TARGET> compound(
            TARGET                             seed1,
            TARGET                             seed2,
            BiFunction<TARGET, TARGET, TARGET> compounder) {
        return FuncList.from(()->StreamPlus.compound(seed1, seed2, compounder));
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
    public static <T1, T2> FuncList<Tuple2<T1, T2>> zipOf(
            List<T1> list1,
            List<T2> list2) {
        return FuncList.from(() -> StreamPlus.zipOf(list1.stream(), list2.stream()));
    }
    
    /**
     * Create a FuncList by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use FuncList1.zip(FuncList2).
     *
     * For example:
     *     list1 = [A, B, C, D, E]
     *     list2 = [1, 2, 3, 4]
     *     merger      = a + "+" + b
     *
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     **/
    public static <T1, T2, TARGET> FuncList<TARGET> zipOf(
            List<T1>                   list1,
            List<T2>                   list2,
            BiFunction<T1, T2, TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(list1.stream(), list2.stream(), merger);
        });
    }
    
    /**
     * Zip integers from two IntFuncLists and combine it into another object.
     * The result stream has the size of the shortest FuncList.
     */
    public static <T1, T2, TARGET> FuncList<TARGET> zipOf(
            AsIntFuncList            list1,
            AsIntFuncList            list2,
            IntIntBiFunction<TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(
                    list1.intStream(),
                    list2.intStream(),
                    merger);
        });
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> FuncList<TARGET> zipOf(
            AsIntFuncList                     list1,
            List<ANOTHER>                     list2,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(
                    list1.intStream(),
                    list2.stream(),
                    merger);
        });
    }
    
    /**
     * Zip integers from two IntFuncLists and combine it into another object.
     * The result stream has the size of the shortest FuncList.
     */
    public static <T1, T2, TARGET> FuncList<TARGET> zipOf(
            AsLongFuncList            list1,
            AsLongFuncList            list2,
            LongLongBiFunction<TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(
                    list1.longStream(),
                    list2.longStream(),
                    merger);
        });
    }
    
    /**
     * Zip integers from two IntFuncLists and combine it into another object.
     * The result stream has the size of the shortest FuncList.
     */
    public static <T1, T2, TARGET> FuncList<TARGET> zipOf(
            AsDoubleFuncList               list1,
            AsDoubleFuncList               list2,
            DoubleDoubleFunction<TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(
                    list1.doubleStream(),
                    list2.doubleStream(),
                    merger);
        });
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> FuncList<TARGET> zipOf(
            AsDoubleFuncList                     list1,
            List<ANOTHER>                        list2,
            DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        return FuncList.from(() -> {
            return StreamPlus.zipOf(
                        list1.doubleStream(),
                        list2.stream(),
                        merger);
        });
    }
    
    //-- Builder --
    
    /** Create a new FuncList. */
    public static <TARGET> FuncListBuilder<TARGET> newListBuilder() {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Create a new list builder. */
    public static <TARGET> FuncListBuilder<TARGET> newBuilder() {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Create a new FuncList. */
    public static <TARGET> FuncListBuilder<TARGET> newListBuilder(Class<TARGET> clz) {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Create a new list builder. */
    public static <TARGET> FuncListBuilder<TARGET> newBuilder(Class<TARGET> clz) {
        return new FuncListBuilder<TARGET>();
    }
    
    //== Core ==
    
    /** Return the stream of data behind this StreamPlus. */
    public StreamPlus<DATA> stream();
    
    
    /** Return the stream of data behind this IntFuncList. */
    public default StreamPlus<DATA> streamPlus() {
        return stream();
    }
    
    @Override
    public default FuncList<DATA> asFuncList() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a FuncList from the given FuncList. */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <SOURCE, TARGET> FuncList<TARGET> deriveFrom(
            List<SOURCE>                                 list,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        boolean isLazy 
                = (list instanceof FuncList)
                ? ((FuncList)list).isLazy()
                : true;
        
        if (!isLazy) {
            val orgStreamPlus = (list instanceof FuncList)
                    ? ((FuncList)list).streamPlus()
                    : StreamPlus.from(list.stream());
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = StreamPlus.from(newStream);
            return ImmutableFuncList.from(isLazy, newStreamPlus);
        }
        
        return FuncList.from(()->{
            val orgStreamPlus = (list instanceof FuncList)
                    ? ((FuncList)list).streamPlus()
                    : StreamPlus.from(list.stream());
            val newStream = action.apply(orgStreamPlus);
            return StreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given IntFuncList. */
    public static <TARGET> FuncList<TARGET> deriveFrom(
            AsIntFuncList                           asFuncList,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        boolean isLazy = asFuncList.asIntFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = StreamPlus.from(newStream);
            return ImmutableFuncList.from(isLazy, newStreamPlus);
        }
        
        return FuncList.from(() -> {
            val orgStreamPlus = asFuncList.intStreamPlus();
            val newStream = action.apply(orgStreamPlus);
            return StreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given IntFuncList. */
    public static <TARGET> FuncList<TARGET> deriveFrom(
            AsLongFuncList                           asFuncList,
            Function<LongStreamPlus, Stream<TARGET>> action) {
        boolean isLazy = asFuncList.asLongFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = asFuncList.longStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = StreamPlus.from(newStream);
            return ImmutableFuncList.from(isLazy, newStreamPlus);
        }
        
        return FuncList.from(() -> {
            val orgStreamPlus = asFuncList.longStreamPlus();
            val newStream = action.apply(orgStreamPlus);
            return StreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from the given DoubleFuncList. */
    public static <TARGET> FuncList<TARGET> deriveFrom(
            AsDoubleFuncList                           funcList,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        boolean isLazy = funcList.asDoubleFuncList().isLazy();
        if (!isLazy) {
            val orgStreamPlus = funcList.doubleStreamPlus();
            val newStream     = action.apply(orgStreamPlus);
            val newStreamPlus = StreamPlus.from(newStream);
            return ImmutableFuncList.from(isLazy, newStreamPlus);
        }
        
        return FuncList.from(() -> {
            val orgStreamPlus = funcList.doubleStreamPlus();
            val newStream = action.apply(orgStreamPlus);
            return StreamPlus.from(newStream);
        });
    }
    
    /** Create a FuncList from another FuncList. */
    public static <SOURCE> IntFuncList deriveToInt(
            List<SOURCE>                            list,
            Function<StreamPlus<SOURCE>, IntStream> action) {
        return IntFuncList.deriveFrom(list, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static <SOURCE> DoubleFuncList deriveToDouble(
            List<SOURCE>                               list,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        return DoubleFuncList.deriveFrom(list, action);
    }
    
    /** Create a FuncList from another FuncList. */
    public static <SOURCE, TARGET> FuncList<TARGET> deriveToObj(
            List<SOURCE>                                 list,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        return deriveFrom(list, action);
    }
    
    //-- Predicate --
    
    /** Test if the data is in the list */
    @Override
    public default boolean test(DATA value) {
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
    public FuncList<DATA> lazy();
    
    /** Return a eager list with the data of this list. */
    public FuncList<DATA> eager();
    
    /** Freeze the data of this list as an immutable list. */
    public default ImmutableFuncList<DATA> freeze() {
        return toImmutableList();
    }
    
    //-- Iterable --
    
    /** @return the iterable of this FuncList. */
    public default IterablePlus<DATA> iterable() {
        return () -> iterator();
    }
    
    //-- Iterator --
    
    /** @return a iterator of this list. */
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    /** @return a spliterator of this list. */
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    /** Map each value into a string value. */
    public default FuncList<String> mapToString() {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(i -> String.valueOf(i)));
    }
    
    /** Map each value into other value using the function. */
    public default <TARGET> FuncList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default LongFuncList mapToLong(ToLongFunction<? super DATA> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.mapToLong(mapper));
    }
    
    /** Map each value into a double value using the function. */
    public default DoubleFuncList mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(Function<? super DATA, ? extends TARGET> mapper) {
        return map(mapper);
    }
    
    //-- FlatMap --
    
    /** Map a value into a list and then flatten that list */
    public default <TARGET> FuncList<TARGET> flatMap(Function<? super DATA, ? extends FuncList<? extends TARGET>> mapper) {
        return deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).stream()));
    }
    
    /** Map a value into an integer list and then flatten that list */
    public default IntFuncList flatMapToInt(Function<? super DATA, ? extends AsIntFuncList> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
    }
    
    /** Map a value into an integer list and then flatten that list */
    public default LongFuncList flatMapToLong(Function<? super DATA, ? extends AsLongFuncList> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
    }
    
    /** Map a value into a double list and then flatten that list */
    public default DoubleFuncList flatMapToDouble(Function<? super DATA, ? extends AsDoubleFuncList> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default FuncList<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default FuncList<DATA> peek(Consumer<? super DATA> action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default FuncList<DATA> limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    public default FuncList<DATA> skip(long n) {
        return deriveFrom(this, stream -> stream.skip(n));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default FuncList<DATA> distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this list */
    public default FuncList<DATA> sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in the list using the given comparator */
    public default FuncList<DATA> sorted(Comparator<? super DATA> comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    //-- Terminate --
    
    /** Process each value using the given action */
    public default void forEach(Consumer<? super DATA> action) {
        stream()
        .forEach(action);
    }
    
    /**
     * Performs an action for each element of this stream, in the encounter
     * order of the stream if the stream has a defined encounter order.
     */
    public default void forEachOrdered(Consumer<? super DATA> action) {
        stream()
        .forEachOrdered(action);
    }
    
    //== Conversion ==
    
    /** @return the array list containing the elements. */
    @Eager
    @Terminal
    public default ArrayList<DATA> toArrayList() {
        val list = new ArrayList<DATA>(size());
        stream().forEach(list::add);
        return list;
    }
    
    @Override
    public default FuncList<DATA> toFuncList() {
        return this;
    }
    
    /** Convert this FuncList to an array. */
    public default Object[] toArray() {
        return stream()
                .toArray();
    }
    
    /** Assign the values in to the given array. */
    @SuppressWarnings("unchecked")
    @Override
    public default <T> T[] toArray(T[] a) {
        int count = size();
        if (a.length != count) {
            a = (T[])Array.newInstance(a.getClass().getComponentType(), count);
        }
        val array = a;
        forEachWithIndex((index, element) -> array[index] = (T)element);
        return array;
    }
    
    /** Convert this FuncList to an array. */
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream()
                .toArray(generator);
    }
    
    /** Returns a functional list builder with the initial data of this func list. */
    public default FuncListBuilder<DATA> toBuilder() {
        return new FuncListBuilder<DATA>(toArrayList());
    }
    
    //== Nullable, Optional and Result
    
    public default Nullable<FuncList<DATA>> __nullable() {
        return Nullable.of(this);
    }
    
    public default Optional<FuncList<DATA>> __optional() {
        return Optional.of(this);
    }
    
    public default Result<FuncList<DATA>> __result() {
        return Result.valueOf(this);
    }
    
    // -- List specific --
    
    public default int size() {
        return (int)stream().count();
    }
    
    /** Find any indexes that the elements match the predicate */
    public default FuncList<Integer> indexesOf(Predicate<? super DATA> predicate) {
        return this
                .mapWithIndex((index, data) -> predicate.test(data) ? index : -1)
                .filter($I.thatNotEqualsTo(-1))
                .toImmutableList();
    }
    
    /** Find the first index of the given object. */
    @Override
    public default int indexOf(Object o) {
        return indexesOf(each -> Objects.equals(o, each))
                .findFirst().orElse(-1);
    }
    
    /** Returns the first element. */
    public default Optional<DATA> first() {
        return stream()
                .limit(1)
                .findFirst();
    }
    
    /** Returns the first elements */
    public default FuncList<DATA> first(int count) {
        return limit(count);
    }
    
    /** Returns the last element. */
    public default Optional<DATA> last() {
        return last(1)
                .findFirst();
    }
    
    /** Returns the last elements */
    public default FuncList<DATA> last(int count) {
        val size  = size();
        val index = Math.max(0, size - count);
        return skip(index);
    }
    
    /** Returns the element at the index. */
    public default Optional<DATA> at(int index) {
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the second to the last elements. */
    public default FuncList<DATA> tail() {
        return skip(1);
    }
    
    /**
     * Add the given value to the end of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     **/
    public default FuncList<DATA> append(DATA value) {
        return deriveFrom(this, stream -> Stream.concat(stream, Stream.of(value)));
    }
    
    /** Add the given values to the end of the list. */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> appendAll(DATA ... values) {
        return deriveFrom(this, stream -> Stream.concat(stream, Stream.of(values)));
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default FuncList<DATA> appendAll(Collection<? extends DATA> collection) {
        return (collection == null)
                ? this
                : deriveFrom(this, stream -> Stream.concat(stream, collection.stream()));
    }
    
    /**
     * Add the given value to the beginning of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     **/
    public default FuncList<DATA> prepend(DATA value) {
        return deriveFrom(this, stream -> Stream.concat(Stream.of(value), stream));
    }
    
    /** Add the given values to the beginning of the list */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> prependAll(DATA ... values) {
        return deriveFrom(this, stream -> Stream.concat(Stream.of(values), stream));
    }
    
    /** Add the given value in the collection to the beginning of the list */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> prependAll(Collection<? extends DATA> collection) {
        if (collection == null)
            return this;
        
        val prefix = FuncList.from((Collection<DATA>)collection);
        return FuncList.concat(prefix, this);
    }
    
    
    /** Returns a new functional list with the value replacing at the index. */
    public default FuncList<DATA> with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, orgValue) -> ((i == index) ? value : orgValue));
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default FuncList<DATA> with(int index, Function<DATA, DATA> mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return mapWithIndex((i, value) -> {
            return (i == index) ? mapper.apply(value) : value;
        });
    }
    
    /**
     * Returns a new list with the given elements inserts into at the given index.
     * 
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     **/
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> insertAt(int index, DATA... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        val first      = limit(index);
        val tail       = skip(index);
        return FuncList.concat(first, FuncList.of(elements), tail);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null) || collection.isEmpty())
            return this;
        
        val first  = limit(index);
        val middle = FuncList.from((Collection<DATA>)collection);
        val tail   = skip(index);
        return FuncList.concat(first, middle, tail);
    }
    
    /** Returns the new list from this list without the element. */
    public default FuncList<DATA> exclude(Object element) {
        return filter(each -> !Objects.equals(each, element));
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default FuncList<DATA> excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        val first  = limit(index);
        val tail   = skip(index + 1);
        return FuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default FuncList<DATA> excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        val first  = limit(fromIndexInclusive);
        val tail   = skip(fromIndexInclusive + count);
        return FuncList.concat(first, tail);
    }
    
    /** Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`. */
    public default FuncList<DATA> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
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
        return FuncList.concat(first, tail);
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    @Override
    public default FuncList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive)
                .limit(length);
    }
    
    /** Returns the new list with reverse order of this list. */
    @Eager
    public default FuncList<DATA> reverse() {
        val temp = this.toArrayList();
        Collections.reverse(temp);
        
        val list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    /** Returns the new list with random order of this list. */
    @Eager
    public default FuncList<DATA> shuffle() {
        val temp = this.toArrayList();
        Collections.shuffle(temp);
        
        val list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    //-- Query --
    
    /** Returns the list of tuple of the index and the value for which the value match the predicate. */
    public default FuncList<IntTuple2<DATA>> query(Predicate<? super DATA> predicate) {
        return this
                .mapWithIndex((index, data) -> predicate.test(data) ? new IntTuple2<DATA>(index, data) : null)
                .filterNonNull();
    }
    
    //-- de-ambiguous --
    
    /** @return an list containing the elements. */
    public default List<DATA> toJavaList() {
        return stream()
                .toJavaList();
    }
    
    /** @return an immutable list containing the elements. */
    public default ImmutableFuncList<DATA> toImmutableList() {
        return stream()
                .toImmutableList();
    }
    
}
