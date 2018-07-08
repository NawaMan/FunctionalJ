package functionalj.annotations;

import functionalj.types.FunctionalList;

@SuppressWarnings("javadoc")
public class WithFunctionalListTest {

    @DataObject(name="ParentWithFunctionalList")
    public static interface IParent2 {
        
        public FunctionalList<String> names();
        public FunctionalList<Child>  children();
        
    }
    
}
