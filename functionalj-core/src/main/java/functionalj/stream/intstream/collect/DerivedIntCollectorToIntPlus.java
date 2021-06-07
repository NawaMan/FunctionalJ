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

import functionalj.stream.collect.CollectorToIntPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToIntPlus;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import lombok.val;

abstract public class DerivedIntCollectorToIntPlus<ACCUMULATED> {
    
    final IntCollectorToIntPlus<ACCUMULATED> collector;
    
    protected DerivedIntCollectorToIntPlus(IntCollectorToIntPlus<ACCUMULATED> collector) {
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
        return a -> finisherToInt().applyAsInt(a);
    }
    
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
    //== Implementations ==
    
    public static class FromObj<INPUT, ACCUMULATED> 
                        extends DerivedIntCollectorToIntPlus<ACCUMULATED>
                        implements CollectorToIntPlus<INPUT, ACCUMULATED> {
        
        private final ToIntFunction<INPUT> mapper;
        
        public FromObj(IntCollectorToIntPlus<ACCUMULATED> collector,
                       ToIntFunction<INPUT>               mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator(){
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
        
        @Override
        public Collector<INPUT, ACCUMULATED, Integer> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> 
                        extends DerivedIntCollectorToIntPlus<ACCUMULATED>
                        implements IntCollectorToIntPlus<ACCUMULATED> {
        
        private final IntUnaryOperator mapper;
        
        public <SOURCE> FromInt(IntCollectorToIntPlus<ACCUMULATED> collector, 
                                IntUnaryOperator                   mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED> 
                        extends DerivedIntCollectorToIntPlus<ACCUMULATED>
                        implements LongCollectorToIntPlus<ACCUMULATED> {
        
        private final LongToIntFunction mapper;
        
        public FromLong(IntCollectorToIntPlus<ACCUMULATED> collector, 
                        LongToIntFunction                  mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED> 
                        extends DerivedIntCollectorToIntPlus<ACCUMULATED>
                        implements DoubleCollectorToIntPlus<ACCUMULATED> {
        
        private final DoubleToIntFunction mapper;
        
        public FromDouble(IntCollectorToIntPlus<ACCUMULATED> collector, 
                          DoubleToIntFunction                mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsInt(s);
                accumulator.accept(a, d);
            };
        }
    }
    
}
