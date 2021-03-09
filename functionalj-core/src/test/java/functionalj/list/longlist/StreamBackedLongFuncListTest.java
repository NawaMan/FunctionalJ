package functionalj.list.longlist;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import org.junit.Test;

import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public class StreamBackedLongFuncListTest {
    
    @Test
    public void testPeekOne() {
        // This test is to check that the stream is only used once.
        // This is done by peeking and check what is peeked.
        
        List<String>  logs   = new ArrayList<String>();
        LongStreamPlus stream = LongStreamPlus.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L).peek(v -> logs.add("" + v));
        LongFuncList   list   = new StreamBackedLongFuncList(stream);
        
        assertEquals("[1, 2, 3]", list.longStream().limit(3).toListString());
        assertEquals("[1, 2, 3]", list             .limit(3).toListString());
        assertEquals("[1, 2, 3]", logs.toString());
        logs.clear();
        
        assertEquals("[1, 2, 3, 4, 5]", list.longStream().limit(5).toListString());
        assertEquals("[1, 2, 3, 4, 5]", list             .limit(5).toListString());
        // One, Two, Three are not peeked again.
        assertEquals("[4, 5]", logs.toString());
        logs.clear();
        
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list.longStream().limit(7).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list            .limit(7).toListString());
        // One, Two, Three, Four, Five are not peeked again.
        assertEquals("[6, 7]", logs.toString());
        logs.clear();
        
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.longStream().limit(10).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list             .limit(10).toListString());
        // One, Two, Three, Four, Five, Six, Seven are not peeked again.
        assertEquals("[8, 9, 10]", logs.toString());
    }
    
    @Test
    public void testAppend() {
        val stream = LongStreamPlus.of(1L, 2L, 3L, 4L, 5L);
        val list   = new StreamBackedLongFuncList(stream);
        
        assertEquals("[1, 2, 3]", list.limit(3).toListString());
        
        val list2 = list.appendAll(6, 7);
        assertEquals("[1, 2, 3, 4]",          list2.limit(4).toListString());
        assertEquals("[1, 2, 3, 4, 5]",       list2.limit(5).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6]",    list2.limit(6).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list2         .toListString());
    }
    
    @Test
    public void testThreadSafety() {
        val size     = 500_000;
        val stream   
                = LongStreamPlus
                .infiniteLong()
                .limit(size);
        
        val list     = new StreamBackedLongFuncList(stream);
        val executor = Executors.newFixedThreadPool(50);
        
        val expected = LongStreamPlus.infiniteLong().limit(size).toListString();
        assertEquals(expected, list.toListString());
        
        val threads  = 100;
        val endLatch = new CountDownLatch(threads);
        
        for (int i = 0; i < threads; i++) {
            executor.submit(()->{
                assertEquals(expected, list.toListString());
                endLatch.countDown();
            });
        }
        
        try { endLatch.await(); }
        catch (InterruptedException e) {}
        
        executor.shutdown();
    }
    
}
