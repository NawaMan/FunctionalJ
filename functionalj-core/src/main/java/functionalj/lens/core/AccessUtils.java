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
package functionalj.lens.core;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.FuncListAccess;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.NullableAccess;
import functionalj.lens.lenses.OptionalAccess;
import functionalj.lens.lenses.ResultAccess;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;
import nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class AccessUtils {
    
    // Nullable
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> NullableAccess<HOST, TYPE, TYPEACCESS>
            createSubNullableAccess(
                    AccessParameterized<HOST, Nullable<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, Nullable<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, Nullable<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public Nullable<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    // Optional 
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> OptionalAccess<HOST, TYPE, TYPEACCESS>
            createSubOptionalAccess(
                    AccessParameterized<HOST, Optional<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, Optional<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, Optional<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public Optional<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    // List 
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> ListAccess<HOST, TYPE, TYPEACCESS>
            createSubListAccess(
                    AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, List<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public List<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> FuncListAccess<HOST, TYPE, TYPEACCESS>
            createSubFuncListAccess(
                    AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, FuncList<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>> 
            NullableAccess<HOST, TYPE, TYPELENS> createNullableAccess(
                        Function<HOST, Nullable<TYPE>>           accessNullable,
                        Function<Function<HOST, TYPE>, TYPELENS> createSubLens) {
        val accessWithSub = new AccessParameterized<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
            @Override
            public Nullable<TYPE> applyUnsafe(HOST host) throws Exception {
                return accessNullable.apply(host);
            }
            @Override
            public TYPELENS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return createSubLens.apply(accessToParameter);
            }
        };
        return () -> accessWithSub;
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>> 
            ResultAccess<HOST, TYPE, TYPELENS> createResultAccess(
                        Function<HOST, Result<TYPE>>             accessResult,
                        Function<Function<HOST, TYPE>, TYPELENS> createSubLens) {
        val accessWithSub = new AccessParameterized<HOST, Result<TYPE>, TYPE, TYPELENS>() {
            @Override
            public Result<TYPE> applyUnsafe(HOST host) throws Exception {
                return accessResult.apply(host);
            }
            @Override
            public TYPELENS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return createSubLens.apply(accessToParameter);
            }
        };
        return () -> accessWithSub;
    }
    
}
