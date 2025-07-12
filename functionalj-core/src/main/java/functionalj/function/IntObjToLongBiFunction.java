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

import functionalj.exception.Throwables;

public interface IntObjToLongBiFunction<DATA> extends Func2<Integer, DATA, Long> {
    
    public long applyAsLongUnsafe(int input1, DATA input2) throws Exception;
    
    public default long applyAsLong(int input1, DATA input2) {
        try {
            return applyAsLongUnsafe(input1, input2);
        } catch (Exception exception) {
            throw Throwables.exceptionTransformer.get().apply(exception);
        }
    }
    
    public default Long applyUnsafe(Integer input1, DATA input2) throws Exception {
        return applyAsLongUnsafe(input1, input2);
    }
    
    @SuppressWarnings("unchecked")
    public static <D> long apply(IntObjBiFunction<D, Long> function, int input1, D input2) {
        if (function instanceof IntObjToLongBiFunction) {
            return ((IntObjToLongBiFunction<D>) function).applyAsLong(input1, input2);
        } else {
            return function.applyAsInt(input1, input2);
        }
    }
}
