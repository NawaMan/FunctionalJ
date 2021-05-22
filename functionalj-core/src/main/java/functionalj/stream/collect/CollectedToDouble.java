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
package functionalj.stream.collect;

import java.util.function.BiConsumer;

public interface CollectedToDouble<DATA, ACCUMULATED>
                    extends Collected<DATA, ACCUMULATED, Double> {
    
    public void accumulate(DATA each);
    public double finishToDouble();
    
    public default Double finish() {
        return finishToDouble();
    }
    
    public static class Impl<DATA, ACCUMULATED> implements CollectedToDouble<DATA, ACCUMULATED> {
        
        private final CollectorToDoublePlus<DATA, ACCUMULATED> collector;
        private final BiConsumer<ACCUMULATED, DATA>            accumulator;
        private final ACCUMULATED                              accumulated;
        
        public Impl(CollectorToDoublePlus<DATA, ACCUMULATED> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.accumulator();
        }
        
        public void accumulate(DATA each) {
            accumulator.accept(accumulated, each);
        }
        
        public double finishToDouble() {
            return collector.finisherToDouble().applyAsDouble(accumulated);
        }
        
    }
    
    public static <SRC, ACC> CollectedToDouble<SRC, ACC> of(CollectorToDoublePlus<SRC, ACC> collector) {
        return new CollectedToDouble.Impl<SRC, ACC>(collector);
    }
    
}
