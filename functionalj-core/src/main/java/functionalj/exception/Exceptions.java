package functionalj.exception;

public class Exceptions {
    
    public static Exception exceptionOf(Throwable throwable) {
    	if (throwable instanceof Exception) {
        	return (Exception)throwable;
    	}
		return WrappedThrowableException.of(throwable);
    }
    
    public static RuntimeException runtimeExceptionOf(Throwable throwable) {
    	if (throwable instanceof RuntimeException) {
        	return (RuntimeException)throwable;
    	}
		return WrappedThrowableRuntimeException.of(throwable);
    }
    
}
