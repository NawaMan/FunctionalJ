package functionalj.functions;

import static functionalj.function.Absent.__;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.pipeable.Pipeable.StartWtih;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.function.Func;
import lombok.val;

public class Func1Test {
    
    @Test
    public void testElevate() {
        val concat = Func.of(String::concat);
        val appendSpace       = concat.elevateWith(" ");
        val appendWorld       = concat.elevateWith("World");
        val appendExclamation = concat.bind(__, "!");
        val str = StartWtih("Hello").pipe(
                    appendSpace,
                    appendWorld,
                    appendExclamation
                );
        assertEquals("Hello World!", str);
    }
    
    @Test
    public void testDefer() {
        val startTime = System.currentTimeMillis();
        
        val length =   Sleep(50).then(String::valueOf).async().apply("Hello!").getPromise()
                .chain(Sleep(50).then(String::length ).async())
                .getResult();
        
        val duration = System.currentTimeMillis() - startTime;
        assertEquals(6, length.get().intValue());
        
        assertTrue(duration > 100);
    }
    
}
