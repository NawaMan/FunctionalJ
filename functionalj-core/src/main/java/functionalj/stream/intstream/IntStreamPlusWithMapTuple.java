package functionalj.stream.intstream;

import java.util.function.IntFunction;

import functionalj.stream.StreamPlus;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface IntStreamPlusWithMapTuple extends IntStreamPlusWithMapThen {
    
    public <TARGET> StreamPlus<TARGET> mapToObj(
        IntFunction<? extends TARGET> mapper);
    
    //== mapTuple ==
    
    public default <T1, T2> 
        StreamPlus<Tuple2<T1, T2>> mapTuple(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        StreamPlus<Tuple3<T1, T2, T3>> mapTuple(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        StreamPlus<Tuple4<T1, T2, T3, T4>> mapTuple(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        StreamPlus<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4,
                IntFunction<? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        StreamPlus<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                IntFunction<? extends T1> mapper1,
                IntFunction<? extends T2> mapper2,
                IntFunction<? extends T3> mapper3,
                IntFunction<? extends T4> mapper4,
                IntFunction<? extends T5> mapper5,
                IntFunction<? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
}
