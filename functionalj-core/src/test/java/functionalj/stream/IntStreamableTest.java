//package functionalj.stream;
//
//import static functionalj.lens.Access.theInteger;
//import static functionalj.lens.Access.theString;
//import static functionalj.FuncList.intFuncList.IntFuncList.compound;
//import static functionalj.FuncList.intFuncList.IntFuncList.cycle;
//import static functionalj.FuncList.intFuncList.IntFuncList.empty;
//import static functionalj.FuncList.intFuncList.IntFuncList.emptyIntFuncList;
//import static functionalj.FuncList.intFuncList.IntFuncList.generate;
//import static functionalj.FuncList.intFuncList.IntFuncList.generateWith;
//import static functionalj.FuncList.intFuncList.IntFuncList.infinite;
//import static functionalj.FuncList.intFuncList.IntFuncList.infiniteInt;
//import static functionalj.FuncList.intFuncList.IntFuncList.ints;
//import static functionalj.FuncList.intFuncList.IntFuncList.iterate;
//import static functionalj.FuncList.intFuncList.IntFuncList.loop;
//import static functionalj.FuncList.intFuncList.IntFuncList.naturalNumbers;
//import static functionalj.FuncList.intFuncList.IntFuncList.ones;
//import static functionalj.FuncList.intFuncList.IntFuncList.range;
//import static functionalj.FuncList.intFuncList.IntFuncList.repeat;
//import static functionalj.FuncList.intFuncList.IntFuncList.steamableOf;
//import static functionalj.FuncList.intFuncList.IntFuncList.wholeNumbers;
//import static functionalj.FuncList.intFuncList.IntFuncList.zeroes;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.function.IntFunction;
//
//import org.junit.Test;
//
//import functionalj.function.FuncUnit0;
//import functionalj.stream.intstream.IntStreamPlus;
//import functionalj.FuncList.FuncList;
//import functionalj.FuncList.intFuncList.IntFuncList;
//import lombok.val;
//
//
//public class IntFuncListTest {
//
//    void run(FuncUnit0 runnable) {
//        runnable.run();
//        runnable.run();
//    }
//
//    @Test
//    public void testEmpty() {
//        val streamble = empty();
//        run(()->{
//            assertEquals("[]", streamble.toListString());
//        });
//    }
//
//    @Test
//    public void testEmptyIntStream() {
//        val streamble = emptyIntFuncList();
//        run(()->{
//            assertEquals("[]", streamble.toListString());
//        });
//    }
//
//    @Test
//    public void testOf() {
//        val intArray = new int[] {1, 1, 2, 3, 5, 8};
//
//        val streamble1 = IntFuncList.of(intArray);
//        run(()->{
//            assertEquals("[1, 1, 2, 3, 5, 8]", streamble1.toListString());
//        });
//
//        val streamble2 = steamableOf(intArray);
//        run(()->{
//            assertEquals("[1, 1, 2, 3, 5, 8]", streamble2.toListString());
//        });
//
//        val streamble3 = ints(intArray);
//        run(()->{
//            assertEquals("[1, 1, 2, 3, 5, 8]", streamble3.toListString());
//        });
//
//        val nullStreamble = IntFuncList.of(null);
//        run(()->{
//            assertEquals("[]", nullStreamble.toListString());
//        });
//
//        val zeroStreamble = IntFuncList.of(new int[0]);
//        run(()->{
//            assertEquals("[]", zeroStreamble.toListString());
//        });
//    }
//
//    @Test
//    public void testOf_immutable() {
//        val intArray = new int[] {1, 1, 2, 3, 5, 8};
//        val streamble = steamableOf(intArray);
//        run(()->{
//            intArray[0] = 0;
//            assertEquals("[1, 1, 2, 3, 5, 8]", streamble.toListString());
//            // NOICE ------^  The value are not changed after.
//        });
//    }
//
//    @Test
//    public void testZeroes() {
//        val zeroes  = zeroes();
//        val zeroes6 = zeroes(6);
//        run(()->{
//            assertEquals("[0, 0, 0, 0, 0]",    zeroes.limit(5).toListString());
//            assertEquals("[0, 0, 0, 0, 0, 0]", zeroes6.toListString());
//        });
//    }
//
//    @Test
//    public void testOnes() {
//        val ones  = ones();
//        val ones6 = ones(6);
//        run(()->{
//            assertEquals("[1, 1, 1, 1, 1]",    ones.limit(5).toListString());
//            assertEquals("[1, 1, 1, 1, 1, 1]", ones6.toListString());
//        });
//    }
//
//    @Test
//    public void testRepeat() {
//        val FuncList = repeat(1, 2, 3);
//        run(()->{
//            assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1]", FuncList.limit(10).toListString());
//        });
//    }
//
//    @Test
//    public void testCycle() {
//        val FuncList = cycle(1, 2, 3);
//        run(()->{
//            assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]", FuncList.limit(11).toListString());
//        });
//    }
//
//    @Test
//    public void testLoop() {
//        val loop  = loop();
//        val loop5 = loop(5);
//        run(()->{
//            assertEquals("[0, 1, 2, 3, 4]", loop.limit(5).toListString());
//            assertEquals("[0, 1, 2, 3, 4]", loop5.toListString());
//        });
//    }
//
//    @Test
//    public void testInfinite() {
//        val FuncList  = infinite();
//        run(()->{
//            assertEquals("[5, 6, 7, 8, 9]", FuncList.skip(5).limit(5).toListString());
//        });
//    }
//
//    @Test
//    public void testInfiniteInt() {
//        val FuncList  = infiniteInt();
//        run(()->{
//            assertEquals("[5, 6, 7, 8, 9]", FuncList.skip(5).limit(5).toListString());
//        });
//    }
//
//    @Test
//    public void testNaturalNumbers() {
//        val FuncList1 = naturalNumbers();
//        val FuncList2 = naturalNumbers(5);
//        run(()->{
//            assertEquals("[1, 2, 3, 4, 5]", FuncList1.limit(5).toListString());
//            assertEquals("[1, 2, 3, 4, 5]", FuncList2.toListString());
//        });
//    }
//
//    @Test
//    public void testWholeNumbers() {
//        val FuncList1 = wholeNumbers();
//        val FuncList2 = wholeNumbers(5);
//        run(()->{
//            assertEquals("[0, 1, 2, 3, 4]", FuncList1.limit(5).toListString());
//            assertEquals("[0, 1, 2, 3, 4]", FuncList2.toListString());
//        });
//    }
//
//    @Test
//    public void testRange() {
//        val FuncList  = range(7, 12);
//        run(()->{
//            assertEquals("[7, 8, 9, 10, 11]", FuncList.toListString());
//        });
//    }
//
//    @Test
//    public void testGenerate() {
//        val FuncList  = generate(()->5);
//        run(()->{
//            assertEquals("[5, 5, 5]", FuncList.limit(3).toListString());
//        });
//    }
//
//    @Test
//    public void testGenerateWith() {
//        val FuncList  = generateWith(()->IntStreamPlus.of(1, 2, 3));
//        run(()->{
//            assertEquals("[1, 2, 3]", FuncList.toListString());
//        });
//    }
//
//    @Test
//    public void testIterate() {
//        val FuncList1  = iterate(1, a -> a + 1);
//        run(()->{
//            assertEquals("[6, 7, 8, 9, 10]", FuncList1.skip(5).limit(5).toListString());
//        });
//
//        val FuncList2  = iterate(1, 1, (a, b) -> a + b);
//        run(()->{
//            assertEquals("[1, 1, 2, 3, 5, 8, 13]", FuncList2.limit(7).toListString());
//        });
//    }
//
//    @Test
//    public void testConcat() {
//        val range1  = range(0, 5);
//        val range2  = range(21, 27);
//        val FuncList  = IntFuncList.concat(range1, range2);
//        run(()->{
//            assertEquals("["
//                            + "0, 1, 2, 3, 4, "
//                            + "21, 22, 23, 24, 25, 26"
//                        + "]",
//                        FuncList.toListString());
//        });
//    }
//
//    @Test
//    public void testCompound() {
//        val FuncList1 = compound(1, i -> i * 2);
//        run(()->{
//            assertEquals("[32, 64, 128, 256, 512]",
//                    FuncList1.skip(5).limit(5).toListString());
//        });
//
//        val FuncList2 = compound(1, 2, (a, b) -> a * 3 + b);
//        run(()->{
//            assertEquals("[1, 2, 5, 11, 26]",
//                    FuncList2.limit(5).toListString());
//        });
//    }
//
//    @Test
//    public void testZipOf() {
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8)]",
//                        zipOf(FuncList1, FuncList2)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (0,9), (0,10), (0,11)]",
//                        zipOf(FuncList1, FuncList2, 0)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (-1,9), (-1,10), (-1,11)]",
//                        zipOf(FuncList1, -1, FuncList2, 1)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,8), (6,9), (7,10), (8,11), (9,1), (10,1), (11,1)]",
//                        zipOf(FuncList1, -1, FuncList2, 1)
//                        .toListString());
//            });
//        }
//
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]",
//                        FuncList.zipOf(FuncList1, FuncList2, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, 0-9, 0-10, 0-11]",
//                        FuncList.zipOf(FuncList1, FuncList2, 0, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]",
//                        FuncList.zipOf(FuncList1, -1, FuncList2, 1, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[5-8, 6-9, 7-10, 8-11, 9-1, 10-1, 11-1]",
//                        FuncList.zipOf(FuncList1, -1, FuncList2, 1, (a, b) -> a + "-" + b)
//                        .toListString());
//                    });
//        }
//
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals("[7, 9, 11, 13, 15]",
//                        zipOf(FuncList1, FuncList2, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[7, 9, 11, 13, 15, 7, 8, 9, 10, 11]",
//                        zipOf(FuncList1, FuncList2, 0, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[7, 9, 11, 13, 15, 6, 7, 8, 9, 10]",
//                        zipOf(FuncList1, -1, FuncList2, 1, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[13, 15, 17, 19, 10, 11, 12]",
//                        zipOf(FuncList1, -1, FuncList2, 1, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//    }
//
//    @Test
//    public void testZipWith() {
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8)]",
//                        FuncList1.zipWith(FuncList2)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (0,9), (0,10), (0,11)]",
//                        FuncList1.zipWith(FuncList2, 0)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (-1,9), (-1,10), (-1,11)]",
//                        FuncList1.zipWith(FuncList2, -1, 1)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[(5,8), (6,9), (7,10), (8,11), (9,1), (10,1), (11,1)]",
//                        FuncList1.zipWith(FuncList2, -1, 1)
//                        .toListString());
//            });
//        }
//
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]",
//                        FuncList1.zipToObjWith(FuncList2, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, 0-9, 0-10, 0-11]",
//                        FuncList1.zipToObjWith(FuncList2, 0, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]",
//                        FuncList1.zipToObjWith(FuncList2, -1, 1, (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[5-8, 6-9, 7-10, 8-11, 9-1, 10-1, 11-1]",
//                        FuncList1.zipToObjWith(FuncList2, -1, 1, (a, b) -> a + "-" + b)
//                        .toListString());
//                    });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]",
//                        FuncList1.zipToObjWith(-1, FuncList2.boxed(), (a, b) -> a + "-" + b)
//                        .toListString());
//            });
//        }
//
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals("[7, 9, 11, 13, 15]",
//                        FuncList1.zipWith(FuncList2, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[7, 9, 11, 13, 15, 7, 8, 9, 10, 11]",
//                        FuncList1.zipWith(FuncList2, 0, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 10);
//            val FuncList2 = range(2, 12);
//            run(()->{
//                assertEquals(
//                        "[7, 9, 11, 13, 15, 6, 7, 8, 9, 10]",
//                        FuncList1.zipWith(FuncList2, -1, 1, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//        {
//            val FuncList1 = range(5, 12);
//            val FuncList2 = range(8, 12);
//            run(()->{
//                assertEquals(
//                        "[13, 15, 17, 19, 10, 11, 12]",
//                        FuncList1.zipWith(FuncList2, -1, 1, (a, b) -> a + b)
//                        .toListString());
//            });
//        }
//    }
//
//    @Test
//    public void testMap() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8);
//        run(()->{
//            assertEquals(
//                    "[2, 2, 4, 6, 10, 16]",
//                    FuncList
//                    .map(i -> i * 2)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testMapToInt() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8);
//        run(()->{
//            assertEquals(
//                    "[2, 2, 4, 6, 10, 16]",
//                    FuncList
//                    .mapToInt(i -> i * 2)
//                    .toListString());
//        });
//    }
//
////    @Test
////    public void testMapToLong() {
////        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
////        assertEquals(
////                "[2, 2, 4, 6, 10, 16]",
////                intStream
////                .mapToLong(i -> i * 2)
////                .toListString());
////    }
////
////    @Test
////    public void testMapToDouble() {
////        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
////        assertEquals(
////                "[2.0, 2.0, 4.0, 6.0, 10.0, 16.0]",
////                intStream
////                .mapToDouble(i -> i * 2)
////                .toListString());
////    }
//
//    @Test
//    public void testMapToObj() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8);
//        run(()->{
//            assertEquals(
//                    "['1', '1', '2', '3', '5', '8']",
//                    FuncList
//                    .mapToObj(i -> "'" + i + "'")
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testFlatMap() {
//        val FuncList = ints(1, 2, 3, 5);
//        run(()->{
//            assertEquals(
//                    "[1, 2, 2, 3, 3, 3, 5, 5, 5, 5, 5]",
//                    FuncList
//                    .flatMap(i -> cycle(i).limit(i))
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testFilter() {
//        val FuncList = loop(10);
//        run(()->{
//            assertEquals(
//                    "[1, 3, 5, 7, 9]",
//                    FuncList
//                    .filter(i -> i % 2 == 1)
//                    .toListString());
//            assertEquals(
//                    "[0, 1, 2]",
//                    FuncList
//                    .filter(theInteger.time(3),
//                            theInteger.thatLessThan(9))
//                    .toListString());
//            assertEquals(
//                    "[0, 1, 2, 3]",
//                    FuncList
//                    .filterAsObject(
//                            (int i) -> "" + (i*3),
//                            theString.length().eq(1))
//                    .toListString());
//            assertEquals(
//                    "[0, 1, 2, 3]",
//                    FuncList
//                    .filterAsObject(
//                            theInteger.time(3).asString(),
//                            theString.length().eq(1))
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testPeek() {
//        val FuncList = loop(5);
//        run(()->{
//            val list = new ArrayList<String>();
//            assertEquals(
//                    "[0, 1, 2, 3, 4]",
//                    FuncList
//                    .peek(i -> list.add("" + i))
//                    .toListString());
//            assertEquals(
//                    "[0, 1, 2, 3, 4]",
//                    list.toString());
//        });
//    }
//
//    @Test
//    public void testLimit() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 2, 3]",
//                    FuncList
//                    .limit(4)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSkip() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21);
//        run(()->{
//            assertEquals(
//                    "[5, 8, 13, 21]",
//                    FuncList
//                    .skip(4)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSkipWhile() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .toListString());
//            assertEquals(
//                    "[0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .skipWhile(i -> i != 0)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSkipUtil() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .toListString());
//            assertEquals(
//                    "[0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .skipUntil(i -> i == 0)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testTakeWhile() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .toListString());
//            assertEquals(
//                    "[1, 1, 2, 3]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .takeWhile(i -> i != 0)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testTakeUtil() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .toListString());
//            assertEquals(
//                    "[1, 1, 2, 3]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .takeUntil(i -> i == 0)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testDistinct() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 2, 3, 0, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .distinct()
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSorted() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .sorted()
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSortedBy() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]",
//                    FuncList
//                    .map(i -> i % 5)
//                    .sorted()
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSortedBy_mapper() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[34, 21, 13, 8, 5, 55, 3, 2, 1, 1, 89]",
//                    FuncList
//                    .sortedBy(i -> Math.abs(i - 30))
//                    .toListString());
//            assertEquals(
//                    "[34 -> 4, 21 -> 9, 13 -> 17, 8 -> 22, 5 -> 25, 55 -> 25, 3 -> 27, 2 -> 28, 1 -> 29, 1 -> 29, 89 -> 59]",
//                    FuncList
//                    .sortedBy(i -> Math.abs(i - 30))
//                    .mapToObj(i -> "" + i + " -> " + Math.abs(i - 30))
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSortedBy_mapper_comparator() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[89, 1, 1, 2, 3, 5, 55, 8, 13, 21, 34]",
//                    FuncList
//                    .sortedBy(i -> Math.abs(i - 30), (a, b) -> b - a)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSortedBy_mapper_object() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[1, 1, 13, 2, 21, 3, 34, 5, 55, 8, 89]",
//                    FuncList
//                    .sortedByObj(i -> "" + i)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testSortedBy_mapper_object_with_comparator() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            assertEquals(
//                    "[13, 21, 34, 55, 89, 1, 1, 2, 3, 5, 8]",
//                    FuncList
//                    .sortedByObj(i -> "" + i, (a, b) -> b.length() - a.length())
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testForEachOrdered() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
//        run(()->{
//            val list = new ArrayList<Integer>();
//            FuncList
//            .map(i -> Math.abs(i - 30))
//            .forEachOrdered(list::add);
//            assertEquals(
//                    "[29, 29, 28, 27, 25, 22, 17, 9, 4, 25, 59]",
//                    list.toString());
//        });
//    }
//
//    @Test
//    public void testReduce() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    OptionalInt.of(88),
//                    FuncList
//                    .reduce((a, b) -> a + b));
//        });
//    }
//
//    @Test
//    public void testReduce_withIdentity() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    188,
//                    FuncList
//                    .reduce(100, (a, b) -> a + b));
//        });
//    }
//
//    @Test
//    public void testCollect() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    ",1,1,2,3,5,8,13,21,34",
//                    FuncList
//                    .collect(
//                            ()-> new StringBuffer(),
//                            (StringBuffer a, int          b)-> a.append("," + b),
//                            (StringBuffer a, StringBuffer b)-> a.append(";" + b))
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMin() {
//        val FuncList1 = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        val FuncList2 = ints();
//        run(()->{
//            assertEquals(
//                    "OptionalInt[0]",
//                    FuncList1
//                    .map(i -> Math.abs(i - 34))
//                    .min()
//                    .toString());
//            assertEquals(
//                    "OptionalInt.empty",
//                    FuncList2
//                    .min()
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMax() {
//        val FuncList1 = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        val FuncList2 = ints();
//        run(()->{
//            assertEquals(
//                    "OptionalInt[33]",
//                    FuncList1
//                    .map(i -> Math.abs(i - 34))
//                    .max()
//                    .toString());
//            assertEquals(
//                    "OptionalInt.empty",
//                    FuncList2
//                    .max()
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testCountSize() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    9L,
//                    FuncList
//                    .count());
//            assertEquals(
//                    9,
//                    FuncList
//                    .size());
//        });
//    }
//
//    @Test
//    public void testMatch() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertTrue(FuncList.anyMatch(i -> i == 13));
//            assertFalse(FuncList.anyMatch(i -> i == 14));
//
//            assertTrue(FuncList.allMatch(i -> i < 50));
//            assertFalse(FuncList.allMatch(i -> i < 20));
//
//            assertTrue(FuncList.noneMatch(i -> i == 15));
//            assertFalse(FuncList.noneMatch(i -> i == 21));
//        });
//    }
//
//    @Test
//    public void testFind() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(OptionalInt.of(21),  FuncList.filter(i -> i == 21).findFirst());
//            assertEquals(OptionalInt.empty(), FuncList.filter(i -> i == 55).findFirst());
//
//            assertEquals(OptionalInt.of(21),  FuncList.filter(i -> i == 21).findAny());
//            assertEquals(OptionalInt.empty(), FuncList.filter(i -> i == 55).findAny());
//        });
//    }
//
//    @Test
//    public void testAsStream() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    StreamPlus.of(1, 1, 2, 3, 5, 8, 13, 21, 34).toListString(),
//                    FuncList.intStream().toListString());
//        });
//    }
//
//    @Test
//    public void testToArray() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertArrayEquals(
//                    new int[] {1, 1, 2, 3, 5, 8, 13, 21, 34},
//                    FuncList.toArray());
//        });
//    }
//
//    @Test
//    public void testSum() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    88,
//                    FuncList.sum());
//        });
//    }
//
//    @Test
//    public void testAverage() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
//        run(()->{
//            assertEquals(
//                    OptionalDouble.of(14.3),
//                    FuncList.average());
//        });
//    }
//
//    @Test
//    public void testSummaryStatistics() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "IntSummaryStatistics{count=9, sum=88, min=1, average=9.777778, max=34}",
//                    FuncList.summaryStatistics().toString());
//        });
//    }
//
//    @Test
//    public void testBoxed() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    StreamPlus.of(1, 1, 2, 3, 5, 8, 13, 21, 34).toList(),
//                    FuncList.boxed().toList());
//        });
//    }
//
//    @Test
//    public void testToImmutableList() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    ImmutableIntFuncList.of(1, 1, 2, 3, 5, 8, 13, 21, 34),
//                    FuncList.toImmutableList());
//        });
//    }
//
//    @Test
//    public void testJoinToString() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "112358132134",
//                    FuncList.joinToString());
//            assertEquals(
//                    "1, 1, 2, 3, 5, 8, 13, 21, 34",
//                    FuncList.joinToString(", "));
//        });
//    }
//
//    @Test
//    public void testPipeable() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
//                    FuncList.pipable().pipeTo(i -> "-" + i.toListString() + "-").toString());
//            assertEquals(
//                    "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
//                    FuncList.pipable().pipeTo(i -> "-" + i.toListString() + "-").toString());
//        });
//    }
//
//    @Test
//    public void testSpawn() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "["
//                    +   "Result:{ Value: 13 }, "
//                    +   "Result:{ Value: 21 }, "
//                    +   "Result:{ Value: 8 }"
//                    + "]",
//                    FuncList
//                        .spawn(i -> DeferAction.from(()->{ Thread.sleep(100*Math.abs(i - 15)); return i; }))
//                        .limit(3)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testAccumulate() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "[1, 2, 4, 7, 12, 20, 33, 54, 88]",
//                    FuncList
//                        .accumulate((a, i) -> a + i)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testRestate() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "[1, 0, 1, 1, 2, 3, 5, 8, 13]",
//                    FuncList
//                        .restate((i, s) -> s.map(j -> j - i))
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMapFirst() {
//        val FuncList = wholeNumbers(14);
//        run(()->{
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, null, 2, null, 2, 3, "
//                    + "2, null, 2, null"
//                    + "]",
//                    FuncList
//                        .mapFirst(
//                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
//                                i -> (Integer)(((i % 3) == 0) ? 3 : null))
//                        .toListString());
//            assertEquals(
//                    "["
//                    + "2, null, 2, 3, 2, 5, 2, null, 2, 3, "
//                    + "2, null, 2, null"
//                    + "]",
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//        val FuncList = wholeNumbers(5);
//        run(()->{
//            assertEquals(
//                    "["
//                    + "(0)((0)), "
//                    + "(1)((1)), "
//                    + "(2)((2)), "
//                    + "(3)((3)), "
//                    + "(4)((4))]",
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//        val FuncList = wholeNumbers(5);
//        run(()->{
//            assertEquals(
//                    "["
//                    + "(0,#0), "
//                    + "(1,#1), "
//                    + "(2,#2), "
//                    + "(3,#3), "
//                    + "(4,#4)"
//                    + "]",
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//        val FuncList = wholeNumbers(5);
//        run(()->{
//            assertEquals(
//                    "["
//                    + "{1:0}, "
//                    + "{1:1}, "
//                    + "{1:2}, "
//                    + "{1:3}, "
//                    + "{1:4}"
//                    + "]",
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//        val FuncList = wholeNumbers(10);
//        run(()->{
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8], "
//                    + "[9]"
//                    + "]",
//                    FuncList
//                    .segment(3)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8]"
//                    + "]",
//                    FuncList
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
//                    FuncList
//                    .segment(3, true)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2], "
//                    + "[3, 4, 5], "
//                    + "[6, 7, 8]"
//                    + "]",
//                    FuncList
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
//                    FuncList
//                    .segment(3, IncompletedSegment.included)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    FuncList
//                    .segment(i -> i % 4 == 0)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    FuncList
//                    .segment(i -> i % 4 == 0, IncompletedSegment.included)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9]"
//                    + "]",
//                    FuncList
//                    .segment(i -> i % 4 == 0, true)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[0, 1, 2, 3], "
//                    + "[4, 5, 6, 7]"
//                    + "]",
//                    FuncList
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
//        val FuncList = wholeNumbers(75);
//        run(()->{
//            assertEquals("["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66], "
//                    + "[73, 74]"
//                    + "]",
//                    FuncList
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
//                    FuncList
//                    .segment(startCondition, endCondition, true)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    FuncList
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
//                    FuncList
//                    .segment(startCondition, endCondition, IncompletedSegment.included)
//                    .skip(5)
//                    .map(streamToString)
//                    .toListString());
//
//            assertEquals("["
//                    + "[53, 54, 55, 56], "
//                    + "[63, 64, 65, 66]"
//                    + "]",
//                    FuncList
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
//        val FuncList = wholeNumbers(30);
//        run(()->{
//            assertEquals("["
//                    + "[], "
//                    + "[1], "
//                    + "[2, 3], "
//                    + "[4, 5, 6, 7], "
//                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
//                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
//                    + "]",
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
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
//                    FuncList
//                    .segmentSize(i -> i, IncompletedSegment.excluded)
//                    .map(streamToString)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testCollapseWhen() {
//        val FuncList = wholeNumbers(10);
//        run(()->{
//            // [0, 1, 2 + 3, 4, 5 + 6, 7, 8 + 9]
//            assertEquals("[0, 1, 9, 11, 24]",
//                    FuncList
//                    .collapseWhen(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testCollapseAfter() {
//        val FuncList = wholeNumbers(10);
//        run(()->{
//            // [0 + 1, 2, 3 + 4 + 5, 6 + 7, 8 + 9]
//            assertEquals("[1, 2, 12, 13, 17]",
//                    FuncList
//                    .collapseAfter(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testCollapseSize() {
//        val FuncList = wholeNumbers(10);
//        run(()->{
//            // [0, 1, 2 + 3, 4 + 5 + 6 + 7]
//            assertEquals("[1, 5, 22, 17]",
//                    FuncList
//                    .collapseSize(i -> i, (a, b) -> a + b)
//                    .toListString());
//
//            assertEquals("[1, 5, 22, 17]",
//                    FuncList
//                    .collapseSize(i -> i, (a, b) -> a + b, true)
//                    .toListString());
//
//            assertEquals("[1, 5, 22]",
//                    FuncList
//                    .collapseSize(i -> i, (a, b) -> a + b, false)
//                    .toListString());
//
//            assertEquals("[1, 5, 22, 17]",
//                    FuncList
//                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.included)
//                    .toListString());
//
//            assertEquals("[1, 5, 22]",
//                    FuncList
//                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.excluded)
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testConcatWith() {
//        val FuncList = range(0, 5);
//        val anotherStreamble = range(21, 27);
//        run(()->{
//            assertEquals("["
//                            + "0, 1, 2, 3, 4, "
//                            + "21, 22, 23, 24, 25, 26"
//                        + "]",
//                        FuncList
//                        .concatWith(anotherStreamble)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMergeWith() {
//        val FuncList = range(0, 5);
//        val anotherStreamble = range(21, 27);
//        run(()->{
//            assertEquals("[0, 21, 1, 22, 2, 23, 3, 24, 4, 25, 26]",
//                    FuncList
//                            .mergeWith(anotherStreamble)
//                            .toListString());
//        });
//    }
//
//    @Test
//    public void testZipWith_boxed() {
//        val FuncList = range(0, 5);
//        val anotherStreamble1 = range(21, 27);
//        val anotherStreamble2 = range(21, 24);
//        run(()->{
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25)]",
//                         FuncList.zipWith(anotherStreamble1.boxed()).toListString());
//
//            assertEquals("[(0,21), (1,22), (2,23), (3,null), (4,null)]",
//                         FuncList.zipWith(-1, anotherStreamble2.boxed()).toListString());
//
//            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25), (-1,26)]",
//                         FuncList.zipWith(-1, anotherStreamble1.boxed()).toListString());
//
//            assertEquals("[(0,21), (1,22), (2,23)]",
//                         FuncList.zipWith(anotherStreamble2.boxed()).toListString());
//
//            assertEquals("[21, 23, 25]",
//                         FuncList.zipWith(anotherStreamble2.boxed(), (a, b) -> a + b).toListString());
//        });
//    }
//
//    @Test
//    public void testChoose() {
//        val FuncList = range(0, 5);
//        val anotherStreamble = range(22, 30);
//        run(()->{
//            // 0 % 3 = 0 vs 22 % 2 = 0 => 22
//            // 1 % 3 = 1 vs 23 % 2 = 1 => 23
//            // 2 % 3 = 2 vs 24 % 2 = 0 =>  2
//            // 3 % 3 = 0 vs 25 % 2 = 1 => 25
//            // 4 % 3 = 1 vs 26 % 2 = 0 =>  4
//            //           vs 27 % 2 = 1 => none
//            //           vs 28 % 2 = 0 => none
//            //           vs 29 % 2 = 1 => none
//            assertEquals("[22, 23, 2, 25, 4]",
//                         FuncList
//                         .choose(anotherStreamble, (a, b) -> a % 3 > b % 2)
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
//        val sum = new Sum();
//        val max = new Max();
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("45",
//                         "" + FuncList.calculate(sum));
//
//            assertEquals("(45,9)",
//                         "" + FuncList.calculate(sum, max));
//
//            assertEquals("(45,9,45)",
//                         "" + FuncList.calculate(sum, max, sum));
//
//            assertEquals("(45,9,45,9)",
//                         "" + FuncList.calculate(sum, max, sum, max));
//
//            assertEquals("(45,9,45,9,45)",
//                         "" + FuncList.calculate(sum, max, sum, max, sum));
//
//            assertEquals("(45,9,45,9,45,9)",
//                         "" + FuncList.calculate(sum, max, sum, max, sum, max));
//        });
//    }
//
//    @Test
//    public void testMapOnly() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0, 10, 2, 30, 4, 50, 6, 70, 8, 90]",
//                    FuncList
//                    .mapOnly(theInteger.thatIsOdd(), theInteger.time(10))
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testMapIf() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]",
//                        FuncList
//                        .mapIf(
//                            theInteger.thatIsOdd(),
//                            theInteger.time(10),
//                            i -> i/2)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMapToObjIf() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]",
//                        FuncList
//                        .mapToObjIf(
//                            theInteger.thatIsOdd(),
//                            i -> i * 10,
//                            i -> i / 2)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMapWithIndex() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]",
//                        FuncList
//                        .mapWithIndex(
//                            (index, value)->index*value)
//                        .toListString());
//
//            assertEquals("[(0,0), (1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8), (9,9)]",
//                        FuncList
//                        .mapWithIndex()
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMapToObjWithIndex() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0-0, 1-1, 2-2, 3-3, 4-4, 5-5, 6-6, 7-7, 8-8, 9-9]",
//                        FuncList
//                        .mapToObjWithIndex(
//                            (index, value)->index + "-" + value)
//                        .toListString());
//
//            assertEquals("[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]",
//                        FuncList
//                        .mapToObjWithIndex(
//                                i -> "" + i,
//                                (index, value) -> index + ": " + value
//                        )
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testMapWithPrev() {
//        val FuncList = range(0, 10);
//        run(()->{
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
//                    FuncList
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
//                    FuncList
//                    .mapWithPrev(
//                        (prev, i) -> prev + "-" + i
//                    )
//                    .toListString());
//        });
//    }
//
//    @Test
//    public void testFilterIn() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[1, 3, 5, 7, 9]",
//                        FuncList
//                        .filterIn(1, 3, 5, 7, 9, 11, 13)
//                        .toListString());
//
//            assertEquals("[1, 3, 5, 7, 9]",
//                        FuncList
//                        .filterIn(asList(1, 3, 5, 7, 9, 11, 13))
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testExcludeIn() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[5, 6, 7, 8, 9]",
//                        FuncList
//                        .exclude(i -> i < 5)
//                        .toListString());
//
//            assertEquals("[0, 2, 4, 6, 8]",
//                        FuncList
//                        .excludeIn(1, 3, 5, 7, 9, 11, 13)
//                        .toListString());
//
//            assertEquals("[0, 2, 4, 6, 8]",
//                        FuncList
//                        .excludeIn(asList(1, 3, 5, 7, 9, 11, 13))
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testFilterWithIndex() {
//        val FuncList = range(0, 10);
//        run(()->{
//            assertEquals("[0, 1, 2, 3, 8, 9]",
//                        FuncList
//                        .filterWithIndex((i, v) -> i < 4 || v > 7)
//                        .toListString());
//        });
//    }
//
//    @Test
//    public void testPeekMore() {
//        val FuncList = range(0, 10);
//        run(()->{
//            {
//                val lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]",
//                            FuncList
//                            .peek(theInteger.thatIsEven(), i -> lines.add("" + i))
//                            .toListString());
//
//                assertEquals(
//                        "[0, 2, 4, 6, 8]",
//                        "" + lines);
//            }
//
//            {
//                val lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]",
//                            FuncList
//                            .peek((int    i) -> "--> " + i + ";",
//                                  (String s) -> lines.add(s))
//                            .toListString());
//
//                assertEquals(
//                        "[--> 0;, --> 1;, --> 2;, --> 3;, --> 4;, --> 5;, --> 6;, --> 7;, --> 8;, --> 9;]",
//                        "" + lines);
//            }
//
//            {
//                val lines = new ArrayList<String>();
//                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]",
//                            FuncList
//                            .peek((int    i) -> "--> " + i + ";",
//                                  (String s) -> s.contains("5"),
//                                  (String s) -> lines.add(s))
//                            .toListString());
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
//        val FuncList = range(0, 7);
//        run(()->{
//            //            [0, 1 -> [0], 2, 3->[0, 1, 2], 4, 5 -> [0, 1, 2, 3, 4], 6]
//            //            [0,      [0], 2,    [0, 1, 2], 4,      [0, 1, 2, 3, 4], 6]
//            //            [0,       0,  2,     0, 1, 2,  4,       0, 1, 2, 3, 4,  6]
//            assertEquals("[0, 0, 2, 0, 1, 2, 4, 0, 1, 2, 3, 4, 6]",
//                        FuncList
//                        .flatMapOnly(theInteger.thatIsOdd(), i -> range(0, i)).toListString());
//        });
//    }
//
//    @Test
//    public void testFlatMapIf() {
//        val FuncList = range(0, 7);
//        run(()->{
//            //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
//            //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
//            //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
//            assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]",
//                        FuncList
//                        .flatMapIf(
//                                theInteger.thatIsOdd(),
//                                i -> range(0, i),
//                                i -> IntFuncList.of(-i)
//                        )
//                        .toListString());
//        });
//    }
//    
//    @Test
//    public void testForEachWithIndex() {
//        val FuncList = range(0, 10);
//        run(()->{
//            val lines = new ArrayList<String>();
//            FuncList
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
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[34]",
//                    FuncList
//                    .minBy(i -> Math.abs(i - 34))
//                    .toString());
//
//            assertEquals(
//                    "OptionalInt[1]",
//                    FuncList
//                    .minBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMaxBy() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[1]",
//                    FuncList
//                    .maxBy(i -> Math.abs(i - 34))
//                    .toString());
//            assertEquals(
//                    "OptionalInt[34]",
//                    FuncList
//                    .maxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMinOf() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[34]",
//                    FuncList
//                    .minOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMaxOf() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[1]",
//                    FuncList
//                    .maxOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMinMax() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "Optional[(1,34)]",
//                    FuncList
//                    .minMax()
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMinMaxOf() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "Optional[(34,1)]",
//                    FuncList
//                    .minMaxOf(i -> Math.abs(i - 34))
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testMinMaxBy() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "Optional[(34,1)]",
//                    FuncList
//                    .minMaxBy(i -> Math.abs(i - 34))
//                    .toString());
//
//            assertEquals(
//                    "Optional[(1,34)]",
//                    FuncList
//                    .minMaxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testFindFirst() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[5]",
//                    FuncList
//                    .findFirst(i -> i % 5 == 0)
//                    .toString());
//            assertEquals(
//                    "OptionalInt[5]",
//                    FuncList
//                    .findFirst(i -> i*2, i -> i % 5 == 0)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testFindAny() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[5]",
//                    FuncList
//                    .findAny(i -> i % 5 == 0)
//                    .toString());
//            assertEquals(
//                    "OptionalInt[5]",
//                    FuncList
//                    .findAny(i -> i*2, i -> i % 5 == 0)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testFindFirstBy() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[13]",
//                    FuncList
//                    .findFirstBy(
//                            i -> "" + i,
//                            s -> s.length() > 1)
//                    .toString());
//        });
//    }
//
//    @Test
//    public void testFindAnyBy() {
//        val FuncList = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
//        run(()->{
//            assertEquals(
//                    "OptionalInt[13]",
//                    FuncList
//                    .findAnyBy(
//                            i -> "" + i,
//                            s -> s.length() > 1)
//                    .toString());
//        });
//    }
//
//}
