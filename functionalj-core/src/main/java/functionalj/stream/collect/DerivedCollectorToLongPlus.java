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

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;
import lombok.val;

abstract public class DerivedCollectorToLongPlus<ACCUMULATED> {
    
    final CollectorToLongPlus<?, ACCUMULATED> collector;
    
    protected DerivedCollectorToLongPlus(CollectorToLongPlus<?, ACCUMULATED> collector) {
        this.collector = collector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    public ToLongFunction<ACCUMULATED> finisherToLong() {
        return collector.finisherToLong();
    }
    
    public Function<ACCUMULATED, Long> finisher() {
        return a -> finisherToLong().applyAsLong(a);
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedCollectorToLongPlus<ACCUMULATED> implements CollectorToLongPlus<INPUT, ACCUMULATED> {
    
        private final Function<INPUT, ?> mapper;
    
        public <SOURCE> FromObj(CollectorToLongPlus<SOURCE, ACCUMULATED> collector, Function<INPUT, SOURCE> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator() {
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.apply(s);
                accumulator.accept(a, d);
            };
        }
    
        @Override
        public Collector<INPUT, ACCUMULATED, Long> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedCollectorToLongPlus<ACCUMULATED> implements IntCollectorToLongPlus<ACCUMULATED> {
    
        private final IntFunction<?> mapper;
    
        public <SOURCE> FromInt(CollectorToLongPlus<SOURCE, ACCUMULATED> collector, IntFunction<SOURCE> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.apply(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED> extends DerivedCollectorToLongPlus<ACCUMULATED> implements LongCollectorToLongPlus<ACCUMULATED> {
    
        private final LongFunction<?> mapper;
    
        public <SOURCE> FromLong(CollectorToLongPlus<SOURCE, ACCUMULATED> collector, LongFunction<SOURCE> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.apply(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED> extends DerivedCollectorToLongPlus<ACCUMULATED> implements DoubleCollectorToLongPlus<ACCUMULATED> {
    
        private final DoubleFunction<?> mapper;
    
        public <SOURCE> FromDouble(CollectorToLongPlus<SOURCE, ACCUMULATED> collector, DoubleFunction<SOURCE> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.apply(s);
                accumulator.accept(a, d);
            };
        }
    }
}
