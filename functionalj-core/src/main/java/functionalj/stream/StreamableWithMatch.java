package functionalj.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamableWithMatch<DATA> extends AsStreamable<DATA> {
    
    public default <T> Optional<DATA> findFirst(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return streamPlus()
                .findFirst(mapper, theCondition);
    }
    
    public default <T>  Optional<DATA> findAny(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return streamPlus()
                .findAny(mapper, theCondition);
    }
    
    public default Optional<DATA> findFirst(
            Predicate<? super DATA> predicate) {
        return streamPlus()
                .findFirst(predicate);
    }
    
    public default Optional<DATA> findAny(
            Predicate<? super DATA> predicate) {
        return streamPlus()
                .findAny(predicate);
    }
    
}
