package functionalj.stream.longstream;

import java.util.function.LongFunction;

import functionalj.function.FunctionInvocationException;
import functionalj.stream.StreamPlus;
import lombok.val;


class LongStreamPlusMapFirstAddOnHelper {
    
    @SafeVarargs
    static final <T> StreamPlus<T> mapFirst(
            LongStreamPlusWithMapFirst stream,
            LongFunction<T> ...        mappers) {
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

public interface LongStreamPlusWithMapFirst {
    
    public <TARGET> StreamPlus<TARGET> mapToObj(
            LongFunction<? extends TARGET> mapper);
    
    //== mapFirst ==
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper1,
            LongFunction<T> mapper2) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper1, mapper2);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper1,
            LongFunction<T> mapper2,
            LongFunction<T> mapper3) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper1,
            LongFunction<T> mapper2,
            LongFunction<T> mapper3,
            LongFunction<T> mapper4) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper1,
            LongFunction<T> mapper2,
            LongFunction<T> mapper3,
            LongFunction<T> mapper4,
            LongFunction<T> mapper5) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            LongFunction<T> mapper1,
            LongFunction<T> mapper2,
            LongFunction<T> mapper3,
            LongFunction<T> mapper4,
            LongFunction<T> mapper5,
            LongFunction<T> mapper6) {
        return LongStreamPlusMapFirstAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
}
