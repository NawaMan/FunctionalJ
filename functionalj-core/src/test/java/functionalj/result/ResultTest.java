package functionalj.result;

import static functionalj.function.Func.f;
import static functionalj.result.Result.Do;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import functionalj.stream.StreamPlus;
import functionalj.validator.Validator;
import lombok.val;

public class ResultTest {
    
    private static final Result<String> result = Result.of("Test");
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testMap() {
        assertStrings("Result:{ Value: Test }", result);
        assertStrings("Result:{ Value: 4 }",    result.map(str -> str.length()));
    }
    
    @Test
    public void testResultFom() {
        assertEquals("Result:{ Value: VALUE }", "" + Result.from(()->"VALUE"));
        assertEquals("Result:{ Exception: java.io.IOException }",
                "" + Result.from(f(()->{ throw new IOException(); })));
    }
    @Test
    public void testResult_value() {
        val result = Result.of("VALUE");
        assertEquals("Result:{ Value: VALUE }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_null() {
        val result = Result.of(null);
        assertEquals("Result:{ Value: null }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertFalse(result.isPresent());
        assertTrue (result.isNull());
    }
    
    @Test
    public void testResult_exception() {
        val result = Result.of((String)null).ensureNotNull();
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + result);
        assertFalse(result.isValue());
        assertTrue (result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map() {
        val result = Result.of("VALUE").map(str -> str.length());
        assertEquals("Result:{ Value: 5 }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_failableMap() {
        val result = Result.of("VALUE").map(str -> new UnsupportedOperationException("Not support."));
        assertEquals("Result:{ Value: java.lang.UnsupportedOperationException: Not support. }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map_null() {
        val result = Result.of("VALUE").map(str -> (String)null).map(String::length);
        assertEquals("Result:{ Value: null }", "" + result);
    }
    @Test
    public void testResult_validate() {
        val validator1 = Validator.of((String s) -> s.toUpperCase().equals(s), "Not upper case");
        val validator2 = Validator.of((String s) -> s.matches("^.*[A-Z].*$"),  "No upper case");
        val validator3 = Validator.of((String s) -> !s.isEmpty(),              "Empty");
        assertEquals("Result:{ Value: (VALUE,[]) }", "" + Result.of("VALUE").validate(validator1, validator2));
        assertEquals("Result:{ Value: (value,["
                +   "functionalj.result.ValidationException: Not upper case, "
                +   "functionalj.result.ValidationException: No upper case"
                + "]) }",
                "" + Result.of("value").validate(validator1, validator2, validator3));
        assertEquals("Result:{ Value: (,["
                +   "functionalj.result.ValidationException: No upper case, "
                +   "functionalj.result.ValidationException: Empty]) }",
                "" + Result.of("").validate(validator1, validator2, validator3));
        
        assertEquals("Result:{ Value: (null,["
                +   "functionalj.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.result.ValidationException: java.lang.NullPointerException"
                + "]) }",
                "" + Result.of((String)null).validate(validator1, validator2, validator3));
    }
    @Test
    public void testResult_validate_oneline() throws Exception {
        assertEquals("Result:{ Invalid: Has upper case: \"VALUE\" }", 
                Result.of("VALUE")
                .validate("Has upper case: \"%s\"", s -> !s.matches("^.*[A-Z].*$"))
                .toString());
        
        assertEquals("Result:{ Invalid: Too long: \"VALUE\" }", 
                Result.of("VALUE")
                .validate("Too long: \"%s\"", String::length, l -> l < 3)
                .toString());
    }
    
    @Test
    public void testResultOf() {
        assertStrings("Result:{ Value: One }", Result.of("One"));
        
        assertStrings("Result:{ Value: One,Two }",
                Result.of("One", "Two", (a, b)-> a + "," + b));
        
        assertStrings("Result:{ Value: One,Two,Three }",
                Result.of("One", "Two", "Three", (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult() {
        assertStrings(
                "Result:{ Value: One }",
                Result.ofResult(Result.of("One")));
        assertStrings("Result:{ Value: One,Two }",
                Result.ofResults(
                        Result.of("One"),
                        Result.of("Two"),
                        (a, b)-> a + "," + b));
        
        assertStrings("Result:{ Value: One,Two,Three }",
                Result.ofResults(
                        Result.of("One"),
                        Result.of("Two"),
                        Result.of("Three"),
                        (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultResult_withException() {
        assertStrings("Result:{ Exception: functionalj.function.FunctionInvocationException: Test fail }",
                Result.ofResults(
                        Result.of("One"),
                        Result.ofException("Test fail"),
                        Result.of("Three"),
                        (a, b, c)-> a + "," + b + "," + c));
    }
    
    @Test
    public void testResultPeek() {
        assertStrings("Result:{ Value: 3 }", 
                Result.of("One")
                .pipe(
                    r -> r.map(String::length),
                    String::valueOf));
    }
    
    @Test
    public void testResultDo() {
        assertStrings(
                "Result:{ Value: One }",
                Result.ofResult(Result.of("One")));
        
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
    public void testResultMapAny() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.of(num)
                    .mapAny(
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
    public void testResultMapAny_Exception() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.of(num)
                    .mapAny(
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
    public void testResultMapAny_AllNull() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.of(num)
                    .mapAny(
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
    public void testResultMapAny_AllException() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.of(num)
                    .mapAny(
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
    public void testResultMapAny_OneNullAllException() {
        val nums  = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess = nums
        .map(num -> (String)Result.of(num)
                    .mapAny(
                        i -> null,
                        i -> { throw new NullPointerException(); },
                        i -> null
                    ).get())
        .toList();
        assertEquals(
                "[null, null, null, null, null, null, null, null, null, null, null, null, null]",
                guess.toString());
    }
    
    @Test
    public void testResultMapAny_Mix() {
        val nums = StreamPlus.loop(13).map(i -> i*i*i).toList();
        val guess
                = nums
                .map(num -> (String)Result.of(num)
                    .mapAny(
                        i -> ((i < 10)   ? i             : null),
                        i -> ((i < 100)  ? (i + " TENS") : null)
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
    
//    
//    // TODO - Don't know why when we have multiple layer of flatMap, Eclipse hangs. Let deal with this later.
//    //        Seems to goes away after upgrade Eclipse to 2018-12 ... but no time to deal with it now.
//    @Test
//    public void testResultFor() {
//        val res1 = FuncList.of(1, 2, 3, 4);
//        
//        System.out.println(res1.flatMap(s1 -> {
//            return StreamPlus.infiniteInt().limit(s1).toList().flatMap(s2 -> {
//                return Result.of(s1 + " + " + s2 + " = " + (s1 + s2)).toList();
//            });
//        }));
//        
//        System.out.println(For(
//                                StreamPlus.infiniteInt().limit(3).toList(), 
//                (s1)         -> StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2)     -> StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2, s3) -> Result.of(s1 + "-" + s2 + "-" + s3).toList()
//        ));
//        
//        System.out.println(For(
//                StreamPlus.infiniteInt().limit(3).toList(), 
//                StreamPlus.infiniteInt().limit(3).toList(),
//                StreamPlus.infiniteInt().limit(3).toList(),
//                (s1, s2, s3) -> Result.of(s1 + "-" + s2 + "-" + s3).toList()
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
