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
package functionalj.stream.intstream;

import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.function.aggregator.IntAggregation;
import functionalj.function.aggregator.IntAggregationToBoolean;
import functionalj.function.aggregator.IntAggregationToInt;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

class AsIntStreamPlusHelper {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public static IntStreamPlus streamFrom(AsIntStreamPlus streamPlus) {
        return streamPlus.intStreamPlus();
    }
}

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsIntStreamPlus extends AsIntStreamPlusWithCalculate, AsIntStreamPlusWithConversion, AsIntStreamPlusWithCollect, AsIntStreamPlusWithForEach, AsIntStreamPlusWithGroupingBy, AsIntStreamPlusWithReduce, AsIntStreamPlusWithStatistic {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public IntStreamPlus intStreamPlus();
    
    /**
     * @return  return the stream underneath the stream plus.
     */
    public default IntStream intStream() {
        return intStreamPlus();
    }
    
    /**
     * Iterate all element through the action
     */
    @Eager
    @Terminal
    public default void forEach(IntConsumer action) {
        intStreamPlus().forEach(action);
    }
    
    // -- Match --
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus.filter(predicate).findFirst();
    }
    
    /**
     * Return the first element that matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(IntAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::test);
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalInt findAny(IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus.filter(predicate).findAny();
    }
    
    /**
     * Return the any element that matches the predicate.
     */
    @Terminal
    public default OptionalInt findAny(IntAggregationToBoolean aggregation) {
        val aggregator = aggregation.newAggregator();
        return findFirst(aggregator::apply);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(IntUnaryOperator mapper, IntPredicate theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus.filter(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(IntAggregationToInt aggregation, IntPredicate theCondition) {
        val mapper = aggregation.newAggregator();
        return findFirst(mapper, theCondition);
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findFirstBy(IntFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findFirstBy(IntFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findFirstBy(IntAggregation<? extends T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findFirst();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findFirstBy(IntAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject((IntFunction<T>) mapper, (Predicate<T>) theCondition::test).findFirst();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalInt findAny(IntUnaryOperator mapper, IntPredicate theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus.filter(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the any element that its mapped value matches the predicate.
     */
    @Terminal
    public default <T> OptionalInt findAnyBy(IntFunction<? extends T> mapper, Predicate<? super T> theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject(mapper, theCondition).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findAnyBy(IntAggregation<T> aggregation, Predicate<? super T> theCondition) {
        val mapper = aggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject((IntFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findAnyBy(IntFunction<T> mapper, AggregationToBoolean<? super T> theConditionAggregation) {
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject((IntFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    /**
     * Use the mapper, return the first element that its mapped value matches the predicate.
     */
    @Terminal
    @Sequential
    public default <T> OptionalInt findAnyBy(IntAggregation<T> aggregation, AggregationToBoolean<? super T> theConditionAggregation) {
        val mapper = aggregation.newAggregator();
        val theCondition = theConditionAggregation.newAggregator();
        val streamPlus = intStreamPlus();
        return streamPlus.filterAsObject((IntFunction<T>) mapper, (Predicate<T>) theCondition::test).findAny();
    }
    
    // == Contains ==
    /**
     * Check if the list contains all the given values
     */
    public default boolean containsAllOf(int... values) {
        val set = new HashSet<Integer>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = intStreamPlus();
        return streamPlus.peek(set::remove).anyMatch(__ -> set.isEmpty());
    }
    
    @SuppressWarnings("resource")
    public default boolean containsAnyOf(int... values) {
        return intStreamPlus().anyMatch(each -> IntStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsNoneOf(int... values) {
        return intStreamPlus().noneMatch(each -> IntStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
}
