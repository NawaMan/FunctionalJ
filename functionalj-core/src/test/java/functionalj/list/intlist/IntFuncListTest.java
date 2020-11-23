package functionalj.list.intlist;

//import static functionalj.lens.Access.$I;
//import static functionalj.lens.Access.theInteger;
//import static functionalj.list.intlist.IntFuncList.emptyIntList;
//import static functionalj.list.intlist.IntFuncList.emptyList;
//import static functionalj.list.intlist.IntFuncList.ints;
//import static functionalj.list.intlist.IntFuncList.range;
//import static functionalj.list.intlist.IntFuncList.wholeNumbers;
//import static java.lang.String.format;
//import static java.util.Arrays.asList;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.EnumSet;
//import java.util.OptionalInt;
//import java.util.Set;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.function.IntFunction;
//import java.util.function.IntPredicate;
//import java.util.function.Supplier;
//import java.util.stream.Collector;
//import java.util.stream.IntStream;
//
//import org.junit.Test;
//
//import functionalj.function.FuncUnit1;
//import functionalj.promise.DeferAction;
//import functionalj.stream.IncompletedSegment;
//import functionalj.stream.IntAccumulator;
//import functionalj.stream.IntCollectorPlus;
//import functionalj.stream.Streamable;
//import functionalj.stream.intstream.IntStreamPlus;
//import functionalj.stream.intstream.IntStreamable;
//

