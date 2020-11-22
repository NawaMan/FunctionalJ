// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.result;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

class ResultChainAddOnHelper {
    @SafeVarargs
    public static final <D, T> Result<T> chainAny(
            ResultChainAddOn<D>                result,
            Function<? super D, Result<T>> ... mappers) {
        return result.chain(d -> {
            Result<T> exception = null;
            boolean hasNull = false;
            for(Function<? super D, Result<T>> mapper : mappers) {
                try {
                    var res = mapper.apply(d);
                    if (res.isNull())
                        hasNull = true;
                    else if (res.isException()) {
                        exception = res;
                        continue;
                    }
                    else return (Result<T>)res;
                } catch (Exception e) {
                    if (exception == null)
                        exception = Result.ofException(e);
                }
            }
            if (hasNull)
                return Result.ofNull();
            
            return exception;
        });
    }
}
public interface ResultChainAddOn<DATA> {

    
    /**
     * Returns this object as a result.
     * 
     * @return this object as a result.
     **/
    public Result<DATA> asResult();
    
    
    public <TARGET> Result<TARGET> flatMap(Func1<? super DATA, ? extends Result<TARGET>> mapper);
    
    public default <TARGET> Result<TARGET> chain(Func1<? super DATA, ? extends Result<TARGET>> mapper) {
        return flatMap(mapper);
    }
    
    public default Result<DATA> chainOnly(Predicate<? super DATA> checker, Func1<? super DATA, Result<DATA>> mapper) {
        return chain(d -> checker.test(d) ? mapper.apply(d) : asResult());
    }
    
