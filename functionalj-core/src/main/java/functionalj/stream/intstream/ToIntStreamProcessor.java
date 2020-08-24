package functionalj.stream.intstream;

import functionalj.lens.lenses.IntegerAccess;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;

@FunctionalInterface
public interface ToIntStreamProcessor<DATA> extends StreamProcessor<DATA, Integer>, IntegerAccess<StreamPlus<DATA>> {
    @Override
    public default Integer applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
 
    @Override
    public default int applyAsInt(StreamPlus<DATA> host) {
        return apply(host);
    }
}