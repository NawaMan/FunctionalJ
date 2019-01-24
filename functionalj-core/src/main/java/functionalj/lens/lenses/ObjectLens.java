package functionalj.lens.lenses;

import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;


class ObjectLensHelper {
    static <HOST, DATA> HOST performChange(ObjectLens<HOST, DATA> lens, DATA data, HOST host) {
        return lens
                .lensSpec()
                .getWrite()
                .apply(host, data);
    }
}

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ObjectLens<HOST, DATA> extends AnyLens<HOST, DATA>, ObjectAccess<HOST, DATA> {
    
    public static <H, D> ObjectLens<H, D> of(LensSpec<H, D> spec) {
        return () -> spec;
    }
    
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA applyUnsafe(HOST host) throws Exception{
        return lensSpec().getRead().apply(host);
    }
    public default Func1<HOST, HOST> changeTo(DATA data) {
        return host -> ObjectLensHelper.performChange(this, data, host);
    }
    
}
