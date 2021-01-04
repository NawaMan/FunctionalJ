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
package functionalj.streamable.doublestreamable;

import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveFrom;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import functionalj.function.IntDoublePredicate;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


public interface DoubleStreamableWithFilter extends AsDoubleStreamable {
    
    /** Map each value to an int and used it to filter the value. */
    public default DoubleStreamable filterAsInt(
            DoubleToIntFunction mapper,
            IntPredicate        predicate) {
        return deriveFrom(this, stream -> stream.filterAsInt(mapper, predicate));
    }
    
    /** Map each value to a long and used it to filter the value. */
    public default DoubleStreamable filterAsLong(
            DoubleToLongFunction mapper,
            LongPredicate        predicate) {
        return deriveFrom(this, stream -> stream.filterAsLong(mapper, predicate));
    }
    
    /** Map each value to a double and used it to filter the value. */
    public default DoubleStreamable filterAsDouble(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        return deriveFrom(this, stream -> stream.filterAsDouble(mapper, predicate));
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> DoubleStreamable filterAsObject(
            DoubleFunction<T>    mapper,
            Predicate<? super T> predicate) {
        DoublePredicate newMapper = value -> {
            val newValue = mapper.apply(value);
            return predicate.test(newValue);
        };
        return deriveFrom(this, stream -> stream.filter(newMapper));
    }
    
    /** Map each value to another object and used it to filter the value. */
    public default <T> DoubleStreamable filter(
            DoubleUnaryOperator mapper,
            DoublePredicate     predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    /** Filter value with its index. */
    public default DoubleStreamable filterWithIndex(
            IntDoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filterWithIndex(predicate));
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> DoubleStreamable filterNonNull(DoubleFunction<T> mapper) {
        return deriveFrom(this, stream -> stream.filterNonNull(mapper));
    }
    
    /** Map the value to another object and filter the one that is not null. */
    public default <T> DoubleStreamable excludeNull(DoubleFunction<T> mapper) {
        return deriveFrom(this, stream -> stream.excludeNull(mapper));
    }
    
    /** Filter only the value that is in the given items. */
    public default DoubleStreamable filterIn(double ... items) {
        return deriveFrom(this, stream -> stream.filterIn(items));
    }
    
    /** Filter only the value that is in the given collections. */
    public default DoubleStreamable filterIn(DoubleFuncList collection) {
        return deriveFrom(this, stream -> stream.filterIn(collection));
    }
    
    /** Filter only the value that the predicate returns false. */
    public default DoubleStreamable exclude(DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.exclude(predicate));
    }
    
    /** Filter out any value that is in the given collection. */
    public default DoubleStreamable excludeIn(DoubleFuncList collection) {
        return deriveFrom(this, stream -> stream.excludeIn(collection));
    }
    
}
