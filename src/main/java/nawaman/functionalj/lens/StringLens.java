package nawaman.functionalj.lens;

import java.util.function.Function;

import nawaman.functionalj.functions.Func1;

@FunctionalInterface
public interface StringLens<HOST> extends AnyEqualableLens<HOST, String>, StringAccess<HOST> {

    public default BooleanAccess<HOST> isBlank() {
        return booleanAccess(true, str->str.trim().isEmpty());
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
    default Func1<HOST, HOST> to(String data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    
}
