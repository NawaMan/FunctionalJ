package functionalj.event;

import functionalj.function.FuncUnit2;
import functionalj.result.Result;

@SuppressWarnings({ "rawtypes", "unused" })
public class SubTopic<DATA> extends Topic<DATA> {
    
    private final Topic        parentTopic;
    private final Subscription subscription;
    
    <SOURCE> SubTopic(Topic<SOURCE> parentTopic, FuncUnit2<Result<SOURCE>, Topic<DATA>> resultConsumer) {
        this.parentTopic  = parentTopic;
        this.subscription = parentTopic.onNext(result -> {
            resultConsumer.accept(result, this);
        });;
    }
    
    void done() {
        super.done();
        subscription.unsubcribe();
    }
    
}
