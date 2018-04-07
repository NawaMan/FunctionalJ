package nawaman.functionalj.lens;

import nawaman.functionalj.functions.Func1;

@FunctionalInterface
public interface StringLens<HOST> extends AnyLens<HOST, String>, StringAccess<HOST> {
    
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
    
}
