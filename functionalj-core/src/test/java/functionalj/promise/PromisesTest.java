package functionalj.promise;

import static functionalj.promise.DeferAction.run;
import static functionalj.result.Result.value;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.functions.FuncUnit0;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public class PromisesTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testOf2_happy() {
        val control1 = (PendingAction<String> )DeferAction.of(String.class).start();
        val control2 = (PendingAction<Integer>)DeferAction.of(Integer.class).start();
        
        val promise 
            = Promise.from(
                str     -> control1,
                padding -> control2,
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.complete("One");
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 8 }",   promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_happySwitch() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise 
            = Promise.from(
                str     -> control1,
                padding -> control2,
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.complete("One");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 8 }",   promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail1() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise 
            = Promise.from(
                str     -> control1,
                padding -> control2,
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }",
                promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise 
            = Promise.from(
                str     -> control1,
                padding -> control2,
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #1 out of 2 fail. }",
                promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail1After2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise 
            = Promise.from(
                str     -> control1,
                padding -> control2.getPromise(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }",
                promise.getCurrentResult());
    }
    
    @Test
    public void testOf6_happy() {
        val control1 = DeferAction.of(Integer.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val control3 = DeferAction.of(Integer.class).start();
        val control4 = DeferAction.of(Integer.class).start();
        val control5 = DeferAction.of(Integer.class).start();
        val control6 = DeferAction.of(Integer.class).start();
        val promise 
            = Promise.from(
                _1 -> control1,
                _2 -> control2,
                _3 -> control3,
                _4 -> control4,
                _5 -> control5,
                _6 -> control6,
                (_1, _2, _3, _4, _5, _6) -> {
                    return 42;
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.complete(1);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(2);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control3.complete(3);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control4.complete(4);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control5.complete(5);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control6.complete(6);
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 42 }",   promise.getCurrentResult());
    }
    
    @Test
    public void testOf6_mix() throws InterruptedException {
        val promise 
            = Promise.from(
                _1 -> run(Sleep(50).thenReturn(1)),
                _2 -> Result.of(2),
                _3 -> run(Sleep(50).thenReturn(3)),
                _4 -> value(4),
                _5 -> run(Sleep(50).thenReturn(5)),
                _6 -> value(6),
                (_1, _2, _3, _4, _5, _6) -> {
                    return _1 + _2 + _3 + _4 + _5 + 6;
                });
        Thread.sleep(150);
        
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 21 }",   promise.getCurrentResult());
    }
    
    
    public static FuncUnit0 Sleep(long time) {
        return ()->Thread.sleep(time);
    }
    
}
