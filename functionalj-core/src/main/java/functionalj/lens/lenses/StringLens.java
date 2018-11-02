package functionalj.lens.lenses;

import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StringLens<HOST>
        extends
            StringAccess<HOST>,
            AnyLens<HOST, String> {
    
    public static <HOST> StringLens<HOST> of(LensSpec<HOST, String> spec) {
        return () -> spec;
    }
    
    
    @Override
    default String apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    @Override
    default HOST apply(HOST host, String data) {
        return lensSpec().getWrite().apply(host, data);
    }
    
    default String read(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    default Func1<HOST, HOST> changeTo(String data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    // Add the chagne to that is supplier and function1
    
}
