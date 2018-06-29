package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.val;

@FunctionalInterface
public interface AnyLens<HOST, DATA> extends AnyAccess<HOST, DATA>, WriteLens<HOST, DATA> {

    public static <T> AnyLens<T, T> of(LensSpec<T, T> spec) {
        return () -> spec;
    }
    
    
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA apply(HOST host) {
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
        return host -> {
            return apply(host, data);
        };
    }
    default Function<HOST, HOST> changeTo(Supplier<DATA> dataSupplier) {
        return host -> {
            val newValue = dataSupplier.get();
            return apply(host, newValue);
        };
    }
    default Function<HOST, HOST> changeTo(Function<DATA, DATA> dataMapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = dataMapper.apply(oldValue);
            return apply(host, newValue);
        };
    }
    default Function<HOST, HOST> changeTo(BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = mapper.apply(host, oldValue);
            return apply(host, newValue);
        };
    }
    
}
