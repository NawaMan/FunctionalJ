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
package functionalj.map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.map.FuncMap;

public class ImmutableMapTest {

    @Test
    public void testGetFindSelect() {
        assertEquals("Two",        "" + FuncMap.of("1", "One", "2", "Two").get("2"));
        assertEquals("Three",      "" + FuncMap.of("1", "One", "2", "Two").getOrDefault("3", "Three"));
        assertEquals("Two",        "" + FuncMap.of("1", "One", "2", "Two").findBy("2").get());
        assertEquals("Three",      "" + FuncMap.of("1", "One", "2", "Two").findBy("3").orElse("Three"));
        assertEquals("[One, Two]", "" + FuncMap.of("1", "One", "2", "Two", "10", "Ten").select(key -> key.length() == 1));
    }
    
    @Test
    public void testToString() {
        assertEquals("[1, 2]",         "" + FuncMap.of("1", "One", "2", "Two").keys().sorted());
        assertEquals("[One, Two]",     "" + FuncMap.of("1", "One", "2", "Two").values().sorted());
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("1", "One", "2", "Two").sorted());
    }
    
    @Test
    public void testWith() {
        assertEquals("{1:One, 2:Two, 3:Three}", "" + FuncMap.of("1", "One", "2", "Two").with("3", "Three").sorted());
        assertEquals("{1:Un, 2:Two}",           "" + FuncMap.of("1", "One", "2", "Two").with("1", "Un").sorted());
        assertEquals("{1:One, 2:Du}",           "" + FuncMap.of("1", "One", "2", "Two").with("2", "Du").sorted());
    }
    
    @Test
    public void testSorted() {
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("1", "One", "2", "Two").sorted());
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("2", "Two", "1", "One").sorted());
    }
    
    @Test
    public void testDefaultTo() {
//        assertEquals("{1:One, 2:Two, 3:Three}", "" + FuncMap.of("1", "One", "2", "Two").defaultTo("3", "Three"));
        assertEquals("{1:One, 2:Two}",          "" + FuncMap.of("1", "One", "2", "Two").defaultTo("2", "Four"));
    }
    
    @Test
    public void testDuplicateElement() {
        assertEquals("{1:One}", "" + FuncMap.of("1", "One", "1", "Two").sorted());
    }
}
