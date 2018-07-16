package functionalj.types.result;

public class ResultCancelledException extends ResultNotAvailableException {
    
    public ResultCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
