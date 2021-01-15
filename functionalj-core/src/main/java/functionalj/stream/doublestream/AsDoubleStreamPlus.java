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

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsDoubleStreamPlus
                    extends
                        AsDoubleStreamPlusWithConversion,
                        AsDoubleStreamPlusWithForEach,
                        AsDoubleStreamPlusWithGroupingBy,
                        AsDoubleStreamPlusWithMatch,
                        AsDoubleStreamPlusWithStatistic {
    
    /** @return  the stream plus instance of this object. */
    public static DoubleStreamPlus streamFrom(AsDoubleStreamPlus streamPlus) {
        return streamPlus.doubleStreamPlus();
    }
    
    /** @return  the stream plus instance of this object. */
    public DoubleStreamPlus doubleStreamPlus();
    
    /** @return  return the stream underneath the stream plus. */
    public default DoubleStream doubleStream() {
        return doubleStreamPlus();
    }
    
    //== Terminal operations ==
    
    /** @return a iterator of this FuncList. */
    public default DoubleIteratorPlus iterator() {
        return streamFrom(this).iterator();
    }
    
    /** @return a spliterator of this FuncList. */
    public default Spliterator.OfDouble spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    @Eager
    @Terminal
    public default void forEach(DoubleConsumer action) {
        streamFrom(this).forEach(action);
    }
    
    @Eager
    @Terminal
    @Sequential
    public default void forEachOrdered(DoubleConsumer action) {
        streamFrom(this).forEachOrdered(action);
    }
    
    @Eager
    @Terminal
    public default double reduce(double identity, DoubleBinaryOperator reducer) {
        return streamFrom(this).reduce(identity, reducer);
    }
    
    @Eager
    @Terminal
    public default OptionalDouble reduce(DoubleBinaryOperator reducer) {
        return streamFrom(this).reduce(reducer);
    }
    
    @Eager
    @Terminal
    public default <R> R collect(
            Supplier<R>          supplier,
            ObjDoubleConsumer<R> accumulator,
            BiConsumer<R, R>     combiner) {
        return streamFrom(this).collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <R> R collect(DoubleCollectorPlus<?, R> collector) {
        Supplier<R>          supplier    = (Supplier)         collector.supplier();
        ObjDoubleConsumer<R> accumulator = (ObjDoubleConsumer)collector.accumulator();
        BiConsumer<R, R>     combiner    = (BiConsumer)       collector.combiner();
        return streamFrom(this).collect(supplier, accumulator, combiner);
    }
    
    //-- statistics --
    
    @Eager
    @Terminal
    public default OptionalDouble min() {
        return streamFrom(this).min();
    }
    
    @Eager
    @Terminal
    public default OptionalDouble max() {
        return streamFrom(this).max();
    }
    
    @Eager
    @Terminal
    public default long count() {
        return streamFrom(this).count();
    }
    
    @Eager
    @Terminal
    public default double sum() {
        return streamFrom(this).sum();
    }
    
    @Eager
    @Terminal
    public default OptionalDouble average() {
        return streamFrom(this).average();
    }
    
    @Eager
    @Terminal
    public default DoubleSummaryStatistics summaryStatistics() {
        return streamFrom(this).summaryStatistics();
    }
    
    //-- Match --
    
    @Terminal
    public default boolean anyMatch(DoublePredicate predicate) {
        return streamFrom(this).anyMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean allMatch(DoublePredicate predicate) {
        return streamFrom(this).allMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean noneMatch(DoublePredicate predicate) {
        return streamFrom(this).noneMatch(predicate);
    }
    
    @Terminal
    public default OptionalDouble findFirst() {
        return streamFrom(this).findFirst();
    }
    
    @Terminal
    public default OptionalDouble findAny() {
        return streamFrom(this).findAny();
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble firstResult() {
        return streamFrom(this).firstResult();
    }
    
    @Sequential
    @Terminal
    public default OptionalDouble lastResult() {
        return streamFrom(this).lastResult();
    }
    
    //== Conversion ==
    
    @Eager
    @Terminal
    public default double[] toArray() {
        return streamFrom(this).toArray();
    }
    
}
