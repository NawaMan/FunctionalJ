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

import static java.util.Objects.requireNonNull;

import java.util.function.ObjDoubleConsumer;
import java.util.stream.Collector;

import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.stream.collect.Collected;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import lombok.val;


public interface DoubleCollected<ACCUMULATED, RESULT> 
                    extends Collected<Double, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    public static <A, R> DoubleCollected<A, R> of(
            AsDoubleFuncList         funcList,
            DoubleStreamProcessor<R> processor) {
        requireNonNull(processor);
        if (processor instanceof Collector)
            return new DoubleCollected.ByCollector<>(((DoubleCollectorPlus<A, R>)processor));
        
        requireNonNull(funcList);
        return new DoubleCollected.ByStreamProcessor<A, R>(funcList, processor);
    }
    
    public static <A, R> DoubleCollected<A, R> of(
            DoubleCollectorPlus<A, R> collector) {
        requireNonNull(collector);
        return new DoubleCollected.ByCollector<>(collector);
    }
    
    public static <A> DoubleCollectedToInt<A> of(
            DoubleCollectorToIntPlus<A> collector) {
        requireNonNull(collector);
        return new DoubleCollectedToInt.ByCollector<>(collector);
    }
    
    public static <A> DoubleCollectedToLong<A> of(
            DoubleCollectorToLongPlus<A> collector) {
        requireNonNull(collector);
        return new DoubleCollectedToLong.ByCollector<>(collector);
    }
    
    public static <A> DoubleCollectedToDouble<A> of(
            DoubleCollectorToDoublePlus<A> collector) {
        requireNonNull(collector);
        return new DoubleCollectedToDouble.ByCollector<>(collector);
    }
    
    
    public void   accumulate(double each);
    public RESULT finish();
    
    public default void accumulate(Double each) {
        accumulate(each);
    }
    
    //== Implementation ==
    
    public static class ByCollector<ACCUMULATED, RESULT>
            implements
                DoubleStreamProcessor<RESULT>,
                DoubleCollected<ACCUMULATED, RESULT> {
        
        private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
        private final ObjDoubleConsumer<ACCUMULATED>           accumulator;
        private final ACCUMULATED                              accumulated;
        
        public ByCollector(DoubleCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.doubleAccumulator();
        }
        
        public void accumulate(double each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public RESULT process(DoubleStreamPlus stream) {
            return stream.calculate(collector);
        }
        
    }
    
    public static class ByStreamProcessor<ACCUMULATED, RESULT>
            implements
                DoubleCollected<ACCUMULATED, RESULT> {
        
        private final DoubleStreamProcessor<RESULT> processor;
        private final AsDoubleFuncList              funcList;
        
        ByStreamProcessor(
                AsDoubleFuncList              funcList,
                DoubleStreamProcessor<RESULT> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(double each) {
        }
        
        public RESULT finish() {
            val stream = funcList.doubleStreamPlus();
            return (RESULT) processor.process((DoubleStreamPlus)stream);
        }
    }
    
}

