package nawaman.functionalj.lens;

@FunctionalInterface
public interface AnyEqualableLens<HOST, DATA> extends AnyLens<HOST, DATA>, AnyEqualableAccess<HOST, DATA> {

    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
