//package functionalj.promise;
//
//import static functionalj.TestHelper.assertAsString;
//import static java.util.stream.Collectors.toList;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//
//import functionalj.environments.Env;
//import functionalj.promise.PromiseCancelTest.Body;
//import functionalj.ref.Run;
//import lombok.val;
//
//public class PromiseScopeTest {
//	
//	@Test
//	public void testAgain() throws Exception {
//        val logs = new ArrayList<String>();
//        ensureThreadCleanup(() -> {
//        	
//            val subAction = DeferAction.<String>from(() -> {
//                logs.add("Sub.start()");
//                Thread.sleep(50);
//                logs.add("Sub.end()");
//                return "Hello there!";
//            })
//            .onCompleted(result -> {
//                logs.add("Sub completed");
//            });
//        	
//
//            Run.with(ActionAsyncRunner.asyncScopeProvider.butWith(AsyncRunnerScopeProvider.nested)).run(() -> {
//            val mainAction = DeferAction.<String>from(() -> {
//                logs.add("Main.start()");
//                Thread.sleep(50);
//
//            	subAction.start();
//                
//                Thread.sleep(10);
//                logs.add("Main.end()");
//                
//                // Ended before sub-action finish
//                return "Hello World!";
//            });
//            
//            mainAction.start();
//            val result = mainAction.getResult();
//            System.out.println(result);
//
//            });
//            System.out.println();
//            logs.stream().map("  - "::concat).forEach(System.out::println);
//        });
//	}
//    
//    @Test
//    public void testScopeSuccess() throws Exception {
//        Run.with(ActionAsyncRunner.asyncScopeProvider.butWith(AsyncRunnerScopeProvider.nested)).run(() -> {
//        // Ensure that all thread are clean up.
//        ensureThreadCleanup(() -> {
//            val logs = new ArrayList<String>();
//            // Normally, a started defer action start and complete operation.
//            
//            val mainAction = DeferAction.<String>from(() -> {
//                logs.add("Main.start()");
//                Thread.sleep(50);
//                
//                val subAction = DeferAction.<String>from(() -> {
//                    logs.add("Sub.start()");
//                    Thread.sleep(50);
//                    logs.add("Sub.end()");
//                    return "Hello there!";
//                })
//                .onCompleted(result -> {
//                    logs.add("Sub completed");
//                })
//                .start();
//                
//                // Call `getResult()` ... wait to finish.
//                subAction.getResult();
//                
//                Thread.sleep(10);
//                logs.add("Main.end()");
//                
//                // Ended before sub-action finish
//                return "Hello World!";
//            })
//            .start();
//            // Call `getResult()` --  wait for the action.
//            val result = mainAction.getResult();
//            
//            // Wait for result ... which mean also wait for the result above as well.
//            assertAsString("Result:{ Value: Hello World! }", result);
//            assertAsString("["
//                    + "Main.start(), "
//                    + "Sub.start(), "
//                    + "Sub.end(), "
//                    + "Sub completed, "
//                    + "Main.end()"
//                    + "]",
//                    logs); 
//        	});
//        });
//    }
//    
//    @Test
//    public void testScopeSuccess_noWaitForSub() throws Exception {
//        val threadFactory = new ThreadFactory() {
//            private final AtomicInteger ID = new AtomicInteger(0);
//            @Override
//            public Thread newThread(Runnable r) {
//                val currentThread = Thread.currentThread();
//                val id = ID.getAndIncrement();
//                return new Thread(r, "Thread::" + currentThread + "::" + id) {
//                    @Override
//                    public String toString() {
//                        return "Thread::" + currentThread + "::" + id;
//                    }
//                };
//            }
//        };
//        
//        Run
//        .with(Env.refs.async.butWith(AsyncRunner.threadFactory(threadFactory)))
//        .and(ActionAsyncRunner.asyncScopeProvider.butWith(AsyncRunnerScopeProvider.nested))
//        .run(() -> {
//        
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
//                Thread.sleep(10);
//                
//                // But do not wait for the sub to finish.
//                logs.add("Main.end()");
//                return "Hello World!";
//            })
//            .start();
//            
//            Thread.sleep(50);
//            
//            // Wait for the main to finish
//            val result = mainAction.getResult();
//            
//            assertAsString("Result:{ Value: Hello World! }", result);
//            
//            Thread.sleep(10);
//            
//            assertAsString(
//                    "["
//                    + "Main.start(), "
//                    + "Sub.start(), "
//                    + "Main.end(), "
//                    + "Sub.interrupted()"     // The sub get interrupted.
//                    + "]",
//                    logs);
//        });
//        
//        });
//    }
//    
//    @Test
//    public void testScopeSuccess_noWaitForSubSub() throws Exception {
//        val threadFactory = new ThreadFactory() {
//            private final AtomicInteger ID = new AtomicInteger(0);
//            @Override
//            public Thread newThread(Runnable r) {
//                val currentThread = Thread.currentThread();
//                val id = ID.getAndIncrement();
//                return new Thread(r, "Thread::" + currentThread + "::" + id) {
//                    @Override
//                    public String toString() {
//                        return "Thread::" + currentThread + "::" + id;
//                    }
//                };
//            }
//        };
//        
//        Run
//        .with(Env.refs.async.butWith(AsyncRunner.threadFactory(threadFactory)))
//        .and(ActionAsyncRunner.asyncScopeProvider.butWith(AsyncRunnerScopeProvider.nested))
//        .run(() -> {
//        
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
//                        
//                        // Start the sub sub
//                        DeferAction.<String>from(() -> {
//                            try {
//                                logs.add("SubSub.start()");
//                                Thread.sleep(100000);
//                                logs.add("SubSub.end()");
//                                return "Hello there!";
//                            } catch (InterruptedException e) {
//                                logs.add("SubSub.interrupted()");
//                                throw e;
//                            }
//                        })
//                        .start()
//                        .getResult();
//                        
//                        logs.add("Sub.end()");
//                        return "Hello there!";
//                    } catch (UncheckedInterruptedException e) {
//                        logs.add("Sub.interrupted()");
//                        throw e;
//                    }
//                })
//                .start();
//                
//                Thread.sleep(50);
//                
//                // But do not wait for the sub to finish.
//                logs.add("Main.end()");
//                return "Hello World!";
//            })
//            .start();
//            
//            Thread.sleep(50);
//            
//            // Wait for the main to finish
//            val result = mainAction.getResult();
//            
//            assertAsString("Result:{ Value: Hello World! }", result);
//            
//            Thread.sleep(10);
//            
//            assertAsString(
//                    "["
//                    + "Main.start(), "
//                    + "Sub.start(), "
//                    + "SubSub.start(), "
//                    + "Main.end(), "
//                    + "Sub.end(), "
//                    + "SubSub.interrupted()"     // The sub get interrupted.
//                    + "]",
//                    logs);
//        });
//        
//        });
//    }
//    
//    private void ensureThreadCleanup(Body body) throws Exception {
//        int startActiveThreads = Thread.activeCount();
//        val beforeThreads      = currentThreads(startActiveThreads);
//        
//        try {
//            body.run();
//        } finally {
//            if (startActiveThreads < Thread.activeCount()) {
//                val afterThreads = currentThreads(startActiveThreads);
//                assertEquals(beforeThreads, afterThreads);
//            }
//        }
//    }
//    
//    private String currentThreads(int startActiveThreads) {
//        val logs    = new ArrayList<String>();
//        val threads = new Thread[startActiveThreads];
//        // Get all active threads in the current thread group
//        int actualThreads = Thread.enumerate(threads);
//        
//        logs.add("Number of active threads: " + actualThreads + "\n");
//        for (int i = 0; i < actualThreads; i++) {
//            logs.add("Thread " + i + ": " + threads[i].getName() + "\n");
//        }
//        return logs.stream().collect(Collectors.joining());
//    }
//    
//}
