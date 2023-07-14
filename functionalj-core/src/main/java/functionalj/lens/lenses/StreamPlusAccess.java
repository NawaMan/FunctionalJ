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

import java.util.List;
import java.util.function.Function;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.AccessUtils;
import functionalj.list.FuncList;
import functionalj.stream.StreamPlus;
import lombok.val;

@FunctionalInterface
public interface StreamPlusAccess<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> extends ObjectAccess<HOST, StreamPlus<TYPE>>, AccessParameterized<HOST, StreamPlus<TYPE>, TYPE, AnyAccess<HOST, TYPE>> {

    public static <H, T, A extends AnyAccess<H, T>> StreamPlusAccess<H, T, A> of(Function<H, StreamPlus<T>> read, Function<Function<H, T>, A> createAccess) {
        val accessParameterized = new AccessParameterized<H, StreamPlus<T>, T, A>() {

            @Override
            public StreamPlus<T> applyUnsafe(H host) throws Exception {
                return read.apply(host);
            }

            @Override
            public A createSubAccessFromHost(Function<H, T> accessToParameter) {
                return createAccess.apply(accessToParameter);
            }
        };
        return AccessUtils.createSubStreamPlusAccess(accessParameterized, read);
    }

    public AccessParameterized<HOST, StreamPlus<TYPE>, TYPE, TYPEACCESS> accessParameterized();

    @Override
    public default StreamPlus<TYPE> applyUnsafe(HOST host) throws Exception {
        return accessParameterized().applyUnsafe(host);
    }

    @Override
    public default TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessParameterized().createSubAccessFromHost(accessToSub);
    }

    public default ListAccess<HOST, TYPE, TYPEACCESS> toList() {
        val spec = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS>() {

            @Override
            public List<TYPE> applyUnsafe(HOST host) throws Exception {
                val streamPlus = spec.apply(host);
                return streamPlus.toList();
            }

            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }

    public default FuncListAccess<HOST, TYPE, TYPEACCESS> toFuncList() {
        val spec = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPEACCESS>() {

            @Override
            public FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
                val streamPlus = spec.apply(host);
                return streamPlus.toList();
            }

            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
}
