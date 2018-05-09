package functionalj.lens;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface NullableAccess<HOST, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends ObjectAccess<HOST, Nullable<TYPE>>, AccessWithSub<HOST, Nullable<TYPE>, TYPE, SUBACCESS> {
    
    public AccessWithSub<HOST, Nullable<TYPE>, TYPE, SUBACCESS> accessWithSub();
    
    @Override
    public default Nullable<TYPE> apply(HOST input) {
        return accessWithSub().apply(input);
    }
    
    @Override
    public default SUBACCESS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
        return accessWithSub().createSubAccess(accessToSub);
    }
    
    public default SUBACCESS get() {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.get();
        });
    }
    
    public default <TARGET> 
    NullableAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> map(Func1<TYPE, TARGET> mapper) {
        val accessWithSub = new AccessWithSub<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Nullable<TARGET> apply(HOST host) {
                return NullableAccess.this.apply(host).map(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccess(Function<Nullable<TARGET>, TARGET> accessToSub) {
                return host->{
                    return accessToSub.apply(apply(host));
                };
            }
        };
        return new NullableAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessWithSub<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
                return accessWithSub;
            }
        };
    }
    
    public default <TARGET> 
    NullableAccess<HOST, TARGET, AnyAccess<HOST, TARGET>> flatMap(Func1<TYPE, Nullable<TARGET>> mapper) {
        val accessWithSub = new AccessWithSub<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public Nullable<TARGET> apply(HOST host) {
                return NullableAccess.this.apply(host).flatMap(mapper);
            }
            @Override
            public AnyAccess<HOST, TARGET> createSubAccess(Function<Nullable<TARGET>, TARGET> accessToSub) {
                return host->{
                    return accessToSub.apply(apply(host));
                };
            }
        };
        return new NullableAccess<HOST, TARGET, AnyAccess<HOST,TARGET>>() {
            @Override
            public AccessWithSub<HOST, Nullable<TARGET>, TARGET, AnyAccess<HOST, TARGET>> accessWithSub() {
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
