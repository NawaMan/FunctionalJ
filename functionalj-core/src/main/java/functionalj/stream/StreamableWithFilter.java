package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.IntObjBiFunction;

public interface StreamableWithFilter<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveFrom(this, stream -> stream.filterIn(collection));
    }
    
    public default Streamable<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveFrom(this, stream -> stream.exclude(predicate));
    }
    
    public default Streamable<DATA> excludeIn(Collection<? super DATA> collection) {
        return deriveFrom(this, stream -> stream.excludeIn(collection));
    }
    
    public default <T> Streamable<DATA> filter(Class<T> clzz) {
        return deriveFrom(this, stream -> stream.filter(clzz));
    }
    
    public default <T> Streamable<DATA> filter(
            Class<T>             clzz, 
            Predicate<? super T> theCondition) {
        return deriveFrom(this, stream -> stream.filter(clzz, theCondition));
    }
    
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return deriveFrom(this, stream -> stream.filter(mapper, theCondition));
    }
    
    public default Streamable<DATA> filterWithIndex(IntObjBiFunction<? super DATA, Boolean> predicate) {
        return deriveFrom(this, stream -> stream.filterWithIndex(predicate));
    }
    
    public default Streamable<DATA> filterNonNull() {
        return deriveFrom(this, stream -> stream.filterNonNull());
    }
    
}
