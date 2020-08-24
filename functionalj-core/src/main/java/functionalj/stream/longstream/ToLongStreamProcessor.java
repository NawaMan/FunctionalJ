package functionalj.stream.longstream;

import functionalj.lens.lenses.LongAccess;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;

@FunctionalInterface
public interface ToLongStreamProcessor<DATA> extends StreamProcessor<DATA, Long>, LongAccess<StreamPlus<DATA>> {
    @Override
    public default Long applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
 
    @Override
    public default long applyAsLong(StreamPlus<DATA> host) {
        return apply(host);
    }
}
