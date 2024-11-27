package functionalj.promise;

import functionalj.function.Func0;
import functionalj.ref.Ref;

@FunctionalInterface
public interface AsyncRunnerScopeProvider extends Func0<AsyncRunnerScope> {
    
    /** Provider for a no-op scope. */
    public static final AsyncRunnerScopeProvider noScope = AsyncRunnerScopeProviderNoScope.instance;
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider global = AsyncRunnerScopeProviderGlobal.instance;
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider nested = AsyncRunnerScopeProviderNested.instance;
    
    /** Reference for the provider. **/
    public static final Ref<AsyncRunnerScopeProvider> asyncScopeProvider 
            = Ref.of(AsyncRunnerScopeProvider.class).defaultTo(AsyncRunnerScopeProvider.noScope);
    
    /** Provider for specific scope. */
    public static AsyncRunnerScopeProvider forScope(AsyncRunnerScope scope) {
        return forScope(scope.toString(), scope);
    }
    
    /** Provider for specific scope. */
    public static AsyncRunnerScopeProvider forScope(String name, AsyncRunnerScope scope) {
        return AsyncRunnerScopeProviderFixScope.forScope(name, scope);
    }
    
    
    //== Functionality ==
    
    public AsyncRunnerScope applyUnsafe() throws Exception;
    
}
