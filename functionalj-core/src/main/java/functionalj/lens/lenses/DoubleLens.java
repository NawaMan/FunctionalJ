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
    
}
