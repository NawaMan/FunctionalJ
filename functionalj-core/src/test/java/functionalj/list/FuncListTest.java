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

import static functionalj.lens.Access.theString;
import static functionalj.map.FuncMap.underlineMap;
import static functionalj.map.FuncMap.UnderlineMap.LinkedHashMap;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import functionalj.lens.LensTest;
import functionalj.stream.IntStreamPlus;
import lombok.val;

@SuppressWarnings("javadoc")
public class FuncListTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testLazy() {
        val counter = new AtomicInteger(0);
        val value   = IntStreamPlus.range(0, 10).toFuncList().map(i -> counter.getAndIncrement()).limit(4).joinToString(", ");
        assertStrings("0, 1, 2, 3", value);
        assertStrings("4",          counter.get());
    }
    
    @Test
    public void testEager() {
        val counter = new AtomicInteger(0);
        val value   = IntStreamPlus.range(0, 10)
                .toFuncList()
                .eager()
                .map(i -> counter.getAndIncrement())
                .limit(4)
                .joinToString(", ");
        assertStrings("0, 1, 2, 3", value);
        assertStrings("10",          counter.get());
    }
    
    @Test
    public void testEager2() {
        val counter = new AtomicInteger(0);
        val value   = IntStreamPlus.range(0, 10)
                .toFuncList()
                .eager()
                .limit(4)
                .map(i -> counter.getAndIncrement())
                .joinToString(", ");
        assertStrings("0, 1, 2, 3", value);
        assertStrings("4",          counter.get());
    }
    
    @Test
    public void testSkipWhile() {
        assertStrings("[3, 4, 5, 4, 3, 2, 1]",       FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3));
        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i > 3));
    }
    
    @Test
    public void testSkipUntil() {
        assertStrings("[4, 5, 4, 3, 2, 1]",          FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i > 3));
        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i < 3));
    }
    
    @Test
    public void testTakeWhile() {
        val list = new ArrayList<Integer>();
        assertStrings("[1, 2, 3]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i < 4).peek(list::add));
        assertStrings("[1, 2, 3]", list);
        
        list.clear();
        assertStrings("[]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i > 4).peek(list::add));
        assertStrings("[]", list);
    }
    
    @Test
    public void testTakeUtil() {
        val list = new ArrayList<Integer>();
        assertStrings("[1, 2, 3, 4]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i > 4).peek(list::add));
        assertStrings("[1, 2, 3, 4]", list);
        
        list.clear();
        assertStrings("[]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i < 4).peek(list::add));
        assertStrings("[]", list);
    }
    @Test
    public void testSkipTake() {
        val list = new ArrayList<Integer>();
        assertStrings("[3, 4, 5, 4, 3]", FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3).takeUntil(i -> i < 3).peek(list::add));
        assertStrings("[3, 4, 5, 4, 3]", list);
    }
    
    @Test
    public void testIndexes() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[0, 1, 5]", "" + list.indexesOf(theString.length().thatLessThan(4)));
    }
    
    @Test
    public void testSelect() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[(0,One), (1,Two), (5,Six)]", "" + list.query(theString.length().thatLessThan(4)));
    }
    
    @Test
    public void testMapToTuple() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[(One,3), (Two,3), (Three,5), (Four,4), (Five,4), (Six,3), (Seven,5)]",
                "" + list.mapTuple(theString, theString.length()));
    }
    
    @Test
    public void testFlatMapOnly() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[One, One, Two, Two, Three, Four, Five, Six, Six, Seven]",
                "" + list.flatMapOnly(theString.length().thatLessThan(4), s -> ImmutableList.of(s, s)));
    }
    
    @Test
    public void testFlatMapIf() {
        val list = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertEquals("[One, One, Two, Two, Three, Three, Three, Four, Four, Four, Five, Five, Five, Six, Six, Seven, Seven, Seven]",
                "" + list.flatMapIf(
                        theString.length().thatLessThan(4),
                        s -> ImmutableList.of(s, s),
                        s -> ImmutableList.of(s, s, s)));
    }
    
    @Test
    public void testToMap() {
        val index   = new AtomicInteger();
        val theList = ImmutableList.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        val theMap  = theList.mapToMap(
                        "index",   __ -> index.getAndIncrement(), 
                        "word",   theString, 
                        "length", theString.length().asString());
        
        String mapString = 
                With(underlineMap.butWith(LinkedHashMap))
                .run(()->theMap.toString());
        assertEquals("["
                        + "{index:0, word:One, length:3}, "
                        + "{index:1, word:Two, length:3}, "
                        + "{index:2, word:Three, length:5}, "
                        + "{index:3, word:Four, length:4}, "
                        + "{index:4, word:Five, length:4}, "
                        + "{index:5, word:Six, length:3}, "
                        + "{index:6, word:Seven, length:5}"
                    + "]",
                    mapString);
    }
    
    @Test
    public void testFillNull() {
        val drivers = FuncList.of(
                new LensTest.Driver(new LensTest.Car("Red")),
                new LensTest.Driver(new LensTest.Car(null)),
                new LensTest.Driver(new LensTest.Car("Blue"))
                );
        val driversOfCarsWithColors = drivers.fillNull(LensTest.Driver.theDriver.car.color, "Green");
        assertEquals("["
                + "Driver(car=Car(color=Red)), "
                + "Driver(car=Car(color=Green)), "
                + "Driver(car=Car(color=Blue))]",
            driversOfCarsWithColors.toString());
        
    }
    
}
