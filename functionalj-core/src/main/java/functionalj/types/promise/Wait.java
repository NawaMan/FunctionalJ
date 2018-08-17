package functionalj.types.promise;

import java.util.function.Function;

@SuppressWarnings("javadoc")
public abstract class Wait {
    
    public static WaitForever forever() {
        return WaitForever.instance ;
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds) {
        return new WaitAwhile.WaitThread(milliseconds);
    }
    
    public static WaitAwhile forSeconds(long seconds) {
        return new WaitAwhile.WaitThread(seconds * 1000);
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds, Function<Runnable, Thread> threadFacory) {
        return new WaitAwhile.WaitThread(milliseconds, threadFacory);
    }
    
    public static WaitAwhile forSeconds(long seconds, Function<Runnable, Thread> threadFacory) {
        return new WaitAwhile.WaitThread(seconds * 1000, threadFacory);
    }
    
    
    Wait() {}
    
    public abstract WaitSession newSession();
    
    protected final void expire(WaitSession session, String message, Throwable throwable) {
        session.expire(message, throwable);
    }
    
}
