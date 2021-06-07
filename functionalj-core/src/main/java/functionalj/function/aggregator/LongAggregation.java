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
package functionalj.function.aggregator;

import java.util.function.DoubleToLongFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;


@FunctionalInterface
public interface LongAggregation<TARGET> extends Aggregation<Long, TARGET> {
    
//    public static <A, R> LongCollected<A, R> collectedOf(LongAggregation<R> aggregation) {
//        return of(aggregation);
//    }
//    
//    @SuppressWarnings("unchecked")
//    public static <A, R> LongCollected<A, R> of(LongAggregation<R> aggregation) {
//        return new LongCollected.Impl<A, R>((LongCollectorPlus<A, R>) aggregation.longCollectorPlus());
//    }
//    
//    public static <A, T> LongAggregation<T> from(LongCollectorPlus<A, T> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToInt from(LongCollectorToIntPlus<A> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToLong from(LongCollectorToLongPlus<A> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToDouble from(LongCollectorToDoublePlus<A> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToInt forInt(LongCollectorToIntPlus<A> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToLong forLong(LongCollectorToLongPlus<A> collector) {
//        return () -> collector;
//    }
//    
//    public static <A> LongAggregationToDouble forDouble(LongCollectorToDoublePlus<A> collector) {
//        return () -> collector;
//    }
    
    //== Instance == 
    
    public LongCollectorPlus<?, TARGET> longCollectorPlus();
    
    
    @Override
    public default CollectorPlus<Long, ?, TARGET> collectorPlus() {
        return longCollectorPlus();
    }
    
    public default LongAggregator<TARGET> newAggregator() {
        val collector = longCollectorPlus();
        return new LongAggregator.Impl<>(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> Aggregation<INPUT, TARGET> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default IntAggregation<TARGET> ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default LongAggregation<TARGET> ofLong(LongFunction<Long> mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default LongAggregation<TARGET> ofLong(LongUnaryOperator mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default DoubleAggregation<TARGET> ofDouble(DoubleToLongFunction mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
}
