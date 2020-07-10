package functionalj.stream.intstream;

import java.util.function.IntFunction;

import functionalj.function.Func1;
import functionalj.stream.Streamable;

public interface IntStreamableWithMapFirst {
    
    public IntStreamPlus intStream();
    
    public IntStreamable derive(Func1<IntStreamable, IntStreamable> action);
    
    public IntStreamable deriveToInt(Func1<IntStreamable, IntStreamable> action);
    
    public <TARGET> Streamable<TARGET> deriveToObj(Func1<IntStreamable, Streamable<TARGET>> action);
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     * 
     * @param <T>      the target type.
     * @param mapper1  the first mapper.
     * @param mapper2  the second mapper.
     * @return         the result of the first map result that is not null.
     */
    public default <T> Streamable<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return deriveToObj(source -> {
            return () -> {
                return source.asStreamPlus().mapFirst(mapper1, mapper2);
            };
        });
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
