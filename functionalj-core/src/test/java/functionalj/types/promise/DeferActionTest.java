package functionalj.types.promise;

import static functionalj.types.promise.DeferAction.run;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
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
                Thread.sleep(200);
                return "Hello";
            })
            .getResult();
        
        val end = System.currentTimeMillis();
        log.add("End: " + (10*((end - start) / 10)));
        log.add("Result: " + result);
        
        assertStrings("[Start: 0, End: 200, Result: Result:{ Value: Hello }]", log);
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
    public void testDeferAction_lifeCycle() throws InterruptedException {
        // NOTE: This test demonstrates that it is possible to detect the phrase and thread that the task run on.
        //       This ability is important to allow control over the async operations.
        val log   = new ArrayList<String>();
        val latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferAction.run(()->{
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }, OnStart.run(()->{
            log.add("... onStart ...");
        }))
        .subscribe(result -> {
            Thread.sleep(100);
            log.add("Done: " + result);
            latch.countDown();
        })
        .eavesdrop(result -> {
            Thread.sleep(100);
            log.add("Done2: " + result);
            latch.countDown();
        })
        .getResult();
        // getResult is just a subscription so if it run before the above subscription,
        //    the test will ends before the above subscription got to run.
        latch.await();
        
        assertStrings("["
        		+ "Init ..., "
        		+ "... onStart ..., "
        		+ "Running ..., "
        		+ "Done2: Result:{ Value: Hello }, "
        		+ "Done: Result:{ Value: Hello }"
        		+ "]", log);
    }
    
    @Test
    public void testDeferAction_lifeCycle_exceptionSafe() throws InterruptedException {
        // NOTE: This test demonstrates that it is possible to detect the phrase and thread that the task run on.
        //       This ability is important to allow control over the async operations.
        val log   = new ArrayList<String>();
        val latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferAction.run(()->{
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }, OnStart.run(()->{
            log.add("... onStart ...");
        }))
        .subscribe(result -> {
            log.add("Done: " + result);
            latch.countDown();
            throw new Exception();
        })
        .eavesdrop(result -> {
            log.add("Done2: " + result);
            latch.countDown();
            throw new Exception();
        })
        .getResult();
        latch.await();
        
        assertStrings("["
        		+ "Init ..., "
        		+ "... onStart ..., "
        		+ "Running ..., "
        		+ "Done2: Result:{ Value: Hello }, "
        		+ "Done: Result:{ Value: Hello }"
        		+ "]", log);
    }
    
    @Test
    public void testDeferAction_sameThread() throws InterruptedException {
        // NOTE: It also demonstrates that onStart, task and notification are run on the same thread.
        val log   = new ArrayList<String>();
        val latch = new CountDownLatch(4);
        val initThread      = new AtomicReference<String>(Thread.currentThread().toString());
        val onStartThread   = new AtomicReference<String>();
        val onRunningThread = new AtomicReference<String>();
        val onDoneThread    = new AtomicReference<String>();
        val onDone2Thread   = new AtomicReference<String>();
        log.add("Init ...");
        DeferAction.run(()->{
            Thread.sleep(100);
            log.add("Running ...");
            onRunningThread.set(Thread.currentThread().toString());
            latch.countDown();
            return "Hello";
        }, OnStart.run(()->{
            Thread.sleep(100);
            log.add("... onStart ...");
            onStartThread.set(Thread.currentThread().toString());
            latch.countDown();
        }))
        .subscribe(result -> {
            Thread.sleep(100);
            log.add("Done: " + result);
            onDoneThread.set(Thread.currentThread().toString());
            latch.countDown();
        })
        .eavesdrop(result -> {
            Thread.sleep(100);
            log.add("Done2: " + result);
            onDone2Thread.set(Thread.currentThread().toString());
            latch.countDown();
        })
        .getResult();
        latch.await();
        
        assertStrings("["
        		+ "Init ..., "
        		+ "... onStart ..., "
        		+ "Running ..., "
        		+ "Done2: Result:{ Value: Hello }, "
        		+ "Done: Result:{ Value: Hello }"
        		+ "]", log);
        // Run on different thread.
        assertFalse(initThread.get().equals(onRunningThread.get()));
        // The rest are on the same thread.
        assertStrings(onStartThread.get(), onRunningThread.get());
        assertStrings(onStartThread.get(), onDoneThread.get());
        assertStrings(onStartThread.get(), onDone2Thread.get());
    }
    
    @Test
    public void testDeferAction_chain() throws InterruptedException {
        val log = new ArrayList<String>();
        val runningThread = new AtomicReference<String>();
        log.add("Init #0...");
        DeferAction.run(()->{
            Thread.sleep(100);
            log.add("Running #1...");
            return "Hello";
        }, OnStart.run(()->{
            log.add("Start #1 ...");
            runningThread.set(Thread.currentThread().toString());
        }))
        .chain(str -> {
            return DeferAction.run(()->{
                Thread.sleep(100);
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
        
        assertEquals("["
                + "Init #0..., "
                + "Start #1 ..., "
                + "Running #1..., "
                +   "Start #2 ..., "
                +   "Running #2..., "
                + "Done #1: Result:{ Value: 5 }"
                + "]",
                log.toString());
    }
    
    @Test
    public void testDeferAction_moreChain() throws InterruptedException {
        val log = new ArrayList<String>();
        DeferAction.from(()->{
            Thread.sleep(50);
            return "Hello";
        }, OnStart.run(()->{
        	log.add("Acion 1 started.");
        }))
        .eavesdrop(result -> {
            log.add("Eavesdrop: " + result.isCancelled());
            log.add("Eavesdrop: " + result.toString());
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
        })
        .start()
        .getResult();
        
        Thread.sleep(50);
        
        assertStrings("["
        		+ "Acion 1 started., "
        		+ "Eavesdrop: false, "
        		+ "Eavesdrop: Result:{ Value: Hello }, "
        		+ "Done: Result:{ Value: Total=47 }"
        		+ "]", log);
    }
    
    @Test
    public void testDeferAction_moreChainAbort() throws InterruptedException {
        val log = new ArrayList<String>();
        DeferAction.from(()->{
            Thread.sleep(50);
            return "Hello";
        })
        .eavesdrop(result -> {
            log.add("Eavesdrop: " + result.isCancelled());
            log.add("Eavesdrop: " + result.toString());
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
        })
        .start()
        .abort();
        
        assertStrings("["
        		+ "Eavesdrop: true, "
        		+ "Eavesdrop: Result:{ Exception: functionalj.types.result.ResultCancelledException }, "
        		+ "Done: Result:{ Exception: functionalj.types.result.ResultCancelledException }"
        		+ "]", log);
    }
}
