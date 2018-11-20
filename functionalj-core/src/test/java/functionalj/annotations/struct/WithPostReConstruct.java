package functionalj.annotations.struct;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import functionalj.annotations.Struct;
import functionalj.annotations.struct.SimpleWithPostReConstruct;
import functionalj.annotations.IPostReConstruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class WithPostReConstruct {
    
    private static List<String> logs = new ArrayList<>();
    
    @Struct(name="SimpleWithPostReConstruct")
    public static interface SimpleDOWithPostReConstruct extends IPostReConstruct {
        public String name();
        
        public default void postReConstruct() {
            logs.add("Hello!");
        }
    }
    
    @Test
    public void testPostConstruct_runAfterWith() {
        val object = new SimpleWithPostReConstruct("Obj1");
        assertEquals("[]", logs.toString());
        
        object.withName("Object1");
        assertEquals("[Hello!]", logs.toString());
    }
}
