package functionalj.list;

import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.theDouble;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theLong;
import static functionalj.list.FuncList.listOf;
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
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.functions.TimeFuncs;
import functionalj.lens.LensTest.Car;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.doublelist.DoubleFuncListBuilder;
import functionalj.list.doublelist.DoubleFuncListDerived;
import functionalj.list.doublelist.ImmutableDoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.doublestream.DoubleAccumulator;
import functionalj.stream.doublestream.DoubleCollectorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import lombok.val;

public class DoubleFuncListTest {
    
    static final double MinusOne = -1;
    static final double Zero     =  0;
    
    static final double One         =  1;
    static final double Two         =  2;
    static final double Three       =  3;
    static final double Four        =  4;
    static final double Five        =  5;
    static final double Six         =  6;
    static final double Seven       =  7;
    static final double Eight       =  8;
    static final double Nine        =  9;
    static final double Ten         = 10;
    static final double Eleven      = 11;
    static final double Twelve      = 12;
    static final double Thirteen    = 13;
    static final double Seventeen   = 17;
    static final double Nineteen    = 19;
    static final double TwentyThree = 23;
    
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    private void run(DoubleFuncList list, FuncUnit1<DoubleFuncList> action) {
        action.accept(list);
        action.accept(list);
    }
    
    private void run(DoubleFuncList list1, DoubleFuncList list2, FuncUnit2<DoubleFuncList, DoubleFuncList> action) {
        action.accept(list1, list2);
        action.accept(list1, list2);
    }
    
