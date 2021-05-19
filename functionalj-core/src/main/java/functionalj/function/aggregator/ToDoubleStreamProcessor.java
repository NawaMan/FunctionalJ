package functionalj.function.aggregator;

import functionalj.lens.lenses.DoubleAccess;
import functionalj.stream.StreamPlus;
import functionalj.stream.Aggregator;

@FunctionalInterface
public interface ToDoubleAggregator<DATA> extends Aggregator<DATA, Double>, DoubleAccess<StreamPlus<DATA>> {
    @Override
    public default Double applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
 
    @Override
    public default double applyAsDouble(StreamPlus<DATA> host) {
        return apply(host);
    }
}
