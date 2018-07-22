package functionalj.types.result;

import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Acceptable<DATA> extends ImmutableResult<DATA> {
    
    protected Acceptable(DATA value, Predicate<DATA> checker) {
        super(((Supplier<DATA>)()->{
                    if (value == null)
                        return null;
                    
                    if (!checker.test(value))
                        return null;
                    
                    return value;
                }).get(),
                null);
    }
    
}
