package functionalj.types.promise;

@SuppressWarnings("javadoc")
public abstract class AbstractPromiseControl<DATA> {
    
    protected final Promise<DATA> promise;
    
    AbstractPromiseControl(Promise<DATA> promise) {
        this.promise = promise;
    }
    
    public final Promise<DATA> getPromise() {
        return promise;
    }
    
}
