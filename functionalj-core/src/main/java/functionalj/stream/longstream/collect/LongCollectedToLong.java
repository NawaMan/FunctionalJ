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

import java.util.function.ObjLongConsumer;

import functionalj.list.longlist.AsLongFuncList;
import functionalj.stream.collect.Collected;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import lombok.val;


public interface LongCollectedToLong<ACCUMULATED> 
                    extends Collected<Long, ACCUMULATED, Long> {
    
    public void accumulate(long each);
    public long finishAsLong();
    
    public default Long finish() {
        return finishAsLong();
    }
    
    public default void accumulate(Long each) {
        accumulate(each);
    }
    
    //== Implementation ==
    
    public static class ByCollector<ACCUMULATED>
            implements
                LongStreamProcessor<Long>,
                LongCollectedToLong<ACCUMULATED> {
        
        private final LongCollectorToLongPlus<ACCUMULATED> collector;
        private final ObjLongConsumer<ACCUMULATED>         accumulator;
        private final ACCUMULATED                          accumulated;
        
        public ByCollector(LongCollectorToLongPlus<ACCUMULATED> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.longAccumulator();
        }
        
        public void accumulate(long each) {
            accumulator.accept(accumulated, each);
        }
        
        @Override
        public long finishAsLong() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public Long process(LongStreamPlus stream) {
            return stream.calculate(collector);
        }
        
    }
    
    public static class ByStreamProcessor<ACCUMULATED>
            implements
                LongCollectedToLong<ACCUMULATED> {
        
        private final LongStreamProcessor<Long> processor;
        private final AsLongFuncList            funcList;
        
        ByStreamProcessor(
                AsLongFuncList            funcList,
                LongStreamProcessor<Long> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(long each) {
        }
        
        @Override
        public long finishAsLong() {
            val stream = funcList.longStreamPlus();
            return processor.process((LongStreamPlus)stream);
        }
        
    }
    
}
