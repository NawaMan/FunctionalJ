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
package functionalj.stream.intstream;

import static functionalj.map.FuncMap.mapOf;

import java.util.function.IntFunction;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.StreamPlus;
import lombok.val;


public interface IntStreamPlusWithMapToMap {
    
    public IntStreamPlus intStreamPlus();
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, IntFunction<? extends VALUE> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(key, mapper.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> ImmutableMap.of(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data),
                    key7, mapper7.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data),
                    key7, mapper7.apply(data),
                    key8, mapper8.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8,
            KEY key9, IntFunction<? extends VALUE> mapper9) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data),
                    key7, mapper7.apply(data),
                    key8, mapper8.apply(data),
                    key9, mapper9.apply(data)));
    }
    
    /** Put mapped value and put it in the map with specific keys. */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8,
            KEY key9, IntFunction<? extends VALUE> mapper9,
            KEY key10, IntFunction<? extends VALUE> mapper10) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data),
                    key7, mapper7.apply(data),
                    key8, mapper8.apply(data),
                    key9, mapper9.apply(data),
                    key10, mapper10.apply(data)));
    }
    
}
