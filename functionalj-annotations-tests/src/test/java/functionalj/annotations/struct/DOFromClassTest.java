package functionalj.annotations.struct;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Struct;
import functionalj.types.struct.DOFromClass;
import lombok.val;

@SuppressWarnings("javadoc")
public class DOFromClassTest {
    
    @Struct(name="DOFromClass")
    public abstract static class DOFromClassDef {
        
        public abstract String name();
        public abstract int    count();
        
        public String nameUpperCase() {
            return name().toUpperCase();
        }
        
    }
    
    @Test
    public void testFromClass() {
        val obj = new DOFromClass("Obj", 5);
        assertEquals("Obj", obj.name());
        assertEquals("OBJ", obj.nameUpperCase());
    }
    
}
