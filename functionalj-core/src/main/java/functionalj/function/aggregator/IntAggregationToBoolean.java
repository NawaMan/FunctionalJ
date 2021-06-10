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

import java.util.function.DoubleToIntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;

import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToBooleanPlus;
import lombok.val;


public abstract class IntAggregationToBoolean extends IntAggregation<Boolean> {
    
    public static <A> IntAggregationToBoolean from(IntCollectorToBooleanPlus<A> collector) {
        return new IntAggregationToBoolean.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract IntCollectorToBooleanPlus<?> intCollectorToBooleanPlus();
    
    
    @Override
    public IntCollectorPlus<?, Boolean> intCollectorPlus() {
        return intCollectorToBooleanPlus();
    }
    
    public IntAggregatorToBoolean newIntAccumulatorToBoolean() {
        val collector = intCollectorToBooleanPlus();
        return new IntAggregatorToBoolean.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToBoolean<INPUT> of(ToIntFunction<INPUT> mapper) {
        val newCollector = intCollectorToBooleanPlus().of(mapper);
        return new AggregationToBoolean.Impl<>(newCollector);
    }
    
    public IntAggregationToBoolean ofInt(IntUnaryOperator mapper) {
        val newCollector = intCollectorToBooleanPlus().of(mapper);
        return new IntAggregationToBoolean.Impl(newCollector);
    }
    
    public LongAggregationToBoolean ofLong(LongToIntFunction mapper) {
        val newCollector = intCollectorToBooleanPlus().of(mapper);
        return new LongAggregationToBoolean.Impl(newCollector);
    }
    
    public DoubleAggregationToBoolean ofDouble(DoubleToIntFunction mapper) {
        val newCollector = intCollectorToBooleanPlus().of(mapper);
        return new DoubleAggregationToBoolean.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends IntAggregationToBoolean {
        
        private final IntCollectorToBooleanPlus<?> collector;
        
        public Impl(IntCollectorToBooleanPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public IntCollectorToBooleanPlus<?> intCollectorToBooleanPlus() {
            return collector;
        }
        
    }
    
}
