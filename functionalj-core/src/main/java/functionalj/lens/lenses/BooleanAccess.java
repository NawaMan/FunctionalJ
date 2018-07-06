package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface BooleanAccess<HOST> 
        extends 
            AnyAccess<HOST, Boolean>, 
            Predicate<HOST>, 
            ConcreteAccess<HOST, Boolean, BooleanAccess<HOST>> {
    
    @Override
    public default BooleanAccess<HOST> newAccess(Function<HOST, Boolean> access) {
        return access::apply;
    }
    
    public default boolean test(HOST host) {
        return Boolean.TRUE.equals(this.apply(host));
    }
    
    public default BooleanAccess<HOST> nagate() {
        return booleanAccess(false, bool -> !bool);
    }
    public default BooleanAccess<HOST> or(boolean anotherBoolean) {
        return booleanAccess(false, bool -> bool || anotherBoolean);
    }
    public default BooleanAccess<HOST> and(boolean anotherBoolean) {
        return booleanAccess(false, bool -> bool && anotherBoolean);
    }
    
}

