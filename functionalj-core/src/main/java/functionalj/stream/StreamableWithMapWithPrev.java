package functionalj.stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.result.Result;
import functionalj.tuple.Tuple2;

public interface StreamableWithMapWithPrev<DATA> {

    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public default <TARGET> Streamable<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithPrev(mapper);
        });
    }
    
    public default Streamable<Tuple2<? super Result<DATA>, ? super DATA>> mapWithPrev() {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapWithPrev();
        });
    }
    
}
