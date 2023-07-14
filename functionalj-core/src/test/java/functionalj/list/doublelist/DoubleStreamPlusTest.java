package functionalj.list.doublelist;

import static functionalj.lens.Access.theDouble;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Ignore;
import org.junit.Test;
import functionalj.stream.Step;
import lombok.val;

public class DoubleStreamPlusTest {

    @Ignore
    @Test
    public void segmentByPercentiles() {
        val logs = new ArrayList<String>();
        Step.from(0.0).step(1).acceptWhile(theDouble.thatLessThanOrEqualsTo(10.00)).toFuncList().shuffle().segmentByPercentiles(25, 75).forEach(each -> {
            logs.add(String.format("Found: %d\n", each.size()));
            each.forEach(d -> logs.add(String.format("    %f\n", d)));
        });
        assertEquals("[Found: 3\n" + ",     0.000000\n" + ",     1.000000\n" + ",     2.000000\n" + ", Found: 5\n" + ",     3.000000\n" + ",     4.000000\n" + ",     5.000000\n" + ",     6.000000\n" + ",     7.000000\n" + ", Found: 3\n" + ",     8.000000\n" + ",     9.000000\n" + ",     10.000000\n" + "]", logs.toString());
    }
}
