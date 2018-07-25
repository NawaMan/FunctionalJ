package functionalj.lens.lenses;

import java.math.BigInteger;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
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
    
}
