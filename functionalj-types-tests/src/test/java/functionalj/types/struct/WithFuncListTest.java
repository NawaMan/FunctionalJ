package functionalj.types.struct;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.types.Struct;
import functionalj.types.struct.Child;
import functionalj.types.struct.ParentWithFuncList;

@SuppressWarnings("javadoc")
public class WithFuncListTest {

    @Struct(name="ParentWithFuncList")
    public static interface IParent2 {
        
        public FuncList<String> names();
        public FuncList<Child>  children();
        
    }
    
    @Test
    public void testAccessToLens() {
        ParentWithFuncList parent = new ParentWithFuncList(
                ImmutableList.of("One", "Two", "Three", "Four"), 
                ImmutableList.empty());
        assertEquals(
                "[One, Two, Three, Four]",
                "" + ParentWithFuncList.theParentWithFuncList
                    .names
                    .apply(parent));
        
        assertEquals(
                "[(One,3), (Two,3), (Three,5), (Four,4)]",
                "" + parent.names().mapTuple(theString, theString.length()));
    }
    
}
