package functionalj.stream.intstream;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.stream.Streamable;

public interface IntStreamableWithMapFirst {
    
    public <TARGET> Streamable<TARGET> deriveFrom(Function<IntStream, Stream<TARGET>> action);
    
    
    //== mapFirst ==
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .mapFirst(mapper1, mapper2));
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .mapFirst(mapper1, mapper2));
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .mapFirst(mapper1, mapper2));
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .mapFirst(mapper1, mapper2));
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5,
            IntFunction<T> mapper6) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .mapFirst(mapper1, mapper2));
    }
}
