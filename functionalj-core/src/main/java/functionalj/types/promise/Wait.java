package functionalj.types.promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public abstract class Wait {
    
    public static final Consumer<Runnable> asyncRunnerOnNewThread        = runnable -> new Thread(runnable).start();
    public static final Consumer<Runnable> asyncRunnerThreadFactory      = runnable -> Executors.defaultThreadFactory().newThread(runnable).start();
    public static final Consumer<Runnable> asyncRunnerCompleteableFuture = runnable -> CompletableFuture.runAsync(runnable);
    public static final Consumer<Runnable> asyncRunnerThreadFactory(ThreadFactory threadFactory) {
        return runnable -> threadFactory.newThread(runnable).start();
    }
    public static final Consumer<Runnable> asyncRunnerExecutorService(ExecutorService executorService) {
        return runnable -> executorService.execute(runnable);
    }
    
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
    
    protected final void expire(WaitSession session, String message, Throwable throwable) {
        session.expire(message, throwable);
    }
    
}
