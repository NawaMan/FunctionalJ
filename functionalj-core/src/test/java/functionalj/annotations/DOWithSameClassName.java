package functionalj.annotations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class DOWithSameClassName {
    
    @DataObject
    public static interface DOSameName {
        
        public String name();
        
    }
    
    @Test
    public void testSameName() {
        val obj = new functionalj.annotations.DOSameName("Obj");
        assertEquals("Obj", obj.name());
    }
    
}
