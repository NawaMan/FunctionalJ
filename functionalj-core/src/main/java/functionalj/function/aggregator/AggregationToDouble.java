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

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

import functionalj.stream.collect.CollectorToDoublePlus;
import lombok.val;

@FunctionalInterface
public interface AggregationToDouble<SOURCE> extends Aggregation<SOURCE, Double> {
    
    public static <S, A> AggregationToDouble<S> from(CollectorToDoublePlus<S, A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public CollectorToDoublePlus<SOURCE, ?> collectorPlus();
    
    
    public default AggregatorToDouble<SOURCE> newAggregator() {
        val collector = collectorPlus();
        return new AggregatorToDouble.Impl<>(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> AggregationToDouble<INPUT> of(Function<INPUT, SOURCE> mapper) {
        val newCollector = collectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default IntAggregationToDouble ofInt(IntFunction<SOURCE> mapper) {
        val newCollector = collectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default LongAggregationToDouble ofLong(LongFunction<SOURCE> mapper) {
        val newCollector = collectorPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default DoubleAggregationToDouble ofDouble(DoubleFunction<SOURCE> mapper) {
        val newCollector = collectorPlus().of(mapper);
        return () -> newCollector;
    }
    
}
