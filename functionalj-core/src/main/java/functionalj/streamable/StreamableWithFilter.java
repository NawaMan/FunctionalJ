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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;

import java.util.Collection;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import functionalj.function.IntObjBiFunction;
import functionalj.stream.AsStreamable;

public interface StreamableWithFilter<DATA> extends AsStreamable<DATA> {
    
    /** Map each value to an int and used it to filter the value. */
    public default Streamable<DATA> filterAsInt(
            ToIntFunction<? super DATA> mapper, 
            IntPredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsInt(mapper, predicate));
    }
    
    /** Map each value to a long and used it to filter the value. */
    public default Streamable<DATA> filterAsLong(
            ToLongFunction<? super DATA> mapper, 
            LongPredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsLong(mapper, predicate));
    }
    
    /** Map each value to a double and used it to filter the value. */
    public default Streamable<DATA> filterAsDouble(
            ToDoubleFunction<? super DATA> mapper, 
            DoublePredicate                predicate) {
        return deriveFrom(this, stream -> stream.filterAsDouble(mapper, predicate));
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> Streamable<DATA> filterAsObject(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(mapper, predicate);
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    /** Filter the element that is only the specific class. */
    public default <T> Streamable<DATA> filter(Class<T> clzz) {
        return deriveFrom(this, stream -> stream.filter(clzz));
    }
    
    /**
     * Case the value to the given class and used it to filter the value.
     * If the value is not of the type (null included), it will be filtered out.
     */
    public default <T> Streamable<DATA> filter(
            Class<T>             clzz, 
            Predicate<? super T> theCondition) {
        return deriveFrom(this, stream -> stream.filter(clzz, theCondition));
    }
    
    /** Filter value with its index. */
    public default Streamable<DATA> filterWithIndex(IntObjBiFunction<? super DATA, Boolean> predicate) {
        return deriveFrom(this, stream -> stream.filterWithIndex(predicate));
    }
    
    /** Filter value that is not null. */
    public default Streamable<DATA> filterNonNull() {
        return deriveFrom(this, stream -> stream.filterNonNull());
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> Streamable<DATA> filterNonNull(Function<? super DATA, T> mapper) {
        return deriveFrom(this, stream -> stream.filterNonNull(mapper));
    }
    
    /** Filter value that is not null. */
    public default Streamable<DATA> excludeNull() {
        return deriveFrom(this, stream -> stream.excludeNull());
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> Streamable<DATA> excludeNull(Function<? super DATA, T> mapper) {
        return deriveFrom(this, stream -> stream.excludeNull(mapper));
    }
    
    /** Filter only the value that is in the given items. */
    @SuppressWarnings("unchecked")
    public default Streamable<DATA> filterIn(DATA ... items) {
        return deriveFrom(this, stream -> stream.filterIn(items));
    }
    
    /** Filter only the value that is in the given collections. */
    public default Streamable<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveFrom(this, stream -> stream.filterIn(collection));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default Streamable<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveFrom(this, stream -> stream.exclude(predicate));
    }
    
    /** Filter out any value that is in the given collection. */
    public default Streamable<DATA> excludeIn(Collection<? super DATA> collection) {
        return deriveFrom(this, stream -> stream.excludeIn(collection));
    }
    
}
