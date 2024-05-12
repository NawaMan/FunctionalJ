// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.ToLongFunction;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

public abstract class LongAggregation<TARGET> extends Aggregation<Long, TARGET> {
    
    public static <A, T> LongAggregation<T> from(LongCollectorPlus<A, T> collector) {
        return new LongAggregation.Impl<T>(collector);
    }
    
    // == Instance ==
    public abstract LongCollectorPlus<?, TARGET> longCollectorPlus();
    
    @Override
    public CollectorPlus<Long, ?, TARGET> collectorPlus() {
        return longCollectorPlus();
    }
    
    public LongAggregator<TARGET> newAggregator() {
        val collector = longCollectorPlus();
        return new LongAggregator.Impl<>(collector);
    }
    
    // == Derived ==
    public <INPUT> Aggregation<INPUT, TARGET> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return new Aggregation.Impl<>(newCollector);
    }
    
    public IntAggregation<TARGET> ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return new IntAggregation.Impl<>(newCollector);
    }
    
    public LongAggregation<TARGET> ofLong(LongFunction<Long> mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return new LongAggregation.Impl<>(newCollector);
    }
    
    public DoubleAggregation<TARGET> ofDouble(DoubleToLongFunction mapper) {
        val newCollector = longCollectorPlus().of(mapper);
        return new DoubleAggregation.Impl<>(newCollector);
    }
    
    // == Implementation ==
    public static class Impl<TRG> extends LongAggregation<TRG> {
        
        private final LongCollectorPlus<?, TRG> collector;
        
        public Impl(LongCollectorPlus<?, TRG> collector) {
            this.collector = collector;
        }
        
        @Override
        public LongCollectorPlus<?, TRG> longCollectorPlus() {
            return collector;
        }
    }
}
