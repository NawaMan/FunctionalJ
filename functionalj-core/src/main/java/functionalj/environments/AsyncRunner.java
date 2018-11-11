package functionalj.environments;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import functionalj.function.FuncUnit1;
import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.ref.ComputeBody;
import functionalj.ref.Run;
import functionalj.ref.RunBody;
import functionalj.ref.Substitution;
import lombok.val;

@FunctionalInterface
public interface AsyncRunner extends FuncUnit1<Runnable> {
    
    public static <E extends Exception> Promise<Object> run(RunBody<E> runnable) {
        return run(null, runnable);
    }
    public static <D, E extends Exception> Promise<D> run(ComputeBody<D, E> body) {
        return run(null, body);
    }
    public static <E extends Exception> Promise<Object> run(AsyncRunner runner, RunBody<E> runnable) {
        return run(runner, ()->{
            runnable.run();
            return null;
        });
    }
    public static <D, E extends Exception>  Promise<D> run(AsyncRunner runner, ComputeBody<D, E> body) {
        val action = DeferAction.of((Class<D>)null).start();
        
        val theRunner     = (runner != null) ? runner : Env.async();
        val substitutions = Run.getCurrentSubstitutions().exclude(Substitution::isThreadLocal);
        theRunner.accept(()->{
            try {
                Run.with(substitutions)
                .run(()->{
                    val value = body.compute();
                    action.complete(value);
                });
            } catch (Exception exception) {
                ThrowFuncs.handleNoThrow(exception);
            }
        });
        
        return action.getPromise();
    }
    
    
    public static final AsyncRunner onSameThread = runnable -> {
        runnable.run();
    };
    
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
