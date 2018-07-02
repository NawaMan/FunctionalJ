package functionalj.types;

public class ResultNotReadyException extends ResultNotAvailableException {
    
    public ResultNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
