package functionalj.lens.lenses;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface DoubleAccess<HOST> extends NumberAccess<HOST, Double>, ToDoubleFunction<HOST> {
    
    public default double applyAsDouble(HOST host) {
        return apply(host).doubleValue();
    }
    
}
