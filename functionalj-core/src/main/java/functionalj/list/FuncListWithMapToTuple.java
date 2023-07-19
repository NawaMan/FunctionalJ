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
package functionalj.list;

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
import java.util.function.Function;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface FuncListWithMapToTuple<DATA> extends AsFuncList<DATA> {
    
    /**
     * Map the value into different values and then combine them into a tuple.
     */
    public default <T1, T2> FuncList<Tuple2<T1, T2>> mapToTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToTuple(mapper1, mapper2));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3> FuncList<Tuple3<T1, T2, T3>> mapToTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToTuple(mapper1, mapper2, mapper3));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4> FuncList<Tuple4<T1, T2, T3, T4>> mapToTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5> FuncList<Tuple5<T1, T2, T3, T4, T5>> mapToTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T6> FuncList<Tuple6<T1, T2, T3, T4, T5, T6>> mapToTuple(Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5, Function<? super DATA, ? extends T6> mapper6) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToTuple(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
}
