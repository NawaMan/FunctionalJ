package functionalj.lens;

import static functionalj.lens.Access.theString;

import org.junit.Assert;
import org.junit.Test;

import lombok.val;

public class PrimitiveAccessTest {

    @Test
    public void testIntAccess() {
        // To properly test this, you have to put a breakpoint Integer#valueOf(int i) and make sure it was never called.
        val str       = "String";
        val strLength = theString.length();
        Assert.assertEquals(6, strLength.applyAsInt(str));
    }
    
    // TODO - Null 
}
