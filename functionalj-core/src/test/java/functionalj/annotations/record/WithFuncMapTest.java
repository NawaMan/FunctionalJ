package functionalj.annotations.record;

import functionalj.annotations.Record;
import functionalj.annotations.record.Child;
import functionalj.map.FuncMap;

@SuppressWarnings("javadoc")
public class WithFuncMapTest {

    @Record(name="ParentWithFuncMap")
    public static interface IParent4 {
        
        public FuncMap<String, Child> children();
        
    }
}
