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
package functionalj.lens.lenses;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theNumber;
import org.junit.Test;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;

public class DoubleToDoubleAccessPrimitiveTest {
    
    @Test
    public void testGreaterThan() {
        DoubleFuncList numbers = IntFuncList.range(0, 10).mapToDouble();
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(5)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(() -> 5)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(n -> 10 - n)));
        assertAsString("[6.0, 7.0, 8.0, 9.0]", numbers.filter(theNumber.thatGreaterThan(theNumber.minus(10).negate())));
    }
}
