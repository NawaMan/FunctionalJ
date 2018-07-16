package functionalj.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

public class FuncTest {
    
    @Test
    public void test() throws Exception {
        assertEquals("Test", Func.from(()->"Test").get());
        
        try {
            Func.from(()->{ throw new NullPointerException(); }).get();
            fail();
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            Func.from(()->{ throw new FileNotFoundException(); }).get();
            fail();
        } catch (FunctionInvocationException e) {
            // Expected
            assertEquals("java.io.FileNotFoundException", e.getCause() + "");
        }
        
        assertEquals("Result:{ Value: Test }",                               Func.from(()->"Test").getSafely() + "");
        assertEquals("Result:{ Exception: java.lang.NullPointerException }", Func.from(()->{ throw new NullPointerException(); }).getSafely() + "");

        assertEquals("Test", Func.from(()->"Test").getUnsafe() + "");
        try {
            Func.from(()->{ throw new NullPointerException(); }).getUnsafe();
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
        try {
            Func.from(()->{ throw new FileNotFoundException(); }).getUnsafe();
            fail();
        } catch (FileNotFoundException e) {
            // Expected
        }
        
        // TODO - Test message
    }
    
    @Test
    public void testInstanceFunction() throws Exception {
        assertEquals(true,             Func.of(String::contains,   "Hello"   ).apply("Hello World!"));
        assertEquals("Hello World!!!", Func.of(String::concat,     "!!"      ).apply("Hello World!"));
        assertEquals("Hello-World!",   Func.of(String::replaceAll, "[ ]", "-").apply("Hello World!"));
    }
    
}
