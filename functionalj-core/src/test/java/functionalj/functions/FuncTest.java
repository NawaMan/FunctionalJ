// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.functions;

import static functionalj.function.Apply.$;
import static functionalj.function.Func.f;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.function.Func2;
import functionalj.function.Func4;
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
    }
    
    @Test
    public void testCompactApply() throws Exception {
        Func4<Integer, Integer, Integer, Integer, Integer> sum = f((a, b, c, d) -> a + b + c + d);
        assertEquals(14, $($($($(sum, 5), 4), 3), 2).intValue());
    }
    
    @Test
    public void testAutoCurry() throws Exception {
        val sum4 = f((a, b, c, d) -> "" + a + b + c + d);
        assertEquals("5432", 
                sum4
                .applyTo(5)
                .applyTo(4)
                .applyTo(3)
                .apply  (2));
    }
    @Test
    public void testElevate() throws Exception {
        assertEquals(true,             Func.elevate(String::contains, "Hello"     ).apply("Hello World!"));
        assertEquals("Hello World!!!", Func.elevate(String::concat,   "!!"        ).apply("Hello World!"));
        assertEquals("Hello-World!",   Func.elevate(String::replaceAll, "[ ]", "-").apply("Hello World!"));
    }
    
    @Test
    public void testDefer() {
        val func = ((Func2<Integer, Integer, Integer>)(a, b) -> a+b).forPromise();
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
