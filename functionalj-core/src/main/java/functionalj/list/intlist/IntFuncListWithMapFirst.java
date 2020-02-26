package functionalj.list.intlist;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.intstream.IntStreamableWithMapFirst;


public interface IntFuncListWithMapFirst extends IntStreamableWithMapFirst {
    
    public <TARGET> FuncList<TARGET> deriveToList(Function<IntStreamable, Stream<TARGET>> action);
    
    //== mapCase ==
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return deriveToList(streamable -> 
                streamable
                    .stream()
                    .mapFirst(mapper1, mapper2));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3) {
        return deriveToList(streamable -> 
                streamable
                    .stream()
                    .mapFirst(mapper1, mapper2, mapper3));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4) {
        return deriveToList(streamable -> 
                streamable
                    .stream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5) {
        return deriveToList(streamable -> 
                streamable
                    .stream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5,
            IntFunction<T> mapper6) {
        return deriveToList(streamable -> 
                streamable
                    .stream()
                    .mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
}
