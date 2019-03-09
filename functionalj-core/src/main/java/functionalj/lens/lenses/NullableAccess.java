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

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.AccessUtils;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface NullableAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends
                ObjectAccess<HOST, Nullable<TYPE>>,
                AccessParameterized<HOST, Nullable<TYPE>, TYPE, SUBACCESS> {
    
    public static <H, T, A extends AnyAccess<H, T>> NullableAccess<H, T, A> of(Function<H, Nullable<T>> read, Function<Function<H, T>, A> createAccess) {
        val accessParameterized = new AccessParameterized<H, Nullable<T>, T, A>() {
            @Override
            public Nullable<T> applyUnsafe(H host) throws Exception {
                return read.apply(host);
            }
            @Override
            public A createSubAccessFromHost(Function<H, T> accessToParameter) {
                return createAccess.apply(accessToParameter);
            }
        };
        return AccessUtils.createSubNullableAccess(accessParameterized, read);
    }
    
    public AccessParameterized<HOST, Nullable<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default Nullable<TYPE> applyUnsafe(HOST host) throws Exception {
        return accessWithSub().apply(host);
    }
    
    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessWithSub().createSubAccessFromHost(accessToSub);
    }
    
    public default SUBACCESS get() {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.get();
        });
    }
    
    public default <TARGET> 
    NullableAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> thenMap(Function<TYPE, TARGET> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Nullable<TARGET> applyUnsafe(HOST host) throws Exception {
                Nullable<TYPE> nullable = NullableAccess.this.apply(host);
                if (nullable == null) nullable = Nullable.empty();
                return nullable.map(Func1.from(mapper));
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new NullableAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default <TARGET> 
    NullableAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> thenFlatMap(Function<TYPE, Nullable<TARGET>> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Nullable<TARGET> applyUnsafe(HOST host) throws Exception {
                return NullableAccess.this.apply(host).flatMap(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new NullableAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default BooleanAccess<HOST> isPresent() {
        return host -> {
            return NullableAccess.this.apply(host).isPresent();
        };
    }
    public default BooleanAccess<HOST> isNotNull() {
        return host -> {
            return NullableAccess.this.apply(host).isNotNull();
        };
    }
    public default BooleanAccess<HOST> isNull() {
        return host -> {
            return NullableAccess.this.apply(host).isNull();
        };
    }
    
    public default SUBACCESS orElse(TYPE fallbackValue) {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.orElse(fallbackValue);
        });
    }
    
    public default SUBACCESS orElseGet(Supplier<TYPE> fallbackValueSupplier) {
        return orGet(fallbackValueSupplier);
    }
    public default SUBACCESS orGet(Supplier<TYPE> fallbackValueSupplier) {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.orElseGet(fallbackValueSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow() {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.orElseThrow();
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.orElseThrow(exceptionSupplier);
        });
    }
    
}
