package functionalj.list;

import static functionalj.list.FuncList.deriveFrom;

import java.util.function.Function;

import functionalj.stream.AsStreamable;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface FuncListWithMapToTuple<DATA> extends AsStreamable<DATA> {
    
    /**Map the value into different values and then combine them into a tuple. */
    public default <T1, T2> FuncList<Tuple2<T1, T2>> mapToTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return deriveFrom(this, stream -> stream.mapToTuple(mapper1, mapper2));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3> FuncList<Tuple3<T1, T2, T3>> mapToTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return deriveFrom(this, stream -> stream.mapToTuple(mapper1, mapper2, mapper3));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4> FuncList<Tuple4<T1, T2, T3, T4>> mapToTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return deriveFrom(this, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4, T5> FuncList<Tuple5<T1, T2, T3, T4, T5>> mapToTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return deriveFrom(this, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4, T5, T6> FuncList<Tuple6<T1, T2, T3, T4, T5, T6>> mapToTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return deriveFrom(this, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
}
