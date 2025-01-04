// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.stream.doublestream.AsDoubleStreamPlusHelper.streamFrom;
import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.function.aggregator.DoubleAggregation;
import functionalj.function.aggregator.DoubleAggregationToBoolean;
import functionalj.function.aggregator.DoubleAggregationToDouble;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

class AsDoubleStreamPlusHelper {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public static DoubleStreamPlus streamFrom(AsDoubleStreamPlus streamPlus) {
        return streamPlus.doubleStreamPlus();
    }
}

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsDoubleStreamPlus extends AsDoubleStreamPlusWithCalculate, AsDoubleStreamPlusWithCollect, AsDoubleStreamPlusWithConversion, AsDoubleStreamPlusWithForEach, AsDoubleStreamPlusWithGroupingBy, AsDoubleStreamPlusWithReduce, AsDoubleStreamPlusWithStatistic {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * @return  return the stream underneath the stream plus.
     */
    public default DoubleStream doubleStream() {
        return doubleStreamPlus();
    }
    
    /**
     * Iterate all element through the action
     */
    @Eager
    @Terminal
    public default void forEach(DoubleConsumer action) {
        streamFrom(this).forEach(action);
    }
    
    // -- Match --
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalDouble findFirst(DoublePredicate predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filter(predicate).findFirst();
    }
    
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalDouble findFirst(DoubleAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::test);
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalDouble findAny(DoublePredicate predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filter(predicate).findAny();
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalDouble findAny(DoubleAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::test);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalDouble findFirst(DoubleUnaryOperator mapper, DoublePredicate theCondition) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filter(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalDouble findFirst(DoubleAggregationToDouble aggregation, DoublePredicate theCondition) {
        val mapper = aggregation.newAggregator();
        return findFirst(mapper, theCondition);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findFirstBy(DoubleFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findFirstBy(DoubleFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findFirstBy(DoubleAggregation<? extends T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findFirstBy(DoubleAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject((DoubleFunction<T>) mapper, (Predicate<T>) theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalDouble findAny(DoubleUnaryOperator mapper, DoublePredicate theCondition) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filter(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalDouble findAnyBy(DoubleFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findAnyBy(DoubleAggregation<T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject((DoubleFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findAnyBy(DoubleFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject((DoubleFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalDouble findAnyBy(DoubleAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = doubleStreamPlus();
        return streamPlus.filterAsObject((DoubleFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    // == Contains ==
    /**
     * Check if the list contains all the given values
     */
    public default boolean containsAllOf(double... values) {
        val set = new HashSet<Double>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = doubleStreamPlus();
        return streamPlus.peek(set::remove).anyMatch(__ -> set.isEmpty());
    }
    
    public default boolean containsAnyOf(double... values) {
        return doubleStreamPlus().allMatch(each -> DoubleStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(double... values) {
        return doubleStreamPlus().noneMatch(each -> DoubleStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
}
