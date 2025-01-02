package functionalj.stream.doublestream;

import functionalj.tuple.IntDoubleTuple;

/**
 * An double item with index attached to it.
 */
public class IndexedDouble extends IntDoubleTuple {
    
    public IndexedDouble(int index, double data) {
        super(index, data);
    }
    
    /** @return  the index. */
    public final int index() {
        return _1();
    }
    
    /** @return  the item value. */
    public final double item() {
        return _2();
    }
    
}
