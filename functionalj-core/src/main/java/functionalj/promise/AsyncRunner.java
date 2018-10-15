package functionalj.promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

@FunctionalInterface
public interface AsyncRunner extends Consumer<Runnable> {
    
    public static final AsyncRunner onNewThread = runnable -> {
        new Thread(runnable).start();
    };
    
    public static final AsyncRunner threadFactory = runnable -> {
        Executors.defaultThreadFactory().newThread(runnable).start();
    };
    
    public static final AsyncRunner completeableFuture = runnable -> {
        CompletableFuture.runAsync(runnable);
    };
    
    public static AsyncRunner threadFactory(ThreadFactory threadFactory) {
        return runnable -> threadFactory.newThread(runnable).start();
    }
    
    public static AsyncRunner executorService(ExecutorService executorService) {
        return runnable -> executorService.execute(runnable);
    }
    
}
