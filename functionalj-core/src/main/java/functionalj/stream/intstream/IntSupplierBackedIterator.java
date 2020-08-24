package functionalj.stream.intstream;

import java.util.function.IntSupplier;

import functionalj.functions.ThrowFuncs;
import functionalj.result.NoMoreResultException;

public class IntSupplierBackedIterator implements IntIteratorPlus {

    /** Throw a no more element exception. This is used for generator. */
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(() -> new NoMoreResultException());
        return (D) null;
    }

    private final IntSupplier supplier;
    private volatile int      next;

    public IntSupplierBackedIterator(IntSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean hasNext() {
        try {
            next = supplier.getAsInt();
            return true;
        } catch (NoMoreResultException e) {
            return false;
        }
    }

    @Override
    public int nextInt() {
        return next;
    }

    @Override
    public OfInt asIterator() {
        return this;
    }
}
