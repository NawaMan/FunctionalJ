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

import static functionalj.lens.core.LensUtils.createLensSpecParameterized;
import java.util.function.Function;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.WriteLens;
import lombok.val;
import nullablej.nullable.Nullable;

@FunctionalInterface
public interface NullableLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> extends ObjectLens<HOST, Nullable<TYPE>>, NullableAccess<HOST, TYPE, SUBLENS> {

    public static class Impl<H, T, SL extends AnyLens<H, T>> extends ObjectLens.Impl<H, Nullable<T>> implements NullableLens<H, T, SL> {

        private LensSpecParameterized<H, Nullable<T>, T, SL> spec;

        public final SL value() {
            return get();
        }

        public Impl(String name, LensSpecParameterized<H, Nullable<T>, T, SL> spec) {
            super(name, spec.getSpec());
            this.spec = spec;
        }

        @Override
        public LensSpecParameterized<H, Nullable<T>, T, SL> lensSpecWithSub() {
            return spec;
        }
    }

    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> of(String name, LensSpec<HOST, Nullable<TYPE>> nullableLensSpec, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read = nullableLensSpec.getRead();
        val write = nullableLensSpec.getWrite();
        val spec = createLensSpecParameterized(read, write, subCreator);
        return new Impl<>(name, spec);
    }

    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> of(LensSpec<HOST, Nullable<TYPE>> nullableLensSpec, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return of(null, nullableLensSpec, subCreator);
    }

    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> of(String name, LensSpecParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> spec) {
        return new Impl<>(name, spec);
    }

    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> of(LensSpecParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> spec) {
        return of(null, spec);
    }

    public LensSpecParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> lensSpecWithSub();

    @Override
    default AccessParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }

    @Override
    public default SUBLENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }

    @Override
    public default LensSpec<HOST, Nullable<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }

    @Override
    public default Nullable<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }

    public default SUBLENS get() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue) -> {
            return lensSpec().getWrite().apply(host, Nullable.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Nullable::get), write);
        return lensSpecWithSub().createSubLens("value", subSpec);
    }
}
