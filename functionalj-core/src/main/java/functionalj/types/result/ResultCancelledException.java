package functionalj.types.result;

public class ResultCancelledException extends ResultNotAvailableException {
    
    ResultCancelledException() {
        
    }
    public ResultCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
