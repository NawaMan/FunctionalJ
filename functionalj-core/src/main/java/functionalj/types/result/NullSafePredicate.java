package functionalj.types.result;

import java.util.function.Predicate;

public interface NullSafePredicate<DATA> extends Predicate<DATA> {

    public boolean test(DATA data);
    
    
    public static <D> NullSafePredicate<D> of(Predicate<D> predicate) {
        return predicate::test;
    }
    
}
