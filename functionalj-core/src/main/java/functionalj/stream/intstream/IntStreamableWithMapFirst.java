package functionalj.stream.intstream;

import java.util.function.IntFunction;

import functionalj.stream.Streamable;

public interface IntStreamableWithMapFirst {
    
    public IntStreamPlus intStream();
    
    //== mapFirst ==
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper) {
        return ()->{
            return intStream()
                    .mapFirst(mapper);
        };
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return ()->{
            return intStream()
                    .mapFirst(mapper1, mapper2);
        };
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3) {
        return ()->{
            return intStream()
                    .mapFirst(mapper1, mapper2, mapper3);
        };
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4) {
        return ()->{
            return intStream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4);
        };
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5) {
        return ()->{
            return intStream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5);
        };
    }
    
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5,
            IntFunction<T> mapper6) {
        return ()->{
            return intStream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
        };
    }
}
