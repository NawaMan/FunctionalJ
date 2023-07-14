// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import java.util.function.LongFunction;
import functionalj.stream.longstream.collect.LongCollected;
import functionalj.stream.longstream.collect.LongCollectorPlus;

public interface LongAggregator<TARGET> extends LongFunction<TARGET>, Aggregator<Long, TARGET> {

    public LongCollected<?, TARGET> asCollected();

    // == Implementation ==
    public static class Impl<TARGET> implements LongAggregator<TARGET> {

        private final LongCollected<?, TARGET> collected;

        public Impl(LongCollectorPlus<?, TARGET> collector) {
            this.collected = LongCollected.collectedOf(collector);
        }

        @Override
        public TARGET apply(long input) {
            collected.accumulate(input);
            return collected.finish();
        }

        @Override
        public TARGET applyUnsafe(Long input) throws Exception {
            return apply(input);
        }

        public LongCollected<?, TARGET> asCollected() {
            return collected;
        }
    }
}
