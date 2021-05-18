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

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

import functionalj.list.AsFuncList;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.longlist.AsLongFuncList;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.doublestream.collect.DoubleCollected;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import functionalj.stream.intstream.collect.IntCollected;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import functionalj.stream.longstream.collect.LongCollected;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;


public interface Collected<DATA, ACCUMULATED, RESULT> {
    
    public void   accumulate(DATA each);
    public RESULT finish();
    
    
    public static class ByCollector<DATA, ACCUMULATED, RESULT>
            implements
                StreamProcessor<DATA, RESULT>,
                Collected<DATA, ACCUMULATED, RESULT> {
        
        private final Collector<DATA, ACCUMULATED, RESULT> collector;
        private final BiConsumer<ACCUMULATED, DATA>        accumulator;
        private final ACCUMULATED                          accumulated;
        
        public ByCollector(Collector<DATA, ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.accumulator();
        }
        
        public void accumulate(DATA each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            return collector.finisher().apply(accumulated);
        }
        
        @Override
        public RESULT process(StreamPlus<? extends DATA> stream) {
            return stream.calculate(collector);
        }
    }
    
    public static class ByStreamProcessor<DATA, ACCUMULATED, RESULT>
            implements
                Collected<DATA, ACCUMULATED, RESULT> {
        
        private final StreamProcessor<? extends DATA, RESULT> processor;
        private final AsFuncList<DATA> funcList;
        
        ByStreamProcessor(
                AsFuncList<DATA>                        funcList,
                StreamProcessor<? extends DATA, RESULT> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(DATA each) {
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public RESULT finish() {
            val stream = funcList.streamPlus();
            return (RESULT) processor.process((StreamPlus)stream);
        }
    }
    
    
    public static <D, A, R> Collected<D, A, R> collectedOf(
            AsFuncList<D>                   funcList,
            StreamProcessor<? extends D, R> processor) {
        return of(funcList, processor);
    }
    
    @SuppressWarnings("unchecked")
    public static <D, A, R> Collected<D, A, R> of(
            AsFuncList<D>                   funcList,
            StreamProcessor<? extends D, R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollector<D, A, R>(((Collector<D, A, R>)processor));
        
        Objects.requireNonNull(funcList);
        return new ByStreamProcessor<>(funcList, processor);
    }
    
    public static <A, R> IntCollected<A, R> of(
            AsIntFuncList         funcList,
            IntStreamProcessor<R> processor) {
        return IntCollected.of(funcList, processor);
    }
    
    public static <A, R> LongCollected<A, R> of(
            AsLongFuncList         funcList,
            LongStreamProcessor<R> processor) {
        return LongCollected.of(funcList, processor);
    }
    
    public static <A, R> DoubleCollected<A, R> of(
            AsDoubleFuncList         funcList,
            DoubleStreamProcessor<R> processor) {
        return DoubleCollected.of(funcList, processor);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D, A, R> Collected<D, A, R> of(CollectorPlus<D, A, R> collector) {
        requireNonNull(collector);
        return new ByCollector(collector);
    }
    
    public static <A, R> IntCollected<A, R> of(IntCollectorPlus<A, R> collector) {
        return IntCollected.of(collector);
    }
    
    public static <A, R> LongCollected<A, R> of(LongCollectorPlus<A, R> collector) {
    return LongCollected.of(collector);
    }
    
    public static <A, R> DoubleCollected<A, R> of(DoubleCollectorPlus<A, R> processor) {
        return DoubleCollected.of(processor);
    }
    
}

