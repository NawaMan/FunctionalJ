package functionalj.promise;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import functionalj.result.Result;

@SuppressWarnings("javadoc")
public class CompletedAction<DATA> implements HasPromise<DATA> {
    
    protected final Promise<DATA> promise;
    
    CompletedAction(Promise<DATA> promise) {
        this.promise = promise;
    }
    
    public CompletedAction<DATA> peek(Consumer<Promise<DATA>> consumer) {
        if (consumer != null)
            consumer.accept(promise);
        
        return this;
    }
    public CompletedAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        if (consumer != null)
            consumer.accept(promise);
        
        return this;
    }
    
    public final Promise<DATA> getPromise() {
        return promise;
    }
    public final Result<DATA> getCurentResult() {
        return promise.getCurrentResult();
    }
    public final Result<DATA> getResult() {
        return promise.getResult();
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        return promise.getResult(timeout, unit);
    }
    
}
