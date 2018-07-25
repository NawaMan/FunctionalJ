package functionalj.lens.lenses;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyLens<HOST, TYPE> {
    
    static <T extends Comparable<T>> ComparableLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    @Override
    default TYPE apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
