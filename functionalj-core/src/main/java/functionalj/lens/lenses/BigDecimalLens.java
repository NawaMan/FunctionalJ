package functionalj.lens.lenses;

import java.math.BigDecimal;

import functionalj.lens.core.LensSpec;

@FunctionalInterface
public interface BigDecimalLens<HOST> extends ComparableLens<HOST, BigDecimal>, BigDecimalAccess<HOST> {
    
    public static <HOST> BigDecimalLens<HOST> of(LensSpec<HOST, BigDecimal> spec) {
        return () -> spec;
    }
    
    @Override
    default BigDecimal apply(HOST host) {
        return lensSpec().getRead().apply(host);
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
