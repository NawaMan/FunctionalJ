package functionalj.annotations.record;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Record;
import functionalj.annotations.record.DOFromClass;
import lombok.val;

@SuppressWarnings("javadoc")
public class DOFromClassTest {
    
    @Record(name="DOFromClass")
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
