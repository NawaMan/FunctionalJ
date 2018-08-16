package functionalj.types.promise;

@SuppressWarnings("javadoc")
public class PromiseControl<DATA> extends AbstractPromiseControl<DATA> {
    
    PromiseControl(Promise<DATA> promise) {
        super(promise);
    }
    
    public PendingPromiseController<DATA> start() {
        promise. start();
        return new PendingPromiseController<>(promise);
    }
    
}