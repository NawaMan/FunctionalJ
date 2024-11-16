package functionalj.promise;

import functionalj.function.Func0;
import functionalj.ref.Ref;

@FunctionalInterface
public interface AsyncRunnerScopeProvider extends Func0<AsyncRunnerScope> {
    
    public static final Ref<AsyncRunnerScopeProvider> asyncScopeProvider 
            = Ref.of(AsyncRunnerScopeProvider.class).defaultTo(AsyncRunnerScopeProviderDefault.INSTANCE);
    
    /** Provider for specific scope. */
    public static AsyncRunnerScopeProvider forScope(AsyncRunnerScope scope) {
        return forScope(scope.toString(), scope);
    }
    
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
    
    /** Provider for a no-op scope. */
    public static final AsyncRunnerScopeProvider NOOP = forScope("NOOP", AsyncRunnerScope.NOOP);
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider GLOBAL = forScope("GLOBAL", AsyncRunnerScope.GLOBAL);
    
    /** Provider for the global scope. */
    public static final AsyncRunnerScopeProvider DEFAULT = AsyncRunnerScopeProviderDefault.INSTANCE;
    
    
    //== Functionality ==
    
    public AsyncRunnerScope applyUnsafe() throws Exception;
    
}
