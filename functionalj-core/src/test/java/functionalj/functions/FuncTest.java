package functionalj.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

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
    public void testInstanceFunction() throws Exception {
        assertEquals(true,             Func.from(String::contains,   "Hello"   ).apply("Hello World!"));
        assertEquals("Hello World!!!", Func.from(String::concat,     "!!"      ).apply("Hello World!"));
        assertEquals("Hello-World!",   Func.from(String::replaceAll, "[ ]", "-").apply("Hello World!"));
    }
    
}