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

import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.map.FuncMap.underlineMap;
import static functionalj.map.FuncMap.UnderlineMap.LinkedHashMap;
import static functionalj.ref.Run.With;
import static functionalj.stream.StreamPlus.noMoreElement;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import functionalj.function.Func0;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.result.NoMoreResultException;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple3;
import lombok.val;

@SuppressWarnings("javadoc")
public class StreamPlusTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    @Test
    public void testOf() {
        val stream1 = StreamPlus.of("One", "Two", "Three");
        val stream2 = StreamPlus.from(stream1);
        assertStrings("[One, Two, Three]", stream2.toList());
    }
    
    @Test
    public void testMap() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[3, 3, 5]", stream.map(s -> s.length()).toList());
    }
    
    @Test
    public void testFlatMap() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[3, 3, 5]", stream.flatMap(s -> Stream.of(s.length())).toList());
    }
    
    @Test
    public void testFilter() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[Three]", stream.filter(s -> s.length() > 4).toList());
    }
    
    @Test
    public void testPeek() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val logs   = new ArrayList<String>();
        assertStrings("[One, Two, Three]", stream.peek(s -> logs.add(s)).toList());
        assertStrings("[One, Two, Three]", logs);
    }
    
    @Test
    public void testPeekNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.peek(null).toList());
    }
    
    @Test
    public void testMapToInt() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("11", stream.mapToInt(String::length).sum());
    }
    
    @Test
    public void testMapToLong() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("11", stream.mapToLong(String::length).sum());
    }
    
    @Test
    public void testMapToDouble() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("11.0", stream.mapToDouble(s -> s.length()*1.0).sum());
    }
    
    @Test
    public void testMapWithPrev() {
        val stream = StreamPlus.of("One", "Two", "Three").mapWithPrev((prev, element) -> prev.orElse("").length() + element.length());
        assertStrings("3, 6, 8", stream.joinToString(", "));
    }
    
    @Test
    public void testForEach() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val logs   = new ArrayList<String>();
        stream.forEach(s -> logs.add(s));
        assertStrings("[One, Two, Three]", logs);
    }
    
    @Test
    public void testForEachNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val logs   = new ArrayList<String>();
        stream.forEach((Consumer<String>)null);
        assertStrings("[]", logs);
    }
    
    @Test
    public void testReduce() {
        val stream1 = StreamPlus.of(1, 2, 3);
        assertEquals(6, stream1.reduce(0, (a, b) -> a + b).intValue());
        
        val stream2 = StreamPlus.of(1, 2, 3);
        assertEquals(6, stream2.reduce((a, b) -> a + b).get().intValue());
    }
    
    @Test
    public void testCollect() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.collect(toList()));
    }
    
    @Test
    public void testMinMax() {
        val stream1 = StreamPlus.of("One", "Two", "Three", "Four");
        assertStrings("Optional[One]",   stream1.min((a, b)-> a.length()-b.length()));
        
        val stream2 = StreamPlus.of("One", "Two", "Three", "Four");
        assertStrings("Optional[Three]", stream2.max((a, b)-> a.length()-b.length()));
    }
    
    @Test
    public void testCount() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("3", stream.count());
    }
    
    @Test
    public void testAnyMatch() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertTrue(stream.anyMatch("One"::equals));
    }
    
    @Test
    public void testAllMatch() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertFalse(stream.allMatch("One"::equals));
    }
    
    @Test
    public void testNoneMatch() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertTrue(stream.noneMatch("Five"::equals));
    }
    
    @Test
    public void testFindFirst() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("Optional[One]", stream.findFirst());
    }
    
    @Test
    public void testFilterNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.filter((Predicate<String>)null).toList());
    }
    
    @Test
    public void testSkipLimit() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[Two]", stream.skip(1).limit(1).toList());
    }
    
    @Test
    public void testDistinct() {
        val stream = StreamPlus.of("One", "Two", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.distinct().toList());
    }
    
    @Test
    public void testSorted() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Three, Two]", stream.sorted().toList());
    }
    
    @Test
    public void testSortedComparator() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four");
        assertStrings("[One, Two, Four, Three]", stream.sorted(comparingInt(String::length)).toList());
    }
    
    @Test
    public void testSortedComparatorNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Three, Two]", stream.sorted((Comparator<String>)null).toList());
    }
    
    @Test
    public void testClosed() {
        val stream = StreamPlus.of("One", "Two", "Three");
        
        val isClosed = new AtomicBoolean(false);
        stream
        .onClose(()-> 
            isClosed.set(true));
        
        assertFalse(isClosed.get());
        assertStrings("[3, 3, 5]", stream.map(theString.length()).toList());
        // TODO - This is still not work.
        //assertTrue(isClosed.get());
        
        try {
            stream.toList();
            fail("Stream should be closed now.");
        } catch (IllegalStateException e) {
            // Expected!!
        }
    }
    
    @Test
    public void testClose() {
        val stream = StreamPlus.of("One", "Two", "Three");
        
        val isClosed = new AtomicBoolean(false);
        stream
        .onClose(()->
            isClosed.set(true));
        
        assertFalse(isClosed.get());
        stream.close();
        assertTrue(isClosed.get());
    }
    
    //== toXXX ===
    
    @Test
    public void testToArray() {
        val stream1 = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", Arrays.toString(stream1.toArray()));
        
        val stream2 = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", Arrays.toString(stream2.toArray(n -> new String[n])));
    }
    
    @Test
    public void testToFuncList() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val list   = stream.toList();
        assertStrings("[One, Two, Three]", list.toList());
        assertTrue(list instanceof FuncList);
    }
    
    @Test
    public void testToImmutableList() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val list   = stream.toImmutableList();
        assertStrings("[One, Two, Three]", list.toList());
        assertTrue(list instanceof ImmutableList);
    }
    
    @Test
    public void testToMutableList() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val list   = stream.toMutableList();
        assertStrings("[One, Two, Three]", list);
        // This is because we use ArrayList as mutable list ... not it should not always be.
        assertTrue(list instanceof ArrayList);
    }
    
    @Test
    public void testToArrayList() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val list   = stream.toArrayList();
        assertStrings("[One, Two, Three]", list);
        assertTrue(list instanceof ArrayList);
    }
    
    @Test
    public void testToSet() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val set    = stream.toSet();
        assertStrings("[One, Two, Three]", set);
        assertTrue(set instanceof Set);
    }
    
    @Test
    public void testToIterator() {
        val stream   = StreamPlus.of("One", "Two", "Three");
        val list     = new ArrayList<String>();
        val iterator = stream.iterator();
        while (iterator.hasNext())
            list.add(iterator.next());
        assertStrings("[One, Two, Three]", list);
    }
    
    @Test
    public void testToSpliterator() {
        val stream      = StreamPlus.of("One", "Two", "Three");
        val spliterator = stream.spliterator();
        assertStrings("[One, Two, Three]", StreamSupport.stream(spliterator, false).collect(toList()));
    }
    
    //== Plus ==
    
    @Test
    public void testJoining() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("OneTwoThree", stream.joinToString());
    }
    
    @Test
    public void testJoiningDelimiter() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("One, Two, Three", stream.joinToString(", "));
    }
    
    public static <DATA> Tuple3<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            StreamPlus<DATA> stream,
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        val temp = stream.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3
        );
    }
    
    @Test
    public void testSplit() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six");
        assertStrings("([One, Two, Six],"
                     + "[Four, Five],"
                     + "[Three])", 
                stream.split(
                        s -> s.length() == 3,
                        s -> s.length() == 4));
    }
    
    @Test
    public void testFizzBuzz() {
        val stream  = StreamPlus.infiniteInt().limit(20);
        val toString = 
                With(underlineMap.butWith(LinkedHashMap))
                .run(()->{
                    val splited 
                            = stream
                            .split(
                                "FizzBuzz", i -> i % (3*5) == 0,
                                "Buzz",     i -> i % 5     == 0,
                                "Fizz",     i -> i % 3     == 0,
                                null);
                    return splited.toString();
                });
        assertEquals("{"
                + "FizzBuzz:[0, 15], "
                + "Buzz:[5, 10], "
                + "Fizz:[3, 6, 9, 12, 18], "
                + "null:[]}", 
                toString);
    }
    
    //== Plus w/ Self ==
    
    @Test
    public void testSkipLimitLong() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[Two]", stream.skip((Long)1L).limit((Long)1L).toList());
    }
    
    @Test
    public void testSkipLimitLongNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.skip(null).limit(null).toList());
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Two, Three]", stream.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)).toList());
    }
    
    @Test
    public void testSkipWhile() {
        assertStrings("[3, 4, 5, 4, 3, 2, 1]",       StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3).toList());
        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i > 3).toList());
    }
    
    @Test
    public void testSkipUntil() {
        assertStrings("[4, 5, 4, 3, 2, 1]",          StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i > 3).toList());
        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i < 3).toList());
    }
    
    @Test
    public void testTakeWhile() {
        val list = new ArrayList<Integer>();
        assertStrings("[1, 2, 3]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i < 4).peek(list::add).toList());
        assertStrings("[1, 2, 3]", list);
        
        list.clear();
        assertStrings("[]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i > 4).peek(list::add).toList());
        assertStrings("[]", list);
    }
    
    @Test
    public void testTakeUtil() {
        val list = new ArrayList<Integer>();
        assertStrings("[1, 2, 3, 4]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i > 4).peek(list::add).toList());
        assertStrings("[1, 2, 3, 4]", list);
        
        list.clear();
        assertStrings("[]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i < 4).peek(list::add).toList());
        assertStrings("[]", list);
    }
    @Test
    public void testSkipTake() {
        val list = new ArrayList<Integer>();
        assertStrings("[3, 4, 5, 4, 3]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3).takeUntil(i -> i < 3).peek(list::add).toList());
        assertStrings("[3, 4, 5, 4, 3]", list);
    }
    
    @Test
    public void testSortedBy() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four");
        assertStrings("[One, Two, Four, Three]", stream.sortedBy(String::length).toList());
    }
    
    @Test
    public void testSortedByComparator() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four");
        assertStrings("[Three, Four, One, Two]", stream.sortedBy(String::length, (a,b)->b-a).toList());
    }
    
    //--map with condition --
    
    @Test
    public void testMapOnly() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[ONE, TWO, Three]", 
                stream
                .mapOnly(
                        $S.length().thatLessThan(4), 
                        $S.toUpperCase()
                ).toList());
    }
    
    @Test
    public void testMapIf() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[ONE, TWO, three]", 
                stream
                .mapIf(
                        $S.length().thatLessThan(4), $S.toUpperCase(),
                        $S.toLowerCase()
                ).toList());
    }
    
    //== Map to tuple. ==
    
    @Test
    public void testMapTuple2() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[(ONE,one), (TWO,two), (THREE,three)]",
                stream.mapTuple(
                        String::toUpperCase,
                        String::toLowerCase
                    ).toList());
    }
    
    //-- Filter --
    
    @Test
    public void testFilterType() {
        StreamPlus<Object>  stream = StreamPlus.of("One", "Two", 3, 4.0, 5L);
        assertStrings("[4.0]", stream.filter(Double.class).toList());
    }
    
    @Test
    public void testFilterType2() {
        StreamPlus<Object> stream = StreamPlus.of("One", "Two", 3, 4.0, 5.0, 6L);
        assertStrings("[5.0]", stream.filter(Double.class, d -> d > 4.5).toList());
    }
    
    //-- Cycle -- 
    
    @Test
    public void testCycle() {
        val stream = StreamPlus.cycle("One", "Two", "Three");
        assertStrings("Two, Three, One, Two, Three", stream.skip(1).limit(5).joinToString(", "));
    }
    
    //-- Generate -- 
    
    @Test
    public void testGenerate() {
        val counter = new AtomicInteger();
        val stream  = StreamPlus.generateBy(()->{
            int count = counter.getAndIncrement();
            if (count < 5)
                return count;
            throw new NoMoreResultException();
        });
        assertStrings("[0, 1, 2, 3, 4]", stream.toListString());
        
        val stream2 = StreamPlus.generateBy(Func0.from(i -> i < 5 ? i : noMoreElement()));
        assertStrings("[0, 1, 2, 3, 4]", stream2.toListString());
    }
    
    //-- Compound --
    
    @Test
    public void testCompound() {
        val stream = StreamPlus.compound(1, a -> a + 1);
        assertStrings("[1, 2, 3, 4, 5]", stream.limit(5).toListString());
    }
    
    //-- Segmentation --
    
    @Test
    public void testSegment() {
        Predicate<Integer> startCondition = i ->(i % 10) == 3;
        Predicate<Integer> endCondition   = i ->(i % 10) == 6;
        
        assertEquals("[[53, 54, 55, 56], " + 
                      "[63, 64, 65, 66], " + 
                      "[73, 74, 75, 76]]",
                StreamPlus.infiniteInt()
                .segment(startCondition, endCondition)
                .skip   (5)
                .limit  (3)
                .map    (StreamPlus::toListString)
                .toListString());
        
        assertEquals("[[53, 54, 55, 56], " + 
                      "[63, 64, 65, 66], " + 
                      "[73, 74, 75, 76]]",
                StreamPlus.infiniteInt()
                .segment(startCondition, endCondition, true)
                .skip   (5)
                .limit  (3)
                .map    (StreamPlus::toListString)
                .toListString());
        
        assertEquals("[[53, 54, 55], " + 
                      "[63, 64, 65], " + 
                      "[73, 74, 75]]",
                StreamPlus.infiniteInt()
                .segment(startCondition, endCondition, false)
                .skip   (5)
                .limit  (3)
                .map    (StreamPlus::toListString)
                .toListString());
        
        assertEquals("[[53, 54, 55, 56, 57, 58, 59, 60, 61, 62], " + 
                      "[63, 64, 65, 66, 67, 68, 69, 70, 71, 72], " + 
                      "[73, 74, 75, 76, 77, 78, 79, 80, 81, 82], " + 
                      "[83, 84, 85]]",
                IntStreamPlus.infinite().asStream()
                .skip(50)
                .limit(36)
                .segment(startCondition)
                .map    (StreamPlus::toListString)
                .toListString());
    }
    @Test
    public void testSegment2() {
        assertEquals("[A, B], [C]",  StreamPlus.of("A", "B", "C").segment(2       ).map(s -> s.toList().toString()).joinToString(", "));
        assertEquals("[A, B]",       StreamPlus.of("A", "B", "C").segment(2, false).map(s -> s.toList().toString()).joinToString(", "));
    }
    
    @Test
    public void testZipWith() {
        {
            val streamA = StreamPlus.of("A", "B", "C");
            val streamB = IntStreamPlus.infinite().asStream();
            assertEquals("(A,0), (B,1), (C,2)", streamA.zipWith(streamB).joinToString(", "));
        }
        {
            val streamA = StreamPlus.of("A", "B", "C");
            val streamB = IntStreamPlus.infinite().asStream();
            assertEquals("(A,0), (B,1), (C,2)", streamA.zipWith(streamB, RequireBoth).joinToString(", "));
        }
        {
            val streamA = StreamPlus.of("A", "B", "C");
            val streamB = IntStreamPlus.infinite().asStream();
            assertEquals("(A,0), (B,1), (C,2), (null,3), (null,4)", streamA.zipWith(streamB, AllowUnpaired).limit(5).joinToString(", "));
        }
    }
    
    @Test
    public void testChoose() {
        val streamA = StreamPlus.of("A", "B", "C");
        val streamB = IntStreamPlus.infinite().asStream().map(theInteger.asString());
        val bool    = new AtomicBoolean(true);
        assertEquals("A, 1, C, 3, 4", streamA.choose(streamB, (a, b) -> {
            boolean curValue = bool.get();
            return bool.getAndSet(!curValue);
        }).limit(5).joinToString(", "));
    }
    
    @Test
    public void testMerge() {
        val streamA = StreamPlus.of("A", "B", "C");
        val streamB = IntStreamPlus.infinite().asStream().map(theInteger.asString());
        assertEquals("A, 0, B, 1, C, 2, 3, 4, 5, 6", streamA.merge(streamB).limit(10).joinToString(", "));
    }
    
}
