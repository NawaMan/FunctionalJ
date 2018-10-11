package functionalj.types.ref;

@FunctionalInterface
public interface RunBody<DATA, EXCEPTION extends Exception> {
	
	public DATA run() throws EXCEPTION;
	
}
