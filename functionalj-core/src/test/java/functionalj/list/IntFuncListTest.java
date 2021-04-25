package functionalj.list;

import static functionalj.TestHelper.assertAsString;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.theDouble;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theLong;
import static functionalj.ref.Run.With;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
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
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import functionalj.function.Accumulator;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.functions.TimeFuncs;
import functionalj.lens.LensTest.Car;
import functionalj.list.FuncList.Mode;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.intlist.IntFuncListBuilder;
import functionalj.list.intlist.IntFuncListDerived;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.intstream.IntAccumulator;
import functionalj.stream.intstream.IntCollectorPlus;
import functionalj.stream.intstream.IntCollectorToIntPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class IntFuncListTest {
    
    static final int MinusOne = -1;
    static final int Zero     =  0;
    
    static final int One         =  1;
    static final int Two         =  2;
    static final int Three       =  3;
    static final int Four        =  4;
    static final int Five        =  5;
    static final int Six         =  6;
    static final int Seven       =  7;
    static final int Eight       =  8;
    static final int Nine        =  9;
    static final int Ten         = 10;
    static final int Eleven      = 11;
    static final int Twelve      = 12;
    static final int Thirteen    = 13;
    static final int Seventeen   = 17;
    static final int Nineteen    = 19;
    static final int TwentyThree = 23;
    
    
    private <T> void run(FuncList<T> list, FuncUnit1<FuncList<T>> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(IntFuncList list, FuncUnit1<IntFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(DoubleFuncList list, FuncUnit1<DoubleFuncList> action) {
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
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList() {
        run(IntFuncList.emptyList(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testEmpty_intFuncList() {
        run(IntFuncList.emptyIntList(), list -> {
            assertAsString("[]", list);
        });
    }
    
    @Test
    public void testOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testAllOf() {
        run(IntFuncList.AllOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testInts() {
        run(IntFuncList.ints(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testIntList() {
        run(IntFuncList.intList(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testListOf() {
        run(IntFuncList.ListOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
        run(IntFuncList.listOf(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    //-- From --
    
    @Test
    public void testFrom_array() {
        run(IntFuncList.from(new int[] {1, 2, 3}), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<Integer> collection = Arrays.asList(One, Two, Three, null);
        run(IntFuncList.from(collection, -1), list -> {
            assertAsString("[1, 2, 3, -1]", list);
        });
        Set<Integer> set = new LinkedHashSet<>(collection);
        run(IntFuncList.from(set, -2), list -> {
            assertAsString("[1, 2, 3, -2]", list);
        });
        FuncList<Integer> lazyList = FuncList.of(One, Two, Three, null);
        run(IntFuncList.from(lazyList, -3), list -> {
            assertAsString("[1, 2, 3, -3]", list);
            assertTrue   (list.isLazy());
        });
        FuncList<Integer> eagerList = FuncList.of(One, Two, Three, null).toEager();
        run(IntFuncList.from(eagerList, -4), list -> {
            assertAsString("[1, 2, 3, -4]", list);
            assertTrue   (list.isEager());
        });
    }
    
    @Test
    public void testFrom_funcList() {
        run(IntFuncList.from(Mode.lazy, IntFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue   (list.isLazy());
        });
        run(IntFuncList.from(Mode.eager, IntFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue   (list.isEager());
        });
        run(IntFuncList.from(Mode.cache, IntFuncList.of(One, Two, Three)), list -> {
            assertAsString("[1, 2, 3]", list);
            assertTrue   (list.isCache());
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(IntFuncList.from(IntStreamPlus.infiniteInt().limit(3)), list -> {
            assertAsString("[0, 1, 2]", list.limit(3));
        });
    }
    
    @Test
    public void testFrom_streamSupplier() {
        run(IntFuncList.from(() -> IntStreamPlus.infiniteInt()), list -> {
            assertAsString("[0, 1, 2, 3, 4]",                list.limit(5));
            assertAsString("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", list.limit(10));
        });
    }
    
    @Test
    public void testZeroes() {
        run(IntFuncList.zeroes().limit(5), list -> {
            assertAsString("[0, 0, 0, 0, 0]", list);
            assertAsString("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
        run(IntFuncList.zeroes(5), list -> {
            assertAsString("[0, 0, 0, 0, 0]", list);
            assertAsString("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
    }
    
    @Test
    public void testOnes() {
        run(IntFuncList.ones().limit(5), list -> {
            assertAsString("[1, 1, 1, 1, 1]", list);
            assertAsString("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
        run(IntFuncList.ones(5), list -> {
            assertAsString("[1, 1, 1, 1, 1]", list);
            assertAsString("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
    }
    
    @Test
    public void testRepeat() {
        run(IntFuncList.repeat(0, 42), list -> {
            assertAsString("[0, 42, 0, 42, 0]",         list.limit(5));
            assertAsString("[0, 42, 0, 42, 0, 42, 0]", list.limit(7));
        });
        run(IntFuncList.repeat(IntFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]",           list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testCycle() {
        run(IntFuncList.cycle(0, 1, 42), list -> {
            assertAsString("[0, 1, 42, 0, 1]",        list.limit(5));
            assertAsString("[0, 1, 42, 0, 1, 42, 0]", list.limit(7));
        });
        run(IntFuncList.cycle(IntFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertAsString("[0, 1, 2, 42, 0, 0, 1]",           list.limit(7));
            assertAsString("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testLoop() {
        run(IntFuncList.loop(),  list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.loop(5), list -> assertAsString("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testLoopBy() {
        run(IntFuncList.loopBy(3),    list -> assertAsString("[0, 3, 6, 9, 12]", list.limit(5)));
        run(IntFuncList.loopBy(3, 5), list -> assertAsString("[0, 3, 6, 9, 12]", list));
    }
    
    @Test
    public void testInfinite() {
        run(IntFuncList.infinite(),    list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.infiniteInt(), list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
    }
    
    @Test
    public void testNaturalNumbers() {
        run(IntFuncList.naturalNumbers(),  list -> assertAsString("[1, 2, 3, 4, 5]", list.limit(5)));
        run(IntFuncList.naturalNumbers(5), list -> assertAsString("[1, 2, 3, 4, 5]", list));
    }
    
    @Test
    public void testWholeNumbers() {
        run(IntFuncList.wholeNumbers(),  list -> assertAsString("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.wholeNumbers(5), list -> assertAsString("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testRange() {
        run(IntFuncList.range( 3, 7), list -> assertAsString("[3, 4, 5, 6]",          list.limit(5)));
        run(IntFuncList.range(-3, 3), list -> assertAsString("[-3, -2, -1, 0, 1, 2]", list.limit(10)));
    }
    
    @Test
    public void testEquals() {
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableIntFuncList);
                assertTrue  (list2 instanceof ImmutableIntFuncList);
                assertTrue  (Objects.equals(list1, list2));
                assertEquals(list1, list2);
            });
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableIntFuncList);
                assertTrue  (list2 instanceof ImmutableIntFuncList);
                assertFalse    (Objects.equals(list1, list2));
                assertNotEquals(list1, list2);
            });
        
        // Make it a derived list
        run(IntFuncList.of(One, Two, Three).map(value -> value),
            IntFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof IntFuncListDerived);
                assertTrue  (list2 instanceof IntFuncListDerived);
                assertEquals(list1, list2);
            });
        run(IntFuncList.of(One, Two, Three)      .map(value -> value),
            IntFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof IntFuncListDerived);
                assertTrue     (list2 instanceof IntFuncListDerived);
                assertNotEquals(list1, list2);
            });
    }
    
    @Test
    public void testHashCode() {
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableIntFuncList);
                assertTrue  (list2 instanceof ImmutableIntFuncList);
                assertEquals(list1.hashCode(), list2.hashCode());
            });
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue     (list1 instanceof ImmutableIntFuncList);
                assertTrue     (list2 instanceof ImmutableIntFuncList);
                assertNotEquals(list1.hashCode(), list2.hashCode());
            });
        
        // Make it a derived list
        run(IntFuncList.of(One, Two, Three).map(value -> value),
            IntFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof IntFuncListDerived);
                assertTrue  (list2 instanceof IntFuncListDerived);
                assertEquals(list1.hashCode(), list2.hashCode());
            });
        run(IntFuncList.of(One, Two, Three).map(value -> value),
            IntFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof IntFuncListDerived);
                assertTrue     (list2 instanceof IntFuncListDerived);
                assertNotEquals(list1.hashCode(), list2.hashCode());
            });
    }
    
    @Test
    public void testToString() {
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableIntFuncList);
                assertTrue  (list2 instanceof ImmutableIntFuncList);
                assertEquals(list1.toString(), list2.toString());
            });
        run(IntFuncList.of(One, Two, Three),
            IntFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue     (list1 instanceof ImmutableIntFuncList);
                assertTrue     (list2 instanceof ImmutableIntFuncList);
                assertNotEquals(list1.toString(), list2.toString());
            });
        
        // Make it a derived list
        run(IntFuncList.of(One, Two, Three).map(value -> value),
            IntFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof IntFuncListDerived);
                assertTrue  (list2 instanceof IntFuncListDerived);
                assertEquals(list1.toString(), list2.toString());
            });
        run(IntFuncList.of(One, Two, Three).map(value -> value),
            IntFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof IntFuncListDerived);
                assertTrue     (list2 instanceof IntFuncListDerived);
                assertNotEquals(list1.toString(), list2.toString());
            });
    }
    
    // -- Concat + Combine --
    
    @Test
    public void testConcat() {
        run(IntFuncList.concat(IntFuncList.of(One, Two), IntFuncList.of(Three, Four)),
            list -> {
                assertAsString("[1, 2, 3, 4]", list);
            }
        );
    }
    
    @Test
    public void testCombine() {
        run(IntFuncList.combine(IntFuncList.of(One, Two), IntFuncList.of(Three, Four)),
            list -> {
                assertAsString("[1, 2, 3, 4]", list);
            }
        );
    }
    
    //-- Generate --
    
    @Test
    public void testGenerate() {
        run(IntFuncList.generateWith(() -> {
                val counter = new AtomicInteger();
                IntSupplier supplier = ()-> counter.getAndIncrement();
                return supplier;
            }),
            list -> {
                assertAsString("[0, 1, 2, 3, 4]", list.limit(5));
            }
        );
        
        run(IntFuncList.generateWith(() -> {
                val counter = new AtomicInteger();
                IntSupplier supplier = ()->{
                    int count = counter.getAndIncrement();
                    if (count < 5)
                        return count;
                    
                    return FuncList.noMoreElement();
                };
                return supplier;
            }),
            list -> {
                assertAsString("[0, 1, 2, 3, 4]", list);
            }
        );
    }
    
    //-- Iterate --
    
    @Test
    public void testIterate() {
        run(IntFuncList.iterate(1,    (i)    -> 2*(i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(IntFuncList.iterate(1, 2, (a, b) -> a + b),     list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]",         list.limit(10)));
    }
    
    //-- Compound --
    
    @Test
    public void testCompound() {
        run(IntFuncList.compound(1,    (i)    -> 2*(i + 1)), list -> assertAsString("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(IntFuncList.compound(1, 2, (a, b) -> a + b),     list -> assertAsString("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]",         list.limit(10)));
    }
    
    //-- zipOf --
    
    @Test
    public void testZipOf_toTuple() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertAsString(
                        "[(1000,1), (2000,2), (3000,3), (4000,4)]",
                        IntFuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_toTuple_default() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertAsString(
                        "[(1000,1), (2000,2), (3000,3), (4000,4), (5000,-1)]",
                        IntFuncList.zipOf(list1, -1000, list2, -1));
        });
        
        run(IntFuncList.of(1000, 2000, 3000, 4000),
            IntFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertAsString(
                        "[(1000,1), (2000,2), (3000,3), (4000,4), (-1000,5)]",
                        IntFuncList.zipOf(list1, -1000, list2, -1));
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertAsString(
                        "[1001, 2002, 3003, 4004]",
                        FuncList.zipOf(
                                list1, list2,
                                (a, b) -> a + + b));
        });
    }
    
    @Test
    public void testZipOf_merge_default() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertAsString(
                        "[1000, 4000, 9000, 16000, -5000]",
                        IntFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a*b));
        });
        run(IntFuncList.of(1000, 2000, 3000, 4000),
            IntFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertAsString(
                        "[1000, 4000, 9000, 16000, -5000]",
                        IntFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a*b));
        });
    }
    
    @Test
    public void testNew() {
        IntFuncListBuilder funcList1 = IntFuncList.newListBuilder();
        IntFuncListBuilder funcList2 = IntFuncList.newBuilder();
        IntFuncListBuilder funcList3 = IntFuncList.newIntListBuilder();
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
    
    //-- Derive --
    
    @Test
    public void testDeriveFrom() {
        run(IntFuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.mapToInt(v -> -v)), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
        run(IntFuncList.deriveFrom(IntFuncList.of(1, 2, 3), s -> s.map(v -> -v)), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
        run(IntFuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToInt(v -> (int)Math.round(-v))), list -> {
            assertAsString("[-1, -2, -3]", list);
        });
    }
    
    @Test
    public void testDeriveTo() {
        run(IntFuncList.deriveToObj(IntFuncList.of(One, Two, Three), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertTrue   (list instanceof FuncList);
            assertAsString("[-1-, -2-, -3-]", list);
        });
        run(IntFuncList.deriveToInt(IntFuncList.of(One, Two, Three), s -> s.map(v -> v + 5)), list -> {
            assertAsString("[6, 7, 8]", list);
        });
        run(IntFuncList.deriveToDouble(IntFuncList.of(One, Two, Three), s -> s.mapToDouble(v -> 3.0*v)), list -> {
            assertAsString("[3.0, 6.0, 9.0]", list);
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
    public void testIsEagerIsLazy() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue(list.toLazy().isLazy());
            assertTrue(list.toEager().isEager());
            
            assertTrue(list.toLazy().freeze().isLazy());
            
            val logs = new ArrayList<String>();
            IntFuncList lazyList 
                    = list
                    .peek(value -> logs.add("" + value));
            
            lazyList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1, 2, 3]", logs.toString());
            
            // Lazy list will have to be re-evaluated again so the logs double.
            lazyList.forEach(value -> {});
            assertEquals("[1, 2, 3, 1, 2, 3]", logs.toString());
            
            
            logs.clear();
            assertEquals("[]", logs.toString());
            
            // Freeze but still lazy
            IntFuncList frozenList 
                    = list
                    .freeze()
                    .peek(value -> logs.add("" + value));
            frozenList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1, 2, 3]", logs.toString());
            
            // Freeze list but still lazy so it will have to be re-evaluated again so the logs double
            frozenList.forEach(value -> {});
            assertEquals("[1, 2, 3, 1, 2, 3]", logs.toString());
            
            
            // Eager list
            logs.clear();
            IntFuncList eagerList 
                    = list
                    .toEager()
                    .peek(value -> logs.add("" + value));
            eagerList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1, 2, 3]", logs.toString());
            
            // Eager list does not re-evaluate so the log stay the same.
            eagerList.forEach(value -> {});
            assertEquals("[1, 2, 3]", logs.toString());
        });
    }
    
    @Test
    public void testEagerLazy() {
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is lazy
            val list = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList();
            // The function has not been materialized so nothing goes through peek.
            assertAsString("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertAsString("[1, 2, 3, 4, 5]", list.limit(5));
            assertAsString("[1, 2, 3, 4, 5]", logs);
        }
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is eager
            val list = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList().toEager();
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
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .toLazy ()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theInteger.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
            // The list has not been materialized so nothing goes through peek.
            assertAsString("[]", logs1);
            assertAsString("[]", logs2);
            
            // Get part of them so those peek will goes through the peek
            assertAsString("[4, 5, 6, 7, 8]", list.limit(5));
            
            // Now that the list has been materialize all the element has been through the logs
            
            // The first log has all the number until there are 5 elements that are bigger than 3.
            assertAsString("[1, 2, 3, 4, 5, 6, 7, 8]", logs1);
            //                       1  2  3  4  5
            
            // The second log captures all the number until 5 of them that are bigger than 3.
            assertAsString("[4, 5, 6, 7, 8]", logs2);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .toEager()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theInteger.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
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
    
    //-- List --
    
    @Test
    public void testToFuncList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toFuncList();
            assertAsString("[1, 2, 3]", funcList.toString());
            assertTrue(funcList instanceof IntFuncList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toJavaList();
            assertAsString("[1, 2, 3]", funcList);
            assertFalse(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val funcList = list.toImmutableList();
            assertAsString("[1, 2, 3]", funcList);
            assertTrue(funcList instanceof ImmutableIntFuncList);
            
            assertAsString("[1, 2, 3]", funcList.map(value -> value).toImmutableList());
            assertTrue(funcList instanceof ImmutableIntFuncList);
        });
    }
    
    @Test
    public void testIterable() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterable().iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextInt());
            
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextInt());
            
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextInt());
            
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testIterator() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextInt());
            
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextInt());
            
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextInt());
            
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testSpliterator() {
        run(IntFuncList.of(One, Two, Three), list -> {
            Spliterator.OfInt spliterator = list.spliterator();
            IntStream         stream      = StreamSupport.intStream(spliterator, false);
            IntStreamPlus     streamPlus  = IntStreamPlus.from(stream);
            assertAsString("[1, 2, 3]", streamPlus.toListString());
        });
    }
    
    @Test
    public void testContainsAllOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsAllOf(One, Five));
            assertFalse(list.containsAllOf(One, Six));
        });
    }
    
    @Test
    public void testContainsAnyOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsAnyOf(One, Six));
            assertFalse(list.containsAnyOf(Six, Seven));
        });
    }
    
    @Test
    public void testContainsNoneOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsNoneOf(Six, Seven));
            assertFalse(list.containsNoneOf(One, Six));
        });
    }
    
    @Test
    public void testJavaList_for() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            for(val value : list.boxed()) {
                logs.add("" + value);
            }
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testJavaList_size_isEmpty() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertEquals(3, list.size());
            assertFalse (list.isEmpty());
        });
        run(IntFuncList.empty(), list -> {
            assertEquals(0, list.size());
            assertTrue  (list.isEmpty());
        });
    }
    
    @Test
    public void testJavaList_contains() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.contains(Two));
            assertFalse(list.contains(Five));
        });
    }
    
    @Test
    public void testJavaList_containsAllOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsAllOf(Two, Three));
            assertTrue (list.containsAllOf(FuncList.   listOf(Two, Three)));
            assertTrue (list.containsAllOf(IntFuncList.listOf(Two, Three)));
            
            assertFalse(list.containsAllOf(Two, Five));
            assertFalse(list.containsAllOf(FuncList.   listOf(Two, Five)));
            assertFalse(list.containsAllOf(IntFuncList.listOf(Two, Five)));
        });
    }
    
    @Test
    public void testJavaList_containsSomeOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsSomeOf(Two, Five));
            assertTrue (list.containsSomeOf(FuncList.   listOf(Two, Five)));
            assertTrue (list.containsSomeOf(IntFuncList.listOf(Two, Five)));
            
            assertFalse(list.containsSomeOf(Five, Seven));
            assertFalse(list.containsSomeOf(FuncList.   listOf(Five, Seven)));
            assertFalse(list.containsSomeOf(IntFuncList.listOf(Five, Seven)));
        });
    }
    
    @Test
    public void testJavaList_containsNoneOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsNoneOf(Five, Six));
            assertTrue (list.containsNoneOf(FuncList.   listOf(Five, Six)));
            assertTrue (list.containsNoneOf(IntFuncList.listOf(Five, Six)));
            
            assertFalse(list.containsNoneOf(Two, Five));
            assertFalse(list.containsNoneOf(FuncList   .listOf(Two, Five)));
            assertFalse(list.containsNoneOf(IntFuncList.listOf(Two, Five)));
        });
    }
    
    @Test
    public void testForEach() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEach(s -> logs.add("" + s));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachOrdered(s -> logs.add("" + s));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testReduce() {
        run(IntFuncList.of(1, 2, 3), list -> {
            assertEquals(6, list.reduce(0, (a, b) -> a + b));
            assertEquals(6, list.reduce((a, b) -> a + b).getAsInt());
        });
    }
    
    static class Sum implements IntCollectorPlus<AtomicInteger, Integer> {
        
        @Override
        public Collector<Integer, AtomicInteger, Integer> collector() {
            return this;
        }
        @Override
        public Integer process(IntStreamPlus stream) {
            return stream.sum();
        }
        @Override
        public Supplier<AtomicInteger> supplier() {
            return () -> new AtomicInteger();
        }
        @Override
        public IntAccumulator<AtomicInteger> intAccumulator() {
            return (atomicInt, i) -> {
                atomicInt.set(atomicInt.get() + i);
            };
        }
        @Override
        public BinaryOperator<AtomicInteger> combiner() {
            return (i1, i2) -> new AtomicInteger(i1.get() + i2.get());
        }
        @Override
        public Function<AtomicInteger, Integer> finisher() {
            return i -> i.get();
        }
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    @Test
    public void testCollect() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val sum = new Sum();
            assertAsString("6", list.collect(sum));
            
            Supplier<StringBuffer>                 supplier    = ()          -> new StringBuffer();
            ObjIntConsumer<StringBuffer>           accumulator = (buffer, i) -> buffer.append(i);
            BiConsumer<StringBuffer, StringBuffer> combiner    = (b1, b2)    -> b1.append(b2.toString());
            assertAsString("123", list.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testSize() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("4", list.size());
        });
    }
    
    @Test
    public void testCount() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("4", list.count());
        });
    }
    
    @Test
    public void testSum() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("6", list.sum());
        });
    }
    
    @Test
    public void testProduct() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[6]", list.product());
        });
    }
    
    @Test
    public void testMinMax() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalInt[1]", list.min());
            assertAsString("OptionalInt[4]", list.max());
        });
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("(OptionalInt[1],OptionalInt[4])", list.minMax());
        });
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("(OptionalInt[4],OptionalInt[1])", list.minMax((a,b) -> b - a));
        });
    }
    
    @Test
    public void testMinByMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalInt[1]", list.minBy(a ->  a));
            assertAsString("OptionalInt[4]", list.maxBy(a ->  a));
            assertAsString("OptionalInt[4]", list.minBy(a -> -a));
            assertAsString("OptionalInt[1]", list.maxBy(a -> -a));
        });
        
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalInt[1]", list.minBy(a ->  a, (a,b)->a-b));
            assertAsString("OptionalInt[4]", list.maxBy(a ->  a, (a,b)->a-b));
            assertAsString("OptionalInt[4]", list.minBy(a -> -a, (a,b)->a-b));
            assertAsString("OptionalInt[1]", list.maxBy(a -> -a, (a,b)->a-b));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("(OptionalInt[1],OptionalInt[4])", list.minMaxBy(a ->  a));
        });
        
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("(OptionalInt[4],OptionalInt[1])", list.minMaxBy(a -> a, (a,b)->b-a));
        });
    }
    
    @Test
    public void testMinOfMaxOf() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("OptionalInt[1]", list.minOf(a ->  a));
            assertAsString("OptionalInt[4]", list.maxOf(a ->  a));
            assertAsString("OptionalInt[4]", list.minOf(a -> -a));
            assertAsString("OptionalInt[1]", list.maxOf(a -> -a));
        });
    }
    
    @Test
    public void testMinIndex() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[0]", list.minIndex());
        });
    }
    
    @Test
    public void testMaxIndex() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[5]", list.maxIndex());
        });
    }
    
    @Test
    public void testMinIndexBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[0]", list.minIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMinIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            IntPredicate     condition = value -> value > 2;
            IntUnaryOperator operator  = value -> value;
            assertAsString("OptionalInt[2]", list.minIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testMaxIndexBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[5]", list.maxIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMaxIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            IntPredicate     condition = value -> value > 2;
            IntUnaryOperator operator  = value -> value;
            assertAsString("OptionalInt[5]", list.maxIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testAnyMatch() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue(list.anyMatch(value -> value == One));
        });
    }
    
    @Test
    public void testAllMatch() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertFalse(list.allMatch(value -> value == One));
        });
    }
    
    @Test
    public void testNoneMatch() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue(list.noneMatch(value -> value == Five));
        });
    }
    
    @Test
    public void testFindFirst() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[1]", list.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[1]", list.findAny());
        });
    }
    
    @Test
    public void testFindLast() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[3]", list.findLast());
        });
    }
    
    @Test
    public void testFirstResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[1]", list.firstResult());
        });
    }
    
    @Test
    public void testLastResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("OptionalInt[3]", list.lastResult());
        });
    }
    
    @Test
    public void testJavaList_get() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertEquals(One,   list.get(0));
            assertEquals(Two,   list.get(1));
            assertEquals(Three, list.get(2));
        });
    }
    
    @Test
    public void testJavaList_indexOf() {
        run(IntFuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals( 1, list.indexOf(Two));
            assertEquals(-1, list.indexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_lastIndexOf() {
        run(IntFuncList.of(One, Two, Three, Two, Three), list -> {
            assertEquals( 3, list.lastIndexOf(Two));
            assertEquals(-1, list.lastIndexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_subList() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 3]",       list.subList(1, 3));
            assertAsString("[2, 3, 4, 5]", list.subList(1, 10));
        });
    }
    
    //-- IntFuncListWithGroupingBy --
    
    @Test
    public void testGroupingBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "{1:[1, 2], 2:[3, 4], 3:[5]}",
                    list
                    .groupingBy(theInteger.dividedBy(2).asInteger())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_aggregate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "{1:[1.0, 2.0], 2:[3.0, 4.0], 3:[5.0]}",
                    list
                    .groupingBy(theInteger.dividedBy(2).asInteger(), l -> l.mapToDouble())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_collect() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
//                    "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before sum
                    "{1:3, 2:7, 3:5}",
                    list
                    .groupingBy(theInteger.dividedBy(2).asInteger(), () -> new Sum())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_process() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val sumHalf = new SumHalf();
            assertAsString(
//                  "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before half
//                  "{1:[0, 1], 2:[1, 2], 3:[2]}",  << Half
                    "{1:1, 2:3, 3:2}",
                    list
                    .groupingBy(theInteger.dividedBy(2).asInteger(), sumHalf)
                    .sortedByKey(theInteger));
        });
    }
    
    //-- Functional list
    
    @Test
    public void testMapToString() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]",
                    list
                    .mapToString()
                    );
        });
    }
    
    @Test
    public void testMap() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 4, 6, 8, 10]",
                    list
                    .map(theInteger.time(2))
            );
        });
    }
    
    @Test
    public void testMapToInt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[10, 20, 30]",
                    list
                    .map(theInteger.time(10))
            );
        });
    }
    
    @Test
    public void testMapToDouble() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString(
                    "[1.0, 1.4142135623730951, 1.7320508075688772]", 
                    list.mapToDouble(theInteger.squareRoot()));
        });
    }
    
    @Test
    public void testMapToObj() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[-1-, -2-, -3-, -4-, -5-]",
                    list
                    .mapToObj(i -> "-" + i + "-")
                    );
        });
    }
    
    //-- FlatMap --
    
    @Test
    public void testFlatMap() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString(
                    "[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]",
                    list.flatMap(i -> IntFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString(
                    "[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]",
                    list.flatMapToInt(i -> IntFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToDouble() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString(
                    "[1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0]",
                    list
                    .flatMapToDouble(i -> DoubleFuncList.cycle(i).limit(i)));
        });
    }
    
    //-- Filter --
    
    @Test
    public void testFilter() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString(
                    "[3]",
                    list.filter(theInteger.time(2).thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testFilter_mapper() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString(
                    "[3]",
                    list.filter(theInteger.time(2), theInteger.thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testPeek() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            assertAsString("[1, 2, 3]", list.peek(i -> logs.add("" + i)));
            assertAsString("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3]", list.limit(3));
        });
    }
    
    @Test
    public void testSkip() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[3, 4, 5]", list.skip(2));
        });
    }
    
    @Test
    public void testDistinct() {
        run(IntFuncList.of(One, Two, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.distinct());
        });
    }
    
    @Test
    public void testSorted() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 4, 6, 8, 10]", list.map(theInteger.time(2)).sorted());
            assertAsString("[10, 8, 6, 4, 2]", list.map(theInteger.time(2)).sorted((a, b) -> (b - a)));
        });
    }
    
    @Test
    public void testBoxed() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testToArray() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", Arrays.toString(list.toArray()));
        });
    }
    
    @Test
    public void testNullableOptionalResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("Nullable.of([1, 2, 3])",      list.__nullable());
            assertAsString("Optional[[1, 2, 3]]",         list.__optional());
            assertAsString("Result:{ Value: [1, 2, 3] }", list.__result());
        });
    }
    
    @Test
    public void testIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Three), list -> {
            assertAsString("2", list.indexOf(Three));
        });
    }
    
    @Test
    public void testLastIndexOf() {
        run(IntFuncList.of(Three, One, Two, Three, Four, Five), list -> {
            assertAsString("3", list.lastIndexOf(Three));
        });
    }
    
    @Test
    public void testIndexesOf() {
        run(IntFuncList.of(One, Two, Three, Four, Two), list -> {
            assertAsString("[0, 2]", list.indexesOf(value -> value == One || value == Three));
            assertAsString("[1, 4]", list.indexesOf(Two));
        });
    }
    
    @Test
    public void testToBuilder() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list.toBuilder().add(Four).add(Five).build());
        });
    }
    
    
    @Test
    public void testFirst() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[1]", list.first());
            assertAsString("[1, 2, 3]",      list.first(3));
        });
    }
    
    @Test
    public void testLast() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[5]", list.last());
            assertAsString("[3, 4, 5]",      list.last(3));
        });
    }
    
    @Test
    public void testAt() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]",     list.at(2));
            assertAsString("OptionalInt.empty",  list.at(10));
        });
    }
    
    @Test
    public void testTail() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[2, 3, 4, 5]", list.tail());
        });
    }
    @Test
    public void testAppend() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",       list);
            assertAsString("[1, 2, 3, 4]", list.append(Four));
            assertAsString("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testAppendAll() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",       list);
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(Four, Five));
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(IntFuncList.listOf(Four, Five)));
            assertAsString("[1, 2, 3, 4, 5]", list.appendAll(IntFuncList.of(Four, Five)));
            assertAsString("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testPrepend() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",    list);
            assertAsString("[0, 1, 2, 3]", list.prepend(Zero));
            assertAsString("[1, 2, 3]",    list);
        });
    }
    
    @Test
    public void testPrependAll() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",        list);
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(MinusOne, Zero));
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(IntFuncList.listOf(MinusOne, Zero)));
            assertAsString("[-1, 0, 1, 2, 3]", list.prependAll(IntFuncList.of(MinusOne, Zero)));
            assertAsString("[1, 2, 3]",        list);
        });
    }
    
    @Test
    public void testWith() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",   list);
            assertAsString("[1, 0, 3]",   list.with(1, Zero));
            assertAsString("[1, 102, 3]", list.with(1, value -> value + 100));
            assertAsString("[1, 2, 3]",   list);
        });
    }
    
    @Test
    public void testInsertAt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",     list);
            assertAsString("[1, 0, 2, 3]", list.insertAt(1, Zero));
            assertAsString("[1, 2, 3]",    list);
        });
    }
    
    @Test
    public void testInsertAllAt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]",       list);
            assertAsString("[1, 2, 0, 0, 3]", list.insertAt(2, Zero, Zero));
            assertAsString("[1, 2, 0, 0, 3]", list.insertAllAt(2, IntFuncList.listOf(Zero, Zero)));
            assertAsString("[1, 2, 0, 0, 3]", list.insertAllAt(2, IntFuncList.of(Zero, Zero)));
            assertAsString("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testExclude() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list);
            assertAsString("[1, 3, 4, 5]",    list.exclude(Two));
            assertAsString("[1, 3, 4, 5]",    list.exclude(theInteger.eq(Two)));
            assertAsString("[1, 3, 4, 5]",    list.excludeAt(1));
            assertAsString("[1, 5]",          list.excludeFrom(1, 3));
            assertAsString("[1, 4, 5]",       list.excludeBetween(1, 3));
            assertAsString("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testReverse() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[1, 2, 3, 4, 5]", list);
            assertAsString("[5, 4, 3, 2, 1]", list.reverse());
            assertAsString("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testShuffle() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
            assertAsString  ("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
            assertNotEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.shuffle().toString());
            assertAsString  ("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
        });
    }
    
    @Test
    public void testQuery() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("[1, 2, 3, 4, 5, 6]", list);
            assertAsString("[(2,3), (5,6)]",     list.query(theInteger.thatIsDivisibleBy(3)));
            assertAsString("[1, 2, 3, 4, 5, 6]", list);
        });
    }
    
    //-- AsIntFuncListWithConversion --
    
    @Test
    public void testToByteArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65, 66, 67, 68]", Arrays.toString(list.toByteArray(c -> (byte)(int)c)));
        });
    }
    
    @Test
    public void testToIntArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65, 66, 67, 68]", Arrays.toString(list.toIntArray(c -> (int)c)));
        });
    }
    
    @Test
    public void testToDoubleArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertAsString("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(list.toDoubleArray(c -> (double)(int)c)));
        });
    }
    
    @Test
    public void testToArrayList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toArrayList();
            assertAsString("[1, 2, 3]", newList);
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    @Test
    public void testToList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toJavaList();
            assertAsString("[1, 2, 3]", newList);
            assertTrue(newList instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toMutableList();
            assertAsString("[1, 2, 3]", newList);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    //-- join --
    
    @Test
    public void testJoin() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("123", list.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("1, 2, 3", list.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list);
        });
    }
    
    //-- toMap --
    
    @Test
    public void testToMap() {
        run(IntFuncList.of(One, Three, Five), list -> {
            assertAsString("{1:1, 3:3, 5:5}", list.toMap(theInteger));
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(IntFuncList.of(One, Three, Five), list -> {
            assertAsString("{1:1, 3:9, 5:25}", list.toMap(theInteger, theInteger.square()));
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(IntFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1+3+5
            assertAsString("{0:2, 1:9}", list.toMap(theInteger.remainderBy(2), theInteger, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(IntFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1*3*5
            assertAsString("{0:2, 1:15}", list.toMap(theInteger.remainderBy(2), (a, b) -> a * b));
        });
    }
    
    @Test
    public void testToSet() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val set    = list.toSet();
            assertAsString("[1, 2, 3]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertAsString("[0:1, 1:2, 2:3]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[5];
            list.populateArray(array);
            assertAsString("[1, 2, 3, 4, 5]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[3];
            list.populateArray(array, 2);
            assertAsString("[0, 0, 1]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[5];
            list.populateArray(array, 1, 3);
            assertAsString("[0, 1, 2, 3, 0]", Arrays.toString(array));
        });
    }
    
    //-- AsIntFuncListWithMatch --
    
    @Test
    public void testFindFirst_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.findFirst(theInteger.square().thatGreaterThan(theInteger.time(2))));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.findFirst(theInteger.square().thatGreaterThan(theInteger.time(2))));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.findFirst(theInteger.square(), theInteger.thatGreaterThan(5)));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.findAny(theInteger.square(), theInteger.thatGreaterThan(5)));
        });
    }
    
    //-- AsFuncListWithStatistic --
    
    @Test
    public void testMinBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.minBy(theInteger.minus(3).square()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.maxBy(theInteger.minus(3).square().negate()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("OptionalInt[6]", list.minBy(theInteger.minus(3).square(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("OptionalInt[3]", list.maxBy(theInteger.minus(3).square(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertAsString("(OptionalInt[3],OptionalInt[6])", list.minMaxBy(theInteger.minus(3).square()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString("(OptionalInt[1],OptionalInt[3])", list.minMaxBy(theInteger.minus(3).square(), (a, b) -> b-a));
        });
    }
    
    //-- IntFuncListWithCalculate --
    
    static class SumHalf implements IntCollectorPlus<int[], Integer> {
        @Override public Supplier<int[]>          supplier()       { return ()       -> new int[] { 0 }; }
        @Override public IntAccumulator<int[]>    intAccumulator() { return (a, i)   -> { a[0] += i; }; }
        @Override public BinaryOperator<int[]>    combiner()       { return (a1, a2) -> new int[] { a1[0] + a2[0] }; }
        @Override public Function<int[], Integer> finisher()       { return (a)      -> a[0] / 2; }
        
        @Override public Integer process(IntStreamPlus stream) {
            return stream.map(i -> i/2).sum();
        }
    }
    static class Average implements IntCollectorPlus<int[], Double> {
        @Override public Supplier<int[]>         supplier()       { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>   intAccumulator() { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<int[]>   combiner()       { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<int[], Double> finisher()       { return (a)      -> (a[1] == 0) ? null : (1.0*a[0]/a[1]); }
        
        @Override public Double process(IntStreamPlus stream) {
            val average = stream.average();
            return average.isPresent() ? ((double)Math.round(average.getAsDouble())) : null;
        }
    }
    static class MinInt implements IntCollectorPlus<int[], Integer> {
        @Override public Supplier<int[]>          supplier()       { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>    intAccumulator() { return (a, i)   -> { a[0] = Math.min(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<int[]>    combiner()       { return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<int[], Integer> finisher()       { return (a)      -> (a[1] == 0) ? null : (a[0]); }
        
        @Override public Integer process(IntStreamPlus stream) {
            val min = stream.min();
            return min.isPresent() ? ((int)Math.round(min.getAsInt())) : null;
        }
    }
    static class MaxInt implements IntCollectorPlus<int[], Integer> {
        @Override public Supplier<int[]>          supplier()       { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>    intAccumulator() { return (a, i)   -> { a[0] = Math.max(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<int[]>    combiner()       { return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<int[], Integer> finisher()       { return (a)      -> (a[1] == 0) ? null : (a[0]); }
        
        @Override public Integer process(IntStreamPlus stream) {
            val max = stream.max();
            return max.isPresent() ? ((int)Math.round(max.getAsInt())) : null;
        }
    }
    static class SumInt implements IntCollectorToIntPlus<int[]> {
        @Override public Supplier<int[]>       supplier()       { return ()       -> new int[] { 0 }; }
        @Override public IntAccumulator<int[]> intAccumulator() { return (a, i)   -> { a[0] += i; }; }
        @Override public BinaryOperator<int[]> combiner()       { return (a1, a2) -> new int[] { a1[0] + a2[0] }; }
        @Override public ToIntFunction<int[]>  finisherAsInt()  { return (a)      -> a[0]; }
        @Override public Integer process(IntStreamPlus stream) {
            return stream.sum();
        }
    }
    static class AvgInt implements IntCollectorPlus<int[], Integer> {
        @Override public Supplier<int[]>          supplier()       { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>    intAccumulator() { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<int[]>    combiner()       { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<int[], Integer> finisher()       { return (a)      -> (a[1] == 0) ? null : (a[0]/a[1]); }
        
        @Override public Integer process(IntStreamPlus stream) {
            val avg = stream.average();
            return avg.isPresent() ? ((int)Math.round(avg.getAsDouble())) : null;
        }
    }
    
    @Test
    public void testCalculate() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            assertAsString("10", list.calculate(sumHalf).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            assertAsString(
                    "(10,5.0)", 
                    list.calculate(sumHalf, average));
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val minInt = new MinInt();
            val maxInt = new MaxInt();
            val range = list.calculate(minInt, maxInt).mapTo((max, min) -> max + min);
            assertAsString("11", range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            assertAsString(
                    "(10,5.0,0)", 
                    list.calculate(sumHalf, average, minInt));
        });
    }
    
    @Test
    public void testCalculate3_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val value   = list
                            .calculate(sumHalf, average, minInt)
                            .mapTo((sumH, avg, min) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min);
            assertAsString(
                    "sumH: 10, avg: 5.0, min: 0", 
                    value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            assertAsString(
                    "(10,5.0,0,11)", 
                    list.calculate(sumHalf, average, minInt, maxInt));
        });
    }
    
    @Test
    public void testCalculate4_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val value     = list
                            .calculate(sumHalf, average, minInt, maxInt)
                            .mapTo((sumH, avg, min, max) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max);
            assertAsString(
                    "sumH: 10, "
                    + "avg: 5.0, "
                    + "min: 0, "
                    + "max: 11", 
                    value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val sumInt  = new SumInt();
            assertAsString(
                    "(10,5.0,0,11,20)",
                    list.calculate(sumHalf, average, minInt, maxInt, sumInt));
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val sumInt  = new SumInt();
            val value     = list
                            .calculate(sumHalf, average, minInt, maxInt, sumInt)
                            .mapTo((sumH, avg, min, max, sumI) -> {
                                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI;
                            });
            assertAsString(
                    "sumH: 10, avg: 5.0, min: 0, max: 11, max: 11, sumI: 20", 
                    value);
        });
    }
    
    @Test
    public void testCalculate6() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val sumInt  = new SumInt();
            val avgInt  = new AvgInt();
            assertAsString(
                    "(10,5.0,0,11,20,5)", 
                    list.calculate(sumHalf, average, minInt, maxInt, sumInt, avgInt));
        });
    }
    
    @Test
    public void testCalculate6_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val sumInt  = new SumInt();
            val avgInt  = new AvgInt();
            val value     = list
                            .calculate(sumHalf, average, minInt, maxInt, sumInt, avgInt)
                            .mapTo((sumH, avg, min, max, sumI, avgI) -> {
                                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI + ", avgI: " + avgI;
                            });
            assertAsString(
                    "sumH: 10, "
                    + "avg: 5.0, "
                    + "min: 0, "
                    + "max: 11, "
                    + "max: 11, "
                    + "sumI: 20, "
                    + "avgI: 5", 
                    value);
        });
    }
    
    @Test
    public void testCalculate_of() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sum = new Sum();
            // 2*2 + 3*2 + 4*2 + 11*2
            // 4   + 6   + 8   + 22
            assertAsString("40", list.calculate(sum.ofInt(theInteger.time(2))));
        });
    }
    
    //-- FuncListWithCombine --
    
    @Test
    public void testAppendWith() {
        run(IntFuncList.of(One, Two), IntFuncList.of(Three, Four), (list1, list2) -> {
            assertAsString(
                        "[1, 2, 3, 4]",
                        list1.appendWith(list2)
                    );
        });
    }
    
    @Test
    public void testParependWith() {
        run(IntFuncList.of(One, Two), IntFuncList.of(Three, Four), (list1, list2) -> {
            assertAsString(
                        "[1, 2, 3, 4]",
                        list2.prependWith(list1)
                    );
        });
    }
        
    @Test
    public void testMerge() {
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (list1, streamabl2) -> {
            assertAsString(
                "100, 0, 200, 1, 300, 2, 3, 4, 5, 6",
                list1
                    .mergeWith(streamabl2)
                    .limit    (10)
                    .join     (", "));
        });
    }
    
    @Test
    public void testZipWith() {
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        "(100,0), (200,1), (300,2)",
                        listA.zipWith(listB).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "(100,0), (200,1), (300,2), (-1,3), (-1,4), (-1,5), (-1,6), (-1,7), (-1,8), (-1,9)",
                        listA.zipWith(listB, -1).join(", "));
            });
        run(IntFuncList.of(100, 200, 300, 400, 500),
                IntFuncList.infinite().limit(3),
                (listA, listB) -> {
                    assertAsString(
                            // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                            //   0   1    2  3  4  5  6  7  8  9
                            "(100,0), (200,1), (300,2), (400,-1), (500,-1)",
                            listA.zipWith(-100, listB, -1).join(", "));
                });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300
                        //   0   1    2
                        "100, 201, 302",
                        listA.zipWith(listB, (iA, iB) -> iA + iB).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                       // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                       //   0   1    2  3  4  5  6  7  8  9
                        "100, 201, 302, 2, 3, 4, 5, 6, 7, 8",
                        listA.zipWith(listB, -1, (iA, iB) -> iA + iB).join(", "));
            });
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.infinite().limit(3),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "10000, 20001, 30002, 39999, 49999",
                        listA.zipWith(-100, listB, -1, (a, b) -> a*100 + b).join(", "));
            });
    }
    
    @Test
    public void testZipWith_object() {
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        "(100,0), (200,1), (300,2)",
                        listA.zipWith(listB.boxed()).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        "(100,0), (200,1), (300,2), (-1,3), (-1,4), (-1,5), (-1,6), (-1,7), (-1,8), (-1,9)",
                        listA.zipWith(-1, listB.boxed()).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        "100->0, 200->1, 300->2",
                        listA.zipWith(listB.boxed(), (a, b) -> a + "->" + b).join(", "));
            });
        run(IntFuncList.of(100, 200, 300, 400, 500),
            IntFuncList.infinite().limit(3),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "10000, 20001, 30002, 39999, 49999",
                        listA.zipWith(-100, listB, -1, (a, b) -> a*100 + b).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300
                        //   0   1    2
                        "100<->0, 200<->1, 300<->2",
                        listA.zipToObjWith(listB, (iA, iB) -> iA + "<->" + iB).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300
                        //   0   1    2
                        "100<->0, 200<->1, 300<->2, -100<->3, -100<->4, -100<->5, -100<->6, -100<->7, -100<->8, -100<->9",
                        listA.zipToObjWith(-100, listB, -1, (iA, iB) -> iA + "<->" + iB).join(", "));
            });
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertAsString(
                        // 100 200  300
                        //   0   1    2
                        "100<->0, 200<->1, 300<->2, -100<->3, -100<->4, -100<->5, -100<->6, -100<->7, -100<->8, -100<->9",
                        listA.zipToObjWith(-100, listB.boxed(), (iA, iB) -> iA + "<->" + iB).join(", "));
            });
    }
    
    @Test
    public void testChoose() {
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
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
        run(IntFuncList.of(100, 200, 300),
            IntFuncList.infinite().limit(10),
            (listA, listB) -> {
                val bool    = new AtomicBoolean(true);
                assertAsString(
                        // 100 200  300 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7
                        "100, 1, 300, 3, 4, 5, 6", 
                        listA.choose(listB, AllowUnpaired, (a, b) -> {
                            // This logic which to choose from one then another
                            boolean curValue = bool.get();
                            return bool.getAndSet(!curValue);
                        }).limit(7).join(", "));
            });
    }
    
    //-- IntStreamPlusWithFilter --
    
    @Test
    public void testFilter_withMappter() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 5]", 
                    list.filter(
                            theInteger.square(), 
                            theInteger.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsInt() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 5]", 
                    list.filterAsInt(
                            theInteger.square(), 
                            theInteger.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsLong() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 5]", 
                    list.filterAsLong(
                            theInteger.square().asLong(), 
                            theLong.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsDouble() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 5]", 
                    list.filterAsDouble(
                            theInteger.square().asDouble() , 
                            theDouble.asInteger().thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsObject() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 5]", 
                    list.filterAsObject(
                            i -> "" + i, 
                            s -> (Integer.parseInt(s) % 2) == 1));
        });
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            IntFunction<String> mapper  = i -> "" + i;
            Predicate<String>   checker = s -> (Integer.parseInt(s) % 2) == 1;
            assertAsString(
                    "[1, 3, 5]", 
                    list.filterAsObject(mapper, checker));
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString("[4]", list.filterWithIndex((index, value) -> (index > 2) && (value < 5)));
        });
    }
    
    @Test
    public void testFilterNonNull() {
        val cars = new Car[] {
                new Car("Blue"),
                new Car("Green"),
                null,
                new Car(null), 
                new Car("Red")
        };
        run(IntFuncList.wholeNumbers(cars.length), list -> {
            assertAsString(
                    "[0, 1, 3, 4]",
                    list.filterNonNull(i -> cars[i]));
        });
    }
    
    @Test
    public void testExcludeNull() {
        val cars = new Car[] {
                new Car("Blue"),
                new Car("Green"),
                null,
                new Car(null), 
                new Car("Red")
        };
        run(IntFuncList.wholeNumbers(cars.length), list -> {
            assertAsString(
                    "[0, 1, 3, 4]",
                    list.excludeNull(i -> cars[i]));
        });
    }
    
    @Test
    public void testFilterIn_array() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[2, 5]",
                    list.filterIn(Two, Five));
        });
    }
    
    @Test
    public void testExcludeIn_array() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 4]",
                    list.excludeIn(Two, Five));
        });
    }
    
    @Test
    public void testFilterIn_funcList() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[2, 5]",
                    list.filterIn(IntFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_funcList() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 4]",
                    list.excludeIn(IntFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testFilterIn_collection() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[2, 5]",
                    list.filterIn(Arrays.asList(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_collection() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1, 3, 4]",
                    list.excludeIn(Arrays.asList(Two, Five)));
        });
    }
    
    //-- FuncListWithFlatMap --
    
    @Test
    public void testFlatMapOnly() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString(
                    "[1, 2, 3, 3, 3]", 
                    list.flatMapOnly(
                            theInteger.thatIsOdd(), 
                            i -> IntFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapIf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString(
                    "[1, -2, -2, 3, 3, 3]", 
                    list.flatMapIf(
                            theInteger.thatIsOdd(),
                            i -> IntFuncList.cycle(i).limit(i),
                            i -> IntFuncList.cycle(-i).limit(i)));
        });
    }
    
    //-- FuncListWithLimit --
    
    @Test
    public void testSkipLimitLong() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[2]", list.skip((Long)1L).limit((Long)1L));
        });
    }
    
    @Test
    public void testSkipLimitLongNull() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.skip(null).limit(null));
        });
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 3]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
        });
    }
    
    @Test
    public void testSkipWhile() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[3, 4, 5, 4, 3, 2, 1]",       list.skipWhile(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i > 3));
            assertAsString("[5, 4, 3, 2, 1]",             list.skipWhile((p, e) -> p == e + 1));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testSkipUntil() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[4, 5, 4, 3, 2, 1]",          list.skipUntil(i -> i > 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil((p, e) -> p == e + 1));
            assertAsString("[5, 4, 3, 2, 1]",             list.skipUntil((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testTakeWhile() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[1, 2, 3]",    list.peek(logs::add).takeWhile(i -> i < 4));
            assertAsString("[1, 2, 3, 4]", logs);
            //                       ^--- Because it needs 4 to do the check in `takeWhile`
            
            logs.clear();
            assertAsString("[]", list.peek(logs::add).takeWhile(i -> i > 4));
            assertAsString("[1]", logs);
            //              ^--- Because it needs 1 to do the check in `takeWhile`
        });
    }
    
    @Test
    public void testTakeWhile_previous() {
        run(IntFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.takeWhile((a, b) -> b == a + 1));
        });
    }
    
    @Test
    public void testTakeUtil() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[1, 2, 3, 4]", list.peek(logs::add).takeUntil(i -> i > 4));
            assertAsString("[1, 2, 3, 4, 5]", logs);
            //                          ^--- Because it needs 5 to do the check in `takeUntil`
            
            logs.clear();
            assertAsString("[]",  list.peek(logs::add).takeUntil(i -> i < 4));
            assertAsString("[1]", logs);
            //              ^--- Because it needs 1 to do the check in `takeUntil`
        });
    }
    
    @Test
    public void testTakeUntil_previous() {
        run(IntFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.takeUntil((a, b) -> b > a + 1));
        });
    }
    
    @Test
    public void testDropAfter() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4]", list.dropAfter(i -> i == 4));
            //                       ^--- Include 4
        });
    }
    
    @Test
    public void testDropAfter_previous() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertAsString("[1, 2, 3, 4, 5, 4]", list.dropAfter((a, b) -> b < a));
            //                             ^--- Include 4
        });
    }
    
    @Test
    public void testSkipTake() {
        run(IntFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Integer>();
            assertAsString("[3, 4, 5, 4, 3]", list.peek(logs::add).skipWhile(i -> i < 3).takeUntil(i -> i < 3));
            assertAsString("[1, 2, 3, 4, 5, 4, 3, 2]", logs);
            //              ^--^-----------------^--- Because it needs these number to do the check in `skipWhile` and `takeWhile`
        });
    }
    
    //-- FuncListWithMap --
    
    @Test
    public void testMapOnly() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 2, 9]",
                    list
                    .mapOnly(
                            theInteger.thatIsOdd(),
                            theInteger.square())
                    );
        });
    }
    
    @Test
    public void testMapIf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 1, 9]",
                    list
                    .mapIf(
                            theInteger.thatIsOdd(),
                            theInteger.square(),
                            theInteger.squareRoot().round().asInteger()
                    ));
        });
    }
    
    @Test
    public void testMapToObjIf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertAsString("[1, 1, 9]",
                    list
                    .mapToObjIf(
                            theInteger.thatIsOdd(),
                            theInteger.square().asString(),
                            theInteger.squareRoot().round().asInteger().asString()
                    ));
        });
    }
    
    //== Map First ==
    
    @Test
    public void testMapFirst_2() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString(
                    "[1, 2, Three, 4, 5, 6, 7, 8, 9, 10, 11, 12]",
                    list
                    .mapFirst(
                            i -> i == 3 ? "Three" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_3() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[1, 2, Three, 4, 5, 6, Seven, 8, 9, 10, 11, 12]",
                    list
                    .mapFirst(
                            i -> i == 3 ? "Three" : null,
                            i -> i == 7 ? "Seven" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_4() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[1, 2, Three, 4, 5, 6, Seven, 8, 9, 10, Eleven, 12]",
                    list
                    .mapFirst(
                            i -> i ==  3 ? "Three" : null,
                            i -> i ==  7 ? "Seven" : null,
                            i -> i == 11 ? "Eleven" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_5() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[One, 2, Three, 4, 5, 6, Seven, 8, 9, 10, Eleven, 12]",
                    list
                    .mapFirst(
                            i -> i ==  3 ? "Three" : null,
                            i -> i ==  7 ? "Seven" : null,
                            i -> i == 11 ? "Eleven" : null,
                            i -> i ==  1 ? "One" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_6() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertAsString("[One, 2, Three, 4, Five, 6, Seven, 8, 9, 10, Eleven, 12]",
                    list
                    .mapFirst(
                            i -> i ==  3 ? "Three"  : null,
                            i -> i ==  7 ? "Seven"  : null,
                            i -> i == 11 ? "Eleven" : null,
                            i -> i ==  1 ? "One"    : null,
                            i -> i ==  5 ? "Five"   : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    //== MapThen ==
    
    @Test
    public void testMapThen_2() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
        assertAsString(
                "[1-2, 2-3, 3-4, 4-5, 5-6]",
                list
                .mapThen(
                        theInteger,
                        theInteger.plus(1),
                        (a, b) -> a + "-" + b)
                );
        });
    }
    
    @Test
    public void testMapThen_3() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
        assertAsString(
                "[1-2-3, 2-3-6, 3-4-9, 4-5-12, 5-6-15]",
                list
                .mapThen(
                        theInteger,
                        theInteger.plus(1),
                        theInteger.time(3),
                        (a, b, c) -> a + "-" + b + "-" + c)
                );
        });
    }
    
    @Test
    public void testMapThen_4() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1-2-3-1, 2-3-6-4, 3-4-9-9, 4-5-12-16, 5-6-15-25]",
                    list
                        .mapThen(
                                theInteger,
                                theInteger.plus(1),
                                theInteger.time(3),
                                theInteger.square(),
                                (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d)
                        );
        });
    }
    
    @Test
    public void testMapThen_5() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1-2-3-1-1, 2-3-6-4-2, 3-4-9-9-6, 4-5-12-16-24, 5-6-15-25-120]",
                    list
                        .mapThen(
                                theInteger,
                                theInteger.plus(1),
                                theInteger.time(3),
                                theInteger.square(),
                                theInteger.factorial(),
                                (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e)
                        );
        });
    }
    
    @Test
    public void testMapThen_6() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertAsString(
                    "[1-2-3-1-1--1, 2-3-6-4-2--2, 3-4-9-9-6--3, 4-5-12-16-24--4, 5-6-15-25-120--5]",
                    list
                        .mapThen(
                            theInteger,
                            theInteger.plus(1),
                            theInteger.time(3),
                            theInteger.square(),
                            theInteger.factorial(),
                            theInteger.negate(),
                            (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f)
                        );
        });
    }
    
    //-- FuncListWithMapGroup --
    
