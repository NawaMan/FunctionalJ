package functionalj.event;

import static java.util.Objects.requireNonNull;

public class MainTopic<DATA> extends Topic<DATA> {
    
    private final Publisher<DATA> publisher;
    
    public MainTopic(Publisher<DATA> publisher) {
        this.publisher = requireNonNull(publisher);
    }
    
    void done() {
        super.done();
        publisher.done();
    }
    
}
