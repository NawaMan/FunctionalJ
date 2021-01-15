// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.intlist;

import static functionalj.lens.Access.theInteger;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.map.FuncMap;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class IntFuncListTest {

    static final int MinusOne = -1;
    static final int Zero     =  0;
    
    static final int One         = 1;
    static final int Two         = 2;
    static final int Three       = 3;
    static final int Four        = 4;
    static final int Five        = 5;
    static final int Six         = 6;
    static final int Seven       = 7;
    static final int Eight       = 8;
    static final int Nine        = 9;
    static final int Ten         = 10;
    static final int Eleven      = 11;
    static final int Twelve      = 12;
    static final int Thirteen    = 13;
    static final int Seventeen   = 17;
    static final int Nineteen    = 19;
    static final int TwentyThree = 23;
    
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    private void run(IntFuncList list, FuncUnit1<IntFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    
    private <T> void run(FuncList<T> list, FuncUnit1<FuncList<T>> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private <T> void run(DoubleFuncList list, FuncUnit1<DoubleFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(IntFuncList list1, IntFuncList list2, FuncUnit2<IntFuncList, IntFuncList> action) {
        action.accept(list1, list2);
        action.accept(list1, list2);
    }
    
    @Test
    public void testEmpty() {
        run(IntFuncList.empty(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList() {
        run(IntFuncList.emptyList(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testEmptyIntList() {
        run(IntFuncList.emptyIntList(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testAllOf() {
        run(IntFuncList.AllOf(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testInts() {
        run(IntFuncList.ints(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testIntList() {
        run(IntFuncList.intList(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testListOf() {
        run(IntFuncList.ListOf(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.listOf(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_array() {
        run(IntFuncList.from(new int[] {1, 2, 3}), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<Integer> collection = Arrays.asList(One, Two, Three);
        run(IntFuncList.from(collection, 0), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        Set<Integer> set = new LinkedHashSet<>(collection);
        run(IntFuncList.from(set, 0), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_list() {
        List<Integer> javaList = Arrays.asList(One, Two, Three);
        run(IntFuncList.from(javaList, 0), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_FuncList() {
        run(IntFuncList.from(IntFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.from(IntFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Ignore("Taking too long - run manually as needed.")
    @Test
    public void testFrom_FuncList_infinite() {
        run(IntFuncList.from(IntFuncList.loop()), list -> {
            try {
                list.toList();
                fail("Expect an exception.");
            } catch (OutOfMemoryError e) {
            }
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(IntFuncList.from(IntFuncList.of(One, Two, Three).intStream()), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testZeroesOnesNumbers() {
        run(IntFuncList.zeroes(5),                 list -> assertStrings("[0, 0, 0, 0, 0]", list));
        run(IntFuncList.ones(5),                   list -> assertStrings("[1, 1, 1, 1, 1]", list));
        run(IntFuncList.naturalNumbers(5),         list -> assertStrings("[1, 2, 3, 4, 5]", list));
        run(IntFuncList.wholeNumbers(5),           list -> assertStrings("[0, 1, 2, 3, 4]", list));
        run(IntFuncList.range(2, 7),               list -> assertStrings("[2, 3, 4, 5, 6]", list));
    }
    
    //-- Common List --
    
    @Test
    public void testEquals() {
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three),
            (list1, list2) -> {
            assertTrue  (Objects.equals(list1, list2));
            assertEquals(list1, list2);
        });
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three, Four),
                (list1, list2) -> {
                assertFalse    (Objects.equals(list1, list2));
                assertNotEquals(list1, list2);
            });
    }
    
    @Test
    public void testHashCode() {
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three),
            (list1, list2) -> {
            assertEquals(Objects.hash(list1), Objects.hash(list2));
        });
    }
    
    @Test
    public void testToString() {
        run(IntFuncList.of(One, Two, Three), (list) -> 
        assertEquals("[1, 2, 3]", list.toString()));
    }
    
    @Test
    public void testConcat() {
        run(IntFuncList.concat(IntFuncList.of(One, Two), IntFuncList.of(Three, Four)),
            list -> {
                assertStrings("[1, 2, 3, 4]", list);
            }
        );
    }
    
    @Test
    public void testCombine() {
        run(IntFuncList.combine(IntFuncList.of(One, Two), IntFuncList.of(Three, Four)),
            list -> {
                assertStrings("[1, 2, 3, 4]", list);
            }
        );
    }
    
    //-- Generate --
    
    @Test
    public void testGenerate() {
        run(IntFuncList.generateWith(() -> {
                val counter = new AtomicInteger();
                IntSupplier supplier = ()->{
                    int count = counter.getAndIncrement();
                    if (count < 5)
                        return count;
                    
                    return IntFuncList.noMoreElement();
                };
                return supplier;
            }),
            list -> {
                assertStrings("[0, 1, 2, 3, 4]", list);
            }
        );
    }
    
    //-- zipOf --
    
    @Test
    public void testZipOf_toTuple() {
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[(100,1), (200,2), (300,3), (400,4)]",
                        IntFuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_process() {
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[101, 202, 303, 404]",
                        IntFuncList.zipOf(list1, list2, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testZipOf_toTuple_withDefault_1() {
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.of(1,   2,   3,   4),
            (list1, list2) -> {
                assertStrings(
                        "[(100,1), (200,2), (300,3), (400,4), (500,-2000)]",
                        IntFuncList.zipOf(list1, -1000, list2, -2000));
        });
    }
    
    @Test
    public void testZipOf_toTuple_withDefault_2() {
        run(IntFuncList.of(100, 200, 300, 400),
            IntFuncList.of(1,   2,   3,   4, 5),
            (list1, list2) -> {
                assertStrings(
                        "[(100,1), (200,2), (300,3), (400,4), (-1000,5)]",
                        IntFuncList.zipOf(list1, -1000, list2, -2000));
        });
    }
    
    @Test
    public void testZipOf_process_withDefault_1() {
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.of(1,   2,   3,   4),
            (list1, list2) -> {
                assertStrings(
                        "[101, 202, 303, 404, -1500]",
                        IntFuncList.zipOf(list1, -1000, list2, -2000, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testZipOf_process_withDefault_2() {
        run(IntFuncList.of(100, 200, 300, 400),
            IntFuncList.of(1,   2,   3,   4,  5),
            (list1, list2) -> {
                assertStrings(
                        "[101, 202, 303, 404, -995]",
                        IntFuncList.zipOf(list1, -1000, list2, -2000, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testNew() {
        run(IntFuncList.newFuncList().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.newIntFuncList().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.newList().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.newIntList().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.newBuilder().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.preparing().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(IntFuncList.preparingInts().add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testDeriveFrom() {
        run(IntFuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.mapToInt(v -> v * 2)), list -> {
            assertStrings("[2, 4, 6]", list);
        });
        run(IntFuncList.deriveFrom(IntFuncList.of(1, 2, 3), s -> s.mapToInt(v -> v * 2)), list -> {
            assertStrings("[2, 4, 6]", list);
        });
        run(IntFuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToInt(v -> (int)Math.round(v*1.5))), list -> {
            assertStrings("[2, 3, 5]", list);
        });
    }
    
    @Test
    public void testDeriveTo() {
        run(IntFuncList.deriveToObj(IntFuncList.of(One, Two, Three), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertTrue   (list instanceof FuncList);
            assertStrings("[-1-, -2-, -3-]", list);
        });
        run(IntFuncList.deriveToInt(IntFuncList.of(One, Two, Three), s -> s.mapToInt(v -> v + 5)), list -> {
            assertStrings("[6, 7, 8]", list);
        });
        run(IntFuncList.deriveToDouble(IntFuncList.of(1, 2, 3), s -> s.mapToDouble(v -> 3.0*v)), list -> {
            assertStrings("[3.0, 6.0, 9.0]", list);
        });
    }
    
    //-- Predicate --
    
    @Test
    public void testTest_predicate() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.test(One));
            assertTrue (list.test(Two));
            assertTrue (list.test(Three));
            assertFalse(list.test(Four));
            assertFalse(list.test(Five));
            assertFalse(list.test(Six));
        });
    }
    
    //-- Eager+Lazy --
    
    @Test
    public void testEagerLazy() {
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is lazy
            val list = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(v -> logs.add("" + v)).toFuncList();
            // The function has not been materialized so nothing goes through peek.
            assertStrings("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertStrings("[1, 2, 3, 4, 5]", list.limit(5));
            assertStrings("[1, 2, 3, 4, 5]", logs);
        }
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is eager
            val list = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(v -> logs.add("" + v)).toFuncList().eager();
            // The function has been materialized so all element goes through peek.
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs);
            // Even we only get part of it, 
            assertStrings("[1, 2, 3, 4, 5]", list.limit(5));
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs);
        }
    }
    
    @Test
    public void testEagerLazy_more() {
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val logs3 = new ArrayList<String>();
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .lazy()
                    .peek   (v -> logs1.add("" + v))
                    .map    (v -> (int)Math.round(v * 1.5))
                    .peek   (v -> logs2.add("" + v))
                    .exclude(theInteger.thatIsDivisibleBy(2))
                    .peek   (v -> logs3.add("" + v))
                    ;
            // The list has not been materialized so nothing goes through peek.
            assertStrings("[]", logs1);
            assertStrings("[]", logs2);
            assertStrings("[]", logs3);
            
            // Get part of them so those accepted will goes through the peek
            assertStrings("[3, 5, 9]", list.limit(3));
            
            // Now that the list has been materialize all the element has been through the logs
            
            // The first log has all the original number until there are 3 elements that after time 1.5 and is divisible by 2.
            assertStrings("[1, 2, 3, 4, 5, 6]", logs1);
            //                 1     2     3

            // The first log has all the original number time 1.5 (round) until there are 3 elements that is divisible by 2.
            assertStrings("[2, 3, 5, 6, 8, 9]", logs2);
            // The third log captures only the length that is divisible by 2.
            assertStrings("[3, 5, 9]", logs3);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val logs3 = new ArrayList<String>();
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .eager()
                    .peek   (v -> logs1.add("" + v))
                    .map    (v -> (int)Math.round(v * 1.5))
                    .peek   (v -> logs2.add("" + v))
                    .exclude(theInteger.thatIsDivisibleBy(2))
                    .peek   (v -> logs3.add("" + v))
                    ;
            // Since the list is eager, all the value pass through all peek all the time
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",    logs1);
            assertStrings("[2, 3, 5, 6, 8, 9, 11, 12, 14, 15]", logs2);
            assertStrings("[3, 5, 9, 11, 15]", logs3);
            // Get part of them so those peek will goes through the peek
            assertStrings("[3, 5, 9, 11, 15]", list.limit(5));
            // No more passing through the log stay still
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs1);
            assertStrings("[2, 3, 5, 6, 8, 9, 11, 12, 14, 15]", logs2);
            assertStrings("[3, 5, 9, 11, 15]", logs3);
        }
    }
    
    //-- List --
    
    @Test
    public void testToFuncList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toFuncList();
            assertStrings("[1, 2, 3]", funcList.toString());
            assertTrue(funcList instanceof IntFuncList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toJavaList();
            assertStrings("[1, 2, 3]", funcList);
            assertFalse(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toImmutableList();
            assertStrings("[1, 2, 3]", funcList);
            assertTrue(funcList instanceof ImmutableIntFuncList);
        });
    }
    
    @Test
    public void testIterator() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.next());
            
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.next());
            
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.next());
            
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testSpliterator() {
        run(IntFuncList.of(One, Two, Three), list -> {
            Spliterator.OfInt spliterator = list.spliterator();
            IntStream         stream      = StreamSupport.intStream(spliterator, false);
            IntStreamPlus     streamPlus  = IntStreamPlus.from(stream);
            assertStrings("[1, 2, 3]", streamPlus.toListString());
        });
    }
    
//    @Test
//    public void testContainsAllOf() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertTrue (list.containsAllOf(One, Five));
//            assertFalse(list.containsAllOf(One, Six));
//        });
//    }
//    
//    @Test
//    public void testContainsAnyOf() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertTrue (list.containsAnyOf(One, Six));
//            assertFalse(list.containsAnyOf(Six, Seven));
//        });
//    }
//    
//    @Test
//    public void testContainsNoneOf() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertTrue (list.containsNoneOf(Six, Seven));
//            assertFalse(list.containsNoneOf(One, Six));
//        });
//    }
//    
//    @Test
//    public void testJavaList_for() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val logs = new ArrayList<String>();
//            for(val value : list) {
//                logs.add(value);
//            }
//            assertStrings("[1, 2, 3]", logs);
//        });
//    }
//    
//    @Test
//    public void testJavaList_size_isEmpty() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertEquals(3, list.size());
//            assertFalse (list.isEmpty());
//        });
//        run(IntFuncList.empty(), list -> {
//            assertEquals(0, list.size());
//            assertTrue  (list.isEmpty());
//        });
//    }
//    
//    @Test
//    public void testJavaList_contains() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertTrue (list.contains(Two));
//            assertFalse(list.contains(Five));
//        });
//    }
//    
//    @Test
//    public void testJavaList_containsAll() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertTrue (list.containsAll(listOf(Two, Three)));
//            assertFalse(list.containsAll(listOf(Two, Five)));
//        });
//    }
//    
//    @Test
//    public void testForEach() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val logs   = new ArrayList<String>();
//            list.forEach(s -> logs.add(s));
//            assertStrings("[1, 2, 3]", logs);
//        });
//    }
//    
//    @Test
//    public void testForEachOrdered() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val logs   = new ArrayList<String>();
//            list.forEachOrdered(s -> logs.add(s));
//            assertStrings("[1, 2, 3]", logs);
//        });
//    }
//    
//    @Test
//    public void testReduce() {
//        run(IntFuncList.of(1, 2, 3), list -> {
//            assertEquals(6, list.reduce(0, (a, b) -> a + b).intValue());
//            
//            assertEquals(6, list.reduce((a, b) -> a + b).get().intValue());
//            
//            assertEquals(6, list.reduce(
//                                        BigInteger.ZERO,
//                                        (b, i) -> b.add(BigInteger.valueOf((long)i)),
//                                        (a, b) -> a.add(b)).intValue());
//        });
//    }
//    @Test
//    public void testCollect() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", list.collect(Collectors.toList()));
//            
//            Supplier<StringBuffer> supplier = StringBuffer::new;
//            BiConsumer<StringBuffer, String> accumulator = StringBuffer::append;
//            BiConsumer<StringBuffer, StringBuffer> combiner = (a, b) -> a.append(b.toString());
//            assertStrings("OneTwoThree", list.collect(supplier, accumulator, combiner));
//        });
//    }
//    
//    @Test
//    public void testMinMax() {
//        run(IntFuncList.of(One, Two, Three, Four), list -> {
//            assertStrings("Optional[One]",   list.min((a, b)-> a.length()-b.length()));
//            assertStrings("Optional[Three]", list.max((a, b)-> a.length()-b.length()));
//        });
//    }
//    
//    @Test
//    public void testCount() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("3", list.count());
//        });
//    }
//    
//    @Test
//    public void testAnyMatch() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertTrue(list.anyMatch(One::equals));
//        });
//    }
//    
//    @Test
//    public void testAllMatch() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertFalse(list.allMatch(One::equals));
//        });
//    }
//    
//    @Test
//    public void testNoneMatch() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertTrue(list.noneMatch(Five::equals));
//        });
//    }
//    
//    @Test
//    public void testFindFirst() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Optional[One]", list.findFirst());
//        });
//    }
//    
//    @Test
//    public void testFindAny() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Optional[One]", list.findAny());
//        });
//    }
//    
//    @Test
//    public void testFindLast() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Optional[Three]", list.findLast());
//        });
//    }
//    
//    @Test
//    public void testFirstResult() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Result:{ Value: One }", list.firstResult());
//        });
//    }
//    
//    @Test
//    public void testLastResult() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Result:{ Value: Three }", list.lastResult());
//        });
//    }
//    
//    @Test
//    public void testJavaList_get() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertEquals(One,   list.get(0));
//            assertEquals(Two,   list.get(1));
//            assertEquals(Three, list.get(2));
//        });
//    }
//    
//    @Test
//    public void testJavaList_indexOf() {
//        run(IntFuncList.of(One, Two, Three, Two, Three), list -> {
//            assertEquals( 1, list.indexOf(Two));
//            assertEquals(-1, list.indexOf(Five));
//        });
//    }
//    
//    @Test
//    public void testJavaList_lastIndexOf() {
//        run(IntFuncList.of(One, Two, Three, Two, Three), list -> {
//            assertEquals( 3, list.lastIndexOf(Two));
//            assertEquals(-1, list.lastIndexOf(Five));
//        });
//    }
//    
//    @Test
//    public void testJavaList_subList() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Two, Three]",             list.subList(1, 3));
//            assertStrings("[Two, Three, Four, Five]", list.subList(1, 10));
//        });
//    }
//    
//    //-- ReadOnly --
//    
//    @Test
//    public void testJavaList_readOnly() {
//        val funcList = FuncList.of(One, Two, Three, Four, Five);
//        runExpectReadOnlyListException(funcList, list -> list.set(1, Six));
//        runExpectReadOnlyListException(funcList, list -> list.add(Six));
//        runExpectReadOnlyListException(funcList, list -> list.add(2, Six));
//        runExpectReadOnlyListException(funcList, list -> list.addAll(asList(Six, Seven)));
//        runExpectReadOnlyListException(funcList, list -> list.addAll(2, asList(Six, Seven)));
//        runExpectReadOnlyListException(funcList, list -> list.remove(Four));
//        runExpectReadOnlyListException(funcList, list -> list.remove(2));
//        runExpectReadOnlyListException(funcList, list -> list.removeAll(asList(Four, Five)));
//        runExpectReadOnlyListException(funcList, list -> list.retainAll(asList(Four, Five)));
//        runExpectReadOnlyListException(funcList, list -> list.clear());
//        runExpectReadOnlyListException(funcList, list -> list.replaceAll(value -> "-" + value + "-"));
//        runExpectReadOnlyListException(funcList, list -> list.sort(String.CASE_INSENSITIVE_ORDER));
//    }
//    
//    //-- AsStreamPlusWithGroupingBy --
//    
//    @Test
//    public void testGroupingBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings(
//                    "{3:[One, Two], 4:[Four, Five], 5:[Three]}",
//                    list
//                    .groupingBy(theString.length())
//                    .sortedByKey(theInteger));
//        });
//    }
//    
//    @Test
//    public void testGroupingBy_aggregate() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings(
//                    "{3:One-Two, 4:Four-Five, 5:Three}",
//                    list
//                    .groupingBy(theString.length(), join("-"))
//                    .sortedByKey(theInteger));
//        });
//    }
//    
//    @Test
//    public void testGroupingBy_process() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            val sumLength = new SumLength();
//            assertStrings(
//                    "{3:6, 4:8, 5:5}",
//                    list
//                    .groupingBy(theString.length(), sumLength)
//                    .sortedByKey(theInteger));
//        });
//    }
//    
//    @Test
//    public void testGroupingBy_collect() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings(
//                    "{3:One:Two, 4:Four:Five, 5:Three}",
//                    list
//                    .groupingBy(theString.length(), () -> Collectors.joining(":"))
//                    .sortedByKey(theInteger));
//        });
//    }
//    
//    //-- Functional list
//    
//    // test
//    // lazy+eager
//    
//    @Test
//    public void testMap() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[-3-, -3-, -5-, -4-, -4-]",
//                    list
//                    .map(s -> "-" + s.length() + "-")
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToInt() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("11", list.mapToInt(String::length).sum());
//        });
//    }
//    
//    @Test
//    public void testMapToDouble() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("11.0", list.map(s -> "" + s.length()).mapToDouble(Double::parseDouble).sum());
//        });
//    }
//    
//    @Test
//    public void testMapToObj() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[-3-, -3-, -5-, -4-, -4-]",
//                    list
//                    .mapToObj(s -> "-" + s.length() + "-")
//                    );
//        });
//    }
//    
//    //-- FlatMap --
//    
//    @Test
//    public void testFlatMap() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings(
//                    "[3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5]",
//                    list.flatMap(s -> FuncList.cycle(s.length()).limit(s.length())));
//        });
//    }
//    
//    @Test
//    public void testFlatMapToInt() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings(
//                    "[3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5]",
//                    list.flatMapToInt(s -> IntFuncList.cycle(s.length()).limit(s.length())));
//        });
//    }
//    
//    @Test
//    public void testFlatMapToDouble() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings(
//                    "[3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 5.0, 5.0, 5.0, 5.0, 5.0]",
//                    list
//                    .flatMapToDouble(s -> DoubleFuncList.cycle(s.length()).limit(s.length())));
//        });
//    }
//    
//    //-- Filter --
//    
//    @Test
//    public void testFilter() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings(
//                    "[Three]",
//                    list.filter(theString.length().thatGreaterThan(4)));
//        });
//    }
//    
//    @Test
//    public void testFilter_mapper() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings(
//                    "[Three]",
//                    list.filter(theString.length(), theInteger.thatGreaterThan(4)));
//        });
//    }
//    
//    @Test
//    public void testPeek() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val logs   = new ArrayList<String>();
//            assertStrings("[1, 2, 3]", list.peek(s -> logs.add(s)));
//            assertStrings("[1, 2, 3]", logs);
//        });
//    }
//    
//    @Test
//    public void testLimit() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[1, 2, 3]", list.limit(3));
//        });
//    }
//    
//    @Test
//    public void testSkip() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.skip(2));
//        });
//    }
//    
//    @Test
//    public void testDistinct() {
//        run(IntFuncList.of(One, Two, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", list.distinct());
//        });
//    }
//    
//    @Test
//    public void testSorted() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[3, 3, 4, 4, 5]", list.map(theString.length()).sorted());
//            assertStrings("[5, 4, 4, 3, 3]", list.map(theString.length()).sorted((a, b) -> (b - a)));
//        });
//    }
//    
//    @Test
//    public void testToArray() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray()));
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray(n -> new String[n])));
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray(String[]::new)));
//            
//            // exact sizes array will be used.
//            val arraySameSize = new String[list.size()];
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray(arraySameSize)));
//            
//            // too small array will be ignored and a new one created
//            val arrayTooSmall = new String[list.size() - 1];
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray(arrayTooSmall)));
//            
//            // too large array will be ignored and a new one created
//            val arrayTooLarge = new String[list.size() + 1];
//            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray(arrayTooLarge)));
//        });
//    }
//    
//    @Test
//    public void testNullableOptionalResult() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("Nullable.of([One, Two, Three])",      list.__nullable());
//            assertStrings("Optional[[One, Two, Three]]",         list.__optional());
//            assertStrings("Result:{ Value: [One, Two, Three] }", list.__result());
//        });
//    }
//    
//    @Test
//    public void testIndexOf() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertEquals(2, list.indexOf(Three));
//        });
//    }
//    
//    @Test
//    public void testIndexesOf() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[0, 2]", list.indexesOf(value -> value.equals(One) || value.equals(Three)));
//        });
//    }
//    
//    @Test
//    public void testFirst() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[One]",     list.first());
//            assertStrings("[1, 2, 3]", list.first(3));
//        });
//    }
//    
//    @Test
//    public void testLast() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Five]",      list.last());
//            assertStrings("[Three, Four, Five]", list.last(3));
//        });
//    }
//    
//    @Test
//    public void testAt() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.at(2));
//            assertStrings("Optional.empty",  list.at(10));
//        });
//    }
//    
//    @Test
//    public void testTail() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Two, Three, Four, Five]", list.tail());
//        });
//    }
//    
//    @Test
//    public void testToBuilder() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[One, Two, Three, Four, Five]", list.toBuilder().add(Four).add(Five).build());
//        });
//    }
//    
//    @Test
//    public void testAppend() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",       list);
//            assertStrings("[One, Two, Three, Four]", list.append(Four));
//            assertStrings("[1, 2, 3]",       list);
//        });
//    }
//    
//    @Test
//    public void testAppendAll() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",             list);
//            assertStrings("[One, Two, Three, Four, Five]", list.appendAll(Four, Five));
//            assertStrings("[One, Two, Three, Four, Five]", list.appendAll(IntFuncList.listOf(Four, Five)));
//            assertStrings("[One, Two, Three, Four, Five]", list.appendAll(FuncList.of(Four, Five)));
//            assertStrings("[1, 2, 3]",             list);
//        });
//    }
//    
//    @Test
//    public void testPrepend() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",       list);
//            assertStrings("[Zero, One, Two, Three]", list.prepend(Zero));
//            assertStrings("[1, 2, 3]",       list);
//        });
//    }
//    
//    @Test
//    public void testPrependAll() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",                 list);
//            assertStrings("[MinusOne, Zero, One, Two, Three]", list.prependAll(MinusOne, Zero));
//            assertStrings("[MinusOne, Zero, One, Two, Three]", list.prependAll(IntFuncList.listOf(MinusOne, Zero)));
//            assertStrings("[MinusOne, Zero, One, Two, Three]", list.prependAll(FuncList.of(MinusOne, Zero)));
//            assertStrings("[1, 2, 3]",                 list);
//        });
//    }
//    
//    @Test
//    public void testWith() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",       list);
//            assertStrings("[One, Zero, Three]",      list.with(1, Zero));
//            assertStrings("[One, Two=>Zero, Three]", list.with(1, value -> value + "=>" + Zero));
//            assertStrings("[1, 2, 3]",       list);
//        });
//    }
//    
//    @Test
//    public void testInsertAt() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",       list);
//            assertStrings("[One, Zero, Two, Three]", list.insertAt(1, Zero));
//            assertStrings("[1, 2, 3]",       list);
//        });
//    }
//    
//    @Test
//    public void testInsertAllAt() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]",             list);
//            assertStrings("[One, Two, Zero, Zero, Three]", list.insertAt(2, Zero, Zero));
//            assertStrings("[One, Two, Zero, Zero, Three]", list.insertAllAt(2, listOf(Zero, Zero)));
//            assertStrings("[One, Two, Zero, Zero, Three]", list.insertAllAt(2, FuncList.of(Zero, Zero)));
//            assertStrings("[1, 2, 3]",             list);
//        });
//    }
//    
//    @Test
//    public void testExclude() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[One, Two, Three, Four, Five]", list);
//            assertStrings("[One, Three, Four, Five]",      list.exclude(Two));
//            assertStrings("[One, Three, Four, Five]",      list.exclude(Two::equals));
//            assertStrings("[One, Three, Four, Five]",      list.excludeAt(1));
//            assertStrings("[One, Five]",                   list.excludeFrom(1, 3));
//            assertStrings("[One, Three, Four, Five]",      list.excludeBetween(1, 2));
//            assertStrings("[One, Two, Three, Four, Five]", list);
//        });
//    }
//    
//    @Test
//    public void testReverse() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[One, Two, Three, Four, Five]", list);
//            assertStrings("[Five, Four, Three, Two, One]", list.reverse());
//            assertStrings("[One, Two, Three, Four, Five]", list);
//        });
//    }
//    
//    @Test
//    public void testShuffle() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
//            assertStrings  ("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list);
//            assertNotEquals("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list.shuffle().toString());
//            assertStrings  ("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list);
//        });
//    }
//    
//    @Test
//    public void testQuery() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
//            assertStrings("[One, Two, Three, Four, Five, Six]", list);
//            assertStrings("[(0,One), (1,Two), (5,Six)]",        list.query(value -> value.length() == 3));
//            assertStrings("[One, Two, Three, Four, Five, Six]", list);
//        });
//    }
//    
//    @Test
//    public void testMinIndexBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
//            assertStrings("Optional[0]", list.minIndexBy(value -> value.length()));
//        });
//    }
//    
//    @Test
//    public void testMaxIndexBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
//            assertStrings("Optional[2]", list.maxIndexBy(value -> value.length()));
//        });
//    }
//    
//    
//    //-- AsStreamPlusWithConversion --
//    
//    @Test
//    public void testToByteArray() {
//        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
//            assertStrings("[65, 66, 67, 68]", Arrays.toString(list.toByteArray(c -> (byte)(int)c)));
//        });
//    }
//    
//    @Test
//    public void testToIntArray() {
//        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
//            assertStrings("[65, 66, 67, 68]", Arrays.toString(list.toIntArray(c -> (int)c)));
//        });
//    }
//    
////    @Test
////    public void testToLongArray() {
////        val stream = StreamPlus.of('A', 'B', 'C', 'D');
////        assertStrings("[65, 66, 67, 68]", Arrays.toString(stream.toLongArray(c -> (long)c)));
////    }
//
//    @Test
//    public void testToDoubleArray() {
//        val stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(stream.toDoubleArray(c -> (double)(int)c)));
//    }
//    
//    @Test
//    public void testToArrayList() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val newList = list.toArrayList();
//            assertStrings("[1, 2, 3]", newList);
//            assertTrue(newList instanceof ArrayList);
//        });
//    }
//    
//    @Test
//    public void testToList() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val newList = list.toJavaList();
//            assertStrings("[1, 2, 3]", newList);
//            assertTrue(newList instanceof List);
//        });
//    }
//    
//    @Test
//    public void testToMutableList() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val newList = list.toMutableList();
//            assertStrings("[1, 2, 3]", newList);
//            // This is because we use ArrayList as mutable list ... not it should not always be.
//            assertTrue(newList instanceof ArrayList);
//        });
//    }
//    
//    //-- join --
//    
//    @Test
//    public void testJoin() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("OneTwoThree", list.join());
//        });
//    }
//    
//    @Test
//    public void testJoin_withDelimiter() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("One, Two, Three", list.join(", "));
//        });
//    }
//    
//    @Test
//    public void testToListString() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", list);
//        });
//    }
//    
//    //-- toMap --
//    
//    @Test
//    public void testToMap() {
//        run(IntFuncList.of(One, Three, Five), list -> {
//            assertStrings("{3:One, 4:Five, 5:Three}", list.toMap(theString.length()).toString());
//        });
//    }
//    
//    @Test
//    public void testToMap_withValue() {
//        run(IntFuncList.of(One, Three, Five), list -> {
//            assertStrings("{3:-->One, 4:-->Five, 5:-->Three}", list.toMap(theString.length(), theString.withPrefix("-->")).toString());
//        });
//    }
//    
//    @Test
//    public void testToMap_withMappedMergedValue() {
//        run(IntFuncList.of(One, Two, Three, Five), list -> {
//            assertStrings("{3:One+Two, 4:Five, 5:Three}", list.toMap(theString.length(), theString, (a, b) -> a + "+" + b).toString());
//        });
//    }
//    
//    @Test
//    public void testToMap_withMergedValue() {
//        run(IntFuncList.of(One, Two, Three, Five), list -> {
//            assertStrings("{3:One+Two, 4:Five, 5:Three}", list.toMap(theString.length(), (a, b) -> a + "+" + b).toString());
//        });
//    }
//    
//    @Test
//    public void testToSet() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val set    = list.toSet();
//            assertStrings("[1, 2, 3]", set);
//            assertTrue(set instanceof Set);
//        });
//    }
//    
//    @Test
//    public void testForEachWithIndex() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            val logs   = new ArrayList<String>();
//            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
//            assertStrings("[0:One, 1:Two, 2:Three]", logs);
//        });
//    }
//    
//    @Test
//    public void testPopulateArray() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            val array  = new String[5];
//            list.populateArray(array);
//            assertStrings("[One, Two, Three, Four, Five]", Arrays.toString(array));
//        });
//    }
//    
//    @Test
//    public void testPopulateArray_withOffset() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            val array  = new String[3];
//            list.populateArray(array, 2);
//            assertStrings("[null, null, One]", Arrays.toString(array));
//        });
//    }
//    
//    @Test
//    public void testPopulateArray_withOffsetLength() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            val array  = new String[5];
//            list.populateArray(array, 1, 3);
//            assertStrings("[null, One, Two, Three, null]", Arrays.toString(array));
//        });
//    }
//    
//    //-- AsFuncListWithMatch --
//    
//    @Test
//    public void testFindFirst_withPredicate() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.findFirst(theString.thatContains("ee")));
//        });
//    }
//    
//    @Test
//    public void testFindAny_withPredicate() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.findAny(theString.thatContains("ee")));
//        });
//    }
//    
//    @Test
//    public void testFindFirst_withMapper_withPredicate() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.findFirst(theString.length(), l ->  l == 5));
//        });
//    }
//    
//    @Test
//    public void testFindAny_withMapper_withPredicate() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.findAny(theString.length(), l -> l == 5));
//        });
//    }
//    
//    //-- AsFuncListWithStatistic --
//    
//    @Test
//    public void testSize() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("3", list.size());
//        });
//    }
//    
//    @Test
//    public void testMinBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[One]", list.minBy(theString.length()));
//        });
//    }
//    
//    @Test
//    public void testMaxBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.maxBy(theString.length()));
//        });
//    }
//    
//    @Test
//    public void testMinBy_withMapper() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[Three]", list.minBy(theString.length(), (a, b)->b-a));
//        });
//    }
//    
//    @Test
//    public void testMaxBy_withMapper() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("Optional[One]", list.maxBy(theString.length(), (a, b)->b-a));
//        });
//    }
//    
//    @Test
//    public void testMinMaxBy() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("(Optional[Five],Optional[Two])", list.minMax(String.CASE_INSENSITIVE_ORDER));
//        });
//    }
//    
//    @Test
//    public void testMinMaxBy_withMapper() {
//        run(IntFuncList.of(One, Two, Three, Four), list -> {
//            assertStrings("(Optional[One],Optional[Three])", list.minMaxBy(theString.length()));
//        });
//    }
//    
//    @Test
//    public void testMinMaxBy_withMapper_withComparator() {
//        run(IntFuncList.of(One, Two, Three, Four), list -> {
//            assertStrings("(Optional[Three],Optional[Two])", list.minMaxBy(theString.length(), (a, b) -> b-a));
//        });
//    }
//    
//    //-- StreamPlusWithCalculate --
//    
//    static class SumLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0 }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class AvgLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0, 0 }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); a[1]++; }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]/a[1]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class MinLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MAX_VALUE }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.min(a[0], s.length()); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]) }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class MaxLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MIN_VALUE }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.max(a[0], s.length()); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]) }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class Sum implements CollectorPlus<Integer, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>            supplier()          { return ()->new int[] { 0 }; }
//        @Override public BiConsumer<int[], Integer> accumulator()       { return (a, e)->{ a[0] += e.intValue(); }; }
//        @Override public BinaryOperator<int[]>      combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
//        @Override public Function<int[], Integer>   finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>       characteristics()   { return characteristics; }
//        @Override public Collector<Integer, int[], Integer> collector() { return this; }
//    }
//    
//    @Test
//    public void testCalculate() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            assertEquals(18, list.calculate(sumLength).intValue());
//        });
//    }
//    
//    @Test
//    public void testCalculate2() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            assertStrings("(18,4)", list.calculate(sumLength, avgLength));
//        });
//    }
//    
//    @Test
//    public void testCalculate2_combine() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            val range = list.calculate(maxLength, minLength).mapTo((max, min) -> max - min).intValue();
//            assertEquals(3, range);
//        });
//    }
//    
//    @Test
//    public void testCalculate3() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            assertStrings("(18,4,3)", list.calculate(sumLength, avgLength, minLength));
//        });
//    }
//    
//    @Test
//    public void testCalculate3_combine() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val value     = list
//                            .calculate(sumLength, avgLength, minLength)
//                            .mapTo((sum, avg, min) -> "sum: " + sum + ", avg: " + avg + ", min: " + min);
//            assertStrings("sum: 18, avg: 4, min: 3", value);
//        });
//    }
//    
//    @Test
//    public void testCalculate4() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            assertStrings("(18,4,3,6)", list.calculate(sumLength, avgLength, minLength, maxLength));
//        });
//    }
//    
//    @Test
//    public void testCalculate4_combine() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            val value     = list
//                            .calculate(sumLength, avgLength, minLength, maxLength)
//                            .mapTo((sum, avg, min, max) -> "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max);
//            assertStrings("sum: 18, avg: 4, min: 3, max: 6", value);
//        });
//    }
//    
//    @Test
//    public void testCalculate5() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            assertStrings("(18,4,3,6,18)", list.calculate(sumLength, avgLength, minLength, maxLength, sumLength));
//        });
//    }
//    
//    @Test
//    public void testCalculate5_combine() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            val value     = list
//                            .calculate(sumLength, avgLength, minLength, maxLength, sumLength)
//                            .mapTo((sum, avg, min, max, sum2) -> {
//                                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2;
//                            });
//            assertStrings("sum: 18, avg: 4, min: 3, max: 6, sum2: 18", value);
//        });
//    }
//    
//    @Test
//    public void testCalculate6() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            assertStrings("(18,4,3,6,18,4)", list.calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength));
//        });
//    }
//    
//    @Test
//    public void testCalculate6_combine() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumLength = new SumLength();
//            val avgLength = new AvgLength();
//            val minLength = new MinLength();
//            val maxLength = new MaxLength();
//            val value     = list
//                            .calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength)
//                            .mapTo((sum, avg, min, max, sum2, avg2) -> {
//                                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2 + ", avg2: " + avg2;
//                            });
//            assertStrings("sum: 18, avg: 4, min: 3, max: 6, sum2: 18, avg2: 4", value);
//        });
//    }
//    
//    @Test
//    public void testCalculate_of() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sum = new Sum();
//            assertEquals(18, list.calculate(sum.of(theString.length())).intValue());
//        });
//    }
//    
//    //-- FuncListWithCombine --
//    
//    @Test
//    public void testConcatWith() {
//        run(IntFuncList.of(One, Two), FuncList.of(Three, Four), (list1, streamabl2) -> {
//            assertStrings("[One, Two, Three, Four]",
//                    list1.concatWith(streamabl2)
//                    );
//        });
//    }
//        
//    @Test
//    public void testMerge() {
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (list1, streamabl2) -> {
//            assertStrings(
//                "A, 0, B, 1, C, 2, 3, 4, 5, 6",
//                list1
//                    .mergeWith(streamabl2)
//                    .limit    (10)
//                    .join     (", "));
//        });
//    }
//    
//    @Test
//    public void testZipWith() {
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2)",
//                        listA.zipWith(listB).join(", "));
//            });
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2)",
//                        listA.zipWith(listB, RequireBoth).join(", "));
//            });
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2), (null,3), (null,4)",
//                        listA.zipWith(listB, AllowUnpaired).limit(5).join(", "));
//            });
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "A:0, B:1, C:2",
//                        listA.zipWith(listB, (c, i) -> c + ":" + i).join(", "));
//            });
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "A:0, B:1, C:2",
//                        listA.zipWith(listB, RequireBoth, (c, i) -> c + ":" + i).join(", "));
//            });
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "A:0, B:1, C:2, null:3, null:4",
//                        listA.zipWith(listB, AllowUnpaired, (c, i) -> c + ":" + i).limit(5).join(", "));
//            });
//    }
//    
//    @Test
//    public void testChoose() {
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                val bool = new AtomicBoolean(true);
//                assertStrings("A, 1, C", listA.choose(listB, (a, b) -> {
//                    boolean curValue = bool.get();
//                    return bool.getAndSet(!curValue);
//                }).limit(5).join(", "));
//            });
//    }
//    
//    @Test
//    public void testChoose_AllowUnpaired() {
//        run(IntFuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                val bool    = new AtomicBoolean(true);
//                assertStrings("A, 1, C, 3, 4, 5, 6", listA.choose(listB, AllowUnpaired, (a, b) -> {
//                    boolean curValue = bool.get();
//                    return bool.getAndSet(!curValue);
//                }).limit(7).join(", "));
//            });
//    }
//    
//    //-- StreamPlusWithFillNull --
//    
//    @Test
//    public void testFillNull() {
//        run(IntFuncList.of("A", "B",  null, "C"), list -> {
//            assertStrings("[A, B, Z, C]", list.fillNull("Z"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_lens() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNull(Car.theCar.color, "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNull(
//                            (Car car)               -> car.color(),
//                            (Car car, String color) -> car.withColor(color),
//                            "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_lens_supplier() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullWith(Car.theCar.color, () -> "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_supplier() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullWith(
//                            (Car car)               -> car.color(),
//                            (Car car, String color) -> car.withColor(color),
//                            ()                      -> "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_lens_function() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullBy(Car.theCar.color, (Car car) -> "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_function() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullBy(
//                            (Car car)               -> car.color(),
//                            (Car car, String color) -> car.withColor(color),
//                            (Car car)               -> "Black"));
//        });
//    }
//    
//    //-- StreamPlusWithFilter --
//    
//    @Test
//    public void testFilterClass() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            assertStrings("[One, Three, Five]", list.filter(String.class));
//        });
//    }
//    
//    @Test
//    public void testFilterClass_withPredicate() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            assertStrings("[One, Five]", list.filter(String.class, theString.length().thatLessThan(5)));
//        });
//    }
//    
//    @Test
//    public void testFilter_withMappter() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings(
//                    "[Three, Four, Five]", 
//                    list.filter(
//                            str -> BigInteger.valueOf(str.length()),
//                            b   -> b.intValue() >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsInt() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsInt(str -> str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsLong() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsLong(str -> (long)str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsDouble() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsDouble(str -> (double)str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsObject() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsObject(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterWithIndex() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Four, Five]", list.filterWithIndex((index, str) -> index > 2 && !str.startsWith("T")));
//        });
//    }
//    
//    @Test
//    public void testFilterNonNull() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Green, Red]",
//                    list.map(theCar.color).filterNonNull());
//        });
//    }
//    
//    @Test
//    public void testFilterNonNull_withMapper() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                    list.filterNonNull(theCar.color));
//        });
//    }
//    
//    @Test
//    public void testExcludeNull() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Green, Red]",
//                    list.map(theCar.color).excludeNull());
//        });
//    }
//    
//    @Test
//    public void testExcludeNull_withMapper() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                    list.excludeNull(theCar.color));
//        });
//    }
//    
//    @Test
//    public void testFilterMapper() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Red)]",
//                    list.filter(theCar.color, color -> Arrays.asList("Blue", "Red").contains(color)));
//        });
//    }
//    
//    @Test
//    public void testFilterIn() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Red]",
//                    list.map(theCar.color).filterIn("Blue", "Red"));
//        });
//    }
//    
//    @Test
//    public void testFilterIn_collection() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Red]",
//                    list.map(theCar.color).filterIn(asList("Blue", "Red")));
//        });
//    }
//    
//    @Test
//    public void testExcludeIn() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Green, null]",
//                    list.map(theCar.color).excludeIn("Blue", "Red"));
//        });
//    }
//    
//    @Test
//    public void testExcludeIn_collection() {
//        run(IntFuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Green, null]",
//                    list.map(theCar.color).excludeIn(asList("Blue", "Red")));
//        });
//    }
//    
//    //-- FuncListWithFlatMap --
//    
//    @Test
//    public void testFlatMapOnly() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[One, 3, 5]", list.flatMapOnly(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("" + s.length())));
//        });
//    }
//    
//    @Test
//    public void testFlatMapIf() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[(One), [3], [5]]", list.flatMapIf(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("[" + s.length() + "]"), s -> FuncList.of("(" + s + ")")));
//        });
//    }
//    
//    //-- FuncListWithLimit --
//    
//    @Test
//    public void testSkipLimitLong() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[Two]", list.skip((Long)1L).limit((Long)1L));
//        });
//    }
//    
//    @Test
//    public void testSkipLimitLongNull() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", list.skip(null).limit(null));
//        });
//    }
//    
//    @Test
//    public void testSkipLimitLongMinus() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1, 2, 3]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
//        });
//    }
//    
//    @Test
//    public void testSkipWhile() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[3, 4, 5, 4, 3, 2, 1]",       list.skipWhile(i -> i < 3));
//            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i > 3));
//        });
//    }
//    
//    @Test
//    public void testSkipUntil() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[4, 5, 4, 3, 2, 1]",          list.skipUntil(i -> i > 3));
//            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i < 3));
//        });
//    }
//    
//    @Test
//    public void testTakeWhile() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            val logs = new ArrayList<Integer>();
//            assertStrings("[1, 2, 3]",    list.peek(logs::add).takeWhile(i -> i < 4));
//            assertStrings("[1, 2, 3, 4]", logs);
//            //                       ^--- Because it needs 4 to do the check in `takeWhile`
//            
//            logs.clear();
//            assertStrings("[]", list.peek(logs::add).takeWhile(i -> i > 4));
//            assertStrings("[1]", logs);
//            //              ^--- Because it needs 1 to do the check in `takeWhile`
//        });
//    }
//    
//    @Test
//    public void testTakeWhile_previous() {
//        run(IntFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.takeWhile((a, b) -> b == a + 1));
//        });
//    }
//    
//    @Test
//    public void testTakeUtil() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            val logs = new ArrayList<Integer>();
//            assertStrings("[1, 2, 3, 4]", list.peek(logs::add).takeUntil(i -> i > 4));
//            assertStrings("[1, 2, 3, 4, 5]", logs);
//            //                          ^--- Because it needs 5 to do the check in `takeUntil`
//            
//            logs.clear();
//            assertStrings("[]",  list.peek(logs::add).takeUntil(i -> i < 4));
//            assertStrings("[1]", logs);
//            //              ^--- Because it needs 1 to do the check in `takeUntil`
//        });
//    }
//    
//    @Test
//    public void testTakeUntil_previous() {
//        run(IntFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.takeUntil((a, b) -> b > a + 1));
//        });
//    }
//    
//    @Test
//    public void testDropAfter() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.dropAfter(i -> i == 4));
//            //                       ^--- Include 4
//        });
//    }
//    
//    @Test
//    public void testDropAfter_previous() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4, 5, 4]", list.dropAfter((a, b) -> b < a));
//            //                             ^--- Include 4
//        });
//    }
//    
//    @Test
//    public void testSkipTake() {
//        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            val logs = new ArrayList<Integer>();
//            assertStrings("[3, 4, 5, 4, 3]", list.peek(logs::add).skipWhile(i -> i < 3).takeUntil(i -> i < 3));
//            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2]", logs);
//            //              ^--^-----------------^--- Because it needs these number to do the check in `skipWhile` and `takeWhile`
//        });
//    }
//    
//    //-- FuncListWithMap --
//    
//    @Test
//    public void testMapOnly() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[ONE, TWO, Three]",
//                    list
//                    .mapOnly(
//                            $S.length().thatLessThan(4),
//                            $S.toUpperCase())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapIf() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[ONE, TWO, three]",
//                    list
//                    .mapIf(
//                            $S.length().thatLessThan(4), $S.toUpperCase(),
//                            $S.toLowerCase())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToObjIf() {
//        run(IntFuncList.of(One, Two, Three), list -> {
//            assertStrings("[ONE, TWO, three]",
//                    list
//                    .mapToObjIf(
//                            $S.length().thatLessThan(4), $S.toUpperCase(),
//                            $S.toLowerCase())
//                    );
//        });
//    }
//    
//    //== Map First ==
//    
//    @Test
//    public void testMapFirst_2() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings("[ONE, TWO, three, four, five, SIX, seven, eight, nine, TEN, eleven, twelve]",
//                    list
//                    .mapFirst(
//                            str -> str.length() == 3 ? str.toUpperCase() : null,
//                            str -> str.toLowerCase())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapFirst_3() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings("[ONE, TWO, Three, four, five, SIX, Seven, Eight, nine, TEN, Eleven, Twelve]",
//                    list
//                    .mapFirst(
//                            str -> str.length() == 3 ? str.toUpperCase() : null,
//                            str -> str.length() == 4 ? str.toLowerCase() : null,
//                            str -> str)
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapFirst_4() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, Eleven, Twelve]",
//                    list
//                    .mapFirst(
//                            str -> str.length() == 3 ? str.toUpperCase() : null,
//                            str -> str.length() == 4 ? str.toLowerCase() : null,
//                            str -> str.length() == 5 ? "(" + str + ")": null,
//                            str -> str)
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapFirst_5() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, [Eleven], Twelve]",
//                    list
//                    .mapFirst(
//                            str -> str.length() == 3 ? str.toUpperCase() : null,
//                            str -> str.length() == 4 ? str.toLowerCase() : null,
//                            str -> str.length() == 5 ? "(" + str + ")": null,
//                            str -> str.length() == 6 && !str.contains("w")? "[" + str + "]": null,
//                            str -> str)
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapFirst_6() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings("[ONE, TWO, (Three), four, -- Five --, -- Six --, (Seven), -- Eight --, -- Nine --, TEN, [Eleven], Twelve]",
//                    list
//                    .mapFirst(
//                            str -> str.contains("i") ? "-- " + str + " --" : null,
//                            str -> str.length() == 3 ? str.toUpperCase() : null,
//                            str -> str.length() == 4 ? str.toLowerCase() : null,
//                            str -> str.length() == 5 ? "(" + str + ")": null,
//                            str -> str.length() == 6 && !str.contains("w") ? "[" + str + "]": null,
//                            str -> str)
//                    );
//        });
//    }
//    
//    //== MapThen ==
//    
//    @Test
//    public void testMapThen_2() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//        assertStrings(
//                "[O-n, T-w, T-h, F-o, F-i]",
//                list
//                .mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        (a, b) -> a + "-" + b)
//                );
//        });
//    }
//    
//    @Test
//    public void testMapThen_3() {
//        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
//        assertStrings(
//                "[O-n-e, T-w-o, T-h-r, F-o-u, F-i-v]",
//                list
//                .mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        $S.charAt(2),
//                        (a, b, c) -> a + "-" + b + "-" + c)
//                );
//        });
//    }
//    
//    @Test
//    public void testMapThen_4() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings(
//                    "[T-h-r-e, F-o-u-r, F-i-v-e, S-e-v-e, E-i-g-h, N-i-n-e, E-l-e-v, T-w-e-l]",
//                    list
//                        .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                        .mapThen(
//                            $S.charAt(0),
//                            $S.charAt(1),
//                            $S.charAt(2),
//                            $S.charAt(3),
//                            (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d)
//                        );
//        });
//    }
//    
//    @Test
//    public void testMapThen_5() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings(
//                    "[T-h-r-e-e, S-e-v-e-n, E-i-g-h-t, E-l-e-v-e, T-w-e-l-v]",
//                    list
//                        .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                        .mapThen(
//                            $S.charAt(0),
//                            $S.charAt(1),
//                            $S.charAt(2),
//                            $S.charAt(3),
//                            $S.charAt(4),
//                            (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e)
//                        );
//        });
//    }
//    
//    @Test
//    public void testMapThen_6() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
//            assertStrings(
//                    "[E-l-e-v-e-n, T-w-e-l-v-e]",
//                    list
//                        .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                        .mapThen(
//                            $S.charAt(0),
//                            $S.charAt(1),
//                            $S.charAt(2),
//                            $S.charAt(3),
//                            $S.charAt(4),
//                            $S.charAt(5),
//                            (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f)
//                        );
//        });
//    }
//    
//    //-- FuncListWithMapGroup --
//    
//    @Test
//    public void testMapTwoToSix() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            assertStrings(
//                    "[(One,Two), (Two,Three), (Three,Four), (Four,Five), (Five,Six), (Six,Seven), (Seven,Eight)]",
//                    list.mapTwo());
//            assertStrings(
//                    "[(One,Two,Three), (Two,Three,Four), (Three,Four,Five), (Four,Five,Six), (Five,Six,Seven), (Six,Seven,Eight)]",
//                    list.mapThree());
//            assertStrings(
//                    "[(One,Two,Three,Four), (Two,Three,Four,Five), (Three,Four,Five,Six), (Four,Five,Six,Seven), (Five,Six,Seven,Eight)]",
//                    list.mapFour());
//            assertStrings(
//                    "[(One,Two,Three,Four,Five), (Two,Three,Four,Five,Six), (Three,Four,Five,Six,Seven), (Four,Five,Six,Seven,Eight)]",
//                    list.mapFive());
//            assertStrings(
//                    "[(One,Two,Three,Four,Five,Six), (Two,Three,Four,Five,Six,Seven), (Three,Four,Five,Six,Seven,Eight)]",
//                    list.mapSix());
//        });
//    }
//    
//    @Test
//    public void testMapGroup_specific() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            assertStrings(
//                    "[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]",
//                    list.mapGroup((a,b) -> a+":"+b));
//            assertStrings(
//                    "[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]",
//                    list.mapGroup((a,b,c) -> a+":"+b+":"+c));
//            assertStrings(
//                    "[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d) -> a+":"+b+":"+c+":"+d));
//            assertStrings(
//                    "[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d,e) -> a+":"+b+":"+c+":"+d+":"+e));
//            assertStrings(
//                    "[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d,e,f) -> a+":"+b+":"+c+":"+d+":"+e+":"+f));
//        });
//    }
//    
//    @Test
//    public void testMapGroup_count() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            Func1<StreamPlus<? extends String>, String> joiner = stream -> stream.join(":");
//            assertStrings(
//                    "[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]",
//                    list.mapGroup(2, joiner));
//            assertStrings(
//                    "[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]",
//                    list.mapGroup(3, joiner));
//            assertStrings(
//                    "[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]",
//                    list.mapGroup(4, joiner));
//            assertStrings(
//                    "[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]",
//                    list.mapGroup(5, joiner));
//            assertStrings(
//                    "[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]",
//                    list.mapGroup(6, joiner));
//            
//            assertStrings(
//                    "[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]",
//                    list.mapGroup(2).map(joiner));
//            assertStrings(
//                    "[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]",
//                    list.mapGroup(3).map(joiner));
//            assertStrings(
//                    "[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]",
//                    list.mapGroup(4).map(joiner));
//            assertStrings(
//                    "[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]",
//                    list.mapGroup(5).map(joiner));
//            assertStrings(
//                    "[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]",
//                    list.mapGroup(6).map(joiner));
//        });
//    }
//    
//    @Test
//    public void testMapGroupToInt() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            assertStrings(
//                    "[6, 8, 9, 8, 7, 8, 10]",
//                    list.mapTwoToInt((a, b) -> a.length() + b.length()));
//            assertStrings(
//                    "[6, 8, 9, 8, 7, 8, 10]",
//                    list.mapGroupToInt(2, stream -> stream.mapToInt(theString.length()).sum()));
//        });
//    }
//    
//    @Test
//    public void testMapGroupToDouble() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            assertStrings(
//                    "[9.0, 15.0, 20.0, 16.0, 12.0, 15.0, 25.0]",
//                    list.mapTwoToDouble((a, b) -> a.length() * b.length()));
//            assertStrings(
//                    "[9.0, 15.0, 20.0, 16.0, 12.0, 15.0, 25.0]",
//                    list.mapGroupToDouble(2, stream -> stream.mapToDouble(theString.length().toDouble()).product().getAsDouble()));
//        });
//    }
//    
//    //-- FuncListWithMapToMap --
//    
//    @Test
//    public void testMapToMap_1() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:O}, "
//                    + "{<1>:T}, "
//                    + "{<1>:F}, "
//                    + "{<1>:S}, "
//                    + "{<1>:E}, "
//                    + "{<1>:T}, "
//                    + "{<1>:S}]",
//                    list
//                        .filter($S.length().thatGreaterThanOrEqualsTo(1))
//                        .mapToMap(
//                                "<1>", $S.charAt(0))
//                        .map(map -> map.sorted())
//                        );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_2() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:O, <2>:n}, "
//                    + "{<1>:T, <2>:h}, "
//                    + "{<1>:F, <2>:i}, "
//                    + "{<1>:S, <2>:e}, "
//                    + "{<1>:E, <2>:l}, "
//                    + "{<1>:T, <2>:h}, "
//                    + "{<1>:S, <2>:e}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(2))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_3() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:O, <2>:n, <3>:e}, "
//                    + "{<1>:T, <2>:h, <3>:r}, "
//                    + "{<1>:F, <2>:i, <3>:v}, "
//                    + "{<1>:S, <2>:e, <3>:v}, "
//                    + "{<1>:E, <2>:l, <3>:e}, "
//                    + "{<1>:T, <2>:h, <3>:i}, "
//                    + "{<1>:S, <2>:e, <3>:v}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(3))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_4() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:T, <2>:h, <3>:r, <4>:e}, "
//                    + "{<1>:F, <2>:i, <3>:v, <4>:e}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e}, "
//                    + "{<1>:E, <2>:l, <3>:e, <4>:v}, "
//                    + "{<1>:T, <2>:h, <3>:i, <4>:r}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_5() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:T, <2>:h, <3>:r, <4>:e, <5>:e}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}, "
//                    + "{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e}, "
//                    + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_6() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e, <6>:n}, "
//                    + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_7() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(7))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5),
//                            "<7>", $S.charAt(6))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_8() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e, <8>:n}, "
//                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(8))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5),
//                            "<7>", $S.charAt(6),
//                            "<8>", $S.charAt(7))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_9() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
//            assertStrings(
//                    "[{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e, <9>:n}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(9))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5),
//                            "<7>", $S.charAt(6),
//                            "<8>", $S.charAt(7),
//                            "<9>", $S.charAt(8))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_10() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
//            assertStrings(
//                    "[{<10>:r, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(10))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5),
//                            "<7>", $S.charAt(6),
//                            "<8>", $S.charAt(7),
//                            "<9>", $S.charAt(8),
//                            "<10>", $S.charAt(9))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToMap_11() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
//            assertStrings(
//                    "[{<10>:r, <11>:e, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(11))
//                    .mapToMap(
//                            "<1>", $S.charAt(0),
//                            "<2>", $S.charAt(1),
//                            "<3>", $S.charAt(2),
//                            "<4>", $S.charAt(3),
//                            "<5>", $S.charAt(4),
//                            "<6>", $S.charAt(5),
//                            "<7>", $S.charAt(6),
//                            "<8>", $S.charAt(7),
//                            "<9>", $S.charAt(8),
//                            "<10>", $S.charAt(9),
//                            "<11>", $S.charAt(10))
//                    .map(map -> map.sorted())
//                    );
//        });
//    }
//    
//    //-- FuncListWithMapToTuple --
//    
//    @Test
//    public void testMapToTuple_2() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(O,n), (T,h), (F,i), (S,e), (E,l)]",
//                    list
//                        .filter($S.length().thatGreaterThanOrEqualsTo(2))
//                        .mapToTuple($S.charAt(0), $S.charAt(1))
//                        );
//        });
//    }
//    
//    @Test
//    public void testMapToTuple_3() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(O,n,e), (T,h,r), (F,i,v), (S,e,v), (E,l,e)]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(3))
//                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2))
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToTuple_4() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(T,h,r,e), (F,i,v,e), (S,e,v,e), (E,l,e,v)]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3))
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToTuple_5() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(T,h,r,e,e), (S,e,v,e,n), (E,l,e,v,e)]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4))
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToTuple_6() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(E,l,e,v,e,n)]",
//                    list
//                    .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), $S.charAt(5))
//                    );
//        });
//    }
//    
//    //-- StreamPlusWithMapWithIndex --
//    
//    @Test
//    public void testMapWithIndex() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[(0,One), (1,Three), (2,Five), (3,Seven), (4,Eleven)]",
//                    list
//                    .mapWithIndex()
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapWithIndex_combine() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
//                    list
//                    .mapWithIndex((i, each) -> i + ": " + each)
//                    );
//        });
//    }
//    
//    @Test
//    public void testMapToObjWithIndex_combine() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
//                list
//                .mapToObjWithIndex((i, each) -> i + ": " + each)
//                );
//        });
//    }
//    
//    @Test
//    public void testMapWithIndex_map_combine() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
//                list
//                .mapWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
//                );
//        });
//    }
//    
//    @Test
//    public void testMapToObjWithIndex_map_combine() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
//                list
//                .mapToObjWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
//                );
//        });
//    }
//    
//    //-- FuncListWithModify --
//    
//    @Test
//    public void testAccumulate1() {
//        run(IntFuncList.of(1, 2, 3, 4, 5), list -> {
//            assertStrings(
//                    "1, 3, 6, 10, 15",
//                    list
//                        .accumulate((a, b)->a+b)
//                        .join(", "));
//        });
//    }
//    
//    @Test
//    public void testAccumulate2() {
//        run(IntFuncList.of(1, 2, 3, 4, 5), list -> {
//            assertStrings(
//                    "1, 12, 123, 1234, 12345",
//                    list
//                        .accumulate((prev, current)->prev*10 + current)
//                        .join(", "));
//        });
//    }
//    
//    @Test
//    public void testRestate1() {
//        run(IntFuncList.wholeNumbers(20).map(i -> i % 5).toFuncList(), list -> {
//            assertStrings(
//                    "0, 1, 2, 3, 4",
//                  list
//                      .restate((a, s)->s.filter(x -> x != a))
//                      .join   (", "));
//        });
//    }
//    
//    // sieve of eratosthenes
//    @Test
//    public void testRestate2() {
//        run(IntFuncList.wholeNumbers(1000).skip(2).boxed().toFuncList(), list -> {
//            assertStrings(
//                    "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, "
//                  + "31, 37, 41, 43, 47, 53, 59, 61, 67, 71, "
//                  + "73, 79, 83, 89, 97, 101, 103, 107, 109, 113, "
//                  + "127, 131, 137, 139, 149, 151, 157, 163, 167, 173, "
//                  + "179, 181, 191, 193, 197, 199, 211, 223, 227, 229, "
//                  + "233, 239, 241, 251, 257, 263, 269, 271, 277, 281",
//                  list
//                      .restate((a, s)->s.filter(x -> x % a != 0))
//                      .limit(60)
//                      .join (", "));
//        });
//    }
//    
//    @Test
//    public void testSpawn() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val timePrecision = 100;
//            val first  = new AtomicLong(-1);
//            val logs   = new ArrayList<String>();
//            list
//            .spawn(str -> Sleep(str.length()*timePrecision + 5).thenReturn(str).defer())
//            .forEach(element -> {
//                first.compareAndSet(-1, System.currentTimeMillis());
//                val start    = first.get();
//                val end      = System.currentTimeMillis();
//                val duration = Math.round((end - start)/(1.0 * timePrecision))*timePrecision;
//                logs.add(element + " -- " + duration);
//            });
//            assertEquals("["
//                    + "Result:{ Value: Two } -- 0, "
//                    + "Result:{ Value: Four } -- " + (1*timePrecision) + ", "
//                    + "Result:{ Value: Three } -- " + (2*timePrecision) + ", "
//                    + "Result:{ Value: Eleven } -- " + (3*timePrecision) + ""
//                    + "]",
//                    logs.toString());
//        });
//    }
//    
//    @Test
//    public void testSpawn_limit() {
//        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
//            val first   = new AtomicLong(-1);
//            val actions = new ArrayList<DeferAction<String>>();
//            val logs    = new ArrayList<String>();
//            list
//            .spawn(str -> {
//                DeferAction<String> action = Sleep(str.length()*50 + 5).thenReturn(str).defer();
//                actions.add(action);
//                return action;
//            })
//            .limit(1)
//            .forEach(element -> {
//                first.compareAndSet(-1, System.currentTimeMillis());
//                val start    = first.get();
//                val end      = System.currentTimeMillis();
//                val duration = Math.round((end - start)/50.0)*50;
//                logs.add(element + " -- " + duration);
//            });
//            assertEquals("[Result:{ Value: Two } -- 0]",
//                    logs.toString());
//            assertEquals(
//                    "Result:{ Value: Two }, " +
//                    "Result:{ Cancelled: Stream closed! }, " +
//                    "Result:{ Cancelled: Stream closed! }, " +
//                    "Result:{ Cancelled: Stream closed! }",
//                    actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
//        });
//    }
//    //-- FuncListWithPeek --
//    
//    @Test
//    public void testPeekClass() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            val elementStrings = new ArrayList<String>();
//            val elementIntegers = new ArrayList<Integer>();
//            list
//                .peek(String.class,  elementStrings::add)
//                .peek(Integer.class, elementIntegers::add)
//                .join() // To terminate the stream
//                ;
//            assertStrings("[One, Three, Five]", elementStrings);
//            assertStrings("[0, 2, 4]", elementIntegers);
//        });
//    }
//    
//    @Test
//    public void testPeekBy() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            val elementStrings = new ArrayList<String>();
//            val elementIntegers = new ArrayList<Integer>();
//            list
//                .peekBy(String.class::isInstance,  e -> elementStrings.add((String)e))
//                .peekBy(Integer.class::isInstance, e -> elementIntegers.add((Integer)e))
//                .join() // To terminate the stream
//                ;
//            assertStrings("[One, Three, Five]", elementStrings);
//            assertStrings("[0, 2, 4]", elementIntegers);
//        });
//    }
//    
//    @Test
//    public void testPeekAs() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            val elementStrings = new ArrayList<String>();
//            list
//                .peekAs(e -> "<" + e + ">", e -> elementStrings.add((String)e))
//                .join() // To terminate the stream
//                ;
//            assertStrings("[<0>, <One>, <2>, <Three>, <4>, <Five>]", elementStrings);
//        });
//    }
//    
//    @Test
//    public void testPeekBy_map() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            val elementStrings = new ArrayList<String>();
//            list
//                .peekBy(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add("" + e))
//                .join() // To terminate the stream
//                ;
//            assertStrings("[0, One, 2, Three, 4]", elementStrings);
//        });
//    }
//    
//    @Test
//    public void testPeekAs_map() {
//        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
//            val elementStrings = new ArrayList<String>();
//            list
//                .peekAs(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add((String)e))
//                .join() // To terminate the stream
//                ;
//            assertStrings("[<0>, <One>, <2>, <Three>, <4>]", elementStrings);
//        });
//    }
//    
//    //-- FuncListWithPipe --
//    
//    @Test
//    public void testPipeable() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[One, Three, Five, Seven, Eleven]",
//                    list
//                        .pipable()
//                        .pipeTo(FuncList::toListString));
//        });
//    }
//    
//    @Test
//    public void testPipe() {
//        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[One, Three, Five, Seven, Eleven]",
//                    list.pipe(FuncList::toListString));
//        });
//    }
    
    
    //-- FuncListWithReshape --
    
    @Test
    public void testSegmentSize() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]",
                    list
                    .segmentSize(6)
                    .map        (IntStreamPlus::toListString)
                    .join       (", ")
            );
        });
    }
    
    @Test
    public void testSegmentSize_excludeTail() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17]",
                    list
                    .segmentSize(6, false)
                    .map        (IntStreamPlus::toListString)
                    .join       (", ")
            );
        });
    }
    
    @Test
    public void testSegmentSize_includeIncomplete() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]",
                    list
                    .segmentSize(6, IncompletedSegment.included)
                    .map        (IntStreamPlus::toListString)
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentSize_excludeIncomplete() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17]",
                    list
                    .segmentSize(6, IncompletedSegment.excluded)
                    .map        (IntStreamPlus::toListString)
                    .join       (", ")
                );
        });
    }
    
    @Test
    public void testSegmentSize_function() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[], " +
                    "[1], " +
                    "[2, 3], " +
                    "[4, 5, 6, 7], " +
                    "[8, 9, 10, 11, 12, 13, 14, 15], " +
                    "[16, 17, 18, 19]",
                    list
                    .segmentSize(i -> i)
                    .map        (IntStreamPlus::toListString)
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentStartCondition() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    list
                    .segment(theInteger.thatIsDivisibleBy(3))
                    .map    (IntStreamPlus::toListString)
                    .join   (", ")
            );
        });
    }
    
    @Test
    public void testSegmentStartCondition_includeIncomplete() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    list
                    .segment(theInteger.thatIsDivisibleBy(3), IncompletedSegment.included)
                    .map    (IntStreamPlus::toListString)
                    .join   (", ")
                    );
            
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    list
                    .segment(theInteger.thatIsDivisibleBy(3), true)
                    .map    (IntStreamPlus::toListString)
                    .join   (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentStartCondition_excludeIncomplete() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17]",
                    list
                    .segment(theInteger.thatIsDivisibleBy(3), IncompletedSegment.excluded)
                    .map    (IntStreamPlus::toListString)
                    .join   (", ")
                    );
            
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17]",
                    list
                    .segment(theInteger.thatIsDivisibleBy(3), false)
                    .map    (IntStreamPlus::toListString)
                    .join   (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentCondition() {
        IntPredicate startCondition = i ->(i % 10) == 3;
        IntPredicate endCondition   = i ->(i % 10) == 6;
        
        run(IntFuncList.wholeNumbers(100), list -> {
            assertStrings("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      list
                      .segment(startCondition, endCondition)
                      .skip   (5)
                      .limit  (3)
                      .map    (IntStreamPlus::toListString)
                      );
            
            assertStrings("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      list
                      .segment(startCondition, endCondition, true)
                      .skip   (5)
                      .limit  (3)
                      .map    (IntStreamPlus::toListString)
                      );
            
            assertStrings("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      list
                      .segment(startCondition, endCondition, false)
                      .skip   (5)
                      .limit  (3)
                      .map    (IntStreamPlus::toListString)
                      );
            
            assertStrings("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                          list
                          .segment(startCondition, endCondition, IncompletedSegment.included)
                          .skip   (5)
                          .limit  (3)
                          .map    (IntStreamPlus::toListString)
                          );
            
            assertStrings("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                          list
                          .segment(startCondition, endCondition, IncompletedSegment.excluded)
                          .skip   (5)
                          .limit  (3)
                          .map    (IntStreamPlus::toListString));
        });
    }
    
    @Test
    public void testCollapse() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 6), list -> {
            // Because 3 and 6 do match the condition to collapse ... so they are merged with the one before them.
            assertStrings(
                    "[1, 5, 4, 11]",
                    list.collapseWhen(
                            i -> (i % 3) == 0,
                            (a,b)->a+b
                        ));
            
            assertStrings(
                    "[1, 2, 7, 5, 6]",
                    list.collapseWhen(
                            i -> (i % 3) == 1,
                            (a,b)->a+b
                        ));
            
            assertStrings(
                    "[1, 9, 11]",
                    list.collapseWhen(
                            i -> (i % 3) <= 1,
                            (a,b)->a+b
                        ));
        });
    }
    
