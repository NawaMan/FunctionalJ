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
package functionalj.list.doublelist;

import static functionalj.lens.Access.theDouble;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.stream.Step;
import lombok.val;

public class DoubleStreamPlusTest {
    
    @Test
    public void segmentByPercentiles() {
        val logs = new ArrayList<String>();
        Step.from(0.0).step(1)
        .acceptWhile(theDouble.thatLessThanOrEqualsTo(10.00))
        .toFuncList()
        .segmentByPercentiles(25, 75)
        .forEach(each -> {
            logs.add(String.format("Found: %d\n", each.size()));
            each.forEach(d -> logs.add(String.format("    %f\n", d)));
        });
        assertEquals(
                "[Found: 3\n"
                + ",     0.000000\n"
                + ",     1.000000\n"
                + ",     2.000000\n"
                + ", Found: 5\n"
                + ",     3.000000\n"
                + ",     4.000000\n"
                + ",     5.000000\n"
                + ",     6.000000\n"
                + ",     7.000000\n"
                + ", Found: 3\n"
                + ",     8.000000\n"
                + ",     9.000000\n"
                + ",     10.000000\n"
                + "]",
                logs.toString());
    }
}
