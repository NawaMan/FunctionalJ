package functionalj.list.doublelist;

import static functionalj.lens.Access.theDouble;

import org.junit.Test;

import functionalj.stream.Step;

public class DoubleStreamplusTest {
    
    @Test
    public void segmentByPercentiles() {
        Step.from (0.0).step(1)
        .takeWhile(theDouble.thatLessThanOrEqualsTo(100.00))
        .toFuncList()
        .shuffle()
        .doubleStreamable()
        .segmentByPercentiles(25, 75)
        .forEach  (each -> {
            System.out.printf("Found: %d", each.size());
            each.forEach(d -> System.out.printf("    %f\n", d));
        });
    }
    
}
