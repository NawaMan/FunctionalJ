package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface DoubleAccess<HOST> 
        extends 
            NumberAccess<HOST, Double>, 
            ToDoubleFunction<HOST>, 
            ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> access) {
        return access::apply;
    }
    
    public default double applyAsDouble(HOST host) {
        return apply(host).doubleValue();
    }
    
}
