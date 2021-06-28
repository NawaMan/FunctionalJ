package functionalj.stream;

import java.util.Iterator;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.result.NoMoreResultException;

public class SupplierBackedIterator<DATA> implements Iterator<DATA> {
    
    /** Throw a no more element exception. This is used for generator. */
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(()->new NoMoreResultException());
        return (D)null;
    }
    
    private final Supplier<DATA> supplier;
    private DATA next;
    
    public SupplierBackedIterator(Supplier<DATA> supplier) {
        this.supplier = supplier;
    }
    
    @Override
    public boolean hasNext() {
        try {
            next = supplier.get();
            return true;
        } catch (NoMoreResultException e) {
            return false;
        }
    }
    @Override
    public DATA next() {
        return next;
    }
    
}
