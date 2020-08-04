package functionalj.list.intlist;

import functionalj.stream.intstream.IntStreamableWithMapToTuple;


public interface IntFuncListWithMapTuple 
                    extends IntStreamableWithMapToTuple {
    
    // public IntStreamPlus intStream();
    
    // public <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> streamable);
    
    // //== mapTuple ==
    
    // public default <T1, T2> 
    //         FuncList<Tuple2<T1, T2>> mapTuple(
    //             IntFunction<? extends T1> mapper1,
    //             IntFunction<? extends T2> mapper2) {
    //     return deriveToList(() -> intStream().mapTuple(mapper1, mapper2));
    // }
    
    // public default <T1, T2, T3> 
    //         FuncList<Tuple3<T1, T2, T3>> mapTuple(
    //             IntFunction<? extends T1> mapper1,
    //             IntFunction<? extends T2> mapper2,
    //             IntFunction<? extends T3> mapper3) {
    //     return deriveToList(() -> intStream().mapTuple(mapper1, mapper2, mapper3));
    // }
    
    // public default <T1, T2, T3, T4> 
    //         FuncList<Tuple4<T1, T2, T3, T4>> mapTuple(
    //             IntFunction<? extends T1> mapper1,
    //             IntFunction<? extends T2> mapper2,
    //             IntFunction<? extends T3> mapper3,
    //             IntFunction<? extends T4> mapper4) {
    //     return deriveToList(() -> intStream().mapTuple(mapper1, mapper2, mapper3, mapper4));
    // }
    
    // public default <T1, T2, T3, T4, T5> 
    //         FuncList<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
    //             IntFunction<? extends T1> mapper1,
    //             IntFunction<? extends T2> mapper2,
    //             IntFunction<? extends T3> mapper3,
    //             IntFunction<? extends T4> mapper4,
    //             IntFunction<? extends T5> mapper5) {
    //     return deriveToList(() -> intStream().mapTuple(mapper1, mapper2, mapper3, mapper4, mapper5));
    // }
    
    // public default <T1, T2, T3, T4, T5, T6> 
    //         FuncList<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
    //             IntFunction<? extends T1> mapper1,
    //             IntFunction<? extends T2> mapper2,
    //             IntFunction<? extends T3> mapper3,
    //             IntFunction<? extends T4> mapper4,
    //             IntFunction<? extends T5> mapper5,
    //             IntFunction<? extends T6> mapper6) {
    //     return deriveToList(() -> intStream().mapTuple(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    // }
    
}
