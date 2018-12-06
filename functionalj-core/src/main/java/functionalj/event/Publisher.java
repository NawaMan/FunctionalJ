package functionalj.event;

import functionalj.function.FuncUnit1;

public class Publisher<DATA> {
    
    private final Topic<DATA> topic = new Topic<>();
    private final FuncUnit1<Publisher<DATA>> onDone;
    
    public Publisher() {
        this(null);
    }
    public Publisher(FuncUnit1<Publisher<DATA>> onDone) {
        this.onDone = onDone;
    }
    
    public Publisher<DATA> publish(DATA data) {
        if (topic.isActive())
            topic.publish(data);
        return this;
    }
    
    public void done() {
        if (!topic.isActive())
            return;
        
        topic.done();
        if (onDone != null) {
            onDone.accept(this);
        }
    }
    
    public Topic<DATA> getTopic() {
        return topic;
    }
    
    public boolean isActive() {
        return topic.isActive();
    }
    
}
