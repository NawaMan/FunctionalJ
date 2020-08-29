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

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import functionalj.stream.makers.Eager;
import functionalj.stream.makers.Sequential;
import functionalj.stream.makers.Terminal;
import lombok.val;

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @param <DATA> the data type of the stream plus.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsIntStreamPlus 
                    extends
                        AsIntStreamPlusWithConversion,
                        AsIntStreamPlusWithForEach,
                        AsIntStreamPlusWithMatch,
                        AsIntStreamPlusWithStatistic {
    
    /** @return  the stream plus instance of this object. */
    public static IntStreamPlus streamOf(AsIntStreamPlus streamPlus) {
        return streamPlus.intStreamPlus();
    }
    
    /** @return  the stream plus instance of this object. */
    public IntStreamPlus intStreamPlus();
    
    /** @return  return the stream underneath the stream plus. */
    public default IntStream intStream() {
        return intStreamPlus();
    }
    
    //== Terminal operations ==
    
    /** @return a iterator of this streamable. */
    public default IntIteratorPlus iterator() {
        return streamOf(this).iterator();
    }
    
    /** @return a spliterator of this streamable. */
    public default Spliterator.OfInt spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    @Eager
    @Terminal
    public default void forEach(IntConsumer action) {
        streamOf(this).forEach(action);
    }
    
    @Eager
    @Terminal
    @Sequential
    public default void forEachOrdered(IntConsumer action) {
        streamOf(this).forEachOrdered(action);
    }
    
    @Eager
    @Terminal
    public default int reduce(int identity, IntBinaryOperator reducer) {
        return streamOf(this).reduce(identity, reducer);
    }
    
    @Eager
    @Terminal
    public default OptionalInt reduce(IntBinaryOperator reducer) {
        return streamOf(this).reduce(reducer);
    }
    
    @Eager
    @Terminal
    public default <R> R collect(
            Supplier<R>       supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R>  combiner) {
        return streamOf(this).collect(supplier, accumulator, combiner);
    }
    
    //-- statistics --
    
    @Eager
    @Terminal
    public default OptionalInt min() {
        return streamOf(this).min();
    }
    
    @Eager
    @Terminal
    public default OptionalInt max() {
        return streamOf(this).max();
    }
    
    @Eager
    @Terminal
    public default long count() {
        return streamOf(this).count();
    }
    
    @Eager
    @Terminal
    public default int sum() {
        return streamOf(this).sum();
    }
    
    @Eager
    @Terminal
    public default OptionalDouble average() {
        return streamOf(this).average();
    }
    
    @Eager
    @Terminal
    public default IntSummaryStatistics summaryStatistics() {
        return streamOf(this).summaryStatistics();
    }
    
    //-- Match --
    
    @Terminal
    public default boolean anyMatch(IntPredicate predicate) {
        return streamOf(this).anyMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean allMatch(IntPredicate predicate) {
        return streamOf(this).allMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean noneMatch(IntPredicate predicate) {
        return streamOf(this).noneMatch(predicate);
    }
    
    @Terminal
    public default OptionalInt findFirst() {
        return streamOf(this).findFirst();
    }
    
    @Terminal
    public default OptionalInt findAny() {
        return streamOf(this).findAny();
    }
    
    //== Conversion ==
    
    @Eager
    @Terminal
    public default int[] toArray() {
        return streamOf(this).toArray();
    }
    
}
