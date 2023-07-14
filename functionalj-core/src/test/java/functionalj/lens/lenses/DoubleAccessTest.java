package functionalj.lens.lenses;

import static functionalj.lens.Access.theDouble;
import static functionalj.lens.lenses.DoubleAccess.equalPrecision;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import functionalj.result.Result;
import functionalj.result.Value;
import lombok.val;

public class DoubleAccessTest {

    @Test
    public void testAsString() {
        val value = Value.of(1234.56789);
        assertEquals("1234.56789", value.map(theDouble.asString()).get());
        assertEquals("1234.57", value.map(theDouble.asString("%4.2f")).get());
        assertEquals("   1234.57", value.map(theDouble.asString("%10.2f")).get());
        assertEquals("1234.57", value.map(theDouble.asString("%.2f")).get());
        assertEquals("   1234.5670", Value.of(1234.567).map(theDouble.asString("%12.4f")).get());
        assertEquals("      0.0000", Value.of(12.5e-10).map(theDouble.asString("%12.4f")).get());
        assertEquals("125000000000.0000", Value.of(12.5e+10).map(theDouble.asString("%12.4f")).get());
        assertEquals("1234.567", Value.of(1234.567).map(theDouble.asString()).get());
        assertEquals("1.25E-34", Value.of(12.5e-35).map(theDouble.asString()).get());
        assertEquals("1.25E36", Value.of(12.5e+35).map(theDouble.asString()).get());
    }

    @Test
    public void testEquals() {
        val value = Result.ofValue(0.001);
        assertTrue(value.map(theDouble.thatEquals(0.001)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0009)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0011)).get());
        With(equalPrecision.butWith(0.001)).run(() -> {
            assertTrue(value.map(theDouble.thatEquals(0.001)).get());
            assertTrue(value.map(theDouble.thatEquals(0.0009)).get());
            assertTrue(value.map(theDouble.thatEquals(0.0011)).get());
        });
        With(equalPrecision.butWith(0.0001)).run(() -> {
            assertTrue(value.map(theDouble.thatEquals(0.001)).get());
            assertFalse(value.map(theDouble.thatEquals(0.0009)).get());
            assertFalse(value.map(theDouble.thatEquals(0.0011)).get());
        });
        assertTrue(value.map(theDouble.thatEquals(0.001).withIn(0.001)).get());
        assertTrue(value.map(theDouble.thatEquals(0.0009).withIn(0.001)).get());
        assertTrue(value.map(theDouble.thatEquals(0.0011).withIn(0.001)).get());
        assertTrue(value.map(theDouble.thatEquals(0.001).withIn(0.0001)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0009).withIn(0.0001)).get());
        assertFalse(value.map(theDouble.thatEquals(0.0011).withIn(0.0001)).get());
        // Primitive
        assertTrue(theDouble.thatEquals(0.001).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0009).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0011).applyAsBoolean(0.001));
        assertTrue(theDouble.thatEquals(0.001).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0009).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0011).applyAsBoolean(0.001));
        assertTrue(theDouble.thatEquals(0.001).withIn(0.001).applyAsBoolean(0.001));
        assertTrue(theDouble.thatEquals(0.0009).withIn(0.001).applyAsBoolean(0.001));
        assertTrue(theDouble.thatEquals(0.0011).withIn(0.001).applyAsBoolean(0.001));
        assertTrue(theDouble.thatEquals(0.001).withIn(0.0001).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0009).withIn(0.0001).applyAsBoolean(0.001));
        assertFalse(theDouble.thatEquals(0.0011).withIn(0.0001).applyAsBoolean(0.001));
    }

    @Test
    public void testRoundTo() {
        assertEquals("1000.0", Result.ofValue(1234.56789).map(theDouble.roundBy(1000.0)).get().toString());
        assertEquals("1200.0", Result.ofValue(1234.56789).map(theDouble.roundBy(100.0)).get().toString());
        assertEquals("1230.0", Result.ofValue(1234.56789).map(theDouble.roundBy(10.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(1.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0)).get().toString());
        assertEquals("1234.6000000000001", Result.ofValue(1234.56789).map(theDouble.roundBy(0.1)).get().toString());
        assertEquals("1234.57", Result.ofValue(1234.56789).map(theDouble.roundBy(0.01)).get().toString());
        assertEquals("1234.568", Result.ofValue(1234.56789).map(theDouble.roundBy(0.001)).get().toString());
        assertEquals("1234.5679", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0001)).get().toString());
        // Round by 5
        assertEquals("0.0", Result.ofValue(1234.56789).map(theDouble.roundBy(5000.0)).get().toString());
        assertEquals("1000.0", Result.ofValue(1234.56789).map(theDouble.roundBy(500.0)).get().toString());
        assertEquals("1250.0", Result.ofValue(1234.56789).map(theDouble.roundBy(50.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(5.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0)).get().toString());
        assertEquals("1234.5", Result.ofValue(1234.56789).map(theDouble.roundBy(0.5)).get().toString());
        assertEquals("1234.5500000000002", Result.ofValue(1234.56789).map(theDouble.roundBy(0.05)).get().toString());
        assertEquals("1234.57", Result.ofValue(1234.56789).map(theDouble.roundBy(0.005)).get().toString());
        assertEquals("1234.568", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0005)).get().toString());
        // Round by 3
        assertEquals("0.0", Result.ofValue(1234.56789).map(theDouble.roundBy(3000.0)).get().toString());
        assertEquals("1200.0", Result.ofValue(1234.56789).map(theDouble.roundBy(300.0)).get().toString());
        assertEquals("1230.0", Result.ofValue(1234.56789).map(theDouble.roundBy(30.0)).get().toString());
        assertEquals("1236.0", Result.ofValue(1234.56789).map(theDouble.roundBy(3.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0)).get().toString());
        assertEquals("1234.5", Result.ofValue(1234.56789).map(theDouble.roundBy(0.3)).get().toString());
        assertEquals("1234.56", Result.ofValue(1234.56789).map(theDouble.roundBy(0.03)).get().toString());
        assertEquals("1234.569", Result.ofValue(1234.56789).map(theDouble.roundBy(0.003)).get().toString());
        assertEquals("1234.5677999999998", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0003)).get().toString());
        // Round by 2
        assertEquals("2000.0", Result.ofValue(1234.56789).map(theDouble.roundBy(2000.0)).get().toString());
        assertEquals("1200.0", Result.ofValue(1234.56789).map(theDouble.roundBy(200.0)).get().toString());
        assertEquals("1240.0", Result.ofValue(1234.56789).map(theDouble.roundBy(20.0)).get().toString());
        assertEquals("1234.0", Result.ofValue(1234.56789).map(theDouble.roundBy(2.0)).get().toString());
        assertEquals("1235.0", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0)).get().toString());
        assertEquals("1234.6000000000001", Result.ofValue(1234.56789).map(theDouble.roundBy(0.2)).get().toString());
        assertEquals("1234.56", Result.ofValue(1234.56789).map(theDouble.roundBy(0.02)).get().toString());
        assertEquals("1234.568", Result.ofValue(1234.56789).map(theDouble.roundBy(0.002)).get().toString());
        assertEquals("1234.5678", Result.ofValue(1234.56789).map(theDouble.roundBy(0.0002)).get().toString());
    }
}
