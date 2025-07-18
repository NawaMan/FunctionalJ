// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.TestHelper.assertAsString;
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

import org.junit.Test;

import functionalj.environments.Console;
import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.functions.TimeFuncs;
import functionalj.list.FuncList;
import functionalj.ref.Run;
import functionalj.ref.Substitution;
import lombok.val;

public class DeferActionTest {
    
    @Test
    public void testLazyStart() throws InterruptedException {
        val action = DeferAction.from(() -> 40).map(i -> i + 1);
        val promise = action.getPromise();
        assertEquals("Result:{ NotReady }", action.getCurrentResult().toString());
        assertEquals("Result:{ NotReady }", promise.getCurrentResult().toString());
        val add1 = theInteger.plus(1);
        val answer = add1.applyTo(promise);
        assertEquals("Result:{ NotReady }", action.getCurrentResult().toString());
        assertEquals("Result:{ NotReady }", promise.getCurrentResult().toString());
        answer.start();
        Thread.sleep(100);
        assertEquals("Result:{ Value: 41 }", action.getCurrentResult().toString());
        assertEquals("Result:{ Value: 41 }", promise.getCurrentResult().toString());
        assertEquals("Result:{ Value: 42 }", answer.getResult().toString());
    }
    
    @Test
    public void testDeferAction() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(() -> {
            Thread.sleep(100);
            return "Hello";
        }).onCompleted(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (100 * ((end - start) / 100)));
            log.add("Result: " + result);
        });
        Thread.sleep(150);
        assertAsString("[Start: 0, End: 100, Result: Result:{ Value: Hello }]", log);
    }
    
    @Test
    public void testDeferAction_cancel() throws InterruptedException {
        val deferAction = DeferAction.from(() -> {
            Thread.sleep(200);
            return "Hello";
        });
        val start = System.currentTimeMillis();
        val endRef = new AtomicLong();
        val action = deferAction.onCompleted(result -> {
            val end = System.currentTimeMillis();
            endRef.set(end - start);
        }).start();
        Thread.sleep(50);
        action.cancel();
        assertTrue(endRef.get() < 150);
        assertAsString("Result:{ Cancelled }", action.getResult());
    }
    
    @Test
    public void testDeferAction_map_cancel() throws InterruptedException {
        val action1 = DeferAction.from(() -> {
            Thread.sleep(5000);
            return "Hello";
        });
        val start = System.currentTimeMillis();
        
        val action2 = action1.map(result -> {
			val end = System.currentTimeMillis();
			return "Result: " + result + ", Time: " + (end - start);
		});
        action2.start();
        
        Thread.sleep(200);
        action2.cancel();

        assertAsString("Result:{ Cancelled: No more listener. }", action1.getCurrentResult());
        assertAsString("Result:{ Cancelled }",                    action2.getCurrentResult());
    }
    
    @Test
    public void testDeferAction_map_cancel_2() throws InterruptedException {
        val action1 = DeferAction.from(() -> {
            Thread.sleep(400);
            return "Hello";
        });
        val start = System.currentTimeMillis();
        
        val action2 = action1.map(result -> {
			val end = System.currentTimeMillis();
			return "Result: " + result + ", Time: " + (end - start);
		});
        action2.start();
        
        val action3 = action1.map(s -> s.toLowerCase());
        
        Thread.sleep(200);
        action2.cancel();

        assertAsString("Result:{ Value: Hello }", action1.getResult());
        assertAsString("Result:{ Cancelled }",    action2.getResult());
        assertAsString("Result:{ Value: hello }", action3.getResult());
    }
    
    @Test
    public void testDeferAction_exception() throws InterruptedException {
        val log = new ArrayList<String>();
        val endRef = new AtomicInteger();
        val latch = new CountDownLatch(2);
        val start = System.currentTimeMillis();
        DeferAction.run(() -> {
            Thread.sleep(100);
            latch.countDown();
            throw new IOException("Fail hard!");
        }).onCompleted(result -> {
            val end = System.currentTimeMillis();
            endRef.set((int) (20 * ((end - start) / 20)));
            log.add("Result: " + result);
            latch.countDown();
        });
        latch.await();
        assertTrue(endRef.get() >= 100);
        assertAsString("[Result: Result:{ Exception: java.io.IOException: Fail hard! }]", log);
    }
    
    @Test
    public void testGetResult() {
        val log = new ArrayList<String>();
        val result = DeferAction.run(() -> {
            Thread.sleep(200);
            return "Hello";
        }).getResult();
        log.add("Result: " + result);
        assertEquals("[Result: Result:{ Value: Hello }]", log.toString());
    }
    
    @Test
    public void testGetResult_cancel() {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        val action = run(Sleep(1000).thenReturn("Hello"));
        try {
            action.getResult(500, TimeUnit.MILLISECONDS).get();
            fail("Expect an interruption.");
        } catch (UncheckedInterruptedException e) {
            val end = System.currentTimeMillis();
            log.add("End: " + (100 * ((end - start) / 100)));
            log.add("Result: " + action.getCurrentResult());
            assertAsString("[Start: 0, End: \\E[5-9]\\Q00, Result: Result:{ NotReady }]", log);
        }
    }
    
