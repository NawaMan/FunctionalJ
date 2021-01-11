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
package functionalj.streamable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.result.NoMoreResultException;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.doublestreamable.AsDoubleStreamable;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.Tuple2;
import lombok.val;


// TODO - Add intersect (retain) - but might want to do it after sort.


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
            StreamableWithCalculate<DATA>,
            StreamableWithCombine<DATA>,
            StreamableWithFillNull<DATA>,
            StreamableWithFilter<DATA>,
            StreamableWithFlatMap<DATA>,
            StreamableWithForEach<DATA>,
            StreamableWithLimit<DATA>,
            StreamableWithMap<DATA>,
            StreamableWithMapFirst<DATA>,
            StreamableWithMapGroup<DATA>,
            StreamableWithMapThen<DATA>,
            StreamableWithMapToMap<DATA>,
            StreamableWithMapToTuple<DATA>,
            StreamableWithMapWithIndex<DATA>,
            StreamableWithModify<DATA>,
            StreamableWithPeek<DATA>,
            StreamableWithPipe<DATA>,
            StreamableWithSegment<DATA>,
            StreamableWithSort<DATA>,
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
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> concat(AsStreamable<? extends TARGET> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /**
     * Concatenate all the given streamables.
     *
     * This method is the alias of {@link Streamable#concat(Streamable...)}
     *   but allowing static import without colliding with {@link String#concat(String)}.
     **/
    @SafeVarargs
    public static <TARGET> Streamable<TARGET> combine(AsStreamable<TARGET> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /**
     * Create a Streamable from the supplier of supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> Streamable<TARGET> generate(Func0<Func0<TARGET>> supplier) {
        return generateWith(supplier);
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
            Func1<TARGET, TARGET> compounder) {
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
            Func1<TARGET, TARGET> compounder) {
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
            TARGET                        seed1,
            TARGET                        seed2,
            Func2<TARGET, TARGET, TARGET> compounder) {
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
    public static <TARGET> Streamable<TARGET> compound(
            TARGET                        seed1,
            TARGET                        seed2,
            Func2<TARGET, TARGET, TARGET> compounder) {
        return ()->StreamPlus.compound(seed1, seed2, compounder);
    }
    
    /** Create a Streamable that contains infinite number of null. */
    public static <TARGET> Streamable<TARGET> nulls() {
        return cycle((TARGET)null);
    }
    
    /** Create a Streamable that contains infinite number of null. */
    public static <TARGET> Streamable<TARGET> nulls(Class<TARGET> dataClass) {
        return cycle((TARGET)null);
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
        return () -> {
                return StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data[i % size]));
        };
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <TARGET> Streamable<TARGET> cycle(FuncList<TARGET> data) {
        val size = data.size();
        return () -> {
            return StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data.get(i % size)));
        };
    }
    
    /** Create a Streamable that for an infinite loop - the value is boolean true */
    public static <TARGET> Streamable<TARGET> loop() {
        return ()-> StreamPlus.from(Stream.generate(() -> (TARGET)null));
    }
    
    /** Create a Streamable that for a loop with the number of time given - the value is the index of the loop. */
    public static <TARGET> Streamable<TARGET> loop(int time) {
        Streamable<TARGET> nulls = nulls();
        return nulls.limit(time);
    }
    
    /** Create a Streamable that for an infinite loop - the value is the index of the loop. */
    public static Streamable<Integer> infiniteInt() {
        return IntStreamable
                .wholeNumbers()
                .boxed();
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
     * Zip integers from two IntStreamables and combine it into another object.
     * The result stream has the size of the shortest streamable.
     */
    public static <T1, T2, TARGET> Streamable<TARGET> zipOf(
            DoubleStreamable               streamable1,
            DoubleStreamable               streamable2,
            DoubleDoubleBiFunction<TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.doubleStream(),
                    merger);
        };
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> Streamable<TARGET> zipOf(
            DoubleStreamable                     streamable1,
            Streamable<ANOTHER>                  streamable2,
            DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.doubleStream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    //== Core ==
    
    /** Return the stream of data behind this Streamable. */
    public default StreamPlus<DATA> stream() {
        return streamPlus();
    }
    
    /** Return this StreamPlus. */
    public  StreamPlus<DATA> streamPlus();
    
    /** Return the this as a streamable. */
    public default Streamable<DATA> streamable() {
        return this;
    }
    
    //-- Derive --
    
    /** Create a Streamable from the given Streamable. */
    public static <SOURCE, TARGET> Streamable<TARGET> deriveFrom(
            AsStreamable<SOURCE>                         asStreamable,
            Function<StreamPlus<SOURCE>, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.streamPlus();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> Streamable<TARGET> deriveFrom(
            AsIntStreamable                         asStreamable,
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return () -> {
            val sourceStream = asStreamable.intStreamPlus();
            val targetStream = action.apply(sourceStream);
            return StreamPlus.from(targetStream);
        };
    }
//
//    /** Create a Streamable from the given LongStreamable. */
//    public static <TARGET> Streamable<TARGET> deriveFrom(
//            AsLongStreamable                         asStreamable,
//            Function<LongStreamPlus, Stream<TARGET>> action) {
//        return () -> {
//            val sourceStream = asStreamable.longStream();
//            val targetStream = action.apply(sourceStream);
//            return StreamPlus.from(targetStream);
//        };
//    }
    
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
//
//    /** Create a Streamable from another streamable. */
//    public static <SOURCE> LongStreamable deriveToLong(
//            AsStreamable<SOURCE>                     asStreamable,
//            Function<StreamPlus<SOURCE>, LongStream> action) {
//        return LongStreamable.deriveFrom(asStreamable, action);
//    }

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
    
    //-- Map --
    
    /** Map each value into other value using the function. */
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.map(mapper));
    }
    
    /** Map each value into an integer value using the function. */
    public default IntStreamable mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.mapToInt(mapper));
    }
    
