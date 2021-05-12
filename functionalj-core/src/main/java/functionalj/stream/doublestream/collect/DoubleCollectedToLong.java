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
package functionalj.stream.doublestream.collect;

import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.stream.collect.Collected;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import lombok.val;


public interface DoubleCollectedToLong<ACCUMULATED> 
                    extends Collected<Double, ACCUMULATED, Long> {
    
    public void accumulate(double each);
    public long finishAsLong();
    
    public default Long finish() {
        return finishAsLong();
    }
    
    public default void accumulate(Double each) {
        accumulate(each);
    }
    
    //== Implementation ==
    
    public static class ByCollector<ACCUMULATED>
            implements
                DoubleStreamProcessor<Long>,
                DoubleCollectedToLong<ACCUMULATED> {
        
        private final DoubleCollectorToLongPlus<ACCUMULATED> collector;
        private final DoubleAccumulator<ACCUMULATED>         accumulator;
        private final ACCUMULATED                            accumulated;
        
        public ByCollector(DoubleCollectorToLongPlus<ACCUMULATED> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.doubleAccumulator();
        }
        
        public void accumulate(double each) {
            accumulator.accept(accumulated, each);
        }
        
        @Override
        public long finishAsLong() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public Long process(DoubleStreamPlus stream) {
            return stream.calculate(collector);
        }
        
    }
    
    public static class ByStreamProcessor<ACCUMULATED>
            implements
                DoubleCollectedToLong<ACCUMULATED> {
        
        private final DoubleStreamProcessor<Long> processor;
        private final AsDoubleFuncList            funcList;
        
        ByStreamProcessor(
                AsDoubleFuncList            funcList,
                DoubleStreamProcessor<Long> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(double each) {
        }
        
        @Override
        public long finishAsLong() {
            val stream = funcList.doubleStreamPlus();
            return processor.process((DoubleStreamPlus)stream);
        }
        
    }
    
}

