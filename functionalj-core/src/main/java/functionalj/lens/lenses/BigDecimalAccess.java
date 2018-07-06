package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.util.function.Function;

@FunctionalInterface
public interface BigDecimalAccess<HOST> 
                    extends 
                        NumberAccess<HOST, BigDecimal>, 
                        ConcreteAccess<HOST, BigDecimal, BigDecimalAccess<HOST>> {

    @Override
    public default BigDecimalAccess<HOST> newAccess(Function<HOST, BigDecimal> access) {
        return access::apply;
    }
    
    
    // TODO - add, subscript ...
    
//    public BigDecimal add(BigDecimal augend);
//    public BigDecimal subtract(BigDecimal subtrahend);
//    public BigDecimal multiply(BigDecimal multiplicand);
//    public BigDecimal divide(BigDecimal divisor);
//    public BigDecimal remainder(BigDecimal divisor);
//    public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple
//    public BigDecimal pow(BigDecimal n);
//    public BigDecimal abs();
//    public BigDecimal negate();
//    public BigDecimal plus();
//    public BigDecimal signum();
//    public BigDecimal min(BigDecimal val);
//    public BigDecimal max(BigDecimal val);
//    public BigDecimal toBigDecimal();
    
}
