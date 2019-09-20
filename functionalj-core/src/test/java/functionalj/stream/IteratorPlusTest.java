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

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import lombok.val;

public class IteratorPlusTest {
    
    @Test
    public void testPullNext() {
        val iterator = IteratorPlus.from(IntStreamPlus.infinite().limit(5).iterator());
        val logs     = new ArrayList<String>();
        Result<Integer> result;
        while ((result = iterator.pullNext()).isValue()) {
            logs.add("" + result.value());
        }
        assertEquals("[0, 1, 2, 3, 4]", logs.toString());
    }
    
    @Test
    public void testPullNext_null() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        Result<String> result;
        while ((result = iterator.pullNext()).isValue()) {
            logs.add("" + result.value());
        }
        assertEquals("[One, Two, null, Three]", logs.toString());
    }
    
    @Test
    public void testPullNext_multiple() {
        val iterator = IteratorPlus.from(IntStreamPlus.infinite().limit(10).iterator());
        assertEquals("[0, 1, 2, 3, 4]", iterator.pullNext(5).get().toList().toString());
        assertEquals("[5]",             iterator.pullNext(1).get().toList().toString());
        assertEquals("[6, 7]",          iterator.pullNext(2).get().toList().toString());
        assertEquals("[8, 9]",          iterator.pullNext(2).get().toList().toString());
        
        try {
            iterator.pullNext(2).get().stream().toList().toString();
            fail("Exception is expected.");
        } catch (NoMoreResultException e) {
        }
        
        assertTrue(iterator.pullNext(2).isAbsent());
    }
    @Test
    public void testUseNext() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(logs::add);
        iterator.useNext(logs::add);
        assertEquals("[One, Two]", logs.toString());
    }
    @Test
    public void testUseNext_multiple() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(3, s -> logs.add(s.toList().toString()));
        iterator.useNext(1, s -> logs.add(s.toList().toString()));
        iterator.useNext(1, s -> logs.add(s.toList().toString()));
        assertEquals("[[One, Two, null], [Three]]", logs.toString());
    }
    
    @Test
    public void testMapNext() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        iterator.useNext(logs::add);
        iterator.useNext(logs::add);
        assertEquals("[One, Two]", logs.toString());
    }
    @Test
    public void testMapNext_multiple() {
        val iterator = IteratorPlus.from(StreamPlus.of("One", "Two", null, "Three"));
        val logs     = new ArrayList<String>();
        
        logs.add(iterator.mapNext(3, s -> s.toList().toString()).get());
        logs.add(iterator.mapNext(1, s -> s.toList().toString()).get());
        
        try {
            logs.add(iterator.mapNext(1, s -> s.toList().toString()).get());
            fail("Exception is expected.");
        } catch (NoMoreResultException e) {
        }
        
        assertTrue(iterator.mapNext(1, s -> s.toList().toString()).isAbsent());
        
        
        assertEquals("[[One, Two, null], [Three]]", logs.toString());
    }
}