//    @Ignore
    @Test
    public void testGetResult_interrupt() {
        val threadRef = new AtomicReference<Thread>();
        val action = run(() -> {
            threadRef.set(Thread.currentThread());
            sleep(200);
            return "Hello";
        });
        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadRef.get().interrupt();
        }).start();
        action.getResult();
        assertAsString("Result:{ Exception: java.lang.InterruptedException: sleep interrupted }", action.getResult());
    }
    
    @Test
    public void testDeferAction_lifeCycle() throws InterruptedException {
        val log = new ArrayList<String>();
        val latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferActionBuilder.from(() -> {
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }).onStart(() -> {
            log.add("... onStart ...");
        }).build().onCompleted(result -> {
            Thread.sleep(100);
            log.add("Done: " + result);
            latch.countDown();
        }).eavesdrop(result -> {
            Thread.sleep(100);
            log.add("Done2: " + result);
            latch.countDown();
        }).getResult();
        // getResult is just a subscription so if it run before the above subscription,
        // the test will ends before the above subscription got to run.
        latch.await();
        assertAsString(
                "[" 
              + "Init ..., "
              + "... onStart ..., "
              + "Running ..., "
              + "Done2: Result:{ Value: Hello }, "
              + "Done: Result:{ Value: Hello }"
              + "]", log);
    }
    
    @Test
    public void testDeferAction_lifeCycle_exceptionSafe() throws InterruptedException {
        val log = new ArrayList<String>();
        val latch = new CountDownLatch(2);
        log.add("Init ...");
        DeferActionBuilder.from(() -> {
            Thread.sleep(100);
            log.add("Running ...");
            return "Hello";
        }).onStart(() -> {
            log.add("... onStart ...");
        }).build().onCompleted(result -> {
            log.add("Done: " + result);
            latch.countDown();
            // Exception throw is ignored as Promise `handleResultConsumptionExcepion` is not implemented
            throw new Exception();
        }).eavesdrop(result -> {
            log.add("Done2: " + result);
            latch.countDown();
            // Exception throw is ignored as Promise `handleResultConsumptionExcepion` is not implemented
            throw new Exception();
        }).getResult();
        latch.await();
        assertAsString(
                "["
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
        val log = new ArrayList<String>();
        val latch = new CountDownLatch(4);
        val initThread = new AtomicReference<String>(Thread.currentThread().toString());
        val onStartThread = new AtomicReference<String>();
        val onRunningThread = new AtomicReference<String>();
        val onDoneThread = new AtomicReference<String>();
        val onDone2Thread = new AtomicReference<String>();
        log.add("Init ...");
        DeferActionBuilder.from(() -> {
            Thread.sleep(100);
            log.add("Running ...");
            onRunningThread.set(Thread.currentThread().toString());
            latch.countDown();
            return "Hello";
        }).onStart(() -> {
            Thread.sleep(100);
            log.add("... onStart ...");
            onStartThread.set(Thread.currentThread().toString());
            latch.countDown();
        }).build().onCompleted(result -> {
            Thread.sleep(100);
            log.add("Done: " + result);
            onDoneThread.set(Thread.currentThread().toString());
            latch.countDown();
        }).eavesdrop(result -> {
            Thread.sleep(100);
            log.add("Done2: " + result);
            onDone2Thread.set(Thread.currentThread().toString());
            latch.countDown();
        }).getResult();
        latch.await();
        assertAsString(
                  "["
                + "Init ..., " 
                + "... onStart ..., " 
                + "Running ..., " 
                + "Done2: Result:{ Value: Hello }, " 
                + "Done: Result:{ Value: Hello }" 
                + "]", 
                log);
        // Run on different thread.
        assertFalse(initThread.get().equals(onRunningThread.get()));
        // The rest are on the same thread.
        assertAsString(onStartThread.get(), onRunningThread.get());
        assertAsString(onStartThread.get(), onDoneThread.get());
        assertAsString(onStartThread.get(), onDone2Thread.get());
    }
    
    @Test
    public void testDeferAction_chain() throws InterruptedException {
        // This demonstrate that we can chain a defer action after another.
        
        val log = new ArrayList<String>();
        val latch = new CountDownLatch(5);
        val runningThread = new AtomicReference<String>();
        log.add("Init #0...");
        DeferActionBuilder.from(() -> {
            Thread.sleep(100);
            log.add("Running #1...");
            latch.countDown();
            return "Hello";
        }).onStart(() -> {
            log.add("Start #1 ...");
            runningThread.set(Thread.currentThread().toString());
            latch.countDown();
        }).build().chain(str -> {
            return DeferActionBuilder.from(() -> {
                Thread.sleep(100);
                log.add("Running #2...");
                latch.countDown();
                return str.length();
            }).onStart(() -> {
                log.add("Start #2 ...");
                runningThread.set(Thread.currentThread().toString());
                latch.countDown();
            }).build().start();
        }).onCompleted(result -> {
            log.add("Done #1: " + result);
            latch.countDown();
        }).getResult();
        latch.await();
        assertEquals(
                  "["
                + "Init #0..., "
                + "Start #1 ..., "
                + "Running #1..., "
                + "Start #2 ..., "
                + "Running #2..., "
                + "Done #1: Result:{ Value: 5 }"
                + "]",
                log.toString());
    }
    
    @Test
    public void testDeferAction_moreChain() throws InterruptedException {
        val log = new ArrayList<String>();
        DeferActionBuilder.from(() -> {
            Thread.sleep(50);
            return "Hello";
        }).onStart(() -> {
            log.add("Acion 1 started.");
        }).build().eavesdrop(result -> {
            log.add("Eavesdrop.isCancelled: " + result.isCancelled());
            log.add("Eavesdrop.toString   : " + result.toString());
        }).chain(str -> {
            Thread.sleep(50);
            return Promise.ofValue(str.length());
        }).chain(length -> {
            Thread.sleep(50);
            return Promise.ofValue(length + 42);
        }).map(value -> {
            Thread.sleep(50);
            return "Total=" + value;
        }).onCompleted(result -> {
            log.add("Done: " + result);
        }).start().getResult();
        
        Thread.sleep(50);
        assertAsString(
                  "[" 
                + "Acion 1 started., " 
                + "Eavesdrop.isCancelled: false, "
                + "Eavesdrop.toString   : Result:{ Value: Hello }, "
                + "Done: Result:{ Value: Total=47 }"    //  "Hello".length() + 42
                + "]",
                log);
    }
    
    @Test
    public void testDeferAction_moreChain_cancel() throws InterruptedException {
        val log = new ArrayList<String>();
        val latch = new CountDownLatch(1);
        DeferAction.from(() -> {
            Thread.sleep(50);
            return "Hello";
        }).eavesdrop(result -> {
            log.add("Eavesdrop: " + result.isCancelled());
            log.add("Eavesdrop: " + result.toString());
            latch.countDown();
        }).chain(str -> {
            Thread.sleep(50);
            return Promise.ofValue(str.length());
        }).chain(length -> {
            Thread.sleep(50);
            return Promise.ofValue(length + 42);
        }).map(value -> {
            Thread.sleep(50);
            return "Total=" + value;
        }).onCompleted(result -> {
            log.add("Done: " + result);
        })
        .start()
        .cancel();
        // Wait for all "Done" propagated.
        latch.await();
        val logString = log.toString();
        assertTrue(logString.contains("Done: Result:{ Cancelled }"));
        assertTrue(logString.contains("Eavesdrop: true, Eavesdrop: Result:{ Cancelled: No more listener. }"));
    }
    
    static class LoggedCreator extends DeferActionCreator {
        
        private final List<String> logs = Collections.synchronizedList(new ArrayList<String>());
        
        private final AtomicInteger deferActionCount = new AtomicInteger(0);
        
        @Override
        public <D> DeferAction<D> create(Func0<D> supplier, Runnable onStart, boolean interruptOnCancel) {
            val id = deferActionCount.getAndIncrement();
            logs.add("New defer action: " + id);
            val wrappedSupplier = (Func0<D>) () -> {
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
            return DeferActionCreator.instance.create(wrappedSupplier, onStart, interruptOnCancel);
        }
        
        public List<String> logs() {
            return FuncList.from(logs);
        }
    }
    
    @Test
    public void testCreator() throws InterruptedException {
        val creator = new LoggedCreator();
        Run.with(DeferActionCreator.current.butWith(creator)).run(() -> {
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
            DeferAction.run(Sleep(100).thenReturn(null)).getResult();
        });
        assertAsString(
                "["
              + "New defer action: 0, Start #0: , End #0: null, "
              + "New defer action: 1, Start #1: , End #1: null, "
              + "New defer action: 2, Start #2: , End #2: null"
              + "]",
              creator.logs);
    }
    
    @Test
    public void testStreamAction() {
        val creator = new LoggedCreator();
        runActions(Env.async(), creator);
        assertNotEquals(
                "["
              + "New defer action: 0, Start #0: , End #0: 0, "
              + "New defer action: 1, Start #1: , End #1: 1, "
              + "New defer action: 2, Start #2: , End #2: 2, "
              + "New defer action: 3, Start #3: , End #3: 3, "
              + "New defer action: 4, Start #4: , End #4: 4"
              + "]",
              creator.logs().toString());
        assertNotEquals(
                "["
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
              + "]",
              creator.logs().toString());
    }
    
    @Test
    public void testStreamAction_SingleThread() {
        val executor = Executors.newSingleThreadExecutor();
        val runner   = AsyncRunner.of(runnable -> {
            executor.execute(runnable);
        });
        val creator  = new LoggedCreator();
        runActions(runner, creator);
        assertEquals(
                "["
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
              + "]",
              creator.logs().toString());
    }
    
    @Test
    public void testStreamAction_TwoThreads() {
        val executor = Executors.newFixedThreadPool(2);
        val runner   = AsyncRunner.executorService(executor);
        val creator  = new LoggedCreator();
        runActions(runner, creator);
        assertNotEquals(
                  "["
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
                + "]",
                creator.logs().toString());
        
        boolean zeroOneDone01 = creator.logs().toString().startsWith("[" + "New defer action: 0, " + "New defer action: 1, " + "New defer action: 2, " + "New defer action: 3, " + "New defer action: 4, " + "Start #0: , Start #1: , End #0: 0, End #1: 2, ");
        boolean oneZeroDone01 = creator.logs().toString().startsWith("[" + "New defer action: 0, " + "New defer action: 1, " + "New defer action: 2, " + "New defer action: 3, " + "New defer action: 4, " + "Start #1: , Start #0: , End #1: 2, End #0: 0, ");
        boolean zeroOneDone10 = creator.logs().toString().startsWith("[" + "New defer action: 0, " + "New defer action: 1, " + "New defer action: 2, " + "New defer action: 3, " + "New defer action: 4, " + "Start #0: , Start #1: , End #1: 2, End #0: 0, ");
        boolean oneZeroDone10 = creator.logs().toString().startsWith("[" + "New defer action: 0, " + "New defer action: 1, " + "New defer action: 2, " + "New defer action: 3, " + "New defer action: 4, " + "Start #1: , Start #0: , End #0: 0, End #1: 2, ");
        if (!(zeroOneDone01 || oneZeroDone01 || zeroOneDone10 || oneZeroDone10)) {
            System.err.println(creator.logs);
        }
        assertTrue(zeroOneDone01 || oneZeroDone01 || zeroOneDone10 || oneZeroDone10);
    }
    
    private void runActions(AsyncRunner runner, final functionalj.promise.DeferActionTest.LoggedCreator creator) {
        val list 
        = With(DeferActionCreator.current.butWith(creator))
          .and(Env.refs.async.butWith(runner))
		.run(() -> {
            val actions
                = FuncList.from(FuncList.iterate(0, i -> i + 2).limit(5))
                .map(i -> DeferAction.from(Sleep(100).thenReturn(i)))
                .toImmutableList();
            
            actions.forEach(DeferAction::start);
            
            val results
                = actions
                .map(action  -> action.getPromise())
                .map(promise -> promise.getResult())
                .map(result  -> result.orElse(null))
                .toImmutableList();
            
            actions.forEach(action -> action.cancel());
            return (List<Integer>) results;
        });
        assertAsString("[0, 2, 4, 6, 8]", list);
    }
    
    @Test
    public void testCancelableStream() throws InterruptedException {
        val executor = Executors.newFixedThreadPool(2);
        val runner   = AsyncRunner.executorService(executor);
        val creator  = new LoggedCreator();
        val startTime = System.currentTimeMillis();
        val list 
        = With(DeferActionCreator.current.butWith(creator))
          .and(Env.refs.async.butWith(runner))
        .run(() -> {
            val actions
                    = FuncList.from(IntStream.range(0, 4).mapToObj(Integer::valueOf))
                    .map(i -> DeferAction.from(Sleep(i < 2 ? 100 : 10000).thenReturn(i)))
                    .toImmutableList();
            
            val results
                    = actions
                    .map(action  -> action.start())
                    .map(action  -> action.getPromise())
                    .map(promise -> promise.getResult())
                    .map(result  -> result.orElse(null))
                    .limit(2)
                    .toImmutableList();
            
            actions.forEach(action -> action.cancel());
            return (List<Integer>) results;
        });
        
        Thread.sleep(100);
        val diffTime = System.currentTimeMillis() - startTime;
        assertAsString("[0, 1]", list);
        assertTrue("Taking too long ... 3 and 4 is running: " + diffTime, diffTime < 5000);
        assertTrue(creator.logs.contains("End #0: 0"));
        assertTrue(creator.logs.contains("End #1: 1"));
        assertFalse(creator.logs.contains("END #3: 3"));
        assertFalse(creator.logs.contains("End #4: 4"));
        assertAsString("[" + "New defer action: 0, " + "New defer action: 1, " + "New defer action: 2, " + "New defer action: 3, " + "Start #0: , " + "End #0: 0, " + "Start #1: , " + "End #1: 1" + "]", creator.logs.toString());
    }
    
    @Test
    public void testInterrupt() throws InterruptedException {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(() -> {
            val latch = new CountDownLatch(1);
            val subs = Substitution.getCurrentSubstitutions();
            val startTime = System.currentTimeMillis();
            val pendingAction = DeferAction.run(() -> {
                Run.with(subs).run(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        val buffer = new ByteArrayOutputStream();
                        e.printStackTrace(new PrintStream(buffer));
                        Console.errPrintln(buffer.toString());
                    }
                    latch.countDown();
                });
            }).onCompleted(result -> {
                Console.outPrintln(result);
            });
            
            Thread.sleep(50);
            pendingAction.cancel();
            latch.await();
            val diffTime = System.currentTimeMillis() - startTime;
            assertTrue("Taking too long", diffTime < 1000);
        });
        stub.errLines().forEach(System.out::println);
        assertTrue(stub.errLines().findFirst(s -> s.contains("java.lang.InterruptedException: sleep interrupted")).isPresent());
    }
    
    @Test
    public void testRace() {
        val startTime = System.currentTimeMillis();
        val action1 = DeferAction.from(TimeFuncs.Sleep(60000).thenReturn("60000"));
        val action2 = DeferAction.from(TimeFuncs.Sleep(10).thenReturn("10"));
        assertEquals("10", DeferAction.race(action1, action2).getResult().get());
        val diffTime = System.currentTimeMillis() - startTime;
        assertTrue("Taking too long ... interrupt not working.", diffTime < 500);
    }
    
    @Test
    public void testRace_confirmCancel() {
        // When one finish, another on is cancelled.
        val action1 = DeferAction.run(TimeFuncs.Sleep(60000).thenReturn("60000"));
        val action2 = DeferAction.run(TimeFuncs.Sleep(10).thenReturn("10"));
        assertEquals("10", DeferAction.race(action1, action2).getResult().get());
        assertEquals("Result:{ Cancelled }", action1.getResult().toString());
    }
    
    @Test
    public void testRace_cancelFirst() {
        // Cancelled the one that finish first will just return the later but successful action.
        val action1 = DeferAction.run(TimeFuncs.Sleep(200).thenReturn("200"));
        val action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        val action  = DeferAction.race(action1, action2);
        action2.cancel();
        
        val result = action.getResult();
        assertEquals("200", result.get());
    }
    
    @Test
    public void testRace_cancelAll() {
        val action1 = DeferAction.run(TimeFuncs.Sleep(200).thenReturn("200"));
        val action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        val action  = DeferAction.race(action1, action2);
        action1.cancel();
        action2.cancel();
        assertAsString("Result:{ Cancelled }", action1.getResult());
        assertAsString("Result:{ Cancelled }", action2.getResult());
        assertAsString("Result:{ Cancelled: Finish without non-null result. }", action.getResult());
    }
    
    @Test
    public void testRace_null() {
        val action1 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn((String) null));
        val action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        val action  = DeferAction.race(action1, action2);
        action2.cancel();
        val result = action.getResult();
        assertNull(result.get());
    }
    
    @Test
    public void testRetry_allFail() {
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(() -> {
            counter.incrementAndGet();
            return null;
        })
        .retry(5).times()
        .waitFor(50L).milliseconds()
        .build()
        .start();
        
        // So ended up failing with retry count.
        assertAsString("Result:{ Cancelled: Retry exceed: 5 }", action.getResult());
        assertEquals(5, counter.get());
    }
    
    @Test
    public void testRetry_finallySuccess() {
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(() -> {
            counter.incrementAndGet();
            return counter.get() == 3 ? "Three" : null;
        }).// I like fluence, but this is rediculous.
        // Let fix this later.
        retry(5).times()
        .waitFor(50).milliseconds()
        .build()
        .start();
        
        assertAsString("Result:{ Value: Three }", action.getResult());
        assertEquals(3, counter.get());
    }
    
    @Test
    public void testRetry_waitTime() {
        val counter = new AtomicInteger(0);
        val builder = DeferActionBuilder.from(() -> {
            counter.incrementAndGet();
            return null;
        })
        .retry(5)
        .times()
        .waitFor(0L)
        .milliseconds();
        
        // With wait time of 0, this should run in no time.
        val time1 = System.currentTimeMillis();
        builder
        .build()
        .start()
        .getResult();
        
        // With wait time of 50, this should run in 5*50 = 250ms.
        val time2 = System.currentTimeMillis();
        builder
        .retry(5).times()
        .waitFor(50L).milliseconds()
        .build()
        .start()
        .getResult();
        
        val time3 = System.currentTimeMillis();
        val diff1 = (time2 - time1) / 50;
        val diff2 = (time3 - time2) / 50;
        assertTrue(diff1 <= 2);
        assertTrue(diff2 >= 2);
    }
    
    @Test
    public void testRetry_cancel() throws InterruptedException {
    	val latch   = new CountDownLatch(1);
        val counter = new AtomicInteger(0);
        val builder = DeferActionBuilder.from(() -> {
        	latch.await();
            counter.incrementAndGet();
            return counter.get() == 3
            		? "Three"
    				: null;
        })
        .retry(5).times()
        .waitFor(2000).milliseconds();
        
        val action
        		= builder
        		.build()
        		.start();
        Thread.sleep(50);
        action.cancel("Can't wait.");
        latch.countDown();
        
        assertAsString(
        		"Result:{ Cancelled: Can't wait. }",
        		action.getResult());
    }
    
    @Test
    public void testDeferLoopTimes() throws InterruptedException {
        val counter = new AtomicInteger(0);
        val action
                = DeferActionBuilder
                .from(() -> counter.incrementAndGet())
                .loopTimes(5);
        
        assertAsString("Result:{ Value: 5 }", action.build().getResult());
        assertAsString("5", counter.get());
        assertAsString("Result:{ Value: 10 }", action.build().getResult());
    }
    
    @Test
    public void testDeferLoopCondition() throws InterruptedException {
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder
                .from(() -> counter.incrementAndGet())
                .loopUntil(result -> result.get() >= 5);
        
        assertAsString("Result:{ Value: 5 }", action.build().getResult());
        assertAsString("5", counter.get());
    }
    
    @Test
    public void testDelayMethod() throws InterruptedException {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val counter2 = new AtomicInteger(0);
        
        val action1 = DeferAction.from(() -> {
            val s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        });
        val action2 = DeferAction.from(() -> {
            sleep(10);
            val s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        });
        
        val concat = f(String::concat);
        val result = concat.applyTo(
                        action1.getPromise(),
                        action2.getPromise());
        
        sleep(10);
        logs.add("Before getting result!");
        logs.add("Result: " + result.getResult());
        
        assertEquals(
                "Before getting result!,\n"
              + "A,\n"
              + "a,\n"
              + "Result: Result:{ Value: Aa }",
              logs.stream().collect(joining(",\n")));
    }
    
    @Test
    public void testValidate() throws InterruptedException {
        val action1 = DeferAction.from(() -> {
            Thread.sleep(10);
            return 10;
        }).validateNotNull();
        val action2 = DeferAction.from(() -> {
            Thread.sleep(10);
            return null;
        }).validateNotNull();
        assertEquals("Result:{ Value: 10 }", "" + action1.getResult());
        assertEquals("Result:{ Invalid: java.lang.NullPointerException }", "" + action2.getResult());
    }
    
    @Test
    public void testRecover() {
        val sucessAction = DeferAction.from(() -> {
            Thread.sleep(10);
            return 10;
        })
        .validateNotNull();
        assertAsString(
                "Result:{ Value: 10 }",
                sucessAction.getResult());
        
        val recoverSucessAction = sucessAction.recover(42);
        assertAsString(
                "Result:{ Value: 10 }",
                recoverSucessAction.getResult());
        
        // Done with exception.
        val failAction = DeferAction.from(() -> {
            Thread.sleep(10);
            return null;
        }).validateNotNull();
        assertAsString(
                "Result:{ Invalid: java.lang.NullPointerException }", 
                failAction.getResult());
        
        // Recover the action2 in case of problem.
        val recoverFailAction = failAction.recover(42);
        assertAsString(
                "Result:{ Value: 42 }",
                recoverFailAction.getResult());
    }
}
