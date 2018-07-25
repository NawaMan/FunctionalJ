package functionalj.types;

@SuppressWarnings("javadoc")
public class WrapException extends RuntimeException {
    
	private static final long serialVersionUID = -5814440055771538679L;
	
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
