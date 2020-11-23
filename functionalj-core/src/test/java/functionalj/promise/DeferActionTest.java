// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.promise;

import static functionalj.function.Func.f;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.theInteger;
import static functionalj.promise.DeferAction.run;
import static functionalj.ref.Run.With;
import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.environments.AsyncRunner;
import functionalj.environments.Console;
import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.functions.TimeFuncs;
import functionalj.list.FuncList;
import functionalj.ref.Run;
import functionalj.ref.Substitution;
import functionalj.streamable.Streamable;


public class DeferActionTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testLazyStart() throws InterruptedException {
        var action = DeferAction.from(()->40)
                .map(i -> i + 1);
        
        var promise = action.getPromise();
        assertEquals("Result:{ NotReady }", action .getCurrentResult().toString());
        assertEquals("Result:{ NotReady }", promise.getCurrentResult().toString());
        
        var add1 = theInteger.plus(1);
        var answer = add1.applyTo(promise);
        assertEquals("Result:{ NotReady }", action .getCurrentResult().toString());
        assertEquals("Result:{ NotReady }", promise.getCurrentResult().toString());
        
        answer.start();
        Thread.sleep(100);
        assertEquals("Result:{ Value: 41 }", action .getCurrentResult().toString());
        assertEquals("Result:{ Value: 41 }", promise.getCurrentResult().toString());
        
