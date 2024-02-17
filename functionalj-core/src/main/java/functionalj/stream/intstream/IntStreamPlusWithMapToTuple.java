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
package functionalj.stream.intstream;

import java.util.function.IntFunction;

import functionalj.stream.StreamPlus;
import functionalj.tuple.Tuple10;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import functionalj.tuple.Tuple7;
import functionalj.tuple.Tuple8;
import functionalj.tuple.Tuple9;

public interface IntStreamPlusWithMapToTuple extends IntStreamPlusWithMapThen {
    
    /**
     * Map the value into different values and then combine them into a tuple.
     */
    public default <T1, T2> StreamPlus<Tuple2<T1, T2>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2) {
        return mapThen(mapper1, mapper2, (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3> StreamPlus<Tuple3<T1, T2, T3>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3, (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4> StreamPlus<Tuple4<T1, T2, T3, T4>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5> StreamPlus<Tuple5<T1, T2, T3, T4, T5>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6> StreamPlus<Tuple6<T1, T2, T3, T4, T5, T6>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7> StreamPlus<Tuple7<T1, T2, T3, T4, T5, T6, T7>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, (v1, v2, v3, v4, v5, v6, v7) -> Tuple7.of(v1, v2, v3, v4, v5, v6, v7));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8> StreamPlus<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, (v1, v2, v3, v4, v5, v6, v7, v8) -> Tuple8.of(v1, v2, v3, v4, v5, v6, v7, v8));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamPlus<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8, IntFunction<? extends T9> mapper9) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, (v1, v2, v3, v4, v5, v6, v7, v8, v9) -> Tuple9.of(v1, v2, v3, v4, v5, v6, v7, v8, v9));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> StreamPlus<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> mapToTuple(IntFunction<? extends T1> mapper1, IntFunction<? extends T2> mapper2, IntFunction<? extends T3> mapper3, IntFunction<? extends T4> mapper4, IntFunction<? extends T5> mapper5, IntFunction<? extends T6> mapper6, IntFunction<? extends T7> mapper7, IntFunction<? extends T8> mapper8, IntFunction<? extends T9> mapper9, IntFunction<? extends T10> mapper10) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, mapper10, (v1, v2, v3, v4, v5, v6, v7, v8, v9, v10) -> Tuple10.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10));
    }
    
}
