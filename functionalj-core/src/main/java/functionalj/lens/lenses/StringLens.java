package functionalj.lens.lenses;

import functionalj.functions.Func1;
import functionalj.lens.core.LensSpec;

@FunctionalInterface
public interface StringLens<HOST> extends AnyLens<HOST, String>, StringAccess<HOST> {
    
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
