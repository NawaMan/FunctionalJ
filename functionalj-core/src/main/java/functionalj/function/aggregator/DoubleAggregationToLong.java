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

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ToDoubleFunction;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;
import lombok.val;

public abstract class DoubleAggregationToLong extends DoubleAggregation<Long> {
    
    public static <A> DoubleAggregationToLong from(DoubleCollectorToLongPlus<A> collector) {
        return new DoubleAggregationToLong.Impl(collector);
    }
    
    // == Instance ==
    public abstract DoubleCollectorToLongPlus<?> doubleCollectorToLongPlus();
    
    @Override
    public DoubleCollectorPlus<?, Long> doubleCollectorPlus() {
        return doubleCollectorToLongPlus();
    }
    
    public DoubleAggregatorToLong newAggregator() {
        val collector = doubleCollectorToLongPlus();
        return new DoubleAggregatorToLong.Impl(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToLong<INPUT> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = doubleCollectorToLongPlus().of(mapper);
        return new AggregationToLong.Impl<>(newCollector);
    }
    
    public IntAggregationToLong ofInt(IntToDoubleFunction mapper) {
        val newCollector = doubleCollectorToLongPlus().of(mapper);
        return new IntAggregationToLong.Impl(newCollector);
    }
    
    public LongAggregationToLong ofLong(LongToDoubleFunction mapper) {
        val newCollector = doubleCollectorToLongPlus().of(mapper);
        return new LongAggregationToLong.Impl(newCollector);
    }
    
    public DoubleAggregationToLong ofDouble(DoubleUnaryOperator mapper) {
        val newCollector = doubleCollectorToLongPlus().of(mapper);
        return new DoubleAggregationToLong.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl extends DoubleAggregationToLong {
        
        private final DoubleCollectorToLongPlus<?> collector;
        
        public Impl(DoubleCollectorToLongPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public DoubleCollectorToLongPlus<?> doubleCollectorToLongPlus() {
            return collector;
        }
    }
}
