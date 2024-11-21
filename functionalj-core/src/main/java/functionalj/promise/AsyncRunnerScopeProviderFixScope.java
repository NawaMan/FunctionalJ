package functionalj.promise;

/**
 * Default implementation of {@link AsyncRunnerScopeProvider} which return global scope first and then locals.
 */
public class AsyncRunnerScopeProviderFixScope implements AsyncRunnerScopeProvider {
    
    /** Provider for specific scope. */
    public static AsyncRunnerScopeProvider forScope(String name, AsyncRunnerScope scope) {
        return new AsyncRunnerScopeProvider() {
            @Override
            public AsyncRunnerScope applyUnsafe() throws Exception {
                return scope;
            }
            @Override
            public String toString() {
                return name;
            }
        };
    }
    
    private final String           name;
    private final AsyncRunnerScope scope;
    
    AsyncRunnerScopeProviderFixScope(String name,  AsyncRunnerScope scope) {
        this.name  = (name != null) ? name : scope.toString();
        this.scope = scope;
    }
    
    @Override
    public AsyncRunnerScope applyUnsafe() throws Exception {
        return scope;
    }
    @Override
    public String toString() {
        return name;
    }
    
}
