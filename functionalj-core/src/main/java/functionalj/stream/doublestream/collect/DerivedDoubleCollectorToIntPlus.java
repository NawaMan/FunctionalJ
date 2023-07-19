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
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToIntPlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import lombok.val;

abstract public class DerivedDoubleCollectorToIntPlus<ACCUMULATED> {
    
    final DoubleCollectorToIntPlus<ACCUMULATED> collector;
    
    protected DerivedDoubleCollectorToIntPlus(DoubleCollectorToIntPlus<ACCUMULATED> collector) {
        this.collector = collector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    public ToIntFunction<ACCUMULATED> finisherToInt() {
        return collector.finisherToInt();
    }
    
    public Function<ACCUMULATED, Integer> finisher() {
        val finisher = finisherToInt();
        return accumulated -> {
            return finisher.applyAsInt(accumulated);
        };
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    // == Implementations ==
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedDoubleCollectorToIntPlus<ACCUMULATED> implements CollectorToIntPlus<INPUT, ACCUMULATED> {
    
        private final ToDoubleFunction<INPUT> mapper;
    
        public FromObj(DoubleCollectorToIntPlus<ACCUMULATED> collector, ToDoubleFunction<INPUT> mapper) {
            super(collector);
            this.mapper = mapper;
        }
    
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator() {
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer) collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    
        @Override
        public Collector<INPUT, ACCUMULATED, Integer> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> extends DerivedDoubleCollectorToIntPlus<ACCUMULATED> implements IntCollectorToIntPlus<ACCUMULATED> {
    
        private final IntToDoubleFunction mapper;
    
        public <SOURCE> FromInt(DoubleCollectorToIntPlus<ACCUMULATED> collector, IntToDoubleFunction mapper) {
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
    
    public static class FromLong<ACCUMULATED> extends DerivedDoubleCollectorToIntPlus<ACCUMULATED> implements LongCollectorToIntPlus<ACCUMULATED> {
    
        private final LongToDoubleFunction mapper;
    
        public FromLong(DoubleCollectorToIntPlus<ACCUMULATED> collector, LongToDoubleFunction mapper) {
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
    
    public static class FromDouble<ACCUMULATED> extends DerivedDoubleCollectorToIntPlus<ACCUMULATED> implements DoubleCollectorToIntPlus<ACCUMULATED> {
    
        private final DoubleUnaryOperator mapper;
    
        public FromDouble(DoubleCollectorToIntPlus<ACCUMULATED> collector, DoubleUnaryOperator mapper) {
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