public class IntFuncListTest {
//    
//    private static final IntFuncList intList = IntFuncList.ints(1, 1, 2, 3, 5, 8, 13);
//    
//    private void run(IntFuncList list, FuncUnit1<IntFuncList> action) {
//        action.accept(list);
//        action.accept(list);
//    }
//    
//    @Test
//    public void testEmptyList() {
//        run(emptyList(), list -> assertEquals("[]", list.toString()));
//        run(emptyIntList(), list -> assertEquals("[]", list.toString()));
//        run(IntFuncList.empty(), list -> assertEquals("[]", list.toString()));
//    }
//    
//    @Test
//    public void test_of() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.of(1, 1, 2, 3, 5, 8, 13).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.AllOf(1, 1, 2, 3, 5, 8, 13).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.ListOf(1, 1, 2, 3, 5, 8, 13).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.listOf(1, 1, 2, 3, 5, 8, 13).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.intList(1, 1, 2, 3, 5, 8, 13).toString());
//    }
//    
//    @Test
//    public void test_from() {
//        // From Array
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.from(new int[] { 1, 1, 2, 3, 5, 8, 13 }).toString());
//        // From Colletion
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.from(asList(1, 1, 2, 3, 5, 8, 13), 0).toString());
//        // From Streamable
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.from(IntStreamable.of(1, 1, 2, 3, 5, 8, 13)).toString());
//        // From StreamPlus
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", IntFuncList.from(IntStreamPlus.of(1, 1, 2, 3, 5, 8, 13)).toString());
//    }
//    
//    @Test
//    public void testBasicList() {
//        assertTrue(emptyList().isEmpty());
//        assertFalse(intList.isEmpty());
//        
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.toString());
//        
//        // Check the coverage if caching works
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.toString());
//        
//        assertEquals(-520212190, intList.hashCode());
//        
//        // Check the coverage if caching works
//        assertEquals(-520212190, intList.hashCode());
//        
//        assertEquals(intList, ints(1, 1, 2, 3, 5, 8, 13));
//    }
//    
//    @Test
//    public void testPipeable() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.pipeable().pipeTo(Object::toString));
//    }
//    
//    @Test
//    public void testNumbers() {
//        assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", IntFuncList.zeroes(10).toString());
//        assertEquals("[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]", IntFuncList.ones(10).toString());
//        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", IntFuncList.naturalNumbers(10).toString());
//        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", IntFuncList.wholeNumbers(10).toString());
//        assertEquals("[7, 8, 9, 10, 11, 12, 13]", IntFuncList.range(7, 14).toString());
//    }
//    
//    @Test
//    public void testContains() {
//        assertTrue(intList.contains(13));
//        assertFalse(intList.contains(25));
//    }
//    
//    @Test
//    public void testContainAllOf() {
//        assertTrue(intList.containsAllOf(13, 5));
//        assertFalse(intList.containsAllOf(13, 25));
//        assertTrue(intList.containsAllOf(Arrays.asList(13, 5)));
//        assertFalse(intList.containsAllOf(Arrays.asList(13, 25)));
//    }
//    
//    @Test
//    public void testContainSomeOf() {
//        assertTrue(intList.containsSomeOf(13, 25));
//        assertFalse(intList.containsSomeOf(7, 25));
//        assertTrue(intList.containsSomeOf(Arrays.asList(13, 25)));
//        assertFalse(intList.containsSomeOf(Arrays.asList(7, 25)));
//    }
//    
//    @Test
//    public void testGet() {
//        assertEquals(1, intList.get(0));
//        assertEquals(1, intList.get(1));
//        assertEquals(2, intList.get(2));
//        assertEquals(3, intList.get(3));
//        assertEquals(5, intList.get(4));
//        assertEquals(8, intList.get(5));
//        assertEquals(13, intList.get(6));
//        
//        try {
//            intList.get(7);
//        } catch (IndexOutOfBoundsException exception) {
//            assertEquals("7", exception.getMessage());
//        }
//    }
//    
//    @Test
//    public void testAt() {
//        assertEquals(OptionalInt.of(1), intList.at(0));
//        assertEquals(OptionalInt.of(1), intList.at(1));
//        assertEquals(OptionalInt.of(2), intList.at(2));
//        assertEquals(OptionalInt.of(3), intList.at(3));
//        assertEquals(OptionalInt.of(5), intList.at(4));
//        assertEquals(OptionalInt.of(8), intList.at(5));
//        assertEquals(OptionalInt.of(13), intList.at(6));
//        assertEquals(OptionalInt.empty(), intList.at(7));
//    }
//    
//    @Test
//    public void testIndexOf() {
//        assertEquals(3, intList.indexOf(3));
//    }
//    
//    @Test
//    public void testLastIndexOf() {
//        assertEquals(1, intList.lastIndexOf(1));
//    }
//    
//    @Test
//    public void testIndexesOf() {
//        assertEquals(ints(0, 1), intList.indexesOf(1));
//        assertEquals(ints(3), intList.indexesOf(3));
//        assertEquals(ints(), intList.indexesOf(23));
//    }
//    
//    @Test
//    public void testSub() {
//        assertEquals("[2, 3, 5]", intList.subList(2, 5).toString());
//    }
//    
//    @Test
//    public void testMap() {
//        assertEquals(
//                "[2, 2, 4, 6, 10, 16, 26]", 
//                intList.map($I.time(2)).toString());
//        assertEquals(
//                "[2, 2, 4, 6, 10, 16, 26]", 
//                intList.mapToInt($I.time(2)).toString());
//    }
//    
//    @Test
//    public void testFilter() {
//        assertEquals("[2, 8]", intList.filter($I.remainderBy(2).eq(0)).toString());
//    }
//    
//    @Test
//    public void testPeek() {
//        var logs = new ArrayList<String>();
//        intList.peek(i -> logs.add("" + i)).forEach(t -> {
//        });
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", logs.toString());
//    }
//    
//    @Test
//    public void testFlatMap() {
//        assertEquals(
//                intList.sum(), 
//                intList.flatMap(i -> IntStreamable.cycle(1).limit(i)).size());
//    }
//    
//    @Test
//    public void testMapToObj() {
//        assertEquals(
//                "[2, 2, 4, 6, 10, 16, 26]", 
//                intList.mapToObj(i -> "" + i*2).toString());
//    }
//    
//    @Test
//    public void testLazy() {
//        var factor = new AtomicInteger(1);
//        
//        var list = intList.lazy().map(i -> i * factor.get());
//        
//        // This set change the values.
//        factor.set(2);
//        assertEquals("[2, 2, 4, 6, 10, 16, 26]", list.toString());
//    }
//    
//    @Test
//    public void testEager() {
//        var factor = new AtomicInteger(1);
//        
//        var list = intList.eager().map(i -> i * factor.get());
//        
//        // This set change the values.
//        factor.set(2);
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", list.toString());
//    }
//    
//    @Test
//    public void testFirst() {
//        assertEquals("OptionalInt[1]", intList.first().toString());
//    }
//    
//    @Test
//    public void testFirsts() {
//        assertEquals("[1, 1, 2]", intList.first(3).toString());
//    }
//    
//    @Test
//    public void testLast() {
//        assertEquals("OptionalInt[13]", intList.last().toString());
//    }
//    
//    @Test
//    public void testLasts() {
//        assertEquals("[5, 8, 13]", intList.last(3).toString());
//    }
//    
//    @Test
//    public void testRest() {
//        assertEquals("[1, 2, 3, 5, 8, 13]", intList.rest().toString());
//    }
//    
//    @Test
//    public void testReverse() {
//        assertEquals("[13, 8, 5, 3, 2, 1, 1]", intList.reverse().toString());
//    }
//    
//    @Test
//    public void testShuffle() {
//        assertNotEquals(intList.toString(), intList.shuffle().toString());
//    }
//    
//    @Test
//    public void testMinIndexOf() {
//        assertEquals("OptionalInt[0]", intList.minIndexOf(i -> i < 5, i -> -i).toString());
//    }
//    
//    @Test
//    public void testMaxIndexOf() {
//        assertEquals("OptionalInt[5]", intList.maxIndexOf(i -> i > 5, i -> -i).toString());
//    }
//    
//    @Test
//    public void testMinIndex() {
//        assertEquals("OptionalInt[0]", intList.minIndex().toString());
//    }
//    
//    @Test
//    public void testMaxIndex() {
//        assertEquals("OptionalInt[6]", intList.maxIndex().toString());
//    }
//    
//    @Test
//    public void testMinIndexBy() {
//        assertEquals("OptionalInt[6]", intList.minIndexBy(i -> -i).toString());
//    }
//    
//    @Test
//    public void testMaxIndexBy() {
//        assertEquals("OptionalInt[0]", intList.maxIndexBy(i -> -i).toString());
//    }
//    
//    @Test
//    public void testAppend() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13, 21]", intList.append(21).toString());
//    }
//    
//    @Test
//    public void testAppendAll() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13, 21, 34]", intList.appendAll(21, 34).toString());
//    }
//    
//    @Test
//    public void testAppendAll_intStreamable() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.appendAll((IntStreamable) null).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13, 21, 34]", intList.appendAll(ints(21, 34).intStreamable()).toString());
//    }
//    
//    @Test
//    public void testAppendAll_streamaSupplier() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.appendAll((Supplier<IntStream>) null).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8, 13, 21, 34]",
//                intList.appendAll(() -> (IntStream) ints(21, 34).intStream()).toString());
//    }
//    
//    @Test
//    public void testPrepend() {
//        assertEquals("[0, 1, 1, 2, 3, 5, 8, 13]", intList.prepend(0).toString());
//    }
//    
//    @Test
//    public void testPrependAll() {
//        assertEquals("[-1, 0, 1, 1, 2, 3, 5, 8, 13]", intList.prependAll(-1, 0).toString());
//    }
//    
//    @Test
//    public void testPrependAll_intStreamable() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.prependAll((IntStreamable) null).toString());
//        assertEquals("[-1, 0, 1, 1, 2, 3, 5, 8, 13]", intList.prependAll(ints(-1, 0).intStreamable()).toString());
//    }
//    
//    @Test
//    public void testPrependAll_streamSupplier() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.prependAll((Supplier<IntStream>) null).toString());
//        assertEquals("[-1, 0, 1, 1, 2, 3, 5, 8, 13]",
//                intList.prependAll(() -> (IntStream) ints(-1, 0).intStream()).toString());
//    }
//    
//    @Test
//    public void testWith() {
//        assertEquals("[0, 1, 2, 3, 5, 8, 13]", intList.with(0, 0).toString());
//        
//        try {
//            intList.with(-1, 0);
//            fail();
//        } catch (IndexOutOfBoundsException e) {
//        }
//        
//        try {
//            intList.with(intList.size(), 0);
//            fail();
//        } catch (IndexOutOfBoundsException e) {
//        }
//    }
//    
//    @Test
//    public void testWith_replace() {
//        assertEquals("[0, 1, 2, 3, 5, 8, 13]", intList.with(0, i -> i - 1).toString());
//        
//        try {
//            intList.with(-1, i -> i - 1);
//            fail();
//        } catch (IndexOutOfBoundsException e) {
//        }
//        
//        try {
//            intList.with(intList.size(), i -> i - 1);
//            fail();
//        } catch (IndexOutOfBoundsException e) {
//        }
//    }
//    
//    @Test
//    public void testInsertAt() {
//        assertEquals("[1, 1, 2, 3, 4, 5, 6, 7, 8, 13]", intList.insertAt(4, 4).insertAt(6, 6, 7).toString());
//    }
//    
//    @Test
//    public void testInsertAllAt() {
//        assertEquals("[1, 1, 2, 3, 5, 6, 7, 8, 13]", intList.insertAllAt(5, ints(6, 7).intStreamable()).toString());
//    }
//    
//    @Test
//    public void testExcludeAt() {
//        assertEquals("[1, 2, 3, 5, 8, 13]", intList.excludeAt(0).toString());
//        
//        try {
//            intList.excludeAt(-1);
//            fail();
//        } catch (IndexOutOfBoundsException e) {
//        }
//    }
//    
//    @Test
//    public void testExcludeFrom() {
//        assertEquals("[2, 3, 5, 8, 13]", intList.excludeFrom(0, 2).toString());
//        assertEquals("[1, 1, 2, 3, 5, 8]", intList.excludeFrom(6, 2).toString());
//    }
//    
//    @Test
//    public void testExcludeBetween() {
//        assertEquals("[1, 1, 8, 13]", intList.excludeBetween(2, 4).toString());
//    }
//    
//    @Test
//    public void testSkipWhile() {
//        assertEquals("[5, 4, 2, 0]", ints(1, 3, 5, 4, 2, 0).skipWhile(i -> i < 4).toString());
//    }
//    
//    @Test
//    public void testSkipUntil() {
//        assertEquals("[5, 4, 2, 0]", ints(1, 3, 5, 4, 2, 0).skipUntil(i -> i > 4).toString());
//    }
//    
//    @Test
//    public void testTakeWhile() {
//        assertEquals("[1, 3]", ints(1, 3, 5, 4, 2, 0).takeWhile(i -> i < 4).toString());
//    }
//    
//    @Test
//    public void testTakeUntil() {
//        assertEquals("[1, 3]", ints(1, 3, 5, 4, 2, 0).takeUntil(i -> i > 4).toString());
//    }
//    
//    @Test
//    public void testDistinct() {
//        assertEquals("[1, 2, 3, 5, 8, 13]", intList.distinct().toString());
//    }
//    
//    @Test
//    public void testSorted() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.shuffle().sorted().toString());
//    }
//    
//    @Test
//    public void testLimit() {
//        assertEquals("[1, 1, 2, 3, 5]", intList.limit(5L).toString());
//        try {
//            intList.limit(-1L).toString();
//            fail();
//        } catch (IllegalArgumentException e) {
//        }
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.limit(100L).toString());
//        
//        assertEquals("[1, 1, 2, 3, 5]", intList.limit((Long) 5L).toString());
//        
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.limit((Long) 100L).toString());
//    }
//    
//    @Test
//    public void testSkip() {
//        assertEquals("[8, 13]", intList.skip(5L).toString());
//        try {
//            intList.skip(-1L).toString();
//            fail();
//        } catch (IllegalArgumentException e) {
//        }
//        assertEquals("[]", intList.skip(100L).toString());
//        
//        assertEquals("[8, 13]", intList.skip((Long) 5L).toString());
//        
//        assertEquals("[]", intList.skip((Long) 100L).toString());
//    }
//    
//    @Test
//    public void testSortedBy() {
//        assertEquals("[1, 1, 2, 3, 5, 8, 13]", intList.shuffle().sortedBy(theInteger.time(2)).toString());
//        
//        assertEquals("[13, 8, 5, 3, 2, 1, 1]",
//                intList.shuffle().sortedBy(theInteger.time(2), (a, b) -> b - a).toString());
//    }
//    
//    @Test
//    public void testSortedByObj() {
//        assertEquals("[1, 1, 13, 2, 3, 5, 8]", intList.shuffle().sortedByObj(i -> "" + i).toString());
//        
//        assertEquals("[8, 5, 3, 2, 13, 1, 1]",
//                intList.shuffle().sortedByObj(i -> "" + i, Comparator.<String>reverseOrder()).toString());
//    }
//    
//    @Test
//    public void testForEach() {
//        var buffer1 = new StringBuffer();
//        intList.forEach(i -> buffer1.append(" ").append(i));
//        assertEquals(" 1 1 2 3 5 8 13", buffer1.toString());
//    }
//    
//    @Test
//    public void testAccumulate() {
//        assertEquals("[1, 2, 4, 7, 12, 20, 33]", intList.accumulate((a, i) -> a + i).toString());
//    }
//    
//    @Test
//    public void testRestate() {
//        assertEquals("[2, 3, 5, 7, 11, 13]", ints(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
//                .restate((i, s) -> s.filter(v -> v % i != 0)).toString());
//    }
//    
//    @Test
//    public void testExclude() {
//        assertEquals("[2, 3, 5, 8, 13]", intList.exclude(1).toString());
//        assertEquals("[1, 1, 2, 3, 5, 13]", intList.exclude(8).toString());
//    }
//    
//    @Test
//    public void testExcludeAll() {
//        assertEquals("[2, 3, 8, 13]", intList.excludeAll(1, 5).toString());
//    }
//    
//    @Test
//    public void testSpawn() {
//        assertEquals("[" + "Result:{ Value: 13 }, " + "Result:{ Value: 8 }, " + "Result:{ Value: 5 }" + "]",
//                intList.spawn(i -> DeferAction.from(() -> {
//                    Thread.sleep(100 * Math.abs(i - 15));
//                    return i;
//                })).limit(3).toString());
//    }
//    
//    @Test
//    public void testMapFirst() {
//        run(wholeNumbers(14), list->{
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, null, 2, null, 2, 3, "
//                    + "2, null, 2, null"
//                    + "]",
//                    list
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null))
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, 5, 2, null, 2, 3, "
//                    + "2, null, 2, null"
//                    + "]",
//                    list
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
//                                i -> (Integer)(((i % 5) == 0) ? 5 : null))
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
//                    + "2, null, 2, null"
//                    + "]",
//                    list
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
//                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
//                                i -> (Integer)(((i % 7) == 0) ? 7 : null))
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
//                    + "2, 11, 2, null"
//                    + "]",
//                    list
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
//                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
//                                i -> (Integer)(((i % 7) == 0) ? 7 : null),
//                                i -> (Integer)(((i % 11) == 0) ? 11 : null))
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
//                    + "2, 11, 2, 13"
//                    + "]",
//                    list
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
//                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
//                                i -> (Integer)(((i % 7) == 0) ? 7 : null),
//                                i -> (Integer)(((i % 11) == 0) ? 11 : null),
//                                i -> (Integer)(((i % 13) == 0) ? 13 : null))
//                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapThen() {
//        run(wholeNumbers(5), list->{
//            assertEquals(
//                    "["
//                    + "(0)((0)), "
//                    + "(1)((1)), "
//                    + "(2)((2)), "
//                    + "(3)((3)), "
//                    + "(4)((4))]",
//                    list
//                        .mapThen(
//                                i -> format("(%s)", i),
//                                i -> format("((%s))", i),
//                                (a,b)->a + b)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0)((0))(((0))), "
//                    + "(1)((1))(((1))), "
//                    + "(2)((2))(((2))), "
//                    + "(3)((3))(((3))), "
//                    + "(4)((4))(((4)))"
//                    + "]",
//                    list
//                        .mapThen(
//                                i -> format("(%s)", i),
//                                i -> format("((%s))", i),
//                                i -> format("(((%s)))", i),
//                                (a,b,c)->a + b + c)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0)((0))(((0)))((((0)))), "
//                    + "(1)((1))(((1)))((((1)))), "
//                    + "(2)((2))(((2)))((((2)))), "
//                    + "(3)((3))(((3)))((((3)))), "
//                    + "(4)((4))(((4)))((((4))))"
//                    + "]",
//                    list
//                        .mapThen(
//                                i -> format("(%s)", i),
//                                i -> format("((%s))", i),
//                                i -> format("(((%s)))", i),
//                                i -> format("((((%s))))", i),
//                                (a,b,c,d)->a + b + c +d)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0)((0))(((0)))((((0))))(((((0))))), "
//                    + "(1)((1))(((1)))((((1))))(((((1))))), "
//                    + "(2)((2))(((2)))((((2))))(((((2))))), "
//                    + "(3)((3))(((3)))((((3))))(((((3))))), "
//                    + "(4)((4))(((4)))((((4))))(((((4)))))"
//                    + "]",
//                    list
//                        .mapThen(
//                                i -> format("(%s)", i),
//                                i -> format("((%s))", i),
//                                i -> format("(((%s)))", i),
//                                i -> format("((((%s))))", i),
//                                i -> format("(((((%s)))))", i),
//                                (a,b,c,d,e)->a + b + c + d + e)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0)((0))(((0)))((((0))))(((((0)))))((((((0)))))), "
//                    + "(1)((1))(((1)))((((1))))(((((1)))))((((((1)))))), "
//                    + "(2)((2))(((2)))((((2))))(((((2)))))((((((2)))))), "
//                    + "(3)((3))(((3)))((((3))))(((((3)))))((((((3)))))), "
//                    + "(4)((4))(((4)))((((4))))(((((4)))))((((((4))))))"
//                    + "]",
//                    list
//                        .mapThen(
//                                i -> format("(%s)", i),
//                                i -> format("((%s))", i),
//                                i -> format("(((%s)))", i),
//                                i -> format("((((%s))))", i),
//                                i -> format("(((((%s)))))", i),
//                                i -> format("((((((%s))))))", i),
//                                (a,b,c,d,e,f)->a + b + c + d + e + f)
//                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapTuple() {
//        run(wholeNumbers(5), list->{
//            assertEquals(
//                    "["
//                    + "(0,#0), "
//                    + "(1,#1), "
//                    + "(2,#2), "
//                    + "(3,#3), "
//                    + "(4,#4)"
//                    + "]",
//                    list
//                        .mapTuple(
//                                i -> i,
//                                i -> "#" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0,#0,##0), "
//                    + "(1,#1,##1), "
//                    + "(2,#2,##2), "
//                    + "(3,#3,##3), "
//                    + "(4,#4,##4)"
//                    + "]",
//                    list
//                        .mapTuple(
//                                i -> i,
//                                i -> "#" + i,
//                                i -> "##" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0,#0,##0,###0), "
//                    + "(1,#1,##1,###1), "
//                    + "(2,#2,##2,###2), "
//                    + "(3,#3,##3,###3), "
//                    + "(4,#4,##4,###4)"
//                    + "]",
//                    list
//                        .mapTuple(
//                                i -> i,
//                                i -> "#" + i,
//                                i -> "##" + i,
//                                i -> "###" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0,#0,##0,###0,####0), "
//                    + "(1,#1,##1,###1,####1), "
//                    + "(2,#2,##2,###2,####2), "
//                    + "(3,#3,##3,###3,####3), "
//                    + "(4,#4,##4,###4,####4)"
//                    + "]",
//                    list
//                        .mapTuple(
//                                i -> i,
//                                i -> "#" + i,
//                                i -> "##" + i,
//                                i -> "###" + i,
//                                i -> "####" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "(0,#0,##0,###0,####0,#####0), "
//                    + "(1,#1,##1,###1,####1,#####1), "
//                    + "(2,#2,##2,###2,####2,#####2), "
//                    + "(3,#3,##3,###3,####3,#####3), "
//                    + "(4,#4,##4,###4,####4,#####4)"
//                    + "]",
//                    list
//                        .mapTuple(
//                                i -> i,
//                                i -> "#" + i,
//                                i -> "##" + i,
//                                i -> "###" + i,
//                                i -> "####" + i,
//                                i -> "#####" + i)
//                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapToMap() {
//        run(wholeNumbers(5), list->{
//            assertEquals(
//                    "["
//                    + "{1:0}, "
//                    + "{1:1}, "
//                    + "{1:2}, "
//                    + "{1:3}, "
//                    + "{1:4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0}, "
//                    + "{1:1, 2:#1}, "
//                    + "{1:2, 2:#2}, "
//                    + "{1:3, 2:#3}, "
//                    + "{1:4, 2:#4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0}, "
//                    + "{1:1, 2:#1, 3:##1}, "
//                    + "{1:2, 2:#2, 3:##2}, "
//                    + "{1:3, 2:#3, 3:##3}, "
//                    + "{1:4, 2:#4, 3:##4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i,
//                                "6", i -> "#####" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i,
//                                "6", i -> "#####" + i,
//                                "7", i -> "######" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i,
//                                "6", i -> "#####" + i,
//                                "7", i -> "######" + i,
//                                "8", i -> "#######" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i,
//                                "6", i -> "#####" + i,
//                                "7", i -> "######" + i,
//                                "8", i -> "#######" + i,
//                                "9", i -> "########" + i)
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0, 10:#########0}, "
//                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1, 10:#########1}, "
//                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2, 10:#########2}, "
//                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3, 10:#########3}, "
//                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4, 10:#########4}"
//                    + "]",
//                    list
//                        .mapToMap(
//                                "1", i -> i,
//                                "2", i -> "#" + i,
//                                "3", i -> "##" + i,
//                                "4", i -> "###" + i,
//                                "5", i -> "####" + i,
//                                "6", i -> "#####" + i,
//                                "7", i -> "######" + i,
//                                "8", i -> "#######" + i,
//                                "9", i -> "########" + i,
//                                "10", i -> "#########" + i)
//                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testSegment_fixedSize() {
//        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
//        
//        run(wholeNumbers(10), list->{
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8], "
//                    + "[9]"
//                    + "]",
//                    list
//                    .segment(3)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8]"
//                    + "]",
//                    list
//                    .segment(3, false)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8], "
//                    + "[9]"
//                    + "]",
//                    list
//                    .segment(3, true)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8]"
//                    + "]",
//                    list
//                    .segment(3, IncompletedSegment.excluded)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8], "
//                    + "[9]"
//                    + "]",
//                    list
//                    .segment(3, IncompletedSegment.included)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    list
//                    .segment(i -> i % 4 == 0)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    list
//                    .segment(i -> i % 4 == 0, IncompletedSegment.included)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    list
//                    .segment(i -> i % 4 == 0, true)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7]"
//                    + "]",
//                    list
//                    .segment(i -> i % 4 == 0, IncompletedSegment.excluded)
//                    .map(streamToString)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testSegment_conditions() {
//        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
//        
//        IntPredicate startCondition = i ->(i % 10) == 3;
//        IntPredicate endCondition   = i ->(i % 10) == 6;
//        
//        run(wholeNumbers(75), list->{
//            assertEquals("["
//                    + "[53, 54, 55, 56], " 
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    list
//                    .segment(startCondition, endCondition)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[53, 54, 55, 56], " 
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    list
//                    .segment(startCondition, endCondition, true)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[53, 54, 55, 56], " 
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segment(startCondition, endCondition, false)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[53, 54, 55, 56], " 
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    list
//                    .segment(startCondition, endCondition, IncompletedSegment.included)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[53, 54, 55, 56], " 
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segment(startCondition, endCondition, IncompletedSegment.excluded)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testSegmentSize() {
//        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
//        
//        run(wholeNumbers(30), list->{
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
//                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
//                    + "]",
//                    list
//                    .segmentSize(i -> i)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
//                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
//                    + "]",
//                    list
//                    .segmentSize(i -> i, true)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15]"
//                    + "]",
//                    list
//                    .segmentSize(i -> i, false)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
//                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
//                    + "]",
//                    list
//                    .segmentSize(i -> i, IncompletedSegment.included)
//                    .map(streamToString)
//                    .toListString());
//            
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15]"
//                    + "]",
//                    list
//                    .segmentSize(i -> i, IncompletedSegment.excluded)
//                    .map(streamToString)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testCollapseWhen() {
//        run(wholeNumbers(10), list->{
//            // [0, 1, 2 + 3, 4, 5 + 6, 7, 8 + 9]
//            assertEquals("[0, 1, 9, 11, 24]",
//                    list
//                    .collapseWhen(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testCollapseAfter() {
//        run(wholeNumbers(10), list->{
//            // [0 + 1, 2, 3 + 4 + 5, 6 + 7, 8 + 9]
//            assertEquals("[1, 2, 12, 13, 17]",
//                    list
//                    .collapseAfter(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testCollapseSize() {
//        run(wholeNumbers(10), list->{
//            // [0, 1, 2 + 3, 4 + 5 + 6 + 7]
//            assertEquals("[1, 5, 22, 17]",
//                    list
//                    .collapseSize(i -> i, (a, b) -> a + b)
//                    .toListString());
//            
//            assertEquals("[1, 5, 22, 17]",
//                    list
//                    .collapseSize(i -> i, (a, b) -> a + b, true)
//                    .toListString());
//            
//            assertEquals("[1, 5, 22]",
//                    list
//                    .collapseSize(i -> i, (a, b) -> a + b, false)
//                    .toListString());
//            
//            assertEquals("[1, 5, 22, 17]",
//                    list
//                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.included)
//                    .toListString());
//            
//            assertEquals("[1, 5, 22]",
//                    list
//                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.excluded)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testConcatWith() {
//        var anotherList = range(21, 27);
//        run(range(0, 5), list->{
//            assertEquals("["
//                            + "0, 1, 2, 3, 4, "
//                            + "21, 22, 23, 24, 25, 26"
//                        + "]", 
//                        list
//                        .concatWith(anotherList)
//                        .toListString());
//            // TODO - uncomment this.
////            assertEquals("["
////                        + "0, 1, 2, 3, 4, "
////                        + "21, 22, 23, 24, 25, 26"
////                        + "]", 
////                        list
////                        .concatWith(21, 22, 23, 24, 25, 26)
////                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testMergeWith() {
//        var anotherList = range(21, 27);
//        run(range(0, 5), list->{
//            assertEquals("[0, 21, 1, 22, 2, 23, 3, 24, 4, 25, 26]", 
//                        list
//                        .mergeWith(anotherList)
//                        .toListString());
//            // TODO - uncomment this.
////            assertEquals("[0, 21, 1, 22, 2, 23, 3, 24, 4, 25, 26]", 
////                        list
////                        .mergeWith(21, 22, 23, 24, 25, 26)
////                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testZipWith_boxed() {
//        var anotherList1 = range(21, 27);
//        var anotherList2 = range(21, 24);
//        run(range(0, 5), list->{
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25)]", 
//                    list.zipWith(anotherList1.boxed()).toListString());
//            
//            assertEquals("[(0,21), (1,22), (2,23), (3,null), (4,null)]", 
//                    list.zipWith(-1, anotherList2.boxed()).toListString());
//            
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25), (-1,26)]", 
//                    list.zipWith(-1, anotherList1.boxed()).toListString());
//            
//            assertEquals("[(0,21), (1,22), (2,23)]", 
//                    list.zipWith(anotherList2.boxed()).toListString());
//            
//            // TODO - Uncomment this.
////            assertEquals("[0-21, 1-22, 2-23, 3-null, 4-null]", 
////                    list.zipWith(-1, anotherList2.boxed(), (a, b)-> a + "-" + b).toListString());
////            
////            assertEquals("[0-21, 1-22, 2-23, 3-24, 4-25, -1-26]", 
////                    list.zipWith(-1, anotherList1.boxed(), (a, b)-> a + "-" + b).toListString());
//            
//            assertEquals("[0-21, 1-22, 2-23]", 
//                    list.zipWith(anotherList2.boxed(), (a, b)-> a + "-" + b).toListString());
//            
//            assertEquals("[21, 23, 25]", 
//                    list.zipWith(anotherList2.boxed(), (a, b) -> a + b).toListString());
//            
//            assertEquals("[0-21, 1-22, 2-23, 3--1, 4--1]", 
//                    list.zipToObjWith(anotherList2, -1, (a, b)-> a + "-" + b).toListString());
//            
//            assertEquals("[0-21, 1-22, 2-23, 3-1, 4-1]", 
//                    list.zipToObjWith(anotherList2, -1, 1, (a, b)-> a + "-" + b).toListString());
//            
//            assertEquals("[21, 23, 25]", 
//                    list.zipToObjWith(anotherList2, (a, b) -> a + b).toListString());
//        });
//    }
//    
//    @Test
//    public void testZipWith() {
//        var anotherList1 = range(21, 27);
//        var anotherList2 = range(21, 24);
//        run(range(0, 5), list->{
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25)]", 
//                    list.zipWith(anotherList1).toListString());
//            assertEquals("[(0,21), (1,22), (2,23)]", 
//                    list.zipWith(anotherList2).toListString());
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25), (-1,26)]", 
//                    list.zipWith(anotherList1, -1).toListString());
//            assertEquals("[(0,21), (1,22), (2,23), (3,-1), (4,-1)]", 
//                    list.zipWith(anotherList2, -1).toListString());
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25), (-1,26)]", 
//                    list.zipWith(anotherList1, -1, 1).toListString());
//            assertEquals("[(0,21), (1,22), (2,23), (3,1), (4,1)]", 
//                    list.zipWith(anotherList2, -1, 1).toListString());
//            assertEquals("[21, 23, 25, 27, 29, 25]", 
//                    list.zipWith(anotherList1, -1, (a, b) -> a+b).toListString());
//            assertEquals("[21, 23, 25, 2, 3]", 
//                    list.zipWith(anotherList2, -1, (a, b) -> a+b).toListString());
//            assertEquals("[21, 23, 25, 27, 29, 25]", 
//                    list.zipWith(anotherList1, -1, 1, (a, b) -> a+b).toListString());
//            assertEquals("[21, 23, 25, 4, 5]", 
//                    list.zipWith(anotherList2, -1, 1, (a, b) -> a+b).toListString());
//        });
//    }
//    
//    @Test
//    public void testChoose() {
//        var anotherList = range(22, 30);
//        run(range(0, 5), list->{
//            // 0 % 3 = 0 vs 22 % 2 = 0 => 22
//            // 1 % 3 = 1 vs 23 % 2 = 1 => 23
//            // 2 % 3 = 2 vs 24 % 2 = 0 =>  2
//            // 3 % 3 = 0 vs 25 % 2 = 1 => 25
//            // 4 % 3 = 1 vs 26 % 2 = 0 =>  4
//            //           vs 27 % 2 = 1 => none
//            //           vs 28 % 2 = 0 => none
//            //           vs 29 % 2 = 1 => none
//            assertEquals("[22, 23, 2, 25, 4]", 
//                        list
//                         .choose(anotherList, (a, b) -> a % 3 > b % 2)
//                         .toListString());
//        });
//    }
//    
//    static class Sum implements IntCollectorPlus<AtomicInteger, Integer> {
//        
//        @Override
//        public Collector<Integer, AtomicInteger, Integer> collector() {
//            return this;
//        }
//        
//        @Override
//        public Integer process(IntStreamPlus stream) {
//            return stream.sum();
//        }
//        
//        @Override
//        public Supplier<AtomicInteger> supplier() {
//            return () -> new AtomicInteger();
//        }
//        
//        @Override
//        public IntAccumulator<AtomicInteger> intAccumulator() {
//            return (atomicInt, i) -> {
//                atomicInt.set(atomicInt.get() + i);
//            };
//        }
//        
//        @Override
//        public BinaryOperator<AtomicInteger> combiner() {
//            return (i1, i2) -> new AtomicInteger(i1.get() + i2.get());
//        }
//        
//        @Override
//        public Function<AtomicInteger, Integer> finisher() {
//            return i -> i.get();
//        }
//        
//        @Override
//        public Set<Characteristics> characteristics() {
//            return EnumSet.noneOf(Characteristics.class);
//        }
//        
//    }
//    
//    static class Max implements IntCollectorPlus<AtomicInteger, Integer> {
//        
//        @Override
//        public Collector<Integer, AtomicInteger, Integer> collector() {
//            return this;
//        }
//        
//        @Override
//        public Integer process(IntStreamPlus stream) {
//            return stream.max().getAsInt();
//        }
//        
//        @Override
//        public Supplier<AtomicInteger> supplier() {
//            return () -> new AtomicInteger();
//        }
//        
//        @Override
//        public IntAccumulator<AtomicInteger> intAccumulator() {
//            return (atomicInt, i) -> {
//                atomicInt.set(Math.max(atomicInt.get(), i));
//            };
//        }
//        
//        @Override
//        public BinaryOperator<AtomicInteger> combiner() {
//            return (i1, i2) -> new AtomicInteger(Math.max(i1.get(), i2.get()));
//        }
//        
//        @Override
//        public Function<AtomicInteger, Integer> finisher() {
//            return i -> i.get();
//        }
//        
//        @Override
//        public Set<Characteristics> characteristics() {
//            return EnumSet.noneOf(Characteristics.class);
//        }
//        
//    }
//    
//    @Test
//    public void testCalculate() {
//        var sum = new Sum();
//        var max = new Max();
//        run(range(0, 10), list->{
//            assertEquals("45", 
//                         "" + list.calculate(sum));
//            
//            assertEquals("(45,9)", 
//                         "" + list.calculate(sum, max));
//            
//            assertEquals("(45,9,45)", 
//                         "" + list.calculate(sum, max, sum));
//            
//            assertEquals("(45,9,45,9)", 
//                         "" + list.calculate(sum, max, sum, max));
//            
//            assertEquals("(45,9,45,9,45)", 
//                         "" + list.calculate(sum, max, sum, max, sum));
//            
//            assertEquals("(45,9,45,9,45,9)", 
//                         "" + list.calculate(sum, max, sum, max, sum, max));
//        });
//    }
//    
//    @Test
//    public void testMapOnly() {
//        run(range(0, 10), list->{
//            assertEquals("[0, 10, 2, 30, 4, 50, 6, 70, 8, 90]", 
//                    list
//                    .mapOnly(theInteger.thatIsOdd(), theInteger.time(10))
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapIf() {
//        run(range(0, 10), list->{
//            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
//                    list
//                    .mapIf(
//                        theInteger.thatIsOdd(), 
//                        theInteger.time(10), 
//                        i -> i/2)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapToObjIf() {
//        run(range(0, 10), list->{
//            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
//                    list
//                    .mapToObjIf(
//                        theInteger.thatIsOdd(), 
//                        i -> i * 10, 
//                        i -> i / 2)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapWithIndex() {
//        run(range(0, 10), list->{
//            assertEquals("[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]", 
//                    list
//                    .mapWithIndex(
//                        (index, value)->index*value)
//                    .toListString());
//            
//            assertEquals("[(0,0), (1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8), (9,9)]", 
//                    list
//                    .mapWithIndex()
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapToObjWithIndex() {
//        run(range(0, 10), list->{
//            assertEquals("[0-0, 1-1, 2-2, 3-3, 4-4, 5-5, 6-6, 7-7, 8-8, 9-9]", 
//                    list
//                    .mapToObjWithIndex(
//                        (index, value)->index + "-" + value)
//                    .toListString());
//            
//            assertEquals("[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
//                    list
//                    .mapToObjWithIndex(
//                            i -> "" + i,
//                            (index, value) -> index + ": " + value
//                    )
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testMapWithPrev() {
//        run(range(0, 10), list->{
//            assertEquals("["
//                    + "(OptionalInt.empty,0), "
//                    + "(OptionalInt[0],1), "
//                    + "(OptionalInt[1],2), "
//                    + "(OptionalInt[2],3), "
//                    + "(OptionalInt[3],4), "
//                    + "(OptionalInt[4],5), "
//                    + "(OptionalInt[5],6), "
//                    + "(OptionalInt[6],7), "
//                    + "(OptionalInt[7],8), "
//                    + "(OptionalInt[8],9)"
//                    + "]", 
//                    list
//                    .mapWithPrev()
//                    .toListString());
//            
//            assertEquals("["
//                    + "OptionalInt.empty-0, "
//                    + "OptionalInt[0]-1, "
//                    + "OptionalInt[1]-2, "
//                    + "OptionalInt[2]-3, "
//                    + "OptionalInt[3]-4, "
//                    + "OptionalInt[4]-5, "
//                    + "OptionalInt[5]-6, "
//                    + "OptionalInt[6]-7, "
//                    + "OptionalInt[7]-8, "
//                    + "OptionalInt[8]-9"
//                    + "]", 
//                    list
//                    .mapWithPrev(
//                        (prev, i) -> prev + "-" + i
//                    )
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testFilterIn() {
//        run(range(0, 10), list->{
//            assertEquals("[1, 3, 5, 7, 9]", 
//                    list
//                    .filterIn(1, 3, 5, 7, 9, 11, 13)
//                    .toListString());
//            
//            assertEquals("[1, 3, 5, 7, 9]", 
//                    list
//                    .filterIn(asList(1, 3, 5, 7, 9, 11, 13))
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testExcludeIn() {
//        run(range(0, 10), list->{
//            assertEquals("[5, 6, 7, 8, 9]", 
//                    list
//                    .exclude(i -> i < 5)
//                    .toListString());
//            
//            assertEquals("[0, 2, 4, 6, 8]", 
//                    list
//                    .excludeIn(1, 3, 5, 7, 9, 11, 13)
//                    .toListString());
//            
//            assertEquals("[0, 2, 4, 6, 8]", 
//                    list
//                    .excludeIn(asList(1, 3, 5, 7, 9, 11, 13))
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testFilterWithIndex() {
//        run(range(0, 10), list->{
//            assertEquals("[0, 1, 2, 3, 8, 9]", 
//                    list
//                    .filterWithIndex((i, v) -> i < 4 || v > 7)
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testPeekMore() {
//        run(range(0, 10), list->{
//            {
//                var lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
//                        list
//                        .peek(theInteger.thatIsEven(), i -> lines.add("" + i))
//                        .toListString());
//                
//                assertEquals(
//                        "[0, 2, 4, 6, 8]", 
//                        "" + lines);
//            }
//            
//            {
//                var lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
//                        list
//                        .peek((int    i) -> "--> " + i + ";", 
//                              (String s) -> lines.add(s))
//                        .toListString());
//                
//                assertEquals(
//                        "[--> 0;, --> 1;, --> 2;, --> 3;, --> 4;, --> 5;, --> 6;, --> 7;, --> 8;, --> 9;]", 
//                        "" + lines);
//            }
//            
//            {
//                var lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
//                        list
//                        .peek((int    i) -> "--> " + i + ";", 
//                              (String s) -> s.contains("5"),
//                              (String s) -> lines.add(s))
//                        .toListString());
//                
//                assertEquals(
//                        "[--> 5;]", 
//                        "" + lines);
//            }
//        });
//    }
//    
//    @Test
//    public void testFlatMapOnly() {
//        run(range(0, 7), list->{
//            //            [0, 1 -> [0], 2, 3->[0, 1, 2], 4, 5 -> [0, 1, 2, 3, 4], 6]
//            //            [0,      [0], 2,    [0, 1, 2], 4,      [0, 1, 2, 3, 4], 6]
//            //            [0,       0,  2,     0, 1, 2,  4,       0, 1, 2, 3, 4,  6]
//            assertEquals("[0, 0, 2, 0, 1, 2, 4, 0, 1, 2, 3, 4, 6]", 
//                    list
//                    .flatMapOnly(theInteger.thatIsOdd(), i -> range(0, i)).toListString());
//        });
//    }
//    
//    @Test
//    public void testFlatMapIf() {
//        run(range(0, 7), list->{
//            //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
//            //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
//            //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
//            assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
//                    list
//                    .flatMapIf(
//                            theInteger.thatIsOdd(), 
//                            i -> range(0, i),
//                            i -> IntStreamable.of(-i)
//                    )
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testFlatMapToObjIf() {
//        run(range(0, 7), list->{
//            //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
//            //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
//            //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
//            assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
//                    list
//                    .flatMapToObjIf(
//                            theInteger.thatIsOdd(), 
//                            i -> range(0, i)         .boxed().streamable(),
//                            i -> IntStreamable.of(-i).boxed()
//                    )
//                    .toListString());
//        });
//    }
//    
//    @Test
//    public void testForEachWithIndex() {
//        run(range(0, 10), list->{
//            var lines = new ArrayList<String>();
//            list
//            .forEachWithIndex((i, v) -> lines.add(i + ": " + v));
//            
//            assertEquals(
//                    "[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
//                    "" + lines);
//        });
//    }
//    
//    @Test
//    public void testMinBy() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[34]", 
//                    list
//                    .minBy(i -> Math.abs(i - 34))
//                    .toString());
//            
//            assertEquals(
//                    "OptionalInt[1]", 
//                    list
//                    .minBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMaxBy() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[1]", 
//                    list
//                    .maxBy(i -> Math.abs(i - 34))
//                    .toString());
//            assertEquals(
//                    "OptionalInt[34]", 
//                    list
//                    .maxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMinOf() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[34]", 
//                    list
//                    .minOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMaxOf() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[1]", 
//                    list
//                    .maxOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMinMax() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "Optional[(1,34)]", 
//                    list
//                    .minMax()
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMinMaxOf() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "Optional[(34,1)]", 
//                    list
//                    .minMaxOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testMinMaxBy() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "Optional[(34,1)]", 
//                    list
//                    .minMaxBy(i -> Math.abs(i - 34))
//                    .toString());
//            
//            assertEquals(
//                    "Optional[(1,34)]", 
//                    list
//                    .minMaxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testFindFirst() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[5]", 
//                    list
//                    .findFirst(i -> i % 5 == 0)
//                    .toString());
//            assertEquals(
//                    "OptionalInt[5]", 
//                    list
//                    .findFirst(i -> i*2, i -> i % 5 == 0)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testFindAny() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[5]", 
//                    list
//                    .findAny(i -> i % 5 == 0)
//                    .toString());
//            assertEquals(
//                    "OptionalInt[5]", 
//                    list
//                    .findAny(i -> i*2, i -> i % 5 == 0)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testFindFirstBy() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[13]", 
//                    list
//                    .findFirstBy(
//                            i -> "" + i, 
//                            s -> s.length() > 1)
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testFindAnyBy() {
//        run(ints(1, 1, 2, 3, 5, 8, 13, 21, 34), list->{
//            assertEquals(
//                    "OptionalInt[13]", 
//                    list
//                    .findAnyBy(
//                            i -> "" + i, 
//                            s -> s.length() > 1)
//                    .toString());
//        });
//    }
//    
}
