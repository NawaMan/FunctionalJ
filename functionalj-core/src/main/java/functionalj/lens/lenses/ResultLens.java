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

import static functionalj.functions.StrFuncs.whenBlank;
import static functionalj.lens.core.LensUtils.createLensSpecParameterized;
import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.function.Named;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.WriteLens;
import functionalj.result.Result;
import lombok.val;


@FunctionalInterface
public interface ResultLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        extends 
            ObjectLens<HOST, Result<TYPE>>,
            ResultAccess<HOST, TYPE, SUBLENS> {
    
    
    public static class Impl<H, T, SL extends AnyLens<H, T>> extends ObjectLens.Impl<H, Result<T>> implements ResultLens<H, T, SL> {
        
        private LensSpecParameterized<H, Result<T>, T, SL> spec;
        
        public final SL value;
        
        public Impl(String name, LensSpecParameterized<H, Result<T>, T, SL> spec) {
            super(name, spec.getSpec());
            this.spec = spec;
            this.value = get();
        }

        @Override
        public LensSpecParameterized<H, Result<T>, T, SL> lensSpecWithSub() {
            return spec;
        }
        
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        ResultLens.Impl<HOST, TYPE, SUBLENS> of(
            String                                                   name,
            LensSpecParameterized<HOST, Result<TYPE>, TYPE, SUBLENS> spec) {
        return new Impl<>(name, spec);
    }
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        ResultLens.Impl<HOST, TYPE, SUBLENS> of(
            String                                  name,
            LensSpec<HOST, Result<TYPE>>            resultLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read  = resultLensSpec.getRead();
        val write = resultLensSpec.getWrite();
        val spec  = createLensSpecParameterized(read, write, subCreator);
        return of(name, spec);
    }
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        ResultLens.Impl<HOST, TYPE, SUBLENS> of(
            LensSpec<HOST, Result<TYPE>>            resultLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return of(null, resultLensSpec, subCreator);
    }
    
    
    public LensSpecParameterized<HOST, Result<TYPE>, TYPE, SUBLENS> lensSpecWithSub();
    
    @Override
    default AccessParameterized<HOST, Result<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<Result<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, Result<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }
    
    @Override
    public default Result<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
    public default SUBLENS get() {
        Function<HOST, TYPE> read = lensSpec().getRead().andThen(Result::get);
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Result.valueOf(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(read, write);
        val thisName = (this instanceof Named) ? ((Named)this).name() : null;
        val lensName = whenBlank(Stream.of(thisName, "value").filter(Objects::nonNull).collect(joining(".")), (String)null);
        return lensSpecWithSub().createSubLens(lensName, subSpec);
    }
    
    public default SUBLENS value() {
        return get();
    }

}
