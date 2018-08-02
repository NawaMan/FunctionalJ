package functionalj.types;

import static functionalj.functions.StrFuncs.appendWith;
import static functionalj.lens.Access.theTuple2;
import static functionalj.lens.Access.theTupleOf;
import static functionalj.lens.LensTypes.STRING;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.functions.StrFuncs;
import functionalj.lens.lenses.StringLens;
import functionalj.lens.lenses.Tuple2Lens;
import functionalj.types.list.ImmutableList;
import functionalj.types.tuple.ImmutableTuple2;
import functionalj.types.tuple.Tuple2;
import lombok.val;

public class Tuple2Test {
    
    // Do not like this one bit. - Find the way to improve this!
    private static final Tuple2Lens<Tuple2<String, String>, String, String, StringLens<Tuple2<String, String>>, StringLens<Tuple2<String, String>>> 
            theTuple = theTuple2.of(STRING(), STRING());
    
    private static final ImmutableList<ImmutableTuple2<String, String>> tuples = ImmutableList.of(
            new ImmutableTuple2<>("I", "Integer"),
            new ImmutableTuple2<>("S", "String")
        );
    
    @Test
    public void testLensRead() {
        val theTuple = theTuple2.of(STRING(), STRING());
        assertEquals("[I, S]", "" + tuples.map(theTuple._1()));
    }
    
    @Test
    public void testLensRead2() {
        assertEquals("[I, S]", "" + tuples.map(theTupleOf(STRING(), STRING())._1()));
    }

    @Test
    public void testLensChange() {
        assertEquals("[(I: ,Integer), (S: ,String)]", 
                "" + tuples.map(theTuple._1().changeTo(appendWith(": "))));
    }
    
}
