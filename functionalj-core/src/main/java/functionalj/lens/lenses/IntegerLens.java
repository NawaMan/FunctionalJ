package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface IntegerLens<HOST> 
        extends 
            IntegerAccess<HOST>,
            ComparableLens<HOST, Integer> {
    
    
    public static <HOST> IntegerLens<HOST> of(LensSpec<HOST, Integer> spec) {
        return () -> spec;
    }
    
    @Override
    default Integer apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    // TODO - add, subscript ...
    
//    public int add(int augend);
//    public long add(long augend);
//    public double add(double augend);
//    public int subtract(int subtrahend);
//    public long subtract(long subtrahend);
//    public double subtract(double subtrahend);
//    public int multiply(int multiplicand);
//    public long multiply(long multiplicand);
//    public double multiply(double multiplicand);
//    public int divide(int divisor);
//    public long divide(long divisor);
//    public double divide(double divisor);
//    public int remainder(int divisor);
//    public long remainder(long divisor);
//    public double remainder(double divisor);
//    public int[] divideAndRemainder(int divisor);
//    public long pow(int n);
//    public int abs();
//    public int negate();
//    public int plus();
//    public int signum();
//    public int min(int val);
//    public long min(long val);
//    public double min(double val);
//    public int max(int val);
//    public long max(long val);
//    public double max(double val);
//    public BigInteger toBigInteger();
//    public BigDecimal toBigDecimal();
//    public long longValue();
//    public int intValue();
//    public double doubleValue();
    
}
