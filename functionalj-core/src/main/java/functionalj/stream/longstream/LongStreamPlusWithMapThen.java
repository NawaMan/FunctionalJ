package functionalj.stream.longstream;

import java.util.function.BiFunction;
import java.util.function.LongFunction;

import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.stream.StreamPlus;
import lombok.val;

public interface LongStreamPlusWithMapThen {
    
    public <TARGET> StreamPlus<TARGET> mapToObj(
            LongFunction<? extends TARGET> mapper);
    
    //== mapThen ==
    
    public default <T1, T2, T> 
        StreamPlus<T> mapThen(
                LongFunction<? extends T1> mapper1,
                LongFunction<? extends T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return mapToObj(each -> {
            
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v  = function.apply(v1, v2);
            return v;
        });
    }
    public default <T1, T2, T3, T> 
        StreamPlus<T> mapThen(
                LongFunction<? extends T1> mapper1,
                LongFunction<? extends T2> mapper2,
                LongFunction<? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v  = function.apply(v1, v2, v3);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T> 
        StreamPlus<T> mapThen(
                LongFunction<? extends T1> mapper1,
                LongFunction<? extends T2> mapper2,
                LongFunction<? extends T3> mapper3,
                LongFunction<? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v  = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T5, T> 
        StreamPlus<T> mapThen(
                LongFunction<? extends T1> mapper1,
                LongFunction<? extends T2> mapper2,
                LongFunction<? extends T3> mapper3,
                LongFunction<? extends T4> mapper4,
                LongFunction<? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return mapToObj(each -> {
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
        StreamPlus<T> mapThen(
                LongFunction<? extends T1> mapper1,
                LongFunction<? extends T2> mapper2,
                LongFunction<? extends T3> mapper3,
                LongFunction<? extends T4> mapper4,
                LongFunction<? extends T5> mapper5,
                LongFunction<? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return mapToObj(each -> {
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
