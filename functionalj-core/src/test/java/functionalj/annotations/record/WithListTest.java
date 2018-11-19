package functionalj.annotations.record;

import java.util.List;

import functionalj.annotations.Record;
import functionalj.annotations.record.Child;

@SuppressWarnings("javadoc")
public class WithListTest {

    @Record(name="ParentWithList")
    public static interface IParent2 {
        
        public List<String> names();
        public List<Child>  children();
        
    }
}
