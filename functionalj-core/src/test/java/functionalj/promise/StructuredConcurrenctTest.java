package functionalj.promise;

import static functionalj.TestHelper.assertAsString;
import static functionalj.function.Func.f;
import static functionalj.function.Lambda.λ;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.promise.DeferAction.race;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import functionalj.functions.TimeFuncs;
import functionalj.result.Result;
import lombok.val;

public class StructuredConcurrenctTest {
    
    //== Combine ==
    
    @Test
    public void testCombine_notStarted() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        val prefix = Sleep( 10).thenReturn("[").defer();
        val suffix = Sleep( 50).thenReturn("]").defer();
        val body   = Sleep(100).thenReturn( "---").defer();
        val string = λ(around, prefix, suffix, body);
        sleep(200);
        
        assertAsString("Result:{ NotReady }", string.getCurrentResult());
        assertAsString("Result:{ NotReady }", prefix.getCurrentResult());
        assertAsString("Result:{ NotReady }", suffix.getCurrentResult());
        assertAsString("Result:{ NotReady }", body.getCurrentResult());
    }
    
    @Test
    public void testCombine_allSuccess() {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        val prefix = Sleep(  10).thenReturn("[").defer();
        val suffix = Sleep(  50).thenReturn("]").defer();
        val body   = Sleep(2000).thenReturn( "---").defer();
        val string = λ(around, prefix, suffix, body);
        
        // `get()` will start the action and wait for all of them to finish.
        assertAsString("[---]", string.get());
    }
    
    @Test
    public void testCombine_oneFailed() {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        val prefix = Sleep(  10).thenReturn("[").defer();
        val suffix = Sleep( 100).thenThrow(RuntimeException::new, String.class).defer();
        val body   = Sleep(2000).thenReturn( "---").defer();
        val string = λ(around, prefix, suffix, body);
        
        string.start();
        
        assertAsString("Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #1 out of 3 fail. }", string.getResult());
        assertAsString("Result:{ Value: [ }",                              prefix.getResult());  // TODO - Should be cancelled?
        assertAsString("Result:{ Exception: java.lang.RuntimeException }", suffix.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }",          body  .getResult());  // TODO - Should be cancelled?
    }
    @Test
    public void testCombine_abort() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        val prefix = Sleep(  10).thenReturn("[").defer();
        val suffix = Sleep(  50).thenReturn("]").defer();
        val body   = Sleep(2000).thenReturn( "---").defer();
        val string = λ(around, prefix, suffix, body);
        
        string.start();
        sleep(100);
        string.abort("Too long");
        
        assertAsString("Result:{ Cancelled: Too long }",          string.getResult());
        assertAsString("Result:{ Value: [ }",                     prefix.getResult());
        assertAsString("Result:{ Value: ] }",                     suffix.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", body  .getResult());  // TODO - Should be cancelled?
    }
    
    //== Race ==
    
    @Test
    public void testRace_notStart() throws InterruptedException {
        val first  = Sleep( 10).thenReturn("1st").defer();
        val second = Sleep(100).thenReturn("2nd").defer();
        val third  = Sleep(200).thenReturn("3rd").defer();
        
        val result = DeferAction.race(first, second, third);
        sleep(400);
        
        assertAsString("Result:{ NotReady }", result.getCurrentResult());
        assertAsString("Result:{ NotReady }", first .getCurrentResult());
        assertAsString("Result:{ NotReady }", second.getCurrentResult());
        assertAsString("Result:{ NotReady }", third .getCurrentResult());
    }
    
    @Test
    public void testRace_allSuccess() throws InterruptedException {
        val first  = Sleep(  10).thenReturn("1st").defer();
        val second = Sleep( 500).thenReturn("2nd").defer();
        val third  = Sleep(2000).thenReturn("3rd").defer();
        
        val result = DeferAction.race(first, second, third);
        result.start();
        
        assertAsString("Result:{ Value: 1st }", result.getResult());
        assertAsString("Result:{ Value: 1st }", first.getResult());
        assertAsString("Result:{ Cancelled }",  second.getResult());
        assertAsString("Result:{ Cancelled }",  third.getResult());
    }
    
    @Test
    public void testRace_firstFailed() throws InterruptedException {
        val first  = Sleep(  10).thenThrow(RuntimeException::new, String.class).defer();
        val second = Sleep( 500).thenReturn("2nd").defer();
        val third  = Sleep(2000).thenReturn("3rd").defer();
        
        val result = DeferAction.race(first, second, third);
        result.start();
        sleep(100);
        
        assertAsString("Result:{ Value: 2nd }",                            result.getResult());
        assertAsString("Result:{ Exception: java.lang.RuntimeException }", first.getResult());
        assertAsString("Result:{ Value: 2nd }",                            second.getResult());
        assertAsString("Result:{ Cancelled }",                             third.getResult());
    }
    
    @Test
    public void testRace_secondFailed() throws InterruptedException {
        val first  = Sleep(  10).thenReturn("1st").defer();
        val second = Sleep( 500).thenThrow(RuntimeException::new, String.class).defer();
        val third  = Sleep(2000).thenReturn("3rd").defer();
        
        val result = DeferAction.race(first, second, third);
        result.start();
        sleep(100);
        
        assertAsString("Result:{ Value: 1st }", result.getResult());
        assertAsString("Result:{ Value: 1st }", first.getResult());
        assertAsString("Result:{ Cancelled }",  second.getResult());    // Cancelled before the exception is thrown.
        assertAsString("Result:{ Cancelled }",  third.getResult());
    }
    
    @Test
    public void testRace_cancelAll() {
        val action1 = DeferAction.run(TimeFuncs.Sleep(200).thenReturn("200"));
        val action2 = DeferAction.run(TimeFuncs.Sleep(100).thenReturn("100"));
        val action  = DeferAction.race(action1, action2);
        action1.abort();
        action2.abort();
        assertAsString("Result:{ Cancelled }", action1.getResult());
        assertAsString("Result:{ Cancelled }", action2.getResult());
        assertAsString("Result:{ Cancelled: Finish without non-null result. }", action.getResult());
    }
    
    //== Mix ==
    
    @Test
    public void testMix_allSuccess() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        
        val first  = Sleep(  10).thenReturn("<1>").defer();
        val second = Sleep( 500).thenReturn("<2>").defer();
        val third  = Sleep(1000).thenReturn("<3>").defer();
        val forth  = Sleep(1500).thenReturn("<4>").defer();
        val fifth  = Sleep(2000).thenReturn("<5>").defer();
        
        val string = λ(around, first, second, race(third, forth, fifth));
        string.start();
        
        assertAsString("Result:{ Value: <1><3><2> }", string.getResult());
        assertAsString("Result:{ Value: <1> }",       first.getResult());
        assertAsString("Result:{ Value: <2> }",       second.getResult());
        assertAsString("Result:{ Value: <3> }",       third.getResult());
        assertAsString("Result:{ Cancelled }",        forth.getResult());
        assertAsString("Result:{ Cancelled }",        fifth.getResult());
    }
    
    @Test
    public void testMix_failDeep() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        
        val first  = Sleep(  10).thenReturn("<1>").defer();
        val second = Sleep( 500).thenReturn("<2>").defer();
        val third  = Sleep( 500).thenThrow(RuntimeException::new, String.class).defer();
        val forth  = Sleep(1000).thenReturn("<4>").defer();
        val fifth  = Sleep(2000).thenReturn("<5>").defer();
        
        val string = λ(around, first, second, race(third, forth, fifth));
        string.start();
        
        assertAsString("Result:{ Value: <1><4><2> }",                      string.getResult());
        assertAsString("Result:{ Value: <1> }",                            first.getResult());
        assertAsString("Result:{ Value: <2> }",                            second.getResult());
        assertAsString("Result:{ Exception: java.lang.RuntimeException }", third.getResult());  // First to finish but failed.
        assertAsString("Result:{ Value: <4> }",                            forth.getResult());  // First to success
        assertAsString("Result:{ Cancelled }",                             fifth.getResult());  // Take longer than the first to success so it get cancelled.
    }
    
    @Test
    public void testMix_failShallow() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        
        val first  = Sleep( 10).thenReturn("<1>").defer();
        val second = Sleep(500).thenThrow(RuntimeException::new, String.class).defer();
        val third  = Sleep(100).thenReturn("<3>").defer();
        val forth  = Sleep(150).thenReturn("<4>").defer();
        val fifth  = Sleep(200).thenReturn("<5>").defer();
        
        val string = λ(around, first, second, race(third, forth, fifth));
        string.start();
        sleep(500);
        
        second.completeWith(Result.<String>ofException(new RuntimeException()));
        
        assertAsString("Result:{ Exception: "
                                + "functionalj.promise.PromisePartiallyFailException: "
                                + "Promise #1 out of 3 fail. "
                            + "}",                                         string.getResult());
        assertAsString("Result:{ Value: <1> }",                            first.getResult());
        assertAsString("Result:{ Exception: java.lang.RuntimeException }", second.getResult());
        assertAsString("Result:{ Value: <3> }",                            third.getResult());
        assertAsString("Result:{ Cancelled }",                             forth.getResult());
        assertAsString("Result:{ Cancelled }",                             fifth.getResult());
    }
    
    @Test
    public void testMix_cancel() throws InterruptedException {
        val around = f((String prefix, String suffix, String text) -> prefix + text + suffix);
        
        val first  = Sleep(  10).thenReturn("1st").defer();
        val second = Sleep( 500).thenReturn("2nd").defer();
        val third  = Sleep(1000).thenReturn("3rd").defer();
        val forth  = Sleep(1500).thenReturn("<4>").defer();
        val fifth  = Sleep(2000).thenReturn("<5>").defer();
        
        val string = λ(around, first, second, race(third, forth, fifth));
        string.start();
        sleep(100);
        string.abort("Change my mind.");
        
        assertAsString("Result:{ Cancelled: Change my mind. }",   string.getResult());
        assertAsString("Result:{ Value: 1st }",                   first.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", second.getResult());  // TODO - Should be cancelled?
        assertAsString("Result:{ Value: 3rd }",                   third.getResult());
        assertAsString("Result:{ Cancelled }",                    forth.getResult());
        assertAsString("Result:{ Cancelled }",                    fifth.getResult());
    }
    
    //== Cleanup ==
    
    @Test
    public void testCleanup_success() {
        val logs = new ArrayList<String>();
        
        val string = Sleep(10).thenReturn("$$$").defer();
        string.onCompleted(result -> logs.add("Ended: " + result));
        
        string.start();
        
        // Wait for all of them to finish.
        assertAsString("Result:{ Value: $$$ }",          string.getResult());
        assertAsString("[Ended: Result:{ Value: $$$ }]", logs);
    }
    
    @Test
    public void testCleanup_failed() {
        val logs = new ArrayList<String>();
        
        val string = Sleep(10).thenThrow(RuntimeException::new, String.class).defer();
        string.onCompleted(result -> logs.add("Ended: " + result));
        
        string.start();
        
        // Wait for all of them to finish.
        assertAsString("Result:{ Exception: java.lang.RuntimeException }",          string.getResult());
        assertAsString("[Ended: Result:{ Exception: java.lang.RuntimeException }]", logs);
    }
    
    //== Retry ==
    
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
    public void testRetry_abort() throws InterruptedException {
        val counter = new AtomicInteger(0);
        val builder = DeferActionBuilder.from(() -> {
            counter.incrementAndGet();
            return counter.get() == 3 ? "Three" : null;
        })
        .retry(5).times()
        .waitFor(50)
        .milliseconds();
        
        val action = builder.build().start();
        Thread.sleep(50);
        action.abort("Can't wait.");
        
        assertAsString("Result:{ Cancelled: Can't wait. }", action.getResult());
    }
    
    //== Spawn ==
    
}
