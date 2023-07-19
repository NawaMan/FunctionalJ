package functionalj.stream.doublestream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

abstract public class DerivedDoubleCollectorPlus<ACCUMULATED, TARGET> {
    
    final DoubleCollectorPlus<ACCUMULATED, TARGET> collector;
    
    protected DerivedDoubleCollectorPlus(DoubleCollectorPlus<ACCUMULATED, TARGET> collector) {
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
    public static class FromObj<INPUT, ACCUMULATED, TARGET> extends DerivedDoubleCollectorPlus<ACCUMULATED, TARGET> implements CollectorPlus<INPUT, ACCUMULATED, TARGET> {
    
        private final ToDoubleFunction<INPUT> mapper;
    
        public <SOURCE> FromObj(DoubleCollectorPlus<ACCUMULATED, TARGET> collector, ToDoubleFunction<INPUT> mapper) {
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
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromInt<ACCUMULATED, TARGET> extends DerivedDoubleCollectorPlus<ACCUMULATED, TARGET> implements IntCollectorPlus<ACCUMULATED, TARGET> {
    
        private final IntToDoubleFunction mapper;
    
        public FromInt(DoubleCollectorPlus<ACCUMULATED, TARGET> collector, IntToDoubleFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED, TARGET> extends DerivedDoubleCollectorPlus<ACCUMULATED, TARGET> implements LongCollectorPlus<ACCUMULATED, TARGET> {
    
        private final LongToDoubleFunction mapper;
    
        public FromLong(DoubleCollectorPlus<ACCUMULATED, TARGET> collector, LongToDoubleFunction mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED, TARGET> extends DerivedDoubleCollectorPlus<ACCUMULATED, TARGET> implements DoubleCollectorPlus<ACCUMULATED, TARGET> {
    
        private final DoubleUnaryOperator mapper;
    
        public FromDouble(DoubleCollectorPlus<ACCUMULATED, TARGET> collector, DoubleUnaryOperator mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
}
