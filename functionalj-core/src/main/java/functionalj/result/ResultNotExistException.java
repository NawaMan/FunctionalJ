package functionalj.types.result;

@SuppressWarnings("javadoc")
public class ResultNotExistException extends RuntimeException {
    
    private static final long serialVersionUID = -1710800832797536830L;

    public ResultNotExistException() {
        super();
    }
    public ResultNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
