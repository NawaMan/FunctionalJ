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
package functionalj.functions;

import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func1;
import functionalj.list.FuncList;
import functionalj.map.ImmutableFuncMap;
import functionalj.tuple.ToMapFunc;
import functionalj.tuple.ToTuple2Func;
import functionalj.tuple.Tuple;
import lombok.val;

public class MapTo {
    
    public static <T> Func1<T, T> only(Predicate<? super T> checker) {
        return input -> checker.test(input) ? input : null;
    }
    
    public static <T> Func1<T, T> forOnly(Predicate<? super T> checker, Func1<? super T, ? extends T> mapper) {
        return input -> checker.test(input) ? mapper.applyUnsafe(input) : input;
    }
    
    public static <T> Func1<T, T> when(Predicate<? super T> checker, Function<? super T, ? extends T> mapper, Function<? super T, ? extends T> elseMapper) {
        return input -> checker.test(input) ? mapper.apply(input) : elseMapper.apply(input);
    }
    
    // FirstOf
    public static <D, T> Func1<D, T> firstOf(FuncList<Function<? super D, ? extends T>> mappers) {
        return input -> {
            Exception exception = null;
            boolean hasNull = false;
            for (val mapper : mappers) {
                try {
                    val res = mapper.apply(input);
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
        };
    }
    
    @SafeVarargs
    public static <D, T> Func1<D, T> firstOf(Function<? super D, ? extends T>... mappers) {
        FuncList<Function<? super D, ? extends T>> mappersArray = FuncList.from(mappers);
        return firstOf(mappersArray);
    }
    
    // Tuple
    public static <D, T1, T2> ToTuple2Func<D, T1, T2> toTuple(Func1<? super D, ? extends T1> mapper1, Func1<? super D, ? extends T2> mapper2) {
        return input -> {
            val v1 = mapper1.apply(input);
            val v2 = mapper2.apply(input);
            return Tuple.of(v1, v2);
        };
    }
    
    // Map
    public static <D, K, V> ToMapFunc<D, K, V> toMap(K key, Func1<? super D, ? extends V> mapper) {
        return data -> ImmutableFuncMap.of(key, mapper.apply(data));
    }
}
