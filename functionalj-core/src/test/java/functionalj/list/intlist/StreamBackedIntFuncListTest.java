package functionalj.list.intlist;

import static functionalj.stream.intstream.IntStreamPlus.infiniteInt;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.Test;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class StreamBackedIntFuncListTest {

    @Test
    public void testPeekOne() {
        // This test is to check that the stream is only used once.
        // This is done by peeking and check what is peeked.
        List<String> logs = new ArrayList<String>();
        IntStreamPlus stream = IntStreamPlus.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).peek(v -> logs.add("" + v));
        IntFuncList list = new StreamBackedIntFuncList(stream);
        assertEquals("[1, 2, 3]", list.intStream().limit(3).toListString());
        assertEquals("[1, 2, 3]", list.limit(3).toListString());
        assertEquals("[1, 2, 3]", logs.toString());
        logs.clear();
        assertEquals("[1, 2, 3, 4, 5]", list.intStream().limit(5).toListString());
        assertEquals("[1, 2, 3, 4, 5]", list.limit(5).toListString());
        // One, Two, Three are not peeked again.
        assertEquals("[4, 5]", logs.toString());
        logs.clear();
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list.intStream().limit(7).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list.limit(7).toListString());
        // One, Two, Three, Four, Five are not peeked again.
        assertEquals("[6, 7]", logs.toString());
        logs.clear();
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.intStream().limit(10).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.limit(10).toListString());
        // One, Two, Three, Four, Five, Six, Seven are not peeked again.
        assertEquals("[8, 9, 10]", logs.toString());
    }

    @Test
    public void testAppend() {
        val stream = IntStreamPlus.of(1, 2, 3, 4, 5);
        val list = new StreamBackedIntFuncList(stream);
        assertEquals("[1, 2, 3]", list.limit(3).toListString());
        val list2 = list.appendAll(6, 7);
        assertEquals("[1, 2, 3, 4]", list2.limit(4).toListString());
        assertEquals("[1, 2, 3, 4, 5]", list2.limit(5).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6]", list2.limit(6).toListString());
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", list2.toListString());
    }

    @Test
    public void testThreadSafety() {
        val size = 100_000;
        val stream = infiniteInt().limit(size);
        val list = new StreamBackedIntFuncList(stream);
        val executor = Executors.newFixedThreadPool(50);
        val expected = IntStreamPlus.infiniteInt().limit(size).toListString();
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
