// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.result;

import static functionalj.TestHelper.assertAsString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import org.junit.Test;
import lombok.val;

public class ValueTest {
    
    private static final Result<String> value = Result.valueOf("Test");
    
    @Test
    public void testGet() {
        // NOTE - Not exactly efficient ... but will do for now.
        val length = value.map(str -> {
            return str.length();
        });
        assertAsString("Result:{ Value: 4 }", length);
    }
    
    @Test
    public void testLazy() {
        val logs = new ArrayList<String>();
        val length = value.map(str -> {
            logs.add(str);
            return str.length();
        });
        logs.add("--- After map but logged first ---");
        assertAsString("Result:{ Value: Test }", value);
        assertAsString("[--- After map but logged first ---]", logs);
        // First use will have 'Test' added to the log.
        assertAsString("Result:{ Value: 4 }", length);
        assertAsString("[--- After map but logged first ---, Test]", logs);
        // Second use does not add 'Test' to the log.
        assertAsString("Result:{ Value: 4 }", length);
        assertAsString("[--- After map but logged first ---, Test]", logs);
    }
    
    @Test
    public void testPrintException() {
        val buffer = new StringWriter();
        val length = value.map(str -> {
            throw new NullPointerException();
        });
        assertAsString("Result:{ Exception: java.lang.NullPointerException }", length.printException(new PrintWriter(buffer)));
        assertAsString("java.lang.NullPointerException", buffer.toString().split("\n")[0]);
    }
}
