package functionalj.lens;

import java.util.function.ToIntFunction;

@FunctionalInterface
public interface IntegerAccess<HOST> extends NumberAccess<HOST, Integer>, ToIntFunction<HOST> {
    
    public default int applyAsInt(HOST host) {
        return apply(host).intValue();
    }
    
}
