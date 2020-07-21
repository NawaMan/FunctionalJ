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
package functionalj.stream;

import static functionalj.lens.Access.$S;
import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.list.FuncListDerived;
import functionalj.list.ImmutableList;
import lombok.val;

public class StreamableTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testMap() {
        assertEquals("[3, 3, 5, 4, 4, 3, 5]", 
                Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .map(String::length)
                        .toListString());
    }
    
    @Test
    public void testCycle() {
        val stream = Streamable.cycle("One", "Two", "Three");
        assertStrings("Two, Three, One, Two, Three", stream.skip(1).limit(5).join(", "));
    }
    
    @Test
    public void testSelectiveMap() {
        assertEquals("[One, --Two, Three, Four, Five]", 
                ImmutableList.of("One", "Two", "Three", "Four", "Five").mapOnly("Two"::equals, str -> "--" + str).toString());
    }
    
    @Test
    public void testSplit() {
        assertEquals("([One, Two],[Four, Five],[Three])", 
                FuncListDerived.from((Supplier<Stream<String>>)()->Stream.of("One", "Two", "Three", "Four", "Five"))
                .split($S.length().thatEquals(3),
                       $S.length().thatLessThanOrEqualsTo(4))
                .toString());
    }
    
    @Test
    public void testMapWithPrev() {
        val stream = Streamable.of("One", "Two", "Three").mapWithPrev((prev, element) -> prev.orElse("").length() + element.length());
        assertEquals("3, 6, 8", stream.join(", "));
        assertEquals("3, 6, 8", stream.join(", "));
    }
    
    @Test
    public void testMapWithPrev_each_prior() {
        // Check if mapWithPrev is "Each-Prior" adverb in Q
        val stream = Streamable.infiniteInt().limit(5).mapWithPrev((prev, element) -> element - prev.orElse(0));
        assertEquals("0, 1, 1, 1, 1", stream.join(", "));
    }
    
    @Test
    public void testAccumulate() {
        val stream = Streamable.of(1, 2, 3, 4, 5);
        assertEquals("1, 3, 6, 10, 15", stream.accumulate((a, b)->a+b).join(", "));
    }
    
}
