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
package functionalj.promise;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;


@SuppressWarnings({ "unchecked", "rawtypes" })
public interface PromiseMapAddOn<DATA> {
    
    
    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper);
    
    
    public default <TARGET> Promise<TARGET> mapTo(Function<? super DATA, TARGET> mapper) {
        return (Promise)mapResult(result -> result.mapTo((Function)mapper));
    }
    public default Promise<DATA> mapOnly(
            Predicate<? super DATA>   checker,
            Function<? super DATA, ? extends DATA> mapper) {
        return (Promise)mapResult(result -> result.mapOnly((Predicate)checker, (Function)mapper));
    }
    public default <T> Promise<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, ? extends T> mapper, 
            Function<? super DATA, ? extends T> elseMapper) {
        return (Promise)mapResult(result -> result.mapIf((Predicate)checker, (Function)mapper, (Function)elseMapper));
    }
    
    // TODO - Find a better name  --- mapFirstOf, mapOneOf, tryMap
    public default <T> Promise<T> mapFirst(
            Function<? super DATA, ? extends T> mapper1,
            Function<? super DATA, ? extends T> mapper2) {
        return (Promise)mapResult(result -> result.mapFirst((Function)mapper1, (Function)mapper2));
    }
    
    public default <T> Promise<T> mapFirst(
            Function<? super DATA, ? extends T> mapper1,
            Function<? super DATA, ? extends T> mapper2,
            Function<? super DATA, ? extends T> mapper3) {
        return (Promise)mapResult(result -> result.mapFirst((Function)mapper1, (Function)mapper2, (Function)mapper3));
    }
    
    public default <T> Promise<T> mapFirst(
            Function<? super DATA, ? extends T> mapper1,
            Function<? super DATA, ? extends T> mapper2,
            Function<? super DATA, ? extends T> mapper3,
            Function<? super DATA, ? extends T> mapper4) {
        return (Promise)mapResult(result -> result.mapFirst((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4));
    }
    
    public default <T> Promise<T> mapFirst(
            Function<? super DATA, ? extends T> mapper1,
            Function<? super DATA, ? extends T> mapper2,
            Function<? super DATA, ? extends T> mapper3,
            Function<? super DATA, ? extends T> mapper4,
            Function<? super DATA, ? extends T> mapper5) {
        return (Promise)mapResult(result -> result.mapFirst((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5));
    }
    
    public default <T> Promise<T> mapFirst(
            Function<? super DATA, ? extends T> mapper1,
            Function<? super DATA, ? extends T> mapper2,
            Function<? super DATA, ? extends T> mapper3,
            Function<? super DATA, ? extends T> mapper4,
            Function<? super DATA, ? extends T> mapper5,
            Function<? super DATA, ? extends T> mapper6) {
        return (Promise)mapResult(result -> result.mapFirst((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5, (Function)mapper6));
    }
    
    //== Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    
    public default <T1, T2> 
        Promise<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return (Promise)mapResult(result -> result.mapTuple((Function)mapper1, (Function)mapper2));
    }
    
    public default <T1, T2, T3> 
        Promise<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return (Promise)mapResult(result -> result.mapTuple((Function)mapper1, (Function)mapper2, (Function)mapper3));
    }
    
    public default <T1, T2, T3, T4> 
        Promise<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return (Promise)mapResult(result -> result.mapTuple((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        Promise<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return (Promise)mapResult(result -> result.mapTuple((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        Promise<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return (Promise)mapResult(result -> result.mapTuple((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5, (Function)mapper6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        Promise<T> mapThen(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return (Promise)mapResult(result -> result.mapThen((Function)mapper1, (Function)mapper2, function));
    }
    public default <T1, T2, T3, T> 
        Promise<T> mapThen(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return (Promise)mapResult(result -> result.mapThen((Function)mapper1, (Function)mapper2, (Function)mapper3, function));
    }
    public default <T1, T2, T3, T4, T> 
        Promise<T> mapThen(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return (Promise)mapResult(result -> result.mapThen((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, function));
    }
    public default <T1, T2, T3, T4, T5, T> 
        Promise<T> mapThen(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return (Promise)mapResult(result -> result.mapThen((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5, function));
    }
    public default <T1, T2, T3, T4, T5, T6, T> 
        Promise<T> mapThen(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return (Promise)mapResult(result -> result.mapThen((Function)mapper1, (Function)mapper2, (Function)mapper3, (Function)mapper4, (Function)mapper5, (Function)mapper6, function));
    }
    
    // -- Generated with: GeneratorFunctorMapToTupleToObject --
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return (Promise)mapResult(result -> result.mapToMap(key, (Function)mapper));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5,
                            key6, (Function)mapper6));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5,
                            key6, (Function)mapper6,
                            key7, (Function)mapper7));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5,
                            key6, (Function)mapper6,
                            key7, (Function)mapper7,
                            key8, (Function)mapper8));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5,
                            key6, (Function)mapper6,
                            key7, (Function)mapper7,
                            key8, (Function)mapper8,
                            key9, (Function)mapper9));
    }
    
    public default <KEY, VALUE> Promise<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9,
            KEY key10, Function<? super DATA, ? extends VALUE> mapper10) {
        return (Promise)mapResult(result -> 
                    result.mapToMap(
                            key1, (Function)mapper1,
                            key2, (Function)mapper2,
                            key3, (Function)mapper3,
                            key4, (Function)mapper4,
                            key5, (Function)mapper5,
                            key6, (Function)mapper6,
                            key7, (Function)mapper7,
                            key8, (Function)mapper8,
                            key9, (Function)mapper9,
                            key10, (Function)mapper10));
    }
    
}
