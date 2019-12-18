package functionalj.list.intlist;

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.theInteger;
import static functionalj.list.intlist.IntFuncList.emptyIntList;
import static functionalj.list.intlist.IntFuncList.ints;
import static functionalj.stream.intstream.IntStreamPlus.cycle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.stream.intstream.IntStreamable;
import lombok.val;

public class IntFuncListTest {
    
    private static final IntFuncList intList = IntFuncList.ints(1, 1, 2, 3, 5, 8, 13);
    
    
    @Test
    public void testEmptyList() {
        assertEquals("[]", emptyIntList().toString());
        assertTrue(IntFuncList.empty().isEmpty());
    }
    
    @Test
    public void testBasicList() {
        assertFalse(intList.isEmpty());
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.toString());
        
        // Check the coverage if caching works
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.toString());
        
        assertEquals(-520212190, intList.hashCode());
        
        // Check the coverage if caching works
        assertEquals(-520212190, intList.hashCode());
        
        assertEquals(intList, ints(1, 1, 2, 3, 5, 8, 13));
    }
    
    @Test
    public void testFromArray() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                FuncList
                .from(new int[] {1, 1, 2, 3, 5, 8, 13})
                .toString());
    }
    
    @Test
    public void testFromCollection() {
        assertEquals(
                "[-1, 1, 2, 3, 5, 8, 13]", 
                IntFuncList
                .from(FuncList.of(null, 1, 2, 3, 5, 8, 13), -1)
                .toString());
    }
    
    @Test
    public void testContains() {
        assertTrue(intList.contains(13));
        assertFalse(intList.contains(25));
    }
    
    @Test
    public void testContainAllOf() {
        assertTrue(intList.containsAllOf(13, 5));
        assertFalse(intList.containsAllOf(13, 25));
    }
    
    @Test
    public void testContainSomeOf() {
        assertTrue(intList.containsSomeOf(13, 25));
        assertFalse(intList.containsSomeOf(7, 25));
    }
    
    @Test
    public void testGet() {
        assertEquals(1,  intList.get(0));
        assertEquals(1,  intList.get(1));
        assertEquals(2,  intList.get(2));
        assertEquals(3,  intList.get(3));
        assertEquals(5,  intList.get(4));
        assertEquals(8,  intList.get(5));
        assertEquals(13, intList.get(6));
        
        try { intList.get(7); }
        catch (IndexOutOfBoundsException exception) {
            assertEquals("7", exception.getMessage());
        }
    }
    
    @Test
    public void testAt() {
        assertEquals(OptionalInt.of(1),   intList.at(0));
        assertEquals(OptionalInt.of(1),   intList.at(1));
        assertEquals(OptionalInt.of(2),   intList.at(2));
        assertEquals(OptionalInt.of(3),   intList.at(3));
        assertEquals(OptionalInt.of(5),   intList.at(4));
        assertEquals(OptionalInt.of(8),   intList.at(5));
        assertEquals(OptionalInt.of(13),  intList.at(6));
        assertEquals(OptionalInt.empty(), intList.at(7));
    }
    
    @Test
    public void testIndexOf() {
        assertEquals(3, intList.indexOf(3));
    }
    
    @Test
    public void testLastIndexOf() {
        assertEquals(1, intList.lastIndexOf(1));
    }
    
    @Test
    public void testIndexesOf() {
        assertEquals(ints(0, 1), intList.indexesOf(1));
        assertEquals(ints(3),    intList.indexesOf(3));
        assertEquals(ints(),     intList.indexesOf(23));
    }
    
    @Test
    public void testSub() {
        assertEquals("[2, 3, 5]", intList.subList(2, 5).toString());
    }
    
    @Test
    public void testMap() {
        assertEquals(
                "[2, 2, 4, 6, 10, 16, 26]", 
                intList.map($I.time(2)).toString());
    }
    
    @Test
    public void testFilter() {
        assertEquals(
                "[2, 8]", 
                intList.filter($I.remainderBy(2).eq(0)).toString());
    }
    
    @Test
    public void testPeek() {
        val logs = new ArrayList<String>();
        intList.peek(i -> logs.add("" + i)).forEach(t -> {});
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                logs.toString());
    }
    
    @Test
    public void testFlatMap() {
        assertEquals(
                intList.sum(), 
                intList.flatMap(i -> cycle(1).limit(i))
                .size());
    }
    
    @Test
    public void testLazy() {
        val factor = new AtomicInteger(1);
        
        val list = intList
                .lazy()
                .map(i -> i * factor.get());
        
        // This set change the values.
        factor.set(2);
        assertEquals(
                "[2, 2, 4, 6, 10, 16, 26]", 
                list.toString());
    }
    
    @Test
    public void testEager() {
        val factor = new AtomicInteger(1);
        
        val list = intList
                .eager()
                .map(i -> i * factor.get());
        
        // This set change the values.
        factor.set(2);
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                list.toString());
    }
    
    @Test
    public void testFirst() {
        assertEquals(
                "OptionalInt[1]", 
                intList.first().toString());
    }
    
    @Test
    public void testFirsts() {
        assertEquals(
                "[1, 1, 2]", 
                intList.first(3).toString());
    }
    
    @Test
    public void testSecond() {
        assertEquals(
                "OptionalInt[13]", 
                intList.last().toString());
    }
    
    @Test
    public void testSeconds() {
        assertEquals(
                "[5, 8, 13]", 
                intList.last(3).toString());
    }
    
    @Test
    public void testRest() {
        assertEquals(
                "[1, 2, 3, 5, 8, 13]", 
                intList.rest().toString());
    }
    
    @Test
    public void testReverse() {
        assertEquals(
                "[13, 8, 5, 3, 2, 1, 1]", 
                intList.reverse().toString());
    }
    
    @Test
    public void testShuffle() {
        assertNotEquals(
                intList.toString(), 
                intList.shuffle().toString());
    }
    
    @Test
    public void testMinIndex() {
        assertEquals("OptionalInt[0]", intList.minIndex().toString());
    }
    
    @Test
    public void testMaxIndex() {
        assertEquals("OptionalInt[6]", intList.maxIndex().toString());
    }
    
    @Test
    public void testMinIndexBy() {
        assertEquals("OptionalInt[6]", intList.minIndexBy(i -> -i).toString());
    }
    
    @Test
    public void testMaxIndexBy() {
        assertEquals("OptionalInt[0]", intList.maxIndexBy(i -> -i).toString());
    }
    
    @Test
    public void testAppend() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13, 21]", 
                intList.append(21).toString());
    }
    
    @Test
    public void testAppendAll() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13, 21, 34]", 
                intList.appendAll(21, 34).toString());
    }
    
    @Test
    public void testAppendAll_intStreamable() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.appendAll((IntStreamable)null).toString());
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13, 21, 34]", 
                intList.appendAll(ints(21, 34).streamable()).toString());
    }
    
    @Test
    public void testAppendAll_streamaSupplier() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.appendAll((Supplier<IntStream>)null).toString());
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13, 21, 34]", 
                intList.appendAll(()->(IntStream)ints(21, 34).stream()).toString());
    }
    
    @Test
    public void testPrepend() {
        assertEquals(
                "[0, 1, 1, 2, 3, 5, 8, 13]", 
                intList.prepend(0).toString());
    }
    
    @Test
    public void testPrependAll() {
        assertEquals(
                "[-1, 0, 1, 1, 2, 3, 5, 8, 13]", 
                intList.prependAll(-1, 0).toString());
    }
    
    @Test
    public void testPrependAll_intStreamable() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.prependAll((IntStreamable)null).toString());
        assertEquals(
                "[-1, 0, 1, 1, 2, 3, 5, 8, 13]", 
                intList.prependAll(ints(-1, 0).streamable()).toString());
    }
    
    @Test
    public void testPrependAll_streamSupplier() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.prependAll((Supplier<IntStream>)null).toString());
        assertEquals(
                "[-1, 0, 1, 1, 2, 3, 5, 8, 13]", 
                intList.prependAll(()->(IntStream)ints(-1, 0).stream()).toString());
    }
    
    @Test
    public void testWith() {
        assertEquals(
                "[0, 1, 2, 3, 5, 8, 13]", 
                intList.with(0, 0).toString());
        
        try {
            intList.with(-1, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        
        try {
            intList.with(intList.size(), 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }
    
    @Test
    public void testWith_replace() {
        assertEquals(
                "[0, 1, 2, 3, 5, 8, 13]", 
                intList.with(0, i -> i - 1).toString());
        
        try {
            intList.with(-1, i -> i - 1);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        
        try {
            intList.with(intList.size(), i -> i - 1);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }
    
    @Test
    public void testInsertAt() {
        assertEquals(
                "[1, 1, 2, 3, 4, 5, 6, 7, 8, 13]", 
                intList.insertAt(4, 4).insertAt(6, 6, 7).toString());
    }
    
    @Test
    public void testInsertAllAt() {
        assertEquals(
                "[1, 1, 2, 3, 5, 6, 7, 8, 13]", 
                intList.insertAllAt(5, ints(6, 7).streamable()).toString());
    }
    
    @Test
    public void testExcludeAt() {
        assertEquals(
                "[1, 2, 3, 5, 8, 13]", 
                intList.excludeAt(0).toString());
        
        try {
            intList.excludeAt(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }
    
    @Test
    public void testExcludeFrom() {
        assertEquals(
                "[2, 3, 5, 8, 13]", 
                intList.excludeFrom(0, 2).toString());
        assertEquals(
                "[1, 1, 2, 3, 5, 8]", 
                intList.excludeFrom(6, 2).toString());
    }
    
    @Test
    public void testExcludeBetween() {
        assertEquals(
                "[1, 1, 8, 13]", 
                intList.excludeBetween(2, 4).toString());
    }
    
    @Test
    public void testSkipWhile() {
        assertEquals(
                "[5, 4, 2, 0]", 
                ints(1, 3, 5, 4, 2, 0).skipWhile(i -> i < 4).toString());
    }
    
    @Test
    public void testSkipUntil() {
        assertEquals(
                "[5, 4, 2, 0]", 
                ints(1, 3, 5, 4, 2, 0).skipUntil(i -> i > 4).toString());
    }
    
    @Test
    public void testTakeWhile() {
        assertEquals(
                "[1, 3]", 
                ints(1, 3, 5, 4, 2, 0).takeWhile(i -> i < 4).toString());
    }
    
    @Test
    public void testTakeUntil() {
        assertEquals(
                "[1, 3]", 
                ints(1, 3, 5, 4, 2, 0).takeUntil(i -> i > 4).toString());
    }
    
    @Test
    public void testDistinct() {
        assertEquals(
                "[1, 2, 3, 5, 8, 13]", 
                intList.distinct().toString());
    }
    
    @Test
    public void testSorted() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.shuffle().sorted().toString());
    }
    
    @Test
    public void testLimit() {
        assertEquals(
                "[1, 1, 2, 3, 5]", 
                intList.limit(5L).toString());
        try {
            intList.limit(-1L).toString();
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.limit(100L).toString());
        
        assertEquals(
                "[1, 1, 2, 3, 5]", 
                intList.limit((Long)5L).toString());
        try {
            intList.limit(Long.valueOf(-1L)).toString();
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.limit((Long)100L).toString());
    }
    
    @Test
    public void testSkip() {
        assertEquals(
                "[8, 13]", 
                intList.skip(5L).toString());
        try {
            intList.skip(-1L).toString();
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(
                "[]", 
                intList.skip(100L).toString());
        
        assertEquals(
                "[8, 13]", 
                intList.skip((Long)5L).toString());
        try {
            intList.skip(Long.valueOf(-1L)).toString();
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(
                "[]", 
                intList.skip((Long)100L).toString());
    }
    
    @Test
    public void testSortedBy() {
        assertEquals(
                "[1, 1, 2, 3, 5, 8, 13]", 
                intList.shuffle().sortedBy(theInteger.time(2)).toString());
        
        assertEquals(
                "[13, 8, 5, 3, 2, 1, 1]", 
                intList.shuffle().sortedBy(theInteger.time(2), (a,b)->b-a).toString());
    }
    
    @Test
    public void testSortedByObj() {
        assertEquals(
                "[1, 1, 13, 2, 3, 5, 8]", 
                intList.shuffle().sortedByObj(i -> "" + i).toString());
        
        assertEquals(
                "[8, 5, 3, 2, 13, 1, 1]", 
                intList.shuffle().sortedByObj(i -> "" + i, Comparator.<String>reverseOrder()).toString());
    }
    
    @Test
    public void testForEach() {
        val buffer1 = new StringBuffer();
        intList.forEach(i -> buffer1.append(" ").append(i));
        assertEquals(
                " 1 1 2 3 5 8 13", 
                buffer1.toString());
    }
    
    @Test
    public void testAccumulate() {
        assertEquals(
                "[1, 2, 4, 7, 12, 20, 33]", 
                intList.accumulate((a, i)-> a + i).toString());
    }
    
    @Test
    public void testRestate() {
        assertEquals(
                "[2, 3, 5, 7, 11, 13]", 
                ints(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
                .restate((i, s)-> s.filter(v -> v % i != 0))
                .toString());
    }
    
    @Test
    public void testExclude() {
        assertEquals(
                "[2, 3, 5, 8, 13]", 
                intList.exclude(1).toString());
        assertEquals(
                "[1, 1, 2, 3, 5, 13]", 
                intList.exclude(8).toString());
    }
    
    @Test
    public void testExcludeAll() {
        assertEquals(
                "[2, 3, 8, 13]", 
                intList.excludeAll(1, 5).toString());
    }
    
    @Test
    public void testCollapse() {
        // Explain
        // The original : [1, 1, 2, 3, 5, 8, 13]
        //    #0: 1 <- is the first one, no collapse
        //    #1: 1 <- is odd so collapse
        //    #2: 2 <- is even so no collapse
        //    #3: 3 <- is odd so collapse
        //    #4: 5 <- is odd so collapse
        //    #5: 8 <- is even so no collapse
        //    #6: 13 <- is odd so collapse.
        // Thus:
        //    #0 + #1      = 1 + 1     = 2
        //    #2 + #3 + #4 = 2 + 3 + 5 = 10
        //    #5 + #6      = 8 + 13    = 21
        assertEquals(
                "[2, 10, 21]",
                intList.collapse($I.thatIsOdd(), (a,b)->a+b).toString());
        
        // Explain
        // The original : [1, 1, 2, 3, 5, 8, 13]
        //    #0: 1 <- is the first one, no collapse
        //    #1: 1 <- is less than 6 so collapse
        //    #2: 2 <- is less than 6 so collapse
        //    #3: 3 <- is less than 6 so collapse
        //    #4: 5 <- is less than 6 so collapse
        //    #5: 8 <- is more than 6 so no collapse
        //    #6: 13 <- is more than 6 so no collapse.
        // Thus:
        //    #0 + #1 + #2 + #3 + #4 = 1 + 1 + 2 + 3 + 5 = 12
        //    #5                     = 8                 = 8
        //    #6                     = 13                = 13
        assertEquals(
                "[12, 8, 13]",
                intList.collapse($I.thatLessThan(6), theInteger::sum).toString());
    }
}
