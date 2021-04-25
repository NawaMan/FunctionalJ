package functionalj.stream.intstream;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public interface IntCollectorToIntPlus<ACCUMULATED>
        extends IntCollectorPlus<ACCUMULATED, Integer> {
    
    Supplier<ACCUMULATED>       supplier();
    IntAccumulator<ACCUMULATED> intAccumulator();
    BinaryOperator<ACCUMULATED> combiner();
    
    ToIntFunction<ACCUMULATED> finisherAsInt();
    
    default Function<ACCUMULATED, Integer> finisher() {
        return acc -> finisherAsInt().applyAsInt(acc);
    }
    
}
