package functionalj.types;

import static functionalj.types.Tuple2.createTheTuple;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.lens.StringLens;
import functionalj.types.Tuple2.Tuple2Lens;
import lombok.val;

public class Tuple2Test {
    
    // Do not like this one bit. - Find the way to improve this!
    private static final Tuple2Lens<Tuple2<String, String>, String, String, StringLens<Tuple2<String, String>>, StringLens<Tuple2<String, String>>> 
            theTuple = createTheTuple(StringLens::of, StringLens::of);
    
    @Test
    public void testLensRead() {
        val tuples = Arrays.asList(
                new Tuple2<>("I", "Integer"),
                new Tuple2<>("S", "String")
            );
        assertEquals("[I, S]", tuples.stream()
                            .map(theTuple._1())
                            .collect(Collectors.toList()).toString());
    }

    @Test
    public void testLensChange() {
        val tuples = Arrays.asList(
                new Tuple2<>("I", "Integer"),
                new Tuple2<>("S", "String")
            );
        assertEquals("[[I: ,Integer], [S: ,String]]", tuples.stream()
                .map(theTuple._1().changeTo(s -> s + ": "))
                .collect(Collectors.toList()).toString());
    }
    
}
