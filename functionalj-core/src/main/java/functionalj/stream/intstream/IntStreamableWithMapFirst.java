package functionalj.stream.intstream;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.stream.Streamable;

public interface IntStreamableWithMapFirst {
    
    public IntStreamable intStreamable();
    
    //== mapCase ==
    
    public default <T> Streamable<T> mapToObjCase(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return Streamable.from(
                intStreamable(), 
                streamable -> 
                    streamable
                    .stream()
                    .mapFirst(mapper1, mapper2));
    }
//    
//    public default <T> Streamable<T> mapToObjCase(
//            IntFunction<T> mapper1,
//            IntFunction<T> mapper2,
//            IntFunction<T> mapper3) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapFirst(mapper1, mapper2, mapper3));
//    }
//    
//    public default <T> Streamable<T> mapToObjCase(
//            IntFunction<T> mapper1,
//            IntFunction<T> mapper2,
//            IntFunction<T> mapper3,
//            IntFunction<T> mapper4) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapFirst(mapper1, mapper2, mapper3, mapper4));
//    }
//    
//    public default <T> Streamable<T> mapToObjCase(
//            IntFunction<T> mapper1,
//            IntFunction<T> mapper2,
//            IntFunction<T> mapper3,
//            IntFunction<T> mapper4,
//            IntFunction<T> mapper5) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
//    }
//    
//    public default <T> Streamable<T> mapToObjCase(
//            IntFunction<T> mapper1,
//            IntFunction<T> mapper2,
//            IntFunction<T> mapper3,
//            IntFunction<T> mapper4,
//            IntFunction<T> mapper5,
//            IntFunction<T> mapper6) {
//        return deriveFrom(stream -> 
//                IntStreamPlus
//                    .from(stream)
//                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
//    }
}
