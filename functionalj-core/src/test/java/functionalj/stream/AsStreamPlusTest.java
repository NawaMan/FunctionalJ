package functionalj.stream;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;
import functionalj.list.FuncList;
import lombok.val;

public class AsStreamPlusTest {
    
    @Test
    public void testToFuncList_finiteStream() {
        assertAsString("[0, 0, 0, 0, 0]", StreamPlus.generate(() -> 0).limit(5).toFuncList());
    }
    
    @Ignore("Too long - run manually.")
    @Test
    public void testToFuncList_infiniteStream() {
        try {
            StreamPlus.generate(() -> 0).toFuncList();
            fail("It is impossible!!");
        } catch (OutOfMemoryError e) {
            // Expected
        }
    }
    
    @Test
    public void testSum() {
        val list = FuncList.of("1", "2", "3", "4", "5");
        assertAsString("15", list.sumToInt(theString.asInteger()));
        assertAsString("15", list.sumToLong(theString.asLong()));
        assertAsString("15.0", list.sumToDouble(theString.asDouble()));
        assertAsString("15", list.sumToBigInteger(theString.asBigInteger()));
        assertAsString("15", list.sumToBigDecimal(theString.asBigDecimal()));
        assertAsString("Result:{ Value: 15 }", list.sum(theString.asBigDecimal()));
    }
    
    @Test
    public void testAverage() {
        val list = FuncList.of("1", "2", "3", "4", "5");
        assertAsString("OptionalDouble[3.0]", list.average(theString.asInteger()));
        assertAsString("OptionalDouble[3.0]", list.average(theString.asLong()));
        assertAsString("OptionalDouble[3.0]", list.average(theString.asDouble()));
        assertAsString("Result:{ Value: 3 }", list.average(theString.asBigDecimal()));
    }
}
