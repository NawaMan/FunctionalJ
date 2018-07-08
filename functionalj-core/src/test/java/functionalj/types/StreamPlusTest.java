package functionalj.types;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import lombok.val;

public class StreamPlusTest {
    
    @Test
    public void testClosed() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertEquals("[3, 3, 5]", "" + stream.map(theString.length()).toList());
        
        try {
            stream.toList();
            fail("Stream should be closed now.");
        } catch (IllegalStateException e) {
            // Expected!!
        }
    }
    
}
