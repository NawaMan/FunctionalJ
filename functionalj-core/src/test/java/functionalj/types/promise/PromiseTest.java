package functionalj.types.promise;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import org.junit.Test;

import lombok.val;

public class PromiseTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testValue() {
        val promise = Promise.ofValue("Hello!");
        assertEquals (PromiseStatus.COMPLETED,    promise.getStatus());
        assertStrings("Result:{ Value: Hello! }", promise.getResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set(r.get()));
        assertStrings("Hello!", ref);
    }
    
    @Test
    public void testException() {
        val promise = Promise.ofException(new IOException());
        assertEquals (PromiseStatus.COMPLETED,                     promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set("" + r.get()));
        assertEquals("null", ref.get());
    }
    
    @Test
    public void testCancel() {
        val promise = Promise.ofAborted();
        assertEquals (PromiseStatus.ABORTED,                                                     promise.getStatus());
        assertStrings("Result:{ Exception: functionalj.types.result.ResultCancelledException }", promise.getResult());
        
        val ref = new AtomicReference<String>(null);
        promise.subscribe(r -> ref.set("" + r.get()));
        assertEquals("null", ref.get());
    }
    
    @Test
    public void testCreateNew_complete() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        val pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        promise.subscribe(r -> list.add("1: " + r.toString()));
        
        pendingControl.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getResult());
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
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        assertEquals (PromiseStatus.NOT_STARTED, promise.getStatus());
        
        val pendingControl = promiseControl.start();
        assertEquals (PromiseStatus.PENDING, promise.getStatus());
        
        pendingControl.fail(new IOException());
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Exception: java.io.IOException }", promise.getResult());
    }
    
    @Test
    public void testLifeCycle_multipleLC_call_noEffect() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        promise.subscribe(r -> list.add("1: " + r.toString()));
        
        pendingControl.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getResult());
        promise.subscribe(r -> list.add("2: " + r.toString()));
        
        
        pendingControl.complete("Forty three");
        
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
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        val sub1 = promise.subscribe(r -> list.add("1: " + r.toString()));
        sub1.unsubscribe();
        
        pendingControl.complete("Forty two");
        assertEquals (PromiseStatus.COMPLETED, promise.getStatus());
        assertStrings("Result:{ Value: Forty two }", promise.getResult());
        
        val sub2 = promise.subscribe(r -> list.add("2: " + r.toString()));
        sub2.unsubscribe();
        
        assertStrings(
                "["
                + "2: Result:{ Value: Forty two }"
                + "]",
              list);
    }
    
    @Test
    public void testCreateNew_map_mapBeforeComplete() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        promise
        .map(String::length)
        .subscribe(r -> list.add(r.toString()));
        
        pendingControl.complete("Done!");
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_map2_mapAfterComplete() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        pendingControl.complete("Done!");
        
        promise
        .map(String::length)
        .subscribe(r -> list.add(r.toString()));
        
        assertStrings("[Result:{ Value: 5 }]", list);
    }
    
    @Test
    public void testCreateNew_flatMap() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        promise
        .flatMap(str -> Promise.ofValue(str.length()))
        .subscribe(r -> list.add(r.toString()));
        
        pendingControl.complete("Done!!");
        
        assertStrings("[Result:{ Value: 6 }]", list);
    }
    
    @Test
    public void testCreateNew_filter() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        val pendingControl = promiseControl.start();
        
        pendingControl.complete("Done!");
        
        promise
            .filter(str -> str.length() < 3)
            .subscribe(r -> list.add(r.toString()));
        
        promise
            .filter(str -> str.length() > 3)
            .subscribe(r -> list.add(r.toString()));
        
        assertStrings(
                "["
                + "Result:{ Value: null }, "
                + "Result:{ Value: Done! }"
                + "]",
                list);
    }
    
    @Test
    public void testCreateNew_waitOrDefault() {
        val list = new ArrayList<String>();
        
        val promiseControl = Promise.of(String.class);
        val promise        = promiseControl.getPromise();
        
        val onExpireds = new ArrayList<BiConsumer<String, Throwable>>();
        val session = new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Throwable> onDone) {
                onExpireds.add(onDone);
            }
        };
        val wait = new WaitAwhile() {
            @Override
            public WaitSession newSession() {
                return session;
            }
        };
        
        promise.subscribe(wait.orDefaultTo("Not done."), r -> list.add(r.get()));
        onExpireds.forEach(c -> {
            c.accept(null, null);
        });
        
        assertStrings(
                "[Not done.]",
                list);
    }
    
    
}
