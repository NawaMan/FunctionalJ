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
package functionalj.stream;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.function.Func1;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple2;

public interface StreamableAdditionalTerminalOperators<DATA> {
    
    public StreamPlus<DATA> stream();
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(
            BiConsumer<? super Integer, ? super DATA> action) {
        stream()
        .forEachWithIndex(action);
    }
    
    //-- groupingBy --
    
    // Eager
    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(
            Function<? super DATA, ? extends KEY> classifier) {
        return stream()
                .groupingBy(classifier);
    }
    
    // Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>   classifier,
            Function<? super FuncList<DATA>, VALUE> aggregate) {
        return stream()
                .groupingBy(classifier, aggregate);
    }
    
    // Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> classifier,
            StreamProcessor<? super DATA, VALUE>  processor) {
        return stream()
                .groupingBy(classifier, processor);
    }
    
    // Eager
    public default <KEY, ACCUMULATED, TARGET> FuncMap<KEY, TARGET> groupingBy(
            Function<? super DATA, ? extends KEY>                  classifier,
            Supplier<Collector<? super DATA, ACCUMULATED, TARGET>> collectorSupplier) {
        return stream()
                .groupingBy(classifier, collectorSupplier);
    }
    
    //-- min-max --
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(
            Func1<DATA, D> mapper) {
        return stream()
                .minBy(mapper);
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(
            Func1<DATA, D> mapper) {
        return stream()
                .maxBy(mapper);
    }
    
    public default <D> Optional<DATA> minBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .minBy(mapper, comparator);
    }
    
    public default <D> Optional<DATA> maxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .maxBy(mapper, comparator);
    }
    
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(
            Comparator<? super DATA> comparator) {
        return stream()
                .minMax(comparator);
    }
    
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D> mapper) {
        return stream()
                .minMaxBy(mapper);
    }
    
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return stream()
                .minMaxBy(mapper, comparator);
    }
    
    //-- Find --
    
    public default <T> Optional<DATA> findFirst(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return stream()
                .findFirst(mapper, theCondition);
    }
    
    public default <T>  Optional<DATA> findAny(
            Function<? super DATA, T> mapper, 
            Predicate<? super T> theCondition) {
        return stream()
                .findAny(mapper, theCondition);
    }
    
    public default Optional<DATA> findFirst(
            Predicate<? super DATA> predicate) {
        return stream()
                .findFirst(predicate);
    }
    
    public default Optional<DATA> findAny(
            Predicate<? super DATA> predicate) {
        return stream()
                .findAny(predicate);
    }
    
    //-- toMap --
    
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper) {
        return stream()
                .toMap(keyMapper);
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>  keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper) {
        return stream()
                .toMap(keyMapper, valueMapper);
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>   keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper,
            BinaryOperator<VALUE>                   mergeFunction) {
        return stream()
                .toMap(keyMapper, valueMapper, mergeFunction);
    }
    
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper,
            BinaryOperator<DATA>                  mergeFunction) {
        return stream()
                .toMap(keyMapper, mergeFunction);
    }
}
