package functionalj.list.intlist;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.list.FuncList;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.intstream.IntStreamableWithMapThen;

public interface IntFuncListWithMapThen 
        extends IntStreamableWithMapThen {
    
    public <TARGET> FuncList<TARGET> deriveToList(Function<IntStreamable, Stream<TARGET>> action);
    
    //== mapThen ==
    
    public default <T1, T2, T> 
            FuncList<T> mapThen(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return deriveToList(streamable -> {
            return streamable
                    .stream()
                    .mapThen(mapper1, mapper2, function);
        });
    }
    
    public default <T1, T2, T3, T> 
            FuncList<T> mapThen(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return deriveToList(streamable -> {
            return streamable
                    .stream()
                    .mapThen(mapper1, mapper2, mapper3, function);
        });
    }
    public default <T1, T2, T3, T4, T> 
            FuncList<T> mapThen(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return deriveToList(streamable -> {
            return streamable
                    .stream()
                    .mapThen(mapper1, mapper2, mapper3, mapper4, function);
        });
    }
    public default <T1, T2, T3, T4, T5, T> 
        FuncList<T> mapThen(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4,
                IntFunction<? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return deriveToList(streamable -> {
            return streamable
                    .stream()
                    .mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, function);
        });
    }
    public default <T1, T2, T3, T4, T5, T6, T> 
        FuncList<T> mapThen(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4,
                IntFunction<? extends T5> mapper5,
                IntFunction<? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return deriveToList(streamable -> {
            return streamable
                    .stream()
                    .mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, function);
        });
    }
}
