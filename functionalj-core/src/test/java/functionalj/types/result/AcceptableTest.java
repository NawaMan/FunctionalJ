package functionalj.types.result;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AcceptableTest {
    
    public static class ThreeDigitString extends AcceptableResult<String> {
        public ThreeDigitString(String value) {
            super(value, str -> str.matches("^[0-9]+$"));
        }
    }
    
    @Test
    public void test() {
        assertTrue (new ThreeDigitString("123").isPresent());
        assertFalse(new ThreeDigitString("ABC").isPresent());
        assertFalse(new ThreeDigitString(null) .isPresent());
        assertEquals("Result:{ Value: 123 }",  "" + new ThreeDigitString("123"));
        assertEquals("Result:{ Value: null }", "" + new ThreeDigitString("ABC"));
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + new ThreeDigitString(null));
    }
    
    private Result<Integer> lengthOf(ThreeDigitString str) {
        return str.map(theString.length());
    }
    
    @Test
    public void testParam() {
        assertTrue  (lengthOf(new ThreeDigitString("123")).isPresent());
        assertFalse (lengthOf(new ThreeDigitString("ABC")).isPresent());
        assertFalse (lengthOf(new ThreeDigitString(null)).isPresent());
        assertEquals("Result:{ Value: 3 }",    "" + lengthOf(new ThreeDigitString("123")));
        assertEquals("Result:{ Value: null }", "" + lengthOf(new ThreeDigitString("ABC")));
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + lengthOf(new ThreeDigitString(null)));
        assertTrue  (new ThreeDigitString("123").isPresent());
        assertFalse (new ThreeDigitString(null).isPresent());
    }
    
    // Notice the null value is passed to the checker
    public static class ThreeDigitStringOrNull extends AcceptableResult<String> {
        public ThreeDigitStringOrNull(String value) {
            super(value, NullSafePredicate.of(str -> (str == null) || str.matches("^[0-9]+$")));
        }
    }
    
    @Test
    public void testNullSafe() {
        assertTrue (new ThreeDigitStringOrNull("123").isPresent());
        assertFalse(new ThreeDigitStringOrNull("ABC").isPresent());
        assertFalse(new ThreeDigitStringOrNull(null).isPresent());
        assertEquals("Result:{ Value: 123 }",  "" + new ThreeDigitStringOrNull("123"));
        assertEquals("Result:{ Value: null }", "" + new ThreeDigitStringOrNull("ABC"));
        assertEquals("Result:{ Value: null }", "" + new ThreeDigitStringOrNull(null));
    }
    
}