//    @Test
//    public void testCollapseSize() {
//        run(IntFuncList.wholeNumbers(20), list -> {
//            assertEquals(
//                    "1, 5, 22, 92, 70",
//                    list.collapseSize(
//                            i -> i,
//                            (a,b)->a+b
//                        ).join(", "));
//                    
//            assertEquals(
//                    "1, 2-3, 4-5-6-7, 8-9-10-11-12-13-14-15, 16-17-18-19",
//                    list.collapseSize(
//                            i -> i,
//                            i -> i*1000,
//                            (a,b)-> a + b
//                        ).join(", "));
//            
//            assertEquals(
//                    "1, 2-3, 4-5-6-7, 8-9-10-11-12-13-14-15, 16-17-18-19",
//                    list.collapseSizeToObj(
//                            i -> i,
//                            i -> "" + i*1000,
//                            (a,b)-> a + "-" + b
//                        ).join(", "));
//        });
//    }
//    
//    @Test
//    public void testSegmentByPercentiles() {
//        run(IntFuncList.wholeNumbers(50), list -> {
//            assertStrings(
//                    "[" +
//                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
//                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
//                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
//                    "]", list.segmentByPercentiles(30,   80));
//            assertStrings(
//                    "[" +
//                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
//                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
//                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
//                    "]", list.segmentByPercentiles(30.0, 80.0));
//            assertStrings(
//                    "[" +
//                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
//                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
//                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
//                    "]", list.segmentByPercentiles(IntFuncList   .of(30,   80)));
//            assertStrings(
//                    "[" +
//                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
//                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
//                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
//                    "]", list.segmentByPercentiles(DoubleFuncList.of(30.0, 80.0)));
//        });
//    }
//    
//    @Test
//    public void testSegmentByPercentiles_mapper() {
//        run(IntFuncList.wholeNumbers(50), list -> {
//            assertStrings(
//                    "["
//                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
//                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
//                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, 30, 80));
//            assertStrings(
//                    "["
//                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
//                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
//                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, 30.0, 80.0));
//            assertStrings(
//                    "["
//                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
//                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
//                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, IntFuncList   .of(30,   80)));
//            assertStrings(
//                    "["
//                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
//                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
//                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30.0, 80.0)));
//        });
//    }
//    
//    @Test
//    public void testSegmentByPercentiles_mapper_comparator() {
//        run(IntFuncList.wholeNumbers(50), list -> {
//            assertStrings(
//                    "["
//                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
//                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
//                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30, 80));
//            assertStrings(
//                    "["
//                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
//                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
//                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30.0, 80.0));
//            assertStrings(
//                    "["
//                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
//                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
//                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, IntFuncList   .of(30,   80)));
//            assertStrings(
//                    "["
//                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
//                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
//                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
//                    + "]",
//                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, DoubleFuncList.of(30.0, 80.0)));
//        });
//    }
    
    //-- FuncListWithSort --
    
    @Test
    public void testSortedBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("[1, 2, 3, 4]", list.sortedBy(i -> i*2));
            assertStrings("[4, 3, 2, 1]", list.sortedBy(i -> 5 - i));
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1, 2, 3, 4]",
                    list.sortedBy(i -> 5 - i, (a,b)->b-a));
        });
    }
    
    //-- FuncListWithSplit --
    
    @Test
    public void testSplitTuple() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "("
                    + "[0, 2, 4, 6, 8, 10, 12, 14, 16, 18],"
                    + "[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]"
                    + ")",
                     list
                    .split(theInteger.thatIsDivisibleBy(2))
                    .toString());
        });
    }
    
    @Test
    public void testSplit() {
        run(IntFuncList.wholeNumbers(20), list -> {
            int Other = 1;
            assertStrings(
                    "{"
                    + "1:[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], "
                    + "2:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split(Two,  theInteger.thatIsDivisibleBy(2),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "1:[1, 5, 7, 11, 13, 17, 19], "
                    + "2:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
                    + "3:[3, 9, 15]"
                    + "}",
                     list
                    .split(Two,   theInteger.thatIsDivisibleBy(2),
                           Three, theInteger.thatIsDivisibleBy(3),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "1:[1, 7, 11, 13, 17, 19], "
                    + "2:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
                    + "3:[3, 9, 15], "
                    + "5:[5]"
                    + "}",
                     list
                    .split(Two,   theInteger.thatIsDivisibleBy(2),
                           Three, theInteger.thatIsDivisibleBy(3),
                           Five,  theInteger.thatIsDivisibleBy(5),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "1:[1, 11, 13, 17, 19], "
                    + "2:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
                    + "3:[3, 9, 15], "
                    + "5:[5], "
                    + "7:[7]"
                    + "}",
                     list
                    .split(Two,    theInteger.thatIsDivisibleBy(2),
                           Three,  theInteger.thatIsDivisibleBy(3),
                           Five,   theInteger.thatIsDivisibleBy(5),
                           Seven,  theInteger.thatIsDivisibleBy(7),
                           Other)
                    .toString());
            assertStrings(
                    "{"
                    + "1:[1, 13, 17, 19], "
                    + "2:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
                    + "3:[3, 9, 15], "
                    + "5:[5], "
                    + "7:[7], "
                    + "11:[11]"
                    + "}",
                     list
                    .split(Two,    theInteger.thatIsDivisibleBy(2),
                           Three,  theInteger.thatIsDivisibleBy(3),
                           Five,   theInteger.thatIsDivisibleBy(5),
                           Seven,  theInteger.thatIsDivisibleBy(7),
                           Eleven, theInteger.thatIsDivisibleBy(11),
                           Other)
                    .sorted()
                    .toString());
        });
    }
    
    @Ignore("Will need to get the changes from FuncList")
    @Test
    public void testFizzBuzz() {
        Function<IntFuncList, IntFuncList> listToList = s -> s.toImmutableList();
        run(IntFuncList.wholeNumbers(20), list -> {
            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap))
            .run(() -> {
                FuncMap<String, IntFuncList> splited
                        = list
                        .split(
                            "FizzBuzz", i -> i % (3*5) == 0,
                            "Buzz",     i -> i % 5     == 0,
                            "Fizz",     i -> i % 3     == 0,
                            null);
                val string
                        = splited
                        .mapValue(listToList)
                        .toString();
                return string;
            });
            assertEquals("{"
                    + "FizzBuzz:[0, 15], "
                    + "Buzz:[5, 10], "
                    + "Fizz:[3, 6, 9, 12, 18]"
                    + "}",
                    toString);
        });
    }
    
}
