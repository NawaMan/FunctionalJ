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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveToLong;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public interface LongFuncListWithFlatMap extends AsLongFuncList {
    
    /**
     * FlatMap with the given mapper for only the value that pass the condition.
     */
    public default LongFuncList flatMapOnly(LongPredicate checker, LongFunction<? extends LongFuncList> mapper) {
        return deriveToLong(this, stream -> {
            LongFunction<? extends LongStream> newMapper = value -> mapper.apply(value).longStream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    
    /**
     * FlatMap with the mapper if the condition is true, otherwise use another elseMapper.
     */
    public default LongFuncList flatMapIf(LongPredicate checker, LongFunction<? extends LongFuncList> trueMapper, LongFunction<? extends LongFuncList> falseMapper) {
        LongFunction<? extends LongStream> newTrueMapper = value -> trueMapper.apply(value).longStream();
        LongFunction<? extends LongStream> newFalseMapper = value -> falseMapper.apply(value).longStream();
        return deriveToLong(this, stream -> stream.flatMapIf(checker, newTrueMapper, newFalseMapper));
    }
}
