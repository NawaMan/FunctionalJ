package functionalj.types.promise;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class PromisesTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testOf2_happy() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promise.from(
                str     -> control1,
                padding -> control2,
                Wait.forever(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.complete("One");
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 8 }",   promise.getResult());
    }
    
    @Test
    public void testOf2_happySwitch() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promises.of(
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
                Wait.forever(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.complete("One");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 8 }",   promise.getResult());
    }
    
    @Test
    public void testOf2_fail1() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promises.of(
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
                Wait.forever(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.types.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }",
                promise.getResult());
    }
    
    @Test
    public void testOf2_fail2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promises.of(
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
                Wait.forever(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.types.promise.PromisePartiallyFailException: Promise #1 out of 2 fail. }",
                promise.getResult());
    }
    
    @Test
    public void testOf2_fail1After2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promises.of(
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
                Wait.forever(),
                (str, padding) -> {
                    return padding + str.length();
                });
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control2.complete(5);
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.types.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }",
                promise.getResult());
    }
    
    @Test
    public void testOf6_happy() {
        val control1 = DeferAction.of(Integer.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val control3 = DeferAction.of(Integer.class).start();
        val control4 = DeferAction.of(Integer.class).start();
        val control5 = DeferAction.of(Integer.class).start();
        val control6 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(
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
        assertStrings("Result:{ Value: 42 }",   promise.getResult());
    }
    
    // TODO - Add concurrecy tests.
    
}