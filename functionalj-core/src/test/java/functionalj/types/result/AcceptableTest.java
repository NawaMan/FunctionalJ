package functionalj.types.result;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AcceptableTest {
    
    public static class ThreeDigitString extends Acceptable<String> {
        public ThreeDigitString(String value) {
            super(value, str -> str.matches("^[0-9]+$"));
        }
    }
    
    @Test
    public void test() {
        assertTrue(new ThreeDigitString("123").isPresent());
        assertFalse(new ThreeDigitString("ABC").isPresent());
        assertEquals("Result:{ Value: 123 }",  "" + new ThreeDigitString("123"));
        assertEquals("Result:{ Value: null }", "" + new ThreeDigitString("ABC"));
    }
    
    private Result<Integer> lengthOf(ThreeDigitString str) {
        return str.map(theString.length());
    }
    
    @Test
    public void testParam() {
        assertTrue  (lengthOf(new ThreeDigitString("123")).isPresent());
        assertFalse (lengthOf(new ThreeDigitString("ABC")).isPresent());
        assertEquals("Result:{ Value: 3 }",    "" + lengthOf(new ThreeDigitString("123")));
        assertEquals("Result:{ Value: null }", "" + lengthOf(new ThreeDigitString("ABC")));
        assertTrue  (new ThreeDigitString("123").toImmutable().castTo(ThreeDigitString.class).isPresent());
    }
    
}
