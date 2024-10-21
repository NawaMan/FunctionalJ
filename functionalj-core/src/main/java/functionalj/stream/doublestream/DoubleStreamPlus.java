// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.doublestream;

import static functionalj.function.Func.itself;
import static functionalj.stream.doublestream.DoubleStreamPlusHelper.terminate;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.function.DoubleComparator;
import functionalj.function.aggregator.DoubleAggregation;
import functionalj.function.aggregator.DoubleAggregationToBoolean;
import functionalj.function.aggregator.DoubleAggregationToDouble;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.DoubleDoubleTuple;
import lombok.val;

// TODO - Intersect
// TODO - Shuffle
@FunctionalInterface
public interface DoubleStreamPlus extends DoubleStream, AsDoubleStreamPlus, DoubleStreamPlusWithCombine, DoubleStreamPlusWithFilter, DoubleStreamPlusWithFlatMap, DoubleStreamPlusWithLimit, DoubleStreamPlusWithMap, DoubleStreamPlusWithMapFirst, DoubleStreamPlusWithMapGroup, DoubleStreamPlusWithMapMulti, DoubleStreamPlusWithMapThen, DoubleStreamPlusWithMapToMap, DoubleStreamPlusWithMapToTuple, DoubleStreamPlusWithMapWithIndex, DoubleStreamPlusWithModify, DoubleStreamPlusWithSegment, DoubleStreamPlusWithPeek, DoubleStreamPlusWithPipe, DoubleStreamPlusWithSort, DoubleStreamPlusWithSplit {
    
    /**
     * Throw a no more element exception. This is used for generator.
     */
    public static double noMoreElement() throws NoMoreResultException {
        SupplierBackedIterator.noMoreElement();
        return Double.NaN;
    }
    
    // == Constructor ==
    /**
     * Returns an empty IntStreamPlus.
     */
    public static DoubleStreamPlus empty() {
        return DoubleStreamPlus.from(DoubleStream.empty());
    }
    
    /**
     * Returns an empty StreamPlus.
     */
    public static DoubleStreamPlus emptyDoubleStream() {
        return empty();
    }
    
    /**
     * Returns an empty StreamPlus.
     */
    public static DoubleStreamPlus of(double... values) {
        if ((values == null) || (values.length == 0))
            return DoubleStreamPlus.empty();
        return DoubleStreamPlus.from(DoubleStream.of(Arrays.copyOf(values, values.length)));
    }
    
    public static DoubleStreamPlus doubles(double... doubles) {
        return DoubleStreamPlus.of(doubles);
    }
    
    // TODO - from-to, from almostTo, stepping.
    public static DoubleStreamPlus from(DoubleStream doubleStream) {
        if (doubleStream instanceof DoubleStreamPlus)
            return (DoubleStreamPlus) doubleStream;
        return () -> doubleStream;
    }
    
    public static DoubleStreamPlus zeroes() {
        return DoubleStreamPlus.generate(() -> 0.0);
    }
    
    public static DoubleStreamPlus zeroes(int count) {
        return DoubleStreamPlus.generate(() -> 0.0).limit(count);
    }
    
    public static DoubleStreamPlus ones() {
        return DoubleStreamPlus.generate(() -> 1.0);
    }
    
