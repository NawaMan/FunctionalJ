package functionalj.lens.lenses;

import java.math.BigDecimal;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface BigDecimalLens<HOST> 
        extends
            BigDecimalAccess<HOST>,
            ComparableLens<HOST, BigDecimal> {
    
    public static <HOST> BigDecimalLens<HOST> of(LensSpec<HOST, BigDecimal> spec) {
        return () -> spec;
    }
    
    @Override
    default BigDecimal apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }

}