    public default <T> Result<T> chainIf(
            Predicate<? super DATA> checker, 
            Func1<? super DATA, Result<T>> mapper, 
            Func1<? super DATA, Result<T>> elseMapper) {
        return chain(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> Result<T> chainAny(
            Function<? super DATA, Result<T>> mapper1,
            Function<? super DATA, Result<T>> mapper2) {
        return ResultChainAddOnHelper.chainAny(this, mapper1, mapper2);
    }
    
    public default <T> Result<T> chainAny(
            Function<? super DATA, Result<T>> mapper1,
            Function<? super DATA, Result<T>> mapper2,
            Function<? super DATA, Result<T>> mapper3) {
        return ResultChainAddOnHelper.chainAny(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> Result<T> chainAny(
            Function<? super DATA, Result<T>> mapper1,
            Function<? super DATA, Result<T>> mapper2,
            Function<? super DATA, Result<T>> mapper3,
            Function<? super DATA, Result<T>> mapper4) {
        return ResultChainAddOnHelper.chainAny(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> Result<T> chainAny(
            Function<? super DATA, Result<T>> mapper1,
            Function<? super DATA, Result<T>> mapper2,
            Function<? super DATA, Result<T>> mapper3,
            Function<? super DATA, Result<T>> mapper4,
            Function<? super DATA, Result<T>> mapper5) {
        return ResultChainAddOnHelper.chainAny(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> Result<T> chainAny(
            Function<? super DATA, Result<T>> mapper1,
            Function<? super DATA, Result<T>> mapper2,
            Function<? super DATA, Result<T>> mapper3,
            Function<? super DATA, Result<T>> mapper4,
            Function<? super DATA, Result<T>> mapper5,
            Function<? super DATA, Result<T>> mapper6) {
        return ResultChainAddOnHelper.chainAny(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
    
    public default <T1, T2> 
        Result<Tuple2<T1, T2>> chainTuple(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2) {
       return chainThen(mapper1, mapper2, (v1, v2)->{
          return Tuple.of(v1, v2);
       });
    }
    public default <T1, T2, T3> 
        Result<Tuple3<T1, T2, T3>> chainTuple(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3) {
       return chainThen(mapper1, mapper2, mapper3, (v1, v2, v3)->{
          return Tuple.of(v1, v2, v3);
       });
    }
    public default <T1, T2, T3, T4> 
       Result<Tuple4<T1, T2, T3, T4>> chainTuple(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4) {
       return chainThen(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4)->{
          return Tuple.of(v1, v2, v3, v4);
       });
    }
    public default <T1, T2, T3, T4, T5> 
       Result<Tuple5<T1, T2, T3, T4, T5>> chainTuple(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5) {
       return chainThen(mapper1, mapper2, mapper3, mapper4, mapper5, (v1, v2, v3, v4, v5)->{
          return Tuple.of(v1, v2, v3, v4, v5);
       });
    }
    public default <T1, T2, T3, T4, T5, T6> 
       Result<Tuple6<T1, T2, T3, T4, T5, T6>> chainTuple(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func1<? super DATA, ? extends Result<T6>> mapper6) {
       return chainThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, (v1, v2, v3, v4, v5, v6)->{
          return Tuple.of(v1, v2, v3, v4, v5, v6);
       });
    }
    
    public default <T, T1, T2> 
       Result<T> chainThen(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func2<T1, T2, T> mapper) {
        return Result.of(Func0.of(()->{
            var value   = asResult().orThrow();
            
            var result1 = mapper1.apply(value);
            var value1  = result1.orThrow();
            
            var result2 = mapper2.apply(value);
            var value2  = result2.orThrow();
            
            var target  = mapper.apply(value1, value2);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3> 
        Result<T> chainThen(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func3<T1, T2, T3, T> mapper) {
        return Result.of(Func0.of(()->{
            var value   = asResult().orThrow();
            
            var result1 = mapper1.apply(value);
            var value1  = result1.orThrow();
            
            var result2 = mapper2.apply(value);
            var value2  = result2.orThrow();
            
            var result3 = mapper3.apply(value);
            var value3  = result3.orThrow();
            
            var target  = mapper.apply(value1, value2, value3);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4> 
        Result<T> chainThen(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func4<T1, T2, T3, T4, T> mapper) {
        return Result.of(Func0.of(()->{
            var value   = asResult().orThrow();
            
            var result1 = mapper1.apply(value);
            var value1  = result1.orThrow();
            
            var result2 = mapper2.apply(value);
            var value2  = result2.orThrow();
            
            var result3 = mapper3.apply(value);
            var value3  = result3.orThrow();
            
            var result4 = mapper4.apply(value);
            var value4  = result4.orThrow();
            
            var target  = mapper.apply(value1, value2, value3, value4);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4, T5> 
        Result<T> chainThen(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func5<T1, T2, T3, T4, T5, T> mapper) {
        return Result.of(Func0.of(()->{
            var value   = asResult().orThrow();
            
            var result1 = mapper1.apply(value);
            var value1  = result1.orThrow();
            
            var result2 = mapper2.apply(value);
            var value2  = result2.orThrow();
            
            var result3 = mapper3.apply(value);
            var value3  = result3.orThrow();
            
            var result4 = mapper4.apply(value);
            var value4  = result4.orThrow();
            
            var result5 = mapper5.apply(value);
            var value5  = result5.orThrow();
            
            var target  = mapper.apply(value1, value2, value3, value4, value5);
            return target;
        }));
    }
    
    public default <T, T1, T2, T3, T4, T5, T6> 
        Result<T> chainThen(
            Func1<? super DATA, ? extends Result<T1>> mapper1,
            Func1<? super DATA, ? extends Result<T2>> mapper2,
            Func1<? super DATA, ? extends Result<T3>> mapper3,
            Func1<? super DATA, ? extends Result<T4>> mapper4,
            Func1<? super DATA, ? extends Result<T5>> mapper5,
            Func1<? super DATA, ? extends Result<T6>> mapper6,
            Func6<T1, T2, T3, T4, T5, T6, T> mapper) {
        return Result.of(Func0.of(()->{
            var value   = asResult().orThrow();
            
            var result1 = mapper1.apply(value);
            var value1  = result1.orThrow();
            
            var result2 = mapper2.apply(value);
            var value2  = result2.orThrow();
            
            var result3 = mapper3.apply(value);
            var value3  = result3.orThrow();
            
            var result4 = mapper4.apply(value);
            var value4  = result4.orThrow();
            
            var result5 = mapper5.apply(value);
            var value5  = result5.orThrow();
            
            var result6 = mapper6.apply(value);
            var value6  = result6.orThrow();
            
            var target  = mapper.apply(value1, value2, value3, value4, value5, value6);
            return target;
        }));
    }
}
