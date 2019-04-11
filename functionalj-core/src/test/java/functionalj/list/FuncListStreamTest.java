// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.list.FuncListDerived;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import lombok.val;

@SuppressWarnings("javadoc")
public class FuncListStreamTest {
    
    @SuppressWarnings("resource")
    @Test
    public void testStream() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListDerived<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 3, 5]", newList.stream().collect(toList()).toString());
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testAppend() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListDerived<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 3, 5, 6]", newList.append(6).toString());
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testSet() {
        val rawList    = (List<String>)asList("One", "Two", "Three");
        val streamable = (Streamable<String>)(()->StreamPlus.from(rawList.stream()));
        val newList    = new FuncListDerived<String, Integer>(streamable, stream -> stream.map(String::length));
        assertEquals("[3, 1, 5]", newList.with(1, 1).toString());
    }
    
}
