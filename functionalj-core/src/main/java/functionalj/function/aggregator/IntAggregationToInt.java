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

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;

import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import lombok.val;


public abstract class IntAggregationToInt extends IntAggregation<Integer> {
    
    public static <A> IntAggregationToInt from(IntCollectorToIntPlus<A> collector) {
        return new IntAggregationToInt.Impl(collector);
    }
    
    //== Instance == 
    
    public abstract IntCollectorToIntPlus<?> intCollectorToIntPlus();
    
    
    @Override
    public IntCollectorPlus<?, Integer> intCollectorPlus() {
        return intCollectorToIntPlus();
    }
    
    public IntAggregatorToInt newIntAccumulatorToInt() {
        val collector = intCollectorToIntPlus();
        return new IntAggregatorToInt.Impl(collector);
    }
    
    //== Derived ==
    
    public <INPUT> AggregationToInt<INPUT> of(ToIntFunction<INPUT> mapper) {
        val newCollector = intCollectorToIntPlus().of(mapper);
        return new AggregationToInt.Impl<>(newCollector);
    }
    
    public IntAggregation<Integer> ofInt(IntFunction<Integer> mapper) {
        if (mapper instanceof IntUnaryOperator) {
            return ofIntToInt((IntUnaryOperator)mapper);
        }
        
        val newCollector = intCollectorToIntPlus().of(mapper);
        return new IntAggregation.Impl<>(newCollector);
    }
    
    // This is a terrible name .... :-(
    // But if we use ofInt, Java confuse this one and the one above
    public IntAggregationToInt ofIntToInt(IntUnaryOperator mapper) {
        val newCollector = intCollectorToIntPlus().of(mapper);
        return new IntAggregationToInt.Impl(newCollector);
    }
    
    public LongAggregationToInt ofLong(LongToIntFunction mapper) {
        val newCollector = intCollectorToIntPlus().of(mapper);
        return new LongAggregationToInt.Impl(newCollector);
    }
    
    //== Implementation ==
    
    public static class Impl extends IntAggregationToInt {
        
        private final IntCollectorToIntPlus<?> collector;
        
        public Impl(IntCollectorToIntPlus<?> collector) {
            this.collector = collector;
        }
        
        @Override
        public IntCollectorToIntPlus<?> intCollectorToIntPlus() {
            return collector;
        }
        
    }
    
}
