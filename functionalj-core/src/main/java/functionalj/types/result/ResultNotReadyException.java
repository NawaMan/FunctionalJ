package functionalj.types.result;

public class ResultNotReadyException extends ResultNotAvailableException {
    
    public ResultNotReadyException() {
        
    }
    public ResultNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
