package functionalj.functions;

@SuppressWarnings("javadoc")
public class FunctionInvocationException extends RuntimeException {
    
	private static final long serialVersionUID = 1145475380276387579L;

	public FunctionInvocationException(Exception exception) {
        super(exception);
    }
    
    public FunctionInvocationException(String message) {
        super(message);
    }
    
}
