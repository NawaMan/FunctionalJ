// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses;

import java.util.function.ToDoubleFunction;

import functionalj.lens.core.LensSpec;


@FunctionalInterface
public interface DoubleLens<HOST>
        extends
            DoubleAccess<HOST>,
            ToDoubleFunction<HOST>,
            ComparableLens<HOST, Double> {
    
    public static <HOST> DoubleLens<HOST> of(LensSpec<HOST, Double> spec) {
        return () -> spec;
    }
    
    @Override
    default Double apply(HOST host) {
        LensSpec<HOST, Double> lensSpec = lensSpec();
        return lensSpec.getRead().apply(host);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public default double applyAsDouble(HOST host) {
        LensSpec<HOST, Double> lensSpec = lensSpec();
        if (lensSpec instanceof PrimitiveLensSpecs.DoubleLensSpecPrimitive) {
            var spec  = (PrimitiveLensSpecs.DoubleLensSpecPrimitive)lensSpec;
            var value = spec.applyAsDouble(host);
            return value;
        }
        
        var value = lensSpec.apply(host);
        return value;
    }

    @Override
    public default Double applyUnsafe(HOST host) throws Exception {
        LensSpec<HOST, Double> lensSpec = lensSpec();
        return lensSpec.apply(host);
    }
    
}
