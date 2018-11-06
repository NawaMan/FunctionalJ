package functionalj.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.function.Func2;
import functionalj.function.FunctionInvocationException;
import functionalj.promise.Promise;
import lombok.val;

public class FuncTest {
    
    @Test
    public void test() throws Exception {
        assertEquals("Test", Func.of(()->"Test").get());
        
        try {
            Func.of(()->{ throw new NullPointerException(); }).get();
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
        
        try {
            Func.of(()->{ throw new FileNotFoundException(); }).get();
            fail();
        } catch (FunctionInvocationException e) {
            // Expected
            assertEquals("java.io.FileNotFoundException", e.getCause() + "");
        }
        
        assertEquals("Result:{ Value: Test }",                               Func.of(()->"Test").getSafely() + "");
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", Func.of(()->{ throw new NullPointerException(); }).getSafely() + "");
        
        assertEquals("Test", Func.of(()->"Test").getUnsafe() + "");
        try {
            Func.of(()->{ throw new NullPointerException(); }).getUnsafe();
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
        try {
            Func.of(()->{ throw new FileNotFoundException(); }).getUnsafe();
            fail();
        } catch (FileNotFoundException e) {
            // Expected
        }
        
        // TODO - Test message
    }
    
    @Test
    public void testElevate() throws Exception {
        assertEquals(true,             Func.elevate(String::contains, "Hello"     ).apply("Hello World!"));
        assertEquals("Hello World!!!", Func.elevate(String::concat,   "!!"        ).apply("Hello World!"));
        assertEquals("Hello-World!",   Func.elevate(String::replaceAll, "[ ]", "-").apply("Hello World!"));
    }
    
    @Test
    public void testDefer() {
        val func = ((Func2<Integer, Integer, Integer>)(a, b) -> a+b).defer();
        val a    = Promise.ofValue(5);
        val b    = Promise.ofValue(7);
        val c    = func.apply(a, b);
        assertEquals(12, c.getResult().value().intValue());
    }
    
}
