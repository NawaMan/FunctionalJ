package functionalj.pipeable;

import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.pipeable.Catch.orElse;
import static org.junit.Assert.*;

import org.junit.Test;

import functionalj.lens.Access;
import lombok.val;

public class PipeLineTest {
    
    @Test
    public void testBasic() {
        val pipeLine = PipeLine.starting
            .with(theString.length())
            .then(theInteger.add(10))
            .then(theInteger.asString())
            .thenReturn();
        
        assertEquals("14", pipeLine.apply("Test"));
    }
    
    @Test
    public void testError() {
        val pipeLine = PipeLine.starting
            .with(theString.length())
            .then(theInteger.add(10))
            .then(theInteger.asString())
            .thenCatch(orElse("<none>"));
        
        assertEquals("<none>", pipeLine.apply(null));
    }
    
}
