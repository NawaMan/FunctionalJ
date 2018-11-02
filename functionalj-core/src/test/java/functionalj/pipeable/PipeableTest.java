package functionalj.pipeable;

import static functionalj.pipeable.Catch.orElse;
import static functionalj.pipeable.Catch.orElseGet;
import static functionalj.pipeable.Catch.toResult;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import functionalj.function.FunctionInvocationException;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class PipeableTest {
    
    @Test
    public void testBasic() {
        val str1 = (Pipeable<String>)()->"Test";
        assertEquals((Integer)4, str1.pipe(String::length));
    }
    
    @Test
    public void testBasicNull() {
        val str1 = (Pipeable<String>)()->null;
        assertEquals(null, str1.pipe(String::length));
    }
    
    @Test
    public void testNullSelf() {
        val str1 = (Pipeable<String>)()->null;
        assertEquals("0", "" + str1.pipe(
                NullSafeOperator.of(str -> Nullable.of(str).map(String::length).orElse(0))
            ));
    }
    
    @SuppressWarnings("null")
	@Test
    public void testRuntimeException() {
        val src1 = (String)null;
        val str1 = (Pipeable<String>)()->src1.toUpperCase();
        try {
            assertEquals(4, str1.pipe(String::length).intValue());
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
    }
    
    @Test
    public void testException() {
        val str1 = (Pipeable<String>)(()-> { throw new IOException(); });
        try {
            assertEquals(4, str1.pipe(String::length).intValue());
        } catch (FunctionInvocationException e) {
            // Expected
        }
    }
    
    @Test
    public void testToResult() {
        val str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("Result:{ Value: 4 }", 
                str1
                .pipe(
                    String::length, 
                    toResult()
                ) + "");
        
        val str2 = (Pipeable<String>)(()-> null);
        assertEquals("Result:{ Value: null }", 
                str2
                .pipe(
                    String::length, 
                    toResult()
                ) + "");

        val str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("Result:{ Exception: java.io.IOException }", 
                str3
                .pipe(
                    String::length, 
                    toResult()
                ) + "");
    }
    
    @Test
    public void testOrElse() {
        val str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("4", 
                str1
                .pipe(
                    String::length, 
                    orElse(0)
                ) + "");
        
        val str2 = (Pipeable<String>)(()-> null);
        assertEquals("0", 
                str2
                .pipe(
                    String::length, 
                    orElse(0)
                ) + "");

        val str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("0", 
                str3
                .pipe(
                    String::length, 
                    orElse(0)
                ) + "");
    }
    
    @Test
    public void testOrElseGet() {
        val str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("4", 
                str1
                .pipe(
                    String::length, 
                    orElseGet(()->0)
                ) + "");
        
        val str2 = (Pipeable<String>)(()-> null);
        assertEquals("0", 
                str2
                .pipe(
                    String::length, 
                    orElseGet(()->0)
                ) + "");

        val str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("0", 
                str3
                .pipe(
                    String::length, 
                    orElseGet(()->0)
                ) + "");
    }
    @Test
    public void testAll() {
        val str = (Pipeable<String>)(()-> "Four");
        val map = Collections.singletonMap(4, "Four");
        assertEquals("4", "" +str.pipe(
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
        assertEquals("4", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length
            ));
        assertEquals("Four", "" +str.pipe(
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get,
                String::length,
                map::get
            ));
    }
    
}
