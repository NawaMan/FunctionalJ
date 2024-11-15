package functionalj.promise;

import lombok.val;

public class AsyncRunnerGlobalScope extends AsyncRunnerScopeManaged {
    
    AsyncRunnerGlobalScope() {
        Runtime.getRuntime()
        .addShutdownHook(new Thread(() -> {
            interruptAll();
        }));
    }
    
    @Override
    protected void onBeforeRun() {
        val currentThread = Thread.currentThread();
        scopedThreads.put(currentThread, currentThread);
    }
    
    @Override
    protected void onAllDone() {
    }
    
}
