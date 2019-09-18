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

import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.list.FuncList.listOf;
import static functionalj.map.FuncMap.underlineMap;
import static functionalj.map.FuncMap.UnderlineMap.LinkedHashMap;
import static functionalj.ref.Run.With;
import static functionalj.stream.StreamPlus.infiniteInt;
import static java.util.Comparator.reverseOrder;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.lens.LensTest;
import functionalj.promise.DeferAction;
import functionalj.stream.IntStreamPlus;
import functionalj.stream.StreamElementProcessor;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;
import lombok.val;

@SuppressWarnings("javadoc")
public class FuncListTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testToArray() {
        val array = FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1).toArray(new Integer[0]);
        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", Arrays.toString(array));
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
    
    @Test
    public void testSpawn() {
        val list = FuncList.of("Two", "Three", "Four", "Eleven");
        val first  = new AtomicLong(-1);
        val logs   = new ArrayList<String>();
        list
        .spawn(str -> {
            return Sleep(str.length()*50 + 5).thenReturn(str).defer();
        })
        .forEach(element -> {
            first.compareAndSet(-1, System.currentTimeMillis());
            val start    = first.get();
            val end      = System.currentTimeMillis();
            val duration = Math.round((end - start)/50.0)*50;
            logs.add(element + " -- " + duration);
        });
        assertEquals("["
                + "Result:{ Value: Two } -- 0, "
                + "Result:{ Value: Four } -- 50, "
                + "Result:{ Value: Three } -- 100, "
                + "Result:{ Value: Eleven } -- 150"
                + "]",
                logs.toString());
    }
    
    @Test
    public void testSpawn_limit() {
        val list  = FuncList.of("Two", "Three", "Four", "Eleven");
        val first   = new AtomicLong(-1);
        val actions = new ArrayList<DeferAction<String>>();
        val logs    = new ArrayList<String>();
        list
        .spawn(str -> {
            val action = Sleep(str.length()*50 + 5).thenReturn(str).defer();
            actions.add(action);
            return action;
        })
        .limit(1)
        .forEach(element -> {
            first.compareAndSet(-1, System.currentTimeMillis());
            val start    = first.get();
            val end      = System.currentTimeMillis();
            val duration = Math.round((end - start)/50.0)*50;
            logs.add(element + " -- " + duration);
        });
        assertEquals("[Result:{ Value: Two } -- 0]",
                logs.toString());
        assertEquals(
                "Result:{ Value: Two }, " + 
                "Result:{ Cancelled: Stream closed! }, " +
                "Result:{ Cancelled: Stream closed! }, " + 
                "Result:{ Cancelled: Stream closed! }",
                actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
    }
    
    @Test
    public void testGet() {
        val stream = FuncList.of("Two", "Three", "Four", "Eleven");
        val sumLength = new StreamElementProcessor<String, Integer>() {
            int total = 0;
            @Override
            public void processElement(long index, String element) {
                total += element.length();
            }
            @Override
            public Integer processComplete(long count) {
                return total;
            }
        };
        assertEquals(18, stream.calculate(sumLength).intValue());
    }
    
    @Test
    public void testGet2() {
        val list = FuncList.of("Two", "Three", "Four", "Eleven");
        val sumLength = new StreamElementProcessor<String, Integer>() {
            int total = 0;
            @Override
            public void processElement(long index, String element) {
                total += element.length();
            }
            @Override
            public Integer processComplete(long count) {
                return total;
            }
        };
        val avgLength = new StreamElementProcessor<String, Integer>() {
            int total = 0;
            @Override
            public void processElement(long index, String element) {
                total += element.length();
            }
            @Override
            public Integer processComplete(long count) {
                return (int) ((int)total/count);
            }
        };
        val concat = new StreamProcessor<String, String>() {
            @Override
            public String process(StreamPlus<String> stream) {
                return stream.joinToString();
            }
        };
        assertEquals("(18,4,TwoThreeFourEleven)", list.calculate(sumLength, avgLength, concat).toString());
    }
    
    @Test
    public void testPercentile() {
        val source = FuncList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertStrings("[[0, 1, 2, 3, 4], [5, 6, 7, 8, 9]]",     source.segmentByPercentiles(50, 100));
        assertStrings("[[0, 1, 2], [3, 4], [5, 6, 7], [8, 9]]", source.segmentByPercentiles(25, 50, 75, 100));
        
        assertStrings("["
                + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24], "
                + "[25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49], "
                + "[50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74], "
                + "[75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]"
                + "]", 
                infiniteInt().limit(100).toImmutableList().segmentByPercentiles(25, 50, 75, 100));
        
        assertStrings("["
                + "[99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75], "
                + "[74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50], "
                + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25], "
                + "[24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
                + "]", 
                infiniteInt().limit(100).toImmutableList().segmentByPercentiles(theInteger, reverseOrder(), 25, 50, 75, 100));
        
        assertStrings("["
                + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24], "
                + "[25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49], "
                + "[50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74], "
                + "[75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]"
                + "]", 
                infiniteInt().limit(100).toImmutableList().segmentByPercentiles(listOf(25, 50, 75).map(Double::valueOf)));
        
        assertStrings("[[, 1, 22, 333, 4444], [55555, 666666, 7777777, 88888888, 999999999]]", 
                FuncList.of("", "1", "22", "333", "4444", "55555", "666666", "7777777", "88888888", "999999999")
                .reverse()
                .segmentByPercentiles(String::length, 50, 100));
    }
