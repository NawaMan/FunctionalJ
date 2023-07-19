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
import functionalj.stream.collect.CollectorToIntPlus;
import lombok.val;

public abstract class AggregationToInt<SOURCE> extends Aggregation<SOURCE, Integer> {
    
    public static <S, A> AggregationToInt<S> from(CollectorToIntPlus<S, A> collector) {
        return new AggregationToInt.Impl<S>(collector);
    }
    
    // == Instance ==
    public abstract CollectorToIntPlus<SOURCE, ?> collectorToIntPlus();
    
    @Override
    public CollectorToIntPlus<SOURCE, ?> collectorPlus() {
        return collectorToIntPlus();
    }
    
    public AggregatorToInt<SOURCE> newAggregator() {
        val collector = collectorToIntPlus();
        return new AggregatorToInt.Impl<>(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToInt<INPUT> of(Function<INPUT, SOURCE> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return new AggregationToInt.Impl<>(newCollector);
    }
    
    public IntAggregationToInt ofInt(IntFunction<SOURCE> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return new IntAggregationToInt.Impl(newCollector);
    }
    
    public LongAggregationToInt ofLong(LongFunction<SOURCE> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return new LongAggregationToInt.Impl(newCollector);
    }
    
    public DoubleAggregationToInt ofDouble(DoubleFunction<SOURCE> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return new DoubleAggregationToInt.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl<SRC> extends AggregationToInt<SRC> {
    
        private final CollectorToIntPlus<SRC, ?> collector;
    
        public Impl(CollectorToIntPlus<SRC, ?> collector) {
            this.collector = collector;
        }
    
        @Override
        public CollectorToIntPlus<SRC, ?> collectorToIntPlus() {
            return collector;
        }
    }
}
