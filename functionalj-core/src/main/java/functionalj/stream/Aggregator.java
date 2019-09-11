package functionalj.stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Aggregator<DATA, ACCUMULATED, TARGET> {
    
    Supplier<ACCUMULATED> initializer();
    
    BiFunction<DATA, ACCUMULATED, ACCUMULATED> accumulator();
    
    Function<ACCUMULATED, TARGET> finalizer();
    
}
