package functionalj.annotations.struct;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import functionalj.annotations.Struct;
import functionalj.annotations.struct.SimpleWithPostReConstruct;
import functionalj.annotations.IPostConstruct;
import lombok.val;

@SuppressWarnings("javadoc")
public class WithPostConstruct {
    
    private static List<String> logs = new ArrayList<>();
    
    @Struct(name="SimpleWithPostReConstruct")
    public static interface SimpleDOWithPostReConstruct extends IPostConstruct {
        public String name();
        
        public default void postConstruct() {
            logs.add("Hello: " + name());
        }
    }
    
    @Test
    public void testPostConstruct_runAfterWith() {
        val object = new SimpleWithPostReConstruct("Obj1");
        assertEquals("[Hello: Obj1]", logs.toString());
        
        object.withName("Object1");
        assertEquals("[Hello: Obj1, Hello: Object1]", logs.toString());
    }
}
