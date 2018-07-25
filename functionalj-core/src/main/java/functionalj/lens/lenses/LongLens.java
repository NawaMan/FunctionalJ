package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface LongLens<HOST> 
        extends
            LongAccess<HOST>,
            ComparableLens<HOST, Long> {
    
    public static <HOST> LongLens<HOST> of(LensSpec<HOST, Long> spec) {
        return () -> spec;
    }
    
    @Override
    default Long apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }

    // TODO - add, subscript ...
    
//    public long add(long augend);
//    public double add(double augend);
//    public long subtract(long subtrahend);
//    public double subtract(double subtrahend);
//    public long multiply(long multiplicand);
//    public double multiply(double multiplicand);
//    public long divide(long divisor);
//    public double divide(double divisor);
//    public long remainder(long divisor);
//    public double remainder(double divisor);
//    public long[] divideAndRemainder(long divisor); --  Tuple
//    public long pow(long n);
//    public long abs();
//    public long negate();
//    public long plus();
//    public long signum();
//    public long min(long val);
//    public double min(double val);
//    public long max(long val);
//    public double max(double val);
//    public BigInteger toBigInteger();
//    public BigDecimal toBigDecimal();
//    public long longValue();
//    public double doubleValue();
}
