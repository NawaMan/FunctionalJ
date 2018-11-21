package functionalj.lens;

import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTypes.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import functionalj.tuple.Tuple;
import lombok.val;

public class AccessOrXXXTest {
    
    @Test
    public void testOrDefaultTo() {
        val theStr = theString.orDefaultTo("N/A");
        assertEquals("String", theStr.apply("String"));
        assertEquals("N/A",    theStr.applyNull());
    }
    
    @Test
    public void testOrDefaultTo_stillStringAccess() {
        // NOTE: This is the desire result and the reason for all those complicated implementation of 
        //        ConcreteAccess so that the return type of orXXX return the same access type.
        val theStr = theString.orDefaultTo("N/A");
        assertEquals("6", "" + theStr.length().apply("String"));
        assertEquals("0", "" + theStr.length().applyNull());
        
        assertEquals("-1", "" + theStr.length().orDefaultTo(-1).applyNull());
    }
    
    @Test
    public void testOrDefaultFrom() {
        val theStr = theString.orDefaultFrom(()->"N/A");
        assertEquals("String", theStr.apply("String"));
        assertEquals("N/A",    theStr.applyNull());
    }
    
    @Test
    public void testOrThrow() {
        val theStr = theString.orThrow();
        assertEquals("String", theStr.apply("String"));
        
        try {
            theStr.applyNull();
            fail();
        } catch (NullPointerException e) {
            // Expected!
        }
    }
    
    @Test
    public void testOrThrowRuntimeException() {
        val theStr = theString.orThrow(()->new RuntimeException("This should never happen!"));
        assertEquals("String", theStr.apply("String"));
        
        try {
            theStr.applyNull();
            fail();
        } catch (RuntimeException e) {
            assertEquals("This should never happen!", e.getMessage());
            // Expected!
        }
    }
    
    @Test
    public void testTuple2Access() {
        val theTupleStrStr = Access.theTuple2.of(STRING(), STRING());
        
        theTupleStrStr._1().length().apply(Tuple.of("ONE","TWO"));
        val theTuple2 = theTupleStrStr.orDefaultTo(Tuple.of("A", "B"));
        
        assertEquals("null", "" + theTupleStrStr.applyNull());
        assertEquals("(A,B)", "" +theTuple2.applyNull());
    }
    
}
