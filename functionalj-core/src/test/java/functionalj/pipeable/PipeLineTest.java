package functionalj.pipeable;

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.pipeable.WhenNull.defaultTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

@SuppressWarnings("javadoc")
public class PipeLineTest {
    
    @Test
    public void testBasic() {
        val pipeLine = PipeLine
                .from(theString.length())
                .then(theInteger.multiply(2))
                .then(theInteger.asString())
                .thenReturn();
        val pipeLine2 = PipeLine
                .from(theString.toUpperCase())
                .thenReturn();
        
        val str = Pipeable.of("Test");
        assertEquals("8",    pipeLine.apply("Test"));
        assertEquals("TEST", pipeLine2.apply("Test"));
        assertEquals("8",    str.pipe(pipeLine));
        assertEquals("TEST", str.pipe(pipeLine2));
        assertEquals("8",    str.pipe(pipeLine, pipeLine2));
        
        val strNull = Pipeable.of((String)null);
        assertEquals(null, pipeLine.applyToNull());
        assertEquals(null, pipeLine2.applyToNull());
        assertEquals(null, strNull.pipe(pipeLine));
        assertEquals(null, strNull.pipe(pipeLine2));
        assertEquals(null, strNull.pipe(pipeLine, pipeLine2));
    }
    
    @Test
    public void testHandlingNull() {
        val pipeLine = PipeLine.ofNullable(String.class)
                .then(theString.length())
                .then(theInteger.multiply(2))
                .then(theInteger.asString())
                .thenReturnOrElse("<none>");
        val pipeLine2 = PipeLine
                .from(theString.toUpperCase())
                .thenReturn();
        
        assertEquals("<none>", pipeLine.applyToNull());
        assertEquals(null,     pipeLine2.applyToNull());
        
        val str = Pipeable.of((String)null);
        assertEquals("<none>", str.pipe(pipeLine));
        assertEquals(null,     str.pipe(pipeLine2));
        assertEquals("<NONE>", str.pipe(pipeLine, pipeLine2));
    }
    
    @Test
    public void testHandlingNullCombine() {
        val pipeLine = PipeLine.ofNullable(String.class)
                .then($S.length())
                .then($I.multiply(2))
                .then($I.asString())
                .then(defaultTo("<none>"))
                .then($S.toUpperCase())
                .thenReturn();

        assertEquals("<NONE>", pipeLine.applyToNull());
        
        val str = Pipeable.of((String)null);
        assertEquals("<NONE>", str.pipe(pipeLine));
    }
    
}
