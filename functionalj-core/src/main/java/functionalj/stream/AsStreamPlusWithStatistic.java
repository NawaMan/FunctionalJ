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

import static functionalj.function.Func.alwaysTrue;
import static functionalj.tuple.Tuple.tuple2;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;


public interface AsStreamPlusWithStatistic<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** @return  the size of the stream */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = streamPlus();
        return (int)streamPlus
                .count();
    }
    
    @Eager
    @Terminal
    public default long count() {
        val streamPlus = streamPlus();
        return streamPlus
                .count();
    }
    
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .min(comparator);
    }
    
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .max(comparator);
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> minBy(Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .min((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return mappedA.compareTo(mappedB);
                });
    }
    
    /** Return the value whose mapped value is the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .max((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return mappedA.compareTo(mappedB);
                });
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @Eager
    @Terminal
    public default <D> Optional<DATA> minBy(
            Function<DATA, D>     mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .min((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return comparator.compare(mappedA, mappedB);
                });
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @Eager
    @Terminal
    public default <D> Optional<DATA> maxBy(
            Function<DATA, D>     mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .max((a,b) -> {
                    val mappedA = mapper.apply(a);
                    val mappedB = mapper.apply(b);
                    return comparator.compare(mappedA, mappedB);
                });
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
            .sorted(comparator)
            .forEach(each -> {
                minRef.compareAndSet(StreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(Func1<DATA, D> mapper) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
        .forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Function<DATA, D> mapper) {
        return minIndexBy(alwaysTrue(), mapper);
    }
    
    /** Map each value using the mapper to a comparable value and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Function<DATA, D> mapper) {
        return maxIndexBy(alwaysTrue(), mapper);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(
            Predicate<DATA>   filter,
            Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapWithIndex(Tuple::of)
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> t._1);
    }
    
    /** Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(
            Predicate<DATA>   filter,
            Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .mapWithIndex(Tuple::of)
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> t._1);
    }
    
}
