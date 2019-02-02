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
package functionalj.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.stream.Streamable;
import lombok.val;

public class StreamableHelperTest {
    
    @Test
    public void at() {
        val list     = Arrays.asList("One", "Two", "Three");
        val valueRef = new AtomicReference<String>();
        
        assertTrue(Streamable.Helper.hasAt(list.stream(), 0, valueRef));
        assertEquals("One", valueRef.get());
        
        assertTrue(Streamable.Helper.hasAt(list.stream(), 1, valueRef));
        assertEquals("Two", valueRef.get());

        assertTrue(Streamable.Helper.hasAt(list.stream(), 2, valueRef));
        assertEquals("Three", valueRef.get());
        
        assertFalse(Streamable.Helper.hasAt(list.stream(), 3, valueRef));
        assertEquals(null, valueRef.get());
    }
    
}