//    
//    @Test
//    public void testToPercentile() {
//        assertStrings(
//                "[(0,0.0), (1,25.0), (2,50.0), (3,75.0), (4,100.0)]", 
//                FuncList.of(0, 1, 2, 3, 4).toPercentilesOf(theInteger));
//        
//        assertStrings("["
//                + "(0,0), (1,2), (2,4), (3,6), (4,8), (5,10), (6,12), (7,14), (8,16), (9,18), (10,20), "
//                + "(11,22), (12,24), (13,26), (14,28), (15,30), (16,32), (17,34), (18,36), (19,38), (20,40), "
//                + "(21,42), (22,44), (23,46), (24,48), (25,51), (26,53), (27,55), (28,57), (29,59), (30,61), "
//                + "(31,63), (32,65), (33,67), (34,69), (35,71), (36,73), (37,75), (38,77), (39,79), (40,81), "
//                + "(41,83), (42,85), (43,87), (44,89), (45,91), (46,93), (47,95), (48,97), (49,100)]", 
//                infiniteInt().limit(50).toImmutableList().toPercentilesOf(theInteger).map(tuple -> tuple.map2(Double::intValue)));
//        
//        assertStrings("["
//                + "(0,0), (1,2), (2,4), (3,6), (4,8), (5,10), (6,12), (7,14), (8,16), (9,18), (10,20), "
//                + "(11,22), (12,24), (13,26), (14,28), (15,30), (16,32), (17,34), (18,36), (19,38), (20,40), "
//                + "(21,42), (22,44), (23,46), (24,48), (25,51), (26,53), (27,55), (28,57), (29,59), (30,61), "
//                + "(31,63), (32,65), (33,67), (34,69), (35,71), (36,73), (37,75), (38,77), (39,79), (40,81), "
//                + "(41,83), (42,85), (43,87), (44,89), (45,91), (46,93), (47,95), (48,97), (49,100)]", 
//                infiniteInt().limit(50).toImmutableList().toPercentilesOf(theInteger).map(toIntPercentiles()));
//        
//        assertStrings("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
//                infiniteInt().limit(10).toImmutableList().toPercentilesOf(theInteger).map(toPercentileElements()));
//        
//        assertStrings("[(0,0), (1,11), (2,22), (3,33), (4,44), (5,55), (6,66), (7,77), (8,88), (9,100)]", 
//                infiniteInt().limit(10).toImmutableList().toPercentilesOf(theInteger).map(toIntPercentiles()));
//        assertStrings("[0, 11, 22, 33, 44, 55, 66, 77, 88, 100]", 
//                infiniteInt().limit(10).toImmutableList().toPercentilesOf(theInteger).map(toPercentileIntValues()));
//        
//        
//        // WRONG!!!
//        assertStrings(
//                "[(0,0.0), (0,25.0), (0,50.0), (0,75.0), (0,100.0)]", 
//                FuncList.of(0, 0, 0, 0, 0).toPercentilesOf(theInteger));
//    }
    
}
