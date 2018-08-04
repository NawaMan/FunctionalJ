package functionalj.annotations.dataobject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.annotations.IPostReConstruct;
import functionalj.annotations.dataobject.SimpleWithPostReConstruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class WithPostReConstruct {
    
    private static List<String> logs = new ArrayList<>();
    
    @DataObject(name="SimpleWithPostReConstruct")
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
