package functionalj.types.result;

@SuppressWarnings("javadoc")
public class ResultNotReadyException extends ResultNotAvailableException {
    
    public ResultNotReadyException() {
        
    }
    public ResultNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
