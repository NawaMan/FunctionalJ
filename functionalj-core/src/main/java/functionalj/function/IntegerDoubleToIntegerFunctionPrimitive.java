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

import java.util.function.ToIntBiFunction;


@FunctionalInterface
public interface IntegerDoubleToIntegerFunctionPrimitive 
            extends 
                ToIntBiFunction<Integer, Double>, 
                ObjectDoubleToIntegerFunctionPrimitive<Integer> {
    
    public static IntegerDoubleToIntegerFunctionPrimitive of(IntegerDoubleToIntegerFunctionPrimitive function) {
        return function;
        
    }
    public static IntegerDoubleToIntegerFunctionPrimitive from(ToIntBiFunction<Integer, Double> function) {
        return (function instanceof IntegerDoubleToIntegerFunctionPrimitive)
                ? (IntegerDoubleToIntegerFunctionPrimitive)function
                : ((d1, d2) -> function.applyAsInt(d1, d2));
    }
    public static IntegerDoubleToIntegerFunctionPrimitive from(ObjectDoubleToIntegerFunctionPrimitive<Integer> function) {
        return (function instanceof IntegerDoubleToIntegerFunctionPrimitive)
                ? (IntegerDoubleToIntegerFunctionPrimitive)function
                : ((d1, d2) -> function.applyObjectDouble(d1, d2));
    }
    
    //-- functionality --
    
    public int applyIntegerDouble(int intValue, double doubleValue);
    
    
    public default int applyAsInt(Integer intValue, Double doubleValue) {
        return applyIntegerDouble(intValue, doubleValue);
    }
    
    @Override
    public default int applyObjectDouble(Integer data, double doubleValue) {
        return applyIntegerDouble(data, doubleValue);
    }
    
}
