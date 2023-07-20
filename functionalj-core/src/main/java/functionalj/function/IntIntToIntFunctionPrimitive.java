// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.IntBinaryOperator;

@FunctionalInterface
public interface IntIntToIntFunctionPrimitive extends IntBinaryOperator, ObjIntToIntFunctionPrimitive<Integer>, IntComparator {
    
    public static IntIntToIntFunctionPrimitive of(IntIntToIntFunctionPrimitive function) {
        return function;
    }
    
    public static IntIntToIntFunctionPrimitive from(IntBinaryOperator function) {
        return (function instanceof IntIntToIntFunctionPrimitive) ? (IntIntToIntFunctionPrimitive) function : ((d1, d2) -> function.applyAsInt(d1, d2));
    }
    
    public static IntIntToIntFunctionPrimitive from(ObjIntToIntFunctionPrimitive<Integer> function) {
        return (function instanceof IntIntToIntFunctionPrimitive) ? (IntIntToIntFunctionPrimitive) function : ((d1, d2) -> function.applyObjInt(d1, d2));
    }
    
    // -- functionality --
    public int applyIntInt(int data, int intValue);
    
    public default int applyAsInt(int data, int intValue) {
        return applyIntInt(data, intValue);
    }
    
    @Override
    public default int applyObjInt(Integer data, int intValue) {
        return applyIntInt(data, intValue);
    }
    
    public default int compareInt(int o1, int o2) {
        return applyIntInt(o1, o2);
    }
}