    @Test
    public void testEmpty() {
        run(DoubleFuncList.empty(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testEmptyFuncList() {
        run(DoubleFuncList.emptyList(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testEmpty_doubleFuncList() {
        run(DoubleFuncList.emptyDoubleList(), list -> {
            assertStrings("[]", list);
        });
    }
    
    @Test
    public void testOf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testAllOf() {
        run(DoubleFuncList.AllOf(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testDoubles() {
        run(DoubleFuncList.doubles(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testIntList() {
        run(DoubleFuncList.doubleList(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testListOf() {
        run(DoubleFuncList.ListOf(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
        run(DoubleFuncList.listOf(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    //-- From --
    
    @Test
    public void testFrom_array() {
        run(DoubleFuncList.from(new double[] {1, 2, 3}), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testFrom_collection() {
        Collection<Double> collection = Arrays.asList(One, Two, Three, null);
        run(DoubleFuncList.from(collection, -1), list -> {
            assertStrings("[1.0, 2.0, 3.0, -1.0]", list);
        });
        Set<Double> set = new LinkedHashSet<>(collection);
        run(DoubleFuncList.from(set, -2), list -> {
            assertStrings("[1.0, 2.0, 3.0, -2.0]", list);
        });
        FuncList<Double> lazyList = FuncList.of(One, Two, Three, null);
        run(DoubleFuncList.from(lazyList, -3), list -> {
            assertStrings("[1.0, 2.0, 3.0, -3.0]", list);
            assertTrue   (list.isLazy());
        });
        FuncList<Double> eagerList = FuncList.of(One, Two, Three, null).eager();
        run(DoubleFuncList.from(eagerList, -4), list -> {
            assertStrings("[1.0, 2.0, 3.0, -4.0]", list);
            assertTrue   (list.isEager());
        });
    }
    
    @Test
    public void testFrom_funcList() {
        run(DoubleFuncList.from(true, DoubleFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
            assertTrue   (list.isLazy());
        });
        run(DoubleFuncList.from(false, DoubleFuncList.of(One, Two, Three)), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
            assertTrue   (list.isEager());
        });
    }
    
    @Test
    public void testFrom_stream() {
        run(DoubleFuncList.from(DoubleStreamPlus.infiniteInt().limit(3)), list -> {
            assertStrings("[0.0, 1.0, 2.0]", list.limit(3));
        });
    }
    
    @Test
    public void testFrom_streamSupplier() {
        run(DoubleFuncList.from(() -> DoubleStreamPlus.infiniteInt()), list -> {
            assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]",                          list.limit(5));
            assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0]", list.limit(10));
        });
    }
    
    @Test
    public void testZeroes() {
        run(DoubleFuncList.zeroes().limit(5), list -> {
            assertStrings("[0.0, 0.0, 0.0, 0.0, 0.0]", list);
            assertStrings("[0.0, 5.0, 0.0, 0.0, 0.0]", list.with(1, 5));
        });
        run(DoubleFuncList.zeroes(5), list -> {
            assertStrings("[0.0, 0.0, 0.0, 0.0, 0.0]", list);
            assertStrings("[0.0, 5.0, 0.0, 0.0, 0.0]", list.with(1, 5));
        });
    }
    
    @Test
    public void testOnes() {
        run(DoubleFuncList.ones().limit(5), list -> {
            assertStrings("[1.0, 1.0, 1.0, 1.0, 1.0]", list);
            assertStrings("[1.0, 5.0, 1.0, 1.0, 1.0]", list.with(1, 5));
        });
        run(DoubleFuncList.ones(5), list -> {
            assertStrings("[1.0, 1.0, 1.0, 1.0, 1.0]", list);
            assertStrings("[1.0, 5.0, 1.0, 1.0, 1.0]", list.with(1, 5));
        });
    }
    
    @Test
    public void testRepeat() {
        run(DoubleFuncList.repeat(0, 42), list -> {
            assertStrings("[0.0, 42.0, 0.0, 42.0, 0.0]",        list.limit(5));
            assertStrings("[0.0, 42.0, 0.0, 42.0, 0.0, 42.0, 0.0]", list.limit(7));
        });
        run(DoubleFuncList.repeat(DoubleFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertStrings("[0.0, 1.0, 2.0, 42.0, 0.0, 0.0, 1.0]",           list.limit(7));
            assertStrings("[0.0, 1.0, 2.0, 42.0, 0.0, 0.0, 1.0, 2.0, 42.0, 0.0]", list.limit(10));
        });
    }
    
    @Test
    public void testCycle() {
        run(DoubleFuncList.cycle(0, 1, 42), list -> {
            assertStrings("[0.0, 1.0, 42.0, 0.0, 1.0]",            list.limit(5));
            assertStrings("[0.0, 1.0, 42.0, 0.0, 1.0, 42.0, 0.0]", list.limit(7));
        });
        run(DoubleFuncList.cycle(DoubleFuncList.cycle(0, 1, 2, 42).limit(5)), list -> {
            assertStrings("[0.0, 1.0, 2.0, 42.0, 0.0, 0.0, 1.0]",                 list.limit(7));
            assertStrings("[0.0, 1.0, 2.0, 42.0, 0.0, 0.0, 1.0, 2.0, 42.0, 0.0]", list.limit(10));
        });
    }
    
    @Test
    public void testLoop() {
        run(DoubleFuncList.loop(),  list -> assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list.limit(5)));
        run(DoubleFuncList.loop(5), list -> assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list));
    }
    
    @Test
    public void testLoopBy() {
        run(DoubleFuncList.loopBy(3),    list -> assertStrings("[0.0, 3.0, 6.0, 9.0, 12.0]", list.limit(5)));
        run(DoubleFuncList.loopBy(3, 5), list -> assertStrings("[0.0, 3.0, 6.0, 9.0, 12.0]", list));
    }
    
    @Test
    public void testInfinite() {
        run(DoubleFuncList.infinite(), list -> assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list.limit(5)));
    }
    
    @Test
    public void testNaturalNumbers() {
        run(DoubleFuncList.naturalNumbers(),  list -> assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.limit(5)));
        run(DoubleFuncList.naturalNumbers(5), list -> assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list));
    }
    
    @Test
    public void testWholeNumbers() {
        run(DoubleFuncList.wholeNumbers(),  list -> assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list.limit(5)));
        run(DoubleFuncList.wholeNumbers(5), list -> assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list));
    }
    
    @Test
    public void testRange() {
        run(DoubleFuncList.range( 3, 7),  list -> assertStrings("[3.0, 4.0, 5.0, 6.0]",              list.limit(5)));
        run(DoubleFuncList.range(-3, 3),  list -> assertStrings("[-3.0, -2.0, -1.0, 0.0, 1.0, 2.0]", list.limit(10)));
    }
    
    @Test
    public void testStepFrom() {
        run(DoubleFuncList.stepFrom( 3, 1), list -> assertStrings("[3.0, 4.0, 5.0, 6.0, 7.0]",              list.limit(5)));
        run(DoubleFuncList.stepFrom(-3, 1), list -> assertStrings("[-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0]", list.limit(7)));
        run(DoubleFuncList.stepFrom(3, -1), list -> assertStrings("[3.0, 2.0, 1.0, 0.0, -1.0, -2.0, -3.0]", list.limit(7)));
    }
    
    @Test
    public void testEquals() {
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableDoubleFuncList);
                assertTrue  (list2 instanceof ImmutableDoubleFuncList);
                assertTrue  (Objects.equals(list1, list2));
                assertEquals(list1, list2);
            });
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableDoubleFuncList);
                assertTrue  (list2 instanceof ImmutableDoubleFuncList);
                assertFalse    (Objects.equals(list1, list2));
                assertNotEquals(list1, list2);
            });
        
        // Make it a derived list
        run(DoubleFuncList.of(One, Two, Three).map(value -> value),
            DoubleFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof DoubleFuncListDerived);
                assertTrue  (list2 instanceof DoubleFuncListDerived);
                assertEquals(list1, list2);
            });
        run(DoubleFuncList.of(One, Two, Three)      .map(value -> value),
            DoubleFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof DoubleFuncListDerived);
                assertTrue     (list2 instanceof DoubleFuncListDerived);
                assertNotEquals(list1, list2);
            });
    }
    
    @Test
    public void testHashCode() {
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableDoubleFuncList);
                assertTrue  (list2 instanceof ImmutableDoubleFuncList);
                assertEquals(list1.hashCode(), list2.hashCode());
            });
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue     (list1 instanceof ImmutableDoubleFuncList);
                assertTrue     (list2 instanceof ImmutableDoubleFuncList);
                assertNotEquals(list1.hashCode(), list2.hashCode());
            });
        
        // Make it a derived list
        run(DoubleFuncList.of(One, Two, Three).map(value -> value),
            DoubleFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof DoubleFuncListDerived);
                assertTrue  (list2 instanceof DoubleFuncListDerived);
                assertEquals(list1.hashCode(), list2.hashCode());
            });
        run(DoubleFuncList.of(One, Two, Three).map(value -> value),
            DoubleFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof DoubleFuncListDerived);
                assertTrue     (list2 instanceof DoubleFuncListDerived);
                assertNotEquals(list1.hashCode(), list2.hashCode());
            });
    }
    
    @Test
    public void testToString() {
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three),
            (list1, list2) -> {
                assertTrue  (list1 instanceof ImmutableDoubleFuncList);
                assertTrue  (list2 instanceof ImmutableDoubleFuncList);
                assertEquals(list1.toString(), list2.toString());
            });
        run(DoubleFuncList.of(One, Two, Three),
            DoubleFuncList.of(One, Two, Three, Four),
            (list1, list2) -> {
                assertTrue     (list1 instanceof ImmutableDoubleFuncList);
                assertTrue     (list2 instanceof ImmutableDoubleFuncList);
                assertNotEquals(list1.toString(), list2.toString());
            });
        
        // Make it a derived list
        run(DoubleFuncList.of(One, Two, Three).map(value -> value),
            DoubleFuncList.of(One, Two, Three).map(value -> value),
            (list1, list2) -> {
                assertTrue  (list1 instanceof DoubleFuncListDerived);
                assertTrue  (list2 instanceof DoubleFuncListDerived);
                assertEquals(list1.toString(), list2.toString());
            });
        run(DoubleFuncList.of(One, Two, Three).map(value -> value),
            DoubleFuncList.of(One, Two, Three, Four).map(value -> value),
            (list1, list2) -> {
                assertTrue     (list1 instanceof DoubleFuncListDerived);
                assertTrue     (list2 instanceof DoubleFuncListDerived);
                assertNotEquals(list1.toString(), list2.toString());
            });
    }
    
    // -- Concat + Combine --
    
    @Test
    public void testConcat() {
        run(DoubleFuncList.concat(DoubleFuncList.of(One, Two), DoubleFuncList.of(Three, Four)),
            list -> {
                assertStrings("[1.0, 2.0, 3.0, 4.0]", list);
            }
        );
    }
    
    @Test
    public void testCombine() {
        run(DoubleFuncList.combine(DoubleFuncList.of(One, Two), DoubleFuncList.of(Three, Four)),
            list -> {
                assertStrings("[1.0, 2.0, 3.0, 4.0]", list);
            }
        );
    }
    
    //-- Generate --
    
    @Test
    public void testGenerate() {
        run(DoubleFuncList.generateWith(() -> {
                val counter = new AtomicInteger();
                DoubleSupplier supplier = ()-> counter.getAndIncrement();
                return supplier;
            }),
            list -> {
                assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list.limit(5));
            }
        );
        
        run(DoubleFuncList.generateWith(() -> {
                val counter = new AtomicInteger();
                DoubleSupplier supplier = ()->{
                    int count = counter.getAndIncrement();
                    if (count < 5)
                        return count;
                    
                    return FuncList.noMoreElement();
                };
                return supplier;
            }),
            list -> {
                assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list);
            }
        );
    }
    
    //-- Iterate --
    
    @Test
    public void testIterate() {
        run(DoubleFuncList.iterate(1, (i) -> 2*(i + 1)), list -> {
            assertStrings(
                    "[1.0, 4.0, 10.0, 22.0, 46.0, 94.0, 190.0, 382.0, 766.0, 1534.0]",
                    list.limit(10));
        });
        run(DoubleFuncList.iterate(1, 2, (a, b) -> a + b), list -> {
            assertStrings(
                    "[1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0]",
                    list.limit(10));
        });
    }
    
    //-- Compound --
    
    @Test
    public void testCompound() {
        run(DoubleFuncList.compound(1, (i) -> 2*(i + 1)), list -> {
            assertStrings(
                    "[1.0, 4.0, 10.0, 22.0, 46.0, 94.0, 190.0, 382.0, 766.0, 1534.0]",
                    list.limit(10));
        });
        run(DoubleFuncList.compound(1, 2, (a, b) -> a + b), list -> {
            assertStrings(
                    "[1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0]",
                    list.limit(10));
        });
    }
    
    //-- zipOf --
    
    @Test
    public void testZipOf_toTuple() {
        run(DoubleFuncList.of(1000, 2000, 3000, 4000, 5000),
            DoubleFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[(1000.0,1.0), (2000.0,2.0), (3000.0,3.0), (4000.0,4.0)]",
                        DoubleFuncList.zipOf(list1, list2));
        });
    }
    
    @Test
    public void testZipOf_toTuple_default() {
        run(DoubleFuncList.of(1000, 2000, 3000, 4000, 5000),
            DoubleFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[(1000.0,1.0), (2000.0,2.0), (3000.0,3.0), (4000.0,4.0), (5000.0,-1.0)]",
                        DoubleFuncList.zipOf(list1, -1000, list2, -1));
        });
        
        run(DoubleFuncList.of(1000, 2000, 3000, 4000),
            DoubleFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertStrings(
                        "[(1000.0,1.0), (2000.0,2.0), (3000.0,3.0), (4000.0,4.0), (-1000.0,5.0)]",
                        DoubleFuncList.zipOf(list1, -1000, list2, -1));
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(DoubleFuncList.of(1000, 2000, 3000, 4000, 5000),
            DoubleFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[1001.0, 2002.0, 3003.0, 4004.0]",
                        FuncList.zipOf(
                                list1, list2,
                                (a, b) -> a + + b));
        });
    }
    
    @Test
    public void testZipOf_merge_default() {
        run(DoubleFuncList.of(1000, 2000, 3000, 4000, 5000),
            DoubleFuncList.of(1, 2, 3, 4),
            (list1, list2) -> {
                assertStrings(
                        "[1000.0, 4000.0, 9000.0, 16000.0, -5000.0]",
                        DoubleFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a*b));
        });
        run(DoubleFuncList.of(1000, 2000, 3000, 4000),
            DoubleFuncList.of(1, 2, 3, 4, 5),
            (list1, list2) -> {
                assertStrings(
                        "[1000.0, 4000.0, 9000.0, 16000.0, -5000.0]",
                        DoubleFuncList.zipOf(list1, -1000, list2, -1, (a, b) -> a*b));
        });
    }
    
    @Test
    public void testNew() {
        DoubleFuncListBuilder funcList1 = DoubleFuncList.newListBuilder();
        DoubleFuncListBuilder funcList2 = DoubleFuncList.newBuilder();
        DoubleFuncListBuilder funcList3 = DoubleFuncList.newDoubleListBuilder();
        run(funcList1.add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
        run(funcList2.add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
        run(funcList3.add(One).add(Two).add(Three).build(), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    //-- Derive --
    
    @Test
    public void testDeriveFrom() {
        run(DoubleFuncList.deriveFrom(FuncList.of(One, Two, Three), s -> s.mapToDouble(v -> -v)), list -> {
            assertStrings("[-1.0, -2.0, -3.0]", list);
        });
        run(DoubleFuncList.deriveFrom(DoubleFuncList.of(1, 2, 3), s -> s.mapToDouble(v -> -v)), list -> {
            assertStrings("[-1.0, -2.0, -3.0]", list);
        });
        run(DoubleFuncList.deriveFrom(DoubleFuncList.of(1.0, 2.0, 3.0), s -> s.mapToDouble(v -> Math.round(-v))), list -> {
            assertStrings("[-1.0, -2.0, -3.0]", list);
        });
    }
    
//    @Test
//    public void testDeriveTo() {
//        run(DoubleFuncList.deriveToObj(DoubleFuncList.of(One, Two, Three), s -> s.mapToObj(v -> "-" + v + "-")), list -> {
//            assertTrue   (list instanceof FuncList);
//            assertStrings("[-1-, -2-, -3-]", list);
//        });
//        run(DoubleFuncList.deriveToInt(DoubleFuncList.of(One, Two, Three), s -> s.mapToInt(v -> (int)Math.round(v + 5))), list -> {
//            assertStrings("[6, 7, 8]", list);
//        });
//        run(DoubleFuncList.deriveToDouble(DoubleFuncList.of(One, Two, Three), s -> s.mapToDouble(v -> 3.0*v)), list -> {
//            assertStrings("[3.0, 6.0, 9.0]", list);
//        });
//    }
    
    //-- Predicate --
    
    @Test
    public void testTest_predicate() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
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
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue(list.lazy().isLazy());
            assertTrue(list.eager().isEager());
            
            assertTrue(list.lazy().freeze().isLazy());
            
            val logs = new ArrayList<String>();
            DoubleFuncList lazyList 
                    = list
                    .peek(value -> logs.add("" + value));
            
            lazyList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1.0, 2.0, 3.0]", logs.toString());
            
            // Lazy list will have to be re-evaluated again so the logs double.
            lazyList.forEach(value -> {});
            assertEquals("[1.0, 2.0, 3.0, 1.0, 2.0, 3.0]", logs.toString());
            
            
            logs.clear();
            assertEquals("[]", logs.toString());
            
            // Freeze but still lazy
            DoubleFuncList frozenList 
                    = list
                    .freeze()
                    .peek(value -> logs.add("" + value));
            frozenList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1.0, 2.0, 3.0]", logs.toString());
            
            // Freeze list but still lazy so it will have to be re-evaluated again so the logs double
            frozenList.forEach(value -> {});
            assertEquals("[1.0, 2.0, 3.0, 1.0, 2.0, 3.0]", logs.toString());
            
            
            // Eager list
            logs.clear();
            DoubleFuncList eagerList 
                    = list
                    .eager()
                    .peek(value -> logs.add("" + value));
            eagerList.forEach(value -> {});    // ForEach but do nothing
            assertEquals("[1.0, 2.0, 3.0]", logs.toString());
            
            // Eager list does not re-evaluate so the log stay the same.
            eagerList.forEach(value -> {});
            assertEquals("[1.0, 2.0, 3.0]", logs.toString());
        });
    }
    
    @Test
    public void testEagerLazy() {
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is lazy
            val list = DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList();
            // The function has not been materialized so nothing goes through peek.
            assertStrings("[]", logs);
            // Get part of them so those peek will goes through the peek
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.limit(5));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", logs);
        }
        {
            val logs = new ArrayList<String>();
            
            // We want to confirm that the list is eager
            val list = DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).peek(value -> logs.add("" + value)).toFuncList().eager();
            // The function has been materialized so all element goes through peek.
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs);
            // Even we only get part of it, 
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.limit(5));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs);
        }
    }
    
    @Test
    public void testEagerLazy_more() {
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            
            val orgData = DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .lazy()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theDouble.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
            // The list has not been materialized so nothing goes through peek.
            assertStrings("[]", logs1);
            assertStrings("[]", logs2);
            
            // Get part of them so those peek will goes through the peek
            assertStrings("[4.0, 5.0, 6.0, 7.0, 8.0]", list.limit(5));
            
            // Now that the list has been materialize all the element has been through the logs
            
            // The first log has all the number until there are 5 elements that are bigger than 3.
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]", logs1);
            //                             1.0  2.0  3.0  4.0  5.0
            
            // The second log captures all the number until 5 of them that are bigger than 3.
            assertStrings("[4.0, 5.0, 6.0, 7.0, 8.0]", logs2);
        }
        {
            val logs1 = new ArrayList<String>();
            val logs2 = new ArrayList<String>();
            
            val orgData = DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten).toFuncList();
            // We want to confirm that the list is lazy
            val list = orgData
                    .eager()
                    .peek   (v -> logs1.add("" + v))
                    .exclude(theDouble.thatLessThanOrEqualsTo(3))
                    .peek   (v -> logs2.add("" + v))
                    ;
            // Since the list is eager, all the value pass through all peek all the time
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs1);
            assertStrings("[4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs2);
            // Get part of them so those peek will goes through the peek
            assertStrings("[4.0, 5.0, 6.0, 7.0, 8.0]", list.limit(5));
            // No more passing through the log stay still
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs1);
            assertStrings("[4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", logs2);
        }
    }
    
    //-- List --
    
    @Test
    public void testToFuncList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val funcList = list.toFuncList();
            assertStrings("[1.0, 2.0, 3.0]", funcList.toString());
            assertTrue(funcList instanceof DoubleFuncList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val funcList = list.toJavaList();
            assertStrings("[1.0, 2.0, 3.0]", funcList);
            assertFalse(funcList instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val funcList = list.toImmutableList();
            assertStrings("[1.0, 2.0, 3.0]", funcList);
            assertTrue(funcList instanceof ImmutableDoubleFuncList);
            
            assertStrings("[1.0, 2.0, 3.0]", funcList.map(value -> value).toImmutableList());
            assertTrue(funcList instanceof ImmutableDoubleFuncList);
        });
    }
    
    @Test
    public void testIterable() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterable().iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextDouble());
            
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextDouble());
            
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextDouble());
            
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testIterator() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val iterator = list.iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue(One == iterator.nextDouble());
            
            assertTrue(iterator.hasNext());
            assertTrue(Two == iterator.nextDouble());
            
            assertTrue(iterator.hasNext());
            assertTrue(Three == iterator.nextDouble());
            
            assertFalse(iterator.hasNext());
        });
    }
    
    @Test
    public void testSpliterator() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            Spliterator.OfDouble spliterator = list.spliterator();
            DoubleStream         stream      = StreamSupport.doubleStream(spliterator, false);
            DoubleStreamPlus     streamPlus  = DoubleStreamPlus.from(stream);
            assertStrings("[1.0, 2.0, 3.0]", streamPlus.toListString());
        });
    }
    
    @Test
    public void testContainsAllOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsAllOf(One, Five));
            assertFalse(list.containsAllOf(One, Six));
        });
    }
    
    @Test
    public void testContainsAnyOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsAnyOf(One, Six));
            assertFalse(list.containsAnyOf(Six, Seven));
        });
    }
    
    @Test
    public void testContainsNoneOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertTrue (list.containsNoneOf(Six, Seven));
            assertFalse(list.containsNoneOf(One, Six));
        });
    }
    
    @Test
    public void testJavaList_for() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val logs = new ArrayList<String>();
            for(val value : list.boxed()) {
                logs.add("" + value);
            }
            assertStrings("[1.0, 2.0, 3.0]", logs);
        });
    }
    
    @Test
    public void testJavaList_size_isEmpty() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertEquals(3, list.size());
            assertFalse (list.isEmpty());
        });
        run(DoubleFuncList.empty(), list -> {
            assertEquals(0, list.size());
            assertTrue  (list.isEmpty());
        });
    }
    
    @Test
    public void testJavaList_contains() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue (list.contains(Two));
            assertFalse(list.contains(Five));
        });
    }
    
    @Test
    public void testJavaList_containsAllOf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsAllOf(listOf(Two, Three)));
            assertFalse(list.containsAllOf(listOf(Two, Five)));
        });
    }
    
    @Test
    public void testJavaList_containsSomeOf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue (list.containsSomeOf(Two, Five));
            assertTrue (list.containsSomeOf(listOf(Two, Five)));
            assertFalse(list.containsSomeOf(listOf(Five, Seven)));
        });
    }
    
    @Test
    public void testForEach() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEach(s -> logs.add("" + s));
            assertStrings("[1.0, 2.0, 3.0]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachOrdered(s -> logs.add("" + s));
            assertStrings("[1.0, 2.0, 3.0]", logs);
        });
    }
    
    @Test
    public void testReduce() {
        run(DoubleFuncList.of(1, 2, 3), list -> {
            assertStrings("6.0", list.reduce(0, (a, b) -> a + b));
            assertStrings("6.0", list.reduce((a, b) -> a + b).getAsDouble());
        });
    }
    
    static class Sum implements DoubleCollectorPlus<double[], Double> {
        
        @Override
        public Collector<Double, double[], Double> collector() {
            return this;
        }
        @Override
        public Double process(DoubleStreamPlus stream) {
            return stream.sum();
        }
        @Override
        public Supplier<double[]> supplier() {
            return () -> new double[] { 0.0 };
        }
        @Override
        public DoubleAccumulator<double[]> doubleAccumulator() {
            return (arrayDouble, d) -> {
                arrayDouble[0] = (arrayDouble[0] + d);
            };
        }
        @Override
        public BinaryOperator<double[]> combiner() {
            return (arrayDouble1, arrayDouble2) -> new double[] { arrayDouble1[0] + arrayDouble2[0] };
        }
        @Override
        public Function<double[], Double> finisher() {
            return arrayDouble -> arrayDouble[0];
        }
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    @Test
    public void testCollect() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val sum = new Sum();
            assertStrings("6.0", list.collect(sum));
            
            Supplier<StringBuffer>                 supplier    = ()          -> new StringBuffer();
            ObjDoubleConsumer<StringBuffer>        accumulator = (buffer, i) -> buffer.append(i);
            BiConsumer<StringBuffer, StringBuffer> combiner    = (b1, b2)    -> b1.append(b2.toString());
            assertStrings("1.02.03.0", list.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testSize() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("4", list.size());
        });
    }
    
    @Test
    public void testCount() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("4", list.count());
        });
    }
    
    @Test
    public void testSum() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("6.0", list.sum());
        });
    }
    
    @Test
    public void testProduct() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[6.0]", list.product());
        });
    }
    
    @Test
    public void testMinMax() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalDouble[1.0]", list.min());
            assertStrings("OptionalDouble[4.0]", list.max());
        });
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalDouble[1.0],OptionalDouble[4.0])", list.minMax());
        });
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalDouble[4.0],OptionalDouble[1.0])", list.minMax((a,b) -> Double.compare(b, a)));
        });
    }
    
    @Test
    public void testMinByMaxBy() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalDouble[1.0]", list.minBy(a ->  a));
            assertStrings("OptionalDouble[4.0]", list.maxBy(a ->  a));
            assertStrings("OptionalDouble[4.0]", list.minBy(a -> -a));
            assertStrings("OptionalDouble[1.0]", list.maxBy(a -> -a));
        });
        
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalDouble[1.0]", list.minBy(a ->  a, (a,b)->Double.compare(a, b)));
            assertStrings("OptionalDouble[4.0]", list.maxBy(a ->  a, (a,b)->Double.compare(a, b)));
            assertStrings("OptionalDouble[4.0]", list.minBy(a -> -a, (a,b)->Double.compare(a, b)));
            assertStrings("OptionalDouble[1.0]", list.maxBy(a -> -a, (a,b)->Double.compare(a, b)));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalDouble[1.0],OptionalDouble[4.0])", list.minMaxBy(a ->  a));
        });
        
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("(OptionalDouble[4.0],OptionalDouble[1.0])", list.minMaxBy(a -> a, (a,b)->Double.compare(b, a)));
        });
    }
    
    @Test
    public void testMinOfMaxOf() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings("OptionalDouble[1.0]", list.minOf(a ->  a));
            assertStrings("OptionalDouble[4.0]", list.maxOf(a ->  a));
            assertStrings("OptionalDouble[4.0]", list.minOf(a -> -a));
            assertStrings("OptionalDouble[1.0]", list.maxOf(a -> -a));
        });
    }
    
    @Test
    public void testMinIndex() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[0]", list.minIndex());
        });
    }
    
    @Test
    public void testMaxIndex() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[5]", list.maxIndex());
        });
    }
    
    @Test
    public void testMinIndexBy() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[0]", list.minIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMinIndexOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            DoublePredicate     condition = value -> value > 2;
            DoubleUnaryOperator operator  = value -> value;
            assertStrings("OptionalInt[2]", list.minIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testMaxIndexBy() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("OptionalInt[5]", list.maxIndexBy(value -> value));
        });
    }
    
    @Test
    public void testMaxIndexOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            DoublePredicate  condition = value -> value > 2;
            DoubleUnaryOperator operator  = value -> value;
            assertStrings("OptionalInt[5]", list.maxIndexOf(condition, operator));
        });
    }
    
    @Test
    public void testAnyMatch() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue(list.anyMatch(value -> value == One));
        });
    }
    
    @Test
    public void testAllMatch() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertFalse(list.allMatch(value -> value == One));
        });
    }
    
    @Test
    public void testNoneMatch() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertTrue(list.noneMatch(value -> value == Five));
        });
    }
    
    @Test
    public void testFindFirst() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[1.0]", list.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[1.0]", list.findAny());
        });
    }
    
    @Test
    public void testFindLast() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[3.0]", list.findLast());
        });
    }
    
    @Test
    public void testFirstResult() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[1.0]", list.firstResult());
        });
    }
    
    @Test
    public void testLastResult() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("OptionalDouble[3.0]", list.lastResult());
        });
    }
    
    @Test
    public void testJavaList_get() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("1.0", list.get(0));
            assertStrings("2.0", list.get(1));
            assertStrings("3.0", list.get(2));
        });
    }
    
    @Test
    public void testJavaList_indexOf() {
        run(DoubleFuncList.of(One, Two, Three, Two, Three), list -> {
            assertStrings("1",  list.indexOf(Two));
            assertStrings("-1", list.indexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_lastIndexOf() {
        run(DoubleFuncList.of(One, Two, Three, Two, Three), list -> {
            assertStrings("3",  list.lastIndexOf(Two));
            assertStrings("-1", list.lastIndexOf(Five));
        });
    }
    
    @Test
    public void testJavaList_subList() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2.0, 3.0]",           list.subList(1, 3));
            assertStrings("[2.0, 3.0, 4.0, 5.0]", list.subList(1, 10));
        });
    }
    
    //-- IntFuncListWithGroupingBy --
    
    @Test
    public void testGroupingBy() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "{0:[1.0], 1:[2.0, 3.0], 2:[4.0, 5.0]}",
                    list
                    .groupingBy(theDouble.dividedBy(2).asInteger())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_aggregate() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "{0:[1.0], 1:[2.0, 3.0], 2:[4.0, 5.0]}",
                    list
                    .groupingBy(theDouble.dividedBy(2).asInteger(), l -> l)
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_collect() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
//                    "{0:[1.0], 1:[2.0, 3.0], 2:[4.0, 5.0]}"    << Before sum
                    "{0:1.0, 1:5.0, 2:9.0}",
                    list
                    .groupingBy(theDouble.dividedBy(2).asInteger(), () -> new Sum())
                    .sortedByKey(theInteger));
        });
    }
    
    @Test
    public void testGroupingBy_process() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            val sumHalf = new SumHalf();
            assertStrings(
//                  "{0:[1.0], 1:[2.0, 3.0], 2:[4.0, 5.0]}"    << Before half
//                  "{0:[1.0], 1:[5.0],      2:[9.0]}"         << Sum
//                  "{0:[0.5], 1:[2.5],      3:[4.5]}"         << Half
                    "{0:0.5, 1:2.5, 2:4.5}",
                    list
                    .groupingBy(theDouble.dividedBy(2).asInteger(), sumHalf)
                    .sortedByKey(theInteger));
        });
    }
    
    //-- Functional list
    
    @Test
    public void testMapToString() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]",
                    list
                    .mapToString()
                    );
        });
    }
    
    @Test
    public void testMap() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2.0, 4.0, 6.0, 8.0, 10.0]",
                    list
                    .map(theDouble.time(2))
            );
        });
    }
    
    @Test
    public void testMapToInt() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[10.0, 20.0, 30.0]",
                    list
                    .map(theDouble.time(10))
            );
        });
    }
    
    @Test
    public void testMapasDouble() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[1.0, 1.4142135623730951, 1.7320508075688772]", 
                    list.mapToDouble(theDouble.squareRoot()));
        });
    }
    
    @Test
    public void testMapToObj() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[-1.0-, -2.0-, -3.0-, -4.0-, -5.0-]",
                    list
                    .mapToObj(i -> "-" + i + "-")
                    );
        });
    }
    
    //-- FlatMap --
    
    @Test
    public void testFlatMap() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0]",
                    list.flatMap(i -> DoubleFuncList.cycle(i).limit((int)i)));
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]",
                    list.flatMapToInt(i -> IntFuncList.cycle((int)i).limit((int)i)));
        });
    }
    
    @Test
    public void testFlatMapasDouble() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0]",
                    list
                    .flatMapToDouble(i -> DoubleFuncList.cycle(i).limit((int)i)));
        });
    }
    
    //-- Filter --
    
    @Test
    public void testFilter() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[3.0]",
                    list.filter(theDouble.time(2).thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testFilter_mapper() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[3.0]",
                    list.filter(theDouble.time(2), theDouble.thatGreaterThan(4)));
        });
    }
    
    @Test
    public void testPeek() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            assertStrings("[1.0, 2.0, 3.0]", list.peek(i -> logs.add("" + i)));
            assertStrings("[1.0, 2.0, 3.0]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list.limit(3));
        });
    }
    
    @Test
    public void testSkip() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[3.0, 4.0, 5.0]", list.skip(2));
        });
    }
    
    @Test
    public void testDistinct() {
        run(DoubleFuncList.of(One, Two, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list.distinct());
        });
    }
    
    @Test
    public void testSorted() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2.0, 4.0, 6.0, 8.0, 10.0]", list.map(theDouble.time(2)).sorted());
            assertStrings("[10.0, 8.0, 6.0, 4.0, 2.0]", list.map(theDouble.time(2)).sorted((a, b) -> Double.compare(b, a)));
        });
    }
    
    @Test
    public void testBoxed() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    @Test
    public void testToArray() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", Arrays.toString(list.toArray()));
        });
    }
    
    @Test
    public void testNullableOptionalResult() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("Nullable.of([1.0, 2.0, 3.0])",      list.__nullable());
            assertStrings("Optional[[1.0, 2.0, 3.0]]",         list.__optional());
            assertStrings("Result:{ Value: [1.0, 2.0, 3.0] }", list.__result());
        });
    }
    
    @Test
    public void testIndexOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Three), list -> {
            assertStrings("2", list.indexOf(Three));
        });
    }
    
    @Test
    public void testLastIndexOf() {
        run(DoubleFuncList.of(Three, One, Two, Three, Four, Five), list -> {
            assertStrings("3", list.lastIndexOf(Three));
        });
    }
    
    @Test
    public void testIndexesOf() {
        run(DoubleFuncList.of(One, Two, Three, Four, Two), list -> {
            assertStrings("[0, 2]", list.indexesOf(value -> value == One || value == Three));
            assertStrings("[1, 4]", list.indexesOf(Two));
        });
    }
    
    @Test
    public void testToBuilder() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.toBuilder().add(Four).add(Five).build());
        });
    }
    
    
    @Test
    public void testFirst() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalDouble[1.0]", list.first());
            assertStrings("[1.0, 2.0, 3.0]",     list.first(3));
        });
    }
    
    @Test
    public void testLast() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalDouble[5.0]", list.last());
            assertStrings("[3.0, 4.0, 5.0]",     list.last(3));
        });
    }
    
    @Test
    public void testAt() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("OptionalDouble[3.0]",     list.at(2));
            assertStrings("OptionalDouble.empty",  list.at(10));
        });
    }
    
    @Test
    public void testTail() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[2.0, 3.0, 4.0, 5.0]", list.tail());
        });
    }
    @Test
    public void testAppend() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",       list);
            assertStrings("[1.0, 2.0, 3.0, 4.0]", list.append(Four));
            assertStrings("[1.0, 2.0, 3.0]",       list);
        });
    }
    
    @Test
    public void testAppendAll() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",       list);
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.appendAll(Four, Five));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.appendAll(DoubleFuncList.listOf(Four, Five)));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list.appendAll(DoubleFuncList.of(Four, Five)));
            assertStrings("[1.0, 2.0, 3.0]",       list);
        });
    }
