package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.function.Function;

import functionalj.function.IntObjBiFunction;
import functionalj.tuple.IntTuple2;

public interface StreamableWithMapWithIndex<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<IntTuple2<DATA>> mapWithIndex() {
        return deriveFrom(this, stream -> stream.mapWithIndex());
    }
    
    public default <T> Streamable<T> mapWithIndex(IntObjBiFunction<? super DATA, T> mapper) {
        return deriveFrom(this, stream -> stream.mapWithIndex(mapper));
    }
    
    public default <T1, T> Streamable<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                IntObjBiFunction<? super T1, T>      mapper) {
        return deriveFrom(this, stream -> stream.mapWithIndex(mapper1, mapper));
    }
    
}
