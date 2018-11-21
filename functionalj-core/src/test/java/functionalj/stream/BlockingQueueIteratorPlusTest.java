package functionalj.stream;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import lombok.val;

public class BlockingQueueIteratorPlusTest {
    
    @Test
    public void testBasic() throws InterruptedException {
        val endValue = (String)UUID.randomUUID().toString();
        val queue    = new LinkedBlockingQueue<String>();
        val iterator = new BlockingQueueIteratorPlus<String>(endValue, queue);
        
        queue.put("One");
        assertEquals("Result:{ Value: One }", 
                iterator
                .pullNext()
                .map(String::valueOf)
                .toString());
        
        queue.put("Two");
        queue.put("Three");
        assertEquals("Result:{ Value: [Two, Three] }", 
                iterator
                .pullNext(2)
                .map(IteratorPlus::stream)
                .map(StreamPlus::toJavaList)
                .map(String::valueOf)
                .toString());
    }
    
    @Test
    public void testBlocking() throws InterruptedException {
        val endValue = (String)UUID.randomUUID().toString();
        val queue    = new LinkedBlockingQueue<String>();
        val iterator = new BlockingQueueIteratorPlus<String>(endValue, queue);
        
        queue.put("One");
        queue.put("Two");
        queue.put("Three");
        
        new Thread(()->{
            try {
                Thread.sleep(50);
                queue.put("Four");
                queue.put("Five");
                queue.put("Six");
            } catch (InterruptedException e) {
            }
        }).start();
        
        assertEquals("Result:{ Value: [One, Two, Three, Four, Five] }", 
                iterator
                .pullNext(5)
                .map(IteratorPlus::stream)
                .map(StreamPlus::toJavaList)
                .map(String::valueOf)
                .toString());
    }
    
    @Test
    public void testBlockingStream() throws InterruptedException {
        val endValue = (String)UUID.randomUUID().toString();
        val queue    = new LinkedBlockingQueue<String>();
        val iterator = new BlockingQueueIteratorPlus<String>(endValue, queue);
        
        queue.put("One");
        queue.put("Two");
        queue.put("Three");
        
        new Thread(()->{
            try {
                Thread.sleep(50);
                queue.put("Four");
                queue.put("Five");
                queue.put("Six");
                queue.put(endValue);
            } catch (InterruptedException e) {
            }
        }).start();
        
        assertEquals("[One, Two, Three, Four, Five, Six]", 
                iterator.stream().toJavaList().toString());
    }
    
}
