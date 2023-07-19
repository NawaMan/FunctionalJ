// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.promise;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.result.Result;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

@SuppressWarnings({ "unchecked", "rawtypes" })
public interface PromiseChainAddOn<DATA> {
    
    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper);
    
    public default <TARGET> Promise<TARGET> chain(Function<? super DATA, ? extends HasPromise<TARGET>> mapper) {
        return mapResult(result -> result.chain((Function) mapper));
    }
    
    public default Promise<DATA> chainOnly(Predicate<? super DATA> checker, Function<? super DATA, Promise<DATA>> mapper) {
        return mapResult(result -> result.chainOnly((Predicate) checker, (Function) mapper));
    }
    
    public default <T> Promise<T> chainIf(Predicate<? super DATA> checker, Function<? super DATA, Promise<T>> mapper, Function<? super DATA, Promise<T>> elseMapper) {
        return mapResult(result -> result.chainIf((Predicate) checker, (Function) mapper, (Function) elseMapper));
    }
    
    public default <T> Promise<T> chainAny(Function<? super DATA, Promise<T>> mapper1, Function<? super DATA, Promise<T>> mapper2) {
        return mapResult(result -> result.chainAny((Function) mapper1, (Function) mapper2));
    }
    
    public default <T> Promise<T> chainAny(Function<? super DATA, Promise<T>> mapper1, Function<? super DATA, Promise<T>> mapper2, Function<? super DATA, Promise<T>> mapper3) {
        return mapResult(result -> result.chainAny((Function) mapper1, (Function) mapper2, (Function) mapper3));
    }
    
    public default <T> Promise<T> chainAny(Function<? super DATA, Promise<T>> mapper1, Function<? super DATA, Promise<T>> mapper2, Function<? super DATA, Promise<T>> mapper3, Function<? super DATA, Promise<T>> mapper4) {
        return mapResult(result -> result.chainAny((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4));
    }
    
    public default <T> Promise<T> chainAny(Function<? super DATA, Promise<T>> mapper1, Function<? super DATA, Promise<T>> mapper2, Function<? super DATA, Promise<T>> mapper3, Function<? super DATA, Promise<T>> mapper4, Function<? super DATA, Promise<T>> mapper5) {
        return mapResult(result -> result.chainAny((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5));
    }
    
    public default <T> Promise<T> chainAny(Function<? super DATA, Promise<T>> mapper1, Function<? super DATA, Promise<T>> mapper2, Function<? super DATA, Promise<T>> mapper3, Function<? super DATA, Promise<T>> mapper4, Function<? super DATA, Promise<T>> mapper5, Function<? super DATA, Promise<T>> mapper6) {
        return mapResult(result -> result.chainAny((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5, (Function) mapper6));
    }
    
    public default <T1, T2> Promise<Tuple2<T1, T2>> chainTuple(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2) {
        return mapResult(result -> result.chainTuple((Function) mapper1, (Function) mapper2));
    }
    
    public default <T1, T2, T3> Promise<Tuple3<T1, T2, T3>> chainTuple(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3) {
        return mapResult(result -> result.chainTuple((Function) mapper1, (Function) mapper2, (Function) mapper3));
    }
    
    public default <T1, T2, T3, T4> Promise<Tuple4<T1, T2, T3, T4>> chainTuple(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4) {
        return mapResult(result -> result.chainTuple((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4));
    }
    
    public default <T1, T2, T3, T4, T5> Promise<Tuple5<T1, T2, T3, T4, T5>> chainTuple(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4, Function<? super DATA, ? extends Promise<T5>> mapper5) {
        return mapResult(result -> result.chainTuple((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5));
    }
    
    public default <T1, T2, T3, T4, T5, T6> Promise<Tuple6<T1, T2, T3, T4, T5, T6>> chainTuple(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4, Function<? super DATA, ? extends Promise<T5>> mapper5, Function<? super DATA, ? extends Promise<T6>> mapper6) {
        return mapResult(result -> result.chainTuple((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5, (Function) mapper6));
    }
    
    public default <T, T1, T2> Promise<T> chainThen(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, BiFunction<T1, T2, T> mapper) {
        return mapResult(result -> result.chainThen((Function) mapper1, (Function) mapper2, (BiFunction) mapper));
    }
    
    public default <T, T1, T2, T3> Promise<T> chainThen(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Func3<T1, T2, T3, T> mapper) {
        return mapResult(result -> result.chainThen((Function) mapper1, (Function) mapper2, (Function) mapper3, (Func3) mapper));
    }
    
    public default <T, T1, T2, T3, T4> Promise<T> chainThen(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4, Func4<T1, T2, T3, T4, T> mapper) {
        return mapResult(result -> result.chainThen((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Func4) mapper));
    }
    
    public default <T, T1, T2, T3, T4, T5> Promise<T> chainThen(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4, Function<? super DATA, ? extends Promise<T5>> mapper5, Func5<T1, T2, T3, T4, T5, T> mapper) {
        return mapResult(result -> result.chainThen((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5, (Func5) mapper));
    }
    
    public default <T, T1, T2, T3, T4, T5, T6> Promise<T> chainThen(Function<? super DATA, ? extends Promise<T1>> mapper1, Function<? super DATA, ? extends Promise<T2>> mapper2, Function<? super DATA, ? extends Promise<T3>> mapper3, Function<? super DATA, ? extends Promise<T4>> mapper4, Function<? super DATA, ? extends Promise<T5>> mapper5, Function<? super DATA, ? extends Promise<T6>> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> mapper) {
        return mapResult(result -> result.chainThen((Function) mapper1, (Function) mapper2, (Function) mapper3, (Function) mapper4, (Function) mapper5, (Function) mapper6, (Func6) mapper));
    }
}
