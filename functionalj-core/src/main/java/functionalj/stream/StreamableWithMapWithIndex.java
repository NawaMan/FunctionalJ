package functionalj.stream;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.function.IntObjBiFunction;
import functionalj.tuple.IntTuple2;

public interface StreamableWithMapWithIndex<DATA> {

    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public default Streamable<IntTuple2<DATA>> mapWithIndex() {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex();
        });
    }
    
    public default <T> Streamable<T> mapWithIndex(
            IntObjBiFunction<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex(mapper);
        });
    }
    
    public default <T1, T> Streamable<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                IntObjBiFunction<? super T1, T> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithIndex(mapper1, mapper);
        });
    }
    
}
