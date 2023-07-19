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
import functionalj.stream.collect.CollectorToBooleanPlus;
import lombok.val;

public abstract class AggregationToBoolean<SOURCE> extends Aggregation<SOURCE, Boolean> {
    
    public static <S, A> AggregationToBoolean<S> from(CollectorToBooleanPlus<S, A> collector) {
        return new AggregationToBoolean.Impl<>(collector);
    }
    
    // == Instance ==
    public abstract CollectorToBooleanPlus<SOURCE, ?> collectorToBooleanPlus();
    
    @Override
    public CollectorToBooleanPlus<SOURCE, ?> collectorPlus() {
        return collectorToBooleanPlus();
    }
    
    public AggregatorToBoolean<SOURCE> newAggregator() {
        val collector = collectorToBooleanPlus();
        return new AggregatorToBoolean.Impl<>(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToBoolean<INPUT> of(Function<INPUT, SOURCE> mapper) {
        val newCollector = collectorToBooleanPlus().of(mapper);
        return new AggregationToBoolean.Impl<>(newCollector);
    }
    
    public IntAggregationToBoolean ofInt(IntFunction<SOURCE> mapper) {
        val newCollector = collectorToBooleanPlus().of(mapper);
        return new IntAggregationToBoolean.Impl(newCollector);
    }
    
    public LongAggregationToBoolean ofLong(LongFunction<SOURCE> mapper) {
        val newCollector = collectorToBooleanPlus().of(mapper);
        return new LongAggregationToBoolean.Impl(newCollector);
    }
    
    public DoubleAggregationToBoolean ofDouble(DoubleFunction<SOURCE> mapper) {
        val newCollector = collectorToBooleanPlus().of(mapper);
        return new DoubleAggregationToBoolean.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl<SRC> extends AggregationToBoolean<SRC> {
    
        private final CollectorToBooleanPlus<SRC, ?> collector;
    
        public Impl(CollectorToBooleanPlus<SRC, ?> collector) {
            this.collector = collector;
        }
    
        @Override
        public CollectorToBooleanPlus<SRC, ?> collectorToBooleanPlus() {
            return collector;
        }
    }
}
