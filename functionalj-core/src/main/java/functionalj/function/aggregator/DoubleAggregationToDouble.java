// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ToDoubleFunction;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import lombok.val;

public abstract class DoubleAggregationToDouble extends DoubleAggregation<Double> {
    
    public static <A> DoubleAggregationToDouble from(DoubleCollectorToDoublePlus<A> collector) {
        return new DoubleAggregationToDouble.Impl(collector);
    }
    
    // == Instance ==
    public abstract DoubleCollectorToDoublePlus<?> doubleCollectorToDoublePlus();
    
    @Override
    public DoubleCollectorPlus<?, Double> doubleCollectorPlus() {
        return doubleCollectorToDoublePlus();
    }
    
    public DoubleAggregatorToDouble newAggregator() {
        val collector = doubleCollectorToDoublePlus();
        return new DoubleAggregatorToDouble.Impl(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToDouble<INPUT> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = doubleCollectorToDoublePlus().of(mapper);
        return new AggregationToDouble.Impl<INPUT>(newCollector);
    }
    
    public IntAggregationToDouble ofInt(IntToDoubleFunction mapper) {
        val newCollector = doubleCollectorToDoublePlus().of(mapper);
        return new IntAggregationToDouble.Impl(newCollector);
    }
    
    public LongAggregationToDouble ofLong(LongToDoubleFunction mapper) {
        val newCollector = doubleCollectorToDoublePlus().of(mapper);
        return new LongAggregationToDouble.Impl(newCollector);
    }
    
    public DoubleAggregation<Double> ofDouble(DoubleFunction<Double> mapper) {
        if (mapper instanceof DoubleUnaryOperator) {
            return ofDoubleToDouble((DoubleUnaryOperator) mapper);
        }
        val newCollector = doubleCollectorToDoublePlus().of(mapper);
        return new DoubleAggregation.Impl<>(newCollector);
    }
    
    // This is a terrible name .... :-(
    // But if we use `ofDouble`, Java confuse this one and the one above
    public DoubleAggregationToDouble ofDoubleToDouble(DoubleUnaryOperator mapper) {
        val newCollector = doubleCollectorToDoublePlus().of(mapper);
        return new DoubleAggregationToDouble.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl extends DoubleAggregationToDouble {
        
        private final DoubleCollectorToDoublePlus<?> collector;
        
        public Impl(DoubleCollectorToDoublePlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public DoubleCollectorToDoublePlus<?> doubleCollectorToDoublePlus() {
            return collector;
        }
    }
}
