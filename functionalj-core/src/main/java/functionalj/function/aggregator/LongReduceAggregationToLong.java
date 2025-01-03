package functionalj.function.aggregator;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.longstream.collect.LongCollectorToLongPlus;

/**
 * This aggregation from long to long by reducing it -- An long monoid.
 */
public class LongReduceAggregationToLong extends LongAggregationToLong implements LongBinaryOperator {
    
    private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
    private long               initialValue;
    private LongBinaryOperator operator;
    
    private LongCollectorToLongPlus<long[]> collectorPlus = new LongCollectorToLongPlus<long[]>() {
        
        @Override
        public Supplier<long[]> supplier() {
            return () -> new long[] { initialValue };
        }
        
        @Override
        public ObjLongConsumer<long[]> longAccumulator() {
            return (a, i) -> a[0] = operator.applyAsLong(a[0], i);
        }
        
        @Override
        public BinaryOperator<long[]> combiner() {
            return (a1, a2) -> new long[] { operator.applyAsLong(a1[0], a1[1]) };
        }
        
        @Override
        public ToLongFunction<long[]> finisherToLong() {
            return a -> a[0];
        }
        
        @Override
        public Collector<Long, long[], Long> collector() {
            return this;
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    };
    
    public LongReduceAggregationToLong(long initialValue, LongBinaryOperator operator) {
        this.initialValue = initialValue;
        this.operator     = operator;
    }
    
    @Override
    public long applyAsLong(long left, long right) {
        return operator.applyAsLong(left, right);
    }
    
    public LongBinaryOperator operator() {
        return operator;
    }
    
    @Override
    public LongCollectorToLongPlus<?> longCollectorToLongPlus() {
        return collectorPlus;
    }
    
}
