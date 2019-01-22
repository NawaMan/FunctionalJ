package functionalj.functions;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.list.FuncList;
import functionalj.map.ImmutableMap;
import functionalj.tuple.ToMapFunc;
import functionalj.tuple.ToTuple2Func;
import functionalj.tuple.Tuple;
import lombok.val;

public class MapTo {
    
    public static <T> Func1<T, T> only(Predicate<? super T> checker) {
        return input -> checker.test(input) ? input : null;
    }
    
    public static <T> Func1<T, T> forOnly(
            Predicate<? super T>          checker,
            Func1<? super T, ? extends T> mapper) {
        return input -> checker.test(input) ? mapper.applyUnsafe(input) : input;
    }
    
    public static <T> Func1<T, T> when(
            Predicate<? super T>             checker, 
            Function<? super T, ? extends T> mapper, 
            Function<? super T, ? extends T> elseMapper) {
        return input -> 
                checker.test(input)
                ? mapper.apply(input)
                : elseMapper.apply(input);
    }
    
    // FirstOf
    
    public static <D, T> Func1<D, T> firstOf(
            FuncList<Function<? super D, ? extends T>> mappers) {
        return input -> {
            Exception exception = null;
            boolean hasNull = false;
            for(val mapper : mappers) {
                try {
                    val res = mapper.apply(input);
                    if (res == null)
                         hasNull = true;
                    else return (T)res;
                } catch (Exception e) {
                    if (exception == null)
                        exception = e;
                }
            }
            if (hasNull)
                return (T)null;
            
            throw exception;
        };
    }
    
    @SafeVarargs
    public static <D, T> Func1<D, T> firstOf(
            Function<? super D, ? extends T> ... mappers) {
        return firstOf(FuncList.from(mappers));
    }
    
    // Tuple
    
    public static <D, T1, T2> ToTuple2Func<D, T1, T2> toTuple(
            Func1<? super D, ? extends T1> mapper1,
            Func1<? super D, ? extends T2> mapper2) {
        return input -> {
            val v1 = mapper1.apply(input);
            val v2 = mapper2.apply(input);
            return Tuple.of(v1, v2);
        };
    }
    
    // TODO - Add more
    
    // Map
    
    public static <D, K, V> ToMapFunc<D, K, V> toMap(
            K key, Func1<? super D, ? extends V> mapper) {
        return data -> ImmutableMap.of(key, mapper.apply(data));
    }
    
    // TODO - Add more
    
}
