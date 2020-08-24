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
package functionalj.stream.intstream;

import static functionalj.tuple.IntIntTuple.intTuple;
import static functionalj.tuple.Tuple.tuple2;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import functionalj.stream.makers.Eager;
import functionalj.stream.makers.Terminal;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface IntStreamPlusWithStatistic {
    
    public IntStreamPlus intStreamPlus();
    
    /** @return  the size of the stream */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = intStreamPlus();
        return (int)streamPlus
                .count();
    }
    
    /** Return the value whose mapped value is the smallest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt minBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .sortedBy(mapper)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> OptionalInt maxBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .sortedBy(mapper,(a, b) -> Objects.compare(a, b, Comparator.reverseOrder()))
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt minBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator)
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the biggest using the comparator. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Eager
    @Terminal
    public default <D> OptionalInt maxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .sortedBy(mapper, (Comparator)comparator.reversed())
                .findFirst()
                ;
    }
    
    /** Return the value whose mapped value is the smallest mapped int value. */
    public default <D> OptionalInt minOf(IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        val result 
                = streamPlus
                .mapToObj(i      -> IntIntTuple.of(i, mapper.applyAsInt(i)))
                .min     ((a, b) -> Integer.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent() 
                ? OptionalInt.of((int)result.get()) 
                : OptionalInt.empty();
    }
    
    /** Return the value whose mapped value is the largest mapped int value. */
    public default <D> OptionalInt maxOf(IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        Optional<Object> result 
                = streamPlus
                .mapToObj(i      -> IntIntTuple.of(i, mapper.applyAsInt(i)))
                .max     ((a, b) -> Integer.compare(a._2, b._2))
                .map     (t      -> t._1);
        return result.isPresent() 
                ? OptionalInt.of((int)result.get()) 
                : OptionalInt.empty();
    }
    
    /** Return the value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default Tuple2<OptionalInt, OptionalInt> minMax(IntBinaryOperator comparator) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        streamPlus
            .sorted((a, b) -> comparator.applyAsInt(a, b))
            .forEach(each -> {
                minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        return Tuple2.of(
                IntStreamPlusHelper.dummy.equals(min) ? OptionalInt.empty() : OptionalInt.of((Integer)min),
                IntStreamPlusHelper.dummy.equals(max) ? OptionalInt.empty() : OptionalInt.of((Integer)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Tuple2<OptionalInt, OptionalInt> minMaxBy(IntFunction<D> mapper) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper)
        .forEach(each -> {
            minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                IntStreamPlusHelper.dummy.equals(min) ? OptionalInt.empty() : OptionalInt.of((Integer)min),
                IntStreamPlusHelper.dummy.equals(max) ? OptionalInt.empty() : OptionalInt.of((Integer)max));
    }
    
    /** Return the value whose mapped value is the smallest and the biggest. */
    public default <D extends Comparable<D>> Optional<IntIntTuple> minMaxOf(
            IntUnaryOperator mapper) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        IntStreamPlus.from(streamPlus)
            .mapToObj(i    -> intTuple(i, mapper.applyAsInt(i)))
            .sortedBy(t    -> t._2)
            .forEach (each -> {
                minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
                maxRef.set(each);
            });
        val min = minRef.get();
        val max = maxRef.get();
        
        if (IntStreamPlusHelper.dummy.equals(min)
         || IntStreamPlusHelper.dummy.equals(max))
            return Optional.empty();
        
        val intTuple = intTuple(((IntIntTuple)min)._1, ((IntIntTuple)max)._1);
        return Optional.of(intTuple);
    }
    
    /** Return the value whose mapped value is the smallest and the biggest using the comparator. */
    @Eager
    @Terminal
    public default <D> Tuple2<OptionalInt, OptionalInt> minMaxBy(
            IntFunction<D>        mapper, 
            Comparator<? super D> comparator) {
        val streamPlus = intStreamPlus();
        val minRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(IntStreamPlusHelper.dummy);
        streamPlus
        .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
        .forEach(each -> {
            minRef.compareAndSet(IntStreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        
        val min = minRef.get();
        val max = maxRef.get();
        return tuple2(
                IntStreamPlusHelper.dummy.equals(min) ? OptionalInt.empty() : OptionalInt.of((Integer)min),
                IntStreamPlusHelper.dummy.equals(max) ? OptionalInt.empty() : OptionalInt.of((Integer)max));
    }
    
}
