// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj;

import static functionalj.function.Func.cacheFor;
import static functionalj.function.Func.lazy;
import static functionalj.function.Func.withIndex;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.junit.Test;

import lombok.val;

public class ClosureConstantTest {
    
    // Index
    @Test
    public void testWithIndex() {
        assertEquals(
                "0: One, 1: Two, 2: Three", 
                asList  ("One", "Two", "Three")
                .stream ()
                .map    (withIndex((str, idx) -> idx + ": " + str))
                .collect(joining(", ")));
    }
    
    @Test
    public void testGrouping() {
        assertEquals(
                "[One, Two], [Three, Four], [Five]", 
                asList  ("One", "Two", "Three", "Four", "Five")
                .stream ()
                .collect(groupingBy(withIndex((str, idx) -> (idx / 2)))).values()
                .stream ()
                .map    (each -> each.toString())
                .collect(joining(", ")));
    }
    
    // Cache
    private static Map<String, Integer> counts = new TreeMap<>();
    
    public static String count(String text) {
        counts.compute(text, (t, v) -> (v == null) ? 1 : v + 1);
        return counts.toString();
    }
    
    @Test
    public void testCache() {
        Function<String, String> f1 = cacheFor(ClosureConstantTest::count);
        assertEquals("{One=1}", f1.apply("One"));
        assertEquals("{One=1}", f1.apply("One"));
        assertEquals("{One=1, Two=1}", f1.apply("Two"));
        assertEquals("14", f1.andThen(String::length).andThen(String::valueOf).apply("Two"));
        Function<String, String> f2 = cacheFor(ClosureConstantTest::count);
        assertEquals("{One=2, Two=1}", f2.apply("One"));
        assertEquals("{One=2, Two=1}", f2.apply("One"));
        assertEquals("{One=2, Two=2}", f2.apply("Two"));
    }
    
    @Test
    public void testLazy() throws InterruptedException {
        val threadCount = 10;
        val callPerThread = 50;
        val c = new AtomicInteger();
        val counter = lazy(() -> {
            return c.incrementAndGet();
        });
        sleep5();
        // Ensure no running before first call to the counter.
        assertEquals(0, c.intValue());
        val latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int l = 0; l < callPerThread; l++) {
                    sleep5();
                    // Making the call.
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
            e.printStackTrace();
        }
    }
}
