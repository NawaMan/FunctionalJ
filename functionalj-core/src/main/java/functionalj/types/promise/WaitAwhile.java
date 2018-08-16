package functionalj.types.promise;

import static functionalj.functions.Func.getOrElse;

import java.util.function.Supplier;

import functionalj.types.result.Result;

@SuppressWarnings("javadoc")
public abstract class WaitAwhile extends Wait {
    
    @SuppressWarnings("rawtypes")
    public static final Supplier<Result> CANCELLATION_RESULT = ()->Result.ofCancelled();
    
    public <DATA> WaitOrDefault<DATA> orDefaultTo(DATA data) {
        return new WaitOrDefault<>(this, ()->Result.of(data));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultFrom(Supplier<DATA> supplier) {
        return new WaitOrDefault<>(this, ()->Result.from(supplier));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultGet(Supplier<Result<DATA>> supplier) {
        return new WaitOrDefault<>(this, supplier);
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultResult(Result<DATA> result) {
        return new WaitOrDefault<>(this, ()->result);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <DATA> WaitOrDefault<DATA> orCancel() {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)(Supplier)CANCELLATION_RESULT);
    }
    
    // TODO - from Exception class.
    public <DATA> WaitOrDefault<DATA> orException(Exception exception) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(exception));
    }
    public <DATA> WaitOrDefault<DATA> orException(Supplier<Exception> exceptionSupplier) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(getOrElse(exceptionSupplier, null)));
    }
    
    
    protected WaitAwhile() {}
    
}
