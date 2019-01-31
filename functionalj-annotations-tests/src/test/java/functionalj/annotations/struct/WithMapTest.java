package functionalj.annotations.struct;

import java.util.Map;

import functionalj.types.Struct;
import functionalj.types.struct.Child;

@SuppressWarnings("javadoc")
public class WithMapTest {

    @Struct(name="ParentWithMap")
    public static interface IParent4 {
        
        public Map<String, Child> children();
        
    }
}
