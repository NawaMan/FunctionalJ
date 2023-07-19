package functionalj.list;

import static functionalj.TestHelper.assertAsString;
import static functionalj.lens.Access.theDouble;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theLong;
import static functionalj.ref.Run.With;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import org.junit.Test;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.function.aggregator.LongAggregation;
import functionalj.function.aggregator.LongAggregationToLong;
import functionalj.functions.TimeFuncs;
import functionalj.lens.LensTest.Car;
import functionalj.list.FuncList.Mode;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.longlist.ImmutableLongFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.list.longlist.LongFuncListBuilder;
import functionalj.list.longlist.LongFuncListDerived;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;
import lombok.val;

public class LongFuncListTest {
    
    static final long MinusOne = -1;
    
    static final long Zero = 0;
    
    static final long One = 1;
    
    static final long Two = 2;
    
    static final long Three = 3;
    
    static final long Four = 4;
    
    static final long Five = 5;
    
    static final long Six = 6;
    
    static final long Seven = 7;
    
    static final long Eight = 8;
    
    static final long Nine = 9;
    
    static final long Ten = 10;
    
    static final long Eleven = 11;
    
    static final long Twelve = 12;
    
    static final long Thirteen = 13;
    
    static final long Seventeen = 17;
    
    static final long Nineteen = 19;
    
    static final long TwentyThree = 23;
    