//    
//    @Test
//    public void testPrepend() {
//        run(DoubleFuncList.of(One, Two, Three), list -> {
//            assertStrings("[1.0, 2.0, 3.0]",    list);
//            assertStrings("[0, 1, 2, 3]", list.prepend(Zero));
//            assertStrings("[1.0, 2.0, 3.0]",    list);
//        });
//    }
//    
    @Test
    public void testPrependAll() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",            list);
            assertStrings("[-1.0, 0.0, 1.0, 2.0, 3.0]", list.prependAll(MinusOne, Zero));
            assertStrings("[-1.0, 0.0, 1.0, 2.0, 3.0]", list.prependAll(DoubleFuncList.listOf(MinusOne, Zero)));
            assertStrings("[-1.0, 0.0, 1.0, 2.0, 3.0]", list.prependAll(DoubleFuncList.of(MinusOne, Zero)));
            assertStrings("[1.0, 2.0, 3.0]",            list);
        });
    }
    
    @Test
    public void testWith() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",   list);
            assertStrings("[1.0, 0.0, 3.0]",   list.with(1, Zero));
            assertStrings("[1.0, 102.0, 3.0]", list.with(1, value -> value + 100));
            assertStrings("[1.0, 2.0, 3.0]",   list);
        });
    }
    
    @Test
    public void testInsertAt() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",      list);
            assertStrings("[1.0, 0.0, 2.0, 3.0]", list.insertAt(1, Zero));
            assertStrings("[1.0, 2.0, 3.0]",     list);
        });
    }
    
    @Test
    public void testInsertAllAt() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]",           list);
            assertStrings("[1.0, 2.0, 0.0, 0.0, 3.0]", list.insertAt(2, Zero, Zero));
            assertStrings("[1.0, 2.0, 0.0, 0.0, 3.0]", list.insertAllAt(2, DoubleFuncList.listOf(Zero, Zero)));
            assertStrings("[1.0, 2.0, 0.0, 0.0, 3.0]", list.insertAllAt(2, DoubleFuncList.of(Zero, Zero)));
            assertStrings("[1.0, 2.0, 3.0]",           list);
        });
    }
    
    @Test
    public void testExclude() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list);
            assertStrings("[1.0, 3.0, 4.0, 5.0]",      list.exclude(Two));
            assertStrings("[1.0, 3.0, 4.0, 5.0]",      list.exclude(theDouble.eq(Two)));
            assertStrings("[1.0, 3.0, 4.0, 5.0]",      list.excludeAt(1));
            assertStrings("[1.0, 5.0]",                list.excludeFrom(1, 3));
            assertStrings("[1.0, 4.0, 5.0]",           list.excludeBetween(1, 3));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list);
        });
    }
    
    @Test
    public void testReverse() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list);
            assertStrings("[5.0, 4.0, 3.0, 2.0, 1.0]", list.reverse());
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", list);
        });
    }
    
    @Test
    public void testShuffle() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten), list -> {
            assertStrings  ("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", list);
            assertNotEquals("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", list.shuffle().toString());
            assertStrings  ("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]", list);
        });
    }
    
    @Test
    public void testQuery() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0]", list);
            assertStrings("[(2,3.0), (5,6.0)]",             list.query(theDouble.remainderBy(3).thatIsZero()));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 6.0]", list);
        });
    }
    
    //-- AsDoubleFuncListWithConversion --
    
    @Test
    public void testToDoubleArray() {
        run(DoubleFuncList.of('A', 'B', 'C', 'D'), list -> {
            assertStrings("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(list.toDoubleArray(c -> (double)(int)c)));
        });
    }
    
    @Test
    public void testToArrayList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val newList = list.toArrayList();
            assertStrings("[1.0, 2.0, 3.0]", newList);
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    @Test
    public void testToList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val newList = list.toJavaList();
            assertStrings("[1.0, 2.0, 3.0]", newList);
            assertTrue(newList instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val newList = list.toMutableList();
            assertStrings("[1.0, 2.0, 3.0]", newList);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(newList instanceof ArrayList);
        });
    }
    
    //-- join --
    
    @Test
    public void testJoin() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("1.02.03.0", list.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("1.0, 2.0, 3.0", list.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list);
        });
    }
    
    //-- toMap --
    
    @Test
    public void testToMap() {
        run(DoubleFuncList.of(One, Three, Five), list -> {
            assertStrings("{1.0:1.0, 5.0:5.0, 3.0:3.0}", list.toMap(theDouble));
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(DoubleFuncList.of(One, Three, Five), list -> {
            assertStrings("{1.0:1.0, 5.0:25.0, 3.0:9.0}", list.toMap(theDouble, theDouble.square()));
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(DoubleFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1+3+5
            assertStrings("{0.0:2.0, 1.0:9.0}", list.toMap(theDouble.remainderBy(2), theDouble, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(DoubleFuncList.of(One, Two, Three, Five), list -> {
            // 0:2, 1:1*3*5
            assertStrings("{0.0:2.0, 1.0:15.0}", list.toMap(theDouble.remainderBy(2), (a, b) -> a * b));
        });
    }
    
    @Test
    public void testToSet() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val set    = list.toSet();
            assertStrings("[1.0, 2.0, 3.0]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            val logs   = new ArrayList<String>();
            list.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertStrings("[0:1.0, 1:2.0, 2:3.0]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new double[5];
            list.populateArray(array);
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new double[3];
            list.populateArray(array, 2);
            assertStrings("[0.0, 0.0, 1.0]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            val array  = new double[5];
            list.populateArray(array, 1, 3);
            assertStrings("[0.0, 1.0, 2.0, 3.0, 0.0]", Arrays.toString(array));
        });
    }
    
    //-- AsIntFuncListWithMatch --
    
    @Test
    public void testFindFirst_withPredicate() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.findFirst(theDouble.square().thatGreaterThan(theDouble.time(2))));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.findFirst(
                            theDouble.square().thatGreaterThan(theDouble.time(2))));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.findFirst(
                            theDouble.square(), 
                            theDouble.thatGreaterThan(5)));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.findAny(
                            theDouble.square(), 
                            theDouble.thatGreaterThan(5)));
        });
    }
    
    //-- AsFuncListWithStatistic --
    
    @Test
    public void testMinBy() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.minBy(theDouble.minus(3).square()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.maxBy(theDouble.minus(3).square().negate()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings(
                    "OptionalDouble[6.0]", 
                    list.minBy(
                            theDouble.minus(3).square(), 
                            theDouble.descendingOrder()));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "OptionalDouble[3.0]", 
                    list.maxBy(
                            theDouble.minus(3).square(), 
                            theDouble.descendingOrder()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            assertStrings(
                    "(OptionalDouble[3.0],OptionalDouble[6.0])", 
                    list.minMaxBy(theDouble.minus(3).square()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "(OptionalDouble[1.0],OptionalDouble[3.0])", 
                    list.minMaxBy(
                            theDouble.minus(3).square(), 
                            theDouble.descendingOrder()));
        });
    }
    
    //-- IntFuncListWithCalculate --
    
    static class SumHalf implements DoubleCollectorPlus<double[], Double> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                  supplier()                       { return ()       -> new double[] { 0.0 }; }
        @Override public DoubleAccumulator<double[]>         doubleAccumulator()              { return (a, i)   -> { a[0] += i / 2.0; }; }
        @Override public BinaryOperator<double[]>            combiner()                       { return (a1, a2) -> new double[] { a1[0] + a2[0] }; }
        @Override public Function<double[], Double>          finisher()                       { return (a)      -> a[0]; }
        @Override public Set<Characteristics>                characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], Double> collector()                      { return this; }
        @Override public Double                              process(DoubleStreamPlus stream) { return stream.map(i -> i/2.0).sum(); }
    }
    static class Average implements DoubleCollectorPlus<double[], OptionalDouble> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                          supplier()                       { return ()       -> new double[] { 0, 0 }; }
        @Override public DoubleAccumulator<double[]>                 doubleAccumulator()              { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<double[]>                    combiner()                       { return (a1, a2) -> new double[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<double[], OptionalDouble>          finisher()                       { return (a)      -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]); }
        @Override public Set<Characteristics>                        characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], OptionalDouble> collector()                      { return this; }
        @Override public OptionalDouble                              process(DoubleStreamPlus stream) { return stream.average(); }
    }
    static class MinDouble implements DoubleCollectorPlus<double[], OptionalDouble> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                          supplier()                       { return ()       -> new double[] { 0, 0 }; }
        @Override public DoubleAccumulator<double[]>                 doubleAccumulator()              { return (a, i)   -> { a[0] = Math.min(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<double[]>                    combiner()                       { return (a1, a2) -> new double[] { Math.min(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<double[], OptionalDouble>          finisher()                       { return (a)      -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]); }
        @Override public Set<Characteristics>                        characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], OptionalDouble> collector()                      { return this; }
        @Override public OptionalDouble                              process(DoubleStreamPlus stream) { return stream.min(); }
    }
    static class MaxDouble implements DoubleCollectorPlus<double[], OptionalDouble> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                          supplier()                       { return ()       -> new double[] { 0, 0 }; }
        @Override public DoubleAccumulator<double[]>                 doubleAccumulator()              { return (a, i)   -> { a[0] = Math.max(i, a[0]); a[1] = 1; }; }
        @Override public BinaryOperator<double[]>                    combiner()                       { return (a1, a2) -> new double[] { Math.max(a1[0], a2[0]), a1[1] + a2[1] }; }
        @Override public Function<double[], OptionalDouble>          finisher()                       { return (a)      -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]); }
        @Override public Set<Characteristics>                        characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], OptionalDouble> collector()                      { return this; }
        @Override public OptionalDouble                              process(DoubleStreamPlus stream) { return stream.max(); }
    }
    static class SumDouble implements DoubleCollectorPlus<double[], Double> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                  supplier()                       { return ()       -> new double[] { 0 }; }
        @Override public DoubleAccumulator<double[]>         doubleAccumulator()              { return (a, i)   -> { a[0] += i; }; }
        @Override public BinaryOperator<double[]>            combiner()                       { return (a1, a2) -> new double[] { a1[0] + a2[0] }; }
        @Override public Function<double[], Double>          finisher()                       { return (a)      -> a[0]; }
        @Override public Set<Characteristics>                characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], Double> collector()                      { return this; }
        @Override public Double                              process(DoubleStreamPlus stream) { return stream.sum(); }
    }
    static class AvgDouble implements DoubleCollectorPlus<double[], OptionalDouble> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<double[]>                          supplier()                       { return ()       -> new double[] { 0, 0 }; }
        @Override public DoubleAccumulator<double[]>                 doubleAccumulator()              { return (a, i)   -> { a[0] += i; a[1] += 1; }; }
        @Override public BinaryOperator<double[]>                    combiner()                       { return (a1, a2) -> new double[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<double[], OptionalDouble>          finisher()                       { return (a)      -> (a[1] == 0) ? OptionalDouble.empty() : OptionalDouble.of(a[0]/a[1]); }
        @Override public Set<Characteristics>                        characteristics()                { return characteristics; }
        @Override public Collector<Double, double[], OptionalDouble> collector()                      { return this; }
        @Override public OptionalDouble                              process(DoubleStreamPlus stream) { val avg = stream.average(); return avg.isPresent() ? OptionalDouble.of((int)Math.round(avg.getAsDouble())) : OptionalDouble.empty(); }
    }
    
    @Test
    public void testCalculate() {
        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
            val sumHalf = new SumHalf();
            assertStrings("10.0", list.calculate(sumHalf).doubleValue());
        });
    }
