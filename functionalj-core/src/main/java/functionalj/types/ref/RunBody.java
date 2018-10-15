package functionalj.types.ref;

@FunctionalInterface
public interface RunBody<EXCEPTION extends Exception> {
	
	public void run() throws EXCEPTION;
	
}
