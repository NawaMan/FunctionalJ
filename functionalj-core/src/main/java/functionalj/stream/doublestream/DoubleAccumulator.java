package functionalj.stream.doublestream;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface DoubleAccumulator<ACCUMULATED> extends BiConsumer<ACCUMULATED, Double> {
    
    void acceptDouble(ACCUMULATED accumulator, double element);
    
    default void accept(ACCUMULATED accumulator, Double element) {
        acceptDouble(accumulator, element.doubleValue());
    }
    
}
