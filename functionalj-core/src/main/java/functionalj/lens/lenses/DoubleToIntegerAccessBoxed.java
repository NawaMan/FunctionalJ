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
package functionalj.lens.lenses;

import java.util.function.DoubleFunction;
import java.util.function.IntSupplier;

import lombok.val;


@FunctionalInterface
public interface DoubleToIntegerAccessBoxed extends IntegerAccessBoxed<Double>, DoubleFunction<Integer> {
    
    public Integer apply(double value);
    
    
    public default Integer applyUnsafe(Double host) throws Exception {
        return apply(host);
    }
    
    public default DoubleToIntegerAccessPrimitive orElse(int fallback) {
        return value -> {
            val result = apply(value);
            return (result != null) ? result : fallback;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive orGet(IntSupplier fallback) {
        return value -> {
            val result = apply(value);
            return (result != null) ? result : fallback.getAsInt();
        };
    }
    
}
