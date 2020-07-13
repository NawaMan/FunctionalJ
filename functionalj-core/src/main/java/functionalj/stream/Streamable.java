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
package functionalj.stream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.doublestream.AsDoubleStreamable;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamable;
import functionalj.stream.intstream.AsIntStreamable;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.longstream.AsLongStreamable;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamable;
import functionalj.tuple.Tuple2;
import lombok.val;

// TODO - Add intersect (retain) - but might want to do it after sort.

class S implements Streamable<String> {

    @Override
    public StreamPlus<String> stream() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

/**
 * Classes implementing this interface can create streams.
 * The streams must be fully repeatable - each created stream should have the same value.
 * 
 * @param <DATA>  the data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Streamable<DATA> 
        extends
            AsStreamable<DATA>,
            StreamableWithConversion<DATA>,
            StreamableWithFilter<DATA>,
            StreamableWithFlatMap<DATA>,
            StreamableWithForEach<DATA>,
            StreamableWithLimit<DATA>,
            StreamableWithMap<DATA>,
            StreamableWithMatch<DATA>,
            StreamableWithPeek<DATA>,
            StreamableWithSort<DATA>,
            StreamableWithStatistic<DATA>,
            StreamableWithCombine<DATA>,
            StreamableWithCalculate<DATA>,
            StreamableWithFillNull<DATA>,
            StreamableWithGroupingBy<DATA>,
            StreamableWithMapFirst<DATA>,
            StreamableWithMapThen<DATA>,
            StreamableWithMapToMap<DATA>,
            StreamableWithMapToTuple<DATA>,
            StreamableWithMapWithIndex<DATA>,
            StreamableWithMapWithPrev<DATA>,
            StreamableWithModify<DATA>,
            StreamableWithPipe<DATA>,
            StreamableWithReshape<DATA>,
            StreamableWithSplit<DATA> {
    
    /** Throw a no more element exception. This is used for generator. */
    public static <TARGET> TARGET noMoreElement() throws NoMoreResultException {
        return StreamPlus.noMoreElement();
    }
    
    //== Constructor ==
    
    /** Returns an empty Streamable. */
    public static <TARGET> Streamable<TARGET> empty() {
        return ()->StreamPlus.empty();
    }
    
    /** Returns an empty Streamable. */
    public static <TARGET> Streamable<TARGET> emptyStreamable() {
        return ()->StreamPlus.empty();
    }
    
    /** Create a Streamable from the given data. */
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> of(TARGET ... data) {
        return ()->StreamPlus.of(data);
    }
    
    /** Create a Streamable from the given data. */
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> steamableOf(TARGET ... data) {
        val list = Arrays.asList(data);
        return Streamable.from(list);
    }
    
    /** Create a Streamable from the given collection. */
    public static <TARGET> Streamable<TARGET> from(Collection<TARGET> collection) {
        return ()->StreamPlus.from(collection.stream());
    }
    
