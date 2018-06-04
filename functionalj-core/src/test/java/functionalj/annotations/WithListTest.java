package functionalj.annotations;

import java.util.List;

@SuppressWarnings("javadoc")
public class WithListTest {

    @DataObject(name="ParentWithList")
    public static interface IParent2 {
        
        public List<String> names();
        public List<Child>  children();
        
    }
}
