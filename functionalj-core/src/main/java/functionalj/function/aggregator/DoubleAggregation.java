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

import functionalj.function.Func1;
import functionalj.stream.doublestream.AsDoubleStreamPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToIntPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;
import lombok.val;

@FunctionalInterface
public interface DoubleAggregation<TARGET> extends Func1<AsDoubleStreamPlus, TARGET> {
    
    public static <A, T> DoubleAggregation<T> from(DoubleCollectorPlus<A, T> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToInt from(DoubleCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToLong from(DoubleCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToDouble from(DoubleCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToInt forInt(DoubleCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToLong forLong(DoubleCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> DoubleAggregationToDouble forDouble(DoubleCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public DoubleCollectorPlus<?, TARGET> doubleCollectorPlus();
    
    
    public default TARGET applyUnsafe(AsDoubleStreamPlus stream) throws Exception {
        val collector = doubleCollectorPlus();
        return stream.collect(collector);
    }
    
    public default DoubleAggregator<TARGET> newAggregator() {
        val collector = doubleCollectorPlus();
        return new DoubleAggregator<>(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> Aggregation<INPUT, TARGET> of(ToDoubleFunction<INPUT> mapper) {
        val newCollector = doubleCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
}
