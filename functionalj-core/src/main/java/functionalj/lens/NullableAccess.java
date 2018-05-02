package functionalj.lens;

import java.util.List;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface NullableAccess<HOST, NULLABLE extends Nullable<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
            extends ObjectAccess<HOST, NULLABLE>, AccessWithSub<HOST, NULLABLE, TYPE, SUBACCESS> {
//    Not yet ready
//    public static <HOST, TYPE> NullableAccess<HOST, Nullable<TYPE>, TYPE, AnyAccess<HOST,TYPE>> theNullable() {
//        return null;
//    }
    
    public AccessWithSub<HOST, NULLABLE, TYPE, SUBACCESS> lensSpecWithSub();
    
    @Override
    public default NULLABLE apply(HOST input) {
        return lensSpecWithSub().apply(input);
    }
    
    @Override
    public default SUBACCESS createSubAccess(Func1<NULLABLE, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    public default SUBACCESS get() {
        return NullableAccess.this.lensSpecWithSub().createSubAccess((NULLABLE nullable) -> { 
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
        return NullableAccess.this.lensSpecWithSub().createSubAccess((NULLABLE nullable) -> { 
            return nullable.orElse(fallbackValue);
        });
    }
    
    public default SUBACCESS orElseGet(Supplier<TYPE> fallbackValueSupplier) {
        return NullableAccess.this.lensSpecWithSub().createSubAccess((NULLABLE nullable) -> { 
            return nullable.orElseGet(fallbackValueSupplier);
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow() {
        return NullableAccess.this.lensSpecWithSub().createSubAccess((NULLABLE nullable) -> { 
            return nullable.orElseThrow();
        });
    }
    
    public default <EXCEPTION extends RuntimeException> SUBACCESS orElseThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return NullableAccess.this.lensSpecWithSub().createSubAccess((NULLABLE nullable) -> { 
            return nullable.orElseThrow(exceptionSupplier);
        });
    }
    
}