//    
//    @Test
//    public void testCalculate2() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf = new SumHalf();
//            val average = new Average();
//            assertStrings("(9,OptionalDouble[20.0])", list.calculate(sumHalf, average));
//        });
//    }
//    
//    @Test
//    public void testCalculate2_combine() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val range = list.calculate(minDouble, maxDouble).mapTo((max, min) -> max.getAsDouble() + min.getAsDouble());
//            assertStrings("11", range);
//        });
//    }
//    
//    @Test
//    public void testCalculate3() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            assertStrings("(9,OptionalDouble[20.0],OptionalInt[0])", list.calculate(sumHalf, average, minDouble));
//        });
//    }
//    
//    @Test
//    public void testCalculate3_combine() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val value     = list
//                            .calculate(sumHalf, average, minDouble)
//                            .mapTo((sumH, avg, min) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min);
//            assertStrings("sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0]", value);
//        });
//    }
//    
//    @Test
//    public void testCalculate4() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            assertStrings(
//                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11])", 
//                    list.calculate(sumHalf, average, minDouble, maxDouble));
//        });
//    }
//    
//    @Test
//    public void testCalculate4_combine() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val value     = list
//                            .calculate(sumHalf, average, minDouble, maxDouble)
//                            .mapTo((sumH, avg, min, max) -> "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max);
//            assertStrings(
//                    "sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11]", 
//                    value);
//        });
//    }
//    
//    @Test
//    public void testCalculate5() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val sumDouble = new SumDouble();
//            assertStrings(
//                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11],20)",
//                    list.calculate(sumHalf, average, minDouble, maxDouble, sumDouble));
//        });
//    }
//    
//    @Test
//    public void testCalculate5_combine() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val sumDouble = new SumDouble();
//            val value     = list
//                            .calculate(sumHalf, average, minDouble, maxDouble, sumDouble)
//                            .mapTo((sumH, avg, min, max, sumI) -> {
//                                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI;
//                            });
//            assertStrings(
//                    "sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11], max: OptionalInt[11], sumI: 20", 
//                    value);
//        });
//    }
//    
//    @Test
//    public void testCalculate6() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val sumDouble = new SumDouble();
//            val avgDouble = new AvgDouble();
//            assertStrings(
//                    "(9,OptionalDouble[20.0],OptionalInt[0],OptionalInt[11],20,OptionalInt[5])", 
//                    list.calculate(sumHalf, average, minDouble, maxDouble, sumDouble, avgDouble));
//        });
//    }
//    
//    @Test
//    public void testCalculate6_combine() {
//        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
//            val sumHalf   = new SumHalf();
//            val average   = new Average();
//            val minDouble = new MinDouble();
//            val maxDouble = new MaxDouble();
//            val sumDouble = new SumDouble();
//            val avgDouble = new AvgDouble();
//            val value     = list
//                            .calculate(sumHalf, average, minDouble, maxDouble, sumDouble, avgDouble)
//                            .mapTo((sumH, avg, min, max, sumI, avgI) -> {
//                                return "sumH: " + sumH + ", avg: " + avg + ", min: " + min + ", max: " + max + ", max: " + max + ", sumI: " + sumI + ", avgI: " + avgI;
//                            });
//            assertStrings("sumH: 9, avg: OptionalDouble[20.0], min: OptionalInt[0], max: OptionalInt[11], max: OptionalInt[11], sumI: 20, avgI: OptionalInt[5]", value);
//        });
//    }
//    
    @Test
    public void testCalculate_of() {
        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
            val sum = new Sum();
            // 2*2 + 3*2 + 4*2 + 11*2
            // 4   + 6   + 8   + 22
            assertStrings("40.0", list.calculate(sum.ofDouble(theDouble.time(2))));
        });
    }
    
    //-- FuncListWithCombine --
    
    @Test
    public void testAppendWith() {
        run(DoubleFuncList.of(One, Two), DoubleFuncList.of(Three, Four), (list1, list2) -> {
            assertStrings(
                        "[1.0, 2.0, 3.0, 4.0]",
                        list1.appendWith(list2)
                    );
        });
    }
    
    @Test
    public void testParependWith() {
        run(DoubleFuncList.of(One, Two), DoubleFuncList.of(Three, Four), (list1, list2) -> {
            assertStrings(
                        "[1.0, 2.0, 3.0, 4.0]",
                        list2.prependWith(list1)
                    );
        });
    }
        
    @Test
    public void testMerge() {
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (list1, streamabl2) -> {
            assertStrings(
                "100.0, 0.0, 200.0, 1.0, 300.0, 2.0, 3.0, 4.0, 5.0, 6.0",
                list1
                    .mergeWith(streamabl2)
                    .limit    (10)
                    .join     (", "));
        });
    }
    
    @Test
    public void testZipWith() {
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        "(100.0,0.0), (200.0,1.0), (300.0,2.0)",
                        listA.zipWith(listB).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "(100.0,0.0), (200.0,1.0), (300.0,2.0), (-1.0,3.0), (-1.0,4.0), (-1.0,5.0), (-1.0,6.0), (-1.0,7.0), (-1.0,8.0), (-1.0,9.0)",
                        listA.zipWith(listB, -1).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300, 400, 500),
            DoubleFuncList.infinite().limit(3),
                (listA, listB) -> {
                    assertStrings(
                            // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                            //   0   1    2  3  4  5  6  7  8  9
                            "(100.0,0.0), (200.0,1.0), (300.0,2.0), (400.0,-1.0), (500.0,-1.0)",
                            listA.zipWith(-100, listB, -1).join(", "));
                });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300
                        //   0   1    2
                        "100.0, 201.0, 302.0",
                        listA.zipWith(listB, (iA, iB) -> iA + iB).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                       // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                       //   0   1    2  3  4  5  6  7  8  9
                        "100.0, 201.0, 302.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0",
                        listA.zipWith(listB, -1, (iA, iB) -> iA + iB).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300, 400, 500),
            DoubleFuncList.infinite().limit(3),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "10000.0, 20001.0, 30002.0, 39999.0, 49999.0",
                        listA.zipWith(-100, listB, -1, (a, b) -> a*100 + b).join(", "));
            });
    }
    
    @Test
    public void testZipWith_object() {
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        "(100.0,0.0), (200.0,1.0), (300.0,2.0)",
                        listA.zipWith(listB.boxed()).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        "(100.0,0.0), (200.0,1.0), (300.0,2.0), (-1.0,3.0), (-1.0,4.0), (-1.0,5.0), (-1.0,6.0), (-1.0,7.0), (-1.0,8.0), (-1.0,9.0)",
                        listA.zipWith(-1, listB.boxed()).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        "100.0->0.0, 200.0->1.0, 300.0->2.0",
                        listA.zipWith(listB.boxed(), (a, b) -> a + "->" + b).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300, 400, 500),
            DoubleFuncList.infinite().limit(3),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300 -1 -1 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7  8  9
                        "10000.0, 20001.0, 30002.0, 39999.0, 49999.0",
                        listA.zipWith(-100, listB, -1, (a, b) -> a*100 + b).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300
                        //   0   1    2
                        "100.0<->0.0, 200.0<->1.0, 300.0<->2.0",
                        listA.zipToObjWith(listB, (iA, iB) -> iA + "<->" + iB).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300
                        //   0   1    2
                        "100.0<->0.0, 200.0<->1.0, 300.0<->2.0, -100.0<->3.0, -100.0<->4.0, -100.0<->5.0, -100.0<->6.0, -100.0<->7.0, -100.0<->8.0, -100.0<->9.0",
                        listA.zipToObjWith(-100, listB, -1, (iA, iB) -> iA + "<->" + iB).join(", "));
            });
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                assertStrings(
                        // 100 200  300
                        //   0   1    2
                        "100.0<->0.0, 200.0<->1.0, 300.0<->2.0, -100.0<->3.0, -100.0<->4.0, -100.0<->5.0, -100.0<->6.0, -100.0<->7.0, -100.0<->8.0, -100.0<->9.0",
                        listA.zipToObjWith(-100, listB.boxed(), (iA, iB) -> iA + "<->" + iB).join(", "));
            });
    }
    
    @Test
    public void testChoose() {
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                val bool = new AtomicBoolean(true);
                assertStrings(
                        "100.0, 1.0, 300.0, 3.0, 4.0", 
                        listA.choose(listB, (a, b) -> {
                    // This logic which to choose from one then another
                    boolean curValue = bool.get();
                    return bool.getAndSet(!curValue);
                }).limit(5).join(", "));
            });
    }
    
    @Test
    public void testChoose_AllowUnpaired() {
        run(DoubleFuncList.of(100, 200, 300),
            DoubleFuncList.infinite().limit(10),
            (listA, listB) -> {
                val bool    = new AtomicBoolean(true);
                assertStrings(
                        // 100 200  300 -1 -1 -1 -1 -1
                        //   0   1    2  3  4  5  6  7
                        "100.0, 1.0, 300.0, 3.0, 4.0, 5.0, 6.0", 
                        listA.choose(listB, AllowUnpaired, (a, b) -> {
                            // This logic which to choose from one then another
                            boolean curValue = bool.get();
                            return bool.getAndSet(!curValue);
                        }).limit(7).join(", "));
            });
    }
    
    //-- DoubleStreamPlusWithFilter --
    
    @Test
    public void testFilter_withMappter() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filter(
                            theDouble.square(), 
                            theDouble.asInteger().thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsInt() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filterAsInt(
                            theDouble.square().asInteger(),
                            theInteger.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsLong() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filterAsLong(
                            theDouble.square().asLong(), 
                            theLong.thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsDouble() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filterAsDouble(
                            theDouble.square().asDouble(), 
                            theDouble.asInteger().thatIsOdd()));
        });
    }
    
    @Test
    public void testFilterAsObject() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filterAsObject(
                            d -> "" + d, 
                            s -> (Double.parseDouble(s) % 2) == 1));
        });
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            DoubleFunction<Integer> mapper  = d -> (int)d;
            Predicate<Integer>      checker = i -> (i % 2) == 1;
            assertStrings(
                    "[1.0, 3.0, 5.0]", 
                    list.filterAsObject(mapper, checker));
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings("[4.0]", list.filterWithIndex((index, value) -> (index > 2) && (value < 5)));
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
        run(DoubleFuncList.wholeNumbers(cars.length), list -> {
            assertStrings(
                    "[0.0, 1.0, 3.0, 4.0]",
                    list.filterNonNull(i -> cars[(int)i]));
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
        run(DoubleFuncList.wholeNumbers(cars.length), list -> {
            assertStrings(
                    "[0.0, 1.0, 3.0, 4.0]",
                    list.excludeNull(i -> cars[(int)i]));
        });
    }
    
    @Test
    public void testFilterIn_array() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[2.0, 5.0]",
                    list.filterIn(Two, Five));
        });
    }
    
    @Test
    public void testExcludeIn_array() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 4.0]",
                    list.excludeIn(Two, Five));
        });
    }
    
    @Test
    public void testFilterIn_funcList() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[2.0, 5.0]",
                    list.filterIn(DoubleFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_funcList() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 4.0]",
                    list.excludeIn(DoubleFuncList.of(Two, Five)));
        });
    }
    
    @Test
    public void testFilterIn_collection() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[2.0, 5.0]",
                    list.filterIn(Arrays.asList(Two, Five)));
        });
    }
    
    @Test
    public void testExcludeIn_collection() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0, 3.0, 4.0]",
                    list.excludeIn(Arrays.asList(Two, Five)));
        });
    }
    
    //-- FuncListWithFlatMap --
    
    @Test
    public void testFlatMapOnly() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[1.0, 2.0, 3.0, 3.0, 3.0]", 
                    list.flatMapOnly(
                            theDouble.roundToInt().thatIsOdd(), 
                            i -> DoubleFuncList.cycle(i).limit((int)i)));
        });
    }
    
    @Test
    public void testFlatMapIf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings(
                    "[1.0, -2.0, -2.0, 3.0, 3.0, 3.0]", 
                    list.flatMapIf(
                            theDouble.roundToInt().thatIsOdd(),
                            i -> DoubleFuncList.cycle(i).limit((int)i),
                            i -> DoubleFuncList.cycle(-i).limit((int)i)));
        });
    }
    
    //-- FuncListWithLimit --
    
    @Test
    public void testSkipLimitLong() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[2.0]", list.skip((Long)1L).limit((Long)1L));
        });
    }
    
    @Test
    public void testSkipLimitLongNull() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list.skip(null).limit(null));
        });
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 3.0]", list.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)));
        });
    }
    
    @Test
    public void testSkipWhile() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertStrings("[3.0, 4.0, 5.0, 4.0, 3.0, 2.0, 1.0]",           list.skipWhile(i -> i < 3));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0, 3.0, 2.0, 1.0]", list.skipWhile(i -> i > 3));
            assertStrings("[5.0, 4.0, 3.0, 2.0, 1.0]",                     list.skipWhile((p, e) -> p == e + 1));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0, 3.0, 2.0, 1.0]", list.skipWhile((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testSkipUntil() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertStrings("[4.0, 5.0, 4.0, 3.0, 2.0, 1.0]",                list.skipUntil(i -> i > 3));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0, 3.0, 2.0, 1.0]", list.skipUntil(i -> i < 3));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0, 3.0, 2.0, 1.0]", list.skipUntil((p, e) -> p == e + 1));
            assertStrings("[5.0, 4.0, 3.0, 2.0, 1.0]",                     list.skipUntil((p, e) -> p == e - 1));
        });
    }
    
    @Test
    public void testTakeWhile() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Double>();
            assertStrings("[1.0, 2.0, 3.0]",    list.peek(logs::add).takeWhile(i -> i < 4));
            assertStrings("[1.0, 2.0, 3.0, 4.0]", logs);
            //                       ^--- Because it needs 4 to do the check in `takeWhile`
            
            logs.clear();
            assertStrings("[]", list.peek(logs::add).takeWhile(i -> i > 4));
            assertStrings("[1.0]", logs);
            //              ^--- Because it needs 1 to do the check in `takeWhile`
        });
    }
    
    @Test
    public void testTakeWhile_previous() {
        run(DoubleFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0]", list.takeWhile((a, b) -> b == a + 1));
        });
    }
    
    @Test
    public void testTakeUtil() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Double>();
            assertStrings("[1.0, 2.0, 3.0, 4.0]", list.peek(logs::add).takeUntil(i -> i > 4));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0]", logs);
            //                          ^--- Because it needs 5 to do the check in `takeUntil`
            
            logs.clear();
            assertStrings("[]",  list.peek(logs::add).takeUntil(i -> i < 4));
            assertStrings("[1.0]", logs);
            //              ^--- Because it needs 1 to do the check in `takeUntil`
        });
    }
    
    @Test
    public void testTakeUntil_previous() {
        run(DoubleFuncList.of(1, 2, 3, 4, 6, 4, 3, 2, 1), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0]", list.takeUntil((a, b) -> b > a + 1));
        });
    }
    
    @Test
    public void testDropAfter() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0]", list.dropAfter(i -> i == 4));
            //                       ^--- Include 4
        });
    }
    
    @Test
    public void testDropAfter_previous() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0]", list.dropAfter((a, b) -> b < a));
            //                                       ^--- Include 4
        });
    }
    
    @Test
    public void testSkipTake() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5, 4, 3, 2, 1), list -> {
            val logs = new ArrayList<Double>();
            assertStrings("[3.0, 4.0, 5.0, 4.0, 3.0]", list.peek(logs::add).skipWhile(i -> i < 3).takeUntil(i -> i < 3));
            assertStrings("[1.0, 2.0, 3.0, 4.0, 5.0, 4.0, 3.0, 2.0]", logs);
            //              ^----^-----------------------------^--- Because it needs these number to do the check in `skipWhile` and `takeWhile`
        });
    }
    
    //-- FuncListWithMap --
    
    @Test
    public void testMapOnly() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 2.0, 9.0]",
                    list
                    .mapOnly(
                            theDouble.asInteger().thatIsOdd(),
                            theDouble.square())
                    );
        });
    }
    
    @Test
    public void testMapIf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 1.414, 9.0]",
                    list
                    .mapIf(
                            theDouble.asInteger().thatIsOdd(),
                            theDouble.square(),
                            theDouble.squareRoot().time(1000).round().dividedBy(1000)
                    ));
        });
    }
    
    @Test
    public void testMapToObjIf() {
        run(DoubleFuncList.of(One, Two, Three), list -> {
            assertStrings("[1.0, 1, 9.0]",
                    list
                    .mapToObjIf(
                            theDouble.asInteger().thatIsOdd(),
                            theDouble.square().asString(),
                            theDouble.squareRoot().round().asInteger().asString()
                    ));
        });
    }
    
    //== Map First ==
    
    @Test
    public void testMapFirst_2() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertStrings(
                    "[1.0, 2.0, Three, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0]",
                    list
                    .mapFirst(
                            i -> i == 3.0 ? "Three" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_3() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertStrings("[1.0, 2.0, Three, 4.0, 5.0, 6.0, Seven, 8.0, 9.0, 10.0, 11.0, 12.0]",
                    list
                    .mapFirst(
                            i -> i == 3.0 ? "Three" : null,
                            i -> i == 7.0 ? "Seven" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_4() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertStrings("[1.0, 2.0, Three, 4.0, 5.0, 6.0, Seven, 8.0, 9.0, 10.0, Eleven, 12.0]",
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
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertStrings("[One, 2.0, Three, 4.0, 5.0, 6.0, Seven, 8.0, 9.0, 10.0, Eleven, 12.0]",
                    list
                    .mapFirst(
                            i -> i ==  3.0 ? "Three" : null,
                            i -> i ==  7.0 ? "Seven" : null,
                            i -> i == 11.0 ? "Eleven" : null,
                            i -> i ==  1.0 ? "One" : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    @Test
    public void testMapFirst_6() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve), list -> {
            assertStrings("[One, 2.0, Three, 4.0, Five, 6.0, Seven, 8.0, 9.0, 10.0, Eleven, 12.0]",
                    list
                    .mapFirst(
                            i -> i ==  3.0 ? "Three"  : null,
                            i -> i ==  7.0 ? "Seven"  : null,
                            i -> i == 11.0 ? "Eleven" : null,
                            i -> i ==  1.0 ? "One"    : null,
                            i -> i ==  5.0 ? "Five"   : null,
                            i -> "" + i
                    )
            );
        });
    }
    
    //== MapThen ==
    
    @Test
    public void testMapThen_2() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
        assertStrings(
                "[1.0-2.0, 2.0-3.0, 3.0-4.0, 4.0-5.0, 5.0-6.0]",
                list
                .mapThen(
                        theDouble,
                        theDouble.plus(1),
                        (a, b) -> a + "-" + b)
                );
        });
    }
    
    @Test
    public void testMapThen_3() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
        assertStrings(
                "[1.0-2.0-3.0, 2.0-3.0-6.0, 3.0-4.0-9.0, 4.0-5.0-12.0, 5.0-6.0-15.0]",
                list
                .mapThen(
                        theDouble,
                        theDouble.plus(1),
                        theDouble.time(3),
                        (a, b, c) -> a + "-" + b + "-" + c)
                );
        });
    }
    
    @Test
    public void testMapThen_4() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0-2.0-3.0-1.0, 2.0-3.0-6.0-4.0, 3.0-4.0-9.0-9.0, 4.0-5.0-12.0-16.0, 5.0-6.0-15.0-25.0]",
                    list
                        .mapThen(
                                theDouble,
                                theDouble.plus(1),
                                theDouble.time(3),
                                theDouble.square(),
                                (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d)
                        );
        });
    }
    
    @Test
    public void testMapThen_5() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0-2.0-3.0-1.0-1.0, 2.0-3.0-6.0-4.0-2.0, 3.0-4.0-9.0-9.0-6.0, 4.0-5.0-12.0-16.0-24.0, 5.0-6.0-15.0-25.0-120.0]",
                    list
                        .mapThen(
                                theDouble,
                                theDouble.plus(1),
                                theDouble.time(3),
                                theDouble.square(),
                                theDouble.asInteger().factorial().asDouble(),
                                (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e)
                        );
        });
    }
    
    @Test
    public void testMapThen_6() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five), list -> {
            assertStrings(
                    "[1.0-2.0-3.0-1.0-1.0--1.0, 2.0-3.0-6.0-4.0-2.0--2.0, 3.0-4.0-9.0-9.0-6.0--3.0, 4.0-5.0-12.0-16.0-24.0--4.0, 5.0-6.0-15.0-25.0-120.0--5.0]",
                    list
                        .mapThen(
                            theDouble,
                            theDouble.plus(1),
                            theDouble.time(3),
                            theDouble.square(),
                            theDouble.asInteger().factorial().asDouble(),
                            theDouble.negate(),
                            (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f)
                        );
        });
    }
    
    //-- FuncListWithMapGroup --
    
