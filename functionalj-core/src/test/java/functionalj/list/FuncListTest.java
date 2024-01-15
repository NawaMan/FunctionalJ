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
package functionalj.list;

import static functionalj.TestHelper.assertAsString;
import static functionalj.function.Func.F;
import static functionalj.functions.StrFuncs.join;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTest.Car.theCar;
import static functionalj.list.FuncList.listOf;
import static functionalj.list.FuncList.newBuilder;
import static functionalj.list.FuncList.newListBuilder;
import static functionalj.ref.Run.With;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static java.util.Arrays.asList;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.function.IntFunctionPrimitive;
import functionalj.function.aggregator.Aggregation;
import functionalj.lens.LensTest.Car;
import functionalj.list.FuncList.Mode;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.stream.collect.CollectorToIntPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class FuncListTest {
    
    static final String MinusOne = "MinusOne";
    
    static final String Zero = "Zero";
    
    static final String One = "One";
    
    static final String Two = "Two";
    
    static final String Three = "Three";
    
    static final String Four = "Four";
    
    static final String Five = "Five";
    
    static final String Six = "Six";
    
    static final String Seven = "Seven";
    
    static final String Eight = "Eight";
    
    static final String Nine = "Nine";
    
    static final String Ten = "Ten";
    
    static final String Eleven = "Eleven";
    
    static final String Twelve = "Twelve";
    
    static final String Thirteen = "Thirteen";
    
    static final String Seventeen = "Seventeen";
    
    static final String Nineteen = "Nineteen";
    
    static final String TwentyThree = "Twenty-three";
    
    private <T> void run(FuncList<T> list, FuncUnit1<FuncList<T>> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private <T> void runExpectReadOnlyListException(FuncList<T> list, FuncUnit1<FuncList<T>> action) {
        try {
            action.accept(list);
            fail("Exception ReadOnlyListException");
        } catch (ReadOnlyListException e) {
        }
    }
    
    private <T> void run(IntFuncList list, FuncUnit1<IntFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private <T> void run(DoubleFuncList list, FuncUnit1<DoubleFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private <T> void run(FuncList<T> list1, FuncList<T> list2, FuncUnit2<FuncList<T>, FuncList<T>> action) {
        action.accept(list1, list2);
        action.accept(list1, list2);
    }
    
    @Test
    public void testEmpty() {
        run(FuncList.empty(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList() {
        run(FuncList.emptyList(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmpty_withClass() {
        run(FuncList.empty(String.class), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList_withClass() {
        run(FuncList.emptyList(String.class), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testOf() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testAllOf() {
        run(FuncList.AllOf(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testListOf() {
        run(FuncList.ListOf(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.listOf(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testFrom_array() {
        run(FuncList.from(new String[] { One, Two, Three }), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.from(new int[] { 1, 2, 3 }), list -> {
            assertAsString("[1, 2, 3]", list);
        });
        run(FuncList.from(new double[] { 1.0, 2.0, 3.0 }), list -> {
            assertAsString("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<String> collection = Arrays.asList(One, Two, Three);
        run(FuncList.from(collection), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        Set<String> set = new LinkedHashSet<>(collection);
        run(FuncList.from(set), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        FuncList<String> lazyList = FuncList.of(One, Two, Three);
        run(FuncList.from(lazyList), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("lazy", list.mode());
        });
        FuncList<String> eagerList = FuncList.of(One, Two, Three).toEager();
        run(FuncList.from(eagerList), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("eager", list.mode());
        });
    }
    
    @Test
    public void testFrom_javaList() {
        List<String> javaList = Arrays.asList(One, Two, Three);
        run(FuncList.from(javaList), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testFrom_funcList() {
        run(FuncList.from(FuncList.of(One, Two, Three)), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.from(FuncList.of(One, Two, Three)), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.from(Mode.lazy, FuncList.of(One, Two, Three)), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("lazy", list.mode());
        });
        run(FuncList.from(Mode.eager, FuncList.of(One, Two, Three)), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("eager", list.mode());
        });
        run(FuncList.from(Mode.cache, FuncList.of(One, Two, Three)), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("cache", list.mode());
        });
    }
    
    @Test
    public void testFrom_FuncList_infinite() {
        run(FuncList.from(FuncList.loop()), list -> {
            assertAsString("[null, null, null, null, null]", list.skip(100000).limit(5));
        });
        run(FuncList.from(IntFuncList.wholeNumbers().boxed()), list -> {
            assertAsString("[100000, 100001, 100002, 100003, 100004]", list.skip(100000).limit(5));
        });
    }
    
    @Ignore("Taking too long - run manually as needed.")
    @Test
    public void testFrom_FuncList_infinite_toList() {
        run(FuncList.from(FuncList.loop()), list -> {
            try {
                list.toList();
                fail("Expect an exception.");
            } catch (OutOfMemoryError e) {
            }
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(FuncList.from(FuncList.of(One, Two, Three).streamPlus().stream()), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testFrom_list() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testNulls() {
        run(FuncList.nulls().limit(5), list -> {
            assertAsString("[null, null, null, null, null]", list);
            assertAsString("[null, 5, null, null, null]", list.with(1, 5));
            assertAsString("[null, 5, Five, null, null]", list.with(1, 5).with(2, "Five"));
        });
        run(FuncList.nulls(Integer.class).limit(5), list -> {
            assertAsString("[null, null, null, null, null]", list);
            assertAsString("[null, 5, null, null, null]", list.with(1, 5));
            // This line will hit compilation error because the class is specified as Integer
            // assertAsString("[null, null, null, null, null]", list.with(1, 5).with(2, "Five"));
        });
    }
    
    @Test
    public void testRepeat() {
        run(FuncList.repeat(0, 42), list -> {
            assertAsString("[0, 42, 0, 42, 0]", list.limit(5));
            assertAsString("[0, 42, 0, 42, 0, 42, 0]", list.limit(7));
        });
        run(FuncList.repeat(FuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]", list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testCycle() {
        run(FuncList.cycle(0, 1, 42), list -> {
            assertAsString("[0, 1, 42, 0, 1]", list.limit(5));
            assertAsString("[0, 1, 42, 0, 1, 42, 0]", list.limit(7));
        });
        run(FuncList.cycle(FuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]", list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testLoop() {
        run(FuncList.loop(), list -> assertAsString("[null, null, null, null, null]", list.limit(5)));
        run(FuncList.loop(5), list -> assertAsString("[null, null, null, null, null]", list));
        run(FuncList.infiniteInt(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
    }
    
    @Test
    public void testEquals() {
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertTrue(Objects.equals(list1, list2));
            assertEquals(list1, list2);
        });
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertFalse(Objects.equals(list1, list2));
            assertNotEquals(list1, list2);
        });
        // Make it a derived list
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertEquals(list1, list2);
        });
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertNotEquals(list1, list2);
        });
    }
    
    @Test
    public void testHashCode() {
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertEquals(list1.hashCode(), list2.hashCode());
        });
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertNotEquals(list1.hashCode(), list2.hashCode());
        });
        // Make it a derived list
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertEquals(list1.hashCode(), list2.hashCode());
        });
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertNotEquals(list1.hashCode(), list2.hashCode());
        });
    }
    
    @Test
    public void testToString() {
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertEquals(list1.toString(), list2.toString());
        });
        run(FuncList.of(One, Two, Three), FuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableFuncList);
            assertTrue(list2 instanceof ImmutableFuncList);
            assertNotEquals(list1.toString(), list2.toString());
        });
        // Make it a derived list
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertEquals(list1.toString(), list2.toString());
        });
        run(FuncList.of(One, Two, Three).map(value -> value), FuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof FuncListDerived);
            assertTrue(list2 instanceof FuncListDerived);
            assertNotEquals(list1.toString(), list2.toString());
        });
    }
    
    @Test
    public void testConcat() {
        run(FuncList.concat(FuncList.of(One, Two), FuncList.of(Three, Four)), list -> {
            assertAsString("[One, Two, Three, Four]", list);
        });
    }
    
    @Test
    public void testCombine() {
        run(FuncList.combine(FuncList.of(One, Two), FuncList.of(Three, Four)), list -> {
            assertAsString("[One, Two, Three, Four]", list);
        });
    }
    
    // -- Generate --
    @Test
    public void testGenerate() {
        run(FuncList.generateWith(() -> {
            val counter = new AtomicInteger();
            Supplier<Integer> supplier = () -> counter.getAndIncrement();
            return supplier;
        }), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list.limit(5));
        });
        run(FuncList.generateWith(() -> {
            val counter = new AtomicInteger();
            Supplier<Integer> supplier = () -> {
                int count = counter.getAndIncrement();
                if (count < 5)
                    return count;
                return FuncList.noMoreElement();
            };
            return supplier;
        }), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list);
        });
    }
    
    // -- Iterate --
    @Test
    public void testIterate() {
        run(FuncList.iterate(1, (i) -> 2 * (i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(FuncList.iterate(1, 2, (a, b) -> a + b), list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]", list.limit(10)));
    }
    
    // -- Compound --
    @Test
    public void testCompound() {
        run(FuncList.compound(1, (i) -> 2 * (i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(FuncList.compound(1, 2, (a, b) -> a + b), list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]", list.limit(10)));
    }
    
    // -- zipOf --
    @Test
    public void testZipOf_toTuple() {
        run(FuncList.of("A", "B", "C", "D", "E"), FuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[(A,1), (B,2), (C,3), (D,4)]", FuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(FuncList.of("A", "B", "C", "D", "E"), FuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[A+1, B+2, C+3, D+4]", FuncList.zipOf(list1, list2, (a, b) -> a + "+" + b));
        });
    }
    
    @Test
    public void testZipOf_merge_int() {
        run(FuncList.zipOf(IntFuncList.of(1, 2, 3, 4, 5), IntFuncList.of(5, 4, 3, 2, 1), (a, b) -> a * b), list -> {
            assertAsString("[5, 8, 9, 8, 5]", list);
        });
    }
    
    @Test
    public void testZipOf_merge_int_obj() {
        run(FuncList.zipOf(IntFuncList.of(1, 2, 3, 4), FuncList.of(Five, Four, Three, Two, One), (a, b) -> "" + a + "*" + b), list -> {
            assertAsString("[1*Five, 2*Four, 3*Three, 4*Two]", list);
        });
    }
    
    @Test
    public void testZipOf_merge_double() {
        run(FuncList.zipOf(DoubleFuncList.of(1.0, 2.0, 3.0, 4.0, 5.0), DoubleFuncList.of(5.0, 4.0, 3.0, 2, 1.0), (a, b) -> a * b), list -> {
            assertAsString("[5.0, 8.0, 9.0, 8.0, 5.0]", list);
        });
    }
    
    @Test
    public void testZipOf_merge_double_obj() {
        run(FuncList.zipOf(DoubleFuncList.of(1, 2, 3, 4), FuncList.of(Five, Four, Three, Two, One), (a, b) -> "" + a + "*" + b), list -> {
            assertAsString("[1.0*Five, 2.0*Four, 3.0*Three, 4.0*Two]", list);
        });
    }
    
    @Test
    public void testNew() {
        FuncListBuilder<String> funcList1 = FuncList.newListBuilder();
        FuncListBuilder<String> funcList2 = FuncList.newBuilder();
        run(funcList1.add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(funcList2.add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(newListBuilder().add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(newBuilder().add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.newListBuilder(String.class).add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
        run(FuncList.newBuilder(String.class).add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    // -- Derive --
    @Test
    public void testDeriveFrom() {
        run(FuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.map(v -> "-" + v + "-")), list -> {
            assertAsString("[-One-, -Two-, -Three-]", list);
        });
        run(FuncList.deriveFrom(IntFuncList.of(1, 2, 3), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertAsString("[-1-, -2-, -3-]", list);
        });
        run(FuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertAsString("[-1.0-, -2.0-, -3.0-]", list);
        });
    }
    
    @Test
    public void testDeriveTo() {
        run(FuncList.deriveToObj(FuncList.of(One, Two, Three), s -> s.map(v -> "-" + v + "-")), list -> {
            assertTrue(list instanceof FuncList);
            assertAsString("[-One-, -Two-, -Three-]", list);
        });
        run(FuncList.deriveToInt(FuncList.of(1, 2, 3), s -> s.mapToInt(v -> v + 5)), list -> {
            assertAsString("[6, 7, 8]", list);
        });
        run(FuncList.deriveToDouble(FuncList.of(1.0, 2.0, 3.0), s -> s.mapToDouble(v -> 3.0 * v)), list -> {
            assertAsString("[3.0, 6.0, 9.0]", list);
        });
    }
    
    // -- Predicate --
    @Test
    public void testTest_predicate() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.test(One));
            assertTrue(list.test(Two));
            assertTrue(list.test(Three));
            assertFalse(list.test(Four));
            assertFalse(list.test(Five));
            assertFalse(list.test(Six));
        });
    }
    
    //-- IntFunction --
    @Test
    public void testIntFunctionApply() {
        run(FuncList.of(One, Two, Three), list -> {
            val func = (IntFunctionPrimitive<String>)list;
            assertEquals(One,   func.apply(0));
            assertEquals(Two,   func.apply(1));
            assertEquals(Three, func.apply(2));
            
            try {
                func.apply(3);
                fail("Except an excaption.");
            } catch (IndexOutOfBoundsException e) {
                assertAsString("java.lang.IndexOutOfBoundsException: Index: 3, Size: 3", e);
            }
            
            ;
            assertEquals("",  func.whenAbsentUse("").apply(3));
        });
    }
    
    // -- Eager+Lazy --
    @Test
    public void testIsEagerIsLazy() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.toLazy().mode().isLazy());
            assertTrue(list.toEager().mode().isEager());
            assertTrue(list.toLazy().freeze().mode().isLazy());
            val logs = new ArrayList<String>();
            FuncList<String> lazyList = list.peek(value -> logs.add("" + value));
            // ForEach but do nothing
            lazyList.forEach(value -> {
            });
            assertEquals("[One, Two, Three]", logs.toString());
            // Lazy list will have to be re-evaluated again so the logs double.
            lazyList.forEach(value -> {
            });
            assertEquals("[One, Two, Three, One, Two, Three]", logs.toString());
            logs.clear();
            assertEquals("[]", logs.toString());
            // Freeze but still lazy
            FuncList<String> frozenList = list.freeze().peek(value -> logs.add("" + value));
            // ForEach but do nothing
            frozenList.forEach(value -> {
            });
            assertEquals("[One, Two, Three]", logs.toString());
            // Freeze list but still lazy so it will have to be re-evaluated again so the logs double
            frozenList.forEach(value -> {
            });
            assertEquals("[One, Two, Three, One, Two, Three]", logs.toString());
            // Eager list
            logs.clear();
            FuncList<String> eagerList = list.toEager().peek(value -> logs.add("" + value));
            // ForEach but do nothing
            eagerList.forEach(value -> {
            });
            assertEquals("[One, Two, Three]", logs.toString());
            // Eager list does not re-evaluate so the log stay the same.
            eagerList.forEach(value -> {
            });
            assertEquals("[One, Two, Three]", logs.toString());
        });
    }
    
    @Test
    public void testEagerLazy() {
        {
            val logs = new ArrayList<String>();
            // We want to confirm that the list is lazy
            val list = FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(logs::add).toFuncList();
            // The function has not been materialized so nothing goes through peek.
            assertAsString("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertAsString("[One, Two, Three, Four, Five]", list.limit(5));
            assertAsString("[One, Two, Three, Four, Five]", logs);
        }
        {
            val logs = new ArrayList<String>();
            // We want to confirm that the list is eager
            val list = FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(logs::add).toFuncList().toEager();
            // The function has been materialized so all element goes through peek.
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", logs);
            // Even we only get part of it,
            assertAsString("[One, Two, Three, Four, Five]", list.limit(5));
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", logs);
        }
    }
    
    @Test
    public void testEagerLazy_more() {
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val logs3 = new ArrayList<String>();
            val orgData = FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData.toLazy().peek(logs1::add).map(theString.length()).peek(v -> logs2.add("" + v)).exclude(theInteger.thatLessThanOrEqualsTo(3)).peek(v -> logs3.add("" + v));
            // The list has not been materialized so nothing goes through peek.
            assertAsString("[]", logs1);
            assertAsString("[]", logs2);
            assertAsString("[]", logs3);
            // Get part of them so those peek will goes through the peek
            assertAsString("[5, 4, 4, 5, 5]", list.limit(5));
            // Now that the list has been materialize all the element has been through the logs
            // The first log has all the original word until there are 5 elements that are longer than 3 characters.
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight]", logs1);
            // 1      2     3          4      5
            // The second log captures all the length until 5 of them that are longer than 3 characters.
            assertAsString("[3, 3, 5, 4, 4, 3, 5, 5]", logs2);
            // The third log captures only the length that is longer than 3.
            assertAsString("[5, 4, 4, 5, 5]", logs3);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val logs3 = new ArrayList<String>();
            val orgData = FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData.toEager().peek(logs1::add).map(theString.length()).peek(v -> logs2.add("" + v)).exclude(theInteger.thatLessThanOrEqualsTo(3)).peek(v -> logs3.add("" + v));
            // Since the list is eager, all the value pass through all peek all the time
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", logs1);
            assertAsString("[3, 3, 5, 4, 4, 3, 5, 5, 4, 3]", logs2);
            assertAsString("[5, 4, 4, 5, 5, 4]", logs3);
            // Get part of them so those peek will goes through the peek
            assertAsString("[5, 4, 4, 5, 5]", list.limit(5));
            // No more passing through the log stay still
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", logs1);
            assertAsString("[3, 3, 5, 4, 4, 3, 5, 5, 4, 3]", logs2);
            assertAsString("[5, 4, 4, 5, 5, 4]", logs3);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testCache() {
        val random = new Random();
        val list = FuncList.from((Supplier) (() -> IntStreamPlus.infiniteInt().limit(100).mapToObj(__ -> random.nextInt(1000))));
        val cacheList = list.toCache();
        assertNotEquals(list.limit(5), list.limit(5));
        assertEquals(cacheList.limit(5), cacheList.limit(5));
    }
    
    // -- List --
    @Test
    public void testToFuncList() {
        run(FuncList.of(One, Two, Three), list -> {
            val funcList = list.toFuncList();
            assertAsString("[One, Two, Three]", funcList.toString());
            assertTrue(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(FuncList.of(One, Two, Three), list -> {
            val funcList = list.toJavaList();
            assertAsString("[One, Two, Three]", funcList);
            assertFalse(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(FuncList.of(One, Two, Three), list -> {
            val funcList = list.toImmutableList();
            assertAsString("[One, Two, Three]", funcList);
            assertTrue(funcList instanceof ImmutableFuncList);
            assertAsString("[One, Two, Three]", funcList.map(value -> value).toImmutableList());
            assertTrue(funcList instanceof ImmutableFuncList);
        });
    }
    
    @Test
    public void testIterable() {
        run(FuncList.of(One, Two, Three), list -> {
            val iterator = list.iterable().iterator();
            assertTrue(iterator.hasNext());
            assertTrue(One.equals(iterator.next()));
            assertTrue(iterator.hasNext());
            assertTrue(Two.equals(iterator.next()));
            assertTrue(iterator.hasNext());
            assertTrue(Three.equals(iterator.next()));
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testIterator() {
        run(FuncList.of(One, Two, Three), list -> {
            val iterator = list.iterator();
            assertTrue(iterator.hasNext());
            assertTrue(One.equals(iterator.next()));
            assertTrue(iterator.hasNext());
            assertTrue(Two.equals(iterator.next()));
            assertTrue(iterator.hasNext());
            assertTrue(Three.equals(iterator.next()));
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testSpliterator() {
        run(FuncList.of(One, Two, Three), list -> {
            Spliterator<String> spliterator = list.spliterator();
            Stream<String> stream = StreamSupport.stream(spliterator, false);
            StreamPlus<String> streamPlus = StreamPlus.from(stream);
            assertAsString("[One, Two, Three]", streamPlus.toListString());
        });
    }
    
    @Test
    public void testContainsAllOf() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsAllOf(One, Five));
            assertFalse(list.containsAllOf(One, Six));
        });
    }
    
    @Test
    public void testContainsAnyOf() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsAnyOf(One, Six));
            assertFalse(list.containsAnyOf(Six, Seven));
        });
    }
    
    @Test
    public void testContainsNoneOf() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsNoneOf(Six, Seven));
            assertFalse(list.containsNoneOf(One, Six));
        });
    }
    
    @Test
    public void testJavaList_for() {
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            for (val value : list) {
                logs.add(value);
            }
            assertAsString("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testJavaList_size_isEmpty() {
        run(FuncList.of(One, Two, Three), list -> {
            assertEquals(3, list.size());
            assertFalse(list.isEmpty());
        });
        run(FuncList.empty(), list -> {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
        });
    }
    
    @Test
    public void testJavaList_contains() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.contains(Two));
            assertFalse(list.contains(Five));
        });
    }
    
    @Test
    public void testJavaList_containsAll() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.containsAll(listOf(Two, Three)));
            assertFalse(list.containsAll(listOf(Two, Five)));
        });
    }
    
    @Test
    public void testForEach() {
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEach(s -> logs.add(s));
            assertAsString("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEachOrdered(s -> logs.add(s));
            assertAsString("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testReduce() {
        run(FuncList.of(1, 2, 3), list -> {
            assertEquals(6, list.reduce(0, (a, b) -> a + b).intValue());
            assertEquals(6, list.reduce((a, b) -> a + b).get().intValue());
            assertEquals(6, list.reduce(BigInteger.ZERO, (b, i) -> b.add(BigInteger.valueOf((long) i)), (a, b) -> a.add(b)).intValue());
        });
    }
    
    @Test
    public void testCollect() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list.collect(Collectors.toList()));
            Supplier<StringBuffer> supplier = StringBuffer::new;
            BiConsumer<StringBuffer, String> accumulator = StringBuffer::append;
            BiConsumer<StringBuffer, StringBuffer> combiner = (a, b) -> a.append(b.toString());
            assertAsString("OneTwoThree", list.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testMinMax() {
        run(FuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[One]", list.min((a, b) -> a.length() - b.length()));
            assertAsString("Optional[Three]", list.max((a, b) -> a.length() - b.length()));
        });
    }
    
    @Test
    public void testCount() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("3", list.count());
        });
    }
    
    @Test
    public void testAnyMatch() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.anyMatch(One::equals));
        });
    }
    
    @Test
    public void testAllMatch() {
        run(FuncList.of(One, Two, Three), list -> {
            assertFalse(list.allMatch(One::equals));
        });
    }
    
    @Test
    public void testNoneMatch() {
        run(FuncList.of(One, Two, Three), list -> {
            assertTrue(list.noneMatch(Five::equals));
        });
    }
    
    @Test
    public void testFindFirst() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Optional[One]", list.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Optional[One]", list.findAny());
        });
    }
    
    @Test
    public void testFindLast() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Optional[Three]", list.findLast());
        });
    }
    
    @Test
    public void testFirstResult() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Result:{ Value: One }", list.firstResult());
        });
    }
    
    @Test
    public void testLastResult() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Result:{ Value: Three }", list.lastResult());
        });
    }
    
    @Test
    public void testJavaList_get() {
        run(FuncList.of(One, Two, Three), list -> {
            assertEquals(One, list.get(0));
            assertEquals(Two, list.get(1));
            assertEquals(Three, list.get(2));
        });
    }
    
    @Test
    public void testJavaList_indexOf() {
        run(FuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals(1, list.indexOf(Two));
            assertEquals(-1, list.indexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_lastIndexOf() {
        run(FuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals(3, list.lastIndexOf(Two));
            assertEquals(-1, list.lastIndexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_subList() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Two, Three]", list.subList(1, 3));
            assertAsString("[Two, Three, Four, Five]", list.subList(1, 10));
        });
    }
    
    // -- ReadOnly --
    @Test
    public void testJavaList_readOnly() {
        val funcList = FuncList.of(One, Two, Three, Four, Five);
        runExpectReadOnlyListException(funcList, list -> list.set(1, Six));
        runExpectReadOnlyListException(funcList, list -> list.add(Six));
        runExpectReadOnlyListException(funcList, list -> list.add(2, Six));
        runExpectReadOnlyListException(funcList, list -> list.addAll(asList(Six, Seven)));
        runExpectReadOnlyListException(funcList, list -> list.addAll(2, asList(Six, Seven)));
        runExpectReadOnlyListException(funcList, list -> list.remove(Four));
        runExpectReadOnlyListException(funcList, list -> list.remove(2));
        runExpectReadOnlyListException(funcList, list -> list.removeAll(asList(Four, Five)));
        runExpectReadOnlyListException(funcList, list -> list.retainAll(asList(Four, Five)));
        runExpectReadOnlyListException(funcList, list -> list.clear());
        runExpectReadOnlyListException(funcList, list -> list.replaceAll(value -> "-" + value + "-"));
        runExpectReadOnlyListException(funcList, list -> list.sort(String.CASE_INSENSITIVE_ORDER));
    }
    
    // -- AsStreamPlusWithGroupingBy --
    @Test
    public void testGroupingBy() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("{3:[One, Two], 4:[Four, Five], 5:[Three]}", list.groupingBy(theString.length()).sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_aggregate() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("{3:One-Two, 4:Four-Five, 5:Three}", list.groupingBy(theString.length(), join("-")).sortedByKey(theInteger));
        });
    }
    
    // 
    // @Test
    // public void testGroupingBy_process() {
    // run(FuncList.of(One, Two, Three, Four, Five), list -> {
    // val sumLength = new SumLength();
    // assertAsString(
    // "{3:6, 4:8, 5:5}",
    // list
    // .groupingBy(theString.length(), sumLength)
    // .sortedByKey(theInteger));
    // });
    // }
    @Test
    public void testGroupingBy_collect() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("{3:One:Two, 4:Four:Five, 5:Three}", list.groupingBy(theString.length(), () -> Collectors.joining(":")).sortedByKey(theInteger));
        });
    }
    
    // -- Functional list
    @Test
    public void testMapToString() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Nullable.of(One), Nullable.of(Two), Nullable.of(Three), Nullable.of(Four), Nullable.of(Five)]", list.map(theString.toNullable()).mapToString());
        });
        run(FuncList.of(One, null, Three, Four, Five), list -> {
            assertAsString("[Nullable.of(One), Nullable.EMPTY, Nullable.of(Three), Nullable.of(Four), Nullable.of(Five)]", list.map(theString.toNullable()).mapToString());
        });
        run(FuncList.of(One, null, Three, Four, Five), list -> {
            assertAsString("[Optional[One], Optional.empty, Optional[Three], Optional[Four], Optional[Five]]", list.map(theString.toOptional()).mapToString());
        });
        // Null string length is zero
        run(FuncList.of(One, null, Three, Four, Five), list -> {
            assertAsString("[3, 0, 5, 4, 4]", list.map(theString.length()).mapToString());
        });
    }
    
    @Test
    public void testMap() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[-3-, -3-, -5-, -4-, -4-]", list.map(s -> "-" + s.length() + "-"));
        });
    }
    
    @Test
    public void testMapToInt() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("11", list.mapToInt(String::length).sum());
        });
    }
    
    @Test
    public void testMapToDouble() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("11.0", list.map(s -> "" + s.length()).mapToDouble(Double::parseDouble).sum());
        });
    }
    
    @Test
    public void testMapToObj() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[-3-, -3-, -5-, -4-, -4-]", list.mapToObj(s -> "-" + s.length() + "-"));
        });
    }
    
    // -- FlatMap --
    @Test
    public void testFlatMap() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5]", list.flatMap(s -> FuncList.cycle(s.length()).limit(s.length())));
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5]", list.flatMapToInt(s -> IntFuncList.cycle(s.length()).limit(s.length())));
        });
    }
    
    @Test
    public void testFlatMapToDouble() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 5.0, 5.0, 5.0, 5.0, 5.0]", list.flatMapToDouble(s -> DoubleFuncList.cycle(s.length()).limit(s.length())));
        });
    }
    
    // -- Filter --
    @Test
    public void testFilter() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[Three]", list.filter(theString.length().thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testFilter_mapper() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[Three]", list.filter(theString.length(), theInteger.thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testPeek() {
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            assertAsString("[One, Two, Three]", list.peek(s -> logs.add(s)));
            assertAsString("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[One, Two, Three]", list.limit(3));
        });
    }
    
    @Test
    public void testSkip() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.skip(2));
        });
    }
    
    @Test
    public void testDistinct() {
        run(FuncList.of(One, Two, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list.distinct());
        });
    }
    
    @Test
    public void testSorted() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[3, 3, 4, 4, 5]", list.map(theString.length()).sorted());
            assertAsString("[5, 4, 4, 3, 3]", list.map(theString.length()).sorted((a, b) -> (b - a)));
        });
    }
    
    @Test
    public void testToArray() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray()));
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray(n -> new String[n])));
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray(String[]::new)));
            // exact sizes array will be used.
            val arraySameSize = new String[list.size()];
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray(arraySameSize)));
            // too small array will be ignored and a new one created
            val arrayTooSmall = new String[list.size() - 1];
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray(arrayTooSmall)));
            // too large array will be ignored and a new one created
            val arrayTooLarge = new String[list.size() + 1];
            assertAsString("[One, Two, Three]", Arrays.toString(list.toArray(arrayTooLarge)));
        });
    }
    
    @Test
    public void testNullableOptionalResult() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("Nullable.of([One, Two, Three])", list.__nullable());
            assertAsString("Optional[[One, Two, Three]]", list.__optional());
            assertAsString("Result:{ Value: [One, Two, Three] }", list.__result());
        });
    }
    
    @Test
    public void testIndexOf() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertEquals(2, list.indexOf(Three));
        });
    }
    
    @Test
    public void testIndexesOf() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[0, 2]", list.indexesOf(value -> value.equals(One) || value.equals(Three)));
        });
    }
    
    @Test
    public void testFirst() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[One]", list.first());
            assertAsString("[One, Two, Three]", list.first(3));
        });
    }
    
    @Test
    public void testLast() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Five]", list.last());
            assertAsString("[Three, Four, Five]", list.last(3));
        });
    }
    
    @Test
    public void testAt() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.at(2));
            assertAsString("Optional.empty", list.at(10));
        });
    }
    
    @Test
    public void testTail() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Two, Three, Four, Five]", list.tail());
        });
    }
    
    @Test
    public void testToBuilder() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three, Four, Five]", list.toBuilder().add(Four).add(Five).build());
        });
    }
    
    @Test
    public void testAppend() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[One, Two, Three, Four]", list.append(Four));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testAppendAll() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[One, Two, Three, Four, Five]", list.appendAll(Four, Five));
            assertAsString("[One, Two, Three, Four, Five]", list.appendAll(FuncList.listOf(Four, Five)));
            assertAsString("[One, Two, Three, Four, Five]", list.appendAll(FuncList.of(Four, Five)));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testPrepend() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[Zero, One, Two, Three]", list.prepend(Zero));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testPrependAll() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[MinusOne, Zero, One, Two, Three]", list.prependAll(MinusOne, Zero));
            assertAsString("[MinusOne, Zero, One, Two, Three]", list.prependAll(FuncList.listOf(MinusOne, Zero)));
            assertAsString("[MinusOne, Zero, One, Two, Three]", list.prependAll(FuncList.of(MinusOne, Zero)));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testWith() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[One, Zero, Three]", list.with(1, Zero));
            assertAsString("[One, Two=>Zero, Three]", list.with(1, value -> value + "=>" + Zero));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testInsertAt() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[One, Zero, Two, Three]", list.insertAt(1, Zero));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testInsertAllAt() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
            assertAsString("[One, Two, Zero, Zero, Three]", list.insertAt(2, Zero, Zero));
            assertAsString("[One, Two, Zero, Zero, Three]", list.insertAllAt(2, listOf(Zero, Zero)));
            assertAsString("[One, Two, Zero, Zero, Three]", list.insertAllAt(2, FuncList.of(Zero, Zero)));
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    @Test
    public void testExclude() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[One, Two, Three, Four, Five]", list);
            assertAsString("[One, Three, Four, Five]", list.exclude(Two));
            assertAsString("[One, Three, Four, Five]", list.exclude(Two::equals));
            assertAsString("[One, Three, Four, Five]", list.excludeAt(1));
            assertAsString("[One, Five]", list.excludeFrom(1, 3));
            assertAsString("[One, Three, Four, Five]", list.excludeBetween(1, 2));
            assertAsString("[One, Two, Three, Four, Five]", list);
        });
    }
    
    @Test
    public void testReverse() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[One, Two, Three, Four, Five]", list);
            assertAsString("[Five, Four, Three, Two, One]", list.reverse());
            assertAsString("[One, Two, Three, Four, Five]", list);
        });
    }
    
    @Test
    public void testShuffle() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list);
            assertNotEquals("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list.shuffle().toString());
            assertAsString("[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]", list);
        });
    }
    
    @Test
    public void testQuery() {
        run(FuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("[One, Two, Three, Four, Five, Six]", list);
            assertAsString("[(0,One), (1,Two), (5,Six)]", list.query(value -> value.length() == 3));
            assertAsString("[One, Two, Three, Four, Five, Six]", list);
        });
    }
    
    @Test
    public void testMinIndexBy() {
        run(FuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("Optional[0]", list.minIndexBy(value -> value.length()));
        });
    }
    
    @Test
    public void testMaxIndexBy() {
        run(FuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("Optional[2]", list.maxIndexBy(value -> value.length()));
        });
    }
    
    // -- AsStreamPlusWithConversion --
    @Test
    public void testToByteArray() {
        run(FuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65, 66, 67, 68]", Arrays.toString(list.toByteArray(c -> (byte) (int) c)));
        });
    }
    
    @Test
    public void testToIntArray() {
        run(FuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65, 66, 67, 68]", Arrays.toString(list.toIntArray(c -> (int) c)));
        });
    }
    
    @Test
    public void testToDoubleArray() {
        run(FuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(list.toDoubleArray(c -> (double) (int) c)));
        });
    }
    
    @Test
    public void testToArrayList() {
        run(FuncList.of(One, Two, Three), list -> {
            val newList = list.toArrayList();
            assertAsString("[One, Two, Three]", newList);
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    @Test
    public void testToList() {
        run(FuncList.of(One, Two, Three), list -> {
            val newList = list.toJavaList();
            assertAsString("[One, Two, Three]", newList);
            assertTrue(newList instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(FuncList.of(One, Two, Three), list -> {
            val newList = list.toMutableList();
            assertAsString("[One, Two, Three]", newList);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    // -- join --
    @Test
    public void testJoin() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("OneTwoThree", list.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("One, Two, Three", list.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list);
        });
    }
    
    // -- toMap --
    @Test
    public void testToMap() {
        run(FuncList.of(One, Three, Five), list -> {
            assertAsString("{3:One, 4:Five, 5:Three}", list.toMap(theString.length()).toString());
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(FuncList.of(One, Three, Five), list -> {
            assertAsString("{3:-->One, 4:-->Five, 5:-->Three}", list.toMap(theString.length(), theString.withPrefix("-->")).toString());
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(FuncList.of(One, Two, Three, Five), list -> {
            assertAsString("{3:One+Two, 4:Five, 5:Three}", list.toMap(theString.length(), theString, (a, b) -> a + "+" + b).toString());
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(FuncList.of(One, Two, Three, Five), list -> {
            assertAsString("{3:One+Two, 4:Five, 5:Three}", list.toMap(theString.length(), (a, b) -> a + "+" + b).toString());
        });
    }
    
    @Test
    public void testToSet() {
        run(FuncList.of(One, Two, Three), list -> {
            val set = list.toSet();
            assertAsString("[One, Two, Three]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertAsString("[0:One, 1:Two, 2:Three]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new String[5];
            list.populateArray(array);
            assertAsString("[One, Two, Three, Four, Five]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new String[3];
            list.populateArray(array, 2);
            assertAsString("[null, null, One]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new String[5];
            list.populateArray(array, 1, 3);
            assertAsString("[null, One, Two, Three, null]", Arrays.toString(array));
        });
    }
    
    // -- AsFuncListWithMatch --
    @Test
    public void testFindFirst_withPredicate() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.findFirst(theString.thatContains("ee")));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.findAny(theString.thatContains("ee")));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.findFirst(theString.length(), l -> l == 5));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.findAny(theString.length(), l -> l == 5));
        });
    }
    
    // -- AsFuncListWithStatistic --
    @Test
    public void testSize() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("3", list.size());
        });
    }
    
    @Test
    public void testMinBy() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[One]", list.minBy(theString.length()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.maxBy(theString.length()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[Three]", list.minBy(theString.length(), (a, b) -> b - a));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[One]", list.maxBy(theString.length(), (a, b) -> b - a));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("Optional[(Five,Two)]", list.minMax(String.CASE_INSENSITIVE_ORDER));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(FuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(One,Three)]", list.minMaxBy(theString.length()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(FuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(Three,Two)]", list.minMaxBy(theString.length(), (a, b) -> b - a));
        });
    }
    
    // -- StreamPlusWithCalculate --
    static class SumLength extends Aggregation<String, Integer> {
        
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        
        private CollectorToIntPlus<String, int[]> collectorPlus = new CollectorToIntPlus<String, int[]>() {
        
            @Override
            public Supplier<int[]> supplier() {
                return () -> new int[] { 0 };
            }
        
            @Override
            public BiConsumer<int[], String> accumulator() {
                return (a, s) -> {
                    a[0] += s.length();
                };
            }
        
            @Override
            public BinaryOperator<int[]> combiner() {
                return (a1, a2) -> new int[] { a1[0] + a1[1] };
            }
        
            @Override
            public ToIntFunction<int[]> finisherToInt() {
                return a -> a[0];
            }
        
            @Override
            public Collector<String, int[], Integer> collector() {
                return this;
            }
        
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        };
        
        @Override
        public CollectorToIntPlus<String, ?> collectorPlus() {
            return collectorPlus;
        }
    }
    
    static class AvgLength extends Aggregation<String, Integer> {
        
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        
        private CollectorToIntPlus<String, int[]> collectorPlus = new CollectorToIntPlus<String, int[]>() {
        
            @Override
            public Supplier<int[]> supplier() {
                return () -> new int[] { 0, 0 };
            }
        
            @Override
            public BiConsumer<int[], String> accumulator() {
                return (a, s) -> {
                    a[0] += s.length();
                    a[1]++;
                };
            }
        
            @Override
            public BinaryOperator<int[]> combiner() {
                return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] };
            }
        
            @Override
            public Function<int[], Integer> finisher() {
                return a -> a[0] / a[1];
            }
        
            @Override
            public ToIntFunction<int[]> finisherToInt() {
                return a -> a[0] / a[1];
            }
        
            @Override
            public Collector<String, int[], Integer> collector() {
                return this;
            }
        
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        };
        
        @Override
        public CollectorToIntPlus<String, ?> collectorPlus() {
            return collectorPlus;
        }
    }
    
    static class MinLength extends Aggregation<String, Integer> {
        
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        
        private CollectorToIntPlus<String, int[]> collectorPlus = new CollectorToIntPlus<String, int[]>() {
        
            @Override
            public Supplier<int[]> supplier() {
                return () -> new int[] { Integer.MAX_VALUE };
            }
        
            @Override
            public BiConsumer<int[], String> accumulator() {
                return (a, s) -> {
                    a[0] = Math.min(a[0], s.length());
                };
            }
        
            @Override
            public BinaryOperator<int[]> combiner() {
                return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]) };
            }
        
            @Override
            public Function<int[], Integer> finisher() {
                return a -> a[0];
            }
        
            @Override
            public ToIntFunction<int[]> finisherToInt() {
                return a -> a[0];
            }
        
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        
            @Override
            public Collector<String, int[], Integer> collector() {
                return this;
            }
        };
        
        @Override
        public CollectorToIntPlus<String, ?> collectorPlus() {
            return collectorPlus;
        }
    }
    
    static class MaxLength extends Aggregation<String, Integer> {
        
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        
        private CollectorToIntPlus<String, int[]> collectorPlus = new CollectorToIntPlus<String, int[]>() {
        
            @Override
            public Supplier<int[]> supplier() {
                return () -> new int[] { Integer.MIN_VALUE };
            }
        
            @Override
            public BiConsumer<int[], String> accumulator() {
                return (a, s) -> {
                    a[0] = Math.max(a[0], s.length());
                };
            }
        
            @Override
            public BinaryOperator<int[]> combiner() {
                return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]) };
            }
        
            @Override
            public Function<int[], Integer> finisher() {
                return a -> a[0];
            }
        
            @Override
            public ToIntFunction<int[]> finisherToInt() {
                return a -> a[0];
            }
        
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        
            @Override
            public Collector<String, int[], Integer> collector() {
                return this;
            }
        };
        
        @Override
        public CollectorToIntPlus<String, ?> collectorPlus() {
            return collectorPlus;
        }
    }
    
    @Test
    public void testCalculate() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            assertEquals(18, list.calculate(sumLength).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            assertAsString("(18,4)", list.calculate(sumLength, avgLength));
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val range = list.calculate(maxLength, minLength).mapWith((max, min) -> max - min).intValue();
            assertEquals(3, range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            assertAsString("(18,4,3)", list.calculate(sumLength, avgLength, minLength));
        });
    }
    
    @Test
    public void testCalculate3_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val value = list.calculate(sumLength, avgLength, minLength).mapWith((sum, avg, min) -> "sum: " + sum + ", avg: " + avg + ", min: " + min);
            assertAsString("sum: 18, avg: 4, min: 3", value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertAsString("(18,4,3,6)", list.calculate(sumLength, avgLength, minLength, maxLength));
        });
    }
    
    @Test
    public void testCalculate4_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value = list.calculate(sumLength, avgLength, minLength, maxLength).mapWith((sum, avg, min, max) -> "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max);
            assertAsString("sum: 18, avg: 4, min: 3, max: 6", value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertAsString("(18,4,3,6,18)", list.calculate(sumLength, avgLength, minLength, maxLength, sumLength));
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value = list.calculate(sumLength, avgLength, minLength, maxLength, sumLength).mapWith((sum, avg, min, max, sum2) -> {
                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2;
            });
            assertAsString("sum: 18, avg: 4, min: 3, max: 6, sum2: 18", value);
        });
    }
    
    @Test
    public void testCalculate6() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertAsString("(18,4,3,6,18,4)", list.calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength));
        });
    }
    
    @Test
    public void testCalculate6_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value = list.calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength).mapWith((sum, avg, min, max, sum2, avg2) -> {
                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2 + ", avg2: " + avg2;
            });
            assertAsString("sum: 18, avg: 4, min: 3, max: 6, sum2: 18, avg2: 4", value);
        });
    }
    
    // -- FuncListWithCombine --
    @Test
    public void testAppendWith() {
        run(FuncList.of(One, Two), FuncList.of(Three, Four), (list1, list2) -> {
            assertAsString("[One, Two, Three, Four]", list1.appendWith(list2));
        });
    }
    
    @Test
    public void testParependWith() {
        run(FuncList.of(One, Two), FuncList.of(Three, Four), (list1, list2) -> {
            assertAsString("[One, Two, Three, Four]", list2.prependWith(list1));
        });
    }
    
    @Test
    public void testMerge() {
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (list1, streamabl2) -> {
            assertAsString("A, 0, B, 1, C, 2, 3, 4, 5, 6", list1.mergeWith(streamabl2).limit(10).join(", "));
        });
    }
    
    @Test
    public void testZipWith() {
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("(A,0), (B,1), (C,2)", listA.zipWith(listB).join(", "));
        });
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("(A,0), (B,1), (C,2)", listA.zipWith(listB, RequireBoth).join(", "));
        });
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("(A,0), (B,1), (C,2), (null,3), (null,4)", listA.zipWith(listB, AllowUnpaired).limit(5).join(", "));
        });
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("A:0, B:1, C:2", listA.zipWith(listB, (c, i) -> c + ":" + i).join(", "));
        });
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("A:0, B:1, C:2", listA.zipWith(listB, RequireBoth, (c, i) -> c + ":" + i).join(", "));
        });
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            assertAsString("A:0, B:1, C:2, null:3, null:4", listA.zipWith(listB, AllowUnpaired, (c, i) -> c + ":" + i).limit(5).join(", "));
        });
    }
    
    @Test
    public void testChoose() {
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            val bool = new AtomicBoolean(true);
            assertAsString("A, 1, C, 3, 4", listA.choose(listB, (a, b) -> {
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(5).join(", "));
        });
    }
    
    @Test
    public void testChoose_AllowUnpaired() {
        run(FuncList.of("A", "B", "C"), IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(), (listA, listB) -> {
            val bool = new AtomicBoolean(true);
            assertAsString("A, 1, C, 3, 4, 5, 6", listA.choose(listB, AllowUnpaired, (a, b) -> {
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(7).join(", "));
        });
    }
    
    // -- StreamPlusWithFillNull --
    @Test
    public void testFillNull() {
        run(FuncList.of("A", "B", null, "C"), list -> {
            assertAsString("[A, B, Z, C]", list.fillNull("Z"));
        });
    }
    
    @Test
    public void testFillNull_lens() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNull(Car.theCar.color, "Black"));
        });
    }
    
    @Test
    public void testFillNull_getter_setter() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNull((Car car) -> car.color(), (Car car, String color) -> car.withColor(color), "Black"));
        });
    }
    
    @Test
    public void testFillNull_lens_supplier() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNullWith(Car.theCar.color, () -> "Black"));
        });
    }
    
    @Test
    public void testFillNull_getter_setter_supplier() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNullWith((Car car) -> car.color(), (Car car, String color) -> car.withColor(color), () -> "Black"));
        });
    }
    
    @Test
    public void testFillNull_lens_function() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNullBy(Car.theCar.color, (Car car) -> "Black"));
        });
    }
    
    @Test
    public void testFillNull_getter_setter_function() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]", list.fillNullBy((Car car) -> car.color(), (Car car, String color) -> car.withColor(color), (Car car) -> "Black"));
        });
    }
    
    // -- StreamPlusWithFilter --
    @Test
    public void testFilterClass() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            assertAsString("[One, Three, Five]", list.filter(String.class));
        });
    }
    
    @Test
    public void testFilterClass_withPredicate() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            assertAsString("[One, Five]", list.filter(String.class, theString.length().thatLessThan(5)));
        });
    }
    
    @Test
    public void testFilter_withMappter() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.filter(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4));
        });
    }
    
    @Test
    public void testFilterAsInt() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.filterAsInt(str -> str.length(), i -> i >= 4));
        });
    }
    
    @Test
    public void testFilterAsLong() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.filterAsLong(str -> (long) str.length(), i -> i >= 4));
        });
    }
    
    @Test
    public void testFilterAsDouble() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.filterAsDouble(str -> (double) str.length(), i -> i >= 4));
        });
    }
    
    @Test
    public void testFilterAsObject() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Three, Four, Five]", list.filterAsObject(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4));
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[Four, Five]", list.filterWithIndex((index, str) -> index > 2 && !str.startsWith("T")));
        });
    }
    
    @Test
    public void testFilterNonNull() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Blue, Green, Red]", list.map(theCar.color).filterNonNull());
        });
    }
    
    @Test
    public void testFilterNonNull_withMapper() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Red)]", list.filterNonNull(theCar.color));
        });
    }
    
    @Test
    public void testExcludeNull() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Blue, Green, Red]", list.map(theCar.color).excludeNull());
        });
    }
    
    @Test
    public void testExcludeNull_withMapper() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Green), Car(color=Red)]", list.excludeNull(theCar.color));
        });
    }
    
    @Test
    public void testFilterMapper() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Car(color=Blue), Car(color=Red)]", list.filter(theCar.color, color -> Arrays.asList("Blue", "Red").contains(color)));
        });
    }
    
    @Test
    public void testFilterOnly() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Blue, Red]", list.map(theCar.color).filterOnly("Blue", "Red"));
        });
    }
    
    @Test
    public void testFilterIn_collection() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Blue, Red]", list.map(theCar.color).filterIn(asList("Blue", "Red")));
        });
    }
    
    @Test
    public void testExcludeAny() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Green, null]", list.map(theCar.color).excludeAny("Blue", "Red"));
        });
    }
    
    @Test
    public void testExcludeIn_collection() {
        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
            assertAsString("[Green, null]", list.map(theCar.color).excludeIn(asList("Blue", "Red")));
        });
    }
    
    // -- FuncListWithFlatMap --
    @Test
    public void testFlatMapOnly() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, 3, 5]", list.flatMapOnly(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("" + s.length())));
        });
    }
    
    @Test
    public void testFlatMapIf() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[(One), [3], [5]]", list.flatMapIf(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("[" + s.length() + "]"), s -> FuncList.of("(" + s + ")")));
        });
    }
    
    // -- FuncListWithLimit --
    @Test
    public void testSkipLimitLong() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[Two]", list.skip((Long) 1L).limit((Long) 1L));
        });
    }
    
    @Test
    public void testSkipLimitLongNull() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list.skip(null).limit(null));
        });
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[One, Two, Three]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
        });
    }
    
    @Test
    public void testSkipWhile() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i > 3));
        });
    }
    
    @Test
    public void testSkipUntil() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i > 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i < 3));
        });
    }
    
    @Test
    public void testacceptWhile() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[1, 2, 3]", list.peek(logs::add).acceptWhile(i -> i < 4));
            assertAsString("[1, 2, 3, 4]", logs);
            // ^--- Because it needs 4 to do the check in `acceptWhile`
            logs.clear();
            assertAsString("[]", list.peek(logs::add).acceptWhile(i -> i > 4));
            assertAsString("[1]", logs);
            // ^--- Because it needs 1 to do the check in `acceptWhile`
        });
    }
    
    @Test
    public void testacceptWhile_previous() {
        run(FuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.acceptWhile((a, b) -> b == a + 1));
        });
    }
    
    @Test
    public void testTakeUtil() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[1, 2, 3, 4]", list.peek(logs::add).acceptUntil(i -> i > 4));
            assertAsString("[1, 2, 3, 4, 5]", logs);
            // ^--- Because it needs 5 to do the check in `acceptUntil`
            logs.clear();
            assertAsString("[]", list.peek(logs::add).acceptUntil(i -> i < 4));
            assertAsString("[1]", logs);
            // ^--- Because it needs 1 to do the check in `acceptUntil`
        });
    }
    
    @Test
    public void testacceptUntil_previous() {
        run(FuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.acceptUntil((a, b) -> b > a + 1));
        });
    }
    
    @Test
    public void testDropAfter() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.dropAfter(i -> i == 4));
            // ^--- Include 4
        });
    }
    
    @Test
    public void testDropAfter_previous() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4, 5, 4]", list.dropAfter((a, b) -> b < a));
            // ^--- Include 4
        });
    }
    
    @Test
    public void testSkipTake() {
        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[3, 4, 5, 4, 3]", list.peek(logs::add).skipWhile(i -> i < 3).acceptUntil(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2]", logs);
            // ^--^-----------------^--- Because it needs these number to do the check in `skipWhile` and `acceptWhile`
        });
    }
    
    // -- FuncListWithMap --
    @Test
    public void testMapOnly() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[ONE, TWO, Three]", list.mapOnly($S.length().thatLessThan(4), $S.toUpperCase()));
        });
    }
    
    @Test
    public void testMapIf() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[ONE, TWO, three]", list.mapIf($S.length().thatLessThan(4), $S.toUpperCase(), $S.toLowerCase()));
        });
    }
    
    @Test
    public void testMapToObjIf() {
        run(FuncList.of(One, Two, Three), list -> {
            assertAsString("[ONE, TWO, three]", list.mapToObjIf($S.length().thatLessThan(4), $S.toUpperCase(), $S.toLowerCase()));
        });
    }
    
    // == Map First ==
    @Test
    public void testMapFirst_2() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[ONE, TWO, three, four, five, SIX, seven, eight, nine, TEN, eleven, twelve]", list.mapFirst(str -> str.length() == 3 ? str.toUpperCase() : null, str -> str.toLowerCase()));
        });
    }
    
    @Test
    public void testMapFirst_3() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[ONE, TWO, Three, four, five, SIX, Seven, Eight, nine, TEN, Eleven, Twelve]", list.mapFirst(str -> str.length() == 3 ? str.toUpperCase() : null, str -> str.length() == 4 ? str.toLowerCase() : null, str -> str));
        });
    }
    
    @Test
    public void testMapFirst_4() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, Eleven, Twelve]", list.mapFirst(str -> str.length() == 3 ? str.toUpperCase() : null, str -> str.length() == 4 ? str.toLowerCase() : null, str -> str.length() == 5 ? "(" + str + ")" : null, str -> str));
        });
    }
    
    @Test
    public void testMapFirst_5() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, [Eleven], Twelve]", list.mapFirst(str -> str.length() == 3 ? str.toUpperCase() : null, str -> str.length() == 4 ? str.toLowerCase() : null, str -> str.length() == 5 ? "(" + str + ")" : null, str -> str.length() == 6 && !str.contains("w") ? "[" + str + "]" : null, str -> str));
        });
    }
    
    @Test
    public void testMapFirst_6() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[ONE, TWO, (Three), four, -- Five --, -- Six --, (Seven), -- Eight --, -- Nine --, TEN, [Eleven], Twelve]", list.mapFirst(str -> str.contains("i") ? "-- " + str + " --" : null, str -> str.length() == 3 ? str.toUpperCase() : null, str -> str.length() == 4 ? str.toLowerCase() : null, str -> str.length() == 5 ? "(" + str + ")" : null, str -> str.length() == 6 && !str.contains("w") ? "[" + str + "]" : null, str -> str));
        });
    }
    
    // == MapThen ==
    @Test
    public void testMapThen_2() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[O-n, T-w, T-h, F-o, F-i]", list.mapThen($S.charAt(0), $S.charAt(1), (a, b) -> a + "-" + b));
        });
    }
    
    @Test
    public void testMapThen_3() {
        run(FuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[O-n-e, T-w-o, T-h-r, F-o-u, F-i-v]", list.mapThen($S.charAt(0), $S.charAt(1), $S.charAt(2), (a, b, c) -> a + "-" + b + "-" + c));
        });
    }
    
    @Test
    public void testMapThen_4() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[T-h-r-e, F-o-u-r, F-i-v-e, S-e-v-e, E-i-g-h, N-i-n-e, E-l-e-v, T-w-e-l]", list.filter($S.length().thatGreaterThanOrEqualsTo(4)).mapThen($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d));
        });
    }
    
    @Test
    public void testMapThen_5() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[T-h-r-e-e, S-e-v-e-n, E-i-g-h-t, E-l-e-v-e, T-w-e-l-v]", list.filter($S.length().thatGreaterThanOrEqualsTo(5)).mapThen($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e));
        });
    }
    
    @Test
    public void testMapThen_6() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[E-l-e-v-e-n, T-w-e-l-v-e]", list.filter($S.length().thatGreaterThanOrEqualsTo(6)).mapThen($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), $S.charAt(5), (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f));
        });
    }
    
    // -- FuncListWithMapGroup --
    @Test
    public void testMapTwoToSix() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[(One,Two), (Two,Three), (Three,Four), (Four,Five), (Five,Six), (Six,Seven), (Seven,Eight)]", list.mapTwo());
            assertAsString("[(One,Two,Three), (Two,Three,Four), (Three,Four,Five), (Four,Five,Six), (Five,Six,Seven), (Six,Seven,Eight)]", list.mapThree());
            assertAsString("[(One,Two,Three,Four), (Two,Three,Four,Five), (Three,Four,Five,Six), (Four,Five,Six,Seven), (Five,Six,Seven,Eight)]", list.mapFour());
            assertAsString("[(One,Two,Three,Four,Five), (Two,Three,Four,Five,Six), (Three,Four,Five,Six,Seven), (Four,Five,Six,Seven,Eight)]", list.mapFive());
            assertAsString("[(One,Two,Three,Four,Five,Six), (Two,Three,Four,Five,Six,Seven), (Three,Four,Five,Six,Seven,Eight)]", list.mapSix());
        });
    }
    
    @Test
    public void testMapGroup_specific() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]", list.mapGroup((a, b) -> a + ":" + b));
            assertAsString("[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]", list.mapGroup((a, b, c) -> a + ":" + b + ":" + c));
            assertAsString("[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]", list.mapGroup((a, b, c, d) -> a + ":" + b + ":" + c + ":" + d));
            assertAsString("[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]", list.mapGroup((a, b, c, d, e) -> a + ":" + b + ":" + c + ":" + d + ":" + e));
            assertAsString("[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]", list.mapGroup((a, b, c, d, e, f) -> a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f));
        });
    }
    
    @Test
    public void testMapGroup_count() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            Func1<StreamPlus<? extends String>, String> joiner = stream -> stream.join(":");
            assertAsString("[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]", list.mapGroup(2, joiner));
            assertAsString("[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]", list.mapGroup(3, joiner));
            assertAsString("[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]", list.mapGroup(4, joiner));
            assertAsString("[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]", list.mapGroup(5, joiner));
            assertAsString("[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]", list.mapGroup(6, joiner));
            assertAsString("[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]", list.mapGroup(2).map(joiner));
            assertAsString("[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]", list.mapGroup(3).map(joiner));
            assertAsString("[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]", list.mapGroup(4).map(joiner));
            assertAsString("[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]", list.mapGroup(5).map(joiner));
            assertAsString("[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]", list.mapGroup(6).map(joiner));
        });
    }
    
    @Test
    public void testMapGroupToInt() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[6, 8, 9, 8, 7, 8, 10]", list.mapTwoToInt((a, b) -> a.length() + b.length()));
            assertAsString("[6, 8, 9, 8, 7, 8, 10]", list.mapGroupToInt(2, stream -> stream.mapToInt(theString.length()).sum()));
        });
    }
    
    @Test
    public void testMapGroupToDouble() {
        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[9.0, 15.0, 20.0, 16.0, 12.0, 15.0, 25.0]", list.mapTwoToDouble((a, b) -> a.length() * b.length()));
            assertAsString("[9.0, 15.0, 20.0, 16.0, 12.0, 15.0, 25.0]", list.mapGroupToDouble(2, stream -> stream.mapToDouble(theString.length().asDouble()).product().getAsDouble()));
        });
    }
    
    // -- FuncListWithMapToMap --
    @Test
    public void testMapToMap_1() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:O}, " + "{<1>:T}, " + "{<1>:F}, " + "{<1>:S}, " + "{<1>:E}, " + "{<1>:T}, " + "{<1>:S}]", list.filter($S.length().thatGreaterThanOrEqualsTo(1)).mapToMap("<1>", $S.charAt(0)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_2() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:O, <2>:n}, " + "{<1>:T, <2>:h}, " + "{<1>:F, <2>:i}, " + "{<1>:S, <2>:e}, " + "{<1>:E, <2>:l}, " + "{<1>:T, <2>:h}, " + "{<1>:S, <2>:e}]", list.filter($S.length().thatGreaterThanOrEqualsTo(2)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_3() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:O, <2>:n, <3>:e}, " + "{<1>:T, <2>:h, <3>:r}, " + "{<1>:F, <2>:i, <3>:v}, " + "{<1>:S, <2>:e, <3>:v}, " + "{<1>:E, <2>:l, <3>:e}, " + "{<1>:T, <2>:h, <3>:i}, " + "{<1>:S, <2>:e, <3>:v}]", list.filter($S.length().thatGreaterThanOrEqualsTo(3)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_4() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:T, <2>:h, <3>:r, <4>:e}, " + "{<1>:F, <2>:i, <3>:v, <4>:e}, " + "{<1>:S, <2>:e, <3>:v, <4>:e}, " + "{<1>:E, <2>:l, <3>:e, <4>:v}, " + "{<1>:T, <2>:h, <3>:i, <4>:r}, " + "{<1>:S, <2>:e, <3>:v, <4>:e}]", list.filter($S.length().thatGreaterThanOrEqualsTo(4)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_5() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:T, <2>:h, <3>:r, <4>:e, <5>:e}, " + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}, " + "{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e}, " + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t}, " + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}]", list.filter($S.length().thatGreaterThanOrEqualsTo(5)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_6() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e, <6>:n}, " + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e}, " + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t}]", list.filter($S.length().thatGreaterThanOrEqualsTo(6)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_7() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e}, " + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e}]", list.filter($S.length().thatGreaterThanOrEqualsTo(7)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5), "<7>", $S.charAt(6)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_8() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e, <8>:n}, " + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e}]", list.filter($S.length().thatGreaterThanOrEqualsTo(8)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5), "<7>", $S.charAt(6), "<8>", $S.charAt(7)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_9() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e, <9>:n}]", list.filter($S.length().thatGreaterThanOrEqualsTo(9)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5), "<7>", $S.charAt(6), "<8>", $S.charAt(7), "<9>", $S.charAt(8)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_10() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
            assertAsString("[{<10>:r, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]", list.filter($S.length().thatGreaterThanOrEqualsTo(10)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5), "<7>", $S.charAt(6), "<8>", $S.charAt(7), "<9>", $S.charAt(8), "<10>", $S.charAt(9)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_11() {
        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
            assertAsString("[{<10>:r, <11>:e, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]", list.filter($S.length().thatGreaterThanOrEqualsTo(11)).mapToMap("<1>", $S.charAt(0), "<2>", $S.charAt(1), "<3>", $S.charAt(2), "<4>", $S.charAt(3), "<5>", $S.charAt(4), "<6>", $S.charAt(5), "<7>", $S.charAt(6), "<8>", $S.charAt(7), "<9>", $S.charAt(8), "<10>", $S.charAt(9), "<11>", $S.charAt(10)).map(map -> map.sorted()));
        });
    }
    
    // -- FuncListWithMapToTuple --
    @Test
    public void testMapToTuple_2() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(O,n), (T,h), (F,i), (S,e), (E,l)]", list.filter($S.length().thatGreaterThanOrEqualsTo(2)).mapToTuple($S.charAt(0), $S.charAt(1)));
        });
    }
    
    @Test
    public void testMapToTuple_3() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(O,n,e), (T,h,r), (F,i,v), (S,e,v), (E,l,e)]", list.filter($S.length().thatGreaterThanOrEqualsTo(3)).mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2)));
        });
    }
    
    @Test
    public void testMapToTuple_4() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(T,h,r,e), (F,i,v,e), (S,e,v,e), (E,l,e,v)]", list.filter($S.length().thatGreaterThanOrEqualsTo(4)).mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3)));
        });
    }
    
    @Test
    public void testMapToTuple_5() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(T,h,r,e,e), (S,e,v,e,n), (E,l,e,v,e)]", list.filter($S.length().thatGreaterThanOrEqualsTo(5)).mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4)));
        });
    }
    
    @Test
    public void testMapToTuple_6() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(E,l,e,v,e,n)]", list.filter($S.length().thatGreaterThanOrEqualsTo(6)).mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), $S.charAt(5)));
        });
    }
    
    // -- StreamPlusWithMapWithIndex --
    @Test
    public void testMapWithIndex() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(0,One), (1,Three), (2,Five), (3,Seven), (4,Eleven)]", list.mapWithIndex());
        });
    }
    
    @Test
    public void testMapWithIndex_combine() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]", list.mapWithIndex((i, each) -> i + ": " + each));
        });
    }
    
    @Test
    public void testMapToObjWithIndex_combine() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]", list.mapToObjWithIndex((i, each) -> i + ": " + each));
        });
    }
    
    @Test
    public void testMapToIntWithIndex_combine() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[3, 6, 6, 8, 10]", // 3 = 0 + 3 (One)
            // 6 = 1 + 5 (Three)
            // 6 = 2 + 4 (Five)
            // 8 = 3 + 5 (Seven)
            // 10 = 4 + 6 (Eleven)
            list.mapToIntWithIndex((index, value) -> index + value.length()));
        });
    }
    
    @Test
    public void testMapToDoubleWithIndex_combine() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[3.0, 6.0, 6.0, 8.0, 10.0]", // 3 = 0 + 3 (One)
            // 6 = 1 + 5 (Three)
            // 6 = 2 + 4 (Five)
            // 8 = 3 + 5 (Seven)
            // 10 = 4 + 6 (Eleven)
            list.mapToDoubleWithIndex((index, value) -> index + value.length()));
        });
    }
    
    // -- FuncListWithModify --
    @Test
    public void testAccumulate() {
        run(FuncList.of(1, 2, 3, 4, 5), list -> {
            assertAsString("[1, 3, 6, 10, 15]", list.accumulate((prev, current) -> prev + current));
            assertAsString("[1, 12, 123, 1234, 12345]", list.accumulate((prev, current) -> prev * 10 + current));
        });
    }
    
    @Test
    public void testRestate() {
        run(IntFuncList.wholeNumbers(20).map(i -> i % 5).toFuncList(), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list.restate((head, tail) -> tail.filter(x -> x != head)));
        });
    }
    
    @Test
    public void testRestate_sieveOfEratosthenes() {
        run(IntFuncList.naturalNumbers(300).filter(theInteger.thatIsNotOne()).boxed().toFuncList(), list -> {
            assertAsString(
                    "[" 
                        + "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, " 
                        + "101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, " 
                        + "211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293" 
                    + "]", 
                    list.restate((head, tail) -> tail.filter(x -> x % head != 0)));
        });
    }
    
    @Test
    public void testSpawn() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first = new AtomicLong(-1);
            val logs = new ArrayList<String>();
            list.spawn(str -> Sleep(str.length() * timePrecision + 5).thenReturn(str).defer()).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / (1.0 * timePrecision)) * timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[" + "Result:{ Value: Two } -- 0, " + "Result:{ Value: Four } -- " + (1 * timePrecision) + ", " + "Result:{ Value: Three } -- " + (2 * timePrecision) + ", " + "Result:{ Value: Eleven } -- " + (3 * timePrecision) + "" + "]", logs.toString());
        });
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first = new AtomicLong(-1);
            val logs = new ArrayList<String>();
            list.spawn(F((String str) -> {
                Thread.sleep(str.length() * timePrecision + 5);
                return str;
            }).defer()).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / (1.0 * timePrecision)) * timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[" + "Result:{ Value: Two } -- 0, " + "Result:{ Value: Four } -- " + (1 * timePrecision) + ", " + "Result:{ Value: Three } -- " + (2 * timePrecision) + ", " + "Result:{ Value: Eleven } -- " + (3 * timePrecision) + "" + "]", logs.toString());
        });
    }
    
    @Test
    public void testSpawn_limit() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val first = new AtomicLong(-1);
            val actions = new ArrayList<DeferAction<String>>();
            val logs = new ArrayList<String>();
            list.spawn(str -> {
                DeferAction<String> action = Sleep(str.length() * 50 + 5).thenReturn(str).defer();
                actions.add(action);
                return action;
            }).limit(1).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / 50.0) * 50;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[Result:{ Value: Two } -- 0]", logs.toString());
            assertEquals("Result:{ Value: Two }, " + "Result:{ Cancelled: Stream closed! }, " + "Result:{ Cancelled: Stream closed! }, " + "Result:{ Cancelled: Stream closed! }", actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
        });
    }
    
    // -- FuncListWithPeek --
    @Test
    public void testPeekClass() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            val elementIntegers = new ArrayList<Integer>();
            list.peek(String.class, elementStrings::add).peek(Integer.class, elementIntegers::add).// To terminate the stream
            join();
            assertAsString("[One, Three, Five]", elementStrings);
            assertAsString("[0, 2, 4]", elementIntegers);
        });
    }
    
    @Test
    public void testPeekBy() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            val elementIntegers = new ArrayList<Integer>();
            list.peekBy(String.class::isInstance, e -> elementStrings.add((String) e)).peekBy(Integer.class::isInstance, e -> elementIntegers.add((Integer) e)).// To terminate the stream
            join();
            assertAsString("[One, Three, Five]", elementStrings);
            assertAsString("[0, 2, 4]", elementIntegers);
        });
    }
    
    @Test
    public void testPeekAs() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekAs(e -> "<" + e + ">", e -> elementStrings.add((String) e)).// To terminate the stream
            join();
            assertAsString("[<0>, <One>, <2>, <Three>, <4>, <Five>]", elementStrings);
        });
    }
    
    @Test
    public void testPeekBy_map() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekBy(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add("" + e)).// To terminate the stream
            join();
            assertAsString("[0, One, 2, Three, 4]", elementStrings);
        });
    }
    
    @Test
    public void testPeekAs_map() {
        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekAs(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add((String) e)).// To terminate the stream
            join();
            assertAsString("[<0>, <One>, <2>, <Three>, <4>]", elementStrings);
        });
    }
    
    // -- FuncListWithPipe --
    @Test
    public void testPipeable() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[One, Three, Five, Seven, Eleven]", list.pipable().pipeTo(FuncList::toListString));
        });
    }
    
    @Test
    public void testPipe() {
        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[One, Three, Five, Seven, Eleven]", list.pipe(FuncList::toListString));
        });
    }
    
    // -- FuncListWithReshape --
    @Test
    public void testSegment() {
        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
            assertAsString(
                    "[" 
                    + "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]"
                    + "]", 
                    list.segment(6).map(FuncList::toListString));
        });
    }
    
    @Test
    public void testSegment_sizeFunction() {
        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
            assertAsString("[" + "[1], " + "[2, 3], " + "[4, 5, 6, 7], " + "[8, 9, 10, 11, 12, 13, 14, 15], " + "[16, 17, 18, 19]" + "]", list.segment(i -> i));
        });
        // Empty
        run(IntFuncList.wholeNumbers(0).boxed(), list -> {
            assertAsString("[]", list.segment(i -> i));
        });
        // End at exact boundary
        run(IntFuncList.wholeNumbers(8).boxed(), list -> {
            assertAsString("[" + "[1], " + "[2, 3], " + "[4, 5, 6, 7]" + "]", list.segment(i -> i));
        });
    }
    
    @Test
    public void testSegmentWhen() {
        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
            assertAsString("[" + "[0, 1, 2], " + "[3, 4, 5], " + "[6, 7, 8], " + "[9, 10, 11], " + "[12, 13, 14], " + "[15, 16, 17], " + "[18, 19]" + "]", list.segmentWhen(theInteger.thatIsDivisibleBy(3)).map(FuncList::toListString));
        });
    }
    
    @Test
    public void testSegmentAfter() {
        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
            assertAsString("[" + "[0], " + "[1, 2, 3], " + "[4, 5, 6], " + "[7, 8, 9], " + "[10, 11, 12], " + "[13, 14, 15], " + "[16, 17, 18], " + "[19]" + "]", list.segmentAfter(theInteger.thatIsDivisibleBy(3)).map(FuncList::toListString));
        });
    }
    
    @Test
    public void testSegmentBetween() {
        Predicate<Integer> startCondition = i -> (i % 10) == 3;
        Predicate<Integer> endCondition = i -> (i % 10) == 6;
        run(IntFuncList.wholeNumbers(75).boxed(), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66], " + "[73, 74]" + "]", list.segmentBetween(startCondition, endCondition, true).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66], " + "[73, 74]" + "]", list.segmentBetween(startCondition, endCondition, IncompletedSegment.included).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, IncompletedSegment.excluded).skip(5).limit(3));
        });
        // Edge cases
        // Empty
        run(IntFuncList.wholeNumbers(0).boxed(), list -> {
            assertAsString("[]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Not enough
        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
            assertAsString("[]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact
        run(IntFuncList.wholeNumbers(67).boxed(), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact - 1
        run(IntFuncList.wholeNumbers(66).boxed(), list -> {
            assertAsString("[" + "[53, 54, 55, 56]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact + 1
        run(IntFuncList.wholeNumbers(68).boxed(), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // From start
        run(IntFuncList.wholeNumbers(30).boxed(), list -> {
            assertAsString("[" + "[3, 4, 5, 6], " + "[13, 14, 15, 16], " + "[23, 24, 25, 26]" + "]", list.segmentBetween(startCondition, endCondition, false));
        });
        // Incomplete start
        run(IntFuncList.wholeNumbers(30).skip(5).boxed(), list -> {
            assertAsString("[" + "[13, 14, 15, 16], " + "[23, 24, 25, 26]" + "]", list.segmentBetween(startCondition, endCondition, false));
        });
    }
    
    @Test
    public void testSegmentByPercentiles() {
        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(30, 80));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(30.0, 80.0));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(IntFuncList.of(30, 80)));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper() {
        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, 30, 80));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, 30.0, 80.0));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, IntFuncList.of(30, 80)));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper_comparator() {
        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30, 80));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30.0, 80.0));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, IntFuncList.of(30, 80)));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    // -- FuncListWithSort --
    @Test
    public void testSortedBy() {
        run(FuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[One, Two, Four, Three]", list.sortedBy(String::length));
            // Using comparable access.
            assertAsString("[One, Two, Four, Three]", list.sortedBy(theString.length()));
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(FuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[Three, Four, One, Two]", list.sortedBy(String::length, (a, b) -> b - a));
            // Using comparable access.
            assertAsString("[Three, Four, One, Two]", list.sortedBy(theString.length(), (a, b) -> b - a));
        });
    }
    
    // -- FuncListWithSplit --
    @Test
    public void testSplitTuple() {
        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
            assertAsString("(" + "[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]," + "[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]" + ")", list.split(theInteger.thatIsDivisibleBy(2)).toString());
        });
    }
    
    @Test
    public void testSplit() {
        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
            String Other = "Other";
            assertAsString("{" + "Other:[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Other).sorted().toString());
            assertAsString("{" + "Other:[1, 5, 7, 11, 13, 17, 19], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Other).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Other:[1, 7, 11, 13, 17, 19], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Other).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Seven:[7], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], " + "Three:[3, 9, 15], " + "Other:[1, 11, 13, 17, 19]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Seven, theInteger.thatIsDivisibleBy(7), Other).toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Other:[1, 13, 17, 19], " + "Seven:[7], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Seven, theInteger.thatIsDivisibleBy(7), Eleven, theInteger.thatIsDivisibleBy(11), Other).sorted().toString());
            // Ignore some values
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Other:[1, 13, 17, 19], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), null, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), null, theInteger.thatIsDivisibleBy(7), Eleven, theInteger.thatIsDivisibleBy(11), Other).sorted().toString());
            // Ignore others
            assertAsString("{" + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3)).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5)).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Seven:[7], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Seven, theInteger.thatIsDivisibleBy(7)).sorted().toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Seven:[7], " + "Three:[3, 9, 15], Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Seven, theInteger.thatIsDivisibleBy(7), Eleven, theInteger.thatIsDivisibleBy(11)).sorted().toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Seven:[7], " + "Thirteen:[13], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split(Two, theInteger.thatIsDivisibleBy(2), Three, theInteger.thatIsDivisibleBy(3), Five, theInteger.thatIsDivisibleBy(5), Seven, theInteger.thatIsDivisibleBy(7), Eleven, theInteger.thatIsDivisibleBy(11), Thirteen, theInteger.thatIsDivisibleBy(13)).sorted().toString());
        });
    }
    
    @Test
    public void testSplit_ignore() {
        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null, theInteger.thatIsDivisibleBy(7), (String) null).toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null, theInteger.thatIsDivisibleBy(7), (String) null, theInteger.thatIsDivisibleBy(11), (String) null).sorted().toString());
            // No other
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2)).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3)).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5)).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null, theInteger.thatIsDivisibleBy(7)).toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null, theInteger.thatIsDivisibleBy(7), (String) null, theInteger.thatIsDivisibleBy(11)).sorted().toString());
            assertAsString("{}", list.split((String) null, theInteger.thatIsDivisibleBy(2), (String) null, theInteger.thatIsDivisibleBy(3), (String) null, theInteger.thatIsDivisibleBy(5), (String) null, theInteger.thatIsDivisibleBy(7), (String) null, theInteger.thatIsDivisibleBy(11), (String) null, theInteger.thatIsDivisibleBy(13)).sorted().toString());
        });
    }
    
    @Test
    public void testFizzBuzz() {
        Function<FuncList<Integer>, FuncList<Integer>> listToList = s -> s.toImmutableList();
        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap)).run(() -> {
                FuncMap<String, FuncList<Integer>> splited = list.split("FizzBuzz", i -> i % (3 * 5) == 0, "Buzz", i -> i % 5 == 0, "Fizz", i -> i % 3 == 0, null);
                val string = splited.mapValue(listToList).toString();
                return string;
            });
            assertEquals("{" + "FizzBuzz:[0, 15], " + "Buzz:[5, 10], " + "Fizz:[3, 6, 9, 12, 18]" + "}", toString);
        });
    }
    
    // == Test String RegEx ==
    @Test
    public void testMatch() {
        assertAsString("[#{Hello}, #{Here}, #{Hello}, #{There}]", FuncList.of("--#{Hello}--#{Here}--", "--#{Hello}--#{There}--").flatMap(theString.matches("#\\{[a-zA-Z0-9$_]+\\}").texts().toList()));
    }
    
    @Test
    public void testGrab() {
        assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]", FuncList.of("1 2 3 4 5 6 7 8 9 10 11").flatMap(theString.grab("[0-9]+")));
    }
    
    @Test
    public void testCapture() {
        val pattern = Pattern.compile("(?<key>[^:]+): (?<value>.*)");
        assertAsString("[{value:Nawa, key:name}]", FuncList.of("name: Nawa").map(theString.capture(pattern)));
    }
}
