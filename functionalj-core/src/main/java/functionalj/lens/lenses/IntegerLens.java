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
    
}
