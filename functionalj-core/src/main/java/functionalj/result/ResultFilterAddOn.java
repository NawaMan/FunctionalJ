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
package functionalj.result;

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.val;

public interface ResultFilterAddOn<DATA> {
    
    public Result<DATA> asResult();
    
    public Result<DATA> filter(Predicate<? super DATA> theCondition);
    
    public default <T extends DATA> Result<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    
    public default <T extends DATA> Result<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return filter(value -> {
            if (clzz.isInstance(value))
                return false;
            val target = clzz.cast(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> Result<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
}
