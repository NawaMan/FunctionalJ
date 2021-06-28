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

import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

import functionalj.stream.StreamPlus;
import lombok.val;


public interface LongStreamPlusWithMap {
    
    public LongStreamPlus longStreamPlus();
    
    
    /** Map the value using the mapper. */
    public default <T> StreamPlus<T> mapToObj(LongFunction<? extends T> mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(mapper);
    }
    
    /** Map the value using the mapper only when the condition is true. */
    public default LongStreamPlus mapOnly(
            LongPredicate     condition, 
            LongUnaryOperator mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .map(value -> {
                    val isTrue = condition.test(value);
                    val mapped = isTrue
                            ? mapper.applyAsLong(value)
                            : value;
                    return mapped;
                });
    }
    
    /** Map the value using the mapper only when the condition is true. Otherwise, map using the elseMapper. */
    public default LongStreamPlus mapIf(
            LongPredicate     condition, 
            LongUnaryOperator mapper, 
            LongUnaryOperator elseMapper) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .map(value -> {
                    val isTrue = condition.test(value);
                    val mapped = isTrue 
                            ? mapper    .applyAsLong(value) 
                            : elseMapper.applyAsLong(value);
                    return mapped;
                });
    }
    
    /** Map the value using the mapper only when the condition is true. Otherwise, map using the elseMapper. */
    public default <T> StreamPlus<T> mapToObjIf(
            LongPredicate   condition, 
            LongFunction<T> mapper, 
            LongFunction<T> elseMapper) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .mapToObj(value -> {
                    val isTrue = condition.test(value);
                    val mapped = isTrue 
                            ? mapper    .apply(value) 
                            : elseMapper.apply(value);
                    return mapped;
                });
    }
    
}
