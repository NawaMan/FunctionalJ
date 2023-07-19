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

import functionalj.lens.lenses.DoubleToLongAccessPrimitive;
import functionalj.stream.doublestream.collect.DoubleCollectedToLong;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;

public interface DoubleAggregatorToLong extends DoubleToLongAccessPrimitive, DoubleAggregator<Long> {
    
    public DoubleCollectedToLong<?> asCollected();
    
    // == Implementation ==
    public static class Impl implements DoubleAggregatorToLong {
    
        private final DoubleCollectedToLong<?> collected;
    
        public Impl(DoubleCollectorToLongPlus<?> collector) {
            this.collected = DoubleCollectedToLong.of(collector);
        }
    
        @Override
        public long applyDoubleToLong(double input) {
            collected.accumulate(input);
            return collected.finish();
        }
    
        public DoubleCollectedToLong<?> asCollected() {
            return collected;
        }
    }
}
