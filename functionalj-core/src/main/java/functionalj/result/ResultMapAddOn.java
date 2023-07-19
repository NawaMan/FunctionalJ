// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

class ResultMapAddOnHelper {
    
    @SafeVarargs
    public static final <D, T> Result<T> mapFirst(ResultMapAddOn<D> result, Function<? super D, ? extends T>... mappers) {
        return result.map(Func.f(d -> {
            Exception exception = null;
            boolean hasNull = false;
            for (val mapper : mappers) {
                try {
                    val res = mapper.apply(d);
                    if (res == null)
                        hasNull = true;
                    else
                        return (T) res;
                } catch (Exception e) {
                    if (exception == null)
                        exception = e;
                }
            }
            if (hasNull)
                return (T) null;
            throw exception;
        }));
    }
}

public interface ResultMapAddOn<DATA> {
    
    public <TARGET> Result<TARGET> map(Function<? super DATA, ? extends TARGET> mapper);
    
    public default <TARGET> Result<TARGET> mapTo(Function<? super DATA, TARGET> mapper) {
        return map(mapper);
    }
    
    public default Result<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, ? extends DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    
    public default <T> Result<T> mapIf(Predicate<? super DATA> checker, Function<? super DATA, ? extends T> mapper, Function<? super DATA, ? extends T> elseMapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    // TODO - Find a better name  --- mapFirstOf, mapOneOf, tryMap
    public default <T> Result<T> mapFirst(Function<? super DATA, ? extends T> mapper1, Function<? super DATA, ? extends T> mapper2) {
        return ResultMapAddOnHelper.mapFirst(this, mapper1, mapper2);
    }
    
    public default <T> Result<T> mapFirst(Function<? super DATA, ? extends T> mapper1, Function<? super DATA, ? extends T> mapper2, Function<? super DATA, ? extends T> mapper3) {
        return ResultMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> Result<T> mapFirst(Function<? super DATA, ? extends T> mapper1, Function<? super DATA, ? extends T> mapper2, Function<? super DATA, ? extends T> mapper3, Function<? super DATA, ? extends T> mapper4) {
        return ResultMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> Result<T> mapFirst(Function<? super DATA, ? extends T> mapper1, Function<? super DATA, ? extends T> mapper2, Function<? super DATA, ? extends T> mapper3, Function<? super DATA, ? extends T> mapper4, Function<? super DATA, ? extends T> mapper5) {
        return ResultMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> Result<T> mapFirst(Function<? super DATA, ? extends T> mapper1, Function<? super DATA, ? extends T> mapper2, Function<? super DATA, ? extends T> mapper3, Function<? super DATA, ? extends T> mapper4, Function<? super DATA, ? extends T> mapper5, Function<? super DATA, ? extends T> mapper6) {
        return ResultMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
    
    // == Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    public default <T1, T2> Result<Tuple2<T1, T2>> mapTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2, (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> Result<Tuple3<T1, T2, T3>> mapTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3, (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> Result<Tuple4<T1, T2, T3, T4>> mapTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> Result<Tuple5<T1, T2, T3, T4, T5>> mapTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    
    public default <T1, T2, T3, T4, T5, T6> Result<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5, Function<? super DATA, ? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    // -- Map and combine --
    public default <T1, T2, T> Result<T> mapThen(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, BiFunction<T1, T2, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v = function.apply(v1, v2);
            return v;
        });
    }
    
    public default <T1, T2, T3, T> Result<T> mapThen(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v = function.apply(v1, v2, v3);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T> Result<T> mapThen(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Func4<T1, T2, T3, T4, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T> Result<T> mapThen(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5, Func5<T1, T2, T3, T4, T5, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T> Result<T> mapThen(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5, Function<? super DATA, ? extends T6> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6);
            return v;
        });
    }
    
    // -- Generated with: GeneratorFunctorMapToTupleToObject --
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableFuncMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5, KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5, KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data), key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5, KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7, Function<? super DATA, ? extends VALUE> mapper7, KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data), key7, mapper7.apply(data), key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5, KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7, Function<? super DATA, ? extends VALUE> mapper7, KEY key8, Function<? super DATA, ? extends VALUE> mapper8, KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data), key7, mapper7.apply(data), key8, mapper8.apply(data), key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> Result<FuncMap<KEY, VALUE>> mapToMap(KEY key1, Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2, KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4, Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5, KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7, Function<? super DATA, ? extends VALUE> mapper7, KEY key8, Function<? super DATA, ? extends VALUE> mapper8, KEY key9, Function<? super DATA, ? extends VALUE> mapper9, KEY key10, Function<? super DATA, ? extends VALUE> mapper10) {
        return map(data -> ImmutableFuncMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3, mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data), key7, mapper7.apply(data), key8, mapper8.apply(data), key9, mapper9.apply(data), key10, mapper10.apply(data)));
    }
}
