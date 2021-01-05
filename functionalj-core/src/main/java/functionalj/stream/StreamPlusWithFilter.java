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
package functionalj.stream;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import functionalj.function.IntObjBiFunction;
import lombok.val;


public interface StreamPlusWithFilter<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Map each value to an int and used it to filter the value. */
    public default StreamPlus<DATA> filterAsInt(
            ToIntFunction<? super DATA> mapper, 
            IntPredicate                predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsInt(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a long and used it to filter the value. */
    public default StreamPlus<DATA> filterAsLong(
            ToLongFunction<? super DATA> mapper, 
            LongPredicate                predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsLong(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to a double and used it to filter the value. */
    public default StreamPlus<DATA> filterAsDouble(
            ToDoubleFunction<? super DATA> mapper, 
            DoublePredicate                predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.applyAsDouble(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> StreamPlus<DATA> filterAsObject(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(mapper, predicate);
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> StreamPlus<DATA> filter(
            Function<? super DATA, T> mapper,
            Predicate<? super T>      predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(value -> {
                    val target = mapper.apply(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Filter the element that is only the specific class. */
    public default <T> StreamPlus<DATA> filter(Class<T> clzz) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(clzz::isInstance);
    }
    
    /**
     * Case the value to the given class and used it to filter the value.
     * If the value is not of the type (null included), it will be filtered out.
     */
    public default <T> StreamPlus<DATA> filter(
            Class<T>             clzz,
            Predicate<? super T> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(value -> {
                    if (!clzz.isInstance(value))
                        return false;
                    
                    val target = clzz.cast(value);
                    val isPass = predicate.test(target);
                    return isPass;
                });
    }
    
    /** Filter value with its index. */
    public default StreamPlus<DATA> filterWithIndex(IntObjBiFunction<? super DATA, Boolean> predicate) {
        val index = new AtomicInteger();
        val streamPlus = streamPlus();
        return streamPlus
                .filter(each -> {
                    return (predicate != null) 
                            && predicate.apply(index.getAndIncrement(), each);
                });
    }
    
    /** Filter value that is not null. */
    public default StreamPlus<DATA> filterNonNull() {
        return excludeNull();
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> StreamPlus<DATA> filterNonNull(Function<? super DATA, T> mapper) {
        return excludeNull(mapper);
    }
    
    /** Filter value that is not null. */
    public default StreamPlus<DATA> excludeNull() {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(Objects::nonNull);
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> StreamPlus<DATA> excludeNull(Function<? super DATA, T> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.filter(value -> {
            val mapped    = mapper.apply(value);
            val isNotNull = Objects.nonNull(mapped);
            return isNotNull;
        });
    }
    
    /** Filter only the value that is in the given items. */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> filterIn(DATA ... items) {
        return filterIn(asList((DATA[])items));
    }
    
    /** Filter only the value that is in the given collections. */
    public default StreamPlus<DATA> filterIn(Collection<? super DATA> collection) {
        if (collection == null)
            return StreamPlus.empty();
        
        if (collection.isEmpty())
            return StreamPlus.empty();
        
        val streamPlus = streamPlus();
        return streamPlus.filter(data -> collection.contains(data));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default StreamPlus<DATA> exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return StreamPlus.empty();
        
        val streamPlus = streamPlus();
        return streamPlus
                .filter(data -> !predicate.test(data));
    }
    
    /** Filter out any value that is in the given items. */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> excludeIn(DATA ... items) {
        val streamPlus = streamPlus();
        return streamPlus
                .excludeIn(asList((DATA[])items));
    }
    
    /** Filter out any value that is in the given collection. */
    public default StreamPlus<DATA> excludeIn(Collection<? super DATA> collection) {
        if (collection == null)
            return StreamPlus.empty();
        
        val streamPlus = streamPlus();
        if (collection.isEmpty())
            return streamPlus;
        
        return streamPlus
                .filter(data -> !collection.contains(data));
    }
    
}
