package functionalj.stream.intstream;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface IntAccumulator<ACCUMULATED> extends BiConsumer<ACCUMULATED, Integer> {
    
    void acceptInt(ACCUMULATED accumulator, int element);
    
    default void accept(ACCUMULATED accumulator, Integer element) {
        acceptInt(accumulator, element.intValue());
    }
    
}