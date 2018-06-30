package functionalj.lens.lenses;

import java.util.function.ToLongFunction;

@FunctionalInterface
public interface LongAccess<HOST> extends NumberAccess<HOST, Long>, ToLongFunction<HOST> {
    
    public default long applyAsLong(HOST host) {
        return apply(host).longValue();
    }
    
}
