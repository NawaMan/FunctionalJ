package functionalj.list.intlist;

import java.util.function.IntFunction;

import functionalj.list.FuncList;
import functionalj.stream.Streamable;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamableWithMapFirst;


public interface IntFuncListWithMapFirst 
                    extends IntStreamableWithMapFirst {
    
    public IntStreamPlus intStream();
    
    public <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> streamable);
    
    //== mapFirst ==
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper) {
        return deriveToList(() -> intStream().mapFirst(mapper));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return deriveToList(() -> intStream().mapFirst(mapper1, mapper2));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3) {
        return deriveToList(() -> intStream().mapFirst(mapper1, mapper2, mapper3));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4) {
        return deriveToList(() -> intStream().mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5) {
        return deriveToList(() -> intStream().mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    public default <T> FuncList<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5,
            IntFunction<T> mapper6) {
        return deriveToList(() -> intStream().mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
}
