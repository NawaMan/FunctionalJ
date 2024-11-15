package functionalj.promise;

import functionalj.function.Func0;

@FunctionalInterface
public interface AsyncRunnerScopeProvider extends Func0<AsyncRunnerScope> {
    
    public AsyncRunnerScope applyUnsafe() throws Exception;
    
}
