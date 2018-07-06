package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyLens<HOST, TYPE>, NumberAccess<HOST, TYPE> {
    
    static <T extends Comparable<T>> ComparableLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    @Override
    default TYPE apply(HOST host) {
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
    
    // toInt, toLong, toBiginteger ...
}
