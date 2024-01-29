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
package functionalj.list;

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
import java.util.function.BiFunction;
import java.util.function.Function;

import functionalj.function.Func10;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import lombok.val;

public interface FuncListWithMapThen<DATA> extends AsFuncList<DATA> {
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, BiFunction<T1, T2, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Func3<T1, T2, T3, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Func4<T1, T2, T3, T4, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Func5<T1, T2, T3, T4, T5, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Function<? super DATA, T6> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Function<? super DATA, T6> mapper6, Function<? super DATA, T7> mapper7, Func7<T1, T2, T3, T4, T5, T6, T7, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Function<? super DATA, T6> mapper6, Function<? super DATA, T7> mapper7, Function<? super DATA, T8> mapper8, Func8<T1, T2, T3, T4, T5, T6, T7, T8, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Function<? super DATA, T6> mapper6, Function<? super DATA, T7> mapper7, Function<? super DATA, T8> mapper8, Function<? super DATA, T9> mapper9, Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, merger));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> FuncList<T> mapThen(Function<? super DATA, T1> mapper1, Function<? super DATA, T2> mapper2, Function<? super DATA, T3> mapper3, Function<? super DATA, T4> mapper4, Function<? super DATA, T5> mapper5, Function<? super DATA, T6> mapper6, Function<? super DATA, T7> mapper7, Function<? super DATA, T8> mapper8, Function<? super DATA, T9> mapper9, Function<? super DATA, T10> mapper10, Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> merger) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, mapper10, merger));
    }
    
}