//    /** Map each value into a long value using the function. */
//    public default LongStreamable mapToLong(ToLongFunction<? super DATA> mapper) {
//        return LongStreamable.deriveFrom(this, stream -> stream.mapToLong(mapper));
//    }
    
    /** Map each value into a double value using the function. */
    public default DoubleStreamable mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    //-- FlatMap --
    
    /** Map a value into a streamable and then flatten that streamable */
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).stream()));
    }
    
    /** Map a value into an integer streamable and then flatten that streamable */
    public default IntStreamable flatMapToInt(Function<? super DATA, ? extends IntStreamable> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
    }
//
//    /** Map a value into a long streamable and then flatten that streamable */
//    public default LongStreamable flatMapToLong(Function<? super DATA, ? extends LongStreamable> mapper) {
//        return LongStreamable.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
//    }
//
//    /** Map a value into a double streamable and then flatten that streamable */
//    public default DoubleStreamable flatMapToDouble(Function<? super DATA, ? extends DoubleStreamable> mapper) {
//        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
//    }
    
    //-- Filter --
    
    /** Select only the element that passes the predicate */
    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /** Select only the element that the mapped value passes the predicate */
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper,
            Predicate<? super T>      predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    //-- Peek --
    
    /** Consume each value using the action whenever a termination operation is called */
    public default Streamable<DATA> peek(Consumer<? super DATA> action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    //-- Limit/Skip --
    
    /** Limit the size */
    public default Streamable<DATA> limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Trim off the first n values */
    public default Streamable<DATA> skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    //-- Distinct --
    
    /** Remove duplicates */
    public default Streamable<DATA> distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    //-- Sorted --
    
    /** Sort the values in this stream */
    public default Streamable<DATA> sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /** Sort the values in this stream using the given comparator */
    public default Streamable<DATA> sorted(Comparator<? super DATA> comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    //-- Terminate --
    
    /** Process each value using the given action */
    public default void forEach(Consumer<? super DATA> action) {
        stream()
        .forEach(action);
    }
    
}
