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

import static functionalj.tuple.LongLongTuple.longTuple;
import static functionalj.tuple.Tuple.tuple2;

import java.util.Comparator;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

import functionalj.function.LongComparator;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.Tuple2;
import lombok.val;


public interface AsLongStreamPlusWithStatistic {
    
    public LongStreamPlus longStreamPlus();
    
    /** @return  the size of the stream */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = longStreamPlus();
        return (int)streamPlus
                .count();
    }
    
    @Eager
    @Terminal
    public default long count() {
        return longStreamPlus().count();
    }
    
    @Eager
    @Terminal
    public default long sum() {
        return longStreamPlus().sum();
    }
    
    /** @return the product of all the number */
    @Eager
    @Terminal
    public default OptionalLong product() {
        return longStreamPlus().reduce((a, b) -> a*b);
    }
    
    @Eager
    @Terminal
    public default OptionalDouble average() {
        return longStreamPlus().average();
    }
    
    @Eager
    @Terminal
    public default OptionalLong min() {
        return longStreamPlus().min();
    }
    
    @Eager
    @Terminal
    public default OptionalLong max() {
        return longStreamPlus().max();
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalLong minBy(LongFunction<D> mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .sortedBy(mapper)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalLong maxBy(LongFunction<D> mapper) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .sortedBy(mapper,(a, b) -> Objects.compare(a, b, Comparator.reverseOrder()))
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalLong minBy(
            LongFunction<D>       mapper,
            Comparator<? super D> comparator) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalLong maxBy(
            LongFunction<D>       mapper,
            Comparator<? super D> comparator) {
        val streamPlus = longStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator.reversed())
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest mapped int value. */
    public default <D> OptionalLong minOf(LongUnaryOperator mapper) {
        val streamPlus = longStreamPlus();
        val result
                = streamPlus
                .mapToObj(i      -> LongLongTuple.of(i, mapper.applyAsLong(i)))
                .min     ((a, b) -> Long.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalLong.of((long)result.get())
                : OptionalLong.empty();
    }
    
    /** Return the value whose mapped value is the largest mapped int value. */
    public default <D> OptionalLong maxOf(LongUnaryOperator mapper) {
        val streamPlus = longStreamPlus();
        Optional<Object> result
                = streamPlus
                .mapToObj(i      -> LongLongTuple.of(i, mapper.applyAsLong(i)))
                .max     ((a, b) -> Long.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalLong.of((long)result.get())
                : OptionalLong.empty();
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Tuple2<OptionalLong, OptionalLong> minMax() {
        val streamPlus = longStreamPlus();
        val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        streamPlus
            .sorted()
            .forEach(each -> {
                minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                LongStreamPlusHelper.dummy.equals(min) ? OptionalLong.empty() : OptionalLong.of((Long)min),
                LongStreamPlusHelper.dummy.equals(max) ? OptionalLong.empty() : OptionalLong.of((Long)max));
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Tuple2<OptionalLong, OptionalLong> minMax(LongComparator comparator) {
        val streamPlus = longStreamPlus();
        val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        streamPlus
            .sorted((a, b) -> comparator.compareLong(a, b))
            .forEach(each -> {
                minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                LongStreamPlusHelper.dummy.equals(min) ? OptionalLong.empty() : OptionalLong.of((Long)min),
                LongStreamPlusHelper.dummy.equals(max) ? OptionalLong.empty() : OptionalLong.of((Long)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Tuple2<OptionalLong, OptionalLong> minMaxBy(LongFunction<D> mapper) {
        val streamPlus = longStreamPlus();
        val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                LongStreamPlusHelper.dummy.equals(min) ? OptionalLong.empty() : OptionalLong.of((Long)min),
                LongStreamPlusHelper.dummy.equals(max) ? OptionalLong.empty() : OptionalLong.of((Long)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Optional<LongLongTuple> minMaxOf(
            LongUnaryOperator mapper) {
        val streamPlus = longStreamPlus();
        val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        LongStreamPlus.from(streamPlus)
            .mapToObj(i    -> longTuple(i, mapper.applyAsLong(i)))
            .sortedBy(t    -> t._2)
            .forEach (each -> {
                minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        
        if (LongStreamPlusHelper.dummy.equals(min)
         || LongStreamPlusHelper.dummy.equals(max))
            return Optional.empty();
        
        val intTuple = longTuple(((LongLongTuple)min)._1, ((LongLongTuple)max)._1);
        return Optional.of(intTuple);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default <D> Tuple2<OptionalLong, OptionalLong> minMaxBy(
            LongFunction<D>       mapper,
            Comparator<? super D> comparator) {
        val streamPlus = longStreamPlus();
        val minRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(LongStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
        .forEach(each -> {
            minRef.compareAndSet(LongStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                LongStreamPlusHelper.dummy.equals(min) ? OptionalLong.empty() : OptionalLong.of((Long)min),
                LongStreamPlusHelper.dummy.equals(max) ? OptionalLong.empty() : OptionalLong.of((Long)max));
    }
    
    @Eager
    @Terminal
    public default LongSummaryStatistics summaryStatistics() {
        return longStreamPlus().summaryStatistics();
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(LongFunction<D> mapper) {
        return minIndexBy(__ -> true, mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(LongFunction<D> mapper) {
        return maxIndexBy(__ -> true, mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> OptionalInt minIndexBy(LongPredicate filter, LongFunction<D> mapper) {
        val min = longStreamPlus()
                .mapWithIndex()
                .filter  (t -> filter.test(t._2))
                .minBy   (t -> mapper.apply(t._2))
                .map   (t -> t._1);
        return min.isPresent() ? OptionalInt.of(min.get().intValue()) : OptionalInt.empty();
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(LongPredicate filter, LongFunction<D> mapper) {
        val max = longStreamPlus()
                .mapWithIndex()
                .filter  (t -> filter.test(t._2))
                .maxBy   (t -> mapper.apply(t._2))
                .map   (t -> t._1);
        return max.isPresent() ? OptionalInt.of(max.get().intValue()) : OptionalInt.empty();
    }
    
}
