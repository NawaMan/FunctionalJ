package functionalj.lens;

import java.util.function.Predicate;

public interface BooleanAccess<HOST> extends AnyAccess<HOST, Boolean> , Predicate<HOST> {
    
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
