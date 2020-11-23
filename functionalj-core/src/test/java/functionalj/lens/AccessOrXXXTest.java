// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens;

import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTypes.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import functionalj.tuple.Tuple;


public class AccessOrXXXTest {
    
    @Test
    public void testOrDefaultTo() {
        var theStr = theString.orDefaultTo("N/A");
        assertEquals("String", theStr.apply("String"));
        assertEquals("N/A",    theStr.applyToNull());
    }
    
    @Test
    public void testOrDefaultTo_stillStringAccess() {
        // NOTE: This is the desire result and the reason for all those complicated implementation of 
        //        ConcreteAccess so that the return type of orXXX return the same access type.
        var theStr = theString.orDefaultTo("N/A");
        assertEquals("6", "" + theStr.length().apply("String"));
        assertEquals("0", "" + theStr.length().applyToNull());
        
        assertEquals("-1", "" + theStr.length().orDefaultTo(-1).applyToNull());
    }
    
    @Test
    public void testOrDefaultFrom() {
        var theStr = theString.orDefaultFrom(()->"N/A");
        assertEquals("String", theStr.apply("String"));
        assertEquals("N/A",    theStr.applyToNull());
    }
    
    @Test
    public void testOrThrow() {
        var theStr = theString.orThrow();
        assertEquals("String", theStr.apply("String"));
        
        try {
            theStr.applyToNull();
            fail();
        } catch (NullPointerException e) {
            // Expected!
        }
    }
    
    @Test
    public void testOrThrowRuntimeException() {
        var theStr = theString.orThrow(()->new RuntimeException("This should never happen!"));
        assertEquals("String", theStr.apply("String"));
        
        try {
            theStr.applyToNull();
            fail();
        } catch (RuntimeException e) {
            assertEquals("This should never happen!", e.getMessage());
            // Expected!
        }
    }
    
    @Test
    public void testTuple2Access() {
        var theTupleStrStr = Access.theTuple2.of(STRING(), STRING());
        
        theTupleStrStr._1().length().apply(Tuple.of("ONE","TWO"));
        var theTuple2 = theTupleStrStr.orDefaultTo(Tuple.of("A", "B"));
        
        assertEquals("null", "" + theTupleStrStr.applyToNull());
        assertEquals("(A,B)", "" +theTuple2.applyToNull());
    }
    
}
