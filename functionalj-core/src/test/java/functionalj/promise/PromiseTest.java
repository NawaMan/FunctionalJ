// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import static functionalj.TestHelper.assertAsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.junit.Test;
import functionalj.function.Func;
import functionalj.pipeable.PipeLine;
import lombok.val;

public class PromiseTest {
    
    @Test
    public void testValue() {
        val promise = Promise.ofValue("Hello!");
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: Hello! }", promise.getCurrentResult());
        val ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set(r.get()));
        assertAsString("Hello!", ref);
    }
    
    @Test
    public void testException() {
        val promise = Promise.ofException(new IOException());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        val ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set("" + r.get()));
        assertAsString("null", ref.get());
    }
    
    @Test
    public void testCancel() {
        val promise = Promise.ofAborted();
        assertEquals(PromiseStatus.ABORTED, promise.getStatus());
        assertAsString("Result:{ Cancelled }", promise.getCurrentResult());
        val ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set("" + r.get()));
        assertAsString("null", ref.get());
    }
    
    @Test
    public void testCreateNew_complete() {
        val list = new ArrayList<String>();
        val promiseControl = DeferAction.of(String.class);
        val promise = promiseControl.getPromise();
        assertEquals(PromiseStatus.NOT_STARTED, promise.getStatus());
        val pendingControl = promiseControl.start();
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        promise.onComplete(r -> list.add("1: " + r.toString()));
        pendingControl.complete("Forty two");
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.onComplete(r -> list.add("2: " + r.toString()));
        assertAsString("[" + "1: Result:{ Value: Forty two }, " + "2: Result:{ Value: Forty two }" + "]", list);
    }
    
    @Test
    public void testCreateNew_exception() {
        val promiseControl = DeferAction.of(String.class);
        val promise = promiseControl.getPromise();
        assertEquals(PromiseStatus.NOT_STARTED, promise.getStatus());
        val pendingControl = promiseControl.start();
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        pendingControl.fail(new IOException());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
    }
    
    @Test
    public void testAbort() {
        val ref = new AtomicReference<String>(null);
        val action = DeferAction.of(String.class).onComplete(r -> ref.set("" + r)).start();
        assertAsString("Result:{ NotReady }", action.getCurrentResult());
        action.abort();
        assertEquals("Result:{ Cancelled }", ref.get());
    }
    
    @Test
    public void testLifeCycle_multipleCall_noEffect() {
        val list = new ArrayList<String>();
        val deferAction = DeferAction.of(String.class);
        val promise = deferAction.getPromise();
        val pendingAction = deferAction.start();
        promise.onComplete(r -> list.add("1: " + r.toString()));
        pendingAction.complete("Forty two");
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.onComplete(r -> list.add("2: " + r.toString()));
        assertAsString("[" + "1: Result:{ Value: Forty two }, " + "2: Result:{ Value: Forty two }" + "]", list);
        // NOTE: I no liking this -- This method is no-skip but repeatable.
        deferAction.start();
        deferAction.start();
        deferAction.start();
        pendingAction.complete("Forty three");
        pendingAction.complete("Forty four");
        pendingAction.complete("Forty five");
        pendingAction.abort();
        assertAsString("[" + "1: Result:{ Value: Forty two }, " + "2: Result:{ Value: Forty two }" + "]", list);
    }
    
    @Test
    public void testCreateNew_unsubscribed() {
        val list = new ArrayList<String>();
        val deferAction = DeferAction.of(String.class);
        val promise = deferAction.getPromise();
        val pendingAction = deferAction.start();
        val sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        val sub2 = promise.onComplete(r -> list.add("2: " + r.toString()));
        sub1.unsubscribe();
        pendingAction.complete("Forty two");
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Value: Forty two }", promise.getCurrentResult());
        sub2.unsubscribe();
        assertAsString("[2: Result:{ Value: Forty two }]", list);
    }
    
    @Test
    public void testCreateNew_lastUnsubscribed() {
        val list = new ArrayList<String>();
        val deferAction = DeferAction.of(String.class);
        val promise = deferAction.getPromise();
        val pendingAction = deferAction.start();
        // Last subscription at this time.
        val sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        assertEquals(PromiseStatus.ABORTED, promise.getStatus());
        assertAsString("Result:{ Cancelled: No more listener. }", promise.getCurrentResult());
        // This subscription will get cancelled as the result.
        val sub2 = promise.onComplete(r -> list.add("2: " + r.toString()));
        sub2.unsubscribe();
        assertAsString("[2: Result:{ Cancelled: No more listener. }]", list);
    }
    
    @Test
    public void testCreateNew_unsubscribed_withEavesdrop() {
        val list = new ArrayList<String>();
        val deferAction = DeferAction.of(String.class);
        val promise = deferAction.getPromise();
        val pendingAction = deferAction.start();
        // Add an eavesdrop
        promise.eavesdrop(r -> list.add("e: " + r.toString()));
        // Last subscription at this time as an eavesdrop does not count.
        val sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        assertEquals(PromiseStatus.ABORTED, promise.getStatus());
        assertAsString("Result:{ Cancelled: No more listener. }", promise.getCurrentResult());
        assertAsString("[e: Result:{ Cancelled: No more listener. }]", list);
    }
    
    @Test
    public void testCreateNew_abortNoSubsriptionAfter_withNoSubscription() {
        val list = new ArrayList<String>();
        val onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        val session = new WaitSession() {
    
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        val wait = new WaitAwhile() {
    
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        val promise = DeferAction.of(String.class).abortNoSubsriptionAfter(wait).eavesdrop(r -> list.add("e: " + r.toString())).start().getPromise();
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals(PromiseStatus.ABORTED, promise.getStatus());
        assertAsString("[e: Result:{ Cancelled: No more listener. }]", list);
    }
    
    @Test
    public void testCreateNew_abortNoSubsriptionAfter_withSubscription() {
        val list = new ArrayList<String>();
        val onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        val session = new WaitSession() {
    
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        val wait = new WaitAwhile() {
    
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        val promise = DeferAction.of(String.class).abortNoSubsriptionAfter(wait).eavesdrop(r -> list.add("e: " + r.toString())).onComplete(r -> list.add("s: " + r.toString())).start().getPromise();
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals(PromiseStatus.PENDING, promise.getStatus());
    }
    
    @Test
    public void testCreateNew_map_mapBeforeComplete() {
        val list = new ArrayList<String>();
        DeferAction.of(String.class).use(promise -> {
            promise.map(String::length).onComplete(r -> list.add(r.toString()));
        }).start().complete("Done!");
        assertAsString("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_map2_mapAfterComplete() {
        val list = new ArrayList<String>();
        DeferAction.of(String.class).use(promise -> {
            promise.map(String::length).onComplete(r -> list.add(r.toString()));
        }).start().complete("Done!");
        assertAsString("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_flatMap() {
        val list = new ArrayList<String>();
        DeferAction.of(String.class).use(promise -> {
            promise.flatMap(str -> Promise.ofValue(str.length())).onComplete(r -> list.add(r.toString()));
        }).start().complete("Done!!");
        assertAsString("[Result:{ Value: 6 }]", list);
    }
    
    @Test
    public void testCreateNew_filter() {
        val list = new ArrayList<String>();
        DeferAction.of(String.class).use(promise -> {
            promise.filter(str -> str.length() < 3).onComplete(r -> list.add(r.toString()));
            promise.filter(str -> str.length() > 3).onComplete(r -> list.add(r.toString()));
        }).start().complete("Done!");
        assertTrue(list.toString().contains("Result:{ Value: null }"));
        assertTrue(list.toString().contains("Result:{ Value: Done! }"));
    }
    
    @Test
    public void testCreateNew_waitOrDefault() {
        val list = new ArrayList<String>();
        val onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        val session = new WaitSession() {
    
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        val wait = new WaitAwhile() {
    
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        DeferAction.of(String.class).use(promise -> promise.onComplete(wait.orDefaultTo("Not done."), r -> list.add(r.get()))).start();
        onExpireds.forEach(c -> {
            c.accept(null, null);
        });
        assertAsString("[Not done.]", list);
    }
    
    @Test
    public void testParentStatus_complete() {
        val parentPromise = DeferAction.of(String.class).getPromise();
        val childPromise = parentPromise.map(String::length);
        assertAsString("NOT_STARTED", parentPromise.getStatus());
        assertAsString("NOT_STARTED", childPromise.getStatus());
        childPromise.start();
        assertAsString("PENDING", parentPromise.getStatus());
        assertAsString("PENDING", childPromise.getStatus());
        parentPromise.makeComplete("HELLO");
        assertAsString("COMPLETED", parentPromise.getStatus());
        assertAsString("COMPLETED", childPromise.getStatus());
        assertAsString("Result:{ Value: HELLO }", parentPromise.getCurrentResult());
        assertAsString("Result:{ Value: 5 }", childPromise.getCurrentResult());
    }
    
    @Test
    public void testParentStatus_exception() {
        val parentPromise = DeferAction.of(String.class).getPromise();
        val childPromise = parentPromise.map(String::length);
        assertAsString("NOT_STARTED", parentPromise.getStatus());
        assertAsString("NOT_STARTED", childPromise.getStatus());
        childPromise.start();
        assertAsString("PENDING", parentPromise.getStatus());
        assertAsString("PENDING", childPromise.getStatus());
        parentPromise.makeFail(new NullPointerException());
        assertAsString("COMPLETED", parentPromise.getStatus());
        assertAsString("COMPLETED", childPromise.getStatus());
        assertAsString("Result:{ Exception: java.lang.NullPointerException }", parentPromise.getCurrentResult());
        assertAsString("Result:{ Exception: java.lang.NullPointerException }", childPromise.getCurrentResult());
    }
    
    @Test
    public void testElseUse() {
        val promise = Promise.ofException(new IOException());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        val promise2 = promise.whenAbsentUse("Else");
        assertAsString("Result:{ Value: Else }", promise2.getResult());
    }
    
    @Test
    public void testElseGet() {
        val promise = Promise.ofException(new IOException());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        val promise2 = promise.whenAbsentGet(() -> "Else");
        assertAsString("Result:{ Value: Else }", promise2.getResult());
    }
    
    @Test
    public void testMapResult() {
        val promise = Promise.ofException(new IOException());
        assertEquals(PromiseStatus.COMPLETED, promise.getStatus());
        assertAsString("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        val promise2 = promise.mapResult(result -> result.whenExceptionApply(e -> e.getClass().getName()));
        assertAsString("Result:{ Value: java.io.IOException }", promise2.getResult());
        val ref = new AtomicReference<String>();
        promise.mapResult(result -> result.ifException(e -> ref.set(e.getClass().getName())));
        assertAsString("java.io.IOException", ref.get());
    }
    
    @Test
    public void testDeferMethod() throws InterruptedException {
        val add = Func.f((Integer a, Integer b) -> (a + b));
        val mul = Func.f((Integer a, Integer b) -> (a * b));
        val a = Sleep(50).thenReturn(20).defer();
        val b = Sleep(50).thenReturn(1).defer();
        val c = Sleep(50).thenReturn(2).defer();
        val r1 = add.applyTo(a, b);
        val r2 = mul.applyTo(add.applyTo(a, b), c);
        // val r3 = add
        // .then(mul.elevateWith(c)) // This does not work as c is a promise.
        // .apply(a, b);
        val r4 = a.pipeTo(add.elevateWith(b), mul.elevateWith(c));
        val f5 = add.elevateWith(b).andThen(mul.elevateWith(c));
        val r5 = f5.apply(a);
        assertAsString("Result:{ Value: 21 }", r1.getResult());
        assertAsString("Result:{ Value: 42 }", r2.getResult());
        // assertAsString("Result:{ Value: 42 }", r3.getResult());
        assertAsString("Result:{ Value: 42 }", r4.getResult());
        assertAsString("Result:{ Value: 42 }", r5.getResult());
    }
    
    @Test
    public void testDeferMethod_PipeLine() throws InterruptedException {
        val add = Func.f((Integer a, Integer b) -> (a + b));
        val nul = Func.f((Integer a, Integer b) -> (a * b));
        val a = Sleep(50).thenReturn(20).defer();
        val b = Sleep(50).thenReturn(1).defer();
        val c = Sleep(50).thenReturn(2).defer();
        val f6 = PipeLine.from(add.elevateWith(b)).then(nul.elevateWith(c)).thenReturn();
        val r6 = f6.apply(a);
        assertAsString("Result:{ Value: 42 }", r6.getResult());
    }
    
    @Test
    public void testName() {
        val name = "ImportantPromise";
        val promiseControl = DeferAction.of(String.class);
        val normalPromise = promiseControl.getPromise();
        val namedPromise = normalPromise.named(name);
        assertTrue(normalPromise.toString().matches("^Promise#[0-9]*$"));
        assertAsString(namedPromise.toString(), name);
        System.out.println(normalPromise.getStatus());
        System.out.println(namedPromise.getStatus());
        promiseControl.complete("DONE!");
        System.out.println(normalPromise.getStatus());
        System.out.println(namedPromise.getStatus());
    }
}
