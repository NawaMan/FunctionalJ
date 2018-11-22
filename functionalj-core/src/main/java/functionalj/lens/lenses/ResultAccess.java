package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.result.Result;
import lombok.val;

// TODO This is made quickly to accommodate Lens for Sealed classes. It is not complete in anyway.
@FunctionalInterface
public interface ResultAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
                    extends
                            ObjectAccess<HOST, Result<TYPE>>,
                            AccessParameterized<HOST, Result<TYPE>, TYPE, SUBACCESS>  {
    
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
        ResultAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> map(Function<TYPE, TARGET> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
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
        ResultAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> flatMap(Function<TYPE, Result<TARGET>> mapper) {
        val accessWithSub = new AccessParameterized<HOST, Result<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
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
    public default BooleanAccess<HOST> isPresent() {
        return host -> {
            return ResultAccess.this.apply(host).isPresent();
        };
    }
    public default BooleanAccess<HOST> isNull() {
        return host -> {
            return ResultAccess.this.apply(host).isNull();
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
