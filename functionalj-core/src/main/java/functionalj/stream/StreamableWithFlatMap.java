package functionalj.stream;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.stream.doublestream.DoubleStreamable;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.longstream.LongStreamable;

public interface StreamableWithFlatMap<DATA> extends AsStreamable<DATA> {
    
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return flatMapToObj(mapper);
    }
    
    public default IntStreamable flatMapToInt(Function<? super DATA, ? extends IntStreamable> mapper) {
        return IntStreamable.deriveFrom(this, stream -> stream.flatMapToInt(value -> mapper.apply(value).intStream()));
    }
    
    public default LongStreamable flatMapToLong(Function<? super DATA, ? extends LongStreamable> mapper) {
        return LongStreamable.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
    }
    
    public default DoubleStreamable flatMapToDouble(Function<? super DATA, ? extends DoubleStreamable> mapper) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    public default <T> Streamable<T> flatMapToObj(Function<? super DATA, ? extends Streamable<? extends T>> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).stream()));
    }
    
    public default Streamable<DATA> flatMapOnly(
            Predicate<? super DATA>                            checker, 
            Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return Streamable.deriveFrom(this, stream -> {
            Function<? super DATA, ? extends Stream<DATA>> newMapper = value -> mapper.apply(value).stream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA>                         checker, 
            Function<? super DATA, ? extends Streamable<T>> mapper, 
            Function<? super DATA, ? extends Streamable<T>> elseMapper) {
        return Streamable.deriveFrom(this, stream -> {
            Function<? super DATA, Stream<T>> newMapper     = value -> mapper.apply(value).stream();
            Function<? super DATA, Stream<T>> newElseMapper = value -> elseMapper.apply(value).stream();
            return stream.flatMapIf(checker, newMapper, newElseMapper);
        });
    }
    
}
