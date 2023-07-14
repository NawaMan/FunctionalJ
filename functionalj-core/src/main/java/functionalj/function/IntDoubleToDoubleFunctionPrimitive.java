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

import java.util.function.ToDoubleBiFunction;

@FunctionalInterface
public interface IntDoubleToDoubleFunctionPrimitive extends ToDoubleBiFunction<Integer, Double>, ObjectDoubleToDoubleFunctionPrimitive<Integer> {

    public static IntDoubleToDoubleFunctionPrimitive of(IntDoubleToDoubleFunctionPrimitive function) {
        return function;
    }

    public static IntDoubleToDoubleFunctionPrimitive from(ToDoubleBiFunction<Integer, Double> function) {
        return (function instanceof IntDoubleToDoubleFunctionPrimitive) ? (IntDoubleToDoubleFunctionPrimitive) function : ((d1, d2) -> function.applyAsDouble(d1, d2));
    }

    public static IntDoubleToDoubleFunctionPrimitive from(ObjectDoubleToDoubleFunctionPrimitive<Integer> function) {
        return (function instanceof IntDoubleToDoubleFunctionPrimitive) ? (IntDoubleToDoubleFunctionPrimitive) function : ((d1, d2) -> function.applyObjectDouble(d1, d2));
    }

    // -- functionality --
    public double applyIntDouble(int intValue, double doubleValue);

    public default double applyAsDouble(Integer intValue, Double doubleValue) {
        return applyIntDouble(intValue, doubleValue);
    }

    @Override
    public default double applyObjectDouble(Integer data, double doubleValue) {
        return applyIntDouble(data, doubleValue);
    }
}
