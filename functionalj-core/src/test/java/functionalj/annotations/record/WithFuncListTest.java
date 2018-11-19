package functionalj.annotations.dataobject;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.DataObject;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;

@SuppressWarnings("javadoc")
public class WithFuncListTest {

    @DataObject(name="ParentWithFuncList")
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
