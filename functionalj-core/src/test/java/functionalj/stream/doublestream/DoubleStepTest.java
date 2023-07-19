package functionalj.stream.doublestream;

import static functionalj.stream.Step.StartAt;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import lombok.val;

public class DoubleStepTest {
    
    @Test
    public void testBasic() {
        val logs = new ArrayList<String>();
        StartAt(10.5).step(0.15).limit(10).mapToObj(value -> format("%f", value)).forEach(value -> logs.add(value));
        assertEquals("[" + "10.500000, " + "10.650000, " + "10.800000, " + "10.950000, " + "11.100000, " + "11.250000, " + "11.400000, " + "11.550000, " + "11.700000, " + "11.850000" + "]", logs.toString());
    }
}
