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

import java.util.function.DoubleBinaryOperator;

@FunctionalInterface
public interface DoubleDoubleToDoubleFunctionPrimitive extends DoubleBinaryOperator, ObjectDoubleToDoubleFunctionPrimitive<Double> {
    
    public static DoubleDoublePredicatePrimitive of(DoubleDoublePredicatePrimitive function) {
        return function;
    }
    
    public static DoubleDoubleToDoubleFunctionPrimitive from(DoubleBinaryOperator function) {
        return (function instanceof DoubleDoubleToDoubleFunctionPrimitive) ? (DoubleDoubleToDoubleFunctionPrimitive) function : ((d1, d2) -> function.applyAsDouble(d1, d2));
    }
    
    public static DoubleDoubleToDoubleFunctionPrimitive from(ObjectDoubleToDoubleFunctionPrimitive<Double> function) {
        return (function instanceof DoubleDoubleToDoubleFunctionPrimitive) ? (DoubleDoubleToDoubleFunctionPrimitive) function : ((d1, d2) -> function.applyObjectDouble(d1, d2));
    }
    
    // -- functionality --
    public double applyDoubleDouble(double data, double doubleValue);
    
    public default double applyAsDouble(double data, double doubleValue) {
        return applyDoubleDouble(data, doubleValue);
    }
    
    public default double applyAsDouble(Double data, double doubleValue) {
        return applyDoubleDouble(data, doubleValue);
    }
    
    @Override
    public default double applyObjectDouble(Double data, double doubleValue) {
        return applyDoubleDouble(data, doubleValue);
    }
}
