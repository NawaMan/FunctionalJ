package functionalj.promise;

import functionalj.ref.ComputeBody;
import functionalj.ref.Ref;
import functionalj.ref.RunBody;

/**
 * AsyncRunner for Action.
 **/
public class ActionAsyncRunner {
    
    // TODO : Scoping is an experimental feature. Don't use it yet.
    
    /** Reference for the provider. **/
    public static final Ref<AsyncRunnerScopeProvider> asyncScopeProvider 
            = Ref.of(AsyncRunnerScopeProvider.class)
            .whenAbsentReferTo(AsyncRunnerScopeProvider.asyncScopeProvider)
            .defaultTo(AsyncRunnerScopeProvider.noScope);
    
    public static <EXCEPTION extends Exception> Promise<Object> run(RunBody<EXCEPTION> runnable) {
        return run(null, runnable);
    }
    
    public static <DATA, EXCEPTION extends Exception> Promise<DATA> run(ComputeBody<DATA, EXCEPTION> body) {
        return run(null, body);
    }
    
    public static <EXCEPTION extends Exception> Promise<Object> run(AsyncRunner runner, RunBody<EXCEPTION> runnable) {
        return run(runner, () -> {
            runnable.run();
            return null;
        });
    }
    
    public static <DATA, EXCEPTION extends Exception> Promise<DATA> run(AsyncRunner runner, ComputeBody<DATA, EXCEPTION> body) {
        return AsyncRunner.run(runner, asyncScopeProvider.get(), body);
    }
    
}
