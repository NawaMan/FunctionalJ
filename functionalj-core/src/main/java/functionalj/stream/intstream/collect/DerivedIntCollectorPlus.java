package functionalj.stream.intstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

abstract public class DerivedIntCollectorPlus<ACCUMULATED, TARGET> {
    
    final IntCollectorPlus<ACCUMULATED, TARGET> collector;
    
    protected DerivedIntCollectorPlus(IntCollectorPlus<ACCUMULATED, TARGET> collector) {
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
    public static class FromObj<INPUT, ACCUMULATED, TARGET> extends DerivedIntCollectorPlus<ACCUMULATED, TARGET> implements CollectorPlus<INPUT, ACCUMULATED, TARGET> {
        
        private final ToIntFunction<INPUT> mapper;
        
        public <SOURCE> FromObj(IntCollectorPlus<ACCUMULATED, TARGET> collector, ToIntFunction<INPUT> mapper) {
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
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromInt<ACCUMULATED, TARGET> extends DerivedIntCollectorPlus<ACCUMULATED, TARGET> implements IntCollectorPlus<ACCUMULATED, TARGET> {
        
        private final IntUnaryOperator mapper;
        
        public FromInt(IntCollectorPlus<ACCUMULATED, TARGET> collector, IntUnaryOperator mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED, TARGET> extends DerivedIntCollectorPlus<ACCUMULATED, TARGET> implements LongCollectorPlus<ACCUMULATED, TARGET> {
        
        private final LongToIntFunction mapper;
        
        public FromLong(IntCollectorPlus<ACCUMULATED, TARGET> collector, LongToIntFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED, TARGET> extends DerivedIntCollectorPlus<ACCUMULATED, TARGET> implements DoubleCollectorPlus<ACCUMULATED, TARGET> {
        
        private final DoubleToIntFunction mapper;
        
        public FromDouble(IntCollectorPlus<ACCUMULATED, TARGET> collector, DoubleToIntFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
}
