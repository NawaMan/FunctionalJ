package functionalj.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamableWithMatch<DATA> {
    
    public StreamPlus<DATA> stream();
    
    public default <T> Optional<DATA> findFirst(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return stream()
                .findFirst(mapper, theCondition);
    }

    public default <T>  Optional<DATA> findAny(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return stream()
                .findAny(mapper, theCondition);
    }
    
    public default Optional<DATA> findFirst(
            Predicate<? super DATA> predicate) {
        return stream()
                .findFirst(predicate);
    }
    
    public default Optional<DATA> findAny(
            Predicate<? super DATA> predicate) {
        return stream()
                .findAny(predicate);
    }
    
}
