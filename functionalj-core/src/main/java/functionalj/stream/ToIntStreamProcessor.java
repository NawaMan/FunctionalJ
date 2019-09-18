package functionalj.stream;

import functionalj.lens.lenses.IntegerAccess;

@FunctionalInterface
public interface ToIntStreamProcessor<DATA> extends StreamProcessor<DATA, Integer>, IntegerAccess<StreamPlus<DATA>> {
    @Override
    public default Integer applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
}
