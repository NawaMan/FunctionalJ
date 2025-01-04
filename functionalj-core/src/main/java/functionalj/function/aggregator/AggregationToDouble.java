// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

public abstract class AggregationToDouble<SOURCE> extends Aggregation<SOURCE, Double> {
    
    public static <S, A> AggregationToDouble<S> from(CollectorToDoublePlus<S, A> collector) {
        return new AggregationToDouble.Impl<S>(collector);
    }
    
    // == Instance ==
    public abstract CollectorToDoublePlus<SOURCE, ?> collectorToDoublePlus();
    
    @Override
    public CollectorToDoublePlus<SOURCE, ?> collectorPlus() {
        return collectorToDoublePlus();
    }
    
    public AggregatorToDouble<SOURCE> newAggregator() {
        val collector = collectorToDoublePlus();
        return new AggregatorToDouble.Impl<>(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToDouble<INPUT> of(Function<INPUT, SOURCE> mapper) {
        val newCollector = collectorToDoublePlus().of(mapper);
        return new AggregationToDouble.Impl<>(newCollector);
    }
    
    public IntAggregationToDouble ofInt(IntFunction<SOURCE> mapper) {
        val newCollector = collectorToDoublePlus().of(mapper);
        return new IntAggregationToDouble.Impl(newCollector);
    }
    
    public LongAggregationToDouble ofLong(LongFunction<SOURCE> mapper) {
        val newCollector = collectorToDoublePlus().of(mapper);
        return new LongAggregationToDouble.Impl(newCollector);
    }
    
    public DoubleAggregationToDouble ofDouble(DoubleFunction<SOURCE> mapper) {
        val newCollector = collectorToDoublePlus().of(mapper);
        return new DoubleAggregationToDouble.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl<SRC> extends AggregationToDouble<SRC> {
        
        private final CollectorToDoublePlus<SRC, ?> collector;
        
        public Impl(CollectorToDoublePlus<SRC, ?> collector) {
            this.collector = collector;
        }
        
        @Override
        public CollectorToDoublePlus<SRC, ?> collectorToDoublePlus() {
            return collector;
        }
    }
}
