package functionalj.promise;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is a {@link AsyncRunnerScope} that actually manage and clean up the sub actions (runnables).
 **/
public class AsyncRunnerNestedScope extends AsyncRunnerScopeManaged {
    
    private static final AtomicInteger ID = new AtomicInteger(0);
    
    private final int id = ID.getAndIncrement();
    
    @Override
    protected void onActionCompleted() {
        interruptAll();
    }
    
    @Override
    public String toString() {
        return "Scope:" + id;
    }
}
