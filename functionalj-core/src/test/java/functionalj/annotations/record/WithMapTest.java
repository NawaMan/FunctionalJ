package functionalj.annotations.record;

import java.util.Map;

import functionalj.annotations.Record;
import functionalj.annotations.record.Child;

@SuppressWarnings("javadoc")
public class WithMapTest {

    @Record(name="ParentWithMap")
    public static interface IParent4 {
        
        public Map<String, Child> children();
        
    }
}
