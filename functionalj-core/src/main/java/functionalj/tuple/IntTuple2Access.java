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
package functionalj.tuple;

import java.util.function.Function;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.IntegerAccess;

@FunctionalInterface
public interface IntTuple2Access<HOST, T2, T2ACCESS extends AnyAccess<HOST, T2>> extends AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> {
    
    public AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> accessParameterized();
    
    @Override
    public default IntTuple2<T2> applyUnsafe(HOST host) throws Exception {
        return accessParameterized().apply(host);
    }
    
    @Override
    public default T2ACCESS createSubAccess(Function<IntTuple2<T2>, T2> accessToParameter) {
        return accessParameterized().createSubAccess(IntTuple2::_2);
    }
    
    @Override
    public default T2ACCESS createSubAccessFromHost(Function<HOST, T2> accessToParameter) {
        return accessParameterized().createSubAccessFromHost(accessToParameter);
    }
    
    public default IntegerAccess<HOST> _1() {
        return intPrimitiveAccess(0, IntTuple2::_1);
    }
    
    public default T2ACCESS T2() {
        return accessParameterized().createSubAccess(IntTuple2::_2);
    }
}
