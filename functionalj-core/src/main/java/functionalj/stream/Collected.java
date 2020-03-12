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

import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import lombok.val;


public interface Collected<DATA, ACCUMULATED, RESULT> {
    
    @SuppressWarnings("unchecked")
    public static <D, A, R> Collected<D, A, R> of(
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
    
    
    @SuppressWarnings("unchecked")
    public static <A, R> CollectedInt<A, R> ofInt(
            IntStreamProcessor<R> processor, 
            IntStreamable         streamable) {
        Objects.requireNonNull(processor);
        if (processor instanceof Collector)
            return new ByCollectedInt<>(((IntCollectorPlus<A, R>)processor));
        
        Objects.requireNonNull(streamable);
        return new ByIntStreamProcessor<>(processor, streamable);
    }
//    public static <A, R> CollectedLong<A, R> ofLong(
//            LongStreamProcessor<R> processor, 
//            LongStreamable         streamable) {
//        Objects.requireNonNull(processor);
//        if (processor instanceof Collector)
//            return new ByCollectedLong<>(((LongCollectorPlus<A, R>)processor));
//        
//        Objects.requireNonNull(streamable);
//        return new ByLongStreamProcessor<>(processor, streamable);
//    }
//    public static <A, R> CollectedDouble<A, R> ofDouble(
//            DoubleCollectorPlus<A, R> collector, 
//            DoubleStreamable          streamable) {
//        Objects.requireNonNull(processor);
//        if (processor instanceof Collector)
//            return new ByCollectedDouble<>(((DoubleCollectorPlus<A, R>)processor));
//        
//        Objects.requireNonNull(streamable);
//        return new ByDoubleStreamProcessor<>(processor, streamable);
//    }
    
    
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
        public RESULT process(StreamPlus<DATA> stream) {
            return stream.calculate(collector);
        }
    }
    
    public static class ByStreamProcessor<DATA, ACCUMULATED, RESULT>
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
                IntStreamProcessor<RESULT> processor, 
                IntStreamable              stream) {
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
        public void   accumulate(int each);
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
//    
//    public static class ByLongStreamProcessor<ACCUMULATED, RESULT>
//                    implements CollectedLong<ACCUMULATED, RESULT> {
//        
//        private final LongStreamProcessor<RESULT> processor;
//        private final LongStreamable              streamable;
//        
//        public ByLongStreamProcessor(
//                LongStreamProcessor<RESULT> processor, 
//                LongStreamable              stream) {
//            this.processor  = processor;
//            this.streamable = stream;
//        }
//        
//        public void accumulate(long each) {
//        }
//        
//        public RESULT finish() {
//            val stream    = streamable.stream();
//            val longStream = LongStreamPlus.from(stream);
//            return processor.process(longStream);
//        }
//    }
    
    public static interface CollectedDouble<ACCUMULATED, RESULT> {
        public void   accumulate(int each);
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
//  
//  public static class ByDoubleStreamProcessor<ACCUMULATED, RESULT>
//                  implements CollectedDouble<ACCUMULATED, RESULT> {
//      
//      private final DoubleStreamProcessor<RESULT> processor;
//      private final DoubleStreamable              streamable;
//      
//      public ByDoubleStreamProcessor(
//              DoubleStreamProcessor<RESULT> processor, 
//              DoubleStreamable              stream) {
//          this.processor  = processor;
//          this.streamable = stream;
//      }
//      
//      public void accumulate(double each) {
//      }
//      
//      public RESULT finish() {
//          val stream       = streamable.stream();
//          val doubleStream = DoubleStreamPlus.from(stream);
//          return processor.process(doubleStream);
//      }
//  }
    
}
