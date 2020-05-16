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

import static functionalj.lens.Access.theLong;
import static functionalj.lens.Access.theString;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static functionalj.stream.longstream.LongStreamPlus.compound;
import static functionalj.stream.longstream.LongStreamPlus.concat;
import static functionalj.stream.longstream.LongStreamPlus.cycle;
import static functionalj.stream.longstream.LongStreamPlus.from;
import static functionalj.stream.longstream.LongStreamPlus.infinite;
import static functionalj.stream.longstream.LongStreamPlus.iterate;
import static functionalj.stream.longstream.LongStreamPlus.longs;
import static functionalj.stream.longstream.LongStreamPlus.loop;
import static functionalj.stream.longstream.LongStreamPlus.naturalNumbers;
import static functionalj.stream.longstream.LongStreamPlus.ones;
import static functionalj.stream.longstream.LongStreamPlus.range;
import static functionalj.stream.longstream.LongStreamPlus.repeat;
import static functionalj.stream.longstream.LongStreamPlus.wholeNumbers;
import static functionalj.stream.longstream.LongStreamPlus.zeroes;
import static functionalj.stream.longstream.LongStreamPlus.zipOf;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.promise.DeferAction;
import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public class LongStreamPlusTest {
    
    @Test
    public void testEmpty() {
        assertEquals("[]", LongStreamPlus.empty().toListString());
    }
    
    @Test
    public void testEmptyLongStream() {
        assertEquals("[]", LongStreamPlus.emptyLongStream().toListString());
    }
    
    @Test
    public void testOf() {
        val longArray  = new long[] {1, 1, 2, 3, 5, 8};
        assertEquals("[1, 1, 2, 3, 5, 8]", LongStreamPlus.of(longArray).toListString());
        
        assertEquals("[1, 1, 2, 3, 5, 8]", LongStreamPlus.longs(1L, 1L, 2L, 3L, 5L, 8L).toListString());
        
        val stream = LongStreamPlus.of(longArray);
        longArray[0] = 0;
        assertEquals("[1, 1, 2, 3, 5, 8]", stream.toListString());
        // NOICE ------^  The value are not changed after.
        
        assertEquals("[]", LongStreamPlus.of(null).toListString());
        
        assertEquals("[]", LongStreamPlus.of(new long[0]).toListString());
    }
    
    @Test
    public void testFrom() {
        assertEquals("[0, 2, 4, 6, 8]", from(LongStream.of(0, 2, 4, 6, 8)).toListString());
        assertEquals("[0, 1, 2, 3, 4]", from(LongStreamPlus.infinite().limit(5)).toListString());
    }
    
    @Test
    public void testZeroes() {
        assertEquals("[0, 0, 0, 0, 0]",    zeroes().limit(5).toListString());
        assertEquals("[0, 0, 0, 0, 0, 0]", zeroes(6).toListString());
    }
    
    @Test
    public void testOnes() {
        assertEquals("[1, 1, 1, 1, 1]",    ones().limit(5).toListString());
        assertEquals("[1, 1, 1, 1, 1, 1]", ones(6).toListString());
    }
    
    @Test
    public void testRepeat() {
        assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1]", repeat(1, 2, 3).limit(10).toListString());
    }
    
    @Test
    public void testCycle() {
        assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]", cycle(1, 2, 3).limit(11).toListString());
    }
    
    @Test
    public void testLoop() {
        assertEquals("[0, 1, 2, 3, 4]", loop().limit(5).toListString());
        assertEquals("[0, 1, 2, 3, 4]", loop(5).toListString());
    }
    
    @Test
    public void testInfinite() {
        assertEquals("[5, 6, 7, 8, 9]", infinite().skip(5).limit(5).toListString());
    }
    
    @Test
    public void testNaturalNumbers() {
        assertEquals("[1, 2, 3, 4, 5]", naturalNumbers().limit(5).toListString());
        assertEquals("[1, 2, 3, 4, 5]", naturalNumbers(5).toListString());
    }
    
    @Test
    public void testWholeNumbers() {
        assertEquals("[0, 1, 2, 3, 4]", wholeNumbers().limit(5).toListString());
        assertEquals("[0, 1, 2, 3, 4]", wholeNumbers(5).toListString());
    }
    
    @Test
    public void testRange() {
        assertEquals("[7, 8, 9, 10, 11]", range(7, 12).toListString());
    }
    
    @Test
    public void testConcat() {
        assertEquals("["
                        + "0, 1, 2, 3, 4, "
                        + "21, 22, 23, 24, 25, 26"
                    + "]", 
                        concat(
                                range(0, 5),
                                range(21, 27)
                        ).toListString());
    }
    
    @Test
    public void testCompound() {
        assertEquals("[32, 64, 128, 256, 512]", 
                compound(1, i -> i * 2).skip(5).limit(5).toListString());
        
        assertEquals("[1, 2, 5, 11, 26]", 
                compound(1, 2, (a, b) -> a * 3 + b).limit(5).toListString());
    }
    
    @Test
    public void testIterate() {
        val longStream = iterate(1, a -> a + 1);
        assertEquals("[6, 7, 8, 9, 10]", longStream.skip(5).limit(5).toListString());
        
        val longStream2 = iterate(1, 1, (a, b) -> a + b);
        assertEquals("[1, 1, 2, 3, 5, 8, 13]", longStream2.limit(7).toListString());
    }
    
    @Test
    public void testZipOf() {
        assertEquals(
                "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8)]", 
                zipOf(range(5, 12), range(2, 12))
                .toListString());
        assertEquals(
                "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (0,9), (0,10), (0,11)]", 
                zipOf(range(5, 12), range(2, 12), 0)
                .toListString());
        assertEquals(
                "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (-1,9), (-1,10), (-1,11)]", 
                zipOf(range(5, 12), -1, range(2, 12), 1)
                .toListString());
        assertEquals(
                "[(5,8), (6,9), (7,10), (8,11), (9,1), (10,1), (11,1)]", 
                zipOf(range(5, 12), -1, range(8, 12), 1)
                .toListString());
        
        
        assertEquals(
                "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]", 
                StreamPlus.zipOf(range(5, 12), range(2, 12), (a, b) -> a + "-" + b)
                .toListString());
        assertEquals(
                "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, 0-9, 0-10, 0-11]", 
                StreamPlus.zipOf(range(5, 12), range(2, 12), 0, (a, b) -> a + "-" + b)
                .toListString());
        assertEquals(
                "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]", 
                StreamPlus.zipOf(range(5, 12), -1, range(2, 12), 1, (a, b) -> a + "-" + b)
                .toListString());
        assertEquals(
                "[5-8, 6-9, 7-10, 8-11, 9-1, 10-1, 11-1]", 
                StreamPlus.zipOf(range(5, 12), -1, range(8, 12), 1, (a, b) -> a + "-" + b)
                .toListString());
        
        
        assertEquals("[7, 9, 11, 13, 15]", 
                zipOf(range(5, 10), range(2, 12), (a, b) -> a + b)
                .toListString());
        assertEquals(
                "[7, 9, 11, 13, 15, 7, 8, 9, 10, 11]", 
                zipOf(range(5, 10), range(2, 12), 0, (a, b) -> a + b)
                .toListString());
        assertEquals(
                "[7, 9, 11, 13, 15, 6, 7, 8, 9, 10]", 
                zipOf(range(5, 10), -1, range(2, 12), 1, (a, b) -> a + b)
                .toListString());
        assertEquals(
                "[13, 15, 17, 19, 10, 11, 12]", 
                zipOf(range(5, 12), -1, range(8, 12), 1, (a, b) -> a + b)
                .toListString());
    }
    
    @Test
    public void testMap() {
        val longStream = longs(1, 1, 2, 3, 5, 8);
        assertEquals(
                "[2, 2, 4, 6, 10, 16]", 
                longStream
                .map(i -> i * 2)
                .toListString());
    }
    
    @Test
    public void testMapToInt() {
        val longStream = longs(1, 1, 2, 3, 5, 8);
        assertEquals(
                "[2, 2, 4, 6, 10, 16]", 
                longStream
                .mapToInt(i -> (int)(i * 2))
                .toListString());
    }
    
    @Test
    public void testMapToLong() {
        val longStream = longs(1, 1, 2, 3, 5, 8);
        assertEquals(
                "[2, 2, 4, 6, 10, 16]", 
                longStream
                .mapToLong(i -> i * 2)
                .toListString());
    }
