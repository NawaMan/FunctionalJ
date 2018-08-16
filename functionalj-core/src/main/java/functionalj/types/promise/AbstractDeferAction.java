package functionalj.types.promise;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("javadoc")
public abstract class AbstractDeferAction<DATA> implements HasPromise<DATA> {
    
    protected final Promise<DATA> promise;
    
    AbstractDeferAction(Promise<DATA> promise) {
        this.promise = requireNonNull(promise);
    }
    
    public final Promise<DATA> getPromise() {
        return promise;
    }
    
}
