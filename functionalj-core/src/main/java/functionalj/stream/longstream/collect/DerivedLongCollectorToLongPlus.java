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
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToLongPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import lombok.val;

abstract public class DerivedLongCollectorToLongPlus<ACCUMULATED> {
    
    final LongCollectorToLongPlus<ACCUMULATED> collector;
    
    protected DerivedLongCollectorToLongPlus(LongCollectorToLongPlus<ACCUMULATED> collector) {
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
        val finisher = finisherToLong();
        return accumulated -> {
            return finisher.applyAsLong(accumulated);
        };
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedLongCollectorToLongPlus<ACCUMULATED> implements CollectorToLongPlus<INPUT, ACCUMULATED> {
        
        private final ToLongFunction<INPUT> mapper;
        
        public FromObj(LongCollectorToLongPlus<ACCUMULATED> collector, ToLongFunction<INPUT> mapper) {
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
        public Collector<INPUT, ACCUMULATED, Long> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedLongCollectorToLongPlus<ACCUMULATED> implements IntCollectorToLongPlus<ACCUMULATED> {
        
        private final IntToLongFunction mapper;
        
        public <SOURCE> FromInt(LongCollectorToLongPlus<ACCUMULATED> collector, IntToLongFunction mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedLongCollectorToLongPlus<ACCUMULATED> implements LongCollectorToLongPlus<ACCUMULATED> {
        
        private final LongUnaryOperator mapper;
        
        public FromLong(LongCollectorToLongPlus<ACCUMULATED> collector, LongUnaryOperator mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedLongCollectorToLongPlus<ACCUMULATED> implements DoubleCollectorToLongPlus<ACCUMULATED> {
        
        private final DoubleToLongFunction mapper;
        
        public FromDouble(LongCollectorToLongPlus<ACCUMULATED> collector, DoubleToLongFunction mapper) {
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
