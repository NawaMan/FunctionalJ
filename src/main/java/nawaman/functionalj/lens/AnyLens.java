package nawaman.functionalj.lens;

@FunctionalInterface
public interface AnyLens<HOST, DATA> extends Lens<HOST, DATA>, AnyAccess<HOST, DATA> {
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
