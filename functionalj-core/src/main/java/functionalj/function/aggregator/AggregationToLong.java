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
import functionalj.stream.collect.CollectorToLongPlus;
import lombok.val;

public abstract class AggregationToLong<SOURCE> extends Aggregation<SOURCE, Long> {
    
    public static <S, A> AggregationToLong<S> from(CollectorToLongPlus<S, A> collector) {
        return new AggregationToLong.Impl<S>(collector);
    }
    
    // == Instance ==
    public abstract CollectorToLongPlus<SOURCE, ?> collectorToLongPlus();
    
    @Override
    public CollectorToLongPlus<SOURCE, ?> collectorPlus() {
        return collectorToLongPlus();
    }
    
    public AggregatorToLong<SOURCE> newAggregator() {
        val collector = collectorToLongPlus();
        return new AggregatorToLong.Impl<>(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToLong<INPUT> of(Function<INPUT, SOURCE> mapper) {
        val newCollector = collectorToLongPlus().of(mapper);
        return new AggregationToLong.Impl<INPUT>(newCollector);
    }
    
    public IntAggregationToLong ofInt(IntFunction<SOURCE> mapper) {
        val newCollector = collectorToLongPlus().of(mapper);
        return new IntAggregationToLong.Impl(newCollector);
    }
    
    public LongAggregationToLong ofLong(LongFunction<SOURCE> mapper) {
        val newCollector = collectorToLongPlus().of(mapper);
        return new LongAggregationToLong.Impl(newCollector);
    }
    
    public DoubleAggregationToLong ofDouble(DoubleFunction<SOURCE> mapper) {
        val newCollector = collectorToLongPlus().of(mapper);
        return new DoubleAggregationToLong.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl<SRC> extends AggregationToLong<SRC> {
        
        private final CollectorToLongPlus<SRC, ?> collector;
        
        public Impl(CollectorToLongPlus<SRC, ?> collector) {
            this.collector = collector;
        }
        
        @Override
        public CollectorToLongPlus<SRC, ?> collectorToLongPlus() {
            return collector;
        }
    }
}
