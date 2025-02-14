// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.function.Named;
import functionalj.lens.core.LensSpec;
import lombok.val;

@FunctionalInterface
public interface LongLens<HOST> extends LongAccess<HOST>, ComparableLens<HOST, Long> {
    
    public static class Impl<H> extends ComparableLens.Impl<H, Long> implements Named, LongLens<H> {
        
        public Impl(String name, LensSpec<H, Long> spec) {
            super(name, spec);
        }
    }
    
    public static <HOST> LongLens<HOST> of(String name, LensSpec<HOST, Long> spec) {
        return new Impl<>(name, spec);
    }
    
    public static <HOST> LongLens<HOST> of(LensSpec<HOST, Long> spec) {
        return of(null, spec);
    }
    
    @Override
    default Long apply(HOST host) {
        LensSpec<HOST, Long> lensSpec = lensSpec();
        return lensSpec.getRead().apply(host);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public default long applyAsLong(HOST host) {
        LensSpec<HOST, Long> lensSpec = lensSpec();
        if (lensSpec instanceof PrimitiveLensSpecs.LongLensSpecPrimitive) {
            val spec = (PrimitiveLensSpecs.LongLensSpecPrimitive) lensSpec;
            val value = spec.applyAsLong(host);
            return value;
        }
        val value = lensSpec.apply(host);
        return value;
    }
    
    @Override
    public default Long applyUnsafe(HOST host) throws Exception {
        LensSpec<HOST, Long> lensSpec = lensSpec();
        return lensSpec.apply(host);
    }
}
