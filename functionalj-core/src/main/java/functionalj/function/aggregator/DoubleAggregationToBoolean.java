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
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ToDoubleFunction;

import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import lombok.val;


public abstract class DoubleAggregationToBoolean extends DoubleAggregation<Boolean> {
    
    public static <A> DoubleAggregationToBoolean from(DoubleCollectorToBooleanPlus<A> collector) {
        return new DoubleAggregationToBoolean.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract DoubleCollectorToBooleanPlus<?> doubleCollectorToBooleanPlus();
    
    
    @Override
    public DoubleCollectorPlus<?, Boolean> doubleCollectorPlus() {
        return doubleCollectorToBooleanPlus();
    }
    
    
    public DoubleAggregatorToBoolean newAggregator() {
        val collector = doubleCollectorToBooleanPlus();
        return new DoubleAggregatorToBoolean.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToBoolean<INPUT> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = doubleCollectorToBooleanPlus().of(mapper);
        return new AggregationToBoolean.Impl<INPUT>(newCollector);
    }
    
    public IntAggregationToBoolean ofInt(IntToDoubleFunction mapper) {
        val newCollector = doubleCollectorToBooleanPlus().of(mapper);
        return new IntAggregationToBoolean.Impl(newCollector);
    }
    
    public LongAggregationToBoolean ofLong(LongToDoubleFunction mapper) {
        val newCollector = doubleCollectorToBooleanPlus().of(mapper);
        return new LongAggregationToBoolean.Impl(newCollector);
    }
    
    public DoubleAggregation<Boolean> ofDouble(DoubleFunction<Double> mapper) {
        if (mapper instanceof DoubleUnaryOperator) {
            return ofDoubleToBoolean((DoubleUnaryOperator)mapper);
        }
        
        val newCollector = doubleCollectorToBooleanPlus().of(mapper);
        return new DoubleAggregation.Impl<>(newCollector);
    }
    
    // This is a terrible name .... :-(
    // But if we use `ofDouble`, Java confuse this one and the one above
    public DoubleAggregationToBoolean ofDoubleToBoolean(DoubleUnaryOperator mapper) {
        val newCollector = doubleCollectorToBooleanPlus().of(mapper);
        return new DoubleAggregationToBoolean.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends DoubleAggregationToBoolean {
        
        private final DoubleCollectorToBooleanPlus<?> collector;
        
        public Impl(DoubleCollectorToBooleanPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public DoubleCollectorToBooleanPlus<?> doubleCollectorToBooleanPlus() {
            return collector;
        }
        
    }
    
}
