// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.function;

import lombok.val;

@FunctionalInterface
public interface ToLongBiLongFunctionPrimitive extends ToLongBiLongFunction<Long> {
    
    public int applyAsLongAndLong(long data, long longValue);
    
    public default long applyAsLong(Long data, long longValue) {
        return applyAsLongAndLong(data, longValue);
    }
    
    
    public static long apply(ToLongBiLongFunction<Long> function, long value, long anotherValue) {
        var resValue 
            = (function instanceof ToLongBiLongFunctionPrimitive)
            ? ((ToLongBiLongFunctionPrimitive)function).applyAsLongAndLong(value, anotherValue)
            : function.applyAsLong(value, anotherValue);
        return resValue;
    }
}