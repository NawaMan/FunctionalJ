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

@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyLens<HOST, TYPE> {
    
    public static class Impl<H, D extends Comparable<D>> extends AnyLens.Impl<H, D> implements Named, ComparableLens<H, D> {
        
        public Impl(String name, LensSpec<H, D> spec) {
            super(name, spec);
        }
    }
    
    static <T extends Comparable<T>> ComparableLens<T, T> of(String name, LensSpec<T, T> spec) {
        return new Impl<>(name, spec);
    }
    
    static <T extends Comparable<T>> ComparableLens<T, T> of(LensSpec<T, T> spec) {
        return of(null, spec);
    }
    
    @Override
    default TYPE apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
}
