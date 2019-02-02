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

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.core.AccessParameterized;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface OptionalAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends
                ObjectAccess<HOST, Optional<TYPE>>,
                AccessParameterized<HOST, Optional<TYPE>, TYPE, SUBACCESS> {
    
    public AccessParameterized<HOST, Optional<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default Optional<TYPE> applyUnsafe(HOST host) throws Exception {
        return accessWithSub().apply(host);
    }

    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessWithSub().createSubAccessFromHost(accessToSub);
    }
    
    public default SUBACCESS get() {
        return OptionalAccess.this.accessWithSub().createSubAccess((Optional<TYPE> optional) -> { 
            return optional.get();
        });
    }
    
    public default <TARGET> 
    OptionalAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> map(Function<TYPE, TARGET> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Optional<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Optional<TARGET> applyUnsafe(HOST host) throws Exception {
                Optional<TYPE> optional = OptionalAccess.this.apply(host);
                if (optional == null) optional = Optional.empty();
                return optional.map(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new OptionalAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Optional<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default <TARGET> 
    OptionalAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> flatMap(Function<TYPE, Optional<TARGET>> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Optional<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Optional<TARGET> applyUnsafe(HOST host) throws Exception {
                return OptionalAccess.this.apply(host).flatMap(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new OptionalAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Optional<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default BooleanAccess<HOST> isPresent() {
        return host -> {
            return OptionalAccess.this.apply(host).isPresent();
        };
    }
    
    public default SUBACCESS orElse(TYPE fallbackValue) {
        return OptionalAccess.this.accessWithSub().createSubAccess((Optional<TYPE> optional) -> { 
            return optional.orElse(fallbackValue);
        });
    }
    
    public default SUBACCESS orElseGet(Supplier<TYPE> fallbackValueSupplier) {
        return orGet(fallbackValueSupplier);
    }
    public default SUBACCESS orGet(Supplier<TYPE> fallbackValueSupplier) {
        return OptionalAccess.this.accessWithSub().createSubAccess((Optional<TYPE> optional) -> { 
            return optional.orElseGet(fallbackValueSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return OptionalAccess.this.accessWithSub().createSubAccess((Optional<TYPE> optional) -> { 
            return optional.orElseThrow(exceptionSupplier);
        });
    }
    
}
