package functionalj.promise;

/**
 * Provider that will provide a NoScope scope (unmanaged).
 */
public class AsyncRunnerScopeProviderNoScope extends AsyncRunnerScopeProviderFixScope {
    
    /** Provider for the NoScope. */
    public static final AsyncRunnerScopeProvider instance = new AsyncRunnerScopeProviderNoScope();
    
    
    AsyncRunnerScopeProviderNoScope() {
        super("NoScope", AsyncRunnerScope.noScope);
    }
    
}
