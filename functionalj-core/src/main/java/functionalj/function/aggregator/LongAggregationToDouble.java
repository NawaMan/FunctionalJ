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

import java.util.function.DoubleToLongFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToDoublePlus;
import lombok.val;

public abstract class LongAggregationToDouble extends LongAggregation<Double> {
    
    public static <A> LongAggregationToDouble from(LongCollectorToDoublePlus<A> collector) {
        return new LongAggregationToDouble.Impl(collector);
    }
    
    // == Instance ==
    public abstract LongCollectorToDoublePlus<?> longCollectorToDoublePlus();
    
    @Override
    public LongCollectorPlus<?, Double> longCollectorPlus() {
        return longCollectorToDoublePlus();
    }
    
    public LongAggregatorToDouble newAggregator() {
        val collector = longCollectorToDoublePlus();
        return new LongAggregatorToDouble.Impl(collector);
    }
    
    // == Derived ==
    public <INPUT> AggregationToDouble<INPUT> of(ToLongFunction<INPUT> mapper) {
        val newCollector = longCollectorToDoublePlus().of(mapper);
        return new AggregationToDouble.Impl<INPUT>(newCollector);
    }
    
    public IntAggregationToDouble ofInt(IntToLongFunction mapper) {
        val newCollector = longCollectorToDoublePlus().of(mapper);
        return new IntAggregationToDouble.Impl(newCollector);
    }
    
    public LongAggregationToDouble ofLong(LongUnaryOperator mapper) {
        val newCollector = longCollectorToDoublePlus().of(mapper);
        return new LongAggregationToDouble.Impl(newCollector);
    }
    
    public DoubleAggregationToDouble ofDouble(DoubleToLongFunction mapper) {
        val newCollector = longCollectorToDoublePlus().of(mapper);
        return new DoubleAggregationToDouble.Impl(newCollector);
    }
    
    // == Implementation ==
    public static class Impl extends LongAggregationToDouble {
        
        private final LongCollectorToDoublePlus<?> collector;
        
        public Impl(LongCollectorToDoublePlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public LongCollectorToDoublePlus<?> longCollectorToDoublePlus() {
            return collector;
        }
    }
}
