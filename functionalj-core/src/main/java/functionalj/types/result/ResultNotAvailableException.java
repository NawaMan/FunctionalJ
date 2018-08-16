package functionalj.types.result;

@SuppressWarnings("javadoc")
public class ResultNotAvailableException extends RuntimeException {
    
	private static final long serialVersionUID = 8558949802999244614L;
	
	ResultNotAvailableException() {
    }
    ResultNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
