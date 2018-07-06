package functionalj.lens.lenses;

import java.math.BigInteger;
import java.util.function.Function;

@FunctionalInterface
public interface BigIntegerAccess<HOST> 
        extends 
            NumberAccess<HOST, BigInteger>, 
            ConcreteAccess<HOST, BigInteger, BigIntegerAccess<HOST>> {
    
    @Override
    public default BigIntegerAccess<HOST> newAccess(Function<HOST, BigInteger> access) {
        return access::apply;
    }
    
}
