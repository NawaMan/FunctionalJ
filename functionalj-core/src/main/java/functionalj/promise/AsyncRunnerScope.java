package functionalj.promise;

import functionalj.ref.Ref;

/**
 * This class represents a runner with might manage its sub action (runnable).
 **/
public abstract class AsyncRunnerScope {
    
    public static Ref<AsyncRunnerScope> asyncScope = Ref.of(AsyncRunnerScope.class).defaultTo(new AsyncRunnerLocalScope());
    
    /** This scope do nothing - Sub runner will not be cleanup. */
    public static final AsyncRunnerScope NOOP = new AsyncRunnerScopeNoOp();
    
    /** A globally usable local scope is a global scope. */
    public static final AsyncRunnerScope GLOBAL = new AsyncRunnerGlobalScope();
    
    protected abstract void onBeforeRun();
    
    protected abstract void onAllDone();
    
}
