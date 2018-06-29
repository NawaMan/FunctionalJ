package functionalj.lens;

@FunctionalInterface
public interface AnyLens<HOST, DATA> extends Lens<HOST, DATA>, AnyAccess<HOST, DATA> {

    public static <T> AnyLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
