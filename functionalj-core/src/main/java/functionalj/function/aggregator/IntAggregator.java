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

import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import lombok.val;

@FunctionalInterface
public interface IntAggregator<TARGET> extends AsIntAggregator<TARGET> {
    
    public static <A, T> IntAggregator<T> from(IntCollectorPlus<A, T> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToInt from(IntCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToLong from(IntCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToDouble from(IntCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToInt forInt(IntCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToLong forLong(IntCollectorToLongPlus<A> collector) {
        return () -> collector;
    }
    
    public static <A> IntAggregatorToDouble forDouble(IntCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    
    public IntCollectorPlus<?, TARGET> collector();
    
    
    public default TARGET process(IntStreamPlus stream) {
        val collector = collector();
        return ((IntAggregator<TARGET>)collector).process(stream);
    }
    
    public default IntAccumulator<TARGET> newAccumulator() {
        val collector = collector();
        return new IntAccumulator<>(collector);
    }
    
}
