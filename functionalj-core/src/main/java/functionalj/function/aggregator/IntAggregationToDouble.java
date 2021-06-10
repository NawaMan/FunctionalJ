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

import java.util.function.DoubleToIntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;

import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import lombok.val;


public abstract class IntAggregationToDouble extends IntAggregation<Double> {
    
    public static <A> IntAggregationToDouble from(IntCollectorToDoublePlus<A> collector) {
        return new IntAggregationToDouble.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract IntCollectorToDoublePlus<?> intCollectorToDoublePlus();
    
    
    @Override
    public IntCollectorPlus<?, Double> intCollectorPlus() {
        return intCollectorToDoublePlus();
    }
    
    public IntAggregatorToDouble newIntAccumulatorToDouble() {
        val collector = intCollectorToDoublePlus();
        return new IntAggregatorToDouble.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToDouble<INPUT> of(ToIntFunction<INPUT> mapper) {
        val newCollector = intCollectorToDoublePlus().of(mapper);
        return new AggregationToDouble.Impl<INPUT>(newCollector);
    }
    
    public IntAggregationToDouble ofInt(IntUnaryOperator mapper) {
        val newCollector = intCollectorToDoublePlus().of(mapper);
        return new IntAggregationToDouble.Impl(newCollector);
    }
    
    public LongAggregationToDouble ofLong(LongToIntFunction mapper) {
        val newCollector = intCollectorToDoublePlus().of(mapper);
        return new LongAggregationToDouble.Impl(newCollector);
    }
    
    public DoubleAggregationToDouble ofDouble(DoubleToIntFunction mapper) {
        val newCollector = intCollectorToDoublePlus().of(mapper);
        return new DoubleAggregationToDouble.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends IntAggregationToDouble {
        
        private final IntCollectorToDoublePlus<?> collector;
        
        public Impl(IntCollectorToDoublePlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public IntCollectorToDoublePlus<?> intCollectorToDoublePlus() {
            return collector;
        }
        
    }
    
}
