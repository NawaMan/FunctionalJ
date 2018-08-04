package functionalj.annotations.dataobject;

import functionalj.annotations.DataObject;
import functionalj.types.map.FuncMap;

@SuppressWarnings("javadoc")
public class WithFuncMapTest {

    @DataObject(name="ParentWithFuncMap")
    public static interface IParent4 {
        
        public FuncMap<String, Child> children();
        
    }
}
