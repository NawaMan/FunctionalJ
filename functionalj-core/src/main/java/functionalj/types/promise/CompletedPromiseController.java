package functionalj.types.promise;

@SuppressWarnings("javadoc")
public class CompletedPromiseController<DATA> extends AbstractPromiseControl<DATA> {
    
    CompletedPromiseController(Promise<DATA> promise) {
        super(promise);
    }
    
}
