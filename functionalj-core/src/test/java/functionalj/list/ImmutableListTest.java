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
package functionalj.list;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import lombok.val;

public class ImmutableListTest {
    
    @Test
    public void testAppend() {
        assertEquals("[One, Two, Three, Four, Five, Six, Seven]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").append("Six").append("Seven").toString());
    }
    
    @Test
    public void testMap() {
        assertEquals("[3, 3, 5, 4, 4, 3, 5]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .map(String::length)
                        .toString());
    }
    
    @Test
    public void testSubList() {
        assertEquals("[Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .subList(3, 5)
                        .toString());
    }
    
    @Test
    public void testSubList_same() {
        assertEquals("[]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .subList(3, 3)
                        .toString());
    }
    
    @Test
    public void testSorted() {
        assertEquals("[1, 2, One, Two]", "" + ImmutableList.of("1", "One", "2", "Two").sorted());
        assertEquals("[1, 2, One, Two]", "" + ImmutableList.of("2", "Two", "1", "One").sorted());
    }
    @Test
    public void testSplit_ensurePredicateGotCalledOncePerItem() {
        val processedStrings = new ArrayList<String>();
        assertEquals("([One, Two],[Four, Five],[Three])", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five")
                .split($S.length().thatEquals(3),
                       it -> {
                           processedStrings.add(it);
                           return it.length() <= 4;
                       })
                .toString());
        assertEquals("[Three, Four, Five]", "" + processedStrings);
    }
    
}
