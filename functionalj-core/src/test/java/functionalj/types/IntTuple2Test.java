package functionalj.types;

import static org.junit.Assert.assertEquals;
import static tuple.IntTuple2.createTheTuple;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.lens.lenses.StringLens;
import lombok.val;
import tuple.IntTuple2;
import tuple.IntTuple2.IntTuple2Lens;

public class IntTuple2Test {

    
    // Do not like this one bit. - Find the way to improve this!
    private static final IntTuple2Lens<IntTuple2<String>, String, StringLens<IntTuple2<String>>> 
            theTuple = createTheTuple(StringLens::of);
    
    @Test
    public void testLensRead() {
        val tuples = Arrays.asList(
                new IntTuple2<>(1, "One"),
                new IntTuple2<>(2, "Two"),
                new IntTuple2<>(3, "Three")
            );
        assertEquals("[1, 2, 3]", tuples.stream()
                            .map(theTuple._1())
                            .collect(Collectors.toList()).toString());
    }
    
}
