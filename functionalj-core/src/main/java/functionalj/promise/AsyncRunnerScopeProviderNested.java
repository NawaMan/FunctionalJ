package functionalj.promise;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderNested implements AsyncRunnerScopeProvider {
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider instance = new AsyncRunnerScopeProviderNested();
    
    
    AsyncRunnerScopeProviderNested() {
    }
    
    @Override
    public AsyncRunnerScope applyUnsafe() throws Exception {
        return new AsyncRunnerNestedScope();
    }
    @Override
    public String toString() {
        return "Nested";
    }
    
}
