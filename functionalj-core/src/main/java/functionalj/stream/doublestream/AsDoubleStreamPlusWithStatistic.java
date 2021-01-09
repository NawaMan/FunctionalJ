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
package functionalj.stream.doublestream;

import static functionalj.tuple.DoubleDoubleTuple.doubleTuple;
import static functionalj.tuple.Tuple.tuple2;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.Tuple2;
import lombok.val;


public interface AsDoubleStreamPlusWithStatistic {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** @return  the size of the stream */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = doubleStreamPlus();
        return (int)streamPlus
                .count();
    }
    
    /** @return the product of all the number */
    @Eager
    @Terminal
    public default OptionalDouble product() {
        return doubleStreamPlus().reduce((a, b) -> a*b);
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble minBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalDouble maxBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper,(a, b) -> Objects.compare(a, b, Comparator.reverseOrder()))
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble minBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalDouble maxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator.reversed())
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest mapped double value. */
    public default <D> OptionalDouble minOf(DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        val result
                = streamPlus
                .mapToObj(i      -> DoubleDoubleTuple.of(i, mapper.applyAsDouble(i)))
                .min     ((a, b) -> Double.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalDouble.of((Double)result.get())
                : OptionalDouble.empty();
    }
    
    /** Return the value whose mapped value is the largest mapped int value. */
    public default <D> OptionalDouble maxOf(DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        Optional<Object> result
                = streamPlus
                .mapToObj(i      -> DoubleDoubleTuple.of(i, mapper.applyAsDouble(i)))
                .max     ((a, b) -> Double.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent()
                ? OptionalDouble.of((double)result.get())
                : OptionalDouble.empty();
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Tuple2<OptionalDouble, OptionalDouble> minMax() {
        val streamPlus = doubleStreamPlus();
        val minRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        streamPlus
            .sorted()
            .forEach(each -> {
                minRef.compareAndSet(DoubleStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                DoubleStreamPlusHelper.dummy.equals(min) ? OptionalDouble.empty() : OptionalDouble.of((Double)min),
                DoubleStreamPlusHelper.dummy.equals(max) ? OptionalDouble.empty() : OptionalDouble.of((Double)max));
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Tuple2<OptionalDouble, OptionalDouble> minMax(DoubleDoubleToIntFunctionPrimitive comparator) {
        val streamPlus = doubleStreamPlus();
        val minRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        streamPlus
            .sorted((a, b) -> comparator.applyAsDoubleAndDouble(a, b))
            .forEach(each -> {
                minRef.compareAndSet(DoubleStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                DoubleStreamPlusHelper.dummy.equals(min) ? OptionalDouble.empty() : OptionalDouble.of((Double)min),
                DoubleStreamPlusHelper.dummy.equals(max) ? OptionalDouble.empty() : OptionalDouble.of((Double)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(DoubleFunction<D> mapper) {
        val streamPlus = doubleStreamPlus();
        val minRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            minRef.compareAndSet(DoubleStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                DoubleStreamPlusHelper.dummy.equals(min) ? OptionalDouble.empty() : OptionalDouble.of((Double)min),
                DoubleStreamPlusHelper.dummy.equals(max) ? OptionalDouble.empty() : OptionalDouble.of((Double)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Optional<DoubleDoubleTuple> minMaxOf(
            DoubleUnaryOperator mapper) {
        val streamPlus = doubleStreamPlus();
        val minRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        DoubleStreamPlus.from(streamPlus)
            .mapToObj(i    -> doubleTuple(i, mapper.applyAsDouble(i)))
            .sortedBy(t    -> t._2)
            .forEach (each -> {
                minRef.compareAndSet(DoubleStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        
        if (DoubleStreamPlusHelper.dummy.equals(min)
         || DoubleStreamPlusHelper.dummy.equals(max))
            return Optional.empty();
        
        val doubleTuple = doubleTuple(((DoubleDoubleTuple)min)._1, ((DoubleDoubleTuple)max)._1);
        return Optional.of(doubleTuple);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default <D> Tuple2<OptionalDouble, OptionalDouble> minMaxBy(
            DoubleFunction<D>     mapper,
            Comparator<? super D> comparator) {
        val streamPlus = doubleStreamPlus();
        val minRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(DoubleStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
        .forEach(each -> {
            minRef.compareAndSet(DoubleStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                DoubleStreamPlusHelper.dummy.equals(min) ? OptionalDouble.empty() : OptionalDouble.of((Double)min),
                DoubleStreamPlusHelper.dummy.equals(max) ? OptionalDouble.empty() : OptionalDouble.of((Double)max));
    }
    
}
