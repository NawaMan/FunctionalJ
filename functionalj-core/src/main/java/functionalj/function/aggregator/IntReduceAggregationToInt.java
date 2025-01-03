package functionalj.function.aggregator;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.intstream.collect.IntCollectorToIntPlus;

/**
 * This aggregation from int to int by reducing it -- An integer monoid.
 */
public class IntReduceAggregationToInt extends IntAggregationToInt implements IntBinaryOperator {
    
    private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
    private int               initialValue;
    private IntBinaryOperator operator;
    
    private IntCollectorToIntPlus<int[]> collectorPlus = new IntCollectorToIntPlus<int[]>() {
        
        @Override
        public Supplier<int[]> supplier() {
            return () -> new int[] { initialValue };
        }
        
        @Override
        public ObjIntConsumer<int[]> intAccumulator() {
            return (a, i) -> a[0] = operator.applyAsInt(a[0], i);
        }
        
        @Override
        public BinaryOperator<int[]> combiner() {
            return (a1, a2) -> new int[] { operator.applyAsInt(a1[0], a1[1]) };
        }
        
        @Override
        public ToIntFunction<int[]> finisherToInt() {
            return a -> a[0];
        }
        
        @Override
        public Collector<Integer, int[], Integer> collector() {
            return this;
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    };
    
    public IntReduceAggregationToInt(int initialValue, IntBinaryOperator operator) {
        this.initialValue = initialValue;
        this.operator     = operator;
    }
    
    @Override
    public int applyAsInt(int left, int right) {
        return operator.applyAsInt(left, right);
    }
    
    public IntBinaryOperator operator() {
        return operator;
    }
    
    @Override
    public IntCollectorToIntPlus<?> intCollectorToIntPlus() {
        return collectorPlus;
    }
    
}
