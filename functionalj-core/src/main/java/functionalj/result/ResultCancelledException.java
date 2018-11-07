package functionalj.result;

@SuppressWarnings("javadoc")
public class ResultCancelledException extends ResultNotAvailableException {
    
    private static final long serialVersionUID = 1402992361738590955L;
    
    ResultCancelledException() {
        
    }
    public ResultCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
