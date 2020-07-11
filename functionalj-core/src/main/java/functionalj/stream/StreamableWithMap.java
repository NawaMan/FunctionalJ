package functionalj.stream;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.longstream.LongStreamable;

public interface StreamableWithMap<DATA> {
    
    public <TARGET> Streamable<TARGET> derive(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public IntStreamable deriveToInt(Func1<Streamable<DATA>, IntStreamable> action);
    
    public LongStreamable deriveToLong(Func1<Streamable<DATA>, LongStreamable> action);
    
    // TODO - Uncomment this.
//    public DoubleStreamable deriveToDouble(Func1<Streamable<DATA>, DoubleStreamable> action);
    
    public <TARGET> Streamable<TARGET> deriveToObj(Func1<Streamable<DATA>, Streamable<TARGET>> action);
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    //-- map --
    
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return derive(streamPlus -> streamPlus.map(mapper));
    }
    
    public default IntStreamable mapToInt(ToIntFunction<? super DATA> mapper) {
        return deriveToInt(stream -> {
            return stream.mapToInt(mapper);
        });
    }
    
    public default LongStreamable mapToLong(ToLongFunction<? super DATA> mapper) {
        return deriveToLong(stream -> {
            return stream.mapToLong(mapper);
        });
    }

    // TODO - Uncomment this.
//    public default DoubleStreamable mapToDouble(ToDoubleFunction<? super DATA> mapper) {
//        return deriveToDouble(stream -> {
//            return stream.mapToDouble(mapper);
//        });
//    }
    
    public default <T> Streamable<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return deriveToObj(stream -> {
            return stream.mapToObj(mapper);
        });
    }
    
    public default Streamable<DATA> mapOnly(
            Predicate<? super DATA>      checker, 
            Function<? super DATA, DATA> mapper) {
        return deriveWith(stream -> {
                return StreamPlus
                        .from(stream)
                        .mapOnly(checker, mapper);
            });
    }
    
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .mapIf(checker, mapper, elseMapper);
        });
    }
}
