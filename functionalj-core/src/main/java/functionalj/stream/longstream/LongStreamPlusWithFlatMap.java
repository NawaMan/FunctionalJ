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
import java.util.stream.LongStream;
import lombok.val;

public interface LongStreamPlusWithFlatMap {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * FlatMap with the given mapper for only the value that pass the condition.
     */
    public default LongStreamPlus flatMapOnly(LongPredicate condition, LongFunction<? extends LongStream> mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus.flatMap(value -> {
            val isTrue = condition.test(value);
            val mapped = isTrue ? mapper.apply(value) : LongStreamPlus.of(value);
            return mapped;
        });
    }
    
    /**
     * FlatMap with the mapper if the condition is true, otherwise use another elseMapper.
     */
    public default LongStreamPlus flatMapIf(LongPredicate condition, LongFunction<? extends LongStream> mapper, LongFunction<? extends LongStream> elseMapper) {
        val streamPlus = longStreamPlus();
        return streamPlus.flatMap(value -> {
            val isTrue = condition.test(value);
            val mapped = isTrue ? mapper.apply(value) : elseMapper.apply(value);
            return mapped;
        });
    }
}
