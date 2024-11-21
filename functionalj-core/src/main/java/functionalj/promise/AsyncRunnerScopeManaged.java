package functionalj.promise;

import java.util.concurrent.ConcurrentHashMap;

import functionalj.environments.Env;
import functionalj.exception.ExceptionUtils;
import lombok.val;

/**
 * AsyncRunnder Scope of this kind will manage its sub runner by interrupting them if the current scope ends.
 **/
public abstract class AsyncRunnerScopeManaged extends AsyncRunnerScope {
    
    protected final ConcurrentHashMap<Thread, Thread> scopedThreads  = new ConcurrentHashMap<>();
    
    @Override
    protected void onBeforeSubAction() {
        val currentThread = Thread.currentThread();
        scopedThreads.put(currentThread, currentThread);
    }
    
    protected final void interruptAll() {
        scopedThreads.keySet().forEach(thread -> {
            try {
                thread.interrupt();
            } catch (Throwable throwable) {
                /* Do not expect any exception .... Do nothing but printing. */
                val message = ExceptionUtils.toString("Unexpected exception: ", throwable);
                Env.console().errPrintln(message);
            }
        });
    }
    
}
