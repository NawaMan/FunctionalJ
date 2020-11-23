// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.pipeable;

import static functionalj.function.Func.f;
import static functionalj.lens.Access.theList;
import static functionalj.list.FuncList.ListOf;
import static functionalj.pipeable.Catch.toResult;
import static functionalj.pipeable.Pipeable.StartBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import functionalj.function.FunctionInvocationException;
import nullablej.nullable.Nullable;

public class PipeableTest {
    
    @Test
    public void testBasic() {
        var str1 = (Pipeable<String>)()->"Test";
        assertEquals((Integer)4, str1.pipeTo(String::length));
    }
    
    @Test
    public void testBasicNull() {
        var str1 = (Pipeable<String>)()->null;
        assertEquals(null, str1.pipeTo(String::length));
    }
    
    @Test
    public void testNullSelf() {
        var str1 = (Pipeable<String>)()->null;
        assertEquals("0", "" + str1
                .pipeTo(
                    NullSafeOperator.of(str -> Nullable.of(str).map(String::length).orElse(0))
                ));
    }
    
    @SuppressWarnings("null")
    @Test
    public void testRuntimeException() {
        var src1 = (String)null;
        var str1 = (Pipeable<String>)()->src1.toUpperCase();
        try {
            assertEquals(4, str1.pipeTo(String::length).intValue());
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
    }
    
    @Test
    public void testException() {
        var str1 = (Pipeable<String>)(()-> { throw new IOException(); });
        try {
            assertEquals(4, str1.pipeTo(String::length).intValue());
        } catch (FunctionInvocationException e) {
            // Expected
        }
    }
    
    @Test
    public void testToResult() {
        var str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("Result:{ Value: 4 }", 
                str1
                .pipeTo(
                    String::length, 
                    toResult()
                ) + "");
        
        var str2 = (Pipeable<String>)(()-> null);
        assertEquals("Result:{ Value: null }", 
                str2
                .pipeTo(
                    String::length, 
                    toResult()
                ) + "");
        
        var str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("Result:{ Exception: java.io.IOException }", 
                str3
                .pipeTo(
                    String::length, 
                    toResult()
                ) + "");
    }
    
    @Test
    public void testFilter() {
        assertEquals("Nullable.EMPTY",         "" +  ListOf(1, 2, 3, 4, 5).__nullable().filter(theList.size().thatIs(3)));
        assertEquals("Optional.empty",         "" +  ListOf(1, 2, 3, 4, 5).__optional().filter(theList.size().thatIs(3)));
        assertEquals("Result:{ Value: null }", "" +  ListOf(1, 2, 3, 4, 5).__result()  .filter(theList.size().thatIs(3)));
    }
    
    @Test
    public void testOrElse() {
        assertEquals("Test",          StartBy(f(()->        "Test"              )).__orElse("This the test"));
        assertEquals("This the test", StartBy(f(()->(String)null                )).__orElse("This the test"));
        assertEquals("This the test", StartBy(f(()->{ throw new IOException(); })).__orElse("This the test"));
    }
    
    @Test
    public void testOrElse_Catch() {
        var str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("4", 
                str1
                .pipeTo(
                    String::length, 
                    Catch.thenReturn(0)
                ) + "");
        
        var str2 = (Pipeable<String>)(()-> null);
        assertEquals("0", 
                str2
                .pipeTo(
                    String::length, 
                    Catch.thenReturn(0)
                ) + "");
        
        var str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("0", 
                str3
                .pipeTo(
                    String::length, 
                    Catch.thenReturn(0)
                ) + "");
    }
    
    @Test
    public void testOrElseGet() {
        var str1 = (Pipeable<String>)(()-> "Test");
        assertEquals("4", 
                str1
                .pipeTo(
                    String::length, 
                    Catch.thenGet(()->0)
                ) + "");
        
        var str2 = (Pipeable<String>)(()-> null);
        assertEquals("0", 
                str2
                .pipeTo(
                    String::length, 
                    Catch.thenGet(()->0)
                ) + "");
        
        var str3 = (Pipeable<String>)(()-> { throw new IOException(); });
        assertEquals("0", 
                str3
                .pipeTo(
                    String::length, 
                    Catch.thenGet(()->0)
                ) + "");
    }
    @Test
    public void testAll() {
        var str = (Pipeable<String>)(()-> "Four");
        var map = Collections.singletonMap(4, "Four");
        assertEquals("4", "" + str
                .pipeTo(
                    String::length
                ));
        assertEquals("Four", "" + str
                .pipeTo(
                    String::length,
                    map::get
                ));
        assertEquals("4", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length
                ));
        assertEquals("Four", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length,
                    map::get
                ));
        assertEquals("4", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length
                ));
        assertEquals("Four", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length,
                    map::get
                ));
        assertEquals("4", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length
                ));
        assertEquals("Four", "" + str
                .pipeTo(
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length,
                    map::get,
                    String::length,
                    map::get
                ));
        assertEquals("4", "" + str
                .pipeTo(
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
        assertEquals("Four", "" + str
                .pipeTo(
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
        assertEquals("4", "" + str
                .pipeTo(
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
        assertEquals("Four", "" + str
                .pipeTo(
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
        assertEquals("4", "" + str
                .pipeTo(
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
        assertEquals("Four", "" + str
                .pipeTo(
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
        assertEquals("4", "" + str
                .pipeTo(
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
        assertEquals("Four", "" + str
                .pipeTo(
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
