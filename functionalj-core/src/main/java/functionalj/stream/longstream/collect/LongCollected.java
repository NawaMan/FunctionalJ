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
package functionalj.stream.longstream.collect;

import static java.util.Objects.requireNonNull;
import java.util.function.ObjLongConsumer;
import functionalj.function.aggregator.LongAggregation;
import functionalj.stream.collect.Collected;
import lombok.val;

public interface LongCollected<ACCUMULATED, RESULT> extends Collected<Long, ACCUMULATED, RESULT> {

    public static <ACC, RES> LongCollected<ACC, RES> collectedOf(LongAggregation<RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (LongCollectorPlus<ACC, RES>) aggregation.longCollectorPlus();
        return new LongCollected.Impl<>(collectorPlus);
    }

    public static <ACC, RES> LongCollected<ACC, RES> collectedOf(LongCollectorPlus<ACC, RES> collector) {
        requireNonNull(collector);
        return new LongCollected.Impl<>(collector);
    }

    public static <ACC, RES> LongCollected<ACC, RES> of(LongAggregation<RES> aggregation) {
        requireNonNull(aggregation);
        @SuppressWarnings("unchecked")
        val collectorPlus = (LongCollectorPlus<ACC, RES>) aggregation.longCollectorPlus();
        return new LongCollected.Impl<>(collectorPlus);
    }

    public static <ACC, RES> LongCollected<ACC, RES> of(LongCollectorPlus<ACC, RES> collector) {
        requireNonNull(collector);
        return new LongCollected.Impl<>(collector);
    }

    // == Instance ==
    public void accumulate(long each);

    public RESULT finish();

    public default void accumulate(Long each) {
        accumulate(each);
    }

    // == Implementation ==
    public static class Impl<ACCUMULATED, RESULT> implements LongCollected<ACCUMULATED, RESULT> {

        private final LongCollectorPlus<ACCUMULATED, RESULT> collector;

        private final ObjLongConsumer<ACCUMULATED> accumulator;

        private final ACCUMULATED accumulated;

        public Impl(LongCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.longAccumulator();
        }

        public void accumulate(long each) {
            accumulator.accept(accumulated, each);
        }

        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
    }
}
