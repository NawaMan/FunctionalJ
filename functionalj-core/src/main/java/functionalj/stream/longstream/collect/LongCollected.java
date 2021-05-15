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

import java.util.Objects;
import java.util.function.ObjLongConsumer;
import java.util.stream.Collector;

import functionalj.list.longlist.AsLongFuncList;
import functionalj.stream.collect.Collected;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import lombok.val;


public interface LongCollected<ACCUMULATED, RESULT> 
                    extends Collected<Long, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    public static <A, R> LongCollected<A, R> of(
            AsLongFuncList         funcList,
            LongStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new LongCollected.ByCollector<>(((LongCollectorPlus<A, R>)processor));
        
        Objects.requireNonNull(funcList);
        return new LongCollected.ByStreamProcessor<A, R>(funcList, processor);
    }
    
    public static <A, R> LongCollected<A, R> of(
            LongCollectorPlus<A, R> collector) {
        requireNonNull(collector);
        return new LongCollected.ByCollector<>(collector);
    }
    
    public static <A> LongCollectedToInt<A> of(
            LongCollectorToIntPlus<A> collector) {
        requireNonNull(collector);
        return new LongCollectedToInt.ByCollector<>(collector);
    }
    
    public static <A> LongCollectedToLong<A> of(
            LongCollectorToLongPlus<A> collector) {
        requireNonNull(collector);
        return new LongCollectedToLong.ByCollector<>(collector);
    }
    
    public static <A> LongCollectedToDouble<A> of(
            LongCollectorToDoublePlus<A> collector) {
        requireNonNull(collector);
        return new LongCollectedToDouble.ByCollector<>(collector);
    }
    
    
    public void   accumulate(long each);
    public RESULT finish();
    
    public default void accumulate(Long each) {
        accumulate(each);
    }
    
    //== Implementation ==
    
    public static class ByCollector<ACCUMULATED, RESULT>
            implements
                LongStreamProcessor<RESULT>,
                LongCollected<ACCUMULATED, RESULT> {
        
        private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
        private final ObjLongConsumer<ACCUMULATED>           accumulator;
        private final ACCUMULATED                            accumulated;
        
        public ByCollector(LongCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.longAccumulator();
        }
        
        public void accumulate(long each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public RESULT process(LongStreamPlus stream) {
            return stream.calculate(collector);
        }
        
    }
    
    public static class ByStreamProcessor<ACCUMULATED, RESULT>
            implements
                LongCollected<ACCUMULATED, RESULT> {
        
        private final LongStreamProcessor<RESULT> processor;
        private final AsLongFuncList              funcList;
        
        ByStreamProcessor(
                AsLongFuncList              funcList,
                LongStreamProcessor<RESULT> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(long each) {
        }
        
        public RESULT finish() {
            val stream = funcList.longStreamPlus();
            return (RESULT) processor.process((LongStreamPlus)stream);
        }
    }
    
}

