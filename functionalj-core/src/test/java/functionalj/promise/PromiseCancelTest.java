package functionalj.promise;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;

public class PromiseCancelTest {
    
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
    
    @Test
    public void testException_noSubscriptionNorCancel() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                throw new RuntimeException("Stop!");
            });
            val action = deferAction.start();
            
            val result = action.getResult();
            assertAsString("Result:{ Exception: java.lang.RuntimeException: Stop! }", result);
            
            assertAsString(
                      "java.lang.RuntimeException: Stop!\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E[a-zA-Z0-9$]+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result.getException()));
        });
    }
    
    @Test
    public void testCancel() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            });
            val action = deferAction.start();
            action.cancel();
            
            val result = action.getResult();
            assertAsString("Result:{ Cancelled }",                        result);
            assertAsString("functionalj.result.ResultCancelledException", result.getException());
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E[a-zA-Z0-9$]+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testCancel(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result.getException()));
        });
    }
    
    @Test
    public void testUnsubscribe() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val logs = new ArrayList<String>();
            
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            });
            val action       = deferAction.start();
            val subscription = action.subscribe(result -> {
                logs.add(result);
            });
            subscription.cancel();
            
            val result = action.getResult();
            assertAsString("Result:{ Cancelled }", result);
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E[a-zA-Z0-9$]+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testUnsubscribe(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result.getException()));
            
            assertAsString("[]", logs);
        });
    }
    
    @Test
    public void testUnsubscribe_cancel()  throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            });
            val action = deferAction.start();
            
            action.cancel();
            
            val result = action.getResult();
            assertAsString("Result:{ Cancelled }", result);
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testUnsubscribe_cancel(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result.getException()));
        });
    }
    
    @Test
    public void testSuccess_map() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello World!";
            })
            .map($S.concat("!!"))
            .map($S.replaceAll("World", "there"));
            val action = deferAction.start();
            
            val result = action.getResult();
            assertAsString("Result:{ Value: Hello there!!! }", result);
        });
    }
    
    @Test
    public void testCancel_map() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val deferAction = DeferAction.<String>from(() -> {
                Thread.sleep(50);
                return "Hello World!";
            })
            .map($S.concat("!!"))
            .map($S.replaceAll("World", "there"));
            val action = deferAction.start();
            
            action.cancel();
            
            val result = action.getResult();
            assertAsString("Result:{ Cancelled }", result);
        });
    }
    
    @Test
    public void testMap_middleCancel() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val action = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            }).start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            
            action.cancel();
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            assertAsString("Result:{ Cancelled }", result);
            assertAsString("Result:{ Cancelled }", result1);
            assertAsString("Result:{ Cancelled }", result2);
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result.getException()));
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result1.getException()));
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result2.getException()));
        });
    }
    
    @Test
    public void testMap_middleCancel1() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val action = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            }).start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            
            action1.cancel();
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            assertAsString("Result:{ Cancelled: No more listener. }", result);
            assertAsString("Result:{ Cancelled }",                    result1);
            assertAsString("Result:{ Cancelled }",                    result2);
            
            assertAsString(
            		"functionalj.result.ResultCancelledException: No more listener.",
            		result.getException());
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel1(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result1.getException()));
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel1(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result2.getException()));
        });
    }
    
    @Test
    public void testMap_middleCancel2() throws Exception {
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            // Normally, a started defer action start and complete operation.
            
            val action = DeferAction.<String>from(() -> {
                Thread.sleep(1000);
                return "Hello World!";
            }).start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            
            action2.cancel();
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            assertAsString("Result:{ Cancelled: No more listener. }", result);
            assertAsString("Result:{ Cancelled: No more listener. }", result1);
            assertAsString("Result:{ Cancelled }",                    result2);
            
            assertAsString(
            		"functionalj.result.ResultCancelledException: No more listener.", 
            		result.getException());
            
            assertAsString(
            		"functionalj.result.ResultCancelledException: No more listener.",
            		result1.getException());
            
            assertAsString(
                    "functionalj.result.ResultCancelledException\n"
                    + "\tat functionalj.result.Result.ofCancelled(Result.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.Promise.cancel(Promise.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.UncompletedAction.cancel(UncompletedAction.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.lambda$\\E.+\\Q(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.ensureThreadCleanup(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\tat functionalj.promise.PromiseCancelTest.testMap_middleCancel2(PromiseCancelTest.java:\\E[0-9]+\\Q)\n"
                    + "\\E.*\\Q",
                    exceptionWtihStacktrace(result2.getException()));
        });
    }
    
    @Test
    public void testMap_cleanUp_success() throws Exception {
        int startValue    =   42;
        int subStartValue =    0;
        int subEndValue   =   -1;
        int cleanUpValue  = 1024;
        
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val intValue = new AtomicInteger(startValue);
            val logMsgs  = new ArrayList<String>();
            
            val action = DeferAction.<String>from(() -> {
                intValue.set(subStartValue);
                logMsgs.add("Started");
                
                Thread.sleep(1000);
                
                intValue.set(subEndValue);
                logMsgs.add("Ended");
                return "Hello World!";
            })
            .onCompleted(__ -> {
                intValue.set(cleanUpValue);
                logMsgs.add("Clean up");
            })
            .start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            
            assertAsString("Result:{ Value: Hello World! }",   result);
            assertAsString("Result:{ Value: Hello World!!! }", result1);
            assertAsString("Result:{ Value: Hello there!!! }", result2);
            
            // The value of the clean up.
            assertAsString(cleanUpValue + "", intValue);
            assertAsString(
                      "[Started, Ended, Clean up]",
                    logMsgs);
        });
    }
    
    @Test
    public void testMap_cleanUp_cancelled() throws Exception {
        int startValue    =   42;
        int subStartValue =    0;
        int subEndValue   =   -1;
        int cleanUpValue  = 1024;
        
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val intValue = new AtomicInteger(startValue);
            val logMsgs  = new ArrayList<String>();
            
            val action = DeferAction.<String>from(() -> {
                intValue.set(subStartValue);
                logMsgs.add("Started");
                
                Thread.sleep(1000);
                
                intValue.set(subEndValue);
                logMsgs.add("Ended");
                return "Hello World!";
            })
            .onCompleted(__ -> {
                intValue.set(cleanUpValue);
                logMsgs.add("Clean up");
            })
            .start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            action.cancel();
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            
            assertAsString("Result:{ Cancelled }", result);
            assertAsString("Result:{ Cancelled }", result1);
            assertAsString("Result:{ Cancelled }", result2);
            
            // The value is from the clean up.
            assertAsString(cleanUpValue + "", intValue);
            assertAsString("[Started, Clean up]", logMsgs);
        });
    }
    
    @Test
    public void testMap_cleanUp_cancelled1() throws Exception {
        int startValue    =   42;
        int subStartValue =    0;
        int subEndValue   =   -1;
        int cleanUpValue  = 1024;
        
        // Ensure that all thread are clean up.
        ensureThreadCleanup(() -> {
            val intValue = new AtomicInteger(startValue);
            val logMsgs  = new ArrayList<String>();
            
            val action = DeferAction.<String>from(() -> {
                intValue.set(subStartValue);
                logMsgs.add("Started");
                
                Thread.sleep(1000);
                
                intValue.set(subEndValue);
                logMsgs.add("Ended");
                return "Hello World!";
            })
            .onCompleted(__ -> {
                intValue.set(cleanUpValue);
                logMsgs.add("Clean up");
            })
            .start();
            
            val action1 = action .map($S.concat("!!"));
            val action2 = action1.map($S.replaceAll("World", "there"));
            
            action1.cancel();
            
            val result  = action.getResult();
            val result1 = action1.getResult();
            val result2 = action2.getResult();
            
            assertAsString("Result:{ Value: Hello World! }", result);
            assertAsString("Result:{ Cancelled }", result1);
            assertAsString("Result:{ Cancelled }", result2);
            
            // The value is from the clean up.
            assertAsString(cleanUpValue + "", intValue);
            assertAsString("[Started, Ended, Clean up]", logMsgs);
        });
    }
    
    private static String exceptionWtihStacktrace(Throwable throwable) throws IOException {
        try(val stringWriter = new StringWriter();
            val printWriter  = new PrintWriter(stringWriter);) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
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
