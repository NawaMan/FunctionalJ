package functionalj.list;

import static functionalj.stream.intstream.IntStreamPlus.infiniteInt;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.Test;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class StreamBackedFuncListTest {
    
    @Test
    public void testPeekOne() {
        // This test is to check that the stream is only used once.
        // This is done by peeking and check what is peeked.
        val logs = new ArrayList<String>();
        val stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten").peek(logs::add);
        val list = new StreamBackedFuncList<String>(stream);
        assertEquals("[One, Two, Three]", list.stream().limit(3).toListString());
        assertEquals("[One, Two, Three]", list.limit(3).toListString());
        assertEquals("[One, Two, Three]", logs.toString());
        logs.clear();
        assertEquals("[One, Two, Three, Four, Five]", list.stream().limit(5).toListString());
        assertEquals("[One, Two, Three, Four, Five]", list.limit(5).toListString());
        // One, Two, Three are not peeked again.
        assertEquals("[Four, Five]", logs.toString());
        logs.clear();
        assertEquals("[One, Two, Three, Four, Five, Six, Seven]", list.stream().limit(7).toListString());
        assertEquals("[One, Two, Three, Four, Five, Six, Seven]", list.limit(7).toListString());
        // One, Two, Three, Four, Five are not peeked again.
        assertEquals("[Six, Seven]", logs.toString());
        logs.clear();
        assertEquals("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list.stream().limit(10).toListString());
        assertEquals("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list.limit(10).toListString());
        // One, Two, Three, Four, Five, Six, Seven are not peeked again.
        assertEquals("[Eight, Nine, Ten]", logs.toString());
    }
    
    @Test
    public void testAppend() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
        val list = new StreamBackedFuncList<String>(stream);
        assertEquals("[One, Two, Three]", list.limit(3).toListString());
        val list2 = list.appendAll("Six", "Seven");
        assertEquals("[One, Two, Three, Four]", list2.limit(4).toListString());
        assertEquals("[One, Two, Three, Four, Five]", list2.limit(5).toListString());
        assertEquals("[One, Two, Three, Four, Five, Six]", list2.limit(6).toListString());
        assertEquals("[One, Two, Three, Four, Five, Six, Seven]", list2.toListString());
    }
    
    @Test
    public void testThreadSafety() {
        val size = 100_000;
        val stream = infiniteInt().limit(size).boxed();
        val list = new StreamBackedFuncList<Integer>(stream);
        val executor = Executors.newFixedThreadPool(50);
        val expected = IntStreamPlus.infiniteInt().limit(size).boxed().toListString();
        assertEquals(expected, list.toListString());
        val threads = 100;
        val endLatch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                assertEquals(expected, list.toListString());
                endLatch.countDown();
            });
        }
        try {
            endLatch.await();
        } catch (InterruptedException e) {
        }
        executor.shutdown();
    }
}
