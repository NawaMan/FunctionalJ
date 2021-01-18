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


@FunctionalInterface
public interface IntIntToDoubleFunctionPrimitive extends ToDoubleBiIntFunction<Integer> {
    
    public int applyAsIntAndInt(int data, int doubleValue);
    
    public default double applyAsDouble(Integer data, int doubleValue) {
        return applyAsIntAndInt(data, doubleValue);
    }
    
    public static double apply(ToDoubleBiIntFunction<Integer> function, int value, int anotherValue) {
        return (function instanceof IntIntToDoubleFunctionPrimitive)
                ? ((IntIntToDoubleFunctionPrimitive)function).applyAsIntAndInt(value, anotherValue)
                : function.applyAsDouble(value, anotherValue);
    }
}