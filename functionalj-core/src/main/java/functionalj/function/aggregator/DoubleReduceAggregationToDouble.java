package functionalj.function.aggregator;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;

/**
 * This aggregation from double to double by reducing it -- An double monoid.
 */
public class DoubleReduceAggregationToDouble extends DoubleAggregationToDouble implements DoubleBinaryOperator {
    
    private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
    private double               initialValue;
    private DoubleBinaryOperator operator;
    
    private DoubleCollectorToDoublePlus<double[]> collectorPlus = new DoubleCollectorToDoublePlus<double[]>() {
        
        @Override
        public Supplier<double[]> supplier() {
            return () -> new double[] { initialValue };
        }
        
        @Override
        public ObjDoubleConsumer<double[]> doubleAccumulator() {
            return (a, i) -> a[0] = operator.applyAsDouble(a[0], i);
        }
        
        @Override
        public BinaryOperator<double[]> combiner() {
            return (a1, a2) -> new double[] { operator.applyAsDouble(a1[0], a1[1]) };
        }
        
        @Override
        public ToDoubleFunction<double[]> finisherToDouble() {
            return a -> a[0];
        }
        
        @Override
        public Collector<Double, double[], Double> collector() {
            return this;
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    };
    
    public DoubleReduceAggregationToDouble(double initialValue, DoubleBinaryOperator operator) {
        this.initialValue = initialValue;
        this.operator     = operator;
    }
    
    @Override
    public double applyAsDouble(double left, double right) {
        return operator.applyAsDouble(left, right);
    }
    
    public DoubleBinaryOperator operator() {
        return operator;
    }
    
    @Override
    public DoubleCollectorToDoublePlus<?> doubleCollectorToDoublePlus() {
        return collectorPlus;
    }
    
}
