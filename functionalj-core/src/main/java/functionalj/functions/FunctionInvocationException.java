package functionalj.functions;

public class FunctionInvocationException extends RuntimeException {
    
    public FunctionInvocationException(Exception exception) {
        super(exception);
    }
    
}
