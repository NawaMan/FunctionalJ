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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.pipeable.PipeLine;


public class PromiseTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testValue() {
        var promise = Promise.of("Hello!");
        assertEquals (PromiseStatus.COMPLETED,    promise.getStatus());
        assertStrings("Result:{ Value: Hello! }", promise.getCurrentResult());
        
        var ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set(r.get()));
        assertStrings("Hello!", ref);
    }
    
    @Test
    public void testException() {
        var promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        
        var ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set("" + r.get()));
        assertStrings("null", ref.get());
    }
    
    @Test
    public void testCancel() {
        var promise = Promise.ofAborted();
        assertEquals (PromiseStatus.ABORTED,  promise.getStatus());
        assertStrings("Result:{ Cancelled }", promise.getCurrentResult());
        
        var ref = new AtomicReference<String>(null);
        promise.onComplete(r -> ref.set("" + r.get()));
        assertStrings("null", ref.get());
    }
    
    @Test
    public void testCreateNew_complete() {
        var list = new ArrayList<String>();
        
        var promiseControl = DeferAction.of(String.class);
        var promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        var pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        promise.onComplete(r -> list.add("1: " + r.toString()));
        
        pendingControl.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.onComplete(r -> list.add("2: " + r.toString()));
        
        assertStrings(
                "["
                + "1: Result:{ Value: Forty two }, "
                + "2: Result:{ Value: Forty two }"
                + "]",
              list);
    }
    
    @Test
    public void testCreateNew_exception() {
        var promiseControl = DeferAction.of(String.class);
        var promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        var pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        pendingControl.fail(new IOException());
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
    }
    
    @Test
    public void testAbort() {
        var ref = new AtomicReference<String>(null);
        var action = DeferAction.of(String.class)
                .onComplete(r -> ref.set("" + r))
                .start();
        
        assertStrings("Result:{ NotReady }", action.getCurrentResult());
        
        action.abort();
        assertEquals("Result:{ Cancelled }", ref.get());
    }
    
    @Test
    public void testLifeCycle_multipleCall_noEffect() {
        var list = new ArrayList<String>();
        
        var deferAction   = DeferAction.of(String.class);
        var promise       = deferAction.getPromise();
        var pendingAction = deferAction.start();
        
        promise.onComplete(r -> list.add("1: " + r.toString()));
        
        pendingAction.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.onComplete(r -> list.add("2: " + r.toString()));
        
        assertStrings(
                "["
                + "1: Result:{ Value: Forty two }, "
                + "2: Result:{ Value: Forty two }"
                + "]",
              list);
        
        // NOTE: I no liking this -- This method is no-skip but repeatable.
        deferAction.start();
        deferAction.start();
        deferAction.start();
        pendingAction.complete("Forty three");
        pendingAction.complete("Forty four");
        pendingAction.complete("Forty five");
        pendingAction.abort();
        
        assertStrings(
                "["
                + "1: Result:{ Value: Forty two }, "
                + "2: Result:{ Value: Forty two }"
                + "]",
              list);
    }
    
    @Test
    public void testCreateNew_unsubscribed() {
        var list = new ArrayList<String>();
        
        var deferAction   = DeferAction.of(String.class);
        var promise       = deferAction.getPromise();
        var pendingAction = deferAction.start();
        
        var sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        var sub2 = promise.onComplete(r -> list.add("2: " + r.toString()));
        
        sub1.unsubscribe();
        
        pendingAction.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        
        sub2.unsubscribe();
        
        assertStrings("[2: Result:{ Value: Forty two }]", list);
    }
    
    @Test
    public void testCreateNew_lastUnsubscribed() {
        var list = new ArrayList<String>();
        
        var deferAction   = DeferAction.of(String.class);
        var promise       = deferAction.getPromise();
        var pendingAction = deferAction.start();
        
        // Last subscription at this time.
        var sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        assertStrings(
                "Result:{ Cancelled: No more listener. }",
                promise.getCurrentResult());
        
        // This subscription will get cancelled as the result.
        var sub2 = promise.onComplete(r -> list.add("2: " + r.toString()));
        sub2.unsubscribe();
        
        assertStrings(
                "[2: Result:{ Cancelled: No more listener. }]",
              list);
    }
    
    @Test
    public void testCreateNew_unsubscribed_withEavesdrop() {
        var list = new ArrayList<String>();
        
        var deferAction   = DeferAction.of(String.class);
        var promise       = deferAction.getPromise();
        var pendingAction = deferAction.start();
        
        // Add an eavesdrop
        promise.eavesdrop(r -> list.add("e: " + r.toString()));
        
        // Last subscription at this time as an eavesdrop does not count.
        var sub1 = promise.onComplete(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        assertStrings(
                "Result:{ Cancelled: No more listener. }",
                promise.getCurrentResult());
        
        assertStrings("[e: Result:{ Cancelled: No more listener. }]", list);
    }
    
    @Test
    public void testCreateNew_abortNoSubsriptionAfter_withNoSubscription() {
        var list = new ArrayList<String>();
        
        var onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        var session = new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        var wait = new WaitAwhile() {
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        
        var promise = DeferAction.of(String.class)
                .abortNoSubsriptionAfter(wait)
                .eavesdrop(r -> list.add("e: " + r.toString()))
                .start()
                .getPromise();
        
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        
        assertStrings("[e: Result:{ Cancelled: No more listener. }]", list);
    }
    
    @Test
    public void testCreateNew_abortNoSubsriptionAfter_withSubscription() {
        var list = new ArrayList<String>();
        
        var onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        var session = new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        var wait = new WaitAwhile() {
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        
        var promise = DeferAction.of(String.class)
                .abortNoSubsriptionAfter(wait)
                .eavesdrop (r -> list.add("e: " + r.toString()))
                .onComplete(r -> list.add("s: " + r.toString()))
                .start()
                .getPromise();
        
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
    }
    
    @Test
    public void testCreateNew_map_mapBeforeComplete() {
        var list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
            .map(String::length)
            .onComplete(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_map2_mapAfterComplete() {
        var list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
            .map(String::length)
            .onComplete(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_flatMap() {
        var list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
                .flatMap(str -> Promise.of(str.length()))
                .onComplete(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!!");
        
        assertStrings("[Result:{ Value: 6 }]", list);
    }
    
    @Test
    public void testCreateNew_filter() {
        var list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
                .filter(str -> str.length() < 3)
                .onComplete(r -> list.add(r.toString()));
            
            promise
                .filter(str -> str.length() > 3)
                .onComplete(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
        assertTrue(list.toString().contains("Result:{ Value: null }"));
        assertTrue(list.toString().contains("Result:{ Value: Done! }"));
    }
    
    @Test
    public void testCreateNew_waitOrDefault() {
        var list = new ArrayList<String>();
        
        var onExpireds = new ArrayList<BiConsumer<String, Exception>>();
        var session = new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
                onExpireds.add(onDone);
            }
        };
        var wait = new WaitAwhile() {
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        
        DeferAction.of(String.class)
        .use(promise -> promise.onComplete(wait.orDefaultTo("Not done."), r -> list.add(r.get())))
        .start();
        
        onExpireds.forEach(c -> {
            c.accept(null, null);
        });
        
        assertStrings(
                "[Not done.]",
                list);
    }
    
    @Test
    public void testParentStatus_complete() {
        var parentPromise = DeferAction.of(String.class)
                .getPromise();
        
        var childPromise = parentPromise.map(String::length);
        
        assertStrings("NOT_STARTED", parentPromise.getStatus());
        assertStrings("NOT_STARTED", childPromise.getStatus());
        
        childPromise.start();
        assertStrings("PENDING", parentPromise.getStatus());
        assertStrings("PENDING", childPromise.getStatus());
        
        parentPromise.makeComplete("HELLO");
        assertStrings("COMPLETED", parentPromise.getStatus());
        assertStrings("COMPLETED", childPromise.getStatus());
        
        assertStrings("Result:{ Value: HELLO }", parentPromise.getCurrentResult());
        assertStrings("Result:{ Value: 5 }",     childPromise.getCurrentResult());
    }
    
    @Test
    public void testParentStatus_exception() {
        var parentPromise = DeferAction.of(String.class)
                .getPromise();
        
        var childPromise = parentPromise.map(String::length);
        
        assertStrings("NOT_STARTED", parentPromise.getStatus());
        assertStrings("NOT_STARTED", childPromise.getStatus());
        
        childPromise.start();
        assertStrings("PENDING", parentPromise.getStatus());
        assertStrings("PENDING", childPromise.getStatus());
        
        parentPromise.makeFail(new NullPointerException());
        assertStrings("COMPLETED", parentPromise.getStatus());
        assertStrings("COMPLETED", childPromise.getStatus());
        
        assertStrings("Result:{ Exception: java.lang.NullPointerException }", parentPromise.getCurrentResult());
        assertStrings("Result:{ Exception: java.lang.NullPointerException }", childPromise.getCurrentResult());
    }
    
    @Test
    public void testElseUse() {
        var promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        
        var promise2 = promise.whenAbsentUse("Else");
        assertStrings("Result:{ Value: Else }", promise2.getResult());
    }
    
    @Test
    public void testElseGet() {
        var promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        
        var promise2 = promise.whenAbsentGet(()->"Else");
        assertStrings("Result:{ Value: Else }", promise2.getResult());
    }
    
    @Test
    public void testMapResult() {
        var promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        
        var promise2 = promise.mapResult(result -> result.whenExceptionApply(e -> e.getClass().getName()));
        assertStrings("Result:{ Value: java.io.IOException }", promise2.getResult());
        
        var ref = new AtomicReference<String>();
        promise.mapResult(result -> result.ifException(e -> ref.set(e.getClass().getName())));
        assertStrings("java.io.IOException", ref.get());
    }
    
    @Test
    public void testDeferMethod() throws InterruptedException {
        var add = Func.f((Integer a, Integer b)->(a + b));
        var mul = Func.f((Integer a, Integer b)->(a * b));
        
        var a = Sleep(50).thenReturn(20).defer();
        var b = Sleep(50).thenReturn( 1).defer();
        var c = Sleep(50).thenReturn( 2).defer();
        
        var r1 = add.applyTo(a, b);
        var r2 = mul.applyTo(add.applyTo(a, b), c);
//        var r3 = add
//                .then(mul.elevateWith(c)) // This does not work as c is a promise.
//                .apply(a, b);
        var r4 = a
                .pipeTo(
                    add.elevateWith(b),
                    mul.elevateWith(c)
                );
        var f5 = add.elevateWith(b).andThen(mul.elevateWith(c));
        var r5 = f5.apply(a);
        
        assertStrings("Result:{ Value: 21 }", r1.getResult());
        assertStrings("Result:{ Value: 42 }", r2.getResult());
//        assertStrings("Result:{ Value: 42 }", r3.getResult());
        assertStrings("Result:{ Value: 42 }", r4.getResult());
        assertStrings("Result:{ Value: 42 }", r5.getResult());
    }
    
    @Test
    public void testDeferMethod_PipeLine() throws InterruptedException {
        var add = Func.f((Integer a, Integer b)->(a + b));
        var nul = Func.f((Integer a, Integer b)->(a * b));
        
        var a = Sleep(50).thenReturn(20).defer();
        var b = Sleep(50).thenReturn( 1).defer();
        var c = Sleep(50).thenReturn( 2).defer();
        
        var f6 = PipeLine
                .from(add.elevateWith(b))
                .then(nul.elevateWith(c))
                .thenReturn();
        var r6 = f6.apply(a);
        assertStrings("Result:{ Value: 42 }", r6.getResult());
    }
    
}
