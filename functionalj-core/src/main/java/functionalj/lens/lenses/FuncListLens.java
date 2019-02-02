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

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import lombok.val;

@SuppressWarnings("javadoc")
public interface FuncListLens<HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, FuncList<TYPE>>,
            FuncListAccess<HOST, TYPE, TYPELENS> {
    
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            FuncListLens<HOST, TYPE, TYPELENS> of(
                Function<HOST,  FuncList<TYPE>>    read,
                WriteLens<HOST, FuncList<TYPE>>    write,
                Function<LensSpec<HOST, TYPE>, TYPELENS> subCreator) {
        return LensUtils.createFuncListLens(read, write, subCreator);
    }
    public static <HOST,  TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            FuncListLens<HOST, TYPE, TYPELENS> of(LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> spec) {
        return ()->spec;
    }
    
    // :-( Duplicate 
    
    public LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> lensSpecParameterized();
    
    public default AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default TYPELENS createSubAccess(Function<FuncList<TYPE>, TYPE> accessToSub) {
        return accessParameterized()
                .createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, FuncList<TYPE>> lensSpec() {
        return lensSpecParameterized()
                .getSpec();
    }
    
    @Override
    public default FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec()
                .getRead()
                .apply(host);
    }
    
    public default TYPELENS createSubLens(Function<FuncList<TYPE>, TYPE> readSub, WriteLens<FuncList<TYPE>, TYPE> writeSub) {
        return LensUtils.createSubLens(this, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
    public default TYPELENS first() {
        return createSubLens(
                (list)           -> list.first().get(),
                (list, newValue) -> list.with(0, newValue));
    }
    
    public default TYPELENS last() {
        return createSubLens(
                (list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    return list.get(list.size() - 1);
                },
                (list, newValue)->{
                    val newList = new ArrayList<>(list);
                    newList.set(list.size() - 1, newValue);
                    return ImmutableList.from(newList);
                });
    }
    
    public default TYPELENS at(int index) {
        return createSubLens(
                (list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    if (index < 0) 
                        return null;
                    if (index >= list.size())
                        return null;
                    return list.get(index);
                },
                (list, newValue) -> {
                    return list.with(index, newValue);
                });
    }
    
    public default Func1<HOST, HOST> changeTo(Predicate<TYPE> checker, Function<TYPE, TYPE> mapper) {
        return host -> {
            val newList = apply(host).map(each -> checker.test(each) ? mapper.apply(each) : each);
            val newHost = FuncListLens.this.apply(host, newList);
            return newHost;
        };
    }
    
}
