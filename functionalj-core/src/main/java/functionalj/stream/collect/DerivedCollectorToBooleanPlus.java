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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import functionalj.stream.intstream.collect.IntCollectorToBooleanPlus;
import functionalj.stream.longstream.collect.LongCollectorToBooleanPlus;
import lombok.val;

abstract public class DerivedCollectorToBooleanPlus<ACCUMULATED> {
    
    final CollectorToBooleanPlus<?, ACCUMULATED> collector;
    
    protected DerivedCollectorToBooleanPlus(CollectorToBooleanPlus<?, ACCUMULATED> collector) {
        this.collector = collector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    public Predicate<ACCUMULATED> finisherToBoolean() {
        return collector.finisherToBoolean();
    }
    
    public Function<ACCUMULATED, Boolean> finisher() {
        return a -> finisherToBoolean().test(a);
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedCollectorToBooleanPlus<ACCUMULATED> implements CollectorToBooleanPlus<INPUT, ACCUMULATED> {
    
        private final Function<INPUT, ?> mapper;
    
        public <SOURCE> FromObj(CollectorToBooleanPlus<SOURCE, ACCUMULATED> collector, Function<INPUT, SOURCE> mapper) {
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
        public Collector<INPUT, ACCUMULATED, Boolean> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedCollectorToBooleanPlus<ACCUMULATED> implements IntCollectorToBooleanPlus<ACCUMULATED> {
    
        private final IntFunction<?> mapper;
    
        public <SOURCE> FromInt(CollectorToBooleanPlus<SOURCE, ACCUMULATED> collector, IntFunction<SOURCE> mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedCollectorToBooleanPlus<ACCUMULATED> implements LongCollectorToBooleanPlus<ACCUMULATED> {
    
        private final LongFunction<?> mapper;
    
        public <SOURCE> FromLong(CollectorToBooleanPlus<SOURCE, ACCUMULATED> collector, LongFunction<SOURCE> mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedCollectorToBooleanPlus<ACCUMULATED> implements DoubleCollectorToBooleanPlus<ACCUMULATED> {
    
        private final DoubleFunction<?> mapper;
    
        public <SOURCE> FromDouble(CollectorToBooleanPlus<SOURCE, ACCUMULATED> collector, DoubleFunction<SOURCE> mapper) {
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
