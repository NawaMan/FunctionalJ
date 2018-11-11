package functionalj.promise;

import functionalj.environments.AsyncRunner;

@SuppressWarnings("javadoc")
public abstract class Wait {
    
    public static WaitForever forever() {
        return WaitForever.instance;
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds) {
        return new WaitAwhile.WaitAsync(milliseconds);
    }
    
    public static WaitAwhile forSeconds(long seconds) {
        return new WaitAwhile.WaitAsync(seconds * 1000);
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds, AsyncRunner asyncRunner) {
        return new WaitAwhile.WaitAsync(milliseconds, asyncRunner);
    }
    
    public static WaitAwhile forSeconds(long seconds, AsyncRunner asyncRunner) {
        return new WaitAwhile.WaitAsync(seconds * 1000, asyncRunner);
    }
    
    
    Wait() {}
    
    public abstract WaitSession newSession();
    
    protected final void expire(WaitSession session, String message, Exception throwable) {
        session.expire(message, throwable);
    }
    
}
