package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.core.AccessCreator;

/*
This is (unexpected) work around for the reusability problems for these common methods whose are SELF typed.
This way, we just need to duplicate these methods here and AnyAccess.
All concrete access still have to implement this interface and the _of method.
 */


@SuppressWarnings("javadoc")
public interface ConcreteAccess<HOST, DATA, ACCESS extends AnyAccess<HOST, DATA>> 
        extends 
            AnyAccess<HOST, DATA>,
            AccessCreator<HOST, DATA, ACCESS> {
    
    public ACCESS newAccess(Function<HOST, DATA> accessToValue);
    
    // We unfortunately have to duplicate this.
    
    public default ACCESS orDefaultTo(DATA fallbackValue) {
        return newAccess(__internal__.orDefaultTo(this, fallbackValue));
    }
    public default ACCESS orDefaultFrom(Supplier<? extends DATA> fallbackValueSupplier) {
        return newAccess(__internal__.orDefaultFrom(this, fallbackValueSupplier));
    }
    public default <EXCEPTION extends RuntimeException> ACCESS orThrow() {
        return newAccess(__internal__.orThrow(this));
    }
    public default <EXCEPTION extends RuntimeException> ACCESS orThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return newAccess(__internal__.orThrow(this, exceptionSupplier));
    }
    public default NullableAccess<HOST, DATA, ACCESS> toNullable() {
        return __internal__.toNullable(this, f -> newAccess(f));
    }
}