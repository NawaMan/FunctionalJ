package functionalj.functions;

import static functionalj.environments.TimeFuncs.Sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lombok.val;

public class Func1Test {
    
    @Test
    public void testDefer() {
        val startTime = System.currentTimeMillis();
        
        val length =   Sleep(50).then(String::valueOf).defer().apply("Hello!")
                .chain(Sleep(50).then(String::length ).defer())
                .getResult();
        
        val duration = System.currentTimeMillis() - startTime;
        assertEquals(6, length.get().intValue());
        
        assertTrue(duration > 100);
    }
    
}
