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
package functionalj.stream.intstream;

import static functionalj.stream.intstream.IntStreamPlusHelper.dummy;
import static functionalj.tuple.IntIntTuple.intTuple;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import functionalj.function.IntComparator;
import functionalj.function.aggregator.IntAggregation;
import functionalj.function.aggregator.IntAggregationToBoolean;
import functionalj.function.aggregator.IntAggregationToInt;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.IntIntTuple;
import lombok.val;

public interface AsIntStreamPlusWithStatistic {
    
    public IntStreamPlus intStreamPlus();
    
    /**
     * @return  the size of the stream
     */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = intStreamPlus();
        return (int) streamPlus.count();
    }
    
    @Eager
    @Terminal
    public default long count() {
        val streamPlus = intStreamPlus();
        return streamPlus.count();
    }
    
    @Eager
    @Terminal
    public default int sum() {
        val streamPlus = intStreamPlus();
        return streamPlus.sum();
    }
    
    /**
     * @return the product of all the number
     */
    @Eager
    @Terminal
    public default OptionalInt product() {
        val streamPlus = intStreamPlus();
        return streamPlus.reduce((a, b) -> a * b);
    }
    
    @Eager
    @Terminal
    public default OptionalDouble average() {
        val streamPlus = intStreamPlus();
        return streamPlus.average();
    }
    
    public default IntSummaryStatistics summaryStatistics() {
        val streamPlus = intStreamPlus();
        return streamPlus.summaryStatistics();
    }
    
    @Eager
    @Terminal
    public default /**
     * Using the comparator, find the minimal value
     */
    OptionalInt min() {
        val streamPlus = intStreamPlus();
        return streamPlus.min();
    }
    
    @Eager
    @Terminal
    public default /**
     * Using the comparator, find the maximum value
     */
    OptionalInt max() {
        val streamPlus = intStreamPlus();
        return streamPlus.max();
    }
    
    /**
     * Fund the min value using the comparator
     */
    public default OptionalInt min(IntComparator comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus.sorted(comparator).findFirst();
    }
    
    /**
     * Fund the max value using the comparator
     */
    public default OptionalInt max(IntComparator comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus.sorted(comparator.reverse()).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the smallest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt minBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the smallest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt minBy(IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minBy(mapper);
    }
    
    /**
     * Return the value whose mapped value is the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt maxBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper, (a, b) -> Objects.compare(a, b, Comparator.reverseOrder())).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the smallest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt maxBy(IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxBy(mapper);
    }
    
    /**
     * Return the value whose mapped value is the smallest using the comparator.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt minBy(IntFunction<D> mapper, Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper, (Comparator) comparator).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the smallest using the comparator.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt minBy(IntAggregation<D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper, (Comparator) comparator).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the biggest using the comparator.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt maxBy(IntFunction<D> mapper, Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper, (Comparator) comparator.reversed()).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the biggest using the comparator.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt maxBy(IntAggregation<D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.sortedBy(mapper, (Comparator) comparator.reversed()).findFirst();
    }
    
    /**
     * Return the value whose mapped value is the smallest mapped int value.
     */
    public default <D> OptionalInt minOf(IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        val result = streamPlus.mapToObj(i -> IntIntTuple.of(i, mapper.applyAsInt(i))).min((a, b) -> Integer.compare(a._2, b._2)).map(t -> t._1);
        return result.isPresent() ? OptionalInt.of((int) result.get()) : OptionalInt.empty();
    }
    
    /**
     * Return the value whose mapped value is the smallest mapped double value.
     */
    public default <D> OptionalInt minOf(IntAggregationToInt aggregation) {
        val mapper = aggregation.newAggregator();
        return minOf(mapper);
    }
    
    /**
     * Return the value whose mapped value is the largest mapped int value.
     */
    public default <D> OptionalInt maxOf(IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        Optional<Object> result = streamPlus.mapToObj(i -> IntIntTuple.of(i, mapper.applyAsInt(i))).max((a, b) -> Integer.compare(a._2, b._2)).map(t -> t._1);
        return result.isPresent() ? OptionalInt.of((int) result.get()) : OptionalInt.empty();
    }
    
    /**
     * Return the value whose mapped value is the smallest mapped double value.
     */
    public default <D> OptionalInt maxOf(IntAggregationToInt aggregation) {
        val mapper = aggregation.newAggregator();
        return maxOf(mapper);
    }
    
    /**
     * Return the value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default Optional<IntIntTuple> minMax() {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sorted().forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(IntIntTuple.of((int) min, (int) max));
    }
    
    /**
     * Return the value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default Optional<IntIntTuple> minMax(IntComparator comparator) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sorted(comparator).forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(IntIntTuple.of((int) min, (int) max));
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sortedBy(mapper).forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(IntIntTuple.of((int) min, (int) max));
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxBy(IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper);
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxBy(IntAggregation<D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper, comparator);
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxOf(IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        IntStreamPlus.from(streamPlus).mapToObj(i -> intTuple(i, mapper.applyAsInt(i))).sortedBy(t -> t._2).forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(IntIntTuple.of(((IntIntTuple) min)._1, ((IntIntTuple) max)._1));
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<IntIntTuple> minMaxBy(IntFunction<D> mapper, Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sortedBy(mapper, (i1, i2) -> comparator.compare(i1, i2)).forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(IntIntTuple.of((int) min, (int) max));
    }
    
    public default OptionalInt minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default OptionalInt maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntFunction<D> mapper) {
        return minIndexBy(__ -> true, mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(__ -> true, mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntFunction<D> mapper) {
        return maxIndexBy(__ -> true, mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxIndexBy(__ -> true, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntPredicate filter, IntFunction<D> mapper) {
        val min = intStreamPlus().mapWithIndex().filter(t -> filter.test(t._2)).minBy(t -> mapper.apply(t._2)).map(t -> t._1);
        return min.isPresent() ? OptionalInt.of(min.get().intValue()) : OptionalInt.empty();
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntAggregationToBoolean aggregateFilter, IntFunction<D> mapper) {
        val filter = aggregateFilter.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntPredicate filter, IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntAggregationToBoolean aggregateFilter, IntAggregation<D> aggregation) {
        val filter = aggregateFilter.newAggregator();
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntPredicate filter, IntFunction<D> mapper) {
        val max = intStreamPlus().mapWithIndex().filter(t -> filter.test(t._2)).maxBy(t -> mapper.apply(t._2)).map(t -> t._1);
        return max.isPresent() ? OptionalInt.of(max.get().intValue()) : OptionalInt.empty();
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntAggregationToBoolean aggregateFilter, IntFunction<D> mapper) {
        val filter = aggregateFilter.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntPredicate filter, IntAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntAggregationToBoolean aggregateFilter, IntAggregation<D> aggregation) {
        val filter = aggregateFilter.newAggregator();
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    public default OptionalInt minIndexOf(IntPredicate filter, IntUnaryOperator mapper) {
        return intStreamPlus().mapWithIndex().map(t -> t.map2ToInt(mapper)).filter(t -> filter.test(t._2)).minBy(t -> mapper.applyAsInt(t._2)).map(t -> OptionalInt.of(t._1)).orElse(OptionalInt.empty());
    }
    
    public default OptionalInt maxIndexOf(IntPredicate filter, IntUnaryOperator mapper) {
        return intStreamPlus().mapWithIndex().map(t -> t.map2ToInt(mapper)).filter(t -> filter.test(t._2)).maxBy(t -> mapper.applyAsInt(t._2)).map(t -> OptionalInt.of(t._1)).orElse(OptionalInt.empty());
    }
}
