// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.f;
import static functionalj.result.Result.Do;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.stream.StreamPlus;
import functionalj.validator.Validator;
import lombok.val;

public class ResultTest {
    
    private static final Result<String> result = Result.valueOf("Test");
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testMap() {
        assertStrings("Result:{ Value: Test }", result);
        assertStrings("Result:{ Value: 4 }",    result.map(str -> str.length()));
    }
    
    @Test
    public void testMapWith() {
        result.mapWith(Func.f((a, b) -> a + b), Result.valueOf("-Value")).ifException(e -> {e.printStackTrace(); });
        assertStrings("Result:{ Value: Test-Value }", result.mapWith(f((a, b) -> a + b), Result.valueOf("-Value")));
        assertStrings("Result:{ Exception: java.lang.IllegalAccessException }", 
                result.mapWith(f((a, b) -> a + b), Result.ofException(new IllegalAccessException())));
    }
    
    @Test
    public void testResultFom() {
        assertEquals("Result:{ Value: VALUE }", "" + Result.of(()->"VALUE"));
        assertEquals("Result:{ Exception: java.io.IOException }",
                "" + Result.of(f(()->{ throw new IOException(); })));
    }
    @Test
    public void testResult_value() {
        val result = Result.valueOf("VALUE");
        assertEquals("Result:{ Value: VALUE }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_null() {
        val result = Result.valueOf(null);
        assertEquals("Result:{ Value: null }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertFalse(result.isPresent());
        assertTrue (result.isNull());
    }
    
    @Test
    public void testResult_exception() {
        val result = Result.valueOf((String)null).ensureNotNull();
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + result);
        assertFalse(result.isValue());
        assertTrue (result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map() {
        val result = Result.valueOf("VALUE").map(str -> str.length());
        assertEquals("Result:{ Value: 5 }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_failableMap() {
        val result = Result.valueOf("VALUE").map(str -> new UnsupportedOperationException("Not support."));
        assertEquals("Result:{ Value: java.lang.UnsupportedOperationException: Not support. }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map_null() {
        val result = Result.valueOf("VALUE").map(str -> (String)null).map(String::length);
        assertEquals("Result:{ Value: null }", "" + result);
    }
    @Test
    public void testResult_validate() {
        val validator1 = Validator.of((String s) -> s.toUpperCase().equals(s), "Not upper case");
        val validator2 = Validator.of((String s) -> s.matches("^.*[A-Z].*$"),  "No upper case");
        val validator3 = Validator.of((String s) -> !s.isEmpty(),              "Empty");
        assertEquals("Result:{ Value: (VALUE,[]) }", "" + Result.valueOf("VALUE").validate(validator1, validator2));
        assertEquals("Result:{ Value: (value,["
                +   "functionalj.result.ValidationException: Not upper case, "
                +   "functionalj.result.ValidationException: No upper case"
                + "]) }",
                "" + Result.valueOf("value").validate(validator1, validator2, validator3));
        assertEquals("Result:{ Value: (,["
                +   "functionalj.result.ValidationException: No upper case, "
                +   "functionalj.result.ValidationException: Empty]) }",
                "" + Result.valueOf("").validate(validator1, validator2, validator3));
        
        assertEquals("Result:{ Value: (null,["
                +   "functionalj.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.result.ValidationException: java.lang.NullPointerException"
                + "]) }",
                "" + Result.valueOf((String)null).validate(validator1, validator2, validator3));
    }
    @Test
    public void testResult_validate_oneline() throws Exception {
        assertEquals("Result:{ Invalid: Has upper case: \"VALUE\" }", 
                Result.valueOf("VALUE")
                .validate("Has upper case: \"%s\"", s -> !s.matches("^.*[A-Z].*$"))
                .toString());
        
        assertEquals("Result:{ Invalid: Too long: \"VALUE\" }", 
                Result.valueOf("VALUE")
                .validate("Too long: \"%s\"", String::length, l -> l < 3)
                .toString());
    }
    
    @Test
    public void testResultOf() {
        assertStrings("Result:{ Value: One }", Result.valueOf("One"));
        
        assertStrings("Result:{ Value: One,Two }",
                Result.of("One", "Two", (a, b)-> a + "," + b));
        
        assertStrings("Result:{ Value: One,Two,Three }",
                Result.of("One", "Two", "Three", (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult() {
        assertStrings(
                "Result:{ Value: One }",
                Result.ofResult(Result.valueOf("One")));
        assertStrings("Result:{ Value: One,Two }",
                Result.ofResults(
                        Result.valueOf("One"),
                        Result.valueOf("Two"),
                        (a, b)-> a + "," + b));
        
        assertStrings("Result:{ Value: One,Two,Three }",
                Result.ofResults(
                        Result.valueOf("One"),
                        Result.valueOf("Two"),
                        Result.valueOf("Three"),
                        (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult_withException() {
        assertStrings("Result:{ Exception: functionalj.function.FunctionInvocationException: Test fail }",
                Result.ofResults(
                        Result.valueOf("One"),
                        Result.ofException("Test fail"),
                        Result.valueOf("Three"),
                        (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultPeek() {
        assertStrings("Result:{ Value: 3 }", 
                Result.valueOf("One")
                .pipe(
                    r -> r.map(String::length),
                    String::valueOf));
    }
    
    @Test
    public void testResultDo() {
        assertStrings(
                "Result:{ Value: One }",
                Result.ofResult(Result.valueOf("One")));
        
        assertStrings("Result:{ Value: One,Two }",
                Do(
                  ()->"One",
                  ()->"Two",
                  (a, b)-> a + "," + b));
    }
    
    @Test
    public void testResultDo_withException() {
        assertStrings(
                "Result:{ Exception: java.lang.RuntimeException: Test exception }",
                Result.ofResult(Result.ofException(new RuntimeException("Test exception"))));
        
        assertStrings("Result:{ Exception: java.lang.RuntimeException: Test exception }",
                Do(
                  ()->"One",
                  ()->{ throw new RuntimeException("Test exception"); },
                  (a, b)-> a + "," + b));
    }
    
    @Test
    public void testResultmapFirst() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.valueOf(num)
                    .mapFirst(
                        i -> ((i < 10)   ? (i + " ONES")    : null),
                        i -> ((i < 100)  ? (i + " TENS")    : null),
                        i -> ((i < 1000) ? (i + " HUNDRED") : (i + " THOUSANDS"))
                    ).orElse("UNKNOWN"))
                .toList();
        assertEquals(
                "["
                + "0 ONES, 1 ONES, 8 ONES, "
                + "27 TENS, 64 TENS, "
                + "125 HUNDRED, 216 HUNDRED, 343 HUNDRED, 512 HUNDRED, 729 HUNDRED, "
                + "1000 THOUSANDS, 1331 THOUSANDS, 1728 THOUSANDS]",
                guess.toString());
    }
    
    @Test
    public void testResultmapFirst_Exception() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.valueOf(num)
                    .mapFirst(
                        i -> ((i < 10)   ? (i + " ONES")    : null),
                        i -> { if (i < 100) return (i + " TENS"); throw new NullPointerException(); },
                        i -> ((i < 1000) ? (i + " HUNDRED") : (i + " THOUSANDS"))
                    ).orElse("UNKNOWN"))
        .toList();
        assertEquals(
                "["
                + "0 ONES, 1 ONES, 8 ONES, "
                + "27 TENS, 64 TENS, "
                + "125 HUNDRED, 216 HUNDRED, 343 HUNDRED, 512 HUNDRED, 729 HUNDRED, "
                + "1000 THOUSANDS, 1331 THOUSANDS, 1728 THOUSANDS]",
                guess.toString());
    }
    
    @Test
    public void testResultmapFirst_AllNull() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.valueOf(num)
                    .mapFirst(
                        i -> null,
                        i -> null,
                        i -> null
                    ).get())
        .toList();
        assertEquals(
                "[null, null, null, null, null, null, null, null, null, null, null, null, null]",
                guess.toString());
    }
    
    @Test
    public void testResultmapFirst_AllException() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.valueOf(num)
                    .mapFirst(
                        i -> { throw new ArrayIndexOutOfBoundsException(-1); },
                        i -> { throw new NullPointerException(); },
                        i -> { throw new NumberFormatException(); }
                    ).toString())
        .toList();
        assertEquals(
                "["
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }, "
                + "Result:{ Exception: java.lang.ArrayIndexOutOfBoundsException: Array index out of range: -1 }"
                + "]",
                guess.toString());
    }
    
    @Test
    public void testResultmapFirst_OneNullAllException() {
        val nums  = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess = nums
        .map(num -> (String)Result.valueOf(num)
                    .mapFirst(
                        i -> null,
                        i -> { throw new NullPointerException(); },
                        i -> null
                    ).get())
        .toList();
        assertEquals(
                "[null, null, null, null, null, null, null, null, null, null, null, null, null]",
                guess.toString());
    }
    
    // TODO - Fail gradle build - Put this back.
//    @Test
//    public void testResultmapFirst_Mix() {
//        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
//        val guess
//                = nums
//                .map(num -> Result.value(num)
//                    .mapFirst(
//                        i -> ((i < 10)   ? i             : null),
//                        i -> ((i < 100)  ? (i + " TENS") : null)
//                    ).orElse("UNKNOWN"))
//                .toList();
//        assertEquals(
//                "[0, 1, 8, 27 TENS, 64 TENS, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN]",
//                guess.toString());
//    }
    
//    
//    // TODO - Don't know why when we have multiple layer of flatMap, Eclipse hangs. Let deal with this later.
//    //        Seems to goes away after upgrade Eclipse to 2018-12 ... but no time to deal with it now.
//    @Test
//    public void testResultFor() {
//        val res1 = FuncList.of(1, 2, 3, 4);
//        
//        System.out.println(res1.flatMap(s1 -> {
//            return StreamPlus.infiniteInt().limit(s1).toList().flatMap(s2 -> {
//                return Result.value(s1 + " + " + s2 + " = " + (s1 + s2)).toList();
//            });
//        }));
//        
//        System.out.println(For(
//                                StreamPlus.infiniteInt().limit(3).toList(), 
//                (s1)         -> StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2)     -> StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2, s3) -> Result.value(s1 + "-" + s2 + "-" + s3).toList()
//        ));
//        
//        System.out.println(For(
//                StreamPlus.infiniteInt().limit(3).toList(), 
//                StreamPlus.infiniteInt().limit(3).toList(),
//                StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2, s3) -> Result.value(s1 + "-" + s2 + "-" + s3).toList()
//        ));
//    }
//    
//    // -- 2 --
//    
//    public static <I1, I2, O> FuncList<O> For(
//            FuncList<I1>               l1,
//            Func1<I1, FuncList<I2>>    next1,
//            Func2<I1, I2, FuncList<O>> yield) {
//        return l1.flatMap(e1 -> {
//            return next1.apply(e1).flatMap(e2 -> {
//                return yield.apply(e1, e2);
//            });
//        });
//    }
//    
//    public static <I1, I2, O> FuncList<O> For(
//            FuncList<I1>               l1,
//            FuncList<I2>               next1,
//            Func2<I1, I2, FuncList<O>> yield) {
//        return l1.flatMap(e1 -> {
//            return next1.flatMap(e2 -> {
//                return yield.apply(e1, e2);
//            });
//        });
//    }
//    
//    // -- 3 --
//    
//    public static <I1, I2, I3, O> FuncList<O> For(
//            FuncList<I1>                l1,
//            Func1<I1, FuncList<I2>>     next1,
//            Func2<I1, I2, FuncList<I3>> next2,
//            Func3<I1, I2, I3, FuncList<O>> yield) {
//        return l1.flatMap(e1 -> {
//            return next1.apply(e1).flatMap(e2 -> {
//                return next2.apply(e1, e2).flatMap(e3 -> {
//                    return yield.apply(e1, e2, e3);
//                });
//            });
//        });
//    }
//    
//    public static <I1, I2, I3, O> FuncList<O> For(
//            FuncList<I1>                   l1,
//            FuncList<I2>                   next1,
//            FuncList<I3>                   next2,
//            Func3<I1, I2, I3, FuncList<O>> yield) {
//        return l1.flatMap(e1 -> {
//            return next1.flatMap(e2 -> {
//                return next2.flatMap(e3 -> {
//                    return yield.apply(e1, e2, e3);
//                });
//            });
//        });
//    }
//    
//    
//    
    
}
