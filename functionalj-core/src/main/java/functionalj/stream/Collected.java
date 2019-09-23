// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import lombok.val;


interface Collected<DATA, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    static <D, A, R> Collected<D, A, R> of(
            StreamProcessor<D, R> processor, 
            Streamable<D>         streamable) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollector<D, A, R>(((Collector<D, A, R>)processor));
        
        Objects.requireNonNull(streamable);
        return new ByStreamProcessor<>(processor, streamable);
    }
    
    public void   accumulate(DATA each);
    public RESULT finish();
    
    
    static <A, R> CollectedInt<A, R> of(IntCollectorPlus<A, R> collector) {
        return new CollectedInt<A, R>(collector);
    }
    static <A, R> CollectedLong<A, R> of(LongCollectorPlus<A, R> collector) {
        return new CollectedLong<A, R>(collector);
    }
    static <A, R> CollectedDouble<A, R> of(DoubleCollectorPlus<A, R> collector) {
        return new CollectedDouble<A, R>(collector);
    }
    
    
    static class ByCollector<DATA, ACCUMULATED, RESULT> 
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
        public RESULT process(StreamPlus<DATA> stream) {
            return stream.calculate(collector);
        }
    }
    
    static class ByStreamProcessor<DATA, ACCUMULATED, RESULT>
            implements
                Collected<DATA, ACCUMULATED, RESULT> {
        
        private final StreamProcessor<DATA, RESULT> processor;
        private final Streamable<DATA>              streamable;
        
        ByStreamProcessor(
                StreamProcessor<DATA, RESULT> processor, 
                Streamable<DATA>              stream) {
            this.processor  = processor;
            this.streamable = stream;
        }
        
        public void accumulate(DATA each) {
        }
        
        public RESULT finish() {
            val stream = streamable.stream();
            return processor.process(stream);
        }
    }
    
    static class CollectedInt<ACCUMULATED, RESULT> {
        
        private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
        private final IntAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                           accumulated;
        
        CollectedInt(IntCollectorPlus<ACCUMULATED, RESULT> collector) {
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
    
    static class CollectedLong<ACCUMULATED, RESULT> {
        
        private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
        private final LongAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                            accumulated;
        
        CollectedLong(LongCollectorPlus<ACCUMULATED, RESULT> collector) {
            this.collector   = collector;
            this.accumulated = collector.supplier().get();
            this.accumulator = collector.longAccumulator();
        }
        
        public void accumulate(long each) {
            accumulator.accept(accumulated, each);
        }
        
        public RESULT finish() {
            val finisher = collector.finisher();
            return finisher.apply(accumulated);
        }
        
    }
    
    static class CollectedDouble<ACCUMULATED, RESULT> {
        
        private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
        private final DoubleAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                              accumulated;
        
        CollectedDouble(DoubleCollectorPlus<ACCUMULATED, RESULT> collector) {
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
    
}

