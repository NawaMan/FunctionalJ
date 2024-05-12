// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.collect;

import static java.util.Objects.requireNonNull;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import functionalj.function.aggregator.Aggregation;
import lombok.val;

public interface Collected<DATA, ACCUMULATED, RESULT> {
    
    public static <INP, ACC, RES> Collected<INP, ACC, RES> collectedOf(Aggregation<? extends INP, RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (Collector<INP, ACC, RES>) aggregation.collectorPlus();
        return new Collected.Impl<>(collectorPlus);
    }
    
    public static <INP, ACC, RES> Collected<INP, ACC, RES> collectedOf(Collector<? extends INP, ACC, RES> collector) {
        requireNonNull(collector);
        @SuppressWarnings("unchecked")
        val collectorPlus = (Collector<INP, ACC, RES>) collector;
        return new Collected.Impl<>(collectorPlus);
    }
    
    public static <INP, ACC, RES> Collected<INP, ACC, RES> of(Aggregation<? extends INP, RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (Collector<INP, ACC, RES>) aggregation.collectorPlus();
        return new Collected.Impl<>(collectorPlus);
    }
    
    public static <INP, ACC, RES> Collected<INP, ACC, RES> of(Collector<? extends INP, ACC, RES> collector) {
        requireNonNull(collector);
        @SuppressWarnings("unchecked")
        val collectorPlus = (Collector<INP, ACC, RES>) collector;
        return new Collected.Impl<>(collectorPlus);
    }
    
    // == Instance ==
    public void accumulate(DATA each);
    
    public RESULT finish();
    
    // == Implementation ==
    public static class Impl<DATA, ACCUMULATED, RESULT> implements Collected<DATA, ACCUMULATED, RESULT> {
        
        private final Collector<DATA, ACCUMULATED, RESULT> collector;
        
        private final BiConsumer<ACCUMULATED, DATA> accumulator;
        
        private final ACCUMULATED accumulated;
        
        public Impl(Collector<DATA, ACCUMULATED, RESULT> collector) {
            this.collector = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.accumulator();
        }
        
        public void accumulate(DATA each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
    }
}
