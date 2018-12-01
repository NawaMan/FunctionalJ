package functionalj.annotations.choice;

import java.util.Objects;

public abstract class AbstractChoiceClass<S> {
    
    public abstract S __switch();
    
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
    }
    
}
