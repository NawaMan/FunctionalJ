package functionalj.environments;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleInQueue extends LinkedBlockingQueue<String> {
    
    private static final long serialVersionUID = -7608868480591623575L;
    
    private final String endValue = Console.Stub.newEndValue();
    
    public ConsoleInQueue() {
        super();
    }
    public ConsoleInQueue(Collection<String> inQueue) {
        super(inQueue);
    }
    
    public void end() {
        this.add(endValue);
    }
    
    public String getEndValue() {
        return endValue;
    }
}
