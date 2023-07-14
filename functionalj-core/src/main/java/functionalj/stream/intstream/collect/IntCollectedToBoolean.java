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
package functionalj.stream.intstream.collect;

import java.util.function.ObjIntConsumer;
import functionalj.stream.collect.Collected;

public interface IntCollectedToBoolean<ACCUMULATED> extends Collected<Integer, ACCUMULATED, Boolean>, IntCollected<ACCUMULATED, Boolean> {

    public static <ACC> IntCollectedToBoolean<ACC> of(IntCollectorToBooleanPlus<ACC> collector) {
        return new IntCollectedToBoolean.Impl<ACC>(collector);
    }

    // == Instance ==
    public void accumulate(int each);

    public boolean finishAsBoolean();

    public default Boolean finish() {
        return finishAsBoolean();
    }

    public default void accumulate(Integer each) {
        accumulate(each);
    }

    // == Implementation ==
    public static class Impl<ACCUMULATED> implements IntCollectedToBoolean<ACCUMULATED> {

        private final IntCollectorToBooleanPlus<ACCUMULATED> collector;

        private final ObjIntConsumer<ACCUMULATED> accumulator;

        private final ACCUMULATED accumulated;

        public Impl(IntCollectorToBooleanPlus<ACCUMULATED> collector) {
            this.collector = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.intAccumulator();
        }

        public void accumulate(int each) {
            accumulator.accept(accumulated, each);
        }

        @Override
        public boolean finishAsBoolean() {
            return collector.finisher().apply(accumulated);
        }
    }
}
