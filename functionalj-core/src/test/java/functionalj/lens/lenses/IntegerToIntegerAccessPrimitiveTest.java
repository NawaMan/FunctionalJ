package functionalj.lens.lenses;

import static functionalj.lens.Access.theInteger;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.intlist.IntFuncList;

public class IntegerToIntegerAccessPrimitiveTest {
    
    private void assertStrings(String expected, Object actual) {
        assertEquals(expected, String.valueOf(actual));
    }
    
    @Test
    public void testGreaterThan() {
        IntFuncList numbers = IntFuncList.range(0, 10);
        assertStrings("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(5)));
        assertStrings("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(()->5)));
        assertStrings("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(n -> 10 - n)));
        assertStrings("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(theInteger.minus(10).negate())));
    }
    
}
