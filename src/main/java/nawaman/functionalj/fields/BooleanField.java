package nawaman.functionalj.fields;

import java.util.function.Function;
import java.util.function.Predicate;

public interface BooleanField<HOST> extends AnyEqualableField<HOST, Boolean> , Predicate<HOST> {
    
    public default boolean test(HOST host) {
        return Boolean.TRUE.equals(this.apply(host));
    }
    
    public default BooleanField<HOST> nagate() {
        return booleanField(false, bool -> !bool);
    }
    public default BooleanField<HOST> or(boolean anotherBoolean) {
        return booleanField(false, bool -> bool || anotherBoolean);
    }
    public default BooleanField<HOST> and(boolean anotherBoolean) {
        return booleanField(false, bool -> bool && anotherBoolean);
    }
}
