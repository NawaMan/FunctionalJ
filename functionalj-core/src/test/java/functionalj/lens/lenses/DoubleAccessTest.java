package functionalj.lens.lenses;

import static functionalj.lens.Access.theDouble;
import static functionalj.lens.lenses.DoubleAccess.equalPrecision;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class DoubleAccessTest {
    
    @Test
    public void testEquals() {
        val value = Result.ofValue(0.001);
        assertTrue (value.map(theDouble.thatEquals(0.001)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0009)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0011)).get());
        
        With(equalPrecision.butWith(0.001)).run(() -> {
            assertTrue(value.map(theDouble.thatEquals(0.001)).get());
            assertTrue(value.map(theDouble.thatEquals(0.0009)).get());
            assertTrue(value.map(theDouble.thatEquals(0.0011)).get());
        });
        With(equalPrecision.butWith(0.0001)).run(() -> {
            assertTrue (value.map(theDouble.thatEquals(0.001)).get());
            assertFalse(value.map(theDouble.thatEquals(0.0009)).get());
            assertFalse(value.map(theDouble.thatEquals(0.0011)).get());
        });
    }
    
    @Test
    public void testRoundTo() {
        assertEquals("1000.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(1000.0)).get().toString());
        assertEquals("1200.0",             Result.ofValue(1234.56789).map(theDouble.roundTo( 100.0)).get().toString());
        assertEquals("1230.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(  10.0)).get().toString());
        assertEquals("1235.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(   1.0)).get().toString());
        assertEquals("1235.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(   0.0)).get().toString());
        assertEquals("1234.6000000000001", Result.ofValue(1234.56789).map(theDouble.roundTo(   0.1)).get().toString());
        assertEquals("1234.57",            Result.ofValue(1234.56789).map(theDouble.roundTo(   0.01)).get().toString());
        assertEquals("1234.568",           Result.ofValue(1234.56789).map(theDouble.roundTo(   0.001)).get().toString());
        assertEquals("1234.5679",          Result.ofValue(1234.56789).map(theDouble.roundTo(   0.0001)).get().toString());
        
        // Round to 5
        assertEquals("0.0",                Result.ofValue(1234.56789).map(theDouble.roundTo(5000.0)).get().toString());
        assertEquals("1000.0",             Result.ofValue(1234.56789).map(theDouble.roundTo( 500.0)).get().toString());
        assertEquals("1250.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(  50.0)).get().toString());
        assertEquals("1235.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(   5.0)).get().toString());
        assertEquals("1235.0",             Result.ofValue(1234.56789).map(theDouble.roundTo(   0.0)).get().toString());
        assertEquals("1234.5",             Result.ofValue(1234.56789).map(theDouble.roundTo(   0.5)).get().toString());
        assertEquals("1234.5500000000002", Result.ofValue(1234.56789).map(theDouble.roundTo(   0.05)).get().toString());
        assertEquals("1234.57",            Result.ofValue(1234.56789).map(theDouble.roundTo(   0.005)).get().toString());
        assertEquals("1234.568",           Result.ofValue(1234.56789).map(theDouble.roundTo(   0.0005)).get().toString());
    }
    
}
