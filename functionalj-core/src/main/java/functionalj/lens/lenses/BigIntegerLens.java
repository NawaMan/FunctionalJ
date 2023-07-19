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

import java.math.BigInteger;
import functionalj.function.Named;
import functionalj.lens.core.LensSpec;

@FunctionalInterface
public interface BigIntegerLens<HOST> extends BigIntegerAccess<HOST>, ComparableLens<HOST, BigInteger> {
    
    public static class Impl<H> extends ComparableLens.Impl<H, BigInteger> implements Named, BigIntegerLens<H> {
        
        public Impl(String name, LensSpec<H, BigInteger> spec) {
            super(name, spec);
        }
    }
    
    public static <HOST> BigIntegerLens<HOST> of(String name, LensSpec<HOST, BigInteger> spec) {
        return new Impl<>(name, spec);
    }
    
    public static <HOST> BigIntegerLens<HOST> of(LensSpec<HOST, BigInteger> spec) {
        return of(null, spec);
    }
    
    @Override
    public default BigInteger apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
}
