package functionalj.stream.longstream;

import functionalj.tuple.IntLongTuple;

/**
 * An long item with index attached to it.
 */
public class IndexedLong extends IntLongTuple {
    
    public IndexedLong(int index, long data) {
        super(index, data);
    }
    
    /** @return  the index. */
    public final int index() {
        return _1();
    }
    
    /** @return  the item value. */
    public final long item() {
        return _2();
    }
    
}
