package functionalj.annotations;

import static functionalj.annotations.ParentWithFunctionalList.theParentWithFunctionalList;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.list.ImmutableList;

public class WithFunctionalListTestTest {
    
    @Test
    public void testAccessToLens() {
        ParentWithFunctionalList parent = new ParentWithFunctionalList(
                ImmutableList.of("One", "Two", "Three", "Four"), 
                ImmutableList.empty());
        assertEquals(
                "[One, Two, Three, Four]",
                "" + theParentWithFunctionalList
                    .names
                    .apply(parent));
        
        assertEquals(
                "[(One,3), (Two,3), (Three,5), (Four,4)]",
                "" + parent.names().mapTuple(theString, theString.length()));
    }
    
}