//    
//    @Test
//    public void testMapToDouble() {
//        val longStream = longs(1, 1, 2, 3, 5, 8);
//        assertEquals(
//                "[2.0, 2.0, 4.0, 6.0, 10.0, 16.0]", 
//                longStream
//                .mapToDouble(i -> i * 2)
//                .toListString());
//    }
    
    @Test
    public void testMapToObj() {
        val longStream = longs(1, 1, 2, 3, 5, 8);
        assertEquals(
                "['1', '1', '2', '3', '5', '8']", 
                longStream
                .mapToObj(i -> "'" + i + "'")
                .toListString());
    }
    
    @Test
    public void testFlatMap() {
        val longStream = longs(1, 2, 3, 5);
        assertEquals(
                "[1, 2, 2, 3, 3, 3, 5, 5, 5, 5, 5]", 
                longStream
                .flatMap(i -> cycle(i).limit(i))
                .toListString());
    }
    
    @Test
    public void testFlatMapToObj() {
        val longStream = longs(1, 2, 3, 5);
        LongFunction<? extends Stream<String>> mapper = i -> Stream.of(cycle(i).limit(i).toListString());
        assertEquals(
                "[[1], [2, 2], [3, 3, 3], [5, 5, 5, 5, 5]]", 
                longStream
                .flatMapToObj(mapper)
                .toListString());
    }
    
    @Test
    public void testFilter() {
        assertEquals(
                "[1, 3, 5, 7, 9]", 
                loop(10)
                .filter(i -> i % 2 == 1)
                .toListString());
        assertEquals(
                "[0, 1, 2]",
                loop(10)
                .filter(theLong.time(3),
                        theLong.thatLessThan(9))
                .toListString());
        assertEquals(
                "[0, 1, 2, 3]",
                loop(10)
                .filterAsObject(
                        (long i) -> "" + (i*3),
                        theString.length().eq(1))
                .toListString());
        assertEquals(
                "[0, 1, 2, 3]",
                loop(10)
                .filterAsObject(
                        theLong.time(3).asString(),
                        theString.length().eq(1))
                .toListString());
    }
    
    @Test
    public void testPeek() {
        val list = new ArrayList<String>();
        assertEquals(
                "[0, 1, 2, 3, 4]", 
                loop(5)
                .peek(i -> list.add("" + i))
                .toListString());
        assertEquals(
                "[0, 1, 2, 3, 4]", 
                list.toString());
    }
    
    @Test
    public void testLimit() {
        assertEquals(
                "[1, 1, 2, 3]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21)
                .limit(4)
                .toListString());
    }
    
    @Test
    public void testSkip() {
        assertEquals(
                "[5, 8, 13, 21]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21)
                .skip(4)
                .toListString());
    }
    
    @Test
    public void testSkipWhile() {
        assertEquals(
                "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .toListString());
        assertEquals(
                "[0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .skipWhile(i -> i != 0)
                .toListString());
    }
    
    @Test
    public void testSkipUtil() {
        assertEquals(
                "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .toListString());
        assertEquals(
                "[0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .skipUntil(i -> i == 0)
                .toListString());
    }
    
    @Test
    public void testTakeWhile() {
        assertEquals(
                "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .toListString());
        assertEquals(
                "[1, 1, 2, 3]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .takeWhile(i -> i != 0)
                .toListString());
    }
    
    @Test
    public void testTakeUtil() {
        assertEquals(
                "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .toListString());
        assertEquals(
                "[1, 1, 2, 3]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map (i -> i % 5)
                .takeUntil(i -> i == 0)
                .toListString());
    }
    
    @Test
    public void testDistinct() {
        assertEquals(
                "[1, 2, 3, 0, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map(i -> i % 5)
                .distinct()
                .toListString());
    }
    
    @Test
    public void testSorted() {
        assertEquals(
                "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map(i -> i % 5)
                .sorted()
                .toListString());
    }
    
    @Test
    public void testSortedBy() {
        assertEquals(
                "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .map(i -> i % 5)
                .sorted()
                .toListString());
    }
    
    @Test
    public void testSortedBy_mapper() {
        assertEquals(
                "[34, 21, 13, 8, 5, 55, 3, 2, 1, 1, 89]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .sortedBy(i -> Math.abs(i - 30))
                .toListString());
        assertEquals(
                "[34 -> 4, 21 -> 9, 13 -> 17, 8 -> 22, 5 -> 25, 55 -> 25, 3 -> 27, 2 -> 28, 1 -> 29, 1 -> 29, 89 -> 59]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .sortedBy(i -> Math.abs(i - 30))
                .mapToObj(i -> "" + i + " -> " + Math.abs(i - 30))
                .toListString());
    }
    
    @Test
    public void testSortedBy_mapper_comparator() {
        assertEquals(
                "[89, 1, 1, 2, 3, 5, 55, 8, 13, 21, 34]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .sortedBy(i -> Math.abs(i - 30), (a, b) -> (int)(b - a))
                .toListString());
    }
    
    @Test
    public void testSortedBy_mapper_object() {
        assertEquals(
                "[1, 1, 13, 2, 21, 3, 34, 5, 55, 8, 89]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .sortedByObj(i -> "" + i)
                .toListString());
    }
    
    @Test
    public void testSortedBy_mapper_object_with_comparator() {
        assertEquals(
                "[13, 21, 34, 55, 89, 1, 1, 2, 3, 5, 8]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
                .sortedByObj(i -> "" + i, (a, b) -> b.length() - a.length())
                .toListString());
    }
    
    @Test
    public void testForEachOrdered() {
        val list = new ArrayList<Long>();
        longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
        .map(i -> Math.abs(i - 30))
        .forEachOrdered(list::add);
        assertEquals(
                "[29, 29, 28, 27, 25, 22, 17, 9, 4, 25, 59]", 
                list.toString());
    }
    
    @Test
    public void testReduce() {
        assertEquals(
                OptionalLong.of(88), 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .reduce((a, b) -> a + b));
    }
    
    @Test
    public void testReduce_withIdentity() {
        assertEquals(
                188, 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .reduce(100, (a, b) -> a + b));
    }
    
    @Test
    public void testCollect() {
        assertEquals(
                ",1,1,2,3,5,8,13,21,34", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .collect(
                        ()-> new StringBuffer(), 
                        (StringBuffer a, long         b)-> a.append("," + b), 
                        (StringBuffer a, StringBuffer b)-> a.append(";" + b))
                .toString());
    }
    
    @Test
    public void testMin() {
        assertEquals(
                "OptionalLong[0]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .map(i -> Math.abs(i - 34))
                .min()
                .toString());
        assertEquals(
                "OptionalLong.empty", 
                longs()
                .min()
                .toString());
    }
    
    @Test
    public void testMax() {
        assertEquals(
                "OptionalLong[33]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .map(i -> Math.abs(i - 34))
                .max()
                .toString());
        assertEquals(
                "OptionalLong.empty", 
                longs()
                .max()
                .toString());
    }
    
    @Test
    public void testCountSize() {
        assertEquals(
                9L, 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .count());
        assertEquals(
                9, 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .size());
    }
    
    @Test
    public void testMatch() {
        assertTrue(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).anyMatch(i -> i == 13));
        assertFalse(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).anyMatch(i -> i == 14));
        
        assertTrue(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).allMatch(i -> i < 50));
        assertFalse(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).allMatch(i -> i < 20));
        
        assertTrue(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).noneMatch(i -> i == 15));
        assertFalse(longs(1, 1, 2, 3, 5, 8, 13, 21, 34).noneMatch(i -> i == 21));
    }
    
    @Test
    public void testFind() {
        assertEquals(OptionalLong.of(21),   longs(1, 1, 2, 3, 5, 8, 13, 21, 34).filter(i -> i == 21).findFirst());
        assertEquals(OptionalLong.empty(), longs(1, 1, 2, 3, 5, 8, 13, 21, 34).filter(i -> i == 55).findFirst());
        
        assertEquals(OptionalLong.of(21),   longs(1, 1, 2, 3, 5, 8, 13, 21, 34).filter(i -> i == 21).findAny());
        assertEquals(OptionalLong.empty(), longs(1, 1, 2, 3, 5, 8, 13, 21, 34).filter(i -> i == 55).findAny());
    }
    
    @Test
    public void testAsStream() {
        assertEquals(
                StreamPlus.of(1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L).toList(),
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).asStream().toList());
    }
    
    @Test
    public void testToArray() {
        assertArrayEquals(
                new long[] {1, 1, 2, 3, 5, 8, 13, 21, 34},
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).toArray());
    }
    
    @Test
    public void testSum() {
        assertEquals(
                88,
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).sum());
    }
    
    @Test
    public void testAverage() {
        assertEquals(
                OptionalDouble.of(14.3),
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34, 55).average());
    }
    
    @Test
    public void testSummaryStatistics() {
        assertEquals(
                "LongSummaryStatistics{count=9, sum=88, min=1, average=9.777778, max=34}",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).summaryStatistics().toString());
    }
    
    @Test
    public void testBoxed() {
        assertEquals(
                StreamPlus.of(1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L).toList(),
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).boxed().toList());
    }
