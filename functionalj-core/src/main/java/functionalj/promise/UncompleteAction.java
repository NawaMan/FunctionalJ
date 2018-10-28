package functionalj.promise;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.TimeUnit;

import functionalj.result.Result;

@SuppressWarnings("javadoc")
public abstract class UncompleteAction<DATA> implements HasPromise<DATA> {
    
    protected final Promise<DATA> promise;
    
    UncompleteAction(Promise<DATA> promise) {
        this.promise = requireNonNull(promise);
    }
    
    public final CompletedAction<DATA> abort() {
        promise.abort();
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(String message) {
        promise.abort(message);
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(Exception cause) {
        promise.abort(cause);
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(String message, Exception cause) {
        promise.abort(message, cause);
        return new CompletedAction<DATA>(promise);
    }
    
    // For internal use only -- This will be wrong if the result is not ready.
    final CompletedAction<DATA> completeWith(Result<DATA> result) {
        if (result.isException())
             promise.makeFail    (result.getException());
        else promise.makeComplete(result.get());
        
        return new CompletedAction<DATA>(promise);
    }
    
    public final CompletedAction<DATA> complete(DATA value) {
        promise.makeComplete(value);
        return new CompletedAction<DATA>(promise);
    }
    
    public final CompletedAction<DATA> fail(Exception exception) {
        promise.makeFail(exception);
        return new CompletedAction<DATA>(promise);
    }
    
    public final Promise<DATA> getPromise() {
        return promise;
    }
    public final Result<DATA> getCurentResult() {
        return promise.getCurrentResult();
    }
    public final Result<DATA> getResult() {
        if (!promise.isStarted() && this instanceof DeferAction) {
            ((DeferAction<DATA>)this).start();
        }
        return promise.getResult();
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        return promise.getResult(timeout, unit);
    }
    
}
