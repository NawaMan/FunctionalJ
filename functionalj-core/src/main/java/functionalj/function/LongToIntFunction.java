package functionalj.function;

import java.util.function.ToIntFunction;

public interface LongToIntFunction extends ToIntFunction<Long>, java.util.function.LongToIntFunction {
    
    public default int apply(Long l) {
        long longValue = l.longValue();
        int intValue = applyAsInt(longValue);
        return intValue;
    }
    
    public int applyAsInt(long l);
    
}
