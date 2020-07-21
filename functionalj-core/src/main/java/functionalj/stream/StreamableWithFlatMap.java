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

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StreamableWithFlatMap<DATA> extends AsStreamable<DATA> {
    
    /** FlatMap with the given mapper. */
    public default <T> Streamable<T> flatMapToObj(Function<? super DATA, ? extends Streamable<? extends T>> mapper) {
        return Streamable.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).stream()));
    }
    
    /** FlatMap with the given mapper for only the value that pass the condition. */
    public default Streamable<DATA> flatMapOnly(
            Predicate<? super DATA>                            checker, 
            Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return Streamable.deriveFrom(this, stream -> {
            Function<? super DATA, ? extends Stream<DATA>> newMapper = value -> mapper.apply(value).stream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA>                         checker, 
            Function<? super DATA, ? extends Streamable<T>> mapper, 
            Function<? super DATA, ? extends Streamable<T>> elseMapper) {
        return Streamable.deriveFrom(this, stream -> {
            Function<? super DATA, Stream<T>> newMapper     = value -> mapper.apply(value).stream();
            Function<? super DATA, Stream<T>> newElseMapper = value -> elseMapper.apply(value).stream();
            return stream.flatMapIf(checker, newMapper, newElseMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default <T> Streamable<T> flatMapToObjIf(
            Predicate<? super DATA>               checker, 
            Function<? super DATA, Streamable<T>> mapper, 
            Function<? super DATA, Streamable<T>> elseMapper) {
        return flatMapIf(checker, mapper, elseMapper);
    }
    
}
