package functionalj.stream;

import static functionalj.function.Func.f;

import java.util.function.Function;

import lombok.val;


class StreamPlusMapAddOnHelper {
    
    @SafeVarargs
    static final <D, T> StreamPlus<T> mapFirst(
            StreamPlusWithMapFirst<D>  stream,
            Function<? super D, T> ... mappers) {
        return stream
                .map(f(d -> {
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
                    
                    throw exception;
                }));
    }
    
}

public interface StreamPlusWithMapFirst<DATA> {
    
    public <TARGET> StreamPlus<TARGET> map(
            Function<? super DATA, ? extends TARGET> mapper);
    
    //== mapFirst ==
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return StreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return StreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return StreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return StreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return StreamPlusMapAddOnHelper
                .mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
}
