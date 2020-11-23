// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.result.Result;


// TODO This is made quickly to accommodate Lens for Choice type. It is not complete in anyway.
@FunctionalInterface
public interface ResultAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
                    extends
                            ObjectAccess<HOST, Result<TYPE>>,
                            AccessParameterized<HOST, Result<TYPE>, TYPE, SUBACCESS>  {
    
    public static <H, T, A extends AnyAccess<H, T>> ResultAccess<H, T, A> of(Function<H, Result<T>> read, Function<Function<H, T>, A> createAccess) {
        var accessParameterized = new AccessParameterized<H, Result<T>, T, A>() {
            @Override
            public Result<T> applyUnsafe(H host) throws Exception {
                return read.apply(host);
            }
            @Override
            public A createSubAccessFromHost(Function<H, T> accessToParameter) {
                return createAccess.apply(accessToParameter);
            }
        };
        return AccessUtils.createSubResultAccess(accessParameterized, read);
    }
    
    
    public AccessParameterized<HOST, Result<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default Result<TYPE> applyUnsafe(HOST host) throws Exception {
        return accessWithSub().apply(host);
    }
    
    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessWithSub().createSubAccessFromHost(accessToSub);
    }
    
    public default SUBACCESS get() {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> result) -> { 
            return result.get();
        });
    }
    
    public default <TARGET> 
        ResultAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> thenMap(Function<TYPE, TARGET> mapper) {
        var accessWithSub = new AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Result<TARGET> applyUnsafe(HOST host) throws Exception {
                Result<TYPE> result = ResultAccess.this.apply(host);
                if (result == null) result = Result.ofNull();
                return result.map(Func1.from(mapper));
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new ResultAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default <TARGET> 
        ResultAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> thenFlatMap(Function<TYPE, Result<TARGET>> mapper) {
        var accessWithSub = new AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Result<TARGET> applyUnsafe(HOST host) throws Exception {
                return ResultAccess.this.apply(host).flatMap(Func1.from(mapper));
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return new ResultAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    // TODO - Add more of these.
    public default BooleanAccessPrimitive<HOST> isPresent() {
        return host -> {
            return ResultAccess.this.apply(host).isPresent();
        };
    }
    public default BooleanAccessPrimitive<HOST> isNull() {
        return host -> {
            return ResultAccess.this.apply(host).isNull();
        };
    }
    public default BooleanAccessPrimitive<HOST> isValue() {
        return host -> {
            return ResultAccess.this.apply(host).isValue();
        };
    }
    
    public default SUBACCESS orElse(TYPE fallbackValue) {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> nullable) -> { 
            return nullable.orElse(fallbackValue);
        });
    }
    
    public default SUBACCESS orElseGet(Supplier<TYPE> fallbackValueSupplier) {
        return orGet(fallbackValueSupplier);
    }
    public default SUBACCESS orGet(Supplier<TYPE> fallbackValueSupplier) {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> nullable) -> { 
            return nullable.orElseGet(fallbackValueSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow() {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> result) -> { 
            return result.orThrowRuntimeException();
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Function<Exception, EXCEPTION> exceptionSupplier) {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> nullable) -> { 
            return nullable.orThrowRuntimeException(exceptionSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return ResultAccess.this.accessWithSub().createSubAccess((Result<TYPE> nullable) -> { 
            return nullable.orThrow(__ -> exceptionSupplier.get());
        });
    }
    
}
