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
    
}
