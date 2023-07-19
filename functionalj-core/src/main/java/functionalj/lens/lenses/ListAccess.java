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

import static functionalj.function.Func.f;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.AccessUtils;
import functionalj.stream.StreamPlus;
import lombok.val;

@FunctionalInterface
public interface ListAccess<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> extends CollectionAccess<HOST, List<TYPE>, TYPE, TYPEACCESS> {
    
    public static <H, T, A extends AnyAccess<H, T>> ListAccess<H, T, A> of(Function<H, List<T>> read, Function<Function<H, T>, A> createAccess) {
        val accessParameterized = new AccessParameterized<H, List<T>, T, A>() {
        
            @Override
            public List<T> applyUnsafe(H host) throws Exception {
                return read.apply(host);
            }
        
            @Override
            public A createSubAccessFromHost(Function<H, T> accessToParameter) {
                return createAccess.apply(accessToParameter);
            }
        };
        return AccessUtils.createSubListAccess(accessParameterized, read);
    }
    
    public default StreamPlusAccess<HOST, TYPE, TYPEACCESS> stream() {
        val accessParameterized = accessParameterized();
        return StreamPlusAccess.of(f(accessParameterized::apply).andThen(StreamPlus::from), accessParameterized::createSubAccessFromHost);
    }
    
    public default TYPEACCESS first() {
        return at(0);
    }
    
    public default TYPEACCESS last() {
        return accessParameterized().createSubAccess((List<TYPE> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        });
    }
    
    public default TYPEACCESS at(int index) {
        return accessParameterized().createSubAccess((List<TYPE> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index < 0)
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        });
    }
    
    public default ListAccess<HOST, TYPE, TYPEACCESS> filter(Predicate<TYPE> checker) {
        return AccessUtils.createSubListAccess(this.accessParameterized(), host -> {
            return apply(host).stream().filter(checker).collect(Collectors.toList());
        });
    }
}
