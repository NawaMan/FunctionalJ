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
package functionalj.streamable.doublestreamable;

import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.tuple.Tuple2;

public interface DoubleStreamableWithStatistic extends AsDoubleStreamable {
    
    /** @return  the size of the stream */
    public default int size() {
        return (int)doubleStream().count();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default <D extends Comparable<D>> OptionalDouble minBy(
            DoubleFunction<D> mapper) {
        return doubleStream()
                .minBy(mapper);
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default <D extends Comparable<D>> OptionalDouble maxBy(
            DoubleFunction<D> mapper) {
        return doubleStream()
                .maxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    public default <D> OptionalDouble minBy(
            DoubleFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return doubleStream()
                .minBy(mapper, comparator);
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    public default <D> OptionalDouble maxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        return doubleStream()
                .maxBy(mapper, comparator);
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    public default Tuple2<OptionalDouble, OptionalDouble> minMax(
            DoubleDoubleToIntFunctionPrimitive comparator) {
        return doubleStream()
                .minMax(comparator);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(
            DoubleFunction<D> mapper) {
        return doubleStream()
                .minMaxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    public default <D> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        return doubleStream()
                .minMaxBy(mapper, comparator);
    }
    
    public default <D extends Comparable<D>> OptionalDouble minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalDouble maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalDouble minIndexBy(DoubleFunction<D> mapper) {
        return minIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalDouble maxIndexBy(DoubleFunction<D> mapper) {
        return maxIndexBy(i -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalDouble minIndexBy(
            DoublePredicate   filter,
            DoubleFunction<D> mapper) {
        return doubleStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalDouble.of(t._1))
                .orElse(OptionalDouble.empty());
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalDouble maxIndexBy(
            DoublePredicate   filter,
            DoubleFunction<D> mapper) {
        return doubleStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalDouble.of(t._1))
                .orElse(OptionalDouble.empty());
    }
    
    public default <D extends Comparable<D>> OptionalDouble minIndexOf(
            DoublePredicate     filter,
            DoubleUnaryOperator mapper) {
        return doubleStream()
                .mapWithIndex()
                .map   (t -> t.map2ToDouble(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsDouble(t._2))
                .map   (t -> OptionalDouble.of(t._1))
                .orElse(OptionalDouble.empty())
                ;
    }
    
    public default <D extends Comparable<D>> OptionalDouble maxIndexOf(
            DoublePredicate   filter,
            DoubleFunction<D> mapper) {
        return doubleStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalDouble.of(t._1))
                .orElse(OptionalDouble.empty());
    }
    
}
