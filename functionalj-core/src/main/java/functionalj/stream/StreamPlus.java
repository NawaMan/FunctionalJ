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
package functionalj.stream;

import static functionalj.function.Func.themAll;
import static functionalj.stream.StreamPlusHelper.terminate;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
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
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.streamable.Streamable;
import functionalj.tuple.Tuple2;
import lombok.val;


// TODO - Add intersect
// TODO - Add prepare


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
 * See annotations in {@link functionalj.stream.markers} package for more information.
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
            StreamPlusWithCalculate<DATA>,
            StreamPlusWithCombine<DATA>,
            StreamPlusWithFillNull<DATA>,
            StreamPlusWithFilter<DATA>,
            StreamPlusWithFlatMap<DATA>,
            StreamPlusWithLimit<DATA>,
            StreamPlusWithMap<DATA>,
            StreamPlusWithMapFirst<DATA>,
            StreamPlusWithMapGroup<DATA>,
            StreamPlusWithMapThen<DATA>,
            StreamPlusWithMapToMap<DATA>,
            StreamPlusWithMapToTuple<DATA>,
            StreamPlusWithMapWithIndex<DATA>,
            StreamPlusWithModify<DATA>,
            StreamPlusWithPeek<DATA>,
            StreamPlusWithPipe<DATA>,
            StreamPlusWithSegment<DATA>,
            StreamPlusWithSort<DATA>,
            StreamPlusWithSplit<DATA> {
    
    /** Throw a no more element exception. This is used for generator. */
    public static <TARGET> TARGET noMoreElement() throws NoMoreResultException {
        return SupplierBackedIterator.noMoreElement();
    }
    
    //== Constructor ==
    
    /** Returns an empty StreamPlus. */
    public static <TARGET> StreamPlus<TARGET> empty() {
        return StreamPlus.from(Stream.empty());
    }
    
    /** Returns an empty StreamPlus. */
    public static <TARGET> StreamPlus<TARGET> emptyStream() {
        return empty();
    }
    
    /** Create a StreamPlus from the given data. */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> of(TARGET ... data) {
        return ArrayBackedStreamPlus.from(data);
    }
    
    /** Create a StreamPlus from the given data */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> streamOf(TARGET ... data) {
        return StreamPlus.of(data);
    }
    
    /** Create a StreamPlus from the given data. */
    public static <TARGET> StreamPlus<TARGET> from(TARGET[] data, int start, int length) {
        return ArrayBackedStreamPlus.from(data, start, length);
    }
    
    /** Create a StreamPlus from the given stream. */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <TARGET> StreamPlus<TARGET> from(Stream<TARGET> stream) {
        if (stream == null)
            return StreamPlus.empty();
        
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    /** Create a StreamPlus from the given iterator. */
    public static <TARGET> StreamPlus<TARGET> from(Iterator<TARGET> iterator) {
        return IteratorPlus.from(iterator)
                .stream();
    }
    
    /** Create a StreamPlus from the given enumeration. */
    public static <TARGET> StreamPlus<TARGET> from(Enumeration<TARGET> enumeration) {
        val iterable = (Iterable<TARGET>)() -> new EnumerationBackedIterator<TARGET>(enumeration);
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> concat(Stream<TARGET> ... streams) {
        return streamOf (streams)
                .flatMap(themAll());
    }
    
    /** Concatenate all streams supplied by the given supplied. */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> concat(Func0<Stream<TARGET>> ... streams) {
        return streamOf (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> combine(Stream<TARGET> ... streams) {
        return streamOf (streams)
                .flatMap(themAll());
    }
    
    /** Concatenate all streams supplied by the given supplied. */
    @SafeVarargs
    public static <TARGET> StreamPlus<TARGET> combine(Func0<Stream<TARGET>> ... streams) {
        return streamOf (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> StreamPlus<TARGET> generate(Func0<TARGET> supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <TARGET> StreamPlus<TARGET> generateWith(Func0<TARGET> supplier) {
        val iterable = (Iterable<TARGET>)() -> new SupplierBackedIterator<TARGET>(supplier);
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
    public static <TARGET> StreamPlus<TARGET> iterate(
            TARGET                seed,
            Func1<TARGET, TARGET> compounder) {
        return StreamPlus.from(Stream.iterate(seed, compounder::apply));
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
    public static <TARGET> StreamPlus<TARGET> compound(
            TARGET                seed,
            Func1<TARGET, TARGET> compounder) {
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
    @Sequential
    public static <TARGET> StreamPlus<TARGET> iterate(
            TARGET                             seed1,
            TARGET                             seed2,
            BiFunction<TARGET, TARGET, TARGET> compounder) {
        return StreamPlus.from(StreamSupport.stream(new Spliterators.AbstractSpliterator<TARGET>(Long.MAX_VALUE, 0) {
            private final    AtomicReference<TARGET> first     = new AtomicReference<>(seed1);
            private final    AtomicReference<TARGET> second    = new AtomicReference<>(seed2);
            private volatile AtomicBoolean           isInOrder = null;
            @Override
            public boolean tryAdvance(Consumer<? super TARGET> action) {
                if (isInOrder == null) {
                    action.accept(seed1);
                    action.accept(seed2);
                    isInOrder = new AtomicBoolean(true);
                }
                
                boolean inOrder = isInOrder.get();
                if (inOrder) {
                    TARGET next = compounder.apply(first.get(), second.get());
                    action.accept(next);
                    first.set(next);
                } else {
                    TARGET next = compounder.apply(second.get(), first.get());
                    action.accept(next);
                    second.set(next);
                }
                isInOrder.set(!inOrder);
                return true;
            }
        }, false));
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
    public static <TARGET> StreamPlus<TARGET> compound(
            TARGET                             seed1,
            TARGET                             seed2,
            BiFunction<TARGET, TARGET, TARGET> compounder) {
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
    public static <T1, T2, TARGET> StreamPlus<TARGET> zipOf(
            Stream<T1>            stream1,
            Stream<T2>            stream2,
            Func2<T1, T2, TARGET> merger) {
        return StreamPlus.from(stream1)
                .zipWith(stream2, ZipWithOption.RequireBoth, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream                stream1,
            IntStream                stream2,
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            IntStream                         stream1,
            Stream<ANOTHER>                   stream2,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    /**
     * Zip integers from two IntStreams and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <TARGET> StreamPlus<TARGET> zipOf(
            DoubleStream                   stream1,
            DoubleStream                   stream2,
            DoubleDoubleBiFunction<TARGET> merger) {
        return DoubleStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            DoubleStream                         stream1,
            Stream<ANOTHER>                      stream2,
            DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        return DoubleStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    //== Core ==
    
    /** Return the stream of data behind this StreamPlus. */
    public Stream<DATA> stream();
    
    /** Return this StreamPlus. */
    public default StreamPlus<DATA> streamPlus() {
        return this;
    }
    
    //-- Derive --
    
    public default <TARGET> StreamPlus<TARGET> derive(Func1<StreamPlus<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus deriveToInt(Func1<StreamPlus<DATA>, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
//    public default LongStreamPlus deriveToLong(Func1<StreamPlus<DATA>, LongStream> action) {
//        return LongStreamPlus.from(action.apply(this));
//    }
    public default LongStream deriveToLong(Func1<StreamPlus<DATA>, LongStream> action) {
        return null;
    }
    
//    public default DoubleStreamPlus deriveToDouble(Func1<StreamPlus<DATA>, DoubleStream> action) {
//        return DoubleStreamPlus.from(action.apply(this));
//    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Func1<StreamPlus<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
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
    @Override
    public default StreamPlus<DATA> sequential() {
        return StreamPlus.from(stream()
                .sequential());
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
    @Override
    public default StreamPlus<DATA> parallel() {
        return StreamPlus.from(stream()
                .parallel());
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
    @Override
    public default StreamPlus<DATA> unordered() {
        return StreamPlus.from(stream()
                .unordered());
    }
    
    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
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
    
    /** @return a iterator of this streamable. */
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream().iterator());
    }
    
    /** @return a spliterator of this streamable. */
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
    public default LongStream mapToLong(ToLongFunction<? super DATA> mapper) {
        return stream().mapToLong(mapper);
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamPlus.from(stream().mapToDouble(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return StreamPlus.from(stream().map(mapper));
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
    public default LongStream flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return stream().flatMapToLong(mapper);
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
    public default <R, A> R collect(Collector<? super DATA, A, R> collector) {
        return terminate(this, stream -> {
            return stream
                    .collect(collector);
        });
    }
    
    //-- statistics --
    
    @Eager
    @Terminal
    @Override
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        return terminate(this, stream -> {
            return stream.min(comparator);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        return terminate(this, stream -> {
            return stream.max(comparator);
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
    
    @SuppressWarnings("unchecked")
    @Sequential
    @Terminal
    public default Optional<DATA> findLast() {
        return terminate(this, stream -> {
            Object dummy = new Object();
            AtomicReference<Object> dataRef = new AtomicReference<>(dummy);
            stream.forEach(dataRef::set);
            Object last = dataRef.get();
            return (dataRef.get() == dummy) ? Optional.empty() : Optional.ofNullable((DATA)last);
        });
    }
    
    @SuppressWarnings("unchecked")
    @Sequential
    @Terminal
    public default Result<DATA> firstResult() {
        return terminate(this, stream -> {
            Object dummy = new Object();
            AtomicReference<Object> dataRef = new AtomicReference<>(dummy);
            stream.limit(1).forEach(dataRef::set);
            Object last = dataRef.get();
            return (dataRef.get() == dummy) ? Result.ofNotExist() : Result.valueOf((DATA)last);
        });
    }
    
    @SuppressWarnings("unchecked")
    @Sequential
    @Terminal
    public default Result<DATA> lastResult() {
        return terminate(this, stream -> {
            Object dummy = new Object();
            AtomicReference<Object> dataRef = new AtomicReference<>(dummy);
            stream.forEach(dataRef::set);
            Object last = dataRef.get();
            return (dataRef.get() == dummy) ? Result.ofNotExist() : Result.valueOf((DATA)last);
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
