package functionalj.list;

import static functionalj.functions.StrFuncs.join;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.list.FuncList.listOf;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.intlist.IntFuncListBuilder;
import functionalj.list.intlist.IntFuncListDerived;
import functionalj.stream.CollectorPlus;
import functionalj.stream.intstream.IntAccumulator;
import functionalj.stream.intstream.IntCollectorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.Tuple2;
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
    
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
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
    
    private void runExpectReadOnlyListException(IntFuncList list, FuncUnit1<IntFuncList> action) {
        try {
            action.accept(list);
            fail("Exception ReadOnlyListException");
        } catch (ReadOnlyListException e) {
        }
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
    public void testEmpty_intFuncList() {
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
    
    //-- From --
    
    @Test
    public void testFrom_array() {
        run(IntFuncList.from(new int[] {1, 2, 3}), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<Integer> collection = Arrays.asList(One, Two, Three, null);
        run(IntFuncList.from(collection, -1), list -> {
            assertStrings("[1, 2, 3, -1]", list);
        });
        Set<Integer> set = new LinkedHashSet<>(collection);
        run(IntFuncList.from(set, -2), list -> {
            assertStrings("[1, 2, 3, -2]", list);
        });
        FuncList<Integer> lazyList = FuncList.of(One, Two, Three, null);
        run(IntFuncList.from(lazyList, -3), list -> {
            assertStrings("[1, 2, 3, -3]", list);
            assertTrue   (list.isLazy());
        });
        FuncList<Integer> eagerList = FuncList.of(One, Two, Three, null).eager();
        run(IntFuncList.from(eagerList, -4), list -> {
            assertStrings("[1, 2, 3, -4]", list);
            assertTrue   (list.isEager());
        });
    }
    
    @Test
    public void testFrom_funcList() {
        run(IntFuncList.from(true, IntFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1, 2, 3]", list);
            assertTrue   (list.isLazy());
        });
        run(IntFuncList.from(false, IntFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1, 2, 3]", list);
            assertTrue   (list.isEager());
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(IntFuncList.from(IntStreamPlus.infiniteInt().limit(3)), list -> {
            assertStrings("[0, 1, 2]", list.limit(3));
        });
    }
    
    @Test
    public void testFrom_streamSupplier() {
        run(IntFuncList.from(() -> IntStreamPlus.infiniteInt()), list -> {
            assertStrings("[0, 1, 2, 3, 4]",                list.limit(5));
            assertStrings("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", list.limit(10));
        });
    }
    
    @Test
    public void testZeroes() {
        run(IntFuncList.zeroes().limit(5), list -> {
            assertStrings("[0, 0, 0, 0, 0]", list);
            assertStrings("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
        run(IntFuncList.zeroes(5), list -> {
            assertStrings("[0, 0, 0, 0, 0]", list);
            assertStrings("[0, 5, 0, 0, 0]", list.with(1, 5));
        });
    }
    
    @Test
    public void testOnes() {
        run(IntFuncList.ones().limit(5), list -> {
            assertStrings("[1, 1, 1, 1, 1]", list);
            assertStrings("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
        run(IntFuncList.ones(5), list -> {
            assertStrings("[1, 1, 1, 1, 1]", list);
            assertStrings("[1, 5, 1, 1, 1]", list.with(1, 5));
        });
    }
    
    @Test
    public void testRepeat() {
        run(IntFuncList.repeat(0, 42), list -> {
            assertStrings("[0, 42, 0, 42, 0]",         list.limit(5));
            assertStrings("[0, 42, 0, 42, 0, 42, 0]", list.limit(7));
        });
        run(IntFuncList.repeat(IntFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertStrings("[0, 1, 2, 42, 0, 0, 1]",           list.limit(7));
            assertStrings("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testCycle() {
        run(IntFuncList.cycle(0, 1, 42), list -> {
            assertStrings("[0, 1, 42, 0, 1]",        list.limit(5));
            assertStrings("[0, 1, 42, 0, 1, 42, 0]", list.limit(7));
        });
        run(IntFuncList.cycle(IntFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertStrings("[0, 1, 2, 42, 0, 0, 1]",           list.limit(7));
            assertStrings("[0, 1, 2, 42, 0, 0, 1, 2, 42, 0]", list.limit(10));
        });
    }
    
    @Test
    public void testLoop() {
        run(IntFuncList.loop(),  list -> assertStrings("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.loop(5), list -> assertStrings("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testLoopBy() {
        run(IntFuncList.loopBy(3),    list -> assertStrings("[0, 3, 6, 9, 12]", list.limit(5)));
        run(IntFuncList.loopBy(3, 5), list -> assertStrings("[0, 3, 6, 9, 12]", list));
    }
    
    @Test
    public void testInfinite() {
        run(IntFuncList.infinite(),    list -> assertStrings("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.infiniteInt(), list -> assertStrings("[0, 1, 2, 3, 4]", list.limit(5)));
    }
    
    @Test
    public void testNaturalNumbers() {
        run(IntFuncList.naturalNumbers(),  list -> assertStrings("[1, 2, 3, 4, 5]", list.limit(5)));
        run(IntFuncList.naturalNumbers(5), list -> assertStrings("[1, 2, 3, 4, 5]", list));
    }
    
    @Test
    public void testWholeNumbers() {
        run(IntFuncList.wholeNumbers(),  list -> assertStrings("[0, 1, 2, 3, 4]", list.limit(5)));
        run(IntFuncList.wholeNumbers(5), list -> assertStrings("[0, 1, 2, 3, 4]", list));
    }
    
    @Test
    public void testRange() {
        run(IntFuncList.range( 3, 7), list -> assertStrings("[3, 4, 5, 6]",          list.limit(5)));
        run(IntFuncList.range(-3, 3), list -> assertStrings("[-3, -2, -1, 0, 1, 2]", list.limit(10)));
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
                IntSupplier supplier = ()-> counter.getAndIncrement();
                return supplier;
            }),
            list -> {
                assertStrings("[0, 1, 2, 3, 4]", list.limit(5));
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
                assertStrings("[0, 1, 2, 3, 4]", list);
            }
        );
    }
    
    //-- Iterate --
    
    @Test
    public void testIterate() {
        run(IntFuncList.iterate(1,    (i)    -> 2*(i + 1)), list -> assertStrings("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(IntFuncList.iterate(1, 2, (a, b) -> a + b),     list -> assertStrings("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]",         list.limit(10)));
    }
    
    //-- Compound --
    
    @Test
    public void testCompound() {
        run(IntFuncList.compound(1,    (i)    -> 2*(i + 1)), list -> assertStrings("[1, 4, 10, 22, 46, 94, 190, 382, 766, 1534]", list.limit(10)));
        run(IntFuncList.compound(1, 2, (a, b) -> a + b),     list -> assertStrings("[1, 2, 3, 5, 8, 13, 21, 34, 55, 89]",         list.limit(10)));
    }
    
    //-- zipOf --
    
    @Test
    public void testZipOf_toTuple() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[(1000,1), (2000,2), (3000,3), (4000,4)]",
                        IntFuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_toTuple_default() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[(1000,1), (2000,2), (3000,3), (4000,4), (5000,-1)]",
                        IntFuncList.zipOf(list1, -1000, list2, -1));
        });
        
        run(IntFuncList.of(1000, 2000, 3000, 4000),
            IntFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertStrings(
                        "[(1000,1), (2000,2), (3000,3), (4000,4), (-1000,5)]",
                        IntFuncList.zipOf(list1, -1000, list2, -1));
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(IntFuncList.of(1000, 2000, 3000, 4000, 5000),
            IntFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
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
                assertStrings(
                        "[1000, 4000, 9000, 16000, -5000]",
                        IntFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a*b));
        });
        run(IntFuncList.of(1000, 2000, 3000, 4000),
            IntFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertStrings(
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
            assertStrings("[1, 2, 3]", list);
        });
        run(funcList2.add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
        run(funcList3.add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    //-- Derive --
    
    @Test
    public void testDeriveFrom() {
        run(IntFuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.mapToInt(v -> -v)), list -> {
            assertStrings("[-1, -2, -3]", list);
        });
        run(IntFuncList.deriveFrom(IntFuncList.of(1, 2, 3), s -> s.map(v -> -v)), list -> {
            assertStrings("[-1, -2, -3]", list);
        });
        run(IntFuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToInt(v -> (int)Math.round(-v))), list -> {
            assertStrings("[-1, -2, -3]", list);
        });
    }
    
    @Test
    public void testDeriveTo() {
        run(IntFuncList.deriveToObj(IntFuncList.of(One, Two, Three), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
            assertTrue   (list instanceof FuncList);
            assertStrings("[-1-, -2-, -3-]", list);
        });
        run(IntFuncList.deriveToInt(IntFuncList.of(One, Two, Three), s -> s.map(v -> v + 5)), list -> {
            assertStrings("[6, 7, 8]", list);
        });
        run(IntFuncList.deriveToDouble(IntFuncList.of(One, Two, Three), s -> s.mapToDouble(v -> 3.0*v)), list -> {
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
    public void testIsEagerIsLazy() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue(list.lazy().isLazy());
            assertTrue(list.eager().isEager());
            
            assertTrue(list.lazy().freeze().isLazy());
            
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
                    .eager()
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
            assertStrings("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertStrings("[1, 2, 3, 4, 5]", list.limit(5));
            assertStrings("[1, 2, 3, 4, 5]", logs);
        }
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is eager
            val list = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList().eager();
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
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .lazy()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theInteger.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
            // The list has not been materialized so nothing goes through peek.
            assertStrings("[]", logs1);
            assertStrings("[]", logs2);
            
            // Get part of them so those peek will goes through the peek
            assertStrings("[4, 5, 6, 7, 8]", list.limit(5));
            
            // Now that the list has been materialize all the element has been through the logs
            
            // The first log has all the number until there are 5 elements that are bigger than 3.
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8]", logs1);
            //                       1  2  3  4  5
            
            // The second log captures all the number until 5 of them that are bigger than 3.
            assertStrings("[4, 5, 6, 7, 8]", logs2);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            
            val orgData = IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .eager()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theInteger.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
            // Since the list is eager, all the value pass through all peek all the time
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs1);
            assertStrings("[4, 5, 6, 7, 8, 9, 10]", logs2);
            // Get part of them so those peek will goes through the peek
            assertStrings("[4, 5, 6, 7, 8]", list.limit(5));
            // No more passing through the log stay still
            assertStrings("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", logs1);
            assertStrings("[4, 5, 6, 7, 8, 9, 10]", logs2);
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
            
            assertStrings("[1, 2, 3]", funcList.map(value -> value).toImmutableList());
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
            assertStrings("[1, 2, 3]", streamPlus.toListString());
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
        run(FuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            for(val value : list) {
                logs.add("" + value);
            }
            assertStrings("[1, 2, 3]", logs);
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
            assertTrue (list.containsAllOf(listOf(Two, Three)));
            assertFalse(list.containsAllOf(listOf(Two, Five)));
        });
    }
    
    @Test
    public void testJavaList_containsSomeOf() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsSomeOf(Two, Five));
            assertTrue (list.containsSomeOf(listOf(Two, Five)));
            assertFalse(list.containsSomeOf(listOf(Five, Seven)));
        });
    }
    
    @Test
    public void testForEach() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEach(s -> logs.add("" + s));
            assertStrings("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachOrdered(s -> logs.add("" + s));
            assertStrings("[1, 2, 3]", logs);
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
            assertStrings("6", list.collect(sum));
            
            Supplier<StringBuffer>                 supplier    = ()          -> new StringBuffer();
            ObjIntConsumer<StringBuffer>           accumulator = (buffer, i) -> buffer.append(i);
            BiConsumer<StringBuffer, StringBuffer> combiner    = (b1, b2)    -> b1.append(b2.toString());
            assertStrings("123", list.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testSize() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("4", list.size());
        });
    }
    
    @Test
    public void testCount() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("4", list.count());
        });
    }
    
    @Test
    public void testSum() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("6", list.sum());
        });
    }
    
    @Test
    public void testProduct() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalInt[6]", list.product());
        });
    }
    
    @Test
    public void testMinMax() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalInt[1]", list.min());
            assertStrings("OptionalInt[4]", list.max());
        });
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalInt[1],OptionalInt[4])", list.minMax());
        });
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalInt[4],OptionalInt[1])", list.minMax((a,b) -> b - a));
        });
    }
    
    @Test
    public void testMinByMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalInt[1]", list.minBy(a ->  a));
            assertStrings("OptionalInt[4]", list.maxBy(a ->  a));
            assertStrings("OptionalInt[4]", list.minBy(a -> -a));
            assertStrings("OptionalInt[1]", list.maxBy(a -> -a));
        });
        
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalInt[1]", list.minBy(a ->  a, (a,b)->a-b));
            assertStrings("OptionalInt[4]", list.maxBy(a ->  a, (a,b)->a-b));
            assertStrings("OptionalInt[4]", list.minBy(a -> -a, (a,b)->a-b));
            assertStrings("OptionalInt[1]", list.maxBy(a -> -a, (a,b)->a-b));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalInt[1],OptionalInt[4])", list.minMaxBy(a ->  a));
        });
        
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalInt[4],OptionalInt[1])", list.minMaxBy(a -> a, (a,b)->b-a));
        });
    }
    
    @Test
    public void testMinOfMaxOf() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalInt[1]", list.minOf(a ->  a));
            assertStrings("OptionalInt[4]", list.maxOf(a ->  a));
            assertStrings("OptionalInt[4]", list.minOf(a -> -a));
            assertStrings("OptionalInt[1]", list.maxOf(a -> -a));
        });
    }
    
    @Test
    public void testMinIndex() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[0]", list.minIndex());
        });
    }
    
    @Test
    public void testMaxIndex() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[5]", list.maxIndex());
        });
    }
    
    @Test
    public void testMinIndexBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[0]", list.minIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMinIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            IntPredicate     condition = value -> value > 2;
            IntUnaryOperator operator  = value -> value;
            assertStrings("OptionalInt[2]", list.minIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testMaxIndexBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[5]", list.maxIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMaxIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            IntPredicate     condition = value -> value > 2;
            IntUnaryOperator operator  = value -> value;
            assertStrings("OptionalInt[5]", list.maxIndexOf(condition, operator));
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
            assertStrings("OptionalInt[1]", list.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalInt[1]", list.findAny());
        });
    }
    
    @Test
    public void testFindLast() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalInt[3]", list.findLast());
        });
    }
    
    @Test
    public void testFirstResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalInt[1]", list.firstResult());
        });
    }
    
    @Test
    public void testLastResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalInt[3]", list.lastResult());
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
            assertStrings("[2, 3]",       list.subList(1, 3));
            assertStrings("[2, 3, 4, 5]", list.subList(1, 10));
        });
    }
    
    //-- IntFuncListWithGroupingBy --
    
    @Test
    public void testGroupingBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "{1:[1, 2], 2:[3, 4], 3:[5]}",
                    list
                    .groupingBy(theInteger.dividedBy(2).toInteger())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_aggregate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "{1:[1.0, 2.0], 2:[3.0, 4.0], 3:[5.0]}",
                    list
                    .groupingBy(theInteger.dividedBy(2).toInteger(), l -> l.mapToDouble())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_collect() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
