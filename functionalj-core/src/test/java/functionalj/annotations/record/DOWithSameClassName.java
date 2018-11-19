package functionalj.annotations.record;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Record;
import lombok.val;

@SuppressWarnings("javadoc")
public class DOWithSameClassName {
    
    @Record
    public static interface DOSameName {
        
        public String name();
        
    }
    
    @Test
    public void testSameName() {
        val obj = new functionalj.annotations.record.DOSameName("Obj");
        assertEquals("Obj", obj.name());
    }
    
}
