package functionalj.result;

import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AcceptableTest {
    
    public static class ThreeDigitString extends Acceptable<String> {
        public ThreeDigitString(String value) {
            super(value, Validation.ToBoolean(str -> str.matches("^[0-9]{3}$"), "Three digit string is required."));
        }
    }
    
    @Test
    public void test() throws Exception {
        assertTrue (new ThreeDigitString("123").isPresent());
        assertFalse(new ThreeDigitString("ABC").isPresent());
        assertFalse(new ThreeDigitString(null) .isPresent());
        assertEquals("Result:{ Value: 123 }",                                  "" + new ThreeDigitString("123"));
        assertEquals("Result:{ Validation: Three digit string is required. }", "" + new ThreeDigitString("ABC"));
        assertEquals("Result:{ Validation: java.lang.NullPointerException }",  "" + new ThreeDigitString(null));
    }
    
    private Result<Integer> lengthOf(ThreeDigitString str) {
        return str.map(theString.length());
    }
    
    @Test
    public void testParam() {
        assertTrue  (lengthOf(new ThreeDigitString("123")).isPresent());
        assertFalse (lengthOf(new ThreeDigitString("ABC")).isPresent());
        assertFalse (lengthOf(new ThreeDigitString(null)).isPresent());
        assertEquals("Result:{ Value: 3 }",                                    "" + lengthOf(new ThreeDigitString("123")));
        assertEquals("Result:{ Validation: Three digit string is required. }", "" + lengthOf(new ThreeDigitString("ABC")));
        assertEquals("Result:{ Validation: java.lang.NullPointerException }",  "" + lengthOf(new ThreeDigitString(null)));
        assertTrue  (new ThreeDigitString("123").isPresent());
        assertFalse (new ThreeDigitString(null).isPresent());
    }
    
    // Notice the null value is passed to the checker
    public static class ThreeDigitStringOrNull extends Acceptable<String> {
        public ThreeDigitStringOrNull(String value) {
            super(value, Validation.ToBoolean(str -> (str != null) && str.matches("^[0-9]{3}$"), "Three digit string is required."));
        }
    }
    
    @Test
    public void testNullSafe() {
        assertTrue (new ThreeDigitStringOrNull("123").isPresent());
        assertFalse(new ThreeDigitStringOrNull("ABC").isPresent());
        assertFalse(new ThreeDigitStringOrNull(null).isPresent());
        assertEquals("Result:{ Value: 123 }",                                  "" + new ThreeDigitStringOrNull("123"));
        assertEquals("Result:{ Validation: Three digit string is required. }", "" + new ThreeDigitStringOrNull("ABC"));
        assertEquals("Result:{ Validation: Three digit string is required. }", "" + new ThreeDigitStringOrNull(null));
    }
    
}
