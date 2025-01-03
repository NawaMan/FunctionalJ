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
package functionalj.list;

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
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
import lombok.val;

public interface FuncListWithFilter<DATA> extends AsFuncList<DATA> {
    
    /**
     * Map each value to an int and used it to filter the value.
     */
    public default FuncList<DATA> filterAsInt(ToIntFunction<? super DATA> mapper, IntPredicate predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterAsInt(mapper, predicate));
    }
    
    /**
     * Map each value to a long and used it to filter the value.
     */
    public default FuncList<DATA> filterAsLong(ToLongFunction<? super DATA> mapper, LongPredicate predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterAsLong(mapper, predicate));
    }
    
    /**
     * Map each value to a double and used it to filter the value.
     */
    public default FuncList<DATA> filterAsDouble(ToDoubleFunction<? super DATA> mapper, DoublePredicate predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterAsDouble(mapper, predicate));
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> FuncList<DATA> filterAsObject(Function<? super DATA, T> mapper, Predicate<? super T> predicate) {
        return filter(mapper, predicate);
    }
    
    /**
     * Map each value to another object and used it to filter the value.
     */
    public default <T> FuncList<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filter(mapper, predicate));
    }
    
    /**
     * Filter the element that is only the specific class.
     */
    public default <T> FuncList<DATA> filter(Class<T> clzz) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filter(clzz));
    }
    
    /**
     * Case the value to the given class and used it to filter the value.
     * If the value is not of the type (null included), it will be filtered out.
     */
    public default <T> FuncList<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filter(clzz, theCondition));
    }
    
    /**
     * Filter value with its index.
     */
    public default FuncList<DATA> filterWithIndex(IntObjBiFunction<? super DATA, Boolean> predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterWithIndex(predicate));
    }
    
    /**
     * Filter value that is not null.
     */
    public default FuncList<DATA> filterNonNull() {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterNonNull());
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> FuncList<DATA> filterNonNull(Function<? super DATA, T> mapper) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterNonNull(mapper));
    }
    
    /**
     * Filter value that is not null.
     */
    public default FuncList<DATA> excludeNull() {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.excludeNull());
    }
    
    /**
     * Map the value to another object and filter the one that is not null.
     */
    public default <T> FuncList<DATA> excludeNull(Function<? super DATA, T> mapper) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.excludeNull(mapper));
    }
    
    /**
     * Filter only the value that is in the given items.
     */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> filterOnly(DATA... items) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterOnly(items));
    }
    
    /**
     * Filter only the value that is in the given collections.
     */
    public default FuncList<DATA> filterIn(Collection<? super DATA> collection) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.filterIn(collection));
    }
    
    /**
     * Filter only the value that the predicate returns false.
     */
    public default FuncList<DATA> exclude(Predicate<? super DATA> predicate) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.exclude(predicate));
    }
    
    /**
     * Filter out any value that is in the given items.
     */
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> excludeAny(DATA... items) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.excludeAny(items));
    }
    
    /**
     * Filter out any value that is in the given collection.
     */
    public default FuncList<DATA> excludeIn(Collection<? super DATA> collection) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.excludeIn(collection));
    }
}
