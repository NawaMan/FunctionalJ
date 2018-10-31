package functionalj;

import static java.util.Objects.requireNonNull;

public class InterruptedRuntimeException extends RuntimeException {
    
    private static final long serialVersionUID = 8712024055767106665L;
    
    public InterruptedRuntimeException(Exception cause) {
        super(requireNonNull(cause));
    }
    
}
