package functionalj.types.promise;

import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public class CompletedAction<DATA> extends AbstractDeferAction<DATA> {
    
    CompletedAction(Promise<DATA> promise) {
        super(promise);
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
    
}
