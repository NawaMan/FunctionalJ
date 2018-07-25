package functionalj.types;

@SuppressWarnings("javadoc")
public class WrapException extends RuntimeException {
    
    private Exception exception;
    
    public static WrapException of(Exception exception) {
        if (exception instanceof WrapException)
            return (WrapException)exception;
        
        if (exception == null) {
            return null;
        }
        
        return new WrapException(exception);
    }
    
    private WrapException(Exception exception) {
        this.exception = exception;
    }
    
    public Exception getException() {
        return exception;
    }
    
}
