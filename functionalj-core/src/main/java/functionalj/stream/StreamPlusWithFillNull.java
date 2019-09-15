package functionalj.stream;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.AnyLens;
import lombok.val;

public interface StreamPlusWithFillNull<DATA> {
    
    public <TARGET> StreamPlus<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action);
    
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            VALUE                replacement) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacement);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            VALUE                    replacement) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream
                    .map(orgElmt -> {
                        val value   = get.apply(orgElmt);
                        if (value == null) {
                            val newElmt = set.apply(orgElmt, replacement);
                            return (DATA)newElmt;
                        }
                        return orgElmt;
                    });
        });
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementSupplier);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Supplier<VALUE>          replacementSupplier) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream
                    .map(orgElmt -> {
                        val value   = get.apply(orgElmt);
                        if (value == null) {
                            val replacement = replacementSupplier.get();
                            val newElmt     = set.apply(orgElmt, replacement);
                            return (DATA)newElmt;
                        }
                        return orgElmt;
                    });
        });
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementSupplier) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementSupplier);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Func1<DATA, VALUE>       replacementFunction) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream
                    .map(orgElmt -> {
                        val value = get.apply(orgElmt);
                        if (value == null) {
                            val replacement = replacementFunction.apply(orgElmt);
                            val newElmt     = set.apply(orgElmt, replacement);
                            return (DATA)newElmt;
                        }
                        return orgElmt;
                    });
        });
    }
}
