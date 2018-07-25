package functionalj.types.result;

@SuppressWarnings("javadoc")
public class ResultCancelledException extends ResultNotAvailableException {
    
    ResultCancelledException() {
        
    }
    public ResultCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
