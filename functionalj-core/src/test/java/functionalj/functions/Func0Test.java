package functionalj.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.function.Func0;
import lombok.val;

public class Func0Test {

    @Test
    @SuppressWarnings("null")
    public void testElseUse() throws Exception {
        val str  = (String)null;
        val func = (Func0<Integer>)(()->{
            return str.length();
        });
        assertTrue  (func.getSafely().isException());
        assertEquals(0, func.whenwhenUse(0).get().intValue());
    }
    
}
