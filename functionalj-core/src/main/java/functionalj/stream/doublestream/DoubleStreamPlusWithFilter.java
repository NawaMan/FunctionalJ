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

import static java.util.Arrays.binarySearch;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import functionalj.function.IntDoublePredicate;
import functionalj.function.aggregator.DoubleAggregationToBoolean;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


public interface DoubleStreamPlusWithFilter {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** Map each value to an double and used it to filter the value. */
    public default DoubleStreamPlus filterAsInt(
            DoubleToIntFunction mapper,
            IntPredicate        predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsInt(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a long and used it to filter the value. */
    public default DoubleStreamPlus filterAsLong(
            DoubleToLongFunction mapper,
            LongPredicate        predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsLong(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a double and used it to filter the value. */
    public default DoubleStreamPlus filterAsDouble(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsDouble(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> DoubleStreamPlus filterAsObject(
            DoubleFunction<T>    mapper,
            Predicate<? super T> predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.apply(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> DoubleStreamPlus filter(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsDouble(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Filter value with its index. */
    public default DoubleStreamPlus filterWithIndex(
            IntDoublePredicate predicate) {
        val index = new AtomicInteger();
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(each -> {
                    val i = index.getAndIncrement();
                    return predicate.testIntegerDouble(i, each);
                });
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> DoubleStreamPlus filterNonNull(DoubleFunction<T> mapper) {
        return excludeNull(mapper);
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> DoubleStreamPlus excludeNull(DoubleFunction<T> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(value -> {
                    val mapped    = mapper.apply(value);
                    val isNotNull = Objects.nonNull(mapped);
                    return isNotNull;
                });
    }
    
    /** Filter only the value that is in the given items. */
    public default DoubleStreamPlus filterIn(double ... items) {
        if ((items == null) || (items.length == 0))
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(i -> binarySearch(items, i) >= 0);
    }
    
    /** Filter only the value that is in the given collections. */
    public default DoubleStreamPlus filterIn(DoubleFuncList collection) {
        if (collection == null)
            return DoubleStreamPlus.empty();
        
        if (collection.isEmpty())
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(data -> collection.contains(data));
    }
    
    /** Filter only the value that is in the given collections. */
    public default DoubleStreamPlus filterIn(Collection<Double> collection) {
        if (collection == null)
            return DoubleStreamPlus.empty();
        
        if (collection.isEmpty())
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        return streamPlus.filter(data -> collection.contains(data));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default DoubleStreamPlus exclude(DoublePredicate predicate) {
        if (predicate == null)
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(data -> !predicate.test(data));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default DoubleStreamPlus exclude(DoubleAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return exclude(predicate);
    }
    
    /** Filter out any value that is in the given items. */
    public default DoubleStreamPlus excludeIn(double ... items) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(i -> binarySearch(items, i) < 0);
    }
    
    /** Filter out any value that is in the given collection. */
    public default DoubleStreamPlus excludeIn(DoubleFuncList collection) {
        if (collection == null)
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        if (collection.isEmpty())
            return streamPlus;
        
        return streamPlus
                .filter(data -> !collection.contains(data));
    }
    
    /** Filter only the value that is in the given collections. */
    public default DoubleStreamPlus excludeIn(Collection<Double> collection) {
        if (collection == null)
            return DoubleStreamPlus.empty();
        
        if (collection.isEmpty())
            return DoubleStreamPlus.empty();
        
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .filter(data -> !collection.contains(data));
    }
    
}
