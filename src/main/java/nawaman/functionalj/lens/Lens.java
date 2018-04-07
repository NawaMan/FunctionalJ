package nawaman.functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

@FunctionalInterface
public interface Lens<HOST, DATA> extends Function<HOST, DATA>, WriteLens<HOST, DATA> {
    
    public static <HOST, DATA> Lens<HOST, DATA> of(LensSpec<HOST, DATA> spec) {
        return ()->spec;
    }
    
    
    public LensSpec<HOST, DATA> lensSpec();
    
    
    @Override
    default DATA apply(HOST host) {
        val spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        val read  = spec.getRead();
        val value = read.apply(host);
        return value;
    }
    
    @Override
    default HOST apply(HOST host, DATA data) {
        val spec  = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        
        val write    = spec.getWrite();
        val newValue = write.apply(host, data);
        return newValue;
    }
    
    default DATA read(HOST host) {
        return apply(host);
    }
    default Function<HOST, HOST> changeTo(DATA data) {
        return host -> apply(host, data);
    }
    
}
