package nawaman;

import static nawaman.functionalj.FunctionalJ.lazy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static org.junit.Assert.*;

import lombok.val;
import nawaman.functionalj.FunctionalJ;

public class ClosureConstantTest {
    
    @Test
    public void testLazy() throws InterruptedException {
        val c = new AtomicInteger();
        val counter = lazy(()->{
            return c.incrementAndGet();
        });
        sleep5();
        // Ensure no running before first call to the counter.
        assertEquals(0, c.intValue());
        
        val threadCount = 100;
        val latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(()->{
                for (int l = 0; l < 1000; l++) {
                    sleep5();
                    counter.get();
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        
        // Ensure that after all so many calls to it, only once does the counter run.
        assertEquals(1, counter.get().intValue());
    }

    private void sleep5() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
