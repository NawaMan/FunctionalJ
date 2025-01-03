package functionalj.function.aggregator;

import static functionalj.TestHelper.assertAsString;
import static functionalj.function.Operators.concat;
import static functionalj.function.Operators.joinWithSpace;
import static functionalj.lens.Access.theString;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.function.aggregator.aggregations.Product;
import functionalj.function.aggregator.aggregations.Sum;
import functionalj.list.FuncList;
import lombok.val;

public class AggregationTest {
    
    @Test
    public void testSumOfStringLength() {
        val list = FuncList.of("Hello", "World");
        assertAsString(
                "(10,10,10.0,25,HelloWorld,Hello World,[Hello, World])",
                list.calculate(
                        Sum          .of(theString.length()),
                        Sum          .of(theString.length().asLong()),
                        Sum          .of(theString.length().asDouble()),
                        Product      .of(theString.length()),
                        concat       .of(theString), 
                        joinWithSpace.of(theString),
                        Aggregation.from(Collectors.toList())
                )
        );
    }
    
}
