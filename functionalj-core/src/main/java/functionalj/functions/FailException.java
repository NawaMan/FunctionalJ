package functionalj.functions;

public class FailException extends RuntimeException {
    
    public FailException(Exception exception) {
        super(exception);
    }
    
}
