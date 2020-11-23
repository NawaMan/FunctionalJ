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
package functionalj.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;



public class PublishTest {

    @Test
    public void testBasic() {
        var logsValue  = new ArrayList<String>();
        var logsResult = new ArrayList<String>();
        var logsHalf   = new ArrayList<String>();
        
        var publisher     = new Publisher<String>();
        var topic         = publisher.getTopic();
        var subscription1 = topic.subscribe(value  -> { logsValue .add(value .toString()); });
        var subscription2 = topic.onNext   (result -> { logsResult.add(result.toString()); });
        var subscription3 = topic.onNext   (result -> { logsHalf  .add(result.toString()); });
        
        publisher.publish("One");
        subscription3.unsubcribe();
        
        publisher.publish("Two");
        publisher.done();
        
        assertEquals("[One, Two]", logsValue.toString());
        assertEquals("["
                + "Result:{ Value: One }, "
                + "Result:{ Value: Two }, "
                + "Result:{ NoMoreResult }"
                + "]", logsResult.toString());
        assertEquals("["
                + "Result:{ Value: One }, "
                + "Result:{ NoMoreResult }"
                + "]", logsHalf.toString());
        
        assertFalse(publisher.isActive());
        assertFalse(publisher.getTopic().isActive());
        assertFalse(subscription1.isActive());
        assertFalse(subscription2.isActive());
        assertFalse(subscription3.isActive());
    }
    
    @Test
    public void testUnsubscribe() {
        var logs = new ArrayList<String>();
        
        var publisher    = new Publisher<String>();
        var topic1       = publisher.getTopic();
        var topic2       = topic1.map(String::length);
        var subscription = topic2.subscribe(value  -> { logs.add(value .toString()); });
        
        publisher.publish("One");
        publisher.publish("Two");
        assertTrue(publisher.isActive());
        subscription.unsubcribe();
        assertFalse(publisher.isActive());
        
        publisher.publish("Three");
        publisher.done();
        assertEquals("[3, 3]", logs.toString());
    }
    
    @Test
    public void testMap() {
        var logs = new ArrayList<String>();
        
        var publisher = new Publisher<String>();
        var topic     = publisher.getTopic().map(String::length);
        topic.subscribe(value  -> { logs .add(value .toString()); });
        
        publisher.publish("One");
        publisher.publish("Two");
        publisher.publish("Three");
        publisher.done();
        assertEquals("[3, 3, 5]", logs.toString());
    }
    
    @Test
    public void testSubUnsubscribe() {
        var logs       = new ArrayList<String>();
        var logResults = new ArrayList<String>();
        
        var publisher    = new Publisher<String>();
        var topic1       = publisher.getTopic();
        var topic2       = topic1.map(String::length);
        var subscription = topic2.subscribe(value  -> { logs.add(value .toString()); });
        topic2.onNext(result -> { logResults.add(result.toString()); });
        
        publisher.publish("One");
        publisher.publish("Two");
        subscription.unsubcribe();
        
        publisher.publish("Three");
        publisher.done();
        assertEquals("[3, 3]", logs.toString());
        assertEquals("["
                + "Result:{ Value: 3 }, "
                + "Result:{ Value: 3 }, "
                + "Result:{ Value: 5 }, "
                + "Result:{ NoMoreResult }]",
                logResults.toString());
    }
}
