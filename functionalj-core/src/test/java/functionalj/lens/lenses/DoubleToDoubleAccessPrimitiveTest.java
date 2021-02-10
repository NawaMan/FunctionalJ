package functionalj.lens.lenses;

import static functionalj.lens.Access.theNumber;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;

public class DoubleToDoubleAccessPrimitiveTest {
    
    private void assertStrings(String expected, Object actual) {
        assertEquals(expected, String.valueOf(actual));
    }
    
    @Test
    public void testGreaterThan() {
        DoubleFuncList numbers = IntFuncList.range(0, 10).mapToDouble();
        assertStrings("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(5)));
        assertStrings("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(()->5)));
        assertStrings("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(n -> 10 - n)));
        assertStrings("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(theNumber.minus(10).negate())));
    }
    
}
