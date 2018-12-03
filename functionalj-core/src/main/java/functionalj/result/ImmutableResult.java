package functionalj.result;

import java.util.concurrent.atomic.AtomicReference;

public class ImmutableResult<DATA> extends Result<DATA> {
    
    private final Object data;
    
    public ImmutableResult(DATA data) {
        this(data, (Exception)null);
    }
    ImmutableResult(DATA data, AtomicReference<Exception> exception) {
        this(data, exception.get());
    }
    ImmutableResult(DATA data, Exception exception) {
        this.data = (exception != null)
                ? new ExceptionHolder(exception)
                : data ;
    }
    
    @Override
    Object __valueData() {
        return data;
    }
    
}
