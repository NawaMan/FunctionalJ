package functionalj.stream;

import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Aggregators {
    
    public static <DATA> Aggregator<DATA, LongAdder, Long> counts() {
        return new Aggregator<DATA, LongAdder, Long>() {
            @Override
            public Supplier<LongAdder> initializer() {
                return () -> new LongAdder();
            }
            @Override
            public BiFunction<DATA, LongAdder, LongAdder> accumulator() {
                return (data, adder) -> {
                    adder.increment();
                    return adder;
                };
            }
            @Override
            public Function<LongAdder, Long> finalizer() {
                return adder -> adder.longValue();
            }
        };
    }
    
}
