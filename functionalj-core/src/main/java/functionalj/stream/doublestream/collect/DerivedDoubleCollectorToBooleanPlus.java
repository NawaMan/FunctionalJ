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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.collect.CollectorToBooleanPlus;
import functionalj.stream.intstream.collect.IntCollectorToBooleanPlus;
import functionalj.stream.longstream.collect.LongCollectorToBooleanPlus;
import lombok.val;

abstract public class DerivedDoubleCollectorToBooleanPlus<ACCUMULATED> {
    
    final DoubleCollectorToBooleanPlus<ACCUMULATED> collector;
    
    protected DerivedDoubleCollectorToBooleanPlus(DoubleCollectorToBooleanPlus<ACCUMULATED> collector) {
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
    
    //== Implementations ==
    
    public static class FromObj<INPUT, ACCUMULATED> 
                        extends DerivedDoubleCollectorToBooleanPlus<ACCUMULATED>
                        implements CollectorToBooleanPlus<INPUT, ACCUMULATED> {
        
        private final ToDoubleFunction<INPUT> mapper;
        
        public FromObj(DoubleCollectorToBooleanPlus<ACCUMULATED> collector,
                       ToDoubleFunction<INPUT>                   mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator(){
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
        
        @Override
        public Collector<INPUT, ACCUMULATED, Boolean> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> 
                        extends DerivedDoubleCollectorToBooleanPlus<ACCUMULATED>
                        implements IntCollectorToBooleanPlus<ACCUMULATED> {
        
        private final IntToDoubleFunction mapper;
        
        public <SOURCE> FromInt(DoubleCollectorToBooleanPlus<ACCUMULATED> collector, 
                                IntToDoubleFunction                       mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED> 
                        extends DerivedDoubleCollectorToBooleanPlus<ACCUMULATED>
                        implements LongCollectorToBooleanPlus<ACCUMULATED> {
        
        private final LongToDoubleFunction mapper;
        
        public FromLong(DoubleCollectorToBooleanPlus<ACCUMULATED> collector, 
                        LongToDoubleFunction                      mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED> 
                        extends DerivedDoubleCollectorToBooleanPlus<ACCUMULATED>
                        implements DoubleCollectorToBooleanPlus<ACCUMULATED> {
        
        private final DoubleUnaryOperator mapper;
        
        public FromDouble(DoubleCollectorToBooleanPlus<ACCUMULATED> collector, 
                          DoubleUnaryOperator                       mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsDouble(s);
                accumulator.accept(a, d);
            };
        }
    }
    
}
