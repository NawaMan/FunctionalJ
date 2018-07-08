package functionalj.annotations;

import static functionalj.annotations.ParentWithFunctionalList.theParentWithFunctionalList;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.types.ImmutableList;

public class WithFunctionalListTestTest {
    
    @Test
    public void testAccessToLens() {
        assertEquals(
                "[One, Two]",
                "" + theParentWithFunctionalList
                    .names
                    .apply(new ParentWithFunctionalList(
                            ImmutableList.of("One", "Two"), 
                            ImmutableList.empty())));
    }
}
