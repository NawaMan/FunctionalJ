package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface IntegerAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Integer>, 
                        ToIntFunction<HOST>,
                        ConcreteAccess<HOST, Integer, IntegerAccess<HOST>> {
    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    public default int applyAsInt(HOST host) {
        return apply(host).intValue();
    }
    
}
