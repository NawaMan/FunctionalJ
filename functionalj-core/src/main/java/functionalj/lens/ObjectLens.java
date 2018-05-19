package functionalj.lens;

import functionalj.functions.Func1;

@FunctionalInterface
public interface ObjectLens<HOST, DATA> extends AnyLens<HOST, DATA>, ObjectAccess<HOST, DATA> {
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    public default Func1<HOST, HOST> changeTo(DATA data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    
}
