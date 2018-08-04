package functionalj.annotations.dataobject;

import java.util.Map;

import functionalj.annotations.DataObject;

@SuppressWarnings("javadoc")
public class WithMapTest {

    @DataObject(name="ParentWithMap")
    public static interface IParent4 {
        
        public Map<String, Child> children();
        
    }
}
