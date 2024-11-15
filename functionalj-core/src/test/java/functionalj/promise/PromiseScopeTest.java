package functionalj.promise;

import static functionalj.TestHelper.assertAsString;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.promise.PromiseCancelTest.Body;
import lombok.val;

public class PromiseScopeTest {
    
    @Test
    public void testScopeSuccess() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val logs = new ArrayList<String>();
            // Normally, a started defer action start and complete operation.
            
            val mainAction = DeferAction.<String>from(() -> {
                logs.add("Main.start()");
                Thread.sleep(50);
                
                val subAction = DeferAction.<String>from(() -> {
                    logs.add("Sub.start()");
                    Thread.sleep(50);
                    logs.add("Sub.end()");
                    return "Hello there!";
                })
                .onComplete(result -> {
                    logs.add("Sub completed");
                })
                .start();
                
                // Call `getResult()` ... wait to finish.
                subAction.getResult();
                
                Thread.sleep(10);
                logs.add("Main.end()");
                
                // Ended before sub-action finish (wait only 10 ... sub-action is took 50).
                return "Hello World!";
            })
            .start();
            // Call `getResult()` --  wait for the action.
            val result = mainAction.getResult();
            
            // Wait for result ... which mean also wait for the result above as well.
            assertAsString("Result:{ Value: Hello World! }", result);
            assertAsString("["
                    + "Main.start(), "
                    + "Sub.start(), "
                    + "Sub.end(), "
                    + "Sub completed, "
                    + "Main.end()"
                    + "]",
                    logs); 
        });
    }
//    
//    @Test
//    public void testScopeSuccess_noWaitForSub() throws Exception {
//        // Ensure that all thread are clean up.
//        ensureThreadCleanup(() -> {
//            val logs = new ArrayList<String>();
//            // Normally, a started defer action start and complete operation.
//            
//            val mainAction = DeferAction.<String>from(() -> {
//                logs.add("Main.start()");
//                
//                // Start the sub
//                DeferAction.<String>from(() -> {
//                    try {
//                        logs.add("Sub.start()");
//                        Thread.sleep(100000);
//                        logs.add("Sub.end()");
//                        return "Hello there!";
//                    } catch (InterruptedException e) {
//                        logs.add("Sub.interrupted()");
//                        throw e;
//                    }
//                })
//                .start();
//                
//                // But do not wait for the sub to finish.
//                logs.add("Main.end()");
//                return "Hello World!";
//            })
//            .start();
//            
//            Thread.sleep(10);
//            
//            // Wait for the main to finish
//            val result = mainAction.getResult();
//            
//            assertAsString("Result:{ Cancelled }", result);
//            
//            assertAsString(
//                    "functionalj.result.ResultCancelledException\n"
//                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
//                    + "\tat functionalj.promise.Promise.abort(Promise.java:\\E[0-9]+\\Q)\n"
//                    + "\tat functionalj.promise.UncompletedAction.abort(UncompletedAction.java:\\E[0-9]+\\Q)\n"
//                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
//                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
//                    + "\tat functionalj.promise.PromiseCancelTest.testScopeSuccess_noWaitForSub(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
//                    + "\\E.*\\Q",
//                    exceptionWtihStacktrace(result.getException()));
//            
//            assertAsString(
//                    "["
//                    + "Main.start(), "
//                    + "Sub.start(), "
//                    + "Sub.interrupted(), "     // The sub get interrupted.
//                    + "Main.end()"
//                    + "]",
//                    logs);
//        });
//    }
    
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
    
    private static String exceptionWtihStacktrace(Throwable throwable) throws IOException {
        try(val stringWriter = new StringWriter();
            val printWriter  = new PrintWriter(stringWriter);) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
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
