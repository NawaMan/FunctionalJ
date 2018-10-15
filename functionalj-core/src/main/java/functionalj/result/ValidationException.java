package functionalj.result;

import lombok.val;

@SuppressWarnings("javadoc")
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 2317758566674598943L;



	public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Exception cause) {
        super(cause);
    }
    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }
    
    ValidationException() {
    }
    
    @Override
    public String toString() {
        val msg      = this.getMessage();
        val cause    = getCause();
        val causeMsg = ((msg != null) || (cause == null)) ? "" : ": " + cause.toString();
        return super.toString() + causeMsg;
    }
    
    
    
    public static ValidationException of(Exception e) {
        if (e instanceof ValidationException)
            return (ValidationException)e;
        return new ValidationException(e);
    }
    
}
