package functionalj.stream;

import java.util.Comparator;
import java.util.function.Function;

public interface StreamableWithSort<DATA> extends AsStreamable<DATA> {
    
    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.sortedBy(mapper));
    }
    
    public default <T> Streamable<DATA> sortedBy(
            Function<? super DATA, T> mapper, 
            Comparator<T>             comparator) {
        return Streamable.deriveFrom(this, stream -> stream.sortedBy(mapper, comparator));
    }
    
}
