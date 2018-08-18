package functionalj.types.promise;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class DeferActionTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testDeferAction() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(()->{
            Thread.sleep(100);
            return "Hello";
        })
        .subscribe(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (10*((end - start) / 10)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(150);
        assertStrings("[Start: 0, End: 100, Result: Result:{ Value: Hello }]", log);
    }
    
    @Test
    public void testDeferAction_abort() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        val action = DeferAction.run(()->{
            Thread.sleep(100);
            return "Hello";
        })
        .subscribe(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (10*((end - start) / 10)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(50);
        action.abort();
        
        assertStrings("[Start: 0, End: 50, Result: Result:{ Exception: functionalj.types.result.ResultCancelledException }]", log);
    }
    
    @Test
    public void testDeferAction_exception() throws InterruptedException {
        val log = new ArrayList<String>();
        val start = System.currentTimeMillis();
        log.add("Start: " + (start - start));
        DeferAction.run(()->{
            Thread.sleep(100);
            throw new IOException("Fail hard!");
        })
        .subscribe(result -> {
            val end = System.currentTimeMillis();
            log.add("End: " + (10*((end - start) / 10)));
            log.add("Result: " + result);
        });
        
        Thread.sleep(150);
        
        assertStrings("["
                + "Start: 0, End: 100, Result: Result:{ Exception: java.io.IOException: Fail hard! }]",
                log);
    }
    
}
