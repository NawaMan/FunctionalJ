package functionalj.annotations.dataobject;

import java.util.List;

import functionalj.annotations.DataObject;

@SuppressWarnings("javadoc")
public class WithListTest {

    @DataObject(name="ParentWithList")
    public static interface IParent2 {
        
        public List<String> names();
        public List<Child>  children();
        
    }
}
