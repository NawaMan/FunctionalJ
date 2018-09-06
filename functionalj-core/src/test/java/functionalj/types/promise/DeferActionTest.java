package functionalj.types.promise;

import static functionalj.types.promise.DeferAction.run;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.types.result.OnStart;
import lombok.val;

@SuppressWarnings("javadoc")
public class DeferActionTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testDeferAction() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(()->{
            Thread.sleep(100);
            return "Hello";
        })
        .subscribe(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (100*((end - start) / 100)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(150);
        assertStrings("[Start: 0, End: 100, Result: Result:{ Value: Hello }]", log);
    }
    
    @Test
    public void testDeferAction_abort() throws InterruptedException {
        val deferAction = DeferAction.from(()->{
            Thread.sleep(200);
            return "Hello";
        });
        
        val start  = System.currentTimeMillis();
        val endRef = new AtomicLong();
        val action = deferAction.subscribe(result -> {
            val end = System.currentTimeMillis();
            endRef.set(end - start);
        })
        .start();
        
        Thread.sleep(50);
        action.abort();
        
        assertTrue(endRef.get() < 100);
        assertStrings("Result:{ Exception: functionalj.types.result.ResultCancelledException }", action.getResult());
    }
    
    @Test
    public void testDeferAction_exception() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(()->{
            Thread.sleep(100);
            throw new IOException("Fail hard!");
        })
        .subscribe(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (10*((end - start) / 10)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(150);
        
        assertStrings("[Start: 0, End: 100, Result: Result:{ Exception: java.io.IOException: Fail hard! }]", log);
    }
    
    @Test
    public void testGetResult() {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        val result
            = DeferAction.run(()->{
                Thread.sleep(100);
                return "Hello";
            })
            .getResult();
        
        val end = System.currentTimeMillis();
        log.add("End: " + (10*((end - start) / 10)));
        log.add("Result: " + result);
        
        assertStrings("[Start: 0, End: 100, Result: Result:{ Value: Hello }]", log);
    }
    
    @Test
    public void testGetResult_abort() {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        
        val action = run(()->{ sleep(100); return "Hello"; });
        
        try {
            action.getResult(50, TimeUnit.MILLISECONDS);
            fail("Expect an interruption.");
            
        } catch (UncheckedInterruptedException e) {
            val end = System.currentTimeMillis();
            log.add("End: " + (10*((end - start) / 10)));
            log.add("Result: " + action.getCurentResult());
            assertStrings("[Start: 0, End: 50, Result: Result:{ Exception: functionalj.types.result.ResultNotReadyException }]", log);
        }
    }
    
    @Test
    public void testGetResult_interrupt() {
        val start     = System.currentTimeMillis();
        val threadRef = new AtomicReference<Thread>();
        val action    = run(()->{ threadRef.set(Thread.currentThread()); sleep(200); return "Hello"; });
        
        new Thread(()-> {
            try { sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            threadRef.get().interrupt();
        }).start();
        
        action.getResult();
        assertTrue((System.currentTimeMillis() - start) < 100);
        assertStrings("Result:{ Exception: java.lang.InterruptedException: sleep interrupted }", action.getResult());
    }
    
    @Test
    public void testDeferAction_onStart() throws InterruptedException {
        // NOTE: This test demonstrates that it is possible to detect the phrase and thread that the task run on.
        //       This ability is important to allow control over the async operations.
        val log = new ArrayList<String>();
        val initThread      = new AtomicReference<String>(Thread.currentThread().toString());
        val onStartThread   = new AtomicReference<String>();
        val onRunningThread = new AtomicReference<String>();
        val onDoneThread    = new AtomicReference<String>();
        log.add("Init ...");
        DeferAction.run(()->{
            Thread.sleep(100);
            log.add("Running ...");
            onRunningThread.set(Thread.currentThread().toString());
            return "Hello";
        }, OnStart.run(()->{
            log.add("... onStart ...");
            onStartThread.set(Thread.currentThread().toString());
        }))
        .subscribe(result -> {
            log.add("Done: " + result);
            onDoneThread.set(Thread.currentThread().toString());
        })
        .getResult();
        
        assertStrings("[Init ..., ... onStart ..., Running ..., Done: Result:{ Value: Hello }]", log);
        assertFalse(initThread.get().equals(onRunningThread.get()));
        assertStrings(onStartThread.get(), onRunningThread.get());
        assertStrings(onStartThread.get(), onDoneThread.get());
    }
    
    @Test
    public void testDeferAction_chain() throws InterruptedException {
        val log = new ArrayList<String>();
        val runningThread = new AtomicReference<String>();
        log.add("Init #0...");
        DeferAction.run(()->{
            Thread.sleep(50);
            log.add("Running #1...");
            return "Hello";
        }, OnStart.run(()->{
            log.add("Start #1 ...");
            runningThread.set(Thread.currentThread().toString());
        }))
        .chain(str -> {
            return DeferAction.run(()->{
                Thread.sleep(50);
                log.add("Running #2...");
                return str.length();
            }, OnStart.run(()->{
                log.add("Start #2 ...");
                runningThread.set(Thread.currentThread().toString());
            }));
        })
        .subscribe(result -> {
            log.add("Done #1: " + result);
        })
        .getResult();
        
        assertStrings("["
                + "Init #0..., "
                + "Start #1 ..., "
                + "Running #1..., "
                +   "Start #2 ..., "
                +   "Running #2..., "
                + "Done #1: Result:{ Value: 5 }"
                + "]",
                log);
    }
    
    @Test
    public void testDeferAction_moreChain() throws InterruptedException {
        val log = new ArrayList<String>();
        DeferAction<String> action = DeferAction.from(()->{
            Thread.sleep(50);
            System.out.println("Hello");
            return "Hello";
        })
        .eavesdrop(result -> {
            log.add("" + result.isCancelled());
            log.add(result.toString());
        })
        .chain(str->{
            Thread.sleep(50);
            return Promise.ofValue(str.length());
        })
        .chain(length->{
            Thread.sleep(50);
            return Promise.ofValue(length + 42);
        })
        .map(value->{
            Thread.sleep(50);
            return "Total=" + value;
        })
        .subscribe(result -> {
            log.add("Done: " + result);
        });
        action
        .start()
        .getResult();
        Thread.sleep(50);
        
        assertStrings("[false, Result:{ Value: Hello }, Done: Result:{ Value: Total=47 }]", log);
    }
    
    @Test
    public void testDeferAction_moreChainAbort() throws InterruptedException {
        val log = new ArrayList<String>();
        DeferAction<String> p1 = DeferAction.from(()->{
            Thread.sleep(50);
            return "Hello";
        });
        DeferAction<String> p2 = p1
        .eavesdrop(result -> {
            // TODO - This is not good. This eavesdrop should be called.
            log.add("" + result.isCancelled());
            log.add(result.toString());
        });
        DeferAction<Integer> p3 = p2
        .chain(str->{
            Thread.sleep(50);
            return Promise.ofValue(str.length());
        });
        DeferAction<Integer> beforeMap = p3
        .chain(length->{
            Thread.sleep(50);
            return Promise.ofValue(length + 42);
        });
        DeferAction<String> afterMap = beforeMap
        .map(value->{
            Thread.sleep(50);
            return "Total=" + value;
        });
        DeferAction<String> action = afterMap
        .subscribe(result -> {
            log.add("Done: " + result);
        });
        action
        .start()
        .abort();
        
        assertStrings("[Done: Result:{ Exception: functionalj.types.result.ResultCancelledException }]", log);
    }
}
