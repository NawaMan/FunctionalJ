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
    
    @Test
    public void testWhenAbsent() {
        val divide     = Func.F((Integer a, Integer b) -> a/b);
        val safeDivide1 = divide.whenAbsentUse(Integer.MAX_VALUE);
        val safeDivide2 = divide.whenAbsentGet(()->Integer.MAX_VALUE);
        val safeDivide3 = divide.whenAbsentApply(exception ->{
            if (exception instanceof ArithmeticException)
                return Integer.MAX_VALUE;
            throw exception;
        });
        val safeDivide4 = divide.whenAbsentApply((a, b, exception) ->{
            if (b == 0) {
                if (a > 0) return Integer.MAX_VALUE;
                if (a < 0) return Integer.MIN_VALUE;
                throw exception;
            }
            throw exception;
        });
        val safeDivide5 = divide.whenAbsentApply((t, exception) ->{
            if (t._2() == 0) {
                if (t._1() > 0) return Integer.MAX_VALUE;
                if (t._1() < 0) return Integer.MIN_VALUE;
                throw exception;
            }
            throw exception;
        });
        assertEquals(Integer.MAX_VALUE, safeDivide1.apply( 1, 0).intValue());
        assertEquals(Integer.MAX_VALUE, safeDivide2.apply( 1, 0).intValue());
        assertEquals(Integer.MAX_VALUE, safeDivide3.apply( 1, 0).intValue());
        assertEquals(Integer.MAX_VALUE, safeDivide4.apply( 1, 0).intValue());
        assertEquals(Integer.MIN_VALUE, safeDivide4.apply(-1, 0).intValue());
        assertEquals(Integer.MAX_VALUE, safeDivide5.apply( 1, 0).intValue());
        assertEquals(Integer.MIN_VALUE, safeDivide5.apply(-1, 0).intValue());
    }
    
}
