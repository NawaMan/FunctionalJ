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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.stream.collect.CollectorToBooleanPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import functionalj.stream.longstream.collect.LongCollectorToBooleanPlus;
import lombok.val;

abstract public class DerivedIntCollectorToBooleanPlus<ACCUMULATED> {

    final IntCollectorToBooleanPlus<ACCUMULATED> collector;

    protected DerivedIntCollectorToBooleanPlus(IntCollectorToBooleanPlus<ACCUMULATED> collector) {
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
    public static class FromObj<INPUT, ACCUMULATED> extends DerivedIntCollectorToBooleanPlus<ACCUMULATED> implements CollectorToBooleanPlus<INPUT, ACCUMULATED> {

        private final ToIntFunction<INPUT> mapper;

        public FromObj(IntCollectorToBooleanPlus<ACCUMULATED> collector, ToIntFunction<INPUT> mapper) {
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
        public Collector<INPUT, ACCUMULATED, Boolean> collector() {
            return this;
        }
    }

    public static class FromInt<ACCUMULATED> extends DerivedIntCollectorToBooleanPlus<ACCUMULATED> implements IntCollectorToBooleanPlus<ACCUMULATED> {

        private final IntUnaryOperator mapper;

        public <SOURCE> FromInt(IntCollectorToBooleanPlus<ACCUMULATED> collector, IntUnaryOperator mapper) {
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

    public static class FromLong<ACCUMULATED> extends DerivedIntCollectorToBooleanPlus<ACCUMULATED> implements LongCollectorToBooleanPlus<ACCUMULATED> {

        private final LongToIntFunction mapper;

        public FromLong(IntCollectorToBooleanPlus<ACCUMULATED> collector, LongToIntFunction mapper) {
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

    public static class FromDouble<ACCUMULATED> extends DerivedIntCollectorToBooleanPlus<ACCUMULATED> implements DoubleCollectorToBooleanPlus<ACCUMULATED> {

        private final DoubleToIntFunction mapper;

        public FromDouble(IntCollectorToBooleanPlus<ACCUMULATED> collector, DoubleToIntFunction mapper) {
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
