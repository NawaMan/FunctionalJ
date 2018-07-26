package functionalj.types.result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.types.tuple.Tuple;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface ResultMap<DATA> {
    
    public <TARGET> Result<TARGET> map(Function<? super DATA, TARGET> mapper);
    

    public default Result<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    public default <T> Result<T> mapIf(Predicate<? super DATA> checker, Function<? super DATA, T> mapper, Function<? super DATA, T> elseMapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    //== Map to tuple. ==
    
    public default <T1, T2> 
        Result<Tuple2<T1, T2>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return map(each -> Tuple.of(
                mapper1.apply(each),
                mapper2.apply(each)));
    }
    
    public default <T1, T2, T3>
        Result<Tuple3<T1, T2, T3>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return map(each -> Tuple.of(
                mapper1.apply(each),
                mapper2.apply(each),
                mapper3.apply(each)));
    }
    
    public default <T1, T2, T3, T4>
        Result<Tuple4<T1, T2, T3, T4>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return map(each -> Tuple.of(
                mapper1.apply(each),
                mapper2.apply(each),
                mapper3.apply(each),
                mapper4.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T5>
        Result<Tuple5<T1, T2, T3, T4, T5>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return map(each -> Tuple.of(
                mapper1.apply(each),
                mapper2.apply(each),
                mapper3.apply(each),
                mapper4.apply(each),
                mapper5.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T5, T6>
        Result<Tuple6<T1, T2, T3, T4, T5, T6>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return map(each -> Tuple.of(
                mapper1.apply(each),
                mapper2.apply(each),
                mapper3.apply(each),
                mapper4.apply(each),
                mapper5.apply(each),
                mapper6.apply(each)));
    }
    
    //== Map to tuple to object. ==
    
    public default <T1, T2, T> 
        Result<T> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v  = function.apply(v1, v2);
            return v;
        });
    }
    
    public default <T1, T2, T3, T> 
        Result<T> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v  = function.apply(v1, v2, v3);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T> 
        Result<T> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v  = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T> 
        Result<T> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T> 
        Result<T> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5, v6);
            return v;
        });
    }
}
