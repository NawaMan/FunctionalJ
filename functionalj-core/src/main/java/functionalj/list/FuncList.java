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
package functionalj.list;

import static functionalj.function.Func.alwaysTrue;
import static functionalj.lens.Access.$I;
import static java.util.function.Function.identity;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.result.Result;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.tuple.IntTuple2;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;
import nullablej.nullable.Nullable;


public interface FuncList<DATA>
        extends
            ReadOnlyList<DATA>,
            Predicate<DATA>,
            FuncListWithCalculate<DATA>,
            FuncListWithCombine<DATA>,
            FuncListWithFillNull<DATA>,
            FuncListWithFilter<DATA>,
            FuncListWithFlatMap<DATA>,
            FuncListWithForEach<DATA>,
            FuncListWithGroupingBy<DATA>,
            FuncListWithLimit<DATA>,
            FuncListWithMap<DATA>,
            FuncListWithMapFirst<DATA>,
            FuncListWithMapThen<DATA>,
            FuncListWithMapToMap<DATA>,
            FuncListWithMapToTuple<DATA>,
            FuncListWithMapWithIndex<DATA>,
            FuncListWithMapWithPrev<DATA>,
            FuncListWithModify<DATA>,
            FuncListWithPeek<DATA>,
            FuncListWithPipe<DATA>,
            FuncListWithReshape<DATA>,
            FuncListWithSort<DATA>,
            FuncListWithSplit<DATA> {
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableList<TARGET> empty() {
        return ImmutableList.empty();
    }
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableList<TARGET> emptyList() {
        return ImmutableList.empty();
    }
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableList<TARGET> empty(Class<TARGET> elementClass) {
        return ImmutableList.empty();
    }
    
    /** Returns an empty FuncList. */
    public static <TARGET> ImmutableList<TARGET> emptyList(Class<TARGET> elementClass) {
        return ImmutableList.empty();
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableList<TARGET> of(TARGET... data) {
        return ImmutableList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableList<TARGET> AllOf(TARGET... data) {
        return ImmutableList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableList<TARGET> ListOf(TARGET... data) {
        return ImmutableList.of(data);
    }
    
    /** Create a FuncList from the given data. */
    @SafeVarargs
    public static <TARGET> ImmutableList<TARGET> listOf(TARGET... data) {
        return ImmutableList.of(data);
    }
    
    // TODO - Function to create FuncList from function of Array
    
    /** Create a FuncList from the given array. */
    public static <TARGET> ImmutableList<TARGET> from(TARGET[] datas) {
        return ImmutableList.from(datas);
    }
    
    /** Create a FuncList from the given array. */
    public static ImmutableIntFuncList from(int[] datas) {
        return ImmutableIntFuncList.from(datas);
    }
    
    /** Create a FuncList from the given collection. */
    public static <TARGET> ImmutableList<TARGET> from(Collection<TARGET> data) {
        return ImmutableList.from(data);
    }
    
    /** Create a FuncList from the given list. */
    public static <TARGET> ImmutableList<TARGET> from(List<TARGET> data) {
        return ImmutableList.from(data);
    }
    
    /** Create a FuncList from the given streamable. */
    public static <TARGET> FuncList<TARGET> from(AsStreamable<TARGET> streamable) {
        if (streamable instanceof FuncList)
            return (FuncList<TARGET>)streamable;
        
        return new FuncListDerived<TARGET, TARGET>(streamable, identity());
    }
    
    /** Create a FuncList from the given stream. */
    public static <TARGET> ImmutableList<TARGET> from(Stream<TARGET> stream) {
        return new ImmutableList<TARGET>(stream.collect(Collectors.toList()));
    }
    
    /** Create a FuncList from the given collection. */
    public static <TARGET> ImmutableList<TARGET> from(FuncList<TARGET> data) {
        return ImmutableList.from(data);
    }
    
    /** Create a new FuncList. */
    public static <TARGET> FuncListBuilder<TARGET> newFuncList() {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Create a new list. */
    public static <TARGET> FuncListBuilder<TARGET> newList() {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Create a new list builder. */
    public static <TARGET> FuncListBuilder<TARGET> newBuilder() {
        return new FuncListBuilder<TARGET>();
    }
    
    /** Concatenate all the given streams. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> concat(FuncList<TARGET> ... list) {
        Streamable[] array = (Streamable[])FuncList.listOf(list).map(FuncList::streamable).toArray();
        return FuncList.from(Streamable.concat(array));
    }
    
    /**
     * Concatenate all the given lists.
     *
     * This method is the alias of {@link FuncList#concat(FuncList...)}
     *   but allowing static import without colliding with {@link String#concat(String)}.
     **/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> combine(FuncList<TARGET> ... lists) {
        Streamable[] array = (Streamable[])FuncList.listOf(lists).map(FuncList::streamable).toArray();
        return FuncList.from(Streamable.combine(array));
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> FuncList<TARGET> generate(Func0<Func0<TARGET>> supplier) {
        return FuncList.from(Streamable.generate(supplier));
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> FuncList<TARGET> generateWith(Func0<Func0<TARGET>> supplier) {
        return FuncList.from(Streamable.generateWith(supplier));
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
    public static <TARGET> FuncList<TARGET> iterate(
            TARGET                seed,
            UnaryOperator<TARGET> compounder) {
        return FuncList.from(Streamable.iterate(seed, compounder));
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
    public static <TARGET> FuncList<TARGET> compound(
            TARGET                seed,
            UnaryOperator<TARGET> compounder) {
        return FuncList.from(Streamable.compound(seed, compounder));
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
    public static <TARGET> FuncList<TARGET> iterate(
            TARGET                 seed1,
            TARGET                 seed2,
            BinaryOperator<TARGET> compounder) {
        return FuncList.from(Streamable.iterate(seed1, seed2, compounder));
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
    public static <TARGET> FuncList<TARGET> compound(
            TARGET                 seed1,
            TARGET                 seed2,
            BinaryOperator<TARGET> compounder) {
        return FuncList.from(Streamable.compound(seed1, seed2, compounder));
    }
    
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
    public static <T1, T2> FuncList<Tuple2<T1, T2>> zipOf(
            List<T1> list1,
            List<T2> list2) {
        return FuncList.from(Streamable.zipOf(Streamable.from(list1), Streamable.from(list2)));
    }
    
    /**
     * Create a Streamable by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use streamable1.zip(streamable2).
     *
     * For example:
     *     streamable1 = [A, B, C, D, E]
     *     streamable2 = [1, 2, 3, 4]
     *     merger      = a + "+" + b
     *
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     **/
    public static <T1, T2, TARGET> FuncList<TARGET> zipOf(
            FuncList<T1>          list1,
            FuncList<T2>          list2,
            Func2<T1, T2, TARGET> merger) {
        return FuncList.from(Streamable.zipOf(Streamable.from(list1), Streamable.from(list2), merger));
    }
//    
//    /**
//     * Zip integers from two IntStreamables and combine it into another object.
//     * The result stream has the size of the shortest streamable.
//     */
//    public static <T1, T2, TARGET> Streamable<TARGET> zipOf(
//            IntStreamable            streamable1,
//            IntStreamable            streamable2,
//            IntIntBiFunction<TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.intStream(),
//                    streamable2.intStream(),
//                    merger);
//        };
//    }
//    
//    /** Zip integers from two IntStreamables and combine it into another object. */
//    public static <TARGET> Streamable<TARGET> zipOf(
//            IntStreamable            streamable1,
//            IntStreamable            streamable2,
//            int                      defaultValue,
//            IntIntBiFunction<TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.intStream(),
//                    streamable2.intStream(),
//                    defaultValue,
//                    merger);
//        };
//    }
//    
//    /** Zip integers from two IntStreams and combine it into another object. */
//    public static <TARGET> Streamable<TARGET> zipOf(
//            IntStreamable            streamable1,
//            int                      defaultValue1,
//            IntStreamable            streamable2,
//            int                      defaultValue2,
//            IntIntBiFunction<TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.intStream(), defaultValue1,
//                    streamable2.intStream(), defaultValue2,
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip integers from an int stream and another object stream and combine it into another object.
//     * The result stream has the size of the shortest stream.
//     */
//    public static <ANOTHER, TARGET> Streamable<TARGET> zipOf(
//            IntStreamable                     streamable1,
//            Streamable<ANOTHER>               streamable2,
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.intStream(),
//                    streamable2.stream(),
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip integers from an int stream and another object stream and combine it into another object.
//     * The default value will be used if the first stream ended first and null will be used if the second stream ended first.
//     */
//    public static <ANOTHER, TARGET> Streamable<TARGET> zipOf(
//            IntStreamable                     streamable1,
//            int                               defaultValue,
//            Streamable<ANOTHER>               streamable2,
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.intStream(),
//                    streamable2.stream(),
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip longs from two LongStreams and combine it into another object.
//     * The result stream has the size of the shortest stream.
//     */
//    public static <TARGET> Streamable<TARGET> zipOf(
//            LongStreamable       streamable1,
//            LongStreamable       streamable2,
//            LongLongBiFunction<TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.longStream(),
//                    streamable2.longStream(),
//                    merger);
//        };
//    }
//    
//    /** Zip longs from two LongStreamables and combine it into another object. */
//    public static <T> Streamable<T> zipOf(
//            LongStreamable       streamable1,
//            LongStreamable       streamable2,
//            int                 defaultValue,
//            LongLongBiFunction<T> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.longStream(),
//                    streamable2.longStream(),
//                    defaultValue,
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip values from a long streamable and another object streamable and combine it into another object.
//     * The result stream has the size of the shortest stream.
//     */
//    public static <TARGET> Streamable<TARGET> zipOf(
//            LongStreamable             streamable1,
//            long                       defaultValue1,
//            LongStreamable             streamable2,
//            long                       defaultValue2,
//            LongLongBiFunction<TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.longStream(), defaultValue1,
//                    streamable2.longStream(), defaultValue2,
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip values from a long streamable and another object streamable and combine it into another object.
//     * The result stream has the size of the shortest stream.
//     */
//    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
//            LongStreamable                     streamable1,
//            Streamable<ANOTHER>                streamable2,
//            LongObjBiFunction<ANOTHER, TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.longStream(),
//                    streamable2.stream(),
//                    merger);
//        };
//    }
//    
//    /**
//     * Zip values from an long streamable and another object streamable and combine it into another object.
//     * The default value will be used if the first streamable ended first and null will be used if the second stream ended first.
//     */
//    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
//            LongStreamable                     streamable1,
//            long                               defaultValue,
//            Streamable<ANOTHER>                streamable2,
//            LongObjBiFunction<ANOTHER, TARGET> merger) {
//        return ()->{
//            return StreamPlus.zipOf(
//                    streamable1.longStream(), defaultValue,
//                    streamable2.stream(),
//                    merger);
//        };
//    }
    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SuppressWarnings("unchecked")
    public static <TARGET> FuncList<TARGET> repeat(TARGET ... data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <TARGET> FuncList<TARGET> repeat(FuncList<TARGET> data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SafeVarargs
    public static <TARGET> FuncList<TARGET> cycle(TARGET ... data) {
        return FuncList.from(Streamable.cycle(data));
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <TARGET> FuncList<TARGET> cycle(FuncList<TARGET> data) {
        return FuncList.from(Streamable.cycle(data));
    }
    
    /** Create a FuncList that for a loop with the number of time given - the value is the index of the loop. */
    public static FuncList<Boolean> loop(int time) {
        return FuncList.from(Streamable.loop(time));
    }
    
    //== Core ==
    
    /** Return the stream of data behind this StreamPlus. */
    public StreamPlus<DATA> stream();
    
    /** Return this StreamPlus. */
    public default StreamPlus<DATA> streamPlus() {
        return stream();
    }
    
    /** Return the this as a streamable. */
    public default Streamable<DATA> streamable() {
        return () -> this.stream();
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE, TARGET> FuncList<TARGET> deriveFrom(
            AsStreamable<SOURCE>                         asStreamable,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        var sourceStream = asStreamable.streamPlus();
        var targetStream = action.apply(sourceStream);
        return FuncList.from(targetStream);
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> FuncList<TARGET> deriveFrom(
            AsIntStreamable                         asStreamable,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        var sourceStream = asStreamable.intStreamPlus();
        var targetStream = action.apply(sourceStream);
        return FuncList.from(targetStream);
    }
//    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> FuncList<TARGET> deriveFrom(
//            AsLongStreamable                         asStreamable,
//            Function<LongStreamPlus, Stream<TARGET>> action) {
//        var sourceStream = asStreamable.longStream();
//        var targetStream = action.apply(sourceStream);
//        return FuncList.from(targetStream);
//    }
//    
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> FuncList<TARGET> deriveFrom(
//            AsDoubleStreamable                         asStreamable,
//            Function<DoubleStreamPlus, Stream<TARGET>> action) {
//        var sourceStream = asStreamable.doubleStream();
//        var targetStream = action.apply(sourceStream);
//        return FuncList.from(targetStream);
//    }
//  
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> IntFuncList deriveToInt(
//            AsStreamable<SOURCE>                    asStreamable,
//            Function<StreamPlus<SOURCE>, IntStream> action) {
////        return IntFuncList.deriveFrom(asStreamable, action);
//        return null;
//    }
//  
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> LongFuncList deriveToLong(
//            AsStreamable<SOURCE>                     asStreamable,
//            Function<StreamPlus<SOURCE>, LongStream> action) {
////        return LongFuncList.deriveFrom(asStreamable, action);
//        return null;
//    }
//  
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> DoubleFuncList deriveToDouble(
//            AsStreamable<SOURCE>                       asStreamable,
//            Function<StreamPlus<SOURCE>, DoubleStream> action) {
////        return DoubleFuncList.deriveFrom(asStreamable, action);
//        return null;
//    }
//    
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE, TARGET> FuncList<TARGET> deriveToObj(
//            AsStreamable<SOURCE>                         asStreamable,
//            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
//        return deriveFrom(asStreamable, action);
//    }
    
    /** Test if the data is in the list */
    @Override
    public default boolean test(DATA data) {
        return contains(data);
    }
    
    /** Check if this is a lazy list */
    public default boolean isLazy() {
        return true;
    }
    
    /** Check if this is an eager list */
    public default boolean isEager() {
        return false;
    }
    
    /** Create a lazy list from this list */
    public FuncList<DATA> lazy();
    
    /** Create a eager list from this list */
    public FuncList<DATA> eager();
    
    @Override
    public default FuncList<DATA> toFuncList() {
        return this;
    }
    
    /** Create an immutable list freezing the values in this list. */
    public default ImmutableList<DATA> freeze() {
        return toImmutableList();
    }
    
    //-- Iterator --
    
    /** @return a iterator of this list. */
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    /** @return a spliterator of this list. */
    public default Spliterator<DATA> spliterator() {
        var iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    /** Map each value into other value using the function. */
    public default <TARGET> FuncList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntFuncList mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
//    /** Map each value into a long value using the function. */
//    public default LongFuncList mapToLong(ToLongFunction<? super DATA> mapper) {
//        return LongFuncList.deriveFrom(this, stream -> stream.mapToLong(mapper));
//    }
//  
//    /** Map each value into a double value using the function. */
//    public default DoubleFuncList mapToDouble(ToDoubleFunction<? super DATA> mapper) {
//        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(mapper));
//    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(Function<? super DATA, ? extends TARGET> mapper) {
        return map(mapper);
    }
    
    //-- FlatMap --
    /** Map a value into a list and then flatten that list */
    public default <TARGET> FuncList<TARGET> flatMap(Function<? super DATA, ? extends FuncList<? extends TARGET>> mapper) {
        return deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).stream()));
    }
    
    /** Map a value into an integer list and then flatten that list */
    public default IntFuncList flatMapToInt(Function<? super DATA, ? extends IntFuncList> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
    }
    
//    /** Map a value into a long list and then flatten that list */
//    public default LongFuncList flatMapToLong(Function<? super DATA, ? extends LongFuncList> mapper) {
//        return LongStreamable.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
//    }
//  
//    /** Map a value into a double list and then flatten that list */
//    public default DoubleFuncList flatMapToDouble(Function<? super DATA, ? extends DoubleFuncList> mapper) {
//        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
//    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default FuncList<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    @Override
    public default <T> FuncList<DATA> filter(
            Function<? super DATA, T> mapper,
            Predicate<? super T>      predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
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
    
    /** Convert this streamable to an array. */
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
        var array = a;
        forEachWithIndex((index, element) -> array[index] = (T)element);
        return array;
    }
    
    /** Convert this streamable to an array. */
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream()
                .toArray(generator);
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
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    /** Returns the first element. */
    public default Optional<DATA> first() {
        var valueRef = new AtomicReference<DATA>();
        if (!StreamPlusHelper.hasAt(stream(), 0, valueRef))
            return Optional.empty();
        
        return Optional.ofNullable(valueRef.get());
    }
    
    /** Returns the first elements */
    public default FuncList<DATA> first(int count) {
        var size  = size();
        var index = Math.max(0, size - count);
        return skip(index);
    }
    
    /** Returns the last element. */
    public default Optional<DATA> last() {
        var size = this.size();
        if (size <= 0)
            return Optional.empty();
        
        return Optional.ofNullable(get(size - 1));
    }
    
    /** Returns the last elements */
    public default FuncList<DATA> last(int count) {
        return limit(count);
    }
    
    /** Returns the element at the index. */
    public default Optional<DATA> at(int index) {
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    /** Returns the second to the last elements. */
    public default FuncList<DATA> rest() {
        return skip(1);
    }
    
    /** Add the given value to the end of the list. */
    public default FuncList<DATA> append(DATA value) {
        return deriveFrom(this, stream -> Stream.concat(stream, Stream.of(value)));
    }
    
    /** Add the given values to the end of the list. */
    public default FuncList<DATA> appendAll(DATA[] values) {
        return deriveFrom(this, stream -> Stream.concat(stream, Stream.of(values)));
    }
    
    /** Add the given value in the collection to the end of the list. */
    public default FuncList<DATA> appendAll(Collection<? extends DATA> collection) {
        return ((collection == null) || collection.isEmpty())
                ? this
                : deriveFrom(this, stream -> Stream.concat(stream, collection.stream()));
    }
    
    /** Add the given value in the streamable to the end of the list */
    public default FuncList<DATA> appendAll(Streamable<? extends DATA> streamable) {
        return (streamable == null)
                ? this
                : deriveFrom(this, stream -> Stream.concat(stream, streamable.stream()));
    }
    
    /** Add the given values from the supplier to the end of the list. */
    public default FuncList<DATA> appendAll(Supplier<Stream<? extends DATA>> supplier) {
        return (supplier == null)
                ? this
                : deriveFrom(this, stream -> Stream.concat(stream, supplier.get()));
    }
    
    /** Add the given value to the beginning of the list */
    public default FuncList<DATA> prepend(DATA value) {
        return deriveFrom(this, stream -> Stream.concat(Stream.of(value), stream));
    }
    
    /** Add the given values to the beginning of the list */
    public default FuncList<DATA> prependAll(DATA[] values) {
        return deriveFrom(this, stream -> Stream.concat(Stream.of(values), stream));
    }
    
    /** Add the given value in the collection to the beginning of the list */
    public default FuncList<DATA> prependAll(Collection<? extends DATA> collection) {
        return ((collection == null) || collection.isEmpty())
                ? this
                : deriveFrom(this, stream -> Stream.concat(collection.stream(), stream));
    }
    
    /** Add the given value in the streamable to the beginning of the list */
    public default FuncList<DATA> prependAll(Streamable<? extends DATA> prefixStreamable) {
        if (prefixStreamable == null)
            return this;
        
        var streamable = Streamable.concat(prefixStreamable, streamable());
        return from(streamable);
    }
    
    /** Add the given values from the supplier to the beginning of the list. */
    public default FuncList<DATA> prependAll(Supplier<Stream<? extends DATA>> supplier) {
        return (supplier == null)
                ? this
                : deriveFrom(this, stream -> Stream.concat(supplier.get(), stream));
    }
    
    /** Returns a new functional list with the value replacing at the index. */
    public default FuncList<DATA> with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return from((Streamable<DATA>)(() -> {
            var i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? value : each)
                    .stream();
        }));
    }
    
    /** Returns a new functional list with the new value (calculated from the mapper) replacing at the index. */
    public default FuncList<DATA> with(int index, Function<DATA, DATA> mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());

        return from((Streamable<DATA>)(() -> {
            var i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? mapper.apply(each) : each)
                    .stream();
        }));
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> insertAt(int index, DATA... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        var first      = streamable().limit(index);
        var tail       = streamable().skip(index);
        var streamable = Streamable.concat(first, Streamable.of(elements), tail);
        return from(streamable);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default FuncList<DATA> insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null) || collection.isEmpty())
            return this;
        
        var first  = streamable().limit(index);
        var middle = Streamable.from(collection);
        var tail   = streamable().skip(index);
        var streamable = Streamable.concat(first, middle, tail);
        return from(streamable);
    }
    
    /** Returns a new list with the given elements inserts into at the given index. */
    public default FuncList<DATA> insertAllAt(int index, AsStreamable<? extends DATA> theStreamable) {
        if (theStreamable == null)
            return this;
        
        var first  = streamable().limit(index);
        var middle = theStreamable.streamable();
        var tail   = streamable().skip(index);
        var streamable = Streamable.concat(first, middle, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the element. */
    public default FuncList<DATA> exclude(Object element) {
        return filter(each -> !Objects.equals(each, element));
    }
    
    /** Returns the new list from this list without the element at the index. */
    public default FuncList<DATA> excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        var first  = streamable().limit(index);
        var tail   = streamable().skip(index + 1);
        var streamable = Streamable.concat(first, tail);
        return from(streamable);
    }
    
    /** Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`. */
    public default FuncList<DATA> excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        var first  = streamable().limit(fromIndexInclusive);
        var tail   = streamable().skip(fromIndexInclusive + count);
        var streamable = Streamable.concat(first, tail);
        return from(streamable);
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
        
        var first  = streamable().limit(fromIndexInclusive);
        var tail   = streamable().skip(toIndexExclusive + 1);
        return from(Streamable.concat(first, tail));
    }
    
    /** Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`. */
    @Override
    public default FuncList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        var length = toIndexExclusive - fromIndexInclusive;
        return new FuncListDerived<>(this, stream -> stream.skip(fromIndexInclusive).limit(length));
    }
    
    /** Returns the new list with reverse order of this list. */
    // Note - Eager
    public default FuncList<DATA> reverse() {
        var temp = this.toMutableList();
        Collections.reverse(temp);
        
        var list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    /** Returns the new list with random order of this list. */
    // Note - Eager
    public default FuncList<DATA> shuffle() {
        var temp = this.toMutableList();
        Collections.shuffle(temp);
        
        var list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    /** Returns the list of tuple of the index and the value for which the value match the predicate. */
    public default FuncList<IntTuple2<DATA>> query(Predicate<? super DATA> predicate) {
        return this
                .mapWithIndex((index, data) -> predicate.test(data) ? new IntTuple2<DATA>(index, data) : null)
                .filterNonNull();
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Func1<DATA, D> mapper) {
        return minIndexBy(alwaysTrue(), mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Func1<DATA, D> mapper) {
        return maxIndexBy(alwaysTrue(), mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(
            Predicate<DATA> filter,
            Func1<DATA, D>  mapper) {
        return stream()
                .mapWithIndex(Tuple::of)
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> t._1);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(
            Predicate<DATA> filter,
            Func1<DATA, D>  mapper) {
        return stream()
                .mapWithIndex(Tuple::of)
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> t._1);
    }
    
    public default int size() {
        return (int)stream().count();
    }
    
    //-- de-ambiguous --
    
    /** @return an list containing the elements. */
    public default List<DATA> toJavaList() {
        return stream()
                .toJavaList();
    }
    
    /** @return an immutable list containing the elements. */
    public default ImmutableList<DATA> toImmutableList() {
        return stream()
                .toImmutableList();
    }
    
}
