package functionalj.lens;

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.ImmutableList;

public class NumberAccessTest {
    
    @SuppressWarnings("unchecked")
    @Test
    public void testAdd() {
        assertEquals(15,  (int)theInteger.add(5).apply(10));
        
        assertEquals(
                "[[One,3,true,9.0], [Two,3,true,9.0], [Three,5,false,15.0], [Four,4,false,12.0]]",
                "" + ImmutableList.of("One", "Two", "Three", "Four")
                    .map(theString, 
                         theString.length(),
                         theString.length().thatLessThan(4),
                         theString.length().add($I.multiply(2)).toDouble()));
    }
    
}
