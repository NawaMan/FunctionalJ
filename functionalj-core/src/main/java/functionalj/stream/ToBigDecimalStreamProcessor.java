package functionalj.stream;

import java.math.BigDecimal;
import functionalj.lens.lenses.BigDecimalAccess;

@FunctionalInterface
public interface ToBigDecimalStreamProcessor<DATA> extends /*StreamProcessor<DATA, BigDecimal>, */
BigDecimalAccess<StreamPlus<DATA>> {
    
    public BigDecimal process(StreamPlus<? extends DATA> stream);
    
    @Override
    public default BigDecimal applyUnsafe(StreamPlus<DATA> input) throws Exception {
        return process(input);
    }
}
