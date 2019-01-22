package functionalj.functions;

import static functionalj.functions.MapTo.only;
import static functionalj.functions.MapTo.toTuple;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class MapToTest {

    @Test
    public void testSelectThen() {
        System.out.println(Result.of("Hello").map(only(s -> s.length() < 4))); //.then(s -> "--" + s + "")
    }
    
    @Test
    public void testTuple() {
        val f1 = toTuple  ((String s)->s, (String s) -> s.length())
                .thenReduce((a,b)-> a + " - " + b);
        System.out.println(Result.of("Hello").map(f1));
    }

}
