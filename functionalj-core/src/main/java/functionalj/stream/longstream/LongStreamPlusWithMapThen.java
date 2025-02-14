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
package functionalj.stream.longstream;

import java.util.function.BiFunction;
import java.util.function.LongFunction;

import functionalj.function.Func10;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.stream.StreamPlus;
import lombok.val;

public interface LongStreamPlusWithMapThen {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, BiFunction<T1, T2, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v = function.apply(v1, v2);
            return v;
        });
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, Func3<T1, T2, T3, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v = function.apply(v1, v2, v3);
            return v;
        });
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, Func4<T1, T2, T3, T4, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    
    /**
     * Map the value into different values and then combine them with the combinator.
     */
    public default <T1, T2, T3, T4, T5, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, Func5<T1, T2, T3, T4, T5, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, LongFunction<? extends T6> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
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
    
    public default <T1, T2, T3, T4, T5, T6, T7, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, LongFunction<? extends T6> mapper6, LongFunction<? extends T7> mapper7, Func7<T1, T2, T3, T4, T5, T6, T7, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v7 = mapper7.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6, v7);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, LongFunction<? extends T6> mapper6, LongFunction<? extends T7> mapper7, LongFunction<? extends T8> mapper8, Func8<T1, T2, T3, T4, T5, T6, T7, T8, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v7 = mapper7.apply(each);
            val v8 = mapper8.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6, v7, v8);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, LongFunction<? extends T6> mapper6, LongFunction<? extends T7> mapper7, LongFunction<? extends T8> mapper8, LongFunction<? extends T9> mapper9, Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v7 = mapper7.apply(each);
            val v8 = mapper8.apply(each);
            val v9 = mapper9.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> StreamPlus<T> mapThen(LongFunction<? extends T1> mapper1, LongFunction<? extends T2> mapper2, LongFunction<? extends T3> mapper3, LongFunction<? extends T4> mapper4, LongFunction<? extends T5> mapper5, LongFunction<? extends T6> mapper6, LongFunction<? extends T7> mapper7, LongFunction<? extends T8> mapper8, LongFunction<? extends T9> mapper9, LongFunction<? extends T10> mapper10, Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T> function) {
        val streamPlus = longStreamPlus();
        return streamPlus.mapToObj(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v7 = mapper7.apply(each);
            val v8 = mapper8.apply(each);
            val v9 = mapper9.apply(each);
            val v10 = mapper10.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
            return v;
        });
    }
    
}
