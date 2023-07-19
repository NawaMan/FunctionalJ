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
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import functionalj.stream.longstream.collect.LongCollectorToDoublePlus;
import lombok.val;

abstract public class DerivedCollectorToDoublePlus<ACCUMULATED> {
    
    final CollectorToDoublePlus<?, ACCUMULATED> collector;
    
    protected DerivedCollectorToDoublePlus(CollectorToDoublePlus<?, ACCUMULATED> collector) {
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
        return a -> finisherToDouble().applyAsDouble(a);
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedCollectorToDoublePlus<ACCUMULATED> implements CollectorToDoublePlus<INPUT, ACCUMULATED> {
    
        private final Function<INPUT, ?> mapper;
    
        public <SOURCE> FromObj(CollectorToDoublePlus<SOURCE, ACCUMULATED> collector, Function<INPUT, SOURCE> mapper) {
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
        public Collector<INPUT, ACCUMULATED, Double> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedCollectorToDoublePlus<ACCUMULATED> implements IntCollectorToDoublePlus<ACCUMULATED> {
    
        private final IntFunction<?> mapper;
    
        public <SOURCE> FromInt(CollectorToDoublePlus<SOURCE, ACCUMULATED> collector, IntFunction<SOURCE> mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedCollectorToDoublePlus<ACCUMULATED> implements LongCollectorToDoublePlus<ACCUMULATED> {
    
        private final LongFunction<?> mapper;
    
        public <SOURCE> FromLong(CollectorToDoublePlus<SOURCE, ACCUMULATED> collector, LongFunction<SOURCE> mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedCollectorToDoublePlus<ACCUMULATED> implements DoubleCollectorToDoublePlus<ACCUMULATED> {
    
        private final DoubleFunction<?> mapper;
    
        public <SOURCE> FromDouble(CollectorToDoublePlus<SOURCE, ACCUMULATED> collector, DoubleFunction<SOURCE> mapper) {
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
