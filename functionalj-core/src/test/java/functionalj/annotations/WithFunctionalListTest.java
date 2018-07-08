package functionalj.annotations;

import static functionalj.annotations.ParentWithFunctionalList.theParentWithFunctionalList;

import functionalj.types.FunctionalList;
import functionalj.types.ImmutableList;

@SuppressWarnings("javadoc")
public class WithFunctionalListTest {

    @DataObject(name="ParentWithFunctionalList")
    public static interface IParent2 {
        
        public FunctionalList<String> names();
        public FunctionalList<Child>  children();
        
    }
    
}
