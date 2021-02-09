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

import java.util.function.BiFunction;
import java.util.function.ToDoubleBiFunction;


@FunctionalInterface
public interface IntegerDoubleToDoubleFunction extends ToDoubleBiFunction<Integer, Double>, Func2<Integer, Double, Double> {
    
    public double applyAsDoubleAndInteger(int intValue, double doubleValue);
    
    @Override
    public default double applyAsDouble(Integer intValue, Double doubleValue) {
        return applyAsDoubleAndInteger(intValue, doubleValue);
    }
    
    @Override
    public default Double apply(Integer intValue, Double doubleValue) {
        return applyAsDoubleAndInteger(intValue, doubleValue);
    }
    
    @Override
    public default Double applyUnsafe(Integer intValue, Double doubleValue) throws Exception {
        return applyAsDoubleAndInteger(intValue, doubleValue);
    }
    
    public static double apply(ToDoubleBiFunction<Double, Integer> function, Integer intValue, double doubleValue) {
        return (function instanceof DoubleDoubleToIntFunctionPrimitive)
                ? ((DoubleDoubleToIntFunctionPrimitive)function).applyAsDoubleAndDouble(doubleValue, intValue)
                : function.applyAsDouble(doubleValue, intValue);
    }
    
    
    public static double apply(BiFunction<Double, Integer, Integer> function, double doubleValue, Integer intValue) {
        return (function instanceof DoubleDoubleToIntFunctionPrimitive)
                ? ((DoubleDoubleToIntFunctionPrimitive)function).applyAsDoubleAndDouble(doubleValue, intValue)
                : function.apply(doubleValue, intValue);
    }
}
