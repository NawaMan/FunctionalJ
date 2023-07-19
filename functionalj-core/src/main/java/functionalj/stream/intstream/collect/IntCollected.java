// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.intstream.collect;

import static java.util.Objects.requireNonNull;
import java.util.function.ObjIntConsumer;
import functionalj.function.aggregator.IntAggregation;
import functionalj.stream.collect.Collected;
import lombok.val;

public interface IntCollected<ACCUMULATED, RESULT> extends Collected<Integer, ACCUMULATED, RESULT> {
    
    public static <ACC, RES> IntCollected<ACC, RES> collectedOf(IntAggregation<RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (IntCollectorPlus<ACC, RES>) aggregation.intCollectorPlus();
        return new IntCollected.Impl<>(collectorPlus);
    }
    
    public static <ACC, RES> IntCollected<ACC, RES> collectedOf(IntCollectorPlus<ACC, RES> collector) {
        requireNonNull(collector);
        return new IntCollected.Impl<>(collector);
    }
    
    public static <ACC, RES> IntCollected<ACC, RES> of(IntAggregation<RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (IntCollectorPlus<ACC, RES>) aggregation.intCollectorPlus();
        return new IntCollected.Impl<>(collectorPlus);
    }
    
    public static <ACC, RES> IntCollected<ACC, RES> of(IntCollectorPlus<ACC, RES> collector) {
        requireNonNull(collector);
        return new IntCollected.Impl<>(collector);
    }
    
    // == Instance ==
    public void accumulate(int each);
    
    public RESULT finish();
    
    public default void accumulate(Integer each) {
        accumulate(each);
    }
    
    // == Implementation ==
    public static class Impl<ACCUMULATED, RESULT> implements IntCollected<ACCUMULATED, RESULT> {
        
        private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
        
        private final ObjIntConsumer<ACCUMULATED> accumulator;
        
        private final ACCUMULATED accumulated;
        
        public Impl(IntCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.intAccumulator();
        }
        
        public void accumulate(int each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
    }
}
