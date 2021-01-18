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
package functionalj.stream.intstream;

import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


public interface AsIntStreamPlusWithMatch {
    
    public IntStreamPlus intStreamPlus();
    
    
    //-- Match --

    /** Check if any element match the predicate */
    @Terminal
    public default boolean anyMatch(IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .anyMatch(predicate);
    }
    
    /** Check if all elements match the predicate */
    @Terminal
    public default boolean allMatch(IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .allMatch(predicate);
    }
    
    /** Check if none of the elements match the predicate */
    @Terminal
    public default boolean noneMatch(IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .noneMatch(predicate);
    }
    
    /** Returns the sequentially first element */
    @Terminal
    public default OptionalInt findFirst() {
        val streamPlus = intStreamPlus();
        return streamPlus
                .findFirst();
    }
    
    /** Returns the any element */
    @Terminal
    public default OptionalInt findAny() {
        val streamPlus = intStreamPlus();
        return streamPlus
                .findAny();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt findLast() {
        val streamPlus = intStreamPlus();
        return streamPlus
                .findLast();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt firstResult() {
        val streamPlus = intStreamPlus();
        return streamPlus
                .firstResult();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt lastResult() {
        val streamPlus = intStreamPlus();
        return streamPlus
                .lastResult();
    }
    
    /** Return the first element that matches the predicate. */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(
            IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(predicate)
                .findFirst();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    public default OptionalInt findAny(
            IntPredicate predicate) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(predicate)
                .findAny();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    @Sequential
    public default OptionalInt findFirst(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findFirst();
    }
    
    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    @Terminal
    public default <T> OptionalInt findAny(
            IntUnaryOperator mapper, 
            IntPredicate     theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findAny();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    @Sequential
    public default <T> OptionalInt findFirstBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>     theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filterAsObject(mapper, theCondition)
                .findFirst();
    }
    
    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    @Terminal
    public default <T> OptionalInt findAnyBy(
            IntFunction<? extends T> mapper, 
            Predicate<? super T>      theCondition) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .filterAsObject(mapper, theCondition)
                .findAny();
    }
    
    //== Contains ==
    
    /** Check if the list contains all the given values */
    public default boolean containsAllOf(int ... values) {
        val set = new HashSet<Integer>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = intStreamPlus();
        return streamPlus
                .peek(set::remove)
                .anyMatch(__ -> set.isEmpty());
    }
    
    public default boolean containsAnyOf(int ... values) {
        return anyMatch(each -> IntStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(int ... values) {
        return noneMatch(each -> IntStreamPlus.of(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
}
