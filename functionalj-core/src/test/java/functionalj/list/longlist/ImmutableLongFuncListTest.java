// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.longlist;

import static org.junit.Assert.*;
import org.junit.Test;
import lombok.val;

public class ImmutableLongFuncListTest {
    
    @Test
    public void testAppend() {
        val orgList = ImmutableLongFuncList.of(1, 2, 3);
        val listOne = orgList.append(1001);
        val listTwo = orgList.append(1002);
        assertEquals("[1, 2, 3]", orgList.toString());
        assertEquals("[1, 2, 3, 1001]", listOne.toString());
        assertEquals("[1, 2, 3, 1002]", listTwo.toString());
        val listOneA = listOne.append(70001);
        val listOneB = listOne.append(70002);
        val listTwoA = listTwo.append(70001);
        val listTwoB = listTwo.append(70002);
        assertEquals("[1, 2, 3]", orgList.toString());
        assertEquals("[1, 2, 3, 1001]", listOne.toString());
        assertEquals("[1, 2, 3, 1002]", listTwo.toString());
        assertEquals("[1, 2, 3, 1001, 70001]", listOneA.toString());
        assertEquals("[1, 2, 3, 1001, 70002]", listOneB.toString());
        assertEquals("[1, 2, 3, 1002, 70001]", listTwoA.toString());
        assertEquals("[1, 2, 3, 1002, 70002]", listTwoB.toString());
    }
    
    @Test
    public void testAppend_fromEmpty() {
        val orgList = ImmutableLongFuncList.empty();
        val listOne = orgList.appendAll(1001, 1002);
        val listTwo = orgList.appendAll(1001, 1002, 1003);
        assertEquals("[]", orgList.toString());
        assertEquals("[1001, 1002]", listOne.toString());
        assertEquals("[1001, 1002, 1003]", listTwo.toString());
    }
}
