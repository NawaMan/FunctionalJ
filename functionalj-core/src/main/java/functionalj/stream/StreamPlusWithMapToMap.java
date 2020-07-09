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
package functionalj.stream;

import static functionalj.map.FuncMap.mapOf;

import java.util.function.Function;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import lombok.val;

public interface StreamPlusWithMapToMap<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key      the key.
     * @param mapper   the mapper.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(key, mapper.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param key5     the key 5.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @param mapper5  the mapper 5.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> ImmutableMap.of(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param key5     the key 5.
     * @param key6     the key 6.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @param mapper5  the mapper 5.
     * @param mapper6  the mapper 6.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapToObj(data -> mapOf(
                    key1, mapper1.apply(data),
                    key2, mapper2.apply(data),
                    key3, mapper3.apply(data),
                    key4, mapper4.apply(data),
                    key5, mapper5.apply(data),
                    key6, mapper6.apply(data)));
    }
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param key5     the key 5.
     * @param key6     the key 6.
     * @param key7     the key 7.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @param mapper5  the mapper 5.
     * @param mapper6  the mapper 6.
     * @param mapper7  the mapper 7.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        val streamPlus = streamPlus();
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
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param key5     the key 5.
     * @param key6     the key 6.
     * @param key7     the key 7.
     * @param key8     the key 8.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @param mapper5  the mapper 5.
     * @param mapper6  the mapper 6.
     * @param mapper7  the mapper 7.
     * @param mapper8  the mapper 8.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        val streamPlus = streamPlus();
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
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>    the key type.
     * @param <VALUE>  the value type.
     * @param key1     the key 1.
     * @param key2     the key 2.
     * @param key3     the key 3.
     * @param key4     the key 4.
     * @param key5     the key 5.
     * @param key6     the key 6.
     * @param key7     the key 7.
     * @param key8     the key 8.
     * @param key9     the key 9.
     * @param mapper1  the mapper 1.
     * @param mapper2  the mapper 2.
     * @param mapper3  the mapper 3.
     * @param mapper4  the mapper 4.
     * @param mapper5  the mapper 5.
     * @param mapper6  the mapper 6.
     * @param mapper7  the mapper 7.
     * @param mapper8  the mapper 8.
     * @param mapper9  the mapper 9.
     * @return         the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        val streamPlus = streamPlus();
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
    
    /**
     * Put mapped value and put it in the map with specific keys.
     * 
     * @param <KEY>     the key type.
     * @param <VALUE>   the value type.
     * @param key1      the key 1.
     * @param key2      the key 2.
     * @param key3      the key 3.
     * @param key4      the key 4.
     * @param key5      the key 5.
     * @param key6      the key 6.
     * @param key7      the key 7.
     * @param key8      the key 8.
     * @param key9      the key 9.
     * @param key10     the key 10.
     * @param mapper1   the mapper 1.
     * @param mapper2   the mapper 2.
     * @param mapper3   the mapper 3.
     * @param mapper4   the mapper 4.
     * @param mapper5   the mapper 5.
     * @param mapper6   the mapper 6.
     * @param mapper7   the mapper 7.
     * @param mapper8   the mapper 8.
     * @param mapper9   the mapper 9.
     * @param mapper10  the mapper 10.
     * @return          the stream of maps.
     */
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
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
        val streamPlus = streamPlus();
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
