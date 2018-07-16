package functionalj.annotations;

import functionalj.types.map.FunctionalMap;

@SuppressWarnings("javadoc")
public class WithFunctionalMapTest {

    @DataObject(name="ParentWithFunctionalMap")
    public static interface IParent4 {
        
        public FunctionalMap<String, Child> children();
        
    }
}
