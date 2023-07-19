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
package functionalj.stream.longstream;

import static functionalj.function.Func.itself;
import static functionalj.stream.longstream.LongStreamPlusHelper.terminate;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.function.LongComparator;
import functionalj.function.aggregator.LongAggregation;
import functionalj.function.aggregator.LongAggregationToBoolean;
import functionalj.function.aggregator.LongAggregationToLong;
import functionalj.list.longlist.AsLongFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.LongLongTuple;
import lombok.val;

// TODO - Intersect
// TODO - Shuffle
@FunctionalInterface
public interface LongStreamPlus extends LongStream, AsLongStreamPlus, LongStreamPlusWithCombine, LongStreamPlusWithFilter, LongStreamPlusWithFlatMap, LongStreamPlusWithLimit, LongStreamPlusWithMap, LongStreamPlusWithMapFirst, LongStreamPlusWithMapGroup, LongStreamPlusWithMapMulti, LongStreamPlusWithMapThen, LongStreamPlusWithMapToMap, LongStreamPlusWithMapToTuple, LongStreamPlusWithMapWithIndex, LongStreamPlusWithModify, LongStreamPlusWithSegment, LongStreamPlusWithPeek, LongStreamPlusWithPipe, LongStreamPlusWithSort, LongStreamPlusWithSplit {
    
    /**
     * Throw a no more element exception. This is used for generator.
     */
    public static int noMoreElement() throws NoMoreResultException {
        return SupplierBackedIterator.noMoreElement();
    }
    
    // == Constructor ==
    /**
     * Returns an empty LongStreamPlus.
     */
    public static LongStreamPlus empty() {
        return LongStreamPlus.from(LongStream.empty());
    }
    
    /**
     * Returns an empty StreamPlus.
     */
    public static LongStreamPlus emptylongStream() {
        return empty();
    }
    
    /**
     * Returns an empty StreamPlus.
     */
    public static LongStreamPlus of(long... longs) {
        if ((longs == null) || (longs.length == 0))
            return LongStreamPlus.empty();
        return LongStreamPlus.from(LongStream.of(Arrays.copyOf(longs, longs.length)));
    }
    
    public static LongStreamPlus longs(long... longs) {
        return LongStreamPlus.of(longs);
    }
    
    // TODO - from-to, from almostTo, stepping.
    public static LongStreamPlus from(LongStream longStream) {
        if (longStream instanceof LongStreamPlus)
            return (LongStreamPlus) longStream;
        return () -> longStream;
    }
    
    public static LongStreamPlus zeroes() {
        return LongStreamPlus.generate(() -> 0);
    }
    
    public static LongStreamPlus zeroes(int count) {
        return LongStreamPlus.generate(() -> 0).limit(count);
    }
    
    public static LongStreamPlus ones() {
        return LongStreamPlus.generate(() -> 1);
    }
    
