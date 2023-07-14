package functionalj.lens.lenses;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theNumber;
import org.junit.Test;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;

public class DoubleToDoubleAccessPrimitiveTest {

    @Test
    public void testGreaterThan() {
        DoubleFuncList numbers = IntFuncList.range(0, 10).mapToDouble();
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(5)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(() -> 5)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(n -> 10 - n)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(theNumber.minus(10).negate())));
    }
}
