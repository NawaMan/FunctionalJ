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
