package functionalj.stream;

import java.math.BigInteger;

import functionalj.lens.lenses.BigIntegerAccess;

@FunctionalInterface
public interface ToBigIntegerStreamProcessor<DATA> extends StreamProcessor<DATA, BigInteger>, BigIntegerAccess<StreamPlus<DATA>> {
    @Override
    public default BigInteger applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
}
