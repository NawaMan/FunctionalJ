package functionalj.reactive;

public class Publisher<DATA> {
    
    private final Topic<DATA> topic = new Topic<>();
    
    public Publisher<DATA> publish(DATA data) {
        topic.publish(data);
        return this;
    }
    
    public void done() {
        topic.done();
    }
    
    public Topic<DATA> getTopic() {
        return topic;
    }
    
    public boolean isActive() {
        return topic.isActive();
    }
    
}
