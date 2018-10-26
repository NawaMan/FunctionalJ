package functionalj.environments;

import static java.util.Objects.requireNonNull;

public class InterruptedRuntimeException extends RuntimeException {
    
    private static final long serialVersionUID = 8712024055767106665L;
    
    public InterruptedRuntimeException(InterruptedException cause) {
        super(requireNonNull(cause));
    }
    
}
