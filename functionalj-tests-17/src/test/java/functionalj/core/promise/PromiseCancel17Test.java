package functionalj.core.promise;

import static functionalj.TestHelper.assertAsString;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.promise.DeferAction;
import lombok.val;

public class PromiseCancel17Test {
    
    static interface Body {
        void run() throws Exception;
    }
    
    
    @Test
    public void testSuccess_noSubscriptionNorCancel() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello World!";
            });
            val action = deferAction.start();
            
            val result = action.getResult();
            assertAsString("Result:{ Value: Hello World! }", result);
        });
    }
    
    private void ensureThreadCleanup(Body body) throws Exception {
        int startActiveThreads = Thread.activeCount();
        val beforeThreads      = currentThreads(startActiveThreads);
        
        try {
            body.run();
        } finally {
            if (startActiveThreads < Thread.activeCount()) {
                val afterThreads = currentThreads(startActiveThreads);
                assertEquals(beforeThreads, afterThreads);
            }
        }
    }
    
    private String currentThreads(int startActiveThreads) {
        val logs    = new ArrayList<String>();
        val threads = new Thread[startActiveThreads];
        // Get all active threads in the current thread group
        int actualThreads = Thread.enumerate(threads);
        
        logs.add("Number of active threads: " + actualThreads + "\n");
        for (int i = 0; i < actualThreads; i++) {
            logs.add("Thread " + i + ": " + threads[i].getName() + "\n");
        }
        return logs.stream().collect(Collectors.joining());
    }
    
}
