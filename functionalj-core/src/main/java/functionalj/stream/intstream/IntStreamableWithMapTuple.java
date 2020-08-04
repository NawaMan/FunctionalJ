//package functionalj.stream.intstream;
//
//import java.util.function.IntFunction;
//
//import functionalj.stream.Streamable;
//import functionalj.tuple.Tuple2;
//import functionalj.tuple.Tuple3;
//import functionalj.tuple.Tuple4;
//import functionalj.tuple.Tuple5;
//import functionalj.tuple.Tuple6;
//
//public interface IntStreamableWithMapTuple {
//    
//    // public IntStreamPlus intStream();
//    
//    // //== mapTuple ==
//    
//    // public default <T1, T2> 
//    //     Streamable<Tuple2<T1, T2>> mapToTuple(
//    //             IntFunction<? extends T1> mapper1,
//    //             IntFunction<? extends T2> mapper2) {
//    //     return ()->{
//    //         return intStream()
//    //                 .mapTuple(mapper1, mapper2);
//    //     };
//    // }
//    
//    // public default <T1, T2, T3> 
//    //     Streamable<Tuple3<T1, T2, T3>> mapToTuple(
//    //             IntFunction<? extends T1> mapper1,
//    //             IntFunction<? extends T2> mapper2,
//    //             IntFunction<? extends T3> mapper3) {
//    //     return ()->{
//    //         return intStream()
//    //                 .mapTuple(mapper1, mapper2, mapper3);
//    //     };
//    // }
//    
//    // public default <T1, T2, T3, T4> 
//    //     Streamable<Tuple4<T1, T2, T3, T4>> mapToTuple(
//    //             IntFunction<? extends T1> mapper1,
//    //             IntFunction<? extends T2> mapper2,
//    //             IntFunction<? extends T3> mapper3,
//    //             IntFunction<? extends T4> mapper4) {
//    //     return ()->{
//    //         return intStream()
//    //                 .mapTuple(mapper1, mapper2, mapper3, mapper4);
//    //     };
//    // }
//    
//    // public default <T1, T2, T3, T4, T5> 
//    //     Streamable<Tuple5<T1, T2, T3, T4, T5>> mapToTuple(
//    //             IntFunction<? extends T1> mapper1,
//    //             IntFunction<? extends T2> mapper2,
//    //             IntFunction<? extends T3> mapper3,
//    //             IntFunction<? extends T4> mapper4,
//    //             IntFunction<? extends T5> mapper5) {
//    //     return ()->{
//    //         return intStream()
//    //                 .mapTuple(mapper1, mapper2, mapper3, mapper4, mapper5);
//    //     };
//    // }
//    // public default <T1, T2, T3, T4, T5, T6> 
//    //     Streamable<Tuple6<T1, T2, T3, T4, T5, T6>> mapToTuple(
//    //             IntFunction<? extends T1> mapper1,
//    //             IntFunction<? extends T2> mapper2,
//    //             IntFunction<? extends T3> mapper3,
//    //             IntFunction<? extends T4> mapper4,
//    //             IntFunction<? extends T5> mapper5,
//    //             IntFunction<? extends T6> mapper6) {
//    //     return ()->{
//    //         return intStream()
//    //                 .mapTuple(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
//    //     };
//    // }
//}
