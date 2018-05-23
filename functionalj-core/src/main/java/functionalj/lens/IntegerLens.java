package functionalj.lens;

@FunctionalInterface
public interface IntegerLens<HOST> extends ComparableLens<HOST, Integer>, IntegerAccess<HOST> {
    
    public static <HOST> IntegerLens<HOST> of(LensSpec<HOST, Integer> spec) {
        return () -> spec;
    }
    
    @Override
    default Integer apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
