package nawaman.functionalj.lens;

@FunctionalInterface
public interface ComparableLens<HOST, TYPE extends Comparable<TYPE>> extends AnyEqualableLens<HOST, TYPE>, ComparableAccess<HOST, TYPE> {
    
    @Override
    default TYPE apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
