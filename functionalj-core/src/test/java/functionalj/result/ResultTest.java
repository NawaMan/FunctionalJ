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
package functionalj.result;

import static functionalj.TestHelper.assertAsString;
import static functionalj.function.Func.f;
import static functionalj.result.Result.Do;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import functionalj.function.Func;
import functionalj.list.intlist.IntFuncList;
import functionalj.validator.Validator;
import lombok.val;

public class ResultTest {
    
    private static final Result<String> result = Result.valueOf("Test");
    
    @Test
    public void testMap() {
        assertAsString("Result:{ Value: Test }", result);
        assertAsString("Result:{ Value: 4 }", result.map(str -> str.length()));
    }
    
    @Test
    public void testMapWith() {
        result.mapWith(Func.f((a, b) -> a + b), Result.valueOf("-Value")).ifException(e -> {
            e.printStackTrace();
        });
        assertAsString("Result:{ Value: Test-Value }", result.mapWith(f((a, b) -> a + b), Result.valueOf("-Value")));
        assertAsString("Result:{ Exception: java.lang.IllegalAccessException }", result.mapWith(f((a, b) -> a + b), Result.ofException(new IllegalAccessException())));
    }
    
    @Test
    public void testResultFom() {
        assertEquals("Result:{ Value: VALUE }", "" + Result.of(() -> "VALUE"));
        assertEquals("Result:{ Exception: java.io.IOException }", "" + Result.of(f(() -> {
            throw new IOException();
        })));
    }
    
