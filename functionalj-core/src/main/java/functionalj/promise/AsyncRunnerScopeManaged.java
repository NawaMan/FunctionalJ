package functionalj.promise;

import java.util.concurrent.ConcurrentHashMap;

import functionalj.environments.Env;
import functionalj.exception.ExceptionUtils;
import lombok.val;

public abstract class AsyncRunnerScopeManaged extends AsyncRunnerScope {
    
    protected final ConcurrentHashMap<Thread, Thread> scopedThreads  = new ConcurrentHashMap<>();
    
    @Override
    protected void onBeforeSubAction() {
        val currentThread = Thread.currentThread();
        scopedThreads.put(currentThread, currentThread);
        System.out.println(this + " adds " + currentThread + ".");
        System.out.println();
    }
    
    protected final void interruptAll() {
        scopedThreads.keySet().forEach(thread -> {
            try {
                thread.interrupt();
                
                val currentThread = Thread.currentThread();
                System.out.println(this + " interrupts " + currentThread + ".");
                System.out.println();
            } catch (Throwable throwable) {
                /* Do not expect any exception .... Do nothing but printing. */
                val message = ExceptionUtils.toString("Unexpected exception: ", throwable);
                Env.console().errPrintln(message);
            }
        });
    }
    
}
