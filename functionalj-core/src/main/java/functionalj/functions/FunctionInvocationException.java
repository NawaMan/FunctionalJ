package functionalj.functions;

@SuppressWarnings("javadoc")
public class FunctionInvocationException extends RuntimeException {
    
    public FunctionInvocationException(Exception exception) {
        super(exception);
    }
    
}
