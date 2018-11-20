package functionalj.annotations.struct;

import functionalj.annotations.Struct;
import functionalj.annotations.struct.Child;
import functionalj.map.FuncMap;

@SuppressWarnings("javadoc")
public class WithFuncMapTest {

    @Struct(name="ParentWithFuncMap")
    public static interface IParent4 {
        
        public FuncMap<String, Child> children();
        
    }
}
