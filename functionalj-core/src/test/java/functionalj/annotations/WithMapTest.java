package functionalj.annotations;

import java.util.Map;

@SuppressWarnings("javadoc")
public class WithMapTest {

    @DataObject(name="ParentWithMap")
    public static interface IParent4 {
        
        public Map<String, Child> children();
        
    }
}
