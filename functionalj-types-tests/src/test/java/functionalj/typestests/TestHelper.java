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
