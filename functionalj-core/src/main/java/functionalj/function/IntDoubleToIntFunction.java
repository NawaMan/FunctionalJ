// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.function.ToIntBiFunction;

import functionalj.exception.Throwables;

@FunctionalInterface
public interface IntDoubleToIntFunction extends ToIntBiFunction<Integer, Double>, Func2<Integer, Double, Integer> {
    
    public int applyIntAndDoubleUnsafe(int intValue, double doubleValue) throws Exception;
    
    public default int applyIntAndDouble(int intValue, double doubleValue) {
        try {
            return applyIntAndDoubleUnsafe(intValue, doubleValue);
        } catch (Exception exception) {
            throw Throwables.exceptionTransformer.get().apply(exception);
        }
    }
    
    @Override
    public default int applyAsInt(Integer intValue, Double doubleValue) {
        return applyIntAndDouble(intValue, doubleValue);
    }
    
    @Override
    public default Integer applyUnsafe(Integer intValue, Double doubleValue) throws Exception {
        return applyIntAndDoubleUnsafe(intValue, doubleValue);
    }
    
    public static double apply(ToIntBiFunction<Integer, Double> function, int intValue, double doubleValue) {
        return (function instanceof DoubleDoubleToIntFunctionPrimitive) ? ((DoubleDoubleToIntFunctionPrimitive) function).applyAsDoubleAndDouble(doubleValue, intValue) : function.applyAsInt(intValue, doubleValue);
    }
    
    public static double apply(BiFunction<Integer, Double, Integer> function, int intValue, double doubleValue) {
        return (function instanceof DoubleDoubleToIntFunctionPrimitive) ? ((DoubleDoubleToIntFunctionPrimitive) function).applyAsDoubleAndDouble(doubleValue, intValue) : function.apply(intValue, doubleValue);
    }
}
