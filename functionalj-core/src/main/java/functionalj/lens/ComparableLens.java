package functionalj.lens;

@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyLens<HOST, TYPE>, NumberAccess<HOST, TYPE> {
    
    @Override
    default TYPE apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