        assertEquals("Result:{ Value: 42 }", answer.getResult().toString());
    }
    
    @Test
    public void testDeferAction() throws InterruptedException {
        var log = new ArrayList<String>();
        var start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(()->{
            Thread.sleep(100);
            return "Hello";
        })
        .onComplete(result -> {
            var end = System.currentTimeMillis();
            log.add("End: " + (100*((end - start) / 100)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(150);
        assertStrings("[Start: 0, End: 100, Result: Result:{ Value: Hello }]", log);
    }
    
    @Test
    public void testDeferAction_abort() throws InterruptedException {
        var deferAction = DeferAction.from(()->{
            Thread.sleep(200);
            return "Hello";
        });
        
        var start  = System.currentTimeMillis();
        var endRef = new AtomicLong();
        var action = deferAction.onComplete(result -> {
            var end = System.currentTimeMillis();
            endRef.set(end - start);
        })
        .start();
        
        Thread.sleep(50);
        action.abort();
        
        assertTrue(endRef.get() < 150);
        assertStrings("Result:{ Cancelled }", action.getResult());
    }
    
    @Test
    public void testDeferAction_exception() throws InterruptedException {
        var log   = new ArrayList<String>();
        var endRef = new AtomicInteger();
        var latch = new CountDownLatch(2);
        var start = System.currentTimeMillis();
        DeferAction.run(()->{
            Thread.sleep(100);
            latch.countDown();
            throw new IOException("Fail hard!");
        })
        .onComplete(result -> {
            var end = System.currentTimeMillis();
            endRef.set((int)(20*((end - start) / 20)));
            
            log.add("Result: " + result);
            latch.countDown();
        });
        
        latch.await();
        
        assertTrue(endRef.get() >= 100);
        assertStrings("[Result: Result:{ Exception: java.io.IOException: Fail hard! }]", log);
    }
    
    @Test
    public void testGetResult() {
        var log = new ArrayList<String>();
        var start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        var result
            = DeferAction.run(()->{
                Thread.sleep(200);
                return "Hello";
            })
            .getResult();
        
        var end = System.currentTimeMillis();
        log.add("End: " + (100*((end - start) / 100)));
        log.add("Result: " + result);
        
        assertEquals("[Start: 0, End: 200, Result: Result:{ Value: Hello }]", log.toString());
    }
    
    @Test
    public void testGetResult_abort() {
        var log = new ArrayList<String>();
        var start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        
        var action = run(Sleep(1000).thenReturn("Hello"));
        
        try {
            action.getResult(500, TimeUnit.MILLISECONDS);
            fail("Expect an interruption.");
            
        } catch (UncheckedInterruptedException e) {
            var end = System.currentTimeMillis();
            log.add("End: " + (100*((end - start) / 100)));
            log.add("Result: " + action.getCurrentResult());
            assertStrings("[Start: 0, End: 500, Result: Result:{ NotReady }]", log);
        }
    }
    
    @Test
    public void testGetResult_interrupt() {
        var start     = System.currentTimeMillis();
        var threadRef = new AtomicReference<Thread>();
        var action    = run(()->{ threadRef.set(Thread.currentThread()); sleep(200); return "Hello"; });
        
        new Thread(()-> {
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            threadRef.get().interrupt();
        }).start();
        
        action.getResult();
        assertTrue((System.currentTimeMillis() - start) < 150);
        assertStrings("Result:{ Exception: java.lang.InterruptedException: sleep interrupted }", action.getResult());
    }
    
    @Test
    public void testDeferAction_lifeCycle() throws InterruptedException {
        // NOTE: This test demonstrates that it is possible to detect the phrase and thread that the task run on.
        //       This ability is important to allow control over the async operations.
        var log   = new ArrayList<String>();
        var latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferActionBuilder.from(()->{
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }).onStart(()->{
            log.add("... onStart ...");
        })
        .build()
        .onComplete(result -> {
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
        var log   = new ArrayList<String>();
        var latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferActionBuilder.from(()->{
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }).onStart(()->{
            log.add("... onStart ...");
        })
        .build()
        .onComplete(result -> {
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
        var log   = new ArrayList<String>();
        var latch = new CountDownLatch(4);
        var initThread      = new AtomicReference<String>(Thread.currentThread().toString());
        var onStartThread   = new AtomicReference<String>();
        var onRunningThread = new AtomicReference<String>();
        var onDoneThread    = new AtomicReference<String>();
        var onDone2Thread   = new AtomicReference<String>();
        log.add("Init ...");
        DeferActionBuilder.from(()->{
            Thread.sleep(100);
            log.add("Running ...");
            onRunningThread.set(Thread.currentThread().toString());
            latch.countDown();
            return "Hello";
        }).onStart(()->{
            Thread.sleep(100);
            log.add("... onStart ...");
            onStartThread.set(Thread.currentThread().toString());
            latch.countDown();
        })
        .build()
        .onComplete(result -> {
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
        var log = new ArrayList<String>();
        var latch = new CountDownLatch(5);
        var runningThread = new AtomicReference<String>();
        log.add("Init #0...");
        DeferActionBuilder.from(()->{
            Thread.sleep(100);
            log.add("Running #1...");
            latch.countDown();
            return "Hello";
        }).onStart(()->{
            log.add("Start #1 ...");
            runningThread.set(Thread.currentThread().toString());
            latch.countDown();
        })
        .build()
        .chain(str -> {
            return DeferActionBuilder.from(()->{
                Thread.sleep(100);
                log.add("Running #2...");
                latch.countDown();
                return str.length();
            }).onStart(()->{
                log.add("Start #2 ...");
                runningThread.set(Thread.currentThread().toString());
                latch.countDown();
            })
            .build()
            .start();
        })
        .onComplete(result -> {
            log.add("Done #1: " + result);
            latch.countDown();
        })
        .getResult();
        latch.await();
        
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
        var log = new ArrayList<String>();
        DeferActionBuilder.from(()->{
            Thread.sleep(50);
            return "Hello";
        })
        .onStart(()->{
            log.add("Acion 1 started.");
        })
        .build()
        .eavesdrop(result -> {
            log.add("Eavesdrop: " + result.isCancelled());
            log.add("Eavesdrop: " + result.toString());
        })
        .chain(str->{
            Thread.sleep(50);
            return Promise.of(str.length());
        })
        .chain(length->{
            Thread.sleep(50);
            return Promise.of(length + 42);
        })
        .map(value->{
            Thread.sleep(50);
            return "Total=" + value;
        })
        .onComplete(result -> {
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
        var log = new ArrayList<String>();
        var latch = new CountDownLatch(1);
        DeferAction.from(()->{
            Thread.sleep(50);
            return "Hello";
        })
        .eavesdrop(result -> {
            log.add("Eavesdrop: " + result.isCancelled());
            log.add("Eavesdrop: " + result.toString());
            latch.countDown();
        })
        .chain(str->{
            Thread.sleep(50);
            return Promise.of(str.length());
        })
        .chain(length->{
            Thread.sleep(50);
            return Promise.of(length + 42);
        })
        .map(value->{
            Thread.sleep(50);
            return "Total=" + value;
        })
        .onComplete(result -> {
            log.add("Done: " + result);
        })
        .start()
        .abort();
        
        // Wait for all "Done" propagated.
        latch.await();
        var logString = log.toString();
        assertTrue(logString.contains("Done: Result:{ Cancelled }"));
        assertTrue(logString.contains("Eavesdrop: false, Eavesdrop: Result:{ Value: Hello }"));
    }
    
    static class LoggedCreator extends DeferActionCreator {
        private final List<String>  logs             = Collections.synchronizedList(new ArrayList<String>());
        private final AtomicInteger deferActionCount = new AtomicInteger(0);
        private final AsyncRunner   runner;
        public LoggedCreator() {
            this(null);
        }
        public LoggedCreator(AsyncRunner runner) {
            this.runner = runner;
        }
        @Override
        public <D> DeferAction<D> create(Func0<D> supplier, Runnable onStart, boolean interruptOnCancel, AsyncRunner runner) {
            var id = deferActionCount.getAndIncrement();
            logs.add("New defer action: " + id);
            var wrappedSupplier = (Func0<D>)()->{
                Thread.sleep(100);
                logs.add("Start #" + id + ": ");
                
                D result = null;
                try {
                    result = supplier.get();
                    return result;
                } finally {
                    logs.add("End #" + id + ": " + result);
                }
            };
            var theRunner = (this.runner != null) ? this.runner : runner;
            return DeferActionCreator.instance.create(wrappedSupplier, onStart, interruptOnCancel, theRunner);
        }
        public List<String> logs() {
            return FuncList.from(logs);
        }
    }
    
    @Test
    public void testCreator() throws InterruptedException {
        var creator = new LoggedCreator();
        Run.with(DeferActionCreator.current.butWith(creator))
        .run(()->{
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
        });
        
        assertStrings("["
                + "New defer action: 0, Start #0: , End #0: null, "
                + "New defer action: 1, Start #1: , End #1: null, "
                + "New defer action: 2, Start #2: , End #2: null"
                + "]", creator.logs);
    }
    
    @Test
    public void testStreamAction() {
        var creator = new LoggedCreator();
        runActions(creator);
        assertNotEquals("["
                + "New defer action: 0, Start #0: , End #0: 0, "
                + "New defer action: 1, Start #1: , End #1: 1, "
                + "New defer action: 2, Start #2: , End #2: 2, "
                + "New defer action: 3, Start #3: , End #3: 3, "
                + "New defer action: 4, Start #4: , End #4: 4"
            + "]", creator.logs().toString());
        assertNotEquals("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
                + "Start #0: , End #0: 0, "
                + "Start #1: , End #1: 1, "
                + "Start #2: , End #2: 2, "
                + "Start #3: , End #3: 3, "
                + "Start #4: , End #4: 4"
            + "]", creator.logs().toString());
    }
    
    @Test
    public void testStreamAction_SingleThread() {
        var executor = Executors.newSingleThreadExecutor();
        var creator  = new LoggedCreator(runnable -> {
            executor.execute(runnable);
        });
        runActions(creator);
        assertEquals("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
                + "Start #0: , End #0: 0, "
                + "Start #1: , End #1: 2, "
                + "Start #2: , End #2: 4, "
                + "Start #3: , End #3: 6, "
                + "Start #4: , End #4: 8"
            + "]", creator.logs().toString());
    }
    
    @Test
    public void testStreamAction_TwoThreads() {
        var executor = Executors.newFixedThreadPool(2);
        var creator  = new LoggedCreator(runnable -> {
            executor.execute(runnable);
        });
        runActions(creator);
        assertNotEquals("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
                + "Start #0: , End #0: 0, "
                + "Start #1: , End #1: 2, "
                + "Start #2: , End #2: 4, "
                + "Start #3: , End #3: 6, "
                + "Start #4: , End #4: 8"
            + "]", creator.logs().toString());
        
        boolean zeroOneDone01 = creator.logs().toString().startsWith("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
            + "Start #0: , Start #1: , End #0: 0, End #1: 2, ");
        boolean oneZeroDone01 = creator.logs().toString().startsWith("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
                + "Start #1: , Start #0: , End #1: 2, End #0: 0, ");
        boolean zeroOneDone10 = creator.logs().toString().startsWith("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
            + "Start #0: , Start #1: , End #1: 2, End #0: 0, ");
        boolean oneZeroDone10 = creator.logs().toString().startsWith("["
                + "New defer action: 0, "
                + "New defer action: 1, "
                + "New defer action: 2, "
                + "New defer action: 3, "
                + "New defer action: 4, "
                + "Start #1: , Start #0: , End #0: 0, End #1: 2, ");
        if (!(zeroOneDone01 || oneZeroDone01 || zeroOneDone10 || oneZeroDone10)) {
            System.err.println(creator.logs);
        }
        assertTrue(zeroOneDone01 || oneZeroDone01 || zeroOneDone10 || oneZeroDone10);
    }
    
    private void runActions(final functionalj.promise.DeferActionTest.LoggedCreator creator) {
        var list = Run.with(DeferActionCreator.current.butWith(creator))
        .run(()->{
            var actions = FuncList
                .from(Streamable.iterate(0, i -> i + 2).limit(5))
                .map (i -> DeferAction.from(Sleep(100).thenReturn(i)))
                .toImmutableList();
            
            actions
                .forEach(DeferAction::start);
            
            var results = actions
                .map(action  -> action.getPromise())
                .map(promise -> promise.getResult())
                .map(result  -> result.orElse(null))
                .toImmutableList();
            
            actions.forEach(action -> action.abort());
            return (List<Integer>)results;
        });
        assertStrings("[0, 2, 4, 6, 8]", list);
    }
    
    @Test @Ignore
    public void testCancelableStream() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(2);
        var creator  = new LoggedCreator(runnable -> {
            executor.execute(runnable);
        });
        var startTime = System.currentTimeMillis();
        var list = Run.with(DeferActionCreator.current.butWith(creator))
        .run(()->{
            var actions = FuncList
                .from(IntStream.range(0, 5).mapToObj(Integer::valueOf))
                .map (i -> DeferAction.run(Sleep(i < 3 ? 100 : 10000).thenReturn(i)))
                .toImmutableList();
            
            var results = actions
                .map(action  -> action.getPromise())
                .map(promise -> promise.getResult())
                .map(result  -> result.orElse(null))
                .takeUntil(i -> i >= 2)
                .toImmutableList();
            
            actions.forEach(action -> action.abort());
            return (List<Integer>)results;
        });
        
        Thread.sleep(100);
        var diffTime = System.currentTimeMillis() - startTime;
        
        assertStrings("[0, 1]", list);
        assertTrue ("Taking too long ... 3 and 4 is running.", diffTime < 5000);
        assertTrue (creator.logs.contains("End #0: 0"));
        assertTrue (creator.logs.contains("End #1: 1"));
        assertFalse(creator.logs.contains("End #3: 3"));
        assertFalse(creator.logs.contains("End #4: 4"));
    }
    
    @Test
    public void testInterrupt() throws InterruptedException {
        var stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            var latch = new CountDownLatch(1);
            var subs  = Substitution.getCurrentSubstitutions();
            var startTime     = System.currentTimeMillis();
            var pendingAction = DeferAction.run(()->{
                Run.with(subs).run(()->{
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        var buffer = new ByteArrayOutputStream();
                        e.printStackTrace(new PrintStream(buffer));
                        Console.errPrintln(buffer.toString());
                    }
                    latch.countDown();
                });
            }).onComplete(result -> {
                Console.outPrintln(result);
            });
            
            Thread.sleep(50);
            pendingAction.abort();
            
            latch.await();
            var diffTime = System.currentTimeMillis() - startTime;
            
            assertTrue("Taking too long", diffTime < 1000);
        });
        assertEquals("java.lang.InterruptedException: sleep interrupted", stub.errLines().limit(1).join(", "));
    }
    
    @Test
    public void testRace() {
        var startTime = System.currentTimeMillis();
        var action1 = DeferAction.from(TimeFuncs.Sleep(60000).thenReturn("60000"));
        var action2 = DeferAction.from(TimeFuncs.Sleep(10   ).thenReturn("10"));
        assertEquals("10", DeferAction.race(action1, action2).getResult().get());
        var diffTime = System.currentTimeMillis() - startTime;
        assertTrue ("Taking too long ... interrupt not working.", diffTime < 500);
    }
    
    @Test
    public void testRace_confirmCancel() {
        var action1 = DeferAction.run(TimeFuncs.Sleep(60000).thenReturn("60000"));
        var action2 = DeferAction.run(TimeFuncs.Sleep(10   ).thenReturn("10"));
        assertEquals("10", DeferAction.race(action1, action2).getResult().get());
        assertEquals("Result:{ Cancelled }", action1.getResult().toString());
    }
    
    @Test
    public void testRace_cancelFirst() {
        var action1 = DeferAction.run(TimeFuncs.Sleep(200).thenReturn("200"));
        var action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        var action  = DeferAction.race(action1, action2);
        action2.abort();
        var result  = action.getResult();
        assertEquals("200", result.get());
    }
    
    @Test
    public void testRace_cancelAll() {
        var action1 = DeferAction.run(TimeFuncs.Sleep(200).thenReturn("200"));
        var action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        var action  = DeferAction.race(action1, action2);
        action1.abort();
        action2.abort();
        assertStrings("Result:{ Cancelled }", action1.getResult());
        assertStrings("Result:{ Cancelled }", action2.getResult());
        assertStrings("Result:{ Cancelled: Finish without non-null result. }", action.getResult());
    }
    
    @Test
    public void testRace_null() {
        var action1 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn((String)null));
        var action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        var action  = DeferAction.race(action1, action2);
        action2.abort();
        var result  = action.getResult();
        assertNull(result.get());
    }
    
    @Test
    public void testRetry_allFail() {
        var counter = new AtomicInteger(0);
        var action = DeferActionBuilder.from(()->{ counter.incrementAndGet(); return null; })
                .retry(5).times().waitFor(50L).milliseconds()
                .build()
                .start();
        assertStrings("Result:{ Cancelled: Retry exceed: 5 }", action.getResult());
        assertEquals(5, counter.get());
    }
    
    @Test
    public void testRetry_finallySuccess() {
        var counter = new AtomicInteger(0);
        var action = DeferActionBuilder.from(()->{ counter.incrementAndGet(); return counter.get() == 3 ? "Three" : null; })
                // I like fluence, but this is rediculous.
                // Let fix this later.
                .retry(5).times().waitFor(50).milliseconds()
                .build()
                .start();
        assertStrings("Result:{ Value: Three }", action.getResult());
        assertEquals(3, counter.get());
    }
    
    @Test
    public void testRetry_waitTime() {
        var counter = new AtomicInteger(0);
        var builder = DeferActionBuilder.from(()->{ counter.incrementAndGet(); return null; })
                .retry(5).times().waitFor(0L).milliseconds();
        
        var time1 = System.currentTimeMillis();
        builder.build().start().getResult();
        
        var time2 = System.currentTimeMillis();
        builder.retry(5).times().waitFor(50L).milliseconds().build().start().getResult();
        
        var time3 = System.currentTimeMillis();
        
        var diff1 = (time2 - time1)/50;
        var diff2 = (time3 - time2)/50;
        assertTrue(diff1 < 2);
        assertTrue(diff2 > 2);
    }
    
    @Test
    public void testRetry_abort() throws InterruptedException {
        var counter = new AtomicInteger(0);
        var builder = DeferActionBuilder.from(()->{ counter.incrementAndGet(); return counter.get() == 3 ? "Three" : null; })
                .retry(5).times().waitFor(50).milliseconds();
        
        var action = builder.build().start();
        
        Thread.sleep(50);
        action.abort("Can't wait.");
        
        assertStrings("Result:{ Cancelled: Can't wait. }", action.getResult());
    }
    
    @Test
    public void testDeferLoopTimes() throws InterruptedException {
        var counter = new AtomicInteger(0);
        var action  = DeferActionBuilder
                .from(()->counter.incrementAndGet())
                .loopTimes(5);
        assertStrings("Result:{ Value: 5 }", action.build().getResult());
        assertStrings("5", counter.get());
        
        assertStrings("Result:{ Value: 10 }", action.build().getResult());
    }
    
    @Test
    public void testDeferLoopCondition() throws InterruptedException {
        var counter = new AtomicInteger(0);
        var action  = DeferActionBuilder
                .from(()->counter.incrementAndGet())
                .loopUntil(result -> result.get() >= 5);
        assertStrings("Result:{ Value: 5 }", 
                        action
                        .build()
                        .getResult());
        assertStrings("5", counter.get());
    }
    
    @Test
    public void testDelayMethod() throws InterruptedException {
        var logs     = new ArrayList<String>();
        var counter1 = new AtomicInteger(0);
        var counter2 = new AtomicInteger(0);
        var action1  = DeferAction.from(()-> {                   String s = "" + (char)('A' + counter1.getAndIncrement()); logs.add(s); return s; });
        var action2  = DeferAction.from(()-> { Thread.sleep(10); String s = "" + (char)('a' + counter2.getAndIncrement()); logs.add(s); return s; });
        var concat   = f(String::concat);
        
        var result = concat.applyTo(action1.getPromise(), action2.getPromise());
        Thread.sleep(10);
        logs.add("Before getting result!");
        logs.add("Result: " + result.getResult());
        logs.add("Result: " + result.getResult());
        assertEquals(
                "Before getting result!,\n" + 
                "A,\n" + 
                "a,\n" + 
                "Result: Result:{ Value: Aa },\n" + 
                "Result: Result:{ Value: Aa }",
                logs.stream().collect(joining(",\n")));
    }
    
}
