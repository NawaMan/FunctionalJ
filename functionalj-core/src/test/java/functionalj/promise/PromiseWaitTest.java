package functionalj.promise;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.environments.AsyncRunner;
import functionalj.list.FuncList;
import functionalj.tuple.Tuple;
import lombok.val;

@SuppressWarnings("javadoc")
public class PromiseWaitTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    private void assertStrings(String message, String str, Object obj) {
        assertEquals(message, str, "" + obj);
    }
    
    @Test
    public void testWaitAWhile_complete() throws InterruptedException {
        val list   = new ArrayList<String>();
        val action = DeferAction.of(String.class)
                .use(promise -> promise.onComplete(Wait.forMilliseconds(100).orDefaultTo("Not done."), r -> list.add(r.get())))
                .start();
        
        Thread.sleep(50);
        action.complete("Complete!");
        
        assertStrings("[Complete!]", list);
    }
    
    @Test
    public void testWaitAWhile_abort() throws InterruptedException {
        val list   = new ArrayList<String>();
        val action = DeferAction.of(String.class)
                .use(promise -> promise.onComplete(Wait.forMilliseconds(50).orDefaultTo("Not done."), r -> list.add(r.get())))
                .start();
        
        Thread.sleep(100);
        action.complete("Complete!");
        
        assertStrings("[Not done.]", list);
    }
    
    @Test
    public void testWaitAWhile_neverStart() throws InterruptedException {
        val list   = new ArrayList<String>();
        DeferAction.of(String.class)
                .subscribe(Wait.forMilliseconds(50).orDefaultTo("Not done."), r -> list.add(r.get()));
        
        Thread.sleep(100);
        assertStrings("[Not done.]", list);
    }
    
    @Test
    public void testWaitAWhile_differentRunners_complete() throws InterruptedException {
        val runners = FuncList.of(
                Tuple.of("asyncRunnerOnNewThread",        AsyncRunner.onNewThread),
                Tuple.of("asyncRunnerThreadFactory",      AsyncRunner.threadFactory),
                Tuple.of("asyncRunnerCompleteableFuture", AsyncRunner.completeableFuture),
                Tuple.of("asyncRunnerExecutorService",    AsyncRunner.executorService(Executors.newSingleThreadExecutor())),
                Tuple.of("asyncRunnerThreadFactory()",    AsyncRunner.threadFactory(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable);
                    }
                }))
            );
        runners
        .forEach(tuple -> {
            val list   = new ArrayList<String>();
            val action = DeferAction.of(String.class)
                    .use(promise -> promise.onComplete(Wait.forMilliseconds(100, tuple._2()).orDefaultTo("Not done."), r -> list.add(r.get())))
                    .start();
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.complete("Complete!");
            
            assertStrings("Name: " + tuple._1(), "[Complete!]", list);
        });
    }
    
    @Test
    public void testWaitAWhile_differentRunners_abort() throws InterruptedException {
        val runners = FuncList.of(
                Tuple.of("asyncRunnerOnNewThread",        AsyncRunner.onNewThread),
                Tuple.of("asyncRunnerThreadFactory",      AsyncRunner.threadFactory),
                Tuple.of("asyncRunnerCompleteableFuture", AsyncRunner.completeableFuture),
                Tuple.of("asyncRunnerExecutorService",    AsyncRunner.executorService(Executors.newSingleThreadExecutor())),
                Tuple.of("asyncRunnerThreadFactory()",    AsyncRunner.threadFactory(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable);
                    }
                }))
            );
        runners
        .forEach(tuple -> {
            val list   = new ArrayList<String>();
            val action = DeferAction.of(String.class)
                    .use(promise -> promise.onComplete(Wait.forMilliseconds(50, tuple._2()).orDefaultTo("Not done."), r -> list.add(r.get())))
                    .start();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.complete("Complete!");
            
            assertStrings("Name: " + tuple._1(), "[Not done.]", list);
        });
    }
    
    @Test
    public void testWaitAWhile_interrupt() throws InterruptedException {
        val threadRef = new AtomicReference<Thread>();
        val list      = new ArrayList<String>();
        val action    = DeferAction.of(String.class)
                .use(promise -> {
                        Wait wait = Wait
                            .forMilliseconds(150, runnable-> {
                                val thread = new Thread(runnable);
                                threadRef.set(thread);
                                thread.start();
                            })
                            .orDefaultTo("Not done.");
                        promise
                        .onComplete(wait, r -> list.add(r.get()));
                })
                .start();
        
        Thread.sleep(50);
        threadRef.get().interrupt();
        
        Thread.sleep(50);
        action.complete("Complete!");
        
        assertStrings("[Not done.]", list);
        // The abort should be initiated at 150 but that was interrupted at 50.
    }
    
    @Test
    public void testWaitAWhile_interrupt_late() throws InterruptedException {
        val threadRef = new AtomicReference<Thread>();
        val list      = new ArrayList<String>();
        val action    = DeferAction.of(String.class)
                .use(promise -> {
                        Wait wait = Wait
                            .forMilliseconds(150, runnable-> {
                                val thread = new Thread(runnable);
                                threadRef.set(thread);
                                thread.start();;
                            })
                            .orDefaultTo("Not done.");
                        promise
                        .onComplete(wait, r -> list.add(r.get()));
                })
                .start();
        
        Thread.sleep(50);
        action.complete("Complete!");
        
        Thread.sleep(100);
        threadRef.get().interrupt();
        
        assertStrings("[Complete!]", list);
    }
    
}
