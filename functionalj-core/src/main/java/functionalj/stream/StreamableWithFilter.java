package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.Collection;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import functionalj.function.IntObjBiFunction;

public interface StreamableWithFilter<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<DATA> filterAsInt(
            ToIntFunction<? super DATA> mapper, 
            IntPredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsInt(mapper, predicate));
    }
    
    public default Streamable<DATA> filterAsLong(
            ToLongFunction<? super DATA> mapper, 
            LongPredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsLong(mapper, predicate));
    }
    
    public default Streamable<DATA> filterAsDouble(
            ToDoubleFunction<? super DATA> mapper, 
            DoublePredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsDouble(mapper, predicate));
    }
    
    public default <T> Streamable<DATA> filterAsObject(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(mapper, predicate);
    }
    
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
