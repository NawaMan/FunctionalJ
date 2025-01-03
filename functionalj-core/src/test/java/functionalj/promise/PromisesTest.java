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
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.promise.DeferAction.run;
import static functionalj.result.Result.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;


public class PromisesTest {
    
    @Test
    public void testOf2_happy() {
        val control1 = (PendingAction<String>) DeferAction.of(String.class).start();
        val control2 = (PendingAction<Integer>) DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2, (str, padding) -> {
            return padding + str.length();
        });
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control1.complete("One");
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control2.complete(5);
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: 8 }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_happy_comprehension() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2, (str, padding) -> {
            return padding + str.length();
        });
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control2.complete(5);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control1.complete("One");
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: 8 }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail1() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2, (str, padding) -> {
            return padding + str.length();
        });
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2, (str, padding) -> {
            return padding + str.length();
        });
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control2.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #1 out of 2 fail. }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_fail1After2() {
        val control1 = DeferAction.of(String.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2.getPromise(), (str, padding) -> {
            return padding + str.length();
        });
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control2.complete(5);
        assertTrue(!PromiseStatus.COMPLETED.equals(promise.getStatus()));
        control1.fail(new Exception());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: functionalj.promise.PromisePartiallyFailException: Promise #0 out of 2 fail. }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf6_happy() {
        val control1 = DeferAction.of(Integer.class).start();
        val control2 = DeferAction.of(Integer.class).start();
        val control3 = DeferAction.of(Integer.class).start();
        val control4 = DeferAction.of(Integer.class).start();
        val control5 = DeferAction.of(Integer.class).start();
        val control6 = DeferAction.of(Integer.class).start();
        val promise = Promise.from(control1, control2, control3, control4, control5, control6, (_1, _2, _3, _4, _5, _6) -> {
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
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: 42 }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf6_mix() throws InterruptedException {
        val promise = Promise.from(run(Sleep(50).thenReturn(1)), Result.valueOf(2), run(Sleep(50).thenReturn(3)), valueOf(4), run(Sleep(50).thenReturn(5)), valueOf(6), (_1, _2, _3, _4, _5, _6) -> {
            return _1 + _2 + _3 + _4 + _5 + _6;
        });
        Thread.sleep(150);
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: 21 }", promise.getCurrentResult());
    }
    
    @Test
    public void testOf2_cancel_propagation() throws InterruptedException {
        val promise1 = DeferAction.of(Integer.class);
        val promise2 = DeferAction.of(Integer.class);
        val promise3 = DeferAction.of(Integer.class).complete(5);
        val promise4 = DeferAction.of(Integer.class);
        val promise5 = DeferAction.of(Integer.class);
        val promise6 = DeferAction.of(Integer.class);
        val promise = Promise.from(promise1, promise2, promise3, promise4, promise5, promise6, (_1, _2, _3, _4, _5, _6) -> {
            return _1 + _2 + _3 + _4 + _5 + _6;
        });
        promise.start();
        val subscription = promise.onCompleted();
        // Last subscriber is cancelled, so all the action accept the one that is completed are cancelled.
        subscription.unsubscribe();
        assertAsString("Result:{ Cancelled: No more listener. }", promise.getResult());
        
        assertAsString("Result:{ Cancelled: No more listener. }", promise1.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", promise2.getResult());
        assertAsString("Result:{ Value: 5 }",                     promise3.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", promise4.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", promise5.getResult());
        assertAsString("Result:{ Cancelled: No more listener. }", promise6.getResult());
    }
    
}
