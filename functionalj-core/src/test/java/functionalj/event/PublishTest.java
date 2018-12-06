package functionalj.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import lombok.val;

public class PublishTest {

    @Test
    public void testBasic() {
        val logsValue  = new ArrayList<String>();
        val logsResult = new ArrayList<String>();
        val logsHalf   = new ArrayList<String>();
        
        val publisher     = new Publisher<String>();
        val topic         = publisher.getTopic();
        val subscription1 = topic.subscribe(value  -> { logsValue .add(value .toString()); });
        val subscription2 = topic.onNext   (result -> { logsResult.add(result.toString()); });
        val subscription3 = topic.onNext   (result -> { logsHalf  .add(result.toString()); });
        
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
        val logs = new ArrayList<String>();
        
        val publisher    = new Publisher<String>();
        val topic1       = publisher.getTopic();
        val topic2       = topic1.map(String::length);
        val subscription = topic2.subscribe(value  -> { logs.add(value .toString()); });
        
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
        val logs = new ArrayList<String>();
        
        val publisher = new Publisher<String>();
        val topic     = publisher.getTopic().map(String::length);
        topic.subscribe(value  -> { logs .add(value .toString()); });
        
        publisher.publish("One");
        publisher.publish("Two");
        publisher.publish("Three");
        publisher.done();
        assertEquals("[3, 3, 5]", logs.toString());
    }
    
    @Test
    public void testSubUnsubscribe() {
        val logs       = new ArrayList<String>();
        val logResults = new ArrayList<String>();
        
        val publisher    = new Publisher<String>();
        val topic1       = publisher.getTopic();
        val topic2       = topic1.map(String::length);
        val subscription = topic2.subscribe(value  -> { logs.add(value .toString()); });
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
