// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
        assertTrue(new ThreeDigitString("123").isPresent());
        assertFalse(new ThreeDigitString("12").isPresent());
        assertFalse(new ThreeDigitString("ABC").isPresent());
        assertFalse(new ThreeDigitString(null).isPresent());
        assertEquals("ThreeDigitString:{ Value: 123 }", "" + new ThreeDigitString("123"));
        assertEquals("ThreeDigitString:{ Invalid: Three digit string is required. }", "" + new ThreeDigitString("ABC"));
        assertEquals("ThreeDigitString:{ Invalid: java.lang.NullPointerException }", "" + new ThreeDigitString(null));
    }
    
    private Result<Integer> lengthOf(ThreeDigitString str) {
        return str.map(theString.length());
    }
    
    @Test
    public void testParam() {
        assertTrue(lengthOf(new ThreeDigitString("123")).isPresent());
        assertFalse(lengthOf(new ThreeDigitString("ABC")).isPresent());
        assertFalse(lengthOf(new ThreeDigitString(null)).isPresent());
        assertEquals("Result:{ Value: 3 }", "" + lengthOf(new ThreeDigitString("123")));
        assertEquals("Result:{ Invalid: Three digit string is required. }", "" + lengthOf(new ThreeDigitString("ABC")));
        assertEquals("Result:{ Invalid: java.lang.NullPointerException }", "" + lengthOf(new ThreeDigitString(null)));
        assertTrue(new ThreeDigitString("123").isPresent());
        assertFalse(new ThreeDigitString(null).isPresent());
    }
    
    // Notice the null value is passed to the checker
    public static class ThreeDigitStringOrNull extends Acceptable<String> {
        
        public ThreeDigitStringOrNull(String value) {
            super(value, Validation.ToBoolean(str -> (str != null) && str.matches("^[0-9]{3}$"), "Three digit string is required."));
        }
    }
    
    @Test
    public void testNullSafe() {
        assertTrue(new ThreeDigitStringOrNull("123").isPresent());
        assertFalse(new ThreeDigitStringOrNull("ABC").isPresent());
        assertFalse(new ThreeDigitStringOrNull(null).isPresent());
        assertEquals("ThreeDigitStringOrNull:{ Value: 123 }", "" + new ThreeDigitStringOrNull("123"));
        assertEquals("ThreeDigitStringOrNull:{ Invalid: Three digit string is required. }", "" + new ThreeDigitStringOrNull("ABC"));
        assertEquals("ThreeDigitStringOrNull:{ Invalid: Three digit string is required. }", "" + new ThreeDigitStringOrNull(null));
    }
}
