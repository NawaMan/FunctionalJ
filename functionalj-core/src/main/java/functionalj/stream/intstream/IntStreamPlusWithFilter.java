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
package functionalj.stream.intstream;

import static java.util.Arrays.binarySearch;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import functionalj.function.IntBiPredicatePrimitive;
import functionalj.list.intlist.IntFuncList;
import lombok.val;


public interface IntStreamPlusWithFilter {
    
    public IntStreamPlus intStreamPlus();
    
    /** Map each value to an int and used it to filter the value. */
    public default IntStreamPlus filterAsInt(
            IntUnaryOperator mapper,
            IntPredicate     predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsInt(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a long and used it to filter the value. */
    public default IntStreamPlus filterAsLong(
            IntToLongFunction mapper,
            LongPredicate     predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsLong(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a double and used it to filter the value. */
    public default IntStreamPlus filterAsDouble(
            IntToDoubleFunction mapper,
            DoublePredicate     predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsDouble(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> IntStreamPlus filterAsObject(
            IntFunction<T>       mapper,
            Predicate<? super T> predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.apply(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    public default <T> IntStreamPlus filterAsObject(
            Function<Integer, ? extends T> mapper,
            Predicate<? super T>           predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.apply(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> IntStreamPlus filter(
            IntUnaryOperator mapper,
            IntPredicate     predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsInt(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Filter value with its index. */
    public default IntStreamPlus filterWithIndex(
            IntBiPredicatePrimitive predicate) {
        val index = new AtomicInteger();
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(each -> {
                    val i = index.getAndIncrement();
                    return predicate.testIntInt(i, each);
                });
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> IntStreamPlus filterNonNull(IntFunction<T> mapper) {
        return excludeNull(mapper);
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> IntStreamPlus excludeNull(IntFunction<T> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(value -> {
                    val mapped    = mapper.apply(value);
                    val isNotNull = Objects.nonNull(mapped);
                    return isNotNull;
                });
    }
    
    /** Filter only the value that is in the given items. */
    public default IntStreamPlus filterIn(int ... items) {
        if ((items == null) || (items.length == 0))
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(i -> binarySearch(items, i) >= 0);
    }
    
    /** Filter only the value that is in the given collections. */
    public default IntStreamPlus filterIn(IntFuncList collection) {
        if (collection == null)
            return IntStreamPlus.empty();
        
        if (collection.isEmpty())
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(data -> collection.contains(data));
    }
    
    /** Filter only the value that is in the given collections. */
    public default IntStreamPlus filterIn(Collection<Integer> collection) {
        if (collection == null)
            return IntStreamPlus.empty();
        
        if (collection.isEmpty())
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        return streamPlus.filter(data -> collection.contains(data));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default IntStreamPlus exclude(IntPredicate predicate) {
        if (predicate == null)
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(data -> !predicate.test(data));
    }
    
    /** Filter out any value that is in the given items. */
    public default IntStreamPlus excludeIn(int ... items) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(i -> binarySearch(items, i) < 0);
    }
    
    /** Filter out any value that is in the given collection. */
    public default IntStreamPlus excludeIn(IntFuncList collection) {
        if (collection == null)
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        if (collection.isEmpty())
            return streamPlus;
        
        return streamPlus
                .filter(data -> !collection.contains(data));
    }
    
    /** Filter only the value that is in the given collections. */
    public default IntStreamPlus excludeIn(Collection<Integer> collection) {
        if (collection == null)
            return IntStreamPlus.empty();
        
        if (collection.isEmpty())
            return IntStreamPlus.empty();
        
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(data -> !collection.contains(data));
    }
    
}
