package functionalj.types;

public class ResultCancelledException extends ResultNotAvailableException {
    
    public ResultCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
