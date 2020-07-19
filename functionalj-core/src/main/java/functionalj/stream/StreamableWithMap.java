package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamableWithMap<DATA> extends AsStreamable<DATA> {

    
    public default <T> Streamable<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    public default Streamable<DATA> mapOnly(
            Predicate<? super DATA>      checker, 
            Function<? super DATA, DATA> mapper) {
        return deriveFrom(this, stream -> stream.mapOnly(checker, mapper));
    }
    
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return deriveFrom(this, stream -> stream.mapIf(checker, mapper, elseMapper));
    }
}
