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

import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import functionalj.stream.intstream.AsIntStreamPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import lombok.val;

@FunctionalInterface
public interface IntAggregation<TARGET> extends Func1<AsIntStreamPlus, TARGET> {
    
    public static <A, T> IntAggregation<T> from(IntCollectorPlus<A, T> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToInt from(IntCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToLong from(IntCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToDouble from(IntCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToInt forInt(IntCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToLong forLong(IntCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregationToDouble forDouble(IntCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    //== Instance == 
    
    public IntCollectorPlus<?, TARGET> intCollectorPlus();
    
    
    public default TARGET applyUnsafe(AsIntStreamPlus stream) throws Exception {
        val collector = intCollectorPlus();
        return stream.collect(collector);
    }
    
    public default IntAggregator<TARGET> newAggregator() {
        val collector = intCollectorPlus();
        return new IntAggregator<>(collector);
    }
    
    //== Derived ==
    
    public default <INPUT> Aggregation<INPUT, TARGET> of(ToIntFunction<INPUT> mapper) {
        val newCollector = intCollectorPlus().of(mapper);
        return () -> newCollector;
    }
    
}
