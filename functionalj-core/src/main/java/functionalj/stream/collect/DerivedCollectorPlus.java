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
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

abstract public class DerivedCollectorPlus<ACCUMULATED, TARGET> {
    
    final Collector<?, ACCUMULATED, TARGET> collector;
    
    protected DerivedCollectorPlus(Collector<?, ACCUMULATED, TARGET> collector) {
        this.collector = collector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    public Function<ACCUMULATED, TARGET> finisher() {
        return collector.finisher();
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED, TARGET> extends DerivedCollectorPlus<ACCUMULATED, TARGET> implements CollectorPlus<INPUT, ACCUMULATED, TARGET> {
        
        private final Function<INPUT, ?> mapper;
        
        public <SOURCE> FromObj(Collector<SOURCE, ACCUMULATED, TARGET> collector, Function<INPUT, SOURCE> mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @Override
        public Collector<INPUT, ACCUMULATED, TARGET> collector() {
            return this;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.apply(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromInt<ACCUMULATED, TARGET> extends DerivedCollectorPlus<ACCUMULATED, TARGET> implements IntCollectorPlus<ACCUMULATED, TARGET> {
        
        private final IntFunction<?> mapper;
        
        public <SOURCE> FromInt(Collector<SOURCE, ACCUMULATED, TARGET> collector, IntFunction<SOURCE> mapper) {
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
    
    public static class FromLong<ACCUMULATED, TARGET> extends DerivedCollectorPlus<ACCUMULATED, TARGET> implements LongCollectorPlus<ACCUMULATED, TARGET> {
        
        private final LongFunction<?> mapper;
        
        public <SOURCE> FromLong(Collector<SOURCE, ACCUMULATED, TARGET> collector, LongFunction<SOURCE> mapper) {
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
    
    public static class FromDouble<ACCUMULATED, TARGET> extends DerivedCollectorPlus<ACCUMULATED, TARGET> implements DoubleCollectorPlus<ACCUMULATED, TARGET> {
        
        private final DoubleFunction<?> mapper;
        
        public <SOURCE> FromDouble(Collector<SOURCE, ACCUMULATED, TARGET> collector, DoubleFunction<SOURCE> mapper) {
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
