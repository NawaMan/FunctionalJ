package nawaman.functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Lens<HOST, DATA> extends Function<HOST, DATA>, BiFunction<HOST, DATA, HOST> {
    
    public static <HOST, DATA> Lens<HOST, DATA> of(LensSpec<HOST, DATA> spec) {
        return ()->spec;
    }
    
    
    public LensSpec<HOST, DATA> lensSpec();
    
    
    @Override
    default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    @Override
    default HOST apply(HOST host, DATA data) {
        return lensSpec().getWrite().apply(host, data);
    }
    
    default DATA read(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    default Function<HOST, HOST> to(DATA data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    
}
