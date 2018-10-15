package functionalj.types.ref;

public interface ComputeBody<DATA, EXCEPTION extends Exception> {
	
	public DATA run() throws EXCEPTION;
	
}
