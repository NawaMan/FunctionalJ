package functionalj.annotations.struct;

import java.util.List;

import functionalj.types.Struct;
import functionalj.types.struct.Child;

@SuppressWarnings("javadoc")
public class WithListTest {

    @Struct(name="ParentWithList")
    public static interface IParent2 {
        
        public List<String> names();
        public List<Child>  children();
        
    }
}
