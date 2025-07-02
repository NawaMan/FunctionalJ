package functionalj.exception;

/**
 * An {@link Exception} that wrap a {@link Throwable} so that it appears as an {@link Exception}.
 */
public class WrappedThrowableException extends Exception {
    
    private static final long serialVersionUID = 1879586611000403483L;
    
    public static WrappedThrowableException of(Throwable throwable) {
        if (throwable instanceof WrappedThrowableException)
            return (WrappedThrowableException) throwable;
        if (throwable instanceof WrappedThrowableRuntimeException)
            return new WrappedThrowableException(((WrappedThrowableRuntimeException)throwable).getThrowable());
        if (throwable == null) {
            return null;
        }
        return new WrappedThrowableException(throwable);
    }
    
    public static Exception exceptionOf(Throwable throwable) {
    	if (throwable instanceof Exception) {
        	return (Exception)throwable;
    	}
		return WrappedThrowableException.of(throwable);
    }
    
    public WrappedThrowableException(Throwable throwable) {
        super(throwable);
    }
    
    public Throwable getThrowable() {
        return this.getCause();
    }
    
}
