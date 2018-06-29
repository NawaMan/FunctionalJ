package functionalj.lens;

@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyLens<HOST, TYPE>, NumberAccess<HOST, TYPE> {
    
    static <T extends Comparable<T>> ComparableLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    @Override
    default TYPE apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
