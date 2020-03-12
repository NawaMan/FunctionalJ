// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

import lombok.val;

@FunctionalInterface
public interface IntBiFunctionPrimitive extends ToIntBiIntFunction<Integer> {
    
    public static IntBiFunctionPrimitive of(BiFunction<Integer, Integer, Integer> function) {
        if (function instanceof IntBiFunctionPrimitive)
            return (IntBiFunctionPrimitive)function;
        
        return (i, j) -> function.apply(i, j);
    }
    public static IntBiFunctionPrimitive intFunction(BiFunction<Integer, Integer, Integer> function) {
        if (function instanceof IntBiFunctionPrimitive)
            return (IntBiFunctionPrimitive)function;
        
        return (i, j) -> function.apply(i, j);
    }
    public static IntBiFunctionPrimitive intFunction(IntBinaryOperator function) {
        if (function instanceof IntBiFunctionPrimitive)
            return (IntBiFunctionPrimitive)function;
        
        return (i, j) -> function.applyAsInt(i, j);
    }
    
    
    public int applyAsIntAndInt(int data, int intValue);
    
    public default int applyAsInt(Integer data, int intValue) {
        return applyAsIntAndInt(data, intValue);
    }
    
    
    public static int apply(ToIntBiIntFunction<Integer> function, int value, int anotherValue) {
        if (function instanceof IntBiFunctionPrimitive)
            return ((IntBiFunctionPrimitive)function).applyAsIntAndInt(value, anotherValue);
        
        val resValue = function.applyAsInt(value, anotherValue);
        return resValue;
    }
}