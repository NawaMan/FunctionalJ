// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.core;

import java.util.function.Function;
import functionalj.lens.lenses.AnyLens;
import lombok.val;

public interface LensSpecParameterized2<HOST, TYPE, SUB1, SUB2, SUBLENS1 extends AnyLens<HOST, SUB1>, SUBLENS2 extends AnyLens<HOST, SUB2>> extends AccessParameterized2<HOST, TYPE, SUB1, SUB2, SUBLENS1, SUBLENS2> {
    
    public LensSpec<HOST, TYPE> getSpec();
    
    public SUBLENS1 createSubLens1(String subName, LensSpec<HOST, SUB1> subSpec);
    
    public SUBLENS2 createSubLens2(String subName, LensSpec<HOST, SUB2> subSpec);
    
    @Override
    public default TYPE applyUnsafe(HOST host) throws Exception {
        return getSpec().getRead().apply(host);
    }
    
    @Override
    public default SUBLENS1 createSubAccess1(Function<TYPE, SUB1> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read);
        return createSubLens1(null, spec);
    }
    
    @Override
    public default SUBLENS2 createSubAccess2(Function<TYPE, SUB2> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read);
        return createSubLens2(null, spec);
    }
    
    @Override
    public default SUBLENS1 createSubAccessFromHost1(Function<HOST, SUB1> accessToParameter) {
        return createSubLens1(null, LensSpec.of(accessToParameter));
    }
    
    @Override
    public default SUBLENS2 createSubAccessFromHost2(Function<HOST, SUB2> accessToParameter) {
        return createSubLens2(null, LensSpec.of(accessToParameter));
    }
}
