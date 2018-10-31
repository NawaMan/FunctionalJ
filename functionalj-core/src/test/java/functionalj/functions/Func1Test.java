package functionalj.functions;

import static functionalj.environments.TimeFuncs.Sleep;
import static functionalj.functions.Absent.__;
import static functionalj.functions.Func.lift;
import static functionalj.pipeable.Pipeable.StartWtih;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lombok.val;

public class Func1Test {
    
    @Test
    public void testLift() {
        val concat = Func.of(String::concat);
        val appendSpace       = concat.lift(" ");
        val appendWorld       = lift(concat, "World");
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
        
        val length =   Sleep(50).then(String::valueOf).async().apply("Hello!")
                .chain(Sleep(50).then(String::length ).async())
                .getResult();
        
        val duration = System.currentTimeMillis() - startTime;
        assertEquals(6, length.get().intValue());
        
        assertTrue(duration > 100);
    }
    
}
