package functionalj.stream.intstream;

import java.util.function.IntFunction;

import functionalj.function.FunctionInvocationException;
import functionalj.stream.StreamPlus;
import lombok.val;


class IntStreamPlusMapAddOnHelper {
    
    @SafeVarargs
    static final <T> StreamPlus<T> mapFirst(
            IntStreamPlusWithMapFirst stream,
            IntFunction<T> ...        mappers) {
        return stream
                .mapToObj(d -> {
                    Exception exception = null;
                    boolean hasNull = false;
                    for(val mapper : mappers) {
                        try {
                            val res = mapper.apply(d);
                            if (res == null)
                                 hasNull = true;
                            else return (T)res;
                        } catch (Exception e) {
                            if (exception == null)
                                exception = e;
                        }
                    }
                    if (hasNull)
                        return (T)null;
                    
                    throw new FunctionInvocationException(exception);
                });
    }
    
}

public interface IntStreamPlusWithMapFirst {
    
    public <TARGET> StreamPlus<TARGET> mapToObj(
            IntFunction<? extends TARGET> mapper);
    
    //== mapFirst ==
    
    public default <T> StreamPlus<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2) {
        return IntStreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3) {
        return IntStreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4) {
        return IntStreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5) {
        return IntStreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            IntFunction<T> mapper1,
            IntFunction<T> mapper2,
            IntFunction<T> mapper3,
            IntFunction<T> mapper4,
            IntFunction<T> mapper5,
            IntFunction<T> mapper6) {
        return IntStreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
}
