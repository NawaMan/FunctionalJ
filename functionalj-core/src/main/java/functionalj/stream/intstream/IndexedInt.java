package functionalj.stream.intstream;

import functionalj.tuple.IntIntTuple;

/**
 * An integer item with index attached to it.
 */
public class IndexedInt extends IntIntTuple {
    
    public IndexedInt(int index, int data) {
        super(index, data);
    }
    
    /** @return  the index. */
    public final int index() {
        return _1();
    }
    
    /** @return  the item value. */
    public final int item() {
        return _2();
    }
    
}
