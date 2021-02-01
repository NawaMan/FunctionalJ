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
package functionalj.list.doublelist;

import static functionalj.list.doublelist.AsDoubleFuncListHelper.funcListOf;
import static functionalj.list.doublelist.AsDoubleFuncListHelper.streamPlusOf;

import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.tuple.Tuple2;

public interface DoubleFuncListWithStatistic extends AsDoubleFuncList {
    
    /** @return  the size of the stream */
    public default int size() {
        return (int)funcListOf(this)
                .count();
    }
    
    /** @return the product of all the number */
    public default double sum() {
        return streamPlusOf(this)
                .sum();
    }
    
    /** @return the product of all the number */
    public default OptionalDouble product() {
        return streamPlusOf(this)
                .product();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default OptionalDouble min() {
        return streamPlusOf(this)
                .min();
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default OptionalDouble max() {
        return streamPlusOf(this)
                .max();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default <D extends Comparable<D>> OptionalDouble minBy(DoubleFunction<D> mapper) {
        return streamPlusOf(this)
                .minBy(mapper);
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default <D extends Comparable<D>> OptionalDouble maxBy(DoubleFunction<D> mapper) {
        return streamPlusOf(this)
                .maxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    public default <D> OptionalDouble minBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        return streamPlusOf(this)
                .minBy(mapper, comparator);
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    public default <D> OptionalDouble maxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        return streamPlusOf(this)
                .maxBy(mapper, comparator);
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    public default Tuple2<OptionalDouble, OptionalDouble> minMax(
            DoubleDoubleToIntFunctionPrimitive comparator) {
        return streamPlusOf(this)
                .minMax(comparator);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(
            DoubleFunction<D> mapper) {
        return streamPlusOf(this)
                .minMaxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    public default <D> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        return streamPlusOf(this)
                .minMaxBy(mapper, comparator);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexBy(DoubleFunction<D> mapper) {
        return minIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(DoubleFunction<D> mapper) {
        return maxIndexBy(i -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            DoublePredicate   filter,
            DoubleFunction<D> mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            DoublePredicate   filter,
            DoubleFunction<D> mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexOf(
            DoublePredicate     filter,
            DoubleUnaryOperator mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .map   (t -> t.map2ToDouble(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsDouble(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty())
                ;
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
            DoublePredicate     filter,
            DoubleUnaryOperator mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .map   (t -> t.map2ToDouble(mapper))
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.applyAsDouble(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty())
                ;
    }
    
}
