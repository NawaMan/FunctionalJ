package functionalj.stream;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.stream.doublestream.DoubleStep;

public class DoubleStepTest {
    
    @Test
    public void testFromTo() {
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).to(34).toList().toString());
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).to(35).toList().toString());
        assertEquals("[0.0, 7.0, 14.0, 21.0, 28.0, 35.0]", DoubleStep.of(7).to(35).inclusive().toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).startFrom(7).to(34).toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0]", DoubleStep.of(7).startFrom(7).to(35).toList().toString());
        assertEquals("[7.0, 14.0, 21.0, 28.0, 35.0]", DoubleStep.of(7).startFrom(7).to(35).inclusive().toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0]", DoubleStep.of(7).startFrom(7).to(-34).toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0]", DoubleStep.of(7).startFrom(7).to(-35).toList().toString());
        assertEquals("[7.0, 0.0, -7.0, -14.0, -21.0, -28.0, -35.0]", DoubleStep.of(7).startFrom(7).to(-35).inclusive().toList().toString());
    }
}
