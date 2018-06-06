package functionalj.lens;

import java.math.BigInteger;

@FunctionalInterface
public interface BigIntegerLens<HOST> extends ComparableLens<HOST, BigInteger>, BigIntegerAccess<HOST> {
    
    public static <HOST> BigIntegerLens<HOST> of(LensSpec<HOST, BigInteger> spec) {
        return () -> spec;
    }
    
    @Override
    default BigInteger apply(HOST host) {
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
