package functionalj.lens.lenses;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.core.AccessParameterized;
import lombok.val;

@FunctionalInterface
public interface OptionalAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends
                ObjectAccess<HOST, Optional<TYPE>>,
                AccessParameterized<HOST, Optional<TYPE>, TYPE, SUBACCESS> {
    
    public AccessParameterized<HOST, Optional<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default Optional<TYPE> apply(HOST input) {
        return accessWithSub().apply(input);
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
            public Optional<TARGET> apply(HOST host) {
                return OptionalAccess.this.apply(host).map(mapper);
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
            public Optional<TARGET> apply(HOST host) {
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
