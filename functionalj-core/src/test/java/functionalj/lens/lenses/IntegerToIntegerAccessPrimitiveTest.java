package functionalj.lens.lenses;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theInteger;
import org.junit.Test;
import functionalj.list.intlist.IntFuncList;

public class IntegerToIntegerAccessPrimitiveTest {

    @Test
    public void testGreaterThan() {
        IntFuncList numbers = IntFuncList.range(0, 10);
        assertAsString("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(5)));
        assertAsString("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(() -> 5)));
        assertAsString("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(n -> 10 - n)));
        assertAsString("[6, 7, 8, 9]", numbers.filter(theInteger.thatGreaterThan(theInteger.minus(10).negate())));
    }
}