//    @Test
//    public void testMapGroup_specific() {
//        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
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
    @Test
    public void testMapGroup_count() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six), list -> {
            ToDoubleFunction<DoubleStreamPlus> joiner = doubleStream -> Double.parseDouble(doubleStream.mapToInt().mapToString().join());
            assertStrings(
                    "[12.0, 23.0, 34.0, 45.0, 56.0]",
                    list.mapGroup(2, joiner));
            assertStrings(
                    "[123.0, 234.0, 345.0, 456.0]",
                    list.mapGroup(3, joiner));
            assertStrings(
                    "[1234.0, 2345.0, 3456.0]",
                    list.mapGroup(4, joiner));
            assertStrings(
                    "[12345.0, 23456.0]",
                    list.mapGroup(5, joiner));
            assertStrings(
                    "[123456.0]",
                    list.mapGroup(6, joiner));
        });
    }
    
    @Test
    public void testMapGroup() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertStrings(
                    "[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]",
                    list.mapTwo((a, b) -> a*10 + b));
        });
    }
    
    @Test
    public void testMapGroupToInt() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertStrings(
                    "[12, 23, 34, 45, 56, 67, 78]",
                    list.mapTwoToInt((a, b) -> (int)(a*10 + b)));
            assertStrings(
                    "[12, 23, 34, 45, 56, 67, 78]",
                    list.mapGroupToInt(2, doubles -> Integer.parseInt(doubles.mapToInt().mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupasDouble() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertStrings(
                    "[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]",
                    list.mapTwoToDouble((a, b) -> a*10 + b));
            assertStrings(
                    "[12.0, 23.0, 34.0, 45.0, 56.0, 67.0, 78.0]",
                    list.mapGroupToDouble(2, ints -> Integer.parseInt(ints.mapToInt().mapToString().join())));
        });
    }
    
    @Test
    public void testMapGroupToObj() {
        run(DoubleFuncList.of(One, Two, Three, Four, Five, Six, Seven, Eight), list -> {
            assertStrings(
                    "[(1.0,2.0), (2.0,3.0), (3.0,4.0), (4.0,5.0), (5.0,6.0), (6.0,7.0), (7.0,8.0)]",
                    list.mapTwoToObj());
            assertStrings(
                    "[1.0-2.0, 2.0-3.0, 3.0-4.0, 4.0-5.0, 5.0-6.0, 6.0-7.0, 7.0-8.0]",
                    list.mapGroupToObj((a,b) -> a + "-" + b));
            assertStrings(
                    "[[1.0, 2.0, 3.0], [2.0, 3.0, 4.0], [3.0, 4.0, 5.0], [4.0, 5.0, 6.0], [5.0, 6.0, 7.0], [6.0, 7.0, 8.0]]",
                    list.mapGroupToObj(3).map(DoubleStreamPlus::toListString));
            assertStrings(
                    "[123, 234, 345, 456, 567, 678]",
                    list.mapGroupToObj(3, doubles -> Integer.parseInt(doubles.mapToInt().mapToString().join())));
        });
    }
    
    //-- FuncListWithMapToMap --
    
    @Test
    public void testMapToMap_1() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0}, "
                    + "{<1>:3.0}, "
                    + "{<1>:5.0}, "
                    + "{<1>:7.0}, "
                    + "{<1>:11.0}, "
                    + "{<1>:13.0}, "
                    + "{<1>:17.0}"
                    + "]",
                    list
                        .mapToMap(
                                "<1>", theDouble)
                        .map(map -> map.sorted())
                        );
        });
    }
    
    @Test
    public void testMapToMap_2() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0}, "
                    + "{<1>:3.0, <2>:-3.0}, "
                    + "{<1>:5.0, <2>:-5.0}, "
                    + "{<1>:7.0, <2>:-7.0}, "
                    + "{<1>:11.0, <2>:-11.0}, "
                    + "{<1>:13.0, <2>:-13.0}, "
                    + "{<1>:17.0, <2>:-17.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_3() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_4() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_5() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_6() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0, <6>:1.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0, <6>:81.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0, <6>:625.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0, <6>:2401.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0, <6>:14641.0}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0, <6>:28561.0}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0, <6>:83521.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3),
                            "<6>", theDouble.pow(4))
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_7() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0, <6>:1.0, <7>:1.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0, <6>:81.0, <7>:9.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0, <6>:625.0, <7>:25.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0, <6>:2401.0, <7>:49.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0, <6>:14641.0, <7>:121.0}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0, <6>:28561.0, <7>:169.0}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0, <6>:83521.0, <7>:289.0}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3),
                            "<6>", theDouble.pow(4),
                            "<7>", theDouble.square())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_8() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0, <6>:1.0, <7>:1.0, <8>:1.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0, <6>:81.0, <7>:9.0, <8>:1.7320508075688772}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0, <6>:625.0, <7>:25.0, <8>:2.23606797749979}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0, <6>:2401.0, <7>:49.0, <8>:2.6457513110645907}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0, <6>:14641.0, <7>:121.0, <8>:3.3166247903554}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0, <6>:28561.0, <7>:169.0, <8>:3.605551275463989}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0, <6>:83521.0, <7>:289.0, <8>:4.123105625617661}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3),
                            "<6>", theDouble.pow(4),
                            "<7>", theDouble.square(),
                            "<8>", theDouble.squareRoot())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_9() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen), list -> {
            assertStrings(
                    "["
                    + "{<1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0, <6>:1.0, <7>:1.0, <8>:1.0, <9>:1.0}, "
                    + "{<1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0, <6>:81.0, <7>:9.0, <8>:1.7320508075688772, <9>:6.0}, "
                    + "{<1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0, <6>:625.0, <7>:25.0, <8>:2.23606797749979, <9>:120.0}, "
                    + "{<1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0, <6>:2401.0, <7>:49.0, <8>:2.6457513110645907, <9>:5040.0}, "
                    + "{<1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0, <6>:14641.0, <7>:121.0, <8>:3.3166247903554, <9>:3.99168E7}, "
                    + "{<1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0, <6>:28561.0, <7>:169.0, <8>:3.605551275463989, <9>:1.932053504E9}, "
                    + "{<1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0, <6>:83521.0, <7>:289.0, <8>:4.123105625617661, <9>:-2.8852224E8}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3),
                            "<6>", theDouble.pow(4),
                            "<7>", theDouble.square(),
                            "<8>", theDouble.squareRoot(),
                            "<9>", theDouble.asInteger().factorial().asDouble())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    @Test
    public void testMapToMap_10() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven, Thirteen, Seventeen, Nineteen, TwentyThree), list -> {
            assertStrings(
                    "["
                    + "{<10>:0, <1>:1.0, <2>:-1.0, <3>:2.0, <4>:-1.0, <5>:3.0, <6>:1.0, <7>:1.0, <8>:1.0, <9>:1.0}, "
                    + "{<10>:1, <1>:3.0, <2>:-3.0, <3>:4.0, <4>:1.0, <5>:9.0, <6>:81.0, <7>:9.0, <8>:1.7320508075688772, <9>:6.0}, "
                    + "{<10>:2, <1>:5.0, <2>:-5.0, <3>:6.0, <4>:3.0, <5>:15.0, <6>:625.0, <7>:25.0, <8>:2.23606797749979, <9>:120.0}, "
                    + "{<10>:3, <1>:7.0, <2>:-7.0, <3>:8.0, <4>:5.0, <5>:21.0, <6>:2401.0, <7>:49.0, <8>:2.6457513110645907, <9>:5040.0}, "
                    + "{<10>:5, <1>:11.0, <2>:-11.0, <3>:12.0, <4>:9.0, <5>:33.0, <6>:14641.0, <7>:121.0, <8>:3.3166247903554, <9>:3.99168E7}, "
                    + "{<10>:6, <1>:13.0, <2>:-13.0, <3>:14.0, <4>:11.0, <5>:39.0, <6>:28561.0, <7>:169.0, <8>:3.605551275463989, <9>:1.932053504E9}, "
                    + "{<10>:8, <1>:17.0, <2>:-17.0, <3>:18.0, <4>:15.0, <5>:51.0, <6>:83521.0, <7>:289.0, <8>:4.123105625617661, <9>:-2.8852224E8}, "
                    + "{<10>:9, <1>:19.0, <2>:-19.0, <3>:20.0, <4>:17.0, <5>:57.0, <6>:130321.0, <7>:361.0, <8>:4.358898943540674, <9>:1.09641728E8}, "
                    + "{<10>:11, <1>:23.0, <2>:-23.0, <3>:24.0, <4>:21.0, <5>:69.0, <6>:279841.0, <7>:529.0, <8>:4.795831523312719, <9>:8.6245376E8}"
                    + "]",
                    list
                    .mapToMap(
                            "<1>", theDouble,
                            "<2>", theDouble.negate(),
                            "<3>", theDouble.plus(1),
                            "<4>", theDouble.minus(2),
                            "<5>", theDouble.time(3),
                            "<6>", theDouble.pow(4),
                            "<7>", theDouble.square(),
                            "<8>", theDouble.squareRoot(),
                            "<9>", theDouble.asInteger().factorial().asDouble(),
                            "<10>", theDouble.dividedBy(2).asInteger())
                    .map(map -> map.sorted())
                    );
        });
    }
    
    //-- FuncListWithMapToTuple --
    
    @Test
    public void testMapToTuple_2() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "["
                    + "(1.0,2.0), "
                    + "(3.0,4.0), "
                    + "(5.0,6.0), "
                    + "(7.0,8.0), "
                    + "(11.0,12.0)"
                    + "]",
                    list
                        .mapToTuple(
                                theDouble,
                                theDouble.plus(1))
                        );
        });
    }
    
    @Test
    public void testMapToTuple_3() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "["
                    + "(1.0,2.0,3.0), "
                    + "(3.0,4.0,9.0), "
                    + "(5.0,6.0,15.0), "
                    + "(7.0,8.0,21.0), "
                    + "(11.0,12.0,33.0)"
                    + "]",
                    list
                    .mapToTuple(
                            theDouble,
                            theDouble.plus(1),
                            theDouble.time(3))
                    );
        });
    }
    
    @Test
    public void testMapToTuple_4() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "["
                    + "(1.0,2.0,3.0,1.0), "
                    + "(3.0,4.0,9.0,9.0), "
                    + "(5.0,6.0,15.0,25.0), "
                    + "(7.0,8.0,21.0,49.0), "
                    + "(11.0,12.0,33.0,121.0)"
                    + "]",
                    list
                    .mapToTuple(
                            theDouble,
                            theDouble.plus(1),
                            theDouble.time(3),
                            theDouble.square())
                    );
        });
    }
    
    @Test
    public void testMapToTuple_5() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "["
                    + "(1.0,2.0,3.0,1.0,1), "
                    + "(3.0,4.0,9.0,9.0,6), "
                    + "(5.0,6.0,15.0,25.0,120), "
                    + "(7.0,8.0,21.0,49.0,5040), "
                    + "(11.0,12.0,33.0,121.0,39916800)"
                    + "]",
                    list
                    .mapToTuple(
                            theDouble,
                            theDouble.plus(1),
                            theDouble.time(3),
                            theDouble.square(),
                            theDouble.asInteger().factorial())
                    );
        });
    }
    
    @Test
    public void testMapToTuple_6() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "["
                    + "(1.0,2.0,3.0,1.0,1,-1.0), "
                    + "(3.0,4.0,9.0,9.0,6,-3.0), "
                    + "(5.0,6.0,15.0,25.0,120,-5.0), "
                    + "(7.0,8.0,21.0,49.0,5040,-7.0), "
                    + "(11.0,12.0,33.0,121.0,39916800,-11.0)"
                    + "]",
                    list
                    .mapToTuple(
                            theDouble,
                            theDouble.plus(1),
                            theDouble.time(3),
                            theDouble.square(),
                            theDouble.asInteger().factorial(),
                            theDouble.negate())
                    );
        });
    }
    
    //-- StreamPlusWithMapWithIndex --
    
    @Test
    public void testMapWithIndex() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "[(0,1.0), (1,3.0), (2,5.0), (3,7.0), (4,11.0)]",
                    list
                    .mapWithIndex()
                    );
        });
    }
    
    @Test
    public void testMapWithIndex_combine() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "[0.0, 3.0, 10.0, 21.0, 44.0]",
                    list
                    .mapWithIndex((i, each) -> i * each)
                    );
        });
    }
    
    @Test
    public void testMapToObjWithIndex_combine() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "[0: 1.0, 1: 3.0, 2: 5.0, 3: 7.0, 4: 11.0]",
                    list
                    .mapToObjWithIndex((i, each) -> i + ": " + each)
                    );
            assertStrings(
                    "[0: 2.0, 1: 6.0, 2: 10.0, 3: 14.0, 4: 22.0]",
                    list
                    .mapWithIndex(i -> i*2, (i, each) -> i + ": " + each)
                    );
            assertStrings(
                    "[0: 2.0, 1: 6.0, 2: 10.0, 3: 14.0, 4: 22.0]",
                    list
                    .mapWithIndex(i -> i*2, (i, each) -> i + ": " + each)
                    );
            assertStrings(
                    "[0.0: 2.0, 1.0: 6.0, 2.0: 10.0, 3.0: 14.0, 4.0: 22.0]",
                    list
                    .mapToObjWithIndex(i -> "" + i*2, (i, each) -> i + ": " + each)
                    );
        });
    }
    
    //-- FuncListWithModify --
    
    @Test
    public void testAccumulate() {
        run(DoubleFuncList.of(1, 2, 3, 4, 5), list -> {
            assertStrings(
                    "[1.0, 3.0, 6.0, 10.0, 15.0]",
                    list.accumulate((prev, current) -> prev + current));
            
            assertStrings(
                    "[1.0, 12.0, 123.0, 1234.0, 12345.0]",
                    list.accumulate((prev, current)->prev*10 + current));
        });
    }
    
    @Test
    public void testRestate() {
        run(DoubleFuncList.wholeNumbers(20).map(i -> i % 5).toFuncList(), list -> {
            assertStrings("[0.0, 1.0, 2.0, 3.0, 4.0]", list.restate((head, tail) -> tail.filter(x -> x != head)));
        });
    }
    
    @Test
    public void testRestate_sieveOfEratosthenes() {
        run(DoubleFuncList.naturalNumbers(300).filter(theDouble.thatIsNotOne()).toFuncList(), list -> {
            assertStrings(
                    "[2.0, 3.0, 5.0, 7.0, 11.0, 13.0, 17.0, 19.0, 23.0, 29.0, 31.0, 37.0, 41.0, 43.0, 47.0, 53.0, 59.0, 61.0, 67.0, "
                    + "71.0, 73.0, 79.0, 83.0, 89.0, 97.0, 101.0, 103.0, 107.0, 109.0, 113.0, 127.0, 131.0, 137.0, 139.0, 149.0, "
                    + "151.0, 157.0, 163.0, 167.0, 173.0, 179.0, 181.0, 191.0, 193.0, 197.0, 199.0, 211.0, 223.0, 227.0, 229.0, 233.0, "
                    + "239.0, 241.0, 251.0, 257.0, 263.0, 269.0, 271.0, 277.0, 281.0, 283.0, 293.0]",
                  list.restate((head, tail) -> tail.filter(x -> x % head != 0)));
        });
    }
    
    @Test
    public void testSpawn() {
        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first  = new AtomicLong(-1);
            val logs   = new ArrayList<String>();
            list
            .spawn(d -> TimeFuncs.Sleep((long)(d*timePrecision + 5)).thenReturn((int)d).defer())
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
        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
            val timePrecision = 100;
            val first  = new AtomicLong(-1);
            val logs   = new ArrayList<String>();
            list
            .spawn(i -> DeferAction.from(() -> {
                Thread.sleep((long)(i*timePrecision + 5));
                return (int)i;
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
        run(DoubleFuncList.of(Two, Three, Four, Eleven), list -> {
            val first   = new AtomicLong(-1);
            val actions = new ArrayList<DeferAction<Integer>>();
            val logs    = new ArrayList<String>();
            list
            .spawn(i -> {
                DeferAction<Integer> action = Sleep(((int)i)*50 + 5).thenReturn((int)i).defer();
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
        run(DoubleFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekAs(e -> "<" + e + ">", e -> elementStrings.add(e))
                .join() // To terminate the stream
                ;
            assertStrings("[<0.0>, <1.0>, <2.0>, <3.0>, <4.0>, <5.0>]", elementStrings);
        });
    }
    
    @Test
    public void testPeekBy_map() {
        run(DoubleFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekBy(s -> !("" + s).contains("2"), e -> elementStrings.add("" + e))
                .join() // To terminate the stream
                ;
            assertStrings("[0.0, 1.0, 3.0, 4.0, 5.0]", elementStrings);
            
            elementStrings.clear();
            list
                .peekBy(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add("" + e))
                .join() // To terminate the stream
                ;
            assertStrings("[0.0, 1.0, 3.0, 4.0, 5.0]", elementStrings);
        });
    }
    
    @Test
    public void testPeekAs_map() {
        run(DoubleFuncList.of(0, One, 2, Three, 4, Five), list -> {
            val elementStrings = new ArrayList<String>();
            list
                .peekAs(e -> "<" + e + ">", s -> !s.contains("2"), e -> elementStrings.add((String)e))
                .join() // To terminate the stream
                ;
            assertStrings("[<0.0>, <1.0>, <3.0>, <4.0>, <5.0>]", elementStrings);
        });
    }
//    
    //-- FuncListWithPipe --
    
    @Test
    public void testPipeable() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0, 7.0, 11.0]",
                    list
                        .pipable()
                        .pipeTo(DoubleFuncList::toListString));
        });
    }
    
    @Test
    public void testPipe() {
        run(DoubleFuncList.of(One, Three, Five, Seven, Eleven), list -> {
            assertStrings(
                    "[1.0, 3.0, 5.0, 7.0, 11.0]",
                    list.pipe(DoubleFuncList::toListString));
        });
    }
    
    
    //-- FuncListWithReshape --
    
    @Test
    public void testSegment() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0], "
                    + "[6.0, 7.0, 8.0, 9.0, 10.0, 11.0], "
                    + "[12.0, 13.0, 14.0, 15.0, 16.0, 17.0], "
                    + "[18.0, 19.0]"
                    + "]",
                    list
                    .segment(6)
                    .mapToObj(DoubleFuncList::toString));
        });
    }
    
    @Test
    public void testSegment_sizeFunction() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                      "[" 
                    + "[1.0], "
                    + "[2.0, 3.0], "
                    + "[4.0, 5.0, 6.0, 7.0], "
                    + "[8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0], "
                    + "[16.0, 17.0, 18.0, 19.0]"
                    + "]",
                    list
                    .segment(i -> (int)i));
        });
        // Empty
        run(DoubleFuncList.wholeNumbers(0), list -> {
            assertStrings(
                      "[]",
                    list
                    .segment(i -> (int)i));
        });
        // End at exact boundary
        run(DoubleFuncList.wholeNumbers(8), list -> {
            assertStrings(
                      "[" 
                    + "[1.0], "
                    + "[2.0, 3.0], "
                    + "[4.0, 5.0, 6.0, 7.0]"
                    + "]",
                    list
                    .segment(i -> (int)i));
        });
    }
    
    @Test
    public void testSegmentWhen() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0], "
                    + "[3.0, 4.0, 5.0], "
                    + "[6.0, 7.0, 8.0], "
                    + "[9.0, 10.0, 11.0], "
                    + "[12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0], "
                    + "[18.0, 19.0]"
                    + "]",
                    list
                    .segmentWhen(theDouble.remainderBy(3).thatIsZero())
                    .map        (DoubleFuncList::toListString)
            );
        });
    }
    
    @Test
    public void testSegmentAfter() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "["
                    + "[0.0], "
                    + "[1.0, 2.0, 3.0], "
                    + "[4.0, 5.0, 6.0], "
                    + "[7.0, 8.0, 9.0], "
                    + "[10.0, 11.0, 12.0], "
                    + "[13.0, 14.0, 15.0], "
                    + "[16.0, 17.0, 18.0], "
                    + "[19.0]"
                    + "]",
                    list
                    .segmentAfter(theDouble.remainderBy(3).thatIsZero())
                    .map         (DoubleFuncList::toListString)
            );
        });
    }
    
    @Test
    public void testSegmentBetween() {
        DoublePredicate startCondition = i ->(i % 10) == 3;
        DoublePredicate endCondition   = i ->(i % 10) == 6;
        
        run(DoubleFuncList.wholeNumbers(75), list -> {
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0], "
                    + "[73.0, 74.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition)
                    .skip          (5)
                    .limit         (3));
            
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0], "
                    + "[73.0, 74.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, true)
                    .skip   (5)
                    .limit  (3));
            
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
            
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0], "
                    + "[73.0, 74.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, IncompletedSegment.included)
                    .skip          (5)
                    .limit         (3));
            
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, IncompletedSegment.excluded)
                    .skip          (5)
                    .limit         (3));
        });
        
        
        // Edge cases
        
        // Empty
        run(DoubleFuncList.wholeNumbers(0), list -> {
            assertStrings(
                    "[]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Not enough
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "[]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact
        run(DoubleFuncList.wholeNumbers(67), list -> {
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact - 1
        run(DoubleFuncList.wholeNumbers(66), list -> {
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        // Exact + 1
        run(DoubleFuncList.wholeNumbers(68), list -> {
            assertStrings(
                    "["
                    + "[53.0, 54.0, 55.0, 56.0], "
                    + "[63.0, 64.0, 65.0, 66.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false)
                    .skip          (5)
                    .limit         (3));
        });
        
        // From start
        run(DoubleFuncList.wholeNumbers(30), list -> {
            assertStrings(
                    "["
                    + "[3.0, 4.0, 5.0, 6.0], "
                    + "[13.0, 14.0, 15.0, 16.0], "
                    + "[23.0, 24.0, 25.0, 26.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false));
        });
        
        // Incomplete start
        run(DoubleFuncList.wholeNumbers(30).skip(5), list -> {
            assertStrings(
                    "["
                    + "[13.0, 14.0, 15.0, 16.0], "
                    + "[23.0, 24.0, 25.0, 26.0]"
                    + "]",
                    list
                    .segmentBetween(startCondition, endCondition, false));
        });
    }
    
    @Test
    public void testSegmentByPercentiles() {
        run(DoubleFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]", 
                    list.segmentByPercentiles(30, 80));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]", 
                    list.segmentByPercentiles(30.0, 80.0));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]", 
                    list.segmentByPercentiles(IntFuncList.of(30,   80)));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]", 
                    list.segmentByPercentiles(DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper() {
        run(DoubleFuncList.wholeNumbers(50), list -> {
            assertStrings(
                    "["
                    + "[49.0, 48.0, 47.0, 46.0, 45.0, 44.0, 43.0, 42.0, 41.0, 40.0, 39.0, 38.0, 37.0, 36.0, 35.0], "
                    + "[34.0, 33.0, 32.0, 31.0, 30.0, 29.0, 28.0, 27.0, 26.0, 25.0, 24.0, 23.0, 22.0, 21.0, 20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0], "
                    + "[9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, 30, 80));
            assertStrings(
                    "["
                    + "[49.0, 48.0, 47.0, 46.0, 45.0, 44.0, 43.0, 42.0, 41.0, 40.0, 39.0, 38.0, 37.0, 36.0, 35.0], "
                    + "[34.0, 33.0, 32.0, 31.0, 30.0, 29.0, 28.0, 27.0, 26.0, 25.0, 24.0, 23.0, 22.0, 21.0, 20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0], "
                    + "[9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, 30.0, 80.0));
            assertStrings(
                    "["
                    + "[49.0, 48.0, 47.0, 46.0, 45.0, 44.0, 43.0, 42.0, 41.0, 40.0, 39.0, 38.0, 37.0, 36.0, 35.0], "
                    + "[34.0, 33.0, 32.0, 31.0, 30.0, 29.0, 28.0, 27.0, 26.0, 25.0, 24.0, 23.0, 22.0, 21.0, 20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0], "
                    + "[9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30,   80)));
            assertStrings(
                    "["
                    + "[49.0, 48.0, 47.0, 46.0, 45.0, 44.0, 43.0, 42.0, 41.0, 40.0, 39.0, 38.0, 37.0, 36.0, 35.0], "
                    + "[34.0, 33.0, 32.0, 31.0, 30.0, 29.0, 28.0, 27.0, 26.0, 25.0, 24.0, 23.0, 22.0, 21.0, 20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0], "
                    + "[9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    @Test
    public void testSegmentByPercentiles_mapper_comparator() {
        run(DoubleFuncList.wholeNumbers(50).toFuncList(), list -> {
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, theDouble.descendingOrder(), 30, 80));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, theDouble.descendingOrder(), 30.0, 80.0));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, theDouble.descendingOrder(), IntFuncList   .of(30,   80)));
            assertStrings(
                    "["
                    + "[0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0], "
                    + "[15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0], "
                    + "[40.0, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0]"
                    + "]",
                    list.segmentByPercentiles(x -> 100 - x, theDouble.descendingOrder(), DoubleFuncList.of(30.0, 80.0)));
        });
    }
    
    //-- FuncListWithSort --
    
    @Test
    public void testSortedBy() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[1.0, 2.0, 3.0, 4.0]", 
                    list.sortedBy(theDouble.plus(2).square()));
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(DoubleFuncList.of(One, Two, Three, Four), list -> {
            assertStrings(
                    "[4.0, 3.0, 2.0, 1.0]",
                    list.sortedBy(
                            d -> (d + 2)*(d + 2),
                            (a,b)-> Double.compare(b, a)));
            // Using comparable access.
            assertStrings(
                    "[4.0, 3.0, 2.0, 1.0]",
                    list.sortedBy(
                            theDouble.plus(2).square(),
                            (a,b)-> Double.compare(b, a)));
        });
    }
    
    //-- FuncListWithSplit --
    
    @Test
    public void testSplitTuple() {
        val d = 2.0;
        val d2 = d % 2;
        System.out.println(d2);
        
        run(DoubleFuncList.wholeNumbers(20).toFuncList(), list -> {
            assertStrings(
                    "("
                    + "[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0],"
                    + "[1.0, 3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0]"
                    + ")",
                     list
                    .split(theDouble.remainderBy(2).thatIsZero())
                    .toString());
        });
    }
    
    @Test
    public void testSplit() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            String Other = "Other";
            assertStrings(
                    "{"
                    + "Other:[1.0, 3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                    .split("Two", theDouble.remainderBy(2).thatIsZero(),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "Other:[1.0, 5.0, 7.0, 11.0, 13.0, 17.0, 19.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero(),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "Five:[5.0], "
                    + "Other:[1.0, 7.0, 11.0, 13.0, 17.0, 19.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero(),
                            "Five",  theDouble.remainderBy(5).thatIsZero(),
                           Other)
                    .sorted()
                    .toString());
            assertStrings(
                    "{"
                    + "Five:[5.0], "
                    + "Seven:[7.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Other:[1.0, 11.0, 13.0, 17.0, 19.0]"
                    + "}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero(),
                            "Five",  theDouble.remainderBy(5).thatIsZero(),
                            "Seven", theDouble.remainderBy(7).thatIsZero(),
                           Other)
                    .toString());
            assertStrings(
                    "{"
                    + "Eleven:[11.0], "
                    + "Five:[5.0], "
                    + "Other:[1.0, 13.0, 17.0, 19.0], "
                    + "Seven:[7.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                    .split("Two",    theDouble.remainderBy(2).thatIsZero(),
                           "Three",  theDouble.remainderBy(3).thatIsZero(),
                           "Five",   theDouble.remainderBy(5).thatIsZero(),
                           "Seven",  theDouble.remainderBy(7).thatIsZero(),
                           "Eleven", theDouble.remainderBy(11).thatIsZero(),
                           Other)
                    .sorted()
                    .toString());
            
            // Ignore some values
            
            assertStrings(
                    "{"
                    + "Eleven:[11.0], "
                    + "Five:[5.0], "
                    + "Other:[1.0, 13.0, 17.0, 19.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                    .split("Two",    theDouble.remainderBy(2).thatIsZero(),
                           null,     theDouble.remainderBy(3).thatIsZero(),
                           "Five",   theDouble.remainderBy(5).thatIsZero(),
                           null,     theDouble.remainderBy(7).thatIsZero(),
                           "Eleven", theDouble.remainderBy(11).thatIsZero(),
                           Other)
                    .sorted()
                    .toString());
            
            // Ignore others
            
            assertStrings(
                    "{"
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero())
                    .sorted()
                    .toString());
            
            assertStrings(
                    "{"
                    + "Five:[5.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero(),
                            "Five",  theDouble.remainderBy(5).thatIsZero())
                    .sorted()
                    .toString());
            
            assertStrings(
                    "{"
                    + "Five:[5.0], "
                    + "Seven:[7.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                     .split("Two",   theDouble.remainderBy(2).thatIsZero(),
                            "Three", theDouble.remainderBy(3).thatIsZero(),
                            "Five",  theDouble.remainderBy(5).thatIsZero(),
                            "Seven", theDouble.remainderBy(7).thatIsZero())
                    .sorted()
                    .toString());
            
            assertStrings(
                    "{"
                    + "Eleven:[11.0], "
                    + "Five:[5.0], "
                    + "Seven:[7.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                     .split("Two",    theDouble.remainderBy(2).thatIsZero(),
                            "Three",  theDouble.remainderBy(3).thatIsZero(),
                            "Five",   theDouble.remainderBy(5).thatIsZero(),
                            "Seven",  theDouble.remainderBy(7).thatIsZero(),
                            "Eleven", theDouble.remainderBy(11).thatIsZero())
                    .sorted()
                    .toString());
            
            assertStrings(
                    "{"
                    + "Eleven:[11.0], "
                    + "Five:[5.0], "
                    + "Seven:[7.0], "
                    + "Thirteen:[13.0], "
                    + "Three:[3.0, 9.0, 15.0], "
                    + "Two:[0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0]"
                    + "}",
                     list
                    .split("Two",      theDouble.remainderBy(2).thatIsZero(),
                           "Three",    theDouble.remainderBy(3).thatIsZero(),
                           "Five",     theDouble.remainderBy(5).thatIsZero(),
                           "Seven",    theDouble.remainderBy(7).thatIsZero(),
                           "Eleven",   theDouble.remainderBy(11).thatIsZero(),
                           "Thirteen", theDouble.remainderBy(13).thatIsZero())
                    .sorted()
                    .toString());
        });
    }
    
    @Test
    public void testSplit_ignore() {
        run(DoubleFuncList.wholeNumbers(20), list -> {
            assertStrings(
                    "{}",
                     list
                    .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                           (String)null)
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                    .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                           (String)null, theDouble.remainderBy(3).thatIsZero(),
                           (String)null)
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                    .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                           (String)null, theDouble.remainderBy(3).thatIsZero(),
                           (String)null, theDouble.remainderBy(5).thatIsZero(),
                           (String)null)
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                    .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                           (String)null, theDouble.remainderBy(3).thatIsZero(),
                           (String)null, theDouble.remainderBy(5).thatIsZero(),
                           (String)null, theDouble.remainderBy(7).thatIsZero(),
                           (String)null)
                    .toString());
            assertStrings(
                    "{}",
                     list
                    .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                           (String)null, theDouble.remainderBy(3).thatIsZero(),
                           (String)null, theDouble.remainderBy(5).thatIsZero(),
                           (String)null, theDouble.remainderBy(7).thatIsZero(),
                           (String)null, theDouble.remainderBy(11).thatIsZero(),
                           (String)null)
                    .sorted()
                    .toString());
            
            // No other
            
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero())
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                            (String)null, theDouble.remainderBy(3).thatIsZero())
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                            (String)null, theDouble.remainderBy(3).thatIsZero(),
                            (String)null, theDouble.remainderBy(5).thatIsZero())
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                            (String)null, theDouble.remainderBy(3).thatIsZero(),
                            (String)null, theDouble.remainderBy(5).thatIsZero(),
                            (String)null, theDouble.remainderBy(7).thatIsZero())
                    .toString());
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                            (String)null, theDouble.remainderBy(3).thatIsZero(),
                            (String)null, theDouble.remainderBy(5).thatIsZero(),
                            (String)null, theDouble.remainderBy(7).thatIsZero(),
                            (String)null, theDouble.remainderBy(11).thatIsZero())
                    .sorted()
                    .toString());
            assertStrings(
                    "{}",
                     list
                     .split((String)null, theDouble.remainderBy(2).thatIsZero(),
                            (String)null, theDouble.remainderBy(3).thatIsZero(),
                            (String)null, theDouble.remainderBy(5).thatIsZero(),
                            (String)null, theDouble.remainderBy(7).thatIsZero(),
                            (String)null, theDouble.remainderBy(11).thatIsZero(),
                            (String)null, theDouble.remainderBy(13).thatIsZero())
                    .sorted()
                    .toString());
        });
    }
    
    @Test
    public void testFizzBuzz() {
        Function<DoubleFuncList, DoubleFuncList> listToList = s -> s.toImmutableList();
        run(DoubleFuncList.wholeNumbers(20), list -> {
            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap))
            .run(() -> {
                FuncMap<String, DoubleFuncList> splited
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
                    + "FizzBuzz:[0.0, 15.0], "
                    + "Buzz:[5.0, 10.0], "
                    + "Fizz:[3.0, 6.0, 9.0, 12.0, 18.0]"
                    + "}",
                    toString);
        });
    }
    
}
