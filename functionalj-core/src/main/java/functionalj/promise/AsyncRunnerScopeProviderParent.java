package functionalj.promise;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderParent implements AsyncRunnerScopeProvider {
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider INSTANCE = new AsyncRunnerScopeProviderParent();
    
    
    AsyncRunnerScopeProviderParent() {
    }
    
    @Override
    public AsyncRunnerScope applyUnsafe() throws Exception {
        return AsyncRunnerScope.asyncScope.get();
    }
    @Override
    public String toString() {
        return "PARENT";
    }
    
}
