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
package functionalj.stream.doublestream;

import static functionalj.tuple.DoubleDoubleTuple.doubleTuple;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleComparator;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.aggregator.DoubleAggregation;
import functionalj.function.aggregator.DoubleAggregationToBoolean;
import functionalj.function.aggregator.DoubleAggregationToDouble;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.DoubleTuple2;
import lombok.val;


public interface AsDoubleStreamPlusWithStatistic {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** @return  the size of the stream */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = doubleStreamPlus();
        return (int)streamPlus
                .count();
    }
    
    @Eager
    @Terminal
    public default long count() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .count();
    }
    
    @Eager
    @Terminal
    public default double sum() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sum();
    }
    
    /** @return the product of all the number */
    @Eager
    @Terminal
    public default OptionalDouble product() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .reduce((a, b) -> a*b);
    }
    
    @Eager
    @Terminal
    public default OptionalDouble average() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .average();
    }
    
    public default DoubleSummaryStatistics summaryStatistics() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .summaryStatistics();
    }
    
    @Eager
    @Terminal
    /** Using the comparator, find the minimal value */
    public default OptionalDouble min() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .min();
    }
    
    @Eager
    @Terminal
    /** Using the comparator, find the maximum value */
    public default OptionalDouble max() {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .max();
    }
    
    /** Fund the min value using the comparator */
    public default OptionalDouble min(DoubleComparator comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sorted(comparator)
                .findFirst();
    }
    
    /** Fund the max value using the comparator */
    public default OptionalDouble max(DoubleComparator comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sorted(comparator.reverse())
                .findFirst();
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble minBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper)
                .findFirst();
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble minBy(DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minBy(mapper);
    }
    
    /** Return the value whose mapped value is the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble maxBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper,(a, b) -> Objects.compare(a, b, Comparator.reverseOrder()))
                .findFirst();
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble maxBy(DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble minBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator)
                .findFirst();
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble minBy(
            DoubleAggregation<D>  aggregation,
            Comparator<? super D> comparator) {
        val mapper     = aggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator)
                .findFirst();
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble maxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator.reversed())
                .findFirst();
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble maxBy(
            DoubleAggregation<D>  aggregation,
            Comparator<? super D> comparator) {
        val mapper     = aggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator.reversed())
                .findFirst();
    }
    
    /** Return the value whose mapped value is the smallest mapped double value. */
    public default <D> OptionalDouble minOf(DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        val result
                = streamPlus
                .mapToObj(i      -> DoubleDoubleTuple.of(i, mapper.applyAsDouble(i)))
                .min     ((a, b) -> Double.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalDouble.of((Double)result.get())
                : OptionalDouble.empty();
    }
    
    /** Return the value whose mapped value is the smallest mapped double value. */
    public default <D> OptionalDouble minOf(DoubleAggregationToDouble aggregation) {
        val mapper = aggregation.newAggregator();
        return minOf(mapper);
    }
    
    /** Return the value whose mapped value is the largest mapped int value. */
    public default <D> OptionalDouble maxOf(DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        Optional<Object> result
                = streamPlus
                .mapToObj(i      -> DoubleDoubleTuple.of(i, mapper.applyAsDouble(i)))
                .max     ((a, b) -> Double.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalDouble.of((double)result.get())
                : OptionalDouble.empty();
    }
    
    /** Return the value whose mapped value is the smallest mapped double value. */
    public default <D> OptionalDouble maxOf(DoubleAggregationToDouble aggregation) {
        val mapper = aggregation.newAggregator();
        return maxOf(mapper);
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Optional<DoubleDoubleTuple> minMax() {
        val streamPlus = doubleStreamPlus();
        val minMaxRef  = new double[] { Double.NaN, Double.NaN };
        streamPlus
            .sorted()
            .forEach(each -> {
                if (Double.isNaN(minMaxRef[0])) {
                    minMaxRef[0] = each;
                }
                minMaxRef[1] = each;
            });
        val min = minMaxRef[0];
        val max = minMaxRef[1];
        return (Double.isNaN(min) || Double.isNaN(max))
                ? Optional.empty()
                : Optional.of(DoubleDoubleTuple.of(min, max));
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Optional<DoubleDoubleTuple> minMax(DoubleDoubleToIntFunctionPrimitive comparator) {
        val streamPlus = doubleStreamPlus();
        val minMaxRef  = new double[] { Double.NaN, Double.NaN };
        streamPlus
            .sorted((a, b) -> comparator.applyAsDoubleAndDouble(a, b))
            .forEach(each -> {
                if (Double.isNaN(minMaxRef[0])) {
                    minMaxRef[0] = each;
                }
                minMaxRef[1] = each;
            });
        val min = minMaxRef[0];
        val max = minMaxRef[1];
        return (Double.isNaN(min) || Double.isNaN(max))
                ? Optional.empty()
                : Optional.of(DoubleDoubleTuple.of(min, max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DoubleDoubleTuple> minMaxBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        val minMaxRef  = new double[] { Double.NaN, Double.NaN };
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            if (Double.isNaN(minMaxRef[0])) {
                minMaxRef[0] = each;
            }
            minMaxRef[1] = each;
        });
        
        val min = minMaxRef[0];
        val max = minMaxRef[1];
        return (Double.isNaN(min) || Double.isNaN(max))
                ? Optional.empty()
                : Optional.of(DoubleDoubleTuple.of(min, max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DoubleDoubleTuple> minMaxBy(DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DoubleDoubleTuple> minMaxBy(
            DoubleAggregation<D>  aggregation,
            Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper, comparator);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Optional<DoubleDoubleTuple> minMaxOf(DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        val minMaxRef  = new double[] { Double.NaN, Double.NaN };
        DoubleStreamPlus.from(streamPlus)
            .mapToObj(i    -> doubleTuple(i, mapper.applyAsDouble(i)))
            .sortedBy(t    -> t._2)
            .forEach (each -> {
                if (Double.isNaN(minMaxRef[0])) {
                    minMaxRef[0] = each._1;
                }
                minMaxRef[1] = each._1;
            });
        val min = minMaxRef[0];
        val max = minMaxRef[1];
        return (Double.isNaN(min) || Double.isNaN(max))
                ? Optional.empty()
                : Optional.of(DoubleDoubleTuple.of(min, max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default <D> Optional<DoubleDoubleTuple> minMaxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        val minMaxRef  = new double[] { Double.NaN, Double.NaN };
        DoubleStreamPlus.from(streamPlus)
        .mapToObj(i    -> DoubleTuple2.of(i, mapper.apply(i)))
        .sortedBy(t    -> t._2, comparator)
        .forEach (each -> {
            if (Double.isNaN(minMaxRef[0])) {
                minMaxRef[0] = each._1;
            }
            minMaxRef[1] = each._1;
        });
        
        val min = minMaxRef[0];
        val max = minMaxRef[1];
        return (Double.isNaN(min) || Double.isNaN(max))
                ? Optional.empty()
                : Optional.of(DoubleDoubleTuple.of(min, max));
    }
    
    public default OptionalInt minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default OptionalInt maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(DoubleFunction<D> mapper) {
        return minIndexBy(__ -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(__ -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(DoubleFunction<D> mapper) {
        return maxIndexBy(__ -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxIndexBy(__ -> true, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            DoublePredicate   filter, 
            DoubleFunction<D> mapper) {
        val min = doubleStreamPlus()
                .mapWithIndex()
                .filter  (t -> filter.test(t._2))
                .minBy   (t -> mapper.apply(t._2))
                .map   (t -> t._1);
        return min.isPresent()
                ? OptionalInt.of(min.get().intValue()) 
                : OptionalInt.empty();
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            DoubleAggregationToBoolean aggregateFilter, 
            DoubleFunction<D>          mapper) {
        val filter = aggregateFilter.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            DoublePredicate      filter, 
            DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            DoubleAggregationToBoolean aggregateFilter, 
            DoubleAggregation<D>       aggregation) {
        val filter = aggregateFilter.newAggregator();
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            DoublePredicate   filter, 
            DoubleFunction<D> mapper) {
        val max = doubleStreamPlus()
                .mapWithIndex()
                .filter  (t -> filter.test(t._2))
                .maxBy   (t -> mapper.apply(t._2))
                .map   (t -> t._1);
        return max.isPresent()
                ? OptionalInt.of(max.get().intValue()) 
                : OptionalInt.empty();
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            DoubleAggregationToBoolean aggregateFilter, 
            DoubleFunction<D>          mapper) {
        val filter = aggregateFilter.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            DoublePredicate      filter, 
            DoubleAggregation<D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            DoubleAggregationToBoolean aggregateFilter, 
            DoubleAggregation<D>       aggregation) {
        val filter = aggregateFilter.newAggregator();
        val mapper = aggregation.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexOf(
            DoublePredicate     filter,
            DoubleUnaryOperator mapper) {
        return doubleStreamPlus()
                .mapWithIndex()
                .map   (t -> t.map2ToDouble(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsDouble(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
            DoublePredicate     filter,
            DoubleUnaryOperator mapper) {
        return doubleStreamPlus()
                .mapWithIndex()
                .map   (t -> t.map2ToDouble(mapper))
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.applyAsDouble(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
}
