package functionalj.stream;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.function.IntObjBiFunction;

public interface StreamableWithFilter<DATA> {

    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public default Streamable<DATA> filterIn(
            Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filterIn(collection);
        });
    }
    
    public default Streamable<DATA> exclude(
            Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .exclude(predicate);
        });
    }
    
    public default Streamable<DATA> excludeIn(
            Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .excludeIn(collection);
        });
    }
    
    public default <T> Streamable<DATA> filter(
            Class<T> clzz) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(clzz);
        });
    }
    
    public default <T> Streamable<DATA> filter(
            Class<T>             clzz, 
            Predicate<? super T> theCondition) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(clzz, theCondition);
        });
    }
    
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filter(mapper, theCondition);
        });
    }
    
    public default Streamable<DATA> filterWithIndex(
            IntObjBiFunction<? super DATA, Boolean> predicate) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .filterWithIndex(predicate);
        });
    }
    
    public default Streamable<DATA> filterNonNull() {
        return deriveWith(stream -> {
            return stream.filter(Objects::nonNull);
        });
    }
    
}
