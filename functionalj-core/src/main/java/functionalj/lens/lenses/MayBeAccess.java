package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.core.AccessParameterized;
import functionalj.types.MayBe;
import lombok.val;

@FunctionalInterface
public interface MayBeAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends
                ObjectAccess<HOST, MayBe<TYPE>>,
                AccessParameterized<HOST, MayBe<TYPE>, TYPE, SUBACCESS> {
    
    public AccessParameterized<HOST, MayBe<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default MayBe<TYPE> applyUnsafe(HOST host) throws Exception {
        return accessWithSub().apply(host);
    }

    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessWithSub().createSubAccessFromHost(accessToSub);
    }
    
    public default SUBACCESS get() {
        return MayBeAccess.this.accessWithSub().createSubAccess((MayBe<TYPE> mayBe) -> { 
            return mayBe.get();
        });
    }
    
    public default <TARGET> 
    MayBeAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> map(Function<TYPE, TARGET> mapper) {
        val accessWithSub = new AccessParameterized<HOST, MayBe<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public MayBe<TARGET> applyUnsafe(HOST host) throws Exception {
                return MayBeAccess.this.apply(host).map(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return () -> accessWithSub;
    }
    
    public default <TARGET> MayBeAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> flatMap(Function<TYPE, MayBe<TARGET>> mapper) {
        val accessWithSub = new AccessParameterized<HOST, MayBe<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public MayBe<TARGET> applyUnsafe(HOST host) throws Exception {
                return (MayBe<TARGET>) MayBeAccess.this.apply(host)
                        .flatMap(value -> mapper.apply(value));
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccessFromHost(Function<HOST, TARGET> accessToParameter) {
                return accessToParameter::apply;
            }
        };
        return () -> accessWithSub;
    }
    
    public default BooleanAccess<HOST> isPresent() {
        return host -> {
            return MayBeAccess.this.apply(host).isPresent();
        };
    }
    public default BooleanAccess<HOST> isNotNull() {
        return host -> {
            return MayBeAccess.this.apply(host).isPresent();
        };
    }
    public default BooleanAccess<HOST> isNull() {
        return host -> {
            return !MayBeAccess.this.apply(host).isPresent();
        };
    }
    
    public default SUBACCESS orElse(TYPE fallbackValue) {
        return MayBeAccess.this.accessWithSub().createSubAccess((MayBe<TYPE> mayBe) -> { 
            return mayBe.orElse(fallbackValue);
        });
    }
    
    public default SUBACCESS orElseGet(Supplier<TYPE> fallbackValueSupplier) {
        return MayBeAccess.this.accessWithSub().createSubAccess((MayBe<TYPE> mayBe) -> { 
            return mayBe.orElseGet(fallbackValueSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow() {
        return MayBeAccess.this.accessWithSub().createSubAccess((MayBe<TYPE> mayBe) -> { 
            return mayBe.orElseThrow();
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return MayBeAccess.this.accessWithSub().createSubAccess((MayBe<TYPE> mayBe) -> { 
            return (TYPE)mayBe.orElseThrow(exceptionSupplier);
        });
    }
    
}
