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
package functionalj.function;

import java.util.function.ToLongBiFunction;


@FunctionalInterface
public interface IntLongToLongFunctionPrimitive 
            extends 
                ToLongBiFunction<Integer, Long>, 
                ObjectLongToLongFunctionPrimitive<Integer> {
    
    public static IntLongToLongFunctionPrimitive of(IntLongToLongFunctionPrimitive function) {
        return function;
        
    }
    public static IntLongToLongFunctionPrimitive from(ToLongBiFunction<Integer, Long> function) {
        return (function instanceof IntLongToLongFunctionPrimitive)
                ? (IntLongToLongFunctionPrimitive)function
                : ((d1, d2) -> function.applyAsLong(d1, d2));
    }
    public static IntLongToLongFunctionPrimitive from(ObjectLongToLongFunctionPrimitive<Integer> function) {
        return (function instanceof IntLongToLongFunctionPrimitive)
                ? (IntLongToLongFunctionPrimitive)function
                : ((d1, d2) -> function.applyObjectLong(d1, d2));
    }
    
    //-- functionality --
    
    public long applyIntegerLong(int intValue, long longValue);
    
    
    public default long applyAsLong(Integer intValue, Long longValue) {
        return applyIntegerLong(intValue, longValue);
    }
    
    @Override
    public default long applyObjectLong(Integer data, long longValue) {
        return applyIntegerLong(data, longValue);
    }
    
}
