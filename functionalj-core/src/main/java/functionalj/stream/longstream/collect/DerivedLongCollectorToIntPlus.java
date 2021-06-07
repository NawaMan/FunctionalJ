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
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.collect.CollectorToIntPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToIntPlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import lombok.val;

abstract public class DerivedLongCollectorToIntPlus<ACCUMULATED> {
    
    final LongCollectorToIntPlus<ACCUMULATED> collector;
    
    protected DerivedLongCollectorToIntPlus(LongCollectorToIntPlus<ACCUMULATED> collector) {
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
                        extends DerivedLongCollectorToIntPlus<ACCUMULATED>
                        implements CollectorToIntPlus<INPUT, ACCUMULATED> {
        
        private final ToLongFunction<INPUT> mapper;
        
        public FromObj(LongCollectorToIntPlus<ACCUMULATED> collector,
                       ToLongFunction<INPUT>                mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<ACCUMULATED, INPUT> accumulator(){
            @SuppressWarnings("rawtypes")
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
        
        @Override
        public Collector<INPUT, ACCUMULATED, Integer> collector() {
            return this;
        }
    }
    
    public static class FromInt<ACCUMULATED> 
                        extends DerivedLongCollectorToIntPlus<ACCUMULATED>
                        implements IntCollectorToIntPlus<ACCUMULATED> {
        
        private final IntToLongFunction mapper;
        
        public <SOURCE> FromInt(LongCollectorToIntPlus<ACCUMULATED> collector, 
                                IntToLongFunction                   mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjIntConsumer<ACCUMULATED> intAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromLong<ACCUMULATED> 
                        extends DerivedLongCollectorToIntPlus<ACCUMULATED>
                        implements LongCollectorToIntPlus<ACCUMULATED> {
        
        private final LongUnaryOperator mapper;
        
        public FromLong(LongCollectorToIntPlus<ACCUMULATED> collector, 
                        LongUnaryOperator                   mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjLongConsumer<ACCUMULATED> longAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
    
    public static class FromDouble<ACCUMULATED> 
                        extends DerivedLongCollectorToIntPlus<ACCUMULATED>
                        implements DoubleCollectorToIntPlus<ACCUMULATED> {
        
        private final DoubleToLongFunction mapper;
        
        public FromDouble(LongCollectorToIntPlus<ACCUMULATED> collector, 
                          DoubleToLongFunction                mapper) {
            super(collector);
            this.mapper = mapper;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public  ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
            val accumulator = (BiConsumer)collector.accumulator();
            return (a, s) -> {
                val d = mapper.applyAsLong(s);
                accumulator.accept(a, d);
            };
        }
    }
    
}
