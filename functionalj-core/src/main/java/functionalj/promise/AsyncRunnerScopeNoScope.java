package functionalj.promise;

/**
 * This class is a AsyncRunnerScope that do nothing.
 **/
public class AsyncRunnerScopeNoScope extends AsyncRunnerScope {

    @Override
    protected void onBeforeSubAction() {
    }
    
    @Override
    protected void onActionCompleted() {
    }
    
}
