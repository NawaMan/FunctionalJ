package functionalj.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import functionalj.types.result.Result;
import functionalj.types.result.validator.Validator;
import lombok.val;

public class ResultTest {

    @Test
    public void testResultFom() {
        assertEquals("Result:{ Value: VALUE }", "" + Result.from(()->"VALUE"));
        assertEquals("Result:{ Exception: functionalj.functions.FunctionInvocationException: java.io.IOException }",
                "" + Result.from(()->{ throw new IOException(); }));
    }
    @Test
    public void testResult_value() {
        val result = Result.of("VALUE");
        assertEquals("Result:{ Value: VALUE }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_null() {
        val result = Result.of(null);
        assertEquals("Result:{ Value: null }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNotNull());
        assertTrue (result.isNull());
    }
    
    @Test
    public void testResult_exception() {
        val result = Result.of((String)null).ensureNotNull();
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", "" + result);
        assertFalse(result.isValue());
        assertTrue (result.isException());
        assertFalse(result.isPresent());
        assertFalse(result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_map() {
        val result = Result.of("VALUE").map(str -> str.length());
        assertEquals("Result:{ Value: 5 }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
        assertFalse(result.isNull());
    }
    
    @Test
    public void testResult_failableMap() {
        val result = Result.of("VALUE").map(str -> new UnsupportedOperationException("Not support."));
        assertEquals("Result:{ Value: java.lang.UnsupportedOperationException: Not support. }", "" + result);
        assertTrue (result.isValue());
        assertFalse(result.isException());
        assertTrue (result.isPresent());
        assertTrue (result.isNotNull());
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
        assertEquals("Result:{ Value: [VALUE,[]] }", "" + Result.of("VALUE").validate(validator1, validator2));
        assertEquals("Result:{ Value: [value,["
                +   "functionalj.types.result.ValidationException: Not upper case, "
                +   "functionalj.types.result.ValidationException: No upper case"
                + "]] }",
                "" + Result.of("value").validate(validator1, validator2, validator3));
        assertEquals("Result:{ Value: [,["
                +   "functionalj.types.result.ValidationException: No upper case, "
                +   "functionalj.types.result.ValidationException: Empty]] }",
                "" + Result.of("").validate(validator1, validator2, validator3));
        
        assertEquals("Result:{ Value: [null,["
                +   "functionalj.types.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.types.result.ValidationException: java.lang.NullPointerException, "
                +   "functionalj.types.result.ValidationException: java.lang.NullPointerException"
                + "]] }",
                "" + Result.of((String)null).validate(validator1, validator2, validator3));
    }
    
}
