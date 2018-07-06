package functionalj.lens.lenses;

import java.math.BigInteger;

import functionalj.lens.core.LensSpec;

@FunctionalInterface
public interface BigIntegerLens<HOST> 
        extends
            BigIntegerAccess<HOST>,
            ComparableLens<HOST, BigInteger> {
    
    public static <HOST> BigIntegerLens<HOST> of(LensSpec<HOST, BigInteger> spec) {
        return () -> spec;
    }
    
    @Override
    public default BigInteger apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }

    // TODO - add, subscript ...
    
//    public BigInteger add(BigInteger augend);
//    public BigInteger subtract(BigInteger subtrahend);
//    public BigInteger multiply(BigInteger multiplicand);
//    public BigInteger divide(BigInteger divisor);
//    public BigInteger remainder(BigInteger divisor);
//    public BigInteger[] divideAndRemainder(BigInteger divisor); --  Tuple
//    public BigInteger pow(BigInteger n);
//    public BigInteger abs();
//    public BigInteger negate();
//    public BigInteger plus();
//    public BigInteger signum();
//    public BigInteger min(BigInteger val);
//    public BigInteger max(BigInteger val);
//    public BigInteger toBigInteger();
//    public BigDecimal toBigDecimal();
    
}
