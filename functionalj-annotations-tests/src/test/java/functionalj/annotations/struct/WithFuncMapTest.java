package functionalj.annotations.struct;

import functionalj.map.FuncMap;
import functionalj.types.Struct;
import functionalj.types.struct.Child;

@SuppressWarnings("javadoc")
public class WithFuncMapTest {

    @Struct(name="ParentWithFuncMap")
    public static interface IParent4 {
        
        public FuncMap<String, Child> children();
        
    }
}
