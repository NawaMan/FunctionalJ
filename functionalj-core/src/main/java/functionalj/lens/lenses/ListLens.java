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

import static functionalj.functions.StrFuncs.joinNonNull;
import static functionalj.functions.StrFuncs.whenBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import functionalj.function.Func1;
import functionalj.function.Named;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import lombok.val;

@FunctionalInterface
public interface ListLens<HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> extends ObjectLens<HOST, List<TYPE>>, ListAccess<HOST, TYPE, TYPELENS> {
    
    public static class Impl<H, T, SL extends AnyLens<H, T>> extends ObjectLens.Impl<H, List<T>> implements ListLens<H, T, SL> {
        
        private LensSpecParameterized<H, List<T>, T, SL> spec;
        
        public Impl(String name, LensSpecParameterized<H, List<T>, T, SL> spec) {
            super(name, spec.getSpec());
            this.spec = spec;
        }
        
        @Override
        public LensSpecParameterized<H, List<T>, T, SL> lensSpecParameterized() {
            return spec;
        }
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS> of(Function<HOST, List<TYPE>> read, WriteLens<HOST, List<TYPE>> write, Function<LensSpec<HOST, TYPE>, TYPELENS> subCreator) {
        return LensUtils.createListLens(read, write, subCreator);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS> of(String name, LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> spec) {
        return new Impl<>(name, spec);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS> of(LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> spec) {
        return of(null, spec);
    }
    
    public LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> lensSpecParameterized();
    
    public default AccessParameterized<HOST, List<TYPE>, TYPE, TYPELENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default TYPELENS createSubAccess(Function<List<TYPE>, TYPE> accessToSub) {
        return accessParameterized().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, List<TYPE>> lensSpec() {
        return lensSpecParameterized().getSpec();
    }
    
    @Override
    public default List<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
    public default TYPELENS createSubLens(String name, Function<List<TYPE>, TYPE> readSub, WriteLens<List<TYPE>, TYPE> writeSub) {
        return LensUtils.createSubLens(this, name, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
    public default TYPELENS createSubLens(Function<List<TYPE>, TYPE> readSub, WriteLens<List<TYPE>, TYPE> writeSub) {
        return LensUtils.createSubLens(this, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
    public default TYPELENS first() {
        val index = 0;
        val name = (this instanceof Named) ? ((Named) this).name() : null;
        val lensName = whenBlank(joinNonNull(".", name, "first()"), (String) null);
        return createSubLens(lensName, (list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        }, (list, newValue) -> {
            val newList = new ArrayList<>(list);
            newList.set(index, newValue);
            return newList;
        });
    }
    
    public default TYPELENS last() {
        val name = (this instanceof Named) ? ((Named) this).name() : null;
        val lensName = whenBlank(joinNonNull(".", name, "last()"), (String) null);
        return createSubLens(lensName, (list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        }, (list, newValue) -> {
            val newList = new ArrayList<>(list);
            newList.set(list.size() - 1, newValue);
            return newList;
        });
    }
    
    public default TYPELENS at(int index) {
        val name = (this instanceof Named) ? ((Named) this).name() : null;
        val lensName = whenBlank(joinNonNull(".", name, "at(" + index + ")"), (String) null);
        return createSubLens(lensName, (list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index < 0)
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        }, (list, newValue) -> {
            val newList = new ArrayList<>(list);
            newList.set(index, newValue);
            return newList;
        });
    }
    
    public default Func1<HOST, HOST> changeTo(Predicate<TYPE> checker, Function<TYPE, TYPE> mapper) {
        return host -> {
            val newList = apply(host).stream().map(each -> checker.test(each) ? mapper.apply(each) : each).collect(Collectors.toList());
            val newHost = apply(host, newList);
            return newHost;
        };
    }
}
