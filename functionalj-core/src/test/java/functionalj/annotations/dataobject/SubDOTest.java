package functionalj.annotations.dataobject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.dataobject.Child;
import functionalj.annotations.dataobject.Parent;
import lombok.val;

@SuppressWarnings("javadoc")
public class SubDOTest {
    
    @DataObject(name="Child")
    public static interface IChild {
        
        public String name();
        
    }
    @DataObject(name="Parent")
    public static interface IParent {
        
        public String name();
        public Child  child();
        
    }
    
    
    @Test
    public void testParentChild() {
        val parent = new Parent("John", new Child("Greg"));
        assertEquals("Greg", parent.child().name());
    }
    
}
