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

import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.promise.DeferAction.run;
import static functionalj.result.Result.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

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
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control1.complete("One");
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
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
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control2.complete(5);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
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
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
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
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
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
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control2.complete(5);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
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
        promise.start();
        
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control1.complete(1);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control2.complete(2);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control3.complete(3);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control4.complete(4);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control5.complete(5);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        
        control6.complete(6);
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 42 }",   promise.getCurrentResult());
    }
    
    @Test
    public void testOf6_mix() throws InterruptedException {
        val promise 
            = Promise.from(
                _1 -> run(Sleep(50).thenReturn(1)),
                _2 -> Result.valueOf(2),
                _3 -> run(Sleep(50).thenReturn(3)),
                _4 -> valueOf(4),
                _5 -> run(Sleep(50).thenReturn(5)),
                _6 -> valueOf(6),
                (_1, _2, _3, _4, _5, _6) -> {
                    return _1 + _2 + _3 + _4 + _5 + 6;
                });
        Thread.sleep(150);
        
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: 21 }",   promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_cancel_propagation() throws InterruptedException {
        val promise1 = DeferAction.of(Integer.class);
        val promise2 = DeferAction.of(Integer.class);
        val promise3 = DeferAction.of(Integer.class).complete(5);
        val promise4 = DeferAction.of(Integer.class);
        val promise5 = DeferAction.of(Integer.class);
        val promise6 = DeferAction.of(Integer.class);
        val promise 
            = Promise.from(
                _1 -> promise1,
                _2 -> promise2,
                _3 -> promise3,
                _4 -> promise4,
                _5 -> promise5,
                _6 -> promise6,
                (_1, _2, _3, _4, _5, _6) -> {
                    return _1 + _2 + _3 + _4 + _5 + 6;
                });
        promise.start();
        
        val subscription = promise.onComplete();
        subscription.unsubscribe();
        
        assertStrings("Result:{ Cancelled: No more listener. }", promise .getResult());
        assertStrings("Result:{ Cancelled: No more listener. }", promise1.getResult());
        assertStrings("Result:{ Cancelled: No more listener. }", promise2.getResult());
        assertStrings("Result:{ Value: 5 }",                     promise3.getResult());
        assertStrings("Result:{ Cancelled: No more listener. }", promise4.getResult());
        assertStrings("Result:{ Cancelled: No more listener. }", promise5.getResult());
        assertStrings("Result:{ Cancelled: No more listener. }", promise6.getResult());
    }
    
}
