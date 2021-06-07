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
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;

import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import lombok.val;


@FunctionalInterface
public interface IntAggregationToInt extends IntAggregation<Integer> {
    
    public static <A> IntAggregationToInt from(IntCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public IntCollectorToIntPlus<?> collectorToIntPlus();
    
    
    @Override
    public default IntCollectorPlus<?, Integer> intCollectorPlus() {
        return collectorToIntPlus();
    }
    
    public default IntAggregatorToInt newIntAccumulatorToInt() {
        val collector = collectorToIntPlus();
        return new IntAggregatorToInt.Impl(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> AggregationToInt<INPUT> of(ToIntFunction<INPUT> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default IntAggregation<Integer> ofInt(IntFunction<Integer> mapper) {
        if (mapper instanceof IntUnaryOperator) {
            return ofIntToInt((IntUnaryOperator)mapper);
        }
        
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
    // This is a terrible name .... :-(
    // But if we use ofInt, Java confuse this one and the one above
    public default IntAggregationToInt ofIntToInt(IntUnaryOperator mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default LongAggregationToInt ofLong(LongToIntFunction mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
    public default DoubleAggregationToInt ofDouble(DoubleToIntFunction mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
}
