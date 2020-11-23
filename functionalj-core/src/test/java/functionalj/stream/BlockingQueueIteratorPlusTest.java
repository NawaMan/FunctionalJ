// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;



public class BlockingQueueIteratorPlusTest {
    
    @Test
    public void testBasic() throws InterruptedException {
        var endValue = (String)UUID.randomUUID().toString();
        var queue    = new LinkedBlockingQueue<String>();
        try (var iterator = new BlockingQueueIteratorPlus<String>(endValue, queue)) {
            
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
    }
    
    @Test
    public void testBlocking() throws InterruptedException {
        var endValue = (String)UUID.randomUUID().toString();
        var queue    = new LinkedBlockingQueue<String>();
        try (var iterator = new BlockingQueueIteratorPlus<String>(endValue, queue)) {
            
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
    }
    
    @Test
    public void testBlockingStream() throws InterruptedException {
        var endValue = (String)UUID.randomUUID().toString();
        var queue    = new LinkedBlockingQueue<String>();
        try (var iterator = new BlockingQueueIteratorPlus<String>(endValue, queue)) {
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
    
}
