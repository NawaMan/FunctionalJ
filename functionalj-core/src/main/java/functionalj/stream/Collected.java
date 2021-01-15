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
package functionalj.stream;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

import functionalj.list.AsFuncList;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.doublestream.DoubleAccumulator;
import functionalj.stream.doublestream.DoubleCollectorPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.intstream.IntAccumulator;
import functionalj.stream.intstream.IntCollectorPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import lombok.val;



public interface Collected<DATA, ACCUMULATED, RESULT> {
    
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
    
    public void   accumulate(DATA each);
    public RESULT finish();
    
    
    // TODO - Add ofIntInt, ofIntIntToInt, ...
    
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedInt<A, R> ofInt(
            AsIntFuncList         funcList,
            IntStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollectedInt<>(((IntCollectorPlus<A, R>)processor));
        
        Objects.requireNonNull(funcList);
        return new ByIntStreamProcessor<>(funcList, processor);
    }
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedDouble<A, R> ofDouble(
            AsDoubleFuncList         funcList,
            DoubleStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return (CollectedDouble<A, R>) new ByCollectedDouble<A, R>((DoubleCollectorPlus<A, R>) processor);
        
        Objects.requireNonNull(funcList);
        return new ByDoubleStreamProcessor<A, R>(funcList, processor);
    }
    
    
    public static class ByCollector<DATA, ACCUMULATED, RESULT>
            implements
                StreamProcessor<DATA, RESULT>,
                Collected<DATA, ACCUMULATED, RESULT>{
        
        private final Collector<DATA, ACCUMULATED, RESULT> collector;
        private final BiConsumer<ACCUMULATED, DATA>        accumulator;
        private final ACCUMULATED                          accumulated;
        
        ByCollector(Collector<DATA, ACCUMULATED, RESULT> collector) {
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
    
    public static interface CollectedInt<ACCUMULATED, RESULT> {
        
        public static <A, R> CollectedInt<A, R> collectedOf(
                IntFuncList         FuncList,
                IntStreamProcessor<R> processor) {
            return Collected.ofInt(FuncList, processor);
        }
        
        public void   accumulate(int each);
        public RESULT finish();
    }
    
    public static class ByCollectedInt<ACCUMULATED, RESULT>
                    implements CollectedInt<ACCUMULATED, RESULT> {
        
        private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
        private final IntAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                           accumulated;
        
        public ByCollectedInt(IntCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.intAccumulator();
        }
        
        public void accumulate(int each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            val finisher = collector.finisher();
            return finisher.apply(accumulated);
        }
        
    }
    
    public static class ByIntStreamProcessor<ACCUMULATED, RESULT>
                    implements CollectedInt<ACCUMULATED, RESULT> {
        
        private final IntStreamProcessor<RESULT> processor;
        private final AsIntFuncList              funcList;
        
        public ByIntStreamProcessor(
                AsIntFuncList              funcList,
                IntStreamProcessor<RESULT> processor) {
            this.processor = processor;
            this.funcList  = funcList;
        }
        
        public void accumulate(int each) {
        }
        
        public RESULT finish() {
            val intStream = funcList.intStreamPlus();
            return processor.process(intStream);
        }
    }
    
    public static interface CollectedDouble<ACCUMULATED, RESULT> {
        
        public static <A, R> CollectedDouble<A, R> collectedOf(
                AsDoubleFuncList         funcList,
                DoubleStreamProcessor<R> processor) {
            return Collected.ofDouble(funcList, processor);
        }
        
        public void   accumulate(double each);
        public RESULT finish();
    }
    
    public static class ByCollectedDouble<ACCUMULATED, RESULT> {
        
        private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
        private final DoubleAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                              accumulated;
        
        public ByCollectedDouble(DoubleCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.doubleAccumulator();
        }
        
        public void accumulate(double each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            val finisher = collector.finisher();
            return finisher.apply(accumulated);
        }
        
    }
    
    public static class ByDoubleStreamProcessor<ACCUMULATED, RESULT>
                    implements CollectedDouble<ACCUMULATED, RESULT> {
        
        private final DoubleStreamProcessor<RESULT> processor;
        private final AsDoubleFuncList              funcList;
        
        public ByDoubleStreamProcessor(
                AsDoubleFuncList              stream,
                DoubleStreamProcessor<RESULT> processor) {
            this.processor = processor;
            this.funcList  = stream;
        }
        
        public void accumulate(double each) {
        }
        
        public RESULT finish() {
           val doubleStream = funcList.doubleStreamPlus();
            return processor.process(doubleStream);
        }
    }
    
}

