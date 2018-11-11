package functionalj.ref;

@FunctionalInterface
public interface RunBody<EXCEPTION extends Exception> {
    
    
    public static RunBody<RuntimeException> from(Runnable runnable) {
        return ()->runnable.run();
    }
    
    
	public void run() throws EXCEPTION;
	
}
