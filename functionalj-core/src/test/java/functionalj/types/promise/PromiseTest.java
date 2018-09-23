package functionalj.types.promise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class PromiseTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testValue() {
        val promise = Promise.ofValue("Hello!");
        assertEquals (PromiseStatus.COMPLETED,    promise.getStatus());
        assertStrings("Result:{ Value: Hello! }", promise.getCurrentResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set(r.get()));
        assertStrings("Hello!", ref);
    }
    
    @Test
    public void testException() {
        val promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set("" + r.get()));
        assertStrings("null", ref.get());
    }
    
    @Test
    public void testCancel() {
        val promise = Promise.ofAborted();
        assertEquals (PromiseStatus.ABORTED,                                                     promise.getStatus());
        assertStrings("Result:{ Exception: functionalj.types.result.ResultCancelledException }", promise.getCurrentResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set("" + r.get()));
        assertStrings("null", ref.get());
    }
    
    @Test
    public void testCreateNew_complete() {
        val list = new ArrayList<String>();
        
        val promiseControl = DeferAction.of(String.class);
        val promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        val pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        promise.subscribe(r -> list.add("1: " + r.toString()));
        
        pendingControl.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.subscribe(r -> list.add("2: " + r.toString()));
        
        assertStrings(
                "["
                + "1: Result:{ Value: Forty two }, "
                + "2: Result:{ Value: Forty two }"
                + "]",
              list);
    }
    
    @Test
    public void testCreateNew_exception() {
        val promiseControl = DeferAction.of(String.class);
        val promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        val pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        pendingControl.fail(new IOException());
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getCurrentResult());
    }
    
    @Test
    public void testAbort() {
        val ref = new AtomicReference<String>(null);
        val action = DeferAction.of(String.class)
                .subscribe(r -> ref.set("" + r))
                .start();
        
        assertStrings("Result:{ Exception: functionalj.types.result.ResultNotReadyException }", action.getCurentResult());
        
        action.abort();
        assertEquals("Result:{ Exception: functionalj.types.result.ResultCancelledException }", ref.get());
    }
    
    @Test
    public void testLifeCycle_multipleCall_noEffect() {
        val list = new ArrayList<String>();
        
        val deferAction   = DeferAction.of(String.class);
        val promise       = deferAction.getPromise();
        val pendingAction = deferAction.start();
        
        promise.subscribe(r -> list.add("1: " + r.toString()));
        
        pendingAction.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        promise.subscribe(r -> list.add("2: " + r.toString()));
        
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
        val list = new ArrayList<String>();
        
        val deferAction   = DeferAction.of(String.class);
        val promise       = deferAction.getPromise();
        val pendingAction = deferAction.start();
        
        val sub1 = promise.subscribe(r -> list.add("1: " + r.toString()));
        val sub2 = promise.subscribe(r -> list.add("2: " + r.toString()));
        
        sub1.unsubscribe();
        
        pendingAction.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getCurrentResult());
        
        sub2.unsubscribe();
        
        assertStrings("[2: Result:{ Value: Forty two }]", list);
    }
    
    @Test
    public void testCreateNew_lastUnsubscribed() {
        val list = new ArrayList<String>();
        
        val deferAction   = DeferAction.of(String.class);
        val promise       = deferAction.getPromise();
        val pendingAction = deferAction.start();
        
        // Last subscription at this time.
        val sub1 = promise.subscribe(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.types.result.ResultCancelledException: No more listener. }",
                promise.getCurrentResult());
        
        // This subscription will get cancelled as the result.
        val sub2 = promise.subscribe(r -> list.add("2: " + r.toString()));
        sub2.unsubscribe();
        
        assertStrings(
                "[2: Result:{ Exception: functionalj.types.result.ResultCancelledException: No more listener. }]",
              list);
    }
    
    @Test
    public void testCreateNew_unsubscribed_withEavesdrop() {
        val list = new ArrayList<String>();
        
        val deferAction   = DeferAction.of(String.class);
        val promise       = deferAction.getPromise();
        val pendingAction = deferAction.start();
        
        // Add an eavesdrop
        promise.eavesdrop(r -> list.add("e: " + r.toString()));
        
        // Last subscription at this time as an eavesdrop does not count.
        val sub1 = promise.subscribe(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        
        // Complete -- but this is too late.
        pendingAction.complete("Forty two");
        
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        assertStrings(
                "Result:{ Exception: functionalj.types.result.ResultCancelledException: No more listener. }",
                promise.getCurrentResult());
        
        assertStrings("[e: Result:{ Exception: functionalj.types.result.ResultCancelledException: No more listener. }]", list);
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
        
        val promise = DeferAction.of(String.class)
                .abortNoSubsriptionAfter(wait)
                .eavesdrop(r -> list.add("e: " + r.toString()))
                .start()
                .getPromise();
        
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals (PromiseStatus.ABORTED, promise.getStatus());
        
        assertStrings("[e: Result:{ Exception: functionalj.types.result.ResultCancelledException: No more listener. }]", list);
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
        
        val promise = DeferAction.of(String.class)
                .abortNoSubsriptionAfter(wait)
                .eavesdrop(r -> list.add("e: " + r.toString()))
                .subscribe(r -> list.add("s: " + r.toString()))
                .start()
                .getPromise();
        
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        onExpireds.forEach(c -> c.accept(null, null));
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
    }
    
    @Test
    public void testCreateNew_map_mapBeforeComplete() {
        val list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
            .map(String::length)
            .subscribe(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_map2_mapAfterComplete() {
        val list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
            .map(String::length)
            .subscribe(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_flatMap() {
        val list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
                .flatMap(str -> Promise.ofValue(str.length()))
                .subscribe(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!!");
        
        assertStrings("[Result:{ Value: 6 }]", list);
    }
    
    @Test
    public void testCreateNew_filter() {
        val list = new ArrayList<String>();
        
        DeferAction.of(String.class)
        .use(promise -> {
            promise
                .filter(str -> str.length() < 3)
                .subscribe(r -> list.add(r.toString()));
            
            promise
                .filter(str -> str.length() > 3)
                .subscribe(r -> list.add(r.toString()));
        })
        .start()
        .complete("Done!");
        
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
        
        DeferAction.of(String.class)
        .use(promise -> promise.subscribe(wait.orDefaultTo("Not done."), r -> list.add(r.get())))
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
    	val parentPromise = DeferAction.of(String.class)
    			.getPromise();
    	
    	val childPromise = parentPromise.map(String::length);
    	
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
    	val parentPromise = DeferAction.of(String.class)
    			.getPromise();
    	
    	val childPromise = parentPromise.map(String::length);
    	
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
    
}
