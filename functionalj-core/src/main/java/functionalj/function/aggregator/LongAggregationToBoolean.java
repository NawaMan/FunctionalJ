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

import java.util.function.DoubleToLongFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToBooleanPlus;
import lombok.val;

public abstract class LongAggregationToBoolean extends LongAggregation<Boolean> {
    
    public static <A> LongAggregationToBoolean from(LongCollectorToBooleanPlus<A> collector) {
        return new LongAggregationToBoolean.Impl(collector);
    }
    
    // == Instance ==
    public abstract LongCollectorToBooleanPlus<?> longCollectorToBooleanPlus();
    
    @Override
    public LongCollectorPlus<?, Boolean> longCollectorPlus() {
        return longCollectorToBooleanPlus();
    }
    
    public LongAggregatorToBoolean newAggregator() {
        val collector = longCollectorToBooleanPlus();
        return new LongAggregatorToBoolean.Impl(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToBoolean<INPUT> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorToBooleanPlus().of(mapper);
        return new AggregationToBoolean.Impl<>(newCollector);
    }
    
    public IntAggregationToBoolean ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorToBooleanPlus().of(mapper);
        return new IntAggregationToBoolean.Impl(newCollector);
    }
    
    public LongAggregation<Boolean> ofLong(LongFunction<Long> mapper) {
        if (mapper instanceof LongUnaryOperator) {
            return ofLongToBoolean((LongUnaryOperator) mapper);
        }
        val newCollector = longCollectorToBooleanPlus().of(mapper);
        return new LongAggregation.Impl<>(newCollector);
    }
    
    public DoubleAggregationToBoolean ofDouble(DoubleToLongFunction mapper) {
        val newCollector = longCollectorToBooleanPlus().of(mapper);
        return new DoubleAggregationToBoolean.Impl(newCollector);
    }
    
    // This is a terrible name .... :-(
    // But if we use `ofDouble`, Java confuse this one and the one above
    public LongAggregationToBoolean ofLongToBoolean(LongUnaryOperator mapper) {
        val newCollector = longCollectorToBooleanPlus().of(mapper);
        return new LongAggregationToBoolean.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl extends LongAggregationToBoolean {
        
        private final LongCollectorToBooleanPlus<?> collector;
        
        public Impl(LongCollectorToBooleanPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public LongCollectorToBooleanPlus<?> longCollectorToBooleanPlus() {
            return collector;
        }
    }
}
