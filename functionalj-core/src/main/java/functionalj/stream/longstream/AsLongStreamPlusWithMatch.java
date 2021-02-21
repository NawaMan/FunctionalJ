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
package functionalj.stream.longstream;

import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;

import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


public interface AsLongStreamPlusWithMatch {
    
    public LongStreamPlus longStreamPlus();
    
    
    //-- Match --
    
    /** Check if any element match the predicate */
    @Terminal
    public default boolean anyMatch(LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .anyMatch(predicate);
    }
    
    /** Check if all elements match the predicate */
    @Terminal
    public default boolean allMatch(LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .allMatch(predicate);
    }
    
    /** Check if none of the elements match the predicate */
    @Terminal
    public default boolean noneMatch(LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .noneMatch(predicate);
    }
    
    /** Returns the sequentially first element */
    @Terminal
    public default OptionalLong findFirst() {
        val streamPlus = longStreamPlus();
        return streamPlus
                .findFirst();
    }
    
    /** Returns the any element */
    @Terminal
    public default OptionalLong findAny() {
        val streamPlus = longStreamPlus();
        return streamPlus
                .findAny();
    }
    
    @Sequential
    @Terminal
    public default OptionalLong findLast() {
        val streamPlus = longStreamPlus();
        return streamPlus
                .findLast();
    }
    
    @Sequential
    @Terminal
    public default OptionalLong firstResult() {
        val streamPlus = longStreamPlus();
        return streamPlus
                .firstResult();
    }
    
    @Sequential
    @Terminal
    public default OptionalLong lastResult() {
        val streamPlus = longStreamPlus();
        return streamPlus
                .lastResult();
    }
    
    /** Return the first element that matches the predicate. */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(
            LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filter(predicate)
                .findFirst();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    public default OptionalLong findAny(
            LongPredicate predicate) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filter(predicate)
                .findAny();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    @Sequential
    public default OptionalLong findFirst(
            LongUnaryOperator mapper, 
            LongPredicate     theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findFirst();
    }
    
    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    @Terminal
    public default <T> OptionalLong findAny(
            LongUnaryOperator mapper, 
            LongPredicate     theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findAny();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    @Sequential
    public default <T> OptionalLong findFirstBy(
            LongFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filterAsObject(mapper, theCondition)
                .findFirst();
    }
    
    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    @Terminal
    public default <T> OptionalLong findAnyBy(
            LongFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .filterAsObject(mapper, theCondition)
                .findAny();
    }
    
    //== Contains ==
    
    /** Check if the list contains all the given values */
    public default boolean containsAllOf(long ... values) {
        val set = new HashSet<Long>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = longStreamPlus();
        return streamPlus
                .peek(set::remove)
                .anyMatch(__ -> set.isEmpty());
    }
    
    public default boolean containsAnyOf(long ... values) {
        return anyMatch(each -> LongStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(long ... values) {
        return noneMatch(each -> LongStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
}
