package functionalj.annotations.dataobject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.dataobject.DOFromClass;
import lombok.val;

@SuppressWarnings("javadoc")
public class DOFromClassTest {
    
    @DataObject(name="DOFromClass")
    public abstract static class DOFromClassDef {
        
        public abstract String name();
        
        public String nameUpperCase() {
            return name().toUpperCase();
        }
        
    }
    
    @Test
    public void testFromClass() {
        val obj = new DOFromClass("Obj");
        assertEquals("Obj", obj.name());
        assertEquals("OBJ", obj.nameUpperCase());
    }
    
}