    private <T> void run(FuncList<T> list, FuncUnit1<FuncList<T>> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(LongFuncList list, FuncUnit1<LongFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(DoubleFuncList list, FuncUnit1<DoubleFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(LongFuncList list1, LongFuncList list2, FuncUnit2<LongFuncList, LongFuncList> action) {
        action.accept(list1, list2);
        action.accept(list1, list2);
    }
    
    @Test
    public void testEmpty() {
        run(LongFuncList.empty(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList() {
        run(LongFuncList.emptyList(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmpty_intFuncList() {
        run(LongFuncList.emptyLongList(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testOf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testAllOf() {
        run(LongFuncList.AllOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testInts() {
        run(LongFuncList.longs(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testIntList() {
        run(LongFuncList.longList(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testListOf() {
        run(LongFuncList.ListOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
        run(LongFuncList.listOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    // -- From --
    @Test
    public void testFrom_array() {
        run(LongFuncList.from(new long[] { 1, 2, 3 }), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<Long> collection = Arrays.asList(One, Two, Three, null);
        run(LongFuncList.from(collection, -1), list -> {
            assertAsString("[1, 2, 3, -1]", list);
        });
        Set<Long> set = new LinkedHashSet<>(collection);
        run(LongFuncList.from(set, -2), list -> {
            assertAsString("[1, 2, 3, -2]", list);
        });
        FuncList<Long> lazyList = FuncList.of(One, Two, Three, null);
        run(LongFuncList.from(lazyList, -3), list -> {
            assertAsString("[1, 2, 3, -3]", list);
            assertTrue(list.isLazy());
        });
        FuncList<Long> eagerList = FuncList.of(One, Two, Three, null).toEager();
        run(LongFuncList.from(eagerList, -4), list -> {
            assertAsString("[1, 2, 3, -4]", list);
            assertTrue(list.isEager());
        });
    }
    
    @Test
    public void testFrom_funcList() {
        run(LongFuncList.from(Mode.lazy, LongFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue(list.isLazy());
        });
        run(LongFuncList.from(Mode.eager, LongFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue(list.isEager());
        });
        run(LongFuncList.from(Mode.cache, LongFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue(list.isCache());
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(LongFuncList.from(LongStreamPlus.infiniteInt().limit(3)), list -> {
            assertAsString("[0, 1, 2]", list.limit(3));
        });
    }
    
    @Test
    public void testFrom_streamSupplier() {
        run(LongFuncList.from(() -> LongStreamPlus.infiniteInt()), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list.limit(5));
            assertAsString("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", list.limit(10));
        });
    }
    
    @Test
    public void testZeroes() {
        run(LongFuncList.zeroes().limit(5), list -> {
            assertAsString("[0, 0, 0, 0, 0]", list);
            assertAsString("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
        run(LongFuncList.zeroes(5), list -> {
            assertAsString("[0, 0, 0, 0, 0]", list);
            assertAsString("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
    }
    
    @Test
    public void testOnes() {
        run(LongFuncList.ones().limit(5), list -> {
            assertAsString("[1, 1, 1, 1, 1]", list);
            assertAsString("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
        run(LongFuncList.ones(5), list -> {
            assertAsString("[1, 1, 1, 1, 1]", list);
            assertAsString("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
    }
    
    @Test
    public void testRepeat() {
        run(LongFuncList.repeat(0, 42), list -> {
            assertAsString("[0, 42, 0, 42, 0]", list.limit(5));
            assertAsString("[0, 42, 0, 42, 0, 42, 0]", list.limit(7));
        });
        run(LongFuncList.repeat(LongFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]", list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testCycle() {
        run(LongFuncList.cycle(0, 1, 42), list -> {
            assertAsString("[0, 1, 42, 0, 1]", list.limit(5));
            assertAsString("[0, 1, 42, 0, 1, 42, 0]", list.limit(7));
        });
        run(LongFuncList.cycle(LongFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]", list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testLoop() {
        run(LongFuncList.loop(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(LongFuncList.loop(5), list -> assertAsString("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testLoopBy() {
        run(LongFuncList.loopBy(3), list -> assertAsString("[0, 3, 6, 9, 12]", list.limit(5)));
        run(LongFuncList.loopBy(3, 5), list -> assertAsString("[0, 3, 6, 9, 12]", list));
    }
    
    @Test
    public void testInfinite() {
        run(LongFuncList.infinite(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(LongFuncList.infiniteInt(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
    }
    
    @Test
    public void testNaturalNumbers() {
        run(LongFuncList.naturalNumbers(), list -> assertAsString("[1, 2, 3, 4, 5]", list.limit(5)));
        run(LongFuncList.naturalNumbers(5), list -> assertAsString("[1, 2, 3, 4, 5]", list));
    }
    
    @Test
    public void testWholeNumbers() {
        run(LongFuncList.wholeNumbers(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(LongFuncList.wholeNumbers(5), list -> assertAsString("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testRange() {
        run(LongFuncList.range(3, 7), list -> assertAsString("[3, 4, 5, 6]", list.limit(5)));
        run(LongFuncList.range(-3, 3), list -> assertAsString("[-3, -2, -1, 0, 1, 2]", list.limit(10)));
    }
    
    @Test
    public void testEquals() {
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertTrue(Objects.equals(list1, list2));
            assertEquals(list1, list2);
        });
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertFalse(Objects.equals(list1, list2));
            assertNotEquals(list1, list2);
        });
        // Make it a derived list
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertEquals(list1, list2);
        });
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertNotEquals(list1, list2);
        });
    }
    
    @Test
    public void testHashCode() {
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertEquals(list1.hashCode(), list2.hashCode());
        });
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertNotEquals(list1.hashCode(), list2.hashCode());
        });
        // Make it a derived list
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertEquals(list1.hashCode(), list2.hashCode());
        });
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertNotEquals(list1.hashCode(), list2.hashCode());
        });
    }
    
    @Test
    public void testToString() {
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertEquals(list1.toString(), list2.toString());
        });
        run(LongFuncList.of(One, Two, Three), LongFuncList.of(One, Two, Three, Four), (list1, list2) -> {
            assertTrue(list1 instanceof ImmutableLongFuncList);
            assertTrue(list2 instanceof ImmutableLongFuncList);
            assertNotEquals(list1.toString(), list2.toString());
        });
        // Make it a derived list
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertEquals(list1.toString(), list2.toString());
        });
        run(LongFuncList.of(One, Two, Three).map(value -> value), LongFuncList.of(One, Two, Three, Four).map(value -> value), (list1, list2) -> {
            assertTrue(list1 instanceof LongFuncListDerived);
            assertTrue(list2 instanceof LongFuncListDerived);
            assertNotEquals(list1.toString(), list2.toString());
        });
    }
    
    // -- Concat + Combine --
    @Test
    public void testConcat() {
        run(LongFuncList.concat(LongFuncList.of(One, Two), LongFuncList.of(Three, Four)), list -> {
            assertAsString("[1, 2, 3, 4]", list);
        });
    }
    
    @Test
    public void testCombine() {
        run(LongFuncList.combine(LongFuncList.of(One, Two), LongFuncList.of(Three, Four)), list -> {
            assertAsString("[1, 2, 3, 4]", list);
        });
    }
    
    // -- Generate --
    @Test
    public void testGenerate() {
        run(LongFuncList.generateWith(() -> {
            val counter = new AtomicInteger();
            LongSupplier supplier = () -> counter.getAndIncrement();
            return supplier;
        }), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list.limit(5));
        });
        run(LongFuncList.generateWith(() -> {
            val counter = new AtomicInteger();
            LongSupplier supplier = () -> {
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
        run(LongFuncList.iterate(1, (i) -> 2 * (i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(LongFuncList.iterate(1, 2, (a, b) -> a + b), list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]", list.limit(10)));
    }
    
    // -- Compound --
    @Test
    public void testCompound() {
        run(LongFuncList.compound(1, (i) -> 2 * (i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(LongFuncList.compound(1, 2, (a, b) -> a + b), list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]", list.limit(10)));
    }
    
    // -- zipOf --
    @Test
    public void testZipOf_toTuple() {
        run(LongFuncList.of(1000, 2000, 3000, 4000, 5000), LongFuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[(1000,1), (2000,2), (3000,3), (4000,4)]", LongFuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_toTuple_default() {
        run(LongFuncList.of(1000, 2000, 3000, 4000, 5000), LongFuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[(1000,1), (2000,2), (3000,3), (4000,4), (5000,-1)]", LongFuncList.zipOf(list1, -1000, list2, -1));
        });
        run(LongFuncList.of(1000, 2000, 3000, 4000), LongFuncList.of(1, 2, 3, 4, 5), (list1, list2) -> {
            assertAsString("[(1000,1), (2000,2), (3000,3), (4000,4), (-1000,5)]", LongFuncList.zipOf(list1, -1000, list2, -1));
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(LongFuncList.of(1000, 2000, 3000, 4000, 5000), LongFuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[1001, 2002, 3003, 4004]", FuncList.zipOf(list1, list2, (a, b) -> a + +b));
        });
    }
    
    @Test
    public void testZipOf_merge_default() {
        run(LongFuncList.of(1000, 2000, 3000, 4000, 5000), LongFuncList.of(1, 2, 3, 4), (list1, list2) -> {
            assertAsString("[1000, 4000, 9000, 16000, -5000]", LongFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a * b));
        });
        run(LongFuncList.of(1000, 2000, 3000, 4000), LongFuncList.of(1, 2, 3, 4, 5), (list1, list2) -> {
            assertAsString("[1000, 4000, 9000, 16000, -5000]", LongFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a * b));
        });
    }
    
    @Test
    public void testNew() {
        LongFuncListBuilder funcList1 = LongFuncList.newListBuilder();
        LongFuncListBuilder funcList2 = LongFuncList.newBuilder();
        LongFuncListBuilder funcList3 = LongFuncList.newIntListBuilder();
        run(funcList1.add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[1, 2, 3]", list);
        });
        run(funcList2.add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[1, 2, 3]", list);
        });
        run(funcList3.add(One).add(Two).add(Three).build(), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    // -- Derive --
    @Test
    public void testDeriveFrom() {
        run(LongFuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.mapToLong(v -> -v)), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
        run(LongFuncList.deriveFrom(LongFuncList.of(1, 2, 3), s -> s.map(v -> -v)), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
        run(LongFuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToLong(v -> Math.round(-v))), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
    }
    
    @Test
    public void testDeriveTo() {
        run(LongFuncList.deriveToObj(LongFuncList.of(One, Two, Three), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertTrue(list instanceof FuncList);
            assertAsString("[-1-, -2-, -3-]", list);
        });
        run(LongFuncList.deriveToLong(LongFuncList.of(One, Two, Three), s -> s.map(v -> v + 5)), list -> {
            assertAsString("[6, 7, 8]", list);
        });
        run(LongFuncList.deriveToDouble(LongFuncList.of(One, Two, Three), s -> s.mapToDouble(v -> 3.0 * v)), list -> {
            assertAsString("[3.0, 6.0, 9.0]", list);
        });
    }
    
    // -- Predicate --
    @Test
    public void testTest_predicate() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.test(One));
            assertTrue(list.test(Two));
            assertTrue(list.test(Three));
            assertFalse(list.test(Four));
            assertFalse(list.test(Five));
            assertFalse(list.test(Six));
        });
    }
    
    // -- Eager+Lazy --
    @Test
    public void testIsEagerIsLazy() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.toLazy().isLazy());
            assertTrue(list.toEager().isEager());
            assertTrue(list.toLazy().freeze().isLazy());
            val logs = new ArrayList<String>();
            LongFuncList lazyList = list.peek(value -> logs.add("" + value));
            // ForEach but do nothing
            lazyList.forEach(value -> {
            });
            assertEquals("[1, 2, 3]", logs.toString());
            // Lazy list will have to be re-evaluated again so the logs double.
            lazyList.forEach(value -> {
            });
            assertEquals("[1, 2, 3, 1, 2, 3]", logs.toString());
            logs.clear();
            assertEquals("[]", logs.toString());
            // Freeze but still lazy
            LongFuncList frozenList = list.freeze().peek(value -> logs.add("" + value));
            // ForEach but do nothing
            frozenList.forEach(value -> {
            });
            assertEquals("[1, 2, 3]", logs.toString());
            // Freeze list but still lazy so it will have to be re-evaluated again so the logs double
            frozenList.forEach(value -> {
            });
            assertEquals("[1, 2, 3, 1, 2, 3]", logs.toString());
            // Eager list
            logs.clear();
            LongFuncList eagerList = list.toEager().peek(value -> logs.add("" + value));
            // ForEach but do nothing
            eagerList.forEach(value -> {
            });
            assertEquals("[1, 2, 3]", logs.toString());
            // Eager list does not re-evaluate so the log stay the same.
            eagerList.forEach(value -> {
            });
            assertEquals("[1, 2, 3]", logs.toString());
        });
    }
    
    @Test
    public void testEagerLazy() {
        {
            val logs = new ArrayList<String>();
            // We want to confirm that the list is lazy
            val list = LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList();
            // The function has not been materialized so nothing goes through peek.
            assertAsString("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertAsString("[1, 2, 3, 4, 5]", list.limit(5));
            assertAsString("[1, 2, 3, 4, 5]", logs);
        }
        {
            val logs = new ArrayList<String>();
            // We want to confirm that the list is eager
            val list = LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList().toEager();
            // The function has been materialized so all element goes through peek.
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs);
            // Even we only get part of it,
            assertAsString("[1, 2, 3, 4, 5]", list.limit(5));
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs);
        }
    }
    
    @Test
    public void testEagerLazy_more() {
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val orgData = LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData.toLazy().peek(v -> logs1.add("" + v)).exclude(theLong.thatLessThanOrEqualsTo(3)).peek(v -> logs2.add("" + v));
            // The list has not been materialized so nothing goes through peek.
            assertAsString("[]", logs1);
            assertAsString("[]", logs2);
            // Get part of them so those peek will goes through the peek
            assertAsString("[4, 5, 6, 7, 8]", list.limit(5));
            // Now that the list has been materialize all the element has been through the logs
            // The first log has all the number until there are 5 elements that are bigger than 3.
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8]", logs1);
            // 1  2  3  4  5
            // The second log captures all the number until 5 of them that are bigger than 3.
            assertAsString("[4, 5, 6, 7, 8]", logs2);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            val orgData = LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData.toEager().peek(v -> logs1.add("" + v)).exclude(theLong.thatLessThanOrEqualsTo(3)).peek(v -> logs2.add("" + v));
            // Since the list is eager, all the value pass through all peek all the time
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs1);
            assertAsString("[4, 5, 6, 7, 8, 9, 10]", logs2);
            // Get part of them so those peek will goes through the peek
            assertAsString("[4, 5, 6, 7, 8]", list.limit(5));
            // No more passing through the log stay still
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs1);
            assertAsString("[4, 5, 6, 7, 8, 9, 10]", logs2);
        }
    }
    
    // -- List --
    @Test
    public void testToFuncList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val funcList = list.toFuncList();
            assertAsString("[1, 2, 3]", funcList.toString());
            assertTrue(funcList instanceof LongFuncList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val funcList = list.toJavaList();
            assertAsString("[1, 2, 3]", funcList);
            assertFalse(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val funcList = list.toImmutableList();
            assertAsString("[1, 2, 3]", funcList);
            assertTrue(funcList instanceof ImmutableLongFuncList);
            assertAsString("[1, 2, 3]", funcList.map(value -> value).toImmutableList());
            assertTrue(funcList instanceof ImmutableLongFuncList);
        });
    }
    
    @Test
    public void testIterable() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterable().iterator();
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextLong());
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextLong());
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextLong());
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testIterator() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterator();
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextLong());
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextLong());
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextLong());
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testSpliterator() {
        run(LongFuncList.of(One, Two, Three), list -> {
            Spliterator.OfLong spliterator = list.spliterator();
            LongStream stream = StreamSupport.longStream(spliterator, false);
            LongStreamPlus streamPlus = LongStreamPlus.from(stream);
            assertAsString("[1, 2, 3]", streamPlus.toListString());
        });
    }
    
    @Test
    public void testContainsAllOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsAllOf(One, Five));
            assertFalse(list.containsAllOf(One, Six));
        });
    }
    
    @Test
    public void testContainsAnyOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsSomeOf(One, Six));
            assertFalse(list.containsSomeOf(Six, Seven));
        });
    }
    
    @Test
    public void testContainsNoneOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue(list.containsNoneOf(Six, Seven));
            assertFalse(list.containsNoneOf(One, Six));
        });
    }
    
    @Test
    public void testJavaList_for() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            for (val value : list.boxed()) {
                logs.add("" + value);
            }
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testJavaList_size_isEmpty() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertEquals(3, list.size());
            assertFalse(list.isEmpty());
        });
        run(LongFuncList.empty(), list -> {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
        });
    }
    
    @Test
    public void testJavaList_contains() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.contains(Two));
            assertFalse(list.contains(Five));
        });
    }
    
