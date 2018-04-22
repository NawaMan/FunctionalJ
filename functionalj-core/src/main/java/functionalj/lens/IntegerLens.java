package functionalj.lens;

@FunctionalInterface
public interface IntegerLens<HOST> extends ComparableLens<HOST, Integer>, IntegerAccess<HOST> {
    
    @Override
    default Integer apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
