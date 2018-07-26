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
import nawaman.nullablej.nullable.Nullable;


/**
 * This class implements additional flatMap methods for Result aiming to make Result code more manageable.
 * Its implement is highly depends on the internal working of Result.
 * 
 * @param <DATA> the data type of the data inside the result.
 * 
 * @author NawaMan -- nawa@nawaman.net
 **/
public interface ResultFlatMap<DATA> {
	
	/**
	 * Returns this object as a result.
	 * 
	 * @return this object as a result.
	 **/
    public Result<DATA> asResult();
    
    
    public <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Nullable<TARGET>> mapper);
    
    public default Result<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, Result<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : asResult());
    }
    
    public default <T> Result<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Result<T>> mapper, 
            Function<? super DATA, Result<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
	public default <T1, T2> 
        Result<Tuple2<T1, T2>> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2) {
    	return flatMap(mapper1, mapper2, (v1, v2)->{
    		return Tuple.of(v1, v2);
    	});
    }
	public default <T1, T2, T3> 
        Result<Tuple3<T1, T2, T3>> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2,
                Function<? super DATA, ? extends Result<T3>> mapper3) {
    	return flatMap(mapper1, mapper2, mapper3, (v1, v2, v3)->{
    		return Tuple.of(v1, v2, v3);
    	});
    }
    public default <T1, T2, T3, T4> 
    	Result<Tuple4<T1, T2, T3, T4>> flatMap(
            Function<? super DATA, ? extends Result<T1>> mapper1,
            Function<? super DATA, ? extends Result<T2>> mapper2,
            Function<? super DATA, ? extends Result<T3>> mapper3,
            Function<? super DATA, ? extends Result<T4>> mapper4) {
    	return flatMap(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4)->{
    		return Tuple.of(v1, v2, v3, v4);
    	});
    }
    public default <T1, T2, T3, T4, T5> 
    	Result<Tuple5<T1, T2, T3, T4, T5>> flatMap(
            Function<? super DATA, ? extends Result<T1>> mapper1,
            Function<? super DATA, ? extends Result<T2>> mapper2,
            Function<? super DATA, ? extends Result<T3>> mapper3,
            Function<? super DATA, ? extends Result<T4>> mapper4,
            Function<? super DATA, ? extends Result<T5>> mapper5) {
    	return flatMap(mapper1, mapper2, mapper3, mapper4, mapper5, (v1, v2, v3, v4, v5)->{
    		return Tuple.of(v1, v2, v3, v4, v5);
    	});
    }
    public default <T1, T2, T3, T4, T5, T6> 
    	Result<Tuple6<T1, T2, T3, T4, T5, T6>> flatMap(
            Function<? super DATA, ? extends Result<T1>> mapper1,
            Function<? super DATA, ? extends Result<T2>> mapper2,
            Function<? super DATA, ? extends Result<T3>> mapper3,
            Function<? super DATA, ? extends Result<T4>> mapper4,
            Function<? super DATA, ? extends Result<T5>> mapper5,
            Function<? super DATA, ? extends Result<T6>> mapper6) {
    	return flatMap(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, (v1, v2, v3, v4, v5, v6)->{
    		return Tuple.of(v1, v2, v3, v4, v5, v6);
    	});
    }
    
    @SuppressWarnings("unchecked")	// This unchecked is done to pass on error.
	public default <T, T1, T2> 
    	Result<T> flatMap(
            Function<? super DATA, ? extends Result<T1>> mapper1,
            Function<? super DATA, ? extends Result<T2>> mapper2,
            BiFunction<T1, T2, T> mapper) {
    	// For some reason these kind of code hangs the compiler :-(
//      return flatMap(value -> {
//          return mapper1.apply(value).flatMap((T1 v1) -> {
//              return mapper2.apply(value).map((T2 v2) -> { 
//                  return Tuple.of(v1, v2);
//              });
//          });
//      });
    	Result<DATA> thisResult = asResult();
		if (!thisResult.isPresent())
    		return (Result<T>)thisResult;
		
    	val value = asResult().get();
    	val t1 = mapper1.apply(value);
    	if (!t1.isPresent())
    		return (Result<T>)t1;
    	val t2 = mapper2.apply(value);
    	if (!t2.isPresent())
    		return (Result<T>)t2;
    	
    	val v1 = t1.get();
    	val v2 = t2.get();
    	val v  = mapper.apply(v1, v2);
		return Result.of(v);
	}
    
    @SuppressWarnings("unchecked")	// This unchecked is done to pass on error.
	public default <T, T1, T2, T3> 
        Result<T> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2,
                Function<? super DATA, ? extends Result<T3>> mapper3,
        		Func3<T1, T2, T3, T> mapper) {
    	// For some reason these kind of code hangs the compiler :-(
//        return flatMap(value -> {
//            return mapper1.apply(value).flatMap((T1 v1) -> {
//                return mapper2.apply(value).flatMap((T2 v2) -> {
//                    return mapper3.apply(value).map((T3 v3) -> { 
//                        return Tuple.of(v1, v2, v3);
//                    });
//                });
//            });
//        });
    	Result<DATA> thisResult = asResult();
		if (!thisResult.isPresent())
    		return (Result<T>)thisResult;
		
    	val value = asResult().get();
    	val t1 = mapper1.apply(value);
    	if (!t1.isPresent())
    		return (Result<T>)t1;
    	val t2 = mapper2.apply(value);
    	if (!t2.isPresent())
    		return (Result<T>)t2;
    	val t3 = mapper3.apply(value);
    	if (!t3.isPresent())
    		return (Result<T>)t3;
    	
    	val v1 = t1.get();
    	val v2 = t2.get();
    	val v3 = t3.get();
    	val v  = mapper.apply(v1, v2, v3);
		return Result.of(v);
    }
    
    @SuppressWarnings("unchecked")	// This unchecked is done to pass on error.
    public default <T, T1, T2, T3, T4> 
        Result<T> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2,
                Function<? super DATA, ? extends Result<T3>> mapper3,
                Function<? super DATA, ? extends Result<T4>> mapper4,
        		Func4<T1, T2, T3, T4, T> mapper) {
    	// For some reason these kind of code hangs the compiler :-(
//        return flatMap(value -> {
//            return mapper1.apply(value).flatMap((T1 v1) -> {
//                return mapper2.apply(value).flatMap((T2 v2) -> {
//                    return mapper3.apply(value).flatMap((T3 v3) -> { 
//                        return mapper4.apply(value).map((T4 v4) -> { 
//                            return Tuple.of(v1, v2, v3, v4);
//                        });
//                    });
//                });
//            });
//        });
    	Result<DATA> thisResult = asResult();
		if (!thisResult.isPresent())
    		return (Result<T>)thisResult;
		
    	val value = asResult().get();
    	val t1 = mapper1.apply(value);
    	if (!t1.isPresent())
    		return (Result<T>)t1;
    	val t2 = mapper2.apply(value);
    	if (!t2.isPresent())
    		return (Result<T>)t2;
    	val t3 = mapper3.apply(value);
    	if (!t3.isPresent())
    		return (Result<T>)t3;
    	val t4 = mapper4.apply(value);
    	if (!t4.isPresent())
    		return (Result<T>)t4;
    	
    	val v1 = t1.get();
    	val v2 = t2.get();
    	val v3 = t3.get();
    	val v4 = t4.get();
    	val v  = mapper.apply(v1, v2, v3, v4);
		return Result.of(v);
    }
    
    @SuppressWarnings("unchecked")	// This unchecked is done to pass on error.
    public default <T, T1, T2, T3, T4, T5> 
        Result<T> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2,
                Function<? super DATA, ? extends Result<T3>> mapper3,
                Function<? super DATA, ? extends Result<T4>> mapper4,
                Function<? super DATA, ? extends Result<T5>> mapper5,
        		Func5<T1, T2, T3, T4, T5, T> mapper) {
    	// For some reason these kind of code hangs the compiler :-(
//        return flatMap(value -> {
//			return mapper1.apply(value).flatMap((T1 v1) -> {
//                return mapper2.apply(value).flatMap((T2 v2) -> {
//                    return mapper3.apply(value).flatMap((T3 v3) -> { 
//                        return mapper4.apply(value).flatMap((T4 v4) -> { 
//                            return mapper5.apply(value).map((T5 v5) -> { 
//                                return Tuple.of(v1, v2, v3, v4, v5);
//                            });
//                        });
//                    });
//                });
//            });
//        });
    	Result<DATA> thisResult = asResult();
		if (!thisResult.isPresent())
    		return (Result<T>)thisResult;
		
    	val value = asResult().get();
    	val t1 = mapper1.apply(value);
    	if (!t1.isPresent())
    		return (Result<T>)t1;
    	val t2 = mapper2.apply(value);
    	if (!t2.isPresent())
    		return (Result<T>)t2;
    	val t3 = mapper3.apply(value);
    	if (!t3.isPresent())
    		return (Result<T>)t3;
    	val t4 = mapper4.apply(value);
    	if (!t4.isPresent())
    		return (Result<T>)t4;
    	val t5 = mapper5.apply(value);
    	if (!t5.isPresent())
    		return (Result<T>)t5;
    	
    	val v1 = t1.get();
    	val v2 = t2.get();
    	val v3 = t3.get();
    	val v4 = t4.get();
    	val v5 = t5.get();
    	val v  = mapper.apply(v1, v2, v3, v4, v5);
		return Result.of(v);
    }
    
    @SuppressWarnings("unchecked")	// This unchecked is done to pass on error.
    public default <T, T1, T2, T3, T4, T5, T6> 
        Result<T> flatMap(
                Function<? super DATA, ? extends Result<T1>> mapper1,
                Function<? super DATA, ? extends Result<T2>> mapper2,
                Function<? super DATA, ? extends Result<T3>> mapper3,
                Function<? super DATA, ? extends Result<T4>> mapper4,
                Function<? super DATA, ? extends Result<T5>> mapper5,
                Function<? super DATA, ? extends Result<T6>> mapper6,
        		Func6<T1, T2, T3, T4, T5, T6, T> mapper) {
    	// For some reason these kind of code hangs the compiler :-(
//        return flatMap(value -> {
//            return mapper1.apply(value).flatMap((T1 v1) -> {
//                return mapper2.apply(value).flatMap((T2 v2) -> {
//                    return mapper3.apply(value).flatMap((T3 v3) -> { 
//                        return mapper4.apply(value).flatMap((T4 v4) -> { 
//                            return mapper5.apply(value).flatMap((T5 v5) -> {
//                                return mapper6.apply(value).map((T6 v6) -> { 
//                                    return Tuple.of(v1, v2, v3, v4, v5, v6);
//                                });
//                            });
//                        });
//                    });
//                });
//            });
//        });
    	Result<DATA> thisResult = asResult();
		if (!thisResult.isPresent())
    		return (Result<T>)thisResult;
		
    	val value = asResult().get();
    	val t1 = mapper1.apply(value);
    	if (!t1.isPresent())
    		return (Result<T>)t1;
    	val t2 = mapper2.apply(value);
    	if (!t2.isPresent())
    		return (Result<T>)t2;
    	val t3 = mapper3.apply(value);
    	if (!t3.isPresent())
    		return (Result<T>)t3;
    	val t4 = mapper4.apply(value);
    	if (!t4.isPresent())
    		return (Result<T>)t4;
    	val t5 = mapper5.apply(value);
    	if (!t5.isPresent())
    		return (Result<T>)t5;
    	val t6 = mapper6.apply(value);
    	if (!t6.isPresent())
    		return (Result<T>)t6;
    	
    	val v1 = t1.get();
    	val v2 = t2.get();
    	val v3 = t3.get();
    	val v4 = t4.get();
    	val v5 = t5.get();
    	val v6 = t6.get();
    	val v  = mapper.apply(v1, v2, v3, v4, v5, v6) ;
		return Result.of(v);
    }
}