//                    "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before sum
                    "{1:3, 2:7, 3:5}",
                    list
                    .groupingBy(theInteger.dividedBy(2).toInteger(), () -> new Sum())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_process() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val sumHalf = new SumHalf();
            assertStrings(
//                  "{1:[1, 2], 2:[3, 4], 3:[5]}",  << Before half
//                  "{1:[0, 1], 2:[1, 2], 3:[2]}",  << Half
                    "{1:1, 2:3, 3:2}",
                    list
                    .groupingBy(theInteger.dividedBy(2).toInteger(), sumHalf)
                    .sortedByKey(theInteger));
        });
    }
    
    //-- Functional list
    
    @Test
    public void testMapToString() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1, 2, 3, 4, 5]",
                    list
                    .mapToString()
                    );
        });
    }
    
    @Test
    public void testMap() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2, 4, 6, 8, 10]",
                    list
                    .map(theInteger.time(2))
            );
        });
    }
    
    @Test
    public void testMapToInt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[10, 20, 30]",
                    list
                    .map(theInteger.time(10))
            );
        });
    }
    
    @Test
    public void testMapToDouble() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[1.0, 1.4142135623730951, 1.7320508075688772]", 
                    list.mapToDouble(theInteger.squareRoot()));
        });
    }
    
    @Test
    public void testMapToObj() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[-1-, -2-, -3-, -4-, -5-]",
                    list
                    .mapToObj(i -> "-" + i + "-")
                    );
        });
    }
    
    //-- FlatMap --
    
    @Test
    public void testFlatMap() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]",
                    list.flatMap(i -> IntFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]",
                    list.flatMapToInt(i -> IntFuncList.cycle(i).limit(i)));
        });
    }
    
    @Test
    public void testFlatMapToDouble() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0]",
                    list
                    .flatMapToDouble(i -> DoubleFuncList.cycle(i).limit(i)));
        });
    }
    
    //-- Filter --
    
    @Test
    public void testFilter() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[3]",
                    list.filter(theInteger.time(2).thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testFilter_mapper() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[3]",
                    list.filter(theInteger.time(2), theInteger.thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testPeek() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            assertStrings("[1, 2, 3]", list.peek(i -> logs.add("" + i)));
            assertStrings("[1, 2, 3]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1, 2, 3]", list.limit(3));
        });
    }
    
    @Test
    public void testSkip() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[3, 4, 5]", list.skip(2));
        });
    }
    
    @Test
    public void testDistinct() {
        run(IntFuncList.of(One, Two, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list.distinct());
        });
    }
    
    @Test
    public void testSorted() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2, 4, 6, 8, 10]", list.map(theInteger.time(2)).sorted());
            assertStrings("[10, 8, 6, 4, 2]", list.map(theInteger.time(2)).sorted((a, b) -> (b - a)));
        });
    }
    
    @Test
    public void testBoxed() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list.boxed());
        });
    }
    
    @Test
    public void testToArray() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", Arrays.toString(list.toArray()));
        });
    }
    
    @Test
    public void testNullableOptionalResult() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("Nullable.of([1, 2, 3])",      list.__nullable());
            assertStrings("Optional[[1, 2, 3]]",         list.__optional());
            assertStrings("Result:{ Value: [1, 2, 3] }", list.__result());
        });
    }
    
    @Test
    public void testIndexOf() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Three), list -> {
            assertStrings("2", list.indexOf(Three));
        });
    }
    
    @Test
    public void testLastIndexOf() {
        run(IntFuncList.of(Three, One, Two, Three, Four, Five), list -> {
            assertStrings("3", list.lastIndexOf(Three));
        });
    }
    
    @Test
    public void testIndexesOf() {
        run(IntFuncList.of(One, Two, Three, Four, Two), list -> {
            assertStrings("[0, 2]", list.indexesOf(value -> value == One || value == Three));
            assertStrings("[1, 4]", list.indexesOf(Two));
        });
    }
    
    @Test
    public void testToBuilder() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3, 4, 5]", list.toBuilder().add(Four).add(Five).build());
        });
    }
    
    
    @Test
    public void testFirst() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[1]", list.first());
            assertStrings("[1, 2, 3]",      list.first(3));
        });
    }
    
    @Test
    public void testLast() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[5]", list.last());
            assertStrings("[3, 4, 5]",      list.last(3));
        });
    }
    
    @Test
    public void testAt() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]",     list.at(2));
            assertStrings("OptionalInt.empty",  list.at(10));
        });
    }
    
    @Test
    public void testTail() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2, 3, 4, 5]", list.tail());
        });
    }
    @Test
    public void testAppend() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",       list);
            assertStrings("[1, 2, 3, 4]", list.append(Four));
            assertStrings("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testAppendAll() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",       list);
            assertStrings("[1, 2, 3, 4, 5]", list.appendAll(Four, Five));
            assertStrings("[1, 2, 3, 4, 5]", list.appendAll(IntFuncList.listOf(Four, Five)));
            assertStrings("[1, 2, 3, 4, 5]", list.appendAll(IntFuncList.of(Four, Five)));
            assertStrings("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testPrepend() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",    list);
            assertStrings("[0, 1, 2, 3]", list.prepend(Zero));
            assertStrings("[1, 2, 3]",    list);
        });
    }
    
    @Test
    public void testPrependAll() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",        list);
            assertStrings("[-1, 0, 1, 2, 3]", list.prependAll(MinusOne, Zero));
            assertStrings("[-1, 0, 1, 2, 3]", list.prependAll(IntFuncList.listOf(MinusOne, Zero)));
            assertStrings("[-1, 0, 1, 2, 3]", list.prependAll(IntFuncList.of(MinusOne, Zero)));
            assertStrings("[1, 2, 3]",        list);
        });
    }
    
    @Test
    public void testWith() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",   list);
            assertStrings("[1, 0, 3]",   list.with(1, Zero));
            assertStrings("[1, 102, 3]", list.with(1, value -> value + 100));
            assertStrings("[1, 2, 3]",   list);
        });
    }
    
    @Test
    public void testInsertAt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",     list);
            assertStrings("[1, 0, 2, 3]", list.insertAt(1, Zero));
            assertStrings("[1, 2, 3]",    list);
        });
    }
    
    @Test
    public void testInsertAllAt() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]",       list);
            assertStrings("[1, 2, 0, 0, 3]", list.insertAt(2, Zero, Zero));
            assertStrings("[1, 2, 0, 0, 3]", list.insertAllAt(2, IntFuncList.listOf(Zero, Zero)));
            assertStrings("[1, 2, 0, 0, 3]", list.insertAllAt(2, IntFuncList.of(Zero, Zero)));
            assertStrings("[1, 2, 3]",       list);
        });
    }
    
    @Test
    public void testExclude() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1, 2, 3, 4, 5]", list);
            assertStrings("[1, 3, 4, 5]",    list.exclude(Two));
            assertStrings("[1, 3, 4, 5]",    list.exclude(theInteger.eq(Two)));
            assertStrings("[1, 3, 4, 5]",    list.excludeAt(1));
            assertStrings("[1, 5]",          list.excludeFrom(1, 3));
            assertStrings("[1, 4, 5]",       list.excludeBetween(1, 3));
            assertStrings("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testReverse() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1, 2, 3, 4, 5]", list);
            assertStrings("[5, 4, 3, 2, 1]", list.reverse());
            assertStrings("[1, 2, 3, 4, 5]", list);
        });
    }
    
    @Test
    public void testShuffle() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
            assertStrings  ("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
            assertNotEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list.shuffle().toString());
            assertStrings  ("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", list);
        });
    }
    
    @Test
    public void testQuery() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("[1, 2, 3, 4, 5, 6]", list);
            assertStrings("[(2,3), (5,6)]",     list.query(theInteger.thatIsDivisibleBy(3)));
            assertStrings("[1, 2, 3, 4, 5, 6]", list);
        });
    }
    
    //-- AsIntFuncListWithConversion --
    
    @Test
    public void testToByteArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertStrings("[65, 66, 67, 68]", Arrays.toString(list.toByteArray(c -> (byte)(int)c)));
        });
    }
    
    @Test
    public void testToIntArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertStrings("[65, 66, 67, 68]", Arrays.toString(list.toIntArray(c -> (int)c)));
        });
    }
    
    @Test
    public void testToDoubleArray() {
        run(IntFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertStrings("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(list.toDoubleArray(c -> (double)(int)c)));
        });
    }
    
    @Test
    public void testToArrayList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toArrayList();
            assertStrings("[1, 2, 3]", newList);
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    @Test
    public void testToList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toJavaList();
            assertStrings("[1, 2, 3]", newList);
            assertTrue(newList instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val newList = list.toMutableList();
            assertStrings("[1, 2, 3]", newList);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    //-- join --
    
    @Test
    public void testJoin() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("123", list.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("1, 2, 3", list.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(IntFuncList.of(One, Two, Three), list -> {
            assertStrings("[1, 2, 3]", list);
        });
    }
    
    //-- toMap --
    
    @Test
    public void testToMap() {
        run(IntFuncList.of(One, Three, Five), list -> {
            assertStrings("{1:1, 3:3, 5:5}", list.toMap(theInteger));
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(IntFuncList.of(One, Three, Five), list -> {
            assertStrings("{1:1, 3:9, 5:25}", list.toMap(theInteger, theInteger.square()));
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(IntFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1+3+5
            assertStrings("{0:2, 1:9}", list.toMap(theInteger.remainderBy(2), theInteger, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(IntFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1*3*5
            assertStrings("{0:2, 1:15}", list.toMap(theInteger.remainderBy(2), (a, b) -> a * b));
        });
    }
    
    @Test
    public void testToSet() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val set    = list.toSet();
            assertStrings("[1, 2, 3]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(IntFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertStrings("[0:1, 1:2, 2:3]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[5];
            list.populateArray(array);
            assertStrings("[1, 2, 3, 4, 5]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[3];
            list.populateArray(array, 2);
            assertStrings("[0, 0, 1]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new int[5];
            list.populateArray(array, 1, 3);
            assertStrings("[0, 1, 2, 3, 0]", Arrays.toString(array));
        });
    }
    
    //-- AsIntFuncListWithMatch --
    
    @Test
    public void testFindFirst_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.findFirst(theInteger.square().thatGreaterThan(theInteger.time(2))));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.findFirst(theInteger.square().thatGreaterThan(theInteger.time(2))));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.findFirst(theInteger.square(), theInteger.thatGreaterThan(5)));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.findAny(theInteger.square(), theInteger.thatGreaterThan(5)));
        });
    }
    
    //-- AsFuncListWithStatistic --
    
    @Test
    public void testMinBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.minBy(theInteger.minus(3).square()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.maxBy(theInteger.minus(3).square().negate()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[6]", list.minBy(theInteger.minus(3).square(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalInt[3]", list.maxBy(theInteger.minus(3).square(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(IntFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("(OptionalInt[3],OptionalInt[6])", list.minMaxBy(theInteger.minus(3).square()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(IntFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalInt[1],OptionalInt[3])", list.minMaxBy(theInteger.minus(3).square(), (a, b) -> b-a));
        });
    }
    
    //-- IntFuncListWithCalculate --
    
    static class SumHalf implements IntCollectorPlus<int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                    supplier()                    { return ()       -> new int[] { 0 }; }
        @Override public IntAccumulator<int[]>              intAccumulator()              { return (a, i)   -> { a[0] += i / 2; }; }
        @Override public BinaryOperator<int[]>              combiner()                    { return (a1, a2) -> new int[] { a1[0] + a2[0] }; }
        @Override public Function<int[], Integer>           finisher()                    { return (a)      -> a[0]; }
        @Override public Set<Characteristics>               characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], Integer> collector()                   { return this; }
        @Override public Integer                            process(IntStreamPlus stream) { return stream.map(i -> i/2).sum(); }
    }
    static class Average implements IntCollectorPlus<int[], OptionalDouble> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                           supplier()                    { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>                     intAccumulator()              { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<int[]>                     combiner()                    { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<int[], OptionalDouble>           finisher()                    { return (a)      -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]); }
        @Override public Set<Characteristics>                      characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], OptionalDouble> collector()                   { return this; }
        @Override public OptionalDouble                            process(IntStreamPlus stream) { return stream.average(); }
    }
    static class MinInt implements IntCollectorPlus<int[], OptionalInt> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                        supplier()                    { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>                  intAccumulator()              { return (a, i)   -> { a[0] = Math.min(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<int[]>                  combiner()                    { return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<int[], OptionalInt>           finisher()                    { return (a)      -> (a[1] == 0) ? OptionalInt.empty() : OptionalInt.of(a[0]); }
        @Override public Set<Characteristics>                   characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], OptionalInt> collector()                   { return this; }
        @Override public OptionalInt                            process(IntStreamPlus stream) { return stream.min(); }
    }
    static class MaxInt implements IntCollectorPlus<int[], OptionalInt> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                        supplier()                    { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>                  intAccumulator()              { return (a, i)   -> { a[0] = Math.max(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<int[]>                  combiner()                    { return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<int[], OptionalInt>           finisher()                    { return (a)      -> (a[1] == 0) ? OptionalInt.empty() : OptionalInt.of(a[0]); }
        @Override public Set<Characteristics>                   characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], OptionalInt> collector()                   { return this; }
        @Override public OptionalInt                            process(IntStreamPlus stream) { return stream.max(); }
    }
    static class SumInt implements IntCollectorPlus<int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                    supplier()                    { return ()       -> new int[] { 0 }; }
        @Override public IntAccumulator<int[]>              intAccumulator()              { return (a, i)   -> { a[0] += i; }; }
        @Override public BinaryOperator<int[]>              combiner()                    { return (a1, a2) -> new int[] { a1[0] + a2[0] }; }
        @Override public Function<int[], Integer>           finisher()                    { return (a)      -> a[0]; }
        @Override public Set<Characteristics>               characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], Integer> collector()                   { return this; }
        @Override public Integer                            process(IntStreamPlus stream) { return stream.sum(); }
    }
    static class AvgInt implements IntCollectorPlus<int[], OptionalInt> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>                        supplier()                    { return ()       -> new int[] { 0, 0 }; }
        @Override public IntAccumulator<int[]>                  intAccumulator()              { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<int[]>                  combiner()                    { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<int[], OptionalInt>           finisher()                    { return (a)      -> (a[1] == 0) ? OptionalInt.empty() : OptionalInt.of(a[0]/a[1]); }
        @Override public Set<Characteristics>                   characteristics()             { return characteristics; }
        @Override public Collector<Integer, int[], OptionalInt> collector()                   { return this; }
        @Override public OptionalInt                            process(IntStreamPlus stream) { val avg = stream.average(); return avg.isPresent() ? OptionalInt.of((int)Math.round(avg.getAsDouble())) : OptionalInt.empty(); }
    }
    
    @Test
    public void testCalculate() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            assertStrings("9", list.calculate(sumHalf).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            assertStrings("(9,OptionalDouble[20.0])", list.calculate(sumHalf, average));
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val minInt = new MinInt();
            val maxInt = new MaxInt();
            val range = list.calculate(minInt, maxInt).mapTo((max, min) -> max.getAsInt() + min.getAsInt());
            assertStrings("11", range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            assertStrings("(9,OptionalDouble[20.0],OptionalInt[0])", list.calculate(sumHalf, average, minInt));
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
            assertStrings("sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0]", value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            assertStrings(
                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11])", 
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
            assertStrings(
                    "sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11]", 
                    value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            val average = new Average();
            val minInt  = new MinInt();
            val maxInt  = new MaxInt();
            val sumInt  = new SumInt();
            assertStrings(
                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11],20)",
                    list.calculate(sumHalf, average, minInt, maxInt, sumInt));
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(FuncList.of(Two, Three, Four, Eleven), list -> {
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
            assertStrings(
                    "sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11], max: OptionalInt[11], sumI: 20", 
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
            assertStrings(
                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11],20,OptionalInt[5])", 
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
            assertStrings("sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11], max: OptionalInt[11], sumI: 20, avgI: OptionalInt[5]", value);
        });
    }
    
    @Test
    public void testCalculate_of() {
        run(IntFuncList.of(Two, Three, Four, Eleven), list -> {
            val sum = new Sum();
            // 2*2 + 3*2 + 4*2 + 11*2
            // 4   + 6   + 8   + 22
            assertStrings("40", list.calculate(sum.ofInt(theInteger.time(2))));
        });
    }
    
//    //-- FuncListWithCombine --
//    
//    @Test
//    public void testAppendWith() {
//        run(FuncList.of(One, Two), FuncList.of(Three, Four), (list1, list2) -> {
//            assertStrings(
//                        "[One, Two, Three, Four]",
//                        list1.appendWith(list2)
//                    );
//        });
//    }
//    
//    @Test
//    public void testParependWith() {
//        run(FuncList.of(One, Two), FuncList.of(Three, Four), (list1, list2) -> {
//            assertStrings(
//                        "[One, Two, Three, Four]",
//                        list2.prependWith(list1)
//                    );
//        });
//    }
//        
//    @Test
//    public void testMerge() {
//        run(FuncList.of("A", "B", "C"),
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
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2)",
//                        listA.zipWith(listB).join(", "));
//            });
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2)",
//                        listA.zipWith(listB, RequireBoth).join(", "));
//            });
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "(A,0), (B,1), (C,2), (null,3), (null,4)",
//                        listA.zipWith(listB, AllowUnpaired).limit(5).join(", "));
//            });
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "A:0, B:1, C:2",
//                        listA.zipWith(listB, (c, i) -> c + ":" + i).join(", "));
//            });
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                assertStrings(
//                        "A:0, B:1, C:2",
//                        listA.zipWith(listB, RequireBoth, (c, i) -> c + ":" + i).join(", "));
//            });
//        run(FuncList.of("A", "B", "C"),
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
//        run(FuncList.of("A", "B", "C"),
//            IntFuncList.infinite().limit(10).boxed().map(theInteger.asString()).toFuncList(),
//            (listA, listB) -> {
//                val bool = new AtomicBoolean(true);
//                assertStrings("A, 1, C, 3, 4", listA.choose(listB, (a, b) -> {
//                    boolean curValue = bool.get();
//                    return bool.getAndSet(!curValue);
//                }).limit(5).join(", "));
//            });
//    }
//    
//    @Test
//    public void testChoose_AllowUnpaired() {
//        run(FuncList.of("A", "B", "C"),
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
//        run(FuncList.of("A", "B",  null, "C"), list -> {
//            assertStrings("[A, B, Z, C]", list.fillNull("Z"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_lens() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNull(Car.theCar.color, "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
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
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullWith(Car.theCar.color, () -> "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_supplier() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
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
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                    list.fillNullBy(Car.theCar.color, (Car car) -> "Black"));
//        });
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_function() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
//            assertStrings("[One, Three, Five]", list.filter(String.class));
//        });
//    }
//    
//    @Test
//    public void testFilterClass_withPredicate() {
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
//            assertStrings("[One, Five]", list.filter(String.class, theString.length().thatLessThan(5)));
//        });
//    }
//    
//    @Test
//    public void testFilter_withMappter() {
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsInt(str -> str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsLong() {
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsLong(str -> (long)str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsDouble() {
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsDouble(str -> (double)str.length(), i -> i >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterAsObject() {
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Three, Four, Five]", list.filterAsObject(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4));
//        });
//    }
//    
//    @Test
//    public void testFilterWithIndex() {
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
//            assertStrings("[Four, Five]", list.filterWithIndex((index, str) -> index > 2 && !str.startsWith("T")));
//        });
//    }
//    
//    @Test
//    public void testFilterNonNull() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Green, Red]",
//                    list.map(theCar.color).filterNonNull());
//        });
//    }
//    
//    @Test
//    public void testFilterNonNull_withMapper() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                    list.filterNonNull(theCar.color));
//        });
//    }
//    
//    @Test
//    public void testExcludeNull() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Green, Red]",
//                    list.map(theCar.color).excludeNull());
//        });
//    }
//    
//    @Test
//    public void testExcludeNull_withMapper() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                    list.excludeNull(theCar.color));
//        });
//    }
//    
//    @Test
//    public void testFilterMapper() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Car(color=Blue), Car(color=Red)]",
//                    list.filter(theCar.color, color -> Arrays.asList("Blue", "Red").contains(color)));
//        });
//    }
//    
//    @Test
//    public void testFilterOnly() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Red]",
//                    list.map(theCar.color).filterOnly("Blue", "Red"));
//        });
//    }
//    
//    @Test
//    public void testFilterIn_collection() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Blue, Red]",
//                    list.map(theCar.color).filterIn(asList("Blue", "Red")));
//        });
//    }
//    
//    @Test
//    public void testExcludeAny() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
//            assertStrings(
//                    "[Green, null]",
//                    list.map(theCar.color).excludeAny("Blue", "Red"));
//        });
//    }
//    
//    @Test
//    public void testExcludeIn_collection() {
//        run(FuncList.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), list -> {
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
//        run(FuncList.of(One, Two, Three), list -> {
//            assertStrings("[One, 3, 5]", list.flatMapOnly(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("" + s.length())));
//        });
//    }
//    
//    @Test
//    public void testFlatMapIf() {
//        run(FuncList.of(One, Two, Three), list -> {
//            assertStrings("[(One), [3], [5]]", list.flatMapIf(str -> str.toLowerCase().startsWith("t"), s -> FuncList.of("[" + s.length() + "]"), s -> FuncList.of("(" + s + ")")));
//        });
//    }
//    
//    //-- FuncListWithLimit --
//    
//    @Test
//    public void testSkipLimitLong() {
//        run(FuncList.of(One, Two, Three), list -> {
//            assertStrings("[Two]", list.skip((Long)1L).limit((Long)1L));
//        });
//    }
//    
//    @Test
//    public void testSkipLimitLongNull() {
//        run(FuncList.of(One, Two, Three), list -> {
//            assertStrings("[One, Two, Three]", list.skip(null).limit(null));
//        });
//    }
//    
//    @Test
//    public void testSkipLimitLongMinus() {
//        run(FuncList.of(One, Two, Three), list -> {
//            assertStrings("[One, Two, Three]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
//        });
//    }
//    
//    @Test
//    public void testSkipWhile() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[3, 4, 5, 4, 3, 2, 1]",       list.skipWhile(i -> i < 3));
//            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipWhile(i -> i > 3));
//        });
//    }
//    
//    @Test
//    public void testSkipUntil() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[4, 5, 4, 3, 2, 1]",          list.skipUntil(i -> i > 3));
//            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", list.skipUntil(i -> i < 3));
//        });
//    }
//    
//    @Test
//    public void testTakeWhile() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
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
//        run(FuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.takeWhile((a, b) -> b == a + 1));
//        });
//    }
//    
//    @Test
//    public void testTakeUtil() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
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
//        run(FuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.takeUntil((a, b) -> b > a + 1));
//        });
//    }
//    
//    @Test
//    public void testDropAfter() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4]", list.dropAfter(i -> i == 4));
//            //                       ^--- Include 4
//        });
//    }
//    
//    @Test
//    public void testDropAfter_previous() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
//            assertStrings("[1, 2, 3, 4, 5, 4]", list.dropAfter((a, b) -> b < a));
//            //                             ^--- Include 4
//        });
//    }
//    
//    @Test
//    public void testSkipTake() {
//        run(FuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
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
//        run(FuncList.of(One, Two, Three), list -> {
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
//        run(FuncList.of(One, Two, Three), list -> {
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
//        run(FuncList.of(One, Two, Three), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
//        run(FuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
//                list
//                .mapToObjWithIndex((i, each) -> i + ": " + each)
//                );
//        });
//    }
//    
//    @Test
//    public void testMapToIntWithIndex_combine() {
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[3, 6, 6, 8, 10]",
//                //  3 = 0 + 3 (One)
//                //  6 = 1 + 5 (Three)
//                //  6 = 2 + 4 (Five)
//                //  8 = 3 + 5 (Seven)
//                // 10 = 4 + 6 (Eleven)
//                list
//                .mapToIntWithIndex((index, value) -> index + value.length()));
//        });
//    }
//    
//    @Test
//    public void testMapToDoubleWithIndex_combine() {
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
//        assertStrings(
//                "[3.0, 6.0, 6.0, 8.0, 10.0]",
//                //  3 = 0 + 3 (One)
//                //  6 = 1 + 5 (Three)
//                //  6 = 2 + 4 (Five)
//                //  8 = 3 + 5 (Seven)
//                // 10 = 4 + 6 (Eleven)
//                list
//                .mapToDoubleWithIndex((index, value) -> index + value.length()));
//        });
//    }
//    
//    //-- FuncListWithModify --
//    
//    @Test
//    public void testAccumulate() {
//        run(FuncList.of(1, 2, 3, 4, 5), list -> {
//            assertStrings(
//                    "[1, 3, 6, 10, 15]",
//                    list.accumulate((prev, current) -> prev + current));
//            
//            assertStrings(
//                    "[1, 12, 123, 1234, 12345]",
//                    list.accumulate((prev, current)->prev*10 + current));
//        });
//    }
//    
//    @Test
//    public void testRestate() {
//        run(IntFuncList.wholeNumbers(20).map(i -> i % 5).toFuncList(), list -> {
//            assertStrings("[0, 1, 2, 3, 4]", list.restate((head, tail) -> tail.filter(x -> x != head)));
//        });
//    }
//    
//    @Test
//    public void testRestate_sieveOfEratosthenes() {
//        run(IntFuncList.naturalNumbers(300).filter(theInteger.thatIsNotOne()).boxed().toFuncList(), list -> {
//            assertStrings(
//                    "["
//                    + "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, "
//                    + "101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, "
//                    + "211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293"
//                    + "]",
//                  list.restate((head, tail) -> tail.filter(x -> x % head != 0)));
//        });
//    }
//    
//    @Test
//    public void testSpawn() {
//        run(FuncList.of(Two, Three, Four, Eleven), list -> {
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
//        run(FuncList.of(Two, Three, Four, Eleven), list -> {
//            val timePrecision = 100;
//            val first  = new AtomicLong(-1);
//            val logs   = new ArrayList<String>();
//            list
//            .spawn(F((String str) -> {
//                Thread.sleep(str.length()*timePrecision + 5);
//                return str;
//            }).defer())
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
//        run(FuncList.of(Two, Three, Four, Eleven), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
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
//        run(FuncList.of(0, One, 2, Three, 4, Five), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
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
//        run(FuncList.of(One, Three, Five, Seven, Eleven), list -> {
//            assertStrings(
//                    "[One, Three, Five, Seven, Eleven]",
//                    list.pipe(FuncList::toListString));
//        });
//    }
//    
//    
//    //-- FuncListWithReshape --
//    
//    @Test
//    public void testSegment() {
//        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[0, 1, 2, 3, 4, 5], "
//                    + "[6, 7, 8, 9, 10, 11], "
//                    + "[12, 13, 14, 15, 16, 17], "
//                    + "[18, 19]"
//                    + "]",
//                    list
//                    .segment(6)
//                    .map    (FuncList::toListString)
//            );
//        });
//    }
//    
//    @Test
//    public void testSegment_sizeFunction() {
//        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
//            assertStrings(
//                      "[" 
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
//                    + "[16, 17, 18, 19]"
//                    + "]",
//                    list
//                    .segment(i -> i));
//        });
//        // Empty
//        run(IntFuncList.wholeNumbers(0).boxed(), list -> {
//            assertStrings(
//                      "[]",
//                    list
//                    .segment(i -> i));
//        });
//        // End at exact boundary
//        run(IntFuncList.wholeNumbers(8).boxed(), list -> {
//            assertStrings(
//                      "[" 
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7]"
//                    + "]",
//                    list
//                    .segment(i -> i));
//        });
//    }
//    
//    @Test
//    public void testSegmentWhen() {
//        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8], "
//                    + "[9, 10, 11], "
//                    + "[12, 13, 14], "
//                    + "[15, 16, 17], "
//                    + "[18, 19]"
//                    + "]",
//                    list
//                    .segmentWhen(theInteger.thatIsDivisibleBy(3))
//                    .map        (FuncList::toListString)
//            );
//        });
//    }
//    
//    @Test
//    public void testSegmentAfter() {
//        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[0], "
//                    + "[1, 2, 3], "
//                    + "[4, 5, 6], "
//                    + "[7, 8, 9], "
//                    + "[10, 11, 12], "
//                    + "[13, 14, 15], "
//                    + "[16, 17, 18], "
//                    + "[19]"
//                    + "]",
//                    list
//                    .segmentAfter(theInteger.thatIsDivisibleBy(3))
//                    .map         (FuncList::toListString)
//            );
//        });
//    }
//    
//    @Test
//    public void testSegmentBetween() {
//        Predicate<Integer> startCondition = i ->(i % 10) == 3;
//        Predicate<Integer> endCondition   = i ->(i % 10) == 6;
//        
//        run(IntFuncList.wholeNumbers(75).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition)
//                    .skip          (5)
//                    .limit         (3));
//            
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, true)
//                    .skip   (5)
//                    .limit  (3));
//            
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//            
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, IncompletedSegment.included)
//                    .skip          (5)
//                    .limit         (3));
//            
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, IncompletedSegment.excluded)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        
//        
//        // Edge cases
//        
//        // Empty
//        run(IntFuncList.wholeNumbers(0).boxed(), list -> {
//            assertStrings(
//                    "[]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        // Not enough
//        run(IntFuncList.wholeNumbers(20).boxed(), list -> {
//            assertStrings(
//                    "[]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        // Exact
//        run(IntFuncList.wholeNumbers(67).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        // Exact - 1
//        run(IntFuncList.wholeNumbers(66).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        // Exact + 1
//        run(IntFuncList.wholeNumbers(68).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false)
//                    .skip          (5)
//                    .limit         (3));
//        });
//        
//        // From start
//        run(IntFuncList.wholeNumbers(30).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[3, 4, 5, 6], "
//                    + "[13, 14, 15, 16], "
//                    + "[23, 24, 25, 26]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false));
//        });
//        
//        // Incomplete start
//        run(IntFuncList.wholeNumbers(30).skip(5).boxed(), list -> {
//            assertStrings(
//                    "["
//                    + "[13, 14, 15, 16], "
//                    + "[23, 24, 25, 26]"
//                    + "]",
//                    list
//                    .segmentBetween(startCondition, endCondition, false));
//        });
//    }
//    
//    @Test
//    public void testSegmentByPercentiles() {
//        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
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
//        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
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
//        run(IntFuncList.wholeNumbers(50).boxed().toFuncList(), list -> {
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
//    
//    //-- FuncListWithSort --
//    
//    @Test
//    public void testSortedBy() {
//        run(FuncList.of(One, Two, Three, Four), list -> {
//            assertStrings("[One, Two, Four, Three]", list.sortedBy(String::length));
//            // Using comparable access.
//            assertStrings("[One, Two, Four, Three]", list.sortedBy(theString.length()));
//        });
//    }
//    
//    @Test
//    public void testSortedByComparator() {
//        run(FuncList.of(One, Two, Three, Four), list -> {
//            assertStrings(
//                    "[Three, Four, One, Two]",
//                    list.sortedBy(String::length, (a,b)->b-a));
//            // Using comparable access.
//            assertStrings(
//                    "[Three, Four, One, Two]",
//                    list.sortedBy(theString.length(), (a,b)->b-a));
//        });
//    }
//    
//    //-- FuncListWithSplit --
//    
//    @Test
//    public void testSplitTuple() {
//        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
//            assertStrings(
//                    "("
//                    + "[0, 2, 4, 6, 8, 10, 12, 14, 16, 18],"
//                    + "[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]"
//                    + ")",
//                     list
//                    .split(theInteger.thatIsDivisibleBy(2))
//                    .toString());
//        });
//    }
//        
//    @Test
//    public void testSplit() {
//        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
//            String Other = "Other";
//            assertStrings(
//                    "{"
//                    + "Other:[1, 3, 5, 7, 9, 11, 13, 15, 17, 19], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,  theInteger.thatIsDivisibleBy(2),
//                           Other)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{"
//                    + "Other:[1, 5, 7, 11, 13, 17, 19], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,   theInteger.thatIsDivisibleBy(2),
//                           Three, theInteger.thatIsDivisibleBy(3),
//                           Other)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{"
//                    + "Five:[5], "
//                    + "Other:[1, 7, 11, 13, 17, 19], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,   theInteger.thatIsDivisibleBy(2),
//                           Three, theInteger.thatIsDivisibleBy(3),
//                           Five,  theInteger.thatIsDivisibleBy(5),
//                           Other)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{"
//                    + "Five:[5], "
//                    + "Seven:[7], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18], "
//                    + "Three:[3, 9, 15], "
//                    + "Other:[1, 11, 13, 17, 19]"
//                    + "}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           Three,  theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5),
//                           Seven,  theInteger.thatIsDivisibleBy(7),
//                           Other)
//                    .toString());
//            assertStrings(
//                    "{"
//                    + "Eleven:[11], "
//                    + "Five:[5], "
//                    + "Other:[1, 13, 17, 19], "
//                    + "Seven:[7], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           Three,  theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5),
//                           Seven,  theInteger.thatIsDivisibleBy(7),
//                           Eleven, theInteger.thatIsDivisibleBy(11),
//                           Other)
//                    .sorted()
//                    .toString());
//            
//            // Ignore some values
//            
//            assertStrings(
//                    "{"
//                    + "Eleven:[11], "
//                    + "Five:[5], "
//                    + "Other:[1, 13, 17, 19], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           null,   theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5),
//                           null,   theInteger.thatIsDivisibleBy(7),
//                           Eleven, theInteger.thatIsDivisibleBy(11),
//                           Other)
//                    .sorted()
//                    .toString());
//            
//            // Ignore others
//            
//            assertStrings(
//                    "{"
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,   theInteger.thatIsDivisibleBy(2),
//                           Three, theInteger.thatIsDivisibleBy(3))
//                    .sorted()
//                    .toString());
//            
//            assertStrings(
//                    "{"
//                    + "Five:[5], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           Three,  theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5))
//                    .sorted()
//                    .toString());
//            
//            assertStrings(
//                    "{"
//                    + "Five:[5], "
//                    + "Seven:[7], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           Three,  theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5),
//                           Seven,  theInteger.thatIsDivisibleBy(7))
//                    .sorted()
//                    .toString());
//            
//            assertStrings(
//                    "{"
//                    + "Eleven:[11], "
//                    + "Five:[5], "
//                    + "Seven:[7], "
//                    + "Three:[3, 9, 15], Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]}",
//                     list
//                    .split(Two,    theInteger.thatIsDivisibleBy(2),
//                           Three,  theInteger.thatIsDivisibleBy(3),
//                           Five,   theInteger.thatIsDivisibleBy(5),
//                           Seven,  theInteger.thatIsDivisibleBy(7),
//                           Eleven, theInteger.thatIsDivisibleBy(11))
//                    .sorted()
//                    .toString());
//            
//            assertStrings(
//                    "{"
//                    + "Eleven:[11], "
//                    + "Five:[5], "
//                    + "Seven:[7], "
//                    + "Thirteen:[13], "
//                    + "Three:[3, 9, 15], "
//                    + "Two:[0, 2, 4, 6, 8, 10, 12, 14, 16, 18]"
//                    + "}",
//                     list
//                    .split(Two,      theInteger.thatIsDivisibleBy(2),
//                           Three,    theInteger.thatIsDivisibleBy(3),
//                           Five,     theInteger.thatIsDivisibleBy(5),
//                           Seven,    theInteger.thatIsDivisibleBy(7),
//                           Eleven,   theInteger.thatIsDivisibleBy(11),
//                           Thirteen, theInteger.thatIsDivisibleBy(13))
//                    .sorted()
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testSplit_ignore() {
//        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null)
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null, theInteger.thatIsDivisibleBy(7),
//                           (String)null)
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null, theInteger.thatIsDivisibleBy(7),
//                           (String)null, theInteger.thatIsDivisibleBy(11),
//                           (String)null)
//                    .sorted()
//                    .toString());
//            
//            // No other
//            
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2))
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3))
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5))
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null, theInteger.thatIsDivisibleBy(7))
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null, theInteger.thatIsDivisibleBy(7),
//                           (String)null, theInteger.thatIsDivisibleBy(11))
//                    .sorted()
//                    .toString());
//            assertStrings(
//                    "{}",
//                     list
//                    .split((String)null, theInteger.thatIsDivisibleBy(2),
//                           (String)null, theInteger.thatIsDivisibleBy(3),
//                           (String)null, theInteger.thatIsDivisibleBy(5),
//                           (String)null, theInteger.thatIsDivisibleBy(7),
//                           (String)null, theInteger.thatIsDivisibleBy(11),
//                           (String)null, theInteger.thatIsDivisibleBy(13))
//                    .sorted()
//                    .toString());
//        });
//    }
//    
//    @Test
//    public void testFizzBuzz() {
//        Function<FuncList<Integer>, FuncList<Integer>> listToList = s -> s.toImmutableList();
//        run(IntFuncList.wholeNumbers(20).boxed().toFuncList(), list -> {
//            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap))
//            .run(() -> {
//                FuncMap<String, FuncList<Integer>> splited
//                        = list
//                        .split(
//                            "FizzBuzz", i -> i % (3*5) == 0,
//                            "Buzz",     i -> i % 5     == 0,
//                            "Fizz",     i -> i % 3     == 0,
//                            null);
//                val string
//                        = splited
//                        .mapValue(listToList)
//                        .toString();
//                return string;
//            });
//            assertEquals(
//                    "{"
//                    + "FizzBuzz:[0, 15], "
//                    + "Buzz:[5, 10], "
//                    + "Fizz:[3, 6, 9, 12, 18]"
//                    + "}",
//                    toString);
//        });
//    }
//    
}
