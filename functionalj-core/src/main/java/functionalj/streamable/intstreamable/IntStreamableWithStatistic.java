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
package functionalj.streamable.intstreamable;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import functionalj.tuple.Tuple2;

public interface IntStreamableWithStatistic extends AsIntStreamable {
    
    /** @return  the size of the stream */
    public default int size() {
        return (int)intStream().count();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default <D extends Comparable<D>> OptionalInt minBy(
            IntFunction<D> mapper) {
        return intStreamPlus()
                .minBy(mapper);
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default <D extends Comparable<D>> OptionalInt maxBy(
            IntFunction<D> mapper) {
        return intStreamPlus()
                .maxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    public default <D> OptionalInt minBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return intStreamPlus()
                .minBy(mapper, comparator);
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    public default <D> OptionalInt maxBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return intStreamPlus()
                .maxBy(mapper, comparator);
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    public default Tuple2<OptionalInt, OptionalInt> minMax(
            IntBinaryOperator comparator) {
        return intStreamPlus()
                .minMax(comparator);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Tuple2<OptionalInt, OptionalInt> minMaxBy(
            IntFunction<D> mapper) {
        return intStreamPlus()
                .minMaxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    public default <D> Tuple2<OptionalInt, OptionalInt> minMaxBy(
            IntFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return intStreamPlus()
                .minMaxBy(mapper, comparator);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntFunction<D> mapper) {
        return minIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntFunction<D> mapper) {
        return maxIndexBy(i -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return intStreamPlus()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return intStreamPlus()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexOf(
            IntPredicate   filter,
            IntUnaryOperator mapper) {
        return intStreamPlus()
                .mapWithIndex()
                .map   (t -> t.map2ToInt(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsInt(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty())
                ;
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return intStreamPlus()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
}