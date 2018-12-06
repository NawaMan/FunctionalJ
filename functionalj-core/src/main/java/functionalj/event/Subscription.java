package functionalj.event;

import java.util.concurrent.atomic.AtomicBoolean;

import functionalj.function.Func1;
import functionalj.result.Result;
import lombok.val;

public class Subscription<DATA> {
    
    public static final Cancellation Continue = Cancellation.Continue;
    public static final Cancellation Cancel   = Cancellation.Cancel;
    
    
    private final Topic<DATA>                       topic;
    private final Func1<Result<DATA>, Cancellation> subscriber;
    private final AtomicBoolean                     isActive = new AtomicBoolean(true);
    
    public Subscription(Topic<DATA> topic, Func1<Result<DATA>, Cancellation> subscriber) {
        this.topic      = topic;
        this.subscriber = subscriber;
    }
    
    public boolean isActive() {
        return isActive.get();
    }
    
    void notifyNext(Result<DATA> next) {
        if (!isActive.get())
            return;
        
        // TODO - Add scheduling here.
        try {
            val cancellation = subscriber.applyTo(next);
            if (Subscription.Cancel.equals(cancellation))
                unsubcribe();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        next.ifNoMore(()->{
            isActive.set(false);
            unsubcribe();
        });
    }
    
    public void unsubcribe() {
        if (!isActive.get())
            return;
        
        this.topic.unsubcribe(this);
        isActive.set(false);
    }

}
