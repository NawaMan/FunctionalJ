// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.intlist;

import static functionalj.list.intlist.IntFuncList.deriveToObj;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

import functionalj.function.Func10;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.list.FuncList;

public interface IntFuncListWithMapThen extends AsIntFuncList {
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, BiFunction<T1, T2, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, Func3<T1, T2, T3, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, Func4<T1, T2, T3, T4, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, Func5<T1, T2, T3, T4, T5, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, Func7<T1, T2, T3, T4, T5, T6, T7, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8, Func8<T1, T2, T3, T4, T5, T6, T7, T8, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8, IntFunction<? extends T9> mapper9, Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, function));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> FuncList<T> mapThen(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8, IntFunction<? extends T9> mapper9, IntFunction<? extends T10> mapper10, Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> function) {
        return deriveToObj(this, stream -> stream.mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, mapper10, function));
    }
    
}
