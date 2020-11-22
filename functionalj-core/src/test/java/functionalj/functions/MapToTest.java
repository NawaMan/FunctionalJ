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
package functionalj.functions;

import static functionalj.functions.MapTo.only;
import static functionalj.functions.MapTo.toTuple;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class MapToTest {

    @Test
    public void testSelectThen() {
        assertEquals("Result:{ Value: null }", "" + Result.valueOf("Hello").map(only(s -> s.length() < 4))); //.then(s -> "--" + s + "")
    }
    
    @Test
    public void testTuple() {
        var f1 = toTuple  ((String s)->s, (String s) -> s.length())
                .thenReduce((a,b)-> a + " - " + b);
        Assert.assertEquals("Result:{ Value: Hello - 5 }", "" + Result.valueOf("Hello").map(f1));
    }

}
