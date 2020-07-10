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

import static functionalj.function.Func.themAll;
import static functionalj.stream.StreamPlusHelper.terminate;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.result.NoMoreResultException;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.makers.Eager;
import functionalj.stream.makers.Sequential;
import functionalj.stream.makers.Terminal;
import functionalj.tuple.Tuple2;
import lombok.val;

// TODO - Add intersect


/**
 * This interface allows extension to Java Stream object.
 * 
 * <ol>
 *   <li>It ensures stream is called and onClose is called.</li>
 *   <li>Add many convenience methods</li>
 * </ol>
 * 
 * Use this class if the source of data is a one-time non-repeatable data source.
 * Otherwise, use {@link Streamable} as some operation may benefit from having repeatable streams.
 * 
 * Unless stated otherwise all methods in this class is:
 * <ol>
 *   <li>Lazy.</li>
 *   <li>Works for parallel</li>
 *   <li>Run in order - if sequence</li>
 *   <li>Terminal method must close the stream</li>
 * </ol>
 * 
 * If any of the rules are not observed, it is a bug.
 * See annotations in {@link functionalj.stream.makers} package for more information.
 * 
 * @param <DATA>  the data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface StreamPlus<DATA> 
        extends 
            Stream<DATA>,
            AsStreamPlus<DATA>,
            StreamPlusWithAdditionalConversion<DATA>,
            StreamPlusWithAdditionalFilter<DATA>,
            StreamPlusWithAdditionalFlatMap<DATA>,
            StreamPlusWithAdditionalForEach<DATA>,
            StreamPlusWithAdditionalLimit<DATA>,
            StreamPlusWithAdditionalMap<DATA>,
            StreamPlusWithAdditionalMatch<DATA>,
            StreamPlusWithAdditionalPeek<DATA>,
            StreamPlusWithAdditionalSort<DATA>,
            StreamPlusWithAdditionalStatistic<DATA>,
            StreamPlusWithCombine<DATA>,
            StreamPlusWithCalculate<DATA>,
            StreamPlusWithFillNull<DATA>,
            StreamPlusWithMapFirst<DATA>,
            StreamPlusWithMapThen<DATA>,
            StreamPlusWithMapToMap<DATA>,
            StreamPlusWithMapTuple<DATA>,
            StreamPlusWithMapWithIndex<DATA>,
            StreamPlusWithMapWithPrev<DATA>,
            StreamPlusWithModify<DATA>,
            StreamPlusWithPipe<DATA>,
            StreamPlusWithReshape<DATA>,
            StreamPlusWithSplit<DATA> {
    
    /** Throw a no more element exception. This is used for generator. */
    public static <D> D noMoreElement() throws NoMoreResultException {
        return SupplierBackedIterator.noMoreElement();
    }
    
    //== Constructor ==
    
    /** Returns an empty StreamPlus. */
    public static <D> StreamPlus<D> empty() {
        return StreamPlus.from(Stream.empty());
    }
    
    /** Returns an empty StreamPlus. */
    public static <D> StreamPlus<D> emptyStream() {
        return empty();
    }
    
    /** Create a StreamPlus from the given data. */
    @SafeVarargs
    public static <D> StreamPlus<D> of(D ... data) {
        return ArrayBackedStreamPlus.from(data);
    }
    
    /** Create a StreamPlus from the given data */
    @SafeVarargs
    public static <D> StreamPlus<D> streamOf(D ... data) {
        return StreamPlus.of(data);
    }
    
    /** Create a StreamPlus from the given stream. */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> StreamPlus<D> from(Stream<D> stream) {
        if (stream == null)
            return StreamPlus.empty();
        
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    /** Create a StreamPlus from the given iterator. */
    public static <D> StreamPlus<D> from(Iterator<D> iterator) {
        return IteratorPlus.from(iterator)
                .stream();
    }
    
    /** Create a StreamPlus from the given enumeration. */
    public static <D> StreamPlus<D> from(Enumeration<D> enumeration) {
        val iterable = (Iterable<D>)() -> new EnumerationBackedIterator<D>(enumeration);
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Stream<D> ... streams) {
        return streamOf (streams)
                .flatMap(themAll());
    }
    
    /** Concatenate all streams supplied by the given supplied. */
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Func0<Stream<D>> ... streams) {
        return streamOf (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <D> StreamPlus<D> combine(Stream<D> ... streams) {
        return streamOf (streams)
                .flatMap(themAll());
    }
    
    /** Concatenate all streams supplied by the given supplied. */
    @SafeVarargs
    public static <D> StreamPlus<D> combine(Func0<Stream<D>> ... streams) {
        return streamOf (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> StreamPlus<D> generate(Func0<D> supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> StreamPlus<D> generateWith(Func0<D> supplier) {
        val iterable = (Iterable<D>)() -> new SupplierBackedIterator<D>(supplier);
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    /**
     * Create a StreamPlus by apply the compounder to the seed over and over.
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
    public static <D> StreamPlus<D> iterate(
            D                seed, 
            UnaryOperator<D> compounder) {
        return StreamPlus.from(Stream.iterate(seed, compounder));
    }
    
    /**
     * Create a StreamPlus by apply the compounder to the seed over and over.
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
    public static <D> StreamPlus<D> compound(
            D                seed, 
            UnaryOperator<D> compounder) {
        return iterate(seed, compounder);
    }
    
    /**
     * Create a StreamPlus by apply the compounder to the seeds over and over.
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
    public static <D> StreamPlus<D> iterate(
            D                 seed1, 
            D                 seed2, 
            BinaryOperator<D> compounder) {
        // TODO - Remove the hacky 'counter' - may create iterator instead - let's experiment.
        AtomicInteger      counter = new AtomicInteger(0);
        AtomicReference<D> d1      = new AtomicReference<D>(seed1);
        AtomicReference<D> d2      = new AtomicReference<D>(seed2);
        return StreamPlus.generate(()->{
            val index = counter.getAndIncrement();
            if (index == 0)
                return seed1;
            if (index == 1)
                return seed2;
            
            D i2 = d2.get();
            D i1 = d1.getAndSet(i2);
            D i  = compounder.apply(i1, i2);
            d2.set(i);
            return i;
        });
    }
    
    /**
     * Create a StreamPlus by apply the compounder to the seeds over and over.
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
    public static <D> StreamPlus<D> compound(
            D seed1, 
            D seed2, 
            BinaryOperator<D> compounder) {
        return iterate(seed1, seed2, compounder);
    }
    
    /**
     * Create a StreamPlus by combining elements together into a StreamPlus of tuples.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     * 
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *     
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static <T1, T2> StreamPlus<Tuple2<T1, T2>> zipOf(
            Stream<T1> stream1, 
            Stream<T2> stream2) {
        return StreamPlus.from(stream1)
                .zipWith(stream2, ZipWithOption.RequireBoth);
    }
    
    /**
     * Create a StreamPlus by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     * 
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *     merger  = a + "+" + b 
     *     
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     **/
    public static <T1, T2, T> StreamPlus<T> zipOf(
            Stream<T1> stream1, 
            Stream<T2> stream2, 
            Func2<T1, T2, T> merger) {
        return StreamPlus.from(stream1)
                .zipWith(stream2, ZipWithOption.RequireBoth, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object. The result stream has the size of the shortest stream.
     * 
     * @param <TARGET>  the target result type.
     * @param stream1   the first integer stream.
     * @param stream2   the second integer stream.
     * @param merger    the merger function.
     * @return          the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream stream1, 
            IntStream stream2, 
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object.
     * 
     * @param <TARGET>      the target result type.
     * @param stream1       the first integer stream.
     * @param stream2       the second integer stream.
     * @param defaultValue  the value used when either of the stream ended but the other has not.
     * @param merger        the merger function.
     * @return              the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream                stream1, 
            IntStream                stream2, 
            int                      defaultValue,
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object.
     * 
     * @param <TARGET>       the target result type.
     * @param stream1        the first integer stream.
     * @param defaultValue1  the value used when the first stream ended by the second one is not.
     * @param stream2        the second integer stream.
     * @param defaultValue2  the value used when the second stream ended by the first one is not.
     * @param merger         the merger function.
     * @return               the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream                stream1, 
            int                      defaultValue1, 
            IntStream                stream2,
            int                      defaultValue2,
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     * 
     * @param <TARGET>  the target result type.
     * @param stream1   the first integer stream.
     * @param stream2   the second stream.
     * @param merger    the merger function.
     * @return          the StreamPlus of the result.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            IntStream                         stream1, 
            Stream<ANOTHER>                   stream2, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The default value will be used if the first stream ended first and null will be used if the second stream ended first.
     * 
     * @param <TARGET>  the target result type.
     * @param stream1   the first integer stream.
     * @param stream2   the second stream.
     * @param merger    the merger function.
     * @return          the StreamPlus of the result.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            IntStream                         stream1, 
            int                               defaultValue,
            Stream<ANOTHER>                   stream2, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(defaultValue, stream2, merger);
    }
    
    /**
     * Zip integers from two LongStreams and combine it into another object. The result stream has the size of the shortest stream.
     * 
     * @param <TARGET>  the target result type.
     * @param stream1   the first long stream.
     * @param stream2   the second long stream.
     * @param merger    the merger function.
     * @return          the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream stream1, 
            LongStream stream2, 
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    /**
     * Zip integers from two LongStreams and combine it into another object.
     * 
     * @param <TARGET>      the target result type.
     * @param stream1       the first long stream.
     * @param stream2       the second long stream.
     * @param defaultValue  the value used when either of the stream ended but the other has not.
     * @param merger        the merger function.
     * @return              the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream                 stream1, 
            LongStream                 stream2, 
            long                       defaultValue,
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object.
     * 
     * @param <TARGET>       the target result type.
     * @param stream1        the first integer stream.
     * @param defaultValue1  the value used when the first stream ended by the second one is not.
     * @param stream2        the second integer stream.
     * @param defaultValue2  the value used when the second stream ended by the first one is not.
     * @param merger         the merger function.
     * @return               the StreamPlus of the result.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream                stream1, 
            long                      defaultValue1, 
            LongStream                stream2,
            long                      defaultValue2,
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    /**
     * Zip values from a long stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     * 
     * @param <TARGET>  the target result type.
     * @param stream1   the first long stream.
     * @param stream2   the second stream.
     * @param merger    the merger function.
     * @return          the StreamPlus of the result.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStream                         stream1, 
            Stream<ANOTHER>                    stream2, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    /**
     * Zip values from an long stream and another object stream and combine it into another object.
     * The default value will be used if the first stream ended first and null will be used if the second stream ended first.
     * 
     * @param <TARGET>      the target result type.
     * @param stream1       the first integer stream.
     * @param defaultValue  the default value for stream1.
     * @param stream2       the second stream.
     * @param merger        the merger function.
     * @return              the StreamPlus of the result.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStream                         stream1, 
            long                               defaultValue,
            Stream<ANOTHER>                    stream2,
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(defaultValue, stream2, merger);
    }
    
    //== Core ==
    
    /** Return the stream of data behind this StreamPlus. */
    public Stream<DATA> stream();
    
    /** Return this StreamPlus. */
    public default StreamPlus<DATA> streamPlus() {
        return this;
    }
    
    public default StreamPlus<DATA> asStream() {
        return streamPlus();
    }
    
    //-- Derive --
    
    public default <TARGET> StreamPlus<TARGET> derive(Func1<StreamPlus<DATA>, StreamPlus<TARGET>> action) {
        return action.apply(this);
    }
    
    public default IntStreamPlus deriveToInt(Func1<StreamPlus<DATA>, IntStreamPlus> action) {
        return action.apply(this);
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Func1<StreamPlus<DATA>, StreamPlus<TARGET>> action) {
        return action.apply(this);
    }
    
    //-- Characteristics --
    
    @Override
    public default StreamPlus<DATA> sequential() {
        return StreamPlus.from(stream()
                .sequential());
    }
    
    @Override
    public default StreamPlus<DATA> parallel() {
        return StreamPlus.from(stream()
                .parallel());
    } 
    
    @Override
    public default StreamPlus<DATA> unordered() {
        return StreamPlus.from(stream()
                .unordered());
    }
    
    @Override
    public default boolean isParallel() {
        return stream()
                .isParallel();
    }
    
    //-- Close --
    
    @Terminal
    @Override
    public default void close() {
        stream()
        .close();
    }
    
    @Override
    public default StreamPlus<DATA> onClose(Runnable closeHandler) {
        return StreamPlus.from(stream().onClose(closeHandler));
    }
    
    //-- Iterator --
    
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream().iterator());
    }
    
    @Override
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    //-- Map --
    
    @Override
    public default <T> StreamPlus<T> map(Function<? super DATA, ? extends T> mapper) {
        return StreamPlus.from(stream().map(mapper));
    }
    
    @Override
    public default IntStreamPlus mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus mapToLong(ToLongFunction<? super DATA> mapper) {
        return LongStreamPlus.from(stream().mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamPlus.from(stream().mapToDouble(mapper));
    }
    
    //-- FlatMap --
    
    @Override
    public default <T> StreamPlus<T> flatMap(Function<? super DATA, ? extends Stream<? extends T>> mapper) {
        return StreamPlus.from(stream().flatMap(mapper));
    }
    
    @Override
    public default IntStreamPlus flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return LongStreamPlus.from(stream().flatMapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(stream().flatMapToDouble(mapper));
    }
    
    //-- Filter --
    
    @Override
    public default StreamPlus<DATA> filter(Predicate<? super DATA> predicate) {
        return StreamPlus.from(stream().filter(predicate));
    }
    
    @Override
    public default <T> StreamPlus<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    //-- Peek --
    
    @Override
    public default StreamPlus<DATA> peek(Consumer<? super DATA> action) {
        return StreamPlus.from(stream().peek(action));
    }
    
    //-- Limit/Skip --
    
    @Override
    public default StreamPlus<DATA> limit(long maxSize) {
        return StreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default StreamPlus<DATA> skip(long offset) {
        return StreamPlus.from(stream().skip(offset));
    }
    
    //-- Distinct --
    
    @Override
    public default StreamPlus<DATA> distinct() {
        return StreamPlus.from(stream().distinct());
    }
    
    //-- Sorted --
    
    @Eager
    @Override
    public default StreamPlus<DATA> sorted() {
        return StreamPlus.from(stream().sorted());
    }
    
    @Eager
    @Override
    public default StreamPlus<DATA> sorted(Comparator<? super DATA> comparator) {
        return StreamPlus.from(stream().sorted(comparator));
    }
    
    //-- Terminate --
    
    @Eager
    @Terminal
    @Override
    public default void forEach(Consumer<? super DATA> action) {
        terminate(this, stream -> {
            stream
            .forEach(action);
        });
    }
    
    @Eager
    @Terminal
    @Sequential
    @Override
    public default void forEachOrdered(Consumer<? super DATA> action) {
        terminate(this, stream -> {
            stream
            .sequential    ()
            .forEachOrdered(action);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default DATA reduce(DATA identity, BinaryOperator<DATA> reducer) {
        return terminate(this, stream -> {
            return stream
                    .reduce(identity, reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default Optional<DATA> reduce(BinaryOperator<DATA> reducer) {
        return terminate(this, stream -> {
            return stream
                    .reduce(reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <U> U reduce(
            U                              identity,
            BiFunction<U, ? super DATA, U> accumulator,
            BinaryOperator<U>              combiner) {
        return terminate(this, stream -> {
            return stream
                    .reduce(identity, accumulator, combiner);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <R> R collect(
            Supplier<R>                 supplier,
            BiConsumer<R, ? super DATA> accumulator,
            BiConsumer<R, R>            combiner) {
        return terminate(this, stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <R, A> R collect(
            Collector<? super DATA, A, R> collector) {
        return terminate(this, stream -> {
            return stream
                    .collect(collector);
        });
    }
    
    //-- statistics --
    
    @Eager
    @Terminal
    @Override
    public default Optional<DATA> min(
            Comparator<? super DATA> comparator) {
        return terminate(this, stream -> {
            return stream.min(comparator);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default Optional<DATA> max(
            Comparator<? super DATA> comparator) {
        return terminate(this, stream -> {
            return stream.max(comparator);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <D extends Comparable<D>> Optional<DATA> minBy(Func1<DATA, D> mapper) {
        return min((a,b)->{
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return mappedA.compareTo(mappedB);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Func1<DATA, D> mapper) {
        return max((a,b)->{
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return mappedA.compareTo(mappedB);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default long count() {
        return terminate(this, stream -> {
            return stream
                    .count();
        });
    }
    
    //-- Match --
    
    @Terminal
    @Override
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        return terminate(this, stream -> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        return terminate(this, stream -> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        return terminate(this, stream -> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Terminal
    @Override
    public default Optional<DATA> findFirst() {
        return terminate(this, stream -> {
            return stream
                    .findFirst();
        });
    }
    
    @Terminal
    @Override
    public default Optional<DATA> findAny() {
        return terminate(this, stream -> {
            return stream
                    .findAny();
        });
    }
    
    //== Conversion ==
    
    @Eager
    @Terminal
    @Override
    public default Object[] toArray() {
        return terminate(this, stream -> {
            return stream
                    .toArray();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return terminate(this, stream -> {
            return stream
                    .toArray(generator);
        });
    }
    
}
