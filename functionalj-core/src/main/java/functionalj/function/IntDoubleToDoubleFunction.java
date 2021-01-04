// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import functionalj.functions.ThrowFuncs;

@FunctionalInterface
public interface IntDoubleToDoubleFunction extends Func2<Integer, Double, Double> {
    
    public double applyIntAndDoubleUnsafe(int input1, double input2) throws Exception;
    
    public default double applyIntAndDouble(int input1, double input2) {
        try {
            return applyIntAndDoubleUnsafe(input1, input2);
        } catch(Exception exception) {
            throw ThrowFuncs.exceptionTransformer.get().apply(exception);
        }
    }
    
    @Override
    public default Double applyUnsafe(Integer input1, Double input2) throws Exception {
        return applyIntAndDoubleUnsafe(input1, input2);
    }
}
