package functionalj.promise;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderDefault implements AsyncRunnerScopeProvider {
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider INSTANCE = new AsyncRunnerScopeProviderDefault();
    
    
    private final AtomicBoolean isFirst = new AtomicBoolean(true);
    
    AsyncRunnerScopeProviderDefault() {
    }
    
    @Override
    public AsyncRunnerScope applyUnsafe() throws Exception {
        return isFirst.getAndSet(false)
                ? AsyncRunnerScope.GLOBAL
                : new AsyncRunnerLocalScope();
    }
    @Override
    public String toString() {
        return "DEFAULT";
    }
    
}
