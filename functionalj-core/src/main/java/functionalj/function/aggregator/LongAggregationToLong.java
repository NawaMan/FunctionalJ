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

import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;
import lombok.val;


public abstract class LongAggregationToLong extends LongAggregation<Long> {
    
    public static <A> LongAggregationToLong from(LongCollectorToLongPlus<A> collector) {
        return new LongAggregationToLong.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract LongCollectorToLongPlus<?> longCollectorToLongPlus();
    
    
    @Override
    public LongCollectorPlus<?, Long> longCollectorPlus() {
        return longCollectorToLongPlus();
    }
    
    public LongAggregatorToLong newLongAccumulatorToLong() {
        val collector = longCollectorToLongPlus();
        return new LongAggregatorToLong.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToLong<INPUT> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorToLongPlus().of(mapper);
        return new AggregationToLong.Impl<>(newCollector);
    }
    
    public IntAggregationToLong ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorToLongPlus().of(mapper);
        return new IntAggregationToLong.Impl(newCollector);
    }
    
    public LongAggregation<Long> ofLong(LongFunction<Long> mapper) {
        if (mapper instanceof LongUnaryOperator) {
            val newCollector = longCollectorToLongPlus().of((LongUnaryOperator)mapper);
            return new LongAggregationToLong.Impl(newCollector);
        }
        
        val newCollector = longCollectorToLongPlus().of(mapper);
        return new LongAggregation.Impl<>(newCollector);
    }
    
    // This is a terrible name .... :-(
    // But if we just use `ofLong`, Java confuse this one and the one above
    public LongAggregationToLong ofLongToLong(LongUnaryOperator mapper) {
        val newCollector = longCollectorToLongPlus().of(mapper);
        return new LongAggregationToLong.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends LongAggregationToLong {
        
        private final LongCollectorToLongPlus<?> collector;
        
        public Impl(LongCollectorToLongPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public LongCollectorToLongPlus<?> longCollectorToLongPlus() {
            return collector;
        }
        
    }
    
}
