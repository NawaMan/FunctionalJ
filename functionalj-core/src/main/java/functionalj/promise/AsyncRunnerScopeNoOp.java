package functionalj.promise;

/**
 * This class is a AsyncRunnerScope that do nothing.
 **/
public class AsyncRunnerScopeNoOp extends AsyncRunnerScope {

    @Override
    protected void onBeforeRun() {
    }
    
    @Override
    protected void onAllDone() {
    }
    
}