    public static DoubleStreamPlus ones(int count) {
        return DoubleStreamPlus.generate(() -> 1.0).limit(count);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static DoubleStreamPlus repeat(double... data) {
        return cycle(data);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static DoubleStreamPlus repeat(AsDoubleFuncList data) {
        return cycle(data);
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static DoubleStreamPlus cycle(double... data) {
        val doubles = Arrays.copyOf(data, data.length);
        val size = doubles.length;
        return DoubleStreamPlus.from(IntStream.range(0, Integer.MAX_VALUE).mapToDouble(i -> data[i % size]));
    }
    
    /**
     * Create a stream that is the repeat of the given array of data.
     */
    public static DoubleStreamPlus cycle(AsDoubleFuncList doubles) {
        val list = doubles.asDoubleFuncList();
        val size = list.size();
        return DoubleStreamPlus.from(IntStream.iterate(0, i -> i + 1).mapToDouble(i -> list.get(i % size)));
    }
    
    /**
     * Create a stream that for a loop with the number of time given - the value is the index of the loop.
     */
    public static DoubleStreamPlus loop() {
        return DoubleStreamPlus.infinite();
    }
    
    /**
     * Create a stream that for a loop with the number of time given - the value is the index of the loop.
     */
    public static DoubleStreamPlus loop(int time) {
        return DoubleStreamPlus.infinite().limit(time);
    }
    
    public static DoubleStreamPlus loopBy(double step) {
        return DoubleStreamPlus.infinite().map(i -> i * step);
    }
    
    public static DoubleStreamPlus loopBy(double step, int time) {
        return DoubleStreamPlus.loopBy(step).limit(time);
    }
    
    /**
     * Create a stream that for an infinite loop - the value is the index of the loop.
     */
    public static DoubleStreamPlus infinite() {
        return IntStreamPlus.infinite().asDoubleStream();
    }
    
    /**
     * Create a stream that for an infinite loop - the value is the index of the loop.
     */
    public static DoubleStreamPlus infiniteInt() {
        return infinite();
    }
    
    public static DoubleStreamPlus naturalNumbers() {
        return IntStreamPlus.naturalNumbers().asDoubleStream();
    }
    
    public static DoubleStreamPlus naturalNumbers(int count) {
        return IntStreamPlus.naturalNumbers(count).asDoubleStream();
    }
    
    public static DoubleStreamPlus wholeNumbers() {
        return IntStreamPlus.wholeNumbers().asDoubleStream();
    }
    
    public static DoubleStreamPlus wholeNumbers(int count) {
        return IntStreamPlus.wholeNumbers(count).asDoubleStream();
    }
    
    /**
     * Create a FuncList that for a loop with the number of time given - the value is the index of the loop.
     */
    public static DoubleStreamPlus range(double startInclusive, double endExclusive) {
        return DoubleStreamPlus.iterate(startInclusive, d -> d + 1).acceptUntil(d -> d >= endExclusive);
    }
    
    /**
     * Create a StreamPlus that for a loop from the start value inclusively by the given step.
     */
    public static DoubleStreamPlus stepFrom(double startInclusive, double step) {
        return DoubleStreamPlus.iterate(startInclusive, d -> d + step);
    }
    
    /**
     * Concatenate all the given streams.
     */
    @SuppressWarnings("resource")
    public static DoubleStreamPlus concat(DoubleStream... streams) {
        return StreamPlus.of(streams).map(s -> DoubleStreamPlus.from(s)).flatMapToDouble(s -> s.doubleStream());
    }
    
    /**
     * Concatenate all the given streams.
     */
    public static DoubleStreamPlus combine(DoubleStreamPlus... streams) {
        return concat(streams);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static DoubleStreamPlus generate(DoubleSupplier supplier) {
        return generateWith(supplier);
    }
    
    /**
     * Create a StreamPlus from the supplier.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static DoubleStreamPlus generateWith(DoubleSupplier supplier) {
        val iterable = (DoubleIterable) () -> new DoubleSupplierBackedIterator(supplier);
        return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
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
    public static DoubleStreamPlus iterate(double seed, DoubleUnaryOperator compounder) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, compounder));
    }
    
    public static DoubleStreamPlus iterate(double seed, DoubleAggregationToDouble aggregation) {
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
    public static DoubleStreamPlus compound(double seed, DoubleUnaryOperator compounder) {
        return iterate(seed, compounder);
    }
    
    public static DoubleStreamPlus compound(double seed, DoubleAggregationToDouble aggregation) {
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
    public static DoubleStreamPlus iterate(double seed1, double seed2, DoubleBinaryOperator compounder) {
        return DoubleStreamPlus.from(StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(Long.MAX_VALUE, 0) {
        
            private final double[] first = new double[] { seed1 };
        
            private final double[] second = new double[] { seed2 };
        
            private volatile AtomicBoolean isInOrder = null;
        
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (isInOrder == null) {
                    action.accept(seed1);
                    action.accept(seed2);
                    isInOrder = new AtomicBoolean(true);
                }
                boolean inOrder = isInOrder.get();
                if (inOrder) {
                    double next = compounder.applyAsDouble(first[0], second[0]);
                    action.accept(next);
                    first[0] = next;
                } else {
                    double next = compounder.applyAsDouble(second[0], first[0]);
                    action.accept(next);
                    second[0] = next;
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
    public static DoubleStreamPlus compound(double seed1, double seed2, DoubleBinaryOperator compounder) {
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
    public static StreamPlus<DoubleDoubleTuple> zipOf(DoubleStream stream1, DoubleStream stream2) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2);
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
    public static StreamPlus<DoubleDoubleTuple> zipOf(DoubleStream stream1, DoubleStream stream2, double defaultValue) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue);
    }
    
    public static StreamPlus<DoubleDoubleTuple> zipOf(DoubleStream stream1, double defaultValue1, DoubleStream stream2, double defaultValue2) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2);
    }
    
    public static DoubleStreamPlus zipOf(DoubleStream stream1, DoubleStream stream2, DoubleBinaryOperator merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, merger);
    }
    
    public static DoubleStreamPlus zipOf(DoubleStream stream1, DoubleStream stream2, double defaultValue, DoubleBinaryOperator merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue, merger);
    }
    
    public static DoubleStreamPlus zipOf(DoubleStream stream1, double defaultValue1, DoubleStream stream2, double defaultValue2, DoubleBinaryOperator merger) {
        return DoubleStreamPlus.from(stream1).zipWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    // == Core ==
    /**
     * Return the stream of data behind this stream.
     */
    public DoubleStream doubleStream();
    
    /**
     * Return this stream.
     */
    public default DoubleStreamPlus doubleStreamPlus() {
        return this;
    }
    
    // -- Derive --
    public default DoubleStreamPlus derive(Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleStreamPlus.from(action.apply(this));
    }
    
    public default IntStreamPlus deriveToInt(Function<DoubleStreamPlus, IntStream> action) {
        return IntStreamPlus.from(action.apply(this));
    }
    
    public default DoubleStreamPlus deriveToDouble(Function<DoubleStreamPlus, DoubleStream> action) {
        return DoubleStreamPlus.from(action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObj(Function<DoubleStreamPlus, Stream<TARGET>> action) {
        return StreamPlus.from(action.apply(this));
    }
    
    @Override
    public default StreamPlus<Double> boxed() {
        return StreamPlus.from(doubleStream().boxed());
    }
    
    public default LongStream asLongStream() {
        return mapToLong(value -> Math.round(value));
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
    public default DoubleStreamPlus sequential() {
        return DoubleStreamPlus.from(doubleStream().sequential());
    }
    
    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     *
     * &lt;p&gt;This is an &lt;a href="package-summary.html#StreamOps"&gt;intermediate
     * operation&lt;/a&gt;.Combine
     *
     * @return a parallel stream
     */
    @Override
    public default DoubleStreamPlus parallel() {
        return DoubleStreamPlus.from(doubleStream().parallel());
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
    public default DoubleStreamPlus unordered() {
        return DoubleStreamPlus.from(doubleStream().unordered());
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
        return doubleStream().isParallel();
    }
    
    // -- Close --
    @Terminal
    @Override
    public default void close() {
        doubleStream().close();
    }
    
    @Override
    public default DoubleStreamPlus onClose(Runnable closeHandler) {
        return DoubleStreamPlus.from(doubleStream().onClose(closeHandler));
    }
    
    // -- Iterator --
    /**
     * @return a iterator of this FuncList.
     */
    @Override
    public default DoubleIteratorPlus iterator() {
        return DoubleIteratorPlus.from(doubleStream().iterator());
    }
    
    /**
     * @return a spliterator of this FuncList.
     */
    @Override
    public default Spliterator.OfDouble spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    // == Functionalities ==
    // -- Map --
    @Override
    public default DoubleStreamPlus map(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    public default IntStreamPlus mapToInt() {
        return mapToInt(d -> (int) Math.round(d));
    }
    
    public default IntStreamPlus mapToInt(DoubleToIntFunction mapper) {
        return IntStreamPlus.from(doubleStream().mapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus mapToLong(DoubleToLongFunction mapper) {
        return LongStreamPlus.from(doubleStream().mapToLong(mapper));
    }
    
    public default DoubleStreamPlus mapToDouble(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(doubleStream().map(mapper));
    }
    
    @Override
    public default <T> StreamPlus<T> mapToObj(DoubleFunction<? extends T> mapper) {
        return StreamPlus.from(doubleStream().mapToObj(mapper));
    }
    
    public default StreamPlus<String> mapToString() {
        return mapToObj(i -> "" + i);
    }
    
    // -- FlatMap --
    @Override
    public default DoubleStreamPlus flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(doubleStream().flatMap(mapper));
    }
    
    public default DoubleStreamPlus flatMap(DoubleAggregation<? extends DoubleStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMap(mapper);
    }
    
    public default IntStreamPlus flatMapToInt(DoubleFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(mapToObj(mapper).flatMapToInt(itself()));
    }
    
    public default IntStreamPlus flatMapToInt(DoubleAggregation<? extends IntStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return IntStreamPlus.from(mapToObj(mapper).flatMapToInt(itself()));
    }
    
    public default LongStreamPlus flatMapToLong(DoubleFunction<? extends LongStream> mapper) {
        return LongStreamPlus.from(mapToObj(mapper).flatMapToLong(itself()));
    }
    
    public default LongStreamPlus flatMapToLong(DoubleAggregation<? extends LongStream> aggregation) {
        val mapper = aggregation.newAggregator();
        return LongStreamPlus.from(mapToObj(mapper).flatMapToLong(itself()));
    }
    
    public default DoubleStreamPlus flatMapToDouble(DoubleFunction<? extends DoubleStream> mapper) {
        return flatMap(mapper);
    }
    
    public default DoubleStreamPlus flatMapToDouble(DoubleAggregation<? extends DoubleStream> aggregation) {
        return flatMap(aggregation);
    }
    
    public default <DATA> StreamPlus<DATA> flatMapToObj(DoubleFunction<? extends Stream<DATA>> mapper) {
        return StreamPlus.from(mapToObj(mapper).flatMap(itself()));
    }
    
    public default <DATA> StreamPlus<DATA> flatMapToObj(DoubleAggregation<? extends Stream<DATA>> aggregation) {
        val mapper = aggregation.newAggregator();
        return StreamPlus.from(mapToObj(mapper).flatMap(itself()));
    }
    
    // -- Filter --
    @Override
    public default DoubleStreamPlus filter(DoublePredicate predicate) {
        return from(doubleStream().filter(predicate));
    }
    
    public default DoubleStreamPlus filter(DoubleAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return from(doubleStream().filter(predicate));
    }
    
    // -- Peek --
    @Override
    public default DoubleStreamPlus peek(DoubleConsumer action) {
        return DoubleStreamPlus.from(doubleStream().peek(action));
    }
    
    // -- Limit/Skip --
    @Override
    public default DoubleStreamPlus limit(long maxSize) {
        return DoubleStreamPlus.from(doubleStream().limit(maxSize));
    }
    
    @Override
    public default DoubleStreamPlus skip(long offset) {
        return DoubleStreamPlus.from(doubleStream().skip(offset));
    }
    
    // -- Distinct --
    @Override
    public default DoubleStreamPlus distinct() {
        return DoubleStreamPlus.from(doubleStream().distinct());
    }
    
    // -- Sorted --
    @Eager
    @Override
    public default DoubleStreamPlus sorted() {
        return DoubleStreamPlus.from(doubleStream().sorted());
    }
    
    @Eager
    public default DoubleStreamPlus sorted(DoubleComparator comparator) {
        return DoubleStreamPlus.from(doubleStream().boxed().sorted((a, b) -> comparator.compare(a, b)).mapToDouble(d -> d));
    }
    
    // -- Terminate --
    @Eager
    @Terminal
    @Override
    public default void forEach(DoubleConsumer action) {
        terminate(this, stream -> {
            stream.forEach(action);
        });
    }
    
    @Eager
    @Terminal
    @Sequential
    @Override
    public default void forEachOrdered(DoubleConsumer action) {
        terminate(this, stream -> {
            stream.sequential().forEachOrdered(action);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default double reduce(double identity, DoubleBinaryOperator reducer) {
        return terminate(this, stream -> {
            return stream.reduce(identity, reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble reduce(DoubleBinaryOperator reducer) {
        return terminate(this, stream -> {
            return stream.reduce(reducer);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return terminate(this, stream -> {
            return stream.collect(supplier, accumulator, combiner);
        });
    }
    
    // -- statistics --
    @Eager
    @Terminal
    @Override
    public default OptionalDouble min() {
        return terminate(this, stream -> {
            return stream.min();
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default OptionalDouble max() {
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
    public default double sum() {
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
    public default DoubleSummaryStatistics summaryStatistics() {
        return terminate(this, stream -> {
            return stream.summaryStatistics();
        });
    }
    
    // -- Match --
    @Terminal
    @Override
    public default boolean anyMatch(DoublePredicate predicate) {
        return terminate(this, stream -> {
            return stream.anyMatch(predicate);
        });
    }
    
    @Terminal
    public default boolean anyMatch(DoubleAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.anyMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean allMatch(DoublePredicate predicate) {
        return terminate(this, stream -> {
            return stream.allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    public default boolean allMatch(DoubleAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.allMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    @Override
    public default boolean noneMatch(DoublePredicate predicate) {
        return terminate(this, stream -> {
            return stream.noneMatch(predicate);
        });
    }
    
    @Eager
    @Terminal
    public default boolean noneMatch(DoubleAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return terminate(this, stream -> {
            return stream.noneMatch(predicate);
        });
    }
    
    @Terminal
    @Override
    public default OptionalDouble findFirst() {
        return terminate(this, stream -> {
            return stream.findFirst();
        });
    }
    
    @Terminal
    @Override
    public default OptionalDouble findAny() {
        return terminate(this, stream -> {
            return stream.findAny();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble findLast() {
        return terminate(this, stream -> {
            boolean[] isAdded = new boolean[] { false };
            double[] dataRef = new double[1];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalDouble.of(dataRef[0]) : OptionalDouble.empty();
        });
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble firstResult() {
        return findFirst();
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble lastResult() {
        return terminate(this, stream -> {
            boolean[] isAdded = new boolean[] { false };
            double[] dataRef = new double[1];
            stream.peek(i -> isAdded[0] = true).forEach(i -> dataRef[0] = i);
            return isAdded[0] ? OptionalDouble.of(dataRef[0]) : OptionalDouble.empty();
        });
    }
    
    // == Conversion ==
    @Eager
    @Terminal
    @Override
    public default double[] toArray() {
        return terminate(this, stream -> {
            return stream.toArray();
        });
    }
}