//    
//    @Test
//    public void testToImmutableList() {
//        assertEquals(
//                ImmutableIntFuncList.of(1, 1, 2, 3, 5, 8, 13, 21, 34),
//                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).toImmutableList());
//    }
    
    @Test
    public void testJoinToString() {
        assertEquals(
                "112358132134",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).joinToString());
        assertEquals(
                "1, 1, 2, 3, 5, 8, 13, 21, 34",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).joinToString(", "));
    }
    
    @Test
    public void testPipeable() {
        assertEquals(
                "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).pipable().pipeTo(i -> "-" + i.toListString() + "-").toString());
        assertEquals(
                "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34).pipe(i -> "-" + i.toListString() + "-").toString());
    }
    
    @Test
    public void testSpawn() {
        assertEquals(
                "["
                +   "Result:{ Value: 13 }, "
                +   "Result:{ Value: 21 }, "
                +   "Result:{ Value: 8 }"
                + "]",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                    .spawn(i -> DeferAction.from(()->{ Thread.sleep(100*Math.abs(i - 15)); return i; }))
                    .limit(3)
                    .toListString());
    }
    
    @Test
    public void testAccumulate() {
        assertEquals(
                "[1, 2, 4, 7, 12, 20, 33, 54, 88]",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                    .accumulate((a, i) -> a + i)
                    .toListString());
    }
    
    @Test
    public void testRestate() {
        assertEquals(
                "[1, 0, 1, 1, 2, 3, 5, 8, 13]",
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                    .restate((i, s) -> s.map(j -> j - i))
                    .toListString());
    }
    
    @Test
    public void testMapFirst() {
        assertEquals(
                "["
                + "2, null, 2, null, 2, null, 2, null, 2, null, "
                + "2, null, 2, null"
                + "]",
                wholeNumbers(14)
                    .mapFirst(i -> (Integer)(((i % 2) == 0) ? 2 : null))
                    .toListString());
        assertEquals(
                "["
                + "2, null, 2, 3, 2, null, 2, null, 2, 3, "
                + "2, null, 2, null"
                + "]",
                wholeNumbers(14)
                    .mapFirst(
                            i -> (Integer)(((i % 2) == 0) ? 2 : null),
                            i -> (Integer)(((i % 3) == 0) ? 3 : null))
                    .toListString());
        assertEquals(
                "["
                + "2, null, 2, 3, 2, 5, 2, null, 2, 3, "
                + "2, null, 2, null"
                + "]",
                wholeNumbers(14)
                    .mapFirst(
                            i -> (Integer)(((i % 2) == 0) ? 2 : null),
                            i -> (Integer)(((i % 3) == 0) ? 3 : null),
                            i -> (Integer)(((i % 5) == 0) ? 5 : null))
                    .toListString());
        assertEquals(
                "["
                + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                + "2, null, 2, null"
                + "]",
                wholeNumbers(14)
                    .mapFirst(
                            i -> (Integer)(((i % 2) == 0) ? 2 : null),
                            i -> (Integer)(((i % 3) == 0) ? 3 : null),
                            i -> (Integer)(((i % 5) == 0) ? 5 : null),
                            i -> (Integer)(((i % 7) == 0) ? 7 : null))
                    .toListString());
        assertEquals(
                "["
                + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                + "2, 11, 2, null"
                + "]",
                wholeNumbers(14)
                    .mapFirst(
                            i -> (Integer)(((i % 2) == 0) ? 2 : null),
                            i -> (Integer)(((i % 3) == 0) ? 3 : null),
                            i -> (Integer)(((i % 5) == 0) ? 5 : null),
                            i -> (Integer)(((i % 7) == 0) ? 7 : null),
                            i -> (Integer)(((i % 11) == 0) ? 11 : null))
                    .toListString());
        assertEquals(
                "["
                + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                + "2, 11, 2, 13"
                + "]",
                wholeNumbers(14)
                    .mapFirst(
                            i -> (Integer)(((i % 2) == 0) ? 2 : null),
                            i -> (Integer)(((i % 3) == 0) ? 3 : null),
                            i -> (Integer)(((i % 5) == 0) ? 5 : null),
                            i -> (Integer)(((i % 7) == 0) ? 7 : null),
                            i -> (Integer)(((i % 11) == 0) ? 11 : null),
                            i -> (Integer)(((i % 13) == 0) ? 13 : null))
                    .toListString());
    }
    
    @Test
    public void testMapThen() {
        assertEquals(
                "["
                + "(0)((0)), "
                + "(1)((1)), "
                + "(2)((2)), "
                + "(3)((3)), "
                + "(4)((4))]",
                wholeNumbers(5)
                    .mapThen(
                            i -> format("(%s)", i),
                            i -> format("((%s))", i),
                            (a,b)->a + b)
                    .toListString());
        assertEquals(
                "["
                + "(0)((0))(((0))), "
                + "(1)((1))(((1))), "
                + "(2)((2))(((2))), "
                + "(3)((3))(((3))), "
                + "(4)((4))(((4)))"
                + "]",
                wholeNumbers(5)
                    .mapThen(
                            i -> format("(%s)", i),
                            i -> format("((%s))", i),
                            i -> format("(((%s)))", i),
                            (a,b,c)->a + b + c)
                    .toListString());
        assertEquals(
                "["
                + "(0)((0))(((0)))((((0)))), "
                + "(1)((1))(((1)))((((1)))), "
                + "(2)((2))(((2)))((((2)))), "
                + "(3)((3))(((3)))((((3)))), "
                + "(4)((4))(((4)))((((4))))"
                + "]",
                wholeNumbers(5)
                    .mapThen(
                            i -> format("(%s)", i),
                            i -> format("((%s))", i),
                            i -> format("(((%s)))", i),
                            i -> format("((((%s))))", i),
                            (a,b,c,d)->a + b + c +d)
                    .toListString());
        assertEquals(
                "["
                + "(0)((0))(((0)))((((0))))(((((0))))), "
                + "(1)((1))(((1)))((((1))))(((((1))))), "
                + "(2)((2))(((2)))((((2))))(((((2))))), "
                + "(3)((3))(((3)))((((3))))(((((3))))), "
                + "(4)((4))(((4)))((((4))))(((((4)))))"
                + "]",
                wholeNumbers(5)
                    .mapThen(
                            i -> format("(%s)", i),
                            i -> format("((%s))", i),
                            i -> format("(((%s)))", i),
                            i -> format("((((%s))))", i),
                            i -> format("(((((%s)))))", i),
                            (a,b,c,d,e)->a + b + c + d + e)
                    .toListString());
        assertEquals(
                "["
                + "(0)((0))(((0)))((((0))))(((((0)))))((((((0)))))), "
                + "(1)((1))(((1)))((((1))))(((((1)))))((((((1)))))), "
                + "(2)((2))(((2)))((((2))))(((((2)))))((((((2)))))), "
                + "(3)((3))(((3)))((((3))))(((((3)))))((((((3)))))), "
                + "(4)((4))(((4)))((((4))))(((((4)))))((((((4))))))"
                + "]",
                wholeNumbers(5)
                    .mapThen(
                            i -> format("(%s)", i),
                            i -> format("((%s))", i),
                            i -> format("(((%s)))", i),
                            i -> format("((((%s))))", i),
                            i -> format("(((((%s)))))", i),
                            i -> format("((((((%s))))))", i),
                            (a,b,c,d,e,f)->a + b + c + d + e + f)
                    .toListString());
    }
    
    @Test
    public void testMapTuple() {
        assertEquals(
                "["
                + "(0,#0), "
                + "(1,#1), "
                + "(2,#2), "
                + "(3,#3), "
                + "(4,#4)"
                + "]",
                wholeNumbers(5)
                    .mapTuple(
                            i -> i,
                            i -> "#" + i)
                    .toListString());
        assertEquals(
                "["
                + "(0,#0,##0), "
                + "(1,#1,##1), "
                + "(2,#2,##2), "
                + "(3,#3,##3), "
                + "(4,#4,##4)"
                + "]",
                wholeNumbers(5)
                    .mapTuple(
                            i -> i,
                            i -> "#" + i,
                            i -> "##" + i)
                    .toListString());
        assertEquals(
                "["
                + "(0,#0,##0,###0), "
                + "(1,#1,##1,###1), "
                + "(2,#2,##2,###2), "
                + "(3,#3,##3,###3), "
                + "(4,#4,##4,###4)"
                + "]",
                wholeNumbers(5)
                    .mapTuple(
                            i -> i,
                            i -> "#" + i,
                            i -> "##" + i,
                            i -> "###" + i)
                    .toListString());
        assertEquals(
                "["
                + "(0,#0,##0,###0,####0), "
                + "(1,#1,##1,###1,####1), "
                + "(2,#2,##2,###2,####2), "
                + "(3,#3,##3,###3,####3), "
                + "(4,#4,##4,###4,####4)"
                + "]",
                wholeNumbers(5)
                    .mapTuple(
                            i -> i,
                            i -> "#" + i,
                            i -> "##" + i,
                            i -> "###" + i,
                            i -> "####" + i)
                    .toListString());
        assertEquals(
                "["
                + "(0,#0,##0,###0,####0,#####0), "
                + "(1,#1,##1,###1,####1,#####1), "
                + "(2,#2,##2,###2,####2,#####2), "
                + "(3,#3,##3,###3,####3,#####3), "
                + "(4,#4,##4,###4,####4,#####4)"
                + "]",
                wholeNumbers(5)
                    .mapTuple(
                            i -> i,
                            i -> "#" + i,
                            i -> "##" + i,
                            i -> "###" + i,
                            i -> "####" + i,
                            i -> "#####" + i)
                    .toListString());
    }
    
    @Test
    public void testMapToMap() {
        assertEquals(
                "["
                + "{1:0}, "
                + "{1:1}, "
                + "{1:2}, "
                + "{1:3}, "
                + "{1:4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0}, "
                + "{1:1, 2:#1}, "
                + "{1:2, 2:#2}, "
                + "{1:3, 2:#3}, "
                + "{1:4, 2:#4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0}, "
                + "{1:1, 2:#1, 3:##1}, "
                + "{1:2, 2:#2, 3:##2}, "
                + "{1:3, 2:#3, 3:##3}, "
                + "{1:4, 2:#4, 3:##4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i,
                            "6", i -> "#####" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i,
                            "6", i -> "#####" + i,
                            "7", i -> "######" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i,
                            "6", i -> "#####" + i,
                            "7", i -> "######" + i,
                            "8", i -> "#######" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i,
                            "6", i -> "#####" + i,
                            "7", i -> "######" + i,
                            "8", i -> "#######" + i,
                            "9", i -> "########" + i)
                    .toListString());
        assertEquals(
                "["
                + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0, 10:#########0}, "
                + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1, 10:#########1}, "
                + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2, 10:#########2}, "
                + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3, 10:#########3}, "
                + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4, 10:#########4}"
                + "]",
                wholeNumbers(5)
                    .mapToMap(
                            "1", i -> i,
                            "2", i -> "#" + i,
                            "3", i -> "##" + i,
                            "4", i -> "###" + i,
                            "5", i -> "####" + i,
                            "6", i -> "#####" + i,
                            "7", i -> "######" + i,
                            "8", i -> "#######" + i,
                            "9", i -> "########" + i,
                            "10", i -> "#########" + i)
                    .toListString());
    }
    
    @Test
    public void testSegment_fixedSize() {
        Function<LongStreamPlus, String> streamToString = s -> s.toListString();
        
        assertEquals("["
                + "[0, 1, 2], "
                + "[3, 4, 5], "
                + "[6, 7, 8], "
                + "[9]"
                + "]",
                wholeNumbers(10)
                .segment(3)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2], "
                + "[3, 4, 5], "
                + "[6, 7, 8]"
                + "]",
                wholeNumbers(10)
                .segment(3, false)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2], "
                + "[3, 4, 5], "
                + "[6, 7, 8], "
                + "[9]"
                + "]",
                wholeNumbers(10)
                .segment(3, true)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2], "
                + "[3, 4, 5], "
                + "[6, 7, 8]"
                + "]",
                wholeNumbers(10)
                .segment(3, IncompletedSegment.excluded)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2], "
                + "[3, 4, 5], "
                + "[6, 7, 8], "
                + "[9]"
                + "]",
                wholeNumbers(10)
                .segment(3, IncompletedSegment.included)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9]"
                + "]",
                wholeNumbers(10)
                .segment(i -> i % 4 == 0)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9]"
                + "]",
                wholeNumbers(10)
                .segment(i -> i % 4 == 0, IncompletedSegment.included)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[0, 1, 2, 3], "
                + "[4, 5, 6, 7]"
                + "]",
                wholeNumbers(10)
                .segment(i -> i % 4 == 0, IncompletedSegment.excluded)
                .map(streamToString)
                .toListString());
    }
    
    @Test
    public void testSegment_conditions() {
        Function<LongStreamPlus, String> streamToString = s -> s.toListString();
        
        LongPredicate startCondition = i ->(i % 10) == 3;
        LongPredicate endCondition   = i ->(i % 10) == 6;
        
        assertEquals("["
                + "[53, 54, 55, 56], " 
                + "[63, 64, 65, 66], "
                + "[73, 74]"
                + "]",
                wholeNumbers(75)
                .segment(startCondition, endCondition)
                .skip(5)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[53, 54, 55, 56], " 
                + "[63, 64, 65, 66], "
                + "[73, 74]"
                + "]",
                wholeNumbers(75)
                .segment(startCondition, endCondition, true)
                .skip(5)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[53, 54, 55, 56], " 
                + "[63, 64, 65, 66]"
                + "]",
                wholeNumbers(75)
                .segment(startCondition, endCondition, false)
                .skip(5)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[53, 54, 55, 56], " 
                + "[63, 64, 65, 66], "
                + "[73, 74]"
                + "]",
                wholeNumbers(75)
                .segment(startCondition, endCondition, IncompletedSegment.included)
                .skip(5)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[53, 54, 55, 56], " 
                + "[63, 64, 65, 66]"
                + "]",
                wholeNumbers(75)
                .segment(startCondition, endCondition, IncompletedSegment.excluded)
                .skip(5)
                .map(streamToString)
                .toListString());
    }
    
    @Test
    public void testSegmentSize() {
        Function<LongStreamPlus, String> streamToString = s -> s.toListString();
        assertEquals("["
                + "[], "
                + "[1], "
                + "[2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9, 10, 11, 12, 13, 14, 15], "
                + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                + "]",
                wholeNumbers(30)
                .segmentSize(i -> (int)i)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[], "
                + "[1], "
                + "[2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9, 10, 11, 12, 13, 14, 15], "
                + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                + "]",
                wholeNumbers(30)
                .segmentSize(i -> (int)i, true)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[], "
                + "[1], "
                + "[2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9, 10, 11, 12, 13, 14, 15]"
                + "]",
                wholeNumbers(30)
                .segmentSize(i -> (int)i, false)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[], "
                + "[1], "
                + "[2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9, 10, 11, 12, 13, 14, 15], "
                + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                + "]",
                wholeNumbers(30)
                .segmentSize(i -> (int)i, IncompletedSegment.included)
                .map(streamToString)
                .toListString());
        
        assertEquals("["
                + "[], "
                + "[1], "
                + "[2, 3], "
                + "[4, 5, 6, 7], "
                + "[8, 9, 10, 11, 12, 13, 14, 15]"
                + "]",
                wholeNumbers(30)
                .segmentSize(i -> (int)i, IncompletedSegment.excluded)
                .map(streamToString)
                .toListString());
    }
    
    @Test
    public void testCollapseWhen() {
        // [0, 1, 2 + 3, 4, 5 + 6, 7, 8 + 9]
        assertEquals("[0, 1, 9, 11, 24]",
                wholeNumbers(10)
                .collapseWhen(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
                .toListString());
    }
    
    @Test
    public void testCollapseAfter() {
        // [0 + 1, 2, 3 + 4 + 5, 6 + 7, 8 + 9]
        assertEquals("[1, 2, 12, 13, 17]",
                wholeNumbers(10)
                .collapseAfter(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
                .toListString());
    }
    
    @Test
    public void testCollapseSize() {
        // [0, 1, 2 + 3, 4 + 5 + 6 + 7]
        assertEquals("[1, 5, 22, 17]",
                wholeNumbers(10)
                .collapseSize(i -> i, (a, b) -> a + b)
                .toListString());
        
        assertEquals("[1, 5, 22, 17]",
                wholeNumbers(10)
                .collapseSize(i -> i, (a, b) -> a + b, true)
                .toListString());
        
        assertEquals("[1, 5, 22]",
                wholeNumbers(10)
                .collapseSize(i -> i, (a, b) -> a + b, false)
                .toListString());
        
        assertEquals("[1, 5, 22, 17]",
                wholeNumbers(10)
                .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.included)
                .toListString());
        
        assertEquals("[1, 5, 22]",
                wholeNumbers(10)
                .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.excluded)
                .toListString());
    }
    
    @Test
    public void testConcatWith() {
        assertEquals("["
                        + "0, 1, 2, 3, 4, "
                        + "21, 22, 23, 24, 25, 26"
                    + "]", 
                        range(0, 5).concatWith(range(21, 27)).toListString());
    }
    
    @Test
    public void testMergeWith() {
        assertEquals("[0, 21, 1, 22, 2, 23, 3, 24, 4, 25, 26]", 
                        range(0, 5).mergeWith(range(21, 27)).toListString());
    }
    
    @Test
    public void testZipWith_boxed() {
        assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25)]", 
                        range(0, 5).zipWith(range(21, 27).boxed()).toListString());
        
        assertEquals("[(0,21), (1,22), (2,23), (3,null), (4,null)]", 
                        range(0, 5).zipWith(range(21, 24).boxed(), AllowUnpaired).toListString());
        
        assertEquals("[(0,21), (1,22), (2,23)]", 
                range(0, 5).zipWith(range(21, 24).boxed(), RequireBoth).toListString());
        
        assertEquals("[21, 23, 25]", 
                        range(0, 5).zipWith(range(21, 24).boxed(), (a, b) -> a + b).toListString());
    }
    
    @Test
    public void testChoose() {
        // 0 % 3 = 0 vs 22 % 2 = 0 => 22
        // 1 % 3 = 1 vs 23 % 2 = 1 => 23
        // 2 % 3 = 2 vs 24 % 2 = 0 =>  2
        // 3 % 3 = 0 vs 25 % 2 = 1 => 25
        // 4 % 3 = 1 vs 26 % 2 = 0 =>  4
        //           vs 27 % 2 = 1 => none
        //           vs 28 % 2 = 0 => none
        //           vs 29 % 2 = 1 => none
        assertEquals("[22, 23, 2, 25, 4]", 
                        range(0, 5).choose(range(22, 30), (a, b) -> a % 3 > b % 2).toListString());
    }
    
    static class Sum implements LongCollectorPlus<AtomicLong, Long> {
        
        @Override
        public Collector<Long, AtomicLong, Long> collector() {
            return this;
        }
        
        @Override
        public Long process(LongStreamPlus stream) {
            return stream.sum();
        }
        
        @Override
        public Supplier<AtomicLong> supplier() {
            return () -> new AtomicLong();
        }
        
        @Override
        public LongAccumulator<AtomicLong> longAccumulator() {
            return (atomicLong, i) -> {
                atomicLong.set(atomicLong.get() + i);
            };
        }
        
        @Override
        public BinaryOperator<AtomicLong> combiner() {
            return (i1, i2) -> new AtomicLong(i1.get() + i2.get());
        }
        
        @Override
        public Function<AtomicLong, Long> finisher() {
            return i -> i.get();
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    static class Max implements LongCollectorPlus<AtomicLong, Long> {
        
        @Override
        public Collector<Long, AtomicLong, Long> collector() {
            return this;
        }
        
        @Override
        public Long process(LongStreamPlus stream) {
            return stream.max().getAsLong();
        }
        
        @Override
        public Supplier<AtomicLong> supplier() {
            return () -> new AtomicLong();
        }
        
        @Override
        public LongAccumulator<AtomicLong> longAccumulator() {
            return (atomicLong, i) -> {
                atomicLong.set(Math.max(atomicLong.get(), i));
            };
        }
        
        @Override
        public BinaryOperator<AtomicLong> combiner() {
            return (i1, i2) -> new AtomicLong(Math.max(i1.get(), i2.get()));
        }
        
        @Override
        public Function<AtomicLong, Long> finisher() {
            return i -> i.get();
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    @Test
    public void testCalculate() {
        val sum = new Sum();
        val max = new Max();
        
        assertEquals("45", 
                     "" + range(0, 10).calculate(sum));
        
        assertEquals("(45,9)", 
                     "" + range(0, 10).calculate(sum, max));
        
        assertEquals("(45,9,45)", 
                     "" + range(0, 10).calculate(sum, max, sum));
        
        assertEquals("(45,9,45,9)", 
                     "" + range(0, 10).calculate(sum, max, sum, max));
        
        assertEquals("(45,9,45,9,45)", 
                     "" + range(0, 10).calculate(sum, max, sum, max, sum));
        
        assertEquals("(45,9,45,9,45,9)", 
                     "" + range(0, 10).calculate(sum, max, sum, max, sum, max));
    }
    
    @Test
    public void testMapOnly() {
        assertEquals("[0, 10, 2, 30, 4, 50, 6, 70, 8, 90]", 
                "" + range(0, 10).mapOnly(theLong.thatIsOdd(), theLong.time(10)).toListString());
    }
    
    @Test
    public void testMapIf() {
        assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
                "" + range(0, 10)
                    .mapIf(
                        theLong.thatIsOdd(), 
                        theLong.time(10), 
                        i -> i/2)
                    .toListString());
    }
    
    @Test
    public void testMapToObjIf() {
        assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
                "" + range(0, 10)
                    .mapToObjIf(
                        theLong.thatIsOdd(), 
                        i -> i * 10, 
                        i -> i / 2)
                    .toListString());
    }
    
    @Test
    public void testMapWithIndex() {
        assertEquals("[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]", 
                "" + range(0, 10)
                    .mapWithIndex(
                        (index, value)->index*value)
                    .toListString());
        
        assertEquals("[(0,0), (1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8), (9,9)]", 
                "" + range(0, 10)
                    .mapWithIndex()
                    .toListString());
    }
    
    @Test
    public void testMapToObjWithIndex() {
        assertEquals("[0-0, 1-1, 2-2, 3-3, 4-4, 5-5, 6-6, 7-7, 8-8, 9-9]", 
                "" + range(0, 10)
                    .mapToObjWithIndex(
                        (index, value)->index + "-" + value)
                    .toListString());
        
        assertEquals("[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
                "" + range(0, 10)
                    .mapToObjWithIndex(
                            i -> "" + i,
                            (index, value) -> index + ": " + value
                    )
                    .toListString());
    }
    
    @Test
    public void testMapWithPrev() {
        assertEquals("["
                + "(OptionalLong.empty,0), "
                + "(OptionalLong[0],1), "
                + "(OptionalLong[1],2), "
                + "(OptionalLong[2],3), "
                + "(OptionalLong[3],4), "
                + "(OptionalLong[4],5), "
                + "(OptionalLong[5],6), "
                + "(OptionalLong[6],7), "
                + "(OptionalLong[7],8), "
                + "(OptionalLong[8],9)"
                + "]", 
                "" + range(0, 10)
                    .mapWithPrev()
                    .toListString());
        
        assertEquals("["
                + "OptionalLong.empty-0, "
                + "OptionalLong[0]-1, "
                + "OptionalLong[1]-2, "
                + "OptionalLong[2]-3, "
                + "OptionalLong[3]-4, "
                + "OptionalLong[4]-5, "
                + "OptionalLong[5]-6, "
                + "OptionalLong[6]-7, "
                + "OptionalLong[7]-8, "
                + "OptionalLong[8]-9"
                + "]", 
                "" + range(0, 10)
                    .mapWithPrev(
                        (prev, i) -> prev + "-" + i
                    )
                    .toListString());
    }
    
    @Test
    public void testFilterIn() {
        assertEquals("[1, 3, 5, 7, 9]", 
                "" + range(0, 10)
                    .filterIn(1, 3, 5, 7, 9, 11, 13)
                    .toListString());
        
        assertEquals("[1, 3, 5, 7, 9]", 
                "" + range(0, 10)
                .filterIn(asList(1L, 3L, 5L, 7L, 9L, 11L, 13L))
                    .toListString());
    }
    
    @Test
    public void testExcludeIn() {
        assertEquals("[5, 6, 7, 8, 9]", 
                "" + range(0, 10)
                    .exclude(i -> i < 5)
                    .toListString());
        
        assertEquals("[0, 2, 4, 6, 8]", 
                "" + range(0, 10)
                .excludeIn(1, 3, 5, 7, 9, 11, 13)
                .toListString());
        
        assertEquals("[0, 2, 4, 6, 8]", 
                "" + range(0, 10)
                .excludeIn(asList(1L, 3L, 5L, 7L, 9L, 11L, 13L))
                    .toListString());
    }
    
    @Test
    public void testFilterWithIndex() {
        assertEquals("[0, 1, 2, 3, 8, 9]", 
                "" + range(0, 10)
                    .filterWithIndex((i, v) -> i < 4 || v > 7)
                    .toListString());
    }
    
    @Test
    public void testPeekMore() {
        {
            val lines = new ArrayList<String>();
            assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                    "" + range(0, 10)
                        .peek(theLong.thatIsEven(), i -> lines.add("" + i))
                        .toListString());
            
            assertEquals(
                    "[0, 2, 4, 6, 8]", 
                    "" + lines);
        }
        
        {
            val lines = new ArrayList<String>();
            assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                    "" + range(0, 10)
                        .peek((long   i) -> "--> " + i + ";", 
                              (String s) -> lines.add(s))
                        .toListString());
            
            assertEquals(
                    "[--> 0;, --> 1;, --> 2;, --> 3;, --> 4;, --> 5;, --> 6;, --> 7;, --> 8;, --> 9;]", 
                    "" + lines);
        }
        
        {
            val lines = new ArrayList<String>();
            assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                    "" + range(0, 10)
                        .peek((long   i) -> "--> " + i + ";", 
                              (String s) -> s.contains("5"),
                              (String s) -> lines.add(s))
                        .toListString());
            
            assertEquals(
                    "[--> 5;]", 
                    "" + lines);
        }
    }
    
    @Test
    public void testFlatMapOnly() {
        //            [0, 1 -> [0], 2, 3->[0, 1, 2], 4, 5 -> [0, 1, 2, 3, 4], 6]
        //            [0,      [0], 2,    [0, 1, 2], 4,      [0, 1, 2, 3, 4], 6]
        //            [0,       0,  2,     0, 1, 2,  4,       0, 1, 2, 3, 4,  6]
        assertEquals("[0, 0, 2, 0, 1, 2, 4, 0, 1, 2, 3, 4, 6]", 
                "" + range(0, 7)
                .flatMapOnly(theLong.thatIsOdd(), i -> range(0, i)).toListString());
    }
    
    @Test
    public void testFlatMapIf() {
        //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
        //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
        //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
        assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
                "" + range(0, 7)
                .flatMapIf(
                        theLong.thatIsOdd(), 
                        i -> range(0, i),
                        i -> LongStreamPlus.of(-i)
                )
                .toListString());
    }
    
    @Test
    public void testFlatMapToObjIf() {
        //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
        //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
        //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
        assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
                "" + range(0, 7)
                .flatMapToObjIf(
                        theLong.thatIsOdd(), 
                        i -> range(0, i)          .boxed(),
                        i -> LongStreamPlus.of(-i).boxed()
                )
                .toListString());
    }
    
    @Test
    public void testForEachWithIndex() {
        val lines = new ArrayList<String>();
        range(0, 10)
        .forEachWithIndex((i, v) -> lines.add(i + ": " + v));
        
        assertEquals(
                "[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
                "" + lines);
    }
    
    @Test
    public void testMinBy() {
        assertEquals(
                "OptionalLong[34]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minBy((long i) -> Math.abs(i - 34))
                .toString());
        
        assertEquals(
                "OptionalLong[1]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minBy((long i) -> Math.abs(i - 34), ((Comparator<Long>)Long::compare).reversed())
                .toString());
    }
    
    @Test
    public void testMaxBy() {
        assertEquals(
                "OptionalLong[1]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .maxBy(i -> Math.abs(i - 34))
                .toString());
        assertEquals(
                "OptionalLong[34]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .maxBy((long i) -> Math.abs(i - 34), ((Comparator<Long>)Long::compare).reversed())
                .toString());
    }
    
    @Test
    public void testMinOf() {
        assertEquals(
                "OptionalLong[34]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minOf(i -> Math.abs(i - 34))
                .toString());
    }
    
    @Test
    public void testMaxOf() {
        assertEquals(
                "OptionalLong[1]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .maxOf(i -> Math.abs(i - 34))
                .toString());
    }
    
    @Test
    public void testMinMax() {
        assertEquals(
                "Optional[(1,34)]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minMax()
                .toString());
    }
    
    @Test
    public void testMinMaxOf() {
        assertEquals(
                "Optional[(34,1)]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minMaxOf(i -> Math.abs(i - 34))
                .toString());
    }
    
    @Test
    public void testMinMaxBy() {
        assertEquals(
                "Optional[(34,1)]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minMaxBy(i -> Math.abs(i - 34))
                .toString());
        
        assertEquals(
                "Optional[(1,34)]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .minMaxBy((long i) -> Math.abs(i - 34), ((Comparator<Long>)Long::compare).reversed())
                .toString());
    }
    
    @Test
    public void testFindFirst() {
        assertEquals(
                "OptionalLong[5]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findFirst(i -> i % 5 == 0)
                .toString());
        assertEquals(
                "OptionalLong[5]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findFirst(i -> i*2, i -> i % 5 == 0)
                .toString());
    }
    
    @Test
    public void testFindAny() {
        assertEquals(
                "OptionalLong[5]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findAny(i -> i % 5 == 0)
                .toString());
        assertEquals(
                "OptionalLong[5]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findAny(i -> i*2, i -> i % 5 == 0)
                .toString());
    }
    
    @Test
    public void testFindFirstBy() {
        assertEquals(
                "OptionalLong[13]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findFirstBy(
                        i -> "" + i, 
                        s -> s.length() > 1)
                .toString());
    }
    
    @Test
    public void testFindAnyBy() {
        assertEquals(
                "OptionalLong[13]", 
                longs(1, 1, 2, 3, 5, 8, 13, 21, 34)
                .findAnyBy(
                        i -> "" + i, 
                        s -> s.length() > 1)
                .toString());
    }
    
}
