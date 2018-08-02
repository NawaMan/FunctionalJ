package functionalj.annotations;

import functionalj.types.map.FuncMap;

@SuppressWarnings("javadoc")
public class WithFuncMapTest {

    @DataObject(name="ParentWithFunctionalMap")
    public static interface IParent4 {
        
        public FuncMap<String, Child> children();
        
    }
}
