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

import java.util.function.ToDoubleFunction;

import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.stream.doublestream.AsDoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.doublestream.collect.DoubleCollectorToIntPlus;
import lombok.val;

@FunctionalInterface
public interface DoubleAggregationToInt extends IntegerAccessPrimitive<AsDoubleStreamPlus> {
    
    public static <A> DoubleAggregationToInt from(DoubleCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public DoubleCollectorToIntPlus<?> collectorToIntPlus();
    
    
    @Override
    public default int applyAsInt(AsDoubleStreamPlus stream) {
        val collector = collectorToIntPlus();
        return stream.collect(collector);
    }
    
    public default DoubleAggregatorToInt newDoubleAccumulatorToInt() {
        val collector = collectorToIntPlus();
        return new DoubleAggregatorToInt(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> AggregationToInt<INPUT> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = collectorToIntPlus().of(mapper);
        return () -> newCollector;
    }
    
}
