package functionalj.types.input;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

import lombok.val;

public class Tests {

    public static void assertAsString(String expected, Object actual) {
        String actualString = null;
        if (actual != null) {
            if (actual.getClass().isArray()) {
                if (actual instanceof boolean[]) {
                    actualString = Arrays.toString((int[]) actual);
                } else if (actual instanceof char[]) {
                    actualString = Arrays.toString((char[]) actual);
                } else if (actual instanceof byte[]) {
                    actualString = Arrays.toString((byte[]) actual);
                } else if (actual instanceof short[]) {
                    actualString = Arrays.toString((short[]) actual);
                } else if (actual instanceof int[]) {
                    actualString = Arrays.toString((int[]) actual);
                } else if (actual instanceof long[]) {
                    actualString = Arrays.toString((long[]) actual);
                } else if (actual instanceof float[]) {
                    actualString = Arrays.toString((float[]) actual);
                } else if (actual instanceof double[]) {
                    actualString = Arrays.toString((double[]) actual);
                } else {
                    val objArray = new Object[Array.getLength(actual)];
                    for (int i = 0; i < Array.getLength(actual); i++) {
                        objArray[i] = Array.get(actual, i);
                    }
                    actualString = Arrays.toString(objArray);
                }
            } else {
                actualString = Objects.toString(actual);
            }
        }
        val expectedRegEx = "^\\Q" + expected + "\\E$";
        if ((actualString != null) && actualString.matches(expectedRegEx))
            return;
        assertEquals(expected, actualString);
    }
}
