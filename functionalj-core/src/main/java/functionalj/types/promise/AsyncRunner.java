package functionalj.types.promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

public interface AsyncRunner extends Consumer<Runnable> {
    
    public static final Consumer<Runnable> onNewThread        = runnable -> new Thread(runnable).start();
    public static final Consumer<Runnable> threadFactory      = runnable -> Executors.defaultThreadFactory().newThread(runnable).start();
    public static final Consumer<Runnable> completeableFuture = runnable -> CompletableFuture.runAsync(runnable);
    
    public static Consumer<Runnable> threadFactory(ThreadFactory threadFactory) {
        return runnable -> threadFactory.newThread(runnable).start();
    }
    
    public static Consumer<Runnable> executorService(ExecutorService executorService) {
        return runnable -> executorService.execute(runnable);
    }
    
}
