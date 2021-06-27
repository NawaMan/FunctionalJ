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

import java.util.function.DoubleToLongFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import lombok.val;


public abstract class LongAggregationToInt extends LongAggregation<Integer> {
    
    public static <A> LongAggregationToInt from(LongCollectorToIntPlus<A> collector) {
        return new LongAggregationToInt.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract LongCollectorToIntPlus<?> longCollectorToIntPlus();
    
    
    @Override
    public LongCollectorPlus<?, Integer> longCollectorPlus() {
        return longCollectorToIntPlus();
    }
    
    public LongAggregatorToInt newAggregator() {
        val collector = longCollectorToIntPlus();
        return new LongAggregatorToInt.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToInt<INPUT> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorToIntPlus().of(mapper);
        return new AggregationToInt.Impl<>(newCollector);
    }
    
    public IntAggregationToInt ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorToIntPlus().of(mapper);
        return new IntAggregationToInt.Impl(newCollector);
    }
    
    public LongAggregationToInt ofLong(LongUnaryOperator mapper) {
        val newCollector = longCollectorToIntPlus().of(mapper);
        return new LongAggregationToInt.Impl(newCollector);
    }
    
    public DoubleAggregationToInt ofDouble(DoubleToLongFunction mapper) {
        val newCollector = longCollectorToIntPlus().of(mapper);
        return new DoubleAggregationToInt.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends LongAggregationToInt {
        
        private final LongCollectorToIntPlus<?> collector;
        
        public Impl(LongCollectorToIntPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public LongCollectorToIntPlus<?> longCollectorToIntPlus() {
            return collector;
        }
        
    }
    
}
