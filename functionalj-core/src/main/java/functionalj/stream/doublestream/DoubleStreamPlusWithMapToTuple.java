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
package functionalj.stream.doublestream;

import java.util.function.DoubleFunction;

import functionalj.stream.StreamPlus;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface DoubleStreamPlusWithMapToTuple
        extends DoubleStreamPlusWithMapThen {
    
    /**Map the value into different values and then combine them into a tuple. */
    public default <T1, T2>
        StreamPlus<Tuple2<T1, T2>> mapToTuple(
                DoubleFunction<? extends T1> mapper1,
                DoubleFunction<? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3>
        StreamPlus<Tuple3<T1, T2, T3>> mapToTuple(
                DoubleFunction<? extends T1> mapper1,
                DoubleFunction<? extends T2> mapper2,
                DoubleFunction<? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4>
        StreamPlus<Tuple4<T1, T2, T3, T4>> mapToTuple(
                DoubleFunction<? extends T1> mapper1,
                DoubleFunction<? extends T2> mapper2,
                DoubleFunction<? extends T3> mapper3,
                DoubleFunction<? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4, T5>
        StreamPlus<Tuple5<T1, T2, T3, T4, T5>> mapToTuple(
                DoubleFunction<? extends T1> mapper1,
                DoubleFunction<? extends T2> mapper2,
                DoubleFunction<? extends T3> mapper3,
                DoubleFunction<? extends T4> mapper4,
                DoubleFunction<? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    
    /** Map the value into different values and then combine them with the combinator. */
    public default <T1, T2, T3, T4, T5, T6>
        StreamPlus<Tuple6<T1, T2, T3, T4, T5, T6>> mapToTuple(
                DoubleFunction<? extends T1> mapper1,
                DoubleFunction<? extends T2> mapper2,
                DoubleFunction<? extends T3> mapper3,
                DoubleFunction<? extends T4> mapper4,
                DoubleFunction<? extends T5> mapper5,
                DoubleFunction<? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
}
