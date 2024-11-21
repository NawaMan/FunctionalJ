package functionalj.promise;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderDefault implements AsyncRunnerScopeProvider {
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider INSTANCE = new AsyncRunnerScopeProviderDefault();
    
    
    AsyncRunnerScopeProviderDefault() {
    }
    
    @Override
    public AsyncRunnerScope applyUnsafe() throws Exception {
        return new AsyncRunnerLocalScope();
    }
    @Override
    public String toString() {
        return "DEFAULT";
    }
    
}
