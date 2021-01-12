package functionalj.stream;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class AsStreamPlusTest {
    
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testToFuncList_finiteStream() {
        assertStrings("[0, 0, 0, 0, 0]", StreamPlus.generate(() -> 0).limit(5).toFuncList());
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
