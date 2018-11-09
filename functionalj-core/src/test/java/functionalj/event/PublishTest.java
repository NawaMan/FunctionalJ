package functionalj.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.reactive.Publisher;
import lombok.val;

public class PublishTest {

    @Test
    public void testBasic() {
        val logsValue  = new ArrayList<String>();
        val logsResult = new ArrayList<String>();
        val logsHalf   = new ArrayList<String>();
        
        val publisher    = new Publisher<String>();
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
                + "Result:{ Exception: functionalj.result.NoMoreResultException }"
                + "]", logsResult.toString());
        assertEquals("["
                + "Result:{ Value: One }, "
                + "Result:{ Exception: functionalj.result.NoMoreResultException }"
                + "]", logsHalf.toString());
        
        assertFalse(publisher.isActive());
        assertFalse(publisher.getTopic().isActive());
        assertFalse(subscription1.isActive());
        assertFalse(subscription2.isActive());
        assertFalse(subscription3.isActive());
    }

}
