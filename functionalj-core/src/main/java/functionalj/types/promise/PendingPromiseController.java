package functionalj.types.promise;

@SuppressWarnings("javadoc")
public class PendingPromiseController<DATA> extends AbstractPromiseControl<DATA> {
    
    PendingPromiseController(Promise<DATA> promise) {
        super(promise);
    }
    
    public CompletedPromiseController<DATA> abort() {
        promise.abort();
        return new CompletedPromiseController<DATA>(promise);
    }
    
    public CompletedPromiseController<DATA> complete(DATA value) {
        promise.makeComplete(value);
        return new CompletedPromiseController<DATA>(promise);
    }
    
    public CompletedPromiseController<DATA> fail(Exception exception) {
        promise.makeFail(exception);
        return new CompletedPromiseController<DATA>(promise);
    }
    
}