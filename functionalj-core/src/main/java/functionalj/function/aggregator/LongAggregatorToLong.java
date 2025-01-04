// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import functionalj.lens.lenses.LongToLongAccessPrimitive;
import functionalj.stream.longstream.collect.LongCollectedToLong;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;

public interface LongAggregatorToLong extends LongToLongAccessPrimitive, LongAggregator<Long> {
    
    public LongCollectedToLong<?> asCollected();
    
    // == Implementation ==
    public static class Impl implements LongAggregatorToLong {
        
        private final LongCollectedToLong<?> collected;
        
        public Impl(LongCollectorToLongPlus<?> collector) {
            this.collected = LongCollectedToLong.of(collector);
        }
        
        @Override
        public long applyLongToLong(long input) {
            collected.accumulate(input);
            return collected.finish();
        }
        
        public LongCollectedToLong<?> asCollected() {
            return collected;
        }
    }
}
