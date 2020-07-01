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

import functionalj.lens.core.LensSpec;
import lombok.val;

@FunctionalInterface
public interface IntegerLens<HOST> 
        extends 
            IntegerAccess<HOST>,
            ComparableLens<HOST, Integer> {
    
    
    public static <HOST> IntegerLens<HOST> of(LensSpec<HOST, Integer> spec) {
        return () -> spec;
    }
    
    @Override
    default Integer apply(HOST host) {
        LensSpec<HOST, Integer> lensSpec = lensSpec();
        return lensSpec.getRead().apply(host);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public default int applyAsInt(HOST host) {
        LensSpec<HOST, Integer> lensSpec = lensSpec();
        if (lensSpec instanceof PrimitiveLensSpecs.IntegerLensSpecPrimitive) {
            val spec  = (PrimitiveLensSpecs.IntegerLensSpecPrimitive)lensSpec;
            val value = spec.applyAsInt(host);
            return value;
        }
        
        val value = lensSpec.apply(host);
        return value;
    }

    @Override
    public default Integer applyUnsafe(HOST host) throws Exception {
        LensSpec<HOST, Integer> lensSpec = lensSpec();
        return lensSpec.apply(host);
    }
    
}
