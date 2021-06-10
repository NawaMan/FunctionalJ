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

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ToDoubleFunction;

import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import lombok.val;


public abstract class  DoubleAggregation<TARGET> extends Aggregation<Double, TARGET> {
    
    public static <A, T> DoubleAggregation<T> from(DoubleCollectorPlus<A, T> collector) {
        return new DoubleAggregation.Impl<T>(collector);
    }
    
    //== Instance == 
    
    public abstract DoubleCollectorPlus<?, TARGET> doubleCollectorPlus();
    
    
    @Override
    public CollectorPlus<Double, ?, TARGET> collectorPlus() {
        return doubleCollectorPlus();
    }
    
    public DoubleAggregator<TARGET> newAggregator() {
        val collector = doubleCollectorPlus();
        return new DoubleAggregator.Impl<>(collector);
    }
    
    //== Derived ==
    
    public <INPUT> Aggregation<INPUT, TARGET> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = doubleCollectorPlus().of(mapper);
        return new Aggregation.Impl<>(newCollector);
    }
    
    public IntAggregation<TARGET> of(IntToDoubleFunction mapper) {
        val newCollector = doubleCollectorPlus().of(mapper);
        return new IntAggregation.Impl<>(newCollector);
    }
    
    public LongAggregation<TARGET> of(LongToDoubleFunction mapper) {
        val newCollector = doubleCollectorPlus().of(mapper);
        return new LongAggregation.Impl<>(newCollector);
    }
    
    public DoubleAggregation<TARGET> of(DoubleUnaryOperator mapper) {
        val newCollector = doubleCollectorPlus().of(mapper);
        return new DoubleAggregation.Impl<>(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl<TRG> extends DoubleAggregation<TRG> {
        
        private final DoubleCollectorPlus<?, TRG> collector;
        
        public Impl(DoubleCollectorPlus<?, TRG> collector) {
            this.collector = collector;
        }
        
        @Override
        public DoubleCollectorPlus<?, TRG> doubleCollectorPlus() {
            return collector;
        }
        
    }
    
}
