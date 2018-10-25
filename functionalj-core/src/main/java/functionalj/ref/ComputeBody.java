package functionalj.ref;

@FunctionalInterface
public interface ComputeBody<DATA, EXCEPTION extends Exception> extends RunBody<EXCEPTION> {
    
    public DATA compute() throws EXCEPTION;
    
    public default void run() throws EXCEPTION {
        compute();
    }
    
}
