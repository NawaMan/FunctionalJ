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
package functionalj.stream.collect;

import java.util.function.BiConsumer;

public interface CollectedToLong<DATA, ACCUMULATED> extends Collected<DATA, ACCUMULATED, Long> {
    
    public static <SRC, ACC> CollectedToLong<SRC, ACC> of(CollectorToLongPlus<SRC, ACC> collector) {
        return new CollectedToLong.Impl<SRC, ACC>(collector);
    }
    
    // == Instance ==
    public void accumulate(DATA each);
    
    public long finishToLong();
    
    public default Long finish() {
        return finishToLong();
    }
    
    // -- Implementation --
    public static class Impl<DATA, ACCUMULATED> implements CollectedToLong<DATA, ACCUMULATED> {
        
        private final CollectorToLongPlus<DATA, ACCUMULATED> collector;
        
        private final BiConsumer<ACCUMULATED, DATA> accumulator;
        
        private final ACCUMULATED accumulated;
        
        public Impl(CollectorToLongPlus<DATA, ACCUMULATED> collector) {
            this.collector = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.accumulator();
        }
        
        public void accumulate(DATA each) {
            accumulator.accept(accumulated, each);
        }
        
        public long finishToLong() {
            return collector.finisherToLong().applyAsLong(accumulated);
        }
    }
}
