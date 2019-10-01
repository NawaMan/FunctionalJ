package functionalj.stream;

import functionalj.lens.lenses.DoubleAccess;

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
