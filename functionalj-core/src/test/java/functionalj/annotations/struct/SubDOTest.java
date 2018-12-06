package functionalj.annotations.struct;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Struct;
import functionalj.annotations.struct.Child;
import functionalj.annotations.struct.Parent;
import lombok.val;

@SuppressWarnings("javadoc")
public class SubDOTest {
    
    @Struct(name="Child")
    public static interface IChild {
        
        String name();
        
    }
    @Struct(name="Parent")
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