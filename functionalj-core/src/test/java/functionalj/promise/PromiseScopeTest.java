package functionalj.promise;

import static functionalj.TestHelper.assertAsString;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.TestHelper;
import functionalj.promise.PromiseCancelTest.Body;
import lombok.val;

public class PromiseScopeTest {
	
	@Test
	public void testAgain() throws Exception {
        ensureThreadCleanup(() -> {
            val subSubAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello there!";
            });
        	
            val subAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                
                subSubAction.start();

                Thread.sleep(200);
                return "Hello there!";
            });
        	
            val mainAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);

            	subAction.start();
                
                Thread.sleep(100);
                
                // Ended before sub-action finish
                return "Hello World!";
            });
            
            mainAction.start();
            val result = mainAction.getResult();
            System.out.println(result);

        });
        System.out.println();
	}
	
	@Test
	public void testFork_doneFirst() throws Exception {
        ensureThreadCleanup(() -> {
            val subAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello there!";
            });
        	
            val mainAction = DeferAction.<String>from(() -> {
            	subAction.fork();
                Thread.sleep(200);
                
                // Ended before sub-action finish
                return "Hello World!";
            });
            
            mainAction.start();
            assertAsString("Result:{ Value: Hello World! }", mainAction.getResult());
            assertAsString("Result:{ Value: Hello there! }", subAction.getCurrentResult());

        });
	}
	
	@Test
	public void testFork_doneAfter() throws Exception {
        ensureThreadCleanup(() -> {
            val subAction = DeferAction.<String>from(() -> {
                Thread.sleep(200);
                return "Hello there!";
            });
        	
            val mainAction = DeferAction.<String>from(() -> {
            	subAction.fork();
                Thread.sleep(50);
                
                // Ended before sub-action finish
                return "Hello World!";
            });
            
            mainAction.start();
            assertAsString("Result:{ Value: Hello World! }", mainAction.getResult());
            assertAsString("Result:{ Cancelled }",           subAction.getCurrentResult());
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
        return logs.stream().collect(joining());
    }
    
}
