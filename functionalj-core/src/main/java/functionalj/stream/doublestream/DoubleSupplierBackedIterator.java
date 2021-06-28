package functionalj.stream.doublestream;

import java.util.function.DoubleSupplier;

import functionalj.functions.ThrowFuncs;
import functionalj.result.NoMoreResultException;

public class DoubleSupplierBackedIterator implements DoubleIteratorPlus {

    /** Throw a no more element exception. This is used for generator. */
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(() -> new NoMoreResultException());
        return (D) null;
    }
    
    private final    DoubleSupplier supplier;
    private volatile double         next;
    
    public DoubleSupplierBackedIterator(DoubleSupplier supplier) {
        this.supplier = supplier;
    }
    
    @Override
    public boolean hasNext() {
        try {
            next = supplier.getAsDouble();
            return true;
        } catch (NoMoreResultException e) {
            return false;
        }
    }
    
    @Override
    public double nextDouble() {
        return next;
    }
    
    @Override
    public OfDouble asIterator() {
        return this;
    }
}
