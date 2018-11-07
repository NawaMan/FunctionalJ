package functionalj.result;

public class NoMoreResultException extends ResultNotAvailableException {
    
    private static final long serialVersionUID = 3232883306883973811L;
    
    public NoMoreResultException() {
        this(null, null);
    }
    public NoMoreResultException(String message) {
        this(message, null);
    }
    public NoMoreResultException(Throwable cause) {
        this(null, cause);
    }
    public NoMoreResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
