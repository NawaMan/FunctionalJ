// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.lens.core.AccessUtils.createSubFuncListAccess;
import static functionalj.lens.lenses.FuncListAccess.__internal__.subList;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.AccessUtils;
import functionalj.list.FuncList;
import functionalj.stream.Streamable;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface FuncListAccess<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> 
        extends CollectionAccess<HOST, FuncList<TYPE>, TYPE, TYPEACCESS> {
    
    public static <H, T, A extends AnyAccess<H, T>> FuncListAccess<H, T, A> of(Function<H, FuncList<T>> read, Function<Function<H, T>, A> createAccess) {
        val accessParameterized = new AccessParameterized<H, FuncList<T>, T, A>() {
            @Override
            public FuncList<T> applyUnsafe(H host) throws Exception {
                return read.apply(host);
            }
            @Override
            public A createSubAccessFromHost(Function<H, T> accessToParameter) {
                return createAccess.apply(accessToParameter);
            }
        };
        return AccessUtils.createSubFuncListAccess(accessParameterized, read);
    }
    
    // :-( .. have to be duplicate
    public default TYPEACCESS first() {
        return at(0);
    }
    public default TYPEACCESS last() {
        return accessParameterized()
                .createSubAccess((FuncList<TYPE> list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    return list.get(list.size() - 1);
                });
    }
    public default TYPEACCESS at(int index) {
        return accessParameterized().createSubAccess((FuncList<TYPE> list) -> {
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
    public default TYPEACCESS get(int index) {
        return at(index);
    }
    public default IntegerAccess<HOST> indexOf(Object o) {
        return intAccess(-1, list -> list.indexOf(o));
    }
    public default IntegerAccess<HOST> lastIndexOf(Object o) {
        return intAccess(-1, list -> list.lastIndexOf(o));
    }
    
    public default FuncListAccess<HOST, Integer, IntegerAccess<HOST>> indexesOf(Predicate<? super TYPE> check) {
        val access  = new AccessParameterized<HOST, FuncList<Integer>, Integer, IntegerAccess<HOST>>() {
            @Override
            public FuncList<Integer> applyUnsafe(HOST host) throws Exception {
                return FuncListAccess.this.apply(host).indexesOf(check);
            }
            @Override
            public IntegerAccess<HOST> createSubAccessFromHost(Function<HOST, Integer> accessToParameter) {
                return host -> accessToParameter.apply(host);
            }
        };
        return () -> access;
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> rest() {
        return subList(this, host -> {
            return apply(host)
                    .rest();
        });
    }
    
    // map
    // flatMap
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> filter(Predicate<TYPE> checker) {
        return subList(this, host -> {
            return apply(host)
                    .filter(checker);
        });
    }
    
	public default FuncListAccess<HOST, TYPE, TYPEACCESS> appendAll(TYPE[] data) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(data);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> appendAll(Collection<? extends TYPE> collection) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(collection);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> appendAll(Supplier<Stream<? extends TYPE>> supplier) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(supplier);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> with(int index, TYPE value) {
        return subList(this, host -> {
            return apply(host)
                    .with(index, value);
        });
    }
    
    @SuppressWarnings("unchecked")
	public default FuncListAccess<HOST, TYPE, TYPEACCESS> insertAt(int index, TYPE ... elements) {
        return subList(this, host -> {
            return apply(host)
                    .insertAt(index, elements);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> insertAllAt(int index, Collection<? extends TYPE> collection) {
        return subList(this, host -> {
            return apply(host)
                    .insertAllAt(index, collection);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> insertAllAt(int index, Streamable<? extends TYPE> streamable) {
        return subList(this, host -> {
            return apply(host)
                    .insertAllAt(index, streamable);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> excludeAt(int index) {
        return subList(this, host -> {
            return apply(host)
                    .excludeAt(index);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> excludeFrom(int fromIndexInclusive, int count) {
        return subList(this, host -> {
            return apply(host)
                    .excludeFrom(fromIndexInclusive, count);
        });
    }
    
    public default FuncListAccess<HOST, TYPE, TYPEACCESS> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        return subList(this, host -> {
            return apply(host)
                    .excludeBetween(fromIndexInclusive, toIndexExclusive);
        });
    }
    
    // groupingBy
    // toMap
    
    public static class __internal__ {

        public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> 
                        FuncListAccess<HOST, TYPE, TYPEACCESS> subList(
                                FuncListAccess<HOST, TYPE, TYPEACCESS> lens,
                                Function<HOST, FuncList<TYPE>> read) {
            return createSubFuncListAccess(lens.accessParameterized(), read);
        }
        
    }
    
}
