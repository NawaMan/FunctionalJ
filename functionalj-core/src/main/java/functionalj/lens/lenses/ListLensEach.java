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

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.list.FuncList;
import lombok.val;

public class ListLensEach<HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> implements FuncListAccess<HOST, TYPE, TYPELENS> {
    
    private final FuncListLens<HOST, TYPE, TYPELENS> parentLens;
    
    private final Predicate<TYPE> checker;
    
    public ListLensEach(FuncListLens<HOST, TYPE, TYPELENS> parentLens, Predicate<TYPE> checker) {
        this.parentLens = parentLens;
        this.checker = checker;
    }
    
    @Override
    public AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> accessParameterized() {
        val read = Func.f((HOST host) -> {
            return apply(host).filter(checker);
        });
        val specWithSub = new AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS>() {
        
            @Override
            public FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
        
            @Override
            public TYPELENS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return parentLens.accessParameterized().createSubAccessFromHost(accessToParameter);
            }
        };
        return specWithSub;
    }
    
    public Func1<HOST, HOST> changeToNull() {
        return changeTo((TYPE) null);
    }
    
    public Func1<HOST, HOST> changeTo(TYPE newValue) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, __ -> newValue);
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
    
    public Func1<HOST, HOST> changeTo(Supplier<TYPE> supplier) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, __ -> supplier.get());
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
    
    public Func1<HOST, HOST> changeTo(Function<TYPE, TYPE> mapper) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, mapper);
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
}
