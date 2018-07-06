package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.ToLongFunction;

@FunctionalInterface
public interface LongAccess<HOST> 
        extends 
            NumberAccess<HOST, Long>, 
            ToLongFunction<HOST>,
            ConcreteAccess<HOST, Long, LongAccess<HOST>> {

    @Override
    public default LongAccess<HOST> newAccess(Function<HOST, Long> accessToValue) {
        return accessToValue::apply;
    }
    
    public default long applyAsLong(HOST host) {
        return apply(host).longValue();
    }
    
}
