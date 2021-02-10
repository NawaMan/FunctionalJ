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
import java.util.function.ToIntBiFunction;


@FunctionalInterface
public interface ObjectObjectToIntegerFunction<INPUT1, INPUT2> extends ToIntBiFunction<INPUT1, INPUT2>, Func2<INPUT1, INPUT2, Integer> {
    
    public static <I1, I2> ObjectObjectToIntegerFunction<I1, I2> of(ToIntBiFunction<I1, I2> function) {
        return (function instanceof ObjectObjectToIntegerFunction)
                ? (ObjectObjectToIntegerFunction<I1, I2>)function
                : ((i1, i2) -> function.applyAsInt(i1, i2));
    }
    
    public static <I1, I2> ObjectObjectToIntegerFunction<I1, I2> of(BiFunction<I1, I2, Integer> function) {
        return (function instanceof ObjectObjectToIntegerFunction)
                ? (ObjectObjectToIntegerFunction<I1, I2>)function
                : ((i1, i2) -> function.apply(i1, i2));
    }
    
    
    public int applyAsInt(INPUT1 input1, INPUT2 input2);
    
    public default Integer applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception {
        return applyAsInt(input1, input2);
    }
    
}
