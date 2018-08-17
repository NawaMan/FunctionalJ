package functionalj.types.promise;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class PromiseWaitTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testWaitAWhile_complete() throws InterruptedException {
        val list   = new ArrayList<String>();
        val action = DeferAction.of(String.class)
                .use(promise -> promise.subscribe(Wait.forMilliseconds(100).orDefaultTo("Not done."), r -> list.add(r.get())))
                .start();
        
        Thread.sleep(50);
        action.complete("Complete!");
        
        assertStrings("[Complete!]", list);
    }
    
    @Test
    public void testWaitAWhile_abort() throws InterruptedException {
        val list   = new ArrayList<String>();
        val action = DeferAction.of(String.class)
                .use(promise -> promise.subscribe(Wait.forMilliseconds(50).orDefaultTo("Not done."), r -> list.add(r.get())))
                .start();
        
        Thread.sleep(100);
        action.complete("Complete!");
        
        assertStrings("[Not done.]", list);
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
                                return thread;
                            })
                            .orDefaultTo("Not done.");
                        promise
                        .subscribe(wait, r -> list.add(r.get()));
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
                                return thread;
                            })
                            .orDefaultTo("Not done.");
                        promise
                        .subscribe(wait, r -> list.add(r.get()));
                })
                .start();
        
        Thread.sleep(50);
        action.complete("Complete!");
        
        Thread.sleep(100);
        threadRef.get().interrupt();
        
        assertStrings("[Complete!]", list);
    }
    
}
