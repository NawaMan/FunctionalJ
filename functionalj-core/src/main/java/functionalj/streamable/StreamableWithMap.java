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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;
import static functionalj.streamable.Streamable.deriveToObj;

import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamableWithMap<DATA> extends AsStreamable<DATA> {
    
    
    /** Map the value using the mapper. */
    public default <T> Streamable<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return deriveToObj(this, stream -> stream.mapToObj(mapper));
    }
    
    /** Map the value using the mapper only when the condition is true. */
    public default Streamable<DATA> mapOnly(
            Predicate<? super DATA>      condition, 
            Function<? super DATA, DATA> mapper) {
        return deriveFrom(this, stream -> stream.mapOnly(condition, mapper));
    }
    
    /** Map the value using the mapper only when the condition is true. Otherwise, map using the elseMapper. */
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>   condition, 
            Function<? super DATA, T> mapper,
            Function<? super DATA, T> elseMapper) {
        return deriveFrom(this, stream -> stream.mapIf(condition, mapper, elseMapper));
    }
    
    /** Map the value using the mapper only when the condition is true. Otherwise, map using the elseMapper.  */
    public default <T> Streamable<T> mapToObjIf(
            Predicate<? super DATA>   condition, 
            Function<? super DATA, T> mapper,
            Function<? super DATA, T> elseMapper) {
        return deriveToObj(this, stream -> stream.mapToObjIf(condition, mapper, elseMapper));
    }
    
}
