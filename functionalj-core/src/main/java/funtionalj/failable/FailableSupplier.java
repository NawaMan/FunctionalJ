package funtionalj.failable;

@FunctionalInterface
public interface FailableSupplier<VALUE> {
    
    public VALUE get() throws Throwable;
    
}
