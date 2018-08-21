package functionalj.types.result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.Func2;
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
import nawaman.nullablej.nullable.Nullable;

public interface ResultChainAddOn<DATA> {

    
    /**
     * Returns this object as a result.
     * 
     * @return this object as a result.
     **/
    public Result<DATA> asResult();
    
    
    public <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Result<TARGET>> mapper);
    
    public default <TARGET> Result<TARGET> chain(Func1<? super DATA, ? extends Result<TARGET>> mapper) {
        return flatMap(mapper);
    }
    
    public default Result<DATA> chainIf(Predicate<? super DATA> checker, Func1<? super DATA, Result<DATA>> mapper) {
        return chain(d -> checker.test(d) ? mapper.apply(d) : asResult());
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker, 
            Func1<? super DATA, Result<T>> mapper, 
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker1, Func1<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Func1<? super DATA, Result<T>> mapper2,
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker1, Func1<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Func1<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Func1<? super DATA, Result<T>> mapper3,
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker1, Func1<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Func1<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Func1<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Func1<? super DATA, Result<T>> mapper4,
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker1, Func1<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Func1<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Func1<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Func1<? super DATA, Result<T>> mapper4,
            Predicate<? super DATA> checker5, Func1<? super DATA, Result<T>> mapper5,
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker1, Func1<? super DATA, Result<T>> mapper1, 
            Predicate<? super DATA> checker2, Func1<? super DATA, Result<T>> mapper2,
            Predicate<? super DATA> checker3, Func1<? super DATA, Result<T>> mapper3,
            Predicate<? super DATA> checker4, Func1<? super DATA, Result<T>> mapper4,
            Predicate<? super DATA> checker5, Func1<? super DATA, Result<T>> mapper5,
            Predicate<? super DATA> checker6, Func1<? super DATA, Result<T>> mapper6,
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T1, T2> 
        Result<Tuple2<T1, T2>> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2) {
       return chain(mapper1, mapper2, (v1, v2)->{
          return Tuple.of(v1, v2);
       });
    }
    public default <T1, T2, T3> 
        Result<Tuple3<T1, T2, T3>> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3) {
       return chain(mapper1, mapper2, mapper3, (v1, v2, v3)->{
          return Tuple.of(v1, v2, v3);
       });
    }
    public default <T1, T2, T3, T4> 
       Result<Tuple4<T1, T2, T3, T4>> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4) {
       return chain(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4)->{
          return Tuple.of(v1, v2, v3, v4);
       });
    }
    public default <T1, T2, T3, T4, T5> 
       Result<Tuple5<T1, T2, T3, T4, T5>> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5) {
       return chain(mapper1, mapper2, mapper3, mapper4, mapper5, (v1, v2, v3, v4, v5)->{
          return Tuple.of(v1, v2, v3, v4, v5);
       });
    }
    public default <T1, T2, T3, T4, T5, T6> 
       Result<Tuple6<T1, T2, T3, T4, T5, T6>> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func1<? super DATA, ? extends Result<T6>> mapper6) {
       return chain(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, (v1, v2, v3, v4, v5, v6)->{
          return Tuple.of(v1, v2, v3, v4, v5, v6);
       });
    }
    
    public default <T, T1, T2> 
       Result<T> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func2<T1, T2, T> mapper) {
        return Result.from(Func0.of(()->{
            val value   = asResult().orThrow();
            
            val result1 = mapper1.apply(value);
            val value1  = result1.orThrow();
            
            val result2 = mapper2.apply(value);
            val value2  = result2.orThrow();
            
            val target  = mapper.apply(value1, value2);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3> 
        Result<T> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func3<T1, T2, T3, T> mapper) {
        return Result.from(Func0.of(()->{
            val value   = asResult().orThrow();
            
            val result1 = mapper1.apply(value);
            val value1  = result1.orThrow();
            
            val result2 = mapper2.apply(value);
            val value2  = result2.orThrow();
            
            val result3 = mapper3.apply(value);
            val value3  = result3.orThrow();
            
            val target  = mapper.apply(value1, value2, value3);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4> 
        Result<T> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func4<T1, T2, T3, T4, T> mapper) {
        return Result.from(Func0.of(()->{
            val value   = asResult().orThrow();
            
            val result1 = mapper1.apply(value);
            val value1  = result1.orThrow();
            
            val result2 = mapper2.apply(value);
            val value2  = result2.orThrow();
            
            val result3 = mapper3.apply(value);
            val value3  = result3.orThrow();
            
            val result4 = mapper4.apply(value);
            val value4  = result4.orThrow();
            
            val target  = mapper.apply(value1, value2, value3, value4);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4, T5> 
        Result<T> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func5<T1, T2, T3, T4, T5, T> mapper) {
        return Result.from(Func0.of(()->{
            val value   = asResult().orThrow();
            
            val result1 = mapper1.apply(value);
            val value1  = result1.orThrow();
            
            val result2 = mapper2.apply(value);
            val value2  = result2.orThrow();
            
            val result3 = mapper3.apply(value);
            val value3  = result3.orThrow();
            
            val result4 = mapper4.apply(value);
            val value4  = result4.orThrow();
            
            val result5 = mapper5.apply(value);
            val value5  = result5.orThrow();
            
            val target  = mapper.apply(value1, value2, value3, value4, value5);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4, T5, T6> 
        Result<T> chain(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func1<? super DATA, ? extends Result<T6>> mapper6,
            Func6<T1, T2, T3, T4, T5, T6, T> mapper) {
        return Result.from(Func0.of(()->{
            val value   = asResult().orThrow();
            
            val result1 = mapper1.apply(value);
            val value1  = result1.orThrow();
            
            val result2 = mapper2.apply(value);
            val value2  = result2.orThrow();
            
            val result3 = mapper3.apply(value);
            val value3  = result3.orThrow();
            
            val result4 = mapper4.apply(value);
            val value4  = result4.orThrow();
            
            val result5 = mapper5.apply(value);
            val value5  = result5.orThrow();
            
            val result6 = mapper6.apply(value);
            val value6  = result6.orThrow();
            
            val target  = mapper.apply(value1, value2, value3, value4, value5, value6);
            return target;
        }));
    }
}
