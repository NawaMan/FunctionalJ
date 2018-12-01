package functionalj.annotations.struct;

import java.util.Objects;

public interface IStruct {
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
    }
    
}
