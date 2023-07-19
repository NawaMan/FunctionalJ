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

import java.util.function.DoubleToIntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import lombok.val;

public abstract class IntAggregationToLong extends IntAggregation<Long> {
    
    public static <A> IntAggregationToLong from(IntCollectorToLongPlus<A> collector) {
        return new IntAggregationToLong.Impl(collector);
    }
    
    // == Instance ==
    public abstract IntCollectorToLongPlus<?> intCollectorToLongPlus();
    
    @Override
    public IntCollectorPlus<?, Long> intCollectorPlus() {
        return intCollectorToLongPlus();
    }
    
    public IntAggregatorToLong newAggregator() {
        val collector = intCollectorToLongPlus();
        return new IntAggregatorToLong.Impl(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToLong<INPUT> of(ToIntFunction<INPUT> mapper) {
        val newCollector = intCollectorToLongPlus().of(mapper);
        return new AggregationToLong.Impl<>(newCollector);
    }
    
    public IntAggregationToLong ofInt(IntUnaryOperator mapper) {
        val newCollector = intCollectorToLongPlus().of(mapper);
        return new IntAggregationToLong.Impl(newCollector);
    }
    
    public LongAggregationToLong ofLong(LongToIntFunction mapper) {
        val newCollector = intCollectorToLongPlus().of(mapper);
        return new LongAggregationToLong.Impl(newCollector);
    }
    
    public DoubleAggregationToLong ofDouble(DoubleToIntFunction mapper) {
        val newCollector = intCollectorToLongPlus().of(mapper);
        return new DoubleAggregationToLong.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl extends IntAggregationToLong {
        
        private final IntCollectorToLongPlus<?> collector;
        
        public Impl(IntCollectorToLongPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public IntCollectorToLongPlus<?> intCollectorToLongPlus() {
            return collector;
        }
    }
}
