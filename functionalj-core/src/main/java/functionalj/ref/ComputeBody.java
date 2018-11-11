package functionalj.ref;

import java.util.function.Supplier;

@FunctionalInterface
public interface ComputeBody<DATA, EXCEPTION extends Exception> extends RunBody<EXCEPTION> {
    
    public static <D> ComputeBody<D, RuntimeException> from(Supplier<D> supplier) {
        return ()->supplier.get();
    }
    
    
    public DATA compute() throws EXCEPTION;
    
    public default void run() throws EXCEPTION {
        compute();
    }
    
}
