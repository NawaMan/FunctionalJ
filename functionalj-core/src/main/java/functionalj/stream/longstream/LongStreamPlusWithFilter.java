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

import static java.util.Arrays.binarySearch;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import functionalj.function.IntLongPredicatePrimitive;
import functionalj.list.longlist.LongFuncList;
import lombok.val;

public interface LongStreamPlusWithFilter {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * Map each value to an int and used it to filter the value.
     */
    public default LongStreamPlus filterAsInt(LongToIntFunction mapper, IntPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val target = mapper.applyAsInt(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    /**
     * Map each value to a long and used it to filter the value.
     */
    public default LongStreamPlus filterAsLong(LongUnaryOperator mapper, LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val target = mapper.applyAsLong(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    /**
     * Map each value to a double and used it to filter the value.
     */
    public default LongStreamPlus filterAsDouble(LongToDoubleFunction mapper, DoublePredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val target = mapper.applyAsDouble(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> LongStreamPlus filterAsObject(LongFunction<T> mapper, Predicate<? super T> predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val target = mapper.apply(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> LongStreamPlus filter(LongUnaryOperator mapper, LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val target = mapper.applyAsLong(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    /**
     * Filter value with its index.
     */
    public default LongStreamPlus filterWithIndex(IntLongPredicatePrimitive predicate) {
        val index = new AtomicInteger();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(each -> {
            val i = index.getAndIncrement();
            return predicate.testIntLong(i, each);
        });
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> LongStreamPlus filterNonNull(LongFunction<T> mapper) {
        return excludeNull(mapper);
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> LongStreamPlus excludeNull(LongFunction<T> mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(value -> {
            val mapped = mapper.apply(value);
            val isNotNull = Objects.nonNull(mapped);
            return isNotNull;
        });
    }
    
    /**
     * Filter only the value that is in the given items.
     */
    public default LongStreamPlus filterIn(long... items) {
        if ((items == null) || (items.length == 0))
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(i -> binarySearch(items, i) >= 0);
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default LongStreamPlus filterIn(LongFuncList collection) {
        if (collection == null)
            return LongStreamPlus.empty();
        if (collection.isEmpty())
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(data -> collection.contains(data));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default LongStreamPlus filterIn(Collection<Long> collection) {
        if (collection == null)
            return LongStreamPlus.empty();
        if (collection.isEmpty())
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(data -> collection.contains(data));
    }
    
    /**
     * Filter only the value that the predicate returns false.
     */
    public default LongStreamPlus exclude(LongPredicate predicate) {
        if (predicate == null)
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(data -> !predicate.test(data));
    }
    
    /**
     * Filter out any value that is in the given items.
     */
    public default LongStreamPlus excludeIn(long... items) {
        val streamPlus = longStreamPlus();
        return streamPlus.filter(i -> binarySearch(items, i) < 0);
    }
    
    /**
     * Filter out any value that is in the given collection.
     */
    public default LongStreamPlus excludeIn(LongFuncList collection) {
        if (collection == null)
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        if (collection.isEmpty())
            return streamPlus;
        return streamPlus.filter(data -> !collection.contains(data));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default LongStreamPlus excludeIn(Collection<Long> collection) {
        if (collection == null)
            return LongStreamPlus.empty();
        if (collection.isEmpty())
            return LongStreamPlus.empty();
        val streamPlus = longStreamPlus();
        return streamPlus.filter(data -> !collection.contains(data));
    }
}
