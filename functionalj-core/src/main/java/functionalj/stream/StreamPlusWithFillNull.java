package functionalj.stream;

import static functionalj.stream.StreamPlusHelper.derive;

import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.AnyLens;
import lombok.val;

public interface StreamPlusWithFillNull<DATA> extends AsStreamPlus<DATA> {
    
    //== fillNull ==
    
    public default StreamPlus<DATA> fillNull(DATA replacement) {
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
            return stream.map(value -> value == null ? replacement : value);
        });
    }
    
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
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
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
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
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
            Func1<DATA, VALUE>   replacementFunction) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementFunction);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Func1<DATA, VALUE>       replacementFunction) {
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
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
