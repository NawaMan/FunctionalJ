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

import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToDoublePlus;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;
import lombok.val;

@FunctionalInterface
public interface LongAggregation<TARGET> extends LongStreamProcessor<TARGET> {
    
    public static <A, T> LongAggregation<T> from(LongCollectorPlus<A, T> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToInt from(LongCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToLong from(LongCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToDouble from(LongCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToInt forInt(LongCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToLong forLong(LongCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> LongAggregationToDouble forDouble(LongCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    
    public LongCollectorPlus<?, TARGET> collector();
    
    
    public default TARGET process(LongStreamPlus stream) {
        val collector = collector();
        return ((LongStreamProcessor<TARGET>)collector).process(stream);
    }
    
    public default LongAggregator<TARGET> newAccumulator() {
        val collector = collector();
        return new LongAggregator<>(collector);
    }
    
}
