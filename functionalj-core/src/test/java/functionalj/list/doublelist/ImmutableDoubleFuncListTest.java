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

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableDoubleFuncListTest {
    
    @Test
    public void testAppend() {
        val orgList = ImmutableDoubleFuncList.of(1.0, 2.0, 3.0);
        val listOne = orgList.append(1001.0);
        val listTwo = orgList.append(1002.0);
        assertEquals("[1.0, 2.0, 3.0]", orgList.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0]", listOne.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0]", listTwo.toString());
        val listOneA = listOne.append(70001.0);
        val listOneB = listOne.append(70002.0);
        val listTwoA = listTwo.append(70001.0);
        val listTwoB = listTwo.append(70002.0);
        assertEquals("[1.0, 2.0, 3.0]", orgList.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0]", listOne.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0]", listTwo.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0, 70001.0]", listOneA.toString());
        assertEquals("[1.0, 2.0, 3.0, 1001.0, 70002.0]", listOneB.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0, 70001.0]", listTwoA.toString());
        assertEquals("[1.0, 2.0, 3.0, 1002.0, 70002.0]", listTwoB.toString());
    }
    
    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableDoubleFuncList.empty();
        val listOne = orgList.appendAll(1001, 1002);
        val listTwo = orgList.appendAll(1001, 1002, 1003);
        assertEquals("[]", orgList.toString());
        assertEquals("[1001.0, 1002.0]", listOne.toString());
        assertEquals("[1001.0, 1002.0, 1003.0]", listTwo.toString());
    }
}
