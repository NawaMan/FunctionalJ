package functionalj.stream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.stream.longstream.LongStep;


public class LongStepTest {
    
    @Test
    public void testFromTo() {
        assertEquals("[0, 7, 14, 21, 28]",     LongStep.of(7).to(34).toList().toString());
        assertEquals("[0, 7, 14, 21, 28, 35]", LongStep.of(7).to(35).toList().toString());
        
        assertEquals("[7, 14, 21, 28]",     LongStep.of(7).startFrom(7).to(34).toList().toString());
        assertEquals("[7, 14, 21, 28, 35]", LongStep.of(7).startFrom(7).to(35).toList().toString());
        
        assertEquals("[7, 0, -7, -14, -21, -28]",      LongStep.of(7).startFrom(7).to(-34).toList().toString());
        assertEquals("[7, 0, -7, -14, -21, -28, -35]", LongStep.of(7).startFrom(7).to(-35).toList().toString());
    }
    
}
