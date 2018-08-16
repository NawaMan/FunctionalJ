package functionalj.types.promise;

import java.util.function.Supplier;

import functionalj.types.result.Result;

@SuppressWarnings("javadoc")
public interface WaitAwhile extends Wait {
    
    @SuppressWarnings("rawtypes")
    public static final Supplier<Result> CANCELLATION_RESULT = ()->Result.ofCancelled();
    
    public default <DATA> WaitOrDefault<DATA> orDefaultTo(DATA data) {
        return new WaitOrDefault<>(this, ()->Result.of(data));
    }
    
    public default <DATA> WaitOrDefault<DATA> orDefaultFrom(Supplier<DATA> supplier) {
        return new WaitOrDefault<>(this, ()->Result.from(supplier));
    }
    
    public default <DATA> WaitOrDefault<DATA> orDefaultGet(Supplier<Result<DATA>> supplier) {
        return new WaitOrDefault<>(this, supplier);
    }
    
    public default <DATA> WaitOrDefault<DATA> orDefaultResult(Result<DATA> result) {
        return new WaitOrDefault<>(this, ()->result);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <DATA> WaitOrDefault<DATA> orDefaultCancellation() {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)(Supplier)CANCELLATION_RESULT);
    }
    
}
