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
        
        val promise = Promise.of(
                Wait.forever(),
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
        assertStrings("Result:{ Value: 8 }",   promise.getResult());
    }
    
    @Test
    public void testOf2_happySwitch() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        
        val promise = Promises.of(
                Wait.forever(),
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
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
                Wait.forever(),
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
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
                Wait.forever(),
                str     -> control1.getPromise(),
                padding -> control2.getPromise(),
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
                Wait.forever(),
                str     -> control1.getPromise(),
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
                "Result:{ Exception: functionalj.types.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }",
                promise.getResult());
    }
    
    // TODO - Add concurrecy tests.
    
}
