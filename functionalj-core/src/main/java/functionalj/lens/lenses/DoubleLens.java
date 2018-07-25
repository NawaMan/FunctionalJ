package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface DoubleLens<HOST>
        extends
            DoubleAccess<HOST>,
            ComparableLens<HOST, Double> {
    
    public static <HOST> DoubleLens<HOST> of(LensSpec<HOST, Double> spec) {
        return () -> spec;
    }
    
    @Override
    default Double apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }

    // TODO - add, subscript ...
    
//    public double add(double augend);
//    public double subtract(double subtrahend);
//    public double multiply(double multiplicand);
//    public double divide(double divisor);
//    public double remainder(double divisor);
//    public double[] divideAndRemainder(double divisor); --  Tuple
//    public double pow(double n);
//    public double abs();
//    public double negate();
//    public double plus();
//    public double signum();
//    public double min(double val);
//    public double max(double val);
//    public BigInteger toBigInteger();
//    public BigDecimal toBigDecimal();
//    public double doubleValue();
    
}