    @Test
    public void testResult_value() {
        val result = Result.valueOf("VALUE");
        assertEquals("Result:{ Value: VALUE }", "" + result);
        assertTrue(result.isValue());
        assertFalse(result.isException());
        assertTrue(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_null() {
        val result = Result.valueOf(null);
        assertEquals("Result:{ Value: null }", "" + result);
        assertTrue(result.isValue());
        assertFalse(result.isException());
        assertFalse(result.isPresent());
        assertTrue(result.isNull());
    }
    
    @Test
    public void testResult_exception() {
        val result = Result.valueOf((String) null).ensureNotNull();
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + result);
        assertFalse(result.isValue());
        assertTrue(result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map() {
        val result = Result.valueOf("VALUE").map(str -> str.length());
        assertEquals("Result:{ Value: 5 }", "" + result);
        assertTrue(result.isValue());
        assertFalse(result.isException());
        assertTrue(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_failableMap() {
        val result = Result.valueOf("VALUE").map(str -> new UnsupportedOperationException("Not support."));
        assertEquals("Result:{ Value: java.lang.UnsupportedOperationException: Not support. }", "" + result);
        assertTrue(result.isValue());
        assertFalse(result.isException());
        assertTrue(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map_null() {
        val result = Result.valueOf("VALUE").map(str -> (String) null).map(String::length);
        assertEquals("Result:{ Value: null }", "" + result);
    }
    
    @Test
    public void testResult_validate() {
        val validator1 = Validator.of((String s) -> s.toUpperCase().equals(s), "Not upper case");
        val validator2 = Validator.of((String s) -> s.matches("^.*[A-Z].*$"), "No upper case");
        val validator3 = Validator.of((String s) -> !s.isEmpty(), "Empty");
        assertEquals("Result:{ Value: (VALUE,[]) }", "" + Result.valueOf("VALUE").validate(validator1, validator2));
        assertEquals("Result:{ Value: (value,[" + "functionalj.result.ValidationException: Not upper case, " + "functionalj.result.ValidationException: No upper case" + "]) }", "" + Result.valueOf("value").validate(validator1, validator2, validator3));
        assertEquals("Result:{ Value: (,[" + "functionalj.result.ValidationException: No upper case, " + "functionalj.result.ValidationException: Empty]) }", "" + Result.valueOf("").validate(validator1, validator2, validator3));
        assertEquals("Result:{ Value: (null,[" + "functionalj.result.ValidationException: java.lang.NullPointerException, " + "functionalj.result.ValidationException: java.lang.NullPointerException, " + "functionalj.result.ValidationException: java.lang.NullPointerException" + "]) }", "" + Result.valueOf((String) null).validate(validator1, validator2, validator3));
    }
    
    @Test
    public void testResult_validate_oneline() throws Exception {
        assertEquals("Result:{ Invalid: Has upper case: \"VALUE\" }", Result.valueOf("VALUE").validate("Has upper case: \"%s\"", s -> !s.matches("^.*[A-Z].*$")).toString());
        assertEquals("Result:{ Invalid: Too long: \"VALUE\" }", Result.valueOf("VALUE").validate("Too long: \"%s\"", String::length, l -> l < 3).toString());
    }
    
    @Test
    public void testResultOf() {
        assertAsString("Result:{ Value: One }", Result.valueOf("One"));
        assertAsString("Result:{ Value: One,Two }", Result.of("One", "Two", (a, b) -> a + "," + b));
        assertAsString("Result:{ Value: One,Two,Three }", Result.of("One", "Two", "Three", (a, b, c) -> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult() {
        assertAsString("Result:{ Value: One }", Result.ofResult(Result.valueOf("One")));
        assertAsString("Result:{ Value: One,Two }", Result.ofResults(Result.valueOf("One"), Result.valueOf("Two"), (a, b) -> a + "," + b));
        assertAsString("Result:{ Value: One,Two,Three }", Result.ofResults(Result.valueOf("One"), Result.valueOf("Two"), Result.valueOf("Three"), (a, b, c) -> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult_withException() {
        assertAsString("Result:{ Exception: functionalj.function.FunctionInvocationException: Test fail }", Result.ofResults(Result.valueOf("One"), Result.ofException("Test fail"), Result.valueOf("Three"), (a, b, c) -> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultPeek() {
        val logs = new ArrayList<String>();
        Result.valueOf("One").peek(logs::add).forValue(logs::add);
        // Two of them are logged ... one from `peek` and another from `forValue`.
        assertAsString("[One, One]", logs);
    }
    
    @Test
    public void testResultPipeTo() {
        assertAsString("Result:{ Value: 3 }", Result.valueOf("One").pipeTo(r -> r.map(String::length), String::valueOf));
    }
    
    @Test
    public void testResultDo() {
        assertAsString("Result:{ Value: One }", Result.ofResult(Result.valueOf("One")));
        assertAsString("Result:{ Value: One,Two }", Do(() -> "One", () -> "Two", (a, b) -> a + "," + b));
    }
    
    @Test
    public void testResultDo_withException() {
        assertAsString("Result:{ Exception: java.lang.RuntimeException: Test exception }", Result.ofResult(Result.ofException(new RuntimeException("Test exception"))));
        assertAsString("Result:{ Exception: java.lang.RuntimeException: Test exception }", Do(() -> "One", () -> {
            throw new RuntimeException("Test exception");
        }, (a, b) -> a + "," + b));
    }
    
    @Test
    public void testResultMapFirst() {
        val nums = IntFuncList.loop(13).map(i -> i * i * i).boxed().toList();
        val guess = nums.map(num -> (String) Result.valueOf(num).mapFirst(i -> ((i < 10) ? (i + " ONES") : null), i -> ((i < 100) ? (i + " TENS") : null), i -> ((i < 1000) ? (i + " HUNDRED") : (i + " THOUSANDS"))).orElse("UNKNOWN")).toList();
        assertEquals("[" + "0 ONES, 1 ONES, 8 ONES, " + "27 TENS, 64 TENS, " + "125 HUNDRED, 216 HUNDRED, 343 HUNDRED, 512 HUNDRED, 729 HUNDRED, " + "1000 THOUSANDS, 1331 THOUSANDS, 1728 THOUSANDS]", guess.toString());
    }
    
    @Test
    public void testResultMapFirst_Exception() {
        // An exception is threaded as a return null -- or next case.
        val nums = IntFuncList.loop(13).map(i -> i * i * i).boxed().toList();
        val guess = nums.map(num -> (String) Result.valueOf(num).mapFirst(i -> ((i < 10) ? (i + " ONES") : null), i -> {
            if (i < 100)
                return (i + " TENS");
            throw new NullPointerException();
        }, i -> ((i < 1000) ? (i + " HUNDRED") : (i + " THOUSANDS"))).orElse("UNKNOWN")).toList();
        assertEquals("[" + "0 ONES, 1 ONES, 8 ONES, " + "27 TENS, 64 TENS, " + "125 HUNDRED, 216 HUNDRED, 343 HUNDRED, 512 HUNDRED, 729 HUNDRED, " + "1000 THOUSANDS, 1331 THOUSANDS, 1728 THOUSANDS]", guess.toString());
    }
    
    @Test
    public void testResultMapFirst_AllNull() {
        val nums = IntFuncList.loop(13).map(i -> i * i * i).boxed().toList();
        val guess = nums.map(num -> (String) Result.valueOf(num).mapFirst(i -> null, i -> null, i -> null).get()).toList();
        assertEquals("[null, null, null, null, null, null, null, null, null, null, null, null, null]", guess.toString());
    }
    
    @Test
    public void testResultMapFirst_AllException() {
        // The first one is used.
        val nums = IntFuncList.loop(13).map(i -> i * i * i).boxed().toList();
        val guess = nums.map(num -> (String) Result.valueOf(num).mapFirst(i -> {
            throw new ArrayIndexOutOfBoundsException(-1);
        }, i -> {
            throw new NullPointerException();
        }, i -> {
            throw new NumberFormatException();
        }).toString()).toList();
        assertEquals("[" + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, " + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }" + "]", guess.toString());
    }
    
    @Test
    public void testResultMapFirst_OneNullAllException() {
        val nums = IntFuncList.loop(13).map(i -> i * i * i).boxed().toList();
        val guess = nums.map(num -> (String) Result.valueOf(num).mapFirst(i -> null, i -> {
            throw new NullPointerException();
        }, i -> null).get()).toList();
        assertEquals("[null, null, null, null, null, null, null, null, null, null, null, null, null]", guess.toString());
    }
    
    @Test
    public void testExceptionInIf() {
        // Exception thrown in ifXXX is propagated out.
        try {
            Result.ofNull().ifNull(() -> {
                throw new IndexOutOfBoundsException("-1");
            });
            fail("Expect an exception!");
        } catch (IndexOutOfBoundsException exception) {
            assertEquals("java.lang.IndexOutOfBoundsException: -1", "" + exception);
        }
    }
    
    @Test
    public void testExceptionInWhen() {
        // Exception thrown in whenXXX is captured as the result.
        val result = Result.ofNull().whenAbsentGet(() -> {
            throw new IndexOutOfBoundsException();
        });
        assertEquals("Result:{ Exception: java.lang.IndexOutOfBoundsException }", "" + result);
    }
}
