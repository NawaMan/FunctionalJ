package functionalj.promise;

import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public class CompletedAction<DATA> extends UncompleteAction<DATA> {
    
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
