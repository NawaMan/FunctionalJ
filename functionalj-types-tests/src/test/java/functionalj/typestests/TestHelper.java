// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.typestests;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
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
        val actualAsString = toString(actual);
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
        val actualAsString = toString(actual);
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
        val actualAsString = toString(actual);
        if (actualAsString.matches(expectedRegEx))
            return;
        if (Objects.equals(expected, actualAsString))
            return;
        val message = failureMessage.get();
        assertEquals(message, expected, actualAsString);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static String toString(Object actual) {
        if (actual instanceof Map) {
            if (!(actual instanceof TreeMap)) {
                return new TreeMap((Map) actual).toString();
            }
        }
        if (actual instanceof Set) {
            if (!(actual instanceof TreeSet)) {
                return new TreeSet((Set) actual).toString();
            }
        }
        return Objects.toString(actual);
    }
}
