package functionalj.stream;

import static functionalj.TestHelper.assertAsString;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class AsStreamPlusTest {
    
    
    @Test
    public void testToFuncList_finiteStream() {
        assertAsString("[0, 0, 0, 0, 0]", StreamPlus.generate(() -> 0).limit(5).toFuncList());
    }
    
    @Ignore("Too long - run manually.")
    @Test
    public void testToFuncList_infiniteStream() {
        try {
            StreamPlus.generate(()->0).toFuncList();
            fail("It is impossible!!");
        } catch (OutOfMemoryError e) {
            // Expected
        }
    }
    
}
