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

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collector;

import functionalj.list.intlist.AsIntFuncList;
import functionalj.stream.collect.Collected;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import lombok.val;


public interface IntCollected<ACCUMULATED, RESULT> 
                    extends Collected<Integer, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    public static <A, R> IntCollected<A, R> of(
            AsIntFuncList         funcList,
            IntStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new IntCollected.ByCollector<>(((IntCollectorPlus<A, R>)processor));
        
        Objects.requireNonNull(funcList);
        return new IntCollected.ByStreamProcessor<A, R>(funcList, processor);
    }
    
    public static <A, R> IntCollected<A, R> of(
            IntCollectorPlus<A, R> collector) {
        requireNonNull(collector);
        return new IntCollected.ByCollector<>(collector);
    }
    
    public static <A> IntCollectedToInt<A> of(
            IntCollectorToIntPlus<A> collector) {
        requireNonNull(collector);
        return new IntCollectedToInt.ByCollector<>(collector);
    }
    
    public static <A> IntCollectedToLong<A> of(
            IntCollectorToLongPlus<A> collector) {
        requireNonNull(collector);
        return new IntCollectedToLong.ByCollector<>(collector);
    }
    
    public static <A> IntCollectedToDouble<A> of(
            IntCollectorToDoublePlus<A> collector) {
        requireNonNull(collector);
        return new IntCollectedToDouble.ByCollector<>(collector);
    }
    
    
    public void   accumulate(int each);
    public RESULT finish();
    
    public default void accumulate(Integer each) {
        accumulate(each);
    }
    
    //== Implementation ==
    
    public static class ByCollector<ACCUMULATED, RESULT>
            implements
                IntStreamProcessor<RESULT>,
                IntCollected<ACCUMULATED, RESULT> {
        
        private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
        private final ObjIntConsumer<ACCUMULATED>           accumulator;
        private final ACCUMULATED                           accumulated;
        
        public ByCollector(IntCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.intAccumulator();
        }
        
        public void accumulate(int each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public RESULT process(IntStreamPlus stream) {
            return stream.calculate(collector);
        }
        
    }
    
    public static class ByStreamProcessor<ACCUMULATED, RESULT>
            implements
                IntCollected<ACCUMULATED, RESULT> {
        
        private final IntStreamProcessor<RESULT> processor;
        private final AsIntFuncList              funcList;
        
        ByStreamProcessor(
                AsIntFuncList              funcList,
                IntStreamProcessor<RESULT> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(int each) {
        }
        
        public RESULT finish() {
            val stream = funcList.intStreamPlus();
            return (RESULT) processor.process((IntStreamPlus)stream);
        }
    }
    
}

