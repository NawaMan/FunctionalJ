package functionalj.stream.doublestream;

import functionalj.lens.lenses.DoubleAccess;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;

@FunctionalInterface
public interface ToDoubleStreamProcessor<DATA> extends StreamProcessor<DATA, Double>, DoubleAccess<StreamPlus<DATA>> {
    @Override
    public default Double applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
 
    @Override
    public default double applyAsDouble(StreamPlus<DATA> host) {
        return apply(host);
    }
}
