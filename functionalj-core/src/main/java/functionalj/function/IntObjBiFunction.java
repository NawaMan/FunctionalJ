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
import functionalj.functions.ThrowFuncs;

public interface IntObjBiFunction<DATA, TARGET> extends Func2<Integer, DATA, TARGET> {
    
    public TARGET applyAsIntUnsafe(int input1, DATA input2) throws Exception;
    
    public default TARGET applyAsInt(int input1, DATA input2) {
        try {
            return applyAsIntUnsafe(input1, input2);
        } catch (Exception exception) {
            throw ThrowFuncs.exceptionTransformer.get().apply(exception);
        }
    }
    
    public default TARGET applyUnsafe(Integer input1, DATA input2) throws Exception {
        return applyAsIntUnsafe(input1, input2);
    }
    
    public static <D, T> T apply(BiFunction<Integer, D, T> function, int input1, D input2) {
        if (function instanceof IntObjBiPredicate) {
            return ((IntObjBiFunction<D, T>) function).applyAsInt(input1, input2);
        } else {
            return function.apply(input1, input2);
        }
    }
}
