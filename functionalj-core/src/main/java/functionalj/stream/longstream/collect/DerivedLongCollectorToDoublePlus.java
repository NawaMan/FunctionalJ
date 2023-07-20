// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import lombok.val;

abstract public class DerivedLongCollectorToDoublePlus<ACCUMULATED> {
    
    final LongCollectorToDoublePlus<ACCUMULATED> collector;
    
    protected DerivedLongCollectorToDoublePlus(LongCollectorToDoublePlus<ACCUMULATED> collector) {
        this.collector = collector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    public ToDoubleFunction<ACCUMULATED> finisherToDouble() {
        return collector.finisherToDouble();
    }
    
    public Function<ACCUMULATED, Double> finisher() {
        val finisher = finisherToDouble();
        return accumulated -> {
            return finisher.applyAsDouble(accumulated);
        };
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedLongCollectorToDoublePlus<ACCUMULATED> implements CollectorToDoublePlus<INPUT, ACCUMULATED> {
        
        private final ToLongFunction<INPUT> mapper;
        
        public FromObj(LongCollectorToDoublePlus<ACCUMULATED> collector, ToLongFunction<INPUT> mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator() {
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
        
        @Override
        public Collector<INPUT, ACCUMULATED, Double> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedLongCollectorToDoublePlus<ACCUMULATED> implements IntCollectorToDoublePlus<ACCUMULATED> {
        
        private final IntToLongFunction mapper;
        
        public <SOURCE> FromInt(LongCollectorToDoublePlus<ACCUMULATED> collector, IntToLongFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED> extends DerivedLongCollectorToDoublePlus<ACCUMULATED> implements LongCollectorToDoublePlus<ACCUMULATED> {
        
        private final LongUnaryOperator mapper;
        
        public FromLong(LongCollectorToDoublePlus<ACCUMULATED> collector, LongUnaryOperator mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED> extends DerivedLongCollectorToDoublePlus<ACCUMULATED> implements DoubleCollectorToDoublePlus<ACCUMULATED> {
        
        private final DoubleToLongFunction mapper;
        
        public FromDouble(LongCollectorToDoublePlus<ACCUMULATED> collector, DoubleToLongFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
}