    public static LongStreamPlus ones(int count) {
        return LongStreamPlus.generate(() -> 1).limit(count);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static LongStreamPlus repeat(long... data) {
        return cycle(data);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static LongStreamPlus repeat(AsLongFuncList data) {
        return cycle(data);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static LongStreamPlus cycle(long... data) {
        val size = data.length;
        return LongStreamPlus.from(LongStream.range(0, Long.MAX_VALUE).map(i -> data[(int) (i % size)]));
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static LongStreamPlus cycle(AsLongFuncList longs) {
        val list = longs.asLongFuncList();
        val size = list.size();
        return LongStreamPlus.from(LongStream.range(0, Long.MAX_VALUE).map(i -> list.get((int) (i % size))));
    }
    
    /**
     * Create a stream that for a loop with the number of time given - the value is the index of the loop.
     */
    public static LongStreamPlus loop() {
        return LongStreamPlus.infinite();
    }
    
    /**
     * Create a stream that for a loop with the number of time given - the value is the index of the loop.
     */
    public static LongStreamPlus loop(long time) {
        return LongStreamPlus.infinite().limit(time);
    }
    
    public static LongStreamPlus loopBy(long step) {
        return LongStreamPlus.infinite().map(i -> i * step);
    }
    
    public static LongStreamPlus loopBy(long step, long time) {
        return LongStreamPlus.loopBy(step).limit(time);
    }
    
    /**
     * Create a stream that for an infinite loop - the value is the index of the loop.
     */
    public static LongStreamPlus infinite() {
        return LongStreamPlus.from(LongStream.range(0, Long.MAX_VALUE));
    }
    
    /**
     * Create a stream that for an infinite loop - the value is the index of the loop.
     */
    public static LongStreamPlus infiniteInt() {
        return infinite();
    }
    
    /**
     * Create a stream that for an infinite loop - the value is the index of the loop.
     */
    public static LongStreamPlus infiniteLong() {
        return infinite();
    }
    
    public static LongStreamPlus naturalNumbers() {
        return LongStreamPlus.from(LongStream.range(1, Long.MAX_VALUE));
    }
    
    public static LongStreamPlus naturalNumbers(long count) {
        return naturalNumbers().limit(count);
    }
    
    public static LongStreamPlus wholeNumbers() {
        return LongStreamPlus.from(LongStream.range(0, Long.MAX_VALUE));
    }
    
    public static LongStreamPlus wholeNumbers(long count) {
        return wholeNumbers().limit(count);
    }
    
    /**
     * Create a StreamPlus that for a loop from the start value inclusively to the end value exclusively.
     */
    public static LongStreamPlus range(long startInclusive, long endExclusive) {
        return LongStreamPlus.from(LongStream.range(startInclusive, endExclusive));
    }
    
    /**
     * Concatenate all the given streams.
     */
    public static LongStreamPlus concat(LongStream... streams) {
        return StreamPlus.of(streams).map(s -> LongStreamPlus.from(s)).flatMapToLong(s -> s.longStream());
    }
    
    /**
     * Concatenate all the given streams.
     */
    public static LongStreamPlus combine(LongStreamPlus... streams) {
        return concat(streams);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static LongStreamPlus generate(LongSupplier supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static LongStreamPlus generateWith(LongSupplier supplier) {
        val iterable = (LongIterable) () -> new LongSupplierBackedIterator(supplier);
        return LongStreamPlus.from(StreamSupport.longStream(iterable.spliterator(), false));
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 &lt;- seed,
     *      2 &lt;- (1*2),
     *      4 &lt;- ((1*2)*2),
     *      8 &lt;- (((1*2)*2)*2),
     *      16 &lt;- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of compound()
     */
    public static LongStreamPlus iterate(long seed, LongUnaryOperator compounder) {
        return LongStreamPlus.from(LongStream.iterate(seed, compounder));
    }
    
    public static LongStreamPlus iterate(long seed, LongAggregationToLong aggregation) {
        val compounder = aggregation.newAggregator();
        return iterate(seed, compounder);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 &lt;- seed,
     *      2 &lt;- (1*2),
     *      4 &lt;- ((1*2)*2),
     *      8 &lt;- (((1*2)*2)*2),
     *      16 &lt;- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of iterate()
     */
    public static LongStreamPlus compound(long seed, LongUnaryOperator compounder) {
        return iterate(seed, compounder);
    }
    
    public static LongStreamPlus compound(long seed, LongAggregationToLong aggregation) {
        return iterate(seed, aggregation);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 &lt;- seed1,
     *      1 &lt;- seed2,
     *      2 &lt;- (1+1),
     *      3 &lt;- (1+2),
     *      5 &lt;- (2+3),
     *      8 &lt;- (5+8)
     *      ...
     *
     * Note: this is an alias of compound()
     */
    public static LongStreamPlus iterate(long seed1, long seed2, LongBinaryOperator compounder) {
        return LongStreamPlus.from(StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(Long.MAX_VALUE, 0) {
    
            private final AtomicLong first = new AtomicLong(seed1);
    
            private final AtomicLong second = new AtomicLong(seed2);
    
            private volatile AtomicBoolean isInOrder = null;
    
            @Override
            public boolean tryAdvance(LongConsumer action) {
                if (isInOrder == null) {
                    action.accept(seed1);
                    action.accept(seed2);
                    isInOrder = new AtomicBoolean(true);
                }
                boolean inOrder = isInOrder.get();
                if (inOrder) {
                    long next = compounder.applyAsLong(first.get(), second.get());
                    action.accept(next);
                    first.set(next);
                } else {
                    long next = compounder.applyAsLong(second.get(), first.get());
                    action.accept(next);
                    second.set(next);
                }
                isInOrder.set(!inOrder);
                return true;
            }
        }, false));
    }
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 &lt;- seed1,
     *      1 &lt;- seed2,
     *      2 &lt;- (1+1),
     *      3 &lt;- (1+2),
     *      5 &lt;- (2+3),
     *      8 &lt;- (5+8)
     *      ...
     *
     * Note: this is an alias of iterate()
     */
    public static LongStreamPlus compound(long seed1, long seed2, LongBinaryOperator compounder) {
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
     */
    public static StreamPlus<LongLongTuple> zipOf(LongStream stream1, LongStream stream2) {
        return LongStreamPlus.from(stream1).zipWith(stream2);
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
     */
    public static StreamPlus<LongLongTuple> zipOf(LongStream stream1, LongStream stream2, long defaultValue) {
        return LongStreamPlus.from(stream1).zipWith(stream2, defaultValue);
    }
    
    public static StreamPlus<LongLongTuple> zipOf(LongStream stream1, long defaultValue1, LongStream stream2, long defaultValue2) {
        return LongStreamPlus.from(stream1).zipWith(defaultValue1, stream2, defaultValue2);
    }
    
    public static LongStreamPlus zipOf(LongStream stream1, LongStream stream2, LongBinaryOperator merger) {
        return LongStreamPlus.from(stream1).zipWith(stream2, merger);
    }
    
    public static LongStreamPlus zipOf(LongStream stream1, LongStream stream2, long defaultValue, LongBinaryOperator merger) {
        return LongStreamPlus.from(stream1).zipWith(stream2, defaultValue, merger);
    }
    
    public static LongStreamPlus zipOf(LongStream stream1, long defaultValue1, LongStream stream2, long defaultValue2, LongBinaryOperator merger) {
        return LongStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    // == Core ==
    /**
     * Return the stream of data behind this stream.
     */
    public LongStream longStream();
    
    /**
     * Return this stream.
     */
    public default LongStreamPlus longStreamPlus() {
        return this;
    }
    
    // -- Derive --
    public default LongStreamPlus derive(Function<LongStreamPlus, LongStream> action) {
        return LongStreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus deriveToInt(Function<LongStreamPlus, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
    public default DoubleStreamPlus deriveToDouble(Function<LongStreamPlus, DoubleStream> action) {
        return DoubleStreamPlus.from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Function<LongStreamPlus, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
    }
    
    @Override
    public default StreamPlus<Long> boxed() {
        return StreamPlus.from(longStream().boxed());
    }
    
    public default IntStreamPlus asIntStream() {
        return mapToInt(value -> (int) value);
    }
    
    public default LongStreamPlus asLongStream() {
        return this;
    }
    
    @Override
    public default DoubleStreamPlus asDoubleStream() {
        return mapToDouble(i -> i);
    }
    
    // -- Characteristics --
    /**
     * Returns an equivalent stream that is sequential.  May return
     * itself, either because the stream was already sequential, or because
     * the underlying stream state was modified to be sequential.
     *
     * &lt;p&gt;This is an &lt;a href="package-summary.html#StreamOps"&gt;intermediate
     * operation&lt;/a&gt;.
     *
     * @return a sequential stream
     */
    @Override
    public default LongStreamPlus sequential() {
        return LongStreamPlus.from(longStream().sequential());
    }
    
    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     *
     * &lt;p&gt;This is an &lt;a href="package-summary.html#StreamOps"&gt;intermediate
     * operation&lt;/a&gt;.
     *
     * @return a parallel stream
     */
    @Override
    public default LongStreamPlus parallel() {
        return LongStreamPlus.from(longStream().parallel());
    }
    
    /**
     * Returns an equivalent stream that is
     * &lt;a href="package-summary.html#Ordering"&gt;unordered&lt;/a&gt;.  May return
     * itself, either because the stream was already unordered, or because
     * the underlying stream state was modified to be unordered.
     *
     * &lt;p&gt;This is an &lt;a href="package-summary.html#StreamOps"&gt;intermediate
     * operation&lt;/a&gt;.
     *
     * @return an unordered stream
     */
    @Override
    public default LongStreamPlus unordered() {
        return LongStreamPlus.from(longStream().unordered());
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
        return longStream().isParallel();
    }
    
    // -- Close --
    @Terminal
    @Override
    public default void close() {
        longStream().close();
    }
    
    @Override
    public default LongStreamPlus onClose(Runnable closeHandler) {
        return LongStreamPlus.from(longStream().onClose(closeHandler));
    }
    
    // -- Iterator --
    /**
     * @return a iterator of this FuncList.
     */
    @Override
    public default LongIteratorPlus iterator() {
        return LongIteratorPlus.from(longStream().iterator());
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    @Override
    public default Spliterator.OfLong spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    // == Functionalities ==
    // -- Map --
    @Override
    public default LongStreamPlus map(LongUnaryOperator mapper) {
        return LongStreamPlus.from(longStream().map(mapper));
    }
    
    @Override
    public default IntStreamPlus mapToInt(LongToIntFunction mapper) {
        return IntStreamPlus.from(longStream().mapToInt(mapper));
    }
    
    public default LongStreamPlus mapToLong(LongUnaryOperator mapper) {
        return LongStreamPlus.from(longStream().map(mapper));
    }
    
    public default DoubleStreamPlus mapToDouble() {
        return mapToDouble(i -> (double) i);
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(LongToDoubleFunction mapper) {
        return DoubleStreamPlus.from(longStream().mapToDouble(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(LongFunction<? extends T> mapper) {
        return StreamPlus.from(longStream().mapToObj(mapper));
    }
    
    public default StreamPlus<String> mapToString() {
        return mapToObj(i -> "" + i);
    }
    
    // -- FlatMap --
    @Override
    public default LongStreamPlus flatMap(LongFunction<? extends LongStream> mapper) {
        return LongStreamPlus.from(longStream().flatMap(mapper));
    }
    
    public default LongStreamPlus flatMap(LongAggregation<? extends LongStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMap(mapper);
    }
    
    public default IntStreamPlus flatMapToInt(LongFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(mapToObj(mapper).flatMapToInt(itself()));
    }
    
    public default IntStreamPlus flatMapToInt(LongAggregation<? extends IntStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToInt(mapper);
    }
    
    public default LongStreamPlus flatMapToLong(LongFunction<? extends LongStream> mapper) {
        return flatMap(mapper);
    }
    
    public default LongStreamPlus flatMapToLong(LongAggregation<? extends LongStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMap(mapper);
    }
    
    public default DoubleStreamPlus flatMapToDouble(LongFunction<? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(mapToObj(mapper).flatMapToDouble(itself()));
    }
    
    public default DoubleStreamPlus flatMapToDouble(LongAggregation<? extends DoubleStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToDouble(mapper);
    }
    
    public default <DATA> StreamPlus<DATA> flatMapToObj(LongFunction<? extends Stream<DATA>> mapper) {
        return StreamPlus.from(mapToObj(mapper).flatMap(itself()));
    }
    
    public default <DATA> StreamPlus<DATA> flatMapToObj(LongAggregation<? extends Stream<DATA>> aggregation) {
        val mapper = aggregation.newAggregator();
        return StreamPlus.from(mapToObj(mapper).flatMap(itself()));
    }
    
    // -- Filter --
    @Override
    public default LongStreamPlus filter(LongPredicate predicate) {
        return from(longStream().filter(predicate));
    }
    
    public default LongStreamPlus filter(LongAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return from(longStream().filter(predicate));
    }
    
    // -- Peek --
    @Override
    public default LongStreamPlus peek(LongConsumer action) {
        return LongStreamPlus.from(longStream().peek(action));
    }
    
    // -- Limit/Skip --
    @Override
    public default LongStreamPlus limit(long maxSize) {
        return LongStreamPlus.from(longStream().limit(maxSize));
    }
    
    @Override
    public default LongStreamPlus skip(long offset) {
        return LongStreamPlus.from(longStream().skip(offset));
    }
    
    // -- Distinct --
    @Override
    public default LongStreamPlus distinct() {
        return LongStreamPlus.from(longStream().distinct());
    }
    
    // -- Sorted --
    @Eager
    @Override
    public default LongStreamPlus sorted() {
        return LongStreamPlus.from(longStream().sorted());
    }
    
    @Eager
    public default LongStreamPlus sorted(LongComparator comparator) {
        return LongStreamPlus.from(longStream().boxed().sorted((a, b) -> comparator.compare(a, b)).mapToLong(i -> i));
    }
    
    // -- Terminate --
    @Eager
    @Terminal
    @Override
    public default void forEach(LongConsumer action) {
        terminate(this, stream -> {
            stream.forEach(action);
        });
    }
    
    @Eager
    @Terminal
    @Sequential
    @Override
    public default void forEachOrdered(LongConsumer action) {
        terminate(this, stream -> {
            stream.sequential().forEachOrdered(action);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default long reduce(long identity, LongBinaryOperator reducer) {
        return terminate(this, stream -> {
            return stream.reduce(identity, reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalLong reduce(LongBinaryOperator reducer) {
        return terminate(this, stream -> {
            return stream.reduce(reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return terminate(this, stream -> {
            return stream.collect(supplier, accumulator, combiner);
        });
    }
    
    // -- statistics --
    @Eager
    @Terminal
    @Override
    public default OptionalLong min() {
        return terminate(this, stream -> {
            return stream.min();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalLong max() {
        return terminate(this, stream -> {
            return stream.max();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default long count() {
        return terminate(this, stream -> {
            return stream.count();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default long sum() {
        return terminate(this, stream -> {
            return stream.sum();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble average() {
        return terminate(this, stream -> {
            return stream.average();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default LongSummaryStatistics summaryStatistics() {
        return terminate(this, stream -> {
            return stream.summaryStatistics();
        });
    }
    
    // -- Match --
    @Terminal
    @Override
    public default boolean anyMatch(LongPredicate predicate) {
        return terminate(this, stream -> {
            return stream.anyMatch(predicate);
        });
    }
    
    @Terminal
    public default boolean anyMatch(LongAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.anyMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean allMatch(LongPredicate predicate) {
        return terminate(this, stream -> {
            return stream.allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    public default boolean allMatch(LongAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean noneMatch(LongPredicate predicate) {
        return terminate(this, stream -> {
            return stream.noneMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    public default boolean noneMatch(LongAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.noneMatch(predicate);
        });
    }
    
    @Terminal
    @Override
    public default OptionalLong findFirst() {
        return terminate(this, stream -> {
            return stream.findFirst();
        });
    }
    
    @Terminal
    @Override
    public default OptionalLong findAny() {
        return terminate(this, stream -> {
            return stream.findAny();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalLong findLast() {
        return terminate(this, stream -> {
            boolean[] isAdded = new boolean[] { false };
            long[] dataRef = new long[1];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalLong.of(dataRef[0]) : OptionalLong.empty();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalLong firstResult() {
        return findFirst();
    }
    
    @Sequential
    @Terminal
    public default OptionalLong lastResult() {
        return terminate(this, stream -> {
            boolean[] isAdded = new boolean[] { false };
            long[] dataRef = new long[0];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalLong.of(dataRef[0]) : OptionalLong.empty();
        });
    }
    
    // == Conversion ==
    @Eager
    @Terminal
    @Override
    public default long[] toArray() {
        return terminate(this, stream -> {
            return stream.toArray();
        });
    }
}
