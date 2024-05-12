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
package functionalj.list;

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableFuncListTest {
    
    @Test
    public void testAppend() {
        val orgList = ImmutableFuncList.of("One", "Two", "Three");
        val listOne = orgList.append("Four.point.one");
        val listTwo = orgList.append("Four.point.two");
        assertEquals("[One, Two, Three]", orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        val listOneA = listOne.append("A");
        val listOneB = listOne.append("B");
        val listTwoA = listTwo.append("A");
        val listTwoB = listTwo.append("B");
        assertEquals("[One, Two, Three]", orgList.toString());
        assertEquals("[One, Two, Three, Four.point.one]", listOne.toString());
        assertEquals("[One, Two, Three, Four.point.two]", listTwo.toString());
        assertEquals("[One, Two, Three, Four.point.one, A]", listOneA.toString());
        assertEquals("[One, Two, Three, Four.point.one, B]", listOneB.toString());
        assertEquals("[One, Two, Three, Four.point.two, A]", listTwoA.toString());
        assertEquals("[One, Two, Three, Four.point.two, B]", listTwoB.toString());
    }
    
    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableFuncList.empty();
        val listOne = orgList.appendAll("Four.point.one-A", "Four.point.one-B");
        val listTwo = orgList.appendAll("Four.point.two-A", "Four.point.two-B", "Four.point.two-C");
        assertEquals("[]", orgList.toString());
        assertEquals("[Four.point.one-A, Four.point.one-B]", listOne.toString());
        assertEquals("[Four.point.two-A, Four.point.two-B, Four.point.two-C]", listTwo.toString());
    }
}
