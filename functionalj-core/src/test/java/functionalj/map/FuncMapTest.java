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
package functionalj.map;

import static functionalj.function.Func.f;
import static functionalj.TestHelper.assertAsString;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Ignore;
import org.junit.Test;
import lombok.val;

public class FuncMapTest {
    
    @Test
    public void testMap() {
        val map1 = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four");
        val map2 = FuncMap.of(1, "ONE", 2, "TWO", 5, "FIVE", 6, "FOUR");
        val merger = f((String s1, String s2) -> s1 + s2);
        assertEquals("{1:OneONE, 2:TwoTWO}", "" + map1.zipWith(map2, merger));
        assertEquals("{1:OneONE, 2:TwoTWO, 3:Threenull, 4:Fournull, 5:nullFIVE, 6:nullFOUR}", "" + map1.zipWith(map2, AllowUnpaired, merger));
    }
    
    @Test
    public void testEquals() {
        val map = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four");
        val map1 = FuncMap.of(1, 3, 2, 3, 3, 5, 4, 4);
        val map2 = map.map(String::length);
        assertTrue(map1.equals(map2));
    }
    
    @Test
    public void testLazy() {
        val counter = new AtomicInteger(0);
        val map = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five", 6, "Six", 7, "Seven");
        val value = map.map(i -> counter.getAndIncrement()).entries().limit(4).join(", ");
        assertAsString("1=0, 2=1, 3=2, 4=3", value);
        assertAsString("4", counter.get());
    }
    
    @Test
    public void testEager() {
        val counter = new AtomicInteger(0);
        val map = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five", 6, "Six", 7, "Seven").eager();
        val value = map.map(i -> counter.getAndIncrement()).entries().limit(4).join(", ");
        assertAsString("1=0, 2=1, 3=2, 4=3", value);
        assertAsString("7", counter.get());
    }
}
