package functionalj.stream;

import static functionalj.stream.intstream.IntStep.step;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.streamable.Streamable;


public class IntStepTest {
    
    @Test
    public void testAsStream() {
//        assertEquals("[0, 7, 14, 21, 28, 35, 42, 49, 56, 63]", IntStep.of(7).limit(10).toList().toString());
    }
    
    @Test
    public void testAsStream_withFrom() {
//        assertEquals("[2, 9, 16, 23, 30, 37, 44, 51, 58, 65]", step(7, from(2)).limit(10).toList().toString());
    }
    
    
    // TODO - Must uncomment this.
    @Test
    public void testAsRange() {
        var step = step(10);
        assertEquals(
                "[0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20]", 
                Streamable
                .infiniteInt()
                .limit(25)
                .map(step)
                .toList()
                .toString());
        
        assertEquals("{"
                + "0:[0, 1, 2, 3, 4], "
                + "10:[5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
                + "20:[15, 16, 17, 18, 19, 20, 21, 22, 23, 24]"
                + "}",
                Streamable
                .infiniteInt()
                .limit(25)
                .groupingBy(step.function())
                .sorted()
                .mapValue(stream -> stream.toList()).toString());
    }
    
}
