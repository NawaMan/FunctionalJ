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

import functionalj.lens.lenses.DoubleAccessPrimitive;
import functionalj.stream.doublestream.AsDoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import lombok.val;

@FunctionalInterface
public interface DoubleAggregationToDouble extends DoubleAccessPrimitive<AsDoubleStreamPlus> {
    
    public static <A> DoubleAggregationToDouble from(DoubleCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public DoubleCollectorToDoublePlus<?> collectorToDoublePlus();
    
    
    @Override
    public default double applyAsDouble(AsDoubleStreamPlus stream) {
        val collector = collectorToDoublePlus();
        return stream.collect(collector);
    }
    
    public default DoubleAggregatorToDouble newDoubleAccumulatorToDouble() {
        val collector = collectorToDoublePlus();
        return new DoubleAggregatorToDouble(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> AggregationToDouble<INPUT> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = collectorToDoublePlus().of(mapper);
        return () -> newCollector;
    }
    
}
