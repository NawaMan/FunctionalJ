package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.function.BiFunction;

import functionalj.result.Result;
import functionalj.tuple.Tuple2;

public interface StreamableWithMapWithPrev<DATA> extends AsStreamable<DATA> {
    
    public default <TARGET> Streamable<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.mapWithPrev(mapper));
    }
    
    public default Streamable<Tuple2<? super Result<DATA>, ? super DATA>> mapWithPrev() {
        return deriveFrom(this, stream -> stream.mapWithPrev());
    }
    
}
