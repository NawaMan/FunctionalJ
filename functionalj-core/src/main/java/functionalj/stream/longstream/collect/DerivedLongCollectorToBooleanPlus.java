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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToBooleanPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import functionalj.stream.intstream.collect.IntCollectorToBooleanPlus;
import lombok.val;

abstract public class DerivedLongCollectorToBooleanPlus<ACCUMULATED> {
    
    final LongCollectorToBooleanPlus<ACCUMULATED> collector;
    
    protected DerivedLongCollectorToBooleanPlus(LongCollectorToBooleanPlus<ACCUMULATED> collector) {
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
        val finisher = finisherToBoolean();
        return accumulated -> {
            return finisher.test(accumulated);
        };
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedLongCollectorToBooleanPlus<ACCUMULATED> implements CollectorToBooleanPlus<INPUT, ACCUMULATED> {
    
        private final ToLongFunction<INPUT> mapper;
    
        public FromObj(LongCollectorToBooleanPlus<ACCUMULATED> collector, ToLongFunction<INPUT> mapper) {
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
        public Collector<INPUT, ACCUMULATED, Boolean> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedLongCollectorToBooleanPlus<ACCUMULATED> implements IntCollectorToBooleanPlus<ACCUMULATED> {
    
        private final IntToLongFunction mapper;
    
        public <SOURCE> FromInt(LongCollectorToBooleanPlus<ACCUMULATED> collector, IntToLongFunction mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedLongCollectorToBooleanPlus<ACCUMULATED> implements LongCollectorToBooleanPlus<ACCUMULATED> {
    
        private final LongUnaryOperator mapper;
    
        public FromLong(LongCollectorToBooleanPlus<ACCUMULATED> collector, LongUnaryOperator mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedLongCollectorToBooleanPlus<ACCUMULATED> implements DoubleCollectorToBooleanPlus<ACCUMULATED> {
    
        private final DoubleToLongFunction mapper;
    
        public FromDouble(LongCollectorToBooleanPlus<ACCUMULATED> collector, DoubleToLongFunction mapper) {
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
