package functionalj.annotations.struct;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

@SuppressWarnings("javadoc")
public class DOWithSameClassName {
    
    @Struct
    public static interface DOSameName {
        
        public String name();
        
    }
    
    @Test
    public void testSameName() {
        val obj = new functionalj.annotations.struct.DOSameName("Obj");
        assertEquals("Obj", obj.name());
    }
    
}
