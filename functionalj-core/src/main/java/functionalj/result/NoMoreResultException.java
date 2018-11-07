package functionalj.result;

public class NoMoreResultException extends ResultNotAvailableException {
    
    private static final long serialVersionUID = 3232883306883973811L;
    
    NoMoreResultException() {
        
    }
    public NoMoreResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
