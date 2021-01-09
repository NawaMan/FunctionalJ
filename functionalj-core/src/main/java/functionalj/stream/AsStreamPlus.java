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

import static functionalj.stream.StreamPlus.streamOf;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import functionalj.result.Result;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @param <DATA> the data type of the stream plus.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsStreamPlus<DATA>
                    extends
                        AsStreamPlusWithConversion<DATA>,
                        AsStreamPlusWithForEach<DATA>,
                        AsStreamPlusWithGroupingBy<DATA>,
                        AsStreamPlusWithMatch<DATA>,
                        AsStreamPlusWithStatistic<DATA> {
    
    /** @return  the stream plus instance of this object. */
    public static <D> StreamPlus<D> streamFrom(AsStreamPlus<D> streamPlus) {
        return streamPlus.streamPlus();
    }
    
    /** @return  the stream plus instance of this object. */
    public StreamPlus<DATA> streamPlus();
    
    /** @return  return the stream underneath the stream plus. */
    public default Stream<DATA> stream() {
        return streamPlus();
    }
    
    //== Terminal operations ==
    
    /** @return a iterator of this streamable. */
    public default IteratorPlus<DATA> iterator() {
        return streamFrom(this).iterator();
    }
    
    /** @return a spliterator of this streamable. */
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        streamFrom(this).forEach(action);
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        streamFrom(this).forEachOrdered(action);
    }
    
    public default DATA reduce(DATA identity, BinaryOperator<DATA> reducer) {
        return streamFrom(this).reduce(identity, reducer);
    }
    
    public default Optional<DATA> reduce(BinaryOperator<DATA> reducer) {
        return streamFrom(this).reduce(reducer);
    }
    
    public default <U> U reduce(
            U                              identity,
            BiFunction<U, ? super DATA, U> accumulator,
            BinaryOperator<U>              combiner) {
        return streamFrom(this).reduce(identity, accumulator, combiner);
    }
    
    public default <R> R collect(
            Supplier<R>                 supplier,
            BiConsumer<R, ? super DATA> accumulator,
            BiConsumer<R, R>            combiner) {
        return streamFrom(this).collect(supplier, accumulator, combiner);
    }
    
    public default <R, A> R collect(Collector<? super DATA, A, R> collector) {
        return streamFrom(this).collect(collector);
    }
    
    //-- statistics --
    
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        return streamFrom(this).min(comparator);
    }
    
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        return streamFrom(this).max(comparator);
    }
    
    public default long count() {
        return streamFrom(this).count();
    }
    
    //-- Match --
    
    @Terminal
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        return streamFrom(this).anyMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        return streamFrom(this).allMatch(predicate);
    }
    
    @Eager
    @Terminal
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        return streamFrom(this).noneMatch(predicate);
    }
    
    @Terminal
    public default Optional<DATA> findFirst() {
        return streamFrom(this).findFirst();
    }
    
    @Terminal
    public default Optional<DATA> findAny() {
        return streamFrom(this).findAny();
    }
    
    @Sequential
    @Terminal
    public default Optional<DATA> findLast() {
        return streamFrom(this).findLast();
    }
    
    @Sequential
    @Terminal
    public default Result<DATA> firstResult() {
        return streamFrom(this).firstResult();
    }
    
    @Sequential
    @Terminal
    public default Result<DATA> lastResult() {
        return streamFrom(this).lastResult();
    }
    
    //== Contains ==
    
    /** Check if the list contains all the given values */
    @SuppressWarnings("unchecked")
    public default boolean containsAllOf(DATA ... values) {
        val set = new HashSet<DATA>(values.length);
        for (val value : values) {
            set.add(value);
        }
        stream().peek(set::remove).anyMatch(__ -> set.isEmpty());
        return set.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    public default boolean containsAnyOf(DATA ... values) {
        return anyMatch(each -> streamOf(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("unchecked")
    public default boolean containsNoneOf(DATA ... values) {
        return noneMatch(each -> streamOf(values).anyMatch(o -> Objects.equals(each, o)));
    }
    
    //== Conversion ==
    
    @Eager
    @Terminal
    public default Object[] toArray() {
        return streamFrom(this).toArray();
    }
    
    @Eager
    @Terminal
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return streamFrom(this).toArray(generator);
    }
    
}