    /**
     * Create a Streamable from the given supplier of stream.
     * 
     * The provided stream should produce the same sequence of values.
     **/
    public static <TARGET> Streamable<TARGET> from(Func0<Stream<TARGET>> supplier) {
        return ()->StreamPlus.from(supplier.get());
    }
    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SuppressWarnings("unchecked")
    public static <TARGET> Streamable<TARGET> repeat(TARGET ... data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <TARGET> Streamable<TARGET> repeat(FuncList<TARGET> data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> cycle(TARGET ... data) {
        val size = data.length;
        return () ->
                StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data[i % size]));
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <TARGET> Streamable<TARGET> cycle(FuncList<TARGET> data) {
        val size = data.size();
        return () ->
                StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data.get(i % size)));
    }
    
    /** Create a Streamable that for an infinite loop - the value is boolean true */
    public static Streamable<Boolean> loop() {
        return ()-> {
            return StreamPlus.from(Stream.generate(() -> Boolean.TRUE));
        };
    }
    
    /** Create a Streamable that for a loop with the number of time given - the value is the index of the loop. */
    public static Streamable<Boolean> loop(int time) {
        return Streamable.loop().limit(time);
    }
    
    /** Create a Streamable that for an infinite loop - the value is the index of the loop. */
    public static Streamable<Integer> infiniteInt() {
        return IntStreamable
                .wholeNumbers()
                .boxed();
    }
    
    /** Create a Streamable that for a loop from the start value inclusively to the end value exclusively. */
    public static Streamable<Integer> range(int startInclusive, int endExclusive) {
        return IntStreamable
                .range(startInclusive, endExclusive)
                .boxed();
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> concat(Streamable<TARGET> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /**
     * Concatenate all the given streamables.
     * 
     * This method is the alias of {@link Streamable#concat(Streamable...)} 
     *   but allowing static import without colliding with {@link String#concat(String)}.
     **/
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> combine(Streamable<TARGET> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /**
     * Create a Streamable from the supplier of supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> Streamable<TARGET> generate(Func0<Func0<TARGET>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    /**
     * Create a Streamable from the supplier of supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> Streamable<TARGET> generateWith(Func0<Func0<TARGET>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
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
    public static <TARGET> Streamable<TARGET> iterate(
            TARGET                seed,
            UnaryOperator<TARGET> compounder) {
        return ()->StreamPlus.iterate(seed, compounder);
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
    public static <TARGET> Streamable<TARGET> compound(
            TARGET                seed,
            UnaryOperator<TARGET> compounder) {
        return ()->StreamPlus.compound(seed, compounder);
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
    public static <TARGET> Streamable<TARGET> iterate(
            TARGET                 seed1,
            TARGET                 seed2,
            BinaryOperator<TARGET> compounder) {
        return ()->StreamPlus.iterate(seed1, seed2, compounder);
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
    public static <TARGET> Streamable<TARGET> compound(
            TARGET                 seed1,
            TARGET                 seed2,
            BinaryOperator<TARGET> compounder) {
        return ()->StreamPlus.compound(seed1, seed2, compounder);
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE, TARGET> Streamable<TARGET> deriveFrom(
            AsStreamable<SOURCE>                         asStreamable,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.stream();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> Streamable<TARGET> deriveFrom(
            AsIntStreamable                         asStreamable,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.intStream();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given LongStreamable. */
    public static <TARGET> Streamable<TARGET> deriveFrom(
            AsLongStreamable                         asStreamable,
            Function<LongStreamPlus, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.longStream();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given LongStreamable. */
    public static <TARGET> Streamable<TARGET> deriveFrom(
            AsDoubleStreamable                         asStreamable,
            Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.doubleStream();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> IntStreamable deriveToInt(
            AsStreamable<SOURCE>                    asStreamable,
            Function<StreamPlus<SOURCE>, IntStream> action) {
        return IntStreamable.deriveFrom(asStreamable, action);
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> LongStreamable deriveToLong(
            AsStreamable<SOURCE>                     asStreamable,
            Function<StreamPlus<SOURCE>, LongStream> action) {
        return LongStreamable.deriveFrom(asStreamable, action);
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE> DoubleStreamable deriveToDouble(
            AsStreamable<SOURCE>                       asStreamable,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        return DoubleStreamable.deriveFrom(asStreamable, action);
    }
    
    /** Create a Streamable from another streamable. */
    public static <SOURCE, TARGET> Streamable<TARGET> deriveToObj(
            AsStreamable<SOURCE>                         asStreamable,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        return deriveFrom(asStreamable, action);
    }
    
    //-- Zip ---
    
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
    public static <T1, T2> Streamable<Tuple2<T1, T2>> zipOf(
            Streamable<T1> streamable1, 
            Streamable<T2> streamable2) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream());
        };
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
    public static <T1, T2, TARGET> Streamable<TARGET> zipOf(
            Streamable<T1>        streamable1, 
            Streamable<T2>        streamable2,
            Func2<T1, T2, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    /**
     * Zip integers from two IntStreamables and combine it into another object.
     * The result stream has the size of the shortest streamable.
     */
    public static <T1, T2, TARGET> Streamable<TARGET> zipOf(
            IntStreamable            streamable1, 
            IntStreamable            streamable2,
            IntIntBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    merger);
        };
    }
    
    /** Zip integers from two IntStreamables and combine it into another object. */
    public static <TARGET> Streamable<TARGET> zipOf(
            IntStreamable            streamable1, 
            IntStreamable            streamable2,
            int                      defaultValue,
            IntIntBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    defaultValue,
                    merger);
        };
    }
    
    /** Zip integers from two IntStreams and combine it into another object. */
    public static <TARGET> Streamable<TARGET> zipOf(
            IntStreamable            streamable1, 
            int                      defaultValue1,
            IntStreamable            streamable2,
            int                      defaultValue2,
            IntIntBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2,
                    merger);
        };
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> Streamable<TARGET> zipOf(
            IntStreamable                     streamable1, 
            Streamable<ANOTHER>               streamable2, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The default value will be used if the first stream ended first and null will be used if the second stream ended first.
     */
    public static <ANOTHER, TARGET> Streamable<TARGET> zipOf(
            IntStreamable                     streamable1, 
            int                               defaultValue,
            Streamable<ANOTHER>               streamable2, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    /**
     * Zip longs from two LongStreams and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <TARGET> Streamable<TARGET> zipOf(
            LongStreamable       streamable1, 
            LongStreamable       streamable2,
            LongLongBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    merger);
        };
    }
    
    /** Zip longs from two LongStreamables and combine it into another object. */
    public static <T> Streamable<T> zipOf(
            LongStreamable       streamable1, 
            LongStreamable       streamable2,
            int                 defaultValue,
            LongLongBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    defaultValue,
                    merger);
        };
    }
    
    /**
     * Zip values from a long streamable and another object streamable and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <TARGET> Streamable<TARGET> zipOf(
            LongStreamable             streamable1, 
            long                       defaultValue1,
            LongStreamable             streamable2,
            long                       defaultValue2,
            LongLongBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(), defaultValue1,
                    streamable2.longStream(), defaultValue2,
                    merger);
        };
    }
    
    /**
     * Zip values from a long streamable and another object streamable and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStreamable                     streamable1, 
            Streamable<ANOTHER>                streamable2, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    /**
     * Zip values from an long streamable and another object streamable and combine it into another object.
     * The default value will be used if the first streamable ended first and null will be used if the second stream ended first.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStreamable                     streamable1, 
            long                               defaultValue,
            Streamable<ANOTHER>                streamable2, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(), defaultValue,
                    streamable2.stream(),
                    merger);
        };
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
        return this;
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
    public default Streamable<DATA> sequential() {
        return deriveFrom(this, stream -> { 
            return stream.sequential();
        });
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
    /** Make the subsequence operation parallel */
    public default Streamable<DATA> parallel() {
        return deriveFrom(this, stream -> { 
            return stream.parallel();
        });
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
    public default Streamable<DATA> unordered() {
        return deriveFrom(this, stream -> { 
            return stream.unordered();
        });
    }
    
    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    public default boolean isParallel() {
        return stream()
                .isParallel();
    }
    
    //-- Iterator --
    
    /** @return the iterable of this streamable. */
    public default IterablePlus<DATA> iterable() {
        return () -> iterator();
    }
    
    /** @return a iterator of this streamable. */
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    /** @return a spliterator of this streamable. */
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.map(mapper));
    }
    
    public default IntStreamable mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
    public default LongStreamable mapToLong(ToLongFunction<? super DATA> mapper) {
        return LongStreamable.deriveFrom(this, stream -> stream.mapToLong(mapper));
    }

    public default DoubleStreamable mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    public default <T> Streamable<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return map(mapper);
    }
    
    //-- Filter --
    
    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return this;
        
        return Streamable.deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    @Override
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsInt(
            ToIntFunction<? super DATA> mapper, 
            IntPredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsInt(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsLong(
            ToLongFunction<? super DATA> mapper, 
            LongPredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsLong(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsDouble(
            ToDoubleFunction<? super DATA> mapper, 
            DoublePredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsDouble(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default <T> Streamable<DATA> filterAsObject(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(mapper, predicate);
    }
    
    //-- Peek --
    
    public default Streamable<DATA> peek(Consumer<? super DATA> action) {
        return Streamable.deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    public default Streamable<DATA> limit(long maxSize) {
        return Streamable.deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    public default Streamable<DATA> skip(long n) {
        return Streamable.deriveFrom(this, stream -> stream.skip(n));
    }
    
    public default Streamable<DATA> distinct() {
        return Streamable.deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    public default Streamable<DATA> sorted() {
        return Streamable.deriveFrom(this, stream -> stream.sorted());
    }
    
    public default Streamable<DATA> sorted(Comparator<? super DATA> comparator) {
        return Streamable.deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.sortedBy(mapper));
    }
    
    public default <T> Streamable<DATA> sortedBy(
            Function<? super DATA, T> mapper, 
            Comparator<T>             comparator) {
        return Streamable.deriveFrom(this, stream -> stream.sortedBy(mapper, comparator));
    }
    
    //-- Terminate --
    
    public default void forEach(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEach(action);
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEachOrdered(action);
    }
    
    public default DATA reduce(DATA identity, BinaryOperator<DATA> accumulator) {
        return stream().reduce(identity, accumulator);
    }
    
    public default Optional<DATA> reduce(BinaryOperator<DATA> accumulator) {
        return stream().reduce(accumulator);
    }
    
    public default <U> U reduce(
                    U                              identity,
                    BiFunction<U, ? super DATA, U> accumulator,
                    BinaryOperator<U>              combiner) {
        return stream().reduce(identity, accumulator, combiner);
    }
    
    public default <R, A> R collect(
            Collector<? super DATA, A, R> collector) {
        return stream().collect(collector);
    }
    
    public default <R> R collect(
            Supplier<R>                 supplier,
            BiConsumer<R, ? super DATA> accumulator,
            BiConsumer<R, R>            combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }
    
    public default Optional<DATA> min(
            Comparator<? super DATA> comparator) {
        return stream().min(comparator);
    }
    
    public default Optional<DATA> max(
            Comparator<? super DATA> comparator) {
        return stream().max(comparator);
    }
    
    public default long count() {
        return stream().count();
    }
    
    public default int size() {
        return (int)stream().count();
    }
    
    public default boolean anyMatch(
            Predicate<? super DATA> predicate) {
        return stream().anyMatch(predicate);
    }
    
    public default boolean allMatch(
            Predicate<? super DATA> predicate) {
        return stream().allMatch(predicate);
    }
    
    public default boolean noneMatch(
            Predicate<? super DATA> predicate) {
        return stream().noneMatch(predicate);
    }
    
    public default Optional<DATA> findFirst() {
        return stream().findFirst();
    }
    
    public default Optional<DATA> findAny() {
        return stream().findAny();
    }
    
    //== toXXX ===
    
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    public default <T> T[] toArray(T[] a) {
        return StreamPlus.of(stream()).toJavaList().toArray(a);
    }
    
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }
    
    public default List<DATA> toJavaList() {
        return stream().collect(Collectors.toList());
    }
    
    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
        val byteArray = new ByteArrayOutputStream();
        stream().forEach(d -> byteArray.write(toByte.apply(d)));
        return byteArray.toByteArray();
    }
    
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        return mapToInt(toInt).toArray();
    }
//    
//    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
//        return mapToLong(toLong).toArray();
//    }
//    
//    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
//        return mapToDouble(toDouble).toArray();
//    }
    
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toFuncList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toLazyList() {
        return FuncList.from(this);
    }
    
    public default String toListString() {
        return "[" + map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(stream());
    }
    
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    public default ArrayList<DATA> toArrayList() {
        return new ArrayList<DATA>(toJavaList());
    }
    
    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    
    //== Plus ==
    
    public default String joinToString() {
        return map(StrFuncs::toStr)
                .collect(Collectors.joining());
    }
    public default String joinToString(String delimiter) {
        return map(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
    }
    
    //++ Plus w/ Self ++
    
    public default <T> Pipeable<Streamable<DATA>> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super Streamable<DATA>, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one 
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.spawn(mapper));
    }
    
    //== accumulate ==
    
    /**
     * Accumulate the previous to the next element.
     * 
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a accumulate function
     * 
     * From this we get
     *      acc0  = head of inputs => i1
     *      rest0 = tail of inputs => [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     * 
     * The outputs are:
     *     output0 = acc0 with acc1 = acc0 ~ rest0 and rest1 = rest of rest0
     *     output1 = acc1 with acc2 = acc1 ~ rest1 and rest2 = rest of rest1
     *     output2 = acc2 with acc3 = acc2 ~ rest2 and rest3 = rest of rest2
     *     ...
     */
    public default Streamable<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        return Streamable.deriveFrom(this, stream -> stream.accumulate(accumulator));
    }
    
    //== restate ==
    
    /**
     * Use each of the element to recreate the stream by applying each element to the rest of the stream and repeat.
     * 
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a restate function
     * 
     * From this we get
     *      head0 = head of inputs = i1
     *      rest0 = tail of inputs = [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     * 
     * The outputs are:
     *     output0 = head0 with rest1 = head0 ~ rest0 and head1 = head of rest0
     *     output1 = head1 with rest2 = head1 ~ rest1 and head2 = head of rest2
     *     output2 = head2 with rest3 = head2 ~ rest2 and head3 = head of rest3
     *     ...
     **/
    public default Streamable<DATA> restate(BiFunction<? super DATA, Streamable<DATA>, Streamable<DATA>> restater) {
        val func = (UnaryOperator<Tuple2<DATA, Streamable<DATA>>>)((Tuple2<DATA, Streamable<DATA>> pair) -> {
            val stream   = pair._2();
            val iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = iterator.next();
            val tail = restater.apply(head, ()->iterator.stream());
            return Tuple2.of(head, tail);
        });
        val seed = Tuple2.of((DATA)null, this);
        val endStream = (Streamable<DATA>)(()->StreamPlus.iterate(seed, func).takeUntil(t -> t == null).skip(1).map(t -> t._1()));
        return endStream;
    }
    
}
