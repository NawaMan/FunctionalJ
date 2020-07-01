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
package functionalj.list;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.map.FuncMap;
import functionalj.stream.Streamable;
import functionalj.stream.StreamableWithMapToMap;

public interface FuncListWithMapToMap<DATA>
        extends StreamableWithMapToMap<DATA> {
    
    public <TARGET> FuncList<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action);
    
    
    //== mapToMap ==
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, VALUE> mapper) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key, mapper);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8,
            KEY key9, Function<? super DATA, VALUE> mapper9) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8,
                              key9, mapper9);
        });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8,
            KEY key9, Function<? super DATA, VALUE> mapper9,
            KEY key10, Function<? super DATA, VALUE> mapper10) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8,
                              key9, mapper9,
                              key10, mapper10);
        });
    }
}
