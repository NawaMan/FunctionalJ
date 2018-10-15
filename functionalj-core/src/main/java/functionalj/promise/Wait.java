package functionalj.promise;

import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public abstract class Wait {
    
    public static WaitForever forever() {
        return WaitForever.instance ;
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds) {
        return new WaitAwhile.WaitAsync(milliseconds);
    }
    
    public static WaitAwhile forSeconds(long seconds) {
        return new WaitAwhile.WaitAsync(seconds * 1000);
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds, Consumer<Runnable> threadFacory) {
        return new WaitAwhile.WaitAsync(milliseconds, threadFacory);
    }
    
    public static WaitAwhile forSeconds(long seconds, Consumer<Runnable> threadFacory) {
        return new WaitAwhile.WaitAsync(seconds * 1000, threadFacory);
    }
    
    
    Wait() {}
    
    public abstract WaitSession newSession();
    
    protected final void expire(WaitSession session, String message, Exception throwable) {
        session.expire(message, throwable);
    }
    
}