//    @Test
//    public void testMapGroup_specific() {
//        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
//            assertAsString(
//                    "[One:Two, Two:Three, Three:Four, Four:Five, Five:Six, Six:Seven, Seven:Eight]",
//                    list.mapGroup((a,b) -> a+":"+b));
//            assertAsString(
//                    "[One:Two:Three, Two:Three:Four, Three:Four:Five, Four:Five:Six, Five:Six:Seven, Six:Seven:Eight]",
//                    list.mapGroup((a,b,c) -> a+":"+b+":"+c));
//            assertAsString(
//                    "[One:Two:Three:Four, Two:Three:Four:Five, Three:Four:Five:Six, Four:Five:Six:Seven, Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d) -> a+":"+b+":"+c+":"+d));
//            assertAsString(
//                    "[One:Two:Three:Four:Five, Two:Three:Four:Five:Six, Three:Four:Five:Six:Seven, Four:Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d,e) -> a+":"+b+":"+c+":"+d+":"+e));
//            assertAsString(
//                    "[One:Two:Three:Four:Five:Six, Two:Three:Four:Five:Six:Seven, Three:Four:Five:Six:Seven:Eight]",
//                    list.mapGroup((a,b,c,d,e,f) -> a+":"+b+":"+c+":"+d+":"+e+":"+f));
//        });
//    }
//    
    @Test
    public void testMapGroup_count() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            ToIntFunction<IntStreamPlus> joiner = intStream -> Integer.parseInt(intStream.mapToString().join());
            assertAsString(
                    "[12, 23, 34, 45, 56]",
                    list.mapGroup(2, joiner));
            assertAsString(
                    "[123, 234, 345, 456]",
                    list.mapGroup(3, joiner));
            assertAsString(
                    "[1234, 2345, 3456]",
                    list.mapGroup(4, joiner));
            assertAsString(
                    "[12345, 23456]",
                    list.mapGroup(5, joiner));
            assertAsString(
                    "[123456]",
                    list.mapGroup(6, joiner));
        });
    }
    
    @Test
    public void testMapGroup() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString(
                    "[12, 23, 34, 45, 56, 67, 78]",
                    list.mapTwo((a, b) -> a*10 + b));
        });
    }
    
    @Test
    public void testMapGroupToInt() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString(
                    "[12, 23, 34, 45, 56, 67, 78]",
                    list.mapTwoToInt((a, b) -> a*10 + b));
            assertAsString(
                    "[12, 23, 34, 45, 56, 67, 78]",
                    list.mapGroupToInt(2, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToDouble() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString(
                    "[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]",
                    list.mapTwoToDouble((a, b) -> a*10 + b));
            assertAsString(
                    "[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]",
                    list.mapGroupToDouble(2, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToObj() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertAsString(
                    "[(1,2), (2,3), (3,4), (4,5), (5,6), (6,7), (7,8)]",
                    list.mapTwoToObj());
            assertAsString(
                    "[1-2, 2-3, 3-4, 4-5, 5-6, 6-7, 7-8]",
                    list.mapTwoToObj((a,b) -> a + "-" + b));
            assertAsString(
                    "[[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6], [5, 6, 7], [6, 7, 8]]",
                    list.mapGroupToObj(3).map(IntStreamPlus::toListString));
            assertAsString(
                    "[123, 234, 345, 456, 567, 678]",
                    list.mapGroupToObj(3, ints -> Integer.parseInt(ints.mapToString().join())));
        });
    }
    
    //-- FuncListWithMapToMap --
    
    @Test
    public void testMapToMap_1() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1}, "
                    + "{<1>:3}, "
                    + "{<1>:5}, "
                    + "{<1>:7}, "
                    + "{<1>:11}, "
                    + "{<1>:13}, "
                    + "{<1>:17}"
                    + "]",
                    list
                        .mapToMap(
                                "<1>", theInteger)
                        .map(map -> map.sorted())
                        );
        });
    }
    
    @Test
    public void testMapToMap_2() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1}, "
                    + "{<1>:3, <2>:-3}, "
                    + "{<1>:5, <2>:-5}, "
                    + "{<1>:7, <2>:-7}, "
                    + "{<1>:11, <2>:-11}, "
                    + "{<1>:13, <2>:-13}, "
                    + "{<1>:17, <2>:-17}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_3() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2}, "
                    + "{<1>:3, <2>:-3, <3>:4}, "
                    + "{<1>:5, <2>:-5, <3>:6}, "
                    + "{<1>:7, <2>:-7, <3>:8}, "
                    + "{<1>:11, <2>:-11, <3>:12}, "
                    + "{<1>:13, <2>:-13, <3>:14}, "
                    + "{<1>:17, <2>:-17, <3>:18}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_4() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2, <4>:-1}, "
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1}, "
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3}, "
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5}, "
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9}, "
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11}, "
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_5() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3}, "
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9}, "
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15}, "
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21}, "
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33}, "
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39}, "
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_6() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1}, "
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81}, "
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625}, "
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401}, "
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641}, "
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561}, "
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3),
                            "<6>", theInteger.pow(4).asInteger())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_7() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1}, "
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9}, "
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25}, "
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49}, "
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121}, "
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169}, "
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3),
                            "<6>", theInteger.pow(4).asInteger(),
                            "<7>", theInteger.square())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_8() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                      "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1},\n"
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2},\n"
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2},\n"
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3},\n"
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3},\n"
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4},\n"
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4}",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3),
                            "<6>", theInteger.pow(4).asInteger(),
                            "<7>", theInteger.square(),
                            "<8>", theInteger.squareRoot().asInteger())
                    .map(map -> map.sorted())
                    .join(",\n")
                    );
        });
    }
    
    @Test
    public void testMapToMap_9() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertAsString(
                    "["
                    + "{<1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1, <9>:1}, "
                    + "{<1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2, <9>:6}, "
                    + "{<1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2, <9>:120}, "
                    + "{<1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3, <9>:5040}, "
                    + "{<1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3, <9>:39916800}, "
                    + "{<1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4, <9>:1932053504}, "
                    + "{<1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4, <9>:-288522240}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3),
                            "<6>", theInteger.pow(4).asInteger(),
                            "<7>", theInteger.square(),
                            "<8>", theInteger.squareRoot().asInteger(),
                            "<9>", theInteger.factorial())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_10() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
            assertAsString(
                    "["
                    + "{<10>:1, <1>:1, <2>:-1, <3>:2, <4>:-1, <5>:3, <6>:1, <7>:1, <8>:1, <9>:1}, "
                    + "{<10>:2, <1>:3, <2>:-3, <3>:4, <4>:1, <5>:9, <6>:81, <7>:9, <8>:2, <9>:6}, "
                    + "{<10>:3, <1>:5, <2>:-5, <3>:6, <4>:3, <5>:15, <6>:625, <7>:25, <8>:2, <9>:120}, "
                    + "{<10>:4, <1>:7, <2>:-7, <3>:8, <4>:5, <5>:21, <6>:2401, <7>:49, <8>:3, <9>:5040}, "
                    + "{<10>:6, <1>:11, <2>:-11, <3>:12, <4>:9, <5>:33, <6>:14641, <7>:121, <8>:3, <9>:39916800}, "
                    + "{<10>:7, <1>:13, <2>:-13, <3>:14, <4>:11, <5>:39, <6>:28561, <7>:169, <8>:4, <9>:1932053504}, "
                    + "{<10>:9, <1>:17, <2>:-17, <3>:18, <4>:15, <5>:51, <6>:83521, <7>:289, <8>:4, <9>:-288522240}, "
                    + "{<10>:10, <1>:19, <2>:-19, <3>:20, <4>:17, <5>:57, <6>:130321, <7>:361, <8>:4, <9>:109641728}, "
                    + "{<10>:12, <1>:23, <2>:-23, <3>:24, <4>:21, <5>:69, <6>:279841, <7>:529, <8>:5, <9>:862453760}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theInteger,
                            "<2>", theInteger.negate(),
                            "<3>", theInteger.plus(1),
                            "<4>", theInteger.minus(2),
                            "<5>", theInteger.time(3),
                            "<6>", theInteger.pow(4).asInteger(),
                            "<7>", theInteger.square(),
                            "<8>", theInteger.squareRoot().asInteger(),
                            "<9>", theInteger.factorial(),
                            "<10>", theInteger.dividedBy(2).asInteger())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    //-- FuncListWithMapToTuple --
    
    @Test
    public void testMapToTuple_2() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "["
                    + "(1,2), "
                    + "(3,4), "
                    + "(5,6), "
                    + "(7,8), "
                    + "(11,12)"
                    + "]",
                    list
                        .mapToTuple(
                                theInteger,
                                theInteger.plus(1))
                        );
        });
    }
    
    @Test
    public void testMapToTuple_3() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "["
                    + "(1,2,3), "
                    + "(3,4,9), "
                    + "(5,6,15), "
                    + "(7,8,21), "
                    + "(11,12,33)"
                    + "]",
                    list
                    .mapToTuple(
                            theInteger,
                            theInteger.plus(1),
                            theInteger.time(3))
                    );
        });
    }
    
    @Test
    public void testMapToTuple_4() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "["
                    + "(1,2,3,1), "
                    + "(3,4,9,9), "
                    + "(5,6,15,25), "
                    + "(7,8,21,49), "
                    + "(11,12,33,121)"
                    + "]",
                    list
                    .mapToTuple(
                            theInteger,
                            theInteger.plus(1),
                            theInteger.time(3),
                            theInteger.square())
                    );
        });
    }
    
    @Test
    public void testMapToTuple_5() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "["
                    + "(1,2,3,1,1), "
                    + "(3,4,9,9,6), "
                    + "(5,6,15,25,120), "
                    + "(7,8,21,49,5040), "
                    + "(11,12,33,121,39916800)"
                    + "]",
                    list
                    .mapToTuple(
                            theInteger,
                            theInteger.plus(1),
                            theInteger.time(3),
                            theInteger.square(),
                            theInteger.factorial())
                    );
        });
    }
    
    @Test
    public void testMapToTuple_6() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "["
                    + "(1,2,3,1,1,-1), "
                    + "(3,4,9,9,6,-3), "
                    + "(5,6,15,25,120,-5), "
                    + "(7,8,21,49,5040,-7), "
                    + "(11,12,33,121,39916800,-11)"
                    + "]",
                    list
                    .mapToTuple(
                            theInteger,
                            theInteger.plus(1),
                            theInteger.time(3),
                            theInteger.square(),
                            theInteger.factorial(),
                            theInteger.negate())
                    );
        });
    }
    
    //-- StreamPlusWithMapWithIndex --
    
    @Test
    public void testMapWithIndex() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "[(0,1), (1,3), (2,5), (3,7), (4,11)]",
                    list
                    .mapWithIndex()
                    );
        });
    }
    
    @Test
    public void testMapWithIndex_combine() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "[1, 13, 25, 37, 411]",
                    list
                    .mapWithIndex((i, each) -> Integer.parseInt( i + "" + each))
                    );
        });
    }
    
    @Test
    public void testMapToObjWithIndex_combine() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "[0: 1, 1: 3, 2: 5, 3: 7, 4: 11]",
                    list
                    .mapToObjWithIndex((i, each) -> i + ": " + each)
                    );
            assertAsString(
                    "[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]",
                    list
                    .mapWithIndex(i -> i*2, (i, each) -> i + ": " + each)
                    );
            assertAsString(
                    "[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]",
                    list
                    .mapWithIndex(i -> i*2, (i, each) -> i + ": " + each)
                    );
            assertAsString(
                    "[0: 2, 1: 6, 2: 10, 3: 14, 4: 22]",
                    list
                    .mapToObjWithIndex(i -> "" + i*2, (i, each) -> i + ": " + each)
                    );
        });
    }
    
    //-- FuncListWithModify --
    
    @Test
    public void testAccumulate() {
        run(IntFuncList.of(1, 2, 3, 4, 5), list -> {
            assertAsString(
                    "[1, 3, 6, 10, 15]",
                    list.accumulate((prev, current) -> prev + current));
            
            assertAsString(
                    "[1, 12, 123, 1234, 12345]",
                    list.accumulate((prev, current)->prev*10 + current));
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
        run(IntFuncList.naturalNumbers(300).filter(theInteger.thatIsNotOne()).toFuncList(), list -> {
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
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first  = new AtomicLong(-1);
            val logs   = new ArrayList<String>();
            list
            .spawn(i -> TimeFuncs.Sleep(i*timePrecision + 5).thenReturn(i).defer())
            .forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start    = first.get();
                val end      = System.currentTimeMillis();
                val duration = Math.round((end - start)/(1.0 * timePrecision))*timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("["
                    + "Result:{ Value: 2 } -- 0, "
                    + "Result:{ Value: 3 } -- " + (1*timePrecision) + ", "
                    + "Result:{ Value: 4 } -- " + (2*timePrecision) + ", "
                    + "Result:{ Value: 11 } -- " + (9*timePrecision) + ""
                    + "]",
                    logs.toString());
        });
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first  = new AtomicLong(-1);
            val logs   = new ArrayList<String>();
            list
            .spawn(i -> DeferAction.from(() -> {
                Thread.sleep(i*timePrecision + 5);
                return i;
            }))
            .forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start    = first.get();
                val end      = System.currentTimeMillis();
                val duration = Math.round((end - start)/(1.0 * timePrecision))*timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("["
                    + "Result:{ Value: 2 } -- 0, "
                    + "Result:{ Value: 3 } -- " + (1*timePrecision) + ", "
                    + "Result:{ Value: 4 } -- " + (2*timePrecision) + ", "
                    + "Result:{ Value: 11 } -- " + (9*timePrecision) + ""
                    + "]",
                    logs.toString());
        });
    }
    
    @Test
    public void testSpawn_limit() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val first   = new AtomicLong(-1);
            val actions = new ArrayList<DeferAction<Integer>>();
            val logs    = new ArrayList<String>();
            list
            .spawn(i -> {
                DeferAction<Integer> action = Sleep(i*50 + 5).thenReturn(i).defer();
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
            assertEquals("[Result:{ Value: 2 } -- 0]",
                    logs.toString());
            assertEquals(
                    "Result:{ Value: 2 }, " +
                    "Result:{ Cancelled: Stream closed! }, " +
                    "Result:{ Cancelled: Stream closed! }, " +
                    "Result:{ Cancelled: Stream closed! }",
                    actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
        });
    }
    
    //-- FuncListWithPeek --
    
    @Test
    public void testPeekAs() {
        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekAs(e -> "<" + e + ">", e -> elementStrings.add(e))
                .join() // To terminate the stream
                ;
            assertAsString("[<0>, <1>, <2>, <3>, <4>, <5>]", elementStrings);
        });
    }
    
    @Test
    public void testPeekBy_map() {
        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekBy(s -> !("" + s).contains("2"), e -> elementStrings.add("" + e))
                .join() // To terminate the stream
                ;
            assertAsString("[0, 1, 3, 4, 5]", elementStrings);
            
            elementStrings.clear();
            list
                .peekBy(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add("" + e))
                .join() // To terminate the stream
                ;
            assertAsString("[0, 1, 3, 4, 5]", elementStrings);
        });
    }
    
    @Test
    public void testPeekAs_map() {
        run(IntFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekAs(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add((String)e))
                .join() // To terminate the stream
                ;
            assertAsString("[<0>, <1>, <3>, <4>, <5>]", elementStrings);
        });
    }
    
    //-- FuncListWithPipe --
    
    @Test
    public void testPipeable() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "[1, 3, 5, 7, 11]",
                    list
                        .pipable()
                        .pipeTo(IntFuncList::toListString));
        });
    }
    
    @Test
    public void testPipe() {
        run(IntFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertAsString(
                    "[1, 3, 5, 7, 11]",
                    list.pipe(IntFuncList::toListString));
        });
    }
    
    
    //-- FuncListWithReshape --
    
    @Test
    public void testSegment() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                    "["
                    + "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]"
                    + "]",
                    list
                    .segment(6)
                    .mapToObj(IntFuncList::toString));
        });
    }
    
    @Test
    public void testSegment_sizeFunction() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                      "[" 
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
                    + "[16, 17, 18, 19]"
                    + "]",
                    list
                    .segment(i -> i));
        });
        // Empty
        run(IntFuncList.wholeNumbers(0), list -> {
            assertAsString(
                      "[]",
                    list
                    .segment(i -> i));
        });
        // End at exact boundary
        run(IntFuncList.wholeNumbers(8), list -> {
            assertAsString(
                      "[" 
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7]"
                    + "]",
                    list
                    .segment(i -> i));
        });
    }
    
    @Test
    public void testSegmentWhen() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                    "["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]"
                    + "]",
                    list
                    .segmentWhen(theInteger.thatIsDivisibleBy(3))
                    .map        (IntFuncList::toListString)
            );
        });
    }
    
    @Test
    public void testSegmentAfter() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                    "["
                    + "[0], "
                    + "[1, 2, 3], "
                    + "[4, 5, 6], "
                    + "[7, 8, 9], "
                    + "[10, 11, 12], "
                    + "[13, 14, 15], "
                    + "[16, 17, 18], "
                    + "[19]"
                    + "]",
                    list
                    .segmentAfter(theInteger.thatIsDivisibleBy(3))
                    .map         (IntFuncList::toListString)
            );
        });
    }
    
    @Test
    public void testSegmentBetween() {
        IntPredicate startCondition = i ->(i % 10) == 3;
        IntPredicate endCondition   = i ->(i % 10) == 6;
        
        run(IntFuncList.wholeNumbers(75), list -> {
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition)
                    .skip          (5)
                    .limit         (3));
            
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, true)
                    .skip   (5)
                    .limit  (3));
            
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
            
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, IncompletedSegment.included)
                    .skip          (5)
                    .limit         (3));
            
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, IncompletedSegment.excluded)
                    .skip          (5)
                    .limit         (3));
        });
        
        
        // Edge cases
        
        // Empty
        run(IntFuncList.wholeNumbers(0), list -> {
            assertAsString(
                    "[]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Not enough
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                    "[]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact
        run(IntFuncList.wholeNumbers(67), list -> {
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact - 1
        run(IntFuncList.wholeNumbers(66), list -> {
            assertAsString(
                    "["
                    + "[53, 54, 55, 56]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact + 1
        run(IntFuncList.wholeNumbers(68), list -> {
            assertAsString(
                    "["
                    + "[53, 54, 55, 56], "
                    + "[63, 64, 65, 66]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        
        // From start
        run(IntFuncList.wholeNumbers(30), list -> {
            assertAsString(
                    "["
                    + "[3, 4, 5, 6], "
                    + "[13, 14, 15, 16], "
                    + "[23, 24, 25, 26]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false));
        });
        
        // Incomplete start
        run(IntFuncList.wholeNumbers(30).skip(5), list -> {
            assertAsString(
                    "["
                    + "[13, 14, 15, 16], "
                    + "[23, 24, 25, 26]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false));
        });
    }
    
    @Test
    public void testSegmentByPercentiles() {
        run(IntFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertAsString(
                    "[" +
                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
                    "]", list.segmentByPercentiles(30,   80));
            assertAsString(
                    "[" +
                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
                    "]", list.segmentByPercentiles(30.0, 80.0));
            assertAsString(
                    "[" +
                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
                    "]", list.segmentByPercentiles(IntFuncList   .of(30,   80)));
            assertAsString(
                    "[" +
                        "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], " +
                        "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], " +
                        "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]" +
                    "]", list.segmentByPercentiles(DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper() {
        run(IntFuncList.wholeNumbers(50), list -> {
            assertAsString(
                    "["
                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, 30, 80));
            assertAsString(
                    "["
                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, 30.0, 80.0));
            assertAsString(
                    "["
                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, IntFuncList.of(30,   80)));
            assertAsString(
                    "["
                    + "[49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35], "
                    + "[34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10], "
                    + "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper_comparator() {
        run(IntFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertAsString(
                    "["
                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30, 80));
            assertAsString(
                    "["
                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, 30.0, 80.0));
            assertAsString(
                    "["
                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, IntFuncList   .of(30,   80)));
            assertAsString(
                    "["
                    + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], "
                    + "[15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39], "
                    + "[40, 41, 42, 43, 44, 45, 46, 47, 48, 49]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, (a, b) -> b - a, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    //-- FuncListWithSort --
    
    @Test
    public void testSortedBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString(
                    "[1, 2, 3, 4]", 
                    list.sortedBy(theInteger.plus(2).square()));
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertAsString(
                    "[4, 3, 2, 1]",
                    list.sortedBy(
                            i -> (i + 2)*(i + 2),
                            (a,b)->b-a));
            // Using comparable access.
            assertAsString(
                    "[4, 3, 2, 1]",
                    list.sortedBy(
                            theInteger.plus(2).square(),
                            (a,b)->b-a));
        });
    }
    
    //-- FuncListWithSplit --
    
    @Test
    public void testSplitTuple() {
        run(IntFuncList.wholeNumbers(20).toFuncList(), list -> {
            assertAsString(
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
            String Other = "Other";
            assertAsString(
                    "{"
                    + "Other:[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",  theInteger.thatIsDivisibleBy(2),
                           Other)
                    .sorted()
                    .toString());
            assertAsString(
                    "{"
                    + "Other:[1, 5, 7, 11, 13, 17, 19], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",   theInteger.thatIsDivisibleBy(2),
                           "Three", theInteger.thatIsDivisibleBy(3),
                           Other)
                    .sorted()
                    .toString());
            assertAsString(
                    "{"
                    + "Five:[5], "
                    + "Other:[1, 7, 11, 13, 17, 19], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",   theInteger.thatIsDivisibleBy(2),
                           "Three", theInteger.thatIsDivisibleBy(3),
                           "Five",  theInteger.thatIsDivisibleBy(5),
                           Other)
                    .sorted()
                    .toString());
            assertAsString(
                    "{"
                    + "Five:[5], "
                    + "Seven:[7], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
                    + "Three:[3, 9, 15], "
                    + "Other:[1, 11, 13, 17, 19]"
                    + "}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           "Three",  theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5),
                           "Seven",  theInteger.thatIsDivisibleBy(7),
                           Other)
                    .toString());
            assertAsString(
                    "{"
                    + "Eleven:[11], "
                    + "Five:[5], "
                    + "Other:[1, 13, 17, 19], "
                    + "Seven:[7], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           "Three",  theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5),
                           "Seven",  theInteger.thatIsDivisibleBy(7),
                           "Eleven", theInteger.thatIsDivisibleBy(11),
                           Other)
                    .sorted()
                    .toString());
            
            // Ignore some values
            
            assertAsString(
                    "{"
                    + "Eleven:[11], "
                    + "Five:[5], "
                    + "Other:[1, 13, 17, 19], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           null,     theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5),
                           null,     theInteger.thatIsDivisibleBy(7),
                           "Eleven", theInteger.thatIsDivisibleBy(11),
                           Other)
                    .sorted()
                    .toString());
            
            // Ignore others
            
            assertAsString(
                    "{"
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",   theInteger.thatIsDivisibleBy(2),
                           "Three", theInteger.thatIsDivisibleBy(3))
                    .sorted()
                    .toString());
            
            assertAsString(
                    "{"
                    + "Five:[5], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           "Three",  theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5))
                    .sorted()
                    .toString());
            
            assertAsString(
                    "{"
                    + "Five:[5], "
                    + "Seven:[7], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           "Three",  theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5),
                           "Seven",  theInteger.thatIsDivisibleBy(7))
                    .sorted()
                    .toString());
            
            assertAsString(
                    "{"
                    + "Eleven:[11], "
                    + "Five:[5], "
                    + "Seven:[7], "
                    + "Three:[3, 9, 15], Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
                     list
                    .split("Two",    theInteger.thatIsDivisibleBy(2),
                           "Three",  theInteger.thatIsDivisibleBy(3),
                           "Five",   theInteger.thatIsDivisibleBy(5),
                           "Seven",  theInteger.thatIsDivisibleBy(7),
                           "Eleven", theInteger.thatIsDivisibleBy(11))
                    .sorted()
                    .toString());
            
            assertAsString(
                    "{"
                    + "Eleven:[11], "
                    + "Five:[5], "
                    + "Seven:[7], "
                    + "Thirteen:[13], "
                    + "Three:[3, 9, 15], "
                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
                    + "}",
                     list
                    .split("Two",      theInteger.thatIsDivisibleBy(2),
                           "Three",    theInteger.thatIsDivisibleBy(3),
                           "Five",     theInteger.thatIsDivisibleBy(5),
                           "Seven",    theInteger.thatIsDivisibleBy(7),
                           "Eleven",   theInteger.thatIsDivisibleBy(11),
                           "Thirteen", theInteger.thatIsDivisibleBy(13))
                    .sorted()
                    .toString());
        });
    }
    
    @Test
    public void testSplit_ignore() {
        run(IntFuncList.wholeNumbers(20), list -> {
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null)
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null)
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null)
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null, theInteger.thatIsDivisibleBy(7),
                           (String)null)
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null, theInteger.thatIsDivisibleBy(7),
                           (String)null, theInteger.thatIsDivisibleBy(11),
                           (String)null)
                    .sorted()
                    .toString());
            
            // No other
            
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2))
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3))
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5))
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null, theInteger.thatIsDivisibleBy(7))
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null, theInteger.thatIsDivisibleBy(7),
                           (String)null, theInteger.thatIsDivisibleBy(11))
                    .sorted()
                    .toString());
            assertAsString(
                    "{}",
                     list
                    .split((String)null, theInteger.thatIsDivisibleBy(2),
                           (String)null, theInteger.thatIsDivisibleBy(3),
                           (String)null, theInteger.thatIsDivisibleBy(5),
                           (String)null, theInteger.thatIsDivisibleBy(7),
                           (String)null, theInteger.thatIsDivisibleBy(11),
                           (String)null, theInteger.thatIsDivisibleBy(13))
                    .sorted()
                    .toString());
        });
    }
    
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
            assertEquals(
                    "{"
                    + "FizzBuzz:[0, 15], "
                    + "Buzz:[5, 10], "
                    + "Fizz:[3, 6, 9, 12, 18]"
                    + "}",
                    toString);
        });
    }
    
    @Test
    public void testAggregate() {
        assertEquals(
                "[0, 1, 3, 6, 10, 15, 21, 28, 36, 45]",
                IntFuncList
                    .range  (0, 10)
                    .map    (Accumulator.ofInt(new SumInt()).orElse(-1))
                    .toList()
                    .toString());
        
        val list = IntFuncList
            .range    (0, 100)
            .takeUntil(Accumulator.ofInt(new SumInt()).orElse(-1).thatGreaterThan(100));
        assertEquals(
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]",
                list.toString());
        System.out.println(list.sum());
    }
    
    @Test
    public void test() {
        val list = IntFuncList.range(0, 10).accumulate(new SumInt());
        System.out.println(list.toString());
        System.out.println(list.toString());
    }
    
}
