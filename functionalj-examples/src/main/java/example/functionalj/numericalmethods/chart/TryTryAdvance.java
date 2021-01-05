package example.functionalj.numericalmethods.chart;

import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.stream.StreamSupport;

import functionalj.stream.doublestream.DoubleStreamPlus;
import lombok.val;

public class TryTryAdvance {
    
    public static void main(String[] args) {
        val spliterator = new Spliterators.AbstractDoubleSpliterator(10, 0) {
            private double current = 10.0;
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                action.accept(current);
                current -= 1.0;
                return (current >= 0);
            }
        };
        val stream = StreamSupport.doubleStream(spliterator, false);
        
        val spliterator0 = stream.spliterator();
        val spliterator1 = new Spliterators.AbstractDoubleSpliterator(10, 0) {
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                return spliterator0.tryAdvance((double value) -> {
                    action.accept(value * 10);
                });
            }
        };
        val stream1 = DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator1, false));
        stream1.mapTwo((value1, value2)-> (value1+value2)).forEach(System.out::println);
    }
    
}
