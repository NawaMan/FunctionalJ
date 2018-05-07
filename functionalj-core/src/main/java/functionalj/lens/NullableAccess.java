package functionalj.lens;

import java.util.function.Supplier;

import functionalj.functions.Func1;
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
    public default SUBACCESS createSubAccess(Func1<Nullable<TYPE>, TYPE> accessToSub) {
        return accessWithSub().createSubAccess(accessToSub);
    }
    
    public default SUBACCESS get() {
        return NullableAccess.this.accessWithSub().createSubAccess((Nullable<TYPE> nullable) -> { 
            return nullable.get();
        });
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
