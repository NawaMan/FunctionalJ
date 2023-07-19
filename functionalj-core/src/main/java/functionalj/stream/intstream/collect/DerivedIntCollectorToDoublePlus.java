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
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import functionalj.stream.longstream.collect.LongCollectorToDoublePlus;
import lombok.val;

abstract public class DerivedIntCollectorToDoublePlus<ACCUMULATED> {
    
    final IntCollectorToDoublePlus<ACCUMULATED> collector;
    
    protected DerivedIntCollectorToDoublePlus(IntCollectorToDoublePlus<ACCUMULATED> collector) {
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
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedIntCollectorToDoublePlus<ACCUMULATED> implements CollectorToDoublePlus<INPUT, ACCUMULATED> {
    
        private final ToIntFunction<INPUT> mapper;
    
        public FromObj(IntCollectorToDoublePlus<ACCUMULATED> collector, ToIntFunction<INPUT> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator() {
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    
        @Override
        public Collector<INPUT, ACCUMULATED, Double> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedIntCollectorToDoublePlus<ACCUMULATED> implements IntCollectorToDoublePlus<ACCUMULATED> {
    
        private final IntUnaryOperator mapper;
    
        public <SOURCE> FromInt(IntCollectorToDoublePlus<ACCUMULATED> collector, IntUnaryOperator mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedIntCollectorToDoublePlus<ACCUMULATED> implements LongCollectorToDoublePlus<ACCUMULATED> {
    
        private final LongToIntFunction mapper;
    
        public FromLong(IntCollectorToDoublePlus<ACCUMULATED> collector, LongToIntFunction mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedIntCollectorToDoublePlus<ACCUMULATED> implements DoubleCollectorToDoublePlus<ACCUMULATED> {
    
        private final DoubleToIntFunction mapper;
    
        public FromDouble(IntCollectorToDoublePlus<ACCUMULATED> collector, DoubleToIntFunction mapper) {
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
