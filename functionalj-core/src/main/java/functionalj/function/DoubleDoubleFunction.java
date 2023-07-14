// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

@FunctionalInterface
public interface DoubleDoubleFunction<TARGET> extends Func2<Double, Double, TARGET> {

    public static <T> DoubleDoubleFunction<T> of(DoubleDoubleFunction<T> function) {
        return function;
    }

    public static <T> DoubleDoubleFunction<T> from(BiFunction<Double, Double, T> function) {
        return (function instanceof DoubleDoubleFunction) ? (DoubleDoubleFunction<T>) function : ((d1, d2) -> function.apply(d1, d2));
    }

    // -- functionality --
    public TARGET applyDoubleDouble(double input1, double input2);

    public default TARGET apply(double input1, double input2) {
        return applyDoubleDouble(input1, input2);
    }

    // -- override functionality --
    @Override
    public default TARGET applyUnsafe(Double input1, Double input2) throws Exception {
        return applyDoubleDouble(input1, input2);
    }
}
