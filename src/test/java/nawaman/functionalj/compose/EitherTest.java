package nawaman.functionalj.compose;

import static nawaman.functionalj.compose.Functional.curry2;
import static nawaman.functionalj.compose.Either.emap;
import static nawaman.functionalj.compose.Functional.compose;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import static org.junit.Assert.*;

import lombok.val;

public class EitherTest {
    
    @Test
    public void test() {
        assertEquals("Right(5)",   Either.of("Left", "Right").map(String::length).toString());
        assertEquals("Left(Left)", Either.<String, String>left("Left").map(String::length).toString());
        assertEquals("Right(5)",   Either.<String, String>right("right").map(String::length).toString());
    }
    
    @Test
    public void testMap() {
        val e = Either.of("Left", "Right");
        val r = emap(e, String::length);
        assertEquals("Right(5)", r);
    }
    
    private static Function<String, Integer> strLength = String::length;
    private static Function<Integer, String> strValueOf = String::valueOf;
    private static Function<Integer, String> toString = strValueOf;
    private static BiFunction<String, String, String> strConcat = String::concat;
    
    @Test
    public void test3() {
        val showLength = compose(
                strLength, 
                toString, 
                curry2(strConcat, "The length is: "));
        assertEquals("The length is: 4", showLength.apply("five"));
    }
    
}
