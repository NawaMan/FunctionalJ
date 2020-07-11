package functionalj.stream;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.longstream.LongStreamable;

public interface StreamableWithFlatMap<DATA> {

    public <TARGET> Streamable<TARGET> derive(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public IntStreamable deriveToInt(Func1<Streamable<DATA>, IntStreamable> action);
    
    public LongStreamable deriveToLong(Func1<Streamable<DATA>, LongStreamable> action);
    
    // TODO - Uncomment this.
//    public DoubleStreamable deriveToDouble(Func1<Streamable<DATA>, DoubleStreamable> action);
    
    public <TARGET> Streamable<TARGET> deriveToObj(Func1<Streamable<DATA>, Streamable<TARGET>> action);
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return derive(stream -> {
            return stream.flatMap(value -> mapper.apply(value).stream());
        });
    }
    
    public default IntStreamable flatMapToInt(Function<? super DATA, ? extends IntStreamable> mapper) {
        return deriveToInt(stream -> {
            return stream.flatMapToInt(mapper);
        });
    }
    
    public default LongStreamable flatMapToLong(Function<? super DATA, ? extends LongStreamable> mapper) {
        return deriveToLong(stream -> {
            return stream.flatMapToLong(mapper);
        });
    }
//    
//    public default DoubleStreamable flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
//        return deriveToDouble(stream -> {
//            return stream.flatMapToDouble(mapper);
//        });
//    }
    
    public default <T> Streamable<T> flatMapToObj(Function<? super DATA, ? extends Streamable<? extends T>> mapper) {
        return deriveToObj(stream -> {
            return stream.flatMapToObj(mapper);
        });
    }
    
    public default Streamable<DATA> flatMapOnly(
            Predicate<? super DATA>                            checker, 
            Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return deriveToObj(stream -> {
            return stream.flatMapOnly(checker, mapper);
        });
    }
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA>                         checker, 
            Function<? super DATA, ? extends Streamable<T>> mapper, 
            Function<? super DATA, ? extends Streamable<T>> elseMapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .flatMapIf(
                            checker, 
                            d -> ((Streamable<T>)mapper    .apply(d)).stream(), 
                            d -> ((Streamable<T>)elseMapper.apply(d)).stream());
        });
    }
    
}
