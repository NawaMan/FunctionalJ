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

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.pipeable.WhenNull.defaultTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class PipeLineTest {
    
    @Test
    public void testBasic() {
        var pipeLine = PipeLine
                .from(theString.length())
                .then(theInteger.time(2))
                .then(theInteger.asString())
                .thenReturn();
        var pipeLine2 = PipeLine
                .from(theString.toUpperCase())
                .thenReturn();
        
        var str = Pipeable.of("Test");
        assertEquals("8",    pipeLine.apply("Test"));
        assertEquals("TEST", pipeLine2.apply("Test"));
        assertEquals("8",    str.pipeTo(pipeLine));
        assertEquals("TEST", str.pipeTo(pipeLine2));
        assertEquals("8",    str.pipeTo(pipeLine, pipeLine2));
        
        var strNull = Pipeable.of((String)null);
        assertEquals(null, pipeLine.applyToNull());
        assertEquals(null, pipeLine2.applyToNull());
        assertEquals(null, strNull.pipeTo(pipeLine));
        assertEquals(null, strNull.pipeTo(pipeLine2));
        assertEquals(null, strNull.pipeTo(pipeLine, pipeLine2));
    }
    
    @Test
    public void testHandlingNull() {
        var pipeLine = PipeLine.ofNullable(String.class)
                .then(theString.length())
                .then(theInteger.time(2))
                .then(theInteger.asString())
                .thenReturnOrElse("<none>");
        var pipeLine2 = PipeLine
                .from(theString.toUpperCase())
                .thenReturn();
        
        assertEquals("<none>", pipeLine.applyToNull());
        assertEquals(null,     pipeLine2.applyToNull());
        
        var str = Pipeable.of((String)null);
        assertEquals("<none>", str.pipeTo(pipeLine));
        assertEquals(null,     str.pipeTo(pipeLine2));
        assertEquals("<NONE>", str.pipeTo(pipeLine, pipeLine2));
    }
    
    @Test
    public void testHandlingNullCombine() {
        var pipeLine = PipeLine.ofNullable(String.class)
                .then($S.length())
                .then($I.time(2))
                .then($I.asString())
                .then(defaultTo("<none>"))
                .then($S.toUpperCase())
                .thenReturn();
        
        assertEquals("<NONE>", pipeLine.applyToNull());
        
        var str = Pipeable.of((String)null);
        assertEquals("<NONE>", str.pipeTo(pipeLine));
    }
    
}
