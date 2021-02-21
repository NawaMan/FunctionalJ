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
package functionalj.list.longlist;

import static functionalj.list.longlist.AsLongFuncListHelper.funcListOf;
import static functionalj.list.longlist.AsLongFuncListHelper.streamPlusOf;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

import functionalj.function.LongComparator;
import functionalj.tuple.Tuple2;

public interface LongFuncListWithStatistic extends AsLongFuncList {
    
    /** @return  the size of the stream */
    public default int size() {
        return (int)funcListOf(this)
                .count();
    }
    
    /** @return the product of all the number */
    public default long sum() {
        return streamPlusOf(this)
                .sum();
    }
    
    /** @return the product of all the number */
    public default OptionalLong product() {
        return streamPlusOf(this)
                .product();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default OptionalLong min() {
        return streamPlusOf(this)
                .min();
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default OptionalLong max() {
        return streamPlusOf(this)
                .max();
    }
    
    /** Return the value whose mapped value is the smallest. */
    public default <D extends Comparable<D>> OptionalLong minBy(LongFunction<D> mapper) {
        return streamPlusOf(this)
                .minBy(mapper);
    }
    
    /** Return the value whose mapped value is the biggest. */
    public default <D extends Comparable<D>> OptionalLong maxBy(LongFunction<D> mapper) {
        return streamPlusOf(this)
                .maxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    public default <D> OptionalLong minBy(
            LongFunction<D>        mapper,
            Comparator<? super D> comparator) {
        return streamPlusOf(this)
                .minBy(mapper, comparator);
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    public default <D> OptionalLong maxBy(
            LongFunction<D>         mapper,
            Comparator<? super D>  comparator) {
        return streamPlusOf(this)
                .maxBy(mapper, comparator);
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    public default Tuple2<OptionalLong, OptionalLong> minMax(
            LongComparator comparator) {
        return streamPlusOf(this)
                .minMax(comparator);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Tuple2<OptionalLong, OptionalLong> minMaxBy(
            LongFunction<D> mapper) {
        return streamPlusOf(this)
                .minMaxBy(mapper);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    public default <D> Tuple2<OptionalLong, OptionalLong> minMaxBy(
            LongFunction<D>       mapper,
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
    
    public default <D extends Comparable<D>> OptionalInt minIndexBy(LongFunction<D> mapper) {
        return minIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(LongFunction<D> mapper) {
        return maxIndexBy(i -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            LongPredicate   filter,
            LongFunction<D> mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            LongPredicate   filter,
            LongFunction<D> mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexOf(
            LongPredicate     filter,
            LongUnaryOperator mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .map   (t -> t.map2ToLong(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsLong(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty())
                ;
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
            LongPredicate     filter,
            LongUnaryOperator mapper) {
        return funcListOf(this)
                .mapWithIndex()
                .map   (t -> t.map2ToLong(mapper))
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.applyAsLong(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
}
