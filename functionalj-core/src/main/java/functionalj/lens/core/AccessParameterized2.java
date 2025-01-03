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
package functionalj.lens.core;

import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import lombok.val;

public interface AccessParameterized2<HOST, TYPE, PARAMETER1, PARAMETER2, PARAMETERACCESS1 extends AnyAccess<HOST, PARAMETER1>, PARAMETERACCESS2 extends AnyAccess<HOST, PARAMETER2>> extends AnyAccess<HOST, TYPE> {
    
    public TYPE applyUnsafe(HOST host) throws Exception;
    
    public PARAMETERACCESS1 createSubAccessFromHost1(Function<HOST, PARAMETER1> accessToParameter);
    
    public PARAMETERACCESS2 createSubAccessFromHost2(Function<HOST, PARAMETER2> accessToParameter);
    
    public default PARAMETERACCESS1 createSubAccess1(Function<TYPE, PARAMETER1> accessToParameter) {
        return createSubAccessFromHost1(host -> {
            val list = apply(host);
            val value = accessToParameter.apply(list);
            return value;
        });
    }
    
    public default PARAMETERACCESS2 createSubAccess2(Function<TYPE, PARAMETER2> accessToParameter) {
        return createSubAccessFromHost2(host -> {
            val list = apply(host);
            val value = accessToParameter.apply(list);
            return value;
        });
    }
}
