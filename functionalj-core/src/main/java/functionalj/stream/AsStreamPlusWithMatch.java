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
package functionalj.stream;

import static functionalj.stream.StreamPlus.streamOf;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.result.Result;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


public interface AsStreamPlusWithMatch<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    
    //-- Match --
    
    @Terminal
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .anyMatch(predicate);
    }
    
    @Terminal
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .allMatch(predicate);
    }
    
    @Terminal
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .noneMatch(predicate);
    }
    
    @Terminal
    public default Optional<DATA> findFirst() {
        val streamPlus = streamPlus();
        return streamPlus
                .findFirst();
    }
    
    @Terminal
    public default Optional<DATA> findAny() {
        val streamPlus = streamPlus();
        return streamPlus
                .findAny();
    }
    
    @Sequential
    @Terminal
    public default Optional<DATA> findLast() {
        val streamPlus = streamPlus();
        return streamPlus
                .findLast();
    }
    
    @Sequential
    @Terminal
    public default Result<DATA> firstResult() {
        val streamPlus = streamPlus();
        return streamPlus
                .firstResult();
    }
    
    @Sequential
    @Terminal
    public default Result<DATA> lastResult() {
        val streamPlus = streamPlus();
        return streamPlus
                .lastResult();
    }
    
    /** Return the first element that matches the predicate. */
    @Terminal
    @Sequential
    public default Optional<DATA> findFirst(
            Predicate<? super DATA> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(predicate)
                .findFirst();
    }
    
    /** Return the any element that matches the predicate. */
    @Terminal
    public default Optional<DATA> findAny(
            Predicate<? super DATA> predicate) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(predicate)
                .findAny();
    }
    
    /** Use the mapper, return the first element that its mapped value matches the predicate. */
    @Terminal
    @Sequential
    public default <T> Optional<DATA> findFirst(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findFirst();
    }
    
    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    @Terminal
    public default <T>  Optional<DATA> findAny(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        val streamPlus = streamPlus();
        return streamPlus
                .filter(mapper, theCondition)
                .findAny();
    }
    
    //== Contains ==
    
    /** Check if the list contains all the given values */
    @SuppressWarnings("unchecked")
    public default boolean containsAllOf(DATA ... values) {
        val set = new HashSet<DATA>(values.length);
        for (val value : values) {
            set.add(value);
        }
        val streamPlus = streamPlus();
        return streamPlus
                .peek(set::remove)
                .anyMatch(__ -> set.isEmpty());
    }
    
    @SuppressWarnings("unchecked")
    public default boolean containsAnyOf(DATA ... values) {
        return anyMatch(each -> streamOf(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("unchecked")
    public default boolean containsNoneOf(DATA ... values) {
        return noneMatch(each -> streamOf(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
}
