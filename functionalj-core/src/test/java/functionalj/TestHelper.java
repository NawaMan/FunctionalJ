// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj;

import static org.junit.Assert.assertEquals;
import java.util.Objects;
import java.util.function.Supplier;
import org.junit.ComparisonFailure;
import org.junit.Test;
import functionalj.list.FuncList;
import lombok.val;

public class TestHelper {
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     *
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     */
    public static void assertAsString(String expected, Object actual) {
        val expectedRegEx = "^\\Q" + expected + "\\E$";
        val actualAsString = Objects.toString(actual);
        if (actualAsString.matches(expectedRegEx))
            return;
        assertEquals(expected, actualAsString);
    }
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     *
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     */
    public static void assertAsString(String failureMessage, String expected, Object actual) {
        val expectedRegEx = "^\\Q" + expected + "\\E$";
        val actualAsString = Objects.toString(actual);
        if (actualAsString.matches(expectedRegEx))
            return;
        assertEquals(failureMessage, expected, actualAsString);
    }
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     *
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     */
    public static void assertAsString(Supplier<String> failureMessage, String expected, Object actual) {
        val expectedRegEx = "^\\Q" + expected + "\\E$";
        val actualAsString = Objects.toString(actual);
        if (actualAsString.matches(expectedRegEx))
            return;
        if (Objects.equals(expected, actualAsString))
            return;
        val message = failureMessage.get();
        assertEquals(message, expected, actualAsString);
    }
    
    // == Tests for the above ==
    @Test
    public void testExact() {
        assertAsString("Should match exacty", "[One, 2, 3.0]", FuncList.of("One", 2, 3.0));
    }
    
    @Test(expected = ComparisonFailure.class)
    public void testExact_unmatch() {
        assertAsString("Should not match", "[One, Two, 3.0]", FuncList.of("One", 2, 3.0));
    }
    
    @Test
    public void testExact_regExLiked() {
        assertAsString("When not use RegEx, any text that look like RegEx should still match exactly.", "[One, [0-9]+, 3.0]", FuncList.of("One", "[0-9]+", 3.0));
    }
    
    @Test
    public void testRegEx() {
        assertAsString("Match as RegEx.", "[One, \\E[0-9]+\\Q, 3.0]", FuncList.of("One", 2, 3.0));
    }
    
    @Test(expected = ComparisonFailure.class)
    public void testRegEx_unmatch() {
        assertAsString("RegEx but not match.", "[One, \\E[0-9]+\\Q, 3.0]", FuncList.of("One", "Two", 3.0));
    }
}
