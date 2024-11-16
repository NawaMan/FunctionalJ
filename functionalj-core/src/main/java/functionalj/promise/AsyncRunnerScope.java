package functionalj.promise;

import functionalj.ref.Ref;
import functionalj.result.Result;

/**
 * This class represents a runner with might manage its sub action (runnable).
 **/
public abstract class AsyncRunnerScope {
    
    /** This scope do nothing - Sub runner will not be cleanup. */
    public static final AsyncRunnerScope NOOP = new AsyncRunnerScopeNoOp();
    
    /** A globally usable local scope is a global scope. */
    public static final AsyncRunnerScope GLOBAL = new AsyncRunnerGlobalScope();
    
    /** The scope to use. */
    static Ref<AsyncRunnerScope> asyncScope = Ref.of(AsyncRunnerScope.class).defaultTo(AsyncRunnerScope.GLOBAL);
    
    /** The current scope. **/
    public static Result<AsyncRunnerScope> currentScope() {
        return asyncScope.asResult();
    }
    
    //== Functionality ==
    
    /** This to be run before a sub action. */
    protected abstract void onBeforeSubAction();
    
    /** This to be run when a main action finish. */
    protected abstract void onActionCompleted();
    
}
