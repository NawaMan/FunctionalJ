// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;
import functionalj.list.FuncList;
import lombok.val;

public class AsStreamPlusTest {
    
    @Test
    public void testToFuncList_finiteStream() {
        assertAsString("[0, 0, 0, 0, 0]", StreamPlus.generate(() -> 0).limit(5).toFuncList());
    }
    
    @Ignore("Too long - run manually.")
    @Test
    public void testToFuncList_infiniteStream() {
        try {
            StreamPlus.generate(() -> 0).toFuncList();
            fail("It is impossible!!");
        } catch (OutOfMemoryError e) {
            // Expected
        }
    }
    
    @Test
    public void testSum() {
        val list = FuncList.of("1", "2", "3", "4", "5");
        assertAsString("15", list.sumToInt(theString.asInteger()));
        assertAsString("15", list.sumToLong(theString.asLong()));
        assertAsString("15.0", list.sumToDouble(theString.asDouble()));
        assertAsString("15", list.sumToBigInteger(theString.asBigInteger()));
        assertAsString("15", list.sumToBigDecimal(theString.asBigDecimal()));
        assertAsString("Result:{ Value: 15 }", list.sum(theString.asBigDecimal()));
    }
    
    @Test
    public void testAverage() {
        val list = FuncList.of("1", "2", "3", "4", "5");
        assertAsString("OptionalDouble[3.0]", list.average(theString.asInteger()));
        assertAsString("OptionalDouble[3.0]", list.average(theString.asLong()));
        assertAsString("OptionalDouble[3.0]", list.average(theString.asDouble()));
        assertAsString("Result:{ Value: 3 }", list.average(theString.asBigDecimal()));
    }
}
