package functionalj.annotations.record;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Record;
import functionalj.annotations.record.Child;
import functionalj.annotations.record.Parent;
import lombok.val;

@SuppressWarnings("javadoc")
public class SubDOTest {
    
    @Record(name="Child")
    public static interface IChild {
        
        String name();
        
    }
    @Record(name="Parent")
    public static interface IParent {
        
        String name();
        Child  child();
        
    }
    
    
    @Test
    public void testParentChild() {
        val parent = new Parent("John", new Child("Greg"));
        assertEquals("Greg", parent.child().name());
    }
    
}
