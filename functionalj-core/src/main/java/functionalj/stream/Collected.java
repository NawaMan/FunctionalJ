// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.stream.doublestream.DoubleAccumulator;
import functionalj.stream.doublestream.DoubleCollectorPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.doublestream.DoubleStreamable;
import functionalj.stream.intstream.IntAccumulator;
import functionalj.stream.intstream.IntCollectorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import functionalj.stream.longstream.LongAccumulator;
import functionalj.stream.longstream.LongCollectorPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import functionalj.stream.longstream.LongStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;


public interface Collected<DATA, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    public static <D, A, R> Collected<D, A, R> of(
            Streamable<D>                   streamable, 
            StreamProcessor<? extends D, R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollector<D, A, R>(((Collector<D, A, R>)processor));
        
        Objects.requireNonNull(streamable);
        return new ByStreamProcessor<>(streamable, processor);
    }
    
    public void   accumulate(DATA each);
    public RESULT finish();
    
    
    // TODO - Add ofIntInt, ofIntIntToInt, ...
    
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedInt<A, R> ofInt(
            IntStreamable         streamable, 
            IntStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollectedInt<>(((IntCollectorPlus<A, R>)processor));
        
        Objects.requireNonNull(streamable);
        return new ByIntStreamProcessor<>(streamable, processor);
    }
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedLong<A, R> ofLong(
            LongStreamable         streamable, 
            LongStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return (CollectedLong<A, R>) new ByCollectedLong<A, R>((LongCollectorPlus<A, R>) processor);
        
        Objects.requireNonNull(streamable);
        return new ByLongStreamProcessor<A, R>(streamable, processor);
    }
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedDouble<A, R> ofDouble(
            DoubleStreamable          streamable, 
            DoubleStreamProcessor<R> processor) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return (CollectedDouble<A, R>) new ByCollectedDouble<A, R>((DoubleCollectorPlus<A, R>) processor);
        
        Objects.requireNonNull(streamable);
        return new ByDoubleStreamProcessor<A, R>(streamable, processor);
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
        private final Streamable<DATA>    streamable;
        
        ByStreamProcessor(
                Streamable<DATA>                        stream, 
                StreamProcessor<? extends DATA, RESULT> processor) {
            this.processor  = processor;
            this.streamable = stream;
        }
        
        public void accumulate(DATA each) {
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public RESULT finish() {
            val stream = streamable.streamPlus();
            return (RESULT) processor.process((StreamPlus)stream);
        }
    }
    
    public static interface CollectedInt<ACCUMULATED, RESULT> {
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
        private final IntStreamable              streamable;
        
        public ByIntStreamProcessor(
                IntStreamable              stream, 
                IntStreamProcessor<RESULT> processor) {
            this.processor  = processor;
            this.streamable = stream;
        }
        
        public void accumulate(int each) {
        }
        
        public RESULT finish() {
            val stream    = streamable.intStream();
            val intStream = IntStreamPlus.from(stream);
            return processor.process(intStream);
        }
    }
    
    public static interface CollectedLong<ACCUMULATED, RESULT> {
        public void   accumulate(long each);
        public RESULT finish();
    }
    
    public static class ByCollectedLong<ACCUMULATED, RESULT> {
        
        private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
        private final LongAccumulator<ACCUMULATED>           accumulator;
        private final ACCUMULATED                            accumulated;
        
        public ByCollectedLong(LongCollectorPlus<ACCUMULATED, RESULT> collector) {
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
    
    public static class ByLongStreamProcessor<ACCUMULATED, RESULT>
                    implements CollectedLong<ACCUMULATED, RESULT> {
        
        private final LongStreamProcessor<RESULT> processor;
        private final LongStreamable              streamable;
        
        public ByLongStreamProcessor(
                LongStreamable              stream, 
                LongStreamProcessor<RESULT> processor) {
            this.processor  = processor;
            this.streamable = stream;
        }
        
        public void accumulate(long each) {
        }
        
        public RESULT finish() {
            val longStream = streamable.longStream();
            return processor.process(longStream);
        }
    }
    
    public static interface CollectedDouble<ACCUMULATED, RESULT> {
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
        private final DoubleStreamable              streamable;
        
        public ByDoubleStreamProcessor(
                DoubleStreamable              stream, 
                DoubleStreamProcessor<RESULT> processor) {
            this.processor  = processor;
            this.streamable = stream;
        }
        
        public void accumulate(double each) {
        }
        
        public RESULT finish() {
           val doubleStream = streamable.doubleStream();
            return processor.process(doubleStream);
        }
    }
    
}

