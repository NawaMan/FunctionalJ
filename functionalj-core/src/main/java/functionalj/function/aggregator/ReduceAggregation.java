package functionalj.function.aggregator;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.collect.CollectorPlus;

/**
 * This aggregation by reducing -- A monoid.
 */
public class ReduceAggregation<DATA> extends Aggregation<DATA, DATA> implements BinaryOperator<DATA> {
    
    private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
    private Supplier<DATA>       initialValueSuppler;
    private BinaryOperator<DATA> operator;
    
    private CollectorPlus<DATA, Object[], DATA> collectorPlus = new CollectorPlus<DATA, Object[], DATA>() {
        
        @Override
        public Supplier<Object[]> supplier() {
            return () -> new Object[] { initialValueSuppler.get() };
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BiConsumer<Object[], DATA> accumulator() {
            return (a, i) -> a[0] = operator.apply((DATA)a[0], i);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public BinaryOperator<Object[]> combiner() {
            return (a1, a2) -> new Object[] { operator.apply((DATA)a1[0], (DATA)a1[1]) };
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public Function<Object[], DATA> finisher() {
            return a -> (DATA)a[0];
        }
        
        @Override
        public Collector<DATA, Object[], DATA> collector() {
            return this;
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    };
    
    public ReduceAggregation(DATA initialValue, BinaryOperator<DATA> operator) {
        this.initialValueSuppler = () -> initialValue;
        this.operator            = operator;
    }
    
    public ReduceAggregation(Supplier<DATA> initialValueSuppler, BinaryOperator<DATA> operator) {
        this.initialValueSuppler = initialValueSuppler;
        this.operator            = operator;
    }
    
    @Override
    public DATA apply(DATA left, DATA right) {
        return operator.apply(left, right);
    }
    
    public BinaryOperator<DATA> operator() {
        return operator;
    }
    
    @Override
    public CollectorPlus<DATA, ?, DATA> collectorPlus() {
        return collectorPlus;
    }
    
}
