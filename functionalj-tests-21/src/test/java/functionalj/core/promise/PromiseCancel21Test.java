package functionalj.core.promise;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.promise.DeferAction;

public class PromiseCancel21Test {
    
    static interface Body {
        void run() throws Exception;
    }
    
    @Test
    public void testSuccess_noSubscriptionNorCancel() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            var deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello World!";
            });
            var action = deferAction.start();
            
            var result = action.getResult();
            assertEquals("Result:{ Value: Hello World! }", result.toString());
        });
    }
    
    private void ensureThreadCleanup(Body body) throws Exception {
        int startActiveThreads = Thread.activeCount();
        var beforeThreads      = currentThreads(startActiveThreads);
        
        try {
            body.run();
        } finally {
            if (startActiveThreads < Thread.activeCount()) {
                var afterThreads = currentThreads(startActiveThreads);
                assertEquals(beforeThreads, afterThreads);
            }
        }
    }
    
    private String currentThreads(int startActiveThreads) {
        var logs    = new ArrayList<String>();
        var threads = new Thread[startActiveThreads];
        // Get all active threads in the current thread group
        int actualThreads = Thread.enumerate(threads);
        
        logs.add("Number of active threads: " + actualThreads + "\n");
        for (int i = 0; i < actualThreads; i++) {
            logs.add("Thread " + i + ": " + threads[i].getName() + "\n");
        }
        return logs.stream().collect(Collectors.joining());
    }
    
}