    @Test
    public void testJavaList_containsAllOf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.containsAllOf(Two, Three));
            assertTrue(list.containsAllOf(FuncList.listOf(Two, Three)));
            assertTrue(list.containsAllOf(LongFuncList.listOf(Two, Three)));
            assertFalse(list.containsAllOf(Two, Five));
            assertFalse(list.containsAllOf(FuncList.listOf(Two, Five)));
            assertFalse(list.containsAllOf(LongFuncList.listOf(Two, Five)));
        });
    }
    
    @Test
    public void testJavaList_containsSomeOf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.containsSomeOf(Two, Five));
            assertTrue(list.containsSomeOf(FuncList.listOf(Two, Five)));
            assertTrue(list.containsSomeOf(LongFuncList.listOf(Two, Five)));
            assertFalse(list.containsSomeOf(Five, Seven));
            assertFalse(list.containsSomeOf(FuncList.listOf(Five, Seven)));
            assertFalse(list.containsSomeOf(LongFuncList.listOf(Five, Seven)));
        });
    }
    
    @Test
    public void testJavaList_containsNoneOf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.containsNoneOf(Five, Six));
            assertTrue(list.containsNoneOf(FuncList.listOf(Five, Six)));
            assertTrue(list.containsNoneOf(LongFuncList.listOf(Five, Six)));
            assertFalse(list.containsNoneOf(Two, Five));
            assertFalse(list.containsNoneOf(FuncList.listOf(Two, Five)));
            assertFalse(list.containsNoneOf(LongFuncList.listOf(Two, Five)));
        });
    }
    
    @Test
    public void testForEach() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEach(s -> logs.add("" + s));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEachOrdered(s -> logs.add("" + s));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testReduce() {
        run(LongFuncList.of(1, 2, 3), list -> {
            assertEquals(6, list.reduce(0, (a, b) -> a + b));
            assertEquals(6, list.reduce((a, b) -> a + b).getAsLong());
        });
    }
    
    static class Sum extends LongAggregationToLong {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorToLongPlus<long[]> collectorPlus = new LongCollectorToLongPlus<long[]>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, s) -> {
                    a[0] += s;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { a1[0] + a1[1] };
            }
    
            @Override
            public ToLongFunction<long[]> finisherToLong() {
                return a -> a[0];
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        };
    
        @Override
        public LongCollectorToLongPlus<?> longCollectorToLongPlus() {
            return collectorPlus;
        }
    }
    
    @Test
    public void testCollect() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val sum = new Sum();
            assertAsString("6", list.collect(sum.longCollectorToLongPlus()));
            Supplier<StringBuffer> supplier = () -> new StringBuffer();
            ObjLongConsumer<StringBuffer> accumulator = (buffer, i) -> buffer.append(i);
            BiConsumer<StringBuffer, StringBuffer> combiner = (b1, b2) -> b1.append(b2.toString());
            assertAsString("123", list.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testSize() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("4", list.size());
        });
    }
    
    @Test
    public void testCount() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("4", list.count());
        });
    }
    
    @Test
    public void testSum() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("6", list.sum());
        });
    }
    
    @Test
    public void testProduct() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[6]", list.product());
        });
    }
    
    @Test
    public void testMinMax() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalLong[1]", list.min());
            assertAsString("OptionalLong[4]", list.max());
        });
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(1,4)]", list.minMax());
        });
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(4,1)]", list.minMax((a, b) -> (int) (b - a)));
        });
    }
    
    @Test
    public void testMinByMaxBy() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalLong[1]", list.minBy(a -> a));
            assertAsString("OptionalLong[4]", list.maxBy(a -> a));
            assertAsString("OptionalLong[4]", list.minBy(a -> -a));
            assertAsString("OptionalLong[1]", list.maxBy(a -> -a));
        });
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalLong[1]", list.minBy(a -> a, (a, b) -> (int) (a - b)));
            assertAsString("OptionalLong[4]", list.maxBy(a -> a, (a, b) -> (int) (a - b)));
            assertAsString("OptionalLong[4]", list.minBy(a -> -a, (a, b) -> (int) (a - b)));
            assertAsString("OptionalLong[1]", list.maxBy(a -> -a, (a, b) -> (int) (a - b)));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(1,4)]", list.minMaxBy(a -> a));
        });
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(4,1)]", list.minMaxBy(a -> a, (a, b) -> (int) (b - a)));
        });
    }
    
    @Test
    public void testMinOfMaxOf() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalLong[1]", list.minOf(a -> a));
            assertAsString("OptionalLong[4]", list.maxOf(a -> a));
            assertAsString("OptionalLong[4]", list.minOf(a -> -a));
            assertAsString("OptionalLong[1]", list.maxOf(a -> -a));
        });
    }
    
    @Test
    public void testMinIndex() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[0]", list.minIndex());
        });
    }
    
    @Test
    public void testMaxIndex() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[5]", list.maxIndex());
        });
    }
    
    @Test
    public void testMinIndexBy() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[0]", list.minIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMinIndexOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            LongPredicate condition = value -> value > 2;
            LongUnaryOperator operator = value -> value;
            assertAsString("OptionalInt[2]", list.minIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testMaxIndexBy() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[5]", list.maxIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMaxIndexOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            LongPredicate condition = value -> value > 2;
            LongUnaryOperator operator = value -> value;
            assertAsString("OptionalInt[5]", list.maxIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testAnyMatch() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.anyMatch(value -> value == One));
        });
    }
    
    @Test
    public void testAllMatch() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertFalse(list.allMatch(value -> value == One));
        });
    }
    
    @Test
    public void testNoneMatch() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertTrue(list.noneMatch(value -> value == Five));
        });
    }
    
    @Test
    public void testFindFirst() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[1]", list.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[1]", list.findAny());
        });
    }
    
    @Test
    public void testFindLast() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[3]", list.findLast());
        });
    }
    
    @Test
    public void testFirstResult() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[1]", list.first());
        });
    }
    
    @Test
    public void testLastResult() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalLong[3]", list.last());
        });
    }
    
    @Test
    public void testJavaList_get() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertEquals(One, list.get(0));
            assertEquals(Two, list.get(1));
            assertEquals(Three, list.get(2));
        });
    }
    
    @Test
    public void testJavaList_indexOf() {
        run(LongFuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals(1, list.indexOf(Two));
            assertEquals(-1, list.indexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_lastIndexOf() {
        run(LongFuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals(3, list.lastIndexOf(Two));
            assertEquals(-1, list.lastIndexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_subList() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 3]", list.subList(1, 3));
            assertAsString("[2, 3, 4, 5]", list.subList(1, 10));
        });
    }
    
    // -- LongFuncListWithGroupingBy --
    @Test
    public void testGroupingBy() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("{1:[1, 2], 2:[3, 4], 3:[5]}", list.groupingBy(theLong.dividedBy(2).asLong()).sortedByKey(theLong));
        });
    }
    
    @Test
    public void testGroupingBy_aggregate() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("{1:[1.0, 2.0], 2:[3.0, 4.0], 3:[5.0]}", list.groupingBy(theLong.dividedBy(2).asLong(), l -> l.mapToDouble()).sortedByKey(theLong));
        });
    }
    
    // 
    // @Test
    // public void testGroupingBy_collect() {
    // run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
    // assertAsString(
    // //                    "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before sum
    // "{1:3, 2:7, 3:5}",
    // list
    // .groupingBy(theLong.dividedBy(2).asLong(), () -> new Sum())
    // .sortedByKey(theLong));
    // });
    // }
    // 
    // @Test
    // public void testGroupingBy_process() {
    // run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
    // val sumHalf = new SumHalf();
    // assertAsString(
    // //                  "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before half
    // //                  "{1:[0, 1], 2:[1, 2], 3:[2]}",  << Half
    // "{1:1, 2:3, 3:2}",
    // list
    // .groupingBy(theLong.dividedBy(2).asLong(), sumHalf)
    // .sortedByKey(theLong));
    // });
    // }
    // 
    // -- Functional list
    @Test
    public void testMapToString() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list.mapToString());
        });
    }
    
    @Test
    public void testMap() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 4, 6, 8, 10]", list.map(theLong.time(2)));
        });
    }
    
    @Test
    public void testMapToInt() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[10, 20, 30]", list.map(theLong.time(10)));
        });
    }
    
    @Test
    public void testMapToDouble() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1.0, 1.4142135623730951, 1.7320508075688772]", list.mapToDouble(theLong.squareRoot()));
        });
    }
    
    @Test
    public void testMapToObj() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[-1-, -2-, -3-, -4-, -5-]", list.mapToObj(i -> "-" + i + "-"));
        });
    }
    
    // -- FlatMap --
    @Test
    public void testFlatMap() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]", list.flatMap(i -> LongFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]", list.flatMapToInt(i -> IntFuncList.cycle((int) i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToDouble() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0]", list.flatMapToDouble(i -> DoubleFuncList.cycle(i).limit(i)));
        });
    }
    
    // -- Filter --
    @Test
    public void testFilter() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[3]", list.filter(theLong.time(2).thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testFilter_mapper() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[3]", list.filter(theLong.time(2), theLong.thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testPeek() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            assertAsString("[1, 2, 3]", list.peek(i -> logs.add("" + i)));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3]", list.limit(3));
        });
    }
    
    @Test
    public void testSkip() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[3, 4, 5]", list.skip(2));
        });
    }
    
    @Test
    public void testDistinct() {
        run(LongFuncList.of(One, Two, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.distinct());
        });
    }
    
    @Test
    public void testSorted() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 4, 6, 8, 10]", list.map(theLong.time(2)).sorted());
            assertAsString("[10, 8, 6, 4, 2]", list.map(theLong.time(2)).sorted((a, b) -> (int) (b - a)));
        });
    }
    
    @Test
    public void testBoxed() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testToArray() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", Arrays.toString(list.toArray()));
        });
    }
    
    @Test
    public void testNullableOptionalResult() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("Nullable.of([1, 2, 3])", list.__nullable());
            assertAsString("Optional[[1, 2, 3]]", list.__optional());
            assertAsString("Result:{ Value: [1, 2, 3] }", list.__result());
        });
    }
    
    @Test
    public void testIndexOf() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Three), list -> {
            assertAsString("2", list.indexOf(Three));
        });
    }
    
    @Test
    public void testLastIndexOf() {
        run(LongFuncList.of(Three, One, Two, Three, Four, Five), list -> {
            assertAsString("3", list.lastIndexOf(Three));
        });
    }
    
    @Test
    public void testIndexesOf() {
        run(LongFuncList.of(One, Two, Three, Four, Two), list -> {
            assertAsString("[0, 2]", list.indexesOf(value -> value == One || value == Three));
            assertAsString("[1, 4]", list.indexesOf(Two));
        });
    }
    
    @Test
    public void testToBuilder() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list.toBuilder().add(Four).add(Five).build());
        });
    }
    
    @Test
    public void testFirst() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[1]", list.first());
            assertAsString("[1, 2, 3]", list.first(3));
        });
    }
    
    @Test
    public void testLast() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[5]", list.last());
            assertAsString("[3, 4, 5]", list.last(3));
        });
    }
    
    @Test
    public void testAt() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.at(2));
            assertAsString("OptionalLong.empty", list.at(10));
        });
    }
    
    @Test
    public void testTail() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 3, 4, 5]", list.tail());
        });
    }
    
    @Test
    public void testAppend() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[1, 2, 3, 4]", list.append(Four));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testAppendAll() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(Four, Five));
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(LongFuncList.listOf(Four, Five)));
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(LongFuncList.of(Four, Five)));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testPrepend() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[0, 1, 2, 3]", list.prepend(Zero));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testPrependAll() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(MinusOne, Zero));
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(LongFuncList.listOf(MinusOne, Zero)));
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(LongFuncList.of(MinusOne, Zero)));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testWith() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[1, 0, 3]", list.with(1, Zero));
            assertAsString("[1, 102, 3]", list.with(1, value -> value + 100));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testInsertAt() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[1, 0, 2, 3]", list.insertAt(1, Zero));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testInsertAllAt() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
            assertAsString("[1, 2, 0, 0, 3]", list.insertAt(2, Zero, Zero));
            assertAsString("[1, 2, 0, 0, 3]", list.insertAllAt(2, LongFuncList.listOf(Zero, Zero)));
            assertAsString("[1, 2, 0, 0, 3]", list.insertAllAt(2, LongFuncList.of(Zero, Zero)));
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testExclude() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list);
            assertAsString("[1, 3, 4, 5]", list.exclude(Two));
            assertAsString("[1, 3, 4, 5]", list.exclude(theLong.eq(Two)));
            assertAsString("[1, 3, 4, 5]", list.excludeAt(1));
            assertAsString("[1, 5]", list.excludeFrom(1, 3));
            assertAsString("[1, 4, 5]", list.excludeBetween(1, 3));
            assertAsString("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testReverse() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list);
            assertAsString("[5, 4, 3, 2, 1]", list.reverse());
            assertAsString("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testShuffle() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
            assertNotEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.shuffle().toString());
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
        });
    }
    
    @Test
    public void testQuery() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("[1, 2, 3, 4, 5, 6]", list);
            assertAsString("[(2,3), (5,6)]", list.query(theLong.thatIsDivisibleBy(3)));
            assertAsString("[1, 2, 3, 4, 5, 6]", list);
        });
    }
    
    // -- AsLongFuncListWithConversion --
    @Test
    public void testToArrayList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val newList = list.toArrayList();
            assertAsString("[1, 2, 3]", newList);
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    @Test
    public void testToList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val newList = list.toJavaList();
            assertAsString("[1, 2, 3]", newList);
            assertTrue(newList instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val newList = list.toMutableList();
            assertAsString("[1, 2, 3]", newList);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    // -- join --
    @Test
    public void testJoin() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("123", list.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("1, 2, 3", list.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    // -- toMap --
    @Test
    public void testToMap() {
        run(LongFuncList.of(One, Three, Five), list -> {
            assertAsString("{1:1, 3:3, 5:5}", list.toMap(theLong));
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(LongFuncList.of(One, Three, Five), list -> {
            assertAsString("{1:1, 3:9, 5:25}", list.toMap(theLong, theLong.square()));
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(LongFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1+3+5
            assertAsString("{0:2, 1:9}", list.toMap(theLong.remainderBy(2), theLong, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(LongFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1*3*5
            assertAsString("{0:2, 1:15}", list.toMap(theLong.remainderBy(2), (a, b) -> a * b));
        });
    }
    
    @Test
    public void testToSet() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val set = list.toSet();
            assertAsString("[1, 2, 3]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(LongFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertAsString("[0:1, 1:2, 2:3]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new long[5];
            list.populateArray(array);
            assertAsString("[1, 2, 3, 4, 5]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new long[3];
            list.populateArray(array, 2);
            assertAsString("[0, 0, 1]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            val array = new long[5];
            list.populateArray(array, 1, 3);
            assertAsString("[0, 1, 2, 3, 0]", Arrays.toString(array));
        });
    }
    
    // -- AsLongFuncListWithMatch --
    @Test
    public void testFindFirst_withPredicate() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.findFirst(theLong.square().thatGreaterThan(theLong.time(2))));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.findFirst(theLong.square().thatGreaterThan(theLong.time(2))));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.findFirst(theLong.square(), theLong.thatGreaterThan(5)));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.findAny(theLong.square(), theLong.thatGreaterThan(5)));
        });
    }
    
    // -- AsFuncLongWithStatistic --
    @Test
    public void testMinBy() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.minBy(theLong.minus(3).square()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.maxBy(theLong.minus(3).square().negate()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalLong[6]", list.minBy(theLong.minus(3).square(), theLong.inReverseOrder()));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalLong[3]", list.maxBy(theLong.minus(3).square(), theLong.inReverseOrder()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("Optional[(3,6)]", list.minMaxBy(theLong.minus(3).square()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("Optional[(1,3)]", list.minMaxBy(theLong.minus(3).square(), theLong.inReverseOrder()));
        });
    }
    
    // -- LongFuncListWithCalculate --
    static class SumHalf extends LongAggregation<Long> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], Long> collectorPlus = new LongCollectorPlus<long[], Long>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] += i;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { a1[0] + a2[0] };
            }
    
            @Override
            public Function<long[], Long> finisher() {
                return (a) -> a[0] / 2;
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], Long> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, Long> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    static class Average extends LongAggregation<OptionalDouble> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], OptionalDouble> collectorPlus = new LongCollectorPlus<long[], OptionalDouble>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0, 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] += i;
                    a[1] += 1;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { a1[0] + a2[0], a1[1] + a2[1] };
            }
    
            @Override
            public Function<long[], OptionalDouble> finisher() {
                return (a) -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]);
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], OptionalDouble> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, OptionalDouble> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    static class MinLong extends LongAggregation<OptionalLong> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], OptionalLong> collectorPlus = new LongCollectorPlus<long[], OptionalLong>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0, 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] = Math.min(i, a[0]);
                    a[1] = 1;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { Math.min(a1[0], a2[0]), a1[1] + a2[1] };
            }
    
            @Override
            public Function<long[], OptionalLong> finisher() {
                return (a) -> (a[1] == 0) ? OptionalLong.empty() : OptionalLong.of(a[0]);
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], OptionalLong> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, OptionalLong> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    static class MaxLong extends LongAggregation<OptionalLong> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], OptionalLong> collectorPlus = new LongCollectorPlus<long[], OptionalLong>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0, 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] = Math.max(i, a[0]);
                    a[1] = 1;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { Math.max(a1[0], a2[0]), a1[1] + a2[1] };
            }
    
            @Override
            public Function<long[], OptionalLong> finisher() {
                return (a) -> (a[1] == 0) ? OptionalLong.empty() : OptionalLong.of(a[0]);
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], OptionalLong> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, OptionalLong> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    static class SumLong extends LongAggregation<Long> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], Long> collectorPlus = new LongCollectorPlus<long[], Long>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] += i;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { a1[0] + a2[0] };
            }
    
            @Override
            public Function<long[], Long> finisher() {
                return (a) -> a[0];
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], Long> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, Long> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    static class AvgLong extends LongAggregation<OptionalLong> {
    
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
    
        private LongCollectorPlus<long[], OptionalLong> collectorPlus = new LongCollectorPlus<long[], OptionalLong>() {
    
            @Override
            public Supplier<long[]> supplier() {
                return () -> new long[] { 0, 0 };
            }
    
            @Override
            public ObjLongConsumer<long[]> longAccumulator() {
                return (a, i) -> {
                    a[0] += i;
                    a[1] += 1;
                };
            }
    
            @Override
            public BinaryOperator<long[]> combiner() {
                return (a1, a2) -> new long[] { a1[0] + a2[0], a1[1] + a2[1] };
            }
    
            @Override
            public Function<long[], OptionalLong> finisher() {
                return (a) -> (a[1] == 0) ? OptionalLong.empty() : OptionalLong.of(a[0] / a[1]);
            }
    
            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
    
            @Override
            public Collector<Long, long[], OptionalLong> collector() {
                return this;
            }
        };
    
        @Override
        public LongCollectorPlus<?, OptionalLong> longCollectorPlus() {
            return collectorPlus;
        }
    }
    
    @Test
    public void testCalculate() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            assertAsString("10", list.calculate(sumHalf).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            assertAsString("(10," + "OptionalDouble[20.0])", list.calculate(sumHalf, average));
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val range = list.calculate(minLong, maxLong).mapTo((max, min) -> max.getAsLong() + min.getAsLong());
            assertAsString("11", range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            assertAsString("(10," + "OptionalDouble[20.0]," + "OptionalLong[0])", list.calculate(sumHalf, average, minLong));
        });
    }
    
    @Test
    public void testCalculate3_combine() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val value = list.calculate(sumHalf, average, minLong).mapTo((sumH, avg, min) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min);
            assertAsString("sumH: 10, " + "avg: OptionalDouble[20.0], " + "min: OptionalLong[0]", value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            assertAsString("(10," + "OptionalDouble[20.0]," + "OptionalLong[0]," + "OptionalLong[11])", list.calculate(sumHalf, average, minLong, maxLong));
        });
    }
    
    @Test
    public void testCalculate4_combine() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val value = list.calculate(sumHalf, average, minLong, maxLong).mapTo((sumH, avg, min, max) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max);
            assertAsString("sumH: 10, " + "avg: OptionalDouble[20.0], " + "min: OptionalLong[0], " + "max: OptionalLong[11]", value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val sumLong = new SumLong();
            assertAsString("(10," + "OptionalDouble[20.0]," + "OptionalLong[0]," + "OptionalLong[11],20)", list.calculate(sumHalf, average, minLong, maxLong, sumLong));
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val sumLong = new SumLong();
            val value = list.calculate(sumHalf, average, minLong, maxLong, sumLong).mapTo((sumH, avg, min, max, sumI) -> {
                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI;
            });
            assertAsString("sumH: 10, " + "avg: OptionalDouble[20.0], " + "min: OptionalLong[0], " + "max: OptionalLong[11], " + "max: OptionalLong[11], " + "sumI: 20", value);
        });
    }
    
    @Test
    public void testCalculate6() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val sumLong = new SumLong();
            val avgLong = new AvgLong();
            assertAsString("(10," + "OptionalDouble[20.0]," + "OptionalLong[0]," + "OptionalLong[11]," + "20," + "OptionalLong[5])", list.calculate(sumHalf, average, minLong, maxLong, sumLong, avgLong));
        });
    }
    
    @Test
    public void testCalculate6_combine() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minLong = new MinLong();
            val maxLong = new MaxLong();
            val sumLong = new SumLong();
            val avgLong = new AvgLong();
            val value = list.calculate(sumHalf, average, minLong, maxLong, sumLong, avgLong).mapTo((sumH, avg, min, max, sumI, avgI) -> {
                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI + ", avgI: " + avgI;
            });
            assertAsString("sumH: 10, " + "avg: OptionalDouble[20.0], " + "min: OptionalLong[0], " + "max: OptionalLong[11], " + "max: OptionalLong[11], " + "sumI: 20, " + "avgI: OptionalLong[5]", value);
        });
    }
    
    @Test
    public void testCalculate_of() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val sum = new Sum();
            // 2*2 + 3*2 + 4*2 + 11*2
            // 4   + 6   + 8   + 22
            assertAsString("40", list.calculate(sum.ofLong(theLong.time(2))));
        });
    }
    
    // -- FuncListWithCombine --
    @Test
    public void testAppendWith() {
        run(LongFuncList.of(One, Two), LongFuncList.of(Three, Four), (list1, list2) -> {
            assertAsString("[1, 2, 3, 4]", list1.appendWith(list2));
        });
    }
    
    @Test
    public void testParependWith() {
        run(LongFuncList.of(One, Two), LongFuncList.of(Three, Four), (list1, list2) -> {
            assertAsString("[1, 2, 3, 4]", list2.prependWith(list1));
        });
    }
    
    @Test
    public void testMerge() {
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (list1, streamabl2) -> {
            assertAsString("100, 0, 200, 1, 300, 2, 3, 4, 5, 6", list1.mergeWith(streamabl2).limit(10).join(", "));
        });
    }
    
    @Test
    public void testZipWith() {
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString("(100,0), (200,1), (300,2)", listA.zipWith(listB).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7  8  9
            "(100,0), (200,1), (300,2), (-1,3), (-1,4), (-1,5), (-1,6), (-1,7), (-1,8), (-1,9)", listA.zipWith(listB, -1).join(", "));
        });
        run(LongFuncList.of(100, 200, 300, 400, 500), LongFuncList.infinite().limit(3), (listA, listB) -> {
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7  8  9
            "(100,0), (200,1), (300,2), (400,-1), (500,-1)", listA.zipWith(-100, listB, -1).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300
            // 0   1    2
            "100, 201, 302", listA.zipWith(listB, (iA, iB) -> iA + iB).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7  8  9
            "100, 201, 302, 2, 3, 4, 5, 6, 7, 8", listA.zipWith(listB, -1, (iA, iB) -> iA + iB).join(", "));
        });
        run(LongFuncList.of(100, 200, 300, 400, 500), LongFuncList.infinite().limit(3), (listA, listB) -> {
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7  8  9
            "10000, 20001, 30002, 39999, 49999", listA.zipWith(-100, listB, -1, (a, b) -> a * 100 + b).join(", "));
        });
    }
    
    @Test
    public void testZipWith_object() {
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString("(100,0), (200,1), (300,2)", listA.zipWith(listB.boxed()).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString("(100,0), (200,1), (300,2), (-1,3), (-1,4), (-1,5), (-1,6), (-1,7), (-1,8), (-1,9)", listA.zipWith(-1, listB.boxed()).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString("100->0, 200->1, 300->2", listA.zipWith(listB.boxed(), (a, b) -> a + "->" + b).join(", "));
        });
        run(LongFuncList.of(100, 200, 300, 400, 500), LongFuncList.infinite().limit(3), (listA, listB) -> {
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7  8  9
            "10000, 20001, 30002, 39999, 49999", listA.zipWith(-100, listB, -1, (a, b) -> a * 100 + b).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300
            // 0   1    2
            "100<->0, 200<->1, 300<->2", listA.zipToObjWith(listB, (iA, iB) -> iA + "<->" + iB).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300
            // 0   1    2
            "100<->0, 200<->1, 300<->2, -100<->3, -100<->4, -100<->5, -100<->6, -100<->7, -100<->8, -100<->9", listA.zipToObjWith(-100, listB, -1, (iA, iB) -> iA + "<->" + iB).join(", "));
        });
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            assertAsString(// 100 200  300
            // 0   1    2
            "100<->0, 200<->1, 300<->2, -100<->3, -100<->4, -100<->5, -100<->6, -100<->7, -100<->8, -100<->9", listA.zipToObjWith(-100, listB.boxed(), (iA, iB) -> iA + "<->" + iB).join(", "));
        });
    }
    
    @Test
    public void testChoose() {
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            val bool = new AtomicBoolean(true);
            assertAsString("100, 1, 300, 3, 4", listA.choose(listB, (a, b) -> {
                // This logic which to choose from one then another
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(5).join(", "));
        });
    }
    
    @Test
    public void testChoose_AllowUnpaired() {
        run(LongFuncList.of(100, 200, 300), LongFuncList.infinite().limit(10), (listA, listB) -> {
            val bool = new AtomicBoolean(true);
            assertAsString(// 100 200  300 -1 -1 -1 -1 -1
            // 0   1    2  3  4  5  6  7
            "100, 1, 300, 3, 4, 5, 6", listA.choose(listB, AllowUnpaired, (a, b) -> {
                // This logic which to choose from one then another
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(7).join(", "));
        });
    }
    
    // -- IntStreamPlusWithFilter --
    @Test
    public void testFilter_withMappter() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 5]", list.filter(theLong.square(), theLong.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsInt() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 5]", list.filterAsInt(theLong.square().asInteger(), theInteger.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsLong() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 5]", list.filterAsLong(theLong.square().asLong(), theLong.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsDouble() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 5]", list.filterAsDouble(theLong.square().asDouble(), theDouble.asInteger().thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsObject() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 5]", list.filterAsObject(i -> "" + i, s -> (Integer.parseInt(s) % 2) == 1));
        });
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            LongFunction<String> mapper = i -> "" + i;
            Predicate<String> checker = s -> (Integer.parseInt(s) % 2) == 1;
            assertAsString("[1, 3, 5]", list.filterAsObject(mapper, checker));
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[4]", list.filterWithIndex((index, value) -> (index > 2) && (value < 5)));
        });
    }
    
    @Test
    public void testFilterNonNull() {
        val cars = new Car[] { new Car("Blue"), new Car("Green"), null, new Car(null), new Car("Red") };
        run(LongFuncList.wholeNumbers(cars.length), list -> {
            assertAsString("[0, 1, 3, 4]", list.filterNonNull(i -> cars[(int) i]));
        });
    }
    
    @Test
    public void testExcludeNull() {
        val cars = new Car[] { new Car("Blue"), new Car("Green"), null, new Car(null), new Car("Red") };
        run(LongFuncList.wholeNumbers(cars.length), list -> {
            assertAsString("[0, 1, 3, 4]", list.excludeNull(i -> cars[(int) i]));
        });
    }
    
    @Test
    public void testFilterIn_array() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 5]", list.filterIn(Two, Five));
        });
    }
    
    @Test
    public void testExcludeIn_array() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 4]", list.excludeIn(Two, Five));
        });
    }
    
    @Test
    public void testFilterIn_funcList() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 5]", list.filterIn(LongFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_funcList() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 4]", list.excludeIn(LongFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testFilterIn_collection() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 5]", list.filterIn(Arrays.asList(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_collection() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 3, 4]", list.excludeIn(Arrays.asList(Two, Five)));
        });
    }
    
    // -- FuncListWithFlatMap --
    @Test
    public void testFlatMapOnly() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3, 3, 3]", list.flatMapOnly(theLong.thatIsOdd(), i -> LongFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapIf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, -2, -2, 3, 3, 3]", list.flatMapIf(theLong.thatIsOdd(), i -> LongFuncList.cycle(i).limit(i), i -> LongFuncList.cycle(-i).limit(i)));
        });
    }
    
    // -- FuncListWithLimit --
    @Test
    public void testSkipLimitLong() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[2]", list.skip((Long) 1L).limit((Long) 1L));
        });
    }
    
    @Test
    public void testSkipLimitLongNull() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.skip(null).limit(null));
        });
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
        });
    }
    
    @Test
    public void testSkipWhile() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i > 3));
            assertAsString("[5, 4, 3, 2, 1]", list.skipWhile((p, e) -> p == e + 1));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testSkipUntil() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i > 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil((p, e) -> p == e + 1));
            assertAsString("[5, 4, 3, 2, 1]", list.skipUntil((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testacceptWhile() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Long>();
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
        run(LongFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.acceptWhile((a, b) -> b == a + 1));
        });
    }
    
    @Test
    public void testTakeUtil() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Long>();
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
        run(LongFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.acceptUntil((a, b) -> b > a + 1));
        });
    }
    
    @Test
    public void testDropAfter() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.dropAfter(i -> i == 4));
            // ^--- Include 4
        });
    }
    
    @Test
    public void testDropAfter_previous() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4, 5, 4]", list.dropAfter((a, b) -> b < a));
            // ^--- Include 4
        });
    }
    
    @Test
    public void testSkipTake() {
        run(LongFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Long>();
            assertAsString("[3, 4, 5, 4, 3]", list.peek(logs::add).skipWhile(i -> i < 3).acceptUntil(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2]", logs);
            // ^--^-----------------^--- Because it needs these number to do the check in `skipWhile` and `acceptWhile`
        });
    }
    
    // -- FuncListWithMap --
    @Test
    public void testMapOnly() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 9]", list.mapOnly(theLong.thatIsOdd(), theLong.square()));
        });
    }
    
    @Test
    public void testMapIf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 1, 9]", list.mapIf(theLong.thatIsOdd(), theLong.square(), theLong.squareRoot().round().asLong()));
        });
    }
    
    @Test
    public void testMapToObjIf() {
        run(LongFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 1, 9]", list.mapToObjIf(theLong.thatIsOdd(), theLong.square().asString(), theLong.squareRoot().round().asInteger().asString()));
        });
    }
    
    // == Map First ==
    @Test
    public void testMapFirst_2() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[1, 2, Three, 4, 5, 6, 7, 8, 9, 10, 11, 12]", list.mapFirst(i -> i == 3 ? "Three" : null, i -> "" + i));
        });
    }
    
    @Test
    public void testMapFirst_3() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[1, 2, Three, 4, 5, 6, Seven, 8, 9, 10, 11, 12]", list.mapFirst(i -> i == 3 ? "Three" : null, i -> i == 7 ? "Seven" : null, i -> "" + i));
        });
    }
    
    @Test
    public void testMapFirst_4() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[1, 2, Three, 4, 5, 6, Seven, 8, 9, 10, Eleven, 12]", list.mapFirst(i -> i == 3 ? "Three" : null, i -> i == 7 ? "Seven" : null, i -> i == 11 ? "Eleven" : null, i -> "" + i));
        });
    }
    
    @Test
    public void testMapFirst_5() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[One, 2, Three, 4, 5, 6, Seven, 8, 9, 10, Eleven, 12]", list.mapFirst(i -> i == 3 ? "Three" : null, i -> i == 7 ? "Seven" : null, i -> i == 11 ? "Eleven" : null, i -> i == 1 ? "One" : null, i -> "" + i));
        });
    }
    
    @Test
    public void testMapFirst_6() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[One, 2, Three, 4, Five, 6, Seven, 8, 9, 10, Eleven, 12]", list.mapFirst(i -> i == 3 ? "Three" : null, i -> i == 7 ? "Seven" : null, i -> i == 11 ? "Eleven" : null, i -> i == 1 ? "One" : null, i -> i == 5 ? "Five" : null, i -> "" + i));
        });
    }
    
    // == MapThen ==
    @Test
    public void testMapThen_2() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1-2, 2-3, 3-4, 4-5, 5-6]", list.mapThen(theLong, theLong.plus(1), (a, b) -> a + "-" + b));
        });
    }
    
    @Test
    public void testMapThen_3() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1-2-3, 2-3-6, 3-4-9, 4-5-12, 5-6-15]", list.mapThen(theLong, theLong.plus(1), theLong.time(3), (a, b, c) -> a + "-" + b + "-" + c));
        });
    }
    
    @Test
    public void testMapThen_4() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1-2-3-1, 2-3-6-4, 3-4-9-9, 4-5-12-16, 5-6-15-25]", list.mapThen(theLong, theLong.plus(1), theLong.time(3), theLong.square(), (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d));
        });
    }
    
    @Test
    public void testMapThen_5() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1-2-3-1-1, 2-3-6-4-2, 3-4-9-9-6, 4-5-12-16-24, 5-6-15-25-120]", list.mapThen(theLong, theLong.plus(1), theLong.time(3), theLong.square(), theLong.factorial(), (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e));
        });
    }
    
    @Test
    public void testMapThen_6() {
        run(LongFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1-2-3-1-1--1, 2-3-6-4-2--2, 3-4-9-9-6--3, 4-5-12-16-24--4, 5-6-15-25-120--5]", list.mapThen(theLong, theLong.plus(1), theLong.time(3), theLong.square(), theLong.factorial(), theLong.negate(), (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f));
        });
    }
    
    // -- FuncListWithMapGroup --
    // @Test
    // public void testMapGroup_specific() {
    // run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
    // assertAsString(
    // "[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]",
    // list.mapGroup((a,b) -> a+":"+b));
    // assertAsString(
    // "[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]",
    // list.mapGroup((a,b,c) -> a+":"+b+":"+c));
    // assertAsString(
    // "[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]",
    // list.mapGroup((a,b,c,d) -> a+":"+b+":"+c+":"+d));
    // assertAsString(
    // "[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]",
    // list.mapGroup((a,b,c,d,e) -> a+":"+b+":"+c+":"+d+":"+e));
    // assertAsString(
    // "[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]",
    // list.mapGroup((a,b,c,d,e,f) -> a+":"+b+":"+c+":"+d+":"+e+":"+f));
    // });
    // }
    // 
    @Test
    public void testMapGroup_count() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            ToLongFunction<LongStreamPlus> joiner = longStream -> Long.parseLong(longStream.mapToString().join());
            assertAsString("[12, 23, 34, 45, 56]", list.mapGroup(2, joiner));
            assertAsString("[123, 234, 345, 456]", list.mapGroup(3, joiner));
            assertAsString("[1234, 2345, 3456]", list.mapGroup(4, joiner));
            assertAsString("[12345, 23456]", list.mapGroup(5, joiner));
            assertAsString("[123456]", list.mapGroup(6, joiner));
        });
    }
    
    @Test
    public void testMapGroup() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[12, 23, 34, 45, 56, 67, 78]", list.mapTwo((a, b) -> a * 10 + b));
        });
    }
    
    @Test
    public void testMapGroupToInt() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[12, 23, 34, 45, 56, 67, 78]", list.mapTwoToInt((a, b) -> (int) (a * 10 + b)));
            assertAsString("[12, 23, 34, 45, 56, 67, 78]", list.mapGroupToInt(2, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToLong() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[12, 23, 34, 45, 56, 67, 78]", list.mapTwoToLong((a, b) -> a * 10 + b));
            assertAsString("[12, 23, 34, 45, 56, 67, 78]", list.mapGroupToLong(2, ints -> Long.parseLong(ints.mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToDouble() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]", list.mapTwoToDouble((a, b) -> a * 10L + b));
            assertAsString("[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]", list.mapGroupToDouble(2, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToObj() {
        run(LongFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString("[(1,2), (2,3), (3,4), (4,5), (5,6), (6,7), (7,8)]", list.mapTwoToObj());
            assertAsString("[1-2, 2-3, 3-4, 4-5, 5-6, 6-7, 7-8]", list.mapTwoToObj((a, b) -> a + "-" + b));
            assertAsString("[[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6], [5, 6, 7], [6, 7, 8]]", list.mapGroupToObj(3).map(LongStreamPlus::toListString));
            assertAsString("[123, 234, 345, 456, 567, 678]", list.mapGroupToObj(3, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    // -- FuncListWithMapToMap --
    @Test
    public void testMapToMap_1() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1}, " + "{<1>:3}, " + "{<1>:5}, " + "{<1>:7}, " + "{<1>:11}, " + "{<1>:13}, " + "{<1>:17}" + "]", list.mapToMap("<1>", theLong).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_2() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1}, " + "{<1>:3, <2>:-3}, " + "{<1>:5, <2>:-5}, " + "{<1>:7, <2>:-7}, " + "{<1>:11, <2>:-11}, " + "{<1>:13, <2>:-13}, " + "{<1>:17, <2>:-17}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate()).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_3() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2}, " + "{<1>:3, <2>:-3, <3>:4}, " + "{<1>:5, <2>:-5, <3>:6}, " + "{<1>:7, <2>:-7, <3>:8}, " + "{<1>:11, <2>:-11, <3>:12}, " + "{<1>:13, <2>:-13, <3>:14}, " + "{<1>:17, <2>:-17, <3>:18}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_4() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2, <4>:-1}, " + "{<1>:3, <2>:-3, <3>:4, <4>:1}, " + "{<1>:5, <2>:-5, <3>:6, <4>:3}, " + "{<1>:7, <2>:-7, <3>:8, <4>:5}, " + "{<1>:11, <2>:-11, <3>:12, <4>:9}, " + "{<1>:13, <2>:-13, <3>:14, <4>:11}, " + "{<1>:17, <2>:-17, <3>:18, <4>:15}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_5() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3}, " + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9}, " + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15}, " + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21}, " + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33}, " + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39}, " + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3)).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_6() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1}, " + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81}, " + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625}, " + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401}, " + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641}, " + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561}, " + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3), "<6>", theLong.pow(4).asInteger()).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_7() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1}, " + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9}, " + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25}, " + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49}, " + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121}, " + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169}, " + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3), "<6>", theLong.pow(4).asInteger(), "<7>", theLong.square()).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_8() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1},\n" + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2},\n" + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2},\n" + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3},\n" + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3},\n" + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4},\n" + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4}", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3), "<6>", theLong.pow(4).asInteger(), "<7>", theLong.square(), "<8>", theLong.squareRoot().asInteger()).map(map -> map.sorted()).join(",\n"));
        });
    }
    
    @Test
    public void testMapToMap_9() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString("[" + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1, <9>:1}, " + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2, <9>:6}, " + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2, <9>:120}, " + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3, <9>:5040}, " + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3, <9>:39916800}, " + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4, <9>:6227020800}, " + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4, <9>:355687428096000}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3), "<6>", theLong.pow(4).asInteger(), "<7>", theLong.square(), "<8>", theLong.squareRoot().asInteger(), "<9>", theLong.factorial()).map(map -> map.sorted()));
        });
    }
    
    @Test
    public void testMapToMap_10() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
            assertAsString("[" + "{<10>:1, <1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1, <9>:1}, " + "{<10>:2, <1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2, <9>:6}, " + "{<10>:3, <1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2, <9>:120}, " + "{<10>:4, <1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3, <9>:5040}, " + "{<10>:6, <1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3, <9>:39916800}, " + "{<10>:7, <1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4, <9>:6227020800}, " + "{<10>:9, <1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4, <9>:355687428096000}, " + "{<10>:10, <1>:19, <2>:-19, <3>:20, <4>:17, <5>:57, <6>:130321, <7>:361, <8>:4, <9>:121645100408832000}, " + "{<10>:12, <1>:23, <2>:-23, <3>:24, <4>:21, <5>:69, <6>:279841, <7>:529, <8>:5, <9>:8128291617894825984}" + "]", list.mapToMap("<1>", theLong, "<2>", theLong.negate(), "<3>", theLong.plus(1), "<4>", theLong.minus(2), "<5>", theLong.time(3), "<6>", theLong.pow(4).asInteger(), "<7>", theLong.square(), "<8>", theLong.squareRoot().asInteger(), "<9>", theLong.factorial(), "<10>", theLong.dividedBy(2).asInteger()).map(map -> map.sorted()));
        });
    }
    
    // -- FuncListWithMapToTuple --
    @Test
    public void testMapToTuple_2() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[" + "(1,2), " + "(3,4), " + "(5,6), " + "(7,8), " + "(11,12)" + "]", list.mapToTuple(theLong, theLong.plus(1)));
        });
    }
    
    @Test
    public void testMapToTuple_3() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[" + "(1,2,3), " + "(3,4,9), " + "(5,6,15), " + "(7,8,21), " + "(11,12,33)" + "]", list.mapToTuple(theLong, theLong.plus(1), theLong.time(3)));
        });
    }
    
    @Test
    public void testMapToTuple_4() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[" + "(1,2,3,1), " + "(3,4,9,9), " + "(5,6,15,25), " + "(7,8,21,49), " + "(11,12,33,121)" + "]", list.mapToTuple(theLong, theLong.plus(1), theLong.time(3), theLong.square()));
        });
    }
    
    @Test
    public void testMapToTuple_5() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[" + "(1,2,3,1,1), " + "(3,4,9,9,6), " + "(5,6,15,25,120), " + "(7,8,21,49,5040), " + "(11,12,33,121,39916800)" + "]", list.mapToTuple(theLong, theLong.plus(1), theLong.time(3), theLong.square(), theLong.factorial()));
        });
    }
    
    @Test
    public void testMapToTuple_6() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[" + "(1,2,3,1,1,-1), " + "(3,4,9,9,6,-3), " + "(5,6,15,25,120,-5), " + "(7,8,21,49,5040,-7), " + "(11,12,33,121,39916800,-11)" + "]", list.mapToTuple(theLong, theLong.plus(1), theLong.time(3), theLong.square(), theLong.factorial(), theLong.negate()));
        });
    }
    
    // -- StreamPlusWithMapWithIndex --
    @Test
    public void testMapWithIndex() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[(0,1), (1,3), (2,5), (3,7), (4,11)]", list.mapWithIndex());
        });
    }
    
    @Test
    public void testMapWithIndex_combine() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[1, 13, 25, 37, 411]", list.mapWithIndex((i, each) -> Integer.parseInt(i + "" + each)));
        });
    }
    
    @Test
    public void testMapToObjWithIndex_combine() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[0: 1, 1: 3, 2: 5, 3: 7, 4: 11]", list.mapToObjWithIndex((i, each) -> i + ": " + each));
            assertAsString("[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]", list.mapWithIndex(i -> i * 2, (i, each) -> i + ": " + each));
            assertAsString("[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]", list.mapWithIndex(i -> i * 2, (i, each) -> i + ": " + each));
            assertAsString("[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]", list.mapToObjWithIndex(i -> "" + i * 2, (i, each) -> i + ": " + each));
        });
    }
    
    // -- FuncListWithModify --
    @Test
    public void testAccumulate() {
        run(LongFuncList.of(1, 2, 3, 4, 5), list -> {
            assertAsString("[1, 3, 6, 10, 15]", list.accumulate((prev, current) -> prev + current));
            assertAsString("[1, 12, 123, 1234, 12345]", list.accumulate((prev, current) -> prev * 10 + current));
        });
    }
    
    @Test
    public void testRestate() {
        run(LongFuncList.wholeNumbers(20).map(i -> i % 5).toFuncList(), list -> {
            assertAsString("[0, 1, 2, 3, 4]", list.restate((head, tail) -> tail.filter(x -> x != head)));
        });
    }
    
    @Test
    public void testRestate_sieveOfEratosthenes() {
        run(LongFuncList.naturalNumbers(300).filter(theLong.thatIsNotOne()).toFuncList(), list -> {
            assertAsString("[" + "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, " + "101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, " + "211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293" + "]", list.restate((head, tail) -> tail.filter(x -> x % head != 0)));
        });
    }
    
    @Test
    public void testSpawn() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first = new AtomicLong(-1);
            val logs = new ArrayList<String>();
            list.spawn(i -> TimeFuncs.Sleep(i * timePrecision + 5).thenReturn(i).defer()).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / (1.0 * timePrecision)) * timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[" + "Result:{ Value: 2 } -- 0, " + "Result:{ Value: 3 } -- " + (1 * timePrecision) + ", " + "Result:{ Value: 4 } -- " + (2 * timePrecision) + ", " + "Result:{ Value: 11 } -- " + (9 * timePrecision) + "" + "]", logs.toString());
        });
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first = new AtomicLong(-1);
            val logs = new ArrayList<String>();
            list.spawn(i -> DeferAction.from(() -> {
                Thread.sleep(i * timePrecision + 5);
                return i;
            })).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / (1.0 * timePrecision)) * timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[" + "Result:{ Value: 2 } -- 0, " + "Result:{ Value: 3 } -- " + (1 * timePrecision) + ", " + "Result:{ Value: 4 } -- " + (2 * timePrecision) + ", " + "Result:{ Value: 11 } -- " + (9 * timePrecision) + "" + "]", logs.toString());
        });
    }
    
    @Test
    public void testSpawn_limit() {
        run(LongFuncList.of(Two, Three, Four, Eleven), list -> {
            val first = new AtomicLong(-1);
            val actions = new ArrayList<DeferAction<Long>>();
            val logs = new ArrayList<String>();
            list.spawn(i -> {
                DeferAction<Long> action = TimeFuncs.Sleep(i * 50 + 5).thenReturn(i).defer();
                actions.add(action);
                return action;
            }).limit(1).forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start = first.get();
                val end = System.currentTimeMillis();
                val duration = Math.round((end - start) / 50.0) * 50;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[Result:{ Value: 2 } -- 0]", logs.toString());
            assertEquals("Result:{ Value: 2 }, " + "Result:{ Cancelled: Stream closed! }, " + "Result:{ Cancelled: Stream closed! }, " + "Result:{ Cancelled: Stream closed! }", actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
        });
    }
    
    // -- FuncListWithPeek --
    @Test
    public void testPeekAs() {
        run(LongFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekAs(e -> "<" + e + ">", e -> elementStrings.add(e)).// To terminate the stream
            join();
            assertAsString("[<0>, <1>, <2>, <3>, <4>, <5>]", elementStrings);
        });
    }
    
    @Test
    public void testPeekBy_map() {
        run(LongFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekBy(s -> !("" + s).contains("2"), e -> elementStrings.add("" + e)).// To terminate the stream
            join();
            assertAsString("[0, 1, 3, 4, 5]", elementStrings);
            elementStrings.clear();
            list.peekBy(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add("" + e)).// To terminate the stream
            join();
            assertAsString("[0, 1, 3, 4, 5]", elementStrings);
        });
    }
    
    @Test
    public void testPeekAs_map() {
        run(LongFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list.peekAs(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add((String) e)).// To terminate the stream
            join();
            assertAsString("[<0>, <1>, <3>, <4>, <5>]", elementStrings);
        });
    }
    
    // -- FuncListWithPipe --
    @Test
    public void testPipeable() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[1, 3, 5, 7, 11]", list.pipable().pipeTo(LongFuncList::toListString));
        });
    }
    
    @Test
    public void testPipe() {
        run(LongFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString("[1, 3, 5, 7, 11]", list.pipe(LongFuncList::toListString));
        });
    }
    
    // -- FuncListWithReshape --
    @Test
    public void testSegment() {
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("[" + "[0, 1, 2, 3, 4, 5], " + "[6, 7, 8, 9, 10, 11], " + "[12, 13, 14, 15, 16, 17], " + "[18, 19]" + "]", list.segment(6).mapToObj(LongFuncList::toString));
        });
    }
    
    @Test
    public void testSegment_sizeFunction() {
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("[" + "[1], " + "[2, 3], " + "[4, 5, 6, 7], " + "[8, 9, 10, 11, 12, 13, 14, 15], " + "[16, 17, 18, 19]" + "]", list.segment(i -> (int) i));
        });
        // Empty
        run(LongFuncList.wholeNumbers(0), list -> {
            assertAsString("[]", list.segment(i -> (int) i));
        });
        // End at exact boundary
        run(LongFuncList.wholeNumbers(8), list -> {
            assertAsString("[" + "[1], " + "[2, 3], " + "[4, 5, 6, 7]" + "]", list.segment(i -> (int) i));
        });
    }
    
    @Test
    public void testSegmentWhen() {
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("[" + "[0, 1, 2], " + "[3, 4, 5], " + "[6, 7, 8], " + "[9, 10, 11], " + "[12, 13, 14], " + "[15, 16, 17], " + "[18, 19]" + "]", list.segmentWhen(theLong.thatIsDivisibleBy(3)).map(LongFuncList::toListString));
        });
    }
    
    @Test
    public void testSegmentAfter() {
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("[" + "[0], " + "[1, 2, 3], " + "[4, 5, 6], " + "[7, 8, 9], " + "[10, 11, 12], " + "[13, 14, 15], " + "[16, 17, 18], " + "[19]" + "]", list.segmentAfter(theLong.thatIsDivisibleBy(3)).map(LongFuncList::toListString));
        });
    }
    
    @Test
    public void testSegmentBetween() {
        LongPredicate startCondition = i -> (i % 10) == 3;
        LongPredicate endCondition = i -> (i % 10) == 6;
        run(LongFuncList.wholeNumbers(75), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66], " + "[73, 74]" + "]", list.segmentBetween(startCondition, endCondition).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66], " + "[73, 74]" + "]", list.segmentBetween(startCondition, endCondition, true).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66], " + "[73, 74]" + "]", list.segmentBetween(startCondition, endCondition, IncompletedSegment.included).skip(5).limit(3));
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, IncompletedSegment.excluded).skip(5).limit(3));
        });
        // Edge cases
        // Empty
        run(LongFuncList.wholeNumbers(0), list -> {
            assertAsString("[]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Not enough
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("[]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact
        run(LongFuncList.wholeNumbers(67), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact - 1
        run(LongFuncList.wholeNumbers(66), list -> {
            assertAsString("[" + "[53, 54, 55, 56]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // Exact + 1
        run(LongFuncList.wholeNumbers(68), list -> {
            assertAsString("[" + "[53, 54, 55, 56], " + "[63, 64, 65, 66]" + "]", list.segmentBetween(startCondition, endCondition, false).skip(5).limit(3));
        });
        // From start
        run(LongFuncList.wholeNumbers(30), list -> {
            assertAsString("[" + "[3, 4, 5, 6], " + "[13, 14, 15, 16], " + "[23, 24, 25, 26]" + "]", list.segmentBetween(startCondition, endCondition, false));
        });
        // Incomplete start
        run(LongFuncList.wholeNumbers(30).skip(5), list -> {
            assertAsString("[" + "[13, 14, 15, 16], " + "[23, 24, 25, 26]" + "]", list.segmentBetween(startCondition, endCondition, false));
        });
    }
    
    @Test
    public void testSegmentByPercentiles() {
        run(LongFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(30, 80));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(30.0, 80.0));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(IntFuncList.of(30, 80)));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper() {
        run(LongFuncList.wholeNumbers(50), list -> {
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, 30, 80));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, 30.0, 80.0));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, LongFuncList.of(30, 80)));
            assertAsString("[" + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], " + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], " + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]" + "]", list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper_comparator() {
        run(LongFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, theLong.inReverseOrder(), 30, 80));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, theLong.inReverseOrder(), 30.0, 80.0));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, theLong.inReverseOrder(), LongFuncList.of(30, 80)));
            assertAsString("[" + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" + "]", list.segmentByPercentiles(x -> 100 - x, theLong.inReverseOrder(), DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    // -- FuncListWithSort --
    @Test
    public void testSortedBy() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[1, 2, 3, 4]", list.sortedBy(theLong.plus(2).square()));
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(LongFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("[4, 3, 2, 1]", list.sortedBy(i -> (i + 2) * (i + 2), theLong.inReverseOrder()));
            // Using comparable access.
            assertAsString("[4, 3, 2, 1]", list.sortedBy(theLong.plus(2).square(), theLong.inReverseOrder()));
        });
    }
    
    // -- FuncListWithSplit --
    @Test
    public void testSplitTuple() {
        run(LongFuncList.wholeNumbers(20).toFuncList(), list -> {
            assertAsString("(" + "[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]," + "[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]" + ")", list.split(theLong.thatIsDivisibleBy(2)).toString());
        });
    }
    
    @Test
    public void testSplit() {
        run(LongFuncList.wholeNumbers(20), list -> {
            String Other = "Other";
            assertAsString("{" + "Other:[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), Other).sorted().toString());
            assertAsString("{" + "Other:[1, 5, 7, 11, 13, 17, 19], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), Other).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Other:[1, 7, 11, 13, 17, 19], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), Other).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Seven:[7], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], " + "Three:[3, 9, 15], " + "Other:[1, 11, 13, 17, 19]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), "Seven", theLong.thatIsDivisibleBy(7), Other).toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Other:[1, 13, 17, 19], " + "Seven:[7], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), "Seven", theLong.thatIsDivisibleBy(7), "Eleven", theLong.thatIsDivisibleBy(11), Other).sorted().toString());
            // Ignore some values
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Other:[1, 13, 17, 19], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), null, theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), null, theLong.thatIsDivisibleBy(7), "Eleven", theLong.thatIsDivisibleBy(11), Other).sorted().toString());
            // Ignore others
            assertAsString("{" + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3)).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5)).sorted().toString());
            assertAsString("{" + "Five:[5], " + "Seven:[7], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), "Seven", theLong.thatIsDivisibleBy(7)).sorted().toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Seven:[7], " + "Three:[3, 9, 15], Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), "Seven", theLong.thatIsDivisibleBy(7), "Eleven", theLong.thatIsDivisibleBy(11)).sorted().toString());
            assertAsString("{" + "Eleven:[11], " + "Five:[5], " + "Seven:[7], " + "Thirteen:[13], " + "Three:[3, 9, 15], " + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]" + "}", list.split("Two", theLong.thatIsDivisibleBy(2), "Three", theLong.thatIsDivisibleBy(3), "Five", theLong.thatIsDivisibleBy(5), "Seven", theLong.thatIsDivisibleBy(7), "Eleven", theLong.thatIsDivisibleBy(11), "Thirteen", theLong.thatIsDivisibleBy(13)).sorted().toString());
        });
    }
    
    @Test
    public void testSplit_ignore() {
        run(LongFuncList.wholeNumbers(20), list -> {
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null, theLong.thatIsDivisibleBy(7), (String) null).toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null, theLong.thatIsDivisibleBy(7), (String) null, theLong.thatIsDivisibleBy(11), (String) null).sorted().toString());
            // No other
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2)).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3)).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5)).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null, theLong.thatIsDivisibleBy(7)).toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null, theLong.thatIsDivisibleBy(7), (String) null, theLong.thatIsDivisibleBy(11)).sorted().toString());
            assertAsString("{}", list.split((String) null, theLong.thatIsDivisibleBy(2), (String) null, theLong.thatIsDivisibleBy(3), (String) null, theLong.thatIsDivisibleBy(5), (String) null, theLong.thatIsDivisibleBy(7), (String) null, theLong.thatIsDivisibleBy(11), (String) null, theLong.thatIsDivisibleBy(13)).sorted().toString());
        });
    }
    
    @Test
    public void testFizzBuzz() {
        Function<LongFuncList, LongFuncList> listToList = s -> s.toImmutableList();
        run(LongFuncList.wholeNumbers(20), list -> {
            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap)).run(() -> {
                FuncMap<String, LongFuncList> splited = list.split("FizzBuzz", i -> i % (3 * 5) == 0, "Buzz", i -> i % 5 == 0, "Fizz", i -> i % 3 == 0, null);
                val string = splited.mapValue(listToList).toString();
                return string;
            });
            assertEquals("{" + "FizzBuzz:[0, 15], " + "Buzz:[5, 10], " + "Fizz:[3, 6, 9, 12, 18]" + "}", toString);
        });
    }
}
