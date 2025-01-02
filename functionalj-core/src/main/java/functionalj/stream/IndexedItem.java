package functionalj.stream;

import functionalj.tuple.IntTuple2;

/**
 * An item with index attached to it.
 * 
 * @param <T>  the type of the item.
 */
public class IndexedItem<T> extends IntTuple2<T> {
    
    public IndexedItem(int index, T data) {
        super(index, data);
    }
    
    /** @return  the index. */
    public final int index() {
        return _1();
    }
    
    /** @return  the item value. */
    public final T item() {
        return _2();
    }
    
}
