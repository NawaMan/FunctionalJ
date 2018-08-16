package functionalj.types.promise;

import java.util.function.Consumer;

import functionalj.types.result.Result;

@SuppressWarnings("javadoc")
public class PendingAction<DATA> extends AbstractDeferAction<DATA> {
    
    PendingAction(Promise<DATA> promise) {
        super(promise);
    }
    
    public CompletedAction<DATA> abort() {
        promise.abort();
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(String message) {
        promise.abort(message);
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(Throwable cause) {
        promise.abort(cause);
        return new CompletedAction<DATA>(promise);
    }
    public CompletedAction<DATA> abort(String message, Throwable cause) {
        promise.abort(message, cause);
        return new CompletedAction<DATA>(promise);
    }
    
    // For internal use only -- This will be wrong if the result is not ready.
    CompletedAction<DATA> completeWith(Result<DATA> result) {
        if (result.isException())
             promise.makeFail    (result.getException());
        else promise.makeComplete(result.get());
        
        return new CompletedAction<DATA>(promise);
    }
    
    public CompletedAction<DATA> complete(DATA value) {
        promise.makeComplete(value);
        return new CompletedAction<DATA>(promise);
    }
    
    public CompletedAction<DATA> fail(Exception exception) {
        promise.makeFail(exception);
        return new CompletedAction<DATA>(promise);
    }
    
    // TODO - Add methods that allow the caller to know if the proceeding success or fail.
    // TODO - Add methods that the proceeding MUST be done or exception should be thrown.
    
    public PendingAction<DATA> peek(Consumer<Promise<DATA>> consumer) {
        if (consumer != null)
            consumer.accept(promise);
        
        return this;
    }
    public PendingAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        if (consumer != null)
            consumer.accept(promise);
        
        return this;
    }
    
}