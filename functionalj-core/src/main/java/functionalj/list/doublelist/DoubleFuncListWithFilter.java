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
package functionalj.list.doublelist;

import static functionalj.list.doublelist.DoubleFuncList.deriveFrom;
import java.util.Collection;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import functionalj.function.IntDoublePredicate;

public interface DoubleFuncListWithFilter extends AsDoubleFuncList {
    
    /**
     * Map each value to an int and used it to filter the value.
     */
    public default DoubleFuncList filterAsInt(DoubleToIntFunction mapper, IntPredicate predicate) {
        return deriveFrom(this, stream -> stream.filterAsInt(mapper, predicate));
    }
    
    /**
     * Map each value to a long and used it to filter the value.
     */
    public default DoubleFuncList filterAsLong(DoubleToLongFunction mapper, LongPredicate predicate) {
        return deriveFrom(this, stream -> stream.filterAsLong(mapper, predicate));
    }
    
    /**
     * Map each value to a double and used it to filter the value.
     */
    public default DoubleFuncList filterAsDouble(DoubleUnaryOperator mapper, DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filterAsDouble(mapper, predicate));
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> DoubleFuncList filterAsObject(DoubleFunction<T> mapper, Predicate<? super T> predicate) {
        return deriveFrom(this, stream -> stream.filterAsObject(mapper, predicate));
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> DoubleFuncList filter(DoubleUnaryOperator mapper, DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    /**
     * Filter value with its index.
     */
    public default DoubleFuncList filterWithIndex(IntDoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.filterWithIndex(predicate));
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> DoubleFuncList filterNonNull(DoubleFunction<T> mapper) {
        return deriveFrom(this, stream -> stream.filterNonNull(mapper));
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> DoubleFuncList excludeNull(DoubleFunction<T> mapper) {
        return deriveFrom(this, stream -> stream.excludeNull(mapper));
    }
    
    /**
     * Filter only the value that is in the given items.
     */
    public default DoubleFuncList filterIn(double... items) {
        return deriveFrom(this, stream -> stream.filterIn(items));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default DoubleFuncList filterIn(DoubleFuncList collection) {
        return deriveFrom(this, stream -> stream.filterIn(collection));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default DoubleFuncList filterIn(Collection<Double> collection) {
        return deriveFrom(this, stream -> stream.filterIn(collection));
    }
    
    /**
     * Filter only the value that the predicate returns false.
     */
    public default DoubleFuncList exclude(DoublePredicate predicate) {
        return deriveFrom(this, stream -> stream.exclude(predicate));
    }
    
    /**
     * Filter only the value that is not in the given items.
     */
    public default DoubleFuncList excludeIn(double... items) {
        return deriveFrom(this, stream -> stream.excludeIn(items));
    }
    
    /**
     * Filter out any value that is in the given collection.
     */
    public default DoubleFuncList excludeIn(DoubleFuncList collection) {
        return deriveFrom(this, stream -> stream.excludeIn(collection));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default DoubleFuncList excludeIn(Collection<Double> collection) {
        return deriveFrom(this, stream -> stream.excludeIn(collection));
    }
}
