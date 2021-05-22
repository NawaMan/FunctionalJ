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

import java.util.function.BiConsumer;
import java.util.stream.Collector;

import functionalj.function.aggregator.Aggregation;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.intlist.AsIntFuncList;
import functionalj.list.longlist.AsLongFuncList;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.doublestream.collect.DoubleCollected;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import functionalj.stream.intstream.collect.IntCollected;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import functionalj.stream.longstream.collect.LongCollected;
import functionalj.stream.longstream.collect.LongCollectorPlus;


public interface Collected<DATA, ACCUMULATED, RESULT> {
    
    public static <D, A, R> Collected<D, A, R> collectedOf(Aggregation<? extends D, R> aggregation) {
        return of(aggregation);
    }
    
    @SuppressWarnings("unchecked")
    public static <D, A, R> Collected<D, A, R> of(Aggregation<? extends D, R> aggregation) {
        return new Collected.Impl<D, A, R>((Collector<D, A, R>) aggregation.collectorPlus());
    }
    
    public static <D, A, R> Collected<D, A, R> collectedOf(Collector<? extends D, A, R> collector) {
        return of(collector);
    }
    
    @SuppressWarnings("unchecked")
    public static <D, A, R> Collected<D, A, R> of(Collector<? extends D, A, R> collector) {
        requireNonNull(collector);
        return new Collected.Impl<D, A, R>((Collector<D, A, R>)collector);
    }
    
    //-- Integer --
    
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
        return new Collected.Impl(collector);
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
    
    //== Instance ==
    
    public void accumulate(DATA each);
    public RESULT finish();
    
    //== Implementation ==
    
    public static class Impl<DATA, ACCUMULATED, RESULT> implements Collected<DATA, ACCUMULATED, RESULT> {
        
        private final Collector<DATA, ACCUMULATED, RESULT> collector;
        private final BiConsumer<ACCUMULATED, DATA>        accumulator;
        private final ACCUMULATED                          accumulated;
        
        public Impl(Collector<DATA, ACCUMULATED, RESULT> collector) {
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
        
    }
    
}

