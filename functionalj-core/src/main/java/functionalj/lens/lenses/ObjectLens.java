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
package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

class ObjectLensHelper {
    
    static <HOST, DATA> HOST performChange(ObjectLens<HOST, DATA> lens, DATA data, HOST host) {
        return lens.lensSpec().getWrite().apply(host, data);
    }
}

@FunctionalInterface
public interface ObjectLens<HOST, DATA> extends AnyLens<HOST, DATA>, ObjectAccess<HOST, DATA> {
    
    public static class Impl<H, D> extends ObjectLensImpl<H, D> {
    
        public Impl(String name, LensSpec<H, D> spec) {
            super(name, spec);
        }
    }
    
    public static <H, D> ObjectLens<H, D> of(String name, LensSpec<H, D> spec) {
        return new Impl<>(name, spec);
    }
    
    public static <H, D> ObjectLens<H, D> of(LensSpec<H, D> spec) {
        return of(null, spec);
    }
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
}
