// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.doublelist;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.Test;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public class StreamBackedDoubleFuncListTest {
    
    @Test
    public void testPeekOne() {
        // This test is to check that the stream is only used once.
        // This is done by peeking and check what is peeked.
        List<String> logs = new ArrayList<String>();
        DoubleStreamPlus stream = DoubleStreamPlus.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).peek(v -> logs.add("" + v));
        DoubleFuncList list = new StreamBackedDoubleFuncList(stream);
        assertEquals("[1.0, 2.0, 3.0]", list.doubleStream().limit(3).toListString());
        assertEquals("[1.0, 2.0, 3.0]", list.limit(3).toListString());
        assertEquals("[1.0, 2.0, 3.0]", logs.toString());
        logs.clear();
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0]", list.doubleStream().limit(5).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0]", list.limit(5).toListString());
        // One, Two, Three are not peeked again.
        assertEquals("[4.0, 5.0]", logs.toString());
        logs.clear();
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0]", list.doubleStream().limit(7).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0]", list.limit(7).toListString());
        // One, Two, Three, Four, Five are not peeked again.
        assertEquals("[6.0, 7.0]", logs.toString());
        logs.clear();
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", list.doubleStream().limit(10).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", list.limit(10).toListString());
        // One, Two, Three, Four, Five, Six, Seven are not peeked again.
        assertEquals("[8.0, 9.0, 10.0]", logs.toString());
    }
    
    @Test
    public void testAppend() {
        val stream = DoubleStreamPlus.of(1L, 2L, 3L, 4L, 5L);
        val list = new StreamBackedDoubleFuncList(stream);
        assertEquals("[1.0, 2.0, 3.0]", list.limit(3).toListString());
        val list2 = list.appendAll(6, 7);
        assertEquals("[1.0, 2.0, 3.0, 4.0]", list2.limit(4).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0]", list2.limit(5).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0]", list2.limit(6).toListString());
        assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0]", list2.toListString());
    }
    
    @Test
    public void testThreadSafety() {
        val size = 100_000;
        val stream = LongStreamPlus.infiniteLong().asDoubleStream().limit(size);
        val list = new StreamBackedDoubleFuncList(stream);
        val executor = Executors.newFixedThreadPool(50);
        val expected = LongStreamPlus.infiniteLong().limit(size).asDoubleStream().toListString();
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
