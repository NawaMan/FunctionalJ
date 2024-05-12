// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.function.aggregator.LongAggregation;
import functionalj.function.aggregator.LongAggregationToBoolean;
import functionalj.function.aggregator.LongAggregationToLong;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

class AsLongStreamPlusHelper {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public static LongStreamPlus streamFrom(AsLongStreamPlus streamPlus) {
        return streamPlus.longStreamPlus();
    }
}

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsLongStreamPlus extends AsLongStreamPlusWithCalculate, AsLongStreamPlusWithConversion, AsLongStreamPlusWithCollect, AsLongStreamPlusWithForEach, AsLongStreamPlusWithGroupingBy, AsLongStreamPlusWithReduce, AsLongStreamPlusWithStatistic {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public LongStreamPlus longStreamPlus();
    
    /**
     * @return  return the stream underneath the stream plus.
     */
    public default LongStream longStream() {
        return longStreamPlus();
    }
    
    /**
     * Iterate all element through the action
     */
    @Eager
    @Terminal
    public default void forEach(LongConsumer action) {
        longStreamPlus().forEach(action);
    }
    
    // == Match ==
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(predicate).findFirst();
    }
    
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(LongAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::test);
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalLong findAny(LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(predicate).findAny();
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalLong findAny(LongAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::apply);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(LongUnaryOperator mapper, LongPredicate theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(LongAggregationToLong aggregation, LongPredicate theCondition) {
        val mapper = aggregation.newAggregator();
        return findFirst(mapper, theCondition);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findFirstBy(LongFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findFirstBy(LongFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findFirstBy(LongAggregation<? extends T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findFirstBy(LongAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject((LongFunction<T>) mapper, (Predicate<T>) theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalLong findAny(LongUnaryOperator mapper, LongPredicate theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalLong findAnyBy(LongFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findAnyBy(LongAggregation<T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject((LongFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findAnyBy(LongFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject((LongFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalLong findAnyBy(LongAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = longStreamPlus();
        return streamPlus.filterAsObject((LongFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    // == Contains ==
    /**
     * Check if the list contains all the given values
     */
    public default boolean containsAllOf(long... values) {
        val set = new HashSet<Long>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = longStreamPlus();
        return streamPlus.peek(set::remove).anyMatch(__ -> set.isEmpty());
    }
    
    @SuppressWarnings("resource")
    public default boolean containsAnyOf(long... values) {
        return longStreamPlus().anyMatch(each -> LongStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsNoneOf(long... values) {
        return longStreamPlus().noneMatch(each -> LongStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
}
