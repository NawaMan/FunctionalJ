package functionalj.types;

import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theString;
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
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import functionalj.lens.Access;
import functionalj.types.list.FunctionalList;
import functionalj.types.list.ImmutableList;
import functionalj.types.stream.StreamPlus;
import lombok.val;

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
        stream.forEach(null);
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
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSortedComparatorNull() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[One, Three, Two]", stream.sorted((Comparator)null).toList());
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
    public void testToFunctionalList() {
        val stream = StreamPlus.of("One", "Two", "Three");
        val list   = stream.toFunctionalList();
        assertStrings("[One, Two, Three]", list.toList());
        assertTrue(list instanceof FunctionalList);
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
        assertStrings("OneTwoThree", stream.joining());
    }
    
    @Test
    public void testJoiningDelimiter() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("One, Two, Three", stream.joining(", "));
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
    
    @Test
    public void testMapIf2() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        assertStrings("[ONE, TWO, three, four, five, SIX, seven]", 
                stream
                .mapIf(
                        $S.length().thatLessThan(4), $S.toUpperCase(),
                        $S.length().thatLessThan(5), $S.toLowerCase(),
                        $S.toLowerCase()
                ).toList());
    }
    
    @Test
    public void testMapIf3() {
        val stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven");
        val index  = new AtomicInteger();
        assertStrings("[1:one, 2:TWO, 3:THree, X:four, 1:five, 2:SIX, 3:SEven]", 
                stream
                .mapIf(
                    s -> (index.get() % 4) == 0, s -> { index.incrementAndGet(); return "1:" + s.toLowerCase(); },
                    s -> (index.get() % 4) == 1, s -> { index.incrementAndGet(); return "2:" + s.toUpperCase(); },
                    s -> (index.get() % 4) == 2, s -> { index.incrementAndGet(); return "3:" + s.substring(0, 2).toUpperCase() + s.substring(2).toLowerCase(); },
                    s -> { index.incrementAndGet(); return "X:" + s.toLowerCase(); }
                ).toList());
    }
    
    //-- mapWithIndex --
    
    @Test
    public void testMapWithIndex() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: One, 1: Two, 2: Three]", stream.mapWithIndex((i, s)-> i + ": " + s).toList());
    }
    
    @Test
    public void testMapWithIndex2() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: ONE, 1: TWO, 2: THREE]",
                stream.mapWithIndex(
                        String::toUpperCase,
                        (i, s)-> i + ": " + s
                    ).toList());
    }
    
    @Test
    public void testMapWithIndex3() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: ONE,one, 1: TWO,two, 2: THREE,three]",
                stream.mapWithIndex(
                        String::toUpperCase,
                        String::toLowerCase,
                        (i, s1, s2)-> i + ": " + s1 + "," + s2
                    ).toList());
    }
    
    @Test
    public void testMapWithIndex4() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: One,ONE,one, 1: Two,TWO,two, 2: Three,THREE,three]",
                stream.mapWithIndex(
                        s -> s,
                        String::toUpperCase,
                        String::toLowerCase,
                        (i, s, s1, s2)-> i + ": " + s + "," + s1 + "," + s2
                    ).toList());
    }
    
    @Test
    public void testMapWithIndex5() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: One,ONE,one,On-, 1: Two,TWO,two,Two, 2: Three,THREE,three,Thr--]",
                stream.mapWithIndex(
                        s -> s,
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> s.replaceAll("e", "-"),
                        (i, s, s1, s2, s3)-> i + ": " + s + "," + s1 + "," + s2 + "," + s3
                    ).toList());
    }
    
    @Test
    public void testMapWithIndex6() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[0: One,ONE,one,On-,One, 1: Two,TWO,two,Two,Tw?, 2: Three,THREE,three,Thr--,Three]",
                stream.mapWithIndex(
                        s -> s,
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> s.replaceAll("e", "-"),
                        s -> s.replaceAll("o", "?"),
                        (i, s, s1, s2, s3, s4)-> i + ": " + s + "," + s1 + "," + s2 + "," + s3 + "," + s4
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
    
    @Test
    public void testMapTuple3() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[(ONE,one,(3)One), (TWO,two,(3)Two), (THREE,three,(3)Three)]",
                stream.mapTuple(
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> "(3)" + s
                    ).toList());
    }
    
    @Test
    public void testMapTuple4() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[(ONE,one,(3)One,(4)One), (TWO,two,(3)Two,(4)Two), (THREE,three,(3)Three,(4)Three)]",
                stream.mapTuple(
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> "(3)" + s,
                        s -> "(4)" + s
                    ).toList());
    }
    
    @Test
    public void testMapTuple5() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[(ONE,one,(3)One,(4)One,(5)One), (TWO,two,(3)Two,(4)Two,(5)Two), (THREE,three,(3)Three,(4)Three,(5)Three)]",
                stream.mapTuple(
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> "(3)" + s,
                        s -> "(4)" + s,
                        s -> "(5)" + s
                    ).toList());
    }
    
    @Test
    public void testMapTuple6() {
        val stream = StreamPlus.of("One", "Two", "Three");
        assertStrings("[(ONE,one,(3)One,(4)One,(5)One,(6)One), (TWO,two,(3)Two,(4)Two,(5)Two,(6)Two), (THREE,three,(3)Three,(4)Three,(5)Three,(6)Three)]",
                stream.mapTuple(
                        String::toUpperCase,
                        String::toLowerCase,
                        s -> "(3)" + s,
                        s -> "(4)" + s,
                        s -> "(5)" + s,
                        s -> "(6)" + s
                    ).toList());
    }
    
    //-- Filter --
    
    @Test
    public void testFilterType() {
        val stream = StreamPlus.of("One", "Two", 3, 4.0, 5L);
        assertStrings("[4.0]", stream.filter(Double.class).toList());
    }
    
    @Test
    public void testFilterType2() {
        val stream = StreamPlus.of("One", "Two", 3, 4.0, 5.0, 6L);
        assertStrings("[5.0]", stream.filter(Double.class, d -> d > 4.5).toList());
    }
    
}
