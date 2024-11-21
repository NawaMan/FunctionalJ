package functionalj.promise;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderGlobal extends AsyncRunnerScopeProviderFixScope {
    
    /** Provider for the Global. */
    public static final AsyncRunnerScopeProvider instance = new AsyncRunnerScopeProviderGlobal();
    
    AsyncRunnerScopeProviderGlobal() {
            super("Global", AsyncRunnerScope.global);
        }
    
}
