package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.val;

public interface Lens {
    
    public static <HOST, DATA> AnyLens<HOST, DATA> of(LensSpec<HOST, DATA> spec) {
        return ()->spec;
    }
    public static <HOST, DATA> AnyLens<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        val spec = new LensSpec<HOST, DATA>(read::apply, write::apply);
        return ()->spec;
    }
    public static <HOST, DATA> AnyLens<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write, boolean isNullSafe) {
        val spec = LensSpec.of(read::apply, write::apply, isNullSafe);
        return ()->spec;
    }
    
}
